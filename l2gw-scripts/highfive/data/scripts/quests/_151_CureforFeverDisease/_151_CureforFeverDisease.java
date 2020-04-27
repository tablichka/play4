package quests._151_CureforFeverDisease;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;

public class _151_CureforFeverDisease extends Quest
{
	int POISON_SAC = 703;
	int FEVER_MEDICINE = 704;
	int ROUND_SHIELD = 102;

	public _151_CureforFeverDisease()
	{
		super(151, "_151_CureforFeverDisease", "Cure for Fever Disease");

		addStartNpc(30050);

		addTalkId(30032);

		addKillId(20103, 20106, 20108);

		addQuestItem(FEVER_MEDICINE, POISON_SAC);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("30050-03.htm"))
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
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = 0;
		if(!st.isCreated())
			cond = st.getInt("cond");
		if(npcId == 30050)
		{
			if(cond == 0)
			{
				if(st.getPlayer().getLevel() >= 15)
					htmltext = "30050-02.htm";
				else
				{
					htmltext = "30050-01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 && st.getQuestItemsCount(POISON_SAC) == 0 && st.getQuestItemsCount(FEVER_MEDICINE) == 0)
				htmltext = "30050-04.htm";
			else if(cond == 1 && st.getQuestItemsCount(POISON_SAC) == 1)
				htmltext = "30050-05.htm";
			else if(cond == 3 && st.getQuestItemsCount(FEVER_MEDICINE) == 1)
			{
				st.giveItems(ROUND_SHIELD, 1);
				st.addExpAndSp(13106, 613);
				st.takeItems(FEVER_MEDICINE, -1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
				if(st.getPlayer().getVarInt("NR41") == 0)
				{
					st.getPlayer().setVar("NR41", 100000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4155", st.getPlayer()).toString(), 5000, ScreenMessageAlign.TOP_CENTER, true));
				}
				else if(st.getPlayer().getVarInt("NR41") % 100000000 / 10000000 == 0)
				{
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 10000000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4155", st.getPlayer()).toString(), 5000, ScreenMessageAlign.TOP_CENTER, true));
				}
				htmltext = "30050-06.htm";
			}
		}
		else if(npcId == 30032)
			if(cond == 2 && st.getQuestItemsCount(POISON_SAC) > 0)
			{
				st.giveItems(FEVER_MEDICINE, 1);
				st.takeItems(POISON_SAC, -1);
				st.set("cond", "3");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				htmltext = "30032-01.htm";
			}
			else if(cond == 3 && st.getQuestItemsCount(FEVER_MEDICINE) > 0)
				htmltext = "30032-02.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if((npcId == 20103 || npcId == 20106 || npcId == 20108) && st.rollAndGiveLimited(POISON_SAC, 1, 50, 1))
		{
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
			st.setState(STARTED);
		}
	}
}