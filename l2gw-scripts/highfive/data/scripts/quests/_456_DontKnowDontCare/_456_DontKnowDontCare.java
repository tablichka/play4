package quests._456_DontKnowDontCare;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 24.09.11 17:02
 */
public class _456_DontKnowDontCare extends Quest
{
	// NPC
	private static final int separated_soul_01 = 32864;
	private static final int separated_soul_02 = 32865;
	private static final int separated_soul_03 = 32866;
	private static final int separated_soul_04 = 32867;
	private static final int separated_soul_05 = 32868;
	private static final int separated_soul_06 = 32869;
	private static final int separated_soul_07 = 32870;
	private static final int separated_soul_08 = 32891;

	public _456_DontKnowDontCare()
	{
		super(456, "_456_DontKnowDontCare", "Don't Know, Don't Care");
		addStartNpc(separated_soul_01);
		addStartNpc(separated_soul_02);
		addStartNpc(separated_soul_03);
		addStartNpc(separated_soul_04);
		addStartNpc(separated_soul_05);
		addStartNpc(separated_soul_06);
		addStartNpc(separated_soul_07);
		addStartNpc(separated_soul_08);
		addTalkId(separated_soul_01, separated_soul_02, separated_soul_03, separated_soul_04, separated_soul_05, separated_soul_06, separated_soul_07, separated_soul_08);
		addQuestItem(17251, 17252, 17253);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == separated_soul_01 || npc.getNpcId() == separated_soul_02 || npc.getNpcId() == separated_soul_03 || npc.getNpcId() == separated_soul_04 ||
			npc.getNpcId() == separated_soul_05 || npc.getNpcId() == separated_soul_06 || npc.getNpcId() == separated_soul_07 || npc.getNpcId() == separated_soul_08)
		{
			if(st.isCompleted())
				return "separated_soul_01_q0456_02.htm";

			if(st.isCreated())
				return "separated_soul_01_q0456_01.htm";

			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					if(st.getQuestItemsCount(17251) == 0 && st.getQuestItemsCount(17252) == 0 && st.getQuestItemsCount(17253) == 0)
						return "npchtm:separated_soul_01_q0456_08.htm";
					if(st.getQuestItemsCount(17251) == 0 || st.getQuestItemsCount(17252) == 0 || st.getQuestItemsCount(17253) == 0)
						return "npchtm:separated_soul_01_q0456_09.htm";
					if(st.getQuestItemsCount(17251) >= 1 && st.getQuestItemsCount(17252) >= 1 && st.getQuestItemsCount(17253) >= 1)
					{
						int i0 = Rnd.get(100000);
						int i1 = 0;
						String s0 = "";
						if(i0 < 100)
						{
							st.giveItems(15743, 1);
							s0 = Util.intToFStr(45651);
						}
						else if(i0 < 200)
						{
							st.giveItems(15744, 1);
							s0 = Util.intToFStr(45652);
						}
						else if(i0 < 300)
						{
							st.giveItems(15745, 1);
							s0 = Util.intToFStr(45653);
						}
						else if(i0 < 400)
						{
							st.giveItems(15746, 1);
							s0 = Util.intToFStr(45654);
						}
						else if(i0 < 500)
						{
							st.giveItems(15747, 1);
							s0 = Util.intToFStr(45655);
						}
						else if(i0 < 600)
						{
							st.giveItems(15748, 1);
							s0 = Util.intToFStr(45656);
						}
						else if(i0 < 700)
						{
							st.giveItems(15749, 1);
							s0 = Util.intToFStr(45657);
						}
						else if(i0 < 800)
						{
							st.giveItems(15750, 1);
							s0 = Util.intToFStr(45658);
						}
						else if(i0 < 900)
						{
							st.giveItems(15751, 1);
							s0 = Util.intToFStr(45659);
						}
						else if(i0 < 1000)
						{
							st.giveItems(15752, 1);
							s0 = Util.intToFStr(45660);
						}
						else if(i0 < 1100)
						{
							st.giveItems(15753, 1);
							s0 = Util.intToFStr(45661);
						}
						else if(i0 < 1200)
						{
							st.giveItems(15754, 1);
							s0 = Util.intToFStr(45662);
						}
						else if(i0 < 1300)
						{
							st.giveItems(15755, 1);
							s0 = Util.intToFStr(45663);
						}
						else if(i0 < 1400)
						{
							st.giveItems(15756, 1);
							s0 = Util.intToFStr(45664);
						}
						else if(i0 < 1500)
						{
							st.giveItems(15757, 1);
							s0 = Util.intToFStr(45665);
						}
						else if(i0 < 1600)
						{
							st.giveItems(15758, 1);
							s0 = Util.intToFStr(45666);
						}
						else if(i0 < 1700)
						{
							st.giveItems(15759, 1);
							s0 = Util.intToFStr(45667);
						}
						else if(i0 < 1800)
						{
							st.giveItems(15763, 1);
							s0 = Util.intToFStr(45668);
						}
						else if(i0 < 1900)
						{
							st.giveItems(15764, 1);
							s0 = Util.intToFStr(45669);
						}
						else if(i0 < 2000)
						{
							st.giveItems(15765, 1);
							s0 = Util.intToFStr(45670);
						}
						else if(i0 < 2050)
						{
							st.giveItems(15558, 1);
							s0 = Util.intToFStr(45671);
						}
						else if(i0 < 2100)
						{
							st.giveItems(15559, 1);
							s0 = Util.intToFStr(45672);
						}
						else if(i0 < 2150)
						{
							st.giveItems(15560, 1);
							s0 = Util.intToFStr(45673);
						}
						else if(i0 < 2200)
						{
							st.giveItems(15561, 1);
							s0 = Util.intToFStr(45674);
						}
						else if(i0 < 2250)
						{
							st.giveItems(15562, 1);
							s0 = Util.intToFStr(45675);
						}
						else if(i0 < 2300)
						{
							st.giveItems(15563, 1);
							s0 = Util.intToFStr(45676);
						}
						else if(i0 < 2350)
						{
							st.giveItems(15564, 1);
							s0 = Util.intToFStr(45677);
						}
						else if(i0 < 2400)
						{
							st.giveItems(15565, 1);
							s0 = Util.intToFStr(45678);
						}
						else if(i0 < 2450)
						{
							st.giveItems(15566, 1);
							s0 = Util.intToFStr(45679);
						}
						else if(i0 < 2500)
						{
							st.giveItems(15567, 1);
							s0 = Util.intToFStr(45680);
						}
						else if(i0 < 2550)
						{
							st.giveItems(15568, 1);
							s0 = Util.intToFStr(45681);
						}
						else if(i0 < 2600)
						{
							st.giveItems(15569, 1);
							s0 = Util.intToFStr(45682);
						}
						else if(i0 < 2650)
						{
							st.giveItems(15570, 1);
							s0 = Util.intToFStr(45683);
						}
						else if(i0 < 2700)
						{
							st.giveItems(15571, 1);
							s0 = Util.intToFStr(45684);
						}
						else if(i0 < 3250)
						{
							st.rollAndGive(6577, 1, 100);
							s0 = Util.intToFStr(45685);
						}
						else if(i0 < 4250)
						{
							st.rollAndGive(6578, 1, 100);
							s0 = Util.intToFStr(45686);
						}
						else if(i0 < 9250)
						{
							st.rollAndGive(9552, 1, 100);
							s0 = Util.intToFStr(45687);
						}
						else if(i0 < 14250)
						{
							st.rollAndGive(9553, 1, 100);
							s0 = Util.intToFStr(45688);
						}
						else if(i0 < 19250)
						{
							st.rollAndGive(9554, 1, 100);
							s0 = Util.intToFStr(45689);
						}
						else if(i0 < 24250)
						{
							st.rollAndGive(9555, 1, 100);
							s0 = Util.intToFStr(45690);
						}
						else if(i0 < 29250)
						{
							st.rollAndGive(9557, 1, 100);
							s0 = Util.intToFStr(45691);
						}
						else if(i0 < 31250)
						{
							st.rollAndGive(9556, 1, 100);
							s0 = Util.intToFStr(45692);
						}
						else if(i0 < 33000)
						{
							st.rollAndGive(959, 1, 100);
							s0 = Util.intToFStr(45693);
						}
						else if(i0 < 100000)
						{
							st.rollAndGive(2134, 3, 100);
							i1 = 1;
						}
						if(i1 == 0)
						{
							Functions.broadcastOnScreenMsgFStr(npc, 1500, 2, 0, 1, 4, 1, 1, 5000, 0, 45650, talker.getName(), s0);
							Functions.broadcastSystemMessageFStr(npc, 1500, 45650, talker.getName(), s0);
						}

						st.takeItems(17251, 1);
						st.takeItems(17252, 1);
						st.takeItems(17253, 1);
						st.exitCurrentQuest(false, true);
						st.playSound(SOUND_FINISH);
						return "npchtm:separated_soul_01_q0456_10.htm";
					}
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == separated_soul_01 || npc.getNpcId() == separated_soul_02 || npc.getNpcId() == separated_soul_03 || npc.getNpcId() == separated_soul_04 ||
			npc.getNpcId() == separated_soul_05 || npc.getNpcId() == separated_soul_06 || npc.getNpcId() == separated_soul_07 || npc.getNpcId() == separated_soul_08)
		{
			if(reply == 456)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					st.setCond(1);
					st.setMemoState(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("separated_soul_01_q0456_07.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.getLevel() < 80)
				{
					showPage("separated_soul_01_q0456_03.htm", talker);
				}
				else if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("separated_soul_01_q0456_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("separated_soul_01_q0456_05.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("separated_soul_01_q0456_06.htm", talker);
				}
			}
		}
	}
}
