package quests._10293_SevenSignsForbiddenBookoftheElmoreAdenKingdom;

import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 29.09.11 21:11
 */
public class _10293_SevenSignsForbiddenBookoftheElmoreAdenKingdom extends Quest implements IItemHandler
{
	// NPC
	private static final int ssq2_elcardia_home1 = 32784;
	private static final int director_sophia = 32596;
	private static final int ssq2_director_sophia2 = 32861;
	private static final int ssq2_director_sophia3 = 32863;
	private static final int ssq2_elcardia_library1 = 32785;
	private static final int ssq2_cl1_library = 32809;
	private static final int ssq2_cl2_library = 32810;
	private static final int ssq2_cl3_library = 32811;
	private static final int ssq2_cl4_library = 32812;
	private static final int ssq2_cl5_library = 32813;

	// Items
	private static final int[] q10293_ssq2_solina_bio = { 17213 };

	public _10293_SevenSignsForbiddenBookoftheElmoreAdenKingdom()
	{
		super(10293, "_10293_SevenSignsForbiddenBookoftheElmoreAdenKingdom", "Seven Signs, Forbidden Book of the Elmore-Aden Kingdom");
		addStartNpc(ssq2_elcardia_home1);
		addTalkId(ssq2_elcardia_home1, director_sophia, ssq2_director_sophia2, ssq2_director_sophia3, ssq2_elcardia_library1);
		addTalkId(ssq2_cl1_library, ssq2_cl2_library, ssq2_cl3_library, ssq2_cl4_library, ssq2_cl5_library);
		addQuestItem(q10293_ssq2_solina_bio[0]);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == ssq2_elcardia_home1)
		{
			if(st.isCreated() && talker.isQuestComplete(10292) && talker.getLevel() >= 81)
				return "ssq2_elcardia_home1_q10293_01.htm";

			if(st.isCompleted())
				return "npchtm:ssq2_elcardia_home1_q10293_02.htm";

			if(st.isStarted())
			{
				if(st.getMemoState() == 1 || st.getMemoState() < 9)
					return "npchtm:ssq2_elcardia_home1_q10293_06.htm";
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_elcardia_home1_q10293_07.htm";
			}
		}
		else if(npc.getNpcId() == director_sophia)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:director_sophia_q10293_01.htm";
				if(st.getMemoState() > 1 && st.getMemoState() < 9)
					return "npchtm:director_sophia_q10293_02.htm";
				if(st.getMemoState() > 8)
					return "npchtm:director_sophia_q10293_05.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_director_sophia2)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:ssq2_director_sophia2_q10293_01.htm";
				if(st.getMemoState() == 2)
					return "npchtm:ssq2_director_sophia2_q10293_05.htm";
				if(st.getMemoState() == 3)
					return "npchtm:ssq2_director_sophia2_q10293_06.htm";
				if(st.getMemoState() == 4)
					return "npchtm:ssq2_director_sophia2_q10293_09.htm";
				if(st.getMemoState() == 5)
					return "npchtm:ssq2_director_sophia2_q10293_10.htm";
				if(st.getMemoState() > 5 && st.getMemoState() < 9)
					return "npchtm:ssq2_director_sophia2_q10293_12.htm";
				if(st.getMemoState() > 8)
					return "npchtm:ssq2_director_sophia2_q10293_14.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_director_sophia3)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() > 5 && st.getMemoState() < 9)
					return "npchtm:ssq2_director_sophia3_q10293_01.htm";
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_director_sophia3_q10293_04.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia_library1)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:ssq2_elcardia_library1_q10293_01.htm";
				if(st.getMemoState() == 2)
				{
					st.setMemoState(3);
					st.setCond(3);
					st.playSound(SOUND_MIDDLE);
					showQuestMark(talker);
					return "npchtm:ssq2_elcardia_library1_q10293_03.htm";
				}
				if(st.getMemoState() == 4)
					return "npchtm:ssq2_elcardia_library1_q10293_04.htm";
				if(st.getMemoState() == 5)
					return "npchtm:ssq2_elcardia_library1_q10293_06.htm";
				if(st.getMemoState() == 3)
					return "npchtm:ssq2_elcardia_library1_q10293_08.htm";
				if(st.getMemoState() == 6)
				{
					st.setMemoState(7);
					return "npchtm:ssq2_elcardia_library1_q10293_09.htm";
				}
				if(st.getMemoState() == 7)
					return "npchtm:ssq2_elcardia_library1_q10293_10.htm";
				if(st.getMemoState() == 8)
				{
					st.setMemoState(9);
					st.setCond(8);
					st.playSound(SOUND_MIDDLE);
					showQuestMark(talker);
					return "npchtm:ssq2_elcardia_library1_q10293_11.htm";
				}
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_elcardia_library1_q10293_12.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_cl1_library)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 6 || st.getMemoState() == 7)
					return "npchtm:ssq2_cl1_library_q10293_01.htm";
				if(st.getMemoState() < 6)
					return "npchtm:ssq2_cl1_library_q10293_02.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_cl2_library)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 6 || st.getMemoState() == 7)
					return "npchtm:ssq2_cl2_library_q10293_01.htm";
				if(st.getMemoState() < 6)
					return "npchtm:ssq2_cl2_library_q10293_02.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_cl3_library)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 6 || st.getMemoState() == 7)
					return "npchtm:ssq2_cl3_library_q10293_01.htm";
				if(st.getMemoState() < 6)
					return "npchtm:ssq2_cl3_library_q10293_02.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_cl4_library)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 6 || st.getMemoState() == 7)
					return "npchtm:ssq2_cl4_library_q10293_01.htm";
				if(st.getMemoState() < 6)
					return "npchtm:ssq2_cl4_library_q10293_02.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_cl5_library)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 6 || st.getMemoState() == 7)
					return "npchtm:ssq2_cl5_library_q10293_01.htm";
				if(st.getMemoState() < 6)
					return "npchtm:ssq2_cl5_library_q10293_02.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == ssq2_elcardia_home1)
		{
			if(reply == 10293)
			{
				if(st.isCreated() && talker.isQuestComplete(10292) && talker.getLevel() >= 81)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("ssq2_elcardia_home1_q10293_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.isQuestComplete(10292) && talker.getLevel() >= 81)
				{
					showQuestPage("ssq2_elcardia_home1_q10293_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 9)
				{
					showPage("ssq2_elcardia_home1_q10293_08.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 9)
				{
					if(talker.isSubClassActive())
					{
						showPage("ssq2_elcardia_home1_q10293_08.htm", talker);
					}
					else
					{
						st.addExpAndSp(15000000, 1500000);
						st.takeItems(q10293_ssq2_solina_bio[0], -1);
						st.playSound(SOUND_FINISH);
						st.exitCurrentQuest(false);
						showPage("ssq2_elcardia_home1_q10293_10.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == director_sophia)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() < 9)
				{
					showPage("director_sophia_q10293_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() < 9)
				{
					showPage("director_sophia_q10293_04.htm", talker);
				}
			}
			else if(reply == 3)
			{
				InstanceManager.enterInstance(156, talker, npc, 0);
			}
		}
		else if(npc.getNpcId() == ssq2_director_sophia2)
		{
			if( reply == 1 )
			{
				if( st.isStarted() && st.getMemoState() == 1 )
				{
					showPage("ssq2_director_sophia2_q10293_02.htm", talker);
				}
			}
			else if( reply == 2 )
			{
				if( st.isStarted() && st.getMemoState() == 1 )
				{
					showPage("ssq2_director_sophia2_q10293_03.htm", talker);
				}
			}
			else if( reply == 3 )
			{
				if( st.isStarted() && st.getMemoState() == 1 )
				{
					st.setMemoState(2);
					showPage("ssq2_director_sophia2_q10293_04.htm", talker);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if( reply == 4 )
			{
				if( st.isStarted() && st.getMemoState() == 3 )
				{
					showPage("ssq2_director_sophia2_q10293_07.htm", talker);
				}
			}
			else if( reply == 5 )
			{
				if( st.isStarted() && st.getMemoState() == 3 )
				{
					st.setMemoState(4);
					showPage("ssq2_director_sophia2_q10293_08.htm", talker);
					st.setCond(4);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if( reply == 6 )
			{
				if( st.isStarted() && st.getMemoState() == 5 )
				{
					st.setMemoState(6);
					showPage("ssq2_director_sophia2_q10293_11.htm", talker);
					st.setCond(6);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 7)
			{
				talker.teleToLocation(37348, -50383, -1164);
				L2NpcInstance elcarid = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia_library1);
				if(elcarid != null)
					elcarid.teleToLocation(37348, -50383, -1164);
			}
			else if(reply == 10)
			{
				Instance inst = npc.getInstanceZone();
				if(inst != null)
					inst.setNoUserTimeout(60000);
				talker.teleToClosestTown();
			}
		}
		else if(npc.getNpcId() == ssq2_director_sophia3)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() > 5 && st.getMemoState() < 9)
				{
					showPage("ssq2_director_sophia3_q10293_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				talker.teleToLocation(37097, -49828, -1128);
				L2NpcInstance elcarida = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia_library1);
				if(elcarida != null)
					elcarida.teleToLocation(37097, -49828, -1128);
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia_library1)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("ssq2_elcardia_library1_q10293_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 4)
				{
					st.setMemoState(5);
					showPage("ssq2_elcardia_library1_q10293_05.htm", talker);
					st.setCond(5);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl1_library || npc.getNpcId() == ssq2_cl2_library || npc.getNpcId() == ssq2_cl3_library ||
				npc.getNpcId() == ssq2_cl4_library || npc.getNpcId() == ssq2_cl5_library)
		{
			if(reply == 1)
			{
				if(npc.i_ai1 == 1 && npc.i_ai0 == 0)
				{
					npc.i_ai0 = 1;
					showPage("ssq2_cl1_library_q10293_03.htm", talker);
					st.setMemoState(8);
					st.setCond(7);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					if(st.getQuestItemsCount(q10293_ssq2_solina_bio[0]) == 0)
					{
						st.giveItems(q10293_ssq2_solina_bio[0], 1);
					}
				}
				else if(npc.i_ai1 == 0)
				{
					showPage("ssq2_cl1_library_q10293_04.htm", talker);
				}
				else if(npc.i_ai0 == 0)
				{
					showPage("ssq2_cl1_library_q10293_04.htm", talker);
				}
			}
		}
	}

	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(!playable.isPlayer())
			return false;

		L2Player talker = playable.getPlayer();
		showPage("data/html/default/ssq2_solina_bio_q10293_01.htm", talker);

		return true;
	}

	public int[] getItemIds()
	{
		return q10293_ssq2_solina_bio;
	}

	public void onLoad()
	{
		super.onLoad();
		ItemHandler.getInstance().registerItemHandler(this);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}
}