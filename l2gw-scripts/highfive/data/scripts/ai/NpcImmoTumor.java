package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 15.12.11 18:27
 */
public class NpcImmoTumor extends Citizen
{
	public int tide = 0;
	public String type = "";
	public int zone = 0;
	public int room = 0;
	public String z3_entrance = "zone3_entrance";
	public String ech_atk_seq0_maker = "rumwarsha15_1424_echmusm1";
	public String ech_atk_seq1_maker = "rumwarsha15_1424_echmusm2";
	public String ech_atk_expel_maker = "rumwarsha15_1424_expelm1";
	public String ech_def_seq0_maker = "rumwarsha15_1424_ech_dummy1m1";
	public String ech_def_seq1_maker = "rumwarsha15_1424_ech_dummy2m1";
	public String ech_def_seq2_maker = "rumwarsha15_1424_ech_dummy2m2";
	public String ech_def_seq3_maker = "rumwarsha15_1424_defwagonm1";
	public String ech_def_seq4_maker = "rumwarsha15_1424_veinm1";
	public String dispatcher_maker = "";
	public L2Skill Skill_Branding = SkillTable.getInstance().getInfo(542375937);
	public int reward_siege = 13797;
	public int reward_rate = 15;
	public int cost_warp_low = 1;
	public int cost_warp_high = 3;
	public int TIME_reward_drop = 60;
	public int TM_RESPAWN_BLANK = 78001;
	public int TM_SPAWN_VEIN = 78002;
	public int TM_SEND_HELP = 78003;
	public int TM_REWARD_DROP = 78004;
	public int TM_TUMOR_CHECK = 78005;
	public int TIME_respawn_blank = 10;
	public int Vein01 = 18709;
	public String Vein01_AI = "ImmoVein";
	public int Vein02 = 18710;
	public String Vein02_AI = "ImmoVein";
	public int Vein03 = 18711;
	public String Vein03_AI = "ImmoVein";
	public int Vein_Convergency = 7;
	public int Vein_Size = 50;
	public int regen_value = 24;
	public int Vein_Delay = 30;
	public int Vein_Demand = 1500;
	public int TACT_AGGRESIVE = 0;
	public int TACT_INTERCEPT = 1;
	public int TACT_DEFENSIVE = 2;
	public String fnHi = "";
	public String fnHi_warpAsk = "tumor002.htm";
	public String fnHi_warpBossAsk = "tumor002a.htm";
	public String fnHi_warpBossAsk2 = "tumor002b.htm";
	public String fnHi_warpConfirm = "tumor003.htm";
	public String fnHi_warpConfirm2 = "tumor003a.htm";
	public String fnHi_warpFailed = "tumor004.htm";
	public String fnHi_warpNoDest = "tumor005.htm";
	public String fnHi_notParty = "tumor006.htm";
	public String fnHi_warpBlank = "tumor099.htm";
	public int z2_tumor1_x = -176036;
	public int z2_tumor1_y = 210002;
	public int z2_tumor1_z = -11948;
	public int z2_tumor2_x = -176039;
	public int z2_tumor2_y = 208203;
	public int z2_tumor2_z = -11949;
	public int z2_tumor3_x = -183288;
	public int z2_tumor3_y = 208205;
	public int z2_tumor3_z = -11939;
	public int z2_tumor4_x = -183290;
	public int z2_tumor4_y = 210004;
	public int z2_tumor4_z = -11939;
	public int z3_tumor1_x = -179779;
	public int z3_tumor1_y = 212540;
	public int z3_tumor1_z = -15520;
	public int z3_tumor2_x = -177028;
	public int z3_tumor2_y = 211135;
	public int z3_tumor2_z = -15520;
	public int z3_tumor3_x = -176355;
	public int z3_tumor3_y = 208043;
	public int z3_tumor3_z = -15520;
	public int z3_tumor4_x = -179284;
	public int z3_tumor4_y = 205990;
	public int z3_tumor4_z = -15520;
	public int z3_tumor5_x = -182268;
	public int z3_tumor5_y = 208218;
	public int z3_tumor5_z = -15520;
	public int z3_tumor6_x = -182069;
	public int z3_tumor6_y = 211140;
	public int z3_tumor6_z = -15520;
	public int echmus_x = -179537;
	public int echmus_y = 209551;
	public int echmus_z = -15504;

