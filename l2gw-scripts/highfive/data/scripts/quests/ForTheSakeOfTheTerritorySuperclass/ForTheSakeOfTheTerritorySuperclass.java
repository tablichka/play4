package quests.ForTheSakeOfTheTerritorySuperclass;

import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowQuestMark;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.commons.arrays.GArray;

import java.util.Arrays;

/**
 * @author: rage
 * @date: 11.07.2010 16:50:13
 */
public abstract class ForTheSakeOfTheTerritorySuperclass extends Quest
{
	private static final String[] _questNames = {
			"_731_ProtectTheMilitaryAssociationLeader",
			"_732_ProtectTheReligiousAssociationLeader",
			"_733_ProtectTheEconomicAssociationLeader",
			"_730_ProtectTheSuppliesSafe",
			"_729_ProtectTheTerritoryCatapult"
	};

	private static final String[] _questStartMessages = {
			"Protect the Military Association Leader TERR",
			"Protect the Religious Association Leader TERR",
			"Protect the Economic Association Leader TERR!",
			"Protect the Supplies Safe TERR",
	};

	private static final String[] _questEndMessages = {
			"The Military Association Leader of TERR has been destroyed!",
			"The Religious Association Leader of TERR has been destroyed!",
			"The Economic Association Leader of TERR  has been destroyed!",
			"The Supplies Safe of TERR has been destroyed!",
			"The catapult of TERR has been destroyed!"
	};

	private static final String[] _territoryName = {
			"",
			"Gludio",
			"Dion",
			"Giran",
			"Oren",
			"Aden",
			"Innadril",
			"Goddard",
			"Rune",
			"Schuttgart"
	};

	public ForTheSakeOfTheTerritorySuperclass(int questId, String questName, String questDescr)
	{
		super(questId, questName, questDescr); // Party true
	}

	public abstract int[] getCatapults();

	public abstract int[] getLeaders();

	public abstract int[] getSupplies();

