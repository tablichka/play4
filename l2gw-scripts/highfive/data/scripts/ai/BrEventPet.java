package ai;

import javolution.util.FastMap;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.L2SummonAI;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2PetInstance;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.SkillTable;

import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 09.02.11 16:21
 */
public class BrEventPet extends L2SummonAI
{
	private L2PetInstance _thisPet;
	private boolean active;

	private static final int buff_time = 10;
	private static final int heal_delay = 3;
	private static final int buffControl = 5771;
	private static final int s_br_herb_rose_red9 = 22158;
	private static final L2Skill s_br_pet_rose_vp = SkillTable.getInstance().getInfo(23166, 1);

	private int i_ai0 = 1;
	private int moveAroundSocial;
	private int buff_type;
	private int heal_type;
	private int num_consume_item;
	private L2Skill debuff;
	private FastMap<Integer, ScheduledFuture<?>> _timers = new FastMap<Integer, ScheduledFuture<?>>().shared();

	private static final L2Skill[][] buffs =
			{
					{
							SkillTable.getInstance().getInfo(5189, 6),
							SkillTable.getInstance().getInfo(5192, 2),
							SkillTable.getInstance().getInfo(5586, 3),
							SkillTable.getInstance().getInfo(5587, 3),
							SkillTable.getInstance().getInfo(5588, 3),
							SkillTable.getInstance().getInfo(5589, 3),
							SkillTable.getInstance().getInfo(5189, 2),
							SkillTable.getInstance().getInfo(5187, 4)
					},
					{
							SkillTable.getInstance().getInfo(5190, 6),
							SkillTable.getInstance().getInfo(5192, 2),
							SkillTable.getInstance().getInfo(5587, 3),
							SkillTable.getInstance().getInfo(5194, 3),
							SkillTable.getInstance().getInfo(5193, 3)
					},
					{
							SkillTable.getInstance().getInfo(5189, 6),
							SkillTable.getInstance().getInfo(5192, 2),
							SkillTable.getInstance().getInfo(5190, 6),
							SkillTable.getInstance().getInfo(5587, 3),
							SkillTable.getInstance().getInfo(5987, 1),
							SkillTable.getInstance().getInfo(5988, 1)
					}
			};

	public BrEventPet(L2Summon actor)
	{
		super(actor);
		_thisPet = (L2PetInstance) actor;
		active = false;
		moveAroundSocial = _thisPet.getTemplate().getAIParams() != null ? _thisPet.getTemplate().getAIParams().getInteger("MoveAroundSocial", 0) : 0;
		buff_type = _thisPet.getTemplate().getAIParams() != null ? _thisPet.getTemplate().getAIParams().getInteger("buff_type", 0) : 0;
		heal_type = _thisPet.getTemplate().getAIParams() != null ? _thisPet.getTemplate().getAIParams().getInteger("heal_type", 1) : 1;
		debuff = SkillTable.getInstance().getInfo(_thisPet.getNpcId() < 1568 ? 23170 : 23169, 1);
		num_consume_item = _thisPet.getNpcId() < 1568 ? 3 : 10;
		startAITask();
	}

	@Override
	public void stopAITask()
	{
		active = false;
	}

