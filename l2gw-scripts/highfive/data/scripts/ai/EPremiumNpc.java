package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.instancemanager.PremiumItemManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Multisell;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.ExGetPremiumItemList;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

/**
 * @author: rage
 * @date: 21.01.13 22:08
 */
public class EPremiumNpc extends Citizen
{
	public int ticket_5h = 13273;
	public int ticket_5h_ev = 13383;
	public int ticket_5h_br = 20914;
	public int ticket_5h_br_ev = 22240;
	public int ticket_pt = 14065;
	public int ticket_pt_ev = 14074;
	public int warrior_buf_con_5h = 13017;
	public int mage_buf_con_5h = 13018;
	public int warrior_con_5h = 13019;
	public int mage_con_5h = 13020;
	public int toy_knight_con = 14061;
	public int spirit_mage_con = 14062;
	public int turtle_con = 14064;
	public int owl_mage_con = 14063;
	public int warrior_buf_pet_5h = 1016045;
	public int mage_buf_pet_5h = 1016046;
	public int warrior_pet_5h = 1016044;
	public int mage_pet_5h = 1016043;
	public int toy_knight_pet = 1016052;
	public int spirit_mage_pet = 1016051;
	public int turtle_pet = 1016053;
	public int owl_mage_pet = 1016050;
	public int m_knight_pet = 20915;
	public int m_mage_pet = 20916;
	public int m_warsmith_pet = 20917;
	public int f_knight_pet = 20918;
	public int f_mage_pet = 20919;
	public int f_warsmith_pet = 20920;
	public String NotHavePaper = "e_premium_manager014.htm";
	public String NotYetTime = "e_premium_manager015.htm";
	public int RcPaper = 15279;
	public int RcPresent = 15278;

