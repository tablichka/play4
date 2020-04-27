package actions;

import commands.admin.AdminEditChar;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.cache.InfoCache;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance.AggroInfo;
import ru.l2gw.gameserver.skills.Stats;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class OnActionShift extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2NpcInstance npc;
	private static final HashMap<Integer, String> elements = new HashMap<Integer, String>();
	static
	{
		elements.put(-2, "none");
		elements.put(0, "Fire");
		elements.put(1, "Water");
		elements.put(2, "Wind");
		elements.put(3, "Earth");
		elements.put(4, "Holy");
		elements.put(5, "Dark");
	}

	public void onLoad()
	{
		_log.info("OnActionShift Loaded");
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	public static boolean OnActionShift_L2NpcInstance(L2Player player, L2Object object)
	{
		if(player == null || object == null || !Config.ALLOW_NPC_SHIFTCLICK)
			return false;

		if(Config.ALLOW_NPC_SHIFTCLICK && !player.isGM())
		{
			if((Config.ALT_GAME_SHOW_DROPLIST || player.getVarB("showdrop")) && object instanceof L2NpcInstance)
			{
				L2NpcInstance _npc = (L2NpcInstance) object;
				if(_npc.isDead())
					return false;
				npc = _npc;
				self = player;
				droplist();
			}
			return false;
		}

		if(object instanceof L2NpcInstance)
		{
			L2NpcInstance npc = (L2NpcInstance) object;

			// Для мертвых мобов не показываем табличку, иначе спойлеры плачут
			if(npc.isDead())
				return false;

			String dialog;

			if(Config.ALT_FULL_NPC_STATS_PAGE)
			{
				dialog = Files.read("data/scripts/actions/player.L2NpcInstance.onActionShift.full.htm");
				dialog = dialog.replaceFirst("%class%", String.valueOf(npc.getClass().getSimpleName().replaceFirst("L2", "").replaceFirst("Instance", "")));
				dialog = dialog.replaceFirst("%id%", String.valueOf(npc.getNpcId()));
				dialog = dialog.replaceFirst("%spawn%", String.valueOf(npc.getSpawn() != null ? npc.getSpawn().getId() : "n/a"));
				dialog = dialog.replaceFirst("%objectId%", String.valueOf(npc.getObjectId()));
				dialog = dialog.replaceFirst("%storeId%", String.valueOf(npc.getStoredId()));
				dialog = dialog.replaceFirst("%makerName%", npc.getSpawnDefine() != null ? npc.getSpawnDefine().getMaker().name : "n/a");
				dialog = dialog.replaceFirst("%max_npc%", npc.getSpawnDefine() != null ? "" + npc.getSpawnDefine().getMaker().maximum_npc : "n/a");
				dialog = dialog.replaceFirst("%npc_count%", npc.getSpawnDefine() != null ? "" + npc.getSpawnDefine().getMaker().npc_count : "n/a");
				dialog = dialog.replaceFirst("%respawn%", String.valueOf(npc.getSpawn() != null ? Util.formatTime(npc.getSpawn().getRespawnDelay()) : npc.getSpawnDefine() != null ? Util.formatTime(npc.getSpawnDefine().respawn) + "(" + Util.formatTime(npc.getSpawnDefine().respawn_rand) + ")" : "0"));
				dialog = dialog.replaceFirst("%walkSpeed%", String.valueOf(npc.getWalkSpeed()));
				dialog = dialog.replaceFirst("%evs%", String.valueOf(npc.getEvasionRate(null)));
				dialog = dialog.replaceFirst("%acc%", String.valueOf(npc.getAccuracy()));
				dialog = dialog.replaceFirst("%crt%", String.valueOf(npc.getCriticalHit(null, null)));
				dialog = dialog.replaceFirst("%aspd%", String.valueOf(npc.getPAtkSpd()));
				dialog = dialog.replaceFirst("%cspd%", String.valueOf(npc.getMAtkSpd()));
				dialog = dialog.replaceFirst("%loc%", String.valueOf(npc.getSpawn() != null ? npc.getSpawn().getLocation() : "n/a"));
				dialog = dialog.replaceFirst("%dist%", String.valueOf((int) npc.getDistance3D(player)));
				dialog = dialog.replaceFirst("%spReward%", String.valueOf(npc.getSpReward()));
				dialog = dialog.replaceFirst("%STR%", String.valueOf(npc.getSTR()));
				dialog = dialog.replaceFirst("%DEX%", String.valueOf(npc.getDEX()));
				dialog = dialog.replaceFirst("%CON%", String.valueOf(npc.getCON()));
				dialog = dialog.replaceFirst("%INT%", String.valueOf(npc.getINT()));
				dialog = dialog.replaceFirst("%WIT%", String.valueOf(npc.getWIT()));
				dialog = dialog.replaceFirst("%MEN%", String.valueOf(npc.getMEN()));
				dialog = dialog.replaceFirst("%xyz%", npc.getLoc().getX() + " " + npc.getLoc().getY() + " " + npc.getLoc().getZ());
				dialog = dialog.replaceFirst("%heading%", String.valueOf(npc.getLoc().getHeading()));
				dialog = dialog.replaceFirst("%undying%", String.valueOf(npc.getTemplate().undying));
				dialog = dialog.replaceFirst("%can_be_attacked%", String.valueOf(npc.getTemplate().can_be_attacked));
				dialog = dialog.replaceFirst("%can_move%", String.valueOf(npc.getTemplate().can_move));
				dialog = dialog.replaceFirst("%flying%", String.valueOf(npc.getTemplate().flying));
				dialog = dialog.replaceFirst("%targetable%", String.valueOf(npc.getTemplate().targetable));
				dialog = dialog.replaceFirst("%show_name_tag%", String.valueOf(npc.getTemplate().show_name_tag));
				dialog = dialog.replaceFirst("%unsowing%", String.valueOf(npc.getTemplate().unsowing));
				dialog = dialog.replaceFirst("%ai_type%", npc.getAI().getL2ClassShortName());
				dialog = dialog.replaceFirst("%atkElem%", elements.get(npc.getTemplate().baseAttrAtk));
				dialog = dialog.replaceFirst("%atkVal%", String.valueOf(npc.getTemplate().baseAttrAtkValue));
				dialog = dialog.replaceFirst("%defFire%", String.valueOf(npc.getTemplate().baseAttrDefFire));
				dialog = dialog.replaceFirst("%defWater%", String.valueOf(npc.getTemplate().baseAttrDefWater));
				dialog = dialog.replaceFirst("%defWind%", String.valueOf(npc.getTemplate().baseAttrDefWind));
				dialog = dialog.replaceFirst("%defEarth%", String.valueOf(npc.getTemplate().baseAttrDefEarth));
				dialog = dialog.replaceFirst("%defHoly%", String.valueOf(npc.getTemplate().baseAttrDefHoly));
				dialog = dialog.replaceFirst("%defDark%", String.valueOf(npc.getTemplate().baseAttrDefDark));
				dialog = dialog.replaceFirst("%curHp%", String.valueOf(npc.getCurrentHp()));
				dialog = dialog.replaceFirst("%curMp%", String.valueOf(npc.getCurrentMp()));
			}
			else
				dialog = Files.read("data/scripts/actions/player.L2NpcInstance.onActionShift.htm");

			dialog = dialog.replaceFirst("%name%", npc.getName());
			dialog = dialog.replaceFirst("%level%", String.valueOf(npc.getLevel()));
			dialog = dialog.replaceFirst("%factionId%", npc.getFactionId().equals("") ? "none" : npc.getFactionId());
			dialog = dialog.replaceFirst("%aggro%", String.valueOf(npc.getAggroRange()));
			dialog = dialog.replaceFirst("%maxHp%", String.valueOf(npc.getMaxHp()));
			dialog = dialog.replaceFirst("%maxMp%", String.valueOf(npc.getMaxMp()));
			dialog = dialog.replaceFirst("%pDef%", String.valueOf(npc.getPDef(null)));
			dialog = dialog.replaceFirst("%mDef%", String.valueOf(npc.getMDef(null, null)));
			dialog = dialog.replaceFirst("%pAtk%", String.valueOf(npc.getPAtk(null)));
			dialog = dialog.replaceFirst("%mAtk%", String.valueOf(npc.getMAtk(null, null)));
			dialog = dialog.replaceFirst("%expReward%", String.valueOf(npc.getExpReward()));
			dialog = dialog.replaceFirst("%runSpeed%", String.valueOf(npc.getRunSpeed()));

			// Дополнительная инфа для ГМов
			if(player.isGM())
				dialog = dialog.replaceFirst("%AI%", String.valueOf(npc.getAI()) + ",<br1>active: " + npc.getAI().isActive() + ",<br1>intention: " + npc.getAI().getIntention());
			else
				dialog = dialog.replaceFirst("%AI%", "");

			show(dialog, player);
			player.sendActionFailed();
			return true;
		}
		return false;
	}

	public static String getNpcRaceById(short raceId)
	{
		switch(raceId)
		{
			case 1:
				return "Undead";
			case 2:
				return "Magic Creatures";
			case 3:
				return "Beasts";
			case 4:
				return "Animals";
			case 5:
				return "Plants";
			case 6:
				return "Humanoids";
			case 7:
				return "Spirits";
			case 8:
				return "Angels";
			case 9:
				return "Demons";
			case 10:
				return "Dragons";
			case 11:
				return "Giants";
			case 12:
				return "Bugs";
			case 13:
				return "Fairies";
			case 14:
				return "Humans";
			case 15:
				return "Elves";
			case 16:
				return "Dark Elves";
			case 17:
				return "Orcs";
			case 18:
				return "Dwarves";
			case 19:
				return "Others";
			case 20:
				return "Non-living Beings";
			case 21:
				return "Siege Weapons";
			case 22:
				return "Defending Army";
			case 23:
				return "Mercenaries";
			case 24:
				return "Unknown Creature";
			case 25:
				return "Kamael";
			default:
				return "Not defined";
		}
	}

	public static void droplist()
	{
		if(npc == null || self == null)
			return;
		if(!Config.ALT_GAME_GEN_DROPLIST_ON_DEMAND)
			show(InfoCache.getFromDroplistCache(npc.getNpcId()), (L2Player) self);
		else
			show(NpcTable.generateDroplist(npc.getTemplate()), (L2Player) self);
	}

	public static void stats()
	{
		if(npc == null || self == null)
			return;
		L2Player player = (L2Player) self;
		String dialog = Files.read("data/scripts/actions/player.L2NpcInstance.stats.htm");
		dialog = dialog.replaceFirst("%name%", npc.getName());
		dialog = dialog.replaceFirst("%level%", String.valueOf(npc.getLevel()));
		dialog = dialog.replaceFirst("%factionId%", npc.getFactionId());
		dialog = dialog.replaceFirst("%aggro%", String.valueOf(npc.getAggroRange()));
		dialog = dialog.replaceFirst("%race%", getNpcRaceById(npc.getTemplate().getRace()));
		dialog = dialog.replaceFirst("%herbs%", String.valueOf(npc.getTemplate().isDropHerbs));
		dialog = dialog.replaceFirst("%maxHp%", String.valueOf(npc.getMaxHp()));
		dialog = dialog.replaceFirst("%maxMp%", String.valueOf(npc.getMaxMp()));
		dialog = dialog.replaceFirst("%pDef%", String.valueOf(npc.getPDef(null)));
		dialog = dialog.replaceFirst("%mDef%", String.valueOf(npc.getMDef(null, null)));
		dialog = dialog.replaceFirst("%pAtk%", String.valueOf(npc.getPAtk(null)));
		dialog = dialog.replaceFirst("%mAtk%", String.valueOf(npc.getMAtk(null, null)));
		dialog = dialog.replaceFirst("%accuracy%", String.valueOf(npc.getAccuracy()));
		dialog = dialog.replaceFirst("%evasionRate%", String.valueOf(npc.getEvasionRate(null)));
		dialog = dialog.replaceFirst("%criticalHit%", String.valueOf(npc.getCriticalHit(null, null)));
		dialog = dialog.replaceFirst("%runSpeed%", String.valueOf(npc.getRunSpeed()));
		dialog = dialog.replaceFirst("%walkSpeed%", String.valueOf(npc.getWalkSpeed()));
		dialog = dialog.replaceFirst("%pAtkSpd%", String.valueOf(npc.getPAtkSpd()));
		dialog = dialog.replaceFirst("%mAtkSpd%", String.valueOf(npc.getMAtkSpd()));
		dialog = dialog.replaceFirst("%STR%", String.valueOf(npc.getSTR()));
		dialog = dialog.replaceFirst("%DEX%", String.valueOf(npc.getDEX()));
		dialog = dialog.replaceFirst("%CON%", String.valueOf(npc.getCON()));
		dialog = dialog.replaceFirst("%INT%", String.valueOf(npc.getINT()));
		dialog = dialog.replaceFirst("%WIT%", String.valueOf(npc.getWIT()));
		dialog = dialog.replaceFirst("%MEN%", String.valueOf(npc.getMEN()));
		show(dialog, player);
	}

	public static void resists()
	{
		if(npc == null || self == null)
			return;
		L2Player player = (L2Player) self;
		StringBuilder dialog = new StringBuilder("<html><body><center><font color=\"LEVEL\">");
		dialog.append(npc.getName()).append("<br></font></center><table width=\"70%\">");

		int FIRE_ATTRIBUTE = (int) npc.calcStat(Stats.FIRE_ATTRIBUTE, 0, null, null);
		if(FIRE_ATTRIBUTE != 0)
			dialog.append("<tr><td>Fire</td><td>").append(FIRE_ATTRIBUTE).append("%</td></tr>");

		int WIND_ATTRIBUTE = (int) npc.calcStat(Stats.WIND_ATTRIBUTE, 0, null, null);
		if(WIND_ATTRIBUTE != 0)
			dialog.append("<tr><td>Wind</td><td>").append(WIND_ATTRIBUTE).append("%</td></tr>");

		int WATER_ATTRIBUTE = (int) npc.calcStat(Stats.WATER_ATTRIBUTE, 0, null, null);
		if(WATER_ATTRIBUTE != 0)
			dialog.append("<tr><td>Water</td><td>").append(WATER_ATTRIBUTE).append("%</td></tr>");

		int EARTH_ATTRIBUTE = (int) npc.calcStat(Stats.EARTH_ATTRIBUTE, 0, null, null);
		if(EARTH_ATTRIBUTE != 0)
			dialog.append("<tr><td>Earth</td><td>").append(EARTH_ATTRIBUTE).append("%</td></tr>");

		int HOLY_ATTRIBUTE = (int) npc.calcStat(Stats.HOLY_ATTRIBUTE, 0, null, null);
		if(HOLY_ATTRIBUTE != 0)
			dialog.append("<tr><td>Light</td><td>").append(HOLY_ATTRIBUTE).append("%</td></tr>");

		int DARK_ATTRIBUTE = (int) npc.calcStat(Stats.DARK_ATTRIBUTE, 0, null, null);
		if(DARK_ATTRIBUTE != 0)
			dialog.append("<tr><td>Darkness</td><td>").append(DARK_ATTRIBUTE).append("%</td></tr>");

		int BLEED_RECEPTIVE = 100 - (int) npc.calcStat(Stats.BLEED_RECEPTIVE, 100, null, null);
		if(BLEED_RECEPTIVE != 0)
			dialog.append("<tr><td>Bleed</td><td>").append(BLEED_RECEPTIVE).append("%</td></tr>");

		int POISON_RECEPTIVE = 100 - (int) npc.calcStat(Stats.POISON_RECEPTIVE, 100, null, null);
		if(POISON_RECEPTIVE != 0)
			dialog.append("<tr><td>Poison</td><td>").append(POISON_RECEPTIVE).append("%</td></tr>");

		int DEATH_RECEPTIVE = 100 - (int) npc.calcStat(Stats.DEATH_RECEPTIVE, 100, null, null);
		if(DEATH_RECEPTIVE != 0)
			dialog.append("<tr><td>Death</td><td>").append(DEATH_RECEPTIVE).append("%</td></tr>");

		int STUN_RECEPTIVE = 100 - (int) npc.calcStat(Stats.STUN_RECEPTIVE, 100, null, null);
		if(STUN_RECEPTIVE != 0)
			dialog.append("<tr><td>Stun</td><td>").append(STUN_RECEPTIVE).append("%</td></tr>");

		int ROOT_RECEPTIVE = 100 - (int) npc.calcStat(Stats.ROOT_RECEPTIVE, 100, null, null);
		if(ROOT_RECEPTIVE != 0)
			dialog.append("<tr><td>Root</td><td>").append(ROOT_RECEPTIVE).append("%</td></tr>");

		int SLEEP_RECEPTIVE = 100 - (int) npc.calcStat(Stats.SLEEP_RECEPTIVE, 100, null, null);
		if(SLEEP_RECEPTIVE != 0)
			dialog.append("<tr><td>Sleep</td><td>").append(SLEEP_RECEPTIVE).append("%</td></tr>");

		int PARALYZE_RECEPTIVE = 100 - (int) npc.calcStat(Stats.PARALYZE_RECEPTIVE, 100, null, null);
		if(PARALYZE_RECEPTIVE != 0)
			dialog.append("<tr><td>Paralyze</td><td>").append(PARALYZE_RECEPTIVE).append("%</td></tr>");

		int FEAR_RECEPTIVE = 100 - (int) npc.calcStat(Stats.FEAR_RECEPTIVE, 100, null, null);
		if(FEAR_RECEPTIVE != 0)
			dialog.append("<tr><td>Fear</td><td>").append(FEAR_RECEPTIVE).append("%</td></tr>");

		int DEBUFF_RECEPTIVE = 100 - (int) npc.calcStat(Stats.DEBUFF_RECEPTIVE, 100, null, null);
		if(DEBUFF_RECEPTIVE != 0)
			dialog.append("<tr><td>Debuff</td><td>").append(DEBUFF_RECEPTIVE).append("%</td></tr>");

		int CANCEL_RECEPTIVE = 100 - (int) npc.calcStat(Stats.CANCEL_RECEPTIVE, 100, null, null);
		if(CANCEL_RECEPTIVE != 0)
			dialog.append("<tr><td>Cancel</td><td>").append(CANCEL_RECEPTIVE).append("%</td></tr>");

		int SWORD_WPN_RECEPTIVE = 100 - (int) npc.calcStat(Stats.SWORD_WPN_RECEPTIVE, 100, null, null);
		if(SWORD_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Sword</td><td>").append(SWORD_WPN_RECEPTIVE).append("%</td></tr>");

		int DUAL_WPN_RECEPTIVE = 100 - (int) npc.calcStat(Stats.DUAL_WPN_RECEPTIVE, 100, null, null);
		if(DUAL_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Dual Sword</td><td>").append(DUAL_WPN_RECEPTIVE).append("%</td></tr>");

		int BLUNT_WPN_RECEPTIVE = 100 - (int) npc.calcStat(Stats.BLUNT_WPN_RECEPTIVE, 100, null, null);
		if(BLUNT_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Blunt</td><td>").append(BLUNT_WPN_RECEPTIVE).append("%</td></tr>");

		int DAGGER_WPN_RECEPTIVE = 100 - (int) npc.calcStat(Stats.DAGGER_WPN_RECEPTIVE, 100, null, null);
		if(DAGGER_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Dagger/rapier</td><td>").append(DAGGER_WPN_RECEPTIVE).append("%</td></tr>");

		int BOW_WPN_RECEPTIVE = 100 - (int) npc.calcStat(Stats.BOW_WPN_RECEPTIVE, 100, null, null);
		if(BOW_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Bow/Crossbow</td><td>").append(BOW_WPN_RECEPTIVE).append("%</td></tr>");

		int CROSSBOW_WPN_RECEPTIVE = 100 - (int) npc.calcStat(Stats.CROSSBOW_WPN_RECEPTIVE, 100, null, null);
		if(CROSSBOW_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Crossbow</td><td>").append(CROSSBOW_WPN_RECEPTIVE).append("%</td></tr>");

		int POLE_WPN_RECEPTIVE = 100 - (int) npc.calcStat(Stats.POLE_WPN_RECEPTIVE, 100, null, null);
		if(POLE_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Polearm</td><td>").append(POLE_WPN_RECEPTIVE).append("%</td></tr>");

		int FIST_WPN_RECEPTIVE = 100 - (int) npc.calcStat(Stats.FIST_WPN_RECEPTIVE, 100, null, null);
		if(FIST_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Fist weapons</td><td>").append(FIST_WPN_RECEPTIVE).append("%</td></tr>");

		if(FIRE_ATTRIBUTE == 0 && WIND_ATTRIBUTE == 0 && WATER_ATTRIBUTE == 0 && EARTH_ATTRIBUTE == 0 && DARK_ATTRIBUTE == 0 && HOLY_ATTRIBUTE // primary elements
				== 0 && BLEED_RECEPTIVE == 0 && DEATH_RECEPTIVE == 0 && STUN_RECEPTIVE // phys debuff
				== 0 && POISON_RECEPTIVE == 0 && ROOT_RECEPTIVE == 0 && SLEEP_RECEPTIVE == 0 && PARALYZE_RECEPTIVE == 0 && FEAR_RECEPTIVE == 0 && DEBUFF_RECEPTIVE == 0 && CANCEL_RECEPTIVE // mag debuff
				== 0 && SWORD_WPN_RECEPTIVE == 0 && DUAL_WPN_RECEPTIVE == 0 && BLUNT_WPN_RECEPTIVE == 0 && DAGGER_WPN_RECEPTIVE == 0 && BOW_WPN_RECEPTIVE == 0 && CROSSBOW_WPN_RECEPTIVE == 0 && POLE_WPN_RECEPTIVE == 0 && FIST_WPN_RECEPTIVE == 0// weapons
				)
			dialog.append("</table>No resists</body></html>");
		else
			dialog.append("</table></body></html>");
		show(dialog.toString(), player);
	}

	public static void aggro()
	{
		if(npc == null || self == null)
			return;
		L2Player player = (L2Player) self;
		StringBuilder dialog = new StringBuilder("<html><body><table width=\"80%\">");
		dialog.append("<tr><td>Name/objectId</td><td>Hate</td><td>Damage</td></tr>");

		for(AggroInfo aggroInfo : npc.getAggroList().values())
			dialog.append("<tr><td>" + (aggroInfo.getAttacker() == null ? aggroInfo.objectId : aggroInfo.getAttacker().getName()) + "</td><td>" + aggroInfo.hate + "</td><td>" + aggroInfo.damage + "</td></tr>");

		dialog.append("</table><br><center><button value=\"");
		if(player.getVar("lang@").equalsIgnoreCase("en"))
			dialog.append("Refresh");
		else
			dialog.append("Обновить");
		dialog.append("\" action=\"bypass -h scripts_actions.OnActionShift:aggro\" width=100 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\" /></center></body></html>");
		show(dialog.toString(), player);
	}

	public static void ai_params()
	{
		if(npc == null || self == null)
			return;

		L2Player player = (L2Player) self;
		StringBuilder dialog = new StringBuilder("<html><body>");
		dialog.append("AI: ");
		dialog.append(npc.getAI().getClass().getSimpleName());
		dialog.append("<br><br><center><table width=\"80%\">");
		dialog.append("<tr><td align=left>[state]=").append(npc.getNpcState()).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai0]=").append(npc.i_ai0).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai1]=").append(npc.i_ai1).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai2]=").append(npc.i_ai2).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai3]=").append(npc.i_ai3).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai4]=").append(npc.i_ai4).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai5]=").append(npc.i_ai5).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai6]=").append(npc.i_ai6).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai7]=").append(npc.i_ai7).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai8]=").append(npc.i_ai8).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_ai9]=").append(npc.i_ai9).append("</td></tr>");
		dialog.append("<tr><td align=left>[l_ai0]=").append(npc.l_ai0).append("</td></tr>");
		dialog.append("<tr><td align=left>[l_ai1]=").append(npc.l_ai1).append("</td></tr>");
		dialog.append("<tr><td align=left>[l_ai2]=").append(npc.l_ai2).append("</td></tr>");
		dialog.append("<tr><td align=left>[l_ai3]=").append(npc.l_ai3).append("</td></tr>");
		dialog.append("<tr><td align=left>[l_ai4]=").append(npc.l_ai4).append("</td></tr>");
		dialog.append("<tr><td align=left>[l_ai5]=").append(npc.l_ai5).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_quest0]=").append(npc.i_quest0).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_quest1]=").append(npc.i_quest1).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_quest2]=").append(npc.i_quest2).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_quest3]=").append(npc.i_quest3).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_quest4]=").append(npc.i_quest4).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_quest5]=").append(npc.i_quest5).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_quest6]=").append(npc.i_quest6).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_quest7]=").append(npc.i_quest7).append("</td></tr>");
		dialog.append("<tr><td align=left>[i_quest8]=").append(npc.i_quest8).append("</td></tr>");
		dialog.append("<tr><td align=left>[c_ai0]=").append(L2ObjectsStorage.getAsCharacter(npc.c_ai0)).append("</td></tr>");
		dialog.append("<tr><td align=left>[c_ai1]=").append(L2ObjectsStorage.getAsCharacter(npc.c_ai1)).append("</td></tr>");
		dialog.append("<tr><td align=left>[c_ai2]=").append(L2ObjectsStorage.getAsCharacter(npc.c_ai2)).append("</td></tr>");
		dialog.append("<tr><td align=left>[param1]=").append(npc.param1).append("</td></tr>");
		dialog.append("<tr><td align=left>[param2]=").append(npc.param2).append("</td></tr>");
		dialog.append("<tr><td align=left>[param3]=").append(npc.param3).append("</td></tr>");

		Class<?> ai = npc.getAI().getClass();
		for(Field field : ai.getFields())
			if(Modifier.isPublic(field.getModifiers()))
			{
				try
				{
					dialog.append("<tr><td align=left>[").append(field.getName()).append("]=");
					dialog.append(field.get(npc.getAI()));
					dialog.append("</td></tr>");
				}
				catch(IllegalAccessException e)
				{
				}
			}

		dialog.append("</table><br><center><button value=\"");
		if(player.getVar("lang@").equalsIgnoreCase("en"))
			dialog.append("Refresh");
		else
			dialog.append("Обновить");
		dialog.append("\" action=\"bypass -h scripts_actions.OnActionShift:ai_params\" width=100 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\" /></center></body></html>");
		show(dialog.toString(), player);
	}

	public static void task_list()
	{
		if(npc == null || self == null)
			return;

		L2Player player = (L2Player) self;
		StringBuilder dialog = new StringBuilder("<html><body>");
		dialog.append("AI: ");
		dialog.append(npc.getAI().getClass().getSimpleName());
		dialog.append("<br>Task list:");
		dialog.append("<br><br><center><table width=\"80%\">");

		for(DefaultAI.Task task : npc.getAI().getTaskList())
			dialog.append("<tr><td align=left>").append(task).append("</td></tr>");

		dialog.append("</table><br><center><button value=\"");
		if(player.getVar("lang@").equalsIgnoreCase("en"))
			dialog.append("Refresh");
		else
			dialog.append("Обновить");
		dialog.append("\" action=\"bypass -h scripts_actions.OnActionShift:task_list\" width=100 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\" /></center></body></html>");
		show(dialog.toString(), player);
	}

	public static boolean OnActionShift_L2DoorInstance(L2Player player, L2Object object)
	{
		if(player == null || object == null || !player.isGM())
			return false;
		if(object instanceof L2DoorInstance)
		{
			String dialog;
			L2DoorInstance door = (L2DoorInstance) object;
			dialog = Files.read("data/scripts/actions/admin.L2DoorInstance.onActionShift.htm");
			dialog = dialog.replaceFirst("%CurrentHp%", String.valueOf(door.getCurrentHp()));
			dialog = dialog.replaceFirst("%MaxHp%", String.valueOf(door.getMaxHp()));
			dialog = dialog.replaceFirst("%ObjectId%", String.valueOf(door.getObjectId()));
			dialog = dialog.replaceFirst("%doorId%", String.valueOf(door.getDoorId()));
			dialog = dialog.replaceFirst("%pdef%", String.valueOf(door.getPDef(null)));
			dialog = dialog.replaceFirst("%mdef%", String.valueOf(door.getMDef(null, null)));
			dialog = dialog.replaceFirst("%grade%", String.valueOf(door.getGrade()));
			dialog = dialog.replaceFirst("%unlockable%", String.valueOf(door.isUnlockable()));
			dialog = dialog.replaceFirst("%destroyable%", String.valueOf(door.isDestroyable()));
			dialog = dialog.replaceFirst("%showhp%", String.valueOf(door.isHPVisible()));
			dialog = dialog.replaceFirst("%xmax%", String.valueOf(door.getXMax()));
			dialog = dialog.replaceFirst("%ymax%", String.valueOf(door.getYMax()));
			dialog = dialog.replaceFirst("%zmax%", String.valueOf(door.getZMax()));
			dialog = dialog.replaceFirst("%xmin%", String.valueOf(door.getXMin()));
			dialog = dialog.replaceFirst("%ymin%", String.valueOf(door.getYMin()));
			dialog = dialog.replaceFirst("%zmin%", String.valueOf(door.getZMin()));

			dialog = dialog.replaceFirst("bypass -h admin_open", "bypass -h admin_open " + door.getDoorId());
			dialog = dialog.replaceFirst("bypass -h admin_close", "bypass -h admin_close " + door.getDoorId());

			show(dialog, player);
		}
		return false;
	}

	public static boolean OnActionShift_L2Player(L2Player player, L2Object object)
	{
		if(player == null || object == null || !AdminTemplateManager.checkBoolean("viewPlayerInfo", player))
			return false;
		if(object.isPlayer())
			AdminEditChar.showCharacterList(player, (L2Player) object);
		return false;
	}

	public static boolean OnActionShift_L2Summon(L2Player player, L2Object object)
	{
		if(player == null || object == null || !AdminTemplateManager.checkBoolean("viewPlayerInfo", player))
			return false;
		if(object.isSummon() || object.isPet())
			return false; //TODO: показывать страничку со статами пета/саммона
		return false;
	}

	public static boolean OnActionShift_L2StaticObjectInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2FeedableBeastInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2TamedBeastInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2AdventurerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2MonsterInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2MinionInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2TeleporterInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2BossInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2RaidBossInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2Merchant(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2GuardInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2TrainerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2MerchantInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2VillageMasterInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2ChestInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2ManorManagerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2OlympiadManagerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2SignsPriestInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2CastleDoormenInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2ClanHallDoormenInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2WyvernManagerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2WeddingManagerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2WarehouseInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2SymbolMakerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2SiegeNpcInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2SiegeGuardInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2PenaltyMonsterInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2ObservationInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2NpcFriendInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2MercManagerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2FestivalMonsterInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2FestivalGuideInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2DeadManInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2ControlTowerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2FlameControlTowerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2ClassMasterInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2ClanHallManagerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2CastleChamberlain(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2CabaleBufferInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2AuctioneerInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2ArtefactInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2XmassTreeInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}

	public static boolean OnActionShift_L2CastleChamberlainInstance(L2Player player, L2Object object)
	{
		return OnActionShift_L2NpcInstance(player, object);
	}
}
