package commands.voiced;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.olympiad.Olympiad;
import ru.l2gw.gameserver.model.zone.L2Zone;

public class Offline extends Functions implements IVoicedCommandHandler, ScriptFile
{
	private String[] _commandList = new String[] { "offline", "ghost" };

	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public boolean useVoicedCommand(String command, L2Player activeChar, String target)
	{
		if(!Config.SERVICES_OFFLINE_TRADE_ALLOW)
		{
			show(new CustomMessage("scripts.commands.user.offline.Disabled", activeChar), activeChar);
			return false;
		}
		if(activeChar.getLevel() < Config.SERVICES_OFFLINE_TRADE_MINLVL)
		{
			show(new CustomMessage("scripts.commands.user.offline.IncorrectUse", activeChar), activeChar);
			return false;
		}
		if(!activeChar.isInStoreMode())
		{
			show(new CustomMessage("scripts.commands.user.offline.IncorrectUse", activeChar), activeChar);
			return false;
		}

		if(Olympiad.isRegisteredInComp(activeChar) || activeChar.isInOlympiadMode())
		{
			show(new CustomMessage("scripts.commands.user.offline.IncorrectUse", activeChar), activeChar);
			return false;
		}

		if(activeChar.getNoChannelRemained() > 0)
		{
			show(new CustomMessage("scripts.commands.user.offline.BanChat", activeChar), activeChar);
			return false;
		}

		if(activeChar.isActionBlocked(L2Zone.BLOCKED_ACTION_PRIVATE_STORE) || activeChar.isInZone(L2Zone.ZoneType.no_restart))
		{
			activeChar.sendMessage(new CustomMessage("trade.OfflineNoTradeZone", activeChar));
			return false;
		}

		if(Config.SERVICES_OFFLINE_TRADE_PRICE > 0 && Config.SERVICES_OFFLINE_TRADE_PRICE_ITEM > 0)
		{
			if(getItemCount(activeChar, Config.SERVICES_OFFLINE_TRADE_PRICE_ITEM) < Config.SERVICES_OFFLINE_TRADE_PRICE)
			{
				show(new CustomMessage("scripts.commands.user.offline.NotEnough", activeChar).addItemName(Config.SERVICES_OFFLINE_TRADE_PRICE_ITEM).addNumber(Config.SERVICES_OFFLINE_TRADE_PRICE), activeChar);
				return false;
			}
			removeItem(activeChar, Config.SERVICES_OFFLINE_TRADE_PRICE_ITEM, Config.SERVICES_OFFLINE_TRADE_PRICE);
		}

		activeChar.offline();
		return true;
	}

	public String[] getVoicedCommandList()
	{
		return _commandList;
	}
}