package events.MadnessOfDwarfs;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;

import java.util.ArrayList;

public class MadnessOfDwarfs_ai extends DefaultAI
{
	private long _lasttime = 0;

	public static enum SayType
	{
		ALL,
		SHOUT,
		PM;
	}

	public MadnessOfDwarfs_ai(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{

		L2NpcInstance actor = getActor();
		if(actor == null || actor.isDead())
			return true;

		if(_def_think)
		{
			doTask();
			return true;
		}

		long timeout = System.currentTimeMillis() - _lasttime;

		if(timeout > 60000)
		{
			_lasttime = System.currentTimeMillis();
			String msg = GetTextMessage(MadnessOfDwarfs._isInWar);
			SayMessage(msg, SayType.ALL);
			return true;
		}

		if(randomAnimation())
			return true;

		return true;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		L2NpcInstance actor = getActor();
		if(actor == null || actor.isDead() || attacker == null)
			return;

		L2Player player = attacker.getPlayer();
		if(player != null)
		{
			// Бить Главного менеджера нельзя )).
			SayMessage(player.getName() + ": Зря ты это сделал", SayType.SHOUT);
			player.broadcastPacket(new MagicSkillUse(actor, player, 2500, 1, 10, 10));
			MadnessOfDwarfs.lossSiege(attacker, getPlayersInRegion());

			//player.doDie(thisActor);
		}
		else
			doLossSiege(attacker);
	}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}

	private void doLossSiege(L2Character attacker)
	{
		SayMessage("Стойте! Cтойте! Не уничтожайте меня! Я сдаюсь!!!", SayType.ALL);
		SayMessage("Торвальд этот город теперь твой, а мы твои заложники", SayType.ALL);
		MadnessOfDwarfs.lossSiege(attacker, getPlayersInRegion());
	}

	private ArrayList<L2Player> getPlayersInRegion()
	{
		//TODO добавить фильтры
		ArrayList<L2Player> result = new ArrayList<L2Player>();

		L2NpcInstance actor = getActor();
		if(actor == null || actor.isDead())
			return result;

		L2WorldRegion region = L2World.getRegion(actor);
		if(region != null && region.getObjectsSize() > 0)
			for(L2Player pl : region.getPlayersList(actor.getReflection()))
				result.add(pl);
		return result;
	}

	public void SayMessage(String text, SayType t)
	{
		L2NpcInstance actor = getActor();
		if(actor == null || actor.isDead())
			return;

		switch(t)
		{
			case SHOUT:
				Functions.npcSay(actor, Say2C.SHOUT, text);
				break;
			case ALL:
				Functions.npcSay(actor, Say2C.ALL, text);
				break;
		}
	}

	public static String GetTextMessage(boolean isWar)
	{
		if(!isWar)
			switch(Rnd.get(4))
			{
				case 0:
					return "Добро пожаловать в наш город";
				case 1:
					return "Я обелиск этого города оберегайте меня.";
				case 2:
					return "Давным давно... Гном Торвальд пошел против Гильдии Гномов...";
				case 3:
					return "Я рад вас видеть";
				case 4:
					return "Примите участи в этом евенте и вы получите массу фана/опыта и если вам повезед то и ценные вещи";
			}
		else
			switch(Rnd.get(4))
			{
				case 0:
					return "На наш город напали!!!";
				case 1:
					return "Жители славного города защите меня от разбойников, иначе мы все станим заложниками";
				case 2:
					return "ААА... Это Торвальд со свой армией он хочет уничтожить нас не допустите этого";
				case 3:
					return "Пришлой время брать в руки оружие и вставать на защиту этого прекрасного города";
				case 4:
					return "Торвальд дал сигнал к атаке... Затрубили горны... Хирды двинулись в сторону города....";
			}
		return "";
	}

	//Затык пока я до аи не добрался
	public L2NpcInstance getActor()
	{
		return _thisActor;
	}
}