	@Override
	public String onEvent(String event, QuestState st)
	{
		if("warEnd".equals(event))
		{
			int cond = st.getInt("cond") & 0x7FFFFFFF;
			boolean catapultKilled = (cond & 2) == 2 || (cond & 16) == 16 || (cond & 32) == 32 || (cond & 64) == 64 || (cond & 256) == 256;
			boolean leadersKilled = (cond & 8) == 8 || (cond & 32) == 32 || (cond & 128) == 128 || (cond & 256) == 256 || (cond & 512) == 512;
			boolean suppliesKilled = (cond & 4) == 4 || (cond & 16) == 16 || (cond & 64) == 64 || (cond & 128) == 128 || (cond & 512) == 512;
			float badges = 13;
			if(catapultKilled)
				badges = 3;
			if(leadersKilled)
				badges += 4.5;
			else
			{
				if((st.getInt("leader") & 1) == 1)
					badges += 1.5;
				if((st.getInt("leader") & 2) == 2)
					badges += 1.5;
				if((st.getInt("leader") & 4) == 4)
					badges += 1.5;
			}
			if(suppliesKilled)
				badges += 3;

			st.getPlayer().addBadges(badges);
			if(st.getPlayer().getVarFloat("tw_badges") < 0)
				st.getPlayer().setVar("tw_badges", 0);
			st.exitCurrentQuest(true);
		}

		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(killer.getQuestState(getName()) == null)
			return;

		int killType = 0, leaderType = 0;
		if(Arrays.binarySearch(getCatapults(), npc.getNpcId()) >= 0)
			killType = 1;
		else if((leaderType = Arrays.binarySearch(getLeaders(), npc.getNpcId())) >= 0)
			killType = 2;
		else if(Arrays.binarySearch(getSupplies(), npc.getNpcId()) >= 0)
			killType = 3;

		int terrId = npc.getTerritoryId();
		if(killType > 0)
		{
			GArray<L2Player> players = new GArray<L2Player>();
			L2Party party = killer.getParty();
			if(party != null)
			{
				if(killType == 1 && party.getCommandChannel() != null)
					for(L2Player member : party.getCommandChannel().getMembers())
					{
						QuestState st = member.getQuestState(getName());
						if(st != null && (st.getInt("cond") & 1024) != 1024 && member.getTerritoryId() != npc.getTerritoryId())
							players.add(member);
					}
				else
					for(L2Player member : party.getPartyMembers())
					{
						QuestState st = member.getQuestState(getName());
						if(st != null && (st.getInt("cond") & 1024) != 1024 && member.getTerritoryId() != npc.getTerritoryId())
							players.add(member);
					}
			}
			else if((killer.getQuestState(getName()).getInt("cond") & 1024) != 1024 && killer.getTerritoryId() != npc.getTerritoryId())
				players.add(killer);

			for(L2Player member : players)
			{
				QuestState qs = member.getQuestState(getName());
				int cond = qs.getInt("cond") & 0x7FFFFFFF;
				boolean catapultKilled = killType == 1 || (cond & 2) == 2 || (cond & 16) == 16 || (cond & 32) == 32 || (cond & 64) == 64 || (cond & 256) == 256;
				boolean leadersKilled = (cond & 8) == 8 || (cond & 32) == 32 || (cond & 128) == 128 || (cond & 256) == 256 || (cond & 512) == 512;
				boolean suppliesKilled = killType == 3 || (cond & 4) == 4 || (cond & 16) == 16 || (cond & 64) == 64 || (cond & 128) == 128 || (cond & 512) == 512;
				int newCond = cond;

				switch(killType)
				{
					case 1: // Catapult killed
						if(leadersKilled && suppliesKilled)
							newCond |= 1024;
						else if(leadersKilled && !suppliesKilled)
							newCond |= 256;
						else if(suppliesKilled)
							newCond |= 64;
						else
							newCond |= 2;
						TerritoryWarManager.getTerritoryById(terrId).setCatapultState(true);
						removeQuestForTerritory(terrId, _questNames[4], _questEndMessages[4].replace("TERR", _territoryName[terrId - 80]));
						break;
					case 2: // Leaders killed
						int leaderMask = qs.getInt("leader");
						leaderType %= 3;
						removeQuestForTerritory(terrId, _questNames[leaderType], _questEndMessages[leaderType].replace("TERR", _territoryName[terrId - 80]));
						leaderType++;
						if(leaderType == 3)
							leaderType = 4;
						leaderMask |= leaderType;
						qs.set("leader", String.valueOf(leaderMask));
						leadersKilled = leaderMask == 7;
						if(leadersKilled)
						{
							if(catapultKilled && suppliesKilled)
								newCond |= 1024;
							else if(catapultKilled && !suppliesKilled)
								newCond |= 32;
							else if(!catapultKilled && suppliesKilled)
								newCond |= 128;
							else
								newCond |= 8;
							TerritoryWarManager.getTerritoryById(terrId).setLeadersState(true);
						}
						break;
					case 3: // Supplies killed
						if(catapultKilled && leadersKilled)
							newCond |= 1024;
						else if(catapultKilled && !leadersKilled)
							newCond |= 16;
						else if(!catapultKilled && leadersKilled)
							newCond |= 512;
						else
							newCond |= 4;
						TerritoryWarManager.getTerritoryById(terrId).setSuppliesState(true);
						removeQuestForTerritory(terrId, _questNames[3], _questEndMessages[3].replace("TERR", _territoryName[terrId - 80]));
						break;
				}

				if(newCond != cond)
				{
					newCond |= 0x80000000;
					member.sendPacket(new ExShowQuestMark(getQuestIntId()));
					member.sendPacket(new PlaySound((newCond & 1024) == 1024 ? SOUND_FINISH : SOUND_MIDDLE));
					qs.set("cond", String.valueOf(newCond));
				}
			}
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, L2Player player, L2Skill skill)
	{
		int attackType = 0, leaderType = 0;
		if(Arrays.binarySearch(getCatapults(), npc.getNpcId()) >= 0)
			attackType = 1;
		else if((leaderType = Arrays.binarySearch(getLeaders(), npc.getNpcId())) >= 0)
			attackType = 2;
		else if(Arrays.binarySearch(getSupplies(), npc.getNpcId()) >= 0)
			attackType = 3;

		int terrId = npc.getTerritoryId();
		switch(attackType)
		{
			case 2:
				leaderType %= 3;
				giveQuestForTerritory(terrId, _questNames[leaderType], _questStartMessages[leaderType].replace("TERR", _territoryName[terrId - 80]));
				break;
			case 3:
				giveQuestForTerritory(terrId, _questNames[3], _questStartMessages[3].replace("TERR", _territoryName[terrId - 80]));
				break;
		}

		return null;
	}
}
