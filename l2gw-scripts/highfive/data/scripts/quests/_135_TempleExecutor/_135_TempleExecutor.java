package quests._135_TempleExecutor;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _135_TempleExecutor extends Quest
{
	//NPC
	private static final int SHEGFIELD = 30068;
	private static final int ALANKELL = 30291;
	private static final int SONIN = 31773;
	private static final int PANO = 30078;
	// Item
	private static final int ADENA = 57;
	private static final int CARGO = 10328;
	private static final int CRYSTAL = 10329;
	private static final int MAP = 10330;
	private static final int SONIN_CR = 10331;
	private static final int PANO_CR = 10332;
	private static final int ALEX_CR = 10333;
	private static final int BADGE = 10334;

	private static final int CURSED_OBSERVER = 21106;
	private static final int DELU_LIZARDMAN_AGENT = 21105;
	private static final int DELU_LIZARDMAN_COMMANDER = 21107;
	private static final int DELU_LIZARDMAN_Q_MASTER = 21104;
	private static final int DELU_LIZARDMAN_SHAMAN = 20781;


	public _135_TempleExecutor()
	{
		super(135, "_135_TempleExecutor", "TempleExecutor");
		addStartNpc(SHEGFIELD);
		addTalkId(ALANKELL, SONIN, PANO);
		addKillId(CURSED_OBSERVER, DELU_LIZARDMAN_AGENT, DELU_LIZARDMAN_SHAMAN, DELU_LIZARDMAN_Q_MASTER, DELU_LIZARDMAN_COMMANDER);
		addQuestItem(CARGO, CRYSTAL, MAP, SONIN_CR, ALEX_CR, PANO_CR);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		if(event.equalsIgnoreCase("accept"))
		{
			if(st.isCreated() && st.getPlayer().getLevel() >= 35)
			{
				st.setCond(1);
				st.setMemoState(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				htmltext = "shegfield_q0135_03.htm";
			}
		}
		else if(event.equalsIgnoreCase("shegfield_1"))
		{
			if(st.isStarted() && st.getMemoState() == 1)
			{
				st.setCond(2);
				st.setMemoState(2);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				htmltext = "npchtm:shegfield_q0135_05.htm";
			}
		}
		else if(event.equalsIgnoreCase("shegfield_2"))
		{
			if(st.isStarted() && st.getMemoState() == 7)
			{
				htmltext = "npchtm:shegfield_q0135_10.htm";
			}
		}
		else if(event.equalsIgnoreCase("shegfield_3"))
		{
			if(st.isStarted() && st.getMemoState() == 7)
			{
				htmltext = "npchtm:shegfield_q0135_11.htm";
			}
		}
		else if(event.equalsIgnoreCase("shegfield_4"))
		{
			if(st.isStarted() && st.getMemoState() == 7)
			{
				htmltext = "npchtm:shegfield_q0135_12.htm";
			}
		}
		else if(event.equalsIgnoreCase("shegfield_5"))
		{
			if(st.isStarted() && st.getMemoState() == 7)
			{
				st.giveItems(BADGE, 1);
				if(st.getPlayer().getLevel() < 41)
					st.addExpAndSp(30000, 2000);

				st.rollAndGive(ADENA, 10000 + 3234 + 3690, 100);
				st.playSound(SOUND_FINISH);
				htmltext = "npchtm:shegfield_q0135_13.htm";
				st.exitCurrentQuest(false);
			}
		}

		else if(event.equalsIgnoreCase("alankell_1"))
		{
			if(st.isStarted() && st.getMemoState() == 3)
			{
				htmltext = "npchtm:alankell_q0135_02a.htm";
			}
		}
		else if(event.equalsIgnoreCase("alankell_2"))
		{
			if(st.isStarted() && st.getMemoState() == 3)
			{
				htmltext = "npchtm:alankell_q0135_04.htm";
			}
		}
		else if(event.equalsIgnoreCase("alankell_3"))
		{
			if(st.isStarted() && st.getMemoState() == 3)
			{
				htmltext = "npchtm:alankell_q0135_05.htm";
			}
		}
		else if(event.equalsIgnoreCase("alankell_4"))
		{
			if(st.isStarted() && st.getMemoState() == 3)
			{
				htmltext = "npchtm:alankell_q0135_06.htm";
			}
		}
		else if(event.equalsIgnoreCase("alankell_5"))
		{
			if(st.isStarted() && st.getMemoState() == 3)
			{
				htmltext = "npchtm:alankell_q0135_07.htm";
				st.setMemoState(4);
				st.setCond(3);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
		}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == SHEGFIELD)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 35)
					htmltext = "shegfield_q0135_01.htm";
				else
				{
					htmltext = "shegfield_q0135_02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				st.setCond(2);
				st.setMemoState(2);
				st.set("MemoStateEx1", 0);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				htmltext = "npchtm:shegfield_q0135_04.htm";
			}
			else if(st.isStarted() && st.getMemoState() >= 2 && cond < 5 && (st.getQuestItemsCount(SONIN_CR) < 1 || st.getQuestItemsCount(PANO_CR) < 1 || st.getQuestItemsCount(ALEX_CR) < 1))
			{

				htmltext = "npchtm:shegfield_q0135_06.htm";
			}
			else if(st.isStarted() && st.getMemoState() >= 5 && (st.getQuestItemsCount(SONIN_CR) < 1 || st.getQuestItemsCount(PANO_CR) < 1 || st.getQuestItemsCount(ALEX_CR) < 1))
			{
				htmltext = "npchtm:shegfield_q0135_07.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 6 && st.getQuestItemsCount(SONIN_CR) >= 1 && st.getQuestItemsCount(PANO_CR) >= 1 && st.getQuestItemsCount(ALEX_CR) >= 1)
			{
				st.takeItems(SONIN_CR, 1);
				st.takeItems(ALEX_CR, 1);
				st.takeItems(PANO_CR, 1);
				st.setMemoState(7);
				htmltext = "npchtm:shegfield_q0135_08.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 7)
			{
				htmltext = "npchtm:shegfield_q0135_09.htm";
			}
		}
		else if(npcId == ALANKELL)
		{
			if(st.isStarted() && st.getMemoState() < 2)
				htmltext = "npchtm:alankell_q0135_01.htm";
			else if(st.isStarted() && st.getMemoState() == 2)
			{
				st.setMemoState(3);
				htmltext = "npchtm:alankell_q0135_02.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 3)
			{
				htmltext = "npchtm:alankell_q0135_03.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 4 && st.getQuestItemsCount(MAP) < 10)
			{
				htmltext = "npchtm:alankell_q0135_08.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 5 && st.getQuestItemsCount(MAP) >= 10 && (st.getQuestItemsCount(SONIN_CR) < 1 || st.getQuestItemsCount(PANO_CR) < 1))
			{
				htmltext = "npchtm:alankell_q0135_09.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 5 && st.getQuestItemsCount(MAP) >= 10 && (st.getQuestItemsCount(SONIN_CR) >= 1 && st.getQuestItemsCount(PANO_CR) >= 1) && st.getQuestItemsCount(ALEX_CR) < 1)
			{
				st.giveItems(ALEX_CR, 1);
				st.takeItems(MAP, -1);
				st.setCond(5);
				st.setMemoState(6);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);

				htmltext = "npchtm:alankell_q0135_10.htm";
			}
			else if(st.isStarted() && st.getMemoState() >= 6)
			{
				if(st.getQuestItemsCount(ALEX_CR) == 0)
				{
					st.giveItems(ALEX_CR, 1);
					st.playSound(SOUND_MIDDLE);
				}
				htmltext = "npchtm:alankell_q0135_11.htm";
			}

		}
		else if(npcId == SONIN)
		{
			if(st.isStarted() && st.getMemoState() < 2)
			{
				htmltext = "npchtm:warehouse_keeper_sonin_q0135_01.htm";
			}
			else if(st.isStarted() && (st.getMemoState() == 2 || st.getMemoState() == 3))
			{
				htmltext = "npchtm:warehouse_keeper_sonin_q0135_02.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 4 && st.getQuestItemsCount(CARGO) < 10 && st.getQuestItemsCount(SONIN_CR) < 1)
			{
				htmltext = "npchtm:warehouse_keeper_sonin_q0135_02a.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 5 && st.getQuestItemsCount(CARGO) >= 10 && st.getQuestItemsCount(SONIN_CR) < 1 && (st.getInt("MemoStateEx1") / 10) == 0)
			{
				st.giveItems(SONIN_CR, 1);
				st.playSound(SOUND_MIDDLE);
				int i0 = st.getInt("MemoStateEx1");
				st.set("MemoStateEx1", i0 + 10);
				st.takeItems(CARGO, -1);
				htmltext = "npchtm:warehouse_keeper_sonin_q0135_03.htm";
			}
			else if(st.isStarted() && st.getMemoState() >= 5 && (st.getInt("MemoStateEx1") / 10) >= 1)
			{
				if(st.getQuestItemsCount(SONIN_CR) == 0)
				{
					st.giveItems(SONIN_CR, 1);
					st.playSound(SOUND_MIDDLE);
				}

				htmltext = "npchtm:warehouse_keeper_sonin_q0135_04.htm";
			}
		}
		else if(npcId == PANO)
		{
			if(st.isStarted() && st.getMemoState() < 2)
			{
				htmltext = "npchtm:pano_q0135_01.htm";
			}
			else if(st.isStarted() && st.getMemoState() >= 2 && st.getMemoState() <= 3)
			{
				htmltext = "npchtm:pano_q0135_02.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 4 && st.getQuestItemsCount(CRYSTAL) < 10 && st.getQuestItemsCount(PANO_CR) < 1)
			{
				htmltext = "npchtm:pano_q0135_02a.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 5 && st.getQuestItemsCount(CRYSTAL) >= 10 && st.getQuestItemsCount(PANO_CR) < 1 && (st.getInt("MemoStateEx1") % 10) == 0)
			{
				st.giveItems(PANO_CR, 1);
				st.playSound(SOUND_MIDDLE);
				int i0 = st.getInt("MemoStateEx1");
				st.set("MemoStateEx1", i0 + 1);
				st.takeItems(CRYSTAL, -1);
				htmltext = "npchtm:pano_q0135_03.htm";
			}
			else if(st.isStarted() && st.getMemoState() >= 5 && (st.getInt("MemoStateEx1") % 10) >= 1)
			{
				if(st.getQuestItemsCount(PANO_CR) == 0)
				{
					st.giveItems(PANO_CR, 1);
					st.playSound(SOUND_MIDDLE);
				}

				htmltext = "npchtm:pano_q0135_04.htm";
			}

		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithMemoState(killer, 4);
		if(st == null)
			return;

		double baseChance = 0;
		int npcId = npc.getNpcId();

		switch(npcId)
		{
			case CURSED_OBSERVER:
				baseChance = 42.3;
				break;
			case DELU_LIZARDMAN_AGENT:
				baseChance = 50.4;
				break;
			case DELU_LIZARDMAN_SHAMAN:
			case DELU_LIZARDMAN_Q_MASTER:
				baseChance = 43.9;
				break;
			case DELU_LIZARDMAN_COMMANDER:
				baseChance = 90.2;
				break;
			default:
				baseChance = 0;
		}

		if(st.getQuestItemsCount(CARGO) < 10)
		{
			if(st.rollAndGiveLimited(CARGO, 1, baseChance, 10))
			{
				if(st.getQuestItemsCount(CARGO) == 10 && st.getQuestItemsCount(MAP) == 10 && st.getQuestItemsCount(CRYSTAL) == 10)
				{
					st.setMemoState(5);
					st.playSound(SOUND_MIDDLE);
					st.setCond(4);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(st.getQuestItemsCount(CRYSTAL) < 10)
		{
			if(st.rollAndGiveLimited(CRYSTAL, 1, baseChance, 10))
			{
				if(st.getQuestItemsCount(CARGO) == 10 && st.getQuestItemsCount(MAP) == 10 && st.getQuestItemsCount(CRYSTAL) == 10)
				{
					st.setMemoState(5);
					st.playSound(SOUND_MIDDLE);
					st.setCond(4);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(st.getQuestItemsCount(MAP) < 10)
		{
			if(st.rollAndGiveLimited(MAP, 1, baseChance, 10))
			{
				if(st.getQuestItemsCount(CARGO) == 10 && st.getQuestItemsCount(MAP) == 10 && st.getQuestItemsCount(CRYSTAL) == 10)
				{
					st.setMemoState(5);
					st.playSound(SOUND_MIDDLE);
					st.setCond(4);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
	}
}
