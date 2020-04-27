package quests._273_InvadersOfHolyland;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;

public class _273_InvadersOfHolyland extends Quest
{
	public final int BLACK_SOULSTONE = 1475;
	public final int RED_SOULSTONE = 1476;

	public _273_InvadersOfHolyland()
	{
		super(273, "_273_InvadersOfHolyland", "Invaders Of Holyland");

		addStartNpc(30566);
		addKillId(20311, 20312, 20313);
		addQuestItem(BLACK_SOULSTONE, RED_SOULSTONE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("atuba_chief_varkees_q0273_03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("atuba_chief_varkees_q0273_07.htm"))
		{
			st.set("cond", "0");
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equals("atuba_chief_varkees_q0273_08.htm"))
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
			if(st.getPlayer().getRace() != Race.orc)
			{
				htmltext = "atuba_chief_varkees_q0273_00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 6)
			{
				htmltext = "atuba_chief_varkees_q0273_01.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "atuba_chief_varkees_q0273_02.htm";
				return htmltext;
			}
		}
		else if(cond > 0)
		{
			if(st.getQuestItemsCount(BLACK_SOULSTONE) == 0 && st.getQuestItemsCount(RED_SOULSTONE) == 0)
				htmltext = "atuba_chief_varkees_q0273_04.htm";
			else if(st.getQuestItemsCount(RED_SOULSTONE) == 0)
			{
				htmltext = "atuba_chief_varkees_q0273_05.htm";
				if(st.getQuestItemsCount(BLACK_SOULSTONE) >= 10)
					st.rollAndGive(57, st.getQuestItemsCount(BLACK_SOULSTONE) * 3 + 1500, 100);
				else
					st.rollAndGive(57, st.getQuestItemsCount(BLACK_SOULSTONE) * 3, 100);

				st.takeItems(BLACK_SOULSTONE, -1);
				st.playSound(SOUND_FINISH);

				if(st.getPlayer().getLevel() < 25 && !st.getPlayer().getVarB("NR57"))
				{
					st.playTutorialVoice("tutorial_voice_026", 1000);
					st.giveItems(5789, 6000);
					st.getPlayer().setVar("NR57", "1");
					st.showQuestionMark(26);
				}

				if(st.getPlayer().getVarInt("NR41") % 10000 / 1000 == 0)
				{
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4152", st.getPlayer()).toString(), 5000, ScreenMessageAlign.TOP_CENTER, true));
				}

				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "atuba_chief_varkees_q0273_06.htm";
				if(st.getQuestItemsCount(RED_SOULSTONE) + st.getQuestItemsCount(BLACK_SOULSTONE) >= 10)
					st.rollAndGive(57, st.getQuestItemsCount(RED_SOULSTONE) * 10 + st.getQuestItemsCount(BLACK_SOULSTONE) * 3 + 1800, 100);
				else
					st.rollAndGive(57, st.getQuestItemsCount(RED_SOULSTONE) * 10 + st.getQuestItemsCount(BLACK_SOULSTONE) * 3, 100);

				st.takeItems(RED_SOULSTONE, -1);
				st.takeItems(BLACK_SOULSTONE, -1);

				st.playSound(SOUND_FINISH);

				if(st.getPlayer().getLevel() < 25 && !st.getPlayer().getVarB("NR57"))
				{
					st.playTutorialVoice("tutorial_voice_026", 1000);
					st.giveItems(5789, 6000);
					st.getPlayer().setVar("NR57", "1");
					st.showQuestionMark(26);
				}

				if(st.getPlayer().getVarInt("NR41") % 10000 / 1000 == 0)
				{
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4152", st.getPlayer()).toString(), 5000, ScreenMessageAlign.TOP_CENTER, true));
				}

				st.exitCurrentQuest(true);
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == 20311)
		{
			if(cond == 1)
			{
				if(Rnd.chance(90))
					st.rollAndGive(BLACK_SOULSTONE, 1, 100);
				else
					st.rollAndGive(RED_SOULSTONE, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == 20312)
		{
			if(cond == 1)
			{
				if(Rnd.chance(87))
					st.rollAndGive(BLACK_SOULSTONE, 1, 100);
				else
					st.rollAndGive(RED_SOULSTONE, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == 20313)
			if(cond == 1)
			{
				if(Rnd.chance(77))
					st.rollAndGive(BLACK_SOULSTONE, 1, 100);
				else
					st.rollAndGive(RED_SOULSTONE, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
	}
}