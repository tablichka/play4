package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 07.11.11 23:57
 */
public class TeleporterMultiList extends Teleporter
{
	public int[][] Position1 = new int[][]{{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] Position2 = new int[][]{{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] Position3 = new int[][]{{1010001, -84169, 244693, -3729, 100000, 0}};

	public TeleporterMultiList(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -8)
		{
			if(reply == 1)
			{
				if(talker.getTransformation() == 111 || talker.getTransformation() == 112 || talker.getTransformation() == 124)
				{
					_thisActor.showPage(talker, "q194_noteleport.htm");
				}
				else
				{
					_thisActor.teleportFStr(talker, Position1, ShopName, "", "", "", 57, 1000308, "", "", "", "", "");
				}
			}
			else if(reply == 2)
			{
				if(talker.getTransformation() == 111 || talker.getTransformation() == 112 || talker.getTransformation() == 124)
				{
					_thisActor.showPage(talker, "q194_noteleport.htm");
				}
				else
				{
					_thisActor.teleportFStr(talker, Position2, ShopName, "", "", "", 57, 1000308, "", "", "", "", "");
				}
			}
			else if(reply == 3)
			{
				if(talker.getTransformation() == 111 || talker.getTransformation() == 112 || talker.getTransformation() == 124)
				{
					_thisActor.showPage(talker, "q194_noteleport.htm");
				}
				else
				{
					_thisActor.teleportFStr(talker, Position3, ShopName, "", "", "", 57, 1000308, "", "", "", "", "");
				}
			}
		}
		else
			super.onMenuSelected(talker, ask, reply);
	}

	@Override
	protected int[][] getSelectedList(int listId)
	{
		if(Position1.hashCode() == listId)
			return Position1;
		else if(Position2.hashCode() == listId)
			return Position2;
		else if(Position3.hashCode() == listId)
			return Position3;

		return super.getSelectedList(listId);
	}
}
