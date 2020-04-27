package quests._337_AudienceWithLandDragon;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Location;

public class _337_AudienceWithLandDragon extends Quest
{
	//npc
	public final int MOKE = 30498;
	public final int HELTON = 30678;
	public final int CHAKIRIS = 30705;
	public final int KAIENA = 30720;
	public final int GABRIELLE = 30753;
	public final int GILMORE = 30754;
	public final int THEODRIC = 30755;
	public final int KENDRA = 30851;
	public final int ORVEN = 30857;
	//mobs
	public final int MARSH_STALKER = 20679;
	public final int MARSH_DRAKE = 20680;
	public final int BLOOD_QUEEN = 18001;
	public final int HARIT_LIZARDMAN_SHAMAN = 20644;
	public final int HARIT_LIZARDMAN_MATRIARCH = 20645;
	public final int HAMRUT = 20649;
	public final int KRANROT = 20650;
	public final int CAVE_MAIDEN = 20287;
	public final int CAVE_MAIDEN1 = 20134;
	public final int CAVE_KEEPER = 20277;
	public final int CAVE_KEEPER1 = 20246;
	public final int ABYSSAL_JEWEL_1 = 27165;
	public final int ABYSSAL_JEWEL_2 = 27166;
	public final int ABYSSAL_JEWEL_3 = 27167;
	public final int JEWEL_GUARDIAN_MARA = 27168;
	public final int JEWEL_GUARDIAN_MUSFEL = 27169;
	public final int JEWEL_GUARDIAN_PYTON = 27170;
	public final int SACRIFICE_OF_THE_SACRIFICED = 27171;
	public final int HARIT_LIZARDMAN_ZEALOT = 27172;
	//items
	public final int FEATHER_OF_GABRIELLE_ID = 3852;
	public final int STALKER_HORN_ID = 3853;
	public final int DRAKE_TALON_ID = 3854;
	public final int REMAINS_OF_SACRIFICED_ID = 3857;
	public final int TOTEM_OF_LAND_DRAGON_ID = 3858;
	public final int HAMRUT_LEG_ID = 3856;
	public final int KRANROT_SKIN_ID = 3855;
	public final int MARA_FANG_ID = 3862;
	public final int MUSFEL_FANG_ID = 3863;
	public final int FIRST_ABYSS_FRAGMENT_ID = 3859;
	public final int SECOND_ABYSS_FRAGMENT_ID = 3860;
	public final int THIRD_ABYSS_FRAGMENT_ID = 3861;
	public final int HERALD_OF_SLAYER_ID = 3890;
	public final int PORTAL_STONE_ID = 3865;
	public final int MARK_OF_WATCHMAN_ID = 3864;

	//	# [STEP, MOB, ITEM, NEED_COUNT, CHANCE, DROP]
	public final int[][] DROPLIST = {
			{1, MARSH_STALKER, STALKER_HORN_ID, 1, 50, 1},
			{1, MARSH_DRAKE, DRAKE_TALON_ID, 1, 50, 1},
			{1, SACRIFICE_OF_THE_SACRIFICED, REMAINS_OF_SACRIFICED_ID, 1, 50, 1},
			{1, HARIT_LIZARDMAN_ZEALOT, TOTEM_OF_LAND_DRAGON_ID, 1, 50, 1},
			{1, HAMRUT, HAMRUT_LEG_ID, 1, 50, 1},
			{1, KRANROT, KRANROT_SKIN_ID, 1, 50, 1},
			{2, JEWEL_GUARDIAN_MARA, MARA_FANG_ID, 1, 50, 1},
			{2, ABYSSAL_JEWEL_1, FIRST_ABYSS_FRAGMENT_ID, 1, 100, 1},
			{2, JEWEL_GUARDIAN_MUSFEL, MUSFEL_FANG_ID, 1, 50, 1},
			{2, ABYSSAL_JEWEL_2, SECOND_ABYSS_FRAGMENT_ID, 1, 100, 1},
			{4, ABYSSAL_JEWEL_3, THIRD_ABYSS_FRAGMENT_ID, 1, 100, 1},};
	//	  # [STEP, MOB, SPWN_MOB, SPWN_COUNT,]
	public final int[][] ATTACKLIST = {
			{2, ABYSSAL_JEWEL_1, JEWEL_GUARDIAN_MARA, 20},
			{2, ABYSSAL_JEWEL_2, JEWEL_GUARDIAN_MUSFEL, 20},
			{4, ABYSSAL_JEWEL_3, JEWEL_GUARDIAN_PYTON, 6},};

