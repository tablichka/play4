package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 15.12.11 15:58
 */
public class AiImmoSbossBasic extends AiImmoBasic
{
	public int TM_WORMWAKE = 78009;
	public int TIME_WORMWAKE = 30;
	public L2Skill Skill_sbossdef = SkillTable.getInstance().getInfo(388628481);
	public int reward_sboss = 13797;
	public int reward_quantity_sboss = 20;

	public AiImmoSbossBasic(L2Character actor)
	{
		super(actor);
		type = "solo_boss_default";
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(TM_WORMWAKE, TIME_WORMWAKE * 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_WORMWAKE)
		{
			L2Character c0 = _thisActor.getMostHated();
			if(c0 != null && _thisActor.getHate(c0) > 0)
			{
				if(c0 != _thisActor)
				{
					if(SkillTable.getAbnormalLevel(c0, 384696321) <= 8 || SkillTable.getAbnormalLevel(c0, 384761859) <= 8 || SkillTable.getAbnormalLevel(c0, 384827395) <= 8 || SkillTable.getAbnormalLevel(c0, 384434177) <= 8 || SkillTable.getAbnormalLevel(c0, 384565251) <= 8 || SkillTable.getAbnormalLevel(c0, 384630787) <= 8 || SkillTable.getAbnormalLevel(c0, 384499715) <= 8 || SkillTable.getAbnormalLevel(c0, 384892929) <= 2 || SkillTable.getAbnormalLevel(c0, 384958465) <= 2)
					{
						if(SkillTable.isMagic(385155074) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
							}
						}
						else if(SkillTable.isMagic(385155074) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(385155074) < _thisActor.getCurrentMp() && SkillTable.hpConsume(385155074) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(385155074))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (385155074 / 65536) + " skill");
							}
							addUseSkillDesire(c0, SkillTable.getInstance().getInfo(385155074), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "no skill condition!");
						}
					}
					else if(SkillTable.getAbnormalLevel(c0, 384696321) <= 9 || SkillTable.getAbnormalLevel(c0, 384761859) <= 9 || SkillTable.getAbnormalLevel(c0, 384827395) <= 9 || SkillTable.getAbnormalLevel(c0, 384434177) <= 9 || SkillTable.getAbnormalLevel(c0, 384565251) <= 9 || SkillTable.getAbnormalLevel(c0, 384630787) <= 9 || SkillTable.getAbnormalLevel(c0, 384499715) <= 10 || SkillTable.getAbnormalLevel(c0, 384892929) <= 4 || SkillTable.getAbnormalLevel(c0, 384958465) <= 4)
					{
						if(SkillTable.isMagic(385155075) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
							}
						}
						else if(SkillTable.isMagic(385155075) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(385155075) < _thisActor.getCurrentMp() && SkillTable.hpConsume(385155075) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(385155075))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (385155075 / 65536) + " skill");
							}
							addUseSkillDesire(c0, SkillTable.getInstance().getInfo(385155075), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, " no skill condition!");
						}
					}
					else if(SkillTable.getAbnormalLevel(c0, 384696321) <= 10 || SkillTable.getAbnormalLevel(c0, 384761859) <= 10 || SkillTable.getAbnormalLevel(c0, 384827395) <= 10 || SkillTable.getAbnormalLevel(c0, 384434177) <= 10 || SkillTable.getAbnormalLevel(c0, 384565251) <= 10 || SkillTable.getAbnormalLevel(c0, 384630787) <= 10 || SkillTable.getAbnormalLevel(c0, 384499715) <= 10 || SkillTable.getAbnormalLevel(c0, 384892929) <= 5 || SkillTable.getAbnormalLevel(c0, 384958465) <= 5)
					{
						if(SkillTable.isMagic(385155076) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
							}
						}
						else if(SkillTable.isMagic(385155076) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
							}
						}
						else if(SkillTable.mpConsume(385155076) < _thisActor.getCurrentMp() && SkillTable.hpConsume(385155076) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(385155076))
						{
							if(debug)
							{
								Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (385155076 / 65536) + " skill");
							}
							addUseSkillDesire(c0, SkillTable.getInstance().getInfo(385155076), 0, 1, (long) (1000000 * UseSkill_BoostValue));
						}
						else if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, " no skill condition!");
						}
					}
					else if(SkillTable.isMagic(385155073) == 0 && (SkillTable.getAbnormalLevel(_thisActor, 23134209) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Phys defence!");
						}
					}
					else if(SkillTable.isMagic(385155073) == 1 && (SkillTable.getAbnormalLevel(_thisActor, 69730305) > 0 || SkillTable.getAbnormalLevel(_thisActor, 87556097) > 0))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Magic block!");
						}
					}
					else if(SkillTable.mpConsume(385155073) < _thisActor.getCurrentMp() && SkillTable.hpConsume(385155073) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(385155073))
					{
						if(debug)
						{
							Functions.npcSay(_thisActor, Say2C.ALL, "Cast " + (385155073 / 65536) + " skill");
						}
						addUseSkillDesire(c0, SkillTable.getInstance().getInfo(385155073), 0, 1, (long) (1000000 * UseSkill_BoostValue));
					}
					else if(debug)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, " no skill condition!");
					}
				}
			}
			addTimer(TM_WORMWAKE, TIME_WORMWAKE * 1000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(_thisActor.getNpcId() != 25634)
		{
			int i0 = (reward_quantity_sboss / 2);
			if(killer != null)
			{
				L2Player c0 = killer.getPlayer();
				if(c0 != null)
					_thisActor.dropItem(c0, reward_sboss, (i0 + Rnd.get(i0)) + 1);
			}
		}
	}
}
