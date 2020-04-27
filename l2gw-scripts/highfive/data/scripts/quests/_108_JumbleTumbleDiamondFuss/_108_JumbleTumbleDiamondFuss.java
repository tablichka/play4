package quests._108_JumbleTumbleDiamondFuss;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _108_JumbleTumbleDiamondFuss extends Quest
{
	int GOUPHS_CONTRACT = 1559;
	int REEPS_CONTRACT = 1560;
	int ELVEN_WINE = 1561;
	int BRONPS_DICE = 1562;
	int BRONPS_CONTRACT = 1563;
	int AQUAMARINE = 1564;
	int CHRYSOBERYL = 1565;
	int GEM_BOX1 = 1566;
	int COAL_PIECE = 1567;
	int BRONPS_LETTER = 1568;
	int BERRY_TART = 1569;
	int BAT_DIAGRAM = 1570;
	int STAR_DIAMOND = 1571;
	int SILVERSMITH_HAMMER = 1511;

	public _108_JumbleTumbleDiamondFuss()
	{
		super(108, "_108_JumbleTumbleDiamondFuss", "Jumble Tumble Diamond Fuss");

		addStartNpc(30523);

		addTalkId(30516);
		addTalkId(30521);
		addTalkId(30522);
		addTalkId(30526);
		addTalkId(30529);
		addTalkId(30555);

		addKillId(20323);
		addKillId(20324);
		addKillId(20480);

		addQuestItem(GEM_BOX1, STAR_DIAMOND, GOUPHS_CONTRACT, REEPS_CONTRACT, ELVEN_WINE, BRONPS_CONTRACT, AQUAMARINE, CHRYSOBERYL, COAL_PIECE, BRONPS_DICE, BRONPS_LETTER, BERRY_TART, BAT_DIAGRAM);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("collector_gouph_q0108_03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.giveItems(GOUPHS_CONTRACT, 1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("carrier_torocco_q0108_02.htm"))
		{
			st.takeItems(REEPS_CONTRACT, 1);
			st.giveItems(ELVEN_WINE, 1);
			st.set("cond", "3");
			st.setState(STARTED);
		}
		else if(event.equals("blacksmith_bronp_q0108_02.htm"))
		{
			st.takeItems(BRONPS_DICE, 1);
			st.giveItems(BRONPS_CONTRACT, 1);
			st.set("cond", "5");
			st.setState(STARTED);
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
		if(npcId == 30523)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getRace() != Race.dwarf)
				{
					htmltext = "collector_gouph_q0108_00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() >= 10)
					htmltext = "collector_gouph_q0108_02.htm";
				else
				{
					htmltext = "collector_gouph_q0108_01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 0 && st.getQuestItemsCount(GOUPHS_CONTRACT) > 0)
				htmltext = "collector_gouph_q0108_04.htm";
			else if(cond > 1 && cond < 7 && (st.getQuestItemsCount(REEPS_CONTRACT) > 0 || st.getQuestItemsCount(ELVEN_WINE) > 0 || st.getQuestItemsCount(BRONPS_DICE) > 0 || st.getQuestItemsCount(BRONPS_CONTRACT) > 0))
				htmltext = "collector_gouph_q0108_05.htm";
			else if(cond == 7 && st.getQuestItemsCount(GEM_BOX1) > 0)
			{
				htmltext = "collector_gouph_q0108_06.htm";
				st.takeItems(GEM_BOX1, 1);
				st.giveItems(COAL_PIECE, 1);
				st.set("cond", "8");
				st.setState(STARTED);
			}
			else if(cond > 7 && cond < 12 && (st.getQuestItemsCount(BRONPS_LETTER) > 0 || st.getQuestItemsCount(COAL_PIECE) > 0 || st.getQuestItemsCount(BERRY_TART) > 0 || st.getQuestItemsCount(BAT_DIAGRAM) > 0))
				htmltext = "collector_gouph_q0108_07.htm";
			else if(cond == 12 && st.getQuestItemsCount(STAR_DIAMOND) > 0)
			{
				htmltext = "collector_gouph_q0108_08.htm";

				if(st.getPlayer().getLevel() < 25 && !st.getPlayer().isMageClass())
				{
					st.playTutorialVoice("tutorial_voice_026", 1000);
					st.giveItems(5789, 7000);
				}
				else if(st.getPlayer().getLevel() < 25 && st.getPlayer().isMageClass())
				{
					st.playTutorialVoice("tutorial_voice_027", 1000);
					st.giveItems(5790, 3000);
				}

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

				st.addExpAndSp(34565, 2962);
				st.rollAndGive(57, 14666, 100);
				st.giveItems(1060, 100); // healing potion
				for(int item = 4412; item <= 4416; item++)
					st.giveItems(item, 10); // echo cry

				st.giveItems(SILVERSMITH_HAMMER, 1);
				st.takeItems(STAR_DIAMOND, 1);
				st.showSocial(3);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
		else if(npcId == 30516)
		{
			if(cond == 1 && st.getQuestItemsCount(GOUPHS_CONTRACT) > 0)
			{
				htmltext = "trader_reep_q0108_01.htm";
				st.giveItems(REEPS_CONTRACT, 1);
				st.takeItems(GOUPHS_CONTRACT, 1);
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else if(cond >= 2)
				htmltext = "trader_reep_q0108_02.htm";
		}
		else if(npcId == 30555)
		{
			if(cond == 2 && st.getQuestItemsCount(REEPS_CONTRACT) == 1)
				htmltext = "carrier_torocco_q0108_01.htm";
			else if(cond == 3 && st.getQuestItemsCount(ELVEN_WINE) > 0)
				htmltext = "carrier_torocco_q0108_03.htm";
			else if(cond == 7 && st.getQuestItemsCount(GEM_BOX1) == 1)
				htmltext = "carrier_torocco_q0108_04.htm";
			else
				htmltext = "carrier_torocco_q0108_05.htm";
		}
		else if(npcId == 30529)
		{
			if(cond == 3 && st.getQuestItemsCount(ELVEN_WINE) > 0)
			{
				st.takeItems(ELVEN_WINE, 1);
				st.giveItems(BRONPS_DICE, 1);
				htmltext = "miner_maron_q0108_01.htm";
				st.set("cond", "4");
				st.setState(STARTED);
			}
			else if(cond == 4)
				htmltext = "miner_maron_q0108_02.htm";
			else
				htmltext = "miner_maron_q0108_03.htm";
		}
		else if(npcId == 30526)
		{
			if(cond == 4 && st.getQuestItemsCount(BRONPS_DICE) > 0)
				htmltext = "blacksmith_bronp_q0108_01.htm";
			else if(cond == 5 && st.getQuestItemsCount(BRONPS_CONTRACT) > 0 && (st.getQuestItemsCount(AQUAMARINE) < 10 || st.getQuestItemsCount(CHRYSOBERYL) < 10))
				htmltext = "blacksmith_bronp_q0108_03.htm";
			else if(cond == 6 && st.getQuestItemsCount(BRONPS_CONTRACT) > 0 && st.getQuestItemsCount(AQUAMARINE) == 10 && st.getQuestItemsCount(CHRYSOBERYL) == 10)
			{
				htmltext = "blacksmith_bronp_q0108_04.htm";
				st.takeItems(BRONPS_CONTRACT, -1);
				st.takeItems(AQUAMARINE, -1);
				st.takeItems(CHRYSOBERYL, -1);
				st.giveItems(GEM_BOX1, 1);
				st.set("cond", "7");
				st.setState(STARTED);
			}
			else if(cond == 7 && st.getQuestItemsCount(GEM_BOX1) > 0)
				htmltext = "blacksmith_bronp_q0108_05.htm";
			else if(cond == 8 && st.getQuestItemsCount(COAL_PIECE) > 0)
			{
				htmltext = "blacksmith_bronp_q0108_06.htm";
				st.takeItems(COAL_PIECE, 1);
				st.giveItems(BRONPS_LETTER, 1);
				st.set("cond", "9");
				st.setState(STARTED);
			}
			else if(cond == 9 && st.getQuestItemsCount(BRONPS_LETTER) > 0)
				htmltext = "blacksmith_bronp_q0108_07.htm";
			else
				htmltext = "blacksmith_bronp_q0108_08.htm";
		}
		else if(npcId == 30521)
		{
			if(cond == 9 && st.getQuestItemsCount(BRONPS_LETTER) > 0)
			{
				htmltext = "warehouse_murphrin_q0108_01.htm";
				st.takeItems(BRONPS_LETTER, 1);
				st.giveItems(BERRY_TART, 1);
				st.set("cond", "10");
				st.setState(STARTED);
			}
			else if(cond == 10 && st.getQuestItemsCount(BERRY_TART) > 0)
				htmltext = "warehouse_murphrin_q0108_02.htm";
			else
				htmltext = "warehouse_murphrin_q0108_03.htm";
		}
		else if(npcId == 30522)
			if(cond == 10 && st.getQuestItemsCount(BERRY_TART) > 0)
			{
				htmltext = "warehouse_airy_q0108_01.htm";
				st.takeItems(BERRY_TART, 1);
				st.giveItems(BAT_DIAGRAM, 1);
				st.set("cond", "11");
				st.setState(STARTED);
			}
			else if(cond == 11 && st.getQuestItemsCount(BAT_DIAGRAM) > 0)
				htmltext = "warehouse_airy_q0108_02.htm";
			else if(cond == 12 && st.getQuestItemsCount(STAR_DIAMOND) > 0)
				htmltext = "warehouse_airy_q0108_03.htm";
			else
				htmltext = "warehouse_airy_q0108_04.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == 20323 || npcId == 20324)
		{
			if(cond == 5 && st.getQuestItemsCount(BRONPS_CONTRACT) > 0)
			{
				if(st.rollAndGiveLimited(AQUAMARINE, 1, 80, 10))
				{
					if(st.getQuestItemsCount(AQUAMARINE) < 10)
						st.playSound(SOUND_ITEMGET);
					else
					{
						st.playSound(SOUND_MIDDLE);
						if(st.getQuestItemsCount(AQUAMARINE) == 10 && st.getQuestItemsCount(CHRYSOBERYL) == 10)
						{
							st.set("cond", "6");
							st.setState(STARTED);
						}
					}
				}
				if(st.rollAndGiveLimited(CHRYSOBERYL, 1, 80, 10))
				{
					if(st.getQuestItemsCount(CHRYSOBERYL) < 10)
						st.playSound(SOUND_ITEMGET);
					else
					{
						st.playSound(SOUND_MIDDLE);
						if(st.getQuestItemsCount(AQUAMARINE) == 10 && st.getQuestItemsCount(CHRYSOBERYL) == 10)
						{
							st.set("cond", "6");
							st.setState(STARTED);
						}
					}
				}
			}
		}
		else if(npcId == 20480)
			if(cond == 11 && st.getQuestItemsCount(BAT_DIAGRAM) > 0 && st.rollAndGiveLimited(STAR_DIAMOND, 1, 50, 1))
			{
				st.takeItems(BAT_DIAGRAM, 1);
				st.set("cond", "12");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
	}
}