package events.NPCBuffer;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.siege.Siege;
import ru.l2gw.gameserver.model.entity.siege.SiegeUnit;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

public class Buffer extends Functions implements ScriptFile
{
	private static boolean _active = false;
	static int usual = 3;
	static int dance = 30;
	static int chov = 12;

	/**
	 * Количество бафов в группе
	 */
	private static int grpCount;

	private static int pet_buffs[][] = {

	};

	// Количество бафов в 1 и второй группах должно быть одинаковое
	private static int[][] buffs = { // id, lvl, group
			// Chants
			{1251, 2, 5, usual}, // Chant of Fury
			{1252, 3, 5, usual}, // Chant of Evasion
			{1253, 3, 5, usual}, // Chant of Rage
			{1284, 3, 5, usual}, // Chant of Revenge
			{1308, 3, 5, usual}, // Chant of Predator
			{1309, 3, 5, usual}, // Chant of Eagle
			{1310, 4, 5, usual}, // Chant of Vampire
			{1362, 1, 5, usual}, // Chant of Spirit
			{1363, 1, 5, chov}, // Chant of Victory
			{1390, 3, 5, usual}, // War Chant
			{1391, 3, 5, usual}, // Earth Chant
			// Songs
			{264, 1, 4, dance}, // Song of Earth
			{265, 1, 4, dance}, // Song of Life
			{266, 1, 4, dance}, // Song of Water
			{267, 1, 4, dance}, // Song of Warding
			{268, 1, 4, dance}, // Song of Wind
			{269, 1, 4, dance}, // Song of Hunter
			{270, 1, 4, dance}, // Song of Invocation
			{304, 1, 4, dance}, // Song of Vitality
			{305, 1, 4, dance}, // Song of Vengeance
			{306, 1, 4, dance}, // Song of Flame Guard
			{308, 1, 4, dance}, // Song of Storm Guard
			{349, 1, 4, dance}, // Song of Renewal
			{363, 1, 4, dance}, // Song of Meditation
			{364, 1, 4, dance}, // Song of Champion
			// Dances
			{271, 1, 3, dance}, // Dance of Warrior
			{272, 1, 3, dance}, // Dance of Inspiration
			{273, 1, 3, dance}, // Dance of Mystic
			{274, 1, 3, dance}, // Dance of Fire
			{275, 1, 3, dance}, // Dance of Fury
			{276, 1, 3, dance}, // Dance of Concentration
			{277, 1, 3, dance}, // Dance of Light
			{307, 1, 3, dance}, // Dance of Aqua Guard
			{309, 1, 3, dance}, // Dance of Earth Guard
			{310, 1, 3, dance}, // Dance of Vampire
			{311, 1, 3, dance}, // Dance of Protection
			{365, 1, 3, dance}, // Dance of Siren
			// Группа для магов 2
			{7059, 1, 2, usual}, // Wild Magic
			{4356, 3, 2, usual}, // Empower
			{4355, 3, 2, usual}, // Acumen
			{4352, 1, 2, usual}, // Berserker Spirit
			{4346, 4, 2, usual}, // Mental Shield
			{4351, 6, 2, usual}, // Concentration
			{4342, 2, 2, usual}, // Wind Walk
			{4347, 6, 2, usual}, // Bless the Body
			{4348, 6, 2, usual}, // Bless the Soul
			{4344, 3, 2, usual}, // Shield
			{7060, 1, 2, usual}, // Clarity
			{4350, 4, 2, usual}, // Resist Shock
			// Группа для воинов 1
			{7057, 1, 1, usual}, // Greater Might
			{4345, 3, 1, usual}, // Might
			{4344, 3, 1, usual}, // Shield
			{4349, 2, 1, usual}, // Magic Barrier
			{4342, 2, 1, usual}, // Wind Walk
			{4347, 6, 1, usual}, // Bless the Body
			{4357, 2, 1, usual}, // Haste
			{4359, 3, 1, usual}, // Focus
			{4358, 3, 1, usual}, // Guidance
			{4360, 3, 1, usual}, // Death Whisper
			{4354, 4, 1, usual}, // Vampiric Rage
			{4346, 4, 1, usual}, // Mental Shield
			//Тестовая группа для воинов
			{7057, 1, 6, usual}, // Greater Might
			{4345, 3, 6, usual}, // Might
			{4344, 3, 6, usual}, // Shield
			{4349, 2, 6, usual}, // Magic Barrier
			{4342, 2, 6, usual}, // Wind Walk
			{4347, 6, 6, usual}, // Bless the Body
			{4357, 2, 6, usual}, // Haste
			{4359, 3, 6, usual}, // Focus
			{4358, 3, 6, usual}, // Guidance
			{4360, 3, 6, usual}, // Death Whisper
			{4354, 4, 6, usual}, // Vampiric Rage
			{4346, 4, 6, usual}, // Mental Shield
			{275, 1, 6, dance}, // "Dance of Fury"
			{274, 1, 6, dance}, // "Dance of Fire"
			{272, 1, 6, dance}, // "Dance of Inspiration"
			{277, 1, 6, dance}, // "Dance of Light"
			{271, 1, 6, dance}, // "Dance of Warrior"
			{307, 1, 6, dance}, // "Dance of Aqua Guard"
			{309, 1, 6, dance}, // "Dance of Earth Guard"
			{311, 1, 6, dance}, // "Dance of Protection"
			{310, 1, 6, dance}, // "Dance of Vampire"
			{530, 1, 6, dance}, // "Dance of Alignment"
			{264, 1, 6, dance}, // "Song of Earth"
			{265, 1, 6, dance}, // "Song of Life"
			{266, 1, 6, dance}, // "Song of Water"
			{267, 1, 6, dance}, // "Song of Warding"
			{268, 1, 6, dance}, // "Song of Wind"
			{269, 1, 6, dance}, // "Song of Hunter"
			{270, 1, 6, dance}, // "Song of Invocation"
			{304, 1, 6, dance}, // "Song of Vitality"
			{305, 1, 6, dance}, // "Song of Vengeance"
			{306, 1, 6, dance}, // "Song of Flame Guard"
			{308, 1, 6, dance}, // "Song of Storm Guard"
			{349, 1, 6, dance}, // "Song of Renewal"
			{363, 1, 6, dance}, // "Song of Meditation"
			{364, 1, 6, dance}, // "Song of Champion"
			{529, 1, 6, dance}, // "Song of Elemental"
			{1461, 1, 6, 6}, // "Chant of Protection"
			{1284, 3, 6, usual}, // "Chant of Revenge"
			{1006, 4, 6, usual}, // "Chant of Fire"
			{1007, 4, 6, usual}, // "Chant of Battle"
			{1518, 1, 6, usual}, // "Chant of Critical Attack"
			{1519, 1, 6, usual}, // "Chant of Blood Awakening"
			{2505, 1, 6, usual}, // "Chant of Combat"
			{1390, 3, 6, usual}, // "War Chant"
			{1362, 1, 6, usual}, // "Chant of Spirit"
			{1415, 1, 6, usual}, // "Paagrios Emblem"
			//Тестовая группа для магов
			{7059, 1, 7, usual}, // Wild Magic
			{4356, 3, 7, usual}, // Empower
			{4355, 3, 7, usual}, // Acumen
			{4352, 1, 7, usual}, // Berserker Spirit
			{4346, 4, 7, usual}, // Mental Shield
			{4351, 6, 7, usual}, // Concentration
			{4342, 2, 7, usual}, // Wind Walk
			{4347, 6, 7, usual}, // Bless the Body
			{4348, 6, 7, usual}, // Bless the Soul
			{4344, 3, 7, usual}, // Shield
			{7060, 1, 7, usual}, // Clarity
			{4350, 4, 7, usual}, // Resist Shock
			{1461, 1, 7, usual}, // Chant of Protection
			{1284, 3, 7, usual}, // Chant of Revenge"
			{1006, 4, 7, usual}, // Chant of Fire"
			{1519, 1, 7, usual}, // Chant of Blood Awakening"
			{1391, 3, 7, usual}, // Earth Chant"
			{1362, 1, 7, usual}, // Chant of Spirit"
			{1415, 1, 7, usual}, // Paagrios Emblem"
			{273, 1, 7, dance}, // Dance of Mystic"
			{276, 1, 7, dance}, // Dance of concentration"
			{307, 1, 7, dance}, // Dance of Aqua Guard"
			{309, 1, 7, dance}, // Dance of Earth Guard"
			{311, 1, 7, dance}, // Dance of Protection"
			{530, 1, 7, dance}, // Dance of Alignment"
			{365, 1, 7, dance}, // Siren's Dance"
			{264, 1, 7, dance}, // Song of Earth"
			{265, 1, 7, dance}, // Song of Life"
			{266, 1, 7, dance}, // Song of Water"
			{267, 1, 7, dance}, // Song of Warding"
			{268, 1, 7, dance}, // Song of Wind"
			{269, 1, 7, dance}, // Song of Hunter"
			{270, 1, 7, dance}, // Song of Invocation"
			{304, 1, 7, dance}, // Song of Vitality"
			{305, 1, 7, dance}, // Song of Vengeance"
			{306, 1, 7, dance}, // Song of Flame Guard"
			{308, 1, 7, dance}, // Song of Storm Guard"
			{349, 1, 7, dance}, // Song of Renewal"
			{363, 1, 7, dance}, // Song of Meditation"
			{364, 1, 7, dance}, // Song of Champion"
			{529, 1, 7, dance}, // Song of Elemental"
			//Воины
			{1240, 3, 8, usual}, // Guidance
			{1048, 6, 8, usual}, // Blessed Soul
			{1045, 6, 8, usual}, // Blessed Body
			{1259, 4, 8, usual}, // Resist Shock
			{1062, 2, 8, usual}, // Berserker Spirit
			{1035, 4, 8, usual}, // Mental Shield
			//{ 1476, 3, 8, usual }, // Appetite for Destruction
			{4699, 13, 8, chov}, // Blessing of Queen
			//{ 1478, 2, 8, usual }, // Protection Instinct
			{1416, 1, 8, usual}, // Pa'agrio's Fist"
			{1204, 2, 8, usual}, // Wind Walk
			{1461, 1, 8, 6}, // Chant of Protection
			{1006, 3, 8, usual}, // Chant of Fire
			{1007, 3, 8, usual}, // Chant of Battle
			{1518, 1, 8, usual}, // Chant of Critical Attack
			{1519, 1, 8, usual}, // Chant of Blood Awakening
			{1390, 3, 8, usual}, // War Chant
			{1416, 1, 8, usual}, // Pa'agrio's Fist
			{1415, 1, 8, usual}, // Paagrios Emblem
			{1040, 3, 8, usual}, // Shield
			{1397, 3, 8, usual}, // Clarity
			{1087, 3, 8, usual}, // Agility
			{275, 1, 8, dance}, // Dance of Fury
			{274, 1, 8, dance}, // Dance of Fire
			{272, 1, 8, dance}, // Dance of Inspiration
			{277, 1, 8, dance}, // Dance of Light
			{271, 1, 8, dance}, // Dance of Warrior
			{307, 1, 8, dance}, // Dance of Aqua Guard
			{309, 1, 8, dance}, // Dance of Earth Guard
			{311, 1, 8, dance}, // Dance of Protection
			{310, 1, 8, dance}, // Dance of Vampire
			{530, 1, 8, dance}, // Dance of Alignment
			{264, 1, 8, dance}, // Song of Earth
			{265, 1, 8, dance}, // Song of Life
			{266, 1, 8, dance}, // Song of Water
			{267, 1, 8, dance}, // Song of Warding
			{268, 1, 8, dance}, // Song of Wind
			{270, 1, 8, dance}, // Song of Invocation
			{304, 1, 8, dance}, // Song of Vitality
			{305, 1, 8, dance}, // Song of Vengeance
			{306, 1, 8, dance}, // Song of Flame Guard
			{308, 1, 8, dance}, // Song of Storm Guard
			{364, 1, 8, dance}, // Song of Champion
			{363, 1, 8, dance}, // Song of Meditation
			{349, 1, 8, dance}, // Song of Renewal
			{529, 1, 8, dance}, // Song of Elemental
			{269, 1, 8, dance}, // Song of Hunter
			//маги
			{1059, 3, 9, usual}, // Greater Empower
			{1085, 3, 9, usual}, // Acumen
			{1303, 2, 9, usual}, // Wild Magic
			{1062, 2, 9, usual}, // Berserker Spirit
			{1035, 4, 9, usual}, // Mental Shield
			{1078, 6, 9, usual}, // Concentration
			{1204, 2, 9, usual}, // Wind Walk
			{1048, 6, 9, usual}, // Blessed Soul
			{1045, 6, 9, usual}, // Blessed Body
			{1040, 3, 9, usual}, // Shield-
			{1259, 4, 9, usual}, // Resist Shock
			{1397, 3, 9, usual}, // Clarity
			{1284, 3, 9, usual}, // Chant of Revenge
			{1006, 3, 9, usual}, // Chant of Fire
			{1519, 1, 9, usual}, // Chant of Blood Awakening
			{1391, 3, 9, usual}, // Earth Chant
			{1479, 3, 9, chov}, // Magic Impulse
			{1415, 1, 9, usual}, // Paagrios Emblem
			{1416, 1, 9, usual}, // Pa'agrio's Fist/
			//{ 1478, 2, 9, usual }, // Protection Instinct
			{1413, 1, 9, chov}, // Magnus Chant
			{4703, 13, 9, chov}, // Gift of Seraphim
			//{ 1476, 3, 9, usual }, // Appetite for Destruction
			{1087, 3, 9, usual}, // Agility
			{273, 1, 9, dance}, // Dance of Mystic-
			{276, 1, 9, dance}, // Dance of concentration
			{307, 1, 9, dance}, // Dance of Aqua Guard
			{309, 1, 9, dance}, // Dance of Earth Guard
			{311, 1, 9, dance}, // Dance of Protection
			{530, 1, 9, dance}, // Dance of Alignment
			{365, 1, 9, dance}, // Siren's Dance
			{264, 1, 9, dance}, // Song of Earth
			{265, 1, 9, dance}, // Song of Life
			{266, 1, 9, dance}, // Song of Water
			{267, 1, 9, dance}, // Song of Warding
			{268, 1, 9, dance}, // Song of Wind
			{270, 1, 9, dance}, // Song of Invocation
			{304, 1, 9, dance}, // Song of Vitality
			{305, 1, 9, dance}, // Song of Vengeance
			{306, 1, 9, dance}, // Song of Flame Guard
			{308, 1, 9, dance}, // Song of Storm Guard
			{364, 1, 9, dance}, // Song of Champion
			{363, 1, 9, dance}, // Song of Meditation
			{349, 1, 9, dance}, // Song of Renewal
			{529, 1, 9, dance}, // Song of Elemental
			//gw_bafer
//fighter
			{1040, 3, 11, usual},  // Shield
			{1036, 2, 11, usual},  // Magic Barrier
			{1504, 1, 11, usual},  // Improve Movement
			{1502, 1, 11, usual},  // Improve Critical
			{1242, 3, 11, usual},  // Death Whisper
			{1086, 2, 11, usual},  // Haste
			{1388, 3, 11, usual},  // Great Might
			{1389, 3, 11, usual},  // Great Shield
			{1501, 1, 11, usual},  // Improve Condition
			{1035, 4, 11, usual},  // Mental Shield
			{1363, 1, 11, chov},  // Chant of Victory
			{4699, 13, 11, chov},  // Blessing of Queen
/*
			{275, 1, 11, dance},  // Dance of Fury
			{274, 1, 11, dance},  // Dance of Fire
			{271, 1, 11, dance},  // "Dance of Warrior"
			{310, 1, 11, dance},  // "Dance of Vampire"
			{269, 1, 11, dance},  // Song of Hunter
			{264, 1, 11, dance},  // Song of Earth
			{267, 1, 11, dance},  // Song of Warding
			{268, 1, 11, dance},  // Song of Wind
			{364, 1, 11, dance},  // Song of Champion
*/
//mage
			{1040, 3, 12, usual},  // Shield
			{1036, 2, 12, usual},  // Magic Barrier
			{1504, 1, 12, usual},  // Improve Movement
			{1303, 2, 12, usual},  // Wild Magic
			{4356, 3, 12, usual},  // Empower
			{1501, 1, 11, usual},  // Improve Condition
			{1085, 3, 12, usual},  // Acumen
			{1389, 3, 12, usual},  // Great Shield
			{4352, 1, 12, usual},  // Berserker Spirit
			{1397, 3, 12, usual},  // Clarity
			{1078, 6, 12, usual},  // Concentration
			{1035, 4, 12, usual},  // Mental Shield
			{1355, 1, 12, chov},  // Prophecy of Water
			{4703, 13, 12, chov}, // Gift of Seraphim
/*
			{276, 1, 12, dance},  // Dance of concentration
			{273, 1, 12, dance},  // Dance of Mystic"
			{365, 1, 12, dance},  // Dance of Siren
			{264, 1, 12, dance},  // Song of Earth
			{267, 1, 12, dance},  // Song of Warding
			{268, 1, 12, dance},  // Song of Wind
			{363, 1, 12, dance},  // Song of Meditation
			{349, 1, 12, dance},  // Song of Renewal
*/
//from orcs
			{1310, 4, 13, usual},  // Chant of Vampire
			{1007, 3, 13, usual},  // Chant of Battle
			{1252, 3, 13, usual},  // Chant of Evasion
			{1006, 3, 13, usual},  // Chant of Fire
			{1251, 2, 13, usual},  // Chant of Fury
			{1308, 3, 13, usual},  // Chant of Predator
			{1253, 3, 13, usual},  // Chant of Rage
			{1284, 3, 13, usual},  // Chant of Revenge
			{1009, 2, 13, usual},  // Chant of Shielding
			{1002, 3, 13, usual}  // Flame Chant

	};

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			_log.info("Loaded Event: NPCBuffer [state: activated]");
			for(int buff[] : buffs)
				if(buff[2] == 1)
					grpCount++;
		}
		else
			_log.info("Loaded Event: NPCBuffer [state: deactivated]");
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	/**
	 * Читает статус эвента из базы.
	 *
	 * @return status
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("NPCBuffer", "off").equalsIgnoreCase("on");
	}

	/**
	 * Запускает эвент
	 */
	public void startEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(!isActive())
		{
			ServerVariables.set("NPCBuffer", "on");
			_active = true;
			_log.info("Event: NPCBuffer started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.NPCBuffer.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'NPCBuffer' already started.");
		_active = true;
		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	 * Останавливает эвент
	 */
	public void stopEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
		if(isActive())
		{
			ServerVariables.unset("NPCBuffer");
			_log.info("Event: NPCBuffer stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.NPCBuffer.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'NPCBuffer' not started.");
		_active = false;
		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	 * Снять все баффы
	 *
	 * @param args массив строк
	 */
	public void clearBuffs(String[] args)
	{
		L2Player player = (L2Player) self;

		if(npc == null)
			return;

		if(!checkCondition(player))
			return;

		for(L2Effect e : player.getAllEffects())
		{
			if(e.getSkill().isBuff() || e.getSkill().isSongDance())
				e.exit();
		}
	}

	/**
	 * Бафает группу баффов, снимает плату за бафф, отображает диалог с кнопкой возврата к списку бафов
	 *
	 * @param args массив строк, где элемент 0 - id группы бафов
	 */
	public void doBuffGroup(String[] args)
	{
		L2Player player = (L2Player) self;

		if(npc == null)
			return;

		if(!checkCondition(player))
			return;

		if(!player.reduceAdena("Buffer", Config.EVENT_price * grpCount, npc, true))
			return;

		L2Skill skill;
		for(int buff[] : buffs)
			if(buff[2] == Integer.valueOf(args[0]))
			{
				skill = SkillTable.getInstance().getInfo(buff[0], buff[1]);
				//ThreadPoolManager.getInstance().scheduleGeneral(new BeginBuff(npc, skill, player), time);
				ThreadPoolManager.getInstance().scheduleGeneral(new BeginBuff(npc, skill, player, 3600000), 200);
			}
	}


	public void doBuffPetGroup(String[] args)
	{
		L2Player player = (L2Player) self;

		if(npc == null)
			return;

		L2Summon pet = player.getPet();

		if((!checkCondition(player)) || (pet == null))
			return;

		if(!player.reduceAdena("Buffer", Config.EVENT_price * grpCount, npc, true))
			return;

		int time = 0;
		L2Skill skill;
		for(int buff[] : buffs)
			if(buff[2] == Integer.valueOf(args[0]))
			{
				skill = SkillTable.getInstance().getInfo(buff[0], buff[1]);
				time += skill.getHitTime();
				//ThreadPoolManager.getInstance().scheduleGeneral(new BeginBuffPet(npc, skill, pet), time);
				ThreadPoolManager.getInstance().scheduleGeneral(new BeginBuffPet(npc, skill, pet, 3600000), 200);
				time += 200;
			}
	}


	/**
	 * Бафает один бафф, снимает плату за бафф, отображает диалог с кнопкой возврата к списку бафов
	 *
	 * @param args массив строк: элемент 0 - id скида, элемент 1 - уровень скила
	 */
	public static void doBuff(String[] args)
	{
		L2Player player = (L2Player) self;

		if(npc == null)
			return;

		if(!checkCondition(player))
			return;

		if(!player.reduceAdena("Buffer", Config.EVENT_price, npc, true))
			return;

		try
		{
			int skill_id = Integer.valueOf(args[0]);
			int skill_lvl = Integer.valueOf(args[1]);
			L2Skill skill = SkillTable.getInstance().getInfo(skill_id, skill_lvl);
			ThreadPoolManager.getInstance().scheduleGeneral(new BeginBuff(npc, skill, player, args.length == 3 ? Integer.valueOf(args[2]) : 3600000), 200);


		}
		catch(Exception e)
		{
			player.sendMessage("Invalid skill!");
		}
		SelectBuffs();
	}


	public static void doBuffPet(String[] args)
	{
		L2Player player = (L2Player) self;
		L2Summon pet = player.getPet();

		if(npc == null)
			return;

		int _price = 0;

		if((!checkCondition(player)) || (pet == null))
			return;

		if(!player.reduceAdena("Buffer", _price, npc, true))
			return;

		try
		{
			for(int i = 0; i < pet_buffs.length; i++)
			{
				L2Skill skill = SkillTable.getInstance().getInfo(pet_buffs[i][0], pet_buffs[i][1]);

				//ThreadPoolManager.getInstance().scheduleGeneral(new BeginBuffPet(npc, skill, pet, Config.EVENT_BUFFER_MOD), 200);
				ThreadPoolManager.getInstance().scheduleGeneral(new BeginBuff(npc, skill, player, args.length == 3 ? Integer.valueOf(args[2]) : Config.EVENT_BUFFER_MOD), 200);
				//ThreadPoolManager.getInstance().scheduleGeneral(new BeginBuffPet(npc, skill, pet), skill.getHitTime());
				_price += Config.EVENT_price;
			}


		}
		catch(Exception e)
		{
			player.sendMessage("Invalid skill!");
		}
		SelectBuffs();
	}


	/**
	 * Проверяет возможность бафа персонажа.<BR>
	 * В случае невозможности бафа показывает игроку html с ошибкой и возвращает false.
	 *
	 * @param player персонаж
	 * @return true, если можно бафать персонажа
	 */
	public static boolean checkCondition(L2Playable cha)
	{
		if(cha == null)
			return false;

		L2Player player = cha.getPlayer();

		if(!_active || player == null)
			return false;

		if(player.isActionsDisabled() || player.isSitting() || player.getLastNpc().getDistance(player) > 300 || player.isInOlympiadMode())
			return false;

		String html;

		// Проверяем по уровню
		if(player.getLevel() > Config.EVENT_Max_lvl || player.getLevel() < Config.EVENT_Min_lvl)
		{
			html = Files.read("data/scripts/events/NPCBuffer/no-lvl.htm", player);
			html = html.replace("%min_lvl%", Integer.toString(Config.EVENT_Min_lvl));
			html = html.replace("%max_lvl%", Integer.toString(Config.EVENT_Max_lvl));
			show(html, player);
			return false;
		}

		//Можно ли юзать бафера во время осады?
		if(!Config.EVENT_Buffer_Siege)
		{
			SiegeUnit castle = npc.getCastle();
			Siege siege = castle.getSiege();
			if(siege != null && siege.isInProgress())
			{
				show(Files.read("data/scripts/events/NPCBuffer/no-siege.htm", player), player);
				return false;
			}
		}
		return true;
	}

	/**
	 * Показывает страницу с выбором возможных бафов
	 */
	public static void SelectBuffs()
	{
		L2Player player = (L2Player) self;

		if(!checkCondition(player))
			return;

		String html;
		html = Files.read("data/scripts/events/NPCBuffer/buffs.htm", player);
		html = html.replace("%grp_price%", Util.formatAdena(Config.EVENT_price * grpCount));
		html = html.replace("%price%", Util.formatAdena(Config.EVENT_price));
		show(html, player);
	}

	/**
	 * Генерит ссылку, которая в дальнейшем аппендится эвент менеждерам
	 *
	 * @return html код ссылки
	 */
	public static String OutDia()
	{
		if(!_active)
			return "";
		String append = "<br><a action=\"bypass -h scripts_events.NPCBuffer.Buffer:SelectBuffs\">";
		append += new CustomMessage("scripts.events.NPCBuffer.Buffer.selectBuffs", self);
		append += "</a>";
		return append;
	}

	// Далее идут аппенды диалогов эвент гейткиперам
	public static String DialogAppend_31212(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31213(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31214(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31215(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31216(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31217(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31218(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31219(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31220(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31221(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31222(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31223(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31224(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31767(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_32048(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static String DialogAppend_31768(Integer val)
	{
		if(val != 0)
			return "";
		return OutDia();
	}

	public static class BeginBuff implements Runnable
	{
		L2Character _buffer;
		L2Skill _skill;
		L2Player _target;
		int _mult;

		public BeginBuff(L2Character buffer, L2Skill skill, L2Player target, int mult)
		{
			_buffer = buffer;
			_skill = skill;
			_target = target;
			_mult = mult;
		}

		public void run()
		{
			if(_target.isInOlympiadMode() || _buffer == null)
				return;
			ThreadPoolManager.getInstance().scheduleGeneral(new EndBuff(_buffer, _skill, _target, _mult), 200);
		}
	}

	public static class EndBuff implements Runnable
	{
		L2Character _buffer;
		L2Skill _skill;
		L2Player _target;
		int _mult;

		public EndBuff(L2Character buffer, L2Skill skill, L2Player target, int mult)
		{
			_buffer = buffer;
			_skill = skill;
			_target = target;
			_mult = mult;
		}

		public void run()
		{
			if(!checkCondition(_target) || _buffer == null)
				return;
			_skill.applyEffects(_buffer, _target, false, _mult);
		}
	}


	public static class BeginBuffPet implements Runnable
	{
		L2Character _buffer;
		L2Skill _skill;
		L2Summon _target;
		int _mult;

		public BeginBuffPet(L2Character buffer, L2Skill skill, L2Summon target, int mult)
		{
			_buffer = buffer;
			_skill = skill;
			_target = target;
			_mult = mult;
		}

		public void run()
		{
			if(!checkCondition(_target) || _buffer == null)
				return;
			ThreadPoolManager.getInstance().scheduleGeneral(new EndBuffPet(_buffer, _skill, _target, _mult), 200);
		}
	}

	public static class EndBuffPet implements Runnable
	{
		L2Character _buffer;
		L2Skill _skill;
		L2Summon _target;
		int _mult;

		public EndBuffPet(L2Character buffer, L2Skill skill, L2Summon target, int mult)
		{
			_buffer = buffer;
			_skill = skill;
			_target = target;
			_mult = mult;
		}

		public void run()
		{
			if(!checkCondition(_target) || _buffer == null)
				return;
			_skill.applyEffects(_buffer, _target, false, _mult);
		}
	}

}