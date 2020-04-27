package quests._129_PailakaDevilsLegacy;

import javolution.util.FastList;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

import java.util.List;

/**
 * @author rage
 * @date 06.10.2010 22:20:40
 */
public class _129_PailakaDevilsLegacy extends Quest
{
	// NPC
	private static int DISURVIVOR = 32498;
	private static int SUPPORTER = 32501;
	private static int DADVENTURER = 32508;
	private static int DADVENTURER2 = 32511;
	private static int CHEST = 32495;
	private static int[] Pailaka2nd = new int[] { 18623, 18624, 18625, 18626, 18627 };

	// BOSS
	private static int KAMS = 18629;
	private static int ALKASO = 18631;
	private static int LEMATAN = 18633;

	// ITEMS
	private static int ScrollOfEscape = 736;
	private static int SWORD = 13042;
	private static int ENCHSWORD = 13043;
	private static int LASTSWORD = 13044;
	private static int KDROP = 13046;
	private static int ADROP = 13047;
	private static int KEY = 13150;
	private static int[] HERBS = new int[] { 8601, 8602, 8604, 8605 };
	private static int[] CHESTDROP = new int[] { 13033, 13048, 13049, 13059 };

	// REWARDS
	private static int PBRACELET = 13295;

	public _129_PailakaDevilsLegacy()
	{
		super(129, "_129_PailakaDevilsLegacy", "Pailaka Devil's Legacy");

		addStartNpc(DISURVIVOR);
		addTalkId(SUPPORTER, DADVENTURER, DADVENTURER2);
		addKillId(KAMS, ALKASO, LEMATAN);
		addKillId(Pailaka2nd);
		addAttackId(CHEST);
		addQuestItem(SWORD, ENCHSWORD, LASTSWORD, KDROP, ADROP, KEY);
		addQuestItem(CHESTDROP);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();
		String htmltext = event;
		if(event.equalsIgnoreCase("Enter"))
		{
			if(st.isCompleted())
				return "completed";

			enterInstance(player, st);
			return null;
		}
		else if(event.equalsIgnoreCase("32498-02.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32498-05.htm"))
		{
			st.setCond(2);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32501-03.htm"))
		{
			st.setCond(3);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			st.giveItems(SWORD, 1);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		L2Player player = st.getPlayer();
		if(npcId == DISURVIVOR)
		{
			if(cond == 0)
				if(player.getLevel() < 61 || player.getLevel() > 67)
				{
					htmltext = "32498-no.htm";
					st.exitCurrentQuest(true);
				}
				else
					return "32498-01.htm";
			else if(st.isCompleted())
				htmltext = "32498-no.htm";
			else if(cond == 1 || cond == 2)
				htmltext = "32498-06.htm";
			else
				htmltext = "32498-07.htm";
		}
		else if(npcId == SUPPORTER)
		{
			if(cond == 1 || cond == 2)
				htmltext = "32501-01.htm";
			else
				htmltext = "32501-04.htm";
		}
		else if(npcId == DADVENTURER)
		{
			if(st.getQuestItemsCount(SWORD) > 0 && st.getQuestItemsCount(KDROP) == 0)
				htmltext = "32508-01.htm";
			if(st.getQuestItemsCount(ENCHSWORD) > 0 && st.getQuestItemsCount(ADROP) == 0)
				htmltext = "32508-01.htm";
			if(st.getQuestItemsCount(SWORD) == 0 && st.getQuestItemsCount(KDROP) > 0)
				htmltext = "32508-05.htm";
			if(st.getQuestItemsCount(ENCHSWORD) == 0 && st.getQuestItemsCount(ADROP) > 0)
				htmltext = "32508-05.htm";
			if(st.getQuestItemsCount(SWORD) == 0 && st.getQuestItemsCount(ENCHSWORD) == 0)
				htmltext = "32508-05.htm";
			if(st.getQuestItemsCount(KDROP) == 0 && st.getQuestItemsCount(ADROP) == 0)
				htmltext = "32508-01.htm";
			if(player.getPet() != null)
				htmltext = "32508-04.htm";
			if(st.getQuestItemsCount(SWORD) > 0 && st.getQuestItemsCount(KDROP) > 0)
			{
				st.takeItems(SWORD, 1);
				st.takeItems(KDROP, 1);
				st.giveItems(ENCHSWORD, 1);
				htmltext = "32508-02.htm";
			}
			if(st.getQuestItemsCount(ENCHSWORD) > 0 && st.getQuestItemsCount(ADROP) > 0)
			{
				st.takeItems(ENCHSWORD, 1);
				st.takeItems(ADROP, 1);
				st.giveItems(LASTSWORD, 1);
				htmltext = "32508-03.htm";
			}
			if(st.getQuestItemsCount(LASTSWORD) > 0)
				htmltext = "32508-03.htm";
		}
		else if(npcId == DADVENTURER2)
		{
			if(cond == 4)
			{
				if(player.getPet() != null)
					htmltext = "32511-03.htm";
				else if(player.getPet() == null)
				{
					st.giveItems(ScrollOfEscape, 1);
					st.giveItems(PBRACELET, 1);
					st.addExpAndSp(10810000, 950000);
					st.setCond(5);
					st.setState(COMPLETED);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					player.getVitality().addPoints(20000);
					Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
					if(inst != null)
						inst.successEnd();
					htmltext = "32511-01.htm";
				}
			}
			else if(st.isCompleted())
				htmltext = "32511-02.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		if(npcId == KAMS && st.getQuestItemsCount(KDROP) == 0)
		{
			st.giveItems(KDROP, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == ALKASO && st.getQuestItemsCount(ADROP) == 0)
		{
			st.giveItems(ADROP, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npcId == LEMATAN)
		{
			st.setCond(4);
			st.playSound(SOUND_MIDDLE);
			st.setState(STARTED);
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
				inst.addSpawn(DADVENTURER2, new Location(84990, -208376, -3342, 55000), 0);
		}
		else if(contains(Pailaka2nd, npcId))
		{
			if(Rnd.chance(50))
				npc.dropItem(player, HERBS[Rnd.get(HERBS.length)], 1);
			if(Rnd.chance(50))
				npc.dropItem(player, HERBS[Rnd.get(HERBS.length)], 1);
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		int npcId = npc.getNpcId();
		L2Player player = st.getPlayer();

		if(npcId == CHEST && Rnd.chance(50))
		{
			if(Rnd.get(100) < 80)
				npc.dropItem(player, CHESTDROP[Rnd.get(CHESTDROP.length)], Rnd.get(1, 10));
			else
				npc.dropItem(player, KEY, 1);
			npc.decayMe();
		}
		return null;
	}

	private void enterInstance(L2Player player, QuestState st)
	{
		int instId = 44;
		InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(instId);

		if(it == null)
		{
			_log.warn(this + " try to enter instance id: " + instId + " but no instance template!");
			return;
		}

		if(player.isCursedWeaponEquipped())
		{
			player.sendPacket(new SystemMessage(SystemMessage.C1S_QUEST_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
			return;
		}

		if(player.isInParty())
		{
			player.sendPacket(Msg.A_PARTY_CANNOT_BE_FORMED_IN_THIS_AREA);
			return;
		}

		Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
		List<L2Player> party = new FastList<L2Player>();

		if(inst != null)
		{
			if(inst.getTemplate().getId() != instId)
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_HAVE_ENTERED_ANOTHER_INSTANCE_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON));
				return;
			}
			if(player.getLevel() < it.getMinLevel() || player.getLevel() > it.getMaxLevel())
			{
				player.sendPacket(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
				return;
			}

			if(it.isDispelBuff())
				for(L2Effect e : player.getAllEffects())
				{
					if(e.getNext() != null && e.getNext().isInUse() && e.getNext().getSkill().getBuffProtectLevel() < 1)
						e.getNext().exit();

					if(e.getSkill().getBuffProtectLevel() < 1)
						e.exit();
				}

			//player.setVar("InstanceRP", player.getX() + "," + player.getY() + "," + player.getZ());
			player.setStablePoint(player.getLoc());
			player.teleToLocation(inst.getStartLoc(), inst.getReflection());
			return;
		}

		if(it.getMaxCount() > 0 && InstanceManager.getInstance().getInstanceCount(instId) >= it.getMaxCount())
		{
			player.sendPacket(new SystemMessage(SystemMessage.THE_MAXIMUM_NUMBER_OF_INSTANCE_ZONES_HAS_BEEN_EXCEEDED_YOU_CANNOT_ENTER));
			return;
		}

		if(it.getMinParty() > 1)
		{
			if(player.getParty() == null)
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_ARE_NOT_CURRENTLY_IN_A_PARTY_SO_YOU_CANNOT_ENTER));
				return;
			}
			else if(!player.getParty().isLeader(player))
			{
				player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.ONLY_A_PARTY_LEADER_CAN_TRY_TO_ENTER));
				return;
			}
			else if(player.getParty().getMemberCount() > it.getMaxParty() || player.getParty().getMemberCount() < it.getMinParty())
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_CANNOT_ENTER_DUE_TO_THE_PARTY_HAVING_EXCEEDED_THE_LIMIT));
				return;
			}

