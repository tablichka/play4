package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.commons.math.Rnd;

public class GuardianAngel extends DefaultAI
{
	public GuardianAngel(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		// Пока дерется, будет кричать. Вызов раз в секунду, так что не зафлудит :)
		if(Rnd.nextBoolean())
			Functions.npcSay(_thisActor, Say2C.ALL, "Эй ты, чучело! Отвали от этого ящика! Он принадлежит мне!");//TODO: Найти fString и заменить.
		else
			Functions.npcSay(_thisActor, Say2C.ALL, "Грр! Кто ты и почему пытаешься остановить меня?");//TODO: Найти fString и заменить.

		return super.thinkActive();
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		Functions.npcSay(_thisActor, Say2C.ALL, "Грр. Твой клинок... В моем сердце...");//TODO: Найти fString и заменить.
		super.onEvtDead(killer);
	}
}