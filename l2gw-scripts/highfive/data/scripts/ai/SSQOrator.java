package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.SevenSigns;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author rage
 * @date 10.08.2010 14:30:50
 */
public class SSQOrator extends DefaultAI
{
	private long _nextSpeech;
	private static final L2Skill _fighter1 = SkillTable.getInstance().getInfo(4364, 1);
	private static final L2Skill _fighter2 = SkillTable.getInstance().getInfo(4364, 2);
	private static final L2Skill _mage1 = SkillTable.getInstance().getInfo(4365, 1);
	private static final L2Skill _mage2 = SkillTable.getInstance().getInfo(4365, 2);
	private static final int[] _messages = new int[]{1000303, 1000421, 1000422, 1000423};

	private static int _winningCabal;

	public SSQOrator(L2Character actor)
	{
		super(actor);
		_nextSpeech = System.currentTimeMillis() + Rnd.get(30) * 1000;
		_winningCabal = SevenSigns.getInstance().getCabalWinner();
	}

	@Override
	protected boolean thinkActive()
	{
		if(_nextSpeech < System.currentTimeMillis())
		{
			_nextSpeech = System.currentTimeMillis() + (180 + Rnd.get(300)) * 1000;
			Functions.npcSay(_thisActor, Say2C.ALL, _messages[Rnd.get(_messages.length)]);
		}

		if(_winningCabal != SevenSigns.CABAL_NULL)
			for(L2Player player : _thisActor.getAroundLivePlayers(300))
			{
				int playerCabal = SevenSigns.getInstance().getPlayerCabal(player);
				if(!player.isInOfflineMode() && playerCabal == _winningCabal)
				{
					int i0 = Rnd.get(100);
					int i1 = Rnd.get(1000);
					if(player.isMageClass())
					{
						L2Effect effect = player.getEffectBySkillId(_mage1.getId());
						if(effect == null)
						{
							if(i1 < 1)
								Functions.npcSay(_thisActor, Say2C.ALL, 1000306);
							_thisActor.altUseSkill(_mage1, player);
						}
						else if(i0 < 5)
						{
							if(i1 < 500)
								Functions.npcSay(_thisActor, Say2C.ALL, 1000424, player.getName());
							_thisActor.altUseSkill(_mage2, player);
						}
					}
					else
					{
						L2Effect effect = player.getEffectBySkillId(_fighter1.getId());
						if(effect == null)
						{
							if(i1 < 1)
								Functions.npcSay(_thisActor, Say2C.ALL, 1000426);
							_thisActor.altUseSkill(_fighter1, player);
						}
						else if(i0 < 5)
						{
							if(i1 < 500)
								Functions.npcSay(_thisActor, Say2C.ALL, 1000425, player.getName());
							_thisActor.altUseSkill(_fighter2, player);
						}
					}
				}
			}

		return true;
	}
}
