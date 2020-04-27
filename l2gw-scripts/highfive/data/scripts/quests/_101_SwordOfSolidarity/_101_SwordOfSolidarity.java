package quests._101_SwordOfSolidarity;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _101_SwordOfSolidarity extends Quest
{
	int ROIENS_LETTER = 796;
	int HOWTOGO_RUINS = 937;
	int BROKEN_SWORD_HANDLE = 739;
	int BROKEN_BLADE_BOTTOM = 740;
	int BROKEN_BLADE_TOP = 741;
	int ALLTRANS_NOTE = 742;
	int SWORD_OF_SOLIDARITY = 738;

	public _101_SwordOfSolidarity()
	{
		super(101, "_101_SwordOfSolidarity", "Sword Of Solidarity");

		addStartNpc(30008);
		addTalkId(30283);

		addKillId(20361);
		addKillId(20362);

		addQuestItem(ALLTRANS_NOTE, HOWTOGO_RUINS, BROKEN_BLADE_TOP, BROKEN_BLADE_BOTTOM, ROIENS_LETTER, BROKEN_SWORD_HANDLE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("roien_q0101_04.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(ROIENS_LETTER, 1);
		}
		else if(event.equalsIgnoreCase("blacksmith_alltran_q0101_02.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.takeItems(ROIENS_LETTER, -1);
			st.giveItems(HOWTOGO_RUINS, 1);
		}
		else if(event.equalsIgnoreCase("blacksmith_alltran_q0101_07.htm"))
		{
			if(st.getPlayer().getLevel() < 25 && !st.getPlayer().isMageClass())
			{
				st.giveItems(5789, 7000);
				st.playTutorialVoice("tutorial_voice_026", 1000);
			}

			st.takeItems(BROKEN_SWORD_HANDLE, -1);

			st.giveItems(SWORD_OF_SOLIDARITY, 1);
			st.giveItems(1060, 100); // healing potion
			for(int item = 4412; item <= 4416; item++)
				st.giveItems(item, 10); // echo cry

			st.addExpAndSp(25747, 2171);
			st.rollAndGive(57, 10981, 100);
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
			st.showSocial(3);
			if(st.getPlayer().getVarInt("NR41") == 0)
			{
				st.getPlayer().setVar("NR41", 100000);
				st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4154", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
			}
			else if((st.getPlayer().getVarInt("NR41") % 1000000) / 100000 != 1)
			{
				st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 100000);
				st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4154", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
			}
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
		if(npcId == 30008)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getRace() != Race.human)
					htmltext = "roien_q0101_00.htm";
				else if(st.getPlayer().getLevel() >= 9)
				{
					htmltext = "roien_q0101_02.htm";
					return htmltext;
				}
				else
				{
					htmltext = "roien_q0101_08.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 && st.getQuestItemsCount(ROIENS_LETTER) == 1)
				htmltext = "roien_q0101_05.htm";
			else if(cond >= 2 && st.getQuestItemsCount(ROIENS_LETTER) == 0 && st.getQuestItemsCount(ALLTRANS_NOTE) == 0)
			{
				if(st.getQuestItemsCount(BROKEN_BLADE_TOP) > 0 && st.getQuestItemsCount(BROKEN_BLADE_BOTTOM) > 0)
					htmltext = "roien_q0101_12.htm";
				if(st.getQuestItemsCount(BROKEN_BLADE_TOP) + st.getQuestItemsCount(BROKEN_BLADE_BOTTOM) <= 1)
					htmltext = "roien_q0101_11.htm";
				if(st.getQuestItemsCount(BROKEN_SWORD_HANDLE) > 0)
					htmltext = "roien_q0101_07.htm";
				if(st.getQuestItemsCount(HOWTOGO_RUINS) == 1)
					htmltext = "roien_q0101_10.htm";
			}
			else if(cond == 4 && st.getQuestItemsCount(ALLTRANS_NOTE) > 0)
			{
				htmltext = "roien_q0101_06.htm";
				st.set("cond", "5");
				st.setState(STARTED);
				st.takeItems(ALLTRANS_NOTE, -1);
				st.giveItems(BROKEN_SWORD_HANDLE, 1);
			}
		}
		else if(npcId == 30283)
			if(cond == 1 && st.getQuestItemsCount(ROIENS_LETTER) > 0)
				htmltext = "blacksmith_alltran_q0101_01.htm";
			else if(cond >= 2 && st.getQuestItemsCount(HOWTOGO_RUINS) == 1)
			{
				if(st.getQuestItemsCount(BROKEN_BLADE_TOP) + st.getQuestItemsCount(BROKEN_BLADE_BOTTOM) == 1)
					htmltext = "blacksmith_alltran_q0101_08.htm";
				else if(st.getQuestItemsCount(BROKEN_BLADE_TOP) + st.getQuestItemsCount(BROKEN_BLADE_BOTTOM) == 0)
					htmltext = "blacksmith_alltran_q0101_03.htm";
				else if(st.getQuestItemsCount(BROKEN_BLADE_TOP) > 0 && st.getQuestItemsCount(BROKEN_BLADE_BOTTOM) > 0)
				{
					htmltext = "blacksmith_alltran_q0101_04.htm";
					st.set("cond", "4");
					st.setState(STARTED);
					st.takeItems(HOWTOGO_RUINS, -1);
					st.takeItems(BROKEN_BLADE_TOP, -1);
					st.takeItems(BROKEN_BLADE_BOTTOM, -1);
					st.giveItems(ALLTRANS_NOTE, 1);
				}
				else if(cond == 4 && st.getQuestItemsCount(ALLTRANS_NOTE) > 0)
					htmltext = "blacksmith_alltran_q0101_05.htm";
			}
			else if(cond == 5 && st.getQuestItemsCount(BROKEN_SWORD_HANDLE) > 0)
				htmltext = "blacksmith_alltran_q0101_06.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if((npcId == 20361 || npcId == 20362) && st.getQuestItemsCount(HOWTOGO_RUINS) > 0)
		{
			if(st.rollAndGiveLimited(BROKEN_BLADE_TOP, 1, 60, 1))
				st.playSound(SOUND_ITEMGET);
			else if(st.rollAndGiveLimited(BROKEN_BLADE_BOTTOM, 1, 60, 1))
				st.playSound(SOUND_ITEMGET);

			if(st.getQuestItemsCount(BROKEN_BLADE_TOP) == 1 && st.getQuestItemsCount(BROKEN_BLADE_BOTTOM) == 1)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "3");
				st.setState(STARTED);
			}
		}
	}
}