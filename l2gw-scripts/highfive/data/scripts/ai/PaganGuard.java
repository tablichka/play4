package ai;

import ru.l2gw.gameserver.ai.Balanced;
import ru.l2gw.gameserver.model.L2Character;

/**
 * AI охраны в Pagan Temple.
 * Не умеют ходить
 * Видит всех в режиме Silent Move
 * Бьют физ атакой игроков, подошедших на расстояние удара
 * Бьют магией, если были атакованы
 * Социальны к собратьям, помогают атакуя магией
 * В случае, если игрок вышел за пределы агро радиуса прекращают использовать дальнобойную магию
 *
 * @author SYS
 */
public class PaganGuard extends Balanced
{
	public PaganGuard(L2Character actor)
	{
		super(actor);
		_actor.setImobilised(true);
	}

	@Override
	protected boolean checkTarget(L2Character target)
	{
		if(!_thisActor.isInRange(target, _thisActor.getAggroRange()))
		{
			_thisActor.stopHate(target);
			return false;
		}
		return super.checkTarget(target);
	}

	@Override
	protected boolean isSilent(L2Character target)
	{
		return false;
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}