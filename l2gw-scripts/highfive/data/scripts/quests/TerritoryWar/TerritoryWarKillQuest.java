package quests.TerritoryWar;

import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

import static quests.TerritoryWar.TerritoryWarQuest._classQuest;

/**
 * @author: rage
 * @date: 12.07.2010 10:41:33
 */
public abstract class TerritoryWarKillQuest extends Quest
{
	/*
	734:
	Defeat the 7 enemy knights
    Out of 7 knights you have defeated 1.
    You weakened the enemy's defense!

	735:
	Defeat 17 enemy warriors and rogues
	Out of 17 warriors and rogues you have defeated 1.
	You weakened the enemy's attack!

	736:
	Defeat 18 enemy wizards and summoners
	Out of 18 wizards and summoners you have defeated 1.
    You weakened the enemy's magic!

	737:
	Defeat 9 enemy priests and wizards
	Out of 9 priests and wizards you have defeated 8.
    You have weakened the enemy's support!

	738:
	Defeat 5 enemy professionals.
	Out of 5 warsmiths and overlords you have defeated 1.
	You destroyed the enemy's professionals!
	 */


	public TerritoryWarKillQuest(int questId, String questName, String questDescr)
	{
		super(questId, questName, questDescr); // Party true
	}

	public abstract String getMiddleMessage(int max, int kills);

	@Override
	public void onPlayerKill(L2Player killer, L2Player killed)
	{
		if(!TerritoryWarManager.getWar().isInProgress() || !TerritoryWarQuest.checkCondition(killer, killed) ||
				!_classQuest.containsKey(killed.getClassId()) || _classQuest.get(killed.getClassId()) != _questId)
			return;

		killer.addBadges(0.1f);
		if(killed.getVarFloat("tw_badges") > 13)
			killed.addBadges(-0.5f);

		if(killer.getQuestState(getName()) != null && killer.getQuestState(getName()).getInt("kills") < killer.getQuestState(getName()).getInt("max"))
		{
			QuestState qs = killer.getQuestState(getName());
			int kills = qs.getInt("kills") + 1;
			int max = qs.getInt("max");
			qs.set("kills", String.valueOf(kills));
			if(kills == max)
				qs.getQuest().notifyEvent("end", qs);
			else
				killer.sendPacket(new ExShowScreenMessage(getMiddleMessage(max, kills), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER));
		}
	}

	@Override
	public void onPlayerKillParty(L2Player killer, L2Player killed, QuestState qs)
	{
		onPlayerKill(qs.getPlayer(), killed);
	}
}
