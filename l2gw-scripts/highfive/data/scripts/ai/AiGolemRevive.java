package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 09.09.11 3:01
 */
public class AiGolemRevive extends WarriorUseSkill
{
	public int Privates01_sil = 22802;
	public String Privates01_ai = "base.WarriorUseSkill";
	public int Privates02_sil = 22803;
	public String Privates02_ai = "base.WarriorUseSkill";

	public AiGolemRevive(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(killer != null)
		{
			if(killer.isPlayer() && CategoryManager.isInCategory(0, killer.getActiveClass()))
			{
				_thisActor.createOnePrivate(Privates01_sil, Privates01_ai, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 1000, killer.getStoredId(), 0);
			}

			_thisActor.createOnePrivate(Privates02_sil, Privates02_ai, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 1000, killer.getStoredId(), 0);
		}
		super.onEvtDead(killer);
	}
}