	@Override
	public void startAITask()
	{
		if(!active)
		{
			active = true;
			addTimer(20100214, 3000);
			addTimer(20100314, 30000);
			addTimer(2001, buff_time);
			addTimer(2002, heal_delay * 1000);
			addTimer(1671, 10000);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(!active)
			return;

		if(timerId == 1671)
		{
			if(moveAroundSocial > 0 && Rnd.chance(moveAroundSocial) && _thisPet.getCurrentHp() > _thisPet.getMaxHp() * 0.4 && !_thisPet.isDead() && !_thisPet.isMoving && !_thisPet.isInCombat() && !_thisPet.isCastingNow())
				_thisPet.broadcastPacket(new SocialAction(_thisPet.getObjectId(), 1));
			addTimer(1671, 10000);
		}
		else if(timerId == 2001)
		{
			L2Player owner = _thisPet.getPlayer();
			if(i_ai0 == 1 && owner != null && !_thisPet.isCastingNow() && _thisPet.getEffectBySkillId(buffControl) == null)
			{
				L2Skill[] buff = buffs[buff_type];
				for(L2Skill skill : buff)
					if(!_thisPet.isSkillDisabled(skill.getId()) && skill.getMpConsume() < _thisPet.getCurrentMp() && skill.getHpConsume() < _thisPet.getCurrentHp() && owner.getEffectByAbnormalType(skill.getAbnormalTypes().get(0)) == null)
					{
						if(_thisPet.isInRange(owner, skill.getCastRange()) || _thisPet.getFollowStatus())
							Cast(skill, owner, null, false, false);
						break;
					}
			}
			addTimer(2001, buff_time * 1000);
		}
		else if(timerId == 2002)
		{
			if(!_thisPet.isInCombat())
			{
				addTimer(2002, heal_delay * 1000);
				return;
			}

			L2Player owner = _thisPet.getPlayer();
			if(i_ai0 == 1 && owner != null && !_thisPet.isCastingNow() && _thisPet.getEffectBySkillId(buffControl) == null)
			{
				if(heal_type == 0)
				{
					if(owner.getCurrentHp() < owner.getMaxHp() * 0.30)
					{
						L2Skill heal2 = SkillTable.getInstance().getInfo(5590, Math.min(_thisPet.getLevel() / 10 + 1, 8));
						if(!_thisPet.isSkillDisabled(heal2.getId()) && heal2.getMpConsume() < _thisPet.getCurrentMp() && heal2.getHpConsume() < _thisPet.getCurrentHp())
						{
							if(_thisPet.isInRange(owner, heal2.getCastRange()) || _thisPet.getFollowStatus())
								Cast(heal2, owner, null, false, false);
						}
					}
					else if(owner.getCurrentMp() < owner.getMaxMp() * 0.60)
					{
						L2Skill heal1 = SkillTable.getInstance().getInfo(5200, _thisPet.getLevel() < 50 ? 1 : _thisPet.getLevel() < 60 ? 3 : 4);
						if(!_thisPet.isSkillDisabled(heal1.getId()) && heal1.getMpConsume() < _thisPet.getCurrentMp() && heal1.getHpConsume() < _thisPet.getCurrentHp())
						{
							if(_thisPet.isInRange(owner, heal1.getCastRange()) || _thisPet.getFollowStatus())
								Cast(heal1, owner, null, false, false);
						}
					}
				}
				else if(heal_type == 1)
				{
					if(owner.getCurrentHp() < owner.getMaxHp() * 0.70 && owner.getCurrentHp() >= owner.getMaxHp() * 0.30)
					{
						L2Skill heal1 = SkillTable.getInstance().getInfo(5195, Math.min(_thisPet.getLevel() / 10 + 1, 8));
						if(!_thisPet.isSkillDisabled(heal1.getId()) && heal1.getMpConsume() < _thisPet.getCurrentMp() && heal1.getHpConsume() < _thisPet.getCurrentHp())
						{
							if(_thisPet.isInRange(owner, heal1.getCastRange()) || _thisPet.getFollowStatus())
								Cast(heal1, owner, null, false, false);
						}
					}
					else if(owner.getCurrentHp() < owner.getMaxHp() * 0.30)
					{
						L2Skill heal2 = SkillTable.getInstance().getInfo(5590, Math.min(_thisPet.getLevel() / 10 + 1, 8));
						if(!_thisPet.isSkillDisabled(heal2.getId()) && heal2.getMpConsume() < _thisPet.getCurrentMp() && heal2.getHpConsume() < _thisPet.getCurrentHp())
						{
							if(_thisPet.isInRange(owner, heal2.getCastRange()) || _thisPet.getFollowStatus())
								Cast(heal2, owner, null, false, false);
						}
					}
				}
			}
			addTimer(2002, heal_delay * 1000);
		}
		else if(timerId == 20100214)
		{
			L2Player owner = _thisPet.getPlayer();
			if(owner != null)
			{
				L2Effect effect = owner.getEffectBySkillId(s_br_herb_rose_red9);
				if(effect != null && effect.getSkillLevel() == 10)
					_thisPet.altUseSkill(s_br_pet_rose_vp, owner);
			}
			addTimer(20100214, 5000);
		}
		else if(timerId == 20100314)
		{
			if(moveAroundSocial > 0 && Rnd.chance(moveAroundSocial) && _thisPet.getCurrentHp() > _thisPet.getMaxHp() * 0.4 && !_thisPet.isDead() && !_thisPet.isMoving && !_thisPet.isInCombat() && !_thisPet.isCastingNow())
				_thisPet.broadcastPacket(new SocialAction(_thisPet.getObjectId(), 2));
			addTimer(20100214, 30000 + Rnd.get(5000));
		}
	}

	@Override
	public void Cast(L2Skill skill, L2Character target, L2ItemInstance usedItem, boolean forceUse, boolean dontMove)
	{
		if(skill.getId() == 6054)
			i_ai0 = i_ai0 == 1 ? 0 : 1;
		else if(skill.getId() == 23168 || skill.getId() == 23167)
		{
			L2Player owner = _thisPet.getPlayer();
			if(owner != null)
			{
				if(owner.getItemCountByItemId(20904) >= num_consume_item)
				{
					if(owner.getEffectBySkill(debuff) != null)
					{
						owner.sendPacket(new SystemMessage(2396));
						return;
					}

					super.Cast(skill, owner, usedItem, forceUse, dontMove);
					owner.destroyItemByItemId("Consume", 20904, num_consume_item, _thisPet, true);
					debuff.applyEffects(_thisPet, owner, false);
				}
				else
					owner.sendPacket(new SystemMessage(2156));
			}

			return;
		}

		super.Cast(skill, target, usedItem, forceUse, dontMove);
	}

	@Override
	public void addTimer(int timerId, Object arg1, Object arg2, long delay)
	{
		if(_timers.containsKey(timerId))
		{
			ScheduledFuture<?> task = _timers.get(timerId);
			if(task != null)
				task.cancel(true);
		}

		_timers.put(timerId, ThreadPoolManager.getInstance().scheduleAi(new Timer(timerId, arg1, arg2, delay), delay, false));
	}
}
