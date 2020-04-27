package events.tournament;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class Tournament_ai extends DefaultAI
{
	private Location[] points = new Location[9];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Tournament_ai(L2Character actor)
	{
		super(actor);
		points[0] = new Location(82545, 148600, -3505, -3395);
		points[1] = new Location(82410, 148277, -3505, -3395);
		points[2] = new Location(82101, 148117, -3505, -3395);
		points[3] = new Location(81673, 148070, -3505, -3395);
		points[4] = new Location(81453, 148378, -3505, -3395);
		points[5] = new Location(81432, 148792, -3505, -3395);
		points[6] = new Location(81702, 149114, -3505, -3395);
		points[7] = new Location(82115, 149111, -3505, -3395);
		points[8] = new Location(82440, 148882, -3505, -3395);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_def_think)
		{
			doTask();
			return true;
		}

		if(System.currentTimeMillis() > wait_timeout && (current_point > -1 || Rnd.chance(5)))
		{
			if(!wait)
				switch(current_point)
				{
					// Это паузы на определеных точках в милисекундах
					// (case 3:) Это номер точки на которой делать паузу
					// npcSayInRange - сказать в Зону
					// npcSayToAll - Сказать всем
					// npcSayToPlayer - Сказать определеному игроку
					// npcShout - просто сказать всем видимым
					// пользовать вот так например
					// Functions.npcShout(_thisActor, "Всем лежать, у меня бомба!");
					// Использывание скиллов //
					// _thisActor.broadcastPacket(new MagicSkillUse(_thisActor, _thisActor, _skillId, skilllevel, castTime, 0));
					// На примере  2025  Large Firework //
					//_thisActor.broadcastPacket(new MagicSkillUse(_thisActor, _thisActor, 2025, 1, 500, 0));
					case 0:
						wait_timeout = System.currentTimeMillis() + 60000;
						Functions.npcSay(_thisActor, Say2C.ALL, "Регистрация на Турнир !!!!");
						wait = true;
						return true;

					case 2:
						wait_timeout = System.currentTimeMillis() + 60000;
						Functions.npcSay(_thisActor, Say2C.ALL, "Регистрация на Турнир !!!!");
						wait = true;
						return true;

					case 4:
						wait_timeout = System.currentTimeMillis() + 60000;
						Functions.npcSay(_thisActor, Say2C.ALL, "Регистрация на Турнир !!!");
						wait = true;
						return true;
					case 6:
						wait_timeout = System.currentTimeMillis() + 60000;
						Functions.npcSay(_thisActor, Say2C.ALL, "Регистрация на Турнир !!!!");
						wait = true;
						return true;
					case 8:
						wait_timeout = System.currentTimeMillis() + 60000;
						Functions.npcSay(_thisActor, Say2C.ALL, "Регистрация на Турнир !!!!");
						wait = true;
						return true;
				}

			wait_timeout = 0;
			wait = false;

			if(current_point >= points.length - 1)
				current_point = -1;

			current_point++;

			// Добавить новое задание
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.loc = points[current_point];
			_task_list.add(task);
			_def_think = true;
			return true;
		}

		return randomAnimation();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}
