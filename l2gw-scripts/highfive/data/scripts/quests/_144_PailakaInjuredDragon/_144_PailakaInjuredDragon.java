package quests._144_PailakaInjuredDragon;

import javolution.util.FastList;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

import java.util.List;

/**
 * @author rage
 * @date 12.10.2010 18:23:18
 */
public class _144_PailakaInjuredDragon extends Quest
{
	// NPC
	private static final int KETRAOSHAMAN = 32499;
	private static final int KOSUPPORTER = 32502;
	private static final int KOIO = 32509;
	private static final int KOSUPPORTER2 = 32512;

	// MOBS
	private static final int[] Mobs = new int[] { 18635, 18636, 18638, 18639, 18640, 18641, 18642, 18644, 18645, 18646, 18648, 18649, 18650, 18652, 18653, 18654, 18655, 18656, 18657 };
	private static final int[] Antelopes = new int[] { 18637, 18643, 18647, 18651 };

	// BOSS
	private static final int LATANA = 18660;

	// ITEMS
	private static final int ScrollOfEscape = 736;
	private static final int SPEAR = 13052;
	private static final int ENCHSPEAR = 13053;
	private static final int LASTSPEAR = 13054;
	private static final int STAGE1 = 13056;
	private static final int STAGE2 = 13057;

	private static final int[] HERBS = { 8600, 8601, 8603, 8604 };
	private static final int[] ITEMDROP = { 13032, 13033 };

	// REWARDS
	private static final int PSHIRT = 13296;

	private static final int[][] BUFFS = { { 4357, 2 }, // Haste Lv2
			{ 4342, 2 }, // Wind Walk Lv2
			{ 4356, 3 }, // Empower Lv3
			{ 4355, 3 }, // Acumen Lv3
			{ 4351, 6 }, // Concentration Lv6
			{ 4345, 3 }, // Might Lv3
			{ 4358, 3 }, // Guidance Lv3
			{ 4359, 3 }, // Focus Lv3
			{ 4360, 3 }, // Death Wisper Lv3
			{ 4352, 2 }, // Berserker Spirit Lv2
			{ 4354, 4 }, // Vampiric Rage Lv4
			{ 4347, 6 } // Blessed Body Lv6
	};


