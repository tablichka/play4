package services;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.util.Files;
import ru.l2gw.util.Location;

import java.util.List;

/**
 * Используется на Primeval Isle NPC Vervato (id: 32104)
 *
 * @Author: SYS
 * @Date: 27/6/2007
 */
public class SummonCorpse extends Functions implements ScriptFile
{
	public static L2Object self;
	public static L2Object npc;
	private static int SUMMON_PRICE = 200000;

	public void onLoad()
	{
		_log.info("Loaded Service: Summon a corpse");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	/**
	 * Телепортирует все труппы, находящиеся в группе в данный момент
	 * @return
	 */
	public void doSummon()
	{
		L2Player player = (L2Player) self;
		String fail = Files.read("data/html/default/32104-fail.htm", player);
		String success = Files.read("data/html/default/32104-success.htm", player);

		if(!player.isInParty())
		{
			show(fail, player);
			return;
		}

		int counter = 0;
		List<L2Player> partyMembers = player.getParty().getPartyMembers();
		for(L2Player partyMember : partyMembers)
			if(partyMember != null && partyMember.isDead() && player.reduceAdena("SummonCorpse", SUMMON_PRICE, null, true))
			{
				counter++;
				Location coords = new Location(11255 + Rnd.get(-20, 20), -23370 + Rnd.get(-20, 20), -3649);
				partyMember.teleportRequest(player, 0, 0);
			}

		if(counter == 0)
			show(fail, player);
		else
			show(success, player);
	}
}