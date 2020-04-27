package quests._697_DefendtheHallofErosion;

import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 16.12.11 17:41
 */
public class _697_DefendtheHallofErosion extends Quest
{
	// NPC
	private static final int officer_tepios = 32603;

	// Mobs
	private static final int spc_tumor_2lv_a = 18708;

	public _697_DefendtheHallofErosion()
	{
		super(697, "_697_DefendtheHallofErosion", "Defend the Hall of Erosion");
		addStartNpc(officer_tepios);
		addTalkId(officer_tepios);

		addKillId(spc_tumor_2lv_a);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == officer_tepios)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 75)
					return "officer_tepios_q0697_01.htm";

				return "officer_tepios_q0697_02.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 2)
					return "officer_tepios_q0697_06.htm";
				if(st.getMemoState() == 4)
				{
					int i0 = Rnd.get(1000);
					int i2, i1;
					if( i0 < 542 )
					{
						i2 = Rnd.get(5) + 14;
					}
					else
					{
						i2 = Rnd.get(5) + 18;
					}

					i1 = Rnd.get(1000);
					if( st.getInt("ex_1") >= 35 )
					{
						if( i1 > 552 )
						{
							i2 = ( i2 + 2 );
						}
					}
					else if( st.getInt("ex_1") >= 45 )
					{
						if( i1 < 552 )
						{
							i2 = ( i2 + 2 );
						}
						else
						{
							i2 = ( i2 + 4 );
						}
					}
					else if( st.getInt("ex_1") >= 55 )
					{
						if( i1 < 552 )
						{
							i2 = ( i2 + 4 );
						}
						else
						{
							i2 = ( i2 + 6 );
						}
					}
					else if( st.getInt("ex_1") >= 65 )
					{
						if( i1 < 552 )
						{
							i2 = ( i2 + 6 );
						}
						else
						{
							i2 = ( i2 + 8 );
						}
					}
					else if( st.getInt("ex_1") >= 75 )
					{
						if( i1 < 552 )
						{
							i2 = ( i2 + 8 );
						}
						else
						{
							i2 = ( i2 + 10 );
						}
					}

					st.giveItems(14052, i2);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "officer_tepios_q0697_07.htm";
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
		if(npc.getNpcId() == officer_tepios)
		{
			if(reply == 697)
			{
				if(st.isCreated() && talker.getLevel() >= 75)
				{
					st.setMemoState(2);
					st.set("ex_1", 0);
					showQuestPage("officer_tepios_q0697_05.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 1)
			{
				if(FieldCycleManager.getStep(3) == 4)
				{
					showPage("officer_tepios_q0697_04.htm", talker);
				}
				else
				{
					showPage("officer_tepios_q0697_03.htm", talker);
				}
			}

		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> party = getPartyMembersWithMemoState(killer, 2);
		if(!party.isEmpty())
		{
			for(QuestState st : party)
				st.set("ex_1", st.getInt("ex_1") + 1);
		}
	}
}