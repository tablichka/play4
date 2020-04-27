package quests._197_SevenSignstheSacredBookofSeal;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 22.11.2010 16:54:35
 */
public class _197_SevenSignstheSacredBookofSeal extends Quest
{
	// NPCs
	private static final int WOOD = 32593; 
	private static final int ORVEN = 30857;
	private static final int LEOPARD = 32594;
	private static final int LAWRENCE = 32595;
	private static final int SOFIA = 32596;
	// Items
	private static final int HAND_WRITTEN_TEXT = 13829;
	private static final int SCULPTURE = 14354;

	private static L2NpcInstance evil_npc = null;

	public _197_SevenSignstheSacredBookofSeal()
	{
		super(197, "_197_SevenSignstheSacredBookofSeal", "Seven Signs, the Sacred Book of Seal");

		addStartNpc(WOOD);
		addTalkId(WOOD, ORVEN, LEOPARD, LAWRENCE, SOFIA);

		addQuestItem(HAND_WRITTEN_TEXT, SCULPTURE);
		addKillId(27396);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(event.equals("priest_wood_q0197_04.htm"))
		{
			if(player.getLevel() >= 79 && player.isQuestComplete(196))
				return "npchtm:" + event;
		}
		else if(event.equals("priest_wood_q0197_05.htm"))
		{
			if(player.getLevel() >= 79 && player.isQuestComplete(196))
				return "npchtm:" + event;
		}
		else if(event.equals("priest_wood_q0197_06.htm"))
		{
			if(player.getLevel() >= 79 && player.isQuestComplete(196))
			{
				st.setCond(1);
				st.playSound(SOUND_ACCEPT);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("priest_wood_q0197_08.htm"))
		{
			if(st.getCond() == 6 && st.haveQuestItems(HAND_WRITTEN_TEXT) && st.haveQuestItems(SCULPTURE))
				return "npchtm:" + event;
		}
		else if(event.equals("priest_wood_q0197_09.htm"))
		{
			if(st.getCond() == 6 && st.haveQuestItems(HAND_WRITTEN_TEXT) && st.haveQuestItems(SCULPTURE))
			{
				if(player.getLevel() >= 79)
				{
					st.addExpAndSp(52518015, 5817677);
					st.takeItems(HAND_WRITTEN_TEXT, -1);
					st.takeItems(SCULPTURE, -1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					return "npchtm:" + event;
				}
				else
					return "npchtm:level_check_q0192_01.htm";
			}
		}
		else if(event.equals("highpriest_orven_q0197_02.htm") || event.equals("highpriest_orven_q0197_03.htm"))
		{
			if(st.getCond() == 1)
				return "npchtm:" + event;
		}
		else if(event.equals("highpriest_orven_q0197_04.htm"))
		{
			if(st.getCond() == 1)
			{
				st.setCond(2);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("ciper_officer_leopard_q0197_02.htm"))
		{
			if(st.getCond() == 2)
				return "npchtm:" + event;
		}
		else if(event.equals("ciper_officer_leopard_q0197_03.htm"))
		{
			if(st.getCond() == 2)
			{
				st.setCond(3);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}	
		}
		else if(event.equals("great_master_lawrence_q0197_02.htm") || event.equals("great_master_lawrence_q0197_03.htm"))
		{
			if(st.getCond() == 3)
				return "npchtm:" + event;
		}
		else if(event.equals("great_master_lawrence_q0197_04.htm"))
		{
			if(st.getCond() == 3)
			{
				if(evil_npc != null && !evil_npc.isDecayed())
					return "npchtm:great_master_lawrence_q0197_04a.htm";

				evil_npc = addSpawn(27396, new Location(152520, -57502, -3408), false, 300000);
				Functions.npcSay(evil_npc, Say2C.ALL, 19806);
				evil_npc.addDamageHate(st.getPlayer(), 0, 2000);
				evil_npc.setRunning();
				evil_npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer());
				evil_npc.i_ai0 = st.getPlayer().getObjectId();
				evil_npc.c_ai0 = st.getPlayer().getLastNpc().getStoredId();
				return "npchtm:" + event;
			}
		}
		else if(event.equals("great_master_lawrence_q0197_08.htm") || event.equals("great_master_lawrence_q0197_09.htm"))
		{
			if(st.getCond() == 4 && st.haveQuestItems(SCULPTURE))
				return "npchtm:" + event;
		}
		else if(event.equals("great_master_lawrence_q0197_10.htm"))
		{
			if(st.getCond() == 4 && st.haveQuestItems(SCULPTURE))
			{
				st.setCond(5);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("director_sophia_q0197_02.htm") || event.equals("director_sophia_q0197_03.htm"))
		{
			if(st.getCond() == 5 && st.haveQuestItems(SCULPTURE))
				return "npchtm:" + event;
		}
		else if(event.equals("director_sophia_q0197_04.htm"))
		{
			if(st.getCond() == 5 && st.haveQuestItems(SCULPTURE))
			{
				st.giveItems(HAND_WRITTEN_TEXT, 1);
				st.setCond(6);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}

		return null;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == WOOD)
		{
			if(st.isCreated())
			{
				if(player.getLevel() >= 79 && player.isQuestComplete(196))
					return "priest_wood_q0197_03.htm";
				st.exitCurrentQuest(true);
				return "priest_wood_q0197_02.htm";
			}
			else if(st.isCompleted())
				return "npchtm:completed";
			else if(cond >= 1 && cond < 6)
				return "npchtm:priest_wood_q0197_10.htm";
			else if(cond == 6)
			{
				if(st.haveQuestItems(HAND_WRITTEN_TEXT) && st.haveQuestItems(SCULPTURE))
					return "npchtm:priest_wood_q0197_07.htm";
			}
		}
		else if(npcId == ORVEN)
		{
			if(cond == 1)
				return "npchtm:highpriest_orven_q0197_01.htm";
			else if(cond >= 2)
				return "npchtm:highpriest_orven_q0197_05.htm";
		}
		else if(npcId == LEOPARD)
		{
			if(cond == 2)
				return "npchtm:ciper_officer_leopard_q0197_01.htm";
			else if(cond >= 3)
				return "npchtm:ciper_officer_leopard_q0197_04.htm";
		}
		else if(npcId == LAWRENCE)
		{
			if(cond == 3)
				return "npchtm:great_master_lawrence_q0197_01.htm";
			else if(cond == 4 && st.haveQuestItems(SCULPTURE))
				return "npchtm:great_master_lawrence_q0197_07.htm";
			else if(cond >= 5 && st.haveQuestItems(SCULPTURE))
				return "npchtm:great_master_lawrence_q0197_11.htm";
		}
		else if(npcId == SOFIA)
		{
			if(cond == 5 && st.haveQuestItems(SCULPTURE))
				return "npchtm:director_sophia_q0197_01.htm";
			else if(cond >= 6 && st.haveQuestItems(SCULPTURE) && st.haveQuestItems(HAND_WRITTEN_TEXT))
				return "npchtm:director_sophia_q0197_05.htm";
		}

		return "npchtm:noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState qs)
	{
		if(npc.getNpcId() == 27396 && npc.i_ai0 == qs.getPlayer().getObjectId() && qs.getCond() == 3)
		{
			qs.giveItems(SCULPTURE, 1);
			qs.setCond(4);
			qs.playSound(SOUND_MIDDLE);
			qs.setState(STARTED);
			Functions.npcSay(npc, Say2C.ALL, 19306, qs.getPlayer().getName());
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(npc.c_ai0);
			if(c0 != null)
				Functions.npcSay(c0, Say2C.ALL, 1800847, qs.getPlayer().getName());
			evil_npc = null;
		}
	}

	@Override
	public void onDespawned(L2NpcInstance npc)
	{
		if(!npc.isDecayed() && !npc.isDead())
		{
			Functions.npcSay(npc, Say2C.ALL, 19305);
			evil_npc = null;
		}
	}
}