			boolean ok = true;
			for(L2Player member : player.getParty().getPartyMembers())
				if(member.getLevel() < it.getMinLevel() || member.getLevel() > it.getMaxLevel())
				{
					player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(member));
					ok = false;
				}
				else if(member.getVar("instance-" + it.getType()) != null || InstanceManager.getInstance().getInstanceByPlayer(member) != null)
				{
					player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1_MAY_NOT_RE_ENTER_YET).addCharName(member));
					ok = false;
				}
				else if(!player.getLastNpc().isInRange(member, 300))
				{
					player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1_IS_IN_A_LOCATION_WHICH_CANNOT_BE_ENTERED_THEREFORE_IT_CANNOT_BE_PROCESSED).addCharName(member));
					ok = false;
				}

			if(!ok)
				return;

			party.addAll(player.getParty().getPartyMembers());
		}
		else
		{
			if(player.getLevel() < it.getMinLevel() || player.getLevel() > it.getMaxLevel())
			{
				player.sendPacket(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
				return;
			}
			else if(player.getVar("instance-" + it.getType()) != null)
			{
				player.sendPacket(new SystemMessage(SystemMessage.C1_MAY_NOT_RE_ENTER_YET).addCharName(player));
				return;
			}

			party.add(player);
		}

		inst = InstanceManager.getInstance().createNewInstance(instId, party);
		if(inst != null)
		{
			if(st.getCond() == 4)
				inst.addSpawn(DADVENTURER2, new Location(84990, -208376, -3342, 55000), 0);

			for(L2Player member : party)
				if(member != null)
				{
					if(it.isDispelBuff())
						for(L2Effect e : member.getAllEffects())
						{
							if(e.getNext() != null && e.getNext().isInUse() && e.getNext().getSkill().getBuffProtectLevel() < 1)
								e.getNext().exit();

							if(e.getSkill().getBuffProtectLevel() < 1)
								e.exit();
						}
					//member.setVar("InstanceRP", member.getX() + "," + member.getY() + "," + member.getZ());
					member.setStablePoint(member.getLoc());
					member.teleToLocation(inst.getStartLoc(), inst.getReflection());
				}
		}
	}
}