	public _144_PailakaInjuredDragon()
	{
		super(144, "_144_PailakaInjuredDragon", "Pailaka Injured Dragon");

		addStartNpc(KETRAOSHAMAN);
		addFirstTalkId(KOSUPPORTER2);
		addTalkId(KOSUPPORTER, KOIO, KOSUPPORTER2);

		addAttackId(Mobs);
		addDecayId(Mobs);
		addKillId(Antelopes);
		addKillId(LATANA);
		addDecayId(LATANA);
		addKillId(Mobs);
		addQuestItem(STAGE1, STAGE2, SPEAR, ENCHSPEAR, LASTSPEAR, 13033, 13032);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();
		L2NpcInstance npc = player.getLastNpc();
		if(event.equalsIgnoreCase("Enter"))
		{
			if(st.getCond() == 1)
			{
				st.setCond(2);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}

			enterInstance(player);
			return "npchtm:32499-08.htm";
		}
		else if(event.startsWith("buff"))
		{
			int[] skill = BUFFS[Integer.parseInt(event.split("buff")[1])];
			if(st.getInt("s" + npc.getObjectId()) < 4)
			{
				makeBuff(npc, player, skill[0], skill[1]);
				st.set("s" + npc.getObjectId(), "" + (st.getInt("s" + npc.getObjectId()) + 1));
				return "npchtm:32509-06.htm";
			}
			if(st.getInt("s" + npc.getObjectId()) == 4)
			{
				makeBuff(npc, player, skill[0], skill[1]);
				st.set("s" + npc.getObjectId(), "5");
				return "npchtm:32509-05.htm";
			}
		}
		else if(event.equalsIgnoreCase("Support"))
		{
			if(st.getInt("s" + npc.getObjectId()) < 5)
				return "npchtm:32509-06.htm";

			return "npchtm:32509-04.htm";
		}
		else if(event.equalsIgnoreCase("32499-02.htm"))
		{
			if(player.getLevel() < 73)
			{
				st.exitCurrentQuest(true);
				return "32499-lowlvl.htm";
			}

			if(player.getLevel() > 77)
			{
				st.exitCurrentQuest(true);
				return "32499-highlvl.htm";
			}
		}
		else if(event.equalsIgnoreCase("32499-03.htm"))
			return "npchtm:" + event;
		else if(event.equalsIgnoreCase("32499-05.htm"))
		{
			if(player.getLevel() < 73)
			{
				st.exitCurrentQuest(true);
				return "32499-lowlvl.htm";
			}

			if(player.getLevel() > 77)
			{
				st.exitCurrentQuest(true);
				return "32499-highlvl.htm";
			}

			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32499-07.htm"))
			return "npchtm:" + event;
		else if(event.equalsIgnoreCase("32502-05.htm"))
		{
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			st.giveItems(SPEAR, 1);
		}
		else if(event.startsWith("32502-"))
			return "npchtm:" + event;
		else if(event.equalsIgnoreCase("32512-02.htm"))
		{
			if(st.getCond() == 4)
			{
				st.takeItems(SPEAR, 1);
				st.takeItems(ENCHSPEAR, 1);
				st.takeItems(LASTSPEAR, 1);

				st.giveItems(ScrollOfEscape, 1);
				st.giveItems(PSHIRT, 1);
				st.addExpAndSp(28000000, 2850000);
				st.setState(COMPLETED);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
				player.getVitality().addPoints(20000);
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null)
					inst.successEnd();
				return "npchtm:" + event;
			}
			else
				return "npchtm:32512-03.htm";
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == KETRAOSHAMAN)
		{
			if(st.isCreated())
				return "32499-01.htm";
			else if(st.isCompleted())
				return "32499-complited.htm";
			else if(cond == 1 || cond == 2 || cond == 3)
				htmltext = "32499-06.htm";
			else
				htmltext = "32499-07.htm";
		}
		else if(npcId == KOSUPPORTER)
		{
			if(cond == 1 || cond == 2)
				htmltext = "npchtm:32502-01.htm";
			else
				htmltext = "npchtm:32502-05.htm";
		}
		else if(npcId == KOIO)
		{
			if(st.getQuestItemsCount(SPEAR) > 0 && st.getQuestItemsCount(STAGE1) == 0)
				htmltext = "32509-01.htm";
			if(st.getQuestItemsCount(ENCHSPEAR) > 0 && st.getQuestItemsCount(STAGE2) == 0)
				htmltext = "32509-01.htm";
			if(st.getQuestItemsCount(SPEAR) == 0 && st.getQuestItemsCount(STAGE1) > 0)
				htmltext = "32509-07.htm";
			if(st.getQuestItemsCount(ENCHSPEAR) == 0 && st.getQuestItemsCount(STAGE2) > 0)
				htmltext = "32509-07.htm";
			if(st.getQuestItemsCount(SPEAR) == 0 && st.getQuestItemsCount(ENCHSPEAR) == 0)
				htmltext = "32509-07.htm";
			if(st.getQuestItemsCount(STAGE1) == 0 && st.getQuestItemsCount(STAGE2) == 0)
				htmltext = "32509-01.htm";
			if(st.getQuestItemsCount(SPEAR) > 0 && st.getQuestItemsCount(STAGE1) > 0)
			{
				st.takeItems(SPEAR, 1);
				st.takeItems(STAGE1, 1);
				st.giveItems(ENCHSPEAR, 1);
				htmltext = "32509-02.htm";
			}
			if(st.getQuestItemsCount(ENCHSPEAR) > 0 && st.getQuestItemsCount(STAGE2) > 0)
			{
				st.takeItems(ENCHSPEAR, 1);
				st.takeItems(STAGE2, 1);
				st.giveItems(LASTSPEAR, 1);
				htmltext = "32509-03.htm";
			}
			if(st.getQuestItemsCount(LASTSPEAR) > 0)
				htmltext = "32509-03.htm";
		}
		else if(npcId == KOSUPPORTER2)
		{
			if(cond == 4)
				htmltext = "32512-01.htm";
			else if(st.isCompleted())
				htmltext = "32512-03.htm";
		}
		return htmltext;
	}

