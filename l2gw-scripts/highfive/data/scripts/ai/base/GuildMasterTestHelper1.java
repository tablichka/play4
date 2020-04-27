package ai.base;

import ai.Citizen;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.instancemanager.SiegeManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Multisell;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.zone.L2Zone.ZoneType;
import ru.l2gw.gameserver.serverpackets.PledgeShowInfoUpdate;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 10.10.11 14:41
 */
public class GuildMasterTestHelper1 extends Citizen
{
	public String fnSell = "test_server_helper002.htm";
	public String fnBuy = "test_server_helper003.htm";
	public String fnUnableItemSell = "test_server_helper005.htm";
	public String fnYouAreThirdClass = "test_server_helper010.htm";
	public String fnYouAreFourthClass = "test_server_helper011.htm";
	public String fnHumanFighter = "test_server_helper012.htm";
	public String fnHumanMage = "test_server_helper013.htm";
	public String fnElfFighter = "test_server_helper014.htm";
	public String fnElfMage = "test_server_helper015.htm";
	public String fnDElfFighter = "test_server_helper016.htm";
	public String fnDElfMage = "test_server_helper017.htm";
	public String fnOrcFighter = "test_server_helper018.htm";
	public String fnOrcMage = "test_server_helper019.htm";
	public String fnDwarfFighter = "test_server_helper020.htm";
	public String fnAfterClassChange = "test_server_helper021.htm";
	public String fnPledgeLevelUP = "test_server_helper022.htm";
	public String fnLowLevel = "test_server_helper023.htm";
	public String fnKamael = "test_server_helper030.htm";
	public int type = 0;

	public int SellList0;
	public int SellList1;
	public int SellList2;
	public int SellList3;
	public int SellList4;
	public int SellList5;
	public int SellList6;
	public int SellList7;

