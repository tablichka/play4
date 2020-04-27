package ai.base;

import ai.CombatMonster;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 13.12.11 20:54
 */
public class UndeadSeedTwinBoss extends CombatMonster
{
	public L2Skill pan_skill = SkillTable.getInstance().getInfo(388759553);
	public L2Skill donut_skill = SkillTable.getInstance().getInfo(388956161);
	public L2Skill pc_buff_skill = SkillTable.getInstance().getInfo(388890625);
	public L2Skill toggle_shield = SkillTable.getInstance().getInfo(388694017);
	public int BadgeName = 13868;
	public int BadgeNumber = 1;
	public int my_weapon = 13868;
	public int FieldCycle = 3;
	public int FieldCycle_Quantity = 2500;
	public int zone_type = 0;
	public String my_maker = "";

	public UndeadSeedTwinBoss(L2Character actor)
	{
		super(actor);
		boss_type = 0;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
		_thisActor.i_ai5 = 0;
		_thisActor.i_quest2 = 0;
		_thisActor.i_quest3 = 0;
		_thisActor.c_ai0 = 0;
		_thisActor.c_ai1 = 0;
		addTimer(1000, 3000);
		addTimer(1001, 20000);
		addTimer(1002, 20000);
		addTimer(1003, 20000);
		addTimer(1004, 20000);
		addTimer(1005, 10000);
		addTimer(1006, 60000);
		addTimer(8001, 4000);
		addTimer(8008, 10000);
		addTimer(9903, 10000);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || _thisActor.i_ai2 == 1)
			return;

		_thisActor.c_ai1 = attacker.getStoredId();
		if(_thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
		}

