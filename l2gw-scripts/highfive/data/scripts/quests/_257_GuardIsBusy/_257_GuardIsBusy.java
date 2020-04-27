package quests._257_GuardIsBusy;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;

public class _257_GuardIsBusy extends Quest
{
	int GLUDIO_LORDS_MARK = 1084;
	int ORC_AMULET = 752;
	int ORC_NECKLACE = 1085;
	int WEREWOLF_FANG = 1086;
	int ADENA = 57;

	public _257_GuardIsBusy()
	{
		super(257, "_257_GuardIsBusy", "Guard Is Busy");

		addStartNpc(30039);

		addTalkId(30039);

		addKillId(20130);
		addKillId(20131);
		addKillId(20132);
		addKillId(20342);
		addKillId(20343);
		addKillId(20006);
		addKillId(20093);
		addKillId(20096);
		addKillId(20098);

		addKillId(20130, 20131, 20132, 20342, 20343, 20006, 20093, 20096, 20098);
		addQuestItem(ORC_AMULET, ORC_NECKLACE, WEREWOLF_FANG, GLUDIO_LORDS_MARK);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("gilbert_q0257_03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.takeItems(GLUDIO_LORDS_MARK, -1);
			st.giveItems(GLUDIO_LORDS_MARK, 1);
		}
		else if(event.equalsIgnoreCase("257_2"))
		{
			htmltext = "gilbert_q0257_05.htm";
			st.takeItems(GLUDIO_LORDS_MARK, -1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("257_3"))
			htmltext = "gilbert_q0257_06.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 6)
			{
				htmltext = "gilbert_q0257_02.htm";
				return htmltext;
			}
			htmltext = "gilbert_q0257_01.htm";
			st.exitCurrentQuest(true);
		}
		else if(cond == 1 && st.getQuestItemsCount(ORC_AMULET) < 1 && st.getQuestItemsCount(ORC_NECKLACE) < 1 && st.getQuestItemsCount(WEREWOLF_FANG) < 1)
			htmltext = "gilbert_q0257_04.htm";
		else if(cond == 1 && (st.getQuestItemsCount(ORC_AMULET) > 0 || st.getQuestItemsCount(ORC_NECKLACE) > 0 || st.getQuestItemsCount(WEREWOLF_FANG) > 0))
		{
			if(st.getQuestItemsCount(ORC_AMULET) + st.getQuestItemsCount(ORC_NECKLACE) + st.getQuestItemsCount(WEREWOLF_FANG) >= 10)
				st.rollAndGive(ADENA, 10 * st.getQuestItemsCount(ORC_AMULET) + 20 * st.getQuestItemsCount(ORC_NECKLACE) + 20 * st.getQuestItemsCount(WEREWOLF_FANG) + 1000, 100);
			else
				st.rollAndGive(ADENA, 10 * st.getQuestItemsCount(ORC_AMULET) + 20 * st.getQuestItemsCount(ORC_NECKLACE) + 20 * st.getQuestItemsCount(WEREWOLF_FANG), 100);

			if(st.getPlayer().getLevel() < 25 && !st.getPlayer().getVarB("NR57"))
			{
				if(st.getPlayer().isMageClass())
				{
					st.playTutorialVoice("tutorial_voice_027", 1000);
					st.giveItems(5790, 3000);
				}
				else
				{
					st.playTutorialVoice("tutorial_voice_026", 1000);
					st.giveItems(5789, 6000);
				}
				st.getPlayer().setVar("NR57", "1");
				st.showQuestionMark(26);
			}
			
			if(st.getPlayer().getVarInt("NR41") % 10000 / 1000 == 0)
			{
				st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1000);
				st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4152", st.getPlayer()).toString(), 5000, ScreenMessageAlign.TOP_CENTER, true));
			}

			st.takeItems(ORC_AMULET, -1);
			st.takeItems(ORC_NECKLACE, -1);
			st.takeItems(WEREWOLF_FANG, -1);
			htmltext = "gilbert_q0257_07.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getQuestItemsCount(GLUDIO_LORDS_MARK) > 0 && st.getInt("cond") > 0)
			if(npcId == 20130 || npcId == 20131 || npcId == 20006)
				st.rollAndGive(ORC_AMULET, 1, 50);
			else if(npcId == 20093 || npcId == 20096 || npcId == 20098)
				st.rollAndGive(ORC_NECKLACE, 1, 50);
			else if(npcId == 20132)
				st.rollAndGive(WEREWOLF_FANG, 1, 33);
			else if(npcId == 20343)
				st.rollAndGive(WEREWOLF_FANG, 1, 50);
			else if(npcId == 20342)
				st.rollAndGive(WEREWOLF_FANG, 1, 75);
	}
}