package quests._287_FiguringItOut;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author rage
 * @date 05.02.11 18:16
 */
public class _287_FiguringItOut extends Quest
{
	// NPCs
	private static final int laki = 32742;

	// Items
	private static final int q_blood_of_lizard_bottle = 15499;

	// Mobs
	private static final HashMap<Integer, Double> _mobs = new HashMap<Integer, Double>();

	static
	{
		_mobs.put(22772, 73.9);
		_mobs.put(22768, 50.9);
		_mobs.put(22773, 73.7);
		_mobs.put(22771, 15.9);
		_mobs.put(22770, 12.3);
		_mobs.put(22774, 26.1);
		_mobs.put(22769, 68.9);
	}

	public _287_FiguringItOut()
	{
		super(287, "_287_FiguringItOut", "Figuring It Out!");

		addStartNpc(laki);
		addTalkId(laki);
		for(int npcId : _mobs.keySet())
			addKillId(npcId);
		addQuestItem(q_blood_of_lizard_bottle);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == laki)
		{
			if(st.isCreated())
			{
				if(reply == 287 && player.getLevel() >= 82 && player.isQuestComplete(250))
				{
					st.setMemoState(1);
					st.set("ex", 1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("laki_q0287_03.htm", player);
				}
				else if(reply == 1 && player.getLevel() >= 82 && player.isQuestComplete(250))
					showQuestPage("laki_q0287_02.htm", player);
			}
			else if(st.isStarted())
			{
				if(reply == 2)
				{
					if(st.getMemoState() == 1 && st.getQuestItemsCount(q_blood_of_lizard_bottle) >= 500)
					{
						st.takeItems(q_blood_of_lizard_bottle, 500);
						int i0 = Rnd.get(5);
						if( i0 == 0 )
						{
							st.giveItems(10381, 1);
						}
						else if( i0 == 1 )
						{
							st.giveItems(10405, 1);
						}
						else if( i0 == 2 )
						{
							st.giveItems(10405, 4);
						}
						else if( i0 == 3 )
						{
							st.giveItems(10405, 4);
						}
						else
						{
							st.giveItems(10405, 6);
						}

						st.playSound(SOUND_FINISH);
						showPage("laki_q0287_06.htm", player);
					}
					else if(st.getMemoState() == 1 && st.getQuestItemsCount(q_blood_of_lizard_bottle) < 500)
					{
						showPage("laki_q0287_07.htm", player);
					}
				}
				else if(reply == 3)
				{
					if(st.getMemoState() == 1 && st.getQuestItemsCount(q_blood_of_lizard_bottle) >= 100)
					{
						st.takeItems(q_blood_of_lizard_bottle, 100);
						int i0 = Rnd.get(10);
						if(i0 == 0)
							st.giveItems(15776, 1);
						else if(i0 == 1)
							st.giveItems(15779, 1);
						else if(i0 == 2)
							st.giveItems(15782, 1);
						else if(i0 == 3)
						{
							int i1 = Rnd.get(2);
							if(i1 == 0)
								st.giveItems(15785, 1);
							else
								st.giveItems(15788, 1);
						}
						else if(i0 == 4)
						{
							int i1 = Rnd.get(10);
							if(i1 < 4)
								st.giveItems(15812, 1);
							else if(i1 >= 4 && i1 < 8)
								st.giveItems(15813, 1);
							else
								st.giveItems(15814, 1);
						}
						else if(i0 == 5)
							st.giveItems(15646, 5);
						else if(i0 == 6)
							st.giveItems(15649, 5);
						else if(i0 == 7)
							st.giveItems(15652, 5);
						else if(i0 == 8)
						{
							int i1 = Rnd.get(2);
							if(i1 == 0)
								st.giveItems(15655, 5);
							else
								st.giveItems(15658, 5);
						}
						else
						{
							int i1 = Rnd.get(10);
							if(i1 < 4)
								st.giveItems(15772, 1);
							else if(((i1 >= 4) && (i1 < 7)))
								st.giveItems(15773, 1);
							else
								st.giveItems(15774, 1);
						}

						st.playSound(SOUND_FINISH);
						showPage("laki_q0287_08.htm", player);
					}
					else if(st.getMemoState() == 1 && st.getQuestItemsCount(q_blood_of_lizard_bottle) < 100)
						showPage("laki_q0287_09.htm", player);
				}
				else if(reply == 4)
				{
					if(st.getMemoState() == 1)
						showPage("laki_q0287_10.htm", player);
				}
				else if(reply == 5)
				{
					if(st.getMemoState() == 1 && st.getQuestItemsCount(q_blood_of_lizard_bottle) >= 1)
						showPage("laki_q0287_11.htm", player);
					else if(st.getMemoState() == 1 && st.getQuestItemsCount(q_blood_of_lizard_bottle) == 0)
					{
						st.exitCurrentQuest(true);
						st.playSound(SOUND_FINISH);
						showPage("laki_q0287_12.htm", player);
					}
				}
				else if(reply == 6)
				{
					if(st.getMemoState() == 1)
					{
						st.takeItems(q_blood_of_lizard_bottle, -1);
						st.exitCurrentQuest(true);
						st.playSound(SOUND_FINISH);
						showPage("laki_q0287_13.htm", player);
					}
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == laki)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82 && st.getPlayer().isQuestComplete(250))
					return "laki_q0287_01.htm";

				st.exitCurrentQuest(true);
				return "laki_q0287_14.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
				{
					if(st.getQuestItemsCount(q_blood_of_lizard_bottle) < 100)
						return "npchtm:laki_q0287_04.htm";

					return "npchtm:laki_q0287_05.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(_mobs.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithMemoState(killer, 1);
			if(qs != null && qs.rollAndGive(q_blood_of_lizard_bottle, 1, _mobs.get(npc.getNpcId())))
				qs.playSound(SOUND_ITEMGET);
		}
	}
}