		if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 1 && _thisActor.i_ai2 == 0)
		{
			if(SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0 || SkillTable.getAbnormalLevel(_thisActor, 29622273) > 0 || SkillTable.getAbnormalLevel(_thisActor, 45547521) > 0)
			{
			}
			else
			{
				_thisActor.i_ai2 = 1;
				clearTasks();
				//_thisActor.changeNpcState(2);
				_thisActor.targetable = false;
				cancelTartes();
				_thisActor.stopMove();
				_thisActor.equipItem(0);
				//_thisActor.altUseSkill(SkillTable.getInstance().getInfo(381616129), _thisActor);
				addUseSkillDesire(_thisActor, 381616129, 1, 1, 10000000);
				addTimer(2002, 60000);
			}
			return;
		}

		_thisActor.callFriends(attacker, damage);

		if(attacker.isPlayer())
		{
			_thisActor.addDamage(attacker, damage);
			addAttackDesire(attacker, 0, DEFAULT_DESIRE);
		}
		else if(!attacker.isPlayer() && CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(!attacker.getPlayer().isDead())
			{
				_thisActor.addDamageHate(attacker, 0, damage);
			}
			if(_thisActor.i_ai2 == 0)
			{
				addAttackDesire(attacker, 1, 100);
			}
		}
	}

	@Override
	protected boolean createNewTask()
	{
		return false;
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
		}

		if(_thisActor.i_ai2 == 1)
			return;

		if(attacker.isPlayer())
		{
			_thisActor.addDamageHate(attacker, 0, 1);
		}
		else if(!attacker.isPlayer() && CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(!attacker.getPlayer().isDead())
			{
				_thisActor.addDamageHate(attacker, 0, 2);
				_thisActor.addDamageHate(attacker.getPlayer(), 0, 1);
			}
			else
			{
				addAttackDesire(attacker, 1, 100);
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		int skillIndex = skill != null ? skill.getId() * 65536 + skill.getLevel() : 0;
		if(skillIndex == 381616129)
		{
			addTimer(2001, 10000);
			_thisActor.setNpcState(2);
			if(_thisActor.c_ai0 != 0)
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 989808, 0, null);
			}
		}
		else if(skillIndex == 381681665)
		{
			_thisActor.equipItem(my_weapon);
			_thisActor.changeNpcState(0);
			_thisActor.i_ai2 = 0;
			addTimer(3001, 2000);
			if(_thisActor.c_ai0 != 0)
			{
				_thisActor.notifyAiEvent(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0), CtrlEvent.EVT_SCRIPT_EVENT, 98913, 0, null);
			}
			_thisActor.i_ai2 = 0;
		}
		super.onEvtFinishCasting(skill);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.i_ai0 == 0)
		{
			if(creature.isPlayer())
			{
				_thisActor.i_ai0 = 1;
				if(_thisActor.i_ai2 == 0)
				{
					addAttackDesire(creature, 1, 10);
				}

				_thisActor.addDamageHate(creature, 0, 10);
				L2Party party0 = Util.getParty(creature);
				if(party0 != null)
				{
					for(L2Player c0 : party0.getPartyMembers())
					{
						if(c0 != null)
						{
							if(!c0.isDead())
							{
								_thisActor.addDamageHate(c0, 0, basic_hate);
							}
						}
					}
				}
			}
		}
		else if(_thisActor.i_ai0 == 1)
		{
			if(creature.isPlayer())
			{
				L2Party party0 = Util.getParty(creature);
				if(party0 != null)
				{
					for(L2Player c0 : party0.getPartyMembers())
					{
						if(c0 != null)
						{
							_thisActor.addDamageHate(c0, 0, basic_hate);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2002)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if((c0 == null || c0.isDead()) && _thisActor.i_ai2 == 1)
			{
				addTimer(2001, 1000);
				_thisActor.i_ai3 = 1;
			}
		}
		else if(timerId == 8001)
		{
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 3000);
			if(_thisActor.i_ai0 == 1)
			{
				L2Character c0 = _thisActor.getMostHated();
				if(c0 != null && _thisActor.i_ai2 == 0)
				{
					int i0 = Rnd.get(100);
					if(Skill01_ID != null && i0 < Skill01_Probability)
					{
						if(SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0 || SkillTable.getAbnormalLevel(_thisActor, 29622273) > 0 || SkillTable.getAbnormalLevel(_thisActor, 45547521) > 0)
						{
						}
						else if(Skill01_Target_Type == 0)
						{
							if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
							{
								addUseSkillDesire(c0, Skill01_ID, 0, 1, 500000);
							}
							else
							{
								removeAllAttackDesire();
								addAttackDesire(c0, 1, DEFAULT_DESIRE);
							}
						}
						else if(Skill01_Target_Type == 1)
						{
							if(_thisActor.isInRange(c0, 200))
							{
								if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
								{
									addUseSkillDesire(_thisActor, Skill01_ID, 0, 0, 500000);
								}
								else
								{
									removeAllAttackDesire();
									addAttackDesire(c0, 1, DEFAULT_DESIRE);
								}
							}
						}
						else if(Skill01_Target_Type == 2)
						{
							L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
							c0 = h0.getAttacker();
							if(c0 != null)
							{
								if(Skill01_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill01_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill01_ID.getId()))
								{
									addUseSkillDesire(c0, Skill01_ID, 0, 1, 500000);
								}
								else
								{
									addAttackDesire(c0, 1, DEFAULT_DESIRE);
								}
							}
						}
					}
					else if(Skill02_ID != null && i0 > Skill01_Probability && i0 < (Skill01_Probability + Skill02_Probability))
					{
						if(SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0 || SkillTable.getAbnormalLevel(_thisActor, 29622273) > 0 || SkillTable.getAbnormalLevel(_thisActor, 45547521) > 0)
						{
						}
						else if(Skill02_Target_Type == 0)
						{
							if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
							{
								addUseSkillDesire(c0, Skill02_ID, 0, 1, 500000);
							}
							else
							{
								removeAllAttackDesire();
								addAttackDesire(c0, 1, DEFAULT_DESIRE);
							}
						}
						else if(Skill02_Target_Type == 1)
						{
							if(_thisActor.isInRange(c0, 200))
							{
								if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
								{
									addUseSkillDesire(_thisActor, Skill02_ID, 0, 0, 500000);
								}
								else
								{
									removeAllAttackDesire();
									addAttackDesire(c0, 1, DEFAULT_DESIRE);
								}
							}
						}
						else if(Skill02_Target_Type == 2)
						{
							L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
							c0 = h0.getAttacker();
							if(c0 != null)
							{
								if(Skill02_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill02_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill02_ID.getId()))
								{
									addUseSkillDesire(c0, Skill02_ID, 0, 1, 500000);
								}
								else
								{
									removeAllAttackDesire();
									addAttackDesire(c0, 1, DEFAULT_DESIRE);
								}
							}
						}
					}
					else if(Skill03_ID != null && i0 > (Skill01_Probability + Skill02_Probability) && i0 < ((Skill01_Probability + Skill02_Probability) + Skill03_Probability))
					{
						if(SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0 || SkillTable.getAbnormalLevel(_thisActor, 29622273) > 0 || SkillTable.getAbnormalLevel(_thisActor, 45547521) > 0)
						{
						}
						else if(Skill03_Target_Type == 0)
						{
							if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
							{
								addUseSkillDesire(c0, Skill03_ID, 0, 1, 500000);
							}
							else
							{
								removeAllAttackDesire();
								addAttackDesire(c0, 1, DEFAULT_DESIRE);
							}
						}
						else if(Skill03_Target_Type == 1)
						{
							if(_thisActor.isInRange(c0, 200))
							{
								if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
								{
									addUseSkillDesire(_thisActor, Skill03_ID, 0, 0, 500000);
								}
								else
								{
									removeAllAttackDesire();
									addAttackDesire(c0, 1, DEFAULT_DESIRE);
								}
							}
						}
						else if(Skill03_Target_Type == 2)
						{
							L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
							c0 = h0.getAttacker();
							if(c0 != null)
							{
								if(Skill03_ID.getMpConsume() < _thisActor.getCurrentMp() && Skill03_ID.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Skill03_ID.getId()))
								{
									addUseSkillDesire(c0, Skill03_ID, 0, 1, 500000);
								}
								else
								{
									removeAllAttackDesire();
									addAttackDesire(c0, 1, DEFAULT_DESIRE);
								}
							}
						}
					}
					else
					{
						removeAllAttackDesire();
						addAttackDesire(c0, 1, DEFAULT_DESIRE);
					}
				}
			}
			addTimer(8001, 4000 + Rnd.get(1000));
		}
		else if(timerId == 8008)
		{
			_thisActor.removeAllHateInfoIF(1, 0);
			_thisActor.removeAllHateInfoIF(3, 3000);
			addTimer(8008, 2000);
		}
		else if(timerId == 1000)
		{
			broadcastScriptEvent(989807, getStoredIdFromCreature(_thisActor), null, 2000);
			addTimer(1000, 30000);
		}
		else if(timerId == 1001)
		{
			if(_thisActor.i_ai0 == 1 && _thisActor.i_ai2 == 0)
			{
				L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
				if(h0 != null)
				{
					L2Character c0 = h0.getAttacker();
					if(c0 != null)
					{
						int i0 = Rnd.get(100);
						if(i0 > 50)
						{
							int i1 = ((c0.getX() + Rnd.get(50)));
							int i2 = ((c0.getY() + Rnd.get(50)));
							int i3 = (c0.getZ());
							addMoveToDesire(i1, i2, i3, 100000000);
						}
					}
				}
			}
			addTimer(1001, (20 + Rnd.get(5)) * 1000);
		}
		else if(timerId == 1002)
		{
			if(_thisActor.i_ai0 == 1 && _thisActor.i_ai2 == 0)
			{
				L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
				if(h0 != null)
				{
					L2Character c0 = h0.getAttacker();
					if(c0 != null)
					{
						addUseSkillDesire(c0, pan_skill, 0, 1, 5000000);
					}
				}
			}
			addTimer(1002, (10 + Rnd.get(5)) * 1000);
		}
		else if(timerId == 1003)
		{
			if(_thisActor.i_ai0 == 1 && _thisActor.i_ai2 == 0)
			{
				L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
				if(h0 != null)
				{
					L2Character c0 = h0.getAttacker();
					if(c0 != null)
					{
						addUseSkillDesire(c0, pan_skill, 0, 1, 5000000);

					}
				}
			}
			addTimer(1003, 20000);
		}
		else if(timerId == 1004)
		{
			L2Character c0 = null;
			if(_thisActor.i_ai0 == 1 && _thisActor.i_ai2 == 0)
			{
				L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
				if(h0 != null)
				{
					c0 = h0.getAttacker();
					if(c0 != null)
					{
						if(!_thisActor.isInRange(c0, 300))
						{
							addUseSkillDesire(c0, donut_skill, 0, 1, 5000000);
							c0 = null;
						}
					}
				}
			}
			if(c0 != null)
			{
				addTimer(1004, 2000);
			}
			else
			{
				addTimer(1004, (15 + Rnd.get(10)) * 1000);
			}
		}
		else if(timerId == 1005)
		{
			if(_thisActor.i_ai0 == 1 && _thisActor.i_ai2 == 0)
			{
				if(_thisActor.c_ai0 != 0)
				{
					L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
					if(!_thisActor.isInRange(c0, 500))
					{
						_thisActor.altUseSkill(toggle_shield, _thisActor);
						Functions.npcSay(_thisActor, Say2C.ALL, 1800266);
					}
					else
					{
						_thisActor.stopEffect(toggle_shield.getId());
					}
				}
			}
			addTimer(1005, 10000);
		}
		else if(timerId == 1006)
		{
			if(_thisActor.i_ai0 == 1 && _thisActor.i_ai2 == 0)
			{
				int i4;
				if(_thisActor.i_ai4 < 4)
				{
					i4 = 1;
					_thisActor.i_ai4++;
				}
				else
				{
					i4 = Rnd.get(2);
				}

				DefaultMaker maker0 = _thisActor.getMyMaker();

				if(maker0 != null)
				{
					int i0 = maker0.npc_count;
					if(i0 < maker0.maximum_npc)
					{
						if(zone_type == 0)
						{
							for(int i1 = 0; i1 < i4; i1++)
							{
								i0 = Rnd.get(6);
								switch(i0)
								{
									case 0:
										_thisActor.createOnePrivate(22509, "Is1SilenRavager", 0, 0, -173101, 218079, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 1:
										_thisActor.createOnePrivate(22510, "Is1DeathScout", 0, 0, -173312, 217536, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 2:
										_thisActor.createOnePrivate(22511, "Is1SilenDeciple", 0, 0, -173920, 217407, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 3:
										_thisActor.createOnePrivate(22512, "Is1BoneCreeper", 0, 0, -174289, 217734, -9580, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 4:
										_thisActor.createOnePrivate(22513, "Is1CorpseShambler", 0, 0, -174263, 218422, -9579, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 5:
										_thisActor.createOnePrivate(22514, "Is1SoulHarvester", 0, 0, -173834, 218762, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 6:
										_thisActor.createOnePrivate(22515, "Is1SoulWagon", 0, 0, -173300, 218594, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
								}
							}
						}
						else if(zone_type == 1)
						{
							for(int i1 = 0; i1 < i4; i1++)
							{
								i0 = Rnd.get(6);
								switch(i0)
								{
									case 0:
										_thisActor.createOnePrivate(22616, "Is1DeathClaw", 0, 0, -173101, 218079, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 1:
										_thisActor.createOnePrivate(22616, "Is1DeathClaw", 0, 0, -173312, 217536, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 2:
										_thisActor.createOnePrivate(22616, "Is1DeathClaw", 0, 0, -173920, 217407, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 3:
										_thisActor.createOnePrivate(22616, "Is1DeathClaw", 0, 0, -174289, 217734, -9580, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 4:
										_thisActor.createOnePrivate(22616, "Is1DeathClaw", 0, 0, -174263, 218422, -9579, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 5:
										_thisActor.createOnePrivate(22616, "Is1DeathClaw", 0, 0, -173834, 218762, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
									case 6:
										_thisActor.createOnePrivate(22616, "Is1DeathClaw", 0, 0, -173300, 218594, -9581, 0, getStoredIdFromCreature(_thisActor), 0, 0);
										break;
								}
							}
						}
					}
				}
			}
			addTimer(1006, 60000);
		}
		else if(timerId == 2001)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(_thisActor.i_ai3 == 1)
			{
				_thisActor.doDie(_thisActor.getMostHated());
				if(c0 != null)
				{
					_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 989808, 0, null);
				}
				return;
			}
			else if(c0 != null && c0.getCurrentHp() > (_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100) * 10)
			{
				_thisActor.setCurrentHp(c0.getCurrentHp() - 10);
			}
			else if(c0 != null)
			{
				_thisActor.setCurrentHp(_thisActor.getMaxHp() * 0.1);
			}

			_thisActor.targetable = true;

			if(boss_type == 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1800267);
			}
			else if(boss_type == 1)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1800268);
			}
			//_thisActor.altUseSkill(SkillTable.getInstance().getInfo(381681665), _thisActor);
			addUseSkillDesire(_thisActor, 381681665, 1, 1, 11000000);
		}
		else if(timerId == 8001)
		{
			if(_thisActor.i_ai0 == 1 && _thisActor.i_ai2 == 0)
			{
				L2Character c0 = _thisActor.getMostHated();
				if(c0 != null)
				{
					addAttackDesire(c0, 1, DEFAULT_DESIRE);
				}
			}
		}
		else if(timerId == 9903)
		{
			if(!_thisActor.inMyTerritory(_thisActor))
			{
				clearTasks();
				_thisActor.i_ai0 = 0;
				_thisActor.i_quest2++;
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800754);
				clearTasks();
				_thisActor.stopMove();
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc());
				_thisActor.removeAllHateInfoIF(0, 0);
				_thisActor.i_ai0 = 0;
				_thisActor.setCurrentHp(_thisActor.getMaxHp());

				if(_thisActor.i_quest2 > 10)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, 1800755);
					Instance inst = _thisActor.getInstanceZone();
					if(inst != null)
						inst.rescheduleEndTask(60);
				}
				if(_thisActor.i_ai1 == 1)
				{
					addTimer(1008, 1000);
				}
			}
			addTimer(9903, 10000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 989807)
		{
			if((Long) arg1 != getStoredIdFromCreature(_thisActor))
			{
				_thisActor.c_ai0 = L2ObjectsStorage.getAsCharacter((Long) arg1).getStoredId();
			}
		}
		else if(eventId == 989808)
		{
			_thisActor.i_ai3 = 1;
		}
		else if(eventId == 98913)
		{
			_thisActor.i_ai3 = 0;
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		Instance inst = _thisActor.getInstanceZone();
		if(inst != null)
			inst.markRestriction();
	}

	private void cancelTartes()
	{
		for(L2Player player : _thisActor.getAroundLivePlayers(2000))
		{
			if(player.getTarget() == _thisActor)
			{
				player.setTarget(null);
			}
		}
	}
}