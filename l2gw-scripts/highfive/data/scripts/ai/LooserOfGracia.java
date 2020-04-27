package ai;

import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 19.12.11 19:14
 */
public class LooserOfGracia extends Citizen
{
	public String fnAdenFort = "looser_of_gracia002.htm";
	public String fnSeedState = "looser_of_gracia003.htm";
	public String fnNotHaveAdena = "looser_of_gracia004.htm";
	public String fnLowLevel = "looser_of_gracia005.htm";

	public LooserOfGracia(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if( ask == -1425 )
		{
			switch(reply)
			{
				case 1:
					if( talker.getLevel() < 75 )
					{
						_thisActor.showPage(talker, fnLowLevel);
						return;
					}
					if( talker.getAdena() >= 150000 && talker.reduceAdena("Teleport", 150000, _thisActor, true))
					{
						talker.teleToLocation(-149406, 255247, -85);
					}
					else
					{
						_thisActor.showPage(talker, fnNotHaveAdena);
					}
					break;
				case 2:
					String fhtml0 = _thisActor.getHtmlFile(talker, fnSeedState);
					int i0 = FieldCycleManager.getStep(3);
					int i1 = 0;
					if( i0 <= 1 )
					{
						i1 = 1800711;
					}
					else if( i0 == 2 )
					{
						i1 = 1800712;
					}
					else if( i0 == 3 )
					{
						i1 = 1800713;
					}
					else if( i0 == 4 )
					{
						i1 = 1800714;
					}
					else if( i0 == 5 )
					{
						i1 = 1800715;
					}
					else
					{
						i1 = 1800716;
					}
					fhtml0 = fhtml0.replace("<?stat_unde?>", "<fstring p1=\"\" p2=\"\" p3=\"\" p4=\"\" p5=\"\">" + i1 + "</fstring>");

					//i0 = FieldCycleManager.getStep(2);
					i0 = ServerVariables.getInt("sod_stage", 1);
					if( i0 <= 1 )
					{
						i1 = 1800708;
					}
					else if( i0 == 2 )
					{
						i1 = 1800709;
					}
					else if( i0 >= 3 )
					{
						i1 = 1800710;
					}
					fhtml0 = fhtml0.replace("<?stat_dest?>", "<fstring p1=\"\" p2=\"\" p3=\"\" p4=\"\" p5=\"\">" + i1 + "</fstring>");
					_thisActor.showHtml(talker, fhtml0);
			}
		}
		super.onMenuSelected(talker, ask, reply);
	}
}