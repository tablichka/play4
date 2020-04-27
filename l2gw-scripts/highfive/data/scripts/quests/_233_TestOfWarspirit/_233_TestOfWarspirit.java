package quests._233_TestOfWarspirit;

import javolution.util.FastList;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _233_TestOfWarspirit extends Quest
{
	// NPCs
	private static int Somak = 30510;
	private static int Vivyan = 30030;
	private static int Sarien = 30436;
	private static int Racoy = 30507;
	private static int Manakia = 30515;
	private static int Orim = 30630;
	private static int Ancestor_Martankus = 30649;
	private static int Pekiron = 30682;
	// Mobs
	private static int Porta = 20213;
	private static int Excuro = 20214;
	private static int Mordeo = 20215;
	private static int Noble_Ant = 20089;
	private static int Noble_Ant_Leader = 20090;
	private static int Leto_Lizardman_Shaman = 20581;
	private static int Leto_Lizardman_Overlord = 20582;
	private static int Medusa = 20158;
	private static int Stenoa_Gorgon_Queen = 27108;
	private static int Tamlin_Orc = 20601;
	private static int Tamlin_Orc_Archer = 20602;
	// Items
	private static short Dimensional_Diamond = 7562;
	private static short MARK_OF_WARSPIRIT = 2879;
	// Quest Items
	private static short VENDETTA_TOTEM = 2880;
	private static short TAMLIN_ORC_HEAD = 2881;
	private static short WARSPIRIT_TOTEM = 2882;
	private static short ORIMS_CONTRACT = 2883;
	private static short PORTAS_EYE = 2884;
	private static short EXCUROS_SCALE = 2885;
	private static short MORDEOS_TALON = 2886;
	private static short BRAKIS_REMAINS1 = 2887;
	private static short PEKIRONS_TOTEM = 2888;
	private static short TONARS_SKULL = 2889;
	private static short TONARS_RIB_BONE = 2890;
	private static short TONARS_SPINE = 2891;
	private static short TONARS_ARM_BONE = 2892;
	private static short TONARS_THIGH_BONE = 2893;
	private static short TONARS_REMAINS1 = 2894;
	private static short MANAKIAS_TOTEM = 2895;
	private static short HERMODTS_SKULL = 2896;
	private static short HERMODTS_RIB_BONE = 2897;
	private static short HERMODTS_SPINE = 2898;
	private static short HERMODTS_ARM_BONE = 2899;
	private static short HERMODTS_THIGH_BONE = 2900;
	private static short HERMODTS_REMAINS1 = 2901;
	private static short RACOYS_TOTEM = 2902;
	private static short VIVIANTES_LETTER = 2903;
	private static short INSECT_DIAGRAM_BOOK = 2904;
	private static short KIRUNAS_SKULL = 2905;
	private static short KIRUNAS_RIB_BONE = 2906;
	private static short KIRUNAS_SPINE = 2907;
	private static short KIRUNAS_ARM_BONE = 2908;
	private static short KIRUNAS_THIGH_BONE = 2909;
	private static short KIRUNAS_REMAINS1 = 2910;
	private static short BRAKIS_REMAINS2 = 2911;
	private static short TONARS_REMAINS2 = 2912;
	private static short HERMODTS_REMAINS2 = 2913;
	private static short KIRUNAS_REMAINS2 = 2914;

	private static short[] Noble_Ant_Drops = {KIRUNAS_THIGH_BONE, KIRUNAS_ARM_BONE, KIRUNAS_SPINE, KIRUNAS_RIB_BONE, KIRUNAS_SKULL};
	private static short[] Leto_Lizardman_Drops = {TONARS_SKULL, TONARS_RIB_BONE, TONARS_SPINE, TONARS_ARM_BONE, TONARS_THIGH_BONE};
	private static short[] Medusa_Drops = {HERMODTS_RIB_BONE, HERMODTS_SPINE, HERMODTS_THIGH_BONE, HERMODTS_ARM_BONE};

	public _233_TestOfWarspirit()
	{
		super(233, "_233_TestOfWarspirit", "Test Of Warspirit");
		addStartNpc(Somak);

		addTalkId(Vivyan);
		addTalkId(Sarien);
		addTalkId(Racoy);
		addTalkId(Manakia);
		addTalkId(Orim);
		addTalkId(Ancestor_Martankus);
		addTalkId(Pekiron);

		addKillId(Porta);
		addKillId(Excuro);
		addKillId(Mordeo);
		addKillId(Noble_Ant);
		addKillId(Noble_Ant_Leader);
		addKillId(Leto_Lizardman_Shaman);
		addKillId(Leto_Lizardman_Overlord);
		addKillId(Medusa);
		addKillId(Stenoa_Gorgon_Queen);
		addKillId(Tamlin_Orc);
		addKillId(Tamlin_Orc_Archer);

		addQuestItem(VENDETTA_TOTEM,
				TAMLIN_ORC_HEAD,
				WARSPIRIT_TOTEM,
				ORIMS_CONTRACT,
				PORTAS_EYE,
				EXCUROS_SCALE,
				MORDEOS_TALON,
				BRAKIS_REMAINS1,
				PEKIRONS_TOTEM,
				TONARS_SKULL,
				TONARS_RIB_BONE,
				TONARS_SPINE,
				TONARS_ARM_BONE,
				TONARS_THIGH_BONE,
				TONARS_REMAINS1,
				MANAKIAS_TOTEM,
				HERMODTS_SKULL,
				HERMODTS_RIB_BONE,
				HERMODTS_SPINE,
				HERMODTS_ARM_BONE,
				HERMODTS_THIGH_BONE,
				HERMODTS_REMAINS1,
				RACOYS_TOTEM,
				VIVIANTES_LETTER,
				INSECT_DIAGRAM_BOOK,
				KIRUNAS_SKULL,
				KIRUNAS_RIB_BONE,
				KIRUNAS_SPINE,
				KIRUNAS_ARM_BONE,
				KIRUNAS_THIGH_BONE,
				KIRUNAS_REMAINS1,
				BRAKIS_REMAINS2,
				TONARS_REMAINS2,
				HERMODTS_REMAINS2,
				KIRUNAS_REMAINS2);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30510-05.htm") && st.isCreated())
		{
			if(!st.getPlayer().getVarB("dd3"))
			{
				st.giveItems(Dimensional_Diamond, 64);
				st.getPlayer().setVar("dd3", "1");
			}
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30630-04.htm") && st.isStarted())
			st.giveItems(ORIMS_CONTRACT, 1);
		else if(event.equalsIgnoreCase("30682-02.htm") && st.isStarted())
			st.giveItems(PEKIRONS_TOTEM, 1);
		else if(event.equalsIgnoreCase("30515-02.htm") && st.isStarted())
			st.giveItems(MANAKIAS_TOTEM, 1);
		else if(event.equalsIgnoreCase("30507-02.htm") && st.isStarted())
			st.giveItems(RACOYS_TOTEM, 1);
		else if(event.equalsIgnoreCase("30030-04.htm") && st.isStarted())
			st.giveItems(VIVIANTES_LETTER, 1);
		else if(event.equalsIgnoreCase("30649-03.htm") && st.isStarted() && st.getQuestItemsCount(WARSPIRIT_TOTEM) > 0)
		{
			st.takeItems(WARSPIRIT_TOTEM, -1);
			st.takeItems(BRAKIS_REMAINS2, -1);
			st.takeItems(HERMODTS_REMAINS2, -1);
			st.takeItems(KIRUNAS_REMAINS2, -1);
			st.takeItems(TAMLIN_ORC_HEAD, -1);
			st.takeItems(TONARS_REMAINS2, -1);
			st.giveItems(MARK_OF_WARSPIRIT, 1);
			if(!st.getPlayer().getVarB("q233"))
			{
				st.addExpAndSp(447444, 30704);
				st.rollAndGive(57, 80903, 100);
				st.getPlayer().setVar("q233", "1");
			}
			st.playSound(SOUND_FINISH);
			st.unset("cond");
			st.exitCurrentQuest(true);
		}

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.getQuestItemsCount(MARK_OF_WARSPIRIT) > 0)
		{
			st.exitCurrentQuest(true);
			return "completed";
		}
		int npcId = npc.getNpcId();
		if(st.isCreated())
		{
			if(npcId != Somak)
				return "noquest";
			if(st.getPlayer().getRace().ordinal() != 3)
			{
				st.exitCurrentQuest(true);
				return "30510-01.htm";
			}
			if(st.getPlayer().getClassId().getId() != 0x32)
			{
				st.exitCurrentQuest(true);
				return "30510-02.htm";
			}
			if(st.getPlayer().getLevel() < 39)
			{
				st.exitCurrentQuest(true);
				return "30510-03.htm";
			}
			st.set("cond", "0");
			return "30510-04.htm";
		}

		if(!st.isStarted() || st.getInt("cond") != 1)
			return "noquest";

		if(npcId == Somak)
		{
			if(st.getQuestItemsCount(VENDETTA_TOTEM) > 0)
			{
				if(st.getQuestItemsCount(TAMLIN_ORC_HEAD) < 13)
					return "30510-08.htm";
				st.takeItems(VENDETTA_TOTEM, -1);
				st.giveItems(WARSPIRIT_TOTEM, 1);
				st.giveItems(BRAKIS_REMAINS2, 1);
				st.giveItems(HERMODTS_REMAINS2, 1);
				st.giveItems(KIRUNAS_REMAINS2, 1);
				st.giveItems(TONARS_REMAINS2, 1);
				st.playSound(SOUND_MIDDLE);
				return "30510-09.htm";
			}
			if(st.getQuestItemsCount(WARSPIRIT_TOTEM) > 0)
				return "30510-10.htm";
			if(st.getQuestItemsCount(BRAKIS_REMAINS1) == 0 || st.getQuestItemsCount(HERMODTS_REMAINS1) == 0 || st.getQuestItemsCount(KIRUNAS_REMAINS1) == 0 || st.getQuestItemsCount(TONARS_REMAINS1) == 0)
				return "30510-06.htm";
			st.takeItems(BRAKIS_REMAINS1, -1);
			st.takeItems(HERMODTS_REMAINS1, -1);
			st.takeItems(KIRUNAS_REMAINS1, -1);
			st.takeItems(TONARS_REMAINS1, -1);
			st.giveItems(VENDETTA_TOTEM, 1);
			st.playSound(SOUND_MIDDLE);
			return "30510-07.htm";
		}

		if(npcId == Orim)
		{
			if(st.getQuestItemsCount(ORIMS_CONTRACT) > 0)
			{
				if(st.getQuestItemsCount(PORTAS_EYE) < 10 || st.getQuestItemsCount(EXCUROS_SCALE) < 10 || st.getQuestItemsCount(MORDEOS_TALON) < 10)
					return "30630-05.htm";
				st.takeItems(ORIMS_CONTRACT, -1);
				st.takeItems(PORTAS_EYE, -1);
				st.takeItems(EXCUROS_SCALE, -1);
				st.takeItems(MORDEOS_TALON, -1);
				st.giveItems(BRAKIS_REMAINS1, 1);
				st.playSound(SOUND_MIDDLE);
				return "30630-06.htm";
			}
			if(st.getQuestItemsCount(BRAKIS_REMAINS1) == 0 && st.getQuestItemsCount(BRAKIS_REMAINS2) == 0 && st.getQuestItemsCount(VENDETTA_TOTEM) == 0)
				return "30630-01.htm";
			return "30630-07.htm";
		}

		if(npcId == Pekiron)
		{
			if(st.getQuestItemsCount(PEKIRONS_TOTEM) > 0)
			{
				for(short drop_id : Leto_Lizardman_Drops)
					if(st.getQuestItemsCount(drop_id) == 0)
						return "30682-03.htm";
				st.takeItems(PEKIRONS_TOTEM, -1);
				for(short drop_id : Leto_Lizardman_Drops)
					if(st.getQuestItemsCount(drop_id) == 0)
						st.takeItems(drop_id, -1);
				st.giveItems(TONARS_REMAINS1, 1);
				st.playSound(SOUND_MIDDLE);
				return "30682-04.htm";
			}
			if(st.getQuestItemsCount(TONARS_REMAINS1) == 0 && st.getQuestItemsCount(TONARS_REMAINS2) == 0 && st.getQuestItemsCount(VENDETTA_TOTEM) == 0)
				return "30682-01.htm";
			return "30682-05.htm";
		}

		if(npcId == Manakia)
		{
			if(st.getQuestItemsCount(MANAKIAS_TOTEM) > 0)
			{
				if(st.getQuestItemsCount(HERMODTS_SKULL) == 0)
					return "30515-03.htm";
				for(short drop_id : Medusa_Drops)
					if(st.getQuestItemsCount(drop_id) == 0)
						return "30515-03.htm";
				st.takeItems(MANAKIAS_TOTEM, -1);
				st.takeItems(HERMODTS_SKULL, -1);
				for(short drop_id : Medusa_Drops)
					if(st.getQuestItemsCount(drop_id) == 0)
						st.takeItems(drop_id, -1);
				st.giveItems(HERMODTS_REMAINS1, 1);
				st.playSound(SOUND_MIDDLE);
				return "30515-04.htm";
			}
			if(st.getQuestItemsCount(HERMODTS_REMAINS1) == 0 && st.getQuestItemsCount(HERMODTS_REMAINS2) == 0 && st.getQuestItemsCount(VENDETTA_TOTEM) == 0)
				return "30515-01.htm";
			if(st.getQuestItemsCount(RACOYS_TOTEM) == 0 && (st.getQuestItemsCount(KIRUNAS_REMAINS2) > 0 || st.getQuestItemsCount(WARSPIRIT_TOTEM) > 0 || st.getQuestItemsCount(BRAKIS_REMAINS2) > 0 || st.getQuestItemsCount(HERMODTS_REMAINS2) > 0 || st.getQuestItemsCount(TAMLIN_ORC_HEAD) > 0 || st.getQuestItemsCount(TONARS_REMAINS2) > 0))
				return "30515-05.htm";
		}

		if(npcId == Racoy)
			if(st.getQuestItemsCount(RACOYS_TOTEM) > 0)
			{
				if(st.getQuestItemsCount(INSECT_DIAGRAM_BOOK) == 0)
					return st.getQuestItemsCount(VIVIANTES_LETTER) == 0 ? "30507-03.htm" : "30507-04.htm";
				if(st.getQuestItemsCount(VIVIANTES_LETTER) == 0)
				{
					for(short drop_id : Noble_Ant_Drops)
						if(st.getQuestItemsCount(drop_id) == 0)
							return "30507-05.htm";
					st.takeItems(RACOYS_TOTEM, -1);
					st.takeItems(INSECT_DIAGRAM_BOOK, -1);
					for(short drop_id : Noble_Ant_Drops)
						if(st.getQuestItemsCount(drop_id) == 0)
							st.takeItems(drop_id, -1);
					st.giveItems(KIRUNAS_REMAINS1, 1);
					st.playSound(SOUND_MIDDLE);
					return "30507-06.htm";
				}
			}
			else
			{
				if(st.getQuestItemsCount(KIRUNAS_REMAINS1) == 0 && st.getQuestItemsCount(KIRUNAS_REMAINS2) == 0 && st.getQuestItemsCount(VENDETTA_TOTEM) == 0)
					return "30507-01.htm";
				return "30507-07.htm";
			}

		if(npcId == Vivyan)
			if(st.getQuestItemsCount(RACOYS_TOTEM) > 0)
			{
				if(st.getQuestItemsCount(INSECT_DIAGRAM_BOOK) == 0)
					return st.getQuestItemsCount(VIVIANTES_LETTER) == 0 ? "30030-01.htm" : "30030-05.htm";
				if(st.getQuestItemsCount(VIVIANTES_LETTER) == 0)
					return "30030-06.htm";
			}
			else if(st.getQuestItemsCount(KIRUNAS_REMAINS1) == 0 && st.getQuestItemsCount(KIRUNAS_REMAINS2) == 0 && st.getQuestItemsCount(VENDETTA_TOTEM) == 0)
				return "30030-07.htm";

		if(npcId == Sarien)
			if(st.getQuestItemsCount(RACOYS_TOTEM) > 0)
			{
				if(st.getQuestItemsCount(INSECT_DIAGRAM_BOOK) == 0 && st.getQuestItemsCount(VIVIANTES_LETTER) > 0)
				{
					st.takeItems(VIVIANTES_LETTER, -1);
					st.giveItems(INSECT_DIAGRAM_BOOK, 1);
					st.playSound(SOUND_MIDDLE);
					return "30436-01.htm";
				}
				if(st.getQuestItemsCount(VIVIANTES_LETTER) == 0 && st.getQuestItemsCount(INSECT_DIAGRAM_BOOK) > 0)
					return "30436-02.htm";
			}
			else if(st.getQuestItemsCount(KIRUNAS_REMAINS1) == 0 && st.getQuestItemsCount(KIRUNAS_REMAINS2) == 0 && st.getQuestItemsCount(VENDETTA_TOTEM) == 0)
				return "30436-03.htm";

		if(npcId == Ancestor_Martankus && st.getQuestItemsCount(WARSPIRIT_TOTEM) > 0)
			return "30649-01.htm";

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted() || st.getInt("cond") < 1)
			return;

		int npcId = npc.getNpcId();

		if(npcId == Porta && st.getQuestItemsCount(ORIMS_CONTRACT) > 0 && st.rollAndGiveLimited(PORTAS_EYE, 1, 100, 10))
		{
			st.playSound(st.getQuestItemsCount(PORTAS_EYE) == 10 ? SOUND_MIDDLE : SOUND_ITEMGET);
		}
		else if(npcId == Excuro && st.getQuestItemsCount(ORIMS_CONTRACT) > 0 && st.rollAndGiveLimited(EXCUROS_SCALE, 1, 100, 10))
		{
			st.playSound(st.getQuestItemsCount(EXCUROS_SCALE) == 10 ? SOUND_MIDDLE : SOUND_ITEMGET);
		}
		else if(npcId == Mordeo && st.getQuestItemsCount(ORIMS_CONTRACT) > 0 && st.rollAndGiveLimited(MORDEOS_TALON, 1, 100, 10))
		{
			st.playSound(st.getQuestItemsCount(MORDEOS_TALON) == 10 ? SOUND_MIDDLE : SOUND_ITEMGET);
		}
		else if((npcId == Noble_Ant || npcId == Noble_Ant_Leader) && st.getQuestItemsCount(RACOYS_TOTEM) > 0)
		{
			FastList<Integer> drops = new FastList<Integer>();
			for(short drop_id : Noble_Ant_Drops)
				if(st.getQuestItemsCount(drop_id) == 0)
					drops.add((int) drop_id);
			if(drops.size() > 0 && Rnd.chance(30))
			{
				int drop_id = drops.get(Rnd.get(drops.size()));
				st.giveItems(drop_id, 1);
				st.playSound(drops.size() == 1 ? SOUND_MIDDLE : SOUND_ITEMGET);
			}
			drops.clear();
			drops = null;
		}
		else if((npcId == Leto_Lizardman_Shaman || npcId == Leto_Lizardman_Overlord) && st.getQuestItemsCount(PEKIRONS_TOTEM) > 0)
		{
			FastList<Integer> drops = new FastList<Integer>();
			for(short drop_id : Leto_Lizardman_Drops)
				if(st.getQuestItemsCount(drop_id) == 0)
					drops.add((int) drop_id);
			if(drops.size() > 0 && Rnd.chance(25))
			{
				int drop_id = drops.get(Rnd.get(drops.size()));
				st.giveItems(drop_id, 1);
				st.playSound(drops.size() == 1 ? SOUND_MIDDLE : SOUND_ITEMGET);
			}
			drops.clear();
			drops = null;
		}
		else if(npcId == Medusa && st.getQuestItemsCount(MANAKIAS_TOTEM) > 0)
		{
			FastList<Integer> drops = new FastList<Integer>();
			for(short drop_id : Medusa_Drops)
				if(st.getQuestItemsCount(drop_id) == 0)
					drops.add((int) drop_id);
			if(drops.size() > 0 && Rnd.chance(30))
			{
				int drop_id = drops.get(Rnd.get(drops.size()));
				st.giveItems(drop_id, 1);
				st.playSound(drops.size() == 1 && st.getQuestItemsCount(HERMODTS_SKULL) > 0 ? SOUND_MIDDLE : SOUND_ITEMGET);
			}
			drops.clear();
			drops = null;
		}
		else if(npcId == Stenoa_Gorgon_Queen && st.getQuestItemsCount(MANAKIAS_TOTEM) > 0 && st.getQuestItemsCount(HERMODTS_SKULL) == 0 && Rnd.chance(30))
		{
			st.giveItems(HERMODTS_SKULL, 1);
			boolean _allset = true;
			for(short drop_id : Medusa_Drops)
				if(st.getQuestItemsCount(drop_id) == 0)
				{
					_allset = false;
					break;
				}
			st.playSound(_allset ? SOUND_MIDDLE : SOUND_ITEMGET);
		}
		else if((npcId == Tamlin_Orc || npcId == Tamlin_Orc_Archer) && st.getQuestItemsCount(VENDETTA_TOTEM) > 0 &&
				st.rollAndGiveLimited(TAMLIN_ORC_HEAD, 1, npcId == Tamlin_Orc ? 30 : 50, 13))
		{
			st.playSound(st.getQuestItemsCount(TAMLIN_ORC_HEAD) == 13 ? SOUND_MIDDLE : SOUND_ITEMGET);
		}

	}

}