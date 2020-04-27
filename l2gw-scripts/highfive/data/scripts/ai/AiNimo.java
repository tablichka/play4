package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 11.09.11 17:39
 */
public class AiNimo extends Citizen
{
	public String fnTutorial = "nimo002.htm";
	public String fnMaxNpc = "nimo004b.htm";
	public String Hadit = "nimo003b.htm";
	public String Giveit = "nimo006.htm";
	public String InvenFull = "nimo003c.htm";
	public String GiveitSuccess = "nimo003a.htm";
	public String SpawnSuccess = "nimo004a.htm";
	public int CHECK_TIMER = 1111;

	public AiNimo(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -415)
		{
			if(reply == 1)
			{
				if(talker.getItemCountByItemId(15487) > 0)
				{
					_thisActor.showPage(talker, Hadit);
				}
				else
				{
					_thisActor.showPage(talker, GiveitSuccess);
					talker.addItem("Maguen", 15487, 1, _thisActor, true);
				}
			}
			else if(reply == 2)
			{

				if(talker.getSessionVar("maguen") == null)
				{
					DefaultMaker maker0 = _thisActor.getSpawnDefine().getMaker();
					if(maker0 != null && maker0.npc_count < maker0.maximum_npc)
					{
						_thisActor.showPage(talker, SpawnSuccess);
						Location pos0 = Location.coordsRandomize(talker, 50, 100);
						_thisActor.createOnePrivate(18839, "AiMarguene", 0, 0, pos0.getX(), pos0.getY(), pos0.getZ(), 0, 0, 1, talker.getStoredId());
						talker.setSessionVar("maguen", "1");
					}
					else
						_thisActor.showPage(talker, fnMaxNpc);
				}
				else
				{
					_thisActor.showPage(talker, fnMaxNpc);
				}
			}
		}
	}
}
