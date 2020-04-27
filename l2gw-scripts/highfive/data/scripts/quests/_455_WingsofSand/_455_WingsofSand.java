package quests._455_WingsofSand;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 25.09.11 20:00
 */
public class _455_WingsofSand extends Quest
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

	// Items
	public static final int great_dragon_tooth = 17250;

	// Mobs
	private static final int emerald_horn = 25718;
	private static final int dust_rider = 25719;
	private static final int bleeding_fly = 25720;
	private static final int blackdagger_wing = 25721;
	private static final int shadow_summoner = 25722;
	private static final int spike_slasher = 25723;
	private static final int muscle_bomber = 25724;

	public _455_WingsofSand()
	{
		super(455, "_455_WingsofSand", "Wings of Sand");
		addStartNpc(separated_soul_01);
		addStartNpc(separated_soul_02);
		addStartNpc(separated_soul_03);
		addStartNpc(separated_soul_04);
		addStartNpc(separated_soul_05);
		addStartNpc(separated_soul_06);
		addStartNpc(separated_soul_07);
		addStartNpc(separated_soul_08);
		addTalkId(separated_soul_01, separated_soul_02, separated_soul_03, separated_soul_04, separated_soul_05, separated_soul_06, separated_soul_07, separated_soul_08);
		addQuestItem(great_dragon_tooth);
		addKillId(emerald_horn, dust_rider, bleeding_fly, blackdagger_wing, shadow_summoner, spike_slasher, muscle_bomber);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == separated_soul_01 || npc.getNpcId() == separated_soul_02 || npc.getNpcId() == separated_soul_03 || npc.getNpcId() == separated_soul_04 ||
				npc.getNpcId() == separated_soul_05 || npc.getNpcId() == separated_soul_06 || npc.getNpcId() == separated_soul_07 || npc.getNpcId() == separated_soul_08)
		{
			if(st.isCompleted())
				return "separated_soul_01_q0455_02.htm";

			if(st.isCreated())
				return "separated_soul_01_q0455_01.htm";

			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(great_dragon_tooth) == 0)
					return "npchtm:separated_soul_01_q0455_08.htm";
				if(st.getMemoState() == 1 && st.getQuestItemsCount(great_dragon_tooth) == 1)
					return "npchtm:separated_soul_01_q0455_09.htm";
				if(st.getMemoState() == 1 && st.getQuestItemsCount(great_dragon_tooth) == 2)
				{
					for(int i1 = 0; i1 < 2; i1++)
					{
						int i0 = Rnd.get(1000);
						if(i0 < 18)
						{
							st.giveItems(15660, 1);
						}
						else if(i0 < 36)
						{
							st.giveItems(15661, 1);
						}
						else if(i0 < 54)
						{
							st.giveItems(15662, 1);
						}
						else if(i0 < 72)
						{
							st.giveItems(15663, 1);
						}
						else if(i0 < 90)
						{
							st.giveItems(15664, 1);
						}
						else if(i0 < 108)
						{
							st.giveItems(15665, 1);
						}
						else if(i0 < 126)
						{
							st.giveItems(15666, 1);
						}
						else if(i0 < 144)
						{
							st.giveItems(15667, 1);
						}
						else if(i0 < 162)
						{
							st.giveItems(15668, 1);
						}
						else if(i0 < 180)
						{
							st.giveItems(15669, 1);
						}
						else if(i0 < 198)
						{
							st.giveItems(15670, 1);
						}
						else if(i0 < 216)
						{
							st.giveItems(15671, 1);
						}
						else if(i0 < 234)
						{
							st.giveItems(15672, 1);
						}
						else if(i0 < 252)
						{
							st.giveItems(15673, 1);
						}
						else if(i0 < 270)
						{
							st.giveItems(15674, 1);
						}
						else if(i0 < 288)
						{
							st.giveItems(15675, 1);
						}
						else if(i0 < 306)
						{
							st.giveItems(15691, 1);
						}
						else if(i0 < 324)
						{
							st.giveItems(15769, 1);
						}
						else if(i0 < 342)
						{
							st.giveItems(15770, 1);
						}
						else if(i0 < 360)
						{
							st.giveItems(15771, 1);
						}
						else if(i0 < 378)
						{
							st.giveItems(15634, 1);
						}
						else if(i0 < 396)
						{
							st.giveItems(15635, 1);
						}
						else if(i0 < 414)
						{
							st.giveItems(15636, 1);
						}
						else if(i0 < 432)
						{
							st.giveItems(15637, 1);
						}
						else if(i0 < 450)
						{
							st.giveItems(15638, 1);
						}
						else if(i0 < 468)
						{
							st.giveItems(15639, 1);
						}
						else if(i0 < 486)
						{
							st.giveItems(15640, 1);
						}
						else if(i0 < 504)
						{
							st.giveItems(15641, 1);
						}
						else if(i0 < 522)
						{
							st.giveItems(15642, 1);
						}
						else if(i0 < 540)
						{
							st.giveItems(15643, 1);
						}
						else if(i0 < 558)
						{
							st.giveItems(15644, 1);
						}
						else if(i0 < 565)
						{
							st.giveItems(15660, 2);
						}
						else if(i0 < 572)
						{
							st.giveItems(15661, 2);
						}
						else if(i0 < 579)
						{
							st.giveItems(15662, 2);
						}
						else if(i0 < 586)
						{
							st.giveItems(15663, 2);
						}
						else if(i0 < 593)
						{
							st.giveItems(15664, 2);
						}
						else if(i0 < 600)
						{
							st.giveItems(15665, 2);
						}
						else if(i0 < 607)
						{
							st.giveItems(15666, 2);
						}
						else if(i0 < 614)
						{
							st.giveItems(15667, 2);
						}
						else if(i0 < 621)
						{
							st.giveItems(15668, 2);
						}
						else if(i0 < 628)
						{
							st.giveItems(15669, 2);
						}
						else if(i0 < 635)
						{
							st.giveItems(15670, 2);
						}
						else if(i0 < 642)
						{
							st.giveItems(15671, 2);
						}
						else if(i0 < 649)
						{
							st.giveItems(15672, 2);
						}
						else if(i0 < 656)
						{
							st.giveItems(15673, 2);
						}
						else if(i0 < 663)
						{
							st.giveItems(15674, 2);
						}
						else if(i0 < 670)
						{
							st.giveItems(15675, 2);
						}
						else if(i0 < 677)
						{
							st.giveItems(15691, 2);
						}
						else if(i0 < 684)
						{
							st.giveItems(15769, 2);
						}
						else if(i0 < 691)
						{
							st.giveItems(15770, 2);
						}
						else if(i0 < 698)
						{
							st.giveItems(15771, 2);
						}
						else if(i0 < 705)
						{
							st.giveItems(15634, 2);
						}
						else if(i0 < 712)
						{
							st.giveItems(15635, 2);
						}
						else if(i0 < 719)
						{
							st.giveItems(15636, 2);
						}
						else if(i0 < 726)
						{
							st.giveItems(15637, 2);
						}
						else if(i0 < 733)
						{
							st.giveItems(15638, 2);
						}
						else if(i0 < 740)
						{
							st.giveItems(15639, 2);
						}
						else if(i0 < 747)
						{
							st.giveItems(15640, 2);
						}
						else if(i0 < 754)
						{
							st.giveItems(15641, 2);
						}
						else if(i0 < 761)
						{
							st.giveItems(15642, 2);
						}
						else if(i0 < 768)
						{
							st.giveItems(15643, 2);
						}
						else if(i0 < 775)
						{
							st.giveItems(15644, 2);
						}
						else if(i0 < 780)
						{
							st.giveItems(15792, 1);
						}
						else if(i0 < 785)
						{
							st.giveItems(15793, 1);
						}
						else if(i0 < 790)
						{
							st.giveItems(15794, 1);
						}
						else if(i0 < 795)
						{
							st.giveItems(15795, 1);
						}
						else if(i0 < 800)
						{
							st.giveItems(15796, 1);
						}
						else if(i0 < 805)
						{
							st.giveItems(15797, 1);
						}
						else if(i0 < 810)
						{
							st.giveItems(15798, 1);
						}
						else if(i0 < 815)
						{
							st.giveItems(15799, 1);
						}
						else if(i0 < 820)
						{
							st.giveItems(15800, 1);
						}
						else if(i0 < 825)
						{
							st.giveItems(15801, 1);
						}
						else if(i0 < 830)
						{
							st.giveItems(15802, 1);
						}
						else if(i0 < 835)
						{
							st.giveItems(15803, 1);
						}
						else if(i0 < 840)
						{
							st.giveItems(15804, 1);
						}
						else if(i0 < 845)
						{
							st.giveItems(15805, 1);
						}
						else if(i0 < 850)
						{
							st.giveItems(15806, 1);
						}
						else if(i0 < 855)
						{
							st.giveItems(15807, 1);
						}
						else if(i0 < 860)
						{
							st.giveItems(15808, 1);
						}
						else if(i0 < 865)
						{
							st.giveItems(15809, 1);
						}
						else if(i0 < 870)
						{
							st.giveItems(15810, 1);
						}
						else if(i0 < 875)
						{
							st.giveItems(15811, 1);
						}
						else if(i0 < 877)
						{
							st.giveItems(15815, 1);
						}
						else if(i0 < 879)
						{
							st.giveItems(15816, 1);
						}
						else if(i0 < 881)
						{
							st.giveItems(15817, 1);
						}
						else if(i0 < 883)
						{
							st.giveItems(15818, 1);
						}
						else if(i0 < 885)
						{
							st.giveItems(15819, 1);
						}
						else if(i0 < 887)
						{
							st.giveItems(15820, 1);
						}
						else if(i0 < 889)
						{
							st.giveItems(15821, 1);
						}
						else if(i0 < 891)
						{
							st.giveItems(15822, 1);
						}
						else if(i0 < 893)
						{
							st.giveItems(15823, 1);
						}
						else if(i0 < 895)
						{
							st.giveItems(15824, 1);
						}
						else if(i0 < 897)
						{
							st.giveItems(15825, 1);
						}
						else if(i0 < 912)
						{
							st.rollAndGive(9552, 1, 100);
						}
						else if(i0 < 927)
						{
							st.rollAndGive(9553, 1, 100);
						}
						else if(i0 < 942)
						{
							st.rollAndGive(9554, 1, 100);
						}
						else if(i0 < 957)
						{
							st.rollAndGive(9555, 1, 100);
						}
						else if(i0 < 972)
						{
							st.rollAndGive(9556, 1, 100);
						}
						else if(i0 < 987)
						{
							st.rollAndGive(9557, 1, 100);
						}
						else if(i0 < 989)
						{
							st.rollAndGive(6577, 1, 100);
						}
						else if(i0 < 1000)
						{
							st.rollAndGive(6578, 1, 100);
						}
					}

					st.takeItems(great_dragon_tooth, -1);
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					return "npchtm:separated_soul_01_q0455_12.htm";
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
			if(reply == 455)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("separated_soul_01_q0455_07.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.getLevel() < 80)
				{
					showPage("separated_soul_01_q0455_03.htm", talker);
				}
				else if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("separated_soul_01_q0455_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("separated_soul_01_q0455_05.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isCreated() && talker.getLevel() >= 80)
				{
					showQuestPage("separated_soul_01_q0455_06.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(great_dragon_tooth) == 1)
				{
					int i0 = Rnd.get(1000);
					if(i0 < 18)
					{
						st.giveItems(15660, 1);
					}
					else if(i0 < 36)
					{
						st.giveItems(15661, 1);
					}
					else if(i0 < 54)
					{
						st.giveItems(15662, 1);
					}
					else if(i0 < 72)
					{
						st.giveItems(15663, 1);
					}
					else if(i0 < 90)
					{
						st.giveItems(15664, 1);
					}
					else if(i0 < 108)
					{
						st.giveItems(15665, 1);
					}
					else if(i0 < 126)
					{
						st.giveItems(15666, 1);
					}
					else if(i0 < 144)
					{
						st.giveItems(15667, 1);
					}
					else if(i0 < 162)
					{
						st.giveItems(15668, 1);
					}
					else if(i0 < 180)
					{
						st.giveItems(15669, 1);
					}
					else if(i0 < 198)
					{
						st.giveItems(15670, 1);
					}
					else if(i0 < 216)
					{
						st.giveItems(15671, 1);
					}
					else if(i0 < 234)
					{
						st.giveItems(15672, 1);
					}
					else if(i0 < 252)
					{
						st.giveItems(15673, 1);
					}
					else if(i0 < 270)
					{
						st.giveItems(15674, 1);
					}
					else if(i0 < 288)
					{
						st.giveItems(15675, 1);
					}
					else if(i0 < 306)
					{
						st.giveItems(15691, 1);
					}
					else if(i0 < 324)
					{
						st.giveItems(15769, 1);
					}
					else if(i0 < 342)
					{
						st.giveItems(15770, 1);
					}
					else if(i0 < 360)
					{
						st.giveItems(15771, 1);
					}
					else if(i0 < 378)
					{
						st.giveItems(15634, 1);
					}
					else if(i0 < 396)
					{
						st.giveItems(15635, 1);
					}
					else if(i0 < 414)
					{
						st.giveItems(15636, 1);
					}
					else if(i0 < 432)
					{
						st.giveItems(15637, 1);
					}
					else if(i0 < 450)
					{
						st.giveItems(15638, 1);
					}
					else if(i0 < 468)
					{
						st.giveItems(15639, 1);
					}
					else if(i0 < 486)
					{
						st.giveItems(15640, 1);
					}
					else if(i0 < 504)
					{
						st.giveItems(15641, 1);
					}
					else if(i0 < 522)
					{
						st.giveItems(15642, 1);
					}
					else if(i0 < 540)
					{
						st.giveItems(15643, 1);
					}
					else if(i0 < 558)
					{
						st.giveItems(15644, 1);
					}
					else if(i0 < 565)
					{
						st.giveItems(15660, 2);
					}
					else if(i0 < 572)
					{
						st.giveItems(15661, 2);
					}
					else if(i0 < 579)
					{
						st.giveItems(15662, 2);
					}
					else if(i0 < 586)
					{
						st.giveItems(15663, 2);
					}
					else if(i0 < 593)
					{
						st.giveItems(15664, 2);
					}
					else if(i0 < 600)
					{
						st.giveItems(15665, 2);
					}
					else if(i0 < 607)
					{
						st.giveItems(15666, 2);
					}
					else if(i0 < 614)
					{
						st.giveItems(15667, 2);
					}
					else if(i0 < 621)
					{
						st.giveItems(15668, 2);
					}
					else if(i0 < 628)
					{
						st.giveItems(15669, 2);
					}
					else if(i0 < 635)
					{
						st.giveItems(15670, 2);
					}
					else if(i0 < 642)
					{
						st.giveItems(15671, 2);
					}
					else if(i0 < 649)
					{
						st.giveItems(15672, 2);
					}
					else if(i0 < 656)
					{
						st.giveItems(15673, 2);
					}
					else if(i0 < 663)
					{
						st.giveItems(15674, 2);
					}
					else if(i0 < 670)
					{
						st.giveItems(15675, 2);
					}
					else if(i0 < 677)
					{
						st.giveItems(15691, 2);
					}
					else if(i0 < 684)
					{
						st.giveItems(15769, 2);
					}
					else if(i0 < 691)
					{
						st.giveItems(15770, 2);
					}
					else if(i0 < 698)
					{
						st.giveItems(15771, 2);
					}
					else if(i0 < 705)
					{
						st.giveItems(15634, 2);
					}
					else if(i0 < 712)
					{
						st.giveItems(15635, 2);
					}
					else if(i0 < 719)
					{
						st.giveItems(15636, 2);
					}
					else if(i0 < 726)
					{
						st.giveItems(15637, 2);
					}
					else if(i0 < 733)
					{
						st.giveItems(15638, 2);
					}
					else if(i0 < 740)
					{
						st.giveItems(15639, 2);
					}
					else if(i0 < 747)
					{
						st.giveItems(15640, 2);
					}
					else if(i0 < 754)
					{
						st.giveItems(15641, 2);
					}
					else if(i0 < 761)
					{
						st.giveItems(15642, 2);
					}
					else if(i0 < 768)
					{
						st.giveItems(15643, 2);
					}
					else if(i0 < 775)
					{
						st.giveItems(15644, 2);
					}
					else if(i0 < 780)
					{
						st.giveItems(15792, 1);
					}
					else if(i0 < 785)
					{
						st.giveItems(15793, 1);
					}
					else if(i0 < 790)
					{
						st.giveItems(15794, 1);
					}
					else if(i0 < 795)
					{
						st.giveItems(15795, 1);
					}
					else if(i0 < 800)
					{
						st.giveItems(15796, 1);
					}
					else if(i0 < 805)
					{
						st.giveItems(15797, 1);
					}
					else if(i0 < 810)
					{
						st.giveItems(15798, 1);
					}
					else if(i0 < 815)
					{
						st.giveItems(15799, 1);
					}
					else if(i0 < 820)
					{
						st.giveItems(15800, 1);
					}
					else if(i0 < 825)
					{
						st.giveItems(15801, 1);
					}
					else if(i0 < 830)
					{
						st.giveItems(15802, 1);
					}
					else if(i0 < 835)
					{
						st.giveItems(15803, 1);
					}
					else if(i0 < 840)
					{
						st.giveItems(15804, 1);
					}
					else if(i0 < 845)
					{
						st.giveItems(15805, 1);
					}
					else if(i0 < 850)
					{
						st.giveItems(15806, 1);
					}
					else if(i0 < 855)
					{
						st.giveItems(15807, 1);
					}
					else if(i0 < 860)
					{
						st.giveItems(15808, 1);
					}
					else if(i0 < 865)
					{
						st.giveItems(15809, 1);
					}
					else if(i0 < 870)
					{
						st.giveItems(15810, 1);
					}
					else if(i0 < 875)
					{
						st.giveItems(15811, 1);
					}
					else if(i0 < 877)
					{
						st.giveItems(15815, 1);
					}
					else if(i0 < 879)
					{
						st.giveItems(15816, 1);
					}
					else if(i0 < 881)
					{
						st.giveItems(15817, 1);
					}
					else if(i0 < 883)
					{
						st.giveItems(15818, 1);
					}
					else if(i0 < 885)
					{
						st.giveItems(15819, 1);
					}
					else if(i0 < 887)
					{
						st.giveItems(15820, 1);
					}
					else if(i0 < 889)
					{
						st.giveItems(15821, 1);
					}
					else if(i0 < 891)
					{
						st.giveItems(15822, 1);
					}
					else if(i0 < 893)
					{
						st.giveItems(15823, 1);
					}
					else if(i0 < 895)
					{
						st.giveItems(15824, 1);
					}
					else if(i0 < 897)
					{
						st.giveItems(15825, 1);
					}
					else if(i0 < 912)
					{
						st.rollAndGive(9552, 1, 100);
					}
					else if(i0 < 927)
					{
						st.rollAndGive(9553, 1, 100);
					}
					else if(i0 < 942)
					{
						st.rollAndGive(9554, 1, 100);
					}
					else if(i0 < 957)
					{
						st.rollAndGive(9555, 1, 100);
					}
					else if(i0 < 972)
					{
						st.rollAndGive(9556, 1, 100);
					}
					else if(i0 < 987)
					{
						st.rollAndGive(9557, 1, 100);
					}
					else if(i0 < 989)
					{
						st.rollAndGive(6577, 1, 100);
					}
					else if(i0 < 1000)
					{
						st.rollAndGive(6578, 1, 100);
					}

					st.takeItems(great_dragon_tooth, -1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false, true);
					showPage("separated_soul_01_q0455_10.htm", talker);
				}
			}
			else if(reply == 5)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(great_dragon_tooth) == 1)
				{
					showPage("separated_soul_01_q0455_11.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> party = getPartyMembersWithMemoState(killer, 1);
		for(QuestState st : party)
			if(st.getQuestItemsCount(great_dragon_tooth) < 2)
			{
				st.giveItems(great_dragon_tooth, 1);
				st.playSound(SOUND_ITEMGET);
				st.setCond((int) st.getQuestItemsCount(great_dragon_tooth) + 1);
				showQuestMark(st.getPlayer());
				st.playSound(SOUND_MIDDLE);
			}
	}
}