	@Override
	public String onFirstTalk(L2NpcInstance npc, L2Player player)
	{
		String htmltext = "noquest";
		QuestState st = player.getQuestState(getName());
		if(st == null)
			return htmltext;

		if(st.getCond() == 4)
			return "npchtm:32512-01.htm";

		return "npchtm:32512-03.htm";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(contains(Antelopes, npc.getNpcId()))
		{
			if(Rnd.chance(50))
				npc.dropItem(killer, HERBS[Rnd.get(HERBS.length)], 1);
			if(Rnd.chance(50))
				npc.dropItem(killer, HERBS[Rnd.get(HERBS.length)], 1);
			if(Rnd.chance(40))
				npc.dropItem(killer, ITEMDROP[Rnd.get(ITEMDROP.length)], Rnd.get(1, 10));
			if(Rnd.chance(40))
				npc.dropItem(killer, ITEMDROP[Rnd.get(ITEMDROP.length)], Rnd.get(1, 10));
		}
		else if(contains(Mobs, npc.getNpcId()))
		{
			if(Rnd.chance(20))
				npc.dropItem(killer, HERBS[Rnd.get(HERBS.length)], 1);
			if(Rnd.chance(20))
				npc.dropItem(killer, HERBS[Rnd.get(HERBS.length)], 1);
			if(Rnd.chance(30))
				npc.dropItem(killer, ITEMDROP[Rnd.get(ITEMDROP.length)], Rnd.get(1, 10));
			if(Rnd.chance(30))
				npc.dropItem(killer, ITEMDROP[Rnd.get(ITEMDROP.length)], Rnd.get(1, 10));

			QuestState st = killer.getQuestState(getName());
			if(st != null && st.getQuestItemsCount(SPEAR) > 0 && st.getQuestItemsCount(STAGE1) == 0 && npc.getSpawn() != null && npc.getSpawn().getGroupSpawn() != null && npc.getSpawn().getGroupSpawn().getEventName().equals("varka_p4") && npc.getSpawn().getGroupSpawn().isAllDead())
				st.giveItems(STAGE1, 1);
			else if(st != null && st.getQuestItemsCount(ENCHSPEAR) > 0 && st.getQuestItemsCount(STAGE2) == 0 && npc.getSpawn() != null && npc.getSpawn().getGroupSpawn() != null && npc.getSpawn().getGroupSpawn().getEventName().equals("varka_p7") && npc.getSpawn().getGroupSpawn().isAllDead())
				st.giveItems(STAGE2, 1);
		}
		else if(npc.getNpcId() == LATANA)
		{
			QuestState st = killer.getQuestState(getName());
			if(st != null)
			{
				st.setCond(4);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
			((DefaultAI) npc.getAI()).broadcastScriptEvent(20, killer, null, 500);
		}
	}

	@Override
	public void onDecay(L2NpcInstance npc)
	{
		if(npc.getNpcId() == LATANA)
		{
			if(npc.getSpawn() != null && npc.getSpawn().getInstance() != null)
				npc.getSpawn().getInstance().addSpawn(KOSUPPORTER2, new Location(105785, -41785, -1776, 32768), 0);	
		}
		else if(npc.getSpawn() != null && npc.getSpawn().getInstance() != null)
			npc.getSpawn().getInstance().notifyDecayd(npc);
	}

	@Override
	public String onAttack(L2NpcInstance npc, L2Player player, L2Skill skill)
	{
		if(npc.getSpawn() != null && npc.getSpawn().getInstance() != null)
			npc.getSpawn().getInstance().notifyAttacked(npc, player);

		return null;
	}

	private void makeBuff(L2NpcInstance npc, L2Player player, int skillId, int level)
	{
		npc.altUseSkill(SkillTable.getInstance().getInfo(skillId, level), player);
	}

	private void enterInstance(L2Player player)
	{
		int instId = 45;
		InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(instId);

		if(it == null)
		{
			_log.warn(this + " try to enter instance id: " + instId + " but no instance template!");
			return;
		}

		if(player.isCursedWeaponEquipped())
		{
			player.sendPacket(new SystemMessage(SystemMessage.C1S_QUEST_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
			return;
		}

		if(player.isInParty())
		{
			player.sendPacket(Msg.A_PARTY_CANNOT_BE_FORMED_IN_THIS_AREA);
			return;
		}

		Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
		List<L2Player> party = new FastList<L2Player>();

		if(inst != null)
		{
			if(inst.getTemplate().getId() != instId)
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_HAVE_ENTERED_ANOTHER_INSTANCE_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON));
				return;
			}
			if(player.getLevel() < it.getMinLevel() || player.getLevel() > it.getMaxLevel())
			{
				player.sendPacket(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
				return;
			}

			if(it.isDispelBuff())
				for(L2Effect e : player.getAllEffects())
				{
					if(e.getNext() != null && e.getNext().isInUse() && e.getNext().getSkill().getBuffProtectLevel() < 1)
						e.getNext().exit();

					if(e.getSkill().getBuffProtectLevel() < 1)
						e.exit();
				}

			player.setStablePoint(player.getLoc());
			player.teleToLocation(inst.getStartLoc(), inst.getReflection());
			return;
		}

		if(it.getMaxCount() > 0 && InstanceManager.getInstance().getInstanceCount(instId) >= it.getMaxCount())
		{
			player.sendPacket(new SystemMessage(SystemMessage.THE_MAXIMUM_NUMBER_OF_INSTANCE_ZONES_HAS_BEEN_EXCEEDED_YOU_CANNOT_ENTER));
			return;
		}

		if(player.getLevel() < it.getMinLevel() || player.getLevel() > it.getMaxLevel())
		{
			player.sendPacket(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
			return;
		}
		else if(player.getVar("instance-" + it.getType()) != null)
		{
			player.sendPacket(new SystemMessage(SystemMessage.C1_MAY_NOT_RE_ENTER_YET).addCharName(player));
			return;
		}

		party.add(player);
		inst = InstanceManager.getInstance().createNewInstance(instId, party);
		if(inst != null)
		{
			if(it.isDispelBuff())
				for(L2Effect e : player.getAllEffects())
				{
					if(e.getNext() != null && e.getNext().isInUse() && e.getNext().getSkill().getBuffProtectLevel() < 1)
						e.getNext().exit();

					if(e.getSkill().getBuffProtectLevel() < 1)
						e.exit();
				}

			player.setStablePoint(player.getLoc());
			player.teleToLocation(inst.getStartLoc(), inst.getReflection());
		}
	}
}
