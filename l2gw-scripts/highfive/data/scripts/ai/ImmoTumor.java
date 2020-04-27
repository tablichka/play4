package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 15.12.11 17:38
 */
public class ImmoTumor extends DefaultNpc
{
	public String dispatcher_maker = "rumwarsha14_1424_dispm1";
	public int tide = 0;
	public int room = 0;
	public int zone = 0;
	public String type = "";
	public L2Skill Skill_Branding = SkillTable.getInstance().getInfo(542375937);
	public L2Skill Skill_dying_display = SkillTable.getInstance().getInfo(395640833);
	public L2Skill Skill_clutch = SkillTable.getInstance().getInfo(392167425);
	public int regen_value = 24;
	public int IsAggressive = 0;
	public float Aggressive_Time = 30.000000f;
	public float DefaultHate = 100.000000f;
	public float Maximum_Hate = 999999984306749440.000000f;
	public int HateClassGroup1 = 4;
	public float HateClassGroup1Boost = 80.000000f;
	public int HateClassGroup2 = 5;
	public float HateClassGroup2Boost = 40.000000f;
	public int FavorClassGroup1 = 3;
	public float FavorClassGroup1Boost = 20.000000f;
	public float ATTACKED_Weight_Point = 2.000000f;
	public float CLAN_ATTACKED_Weight_Point = 0.000000f;
	public float PARTY_ATTACKED_Weight_Point = 0.000000f;
	public float SEE_SPELL_Weight_Point = 1.000000f;
	public float HATE_SKILL_Weight_Point = 100.000000f;
	public float TUMOR_ATTACKED_Weight_Point = 0.000000f;
	public float VEIN_SIGNAL_Weight_Point = 0.000000f;
	public float TUMOR_HELP_Weight_Point = 0.000000f;
	public float LIFESEED_TAUNT_Weight_Point = 0.000000f;
	public int TACT_AGGRESIVE = 0;
	public int TACT_INTERCEPT = 1;
	public int TACT_DEFENSIVE = 2;
	public int reward_siege = 13797;
	public int reward_rate_destroyed = 70;
	public int reward_quantity_destroyed = 10;
	public int TM_RESPAWN_INVIN = 78001;
	public int TIME_respawn_invin = 60;
	public int TM_SPAWN_VEIN = 78002;
	public int TM_SEND_HELP = 78003;
	public int TM_CLUTCH = 78004;
	public int TM_AREA_RENEW = 78005;
	public int TIME_AREA_RENEW = 60;
	public int TIME_clutch = 60;
	public int duration_clutch = 60;
	public String area_z2a_r212_mob_atk1 = "14_24_undying_z2a_r212_atk_up1";
	public String area_z2a_r222_mob_atk1 = "14_24_undying_z2a_r222_atk_up1";
	public String area_z2a_r232_mob_atk1 = "14_24_undying_z2a_r232_atk_up1";
	public String area_z2a_r242_mob_atk1 = "14_24_undying_z2a_r242_atk_up1";
	public String area_z2a_r211_mob_atk1 = "14_24_undying_z2a_r211_atk_up1";
	public String area_z2a_r221_mob_atk1 = "14_24_undying_z2a_r221_atk_up1";
	public String area_z2a_r231_mob_atk1 = "14_24_undying_z2a_r231_atk_up1";
	public String area_z2a_r241_mob_atk1 = "14_24_undying_z2a_r241_atk_up1";
	public String area_z2a_r212_mob_atk2 = "14_24_undying_z2a_r212_atk_up2";
	public String area_z2a_r222_mob_atk2 = "14_24_undying_z2a_r222_atk_up2";
	public String area_z2a_r232_mob_atk2 = "14_24_undying_z2a_r232_atk_up2";
	public String area_z2a_r242_mob_atk2 = "14_24_undying_z2a_r242_atk_up2";
	public String area_z2a_r211_mob_atk2 = "14_24_undying_z2a_r211_atk_up2";
	public String area_z2a_r221_mob_atk2 = "14_24_undying_z2a_r221_atk_up2";
	public String area_z2a_r231_mob_atk2 = "14_24_undying_z2a_r231_atk_up2";
	public String area_z2a_r241_mob_atk2 = "14_24_undying_z2a_r241_atk_up2";
	public String area_z2a_r212_mob_atk3 = "14_24_undying_z2a_r212_atk_up3";
	public String area_z2a_r222_mob_atk3 = "14_24_undying_z2a_r222_atk_up3";
	public String area_z2a_r232_mob_atk3 = "14_24_undying_z2a_r232_atk_up3";
	public String area_z2a_r242_mob_atk3 = "14_24_undying_z2a_r242_atk_up3";
	public String area_z2a_r211_mob_atk3 = "14_24_undying_z2a_r211_atk_up3";
	public String area_z2a_r221_mob_atk3 = "14_24_undying_z2a_r221_atk_up3";
	public String area_z2a_r231_mob_atk3 = "14_24_undying_z2a_r231_atk_up3";
	public String area_z2a_r241_mob_atk3 = "14_24_undying_z2a_r241_atk_up3";
	public String area_z2a_r212_mob_atk4 = "14_24_undying_z2a_r212_atk_up4";
	public String area_z2a_r222_mob_atk4 = "14_24_undying_z2a_r222_atk_up4";
	public String area_z2a_r232_mob_atk4 = "14_24_undying_z2a_r232_atk_up4";
	public String area_z2a_r242_mob_atk4 = "14_24_undying_z2a_r242_atk_up4";
	public String area_z2a_r211_mob_atk4 = "14_24_undying_z2a_r211_atk_up4";
	public String area_z2a_r221_mob_atk4 = "14_24_undying_z2a_r221_atk_up4";
	public String area_z2a_r231_mob_atk4 = "14_24_undying_z2a_r231_atk_up4";
	public String area_z2a_r241_mob_atk4 = "14_24_undying_z2a_r241_atk_up4";
	public String area_z2a_r212_mob_atk5 = "14_24_undying_z2a_r212_atk_up5";
	public String area_z2a_r222_mob_atk5 = "14_24_undying_z2a_r222_atk_up5";
	public String area_z2a_r232_mob_atk5 = "14_24_undying_z2a_r232_atk_up5";
	public String area_z2a_r242_mob_atk5 = "14_24_undying_z2a_r242_atk_up5";
	public String area_z2a_r211_mob_atk5 = "14_24_undying_z2a_r211_atk_up5";
	public String area_z2a_r221_mob_atk5 = "14_24_undying_z2a_r221_atk_up5";
	public String area_z2a_r231_mob_atk5 = "14_24_undying_z2a_r231_atk_up5";
	public String area_z2a_r241_mob_atk5 = "14_24_undying_z2a_r241_atk_up5";
	public String area_z2a_r212_mob_def1 = "14_24_undying_z2a_r212_def_up1";
	public String area_z2a_r222_mob_def1 = "14_24_undying_z2a_r222_def_up1";
	public String area_z2a_r232_mob_def1 = "14_24_undying_z2a_r232_def_up1";
	public String area_z2a_r242_mob_def1 = "14_24_undying_z2a_r242_def_up1";
	public String area_z2a_r211_mob_def1 = "14_24_undying_z2a_r211_def_up1";
	public String area_z2a_r221_mob_def1 = "14_24_undying_z2a_r221_def_up1";
	public String area_z2a_r231_mob_def1 = "14_24_undying_z2a_r231_def_up1";
	public String area_z2a_r241_mob_def1 = "14_24_undying_z2a_r241_def_up1";
	public String area_z2a_r212_mob_def2 = "14_24_undying_z2a_r212_def_up2";
	public String area_z2a_r222_mob_def2 = "14_24_undying_z2a_r222_def_up2";
	public String area_z2a_r232_mob_def2 = "14_24_undying_z2a_r232_def_up2";
	public String area_z2a_r242_mob_def2 = "14_24_undying_z2a_r242_def_up2";
	public String area_z2a_r211_mob_def2 = "14_24_undying_z2a_r211_def_up2";
	public String area_z2a_r221_mob_def2 = "14_24_undying_z2a_r221_def_up2";
	public String area_z2a_r231_mob_def2 = "14_24_undying_z2a_r231_def_up2";
	public String area_z2a_r241_mob_def2 = "14_24_undying_z2a_r241_def_up2";
	public String area_z2a_r212_mob_def3 = "14_24_undying_z2a_r212_def_up3";
	public String area_z2a_r222_mob_def3 = "14_24_undying_z2a_r222_def_up3";
	public String area_z2a_r232_mob_def3 = "14_24_undying_z2a_r232_def_up3";
	public String area_z2a_r242_mob_def3 = "14_24_undying_z2a_r242_def_up3";
	public String area_z2a_r211_mob_def3 = "14_24_undying_z2a_r211_def_up3";
	public String area_z2a_r221_mob_def3 = "14_24_undying_z2a_r221_def_up3";
	public String area_z2a_r231_mob_def3 = "14_24_undying_z2a_r231_def_up3";
	public String area_z2a_r241_mob_def3 = "14_24_undying_z2a_r241_def_up3";
	public String area_z2a_r212_mob_def4 = "14_24_undying_z2a_r212_def_up4";
	public String area_z2a_r222_mob_def4 = "14_24_undying_z2a_r222_def_up4";
	public String area_z2a_r232_mob_def4 = "14_24_undying_z2a_r232_def_up4";
	public String area_z2a_r242_mob_def4 = "14_24_undying_z2a_r242_def_up4";
	public String area_z2a_r211_mob_def4 = "14_24_undying_z2a_r211_def_up4";
	public String area_z2a_r221_mob_def4 = "14_24_undying_z2a_r221_def_up4";
	public String area_z2a_r231_mob_def4 = "14_24_undying_z2a_r231_def_up4";
	public String area_z2a_r241_mob_def4 = "14_24_undying_z2a_r241_def_up4";
	public String area_z2a_r212_mob_def5 = "14_24_undying_z2a_r212_def_up5";
	public String area_z2a_r222_mob_def5 = "14_24_undying_z2a_r222_def_up5";
	public String area_z2a_r232_mob_def5 = "14_24_undying_z2a_r232_def_up5";
	public String area_z2a_r242_mob_def5 = "14_24_undying_z2a_r242_def_up5";
	public String area_z2a_r211_mob_def5 = "14_24_undying_z2a_r211_def_up5";
	public String area_z2a_r221_mob_def5 = "14_24_undying_z2a_r221_def_up5";
	public String area_z2a_r231_mob_def5 = "14_24_undying_z2a_r231_def_up5";
	public String area_z2a_r241_mob_def5 = "14_24_undying_z2a_r241_def_up5";
	public String area_z2d_r212_mob_atk1 = "14_24_undying_z2d_r212_atk_up1";
	public String area_z2d_r222_mob_atk1 = "14_24_undying_z2d_r222_atk_up1";
	public String area_z2d_r232_mob_atk1 = "14_24_undying_z2d_r232_atk_up1";
	public String area_z2d_r242_mob_atk1 = "14_24_undying_z2d_r242_atk_up1";
	public String area_z2d_r211_mob_atk1 = "14_24_undying_z2d_r211_atk_up1";
	public String area_z2d_r221_mob_atk1 = "14_24_undying_z2d_r221_atk_up1";
	public String area_z2d_r231_mob_atk1 = "14_24_undying_z2d_r231_atk_up1";
	public String area_z2d_r241_mob_atk1 = "14_24_undying_z2d_r241_atk_up1";
	public String area_z2d_r212_mob_atk2 = "14_24_undying_z2d_r212_atk_up2";
	public String area_z2d_r222_mob_atk2 = "14_24_undying_z2d_r222_atk_up2";
	public String area_z2d_r232_mob_atk2 = "14_24_undying_z2d_r232_atk_up2";
	public String area_z2d_r242_mob_atk2 = "14_24_undying_z2d_r242_atk_up2";
	public String area_z2d_r211_mob_atk2 = "14_24_undying_z2d_r211_atk_up2";
	public String area_z2d_r221_mob_atk2 = "14_24_undying_z2d_r221_atk_up2";
	public String area_z2d_r231_mob_atk2 = "14_24_undying_z2d_r231_atk_up2";
	public String area_z2d_r241_mob_atk2 = "14_24_undying_z2d_r241_atk_up2";
	public String area_z2d_r212_mob_atk3 = "14_24_undying_z2d_r212_atk_up3";
	public String area_z2d_r222_mob_atk3 = "14_24_undying_z2d_r222_atk_up3";
	public String area_z2d_r232_mob_atk3 = "14_24_undying_z2d_r232_atk_up3";
	public String area_z2d_r242_mob_atk3 = "14_24_undying_z2d_r242_atk_up3";
	public String area_z2d_r211_mob_atk3 = "14_24_undying_z2d_r211_atk_up3";
	public String area_z2d_r221_mob_atk3 = "14_24_undying_z2d_r221_atk_up3";
	public String area_z2d_r231_mob_atk3 = "14_24_undying_z2d_r231_atk_up3";
	public String area_z2d_r241_mob_atk3 = "14_24_undying_z2d_r241_atk_up3";
	public String area_z2d_r212_mob_atk4 = "14_24_undying_z2d_r212_atk_up4";
	public String area_z2d_r222_mob_atk4 = "14_24_undying_z2d_r222_atk_up4";
	public String area_z2d_r232_mob_atk4 = "14_24_undying_z2d_r232_atk_up4";
	public String area_z2d_r242_mob_atk4 = "14_24_undying_z2d_r242_atk_up4";
	public String area_z2d_r211_mob_atk4 = "14_24_undying_z2d_r211_atk_up4";
	public String area_z2d_r221_mob_atk4 = "14_24_undying_z2d_r221_atk_up4";
	public String area_z2d_r231_mob_atk4 = "14_24_undying_z2d_r231_atk_up4";
	public String area_z2d_r241_mob_atk4 = "14_24_undying_z2d_r241_atk_up4";
	public String area_z2d_r212_mob_atk5 = "14_24_undying_z2d_r212_atk_up5";
	public String area_z2d_r222_mob_atk5 = "14_24_undying_z2d_r222_atk_up5";
	public String area_z2d_r232_mob_atk5 = "14_24_undying_z2d_r232_atk_up5";
	public String area_z2d_r242_mob_atk5 = "14_24_undying_z2d_r242_atk_up5";
	public String area_z2d_r211_mob_atk5 = "14_24_undying_z2d_r211_atk_up5";
	public String area_z2d_r221_mob_atk5 = "14_24_undying_z2d_r221_atk_up5";
	public String area_z2d_r231_mob_atk5 = "14_24_undying_z2d_r231_atk_up5";
	public String area_z2d_r241_mob_atk5 = "14_24_undying_z2d_r241_atk_up5";
	public String area_z2d_r212_mob_def1 = "14_24_undying_z2d_r212_def_up1";
	public String area_z2d_r222_mob_def1 = "14_24_undying_z2d_r222_def_up1";
	public String area_z2d_r232_mob_def1 = "14_24_undying_z2d_r232_def_up1";
	public String area_z2d_r242_mob_def1 = "14_24_undying_z2d_r242_def_up1";
	public String area_z2d_r211_mob_def1 = "14_24_undying_z2d_r211_def_up1";
	public String area_z2d_r221_mob_def1 = "14_24_undying_z2d_r221_def_up1";
	public String area_z2d_r231_mob_def1 = "14_24_undying_z2d_r231_def_up1";
	public String area_z2d_r241_mob_def1 = "14_24_undying_z2d_r241_def_up1";
	public String area_z2d_r212_mob_def2 = "14_24_undying_z2d_r212_def_up2";
	public String area_z2d_r222_mob_def2 = "14_24_undying_z2d_r222_def_up2";
	public String area_z2d_r232_mob_def2 = "14_24_undying_z2d_r232_def_up2";
	public String area_z2d_r242_mob_def2 = "14_24_undying_z2d_r242_def_up2";
	public String area_z2d_r211_mob_def2 = "14_24_undying_z2d_r211_def_up2";
	public String area_z2d_r221_mob_def2 = "14_24_undying_z2d_r221_def_up2";
	public String area_z2d_r231_mob_def2 = "14_24_undying_z2d_r231_def_up2";
	public String area_z2d_r241_mob_def2 = "14_24_undying_z2d_r241_def_up2";
	public String area_z2d_r212_mob_def3 = "14_24_undying_z2d_r212_def_up3";
	public String area_z2d_r222_mob_def3 = "14_24_undying_z2d_r222_def_up3";
	public String area_z2d_r232_mob_def3 = "14_24_undying_z2d_r232_def_up3";
	public String area_z2d_r242_mob_def3 = "14_24_undying_z2d_r242_def_up3";
	public String area_z2d_r211_mob_def3 = "14_24_undying_z2d_r211_def_up3";
	public String area_z2d_r221_mob_def3 = "14_24_undying_z2d_r221_def_up3";
	public String area_z2d_r231_mob_def3 = "14_24_undying_z2d_r231_def_up3";
	public String area_z2d_r241_mob_def3 = "14_24_undying_z2d_r241_def_up3";
	public String area_z2d_r212_mob_def4 = "14_24_undying_z2d_r212_def_up4";
	public String area_z2d_r222_mob_def4 = "14_24_undying_z2d_r222_def_up4";
	public String area_z2d_r232_mob_def4 = "14_24_undying_z2d_r232_def_up4";
	public String area_z2d_r242_mob_def4 = "14_24_undying_z2d_r242_def_up4";
	public String area_z2d_r211_mob_def4 = "14_24_undying_z2d_r211_def_up4";
	public String area_z2d_r221_mob_def4 = "14_24_undying_z2d_r221_def_up4";
	public String area_z2d_r231_mob_def4 = "14_24_undying_z2d_r231_def_up4";
	public String area_z2d_r241_mob_def4 = "14_24_undying_z2d_r241_def_up4";
	public String area_z2d_r212_mob_def5 = "14_24_undying_z2d_r212_def_up5";
	public String area_z2d_r222_mob_def5 = "14_24_undying_z2d_r222_def_up5";
	public String area_z2d_r232_mob_def5 = "14_24_undying_z2d_r232_def_up5";
	public String area_z2d_r242_mob_def5 = "14_24_undying_z2d_r242_def_up5";
	public String area_z2d_r211_mob_def5 = "14_24_undying_z2d_r211_def_up5";
	public String area_z2d_r221_mob_def5 = "14_24_undying_z2d_r221_def_up5";
	public String area_z2d_r231_mob_def5 = "14_24_undying_z2d_r231_def_up5";
	public String area_z2d_r241_mob_def5 = "14_24_undying_z2d_r241_def_up5";
	public String area_z3a_r301_mob_atk1 = "14_24_undying_z3a_r301_atk_up1";
	public String area_z3a_r302_mob_atk1 = "14_24_undying_z3a_r302_atk_up1";
	public String area_z3a_r303_mob_atk1 = "14_24_undying_z3a_r303_atk_up1";
	public String area_z3a_r304_mob_atk1 = "14_24_undying_z3a_r304_atk_up1";
	public String area_z3a_r305_mob_atk1 = "14_24_undying_z3a_r305_atk_up1";
	public String area_z3a_r306_mob_atk1 = "14_24_undying_z3a_r306_atk_up1";
	public String area_z3a_r301_mob_atk2 = "14_24_undying_z3a_r301_atk_up2";
	public String area_z3a_r302_mob_atk2 = "14_24_undying_z3a_r302_atk_up2";
	public String area_z3a_r303_mob_atk2 = "14_24_undying_z3a_r303_atk_up2";
	public String area_z3a_r304_mob_atk2 = "14_24_undying_z3a_r304_atk_up2";
	public String area_z3a_r305_mob_atk2 = "14_24_undying_z3a_r305_atk_up2";
	public String area_z3a_r306_mob_atk2 = "14_24_undying_z3a_r306_atk_up2";
	public String area_z3a_r301_mob_atk3 = "14_24_undying_z3a_r301_atk_up3";
	public String area_z3a_r302_mob_atk3 = "14_24_undying_z3a_r302_atk_up3";
	public String area_z3a_r303_mob_atk3 = "14_24_undying_z3a_r303_atk_up3";
	public String area_z3a_r304_mob_atk3 = "14_24_undying_z3a_r304_atk_up3";
	public String area_z3a_r305_mob_atk3 = "14_24_undying_z3a_r305_atk_up3";
	public String area_z3a_r306_mob_atk3 = "14_24_undying_z3a_r306_atk_up3";
	public String area_z3a_r301_mob_atk4 = "14_24_undying_z3a_r301_atk_up4";
	public String area_z3a_r302_mob_atk4 = "14_24_undying_z3a_r302_atk_up4";
	public String area_z3a_r303_mob_atk4 = "14_24_undying_z3a_r303_atk_up4";
	public String area_z3a_r304_mob_atk4 = "14_24_undying_z3a_r304_atk_up4";
	public String area_z3a_r305_mob_atk4 = "14_24_undying_z3a_r305_atk_up4";
	public String area_z3a_r306_mob_atk4 = "14_24_undying_z3a_r306_atk_up4";
	public String area_z3a_r301_mob_atk5 = "14_24_undying_z3a_r301_atk_up5";
	public String area_z3a_r302_mob_atk5 = "14_24_undying_z3a_r302_atk_up5";
	public String area_z3a_r303_mob_atk5 = "14_24_undying_z3a_r303_atk_up5";
	public String area_z3a_r304_mob_atk5 = "14_24_undying_z3a_r304_atk_up5";
	public String area_z3a_r305_mob_atk5 = "14_24_undying_z3a_r305_atk_up5";
	public String area_z3a_r306_mob_atk5 = "14_24_undying_z3a_r306_atk_up5";
	public String area_z3a_r301_mob_def1 = "14_24_undying_z3a_r301_def_up1";
	public String area_z3a_r302_mob_def1 = "14_24_undying_z3a_r302_def_up1";
	public String area_z3a_r303_mob_def1 = "14_24_undying_z3a_r303_def_up1";
	public String area_z3a_r304_mob_def1 = "14_24_undying_z3a_r304_def_up1";
	public String area_z3a_r305_mob_def1 = "14_24_undying_z3a_r305_def_up1";
	public String area_z3a_r306_mob_def1 = "14_24_undying_z3a_r306_def_up1";
	public String area_z3a_r301_mob_def2 = "14_24_undying_z3a_r301_def_up2";
	public String area_z3a_r302_mob_def2 = "14_24_undying_z3a_r302_def_up2";
	public String area_z3a_r303_mob_def2 = "14_24_undying_z3a_r303_def_up2";
	public String area_z3a_r304_mob_def2 = "14_24_undying_z3a_r304_def_up2";
	public String area_z3a_r305_mob_def2 = "14_24_undying_z3a_r305_def_up2";
	public String area_z3a_r306_mob_def2 = "14_24_undying_z3a_r306_def_up2";
	public String area_z3a_r301_mob_def3 = "14_24_undying_z3a_r301_def_up3";
	public String area_z3a_r302_mob_def3 = "14_24_undying_z3a_r302_def_up3";
	public String area_z3a_r303_mob_def3 = "14_24_undying_z3a_r303_def_up3";
	public String area_z3a_r304_mob_def3 = "14_24_undying_z3a_r304_def_up3";
	public String area_z3a_r305_mob_def3 = "14_24_undying_z3a_r305_def_up3";
	public String area_z3a_r306_mob_def3 = "14_24_undying_z3a_r306_def_up3";
	public String area_z3a_r301_mob_def4 = "14_24_undying_z3a_r301_def_up4";
	public String area_z3a_r302_mob_def4 = "14_24_undying_z3a_r302_def_up4";
	public String area_z3a_r303_mob_def4 = "14_24_undying_z3a_r303_def_up4";
	public String area_z3a_r304_mob_def4 = "14_24_undying_z3a_r304_def_up4";
	public String area_z3a_r305_mob_def4 = "14_24_undying_z3a_r305_def_up4";
	public String area_z3a_r306_mob_def4 = "14_24_undying_z3a_r306_def_up4";
	public String area_z3a_r301_mob_def5 = "14_24_undying_z3a_r301_def_up5";
	public String area_z3a_r302_mob_def5 = "14_24_undying_z3a_r302_def_up5";
	public String area_z3a_r303_mob_def5 = "14_24_undying_z3a_r303_def_up5";
	public String area_z3a_r304_mob_def5 = "14_24_undying_z3a_r304_def_up5";
	public String area_z3a_r305_mob_def5 = "14_24_undying_z3a_r305_def_up5";
	public String area_z3a_r306_mob_def5 = "14_24_undying_z3a_r306_def_up5";
	public String area_z3d_r301_mob_atk1 = "14_24_undying_z3d_r301_atk_up1";
	public String area_z3d_r302_mob_atk1 = "14_24_undying_z3d_r302_atk_up1";
	public String area_z3d_r303_mob_atk1 = "14_24_undying_z3d_r303_atk_up1";
	public String area_z3d_r304_mob_atk1 = "14_24_undying_z3d_r304_atk_up1";
	public String area_z3d_r305_mob_atk1 = "14_24_undying_z3d_r305_atk_up1";
	public String area_z3d_r306_mob_atk1 = "14_24_undying_z3d_r306_atk_up1";
	public String area_z3d_r301_mob_atk2 = "14_24_undying_z3d_r301_atk_up2";
	public String area_z3d_r302_mob_atk2 = "14_24_undying_z3d_r302_atk_up2";
	public String area_z3d_r303_mob_atk2 = "14_24_undying_z3d_r303_atk_up2";
	public String area_z3d_r304_mob_atk2 = "14_24_undying_z3d_r304_atk_up2";
	public String area_z3d_r305_mob_atk2 = "14_24_undying_z3d_r305_atk_up2";
	public String area_z3d_r306_mob_atk2 = "14_24_undying_z3d_r306_atk_up2";
	public String area_z3d_r301_mob_atk3 = "14_24_undying_z3d_r301_atk_up3";
	public String area_z3d_r302_mob_atk3 = "14_24_undying_z3d_r302_atk_up3";
	public String area_z3d_r303_mob_atk3 = "14_24_undying_z3d_r303_atk_up3";
	public String area_z3d_r304_mob_atk3 = "14_24_undying_z3d_r304_atk_up3";
	public String area_z3d_r305_mob_atk3 = "14_24_undying_z3d_r305_atk_up3";
	public String area_z3d_r306_mob_atk3 = "14_24_undying_z3d_r306_atk_up3";
	public String area_z3d_r301_mob_atk4 = "14_24_undying_z3d_r301_atk_up4";
	public String area_z3d_r302_mob_atk4 = "14_24_undying_z3d_r302_atk_up4";
	public String area_z3d_r303_mob_atk4 = "14_24_undying_z3d_r303_atk_up4";
	public String area_z3d_r304_mob_atk4 = "14_24_undying_z3d_r304_atk_up4";
	public String area_z3d_r305_mob_atk4 = "14_24_undying_z3d_r305_atk_up4";
	public String area_z3d_r306_mob_atk4 = "14_24_undying_z3d_r306_atk_up4";
	public String area_z3d_r301_mob_atk5 = "14_24_undying_z3d_r301_atk_up5";
	public String area_z3d_r302_mob_atk5 = "14_24_undying_z3d_r302_atk_up5";
	public String area_z3d_r303_mob_atk5 = "14_24_undying_z3d_r303_atk_up5";
	public String area_z3d_r304_mob_atk5 = "14_24_undying_z3d_r304_atk_up5";
	public String area_z3d_r305_mob_atk5 = "14_24_undying_z3d_r305_atk_up5";
	public String area_z3d_r306_mob_atk5 = "14_24_undying_z3d_r306_atk_up5";
	public String area_z3d_r301_mob_def1 = "14_24_undying_z3d_r301_def_up1";
	public String area_z3d_r302_mob_def1 = "14_24_undying_z3d_r302_def_up1";
	public String area_z3d_r303_mob_def1 = "14_24_undying_z3d_r303_def_up1";
	public String area_z3d_r304_mob_def1 = "14_24_undying_z3d_r304_def_up1";
	public String area_z3d_r305_mob_def1 = "14_24_undying_z3d_r305_def_up1";
	public String area_z3d_r306_mob_def1 = "14_24_undying_z3d_r306_def_up1";
	public String area_z3d_r301_mob_def2 = "14_24_undying_z3d_r301_def_up2";
	public String area_z3d_r302_mob_def2 = "14_24_undying_z3d_r302_def_up2";
	public String area_z3d_r303_mob_def2 = "14_24_undying_z3d_r303_def_up2";
	public String area_z3d_r304_mob_def2 = "14_24_undying_z3d_r304_def_up2";
	public String area_z3d_r305_mob_def2 = "14_24_undying_z3d_r305_def_up2";
	public String area_z3d_r306_mob_def2 = "14_24_undying_z3d_r306_def_up2";
	public String area_z3d_r301_mob_def3 = "14_24_undying_z3d_r301_def_up3";
	public String area_z3d_r302_mob_def3 = "14_24_undying_z3d_r302_def_up3";
	public String area_z3d_r303_mob_def3 = "14_24_undying_z3d_r303_def_up3";
	public String area_z3d_r304_mob_def3 = "14_24_undying_z3d_r304_def_up3";
	public String area_z3d_r305_mob_def3 = "14_24_undying_z3d_r305_def_up3";
	public String area_z3d_r306_mob_def3 = "14_24_undying_z3d_r306_def_up3";
	public String area_z3d_r301_mob_def4 = "14_24_undying_z3d_r301_def_up4";
	public String area_z3d_r302_mob_def4 = "14_24_undying_z3d_r302_def_up4";
	public String area_z3d_r303_mob_def4 = "14_24_undying_z3d_r303_def_up4";
	public String area_z3d_r304_mob_def4 = "14_24_undying_z3d_r304_def_up4";
	public String area_z3d_r305_mob_def4 = "14_24_undying_z3d_r305_def_up4";
	public String area_z3d_r306_mob_def4 = "14_24_undying_z3d_r306_def_up4";
	public String area_z3d_r301_mob_def5 = "14_24_undying_z3d_r301_def_up5";
	public String area_z3d_r302_mob_def5 = "14_24_undying_z3d_r302_def_up5";
	public String area_z3d_r303_mob_def5 = "14_24_undying_z3d_r303_def_up5";
	public String area_z3d_r304_mob_def5 = "14_24_undying_z3d_r304_def_up5";
	public String area_z3d_r305_mob_def5 = "14_24_undying_z3d_r305_def_up5";
	public String area_z3d_r306_mob_def5 = "14_24_undying_z3d_r306_def_up5";
	public float Attack_DecayRatio = 6.600000f;
	public float UseSkill_DecayRatio = 66000.000000f;
	public float Attack_BoostValue = 300.000000f;
	public float UseSkill_BoostValue = 100000.000000f;
	public GArray<String> roomAreas = new GArray<>(10);
	
