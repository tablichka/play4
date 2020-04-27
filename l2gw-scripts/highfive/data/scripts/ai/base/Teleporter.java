package ai.base;

import ai.Citizen;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.util.Location;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 07.11.11 19:36
 */
public class Teleporter extends Citizen
{
	public String ShopName = "";
	public String fnYouAreChaotic = "tcm.htm";
	public String fnNobless = "";
	public String fnNoNobless = "";
	public String fnNoNoblessItem = "";
	public String fnFlagMan = "flagman.htm";
	public int IsGateKeeperForCoreTime = 0;
	public int siegeStatusChecker = 0;
	public String name;

	//public int[][] Position = {{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] Position = {{1010013, 82971, 53207, -1470, 9400, 4}, {1010049, 111455, 219400, -3546, 7600, 6}, {1010006, 15472, 142880, -2699, 6800, 2}, {1010199, 148024, -55281, -2728, 63000, 7}, {1010200, 43835, -47749, -792, 59000, 8}, {1010574, 87126, -143520, -1288, 87000, 9}, {1010005, -12787, 122779, -3114, 29000, 1}, {1010023, 146783, 25808, -2000, 13000, 5}, {1010021, 47938, 186864, -3420, 5200, 3}, {1010022, 105918, 109759, -3170, 4400, 3}, {1010567, 43408, 206881, -3752, 5700, 0}, {1010118, 85546, 131328, -3672, 1000, 0}};
	public int[][] PositionNoblessNeedItemTown = {{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] PositionNoblessNoItemTown = {{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] PositionNoblessNeedItemField = {{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] PositionNoblessNoItemField = {{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] PositionNoblessNeedItemSSQ = {{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] PositionNoblessNoItemSSQ = {{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] PositionNewbie = {{1010001, -84169, 244693, -3729, 100000, 0}};
	public int[][] PositionPoint = {{1010001, -84169, 244693, -3729, 100000, 0}};

	public Teleporter(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(talker.isCombatFlagEquipped())
		{
			_thisActor.showPage(talker, fnFlagMan);
		}
		else if(talker.getKarma() > 0)
		{
			_thisActor.showPage(talker, fnYouAreChaotic);
		}
		else
		{
			_thisActor.showPage(talker, fnHi);
		}

		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -19)
		{
			if(talker.isNoble())
			{
				_thisActor.showPage(talker, fnNobless);
			}
			else
			{
				_thisActor.showPage(talker, fnNoNobless);
			}
		}
		else if(ask == -20)
		{
			if(reply == 1)
			{
				if(talker.isNoble())
				{
					if(talker.getItemCountByItemId(13722) != 0)
					{
						if(talker.getTransformation() == 111 || talker.getTransformation() == 112 || talker.getTransformation() == 124)
						{
							_thisActor.showPage(talker, "q194_noteleport.htm");
						}
						else
						{
							_thisActor.teleportFStr(talker, PositionNoblessNeedItemTown, ShopName, "", "", "", 13722, 1000528, "", "", "", "", "");
						}
					}
					else
					{
						_thisActor.showPage(talker, fnNoNoblessItem);
					}
				}
				else
				{
					_thisActor.showPage(talker, fnNoNobless);
				}
			}
			else if(reply == 2)
			{
				if(talker.isNoble())
				{
					if(talker.getItemCountByItemId(13722) != 0)
					{
						if(talker.getTransformation() == 111 || talker.getTransformation() == 112 || talker.getTransformation() == 124)
						{
							_thisActor.showPage(talker, "q194_noteleport.htm");
						}
						else
						{
							_thisActor.teleportFStr(talker, PositionNoblessNeedItemField, ShopName, "", "", "", 13722, 1000528, "", "", "", "", "");
						}
					}
					else
					{
						_thisActor.showPage(talker, fnNoNoblessItem);
					}
				}
				else
				{
					_thisActor.showPage(talker, fnNoNobless);
				}
			}
			else if(reply == 3)
			{
				if(talker.isNoble())
				{
					if(talker.getItemCountByItemId(13722) != 0)
					{
						if(talker.getTransformation() == 111 || talker.getTransformation() == 112 || talker.getTransformation() == 124)
						{
							_thisActor.showPage(talker, "q194_noteleport.htm");
						}
						else
						{
							_thisActor.teleportFStr(talker, PositionNoblessNeedItemSSQ, ShopName, "", "", "", 13722, 1000528, "", "", "", "", "");
						}
					}
					else
					{
						_thisActor.showPage(talker, fnNoNoblessItem);
					}
				}
				else
				{
					_thisActor.showPage(talker, fnNoNobless);
				}
			}
		}
		else if(ask == -21)
		{
			if(talker.isNoble())
			{
				if(talker.getTransformation() == 111 || talker.getTransformation() == 112 || talker.getTransformation() == 124)
				{
					_thisActor.showPage(talker, "q194_noteleport.htm");
				}
				else if(reply == 1 && talker.isNoble())
				{
					_thisActor.teleportFStr(talker, PositionNoblessNoItemTown, ShopName, "", "", "", 57, 1000308, "", "", "", "", "");
				}
				if(reply == 2 && talker.isNoble())
				{
					_thisActor.teleportFStr(talker, PositionNoblessNoItemField, ShopName, "", "", "", 57, 1000308, "", "", "", "", "");
				}
				if(reply == 3 && talker.isNoble())
				{
					_thisActor.teleportFStr(talker, PositionNoblessNoItemSSQ, ShopName, "", "", "", 57, 1000308, "", "", "", "", "");
				}
			}
			else
			{
				_thisActor.showPage(talker, fnNoNobless);
			}
		}
		else if(ask == -31)
		{
			if(talker.getLevel() >= 20 || !CategoryManager.isInCategory(6, talker))
			{
				_thisActor.showPage(talker, name + "005.htm");
			}
			else if(talker.getTransformation() == 111 || talker.getTransformation() == 112 || talker.getTransformation() == 124)
			{
				_thisActor.showPage(talker, "q194_noteleport.htm");
			}
			else
			{
				_thisActor.teleportFStr(talker, PositionPoint, ShopName, "", "", "", 57, 1000308, "", "", "", "", "");
			}
		}
		else if(ask == 255)
		{
			talker.setVar("backCoords", talker.getX() + " " + talker.getY() + " " + talker.getZ());
			talker.teleToLocation(12661, 181687, -3560);
		}
		else if(ask == -1055)
		{
			if(reply == 0)
			{
				talker.teleToLocation(-149406, 255247, -85);
			}
		}
		else if(ask == -1056)
		{
			if(reply == 0)
			{
				talker.teleToLocation(-84752, 243122, -3728);
			}
		}
		else if(ask == -1816)
		{
			if(reply == 3)
			{
				if(Rnd.get(3) < 1)
				{
					talker.teleToLocation(-58752, -56898, -2032);
				}
				else if(Rnd.get(2) < 1)
				{
					talker.teleToLocation(-59722, -57866, -2032);
				}
				else
				{
					talker.teleToLocation(-60695, -56894, -2032);
				}
			}
		}
		else if(ask == 20003)
		{
			if(reply == 1)
			{
				DefaultMaker maker0 = _thisActor.getMyMaker();

				if(maker0 != null && maker0.maximum_npc - maker0.npc_count >= 1)
				{
					if(!_thisActor.isInRange(talker, 25))
					{
						_thisActor.createOnePrivate(32600, "EventAlegria", 0, 0, (_thisActor.getX() + talker.getX()) / 2, (_thisActor.getY() + talker.getY()) / 2, _thisActor.getZ(), _thisActor.calcHeading(talker.getLoc()), 0, 0, talker.getStoredId());
					}
					else
					{
						_thisActor.showPage(talker, "event_alegria007.htm");
					}
				}
				else
				{
					_thisActor.showPage(talker, "event_alegria008.htm");
				}
			}
		}
		else
			super.onMenuSelected(talker, ask, reply);
	}

	@Override
	public void onTeleportRequested(L2Player talker)
	{
		if(talker.getTransformation() == 111 || talker.getTransformation() == 112 || talker.getTransformation() == 124)
		{
			_thisActor.showPage(talker, "q194_noteleport.htm");
		}
		else
		{
			_thisActor.teleportFStr(talker, Position, ShopName, "", "", "", 57, 1000308, "", "", "", "", "");
		}
	}

	@Override
	public void onTeleport(L2Player talker, int listId, int pos, int itemId)
	{
		int[][] currList = getSelectedList(listId);

		if(currList == null || currList.length <= pos)
			return;

		int[] telPos = currList[pos];

		if(itemId > 0)
		{
			int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			long count = itemId == 57 && day != 1 && day != 7 && (hour <= 12 || hour >= 22) ? telPos[4] / 2 : telPos[4];
			if(count > 0 && itemId == 57 && talker.getLevel() < 41)
				count = 0;

			if(count > 0 && !talker.destroyItemByItemId("Teleport", itemId, count, _thisActor, true))
				return;
		}

		talker.teleToLocation(Location.coordsRandomize(telPos[1], telPos[2], telPos[3], 0, 0, 50));
	}

	protected int[][] getSelectedList(int listId)
	{
		if(Position.hashCode() == listId)
			return Position;
		else if(PositionNoblessNeedItemTown.hashCode() == listId)
			return  PositionNoblessNeedItemTown;
		else if(PositionNoblessNoItemTown.hashCode() == listId)
			return  PositionNoblessNoItemTown;
		else if(PositionNoblessNeedItemField.hashCode() == listId)
			return  PositionNoblessNeedItemField;
		else if(PositionNoblessNoItemField.hashCode() == listId)
			return  PositionNoblessNoItemField;
		else if(PositionNoblessNeedItemSSQ.hashCode() == listId)
			return  PositionNoblessNeedItemSSQ;
		else if(PositionNoblessNoItemSSQ.hashCode() == listId)
			return  PositionNoblessNoItemSSQ;
		else if(PositionNewbie.hashCode() == listId)
			return  PositionNewbie;
		else if(PositionPoint.hashCode() == listId)
			return  PositionPoint;

		return null;
	}
}
