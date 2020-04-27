package quests.SagasSuperclass;

import javolution.util.FastList;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.quest.QuestTimer;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class SagasSuperclass extends Quest
{
	protected int id = 0;
	protected String qn = "SagasSuperclass";
	protected String name = "Saga's Superclass";
	protected int classid = 0;
	protected int prevclass = 0;
	protected int[] NPC = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	public int[] Items = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	protected int[] Mob = new int[]{0, 1, 2};
	protected int[] X = new int[]{0, 1, 2};
	protected int[] Y = new int[]{0, 1, 2};
	protected int[] Z = new int[]{0, 1, 2};
	public String[] Text = new String[18];
	protected FastList<Spawn> Spawn_List = new FastList<Spawn>();

	private class Spawn
	{
		public int id;
		public String name;
		public L2NpcInstance npc;

		public Spawn(int id, String name, L2NpcInstance npc)
		{
			this.id = id;
			this.name = name;
			this.npc = npc;
		}
	}

	protected int[] Archon_Minions = new int[]{21646, 21647, 21648, 21649, 21650, 21651, 21652};
	protected int[] Guardian_Angels = new int[]{27214, 27215, 27216};
	protected int[] Archon_Hellisha_Norm = new int[]{18212, 18214, 18215, 18216, 18218};

	protected static HashMap<Integer, String> Quests = new HashMap<Integer, String>();

	static
	{
		Quests.put(67, "_067_SagaOfTheDoombringer");
		Quests.put(68, "_068_SagaOfTheSoulHound");
		Quests.put(69, "_069_SagaOfTheTrickster");
		Quests.put(70, "_070_SagaOfThePhoenixKnight");
		Quests.put(71, "_071_SagaOfEvasTemplar");
		Quests.put(72, "_072_SagaOfTheSwordMuse");
		Quests.put(73, "_073_SagaOfTheDuelist");
		Quests.put(74, "_074_SagaOfTheDreadnoughts");
		Quests.put(75, "_075_SagaOfTheTitan");
		Quests.put(76, "_076_SagaOfTheGrandKhavatari");
		Quests.put(77, "_077_SagaOfTheDominator");
		Quests.put(78, "_078_SagaOfTheDoomcryer");
		Quests.put(79, "_079_SagaOfTheAdventurer");
		Quests.put(80, "_080_SagaOfTheWindRider");
		Quests.put(81, "_081_SagaOfTheGhostHunter");
		Quests.put(82, "_082_SagaOfTheSagittarius");
		Quests.put(83, "_083_SagaOfTheMoonlightSentinel");
		Quests.put(84, "_084_SagaOfTheGhostSentinel");
		Quests.put(85, "_085_SagaOfTheCardinal");
		Quests.put(86, "_086_SagaOfTheHierophant");
		Quests.put(87, "_087_SagaOfEvasSaint");
		Quests.put(88, "_088_SagaOfTheArchmage");
		Quests.put(89, "_089_SagaOfTheMysticMuse");
		Quests.put(90, "_090_SagaOfTheStormScreamer");
		Quests.put(91, "_091_SagaOfTheArcanaLord");
		Quests.put(92, "_092_SagaOfTheElementalMaster");
		Quests.put(93, "_093_SagaOfTheSpectralMaster");
		Quests.put(94, "_094_SagaOfTheSoultaker");
		Quests.put(95, "_095_SagaOfTheHellKnight");
		Quests.put(96, "_096_SagaOfTheSpectralDancer");
		Quests.put(97, "_097_SagaOfTheShillienTemplar");
		Quests.put(98, "_098_SagaOfTheShillienSaint");
		Quests.put(99, "_099_SagaOfTheFortuneSeeker");
		Quests.put(100, "_100_SagaOfTheMaestro");
	}

	protected static int[][] QuestClass = new int[][]{
			{0x7f},
			{0x80, 0x81},
			{0x82},
			{0x05},
			{0x14},
			{0x15},
			{0x02},
			{0x03},
			{0x2e},
			{0x30},
			{0x33},
			{0x34},
			{0x08},
			{0x17},
			{0x24},
			{0x09},
			{0x18},
			{0x25},
			{0x10},
			{0x11},
			{0x1e},
			{0x0c},
			{0x1b},
			{0x28},
			{0x0e},
			{0x1c},
			{0x29},
			{0x0d},
			{0x06},
			{0x22},
			{0x21},
			{0x2b},
			{0x37},
			{0x39}};

	private void cleanTempVars()
	{
		Connection con = null;
		PreparedStatement st = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			st = con.prepareStatement("DELETE FROM character_quests WHERE name=? AND (var='spawned' OR var='kills' OR var='Archon' OR var LIKE 'Mob_%')");
			st.setString(1, qn);
			st.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, st);
		}
	}

	private void FinishQuest(QuestState st, L2Player player)
	{
		st.addExpAndSp(2299404, 0);
		st.rollAndGive(57, 5000000, 100);
		st.giveItems(6622, 1);
		if(getPrevClass(player) == 43) // Shillien Elder
		{
			st.giveItems(15309, 4);
		}
		else if(getPrevClass(player) == 16) // Bishop
		{
			st.giveItems(15307, 1);
		}
		else if(getPrevClass(player) == 30) // Elven Elder
		{
			st.giveItems(15308, 1);
		}
		st.exitCurrentQuest(true);
		player.setClassId((short) getClassId(player));
		if(!player.isSubClassActive() && player.getBaseClass() == getPrevClass(player))
			player.setBaseClass(getClassId(player));
		player.broadcastUserInfo(true);
		Cast(FindTemplate(NPC[0]), player, 4339, 1);
	}

	public void onLoad()
	{
		cleanTempVars();
	}

	public SagasSuperclass(int id, String qn, String name)
	{
		super(id, qn, name);
		this.qn = qn;
		this.name = name;
	}

	protected void registerNPCs()
	{
		addStartNpc(NPC[0]);
		addAttackId(Mob[2]);
		addFirstTalkId(NPC[4]);

		for(int npc : NPC)
			addTalkId(npc);

		for(int mobid : Mob)
			addKillId(mobid);

		for(int mobid : Archon_Minions)
			addKillId(mobid);

		for(int mobid : Guardian_Angels)
			addKillId(mobid);

		for(int mobid : Archon_Hellisha_Norm)
			addKillId(mobid);

		for(int ItemId : Items)
			if(ItemId != 0 && ItemId != 7080 && ItemId != 7081 && ItemId != 6480 && ItemId != 6482)
				addQuestItem(ItemId);
	}

	protected int getClassId(@SuppressWarnings("unused") L2Player player)
	{
		return classid;
	}

	protected int getPrevClass(@SuppressWarnings("unused") L2Player player)
	{
		if(prevclass == 128 && player.getClassId().getId() == 129)
			return 129;
		return prevclass;
	}

	protected void Cast(L2NpcInstance npc, L2Character target, int skillId, int level)
	{
		target.broadcastPacket(new MagicSkillUse(target, target, skillId, level, 6000, 1));
		target.broadcastPacket(new MagicSkillUse(npc, npc, skillId, level, 6000, 1));
	}

	protected L2NpcInstance FindTemplate(int npcId)
	{
		return L2ObjectsStorage.getByNpcId(npcId);
	}

	protected void AddSpawn(L2Player player, L2NpcInstance mob)
	{
		Spawn_List.add(new Spawn(mob.getObjectId(), player.getName(), mob));
	}

	protected boolean CheckSpawn(L2Player player, int npcObjectId)
	{
		L2Object isQuest = L2ObjectsStorage.findObject(npcObjectId);
		return isQuest != null;
	}

	protected L2NpcInstance FindSpawn(L2Player player, int npcObjectId)
	{
		for(Spawn spawn : Spawn_List)
			if(spawn.id == npcObjectId && spawn.name.equals(player.getName()))
			{
				return spawn.npc;
			}
		return null;
	}

	protected void DeleteSpawn(L2Player player, int npcObjectId)
	{
		for(Spawn spawn : Spawn_List)
			if(spawn.id == npcObjectId && spawn.name.equals(player.getName()))
			{
				spawn.npc.deleteMe();
				Spawn_List.remove(spawn);
				return;
			}
	}

	protected L2NpcInstance spawn(int id, Location loc)
	{
		L2NpcTemplate template = NpcTable.getTemplate(id);
		L2Spawn spawn;
		try
		{
			spawn = new L2Spawn(template);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		spawn.setLoc(loc);
		L2NpcInstance npc = spawn.doSpawn(true);
		spawn.stopRespawn();
		return npc;
	}

	public void giveHallishaMark(QuestState st)
	{
		if(st.getInt("spawned") > 0)
		{
			QuestTimer qt = st.getQuestTimer("Archon Hellisha has despawned");
			if(qt == null)
			{
				DeleteSpawn(st.getPlayer(), st.getInt("Archon"));
				st.set("spawned", "0");
			}
		}
		if(st.getInt("spawned") <= 0)
			if(st.getQuestItemsCount(Items[3]) >= 700)
			{
				st.takeItems(Items[3], 20);
				L2NpcInstance Archon = spawn(Mob[1], st.getPlayer().getLoc());
				AddSpawn(st.getPlayer(), Archon);
				int ArchonId = Archon.getObjectId();
				st.set("Archon", str(ArchonId));
				st.set("spawned", "1");
				startQuestTimer("Archon Hellisha has despawned", 600000L, Archon, st.getPlayer());
				AutoChat(Archon, Text[13].replace("PLAYERNAME", st.getPlayer().getName()));
				Archon.addDamageHate(st.getPlayer(), 0, 99999);
				Archon.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, st.getPlayer(), null);
			}
			else
				st.rollAndGive(Items[3], 1, 100);
	}

	protected QuestState findRightState(L2Player player, L2NpcInstance npc)
	{
		int npcObjectId = npc.getObjectId();
		String name = player.getName();
		L2Player st1 = null;
		for(Spawn spawn : Spawn_List)
			if(spawn.id == npcObjectId && spawn.name.equals(player.getName()))
			{
				st1 = L2ObjectsStorage.getPlayer(name);
				break;
			}
		if(st1 == null)
			for(Spawn spawn : Spawn_List)
				if(spawn.id == npcObjectId)
				{
					st1 = L2ObjectsStorage.getPlayer(spawn.name);
					break;
				}
		if(st1 != null)
			return st1.getQuestState(qn);
		return null;
	}

	protected QuestState findQuest(L2Player player)
	{
		QuestState st = null;
		for(Integer q : Quests.keySet())
		{
			st = player.getQuestState(Quests.get(q));
			if(st != null)
			{
				int[] qc = QuestClass[q - 67];
				for(int c : qc)
					if(player.getClassId().getId() == c)
						return st;
			}
		}
		return st;
	}

	protected void AutoChat(L2NpcInstance npc, String text)
	{
		Functions.npcSay(npc, Say2C.ALL, text);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = ""; // simple initialization...if none of the events match, return nothing.
		L2Player player = st.getPlayer();
		L2NpcInstance npc = player.getLastNpc();
		if(event.equalsIgnoreCase("0-011.htm") || event.equalsIgnoreCase("0-012.htm") || event.equalsIgnoreCase("0-013.htm") || event.equalsIgnoreCase("0-014.htm") || event.equalsIgnoreCase("0-015.htm"))
			htmltext = event;
		else if(event.equalsIgnoreCase("accept"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(Items[10], 1);
			htmltext = "0-03.htm";
		}
		else if(event.equalsIgnoreCase("0-1"))
		{
			if(player.getLevel() < 76)
			{
				htmltext = "0-02.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "0-05.htm";
		}
		else if(event.equalsIgnoreCase("0-2"))
		{
			if(player.getLevel() >= 76)
			{
				htmltext = "0-07.htm";
				st.takeItems(Items[10], -1);
				FinishQuest(st, player);
			}
			else
			{
				st.takeItems(Items[10], -1);
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "20");
				htmltext = "0-08.htm";
			}
		}
		else if(event.equalsIgnoreCase("1-3"))
		{
			st.set("cond", "3");
			htmltext = "1-05.htm";
		}
		else if(event.equalsIgnoreCase("1-4"))
		{
			st.set("cond", "4");
			st.takeItems(Items[0], 1);
			if(Items[11] != 0)
				st.takeItems(Items[11], 1);
			st.giveItems(Items[1], 1);
			htmltext = "1-06.htm";
		}
		else if(event.equalsIgnoreCase("2-1"))
		{
			st.set("cond", "2");
			htmltext = "2-05.htm";
		}
		else if(event.equalsIgnoreCase("2-2"))
		{
			st.set("cond", "5");
			st.takeItems(Items[1], 1);
			st.giveItems(Items[4], 1);
			htmltext = "2-06.htm";
		}
		else if(event.equalsIgnoreCase("3-5"))
			htmltext = "3-07.htm";
		else if(event.equalsIgnoreCase("3-6"))
		{
			st.set("cond", "11");
			htmltext = "3-02.htm";
		}
		else if(event.equalsIgnoreCase("3-7"))
		{
			st.set("cond", "12");
			htmltext = "3-03.htm";
		}
		else if(event.equalsIgnoreCase("3-8"))
		{
			st.set("cond", "13");
			st.takeItems(Items[2], 1);
			st.giveItems(Items[7], 1);
			htmltext = "3-08.htm";
		}
		else if(event.equalsIgnoreCase("4-1"))
			htmltext = "4-010.htm";
		else if(event.equalsIgnoreCase("4-2"))
		{
			st.giveItems(Items[9], 1);
			st.set("cond", "18");
			st.playSound(SOUND_MIDDLE);
			htmltext = "4-011.htm";
		}
		else if(event.equalsIgnoreCase("4-3"))
		{
			st.giveItems(Items[9], 1);
			st.set("cond", "18");
			st.set("Quest0", "0");
			st.playSound(SOUND_MIDDLE);
			L2NpcInstance Mob_2 = FindSpawn(player, st.getInt("Mob_2"));
			if(Mob_2 != null)
			{
				AutoChat(Mob_2, Text[13].replace("PLAYERNAME", player.getName()));
				DeleteSpawn(player, Mob_2.getObjectId());
			}
			return null;
		}
		else if(event.equalsIgnoreCase("5-1"))
		{
			st.set("cond", "6");
			st.takeItems(Items[4], 1);
			Cast(FindTemplate(NPC[5]), player, 4546, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "5-02.htm";
		}
		else if(event.equalsIgnoreCase("6-1"))
		{
			st.set("cond", "8");
			st.takeItems(Items[5], 1);
			Cast(FindTemplate(NPC[6]), player, 4546, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "6-03.htm";
		}
		else if(event.equalsIgnoreCase("7-1"))
		{
			if(st.getInt("spawned") == 1)
				htmltext = "7-03.htm";
			else if(st.getInt("spawned") == 0)
			{
				L2NpcInstance Mob_1 = spawn(Mob[0], new Location(X[0], Y[0], Z[0]));
				st.set("Mob_1", str(Mob_1.getObjectId()));
				st.set("spawned", "1");
				startQuestTimer("Mob_1 Timer 1", 500L, Mob_1, player);
				startQuestTimer("Mob_1 has despawned", 300000L, Mob_1, player);
				AddSpawn(player, Mob_1);
				htmltext = "7-02.htm";
			}
			else
				htmltext = "7-04.htm";
		}
		else if(event.equalsIgnoreCase("7-2"))
		{
			st.set("cond", "10");
			st.takeItems(Items[6], 1);
			Cast(FindTemplate(NPC[7]), player, 4546, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "7-06.htm";
		}
		else if(event.equalsIgnoreCase("8-1"))
		{
			st.set("cond", "14");
			st.takeItems(Items[7], 1);
			Cast(FindTemplate(NPC[8]), player, 4546, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "8-02.htm";
		}
		else if(event.equalsIgnoreCase("9-1"))
		{
			st.set("cond", "17");
			st.takeItems(Items[8], 1);
			Cast(FindTemplate(NPC[9]), player, 4546, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "9-03.htm";
		}
		else if(event.equalsIgnoreCase("10-1"))
		{
			if(st.getInt("Quest0") > 0)
			{
				if(st.getInt("Mob_3") != 0)
				{
					if(!CheckSpawn(player, st.getInt("Mob_3")))
						st.set("Quest0", "0");
				}
				else
					st.set("Quest0", "0");
			}
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(NPC[4]);
			if(isQuest != null)
			{
				htmltext = "10-04.htm";
				QuestTimer qt = st.getQuestTimer("Mob_3 Error");
				if(qt == null)
					st.startQuestTimer("Mob_3 Error", 59000);
			}
			else if(st.getInt("Quest0") == 0)
			{
				L2NpcInstance Mob_3 = spawn(Mob[2], new Location(X[1], Y[1], Z[1]));
				L2NpcInstance Mob_2 = spawn(NPC[4], new Location(X[2], Y[2], Z[2]));
				AddSpawn(player, Mob_3);
				AddSpawn(player, Mob_2);
				st.set("Mob_3", str(Mob_3.getObjectId()));
				st.set("Mob_2", str(Mob_2.getObjectId()));
				st.set("Quest0", "1");
				st.set("Quest1", "45");
				startQuestTimer("Mob_3 Timer 1", 500, Mob_3, player);
				startQuestTimer("Mob_3 has despawned", 59000, Mob_3, player);
				startQuestTimer("Mob_2 Timer 1", 500, Mob_2, player);
				startQuestTimer("Mob_2 has despawned", 60000, Mob_2, player);
				htmltext = "10-02.htm";
			}
			else if(st.getInt("Quest1") == 45)
				htmltext = "10-03.htm";
			else
				htmltext = "10-04.htm";
		}
		else if(event.equalsIgnoreCase("10-2"))
		{
			st.set("cond", "19");
			st.takeItems(Items[9], 1);
			Cast(FindTemplate(NPC[10]), player, 4546, 1);
			st.playSound(SOUND_MIDDLE);
			htmltext = "10-06.htm";
		}
		else if(event.equalsIgnoreCase("11-9"))
		{
			st.set("cond", "15");
			htmltext = "11-03.htm";
		}
		else if(event.equalsIgnoreCase("Mob_1 Timer 1"))
		{
			AutoChat(npc, Text[0].replace("PLAYERNAME", player.getName()));
			return null;
		}
		else if(event.equalsIgnoreCase("Mob_1 has despawned"))
		{
			if(npc != null)
			{
				AutoChat(npc, Text[1].replace("PLAYERNAME", player.getName()));
				DeleteSpawn(player, npc.getObjectId());
			}
			st.set("spawned", "0");
			return null;
		}
		else if(event.equalsIgnoreCase("Archon Hellisha has despawned"))
		{
			if(npc != null)
			{
				AutoChat(npc, Text[6].replace("PLAYERNAME", player.getName()));
				DeleteSpawn(player, npc.getObjectId());
			}
			st.set("spawned", "0");
			return null;
		}
		else if(event.equalsIgnoreCase("Mob_3 Timer 1"))
		{
			L2NpcInstance Mob_2 = FindSpawn(player, st.getInt("Mob_2"));
			if(npc.knowsObject(Mob_2))
			{
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, Mob_2, null);
				Mob_2.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, npc, null);
				if(player != null)
				{
					AutoChat(npc, Text[14].replace("PLAYERNAME", player.getName()));
				}
			}
			else
				startQuestTimer("Mob_3 Timer 1", 500, npc, player);
			return null;
		}
		else if(event.equalsIgnoreCase("Mob_3 has despawned"))
		{
			AutoChat(npc, Text[15].replace("PLAYERNAME", player.getName()));
			st.set("Quest0", "2");
			if(npc != null)
				npc.reduceHp(9999999, npc, false, false);
			QuestTimer qt = st.getQuestTimer("Mob_3 Error");
			if(qt != null)
				qt.cancel();
			DeleteSpawn(player, st.getInt("Mob_3"));
			return null;
		}
		else if(event.equalsIgnoreCase("Mob_2 Timer 1"))
		{
			AutoChat(npc, Text[7].replace("PLAYERNAME", player.getName()));
			startQuestTimer("Mob_2 Timer 2", 1500, npc, player);
			if(st.getInt("Quest1") == 45)
				st.set("Quest1", "0");
			return null;
		}
		else if(event.equalsIgnoreCase("Mob_2 Timer 2"))
		{
			AutoChat(npc, Text[8].replace("PLAYERNAME", player.getName()));
			startQuestTimer("Mob_2 Timer 3", 10000, npc, player);
			return null;
		}
		else if(event.equalsIgnoreCase("Mob_2 Timer 3"))
		{
			if(st.getInt("Quest0") == 0)
			{
				startQuestTimer("Mob_2 Timer 3", 13000, npc, player);
				if(Rnd.get(2) == 0)
					AutoChat(npc, Text[9].replace("PLAYERNAME", player.getName()));
				else
					AutoChat(npc, Text[10].replace("PLAYERNAME", player.getName()));
			}
			return null;
		}
		else if(event.equalsIgnoreCase("Mob_2 has despawned"))
		{
			st.set("Quest1", str(st.getInt("Quest1") + 1));
			if(st.getInt("Quest0") == 1 || st.getInt("Quest0") == 2 || st.getInt("Quest1") > 3)
			{
				st.set("Quest0", "0");
				if(st.getInt("Quest0") == 1)
					AutoChat(npc, Text[11].replace("PLAYERNAME", player.getName()));
				else
					AutoChat(npc, Text[12].replace("PLAYERNAME", player.getName()));
				if(npc != null)
					npc.reduceHp(9999999, npc, false, false);
				DeleteSpawn(player, st.getInt("Mob_2"));
			}
			else
				startQuestTimer("Mob_2 has despawned", 1000, npc, player);
			return null;
		}
		else if(event.equalsIgnoreCase("Mob_3 Error"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(NPC[4]);
			if(isQuest != null)
				isQuest.deleteMe();
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
		if(player.getClassId().getId() == getPrevClass(player))
			if(st.isCreated())
			{
				if(npcId == NPC[0])
					htmltext = "0-01.htm";
			}
			else if(cond == 1)
			{
				if(npcId == NPC[0])
					htmltext = "0-04.htm";
				else if(npcId == NPC[2])
					htmltext = "2-01.htm";
			}
			else if(cond == 2)
			{
				if(npcId == NPC[2])
					htmltext = "2-02.htm";
				else if(npcId == NPC[1])
					htmltext = "1-01.htm";
			}
			else if(cond == 3)
			{
				if(npcId == NPC[1])
				{
					if(st.getQuestItemsCount(Items[0]) > 0)
					{
						if(Items[11] == 0)
							htmltext = "1-03.htm";
						else if(st.getQuestItemsCount(Items[11]) > 0)
							htmltext = "1-03.htm";
						else
							htmltext = "1-02.htm";
					}
					else
						htmltext = "1-02.htm";
				}
				else if(NPC.length > 12 && npcId == NPC[12])
				{
					if(Items[11] == 0)
						htmltext = "1_1-1.htm";
					else if(st.getQuestItemsCount(Items[11]) == 0)
					{
						st.giveItems(Items[11],1);
						htmltext = "1_1-1.htm";
					}
					else
						htmltext = "1_1-2.htm";
				}
			}
			else if(cond == 4)
			{
				if(npcId == NPC[1])
					htmltext = "1-04.htm";
				else if(npcId == NPC[2])
					htmltext = "2-03.htm";
			}
			else if(cond == 5)
			{
				if(npcId == NPC[2])
					htmltext = "2-04.htm";
				else if(npcId == NPC[5])
					htmltext = "5-01.htm";
			}
			else if(cond == 6)
			{
				if(npcId == NPC[5])
					htmltext = "5-03.htm";
				else if(npcId == NPC[6])
					htmltext = "6-01.htm";
			}
			else if(cond == 7)
			{
				if(npcId == NPC[6])
					htmltext = "6-02.htm";
			}
			else if(cond == 8)
			{
				if(npcId == NPC[6])
					htmltext = "6-04.htm";
				else if(npcId == NPC[7])
					htmltext = "7-01.htm";
			}
			else if(cond == 9)
			{
				if(npcId == NPC[7])
					htmltext = "7-05.htm";
			}
			else if(cond == 10)
			{
				if(npcId == NPC[7])
					htmltext = "7-07.htm";
				else if(npcId == NPC[3])
					htmltext = "3-01.htm";
			}
			else if(cond == 11 || cond == 12)
			{
				if(npcId == NPC[3])
					if(st.getQuestItemsCount(Items[2]) > 0)
						htmltext = "3-05.htm";
					else
						htmltext = "3-04.htm";
			}
			else if(cond == 13)
			{
				if(npcId == NPC[3])
					htmltext = "3-06.htm";
				else if(npcId == NPC[8])
					htmltext = "8-01.htm";
			}
			else if(cond == 14)
			{
				if(npcId == NPC[8])
					htmltext = "8-03.htm";
				else if(npcId == NPC[11])
					htmltext = "11-01.htm";
			}
			else if(cond == 15)
			{
				if(npcId == NPC[11])
					htmltext = "11-02.htm";
				else if(npcId == NPC[9])
					htmltext = "9-01.htm";
			}
			else if(cond == 16)
			{
				if(npcId == NPC[9])
					htmltext = "9-02.htm";
			}
			else if(cond == 17)
			{
				if(npcId == NPC[9])
					htmltext = "9-04.htm";
				else if(npcId == NPC[10])
					htmltext = "10-01.htm";
			}
			else if(cond == 18)
			{
				if(npcId == NPC[10])
					htmltext = "10-05.htm";
			}
			else if(cond == 19)
			{
				if(npcId == NPC[10])
					htmltext = "10-07.htm";
				if(npcId == NPC[0])
					htmltext = "0-06.htm";
			}
			else if(cond == 20)
				if(npcId == NPC[0])
					if(player.getLevel() >= 76)
					{
						htmltext = "0-09.htm";
						if(getClassId(player) < 131 || getClassId(player) > 135)
							FinishQuest(st, player);
					}
					else
						htmltext = "0-010.htm";
		return htmltext;
	}

	@Override
	public String onFirstTalk(L2NpcInstance npc, L2Player player)
	{
		String htmltext = "noquest";
		QuestState st = player.getQuestState(qn);
		if(st == null)
			return htmltext;
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == NPC[4])
			if(cond == 17)
			{
				QuestState st2 = findRightState(player, npc);
				if(st2 != null)
					if(st == st2)
					{
						if(st.getInt("Tab") == 1)
						{
							if(st.getInt("Quest0") == 0)
								htmltext = "4-04.htm";
							else if(st.getInt("Quest0") == 1)
								htmltext = "4-06.htm";
						}
						else if(st.getInt("Quest0") == 0)
							htmltext = "4-01.htm";
						else if(st.getInt("Quest0") == 1)
							htmltext = "4-03.htm";
					}
					else if(st.getInt("Tab") == 1)
					{
						if(st.getInt("Quest0") == 0)
							htmltext = "4-05.htm";
						else if(st.getInt("Quest0") == 1)
							htmltext = "4-07.htm";
					}
					else if(st.getInt("Quest0") == 0)
						htmltext = "4-02.htm";
			}
			else if(cond == 18)
				htmltext = "4-08.htm";
		return htmltext;
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		L2Player player = st.getPlayer();
		if(st.getInt("cond") == 17)
			if(npc.getNpcId() == Mob[2])
			{
				QuestState st2 = findRightState(player, npc);
				if(st == st2)
				{
					st.set("Quest0", str(st.getInt("Quest0") + 1));
					if(st.getInt("Quest0") == 1)
						AutoChat(npc, Text[16].replace("PLAYERNAME", player.getName()));
					if(st.getInt("Quest0") > 15)
					{
						st.set("Quest0", "1");
						AutoChat(npc, Text[17].replace("PLAYERNAME", player.getName()));
						npc.reduceHp(9999999, npc, false, false);
						DeleteSpawn(player, st.getInt("Mob_3"));
						QuestTimer qt = st.getQuestTimer("Mob_3 has despawned");
						if(qt != null)
						{
							qt.cancel();
							qt = null;
						}
						st.set("Tab", "1");
					}
				}
			}
		return null;
	}

	protected boolean isArchonMinions(int npcId)
	{
		for(int id : Archon_Minions)
			if(id == npcId)
				return true;
		return false;
	}

	protected boolean isArchonHellishaNorm(int npcId)
	{
		for(int id : Archon_Hellisha_Norm)
			if(id == npcId)
				return true;
		return false;
	}

	protected boolean isGuardianAngels(int npcId)
	{
		for(int id : Guardian_Angels)
			if(id == npcId)
				return true;
		return false;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		if(isArchonMinions(npcId))
		{
			L2Party party = killer.getParty();
			if(party != null)
			{
				ArrayList<QuestState> PartyQuestMembers = new ArrayList<QuestState>();
				for(L2Player player1 : party.getPartyMembers())
				{
					QuestState st1 = findQuest(player1);
					if(st1 != null && st1.getInt("cond") == 15 && killer.getDistance(player1) <= Config.ALT_PARTY_DISTRIBUTION_RANGE)
						PartyQuestMembers.add(st1);
				}
				if(PartyQuestMembers.size() > 0)
				{
					QuestState st2 = PartyQuestMembers.get(Rnd.get(PartyQuestMembers.size()));
					((SagasSuperclass) st2.getQuest()).giveHallishaMark(st2);
				}
			}
			else
			{
				QuestState st1 = findQuest(killer);
				if(st1 != null && st1.getInt("cond") == 15)
					((SagasSuperclass) st1.getQuest()).giveHallishaMark(st1);
			}
		}
		else if(isArchonHellishaNorm(npcId))
		{
			L2Party party = killer.getParty();
			if(party != null)
			{
				AutoChat(npc, "Now, my soul freed from the shackles of the millennium, Halixia, to the back side I come ...");
				for(L2Player player1 : party.getPartyMembers())
				{
					QuestState st1 = findQuest(player1);
					if(st1 != null && st1.getInt("cond") == 15 && killer.getDistance(player1) <= Config.ALT_PARTY_DISTRIBUTION_RANGE)
					{
						st1.giveItems(((SagasSuperclass) st1.getQuest()).Items[8], 1);
						st1.takeItems(((SagasSuperclass) st1.getQuest()).Items[3], -1);
						st1.set("cond", "16");
						st1.playSound(SOUND_MIDDLE);
					}
				}
			}
			else
			{
				QuestState st1 = findQuest(killer);
				if(st1 != null)
					if(st1.getInt("cond") == 15)
					{
						// This is just a guess....not really sure what it actually says, if anything
						AutoChat(npc, ((SagasSuperclass) st1.getQuest()).Text[4].replace("PLAYERNAME", st1.getPlayer().getName()));
						st1.giveItems(((SagasSuperclass) st1.getQuest()).Items[8], 1);
						st1.takeItems(((SagasSuperclass) st1.getQuest()).Items[3], -1);
						st1.set("cond", "16");
						st1.playSound(SOUND_MIDDLE);
					}
			}
		}
		else if(isGuardianAngels(npcId))
		{
			QuestState st1 = findQuest(killer);
			if(st1 != null)
				if(st1.getInt("cond") == 6)
					if(st1.getInt("kills") < 9)
						st1.set("kills", str(st1.getInt("kills") + 1));
					else
					{
						st1.playSound(SOUND_MIDDLE);
						st1.giveItems(((SagasSuperclass) st1.getQuest()).Items[5], 1);
						st1.set("cond", "7");
					}
		}
		else
		{
			QuestState st = findQuest(killer);

			if(st == null)
				return;

			int cond = st.getInt("cond");
			if(npcId == Mob[0] && cond == 8)
			{
				QuestState st2 = findRightState(killer, npc);
				if(st2 != null)
				{
					if(!killer.isInParty())
						if(st == st2)
						{
							AutoChat(npc, Text[12].replace("PLAYERNAME", killer.getName()));
							st.giveItems(Items[6], 1);
							st.set("cond", "9");
							st.playSound(SOUND_MIDDLE);
						}
					QuestTimer qt = st.getQuestTimer("Mob_1 has despawned");
					if(qt != null)
					{
						qt.cancel();
						qt = null;
					}
					DeleteSpawn(st2.getPlayer(), st2.getInt("Mob_1"));
					st2.set("spawned", "0");
				}
			}
			else if(npcId == Mob[1] && cond == 15)
			{
				QuestState st2 = findRightState(killer, npc);
				if(st2 != null)
				{
					if(!killer.isInParty())
						if(st == st2)
						{
							AutoChat(npc, Text[4].replace("PLAYERNAME", killer.getName()));
							st.giveItems(Items[8], 1);
							st.takeItems(Items[3], -1);
							st.set("cond", "16");
							st.playSound(SOUND_MIDDLE);
						}
						else
							AutoChat(npc, Text[5].replace("PLAYERNAME", killer.getName()));
					QuestTimer qt = st.getQuestTimer("Archon Hellisha has despawned");
					if(qt != null)
					{
						qt.cancel();
						qt = null;
					}
					DeleteSpawn(st2.getPlayer(), st2.getInt("Archon"));
					st2.set("spawned", "0");
				}
			}

		}
	}
}
