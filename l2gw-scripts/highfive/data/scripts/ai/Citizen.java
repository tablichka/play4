package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Multisell;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

import static ru.l2gw.gameserver.model.zone.L2Zone.ZoneType;

/**
 * @author: rage
 * @date: 07.09.11 20:52
 */
public class Citizen extends L2CharacterAI
{
	public String fnHi = "chi.htm";
	public String fnFeudInfo = "defaultfeudinfo.htm";
	public String fnNoFeudInfo = "nofeudinfo.htm";
	public String fnBracketL = "[";
	public String fnBracketR = "]";
	public String fnFlagMan = "flagman.htm";
	public int MoveAroundSocial = 0;
	public int MoveAroundSocial1 = 0;
	public String ai_type = "pet_around_pet_manager";
	public int HavePet = 0;
	public int silhouette = 1020130;
	public int FriendShip1 = 0;
	public int FriendShip2 = 0;
	public int FriendShip3 = 0;
	public int FriendShip4 = 0;
	public int FriendShip5 = 0;
	public String fnNoFriend = "citizen_html";
	public int NoFnHi = 0;
	public boolean debug;
	protected L2NpcInstance _thisActor;

	public Citizen(L2Character actor)
	{
		super(actor);
		_thisActor = (L2NpcInstance) actor;
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(NoFnHi == 1)
		{
			return true;
		}
		if(talker.getItemCountByItemId(13560) > 0 || talker.getItemCountByItemId(13561) > 0 || talker.getItemCountByItemId(13562) > 0 || talker.getItemCountByItemId(13563) > 0 || talker.getItemCountByItemId(13564) > 0 || talker.getItemCountByItemId(13565) > 0 || talker.getItemCountByItemId(13566) > 0 || talker.getItemCountByItemId(13567) > 0 || talker.getItemCountByItemId(13568) > 0)
		{
			_thisActor.showPage(talker, fnFlagMan);
			return true;
		}
		if(FriendShip1 == 0)
		{
			_thisActor.showPage(talker, fnHi);
		}
		else if(talker.getItemCountByItemId(FriendShip1) > 0 || talker.getItemCountByItemId(FriendShip2) > 0 || talker.getItemCountByItemId(FriendShip3) > 0 || talker.getItemCountByItemId(FriendShip4) > 0 || talker.getItemCountByItemId(FriendShip5) > 0)
		{
			_thisActor.showPage(talker, fnHi);
		}
		else
		{
			_thisActor.showPage(talker, fnNoFriend);
		}

		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		if(HavePet == 1)
		{
			_thisActor.createOnePrivate(silhouette, ai_type, 0, 0, (_thisActor.getX() + 10), (_thisActor.getY() + 10), _thisActor.getZ(), 0, 0, 0, 0);
		}
		super.onEvtSpawn();
	}

	@Override
	public void onTalkSelected(L2Player talker, int choice, boolean fromChoice)
	{
		_thisActor.showQuestWindow(talker);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -303)
		{
			if(reply == 579)
			{
				if(talker.getLevel() >= 40 && talker.getLevel() < 46)
				{
					if(talker.getRace().ordinal() == 5)
					{
						talker.setLastMultisellNpc(_thisActor);
						L2Multisell.getInstance().SeparateAndSend(603, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
					}
					else
					{
						talker.setLastMultisellNpc(_thisActor);
						L2Multisell.getInstance().SeparateAndSend(reply, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
					}
				}
			}
			else if(reply == 580)
			{
				if(talker.getLevel() >= 46 && talker.getLevel() < 52)
				{
					if(talker.getRace().ordinal() == 5)
					{
						talker.setLastMultisellNpc(_thisActor);
						L2Multisell.getInstance().SeparateAndSend(604, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
					}
					else
					{
						talker.setLastMultisellNpc(_thisActor);
						L2Multisell.getInstance().SeparateAndSend(reply, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
					}
				}
			}
			else if(reply == 581)
			{
				if(talker.getLevel() >= 52)
				{
					if(talker.getRace().ordinal() == 5)
					{
						talker.setLastMultisellNpc(_thisActor);
						L2Multisell.getInstance().SeparateAndSend(605, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
					}
					else
					{
						talker.setLastMultisellNpc(_thisActor);
						L2Multisell.getInstance().SeparateAndSend(reply, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
					}
				}
			}
			else
			{
				talker.setLastMultisellNpc(_thisActor);
				L2Multisell.getInstance().SeparateAndSend(reply, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
			}
		}
		else if(ask == -601)
		{
			if(reply == 0)
			{
				if(talker.getItemCountByItemId(8957) <= 0 && talker.getItemCountByItemId(8958) <= 0 && talker.getItemCountByItemId(8959) <= 0)
				{
					_thisActor.showPage(talker, "welcomeback003.htm");
				}
				else
				{
					_thisActor.showPage(talker, "welcomeback004.htm");
				}
			}
			else if(reply == 1)
			{
				if(talker.getItemCountByItemId(8957) <= 0 && talker.getItemCountByItemId(8958) <= 0 && talker.getItemCountByItemId(8959) <= 0)
				{
					_thisActor.showPage(talker, "welcome_lin2_cat002.htm");
				}
				else
				{
					_thisActor.showPage(talker, "welcome_lin2_cat004.htm");
				}
			}
			else if(reply == 2)
			{
				if(talker.getLevel() < 20)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(583, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 20 && talker.getLevel() < 40)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(584, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 40 && talker.getLevel() < 52)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(585, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 52 && talker.getLevel() < 61)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(586, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 61 && talker.getLevel() < 76)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(587, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 76)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(588, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
			}
			else if(reply == 3)
			{
				if(talker.getLevel() < 20)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(589, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 20 && talker.getLevel() < 40)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(590, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 40 && talker.getLevel() < 52)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(591, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 52 && talker.getLevel() < 61)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(592, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 61 && talker.getLevel() < 76)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(593, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 76)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(594, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
			}
			else if(reply == 4)
			{
				if(talker.getLevel() < 20)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(595, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 20 && talker.getLevel() < 40)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(596, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 40 && talker.getLevel() < 52)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(597, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 52 && talker.getLevel() < 61)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(598, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 61 && talker.getLevel() < 76)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(601, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
				else if(talker.getLevel() >= 76)
				{
					talker.setLastMultisellNpc(_thisActor);
					L2Multisell.getInstance().SeparateAndSend(600, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
				}
			}
		}
	}
}
