package services.community;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Summon;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.skills.Formulas;
import ru.l2gw.gameserver.skills.Stats;
import ru.l2gw.util.Files;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author: agr0naft
 * @date: 07.01.11 13:49
 */
public class PlayerInfo implements ScriptFile, ICommunityBoardHandler
{
	private static Log _log = LogFactory.getLog("community");

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_log.info("CommunityBoard: Player information board loaded.");
			CommunityBoardManager.getInstance().registerHandler(this);
		}
	}

	public void onReload()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
			CommunityBoardManager.getInstance().unregisterHandler(this);
	}

	public void onShutdown()
	{}

	public String[] getBypassCommands()
	{
		return new String[]{"_myinfo"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		if(player == null)
			return;

		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		try
		{

		if("myinfo".equals(cmd))
		{
			String html = Files.read("data/scripts/services/community/html/playerinfo.htm", player, false);
			html = html.replace("<?player_name?>", "<font color='" + player.getNameColor() + "'>" + player.getName() + "</font>");
			html = html.replace("<?player_title?>", "<font color='" + player.getTitleColor() + "'>" + player.getTitle() + "</font>");
			html = html.replace("<?player_agathion_name?>", "&@" + player.getAgathionId() + ";");
			html = html.replace("<?player_level?>", String.valueOf(player.getLevel()));
			html = html.replace("<?player_exp?>", String.valueOf(player.getExp()));
			html = html.replace("<?player_sp?>", String.valueOf(player.getSp()));
			html = html.replace("<?player_base_class?>", ClassId.values()[player.getBaseClass()].name());
			html = html.replace("<?player_active_class?>", player.getClassId().name());
			html = html.replace("<?player_object_id?>", String.valueOf(player.getObjectId()));
			html = html.replace("<?player_ip?>", String.valueOf(player.getNetConnection().getIpAddr()));
			html = html.replace("<?player_account_name?>", player.getAccountName());
			html = html.replace("<?player_x_pos?>", String.valueOf(player.getX()));
			html = html.replace("<?player_y_pos?>", String.valueOf(player.getY()));
			html = html.replace("<?player_z_pos?>", String.valueOf(player.getZ()));
			html = html.replace("<?player_heading?>", String.valueOf(player.getHeading()));
			html = html.replace("<?player_reflect_id?>", String.valueOf(player.getReflection()));
			html = html.replace("<?player_str?>", String.valueOf(player.getSTR()));
			html = html.replace("<?player_dex?>", String.valueOf(player.getDEX()));
			html = html.replace("<?player_con?>", String.valueOf(player.getCON()));
			html = html.replace("<?player_int?>", String.valueOf(player.getINT()));
			html = html.replace("<?player_wit?>", String.valueOf(player.getWIT()));
			html = html.replace("<?player_men?>", String.valueOf(player.getMEN()));
			html = html.replace("<?player_transformation?>", player.getTransformationName() != null ? player.getTransformationName() : "");
			html = html.replace("<?player_cur_hp?>", String.valueOf((int) player.getCurrentHp()));
			html = html.replace("<?player_max_hp?>", String.valueOf(player.getMaxHp()));
			html = html.replace("<?player_cur_mp?>", String.valueOf((int) player.getCurrentMp()));
			html = html.replace("<?player_max_mp?>", String.valueOf(player.getMaxMp()));
			html = html.replace("<?player_cur_load?>", String.valueOf(player.getCurrentLoad()));
			html = html.replace("<?player_max_load?>", String.valueOf(player.getMaxLoad()));
			html = html.replace("<?player_patk?>", String.valueOf(player.getPAtk(null)));
			html = html.replace("<?player_patkspd?>", String.valueOf(player.getPAtkSpd()));
			html = html.replace("<?player_pdef?>", String.valueOf(player.getPDef(null)));
			html = html.replace("<?player_evasion?>", String.valueOf(player.getEvasionRate(null)));
			html = html.replace("<?player_accuracy?>", String.valueOf(player.getAccuracy()));
			html = html.replace("<?player_crit?>", String.valueOf(player.getCriticalHit(null, null)));
			html = html.replace("<?player_matk?>", String.valueOf(player.getMAtk(null, null)));
			html = html.replace("<?player_matkspd?>", String.valueOf(player.getMAtkSpd()));
			html = html.replace("<?player_mdef?>", String.valueOf(player.getMDef(null, null)));
			html = html.replace("<?player_karma?>", String.valueOf(player.getKarma()));
			html = html.replace("<?player_attack_speed?>", String.valueOf(player.getAttackSpeedMultiplier()));
			html = html.replace("<?player_pk_kills?>", String.valueOf(player.getPkKills()));
			html = html.replace("<?player_pvp_kills?>", String.valueOf(player.getPvpKills()));
			html = html.replace("<?player_rec_left?>", String.valueOf(player.getRecSystem().getRecommendsLeft()));
			html = html.replace("<?player_rec_have?>", String.valueOf(player.getRecSystem().getRecommendsHave()));
			html = html.replace("<?player_inventory_limit?>", String.valueOf(player.getInventoryLimit()));
			html = html.replace("<?player_max_cp?>", String.valueOf(player.getMaxCp()));
			html = html.replace("<?player_cur_cp?>", String.valueOf((int) player.getCurrentCp()));
			html = html.replace("<?player_fame?>", String.valueOf(player.getFame()));
			html = html.replace("<?player_vitality?>", String.valueOf(player.getVitality() == null ? 20000 : player.getVitality().getPoints()));

			NumberFormat df = NumberFormat.getNumberInstance(Locale.ENGLISH);
			df.setMaximumFractionDigits(4);
			df.setMinimumFractionDigits(1);
			html = html.replace("<?player_hp_regen?>", String.valueOf(df.format(Formulas.calcHpRegen(player))));
			html = html.replace("<?player_mp_regen?>", String.valueOf(df.format(Formulas.calcMpRegen(player))));
			html = html.replace("<?player_cp_regen?>", String.valueOf(df.format(Formulas.calcCpRegen(player))));
			html = html.replace("<?player_hp_drain?>", String.valueOf(df.format(player.calcStat(Stats.ABSORB_DAMAGE_PERCENT, 0, null, null))) + "%");
			html = html.replace("<?player_hp_gain_bonus?>", String.valueOf(df.format(player.calcStat(Stats.HEAL_EFFECTIVNESS, 100, null, null) - 100)) + "%");
			html = html.replace("<?player_mp_gain_bonus?>", String.valueOf(df.format(player.calcStat(Stats.MANAHEAL_EFFECTIVNESS, 100, null, null) - 100)) + "%");
			html = html.replace("<?player_crit_damage?>", String.valueOf(df.format(player.calcStat(Stats.CRITICAL_DAMAGE, 100, null, null) + 100)) + "% + " + String.valueOf((int) player.calcStat(Stats.CRITICAL_DAMAGE_STATIC, 0, null, null)));
			html = html.replace("<?player_magic_crit?>", String.valueOf(df.format(player.getCriticalMagic(player, null))) + "%");

			int[] attackElement = player.getAttackElement();
			String atkElement;
			if(attackElement == null || attackElement[0] == -2)
				atkElement = "&$27;";
			else
			{
				switch(attackElement[0])
				{
					case 0:
						atkElement = "&$1622;"; //Fire
					case 1:
						atkElement = "&$1623;"; //Water
					case 2:
						atkElement = "&$1624;"; //Wind
					case 3:
						atkElement = "&$1625;"; //Earth
					case 4:
						atkElement = "&$1626;"; //Holy
					case 5:
						atkElement = "&$1627;"; //Dark
					default:
						atkElement = "&$27;"; //None
				}
			}
			html = html.replace("<?player_atk_element?>", atkElement);
			html = html.replace("<?player_atk_element_val?>", attackElement == null ? "0" : String.valueOf(attackElement[1]));
			html = html.replace("<?player_fire_res?>", String.valueOf(player.getDefenceFire()));
			html = html.replace("<?player_wind_res?>", String.valueOf(player.getDefenceWater()));
			html = html.replace("<?player_water_res?>", String.valueOf(player.getDefenceWind()));
			html = html.replace("<?player_earth_res?>", String.valueOf(player.getDefenceEarth()));
			html = html.replace("<?player_holy_res?>", String.valueOf(player.getDefenceHoly()));
			html = html.replace("<?player_dark_res?>", String.valueOf(player.getDefenceDark()));
			html = html.replace("<?player_bleed_res?>", String.valueOf(100 - (int) player.calcStat(Stats.BLEED_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_poison_res?>", String.valueOf(100 - (int) player.calcStat(Stats.POISON_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_death_res?>", String.valueOf(100 - (int) player.calcStat(Stats.DEATH_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_stun_res?>", String.valueOf(100 - (int) player.calcStat(Stats.STUN_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_root_res?>", String.valueOf(100 - (int) player.calcStat(Stats.ROOT_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_sleep_res?>", String.valueOf(100 - (int) player.calcStat(Stats.SLEEP_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_paralyze_res?>", String.valueOf(100 - (int) player.calcStat(Stats.PARALYZE_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_fear_res?>", String.valueOf(100 - (int) player.calcStat(Stats.FEAR_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_debuff_res?>", String.valueOf(100 - (int) player.calcStat(Stats.DEBUFF_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_cancel_res?>", String.valueOf(100 - (int) player.calcStat(Stats.CANCEL_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_sword_res?>", String.valueOf(100 - (int) player.calcStat(Stats.SWORD_WPN_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_dual_res?>", String.valueOf(100 - (int) player.calcStat(Stats.DUAL_WPN_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_blunt_res?>", String.valueOf(100 - (int) player.calcStat(Stats.BLUNT_WPN_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_dagger_res?>", String.valueOf(100 - (int) player.calcStat(Stats.DAGGER_WPN_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_bow_res?>", String.valueOf(100 - (int) player.calcStat(Stats.BOW_WPN_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_crossbow_res?>", String.valueOf(100 - (int) player.calcStat(Stats.CROSSBOW_WPN_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_pole_res?>", String.valueOf(100 - (int) player.calcStat(Stats.POLE_WPN_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_fist_res?>", String.valueOf(100 - (int) player.calcStat(Stats.FIST_WPN_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_crit_chance_res?>", String.valueOf(100 - (int) player.calcStat(Stats.CRIT_CHANCE_RECEPTIVE, 100, null, null)));
			html = html.replace("<?player_crit_damage_res?>", String.valueOf(100 - (int) player.calcStat(Stats.CRIT_DAMAGE_RECEPTIVE, 100, null, null)));

			String petName = "";
			L2Summon summon = player.getPet();
			if(summon != null)
			{
				if(summon.isPet() && summon.getName() != null) // If pet name exist
					petName = summon.getName();
				else
					petName = "&@" + summon.getTemplate().npcId + ";";
			}
			html = html.replace("<?player_pet_name?>", petName);

			int cubicCount = 0;
			if(!player.getCubics().isEmpty())
				cubicCount = player.getCubics().size();
			html = html.replace("<?player_cubic_count?>", String.valueOf(cubicCount));

			String pledge_rank;
			switch(player.getPledgeRank())
			{
				case 1:
					pledge_rank = "&$1386;";
					break;
				case 2:
					pledge_rank = "&$1388;";
					break;
				case 3:
					pledge_rank = "&$1389;";
					break;
				case 4:
					pledge_rank = "&$1390;";
					break;
				case 5:
					pledge_rank = "&$1391;";
					break;
				case 7:
					pledge_rank = "&$1393;";
					break;
				case 6:
					pledge_rank = "&$1392;";
					break;
				case 8:
					pledge_rank = "&$1394;";
					break;
				case 9:
					pledge_rank = "&$1395;";
					break;
				case 10:
					pledge_rank = "&$1396;";
					break;
				case 12:
					pledge_rank = "&$1398;";
					break;
				case 11:
					pledge_rank = "&$1397;";
					break;
				default: // Vagabond
					pledge_rank = "&$1385;";
					break;
			}
			html = html.replace("<?player_pledge_rank?>", pledge_rank);

			String noble = "";
			if(player.isHero() || player.isDonateHero())
				noble = "Hero";
			else if(player.isNoble() || player.isDonateNoble())
				noble = "Noblesse";
			html = html.replace("<?player_noble?>", noble);

			String race;
			switch(player.getRace())
			{
				case human:
					race = "&$170;";
					break;
				case elf:
					race = "&$171;";
					break;
				case darkelf:
					race = "&$172;";
					break;
				case orc:
					race = "&$173;";
					break;
				case dwarf:
					race = "&$174;";
					break;
				case kamael:
					race = "&$1544;";
					break;
				default:
					race = "Unknown Race";
					break;
			}
			html = html.replace("<?player_race?>", race);

			String sex = player.getSex() == 0 ? "&$177;" : "&$178;";
			html = html.replace("<?player_sex?>", sex);
			ShowBoard.separateAndSend(html, player);
		}
		}catch(Exception e)
		{
		e.printStackTrace();
		}
	}

	@Override
	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}
}
