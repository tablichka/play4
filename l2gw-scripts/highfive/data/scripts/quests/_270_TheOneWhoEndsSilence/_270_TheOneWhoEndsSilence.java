package quests._270_TheOneWhoEndsSilence;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author rage
 * @date 04.02.11 19:26
 */
public class _270_TheOneWhoEndsSilence extends Quest
{
	// NPCs
	private static final int new_falsepriest_gremory = 32757;

	// Items
	private static final int q_tatters_of_monk = 15526;

	// Mobs
	private static final HashMap<Integer, Integer> _mobs = new HashMap<Integer, Integer>();
	static
	{
		_mobs.put(22799, 89);
		_mobs.put(22794, 69);
		_mobs.put(22800, 95);
		_mobs.put(22796, 90);
		_mobs.put(22798, 88);
		_mobs.put(22795, 73);
		_mobs.put(22791, 5);
		_mobs.put(22790, 5);
		_mobs.put(22793, 6);
	}

	public _270_TheOneWhoEndsSilence()
	{
		super(270, "_270_TheOneWhoEndsSilence", "The One Who Ends Silence");

		addStartNpc(new_falsepriest_gremory);
		addTalkId(new_falsepriest_gremory);
		for(int npcId : _mobs.keySet())
			addKillId(npcId);
		addQuestItem(q_tatters_of_monk);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("cute_harry_q0250_12.htm", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == new_falsepriest_gremory)
		{
			if(st.isCreated() && player.getLevel() >= 82 && player.isQuestComplete(10288))
			{
				if(reply == 270)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("new_falsepriest_gremory_q0270_04.htm", player);
				}
				else if(reply == 1)
					showQuestPage("new_falsepriest_gremory_q0270_02.htm", player);
			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				if(reply == 2)
				{
					if(st.getQuestItemsCount(q_tatters_of_monk) < 1)
						showPage("new_falsepriest_gremory_q0270_06.htm", player);
					else if(st.getQuestItemsCount(q_tatters_of_monk) < 100)
						showPage("new_falsepriest_gremory_q0270_07.htm", player);
					else if(st.getQuestItemsCount(q_tatters_of_monk) >= 100)
						showPage("new_falsepriest_gremory_q0270_08.htm", player);
				}
				else if(reply >= 11 && reply <= 15)
				{
					if(reply == 11 && st.getQuestItemsCount(q_tatters_of_monk) >= 100)
					{
						if(Rnd.chance(5))
						{
							if(Rnd.get(1000) < 438)
								st.giveItems(10373 + Rnd.get(9), 1);
							else
								st.giveItems(10397 + Rnd.get(9), 1);
						}

						int i1 = Rnd.get(100);
						if(i1 < 1)
							st.giveItems(5593, 1);
						else if(i1 < 28)
							st.giveItems(5594, 1);
						else if(i1 < 61)
							st.giveItems(5595, 1);
						else
							st.giveItems(9898, 1);

						st.playSound(SOUND_MIDDLE);
						st.takeItems(q_tatters_of_monk, 100);
						showPage("new_falsepriest_gremory_q0270_09.htm", player);
					}
					else if (reply == 11 && st.getQuestItemsCount(q_tatters_of_monk) < 100)
						showPage("new_falsepriest_gremory_q0270_10.htm", player);
					else if (reply == 12 && st.getQuestItemsCount(q_tatters_of_monk) >= 200)
					{
						if ( Rnd.get(1000) < 549 )
							st.giveItems(10373 + Rnd.get(9), 1);
						else 
							st.giveItems(10397 + Rnd.get(9), 1);

						int i1 = Rnd.get(100);
						if ( i1 < 20 )
							st.giveItems(5593, 1);
						else if ( i1 < 40 )
							st.giveItems(5594, 1);
						else if ( i1 < 70 )
							st.giveItems(5595, 1);
						else 
							st.giveItems(9898, 1);

						st.playSound(SOUND_MIDDLE);
						st.takeItems(q_tatters_of_monk, 200);
						showPage("new_falsepriest_gremory_q0270_09.htm", player);
					}
					else if (reply == 12 && st.getQuestItemsCount(q_tatters_of_monk) < 200)
						showPage("new_falsepriest_gremory_q0270_10.htm", player);
					else if (reply == 13 && st.getQuestItemsCount(q_tatters_of_monk) >= 300)
					{
						st.giveItems(10373 + Rnd.get(9), 1);
						st.giveItems(10397 + Rnd.get(9), 1);

						int i2 = Rnd.get(1000);
						if ( i2 < 242 )
							st.giveItems(5593, 1);
						else if ( i2 < 486 )
							st.giveItems(5594, 1);
						else if ( i2 < 742 )
							st.giveItems(5595, 1);
						else
							st.giveItems(9898, 1);

						st.takeItems(q_tatters_of_monk, 300);
						showPage("new_falsepriest_gremory_q0270_09.htm", player);
					}
					else if (reply == 13 && st.getQuestItemsCount(q_tatters_of_monk) < 300)
						showPage("new_falsepriest_gremory_q0270_10.htm", player);
					else if(reply == 14 && st.getQuestItemsCount(q_tatters_of_monk) >= 400)
					{
						st.giveItems(10373 + Rnd.get(9), 1);
						st.giveItems(10397 + Rnd.get(9), 1);

						int i2 = Rnd.get(1000);
						if(i2 < 242)
							st.giveItems(5593, 1);
						else if(i2 < 486)
							st.giveItems(5594, 1);
						else if(i2 < 742)
							st.giveItems(5595, 1);
						else
							st.giveItems(9898, 1);

						if(Rnd.chance(5))
						{
							if(Rnd.get(1000) < 438)
								st.giveItems(10373 + Rnd.get(9), 1);
							else
								st.giveItems(10397 + Rnd.get(9), 1);
						}
						int i1 = Rnd.get(100);
						if(i1 < 1)
							st.giveItems(5593, 1);
						else if(i1 < 28)
							st.giveItems(5594, 1);
						else if(i1 < 61)
							st.giveItems(5595, 1);
						else
							st.giveItems(9898, 1);

						st.takeItems(q_tatters_of_monk, 400);
						showPage("new_falsepriest_gremory_q0270_09.htm", player);
					}
					else if (reply == 14 && st.getQuestItemsCount(q_tatters_of_monk) < 400)
						showPage("new_falsepriest_gremory_q0270_10.htm", player);
					else if(reply == 15 && st.getQuestItemsCount(q_tatters_of_monk) >= 500)
					{
						st.giveItems(10373 + Rnd.get(9), 1);
						st.giveItems(10397 + Rnd.get(9), 1);
						int i2 = Rnd.get(1000);
						if(i2 < 242)
							st.giveItems(5593, 1);
						else if(i2 < 486)
							st.giveItems(5594, 1);
						else if(i2 < 742)
							st.giveItems(5595, 1);
						else
							st.giveItems(9898, 1);

						if(Rnd.get(1000) < 549)
							st.giveItems(10373 + Rnd.get(9), 1);
						else
							st.giveItems(10397 + Rnd.get(9), 1);

						int i1 = Rnd.get(100);
						if(i1 < 20)
							st.giveItems(5593, 1);
						else if(i1 < 40)
							st.giveItems(5594, 1);
						else if(i1 < 70)
							st.giveItems(5595, 1);
						else
							st.giveItems(9898, 1);

						st.takeItems(q_tatters_of_monk, 500);
						showPage("new_falsepriest_gremory_q0270_09.htm", player);
					}
					else if (reply == 15 && st.getQuestItemsCount(q_tatters_of_monk) < 500)
						showPage("new_falsepriest_gremory_q0270_10.htm", player);
				}
				else if ( reply == 6 )
				{
					if ( st.haveQuestItems(q_tatters_of_monk))
						showPage("new_falsepriest_gremory_q0270_12.htm", player);
					else
					{
						st.takeItems(q_tatters_of_monk, -1);
						st.exitCurrentQuest(true);
						st.playSound(SOUND_FINISH);
						showPage("new_falsepriest_gremory_q0270_13.htm", player);
					}
				}
				else if ( reply == 7 )
				{
					st.takeItems(q_tatters_of_monk, -1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("new_falsepriest_gremory_q0270_13.htm", player);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:pinaps_q0251_03.htm";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == new_falsepriest_gremory)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82 && st.getPlayer().isQuestComplete(10288))
					return "new_falsepriest_gremory_q0270_01.htm";

				st.exitCurrentQuest(true);
				return "new_falsepriest_gremory_q0270_03.htm";
			}
			if(st.isStarted() && cond == 1)
				return "npchtm:new_falsepriest_gremory_q0270_05.htm";
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(_mobs.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithMemoState(killer, 1);
			if(qs != null && qs.rollAndGive(q_tatters_of_monk, 1, _mobs.get(npc.getNpcId())))
				qs.playSound(SOUND_ITEMGET);
		}
	}
}
