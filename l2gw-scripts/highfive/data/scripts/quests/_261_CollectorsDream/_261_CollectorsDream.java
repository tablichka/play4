package quests._261_CollectorsDream;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _261_CollectorsDream extends Quest
{
	int GIANT_SPIDER_LEG = 1087;

	public _261_CollectorsDream()
	{
		super(261, "_261_CollectorsDream", "Collectors Dream");

		addStartNpc(30222);

		addTalkId(30222);

		addKillId(20308);
		addKillId(20460);
		addKillId(20466);

		addQuestItem(GIANT_SPIDER_LEG);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.intern().equalsIgnoreCase("moneylender_alshupes_q0261_03.htm"))
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
		int cond = st.getInt("cond");
		if(cond == 0)
		{
			if(st.getPlayer().getLevel() >= 15)
			{
				htmltext = "moneylender_alshupes_q0261_02.htm";
				return htmltext;
			}
			htmltext = "moneylender_alshupes_q0261_01.htm";
			st.exitCurrentQuest(true);
		}
		else if(cond == 1 || st.getQuestItemsCount(GIANT_SPIDER_LEG) < 8)
			htmltext = "moneylender_alshupes_q0261_04.htm";
		else if(cond == 2 && st.getQuestItemsCount(GIANT_SPIDER_LEG) >= 8)
		{
			st.rollAndGive(57, 1000, 100);
			st.addExpAndSp(2000, 0);
			st.takeItems(GIANT_SPIDER_LEG, -1);

			if(st.getPlayer().getVarInt("NR41") == 0)
			{
				st.getPlayer().setVar("NR41", 100000);
				st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4155", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
			}
			else if(st.getPlayer().getVarInt("NR41") % 100000000 / 10000000 == 0)
			{
				st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 10000000);
				st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4155", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
			}

			htmltext = "moneylender_alshupes_q0261_05.htm";
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(GIANT_SPIDER_LEG, 1, 100, 8))
		{
			if(st.getQuestItemsCount(GIANT_SPIDER_LEG) == 8)
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