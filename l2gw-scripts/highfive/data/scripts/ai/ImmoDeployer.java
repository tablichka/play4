package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.DoorTable;
import ru.l2gw.commons.arrays.GCSArray;
import ru.l2gw.util.Util;

import java.lang.ref.WeakReference;

/**
 * @author: rage
 * @date: 15.12.11 19:30
 */
public class ImmoDeployer extends DefaultNpc
{
	public int FieldCycle = 3;
	public int FieldCycle_z2_Quantity = 100000;
	public int FieldCycle_z3_Quantity = 200000;
	public int tide = 0;
	public int zone = 2;
	public int room = 0;
	public String type = "dispatcher";
	public String z3_entrance = "zone3_entrance";
	public int boss_vein_limit = 20;
	public int TACT_AGGRESIVE = 0;
	public int TACT_INTERCEPT = 1;
	public int TACT_DEFENSIVE = 2;
	public int TM_INITIAL_DELAY = 78000;
	public int TIME_INITIAL_DELAY = 30;
	public int TM_ZONE2_ATK_LIMIT = 78001;
	public int TM_ZONE3_ATK_LIMIT = 78004;
	public int TM_ZONE3_DEF_LIMIT = 78005;
	public int TIME_LIMIT = 1500;
	public int TM_ZONE2_DEF1_LIMIT = 78002;
	public int TIME_ZONE2_DEF1_LIMIT = 900;
	public int TM_ZONE2_DEF2_LIMIT = 78003;
	public int TIME_ZONE2_DEF2_LIMIT = 300;
	public int TM_STRATEGY_RENEW = 78006;
	public int TM_TIME_REMAINING1 = 78014;
	public int TM_TIME_REMAINING2 = 78015;
	public int TM_TIME_REMAINING3 = 78016;
	public int TM_TIME_REMAINING4 = 78017;
	public int TIME_TIME_REMAINING = 300;
	public int TM_zone2_fail_laziness = 78018;
	public int TM_ZONE2_LOOP = 78019;
	public int TIME_ZONE2_LOOP = 60;
	public String z2_a_sboss01_maker = "rumwarsha14_1424_a_sb1m1";
	public String z2_a_sboss02_maker = "rumwarsha14_1424_a_sb2m1";
	public String z2_a_sboss03_maker = "rumwarsha14_1424_a_sb3m1";
	public String z2_a_sboss04_maker = "rumwarsha14_1424_a_sb4m1";
	public String z2_a_sb01_mob_maker = "rumwarsha14_1424_a_sb1m2";
	public String z2_a_sb02_mob_maker = "rumwarsha14_1424_a_sb2m2";
	public String z2_a_sb03_mob_maker = "rumwarsha14_1424_a_sb3m2";
	public String z2_a_sb04_mob_maker = "rumwarsha14_1424_a_sb4m2";
	public String z2_a_tumor01_maker = "rumwarsha14_1424_a_t1m1";
	public String z2_a_tumor02_maker = "rumwarsha14_1424_a_t2m1";
	public String z2_a_tumor03_maker = "rumwarsha14_1424_a_t3m1";
	public String z2_a_tumor04_maker = "rumwarsha14_1424_a_t4m1";
	public String z2_a_tm01_mob_maker = "rumwarsha14_1424_a_t1m2";
	public String z2_a_tm02_mob_maker = "rumwarsha14_1424_a_t2m2";
	public String z2_a_tm03_mob_maker = "rumwarsha14_1424_a_t3m2";
	public String z2_a_tm04_mob_maker = "rumwarsha14_1424_a_t4m2";
	public String z2_d_sboss01_maker = "rumwarsha14_1424_d_sb1m1";
	public String z2_d_sboss02_maker = "rumwarsha14_1424_d_sb2m1";
	public String z2_d_sboss03_maker = "rumwarsha14_1424_d_sb3m1";
	public String z2_d_sboss04_maker = "rumwarsha14_1424_d_sb4m1";
	public String z2_d_sb01_mob_maker = "rumwarsha14_1424_d_sb1m2";
	public String z2_d_sb02_mob_maker = "rumwarsha14_1424_d_sb2m2";
	public String z2_d_sb03_mob_maker = "rumwarsha14_1424_d_sb3m2";
	public String z2_d_sb04_mob_maker = "rumwarsha14_1424_d_sb4m2";
	public String z2_d_tumor01_maker = "rumwarsha14_1424_d_t1m1";
	public String z2_d_tumor02_maker = "rumwarsha14_1424_d_t2m1";
	public String z2_d_tumor03_maker = "rumwarsha14_1424_d_t3m1";
	public String z2_d_tumor04_maker = "rumwarsha14_1424_d_t4m1";
	public String z2_d_tm01_mob_maker = "rumwarsha14_1424_d_t1m2";
	public String z2_d_tm02_mob_maker = "rumwarsha14_1424_d_t2m2";
	public String z2_d_tm03_mob_maker = "rumwarsha14_1424_d_t3m2";
	public String z2_d_tm04_mob_maker = "rumwarsha14_1424_d_t4m2";
	public String z2_d_defboss_maker = "rumwarsha14_1424_defbossm1";
	public String z2_a_trap01_maker = "rumwarsha14_1424_a_l0m1";
	public String z2_a_trap11_maker = "rumwarsha14_1424_a_l1m1";
	public String z2_a_trap21_maker = "rumwarsha14_1424_a_l2m1";
	public String z2_a_trap31_maker = "rumwarsha14_1424_a_l3m1";
	public String z2_a_trap41_maker = "rumwarsha14_1424_a_l4m1";
	public String z2_a_trap51_maker = "rumwarsha14_1424_a_l5m1";
	public String z2_a_trap61_maker = "rumwarsha14_1424_a_l6m1";
	public String z2_a_trap71_maker = "rumwarsha14_1424_a_l7m1";
	public String z2_a_trap81_maker = "rumwarsha14_1424_a_l8m1";
	public String z2_a_trap91_maker = "rumwarsha14_1424_a_l9m1";
	public String z2_a_trap101_maker = "rumwarsha14_1424_a_l10m1";
	public String z2_a_trap111_maker = "rumwarsha14_1424_a_l11m1";
	public String z2_a_trap02_maker = "rumwarsha14_1424_a_l0m2";
	public String z2_a_trap12_maker = "rumwarsha14_1424_a_l1m2";
	public String z2_a_trap22_maker = "rumwarsha14_1424_a_l2m2";
	public String z2_a_trap32_maker = "rumwarsha14_1424_a_l3m2";
	public String z2_a_trap42_maker = "rumwarsha14_1424_a_l4m2";
	public String z2_a_trap52_maker = "rumwarsha14_1424_a_l5m2";
	public String z2_a_trap62_maker = "rumwarsha14_1424_a_l6m2";
	public String z2_a_trap72_maker = "rumwarsha14_1424_a_l7m2";
	public String z2_a_trap82_maker = "rumwarsha14_1424_a_l8m2";
	public String z2_a_trap92_maker = "rumwarsha14_1424_a_l9m2";
	public String z2_a_trap102_maker = "rumwarsha14_1424_a_l10m2";
	public String z2_a_trap112_maker = "rumwarsha14_1424_a_l11m2";
	public String z2_d_trap01_maker = "rumwarsha14_1424_d_l0m1";
	public String z2_d_trap11_maker = "rumwarsha14_1424_d_l1m1";
	public String z2_d_trap21_maker = "rumwarsha14_1424_d_l2m1";
	public String z2_d_trap31_maker = "rumwarsha14_1424_d_l3m1";
	public String z2_d_trap41_maker = "rumwarsha14_1424_d_l4m1";
	public String z2_d_trap51_maker = "rumwarsha14_1424_d_l5m1";
	public String z2_d_trap61_maker = "rumwarsha14_1424_d_l6m1";
	public String z2_d_trap71_maker = "rumwarsha14_1424_d_l7m1";
	public String z2_d_trap81_maker = "rumwarsha14_1424_d_l8m1";
	public String z2_d_trap91_maker = "rumwarsha14_1424_d_l9m1";
	public String z2_d_trap101_maker = "rumwarsha14_1424_d_l10m1";
	public String z2_d_trap111_maker = "rumwarsha14_1424_d_l11m1";
	public String z2_d_trap02_maker = "rumwarsha14_1424_d_l0m2";
	public String z2_d_trap12_maker = "rumwarsha14_1424_d_l1m2";
	public String z2_d_trap22_maker = "rumwarsha14_1424_d_l2m2";
	public String z2_d_trap32_maker = "rumwarsha14_1424_d_l3m2";
	public String z2_d_trap42_maker = "rumwarsha14_1424_d_l4m2";
	public String z2_d_trap52_maker = "rumwarsha14_1424_d_l5m2";
	public String z2_d_trap62_maker = "rumwarsha14_1424_d_l6m2";
	public String z2_d_trap72_maker = "rumwarsha14_1424_d_l7m2";
	public String z2_d_trap82_maker = "rumwarsha14_1424_d_l8m2";
	public String z2_d_trap92_maker = "rumwarsha14_1424_d_l9m2";
	public String z2_d_trap102_maker = "rumwarsha14_1424_d_l10m2";
	public String z2_d_trap112_maker = "rumwarsha14_1424_d_l11m2";
	public String ech_atk_seq0_maker = "rumwarsha15_1424_echmusm1";
	public String ech_atk_seq1_maker = "rumwarsha15_1424_echmusm2";
	public String ech_atk_expel_maker = "rumwarsha15_1424_expelm1";
	public String z3_a_tumor01_maker = "rumwarsha15_1424_a_t1m1";
	public String z3_a_tumor02_maker = "rumwarsha15_1424_a_t2m1";
	public String z3_a_tumor03_maker = "rumwarsha15_1424_a_t3m1";
	public String z3_a_tumor04_maker = "rumwarsha15_1424_a_t4m1";
	public String z3_a_tumor05_maker = "rumwarsha15_1424_a_t5m1";
	public String z3_a_tumor06_maker = "rumwarsha15_1424_a_t6m1";
	public String z3_a_tm01_mob_maker = "rumwarsha15_1424_a_t1m2";
	public String z3_a_tm02_mob_maker = "rumwarsha15_1424_a_t2m2";
	public String z3_a_tm03_mob_maker = "rumwarsha15_1424_a_t3m2";
	public String z3_a_tm04_mob_maker = "rumwarsha15_1424_a_t4m2";
	public String z3_a_tm05_mob_maker = "rumwarsha15_1424_a_t5m2";
	public String z3_a_tm06_mob_maker = "rumwarsha15_1424_a_t6m2";
	public String ech_def_seq0_maker = "rumwarsha15_1424_ech_dummy1m1";
	public String ech_def_seq1_maker = "rumwarsha15_1424_ech_dummy2m1";
	public String ech_def_seq2_maker = "rumwarsha15_1424_ech_dummy2m2";
	public String ech_def_seq3_maker = "rumwarsha15_1424_defwagonm1";
	public String ech_def_seq4_maker = "rumwarsha15_1424_veinm1";
	public String z3_d_tumor01_maker = "rumwarsha15_1424_d_t1m1";
	public String z3_d_tumor02_maker = "rumwarsha15_1424_d_t2m1";
	public String z3_d_tumor03_maker = "rumwarsha15_1424_d_t3m1";
	public String z3_d_tumor04_maker = "rumwarsha15_1424_d_t4m1";
	public String z3_d_tumor05_maker = "rumwarsha15_1424_d_t5m1";
	public String z3_d_tumor06_maker = "rumwarsha15_1424_d_t6m1";
	public String z3_d_tm01_mob_maker = "rumwarsha15_1424_d_t1m2";
	public String z3_d_tm02_mob_maker = "rumwarsha15_1424_d_t2m2";
	public String z3_d_tm03_mob_maker = "rumwarsha15_1424_d_t3m2";
	public String z3_d_tm04_mob_maker = "rumwarsha15_1424_d_t4m2";
	public String z3_d_tm05_mob_maker = "rumwarsha15_1424_d_t5m2";
	public String z3_d_tm06_mob_maker = "rumwarsha15_1424_d_t6m2";
	public String z3_a_trap11_maker = "rumwarsha15_1424_a_c1m1";
	public String z3_a_trap21_maker = "rumwarsha15_1424_a_c2m1";
	public String z3_a_trap31_maker = "rumwarsha15_1424_a_c3m1";
	public String z3_a_trap41_maker = "rumwarsha15_1424_a_c4m1";
	public String z3_a_trap51_maker = "rumwarsha15_1424_a_c5m1";
	public String z3_a_trap61_maker = "rumwarsha15_1424_a_c6m1";
	public String z3_a_trap12_maker = "rumwarsha15_1424_a_c1m2";
	public String z3_a_trap22_maker = "rumwarsha15_1424_a_c2m2";
	public String z3_a_trap32_maker = "rumwarsha15_1424_a_c3m2";
	public String z3_a_trap42_maker = "rumwarsha15_1424_a_c4m2";
	public String z3_a_trap52_maker = "rumwarsha15_1424_a_c5m2";
	public String z3_a_trap62_maker = "rumwarsha15_1424_a_c6m2";
	public String z3_d_trap11_maker = "rumwarsha15_1424_d_c1m1";
	public String z3_d_trap21_maker = "rumwarsha15_1424_d_c2m1";
	public String z3_d_trap31_maker = "rumwarsha15_1424_d_c3m1";
	public String z3_d_trap41_maker = "rumwarsha15_1424_d_c4m1";
	public String z3_d_trap51_maker = "rumwarsha15_1424_d_c5m1";
	public String z3_d_trap61_maker = "rumwarsha15_1424_d_c6m1";
	public String z3_d_trap12_maker = "rumwarsha15_1424_d_c1m2";
	public String z3_d_trap22_maker = "rumwarsha15_1424_d_c2m2";
	public String z3_d_trap32_maker = "rumwarsha15_1424_d_c3m2";
	public String z3_d_trap42_maker = "rumwarsha15_1424_d_c4m2";
	public String z3_d_trap52_maker = "rumwarsha15_1424_d_c5m2";
	public String z3_d_trap62_maker = "rumwarsha15_1424_d_c6m2";
	public int echmus_x = -179537;
	public int echmus_y = 209551;
	public int echmus_z = -15504;
	public int tumor_3_1_x = -179779;
	public int tumor_3_1_y = 212540;
	public int tumor_3_1_z = -15520;
	public int tumor_3_2_x = -177028;
	public int tumor_3_2_y = 211135;
	public int tumor_3_2_z = -15520;
	public int tumor_3_3_x = -176355;
	public int tumor_3_3_y = 208043;
	public int tumor_3_3_z = -15520;
	public int tumor_3_4_x = -179284;
	public int tumor_3_4_y = 205990;
	public int tumor_3_4_z = -15520;
	public int tumor_3_5_x = -182268;
	public int tumor_3_5_y = 208218;
	public int tumor_3_5_z = -15520;
	public int tumor_3_6_x = -182069;
	public int tumor_3_6_y = 211140;
	public int tumor_3_6_z = -15520;
	public int TM_scene_atk_opening = 78007;
	public int TM_scene_atk_success = 78008;
	public int TM_scene_atk_failure = 78009;
	public int TIME_scene_atk_opening = 62;
	public int TIME_scene_atk_success = 18;
	public int TIME_scene_atk_failure = 17;
	public int TM_scene_def_opening = 78010;
	public int TM_scene_def_success = 78011;
	public int TM_scene_def_failure = 78012;
	public int TIME_scene_def_opening = 0;
	public int TIME_scene_def_success = 0;
	public int TIME_scene_def_failure = 0;
	public int QID_z2_atk = 696;
	public int QID_z2_def = 697;
	public int QID_z3_def = 698;
	public int TM_look_quester = 78013;
	public int TIME_look_quester = 30;
	private GCSArray<Integer> int_list = new GCSArray<>();
	private WeakReference<L2CommandChannel> cc_ai5;