	public ImmoTumor(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(room > 0)
		{
			switch(room)
			{
				case 212:
					roomAreas.add(area_z2a_r212_mob_atk1);
					roomAreas.add(area_z2a_r212_mob_atk2);
					roomAreas.add(area_z2a_r212_mob_atk3);
					roomAreas.add(area_z2a_r212_mob_atk4);
					roomAreas.add(area_z2a_r212_mob_atk5);
					roomAreas.add(area_z2a_r212_mob_def1);
					roomAreas.add(area_z2a_r212_mob_def2);
					roomAreas.add(area_z2a_r212_mob_def3);
					roomAreas.add(area_z2a_r212_mob_def4);
					roomAreas.add(area_z2a_r212_mob_def5);
					break;
				case 222:
					roomAreas.add(area_z2a_r222_mob_atk1);
					roomAreas.add(area_z2a_r222_mob_atk2);
					roomAreas.add(area_z2a_r222_mob_atk3);
					roomAreas.add(area_z2a_r222_mob_atk4);
					roomAreas.add(area_z2a_r222_mob_atk5);
					roomAreas.add(area_z2a_r222_mob_def1);
					roomAreas.add(area_z2a_r222_mob_def2);
					roomAreas.add(area_z2a_r222_mob_def3);
					roomAreas.add(area_z2a_r222_mob_def4);
					roomAreas.add(area_z2a_r222_mob_def5);
					break;
				case 232:
					roomAreas.add(area_z2a_r232_mob_atk1);
					roomAreas.add(area_z2a_r232_mob_atk2);
					roomAreas.add(area_z2a_r232_mob_atk3);
					roomAreas.add(area_z2a_r232_mob_atk4);
					roomAreas.add(area_z2a_r232_mob_atk5);
					roomAreas.add(area_z2a_r232_mob_def1);
					roomAreas.add(area_z2a_r232_mob_def2);
					roomAreas.add(area_z2a_r232_mob_def3);
					roomAreas.add(area_z2a_r232_mob_def4);
					roomAreas.add(area_z2a_r232_mob_def5);
					break;
				case 242:
					roomAreas.add(area_z2a_r242_mob_atk1);
					roomAreas.add(area_z2a_r242_mob_atk2);
					roomAreas.add(area_z2a_r242_mob_atk3);
					roomAreas.add(area_z2a_r242_mob_atk4);
					roomAreas.add(area_z2a_r242_mob_atk5);
					roomAreas.add(area_z2a_r242_mob_def1);
					roomAreas.add(area_z2a_r242_mob_def2);
					roomAreas.add(area_z2a_r242_mob_def3);
					roomAreas.add(area_z2a_r242_mob_def4);
					roomAreas.add(area_z2a_r242_mob_def5);
					break;
				case 211:
					roomAreas.add(area_z2a_r211_mob_atk1);
					roomAreas.add(area_z2a_r211_mob_atk2);
					roomAreas.add(area_z2a_r211_mob_atk3);
					roomAreas.add(area_z2a_r211_mob_atk4);
					roomAreas.add(area_z2a_r211_mob_atk5);
					roomAreas.add(area_z2a_r211_mob_def1);
					roomAreas.add(area_z2a_r211_mob_def2);
					roomAreas.add(area_z2a_r211_mob_def3);
					roomAreas.add(area_z2a_r211_mob_def4);
					roomAreas.add(area_z2a_r211_mob_def5);
					break;
				case 221:
					roomAreas.add(area_z2a_r221_mob_atk1);
					roomAreas.add(area_z2a_r221_mob_atk2);
					roomAreas.add(area_z2a_r221_mob_atk3);
					roomAreas.add(area_z2a_r221_mob_atk4);
					roomAreas.add(area_z2a_r221_mob_atk5);
					roomAreas.add(area_z2a_r221_mob_def1);
					roomAreas.add(area_z2a_r221_mob_def2);
					roomAreas.add(area_z2a_r221_mob_def3);
					roomAreas.add(area_z2a_r221_mob_def4);
					roomAreas.add(area_z2a_r221_mob_def5);
					break;
				case 231:
					roomAreas.add(area_z2a_r231_mob_atk1);
					roomAreas.add(area_z2a_r231_mob_atk2);
					roomAreas.add(area_z2a_r231_mob_atk3);
					roomAreas.add(area_z2a_r231_mob_atk4);
					roomAreas.add(area_z2a_r231_mob_atk5);
					roomAreas.add(area_z2a_r231_mob_def1);
					roomAreas.add(area_z2a_r231_mob_def2);
					roomAreas.add(area_z2a_r231_mob_def3);
					roomAreas.add(area_z2a_r231_mob_def4);
					roomAreas.add(area_z2a_r231_mob_def5);
					break;
				case 241:
					roomAreas.add(area_z2a_r241_mob_atk1);
					roomAreas.add(area_z2a_r241_mob_atk2);
					roomAreas.add(area_z2a_r241_mob_atk3);
					roomAreas.add(area_z2a_r241_mob_atk4);
					roomAreas.add(area_z2a_r241_mob_atk5);
					roomAreas.add(area_z2a_r241_mob_def1);
					roomAreas.add(area_z2a_r241_mob_def2);
					roomAreas.add(area_z2a_r241_mob_def3);
					roomAreas.add(area_z2a_r241_mob_def4);
					roomAreas.add(area_z2a_r241_mob_def5);
					break;
				case 301:
					roomAreas.add(area_z3a_r301_mob_atk1);
					roomAreas.add(area_z3a_r301_mob_atk2);
					roomAreas.add(area_z3a_r301_mob_atk3);
					roomAreas.add(area_z3a_r301_mob_atk4);
					roomAreas.add(area_z3a_r301_mob_atk5);
					roomAreas.add(area_z3a_r301_mob_def1);
					roomAreas.add(area_z3a_r301_mob_def1);
					roomAreas.add(area_z3a_r301_mob_def1);
					roomAreas.add(area_z3a_r301_mob_def1);
					roomAreas.add(area_z3a_r301_mob_def1);
					roomAreas.add(area_z3a_r301_mob_def1);
					break;
				case 302:
					roomAreas.add(area_z3a_r302_mob_atk1);
					roomAreas.add(area_z3a_r302_mob_atk2);
					roomAreas.add(area_z3a_r302_mob_atk3);
					roomAreas.add(area_z3a_r302_mob_atk4);
					roomAreas.add(area_z3a_r302_mob_atk5);
					roomAreas.add(area_z3a_r302_mob_def1);
					roomAreas.add(area_z3a_r302_mob_def2);
					roomAreas.add(area_z3a_r302_mob_def3);
					roomAreas.add(area_z3a_r302_mob_def4);
					roomAreas.add(area_z3a_r302_mob_def5);
					break;
				case 303:
					roomAreas.add(area_z3a_r303_mob_atk1);
					roomAreas.add(area_z3a_r303_mob_atk2);
					roomAreas.add(area_z3a_r303_mob_atk3);
					roomAreas.add(area_z3a_r303_mob_atk4);
					roomAreas.add(area_z3a_r303_mob_atk5);
					roomAreas.add(area_z3a_r303_mob_def1);
					roomAreas.add(area_z3a_r303_mob_def2);
					roomAreas.add(area_z3a_r303_mob_def3);
					roomAreas.add(area_z3a_r303_mob_def4);
					roomAreas.add(area_z3a_r303_mob_def5);
					break;
				case 304:
					roomAreas.add(area_z3a_r304_mob_atk1);
					roomAreas.add(area_z3a_r304_mob_atk2);
					roomAreas.add(area_z3a_r304_mob_atk3);
					roomAreas.add(area_z3a_r304_mob_atk4);
					roomAreas.add(area_z3a_r304_mob_atk5);
					roomAreas.add(area_z3a_r304_mob_def1);
					roomAreas.add(area_z3a_r304_mob_def2);
					roomAreas.add(area_z3a_r304_mob_def3);
					roomAreas.add(area_z3a_r304_mob_def4);
					roomAreas.add(area_z3a_r304_mob_def5);
					break;
				case 305:
					roomAreas.add(area_z3a_r305_mob_atk1);
					roomAreas.add(area_z3a_r305_mob_atk2);
					roomAreas.add(area_z3a_r305_mob_atk3);
					roomAreas.add(area_z3a_r305_mob_atk4);
					roomAreas.add(area_z3a_r305_mob_atk5);
					roomAreas.add(area_z3a_r305_mob_def1);
					roomAreas.add(area_z3a_r305_mob_def2);
					roomAreas.add(area_z3a_r305_mob_def3);
					roomAreas.add(area_z3a_r305_mob_def4);
					roomAreas.add(area_z3a_r305_mob_def5);
					break;
				case 306:
					roomAreas.add(area_z3a_r306_mob_atk1);
					roomAreas.add(area_z3a_r306_mob_atk2);
					roomAreas.add(area_z3a_r306_mob_atk3);
					roomAreas.add(area_z3a_r306_mob_atk4);
					roomAreas.add(area_z3a_r306_mob_atk5);
					roomAreas.add(area_z3a_r306_mob_def1);
					roomAreas.add(area_z3a_r306_mob_def2);
					roomAreas.add(area_z3a_r306_mob_def3);
					roomAreas.add(area_z3a_r306_mob_def4);
					roomAreas.add(area_z3a_r306_mob_def5);
					break;
			}
		}

		_thisActor.i_ai0 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.setCurrentHp(_thisActor.getMaxHp() * 0.500000);
		addTimer(TM_RESPAWN_INVIN, TIME_respawn_invin * 1000);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010058 && _thisActor.i_ai0 != 0)
		{
			if((Long) arg1 == 0)
			{
				_thisActor.i_ai2++;
				_thisActor.setHpRegen(_thisActor.getTemplate().baseHpReg + regen_value);
				L2NpcInstance c0 = L2ObjectsStorage.getAsNpc((Long) arg2);
				if(c0 != null)
				{
					c0.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 998915, 0, 0);
				}
			}
			else if((Long) arg1 == 1)
			{
				_thisActor.i_ai2 -= (Long) arg2;
				if(_thisActor.getBaseHpRegen() - (Long) arg2 * regen_value > 0)
				{
					_thisActor.setHpRegen(_thisActor.getBaseHpRegen() - (Long) arg2 * regen_value);
				}
				else
				{
					_thisActor.setHpRegen(0);
				}
			}
		}
		else if(eventId == 989804)
		{
			double f0 = 0;
			if((Integer) arg1 == 99)
			{
				_thisActor.setCurrentHp(_thisActor.getMaxHp() * 0.300000);
			}
			else if((tide == 2 && (Integer) arg1 == 0) || (tide == 3 && (Integer) arg1 == 0))
			{
				f0 = 0.800000;
			}
			else if(tide == 3 && (Integer) arg1 == 1)
			{
				f0 = 0.670000;
			}
			else if(tide == 2 && (Integer) arg1 == 1)
			{
				f0 = 0.600000;
			}
			else if(tide == 3 && (Integer) arg1 == 2)
			{
				f0 = 0.540000;
			}
			else if((tide == 2 && (Integer) arg1 == 2) || (tide == 3 && (Integer) arg1 == 3))
			{
				f0 = 0.400000;
			}
			else if(tide == 3 && (Integer) arg1 == 4)
			{
				f0 = 0.270000;
			}
			else if(tide == 2 && (Integer) arg1 == 3)
			{
				f0 = 0.200000;
			}
			else if(tide == 3 && (Integer) arg1 == 5)
			{
				f0 = 0.140000;
			}
			else if((tide == 2 && (Integer) arg1 == 4) || (tide == 3 && (Integer) arg1 == 6))
			{
				f0 = 0.000000;
			}
			if(f0 > 0.000000)
			{
				addUseSkillDesire(_thisActor, Skill_dying_display, 1, 1, 10000000000L);
				_thisActor.setCurrentHp(_thisActor.getCurrentHp() + _thisActor.getMaxHp() * f0);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		int i1 = 0;
		if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.800000)
		{
			i1 = 0;
		}
		else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.600000)
		{
			i1 = 1;
		}
		else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.400000)
		{
			i1 = 2;
		}
		else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.200000)
		{
			i1 = 3;
		}
		else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.010000)
		{
			i1 = 4;
		}
		if(damage / _thisActor.getMaxHp() >= 0.010000)
		{
			i1 += 4;
		}
		else if(damage / _thisActor.getMaxHp() >= 0.006000)
		{
			i1 += 3;
		}
		else if(damage / _thisActor.getMaxHp() >= 0.003000)
		{
			i1 += 2;
		}
		else if(damage / _thisActor.getMaxHp() >= 0.001000)
		{
			i1 += 1;
		}

		broadcastScriptEvent(78010049, attacker.getStoredId(), i1, 700);
		if(skill == Skill_Branding)
		{
			if(attacker.isPlayer())
			{
				Functions.showSystemMessageFStr(attacker, 1800292, String.valueOf((int) (_thisActor.getCurrentHp() * 0.050000)));
				_thisActor.setCurrentHp(_thisActor.getCurrentHp() - _thisActor.getCurrentHp() * 0.050000);
			}
		}
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getAggroListSize() == 0)
			{
				float f0 = DefaultHate;
				if(HateClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup1, attacker.getActiveClass()))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, attacker.getActiveClass()))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getActiveClass()))
					{
						f0 -= FavorClassGroup1Boost;
					}
				}
				if((f0 + 1) < 0)
				{
					f0 = 0;
				}
				else
				{
					f0 = damage * (f0 + 1);
				}
				_thisActor.addDamageHate(attacker, 0, (long) (f0 * ATTACKED_Weight_Point));
			}
			else
			{
				float f0 = DefaultHate;
				if(HateClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup1, attacker.getActiveClass()))
					{
						f0 += HateClassGroup1Boost;
					}
				}
				if(HateClassGroup2 > -1)
				{
					if(CategoryManager.isInCategory(HateClassGroup2, attacker.getActiveClass()))
					{
						f0 += HateClassGroup2Boost;
					}
				}
				if(FavorClassGroup1 > -1)
				{
					if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getActiveClass()))
					{
						f0 -= FavorClassGroup1Boost;
					}
				}
				if((f0 + 1) < 0)
				{
					f0 = 0;
				}
				else
				{
					f0 = damage * (f0 + 1);
				}
				_thisActor.addDamageHate(attacker, 0, (long) (f0 * ATTACKED_Weight_Point));
			}
		}
		if(CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			float f0 = DefaultHate;
			if(HateClassGroup1 > -1)
			{
				if(CategoryManager.isInCategory(HateClassGroup1, attacker.getPlayer().getActiveClass()))
				{
					f0 += HateClassGroup1Boost;
				}
			}
			if(HateClassGroup2 > -1)
			{
				if(CategoryManager.isInCategory(HateClassGroup2, attacker.getPlayer().getActiveClass()))
				{
					f0 += HateClassGroup2Boost;
				}
			}
			if(FavorClassGroup1 > -1)
			{
				if(CategoryManager.isInCategory(FavorClassGroup1, attacker.getPlayer().getActiveClass()))
				{
					f0 -= FavorClassGroup1Boost;
				}
			}
			if((f0 + 1) < 0)
			{
				f0 = 0;
			}
			else
			{
				f0 = damage * (f0 + 1);
			}
			_thisActor.addDamageHate(attacker.getPlayer(), 0, (long) (f0 * ATTACKED_Weight_Point));
		}
		if(damage > 0)
		{
			L2Character c0 = _thisActor.getMostHated();
			if(c0 != null && _thisActor.getHate(c0) > 0)
			{
				if(c0.isPlayer() && SkillTable.getAbnormalLevel(c0, Skill_clutch) > 0)
				{
					Functions.showSystemMessageFStr(c0, 1800260, String.valueOf(damage / 2));
					_thisActor.setCurrentHp(_thisActor.getCurrentHp() + damage / 2);
					c0.setCurrentHp(c0.getCurrentHp() - damage / 2);
				}
			}
			_thisActor.addDamageHate(attacker, damage, 0);
		}
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		super.onEvtManipulation(target, (int) (aggro * HATE_SKILL_Weight_Point), skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_RESPAWN_INVIN)
		{
			_thisActor.i_ai0 = 1;
			broadcastScriptEvent(78010065, _thisActor.getStoredId(), 0, 2000);
			addTimer(TM_AREA_RENEW, 1000);
			addTimer(TM_CLUTCH, TIME_clutch * 1000);
		}
		else if(timerId == TM_AREA_RENEW)
		{
			int i1 = tide;
			if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.950000)
			{
				int i0 = 5;
				if(i1 == 0 || i1 == 1)
				{
					changeAreas(i0);
				}
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "#" + room + " area lv" + i0 + "/tide" + i1);
				}
			}
			else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.800000)
			{
				int i0 = 4;
				if(i1 == 0 || i1 == 1)
				{
					changeAreas(i0);
				}
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "#" + room + " area lv" + i0 + "/tide" + i1);
				}
			}
			else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.600000)
			{
				int i0 = 3;
				if(i1 == 0 || i1 == 1)
				{
					changeAreas(i0);
				}
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "#" + room + " area lv" + i0 + "/tide" + i1);
				}
			}
			else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.400000)
			{
				int i0 = 2;
				if(i1 == 0 || i1 == 1)
				{
					changeAreas(i0);
				}
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "#" + room + " area lv" + i0 + "/tide" + i1);
				}
			}
			else if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() >= 0.200000)
			{
				int i0 = 1;
				if(i1 == 0 || i1 == 1)
				{
					changeAreas(i0);
				}
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "#" + room + " area lv" + i0 + "/tide" + i1);
				}
			}
			else
			{
				int i0 = 0;
				if(i1 == 0 || i1 == 1)
				{
					changeAreas(i0);
				}
				if(debug)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "#" + room + " area lv" + i0 + "/tide" + i1);
				}
			}
			addTimer(TM_AREA_RENEW, TIME_AREA_RENEW * 1000);
		}
		else if(timerId == TM_CLUTCH)
		{
			removeAllAttackDesire();
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 2000);
			L2Character c0 = _thisActor.getMostHated();

			if(c0 != null && c0.isPlayer() && _thisActor.getHate(c0) > 0)
			{
				addUseSkillDesire(c0, Skill_clutch, 0, 1, 10000000000L);
			}
			addTimer(TM_CLUTCH, TIME_clutch * 1000);
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		int i0 = 0;
		int i1 = tide;
		if(i1 == 0 || i1 == 1)
		{
			changeAreas(i0);
		}
		if(Rnd.get(100) <= reward_rate_destroyed)
		{
			if(killer != null)
			{
				L2Player c0 = killer.getPlayer();
				if(c0 != null)
					_thisActor.dropItem(c0, reward_siege, Rnd.get(reward_quantity_destroyed) + 1);
			}
		}
		broadcastScriptEvent(989812, 0, 0, 1500);
	}

	private void changeAreas(int i0)
	{
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(0), i0 == 1 ? 1 : 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(1), i0 == 2 ? 1 : 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(2), i0 == 3 ? 1 : 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(3), i0 == 4 ? 1 : 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(4), i0 == 5 ? 1 : 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(5), i0 == 1 ? 1 : 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(6), i0 == 2 ? 1 : 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(7), i0 == 3 ? 1 : 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(8), i0 == 4 ? 1 : 0, _thisActor.getReflection());
		ZoneManager.getInstance().areaSetOnOff(roomAreas.get(9), i0 == 5 ? 1 : 0, _thisActor.getReflection());
	}
}