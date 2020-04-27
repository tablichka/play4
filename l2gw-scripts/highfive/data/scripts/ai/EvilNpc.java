package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

public class EvilNpc extends DefaultAI
{
	private long _lastAction;
	private static String[] _txt = {
			"что ты меня трогаешь? Не нравлюсь? На себя в зеркало посмотри! Крокодил!!!",
			"а с разбегу об стенку замка? Слабо?",
			"сейчас придет начальник и поломает тебе ногу или руку!",
			"отреж себе голову, идиот!",
			"за нападение ты можешь сесть в тюрьму!",
			"знаешь, я тоже дратся умею, так что не выпендривайся!" };

	public EvilNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || attacker.getPlayer() == null)
			return;

		_actor.startAttackStanceTask();

		// Ругаемся и кастуем скилл не чаще, чем раз в 3 секунды
		if(System.currentTimeMillis() - _lastAction > 3000)
		{
			int chance = Rnd.get(0, 100);
			if(chance < 2)
			{
				attacker.getPlayer().setKarma(attacker.getPlayer().getKarma() + 5);
				attacker.sendChanges();
			}
			else if(chance < 4)
				_actor.doCast(SkillTable.getInstance().getInfo(4578, 1), attacker, true); // Petrification
			else
				_actor.doCast(SkillTable.getInstance().getInfo(4185, 7), attacker, true); // Sleep

			Functions.npcSay(_thisActor, Say2C.ALL, attacker.getName() + ", " + _txt[Rnd.get(_txt.length)]);
			_lastAction = System.currentTimeMillis();
		}
	}
}