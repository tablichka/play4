package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 09.09.11 5:22
 */
public class AiBeastfarmBeast extends AiBeastfarmBasic
{
	public int Skill01_Prob = 2500;
	public L2Skill Skill_LookAtMe = SkillTable.getInstance().getInfo(436797441);
	public L2Skill Skill_Display = null;
	public L2Skill Buff = null;
	public L2Skill DeBuff = null;
	public int TIMER_spawn_attack = 2115006;
	public int ITEM_feed_item = 15474;
	public int ITEM_feed_adena = 15475;
	public int ITEM_feed_item_s = 15478;
	public int ITEM_feed_adena_s = 15479;
	public int ITEM_feed_item_bress = 15476;
	public int ITEM_feed_adena_bress = 15477;

	public AiBeastfarmBeast(L2Character actor)
	{
		super(actor);
		Skill01_ID = null;
		TIMER_despawn = 2115003;
		my_type = -1;
		my_grade = -1;
		debug_mode = 0;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addEffectActionDesire(1, 120, 1000000);
		_thisActor.i_ai4 = 0;
		_thisActor.i_ai8 = 0;
		_thisActor.i_ai9 = 0;
		if(_thisActor.param1 != 0 && _thisActor.param2 != 0)
		{
			_thisActor.l_ai0 = _thisActor.param1;
			_thisActor.l_ai1 = _thisActor.param2;
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
			if(c0 != null)
			{
				_thisActor.l_ai5 = _thisActor.param1;
				if(Skill_LookAtMe != null)
				{
					_thisActor.altUseSkill(Skill_LookAtMe, c0);
				}
				addTimer(TIMER_spawn_attack, 1000);
			}
			else if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "ERR : param1's pc is null");
			}
		}
		else if(debug_mode > 0)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "ERR : param1 or param2 is 0");
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		super.onEvtTimer(timerId, arg1, arg2);
		if(timerId == TIMER_spawn_attack)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai5);
			if(c0 != null)
			{
				addAttackDesire(c0, 1, 10000);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		int i0 = 0;
		int i1 = 0;
		_thisActor.addDamage(attacker, damage);
		addAttackDesire(attacker, 1, damage);
		if(Skill01_ID != null)
		{
			if(Rnd.get(10000) < Skill01_Prob)
			{
				if(_thisActor.getMostHated() != null)
				{
					addUseSkillDesire(attacker, Skill01_ID, 0, 1, 1000000);
				}
			}
		}
		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.700000))
		{
			if(_thisActor.i_ai8 == 0)
			{
				_thisActor.i_ai8 = 1;
				if(my_grade == 4)
				{
					if(Rnd.get(2) == 1)
					{
						if(Buff.getMpConsume() < _thisActor.getCurrentMp() && Buff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Buff.getId()))
						{
							addUseSkillDesire(_thisActor, Buff, 1, 1, 10000000);
						}
					}
					else if(DeBuff.getMpConsume() < _thisActor.getCurrentMp() && DeBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DeBuff.getId()))
					{
						addUseSkillDesire(attacker, DeBuff, 0, 1, 1000000);
					}
				}
				else if(Buff.getMpConsume() < _thisActor.getCurrentMp() && Buff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Buff.getId()))
				{
					addUseSkillDesire(_thisActor, Buff, 1, 1, 10000000);
				}
			}
		}
		if(CategoryManager.isInCategory(122, attacker.getNpcId()))
		{
			_thisActor.i_ai6++;
			if(_thisActor.i_ai6 == 5)
			{
				_thisActor.doDie(null);
			}
		}
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai5);
		if(c0 != null)
		{
			if(attacker == c0)
			{
				if(skill == SKILL_feed_item_s || skill == SKILL_feed_adena_s || skill == SKILL_feed_item_bress || skill == SKILL_feed_adena_bress)
				{
					if(Rnd.get(10000) < 7000)
					{
						Functions.showSystemMessageFStr(attacker, 1801091);
					}
					else
					{
						Functions.showSystemMessageFStr(attacker, 1801092);
						if(skill == SKILL_feed_item_s)
						{
							_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_item_s, 1);
						}
						else if(skill == SKILL_feed_adena_s)
						{
							_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_adena_s, 1);
						}
						else if(skill == SKILL_feed_item_bress)
						{
							_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_item_bress, 1);
						}
						else if(skill == SKILL_feed_adena_bress)
						{
							_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_adena_bress, 1);
						}
					}
				}
				else if(skill == SKILL_feed_item || skill == SKILL_feed_adena)
				{
					if((_thisActor.l_ai1 == 1 && skill == SKILL_feed_item) || (_thisActor.l_ai1 == 2 && skill == SKILL_feed_adena))
					{
						_thisActor.i_ai4++;
						if(my_grade == 2)
						{
							if(((_thisActor.i_ai4 == 1 && Rnd.get(100) < 30) || (_thisActor.i_ai4 == 2 && Rnd.get(100) < 50)) || (_thisActor.i_ai4 == 3 && _thisActor.i_ai9 == 0))
							{
								_thisActor.i_ai9 = 1;
								switch(my_type)
								{
									case 1:
										if(_thisActor.l_ai1 == 1)
										{
											i0 = 18876;
											i1 = 1;
										}
										else if(_thisActor.l_ai1 == 2)
										{
											i0 = 18877;
											i1 = 2;
										}
										break;
									case 2:
										if(_thisActor.l_ai1 == 1)
										{
											i0 = 18883;
											i1 = 1;
										}
										else if(_thisActor.l_ai1 == 2)
										{
											i0 = 18884;
											i1 = 2;
										}
										break;
									case 3:
										if(_thisActor.l_ai1 == 1)
										{
											i0 = 18890;
											i1 = 1;
										}
										else if(_thisActor.l_ai1 == 2)
										{
											i0 = 18891;
											i1 = 2;
										}
										break;
									case 4:
										if(_thisActor.l_ai1 == 1)
										{
											i0 = 18897;
											i1 = 1;
										}
										else if(_thisActor.l_ai1 == 2)
										{
											i0 = 18898;
											i1 = 2;
										}
										break;
								}
								_thisActor.createOnePrivate(i0, "AiBeastfarmBeast", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading() * 182, getStoredIdFromCreature(c0), i1, 0);
								addTimer(TIMER_despawn, 500);
							}
						}
						else if(my_grade == 3)
						{
							if(_thisActor.i_ai4 == 1 && _thisActor.i_ai9 == 0)
							{
								if(Rnd.get(100) < 10)
								{
									_thisActor.i_ai9 = 1;
									switch(my_type)
									{
										case 1:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 11;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 12;
											}
											break;
										case 2:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 21;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 22;
											}
											break;
										case 3:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 31;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 32;
											}
											break;
										case 4:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 41;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 42;
											}
											break;
									}
									DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(MAKER_tamed_beast);
									if(maker0 != null)
									{
										maker0.onScriptEvent(21150002, getStoredIdFromCreature(attacker), i0);
									}
									addTimer(TIMER_despawn, 500);
								}
								else if(Rnd.get(100) < 20)
								{
									_thisActor.i_ai9 = 1;
									switch(my_type)
									{
										case 1:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18878;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18879;
												i1 = 2;
											}
											break;
										case 2:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18885;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18886;
												i1 = 2;
											}
											break;
										case 3:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18892;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18893;
												i1 = 2;
											}
											break;
										case 4:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18899;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18900;
												i1 = 2;
											}
											break;
									}
									_thisActor.createOnePrivate(i0, "AiBeastfarmBeast", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading() * 182, getStoredIdFromCreature(c0), i1, 0);
									addTimer(TIMER_despawn, 500);
								}
							}
							else if(_thisActor.i_ai4 == 2 && _thisActor.i_ai9 == 0)
							{
								if(Rnd.get(100) < 20)
								{
									_thisActor.i_ai9 = 1;
									switch(my_type)
									{
										case 1:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 11;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 12;
											}
											break;
										case 2:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 21;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 22;
											}
											break;
										case 3:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 31;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 32;
											}
											break;
										case 4:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 41;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 42;
											}
											break;
									}
									DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(MAKER_tamed_beast);
									if(maker0 != null)
									{
										maker0.onScriptEvent(21150002, getStoredIdFromCreature(attacker), i0);
									}
									addTimer(TIMER_despawn, 500);
								}
								else if(Rnd.get(100) < 50)
								{
									_thisActor.i_ai9 = 1;
									switch(my_type)
									{
										case 1:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18878;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18879;
												i1 = 2;
											}
											break;
										case 2:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18885;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18886;
												i1 = 2;
											}
											break;
										case 3:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18892;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18893;
												i1 = 2;
											}
											break;
										case 4:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18899;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18900;
												i1 = 2;
											}
											break;
									}
									_thisActor.createOnePrivate(i0, "AiBeastfarmBeast", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading(), getStoredIdFromCreature(c0), i1, 0);
									addTimer(TIMER_despawn, 500);
								}
							}
							else if(_thisActor.i_ai4 == 3 && _thisActor.i_ai9 == 0)
							{
								if(Rnd.get(100) < 30)
								{
									_thisActor.i_ai9 = 1;
									switch(my_type)
									{
										case 1:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 11;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 12;
											}
											break;
										case 2:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 21;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 22;
											}
											break;
										case 3:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 31;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 32;
											}
											break;
										case 4:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 41;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 42;
											}
											break;
									}
									DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(MAKER_tamed_beast);
									if(maker0 != null)
									{
										maker0.onScriptEvent(21150002, getStoredIdFromCreature(attacker), i0);
									}
									addTimer(TIMER_despawn, 500);
								}
								else
								{
									_thisActor.i_ai9 = 1;
									switch(my_type)
									{
										case 1:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18878;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18879;
												i1 = 2;
											}
											break;
										case 2:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18885;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18886;
												i1 = 2;
											}
											break;
										case 3:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18892;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18893;
												i1 = 2;
											}
											break;
										case 4:
											if(_thisActor.l_ai1 == 1)
											{
												i0 = 18899;
												i1 = 1;
											}
											else if(_thisActor.l_ai1 == 2)
											{
												i0 = 18900;
												i1 = 2;
											}
											break;
									}
									_thisActor.createOnePrivate(i0, "AiBeastfarmBeast", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading() * 182, getStoredIdFromCreature(c0), i1, 0);
									addTimer(TIMER_despawn, 500);
								}
							}
						}
						else if(my_grade == 4)
						{
							if(Rnd.get(10000) < 7000)
							{
								Functions.showSystemMessageFStr(attacker, 1801091);
							}
							else
							{
								Functions.showSystemMessageFStr(attacker, 1801092);
								if(skill == SKILL_feed_item)
								{
									_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_item, 1);
								}
								else if(skill == SKILL_feed_adena)
								{
									_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_adena, 1);
								}
								else if(skill == SKILL_feed_item_s)
								{
									_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_item_s, 1);
								}
								else if(skill == SKILL_feed_adena_s)
								{
									_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_adena_s, 1);
								}
								else if(skill == SKILL_feed_item_bress)
								{
									_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_item_bress, 1);
								}
								else if(skill == SKILL_feed_adena_bress)
								{
									_thisActor.dropItem(attacker.getPlayer(), ITEM_feed_adena_bress, 1);
								}
							}
						}
						else if(debug_mode > 0)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "ERR : my_grade param is incorrect. " + my_grade);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character cha)
	{
		super.onEvtDead(cha);
		if(cha == null)
			return;

		L2Player killer = cha.getPlayer();
		if(killer != null && killer.isQuestStarted(458) && killer.getQuestState(458).getMemoState() == 1)
		{
			QuestState st = killer.getQuestState(458);
			int i3 = 0;
			if(my_grade == 4)
			{
				switch(my_type)
				{
					case 1:
					{
						i3 = 18879;
						if(st.getInt("k18879") < 10)
							st.set("k18879", st.getInt("k18879") + 1);
						break;
					}
					case 2:
					{
						i3 = 18886;
						if(st.getInt("k18886") < 10)
							st.set("k18886", st.getInt("k18886") + 1);
						break;
					}
					case 3:
					{
						i3 = 18893;
						if(st.getInt("k18893") < 10)
							st.set("k18893", st.getInt("k18893") + 1);
						break;
					}
					case 4:
					{
						i3 = 18900;
						if(st.getInt("k18900") < 10)
							st.set("k18900", st.getInt("k18900") + 1);
						break;
					}
				}
			}

			if(st.getInt("k" + i3) < 10)
			{
				int i0 = 0;
				int i1 = 0;
				int i2 = 0;
				if(_thisActor.isOverhit())
				{
					float f0 = 1f + (float) (((L2MonsterInstance) _thisActor).getOverhitDamage() / _thisActor.getMaxHp());
					if(f0 >= 1.200000)
					{
						st.set("ex_2", st.getInt("ex_2") + 1);
					}

					st.set("ex_1", st.getInt("ex_1") + 1);
					st.set("ex_3", st.getInt("ex_3") + 1);
					i0 = st.getInt("ex_3") % 100;
					i1 = st.getInt("ex_3") - (i0 * 100);
					if(i0 < i1)
					{
						st.set("ex_3", (i1 * 100) + i1);
					}
				}
				else
				{
					i2 = st.getInt("ex_3") % 100;
					st.set("ex_3", i2 * 100);
				}
			}

			if(st.getInt("k18879") == 10 && st.getInt("k18886") == 10 && st.getInt("k18893") == 10 && st.getInt("k18900") == 10)
			{
				st.setMemoState(2);
				st.setCond(2);
				st.getQuest().showQuestMark(killer);
				st.playSound(Quest.SOUND_MIDDLE);
				st.set("ex_3", st.getInt("ex_3") % 100);
			}
			else
			{
				st.playSound(Quest.SOUND_ITEMGET);
			}
		}
		if(killer != null && killer.isQuestStarted(631) && killer.getQuestState(631).getMemoState() == 1)
		{
			QuestState st = killer.getQuestState(631);
			int i1 = 0;
			if(my_grade == 4)
			{
				switch(my_type)
				{
					case 1:
						if(_thisActor.l_ai1 == 1)
						{
							i1 = 172;
						}
						else if(_thisActor.l_ai1 == 2)
						{
							i1 = 334;
						}
						break;
					case 2:
						if(_thisActor.l_ai1 == 1)
						{
							i1 = 172;
						}
						else if(_thisActor.l_ai1 == 2)
						{
							i1 = 334;
						}
						break;
					case 3:
						if(_thisActor.l_ai1 == 1)
						{
							i1 = 182;
						}
						else if(_thisActor.l_ai1 == 2)
						{
							i1 = 349;
						}
						break;
					case 4:
						if(_thisActor.l_ai1 == 1)
						{
							i1 = 182;
						}
						else if(_thisActor.l_ai1 == 2)
						{
							i1 = 349;
						}
						break;
				}

				if(st.rollAndGiveLimited(15534, 1, i1 / 10., 120))
				{
					if(st.getQuestItemsCount(15534) >= 120)
					{
						st.setMemoState(2);
						st.setCond(2);
						Quest.showQuestMark(killer, 631);
						st.playSound(Quest.SOUND_MIDDLE);
					}
					else
					{
						st.playSound(Quest.SOUND_ITEMGET);
					}
				}
			}
		}
	}
}
