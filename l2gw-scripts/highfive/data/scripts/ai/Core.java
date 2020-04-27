package ai;

import npc.model.CoreCubeInstance;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * AI боса Core:<br> - Бубнит при атаке и смерти.<br> - При смерти играет музыку.<br> - При смерти спаунит обратные порталы, которые удаляются через 15 минут
 *
 * @author SYS
 */
public class Core extends Fighter
{
	private boolean _firstTimeAttacked = true;
	private static final int TELEPORTATION_CUBIC_ID = 31842;
	private static final Location CUBIC_1_POSITION = new Location(16502, 110165, -6394, 0);
	private static final Location CUBIC_2_POSITION = new Location(18948, 110165, -6394, 0);

	public Core(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_firstTimeAttacked)
		{
			_firstTimeAttacked = false;
			Functions.npcSay(_thisActor, Say2C.ALL, 1000001);
			Functions.npcSay(_thisActor, Say2C.ALL, 1000002);
		}
		else if(Rnd.chance(1))
			Functions.npcSay(_thisActor, Say2C.ALL, 1000003);

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_thisActor.broadcastPacket(new PlaySound(1, "BS02_D", 1, 0, _thisActor.getLoc()));
		Functions.npcSay(_thisActor, Say2C.ALL, 1000004);
		Functions.npcSay(_thisActor, Say2C.ALL, 1000005);
		Functions.npcSay(_thisActor, Say2C.ALL, 1000006);

		try
		{
			L2NpcTemplate template = NpcTable.getTemplate(TELEPORTATION_CUBIC_ID);
			CoreCubeInstance cube1 = new CoreCubeInstance(IdFactory.getInstance().getNextId(), template, 0, 0, 0, 0);
			cube1.spawnMe(CUBIC_1_POSITION);
			cube1.onSpawn();

			CoreCubeInstance cube2 = new CoreCubeInstance(IdFactory.getInstance().getNextId(), template, 0, 0, 0, 0);
			cube2.spawnMe(CUBIC_2_POSITION);
			cube2.onSpawn();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		_firstTimeAttacked = true;
		super.onEvtDead(killer);
	}
}