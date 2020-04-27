package quests._106_ForgottenTruth;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _106_ForgottenTruth extends Quest
{
	int ONYX_TALISMAN1 = 984;
	int ONYX_TALISMAN2 = 985;
	int ANCIENT_SCROLL = 986;
	int ANCIENT_CLAY_TABLET = 987;
	int KARTAS_TRANSLATION = 988;
	int ELDRITCH_DAGGER = 989;
	int ELDRITCH_STAFF = 2373;

	public _106_ForgottenTruth()
	{
		super(106, "_106_ForgottenTruth", "Forgotten Truth");

		addStartNpc(30358);
		addTalkId(30133);

		addKillId(27070);

		addQuestItem(KARTAS_TRANSLATION, ONYX_TALISMAN1, ONYX_TALISMAN2, ANCIENT_SCROLL, ANCIENT_CLAY_TABLET);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("tetrarch_thifiell_q0106_05.htm"))
		{
			st.giveItems(ONYX_TALISMAN1, 1);
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
		int cond = st.getInt("cond");
		if(npcId == 30358)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getRace() != Race.darkelf)
				{
					htmltext = "tetrarch_thifiell_q0106_00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() >= 10)
					htmltext = "tetrarch_thifiell_q0106_03.htm";
				else
				{
					htmltext = "tetrarch_thifiell_q0106_02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond > 0 && (st.getQuestItemsCount(ONYX_TALISMAN1) > 0 || st.getQuestItemsCount(ONYX_TALISMAN2) > 0) && st.getQuestItemsCount(KARTAS_TRANSLATION) == 0)
				htmltext = "tetrarch_thifiell_q0106_06.htm";
			else if(cond == 4 && st.getQuestItemsCount(KARTAS_TRANSLATION) > 0)
			{
				if(st.getPlayer().getLevel() < 25 && st.getPlayer().isMageClass())
				{
					st.playTutorialVoice("tutorial_voice_027", 1000);
					st.giveItems(5790, 3000);
				}
				st.giveItems(1060, 100); // healing potion
				for(int item = 4412; item <= 4416; item++)
					st.giveItems(item, 10); // echo cry

				if(st.getPlayer().isMageClass())
					st.giveItems(2509, 500);
				else
					st.giveItems(1835, 1000);

				if(st.getPlayer().getVarInt("NR41") == 0)
				{
					st.getPlayer().setVar("NR41", 100000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4154", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
				}
				else if(st.getPlayer().getVarInt("NR41") % 1000000 / 100000 == 0)
				{
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 100000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4154", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
				}

				htmltext = "tetrarch_thifiell_q0106_07.htm";
				st.takeItems(KARTAS_TRANSLATION, -1);
				st.giveItems(ELDRITCH_DAGGER, 1);
				st.addExpAndSp(24195, 2074);
				st.rollAndGive(57, 10266, 100);
				st.showSocial(3);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
			}
		}
		else if(npcId == 30133)
			if(cond == 1 && st.getQuestItemsCount(ONYX_TALISMAN1) > 0)
			{
				htmltext = "karta_q0106_01.htm";
				st.takeItems(ONYX_TALISMAN1, -1);
				st.giveItems(ONYX_TALISMAN2, 1);
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else if(cond == 2 && st.getQuestItemsCount(ONYX_TALISMAN2) > 0 && (st.getQuestItemsCount(ANCIENT_SCROLL) == 0 || st.getQuestItemsCount(ANCIENT_CLAY_TABLET) == 0))
				htmltext = "karta_q0106_02.htm";
			else if(cond == 3 && st.getQuestItemsCount(ANCIENT_SCROLL) > 0 && st.getQuestItemsCount(ANCIENT_CLAY_TABLET) > 0)
			{
				htmltext = "karta_q0106_03.htm";
				st.takeItems(ONYX_TALISMAN2, -1);
				st.takeItems(ANCIENT_SCROLL, -1);
				st.takeItems(ANCIENT_CLAY_TABLET, -1);
				st.giveItems(KARTAS_TRANSLATION, 1);
				st.set("cond", "4");
				st.setState(STARTED);
			}
			else if(cond == 4 && st.getQuestItemsCount(KARTAS_TRANSLATION) > 0)
				htmltext = "karta_q0106_04.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == 27070)
			if(st.getInt("cond") == 2 && st.getQuestItemsCount(ONYX_TALISMAN2) > 0)
				if(st.rollAndGiveLimited(ANCIENT_SCROLL, 1, 20, 1))
					st.playSound(SOUND_ITEMGET);
				else if(st.rollAndGiveLimited(ANCIENT_CLAY_TABLET, 1, 10, 1))
					st.playSound(SOUND_ITEMGET);

		if(st.getQuestItemsCount(ANCIENT_SCROLL) == 1 && st.getQuestItemsCount(ANCIENT_CLAY_TABLET) == 1)
		{
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
	}
}