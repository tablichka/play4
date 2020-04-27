package quests._198_SevenSignsEmbryo;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExStartScenePlayer;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 22.11.2010 21:59:15
 */
public class _198_SevenSignsEmbryo extends Quest
{
	// NPCs
	private static final int WOOD = 32593;
	private static final int FRANZ = 32597;
	private static final int JEINA = 32617;
	// Items
	private static final int SCULPTURE = 14355;

	private static final L2Skill heal = SkillTable.getInstance().getInfo(4065, 8);

	public _198_SevenSignsEmbryo()
	{
		super(198, "_198_SevenSignsEmbryo", "Seven Signs, Embryo");
		addStartNpc(WOOD);
		addTalkId(WOOD, FRANZ, JEINA);
		addKillId(27346);
		addQuestItem(SCULPTURE);
	}

	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(event.equals("priest_wood_q0198_04.htm"))
		{
			if(player.getLevel() >= 79 && player.isQuestComplete(197))
			{
				st.setCond(1);
				st.playSound(SOUND_ACCEPT);
				st.setState(STARTED);
				return event;
			}
		}
		else if(event.equals("priest_wood_q0198_06.htm"))
		{
			if(st.getCond() >= 1 && st.getCond() < 3)
			{
				if(InstanceManager.enterInstance(113, player, player.getLastNpc(), 198))
					return "npchtm:priest_wood_q0198_06.htm";
			}
		}
		else if(event.equals("inzone_frantz_q0198_02.htm") || event.equals("inzone_frantz_q0198_03.htm") || event.equals("inzone_frantz_q0198_04.htm"))
		{
			if(st.getCond() == 1)
				return "npchtm:" + event;
		}
		else if(event.equals("inzone_frantz_q0198_05.htm"))
		{
			L2NpcInstance franz = player.getLastNpc();
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(franz.c_ai0);
			if(c0 != null && !c0.isDead() && !c0.isDecayed())
				return "npchtm:inzone_frantz_q0198_05a.htm";

			if(st.getCond() == 1)
			{
				Functions.npcSay(franz, Say2C.ALL, 1800845, player.getName());
				L2NpcInstance evil = addSpawn(27346, new Location(-23734, -9184, -5384), false, 300000);
				franz.c_ai0 = evil.getStoredId();
				evil.setReflection(franz.getReflection());
				evil.addDamageHate(st.getPlayer(), 0, 2000);
				evil.setRunning();
				evil.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer());
				evil.i_ai0 = player.getObjectId();
				evil.c_ai0 = franz.getStoredId();
				L2NpcInstance mob = addSpawn(27399, new Location(-23734, -9184, -5384), true, 300000);
				evil.c_ai1 = mob.getStoredId();
				mob.setReflection(evil.getReflection());
				mob.addDamageHate(st.getPlayer(), 0, 2000);
				mob.setRunning();
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer());
				mob = addSpawn(27402, new Location(-23734, -9184, -5384), true, 300000);
				evil.c_ai2 = mob.getStoredId();
				mob.setReflection(evil.getReflection());
				mob.addDamageHate(st.getPlayer(), 0, 2000);
				mob.setRunning();
				mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer());
				startQuestTimer("t9999_" + franz.getReflection(), 30000 - Rnd.get(20000), franz, st.getPlayer(), true);
				return "npchtm:inzone_frantz_q0198_05.htm";
			}
		}
		else if(event.equals("inzone_frantz_q0198_09.htm") || event.equals("inzone_frantz_q0198_10.htm") || event.equals("inzone_frantz_q0198_11.htm"))
		{
			if(st.getCond() == 2 && st.haveQuestItems(SCULPTURE))
				return "npchtm:" + event;
		}
		else if(event.equals("inzone_frantz_q0198_12.htm"))
		{
			if(st.getCond() == 2 && st.haveQuestItems(SCULPTURE))
			{
				st.takeItems(SCULPTURE, -1);
				st.setCond(3);
				st.playSound(SOUND_MIDDLE);
				Functions.npcSay(player.getLastNpc(), Say2C.ALL, 19805);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("priest_jeina_q0198_01.htm") || event.equals("priest_jeina_q0198_02a.htm"))
		{
			if(st.getCond() >= 1)
				return "npchtm:" + event;
		}
		else if(event.equals("priest_jeina_q0198_02.htm"))
		{
			if(st.getCond() >= 1)
			{
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null)
				{
					player.teleToClosestTown();
					inst.stopInstance();
					return "npchtm:" + event;
				}
			}
		}

		return null;
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if(npc == null)
			return null;
		L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(npc.c_ai0);
		if(event.startsWith("t9999_") && !npc.isDecayed() && c0 != null && !c0.isDead())
		{
			if(npc.isInRange(c0, 600))
				npc.altUseSkill(heal, c0);
			else if(player != null)
				Functions.npcSay(npc, Say2C.ALL, 1800846, player.getName());

			startQuestTimer("t9999_" + npc.getReflection(), 30000 - Rnd.get(20000), npc, player, true);
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
			if(st.isCompleted())
				return "npchtm:completed";
			else if(st.isCreated())
			{
				if(player.getLevel() >= 79 && player.isQuestComplete(197))
					return "priest_wood_q0198_03.htm";

				st.exitCurrentQuest(true);
				return "priest_wood_q0198_02.htm";
			}
			else if(st.isStarted())
			{
				if(cond >= 1 && cond < 3)
					return "npchtm:priest_wood_q0198_05.htm";
				else if(cond == 3)
				{
					if(player.getLevel() >= 79)
					{
						st.addExpAndSp(315108090, 34906059);
						st.rollAndGive(5575, 1500000, 100);
						if(!player.isQuestComplete(199))
							st.giveItems(15312, 1);
						st.playSound(SOUND_FINISH);
						st.exitCurrentQuest(false);
						return "npchtm:priest_wood_q0198_07.htm";
					}
					return "npchtm:level_check_q0192_01.htm";
				}
			}
		}
		else if(npcId == FRANZ)
		{
			if(cond == 1)
				return "npchtm:inzone_frantz_q0198_01.htm";
			if(cond == 2)
				return "npchtm:inzone_frantz_q0198_08.htm";
			if(cond == 3)
				return "npchtm:inzone_frantz_q0198_13.htm";
		}

		return "npchtm:noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState qs)
	{
		if(npc.getNpcId() == 27346 && npc.i_ai0 == qs.getPlayer().getObjectId() && qs.getCond() == 1)
		{
			qs.giveItems(SCULPTURE, 1);
			qs.playSound(SOUND_MIDDLE);
			qs.setCond(2);
			qs.setState(STARTED);
			qs.getPlayer().showQuestMovie(ExStartScenePlayer.SCENE_SSQ_EMBRYO);
			Functions.npcSay(npc, Say2C.ALL, 19306, qs.getPlayer().getName());
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(npc.c_ai0);
			if(c0 != null)
			{
				Functions.npcSay(c0, Say2C.ALL, 1800847, qs.getPlayer().getName());
				c0.c_ai0 = 0;
			}

			c0 = L2ObjectsStorage.getAsNpc(npc.c_ai1);
			if(c0 != null)
				c0.decayMe();
			c0 = L2ObjectsStorage.getAsNpc(npc.c_ai2);
			if(c0 != null)
				c0.decayMe();
		}
	}

	@Override
	public void onDespawned(L2NpcInstance npc)
	{
		if(!npc.isDecayed() && !npc.isDead() && npc.getNpcId() == 27346)
		{
			Functions.npcSay(npc, Say2C.ALL, 19305);
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(npc.c_ai0);
			if(c0 == null)
				return;
			c0.c_ai0 = 0;
			c0 = L2ObjectsStorage.getAsNpc(npc.c_ai1);
			if(c0 != null)
				c0.decayMe();
			c0 = L2ObjectsStorage.getAsNpc(npc.c_ai2);
			if(c0 != null)
				c0.decayMe();
		}
	}
}
