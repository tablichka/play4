package quests._005_MinersFavor;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

/**
 * One-time
 * Solo
 */
public class _005_MinersFavor extends Quest
{
	//NPC
	public final int BOLTER = 30554;
	public final int SHARI = 30517;
	public final int GARITA = 30518;
	public final int REED = 30520;
	public final int BRUNON = 30526;
	//QuestItem
	public final int BOLTERS_LIST = 1547;
	public final int MINING_BOOTS = 1548;
	public final int MINERS_PICK = 1549;
	public final int BOOMBOOM_POWDER = 1550;
	public final int REDSTONE_BEER = 1551;
	public final int BOLTERS_SMELLY_SOCKS = 1552;
	//Item
	public final int NECKLACE = 906;
	private final static int ADENA_ID = 57;

	public _005_MinersFavor()
	{
		super(5, "_005_MinersFavor", "Miner's Favor");

		addStartNpc(BOLTER);
		addTalkId(SHARI);
		addTalkId(GARITA);
		addTalkId(REED);
		addTalkId(BRUNON);

		addQuestItem(BOLTERS_LIST, BOLTERS_SMELLY_SOCKS, MINING_BOOTS, MINERS_PICK, BOOMBOOM_POWDER, REDSTONE_BEER);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("miner_bolter_q0005_03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(BOLTERS_LIST, 1);
			st.giveItems(BOLTERS_SMELLY_SOCKS, 1);
		}
		else if(event.equalsIgnoreCase("blacksmith_bronp_q0005_02.htm"))
		{
			st.takeItems(BOLTERS_SMELLY_SOCKS, -1);
			st.giveItems(MINERS_PICK, 1);
			if(st.getQuestItemsCount(BOLTERS_LIST) > 0 && st.getQuestItemsCount(MINING_BOOTS) + st.getQuestItemsCount(MINERS_PICK) + st.getQuestItemsCount(BOOMBOOM_POWDER) + st.getQuestItemsCount(REDSTONE_BEER) == 4)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);

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
		if(npcId == BOLTER)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 2)
					htmltext = "miner_bolter_q0005_02.htm";
				else
				{
					htmltext = "miner_bolter_q0005_01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "miner_bolter_q0005_04.htm";
			else if(cond == 2 && st.getQuestItemsCount(MINING_BOOTS) + st.getQuestItemsCount(MINERS_PICK) + st.getQuestItemsCount(BOOMBOOM_POWDER) + st.getQuestItemsCount(REDSTONE_BEER) == 4)
			{
				htmltext = "miner_bolter_q0005_06.htm";
				st.takeItems(MINING_BOOTS, -1);
				st.takeItems(MINERS_PICK, -1);
				st.takeItems(BOOMBOOM_POWDER, -1);
				st.takeItems(REDSTONE_BEER, -1);
				st.takeItems(BOLTERS_LIST, -1);
				st.giveItems(NECKLACE, 1);
				st.getPlayer().addExpAndSp(5672, 446);
				st.rollAndGive(ADENA_ID, 2466, 100);
				if(st.getPlayer().getVarInt("NR41") % 10 == 0)
				{
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4151", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
				}
				st.unset("cond");
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
		else if(cond == 1 && st.getQuestItemsCount(BOLTERS_LIST) > 0)
		{
			if(npcId == SHARI)
			{
				if(st.getQuestItemsCount(BOOMBOOM_POWDER) == 0)
				{
					htmltext = "trader_chali_q0005_01.htm";
					st.giveItems(BOOMBOOM_POWDER, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else
					htmltext = "trader_chali_q0005_02.htm";
			}
			else if(npcId == GARITA)
			{
				if(st.getQuestItemsCount(MINING_BOOTS) == 0)
				{
					htmltext = "trader_garita_q0005_01.htm";
					st.giveItems(MINING_BOOTS, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else
					htmltext = "trader_garita_q0005_02.htm";
			}
			else if(npcId == REED)
			{
				if(st.getQuestItemsCount(REDSTONE_BEER) == 0)
				{
					htmltext = "warehouse_chief_reed_q0005_01.htm";
					st.giveItems(REDSTONE_BEER, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else
					htmltext = "warehouse_chief_reed_q0005_02.htm";
			}
			else if(npcId == BRUNON && st.getQuestItemsCount(BOLTERS_SMELLY_SOCKS) > 0)
				if(st.getQuestItemsCount(MINERS_PICK) == 0)
					htmltext = "blacksmith_bronp_q0005_01.htm";
				else
					htmltext = "blacksmith_bronp_q0005_03.htm";
			if(st.getQuestItemsCount(BOLTERS_LIST) > 0 && st.getQuestItemsCount(MINING_BOOTS) + st.getQuestItemsCount(MINERS_PICK) + st.getQuestItemsCount(BOOMBOOM_POWDER) + st.getQuestItemsCount(REDSTONE_BEER) == 4)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
		}
		return htmltext;
	}
}