	public NpcImmoTumor(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(type.equals("def"))
		{
			addTimer(TM_RESPAWN_BLANK, TIME_respawn_blank * 1000);
		}
		_thisActor.c_ai0 = 0;
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_TUMOR_CHECK)
		{
			DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), dispatcher_maker);
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010061, room, 0);
			}
		}
		else if(timerId == TM_RESPAWN_BLANK)
		{
			_thisActor.i_ai0 = 1;
			addTimer(TM_SPAWN_VEIN, 1000);
			addTimer(TM_REWARD_DROP, TIME_reward_drop * 1000);
		}
		else if(timerId == TM_SPAWN_VEIN)
		{
			DefaultMaker maker0 = _thisActor.getMyMaker();
			if(maker0.npc_count < maker0.maximum_npc && _thisActor.i_ai2 < Vein_Demand)
			{
				switch(Rnd.get(3))
				{
					case 0:
						_thisActor.createOnePrivate(Vein01, Vein01_AI, 0, 0, _thisActor.getX() + Rnd.get(Vein_Convergency) * Vein_Size - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getY() + Rnd.get(Vein_Convergency) * Vein_Size - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getZ(), Rnd.get(61440), 0, 0, 0);
						break;
					case 1:
						_thisActor.createOnePrivate(Vein02, Vein02_AI, 0, 0, _thisActor.getX() + Rnd.get(Vein_Convergency) * Vein_Size - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getY() + Rnd.get(Vein_Convergency) * Vein_Size - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getZ(), Rnd.get(61440), 0, 0, 0);
						break;
					case 2:
						_thisActor.createOnePrivate(Vein03, Vein03_AI, 0, 0, _thisActor.getX() + Rnd.get(Vein_Convergency) * Vein_Size - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getY() + Rnd.get(Vein_Convergency) * Vein_Size - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getZ(), Rnd.get(61440), 0, 0, 0);
						break;
				}
			}

			int i0 = Vein_Delay / 2 + Rnd.get(Vein_Delay / 2);
			addTimer(TM_SPAWN_VEIN, i0 * 1000);
		}
		else if(timerId == TM_REWARD_DROP)
		{
			if(Rnd.get(100) <= reward_rate)
			{
				_thisActor.dropItem(reward_siege, 1);
			}
			addTimer(TM_REWARD_DROP, TIME_reward_drop * 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010058 && _thisActor.i_ai0 != 0)
		{
			if((Long) arg1 == 0)
			{
				_thisActor.i_ai2 = (_thisActor.i_ai2 + regen_value);
				if(_thisActor.i_ai2 >= Vein_Demand)
				{
					if(_thisActor.i_ai0 == 1)
					{
						_thisActor.i_ai0 = 0;
						_thisActor.onDecay();
					}
				}
				L2NpcInstance c0 = L2ObjectsStorage.getAsNpc((Long) arg2);
				if(c0 != null)
				{
					c0.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 998915, 0, 0);
				}
			}
			else if((Long) arg1 == 1)
			{
				_thisActor.i_ai2 -= (Long) arg2;
			}
		}
		else if(eventId == 78010062 && (Integer) arg1 == room && _thisActor.i_ai0 != 0)
		{
			L2Player p0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(p0 == null)
				return;

			if((Integer) arg2 != 9999 && p0.getItemCountByItemId(reward_siege) >= cost_warp_low)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800247, p0.getName());
				Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800247, p0.getName());
				p0.destroyItem("Consume", reward_siege, cost_warp_low, _thisActor, true);
			}
			else if((Integer) arg2 != 9999)
			{
				_thisActor.showPage(p0, fnHi_warpFailed);
				return;
			}

			L2Party party0 = Util.getParty(p0);
			if((Integer) arg2 == 211)
			{
				if(party0 == null)
				{
					p0.teleToLocation(z2_tumor1_x + Rnd.get(100) - Rnd.get(100), z2_tumor1_y + Rnd.get(100) - Rnd.get(100), z2_tumor1_z);
				}
				else
				{
					_thisActor.teleportParty(party0, z2_tumor1_x + Rnd.get(100) - Rnd.get(100), z2_tumor1_y + Rnd.get(100) - Rnd.get(100), z2_tumor1_z, 2000, 0);
				}
			}
			else if((Integer) arg2 == 221)
			{
				if(party0 == null)
				{
					p0.teleToLocation(z2_tumor2_x + Rnd.get(100) - Rnd.get(100), z2_tumor2_y + Rnd.get(100) - Rnd.get(100), z2_tumor2_z);
				}
				else
				{
					_thisActor.teleportParty(party0, z2_tumor2_x + Rnd.get(100) - Rnd.get(100), z2_tumor2_y + Rnd.get(100) - Rnd.get(100), z2_tumor2_z, 2000, 0);
				}
			}
			else if((Integer) arg2 == 231)
			{
				if(party0 == null)
				{
					p0.teleToLocation(z2_tumor3_x + Rnd.get(100) - Rnd.get(100), z2_tumor3_y + Rnd.get(100) - Rnd.get(100), z2_tumor3_z);
				}
				else
				{
					_thisActor.teleportParty(party0, z2_tumor3_x + Rnd.get(100) - Rnd.get(100), z2_tumor3_y + Rnd.get(100) - Rnd.get(100), z2_tumor3_z, 2000, 0);
				}
			}
			else if((Integer) arg2 == 241)
			{
				if(party0 == null)
				{
					p0.teleToLocation(z2_tumor4_x + Rnd.get(100) - Rnd.get(100), z2_tumor4_y + Rnd.get(100) - Rnd.get(100), z2_tumor4_z);
				}
				else
				{
					_thisActor.teleportParty(party0, z2_tumor4_x + Rnd.get(100) - Rnd.get(100), z2_tumor4_y + Rnd.get(100) - Rnd.get(100), z2_tumor4_z, 2000, 0);
				}
			}
			else if((Integer) arg2 == 301)
			{
				if(party0 == null)
				{
					p0.teleToLocation(z3_tumor1_x + Rnd.get(100) - Rnd.get(100), z3_tumor1_y + Rnd.get(100) - Rnd.get(100), z3_tumor1_z);
				}
				else
				{
					_thisActor.teleportParty(party0, z3_tumor1_x + Rnd.get(100) - Rnd.get(100), z3_tumor1_y + Rnd.get(100) - Rnd.get(100), z3_tumor1_z, 2000, 0);
				}
			}
			else if((Integer) arg2 == 302)
			{
				if(party0 == null)
				{
					p0.teleToLocation(z3_tumor2_x + Rnd.get(100) - Rnd.get(100), z3_tumor2_y + Rnd.get(100) - Rnd.get(100), z3_tumor2_z);
				}
				else
				{
					_thisActor.teleportParty(party0, z3_tumor2_x + Rnd.get(100) - Rnd.get(100), z3_tumor2_y + Rnd.get(100) - Rnd.get(100), z3_tumor2_z, 2000, 0);
				}
			}
			else if((Integer) arg2 == 303)
			{
				if(party0 == null)
				{
					p0.teleToLocation(z3_tumor3_x + Rnd.get(100) - Rnd.get(100), z3_tumor3_y + Rnd.get(100) - Rnd.get(100), z3_tumor3_z);
				}
				else
				{
					_thisActor.teleportParty(party0, z3_tumor3_x + Rnd.get(100) - Rnd.get(100), z3_tumor3_y + Rnd.get(100) - Rnd.get(100), z3_tumor3_z, 2000, 0);
				}
			}
			else if((Integer) arg2 == 304)
			{
				if(party0 == null)
				{
					p0.teleToLocation(z3_tumor4_x + Rnd.get(100) - Rnd.get(100), z3_tumor4_y + Rnd.get(100) - Rnd.get(100), z3_tumor4_z);
				}
				else
				{
					_thisActor.teleportParty(party0, z3_tumor4_x + Rnd.get(100) - Rnd.get(100), z3_tumor4_y + Rnd.get(100) - Rnd.get(100), z3_tumor4_z, 2000, 0);
				}
			}
			else if((Integer) arg2 == 305)
			{
				if(party0 == null)
				{
					p0.teleToLocation(z3_tumor5_x + Rnd.get(100) - Rnd.get(100), z3_tumor5_y + Rnd.get(100) - Rnd.get(100), z3_tumor5_z);
				}
				else
				{
					_thisActor.teleportParty(party0, z3_tumor5_x + Rnd.get(100) - Rnd.get(100), z3_tumor5_y + Rnd.get(100) - Rnd.get(100), z3_tumor5_z, 2000, 0);
				}
			}
			else if((Integer) arg2 == 9999)
			{
				_thisActor.showPage(p0, fnHi_warpNoDest);
			}
		}
		else if(eventId == 78010063 && (Integer) arg1 == room)
		{
			L2Player p0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
			if(p0 == null)
				return;

			Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800248, p0.getName());
			Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800248, p0.getName());
			if(p0.getItemCountByItemId(reward_siege) >= cost_warp_high)
			{
				p0.destroyItemByItemId("Consume", reward_siege, cost_warp_high, _thisActor, true);
			}
			else if(!type.equals("boss"))
			{
				_thisActor.showPage(p0, fnHi_warpFailed);
				return;
			}

			L2Party party0 = Util.getParty(p0);
			if(party0 != null)
			{
				_thisActor.teleportParty(party0, echmus_x + Rnd.get(100) - Rnd.get(100), echmus_y + Rnd.get(100) - Rnd.get(100), echmus_z, 2000, 0);

				DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
				if(maker0 != null)
				{
					maker0.onScriptEvent(78010067, party0.getPartyId(), 0);
				}
			}
			else
			{
				_thisActor.showPage(p0, fnHi_notParty);
			}
		}
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(talker != null)
		{
			_thisActor.c_ai0 = talker.getStoredId();
		}
		_thisActor.showPage(talker, fnHi);
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(talker != null)
		{
			_thisActor.c_ai0 = talker.getStoredId();
		}
		if(ask == -7801)
		{
			if(reply == 1)
			{
				if(_thisActor.i_ai0 != 0 && type.equals("def") && zone == 3 && tide == 0)
				{
					_thisActor.showPage(talker, fnHi_warpBossAsk2);
				}
				else if(_thisActor.i_ai0 == 0 && type.equals("boss"))
				{
					_thisActor.showPage(talker, fnHi_warpBossAsk);
				}
				else if(_thisActor.i_ai0 != 0 && type.equals("def") && (zone == 2 || zone == 3 && tide == 1))
				{
					_thisActor.showPage(talker, fnHi_warpAsk);
				}
				else
				{
					_thisActor.showPage(talker, fnHi_warpBlank);
				}
			}
			else if(reply == 2 && _thisActor.i_ai0 != 0 && type.equals("def"))
			{
				L2Party party0 = Util.getParty(talker);
				if(party0 != null || debug)
				{
					_thisActor.showPage(talker, fnHi_warpConfirm);
				}
				else
				{
					_thisActor.showPage(talker, fnHi_notParty);
				}
			}
			else if(reply == 4 && _thisActor.i_ai0 != 0 && type.equals("def"))
			{
				L2Party party0 = Util.getParty(talker);
				if(party0 != null || debug)
				{
					if(talker.getItemCountByItemId(reward_siege) >= cost_warp_low)
					{
						addTimer(TM_TUMOR_CHECK, 1);
					}
					else
					{
						_thisActor.showPage(talker, fnHi_warpFailed);
					}
				}
				else
				{
					_thisActor.showPage(talker, fnHi_notParty);
				}
			}
			else if(reply == 3 && _thisActor.i_ai0 == 0 && type.equals("boss"))
			{
				L2Party party0 = Util.getParty(talker);
				if(party0 != null)
				{
					L2Player p0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
					if(p0 != null)
					{
						Functions.broadcastOnScreenMsgFStr(_thisActor, 8000, 5, 0, 1, 2, 1, 1, 5000, 0, 1800248, p0.getName());
						Functions.broadcastSystemMessageFStr(_thisActor, 8000, 1800248, p0.getName());
					}
					_thisActor.teleportParty(party0, echmus_x + Rnd.get(100) - Rnd.get(100), echmus_y + Rnd.get(100) - Rnd.get(100), echmus_z, 2000, 0);

					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_seq0_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010067, party0.getPartyId(), 0);
					}
				}
				else
				{
					_thisActor.showPage(talker, fnHi_notParty);
					return;
				}

				if(party0 != null || debug)
				{
					DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), dispatcher_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(78010067, 0, 0);
					}
					_thisActor.onDecay();
				}
			}
			else if(reply == 6 && _thisActor.i_ai0 != 0 && (type.equals("def") && zone == 3 && tide == 0))
			{
				L2Party party0 = Util.getParty(talker);
				if(party0 != null || debug)
				{
					_thisActor.showPage(talker, fnHi_warpConfirm2);
				}
				else
				{
					_thisActor.showPage(talker, fnHi_notParty);
				}
			}
			else if(reply == 5 && _thisActor.i_ai0 != 0 && (type.equals("def") && zone == 3 && tide == 0))
			{
				L2Party party0 = Util.getParty(talker);
				if(party0 != null || debug)
				{
					if(talker.getItemCountByItemId(reward_siege) >= cost_warp_high)
					{
						DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), ech_atk_expel_maker);
						if(maker0 != null)
						{
							maker0.onScriptEvent(78010069, room, 0);
						}
					}
					else
					{
						_thisActor.showPage(talker, fnHi_warpFailed);
					}
				}
				else
				{
					_thisActor.showPage(talker, fnHi_notParty);
				}
			}
			else
			{
				_thisActor.showPage(talker, fnHi_warpBlank);
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		broadcastScriptEvent(989812, 0, 0, 1500);
	}
}