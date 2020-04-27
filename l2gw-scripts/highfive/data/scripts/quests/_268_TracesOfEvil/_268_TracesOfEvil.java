package quests._268_TracesOfEvil;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Traces Of Evil
 *
 * @author Ruslaner
 * @Last_Fixes by Felixx спиздил viRUS
 */

public class _268_TracesOfEvil extends Quest
{
	//NPC
	public final int KUNAI = 30559;
	//MOBS
	public final int SPIDER = 20474;
	public final int FANG_SPIDER = 20476;
	public final int BLADE_SPIDER = 20478;
	//ITEMS
	public final int CONTAMINATED = 10869;
	public final int ADENA = 57;

	public _268_TracesOfEvil()
	{
		super(268, "_268_TracesOfEvil", "Traces Of Evil");
		addStartNpc(KUNAI);
		addTalkId(KUNAI);
		addKillId(SPIDER);
		addKillId(FANG_SPIDER);
		addKillId(BLADE_SPIDER);
		addQuestItem(CONTAMINATED);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("30559-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.isCreated())
			if(st.getPlayer().getLevel() < 15)
			{
				htmltext = "30559-00.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30559-01.htm";
		else if(st.getQuestItemsCount(CONTAMINATED) >= 30)
		{
			htmltext = "30559-04.htm";
			st.rollAndGive(ADENA, 2474, 100);
			st.addExpAndSp(8738, 409);
			st.playSound(SOUND_FINISH);
			st.unset("cond");
			st.exitCurrentQuest(true);
		}
		else
			htmltext = "30559-03.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 1 && st.rollAndGiveLimited(CONTAMINATED, 1, 100, 30))
		{
			if(st.getQuestItemsCount(CONTAMINATED) == 30)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}