package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author rage
 * @date 27.11.2010 16:58:31
 */
public class SSQBlocker extends DefaultAI
{
	private int id_number;
	private static final L2Skill _skill1 = SkillTable.getInstance().getInfo(5980, 3);

	public SSQBlocker(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		id_number = getInt("id_number", 0);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || !attacker.isPlayer())
			return;

		if(_thisActor.i_ai0 == 0 && _thisActor.getCurrentHp() - damage <= 10)
		{
			_thisActor.i_ai0 = 1;
			L2Player player = attacker.getPlayer();
			player.addItem("Quest", 13846, 1, _thisActor, true);
			_thisActor.setRHandId(15281);
			_thisActor.setLHandId(15281);
			_thisActor.updateAbnormalEffect();
			_thisActor.broadcastPacket(Msg.THE_SEALING_DEVICE_GLITTERS_AND_MOVES_ACTIVATION_COMPLETE_NORMALLY);
			broadcastScriptEvent(100, id_number, player.getObjectId(), 3000);
		}

		if(Rnd.chance(50) && !_thisActor.isCastingNow())
			_thisActor.doCast(_skill1, _thisActor, false);
	}
}
