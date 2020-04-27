package quests._128_PailakaSongofIceandFire;

import javolution.util.FastList;
import ru.l2gw.commons.math.Rnd;
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
import ru.l2gw.util.Location;

import java.util.List;

/**
 * @author rage
 * @date 06.10.2010 14:33:52
 */
public class _128_PailakaSongofIceandFire extends Quest
{
	// NPC
	private static int ADLER = 32497;
	private static int ADLER2 = 32510;
	private static int SINAI = 32500;
	private static int TINSPECTOR = 32507;

	// BOSS
	private static int HILLAS = 18610;
	private static int PAPION = 18609;
	private static int GARGOS = 18607;
	private static int KINSUS = 18608;
	private static int ADIANTUM = 18620;

	// MOBS
	private static int Bloom = 18616;
	private static int CrystalWaterBottle = 32492;
	private static int BurningBrazier = 32493;

	// ITEMS
	private static int PailakaInstantShield = 13032;
	private static int QuickHealingPotion = 13033;
	private static int FireAttributeEnhancer = 13040;
	private static int WaterAttributeEnhancer = 13041;
	private static int SpritesSword = 13034;
	private static int EnhancedSpritesSword = 13035;
	private static int SwordofIceandFire = 13036;
	private static int EssenceofWater = 13038;
	private static int EssenceofFire = 13039;

	private static int TempleBookofSecrets1 = 13130;
	private static int TempleBookofSecrets2 = 13131;
	private static int TempleBookofSecrets3 = 13132;
	private static int TempleBookofSecrets4 = 13133;
	private static int TempleBookofSecrets5 = 13134;
	private static int TempleBookofSecrets6 = 13135;
	private static int TempleBookofSecrets7 = 13136;

	// REWARDS
	private static int PailakaRing = 13294;
	private static int PailakaEarring = 13293;
	private static int ScrollofEscape = 736;

	private static int[] MOBS = new int[] { 18611, 18612, 18613, 18614, 18615 };
	private static int[] HPHERBS = new int[] { 8600, 8601, 8602 };
	private static int[] MPHERBS = new int[] { 8603, 8604, 8605 };


	public _128_PailakaSongofIceandFire()
	{
		super(128, "_128_PailakaSongofIceandFire", "Pailaka Song of Ice and Fire");

		addStartNpc(ADLER);
		addTalkId(ADLER2, SINAI);
		addFirstTalkId(TINSPECTOR);
		addKillId(HILLAS, PAPION, ADIANTUM, KINSUS, GARGOS, Bloom, CrystalWaterBottle, BurningBrazier);
		addKillId(MOBS);
		addAttackId(CrystalWaterBottle);
		addAttackId(BurningBrazier);
		addQuestItem(SpritesSword, EnhancedSpritesSword, SwordofIceandFire, EssenceofWater, EssenceofFire);
		addQuestItem(TempleBookofSecrets1, TempleBookofSecrets2, TempleBookofSecrets3, TempleBookofSecrets4, TempleBookofSecrets5, TempleBookofSecrets6, TempleBookofSecrets7);
		addQuestItem(PailakaInstantShield, QuickHealingPotion, FireAttributeEnhancer, WaterAttributeEnhancer);
	}
	
	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();
		String htmltext = event;

