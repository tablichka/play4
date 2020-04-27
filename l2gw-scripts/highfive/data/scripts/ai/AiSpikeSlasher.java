package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 03.09.11 15:33
 */
public class AiSpikeSlasher extends DetectPartyWarrior
{
	public L2Skill summonSkill = SkillTable.getInstance().getInfo(448331777);
	public int isChasePC = 2500;

	public AiSpikeSlasher(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(!_thisActor.isInRange(_thisActor.getSpawnedLoc(), isChasePC))
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc());

		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.60 && _thisActor.i_ai2 == 0)
		{
			_thisActor.i_ai2 = 1;
			addUseSkillDesire(_thisActor, summonSkill, 1, 0, 99999999900000000L);
			int i1 = 3 + Rnd.get(3);
			Location pos;
			for(int i = 0; i < i1; i++)
			{
				pos = Location.coordsRandomize(_thisActor, 200);
				_thisActor.createOnePrivate(25733, null, 0, 0, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0, 0);
			}
		}
		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.20 && _thisActor.i_ai3 == 0)
		{
			_thisActor.i_ai3 = 1;
			addUseSkillDesire(_thisActor, summonSkill, 1, 0, 99999999900000000L);
			int i1 = 3 + Rnd.get(3);
			Location pos;
			for(int i = 0; i < i1; i++)
			{
				pos = Location.coordsRandomize(_thisActor, 200);
				_thisActor.createOnePrivate(25733, null, 0, 0, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0, 0);
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}