	public GuildMasterTestHelper1(L2Character actor)
	{
		super(actor);
		fnHi = "test_server_helper001.htm";
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -4 && reply == 1)
		{
			if(type == 0)
			{
				_thisActor.showPage(talker, "test_server_helper001a.htm");
			}
			else
			{
				_thisActor.showPage(talker, "test_server_helper001b.htm");
			}
		}
		else if(ask == -6 && reply == 1)
		{
			if(talker.isNoble())
			{
				_thisActor.showPage(talker, "test_server_helper025b.htm");
			}
			else if(talker.getLevel() < 75)
			{
				_thisActor.showPage(talker, "test_server_helper025a.htm");
			}
			else
			{
				talker.setNoble(true);
				Util.completeQuest(talker, 235);
				_thisActor.showPage(talker, "test_server_helper025.htm");
			}
		}
		else if(ask == -7 && reply == 1)
		{
			if(CategoryManager.isInCategory(6, talker.getActiveClass()))
			{
				if(talker.getLevel() < 20)
				{
					_thisActor.showPage(talker, "test_server_helper027.htm");
					return;
				}
				if(talker.getRace().ordinal() == 0)
				{
					switch(talker.getActiveClass())
					{
						case 0:
							_thisActor.showPage(talker, "test_server_helper026a.htm");
							break;
						case 10:
							_thisActor.showPage(talker, "test_server_helper026b.htm");
							break;
					}
				}
				else if(talker.getRace().ordinal() == 1)
				{
					switch(talker.getActiveClass())
					{
						case 18:
							_thisActor.showPage(talker, "test_server_helper026c.htm");
							break;
						case 25:
							_thisActor.showPage(talker, "test_server_helper026d.htm");
							break;
					}
				}
				else if(talker.getRace().ordinal() == 2)
				{
					switch(talker.getActiveClass())
					{
						case 31:
							_thisActor.showPage(talker, "test_server_helper026e.htm");
							break;
						case 38:
							_thisActor.showPage(talker, "test_server_helper026f.htm");
							break;
					}
				}
				else if(talker.getRace().ordinal() == 3)
				{
					switch(talker.getActiveClass())
					{
						case 44:
							_thisActor.showPage(talker, "test_server_helper026g.htm");
							break;
						case 49:
							_thisActor.showPage(talker, "test_server_helper026h.htm");
							break;
					}
				}
				else if(talker.getRace().ordinal() == 4)
				{
					if(talker.getActiveClass() == 53)
					{
						_thisActor.showPage(talker, "test_server_helper026i.htm");
					}
				}
				else if(talker.getRace().ordinal() == 5)
				{
					switch(talker.getActiveClass())
					{
						case 123:
							talker.setClassId((short) 125, false);
							talker.broadcastUserInfo(true);
							_thisActor.showPage(talker, fnAfterClassChange);
							break;
						case 124:
							talker.setClassId((short) 126, false);
							talker.broadcastUserInfo(true);
							_thisActor.showPage(talker, fnAfterClassChange);
							break;
					}
				}
			}
			else if(CategoryManager.isInCategory(7, talker.getActiveClass()))
			{
				_thisActor.showPage(talker, "test_server_helper028.htm");
			}
			else if(CategoryManager.isInCategory(8, talker.getActiveClass()))
			{
				_thisActor.showPage(talker, fnYouAreThirdClass);
			}
			else if(CategoryManager.isInCategory(9, talker.getActiveClass()))
			{
				_thisActor.showPage(talker, fnYouAreFourthClass);
			}
		}
		else if(ask == -2 && reply == 1)
		{
			if(CategoryManager.isInCategory(7, talker.getActiveClass()))
			{
				if(talker.getLevel() < 40)
				{
					_thisActor.showPage(talker, fnLowLevel);
					return;
				}
				if(talker.getRace().ordinal() == 0)
				{
					switch(talker.getActiveClass())
					{
						case 0:
							_thisActor.showPage(talker, fnHumanFighter);
							break;
						case 1:
							_thisActor.showPage(talker, "test_server_helper012a.htm");
							break;
						case 4:
							_thisActor.showPage(talker, "test_server_helper012b.htm");
							break;
						case 7:
							_thisActor.showPage(talker, "test_server_helper012c.htm");
							break;
						case 10:
							_thisActor.showPage(talker, fnHumanMage);
							break;
						case 11:
							_thisActor.showPage(talker, "test_server_helper013a.htm");
							break;
						case 15:
							_thisActor.showPage(talker, "test_server_helper013b.htm");
							break;
					}
				}
				else if(talker.getRace().ordinal() == 1)
				{
					switch(talker.getActiveClass())
					{
						case 18:
							_thisActor.showPage(talker, fnElfFighter);
							break;
						case 19:
							_thisActor.showPage(talker, "test_server_helper014a.htm");
							break;
						case 22:
							_thisActor.showPage(talker, "test_server_helper014b.htm");
							break;
						case 25:
							_thisActor.showPage(talker, fnElfMage);
							break;
						case 26:
							_thisActor.showPage(talker, "test_server_helper015a.htm");
							break;
						case 29:
							_thisActor.showPage(talker, "test_server_helper015b.htm");
							break;
					}
				}
				else if(talker.getRace().ordinal() == 2)
				{
					switch(talker.getActiveClass())
					{
						case 31:
							_thisActor.showPage(talker, fnDElfFighter);
							break;
						case 32:
							_thisActor.showPage(talker, "test_server_helper016a.htm");
							break;
						case 35:
							_thisActor.showPage(talker, "test_server_helper016b.htm");
							break;
						case 38:
							_thisActor.showPage(talker, fnDElfMage);
							break;
						case 39:
							_thisActor.showPage(talker, "test_server_helper017a.htm");
							break;
						case 42:
							_thisActor.showPage(talker, "test_server_helper017b.htm");
							break;
					}
				}
				else if(talker.getRace().ordinal() == 3)
				{
					switch(talker.getActiveClass())
					{
						case 44:
							_thisActor.showPage(talker, fnOrcFighter);
							break;
						case 45:
							_thisActor.showPage(talker, "test_server_helper018a.htm");
							break;
						case 47:
							_thisActor.showPage(talker, "test_server_helper018b.htm");
							break;
						case 49:
							_thisActor.showPage(talker, fnOrcMage);
							break;
						case 50:
							_thisActor.showPage(talker, fnOrcMage);
							break;
					}
				}
				else if(talker.getRace().ordinal() == 4)
				{
					switch(talker.getActiveClass())
					{
						case 53:
							_thisActor.showPage(talker, fnDwarfFighter);
							break;
						case 56:
							_thisActor.showPage(talker, "test_server_helper020b.htm");
							break;
						case 54:
							_thisActor.showPage(talker, "test_server_helper020a.htm");
							break;
					}
				}
				else if(talker.getRace().ordinal() == 5)
				{
					switch(talker.getActiveClass())
					{
						case 125:
							_thisActor.showPage(talker, "test_server_helper020c.htm");
							break;
						case 126:
							_thisActor.showPage(talker, "test_server_helper020d.htm");
							break;
					}
				}
			}
			else if(CategoryManager.isInCategory(8, talker.getActiveClass()))
			{
				_thisActor.showPage(talker, fnYouAreThirdClass);
			}
			else if(CategoryManager.isInCategory(9, talker.getActiveClass()))
			{
				_thisActor.showPage(talker, fnYouAreFourthClass);
			}
			else
			{
				_thisActor.showPage(talker, "test_server_helper029.htm");
			}
		}
		else if(ask == -5 && reply == 1)
		{
			if(CategoryManager.isInCategory(8, talker.getActiveClass()) && talker.getLevel() > 75)
			{
				switch(talker.getActiveClass())
				{
					case 2:
						talker.setClassId((short) 88, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 3:
						talker.setClassId((short) 89, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 5:
						talker.setClassId((short) 90, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 6:
						talker.setClassId((short) 91, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 8:
						talker.setClassId((short) 93, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 9:
						talker.setClassId((short) 92, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 12:
						talker.setClassId((short) 94, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 13:
						talker.setClassId((short) 95, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 14:
						talker.setClassId((short) 96, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 16:
						talker.setClassId((short) 97, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						talker.addItem("Quest", 15307, 1, _thisActor, true);
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 17:
						talker.setClassId((short) 98, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 20:
						talker.setClassId((short) 99, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 21:
						talker.setClassId((short) 100, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 23:
						talker.setClassId((short) 101, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 24:
						talker.setClassId((short) 102, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 27:
						talker.setClassId((short) 103, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 28:
						talker.setClassId((short) 104, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 30:
						talker.setClassId((short) 105, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						talker.addItem("Quest", 15308, 1, _thisActor, true);
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 33:
						talker.setClassId((short) 106, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 34:
						talker.setClassId((short) 107, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 36:
						talker.setClassId((short) 108, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 37:
						talker.setClassId((short) 109, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 40:
						talker.setClassId((short) 110, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 41:
						talker.setClassId((short) 111, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 43:
						talker.setClassId((short) 112, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						talker.addItem("Quest", 15309, 1, _thisActor, true);
						talker.addItem("Quest", 15309, 1, _thisActor, true);
						talker.addItem("Quest", 15309, 1, _thisActor, true);
						talker.addItem("Quest", 15309, 1, _thisActor, true);
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 46:
						talker.setClassId((short) 113, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 48:
						talker.setClassId((short) 114, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 51:
						talker.setClassId((short) 115, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 52:
						talker.setClassId((short) 116, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 55:
						talker.setClassId((short) 117, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 57:
						talker.setClassId((short) 118, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 127:
						talker.setClassId((short) 131, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 128:
						talker.setClassId((short) 132, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 129:
						talker.setClassId((short) 133, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 130:
						talker.setClassId((short) 134, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
					case 135:
						talker.setClassId((short) 136, false);
						talker.broadcastUserInfo(true);
						Util.playSound(talker, "ItemSound.quest_fanfare_2");
						_thisActor.showPage(talker, fnAfterClassChange);
						break;
				}
			}
			else if(CategoryManager.isInCategory(9, talker.getActiveClass()))
			{
				_thisActor.showPage(talker, fnYouAreFourthClass);
			}
			else
			{
				_thisActor.showPage(talker, "test_server_helper024.htm");
			}
		}
		if(ask == -3)
		{
			if(reply == 0)
			{
				if(talker.getLevel() < 10)
				{
					_thisActor.showPage(talker, "pl002.htm");
				}
				else if(talker.isClanLeader())
				{
					_thisActor.showPage(talker, "pl003.htm");
				}
				else if(talker.getClanId() != 0)
				{
					_thisActor.showPage(talker, "pl004.htm");
				}
				else
				{
					_thisActor.showPage(talker, "pl005.htm");
				}
			}
			else if(reply == 2)
			{
				if(talker.isClanLeader())
				{
					_thisActor.showPage(talker, "pl007.htm");
				}
				else
				{
					_thisActor.showPage(talker, "pl008.htm");
				}
			}
			else if(reply == 3)
			{
				if(talker.isClanLeader())
				{
					_thisActor.showPage(talker, "pl010.htm");
				}
				else
				{
					_thisActor.showPage(talker, "pl011.htm");
				}
			}
			else if(reply == 1)
			{
				if(talker.isClanLeader())
				{
					_thisActor.showPage(talker, fnPledgeLevelUP);
				}
				else
				{
					_thisActor.showPage(talker, "pl014.htm");
				}
			}
		}
		if(ask == -1)
		{
			if(reply == 0)
			{
				_thisActor.showBuyWindow(talker, SellList0);
			}
			else if(reply == 1)
			{
				_thisActor.showBuyWindow(talker, SellList1);
			}
			else if(reply == 2)
			{
				_thisActor.showBuyWindow(talker, SellList2);
			}
			else if(reply == 3)
			{
				_thisActor.showBuyWindow(talker, SellList3);
			}
			else if(reply == 4)
			{
				_thisActor.showBuyWindow(talker, SellList4);
			}
			else if(reply == 5)
			{
				_thisActor.showBuyWindow(talker, SellList5);
			}
			else if(reply == 6)
			{
				_thisActor.showBuyWindow(talker, SellList6);
			}
			else if(reply == 7)
			{
				_thisActor.showBuyWindow(talker, SellList7);
			}
			else
			{
				super.onMenuSelected(talker, ask, reply);
			}
		}
		if(ask == -8 && reply == 1)
		{
			if(talker != null)
			{
				Util.rewardSkills(talker);
			}
		}
		if(ask == -1006)
		{
			if(reply == 1)
			{
				if(!talker.isQuestComplete(133))
				{
					Util.completeQuest(talker, 133);
				}
				talker.teleToLocation(-11272, 236464, -3248);
			}
		}
		if(ask == -303)
		{
			if(reply == 622)
			{
				talker.setLastMultisellNpc(_thisActor);
				L2Multisell.getInstance().SeparateAndSend(622, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
			}
			else if(reply == 644)
			{
				talker.setLastMultisellNpc(_thisActor);
				L2Multisell.getInstance().SeparateAndSend(644, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
			}
			else if(reply == 695)
			{
				talker.setLastMultisellNpc(_thisActor);
				L2Multisell.getInstance().SeparateAndSend(695, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
			}
			else if(reply == 696)
			{
				talker.setLastMultisellNpc(_thisActor);
				L2Multisell.getInstance().SeparateAndSend(696, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
			}
			else if(reply == 697)
			{
				talker.setLastMultisellNpc(_thisActor);
				L2Multisell.getInstance().SeparateAndSend(697, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
			}
		}
		if(ask == -1000 & reply == -1000)
		{
			L2Clan clan = talker.getClan();
			if(clan != null && clan.getLevel() >= 0 && clan.getLevel() < 5)
			{
				levelUpPledge(talker, clan.getLevel() + 1);
			}
		}
	}

	@Override
	public void classChangeRequested(L2Player talker, int occupation_name_id)
	{
		if(CategoryManager.isInCategory(6, talker.getActiveClass()) && talker.getLevel() > 19)
		{
			if(CategoryManager.isInCategory(7, occupation_name_id))
			{
				talker.setClassId((short) occupation_name_id, false);
				talker.broadcastUserInfo(true);
				Util.playSound(talker, "ItemSound.quest_fanfare_2");
				_thisActor.showPage(talker, fnAfterClassChange);
			}
		}
		else if(CategoryManager.isInCategory(7, talker.getActiveClass()) && talker.getLevel() > 39)
		{
			if(CategoryManager.isInCategory(8, occupation_name_id))
			{
				talker.setClassId((short) occupation_name_id, false);
				talker.broadcastUserInfo(true);
				Util.playSound(talker, "ItemSound.quest_fanfare_2");
				_thisActor.showPage(talker, fnAfterClassChange);
			}
		}
		else if(CategoryManager.isInCategory(8, talker.getActiveClass()) && talker.getLevel() > 74)
		{
			if(CategoryManager.isInCategory(9, occupation_name_id))
			{
				talker.setClassId((short) occupation_name_id, false);
				talker.broadcastUserInfo(true);
				Util.playSound(talker, "ItemSound.quest_fanfare_2");
				_thisActor.showPage(talker, fnAfterClassChange);
			}
		}
	}

	private static void levelUpPledge(L2Player talker, int level)
	{
		L2Clan clan = talker.getClan();
		if(clan == null)
			return;

		clan.setLevel((byte) level);
		clan.updateClanInDB();

		if(Config.PREMIUM_ENABLED && Config.PREMIUM_MIN_CLAN_LEVEL >= clan.getLevel())
			talker.startPremiumTask(0);

		if(clan.getLevel() > 3)
			SiegeManager.addSiegeSkills(talker);

		if(clan.getLevel() == 5)
			talker.sendPacket(new SystemMessage(SystemMessage.NOW_THAT_YOUR_CLAN_LEVEL_IS_ABOVE_LEVEL_5_IT_CAN_ACCUMULATE_CLAN_REPUTATION_POINTS));

		// notify all the members about it
		final SystemMessage sm = new SystemMessage(SystemMessage.CLANS_SKILL_LEVEL_HAS_INCREASED);
		final PledgeShowInfoUpdate pu = new PledgeShowInfoUpdate(clan);
		for(L2Player member : clan.getOnlineMembers(""))
			if(member.isOnline())
			{
				member.updatePledgeClass();
				member.sendPacket(sm);
				member.sendPacket(pu);
				member.getPlayer().broadcastUserInfo(true);
			}
	}
}