		if(event.equalsIgnoreCase("Enter"))
		{
			enterInstance(player, st);
			return "npchtm:32497-enter.htm";
		}
		else if(event.equalsIgnoreCase("32497-04.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32500-06.htm"))
		{
			st.setCond(2);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			st.giveItems(SpritesSword, 1);
			st.giveItems(TempleBookofSecrets1, 1);
		}
		else if(event.equalsIgnoreCase("32507-03.htm"))
		{
			st.setCond(4);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			st.takeItems(TempleBookofSecrets2, -1);
			st.giveItems(TempleBookofSecrets3, 1);
			if(st.getQuestItemsCount(EssenceofWater) == 0)
				htmltext = "32507-01.htm";
			else
			{
				st.takeItems(SpritesSword, -1);
				st.takeItems(EssenceofWater, -1);
				st.giveItems(EnhancedSpritesSword, 1);
			}
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
				inst.addSpawn(PAPION, new Location(-53903, 181484, -4555, 30456), 0);
		}
		else if(event.equalsIgnoreCase("32507-07.htm"))
		{
			st.setCond(7);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			st.takeItems(TempleBookofSecrets5, -1);
			st.giveItems(TempleBookofSecrets6, 1);
			if(st.getQuestItemsCount(EssenceofFire) == 0)
				htmltext = "32507-04.htm";
			else
			{
				st.takeItems(EnhancedSpritesSword, -1);
				st.takeItems(EssenceofFire, -1);
				st.giveItems(SwordofIceandFire, 1);
			}
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
				inst.addSpawn(GARGOS, new Location(-61354, 183624, -4821, 63613), 0);
		}
		else if(event.equalsIgnoreCase("32510-02.htm"))
		{
			st.giveItems(PailakaRing, 1);
			st.giveItems(PailakaEarring, 1);
			st.giveItems(ScrollofEscape, 1);
			st.addExpAndSp(810000, 50000);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
			player.getVitality().addPoints(20000);
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
				inst.successEnd();
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		L2Player player = st.getPlayer();
		if(npcId == ADLER)
		{
			if(cond == 0)
				if(player.getLevel() < 36 || player.getLevel() > 42)
				{
					htmltext = "32497-no.htm";
					st.exitCurrentQuest(true);
				}
				else
					return "32497-01.htm";
			else if(st.isCreated())
				htmltext = "32497-no.htm";
			else
				return "32497-05.htm";
		}
		else if(npcId == SINAI)
		{
			if(cond == 1)
				htmltext = "32500-01.htm";
			else
				htmltext = "32500-06.htm";
		}
		else if(npcId == ADLER2)
			if(cond == 9)
				htmltext = "32510-01.htm";
			else if(st.isCompleted())
				htmltext = "32510-02.htm";
		return htmltext;
	}

