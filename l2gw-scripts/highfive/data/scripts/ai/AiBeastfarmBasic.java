package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 09.09.11 4:42
 */
public class AiBeastfarmBasic extends WarriorUseSkill
{
	public L2Skill SKILL_feed_item = SkillTable.getInstance().getInfo(593035265);
	public L2Skill SKILL_feed_adena = SkillTable.getInstance().getInfo(593100801);
	public L2Skill SKILL_feed_item_s = SkillTable.getInstance().getInfo(593297409);
	public L2Skill SKILL_feed_adena_s = SkillTable.getInstance().getInfo(593362945);
	public L2Skill SKILL_feed_item_bress = SkillTable.getInstance().getInfo(593166337);
	public L2Skill SKILL_feed_adena_bress = SkillTable.getInstance().getInfo(593231873);
	public int PROB_gold_beast = 10;
	public int PROB_beast_s = 8000;
	public int PROB_beast_bress = 8000;
	public int PROB_direct_pet = 100;
	public int PROB_direct_4th = 100;
	public int TIMER_despawn = 2115003;
	public int TIMER_itsme = 2115008;
	public String MAKER_tamed_beast = "rune06_npc2115_05m1";
	public int my_grade = 1;
	public int my_type = -1;
	public int debug_mode = 0;

	public AiBeastfarmBasic(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.c_ai0 = 0;
		_thisActor.i_ai6 = 0;
		_thisActor.i_ai8 = 0;
		int i0 = Rnd.get(250) + 50;
		addTimer(TIMER_itsme, (i0 * 1000));
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_thisActor.addDamage(attacker, damage);
		if(skill == SKILL_feed_item || skill == SKILL_feed_adena || skill == SKILL_feed_item_s || skill == SKILL_feed_adena_s || skill == SKILL_feed_item_bress || skill == SKILL_feed_adena_bress)
		{
			int i0 = 0;
			int i1 = 0;
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 == null)
			{
				if(attacker != null && attacker.isPlayer())
				{
					_thisActor.c_ai0 = attacker.getStoredId();
				}
				if(Rnd.get(10000) < PROB_gold_beast)
				{
					switch(my_type)
					{
						case 1:
							i0 = 18901;
							break;
						case 2:
							i0 = 18902;
							break;
						case 3:
							i0 = 18903;
							break;
						case 4:
							i0 = 18904;
							break;
					}
					_thisActor.createOnePrivate(i0, "AiBeastfarmBeastEv", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading() * 182, _thisActor.c_ai0, 0, 0);
					addTimer(TIMER_despawn, 500);
				}
				else if(_thisActor.i_ai8 == 1 && Rnd.get(10000) < 1000)
				{
					switch(my_type)
					{
						case 1:
							i0 = 18901;
							break;
						case 2:
							i0 = 18902;
							break;
						case 3:
							i0 = 18903;
							break;
						case 4:
							i0 = 18904;
							break;
					}
					_thisActor.createOnePrivate(i0, "AiBeastfarmBeastEv", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading() * 182, _thisActor.c_ai0, 0, 0);
					addTimer(TIMER_despawn, 500);
				}
				else if(skill == SKILL_feed_item_s || skill == SKILL_feed_adena_s)
				{
					if(Rnd.get(10000) < PROB_beast_s)
					{
						if(skill == SKILL_feed_item_s)
						{
							switch(my_type)
							{
								case 1:
									i0 = 18878;
									break;
								case 2:
									i0 = 18885;
									break;
								case 3:
									i0 = 18892;
									break;
								case 4:
									i0 = 18899;
									break;
							}
							i1 = 1;
						}
						else if(skill == SKILL_feed_adena_s)
						{
							switch(my_type)
							{
								case 1:
									i0 = 18879;
									break;
								case 2:
									i0 = 18886;
									break;
								case 3:
									i0 = 18893;
									break;
								case 4:
									i0 = 18900;
									break;
							}
							i1 = 2;
						}
						_thisActor.createOnePrivate(i0, "AiBeastfarmBeast", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading() * 182, _thisActor.c_ai0, i1, 0);
						addTimer(TIMER_despawn, 500);
					}

					Functions.showSystemMessageFStr(attacker, 1801093);
					addAttackDesire(attacker, 1, 10000);
					_thisActor.c_ai0 = 0;
				}
				else if(skill == SKILL_feed_item_bress || skill == SKILL_feed_adena_bress)
				{
					if(Rnd.get(10000) < PROB_beast_bress)
					{
						DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(MAKER_tamed_beast);
						if(maker0 != null)
						{
							switch(my_type)
							{
								case 1:
									if(skill == SKILL_feed_item_bress)
									{
										i0 = 11;
									}
									else if(skill == SKILL_feed_adena_bress)
									{
										i0 = 12;
									}
									break;
								case 2:
									if(skill == SKILL_feed_item_bress)
									{
										i0 = 21;
									}
									else if(skill == SKILL_feed_adena_bress)
									{
										i0 = 22;
									}
									break;
								case 3:
									if(skill == SKILL_feed_item_bress)
									{
										i0 = 31;
									}
									else if(skill == SKILL_feed_adena_bress)
									{
										i0 = 32;
									}
									break;
								case 4:
									if(skill == SKILL_feed_item_bress)
									{
										i0 = 41;
									}
									else if(skill == SKILL_feed_adena_bress)
									{
										i0 = 42;
									}
									break;
							}

							maker0.onScriptEvent(21150002, getStoredIdFromCreature(attacker), i0);
							addTimer(TIMER_despawn, 500);
						}
					}

					Functions.showSystemMessageFStr(attacker, 1801093);
					addAttackDesire(attacker, 1, 10000);
					_thisActor.c_ai0 = 0;
				}
				else if(Rnd.get(10000) < PROB_direct_pet)
				{
					switch(my_type)
					{
						case 1:
							if(skill == SKILL_feed_item)
							{
								i0 = 11;
							}
							else if(skill == SKILL_feed_adena)
							{
								i0 = 12;
							}
							break;
						case 2:
							if(skill == SKILL_feed_item)
							{
								i0 = 21;
							}
							else if(skill == SKILL_feed_adena)
							{
								i0 = 22;
							}
							break;
						case 3:
							if(skill == SKILL_feed_item)
							{
								i0 = 31;
							}
							else if(skill == SKILL_feed_adena)
							{
								i0 = 32;
							}
							break;
						case 4:
							if(skill == SKILL_feed_item)
							{
								i0 = 41;
							}
							else if(skill == SKILL_feed_adena)
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
				else if(Rnd.get(10000) < PROB_direct_4th)
				{
					switch(my_type)
					{
						case 1:
							if(skill == SKILL_feed_item)
							{
								i0 = 18878;
								i1 = 1;
							}
							else if(skill == SKILL_feed_adena)
							{
								i0 = 18879;
								i1 = 2;
							}
							break;
						case 2:
							if(skill == SKILL_feed_item)
							{
								i0 = 18885;
								i1 = 1;
							}
							else if(skill == SKILL_feed_adena)
							{
								i0 = 18886;
								i1 = 2;
							}
							break;
						case 3:
							if(skill == SKILL_feed_item)
							{
								i0 = 18892;
								i1 = 1;
							}
							else if(skill == SKILL_feed_adena)
							{
								i0 = 18893;
								i1 = 2;
							}
							break;
						case 4:
							if(skill == SKILL_feed_item)
							{
								i0 = 18899;
								i1 = 1;
							}
							else if(skill == SKILL_feed_adena)
							{
								i0 = 18900;
								i1 = 2;
							}
							break;
					}
					_thisActor.createOnePrivate(i0, "AiBeastfarmBeast", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading() * 182, getStoredIdFromCreature(attacker), i1, 0);
					addTimer(TIMER_despawn, 500);
				}
				else if(skill == SKILL_feed_item)
				{
					switch(my_type)
					{
						case 1:
							i0 = 18874;
							break;
						case 2:
							i0 = 18881;
							break;
						case 3:
							i0 = 18888;
							break;
						case 4:
							i0 = 18895;
							break;
					}
					i1 = 1;
				}
				else if(skill == SKILL_feed_adena)
				{
					switch(my_type)
					{
						case 1:
							i0 = 18875;
							break;
						case 2:
							i0 = 18882;
							break;
						case 3:
							i0 = 18889;
							break;
						case 4:
							i0 = 18896;
							break;
					}
					i1 = 2;
				}
				_thisActor.createOnePrivate(i0, "AiBeastfarmBeast", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading() * 182, _thisActor.c_ai0, i1, 0);
				addTimer(TIMER_despawn, 500);
			}
		}
		else
		{
			addAttackDesire(attacker, 1, (damage * 2));
			if(CategoryManager.isInCategory(122, attacker.getNpcId()))
			{
				_thisActor.i_ai6++;
				if(_thisActor.i_ai6 == 5)
				{
					broadcastScriptEvent(21150001, getStoredIdFromCreature(attacker), (int) _thisActor.getLevel(), 1500);
					_thisActor.setAbilityItemDrop(false);
					_thisActor.doDie(null);
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		super.onEvtTimer(timerId, arg1, arg2);
		if(timerId == TIMER_despawn)
		{
			_thisActor.onDecay();
		}
		else if(timerId == TIMER_itsme)
		{
			broadcastScriptEvent(21150004, getStoredIdFromCreature(_thisActor), null, 500);
			int i0 = Rnd.get(100) + 200;
			addTimer(TIMER_itsme, i0 * 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 21150003)
		{
			broadcastScriptEvent(21150004, getStoredIdFromCreature(_thisActor), null, 1000);
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(killer != null)
		{
			if(CategoryManager.isInCategory(122, killer.getNpcId()))
			{
				broadcastScriptEvent(21150001, getStoredIdFromCreature(killer), (int) _thisActor.getLevel(), 1500);
			}
		}
	}
}
