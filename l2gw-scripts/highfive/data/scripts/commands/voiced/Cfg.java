package commands.voiced;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.serverpackets.*;
import ru.l2gw.util.Files;

/**
 * @author rage
 * @date 19.12.10 12:18
 */
public class Cfg extends Functions implements IVoicedCommandHandler, ScriptFile
{
	private String[] _commandList = new String[]{"cfg"};

	@Override
	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	@Override
	public void onReload()
	{
	}

	@Override
	public void onShutdown()
	{
	}

	@Override
	public boolean useVoicedCommand(String command, L2Player activeChar, String args)
	{
		if(command.equals("cfg"))
		{
			if(Config.COMMUNITYBOARD_ENABLED)
			{
				ICommunityBoardHandler handler = CommunityBoardManager.getInstance().getCommunityHandler("_bbsaccount");
				if(handler != null)
				{
					handler.onBypassCommand(activeChar, "_bbsaccount");
					return true;
				}
			}

			if(args != null)
			{
				String[] param = args.split(" ");
				if(param[0].equalsIgnoreCase("lang"))
				{
					if(param[1].equalsIgnoreCase("en"))
						activeChar.setVar("lang@", "en");
					else if(param[1].equalsIgnoreCase("ru"))
						activeChar.setVar("lang@", "ru");
				}
				else if(param[0].equalsIgnoreCase("noe"))
				{
					if(param[1].equalsIgnoreCase("on"))
						activeChar.setVar("NoExp", "1");
					else if(param[1].equalsIgnoreCase("of"))
						activeChar.unsetVar("NoExp");
				}
				else if(param[0].equalsIgnoreCase("trace"))
				{
					if(param[1].equalsIgnoreCase("on"))
						activeChar.setVar("trace", "1");
					else if(param[1].equalsIgnoreCase("of"))
						activeChar.unsetVar("trace");
				}
				else if(param[0].equalsIgnoreCase("notraders"))
				{
					if(param[1].equalsIgnoreCase("on"))
					{
						activeChar.setVar("notraders", "1");
						for(L2Player player : L2World.getAroundPlayers(activeChar))
							if(player.getPrivateStoreType() != L2Player.STORE_PRIVATE_NONE)
								activeChar.sendPacket(new DeleteObject(player));
					}
					else if(param[1].equalsIgnoreCase("of"))
					{
						activeChar.unsetVar("notraders");
						for(L2Player player : L2World.getAroundPlayers(activeChar))
							if(player.getPrivateStoreType() != L2Player.STORE_PRIVATE_NONE)
							{
								activeChar.sendPacket(new CharInfo(player));
								if(activeChar.getPrivateStoreType() == L2Player.STORE_PRIVATE_BUY)
									player.sendPacket(new PrivateStoreMsgBuy(player));
								else if(activeChar.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL || activeChar.getPrivateStoreType() == L2Player.STORE_PRIVATE_SELL_PACKAGE)
									player.sendPacket(new PrivateStoreMsgSell(player));
								else if(activeChar.getPrivateStoreType() == L2Player.STORE_PRIVATE_MANUFACTURE)
									player.sendPacket(new RecipeShopMsg(player));
							}
					}
				}
				else if(param[0].equalsIgnoreCase("notShowBuffAnim"))
				{
					if(param[1].equalsIgnoreCase("on"))
					{
						activeChar.setNotShowBuffAnim(true);
						activeChar.setVar("notShowBuffAnim", "1");
					}
					else if(param[1].equalsIgnoreCase("of"))
					{
						activeChar.setNotShowBuffAnim(false);
						activeChar.unsetVar("notShowBuffAnim");
					}
				}
			}

			String dialog = Files.read("data/scripts/commands/voiced/cfg.html", activeChar);

			dialog = dialog.replaceFirst("%lang%", activeChar.getVar("lang@").toUpperCase());
			dialog = dialog.replaceFirst("%noe%", activeChar.getVarB("NoExp") ? "On" : "Off");
			dialog = dialog.replaceFirst("%trace%", activeChar.getVarB("trace") ? "On" : "Off");
			dialog = dialog.replaceFirst("%notraders%", activeChar.getVarB("notraders") ? "On" : "Off");
			dialog = dialog.replaceFirst("%notShowBuffAnim%", activeChar.getVarB("notShowBuffAnim") ? "On" : "Off");
			show(dialog, activeChar);

			return true;
		}

		return false;
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return _commandList;
	}
}
