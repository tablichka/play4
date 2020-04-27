package quests._464_Oath;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author: rage
 * @date: 14.10.11 20:20
 */
public class _464_Oath extends Quest implements IItemHandler, ScriptFile
{
	// NPC
	private static final int director_sophia = 32596;
	private static final int blacksmith_buryun = 31960;
	private static final int cardinal_seresin = 30657;
	private static final int chichirin = 30539;
	private static final int falsepriest_dominic = 31350;
	private static final int gatekeeper_flauen = 30899;
	private static final int master_tobias = 30297;
	private static final int saint_agnes = 31588;
	private static final int trader_holly = 30839;

	// Items
	private static final int strongbox_of_promise = 15537;
	private static final int q_book_of_silence_1 = 15538;
	private static final int q_book_of_silence_2 = 15539;
	private final static int[] _itemIds = {strongbox_of_promise, q_book_of_silence_1, q_book_of_silence_2};

	// Mobs
	private static final HashMap<Integer, Double> dropChance = new HashMap<>(12);
	static
	{
		dropChance.put(22789, 0.05);
		dropChance.put(22790, 0.05);
		dropChance.put(22791, 0.04);
		dropChance.put(22792, 0.04);
		dropChance.put(22793, 0.05);
		dropChance.put(22794, 0.06);
		dropChance.put(22795, 0.08);
		dropChance.put(22796, 0.09);
		dropChance.put(22797, 0.07);
		dropChance.put(22798, 0.09);
		dropChance.put(22799, 0.09);
		dropChance.put(22800, 0.10);
	}

