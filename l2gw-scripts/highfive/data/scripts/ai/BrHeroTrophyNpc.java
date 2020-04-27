package ai;

import events.HeroTrophy.HeroTrophy;
import ru.l2gw.commons.arrays.ArrayUtils;
import ru.l2gw.commons.crontab.Crontab;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;

/**
 * @author: rage
 * @date: 19.01.13 20:30
 */
public class BrHeroTrophyNpc extends Citizen
{
	private static final Crontab crontab = new Crontab("30 6 * * *");

	public BrHeroTrophyNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, "br_hero_trophy_npc01.htm");
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -1 && reply == 1)
		{
			long count = 0;
			for(L2ItemInstance item : talker.getInventory().getItems())
				if(ArrayUtils.contains(HeroTrophy.earItems, item.getItemId()))
					count += item.getCount();

			if(talker.isHero())
			{
				_thisActor.showPage(talker, "br_hero_trophy_npc03c.htm");
			}
			else if(count < HeroTrophy.EAR_COUNT_FOR_HERO)
			{
				String fhtml0 = _thisActor.getHtmlFile(talker, "br_hero_trophy_npc03a.htm");
				fhtml0 = fhtml0.replace("<?ears?>", String.valueOf(HeroTrophy.EAR_COUNT_FOR_HERO - count));
				_thisActor.showHtml(talker, fhtml0);
			}
			else if(talker.getVarB("ht_hero"))
			{
				_thisActor.showPage(talker, "br_hero_trophy_npc03b.htm");
			}
			else
			{
				talker.unsetVar("ht_no_hero");
				talker.setVar("ht_hero", "true", (int) (crontab.timeNextUsage(System.currentTimeMillis()) / 1000));
				talker.setHero(true);
				talker.broadcastUserInfo(true);
				_thisActor.showPage(talker, "br_hero_trophy_npc03.htm");
			}
		}
		else
			super.onMenuSelected(talker, ask, reply);
	}
}