	public ImmoDeployer(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(!type.equals("expeller"))
		{
			_thisActor.i_ai0 = 0;
			_thisActor.c_ai0 = 0;
			_thisActor.i_ai5 = 0;
			_thisActor.lookNeighbor(450);
			addTimer(TM_look_quester, TIME_look_quester * 1000);
			if(zone == 2 && tide == 0)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800262, "#1800241", "#1800243");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800262, "#1800241", "#1800243");
			}
			else if(zone == 2 && tide == 1)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800262, "#1800241", "#1800244");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800262, "#1800241", "#1800244");
			}
			else if(zone == 3 && tide == 0)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800262, "#1800242", "#1800243");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800262, "#1800242", "#1800243");
			}
			else if(zone == 3 && tide == 1)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800262, "#1800242", "#1800244");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800262, "#1800242", "#1800244");
			}
			addTimer(TM_INITIAL_DELAY, TIME_INITIAL_DELAY * 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010053)
		{
			if((Integer) arg1 == 0)
			{
				switch((Integer) arg2)
				{
					case 211:
						_thisActor.i_ai1 = 0;
						break;
					case 221:
						_thisActor.i_ai2 = 0;
						break;
					case 231:
						_thisActor.i_ai3 = 0;
						break;
					case 241:
						_thisActor.i_ai4 = 0;
						break;
					case 301:
						_thisActor.i_ai1 = 0;
						break;
					case 302:
						_thisActor.i_ai2 = 0;
						break;
					case 303:
						_thisActor.i_ai3 = 0;
						break;
					case 304:
						_thisActor.i_ai4 = 0;
						break;
					case 305:
						_thisActor.i_quest1 = 0;
						break;
					case 306:
						_thisActor.i_quest2 = 0;
						break;
				}
				if(zone == 2 && tide == 0)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800275, "#1800241");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800275, "#1800241");
				}
				else if(zone == 2 && tide == 1)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800301, "#1800241");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800301, "#1800241");
				}
				else if(zone == 3 && tide == 0)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800303, "#1800242");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800303, "#1800242");
				}
				else if(zone == 3 && tide == 1)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800305, "#1800242");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800305, "#1800242");
				}
			}
			else if((Integer) arg1 == 1)
			{
				switch((Integer) arg2)
				{
					case 211:
						_thisActor.i_ai1 = 1;
						break;
					case 221:
						_thisActor.i_ai2 = 1;
						break;
					case 231:
						_thisActor.i_ai3 = 1;
						break;
					case 241:
						_thisActor.i_ai4 = 1;
						break;
					case 301:
						_thisActor.i_ai1 = 1;
						break;
					case 302:
						_thisActor.i_ai2 = 1;
						break;
					case 303:
						_thisActor.i_ai3 = 1;
						break;
					case 304:
						_thisActor.i_ai4 = 1;
						break;
					case 305:
						_thisActor.i_quest1 = 1;
						break;
					case 306:
						_thisActor.i_quest2 = 1;
						break;
				}
				if(zone == 2 && tide == 0 && (((_thisActor.i_ai1 + _thisActor.i_ai2) + _thisActor.i_ai3) + _thisActor.i_ai4) < 4)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800274, "#1800241");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800274, "#1800241");
				}
				else if(zone == 2 && tide == 0)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800299, "#1800241");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800299, "#1800241");
				}
				else if(zone == 2 && tide == 1)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800300, "#1800241");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800300, "#1800241");
				}
				else if(zone == 3 && tide == 0)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800302, "#1800242");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800302, "#1800242");
				}
				else if(zone == 3 && tide == 1)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800304, "#1800242");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800304, "#1800242");
				}
			}
		}
		else if(eventId == 78010054)
		{
			if((Integer) arg1 == 0)
			{
				switch((Integer) arg2)
				{
					case 212:
						_thisActor.i_quest1 = 0;
						break;
					case 222:
						_thisActor.i_quest2 = 0;
						break;
					case 232:
						_thisActor.i_quest3 = 0;
						break;
					case 242:
						_thisActor.i_quest4 = 0;
						break;
				}
			}
			else if((Integer) arg1 == 1)
			{
				switch((Integer) arg2)
				{
					case 212:
						_thisActor.i_quest1 = 1;
						break;
					case 222:
						_thisActor.i_quest2 = 1;
						break;
					case 232:
						_thisActor.i_quest3 = 1;
						break;
					case 242:
						_thisActor.i_quest4 = 1;
						break;
				}
			}
		}
		if(eventId == 78010053 || eventId == 78010054)
		{
			addTimer(TM_STRATEGY_RENEW, 1000);
		}
		if(eventId == 78010053 && (Integer) arg1 == 1 && zone == 2 && tide == 1)
		{
			DefaultMaker maker0 = null;
			switch((Integer) arg2)
			{
				case 211:
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb01_mob_maker);
					break;
				case 221:
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb02_mob_maker);
					break;
				case 231:
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb03_mob_maker);
					break;
				case 241:
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb04_mob_maker);
					break;
			}
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010051, 0, 0);
			}
		}
		if(eventId == 78010061 && (zone == 2 || zone == 3))
		{
			if(zone == 2 || zone == 3)
			{
				if(_thisActor.i_ai1 == 1 && (Integer) arg1 != 211 && (Integer) arg1 != 301)
				{
					if(zone == 2)
					{
						int_list.add(211);
					}
					else
					{
						int_list.add(301);
					}
				}
				if(_thisActor.i_ai2 == 1 && (Integer) arg1 != 221 && (Integer) arg1 != 302)
				{
					if(zone == 2)
					{
						int_list.add(221);
					}
					else
					{
						int_list.add(302);
					}
				}
				if(_thisActor.i_ai3 == 1 && (Integer) arg1 != 231 && (Integer) arg1 != 303)
				{
					if(zone == 2)
					{
						int_list.add(231);
					}
					else
					{
						int_list.add(303);
					}
				}
				if(_thisActor.i_ai4 == 1 && (Integer) arg1 != 241 && (Integer) arg1 != 304)
				{
					if(zone == 2)
					{
						int_list.add(241);
					}
					else
					{
						int_list.add(304);
					}
				}
				if(_thisActor.i_quest1 == 1 && zone == 3 && (Integer) arg1 != 305)
				{
					int_list.add(305);
				}
				if(_thisActor.i_quest2 == 1 && zone == 3 && (Integer) arg1 != 306)
				{
					int_list.add(306);
				}
				int i0;
				if(int_list.size() > 0)
				{
					i0 = int_list.get(Rnd.get(int_list.size()));
				}
				else
				{
					i0 = 9999;
				}
				if(tide == 0)
				{
					if((Integer) arg1 == 211)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
					else if((Integer) arg1 == 221)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
					else if((Integer) arg1 == 231)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
					else if((Integer) arg1 == 241)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
					else if((Integer) arg1 == 301)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
					else if((Integer) arg1 == 302)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
					else if((Integer) arg1 == 303)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
					else if((Integer) arg1 == 304)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
					else if((Integer) arg1 == 305)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
					else if((Integer) arg1 == 306)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010062, arg1, i0);
						}
					}
				}
				else if((Integer) arg1 == 211)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
				else if((Integer) arg1 == 221)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
				else if((Integer) arg1 == 231)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
				else if((Integer) arg1 == 241)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
				else if((Integer) arg1 == 301)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
				else if((Integer) arg1 == 302)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
				else if((Integer) arg1 == 303)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
				else if((Integer) arg1 == 304)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
				else if((Integer) arg1 == 305)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
				else if((Integer) arg1 == 306)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010062, arg1, i0);
					}
				}
			}
		}
		else if((eventId == 78010069 || eventId == 78010066) && type.equals("expeller"))
		{
			if((Integer) arg1 == 301)
			{
				_thisActor.teleToLocation(tumor_3_1_x, tumor_3_1_y, tumor_3_1_z);
				//myself.InstantTeleportInMyTerritory(tumor_3_1_x, tumor_3_1_y, tumor_3_1_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_1_x, tumor_3_1_y, tumor_3_1_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_1_x, tumor_3_1_y, tumor_3_1_z, 0);
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010063, arg1, arg2);
				}
			}
			else if((Integer) arg1 == 302)
			{
				_thisActor.teleToLocation(tumor_3_2_x, tumor_3_2_y, tumor_3_2_z);
				//myself.InstantTeleportInMyTerritory(tumor_3_2_x, tumor_3_2_y, tumor_3_2_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_2_x, tumor_3_2_y, tumor_3_2_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_2_x, tumor_3_2_y, tumor_3_2_z, 0);
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010063, arg1, arg2);
				}
			}
			else if((Integer) arg1 == 303)
			{
				_thisActor.teleToLocation(tumor_3_3_x, tumor_3_3_y, tumor_3_3_z);
				//myself.InstantTeleportInMyTerritory(tumor_3_3_x, tumor_3_3_y, tumor_3_3_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_3_x, tumor_3_3_y, tumor_3_3_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_3_x, tumor_3_3_y, tumor_3_3_z, 0);
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010063, arg1, arg2);
				}
			}
			else if((Integer) arg1 == 304)
			{
				_thisActor.teleToLocation(tumor_3_4_x, tumor_3_4_y, tumor_3_4_z);
				//myself.InstantTeleportInMyTerritory(tumor_3_4_x, tumor_3_4_y, tumor_3_4_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_4_x, tumor_3_4_y, tumor_3_4_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_4_x, tumor_3_4_y, tumor_3_4_z, 0);
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010063, arg1, arg2);
				}
			}
			else if((Integer) arg1 == 305)
			{
				_thisActor.teleToLocation(tumor_3_5_x, tumor_3_5_y, tumor_3_5_z);
				//myself.InstantTeleportInMyTerritory(tumor_3_5_x, tumor_3_5_y, tumor_3_5_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_5_x, tumor_3_5_y, tumor_3_5_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_5_x, tumor_3_5_y, tumor_3_5_z, 0);
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010063, (Integer) arg1, (Integer) arg2);
				}
			}
			else if((Integer) arg1 == 306)
			{
				_thisActor.teleToLocation(tumor_3_6_x, tumor_3_6_y, tumor_3_6_z);
				//myself.InstantTeleportInMyTerritory(tumor_3_6_x, tumor_3_6_y, tumor_3_6_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_6_x, tumor_3_6_y, tumor_3_6_z, 0);
				//myself.InstantTeleportInMyTerritory(tumor_3_6_x, tumor_3_6_y, tumor_3_6_z, 0);
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010063, arg1, arg2);
				}
			}
			else
			{
				switch(Rnd.get(6))
				{
					case 0:
						_thisActor.teleToLocation(tumor_3_1_x, tumor_3_1_y, tumor_3_1_z);
						//myself.InstantTeleportInMyTerritory(tumor_3_1_x, tumor_3_1_y, tumor_3_1_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_1_x, tumor_3_1_y, tumor_3_1_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_1_x, tumor_3_1_y, tumor_3_1_z, 0);
						break;
					case 1:
						_thisActor.teleToLocation(tumor_3_2_x, tumor_3_2_y, tumor_3_2_z);
						//myself.InstantTeleportInMyTerritory(tumor_3_2_x, tumor_3_2_y, tumor_3_2_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_2_x, tumor_3_2_y, tumor_3_2_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_2_x, tumor_3_2_y, tumor_3_2_z, 0);
						break;
					case 2:
						_thisActor.teleToLocation(tumor_3_3_x, tumor_3_3_y, tumor_3_3_z);
						//myself.InstantTeleportInMyTerritory(tumor_3_3_x, tumor_3_3_y, tumor_3_3_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_3_x, tumor_3_3_y, tumor_3_3_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_3_x, tumor_3_3_y, tumor_3_3_z, 0);
						break;
					case 3:
						_thisActor.teleToLocation(tumor_3_4_x, tumor_3_4_y, tumor_3_4_z);
						//myself.InstantTeleportInMyTerritory(tumor_3_4_x, tumor_3_4_y, tumor_3_3_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_4_x, tumor_3_4_y, tumor_3_3_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_4_x, tumor_3_4_y, tumor_3_3_z, 0);
						break;
					case 4:
						_thisActor.teleToLocation(tumor_3_5_x, tumor_3_5_y, tumor_3_5_z);
						//myself.InstantTeleportInMyTerritory(tumor_3_5_x, tumor_3_5_y, tumor_3_3_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_5_x, tumor_3_5_y, tumor_3_3_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_5_x, tumor_3_5_y, tumor_3_3_z, 0);
						break;
					case 5:
						_thisActor.teleToLocation(tumor_3_6_x, tumor_3_6_y, tumor_3_6_z);
						//myself.InstantTeleportInMyTerritory(tumor_3_6_x, tumor_3_6_y, tumor_3_6_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_6_x, tumor_3_6_y, tumor_3_6_z, 0);
						//myself.InstantTeleportInMyTerritory(tumor_3_6_x, tumor_3_6_y, tumor_3_6_z, 0);
						break;
				}
			}
		}
		if(eventId == 78010073 && zone == 3 && tide == 1)
		{
			_thisActor.i_quest4++;
			if(_thisActor.i_quest4 >= boss_vein_limit && _thisActor.i_quest0 < 99)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800246, "#1800242", "#1800244");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800246, "#1800242", "#1800244");
				_thisActor.i_quest0 = 99;
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm05_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm06_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq2_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq3_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm05_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm06_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}

				Instance inst = _thisActor.getInstanceZone();
				if(inst != null)
					inst.rescheduleEndTask(15 * 60);
			}
			else
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800232, String.valueOf(boss_vein_limit - _thisActor.i_quest4));
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800232, String.valueOf(boss_vein_limit - _thisActor.i_quest4));
				if(Rnd.get(5) == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq0_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010070, 0, 0);
					}
				}
				if((_thisActor.i_quest4 == 5 && _thisActor.i_quest0 == 0) || (_thisActor.i_quest4 == 15 && _thisActor.i_quest0 == 1))
				{
					if(_thisActor.i_quest4 == 5)
					{
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800252, "");
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800252, "");
						_thisActor.i_quest0 = 1;
					}
					else
					{
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800253, "");
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800253, "");
						_thisActor.i_quest0 = 2;
					}

					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq2_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010070, _thisActor.i_quest4, 0);
					}
				}
			}
		}
		if(zone == 3 && tide == 0 && eventId == 78010053)
		{
			if((Integer) arg1 == 1)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010068, 0, 0);
				}
			}
			else if((Integer) arg1 == 0)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010070, 0, 0);
				}
			}
		}
		else if(zone == 3 && tide == 1 && eventId == 78010053)
		{
			if((Integer) arg1 == 1)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq3_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010068, 0, 0);
				}
			}
			else if((Integer) arg1 == 0)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq3_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010070, 0, 0);
				}
			}
		}
		if(eventId == 78010053 && zone == 2 && tide == 0)
		{
			if((Integer) arg1 == 1 && _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 == 4 && _thisActor.i_quest1 == 0)
			{
				_thisActor.i_quest1 = 1;
				if((Integer) arg2 == 211)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010068, 0, 0);
					}
				}
				else if((Integer) arg2 == 221)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010068, 0, 0);
					}
				}
				else if((Integer) arg2 == 231)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010068, 0, 0);
					}
				}
				else if((Integer) arg2 == 241)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010068, 0, 0);
					}
				}
			}
			else if((Integer) arg1 == 0 && _thisActor.i_quest1 == 1)
			{
				_thisActor.i_quest1 = 0;
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010070, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010070, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010070, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010070, 0, 0);
				}
			}
		}
		if(eventId == 78010054 && zone == 2 && tide == 1 && (((_thisActor.i_quest1 + _thisActor.i_quest2) + _thisActor.i_quest3) + _thisActor.i_quest4) < 4 && _thisActor.i_quest0 < 99)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800246, "#1800241", "#1800244");
			Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800246, "#1800241", "#1800244");
			_thisActor.i_quest0 = 99;
			if(tide == 0)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap71_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap81_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap91_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap101_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap111_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap72_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap82_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap92_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap102_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap112_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}
			else
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap71_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap81_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap91_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap101_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap111_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap72_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap82_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap92_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap102_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap112_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.rescheduleEndTask(15 * 60);
		}
		if(eventId == 78010071 && zone == 2 && tide == 0 && _thisActor.i_quest0 < 99)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800245, "#1800241", "#1800243");
			Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800245, "#1800241", "#1800243");
			_thisActor.i_quest0 = 99;

			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.successEnd();

			if(FieldCycle_z2_Quantity > 0)
			{
				int i0 = FieldCycleManager.getStep(FieldCycle);
				int i1 = FieldCycle_z2_Quantity;
				int i2 = (0 - FieldCycle_z2_Quantity);
				if(i0 >= 1 && i0 <= 2)
				{
					FieldCycleManager.addPoint("npc_" + 1, FieldCycle, i1, _thisActor);
				}
				else if(i0 >= 3 && i0 <= 5)
				{
					FieldCycleManager.addPoint("npc_" + 1, FieldCycle, i2, _thisActor);
				}
			}

			L2CommandChannel cc = cc_ai5 != null ? cc_ai5.get() : null;
			if(cc != null)
			{
				if(cc.getParties().size() > 0)
				{
					for(L2Party party0 : cc.getParties())
					{
						if(party0 != null)
						{
							for(L2Player c0 : party0.getPartyMembers())
							{
								if(c0 != null)
								{
									if(c0.getReflection() == _thisActor.getReflection())
									{
										QuestState st = c0.getQuestState(696);
										if(st != null)
											st.setMemoState(4);
									}
								}
							}
						}
					}
				}
			}

			if(tide == 0)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap71_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap81_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap91_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap101_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap111_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap72_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap82_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap92_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap102_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap112_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}
			else
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap71_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap81_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap91_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap101_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap111_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap72_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap82_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap92_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap102_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap112_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}

			inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.rescheduleEndTask(15 * 60);
		}
		else if(eventId == 78010071 && (Integer) arg1 == 1 && zone == 3 && tide == 0 && _thisActor.i_quest0 < 99)
		{
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.markRestriction();

			if(FieldCycle_z3_Quantity > 0)
			{
				int i0 = FieldCycleManager.getStep(FieldCycle);
				int i1 = FieldCycle_z3_Quantity;
				int i2 = -FieldCycle_z3_Quantity;
				if(i0 >= 1 && i0 <= 2)
				{
					FieldCycleManager.addPoint("npc_" + 1, FieldCycle, i1, _thisActor);
				}
				else if(i0 >= 3 && i0 <= 5)
				{
					FieldCycleManager.addPoint("npc_" + 1, FieldCycle, i2, _thisActor);
				}
			}

			_thisActor.i_quest0 = 99;
			if(tide == 0)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm05_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm06_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}
			else
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq2_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq3_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm05_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm06_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}

			Functions.startScenePlayerAround(_thisActor, 3, 8000, 1000);
			addTimer(TM_scene_atk_success, TIME_scene_atk_success * 1000);
		}
		else if(eventId == 78010071 && (Integer) arg1 == 0 && zone == 3 && tide == 0 && _thisActor.i_quest0 < 99)
		{
			_thisActor.i_quest0 = 99;
			if(tide == 0)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm05_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm06_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}
			else
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq2_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq3_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm05_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm06_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}

			Functions.startScenePlayerAround(_thisActor, 4, 8000, 1000);
			addTimer(TM_scene_atk_failure, TIME_scene_atk_failure * 1000);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		String s0 = "";
		String s1 = "";
		if(timerId == TM_INITIAL_DELAY)
		{
			if(zone == 2)
			{
				if(tide == 0)
				{
					addTimer(TM_ZONE2_ATK_LIMIT, TIME_LIMIT * 1000);
					_thisActor.i_ai1 = 0;
					_thisActor.i_ai2 = 0;
					_thisActor.i_ai3 = 0;
					_thisActor.i_ai4 = 0;
					_thisActor.i_quest1 = 0;
					_thisActor.i_quest2 = 0;
					_thisActor.i_quest3 = 0;
					_thisActor.i_quest4 = 0;
					_thisActor.i_quest0 = 0;
					if(tide == 0)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap11_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap21_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap31_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap41_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap51_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap61_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap71_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap81_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap91_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap101_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap111_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						addTimer(TM_STRATEGY_RENEW, 60000);
					}
					else
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap11_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap21_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap31_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap41_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap51_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap61_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap71_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap81_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap91_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap101_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap111_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						addTimer(TM_STRATEGY_RENEW, 2 * 60 * 1000);
					}
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800263, "#1800241", "#1800243");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800263, "#1800241", "#1800243");
				}
				else if(tide == 1)
				{
					addTimer(TM_ZONE2_DEF1_LIMIT, TIME_ZONE2_DEF1_LIMIT * 1000);
					_thisActor.i_ai1 = 1;
					_thisActor.i_ai2 = 1;
					_thisActor.i_ai3 = 1;
					_thisActor.i_ai4 = 1;
					_thisActor.i_quest1 = 1;
					_thisActor.i_quest2 = 1;
					_thisActor.i_quest3 = 1;
					_thisActor.i_quest4 = 1;
					_thisActor.i_quest0 = 0;
					if(tide == 0)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap11_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap21_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap31_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap41_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap51_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap61_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap71_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap81_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap91_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap101_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap111_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						addTimer(TM_STRATEGY_RENEW, 60000);
					}
					else
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap11_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap21_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap31_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap41_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap51_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap61_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap71_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap81_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap91_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap101_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap111_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						addTimer(TM_STRATEGY_RENEW, 2 * 60000);
					}
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800263, "#1800241", "#1800244");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800263, "#1800241", "#1800244");
				}
				addTimer(TM_TIME_REMAINING1, TIME_TIME_REMAINING * 1000);
				addTimer(TM_ZONE2_LOOP, 1000);
			}
			else if(zone == 3)
			{
				DoorTable.getInstance().doorOpenClose(z3_entrance, 0, _thisActor.getReflection());

				if(tide == 0)
				{
					_thisActor.i_ai1 = 0;
					_thisActor.i_ai2 = 0;
					_thisActor.i_ai3 = 0;
					_thisActor.i_ai4 = 0;
					_thisActor.i_quest1 = 0;
					_thisActor.i_quest2 = 0;
					_thisActor.i_quest3 = 0;
					_thisActor.i_quest4 = 0;
					_thisActor.i_quest0 = 0;
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010067, 0, 0);
					}

					Functions.startScenePlayerAround(_thisActor, 2, 8000, 1000);
					addTimer(TM_scene_atk_opening, TIME_scene_atk_opening * 1000);
				}
				else if(tide == 1)
				{
					addTimer(TM_ZONE3_DEF_LIMIT, TIME_LIMIT * 1000);
					_thisActor.i_ai1 = 1;
					_thisActor.i_ai2 = 1;
					_thisActor.i_ai3 = 1;
					_thisActor.i_ai4 = 1;
					_thisActor.i_quest1 = 1;
					_thisActor.i_quest2 = 1;
					_thisActor.i_quest3 = 0;
					_thisActor.i_quest4 = 0;
					_thisActor.i_quest0 = 0;
					int i0 = (Rnd.get(3) + 2);
					if(tide == 0)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 1, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm05_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm06_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap11_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap21_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap31_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap41_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap51_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap61_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						addTimer(TM_STRATEGY_RENEW, 60000);
					}
					else
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq3_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm01_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm02_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm03_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm04_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm05_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm06_mob_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, i0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap11_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap21_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap31_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap41_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap51_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap61_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010067, 0, 0);
						}
						addTimer(TM_STRATEGY_RENEW, 2 * 60 * 1000);
					}
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800263, "#1800242", "#1800244");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800263, "#1800242", "#1800244");
				}
				addTimer(TM_TIME_REMAINING1, TIME_TIME_REMAINING * 1000);
			}
		}
		else if(timerId == TM_scene_atk_opening)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800263, "#1800242", "#1800243");
			Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800263, "#1800242", "#1800243");
			int i0 = Rnd.get(3) + 2;
			if(tide == 0)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 1, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm05_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm06_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				addTimer(TM_STRATEGY_RENEW, 60000);
			}
			else
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq3_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm05_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm06_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, i0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, 0, 0);
				}
				addTimer(TM_STRATEGY_RENEW, 2 * 60 * 1000);
			}
			addTimer(TM_ZONE3_ATK_LIMIT, TIME_LIMIT * 1000);
		}
		else if(timerId == TM_STRATEGY_RENEW)
		{
			if(zone == 2)
			{
				int i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2 + _thisActor.i_quest3 + _thisActor.i_quest4;
				if(i0 > 0 && i0 <= 3)
				{
					_thisActor.i_ai0 = TACT_AGGRESIVE;
				}
				else if(i0 >= 4 && i0 <= 5)
				{
					_thisActor.i_ai0 = TACT_INTERCEPT;
				}
				else if(i0 >= 6 && i0 <= 8)
				{
					_thisActor.i_ai0 = TACT_DEFENSIVE;
				}
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai1 + _thisActor.i_quest1, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai1, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai2 + _thisActor.i_quest2, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai2, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai3 + _thisActor.i_quest3, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai3, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai4 + _thisActor.i_quest4, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai4, _thisActor.i_ai0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai1 + _thisActor.i_quest1, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai1, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai2 + _thisActor.i_quest2, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai2, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai3 + _thisActor.i_quest3, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai3, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai4 + _thisActor.i_quest4, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai4, _thisActor.i_ai0);
					}
				}
			}
			else if(zone == 3)
			{
				int i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2;
				if(i0 > 0 && i0 <= 2)
				{
					_thisActor.i_ai0 = TACT_AGGRESIVE;
				}
				else if(i0 >= 3 && i0 <= 4)
				{
					_thisActor.i_ai0 = TACT_INTERCEPT;
				}
				else if(i0 >= 5 && i0 <= 6)
				{
					_thisActor.i_ai0 = TACT_DEFENSIVE;
				}
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai1, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai2, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai3, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai4, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm05_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_quest1, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm06_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_quest2, _thisActor.i_ai0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai1, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai2, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai3, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_ai4, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm05_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_quest1, _thisActor.i_ai0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm06_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010065, _thisActor.i_quest2, _thisActor.i_ai0);
					}
				}
			}
		}
		else if(timerId == TM_ZONE2_ATK_LIMIT)
		{
			if(tide == 0 && zone == 2 && _thisActor.i_quest0 < 99)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800246, "#1800241", "#1800243");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800246, "#1800241", "#1800243");
				_thisActor.i_quest0 = 99;
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap71_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap81_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap91_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap101_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap111_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap72_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap82_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap92_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap102_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap112_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap71_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap81_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap91_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap101_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap111_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap72_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap82_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap92_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap102_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap112_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}
				Instance inst = _thisActor.getInstanceZone();
				if(inst != null)
					inst.rescheduleEndTask(15 * 60);
			}
		}
		else if(timerId == TM_ZONE2_DEF1_LIMIT)
		{
			if(zone == 2 && tide == 1 && _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 < 4)
			{
				if(debug)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800251, "");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800251, "");
				}
				if(_thisActor.i_ai1 == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010068, 0, 0);
					}
				}
				if(_thisActor.i_ai2 == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010068, 0, 0);
					}
				}
				if(_thisActor.i_ai3 == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010068, 0, 0);
					}
				}
				if(_thisActor.i_ai4 == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010068, 0, 0);
					}
				}
			}
			addTimer(TM_ZONE2_DEF2_LIMIT, TIME_ZONE2_DEF2_LIMIT * 1000);
		}
		else if(timerId == TM_ZONE2_DEF2_LIMIT)
		{
			if(zone == 2 && tide == 1 && _thisActor.i_quest0 < 99)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800245, "#1800241", "#1800244");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800245, "#1800241", "#1800244");
				_thisActor.i_quest0 = 99;
				if(FieldCycle_z2_Quantity > 0)
				{
					int i0 = FieldCycleManager.getStep(FieldCycle);
					int i1 = FieldCycle_z2_Quantity;
					int i2 = (0 - FieldCycle_z2_Quantity);
					if(i0 >= 1 && i0 <= 2)
					{
						FieldCycleManager.addPoint("npc_" + 1, FieldCycle, i1, _thisActor);
					}
					else if(i0 >= 3 && i0 <= 5)
					{
						FieldCycleManager.addPoint("npc_" + 1, FieldCycle, i2, _thisActor);
					}
				}

				if(_thisActor.i_ai5 != 0)
				{
					L2CommandChannel cc = cc_ai5 != null ? cc_ai5.get() : null;
					if(cc != null)
					{
						for(L2Player c0 : cc.getMembers())
						{
							if(c0 != null)
							{
								if(c0.getReflection() == _thisActor.getReflection())
								{
									QuestState st = c0.getQuestState(697);
									if(st != null)
										st.setMemoState(4);
								}
							}
						}
					}
				}

				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap71_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap81_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap91_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap101_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap111_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap72_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap82_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap92_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap102_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap112_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap71_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap81_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap91_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap101_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap111_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap72_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap82_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap92_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap102_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap112_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}
				Instance inst = _thisActor.getInstanceZone();
				if(inst != null)
				{
					inst.markRestriction();
					inst.rescheduleEndTask(15 * 60);
				}
			}
		}
		else if(timerId == TM_ZONE3_ATK_LIMIT)
		{
			if(tide == 0 && zone == 3 && _thisActor.i_quest0 < 99)
			{
				_thisActor.i_quest0 = 99;
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm05_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm06_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq2_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq3_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm05_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm06_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}

				Functions.startScenePlayerAround(_thisActor, 4, 8000, 1000);
				addTimer(TM_scene_atk_failure, TIME_scene_atk_failure * 1000);
			}
		}
		else if(timerId == TM_ZONE3_DEF_LIMIT)
		{
			if(tide == 1 && zone == 3 && _thisActor.i_quest4 < boss_vein_limit && _thisActor.i_quest0 < 99)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800245, "#1800242", "#1800244");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800245, "#1800242", "#1800244");
				_thisActor.i_quest0 = 99;
				if(FieldCycle_z3_Quantity > 0)
				{
					int i0 = FieldCycleManager.getStep(FieldCycle);
					int i1 = FieldCycle_z3_Quantity;
					int i2 = (0 - FieldCycle_z3_Quantity);
					if(i0 >= 1 && i0 <= 2)
					{
						FieldCycleManager.addPoint("npc_" + 1, FieldCycle, i1, _thisActor);
					}
					else if(i0 >= 3 && i0 <= 5)
					{
						FieldCycleManager.addPoint("npc_" + 1, FieldCycle, i2, _thisActor);
					}
				}
				if(_thisActor.i_ai5 != 0)
				{
					L2CommandChannel cc = cc_ai5 != null ? cc_ai5.get() : null;
					if(cc != null)
					{
						for(L2Player c0 : cc.getMembers())
						{
							if(c0 != null)
							{
								if(c0.getReflection() == _thisActor.getReflection())
								{
									QuestState st = c0.getQuestState(698);
									if(st != null)
										st.setMemoState(4);
								}
							}
						}
					}
				}

				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm05_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tm06_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq2_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_def_seq3_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm01_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm02_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm03_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm04_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm05_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tm06_mob_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap11_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap21_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap31_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap41_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap51_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap61_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap12_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap22_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap32_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap42_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap52_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_trap62_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
				}
				Instance inst = _thisActor.getInstanceZone();
				if(inst != null)
				{
					inst.markRestriction();
					inst.rescheduleEndTask(15 * 60);
				}
			}
		}
		else if(timerId == TM_scene_atk_success)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 3, 1, 1, 5000, 0, 1800245, "#1800242", "#1800243");
			Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800245, "#1800242", "#1800243");
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
			{
				inst.rescheduleEndTask(15 * 60);
			}
		}
		else if(timerId == TM_scene_atk_failure)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800246, "#1800242", "#1800243");
			Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800246, "#1800242", "#1800243");
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
			{
				inst.rescheduleEndTask(15 * 60);
			}
		}
		else if(timerId == TM_ZONE2_LOOP)
		{
			if(_thisActor.i_quest0 >= 0 && _thisActor.i_quest0 <= 12)
			{
				if(_thisActor.i_quest0 == 0 || _thisActor.i_quest0 == 12)
				{
					_thisActor.i_quest0 = 1;
				}
				else
				{
					_thisActor.i_quest0 = (_thisActor.i_quest0 + 1);
				}
				s0 = "14_24_undying_z2";
				if(tide == 0)
				{
					s0 = s0 + "a";
				}
				else
				{
					s0 = s0 + "d";
				}
				s0 = s0 + "_loop";
				if(_thisActor.i_quest0 == 1 || _thisActor.i_quest0 == 5 || _thisActor.i_quest0 == 9)
				{
					s1 = s0 + "0" + _thisActor.i_quest0 + "_01";
				}
				else if(_thisActor.i_quest0 == 2 || _thisActor.i_quest0 == 6 || _thisActor.i_quest0 == 10)
				{
					if(_thisActor.i_quest0 < 10)
					{
						s1 = s0 + "0" + _thisActor.i_quest0 + "_02";
					}
					else
					{
						s1 = s0 + _thisActor.i_quest0 + _thisActor.i_quest0 + "_02";
					}
				}
				else if(_thisActor.i_quest0 == 3 || _thisActor.i_quest0 == 7 || _thisActor.i_quest0 == 11)
				{
					if(_thisActor.i_quest0 < 10)
					{
						s1 = s0 + "0" + _thisActor.i_quest0 + "_03";
					}
					else
					{
						s1 = s0 + _thisActor.i_quest0 + _thisActor.i_quest0 + "_03";
					}
				}
				else if(_thisActor.i_quest0 == 4 || _thisActor.i_quest0 == 8 || _thisActor.i_quest0 == 12)
				{
					if(_thisActor.i_quest0 < 10)
					{
						s1 = s0 + "0" + _thisActor.i_quest0 + "_04";
					}
					else
					{
						s1 = s0 + _thisActor.i_quest0 + _thisActor.i_quest0 + "_04";
					}
				}
				addTimer(TM_ZONE2_LOOP, TIME_ZONE2_LOOP * 1000);
			}
		}
		else if(timerId == TM_TIME_REMAINING1)
		{
			int i0 = 0;
			if(zone == 2 && tide == 0 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4;
				if(i0 == 0)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800307, "");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800307, "");
				}
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800241", "#1800243", "20");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800241", "#1800243", "20");
			}
			else if(zone == 2 && tide == 1 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800241", "#1800244", "20");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800241", "#1800244", "20");
			}
			else if(zone == 3 && tide == 0 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800242", "#1800243", "20");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800242", "#1800243", "20");
			}
			else if(zone == 3 && tide == 1 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800242", "#1800244", "20");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800242", "#1800244", "20");
			}
			if(zone == 2)
			{
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
			}
			else if(zone == 3)
			{
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
			}
			addTimer(TM_TIME_REMAINING2, TIME_TIME_REMAINING * 1000);
		}
		else if(timerId == TM_TIME_REMAINING2 && _thisActor.i_quest0 < 99)
		{
			int i0 = 0;
			if(zone == 2 && tide == 0 && _thisActor.i_quest0 < 99)
			{
				if(_thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 > 0)
				{
					if(_thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 == 1)
					{
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800307, "");
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800307, "");
					}
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800241", "#1800243", "15");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800241", "#1800243", "15");
				}
				else
				{
					addTimer(TM_zone2_fail_laziness, 1000);
					return;
				}
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4;
			}
			else if(zone == 2 && tide == 1 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800241", "#1800244", "15");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800241", "#1800244", "15");
			}
			else if(zone == 3 && tide == 0 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800242", "#1800243", "15");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800242", "#1800243", "15");
			}
			else if(zone == 3 && tide == 1 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800242", "#1800244", "15");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800242", "#1800244", "15");
			}
			if(zone == 2)
			{
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
			}
			else if(zone == 3)
			{
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
			}
			addTimer(TM_TIME_REMAINING3, TIME_TIME_REMAINING * 1000);
		}
		else if(timerId == TM_TIME_REMAINING3 && _thisActor.i_quest0 < 99)
		{
			int i0 = 0;
			if(zone == 2 && tide == 0 && _thisActor.i_quest0 < 99)
			{
				if(_thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 > 1)
				{
					if(_thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 == 2)
					{
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800307, "");
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800307, "");
					}
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800241", "#1800243", "10");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800241", "#1800243", "10");
				}
				else
				{
					addTimer(TM_zone2_fail_laziness, 1000);
					return;
				}
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4;
			}
			else if(zone == 2 && tide == 1 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800241", "#1800244", "10");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800241", "#1800244", "10");
			}
			else if(zone == 3 && tide == 0 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800242", "#1800243", "10");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800242", "#1800243", "10");
			}
			else if(zone == 3 && tide == 1 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1801197, "#1800242", "#1800244", "10");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800242", "#1800244", "10");
			}
			if(zone == 2)
			{
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
			}
			else if(zone == 3)
			{
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
			}
			addTimer(TM_TIME_REMAINING4, TIME_TIME_REMAINING * 1000);
		}
		else if(timerId == TM_TIME_REMAINING4 && _thisActor.i_quest0 < 99)
		{
			int i0 = 0;
			if(zone == 2 && tide == 0 && _thisActor.i_quest0 < 99)
			{
				if(_thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 > 2)
				{
					if(_thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 < 4)
					{
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800307, "");
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800307, "");
					}
					Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1801197, "#1800241", "#1800243", "5");
					Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800241", "#1800243", "5");
				}
				else
				{
					addTimer(TM_zone2_fail_laziness, 1000);
					return;
				}
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4;
			}
			else if(zone == 2 && tide == 1 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1801197, "#1800241", "#1800244", "5");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800241", "#1800244", "5");
			}
			else if(zone == 3 && tide == 0 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1801197, "#1800242", "#1800243", "5");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800242", "#1800243", "5");
			}
			else if(zone == 3 && tide == 1 && _thisActor.i_quest0 < 99)
			{
				i0 = _thisActor.i_ai1 + _thisActor.i_ai2 + _thisActor.i_ai3 + _thisActor.i_ai4 + _thisActor.i_quest1 + _thisActor.i_quest2;
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1801197, "#1800242", "#1800244", "5");
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1801197, "#1800242", "#1800244", "5");
			}
			if(zone == 2)
			{
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
			}
			else if(zone == 3)
			{
				if(tide == 0)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_a_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
				else
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor01_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor02_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor03_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor04_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor05_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z3_d_tumor06_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(989804, i0, 0);
					}
				}
			}
		}
		else if(timerId == TM_look_quester)
		{
			if(_thisActor.c_ai0 == 0 || L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0) == null)
			{
				_thisActor.lookNeighbor(450);
				addTimer(TM_look_quester, TIME_look_quester * 1000);
			}
		}
		else if(timerId == TM_zone2_fail_laziness)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800308, "");
			Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800308, "");
			Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 4, 1, 1, 5000, 0, 1800246, "#1800241", "#1800243");
			Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800246, "#1800241", "#1800243");
			_thisActor.i_quest0 = 99;
			if(tide == 0)
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sboss04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_sb04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap71_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap81_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap91_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap101_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap111_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap72_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap82_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap92_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap102_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_a_trap112_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}
			else
			{
				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm01_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm02_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor03_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm03_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sboss04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_sb04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tumor04_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_tm04_mob_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap01_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap11_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap21_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap31_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap41_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap51_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap61_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap71_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap81_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap91_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap101_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap111_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap02_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap12_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap22_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap32_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap42_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap52_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap62_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap72_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap82_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap92_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap102_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
				maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), z2_d_trap112_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
				}
			}
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.rescheduleEndTask(15 * 60);
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature != null)
		{
			L2Party party = Util.getParty(creature);
			if(party != null && party.getCommandChannel() != null && (cc_ai5 == null || cc_ai5.get() == null))
			{
				cc_ai5 = new WeakReference<>(party.getCommandChannel());
				_thisActor.c_ai0 = party.getCommandChannel().getChannelLeader().getStoredId();
			}
		}
	}
}