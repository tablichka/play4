package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 22.10.2010 11:40:02
 */
public class HBOutpostCaptain extends Fighter
{
	private static final L2Skill _paralize = SkillTable.getInstance().getInfo(5306, 1);
	private long _nextSpeech;
	private long _nextAttack;

	public HBOutpostCaptain(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_thisActor.getAroundLivePlayers(500).size() > 0 && _nextSpeech < System.currentTimeMillis())
		{
			_nextSpeech = System.currentTimeMillis() + 15000;
			Functions.npcSayCustom(_thisActor, Say2C.ALL, "scripts.ai.HBOutpostCaptain1", null);//TODO: Найти fString и заменить.
			return true;
		}

		GArray<L2Player> players = _thisActor.getAroundLivePlayers(150);
		if(players.size() > 0 && _nextAttack < System.currentTimeMillis())
		{
			_nextAttack = System.currentTimeMillis() + 120000;
			Functions.npcSayCustom(_thisActor, Say2C.ALL, "scripts.ai.HBOutpostCaptain2", null);//TODO: Найти fString и заменить.
			L2Player player = players.get(Rnd.get(players.size()));
			_thisActor.setTarget(player);
			_thisActor.doCast(_paralize, player, false);
			_thisActor.addDamageHate(player, 0, 10000);
			for(L2NpcInstance mob : _thisActor.getKnownNpc(1200))
				if(!mob.isDead() && mob.getNpcId() >= 22355 && mob.getNpcId() <= 22358)
				{
					mob.addDamageHate(player, 0, 10000);
					mob.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, player);
				}

		}

		return super.thinkActive();
	}
}