	public EPremiumNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, "e_premium_manager001.htm");
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 1)
		{
			if(reply == 1)
			{
				if(PremiumItemManager.getItemsByObjectId(talker.getObjectId()).isEmpty())
					talker.sendPacket(new SystemMessage(SystemMessage.THERE_ARE_NO_MORE_VITAMIN_ITEMS_TO_BE_FOUND));
				else
					talker.sendPacket(new ExGetPremiumItemList(talker.getObjectId()));
			}
		}
		else if(ask == 2)
		{
			if(reply == 1)
			{
				_thisActor.showPage(talker, "e_premium_manager002.htm");
			}
		}
		else if(ask == 3)
		{
			if(reply == 1)
			{
				_thisActor.showPage(talker, "e_premium_manager003.htm");
			}
			else if(reply == 2)
			{
				if(talker.getItemCountByItemId(ticket_5h) > 0 || talker.getItemCountByItemId(ticket_5h_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h, 1, _thisActor, true);
					}
					talker.addItem("Exchange", warrior_buf_con_5h, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager007.htm");
				}
			}
		}
		else if(ask == 4)
		{
			if(reply == 1)
			{
				_thisActor.showPage(talker, "e_premium_manager004.htm");
			}
			else if(reply == 2)
			{
				if(talker.getItemCountByItemId(ticket_5h) > 0 || talker.getItemCountByItemId(ticket_5h_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h, 1, _thisActor, true);
					}
					talker.addItem("Exchange", mage_buf_con_5h, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager007.htm");
				}
			}
		}
		else if(ask == 5)
		{
			if(reply == 1)
			{
				_thisActor.showPage(talker, "e_premium_manager005.htm");
			}
			else if(reply == 2)
			{
				if(talker.getItemCountByItemId(ticket_5h) > 0 || talker.getItemCountByItemId(ticket_5h_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h, 1, _thisActor, true);
					}
					talker.addItem("Exchange", warrior_con_5h, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager007.htm");
				}
			}
		}
		else if(ask == 6)
		{
			if(reply == 1)
			{
				_thisActor.showPage(talker, "e_premium_manager006.htm");
			}
			else if(reply == 2)
			{
				if(talker.getItemCountByItemId(ticket_5h) > 0 || talker.getItemCountByItemId(ticket_5h_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h, 1, _thisActor, true);
					}
					talker.addItem("Exchange", mage_con_5h, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager007.htm");
				}
			}
		}
		else if(ask == -303)
		{
			talker.setLastMultisellNpc(_thisActor);
			L2Multisell.getInstance().SeparateAndSend(706, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(L2Zone.ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
		}
		else if(ask == 21000)
		{
			if(reply == 11)
			{
				if(talker.getItemCountByItemId(ticket_pt) > 0 || talker.getItemCountByItemId(ticket_pt_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_pt_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_pt_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_pt, 1, _thisActor, true);
					}
					talker.addItem("Exchange", toy_knight_con, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager007.htm");
				}
			}
			else if(reply == 21)
			{
				if(talker.getItemCountByItemId(ticket_pt) > 0 || talker.getItemCountByItemId(ticket_pt_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_pt_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_pt_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_pt, 1, _thisActor, true);
					}
					talker.addItem("Exchange", spirit_mage_con, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager007.htm");
				}
			}
			else if(reply == 31)
			{
				if(talker.getItemCountByItemId(ticket_pt) > 0 || talker.getItemCountByItemId(ticket_pt_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_pt_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_pt_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_pt, 1, _thisActor, true);
					}
					talker.addItem("Exchange", owl_mage_con, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager007.htm");
				}
			}
			else if(reply == 41)
			{
				if(talker.getItemCountByItemId(ticket_pt) > 0 || talker.getItemCountByItemId(ticket_pt_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_pt_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_pt_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_pt, 1, _thisActor, true);
					}
					talker.addItem("Exchange", turtle_con, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager007.htm");
				}
			}
			else if(reply == 51)
			{
				if(talker.getItemCountByItemId(ticket_5h_br) > 0 || talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br, 1, _thisActor, true);
					}
					talker.addItem("Exchange", m_knight_pet, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager025.htm");
				}
			}
			else if(reply == 61)
			{
				if(talker.getItemCountByItemId(ticket_5h_br) > 0 || talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br, 1, _thisActor, true);
					}
					talker.addItem("Exchange", m_mage_pet, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager025.htm");
				}
			}
			else if(reply == 71)
			{
				if(talker.getItemCountByItemId(ticket_5h_br) > 0 || talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br, 1, _thisActor, true);
					}
					talker.addItem("Exchange", m_warsmith_pet, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager025.htm");
				}
			}
			else if(reply == 81)
			{
				if(talker.getItemCountByItemId(ticket_5h_br) > 0 || talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br, 1, _thisActor, true);
					}
					talker.addItem("Exchange", f_knight_pet, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager025.htm");
				}
			}
			else if(reply == 91)
			{
				if(talker.getItemCountByItemId(ticket_5h_br) > 0 || talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br, 1, _thisActor, true);
					}
					talker.addItem("Exchange", f_mage_pet, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager025.htm");
				}
			}
			else if(reply == 101)
			{
				if(talker.getItemCountByItemId(ticket_5h_br) > 0 || talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
				{
					if(talker.getItemCountByItemId(ticket_5h_br_ev) > 0)
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br_ev, 1, _thisActor, true);
					}
					else
					{
						talker.destroyItemByItemId("Exchange", ticket_5h_br, 1, _thisActor, true);
					}
					talker.addItem("Exchange", f_warsmith_pet, 1, _thisActor, true);
					_thisActor.showPage(talker, "e_premium_manager008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "e_premium_manager025.htm");
				}
			}
		}
		/*
		else if(ask == -2271)
		{
			if(reply == 1)
			{
				if(myself.GetInventoryInfo(talker, 0) >= myself.GetInventoryInfo(talker, 1) * 0.800000 || myself.GetInventoryInfo(talker, 2) >= myself.GetInventoryInfo(talker, 3) * 0.800000)
				{
					myself.ShowSystemMessage(talker, 1118);
					return;
				}
				if(talker.getItemCountByItemId(RcPaper) > 0)
				{
					if(gg.GetDailyQuestFlag(talker, 992) == 1)
					{
						talker.destroyItemByItemId("Exchange", RcPaper, 1, _thisActor, true);
						st.giveItems(RcPresent, 1);
						st.exitCurrentQuest(false, true);
					}
					else
					{
						_thisActor.showPage(talker, NotYetTime);
					}
				}
				else
				{
					_thisActor.showPage(talker, NotHavePaper);
				}
			}
		}
		*/
	}
}
