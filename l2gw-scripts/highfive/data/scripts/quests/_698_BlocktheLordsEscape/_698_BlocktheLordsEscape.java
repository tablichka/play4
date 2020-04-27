package quests._698_BlocktheLordsEscape;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 17.12.11 16:38
 */
public class _698_BlocktheLordsEscape extends Quest
{
	// NPC
	private static final int officer_tepios = 32603;

	// Mobs
	private static final int sboss_carpencharr = 25637;
	private static final int sboss_romerohiv = 25638;
	private static final int sboss_hitchkharshiek = 25639;
	private static final int sboss_freedkyilla = 25640;
	private static final int sboss_cravenizad = 25641;
	private static final int sboss_jaxsibhan = 25642;
	private static final int spc_soulwagon_2lv_d = 22523;

	public _698_BlocktheLordsEscape()
	{
		super();
		addStartNpc(officer_tepios);
		addTalkId(officer_tepios);

		addKillId(sboss_carpencharr, sboss_romerohiv, sboss_hitchkharshiek, sboss_freedkyilla, sboss_cravenizad, sboss_jaxsibhan, spc_soulwagon_2lv_d);
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
					return "officer_tepios_q0698_01.htm";

				return "officer_tepios_q0698_02.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 2)
					return "officer_tepios_q0698_06.htm";
				if(st.getMemoState() == 4)
				{
					int i0 = Rnd.get(2);
					int i1;
					if(i0 < 1)
					{
						i1 = (Rnd.get(5) + 8);
					}
					else
					{
						i1 = (Rnd.get(19) + 8);
					}
					if(st.getInt("ex_1") >= 31)
					{
						st.set("ex_1", 31);
					}

					int i2;
					if(st.getInt("ex_1") >= 9)
					{
						i2 = Rnd.get(4) + st.getInt("ex_1") - 8;
					}
					else
					{
						i2 = Rnd.get(4);
					}

					st.giveItems(14052, i1 + i2);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "officer_tepios_q0698_07.htm";
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
			if(reply == 698)
			{
				if(st.isCreated() && talker.getLevel() >= 75)
				{
					st.setMemoState(2);
					showQuestPage("officer_tepios_q0698_05.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 1)
			{
				if(FieldCycleManager.getStep(3) == 5)
				{
					showPage("officer_tepios_q0698_04.htm", talker);
				}
				else
				{
					showPage("officer_tepios_q0698_03.htm", talker);
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
			{
				st.set("ex_1", st.getInt("ex_1") + (npc.getNpcId() == spc_soulwagon_2lv_d ? 1 : 5));
			}
		}
	}
}