package quests._265_ChainsOfSlavery;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;

public class _265_ChainsOfSlavery extends Quest
{
	// NPC
	private static final int KRISTIN = 30357;

	// MOBS
	private static final int IMP = 20004;
	private static final int IMP_ELDER = 20005;

	// ITEMS
	private static final int IMP_SHACKLES = 1368;

	public _265_ChainsOfSlavery()
	{
		super(265, "_265_ChainsOfSlavery", "Chains Of Slavery");
		addStartNpc(KRISTIN);

		addKillId(IMP);
		addKillId(IMP_ELDER);

		addQuestItem(IMP_SHACKLES);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("sentry_krpion_q0265_03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("sentry_krpion_q0265_06.htm"))
			st.exitCurrentQuest(true);
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getRace() != Race.darkelf)
			{
				htmltext = "sentry_krpion_q0265_00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 6)
			{
				htmltext = "sentry_krpion_q0265_01.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "sentry_krpion_q0265_02.htm";
		}
		else
		{
			long count = st.getQuestItemsCount(IMP_SHACKLES);
			if(count > 0)
			{
				if(count >= 10)
					st.rollAndGive(57, 12 * count + 500, 100);
				else
					st.rollAndGive(57, 12 * count, 100);

				st.takeItems(IMP_SHACKLES, -1);
				htmltext = "sentry_krpion_q0265_05.htm";

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
			}
			else
				htmltext = "sentry_krpion_q0265_04.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getInt("cond") == 1 && st.rollAndGive(IMP_SHACKLES, 1, Rnd.get(5 + npcId - 20004)))
			st.playSound(SOUND_ITEMGET);
	}
}