	public final int[][] KILLLIST = {
			{1, BLOOD_QUEEN, SACRIFICE_OF_THE_SACRIFICED, 6},
			{1, HARIT_LIZARDMAN_SHAMAN, HARIT_LIZARDMAN_ZEALOT, 4},
			{1, HARIT_LIZARDMAN_MATRIARCH, HARIT_LIZARDMAN_ZEALOT, 4},
			{4, CAVE_KEEPER, ABYSSAL_JEWEL_3, 1},
			{4, CAVE_MAIDEN, ABYSSAL_JEWEL_3, 1},
			{4, CAVE_KEEPER1, ABYSSAL_JEWEL_3, 1},
			{4, CAVE_MAIDEN1, ABYSSAL_JEWEL_3, 1},};

	public void onLoad()
	{
		L2ObjectsStorage.getByNpcId(ABYSSAL_JEWEL_1).setImobilised(true);
		L2ObjectsStorage.getByNpcId(ABYSSAL_JEWEL_2).setImobilised(true);
		saveGlobalQuestVar("guard27165", "false");
		saveGlobalQuestVar("guard27166", "false");
		saveGlobalQuestVar("guard27167", "false");
	}

	public _337_AudienceWithLandDragon()
	{
		super(337, "_337_AudienceWithLandDragon", "Audience with the Land Dragon");

		addStartNpc(GABRIELLE);

		addTalkId(MOKE);
		addTalkId(HELTON);
		addTalkId(CHAKIRIS);
		addTalkId(KAIENA);
		addTalkId(GABRIELLE);
		addTalkId(GILMORE);
		addTalkId(THEODRIC);
		addTalkId(KENDRA);
		addTalkId(ORVEN);

		addKillId(BLOOD_QUEEN);
		addKillId(MARSH_STALKER);
		addKillId(MARSH_DRAKE);
		addKillId(SACRIFICE_OF_THE_SACRIFICED);
		addKillId(HARIT_LIZARDMAN_SHAMAN);
		addKillId(HARIT_LIZARDMAN_MATRIARCH);
		addKillId(HARIT_LIZARDMAN_ZEALOT);
		addKillId(HAMRUT);
		addKillId(KRANROT);
		addKillId(ABYSSAL_JEWEL_1);
		addKillId(ABYSSAL_JEWEL_2);
		addKillId(CAVE_KEEPER);
		addKillId(CAVE_KEEPER1);
		addKillId(CAVE_MAIDEN);
		addKillId(CAVE_MAIDEN1);
		addKillId(ABYSSAL_JEWEL_3);
		addKillId(JEWEL_GUARDIAN_MARA);
		addKillId(JEWEL_GUARDIAN_MUSFEL);
		addKillId(JEWEL_GUARDIAN_PYTON);

		addAttackId(ABYSSAL_JEWEL_1);
		addAttackId(ABYSSAL_JEWEL_2);
		addAttackId(ABYSSAL_JEWEL_3);

		addQuestItem(FEATHER_OF_GABRIELLE_ID,
				HERALD_OF_SLAYER_ID,
				STALKER_HORN_ID,
				DRAKE_TALON_ID,
				REMAINS_OF_SACRIFICED_ID,
				TOTEM_OF_LAND_DRAGON_ID,
				HAMRUT_LEG_ID,
				KRANROT_SKIN_ID,
				MARA_FANG_ID,
				FIRST_ABYSS_FRAGMENT_ID,
				MUSFEL_FANG_ID,
				SECOND_ABYSS_FRAGMENT_ID,
				THIRD_ABYSS_FRAGMENT_ID,
				MARK_OF_WATCHMAN_ID);
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		// Jewel 1
		if(event.equalsIgnoreCase("guard27165"))
			saveGlobalQuestVar("guard27165", "false");
			// Jewel 2
		else if(event.equalsIgnoreCase("guard27166"))
			saveGlobalQuestVar("guard27166", "false");
			// Jewel 3
		else if(event.equalsIgnoreCase("guard27167"))
			saveGlobalQuestVar("guard27167", "false");
		return null;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{

		if(event.equalsIgnoreCase("jewel3_fail"))
		{
			st.set("jewel3", "0");
			return null;
		}

		if(event.equalsIgnoreCase("30753-02.htm"))
			st.exitCurrentQuest(true);
		else if(event.equalsIgnoreCase("30753-06.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.set("all", "0");
			st.set("orven", "0");
			st.set("kendra", "0");
			st.set("chakiris", "0");
			st.set("kaiena", "0");
			st.set("moke", "0");
			st.set("helton", "0");
			st.set("jewel3", "0");
			st.giveItems(FEATHER_OF_GABRIELLE_ID, 1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30753-10.htm"))
		{
			st.set("cond", "2");
			st.takeItems(MARK_OF_WATCHMAN_ID, -1);
		}
		else if(event.equalsIgnoreCase("30754-03.htm"))
			st.set("cond", "4");
		else if(event.equalsIgnoreCase("30755-05.htm"))
		{
			st.giveItems(PORTAL_STONE_ID, 1);
			st.takeItems(HERALD_OF_SLAYER_ID, -1);
			st.takeItems(THIRD_ABYSS_FRAGMENT_ID, -1);
			st.playSound(SOUND_FINISH);
			st.set("jewel3", "0");
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == GABRIELLE)
			if(st.isCreated())
				if(st.getPlayer().getLevel() >= 50)
					htmltext = "30753-03.htm";
				else
					htmltext = "30753-01.htm";
			else if(cond == 1)
				if(st.getInt("all") == 0)
					htmltext = "30753-07.htm";
				else
					htmltext = "30753-09.htm";
			else if(cond == 2)
				if(st.getInt("all") < 2)
					htmltext = "30753-11.htm";
				else
				{
					htmltext = "30753-12.htm";
					st.takeItems(MARK_OF_WATCHMAN_ID, -1);
					st.takeItems(FEATHER_OF_GABRIELLE_ID, -1);
					st.giveItems(HERALD_OF_SLAYER_ID, 1);
					st.set("cond", "3");
				}
			else if(cond == 3)
				htmltext = "30753-13.htm";
			else if(cond == 4)
				htmltext = "30753-14.htm";
		if(npcId == CHAKIRIS)
			if(st.getInt("all") == 1)  // if all 4 tasks have been done
				htmltext = "30705-04.htm";
			else if(st.getInt("chakiris") == 1) // if not all 4 are done
				htmltext = "30705-03.htm";
			else if(cond == 1)
				if(st.getQuestItemsCount(HAMRUT_LEG_ID) == 0 || st.getQuestItemsCount(KRANROT_SKIN_ID) == 0)
					htmltext = "30705-01.htm";
				else
				{
					st.giveItems(MARK_OF_WATCHMAN_ID, 1);
					st.takeItems(HAMRUT_LEG_ID, -1);
					st.takeItems(KRANROT_SKIN_ID, -1);
					htmltext = "30705-02.htm";
					st.set("chakiris", "1");
					if(st.getInt("orven") == 1 && st.getInt("kendra") == 1 && st.getInt("chakiris") == 1 && st.getInt("kaiena") == 1)
						st.set("all", "1");
				}
		if(npcId == KAIENA)
			if(st.getInt("all") == 1) //if all 4 tasks have been done
				htmltext = "30720-04.htm";
			else if(st.getInt("kaiena") == 1) // if not all 4 are done
				htmltext = "30720-03.htm";
			else if(cond == 1)
				if(st.getQuestItemsCount(STALKER_HORN_ID) == 0 || st.getQuestItemsCount(DRAKE_TALON_ID) == 0)
					htmltext = "30720-01.htm";
				else
				{
					st.giveItems(MARK_OF_WATCHMAN_ID, 1);
					st.takeItems(STALKER_HORN_ID, -1);
					st.takeItems(DRAKE_TALON_ID, -1);
					htmltext = "30720-02.htm";
					st.set("kaiena", "1");
					if(st.getInt("orven") == 1 && st.getInt("kendra") == 1 && st.getInt("chakiris") == 1 && st.getInt("kaiena") == 1)
						st.set("all", "1");
				}
		if(npcId == KENDRA)
			if(st.getInt("all") == 1) // if all 4 tasks have been done
				htmltext = "30851-04.htm";
			else if(st.getInt("kendra") == 1) // if not all 4 are done
				htmltext = "30851-03.htm";
			else if(cond == 1)
				if(st.getQuestItemsCount(TOTEM_OF_LAND_DRAGON_ID) == 0)
					htmltext = "30851-01.htm";
				else
				{
					st.giveItems(MARK_OF_WATCHMAN_ID, 1);
					st.takeItems(TOTEM_OF_LAND_DRAGON_ID, -1);
					htmltext = "30851-02.htm";
					st.set("kendra", "1");
					if(st.getInt("orven") == 1 && st.getInt("kendra") == 1 && st.getInt("chakiris") == 1 && st.getInt("kaiena") == 1)
						st.set("all", "1");
				}
		if(npcId == ORVEN)
			if(st.getInt("all") == 1) //if all 4 tasks have been done
				htmltext = "30857-04.htm";
			else if(st.getInt("orven") == 1) //if not all 4 are done
				htmltext = "30857-03.htm";
			else if(cond == 1)
				if(st.getQuestItemsCount(REMAINS_OF_SACRIFICED_ID) == 0)
					htmltext = "30857-01.htm";
				else
				{
					st.giveItems(MARK_OF_WATCHMAN_ID, 1);
					st.takeItems(REMAINS_OF_SACRIFICED_ID, -1);
					htmltext = "30857-02.htm";
					st.set("orven", "1");
					if(st.getInt("orven") == 1 && st.getInt("kendra") == 1 && st.getInt("chakiris") == 1 && st.getInt("kaiena") == 1)
						st.set("all", "1");
				}
		if(npcId == MOKE)
			if(st.getInt("all") == 2)
				htmltext = "30498-05.htm";
			else if(st.getInt("moke") == 1)
				htmltext = "30498-04.htm";
			else if(cond == 2)
				if(st.getQuestItemsCount(MARA_FANG_ID) == 0 || st.getQuestItemsCount(FIRST_ABYSS_FRAGMENT_ID) == 0)
					htmltext = "30498-01.htm";
				else
				{
					htmltext = "30498-03.htm";
					st.giveItems(MARK_OF_WATCHMAN_ID, 1);
					st.takeItems(MARA_FANG_ID, -1);
					st.takeItems(FIRST_ABYSS_FRAGMENT_ID, -1);
					if(st.getInt("helton") == 1)
						st.set("all", "2");
					else
						st.set("moke", "1");
				}
		if(npcId == HELTON)
			if(st.getInt("all") == 2)
				htmltext = "30678-05.htm";
			else if(st.getInt("helton") == 1)
				htmltext = "30678-04.htm";
			else if(cond == 2)
				if(st.getQuestItemsCount(MUSFEL_FANG_ID) == 0 || st.getQuestItemsCount(SECOND_ABYSS_FRAGMENT_ID) == 0)
					htmltext = "30678-01.htm";
				else
				{
					htmltext = "30678-03.htm";
					st.giveItems(MARK_OF_WATCHMAN_ID, 1);
					st.takeItems(MUSFEL_FANG_ID, -1);
					st.takeItems(SECOND_ABYSS_FRAGMENT_ID, -1);
					if(st.getInt("moke") == 1)
						st.set("all", "2");
					else
						st.set("helton", "1");
				}
		if(npcId == GILMORE)
			if(cond < 3)
				htmltext = "30754-01.htm";
			else if(cond == 3 && st.getQuestItemsCount(HERALD_OF_SLAYER_ID) == 1)
				htmltext = "30754-02.htm";
			else if(cond == 4)
				if(st.getQuestItemsCount(THIRD_ABYSS_FRAGMENT_ID) == 1)
					htmltext = "30754-05.htm";
				else
					htmltext = "30754-04.htm";
		if(npcId == THEODRIC)
			if(cond < 3)
				htmltext = "30755-01.htm";
			else if(cond == 3 && st.getQuestItemsCount(HERALD_OF_SLAYER_ID) == 1)
				htmltext = "30755-02.htm";
			else if(cond == 4)
				if(st.getQuestItemsCount(THIRD_ABYSS_FRAGMENT_ID) == 0)
					htmltext = "30755-03.htm";
				else
					htmltext = "30755-04.htm";

		return htmltext;
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		String guardSpawn = "guard" + npcId;

		if(npcId == ABYSSAL_JEWEL_1 && st.getInt("moke") == 1)
			return null;
		if(npcId == ABYSSAL_JEWEL_2 && st.getInt("helton") == 1)
			return null;

		boolean guardSpawned;
		try
		{
			guardSpawned = Boolean.parseBoolean(loadGlobalQuestVar(guardSpawn));
		}
		catch(Exception e)
		{
			guardSpawned = false;
			e.printStackTrace();
		}
		for(int[] element : ATTACKLIST)
			//	# [COND, MOB, SPWN_MOB, SPWN_COUNT]
			if(npcId == element[1] && cond == element[0] && npc.getMaxHp() / 2 > npc.getCurrentHp() && !guardSpawned)
			{
				Location loc = npc.getLoc();
				for(int j = 0; j < element[3]; j++)
				{
					L2NpcInstance mob = addSpawn(element[2], loc, true, 240000);
					mob.addDamageHate(st.getPlayer(), 0, 1000);
				}
				st.playSound(SOUND_BEFORE_BATTLE);
				saveGlobalQuestVar(guardSpawn, "true");
				startQuestTimer(guardSpawn, 240000, null, null, true);
			}
			else if(npcId == element[1] && cond == element[0] && npc.getMaxHp() / 4 > npc.getCurrentHp())
				for(int[] drop : DROPLIST)
					if(npcId == drop[1] && cond == drop[0] && st.getQuestItemsCount(drop[2]) < drop[3] && Rnd.chance(drop[4]))
					{
						st.giveItems(drop[2], 1);
						st.playSound(SOUND_ITEMGET);
					}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		for(int[] element : DROPLIST)
			//	# [STEP, ID, ITEM, NEED_COUNT, CHANCE, DROP]
			if(npcId == element[1] && cond == element[0] && st.getQuestItemsCount(element[2]) < element[3] && Rnd.chance(element[4]))
			{
				st.giveItems(element[2], element[5]);
				st.playSound(SOUND_ITEMGET);
			}
		for(int[] element : KILLLIST)
		{
			if((npcId == CAVE_KEEPER || npcId == CAVE_KEEPER1 || npcId == CAVE_MAIDEN || npcId == CAVE_MAIDEN1) && st.getInt("jewel3") == 1)
				return;
			//	# [STEP, MOB, SPWN_MOB, SPWN_COUNT]
			if(cond == element[0] && npcId == element[1] && Rnd.chance(25))
			{
				Location loc = npc.getLoc();
				for(int j = 0; j < element[3]; j++)
				{
					L2NpcInstance mob = addSpawn(element[2], loc, true, 240000);
					if(element[2] == ABYSSAL_JEWEL_3)
						mob.setImobilised(true);
					mob.addDamageHate(st.getPlayer(), 0, 100000);
				}
				st.playSound(SOUND_BEFORE_BATTLE);
				if(element[2] == ABYSSAL_JEWEL_3)
				{
					st.set("jewel3", "1");
					st.startQuestTimer("jewel3_fail", 240000);
				}
			}
		}
	}
}