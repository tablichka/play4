package quests._103_SpiritOfCraftsman;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _103_SpiritOfCraftsman extends Quest
{
	public final int KAROYDS_LETTER_ID = 968;
	public final int CECKTINONS_VOUCHER1_ID = 969;
	public final int CECKTINONS_VOUCHER2_ID = 970;
	public final int BONE_FRAGMENT1_ID = 1107;
	public final int SOUL_CATCHER_ID = 971;
	public final int PRESERVE_OIL_ID = 972;
	public final int ZOMBIE_HEAD_ID = 973;
	public final int STEELBENDERS_HEAD_ID = 974;
	public final int BLOODSABER_ID = 975;

	public _103_SpiritOfCraftsman()
	{
		super(103, "_103_SpiritOfCraftsman", "Spirit Of Craftsman");

		addStartNpc(30307);

		addTalkId(30132);
		addTalkId(30144);

		addKillId(20015);
		addKillId(20020);
		addKillId(20455);
		addKillId(20517);
		addKillId(20518);

		addQuestItem(KAROYDS_LETTER_ID, CECKTINONS_VOUCHER1_ID, CECKTINONS_VOUCHER2_ID, BONE_FRAGMENT1_ID, SOUL_CATCHER_ID, PRESERVE_OIL_ID, ZOMBIE_HEAD_ID, STEELBENDERS_HEAD_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("blacksmith_karoyd_q0103_05.htm"))
		{
			st.giveItems(KAROYDS_LETTER_ID, 1);
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
		if(st.isCreated())
			st.set("cond", "0");
		if(npcId == 30307 && st.getInt("cond") == 0)
		{
			if(st.getPlayer().getRace() != Race.darkelf)
				htmltext = "blacksmith_karoyd_q0103_00.htm";
			else if(st.getPlayer().getLevel() >= 11)
			{
				htmltext = "blacksmith_karoyd_q0103_03.htm";
				return htmltext;
			}
			else
			{
				htmltext = "blacksmith_karoyd_q0103_02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == 30307 && st.getInt("cond") == 0)
			htmltext = "completed";
		else if(st.isStarted())
			if(npcId == 30307 && st.getInt("cond") >= 1 && (st.getQuestItemsCount(KAROYDS_LETTER_ID) >= 1 || st.getQuestItemsCount(CECKTINONS_VOUCHER1_ID) >= 1 || st.getQuestItemsCount(CECKTINONS_VOUCHER2_ID) >= 1))
				htmltext = "blacksmith_karoyd_q0103_06.htm";
			else if(npcId == 30132 && st.getInt("cond") == 1 && st.getQuestItemsCount(KAROYDS_LETTER_ID) == 1)
			{
				htmltext = "cecon_q0103_01.htm";
				st.set("cond", "2");
				st.setState(STARTED);
				st.takeItems(KAROYDS_LETTER_ID, 1);
				st.giveItems(CECKTINONS_VOUCHER1_ID, 1);
			}
			else if(npcId == 30132 && st.getInt("cond") >= 2 && (st.getQuestItemsCount(CECKTINONS_VOUCHER1_ID) >= 1 || st.getQuestItemsCount(CECKTINONS_VOUCHER2_ID) >= 1))
				htmltext = "cecon_q0103_02.htm";
			else if(npcId == 30144 && st.getInt("cond") == 2 && st.getQuestItemsCount(CECKTINONS_VOUCHER1_ID) >= 1)
			{
				htmltext = "harne_q0103_01.htm";
				st.set("cond", "3");
				st.setState(STARTED);
				st.takeItems(CECKTINONS_VOUCHER1_ID, 1);
				st.giveItems(CECKTINONS_VOUCHER2_ID, 1);
			}
			else if(npcId == 30144 && st.getInt("cond") == 3 && st.getQuestItemsCount(CECKTINONS_VOUCHER2_ID) >= 1 && st.getQuestItemsCount(BONE_FRAGMENT1_ID) < 10)
				htmltext = "harne_q0103_02.htm";
			else if(npcId == 30144 && st.getInt("cond") == 4 && st.getQuestItemsCount(CECKTINONS_VOUCHER2_ID) == 1 && st.getQuestItemsCount(BONE_FRAGMENT1_ID) >= 10)
			{
				htmltext = "harne_q0103_03.htm";
				st.set("cond", "5");
				st.setState(STARTED);
				st.takeItems(CECKTINONS_VOUCHER2_ID, 1);
				st.takeItems(BONE_FRAGMENT1_ID, 10);
				st.giveItems(SOUL_CATCHER_ID, 1);
			}
			else if(npcId == 30144 && st.getInt("cond") == 5 && st.getQuestItemsCount(SOUL_CATCHER_ID) == 1)
				htmltext = "harne_q0103_04.htm";
			else if(npcId == 30132 && st.getInt("cond") == 5 && st.getQuestItemsCount(SOUL_CATCHER_ID) == 1)
			{
				htmltext = "cecon_q0103_03.htm";
				st.set("cond", "6");
				st.setState(STARTED);
				st.takeItems(SOUL_CATCHER_ID, 1);
				st.giveItems(PRESERVE_OIL_ID, 1);
			}
			else if(npcId == 30132 && st.getInt("cond") == 6 && st.getQuestItemsCount(PRESERVE_OIL_ID) == 1 && st.getQuestItemsCount(ZOMBIE_HEAD_ID) == 0 && st.getQuestItemsCount(STEELBENDERS_HEAD_ID) == 0)
				htmltext = "cecon_q0103_04.htm";
			else if(npcId == 30132 && st.getInt("cond") == 7 && st.getQuestItemsCount(ZOMBIE_HEAD_ID) == 1)
			{
				htmltext = "cecon_q0103_05.htm";
				st.set("cond", "8");
				st.setState(STARTED);
				st.takeItems(ZOMBIE_HEAD_ID, 1);
				st.giveItems(STEELBENDERS_HEAD_ID, 1);
			}
			else if(npcId == 30132 && st.getInt("cond") == 8 && st.getQuestItemsCount(STEELBENDERS_HEAD_ID) == 1)
				htmltext = "cecon_q0103_06.htm";
			else if(npcId == 30307 && st.getInt("cond") == 8 && st.getQuestItemsCount(STEELBENDERS_HEAD_ID) == 1)
			{
				if(st.getPlayer().getLevel() < 25 && !st.getPlayer().isMageClass())
				{
					st.playTutorialVoice("tutorial_voice_026", 1000);
					st.giveItems(5789, 7000);
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

				st.addExpAndSp(46663, 3999);
				st.rollAndGive(57, 19799, 100);
				htmltext = "blacksmith_karoyd_q0103_07.htm";
				st.takeItems(STEELBENDERS_HEAD_ID, 1);
				st.giveItems(BLOODSABER_ID, 1);
				st.showSocial(3);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if((npcId == 20517 || npcId == 20518 || npcId == 20455) && st.getInt("cond") == 3)
		{
			if(st.getQuestItemsCount(CECKTINONS_VOUCHER2_ID) == 1 && st.rollAndGiveLimited(BONE_FRAGMENT1_ID, 1, 33, 10))
			{
				if(st.getQuestItemsCount(BONE_FRAGMENT1_ID) == 10)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "4");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if((npcId == 20015 || npcId == 20020) && st.getInt("cond") == 6)
			if(st.getQuestItemsCount(PRESERVE_OIL_ID) == 1 && st.rollAndGiveLimited(ZOMBIE_HEAD_ID, 1, 33, 1))
			{
				st.playSound(SOUND_MIDDLE);
				st.takeItems(PRESERVE_OIL_ID, 1);
				st.set("cond", "7");
				st.setState(STARTED);
			}

	}
}