	public _464_Oath()
	{
		super(464, "_464_Oath", "Oath");
		addTalkId(director_sophia, blacksmith_buryun, cardinal_seresin, chichirin, falsepriest_dominic, gatekeeper_flauen, master_tobias);
		addTalkId(saint_agnes, trader_holly);
		for(int npcId : dropChance.keySet())
			addKillId(npcId);
		addQuestItem(q_book_of_silence_1, q_book_of_silence_2);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(npc.getNpcId() == director_sophia)
		{
			if(st.isStarted())
			{
				if(st.getQuestItemsCount(q_book_of_silence_1) >= 1)
					return "npchtm:director_sophia_q0464_01.htm";
				if(st.getMemoState() == 2)
				{
					if(st.getInt("ex_1") == 1)
						return "npchtm:director_sophia_q0464_05.htm";
					if(st.getInt("ex_1") == 2)
						return "npchtm:director_sophia_q0464_05a.htm";
					if(st.getInt("ex_1") == 3)
						return "npchtm:director_sophia_q0464_05b.htm";
					if(st.getInt("ex_1") == 4)
						return "npchtm:director_sophia_q0464_05c.htm";
					if(st.getInt("ex_1") == 5)
						return "npchtm:director_sophia_q0464_05d.htm";
					if(st.getInt("ex_1") == 6)
						return "npchtm:director_sophia_q0464_05e.htm";
					if(st.getInt("ex_1") == 7)
						return "npchtm:director_sophia_q0464_05f.htm";
					if(st.getInt("ex_1") == 8)
						return "npchtm:director_sophia_q0464_05g.htm";
				}
			}
		}
		else if(npc.getNpcId() == cardinal_seresin)
		{
			if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 1)
				return "npchtm:cardinal_seresin_q0464_01.htm";
		}
		else if(npc.getNpcId() == trader_holly)
		{
			if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 2)
				return "npchtm:trader_holly_q0464_01.htm";
		}
		else if(npc.getNpcId() == gatekeeper_flauen)
		{
			if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 3)
				return "npchtm:gatekeeper_flauen_q0464_01.htm";
		}
		else if(npc.getNpcId() == falsepriest_dominic)
		{
			if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 4)
				return "npchtm:falsepriest_dominic_q0464_01.htm";
		}
		else if(npc.getNpcId() == chichirin)
		{
			if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 5)
				return "npchtm:chichirin_q0464_01.htm";
		}
		else if(npc.getNpcId() == master_tobias)
		{
			if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 6)
				return "npchtm:master_tobias_q0464_01.htm";
		}
		else if(npc.getNpcId() == blacksmith_buryun)
		{
			if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 7)
				return "npchtm:blacksmith_buryun_q0464_01.htm";
		}
		else if(npc.getNpcId() == saint_agnes)
		{
			if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 8)
				return "npchtm:saint_agnes_q0464_01.htm";
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == director_sophia)
		{
			if( reply == 1 )
			{
				if( st.isStarted() && st.getQuestItemsCount(q_book_of_silence_1) >= 1 )
				{
					showPage("director_sophia_q0464_02.htm", talker);
				}
			}
			else if( reply == 2 )
			{
				if( st.isStarted() && st.getQuestItemsCount(q_book_of_silence_1) >= 1 )
				{
					showPage("director_sophia_q0464_03.htm", talker);
				}
			}
			if( reply == 3 )
			{
				if( st.isStarted() && st.getQuestItemsCount(q_book_of_silence_1) >= 1 )
				{
					int i0 = Rnd.get(8);
					if( i0 == 0 )
					{
						showPage("director_sophia_q0464_04.htm", talker);
					}
					else if( i0 == 1 )
					{
						showPage("director_sophia_q0464_04a.htm", talker);
					}
					else if( i0 == 2 )
					{
						showPage("director_sophia_q0464_04b.htm", talker);
					}
					else if( i0 == 3 )
					{
						showPage("director_sophia_q0464_04c.htm", talker);
					}
					else if( i0 == 4 )
					{
						showPage("director_sophia_q0464_04d.htm", talker);
					}
					else if( i0 == 5 )
					{
						showPage("director_sophia_q0464_04e.htm", talker);
					}
					else if( i0 == 6 )
					{
						showPage("director_sophia_q0464_04f.htm", talker);
					}
					else if( i0 == 7 )
					{
						showPage("director_sophia_q0464_04g.htm", talker);
					}

					st.giveItems(q_book_of_silence_2, 1);
					st.takeItems(q_book_of_silence_1, -1);
					st.setMemoState(2);
					st.set("ex_1", i0 + 1);
					st.setCond(i0 + 2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == cardinal_seresin)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 1)
				{
					st.rollAndGive(57, 42910, 100);
					st.addExpAndSp(15449, 17696);
					st.takeItems(q_book_of_silence_2, -1);
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					showPage("cardinal_seresin_q0464_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == trader_holly)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 2)
				{
					st.rollAndGive(57, 52599, 100);
					st.addExpAndSp(189377, 21692);
					st.takeItems(q_book_of_silence_2, -1);
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					showPage("trader_holly_q0464_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == gatekeeper_flauen)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 3)
				{
					st.rollAndGive(57, 69210, 100);
					st.addExpAndSp(249180, 28542);
					st.takeItems(q_book_of_silence_2, -1);
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					showPage("gatekeeper_flauen_q0464_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == falsepriest_dominic)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 4)
				{
					st.rollAndGive(57, 69210, 100);
					st.addExpAndSp(249180, 28542);
					st.takeItems(q_book_of_silence_2, -1);
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					showPage("falsepriest_dominic_q0464_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == chichirin)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 5)
				{
					st.rollAndGive(57, 169442, 100);
					st.addExpAndSp(19408, 47062);
					st.takeItems(q_book_of_silence_2, -1);
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					showPage("chichirin_q0464_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == master_tobias)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 6)
				{
					st.rollAndGive(57, 210806, 100);
					st.addExpAndSp(24146, 58551);
					st.takeItems(q_book_of_silence_2, -1);
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					showPage("master_tobias_q0464_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == blacksmith_buryun)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 7)
				{
					st.rollAndGive(57, 42910, 100);
					st.addExpAndSp(15449, 17696);
					st.takeItems(q_book_of_silence_2, -1);
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					showPage("blacksmith_buryun_q0464_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == saint_agnes)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(q_book_of_silence_2) >= 1 && st.getInt("ex_1") == 8)
				{
					st.rollAndGive(57, 42910, 100);
					st.addExpAndSp(15449, 17696);
					st.takeItems(q_book_of_silence_2, -1);
					st.exitCurrentQuest(false, true);
					st.playSound(SOUND_FINISH);
					showPage("saint_agnes_q0464_02.htm", talker);
				}
			}
		}
	}

	@Override
	public void onQuestStart(L2Player talker)
	{
		if(talker.getItemCountByItemId(strongbox_of_promise) < 1)
		{
			showQuestPage("strongbox_of_promise_q0464_01.htm", talker);
			return;
		}

		if(talker.getLevel() < 82)
		{
			showQuestPage("strongbox_of_promise_q0464_02.htm", talker);
			return;
		}

		QuestState st = newQuestState(talker);
		st.setMemoState(1);
		st.setCond(1);
		st.playSound(SOUND_ACCEPT);
		st.setState(STARTED);
		st.giveItems(q_book_of_silence_1, 1);
		st.takeItems(strongbox_of_promise, -1);
		showQuestPage("strongbox_of_promise_q0464_03.htm", talker);
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChance.containsKey(npc.getNpcId()) && killer.getItemCountByItemId(strongbox_of_promise) == 0 && !killer.isQuestStarted(464) && !killer.isQuestComplete(464) && Rnd.chance(dropChance.get(npc.getNpcId())))
		{
			npc.dropItem(killer, strongbox_of_promise, 1);
		}
	}

	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable.isPlayer())
		{
			if(item.getItemId() == strongbox_of_promise)
				showQuestPage("strongbox_of_promise001.htm", playable.getPlayer());
			else if(item.getItemId() == q_book_of_silence_1)
				showPage("q_book_of_silence_1001.htm", playable.getPlayer());
			else if(item.getItemId() == q_book_of_silence_2)
				showPage("q_book_of_silence_2001.htm", playable.getPlayer());
		}
		return true;
	}

	@Override
	public final int[] getItemIds()
	{
		return _itemIds;
	}

	@Override
	public void onLoad()
	{
		super.onLoad();
		ItemHandler.getInstance().registerItemHandler(this);
	}

}
