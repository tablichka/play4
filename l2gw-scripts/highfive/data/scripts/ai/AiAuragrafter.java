package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 06.09.11 22:48
 */
public class AiAuragrafter extends DefaultAI
{
	public long Max_Desire = 1000000000000000000L;
	public int TID_LIFETIME = 78001;
	public int TIME_LIFETIME = 3;
	public L2Skill aura_hp01 = SkillTable.getInstance().getInfo(434176001);
	public L2Skill aura_hp02 = SkillTable.getInstance().getInfo(434241538);
	public L2Skill aura_hp03 = SkillTable.getInstance().getInfo(434307075);
	public L2Skill aura_mp01 = SkillTable.getInstance().getInfo(434372609);
	public L2Skill aura_mp02 = SkillTable.getInstance().getInfo(434438146);
	public L2Skill aura_mp03 = SkillTable.getInstance().getInfo(434503683);
	public L2Skill aura_melee01 = SkillTable.getInstance().getInfo(434569217);
	public L2Skill aura_melee02 = SkillTable.getInstance().getInfo(434700289);
	public L2Skill aura_melee03 = SkillTable.getInstance().getInfo(434831361);
	public L2Skill aura_melee04 = SkillTable.getInstance().getInfo(434962433);
	public L2Skill aura_melee05 = SkillTable.getInstance().getInfo(435093505);
	public L2Skill aura_bow01 = SkillTable.getInstance().getInfo(437387265);
	public L2Skill aura_caster01 = SkillTable.getInstance().getInfo(434634753);
	public L2Skill aura_caster02 = SkillTable.getInstance().getInfo(434765825);
	public L2Skill aura_special01 = SkillTable.getInstance().getInfo(434896897);
	public L2Skill aura_special02 = SkillTable.getInstance().getInfo(435027969);
	public L2Skill aura_special03 = SkillTable.getInstance().getInfo(435159041);

	public AiAuragrafter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if( debug )
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "spawned");
		}
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param1);
		if( c0 != null )
		{
			if( debug )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, c0.getName());
			}
			int i0 = Rnd.get(100);
			if( i0 <= 42 )
			{
				int i1 = Rnd.get(100);
				if( i1 <= 7 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_hp03");
					}
					addUseSkillDesire(c0, aura_hp03, 0, 1, Max_Desire);
				}
				else if( i1 <= 45 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_hp02");
					}
					addUseSkillDesire(c0, aura_hp02, 0, 1, Max_Desire);
				}
				else if( debug )
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "aura_hp01");
				}
				addUseSkillDesire(c0, aura_hp01, 0, 1, Max_Desire);
			}
			if( i0 <= 11 )
			{
				int i1 = Rnd.get(100);
				if( i1 <= 8 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_mp03");
					}
					addUseSkillDesire(c0, aura_mp03, 0, 1, Max_Desire);
				}
				else if( i1 <= 60 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_mp02");
					}
					addUseSkillDesire(c0, aura_mp02, 0, 1, Max_Desire);
				}
				else if( debug )
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "aura_mp01");
				}
				addUseSkillDesire(c0, aura_mp01, 0, 1, Max_Desire);
			}
			if( i0 <= 25 )
			{
				int i1 = Rnd.get(100);
				if( i1 <= 20 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_melee05");
					}
					_thisActor.altUseSkill(aura_melee05, c0);
				}
				else if( i1 <= 40 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_bow01");
					}
					_thisActor.altUseSkill(aura_bow01, c0);
				}
				else if( i1 <= 60 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_melee03");
					}
					_thisActor.altUseSkill(aura_melee03, c0);
				}
				else if( i1 <= 80 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_melee02");
					}
					_thisActor.altUseSkill(aura_melee02, c0);
				}
				else if( debug )
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "aura_melee01");
				}
				_thisActor.altUseSkill(aura_melee01, c0);
			}
			if( i0 <= 10 )
			{
				if( debug )
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "aura_bow01");
				}
				_thisActor.altUseSkill(aura_bow01, c0);
			}
			if( i0 <= 1 )
			{
				int i1 = Rnd.get(100);
				if( i1 <= 34 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_melee01~03");
					}
					_thisActor.altUseSkill(aura_melee01, c0);
					_thisActor.altUseSkill(aura_melee02, c0);
					_thisActor.altUseSkill(aura_melee03, c0);
				}
				else if( i1 <= 67 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_bow01");
					}
					_thisActor.altUseSkill(aura_bow01, c0);
				}
				else if( debug )
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "aura_hp03, aura_mp03");
				}
				addUseSkillDesire(c0, aura_hp03, 0, 1, Max_Desire);
				addUseSkillDesire(c0, aura_mp03, 0, 1, Max_Desire);
			}
			if( i0 <= 11 )
			{
				int i1 = Rnd.get(100);
				if( i1 <= 3 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_special03");
					}
					_thisActor.altUseSkill(aura_special03, c0);
				}
				else if( i1 <= 6 )
				{
					if( debug )
					{
						Functions.npcSay(_thisActor, Say2C.ALL, "aura_special02");
					}
					_thisActor.altUseSkill(aura_special02, c0);
				}
				else if( debug )
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "aura_special01");
				}
				_thisActor.altUseSkill(aura_special01, c0);
			}
		}
		addTimer(TID_LIFETIME, ( TIME_LIFETIME * 1000 ));
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == TID_LIFETIME )
		{
			if( debug )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "despawned");
			}
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if( debug )
		{
			Functions.npcSay(_thisActor, Say2C.ALL, "USE_SKILL_FINISHED:" + skill.getId());
		}
	}
}