	@Override
	public String onFirstTalk(L2NpcInstance npc, L2Player player)
	{
		String htmltext = "noquest";
		QuestState st = player.getQuestState(getName());
		if(st == null || st.isCompleted())
			return htmltext;
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == TINSPECTOR)
			if(cond == 2)
				htmltext = "32507-01.htm";
			else if(cond == 3)
				htmltext = "32507-02.htm";
			else if(cond == 6)
				htmltext = "32507-05.htm";
			else
				htmltext = "32507-04.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(contains(MOBS, npcId))
		{
			int herbRnd = Rnd.get(2);
			if(Rnd.get(100) < 50)
				npc.dropItem(player, HPHERBS[herbRnd], 1);
			if(Rnd.get(100) < 50)
				npc.dropItem(player, MPHERBS[herbRnd], 1);
		}
		else if(npcId == HILLAS && cond == 2)
		{
			st.takeItems(TempleBookofSecrets1, -1);
			st.giveItems(EssenceofWater, 1);
			st.giveItems(TempleBookofSecrets2, 1);
			st.setCond(3);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(npcId == PAPION && cond == 4)
		{
			st.takeItems(TempleBookofSecrets3, -1);
			st.giveItems(TempleBookofSecrets4, 1);
			st.setCond(5);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
				inst.addSpawn(KINSUS, new Location(-61404, 181351, -4815, 63953), 0);
		}
		else if(npcId == KINSUS && cond == 5)
		{
			st.takeItems(TempleBookofSecrets4, -1);
			st.giveItems(EssenceofFire, 1);
			st.giveItems(TempleBookofSecrets5, 1);
			st.setCond(6);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(npcId == GARGOS && cond == 7)
		{
			st.takeItems(TempleBookofSecrets6, -1);
			st.giveItems(TempleBookofSecrets7, 1);
			st.setCond(8);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
				inst.addSpawn(ADIANTUM, new Location(-53297, 185027, -4617, 1512), 0);
		}
		else if(npcId == ADIANTUM && cond == 8)
		{
			st.takeItems(TempleBookofSecrets7, -1);
			st.setCond(9);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
				inst.addSpawn(ADLER2, new Location(npc.getX(), npc.getY(), npc.getZ(), npc.getHeading()), 0);
		}
		else if(npcId == Bloom)
		{
			if(Rnd.chance(50))
				npc.dropItem(player, PailakaInstantShield, Rnd.get(1, 7));
			if(Rnd.chance(30))
				npc.dropItem(player, QuickHealingPotion, Rnd.get(1, 7));
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		int npcId = npc.getNpcId();
		L2Player player = st.getPlayer();
		
		if(npcId == CrystalWaterBottle)
		{
			if(Rnd.chance(60))
				return null;

			if(Rnd.chance(50))
				npc.dropItem(player, PailakaInstantShield, Rnd.get(1, 10));
			if(Rnd.chance(30))
				npc.dropItem(player, QuickHealingPotion, Rnd.get(1, 10));
			else if(Rnd.chance(30))
				npc.dropItem(player, WaterAttributeEnhancer, Rnd.get(1, 5));

			npc.decayMe();
		}
		else if(npcId == BurningBrazier)
		{
			if(Rnd.chance(60))
				return null;

			if(Rnd.chance(50))
				npc.dropItem(player, PailakaInstantShield, Rnd.get(1, 10));
			if(Rnd.chance(30))
				npc.dropItem(player, QuickHealingPotion, Rnd.get(1, 10));
			else if(Rnd.chance(30))
				npc.dropItem(player, FireAttributeEnhancer, Rnd.get(1, 5));

			npc.decayMe();
		}
		return null;
	}

	private void enterInstance(L2Player player, QuestState st)
	{
		int instId = 43;
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

			//player.setVar("InstanceRP", player.getX() + "," + player.getY() + "," + player.getZ());
			player.setStablePoint(player.getLoc());
			player.teleToLocation(inst.getStartLoc(), inst.getReflection());
			return;
		}

		if(it.getMaxCount() > 0 && InstanceManager.getInstance().getInstanceCount(instId) >= it.getMaxCount())
		{
			player.sendPacket(new SystemMessage(SystemMessage.THE_MAXIMUM_NUMBER_OF_INSTANCE_ZONES_HAS_BEEN_EXCEEDED_YOU_CANNOT_ENTER));
			return;
		}

		if(it.getMinParty() > 1)
		{
			if(player.getParty() == null)
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_ARE_NOT_CURRENTLY_IN_A_PARTY_SO_YOU_CANNOT_ENTER));
				return;
			}
			else if(!player.getParty().isLeader(player))
			{
				player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.ONLY_A_PARTY_LEADER_CAN_TRY_TO_ENTER));
				return;
			}
			else if(player.getParty().getMemberCount() > it.getMaxParty() || player.getParty().getMemberCount() < it.getMinParty())
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_CANNOT_ENTER_DUE_TO_THE_PARTY_HAVING_EXCEEDED_THE_LIMIT));
				return;
			}

			boolean ok = true;
			for(L2Player member : player.getParty().getPartyMembers())
				if(member.getLevel() < it.getMinLevel() || member.getLevel() > it.getMaxLevel())
				{
					player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(member));
					ok = false;
				}
				else if(member.getVar("instance-" + it.getType()) != null || InstanceManager.getInstance().getInstanceByPlayer(member) != null)
				{
					player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1_MAY_NOT_RE_ENTER_YET).addCharName(member));
					ok = false;
				}
				else if(!player.getLastNpc().isInRange(member, 300))
				{
					player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1_IS_IN_A_LOCATION_WHICH_CANNOT_BE_ENTERED_THEREFORE_IT_CANNOT_BE_PROCESSED).addCharName(member));
					ok = false;
				}

			if(!ok)
				return;

			party.addAll(player.getParty().getPartyMembers());
		}
		else
		{
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
		}

		inst = InstanceManager.getInstance().createNewInstance(instId, party);
		if(inst != null)
		{
			if(st.getCond() == 4)
				inst.addSpawn(PAPION, new Location(-53903, 181484, -4555, 30456), 0);
			else if(st.getCond() == 7)
				inst.addSpawn(GARGOS, new Location(-61354, 183624, -4821, 63613), 0);
			else if(st.getCond() == 8)
				inst.addSpawn(ADIANTUM, new Location(-53297, 185027, -4617, 1512), 0);
			else if(st.getCond() == 9)
				inst.addSpawn(ADLER2, new Location(-53297, 185027, -4617, 1512), 0);

			for(L2Player member : party)
				if(member != null)
				{
					if(it.isDispelBuff())
						for(L2Effect e : member.getAllEffects())
						{
							if(e.getNext() != null && e.getNext().isInUse() && e.getNext().getSkill().getBuffProtectLevel() < 1)
								e.getNext().exit();

							if(e.getSkill().getBuffProtectLevel() < 1)
								e.exit();
						}
					//member.setVar("InstanceRP", member.getX() + "," + member.getY() + "," + member.getZ());
					member.setStablePoint(member.getLoc());
					member.teleToLocation(inst.getStartLoc(), inst.getReflection());
				}
		}
	}
}
