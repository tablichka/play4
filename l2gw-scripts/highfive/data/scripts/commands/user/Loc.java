package commands.user;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IUserCommandHandler;
import ru.l2gw.gameserver.handler.UserCommandHandler;
import ru.l2gw.gameserver.instancemanager.TownManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

/**
 * Support for /loc command
 */
public class Loc implements IUserCommandHandler, ScriptFile
{
	private static final int[] COMMAND_IDS = {0};

	public boolean useUserCommand(int id, L2Player activeChar)
	{
		if(COMMAND_IDS[0] != id)
			return false;

		int nearestTown = TownManager.getInstance().getClosestTownNumber(activeChar);
		int msg;
		int gx = ((activeChar.getX() - L2World.MAP_MIN_X) >> 15) + Config.GEO_X_FIRST;
		int gy = ((activeChar.getY() - L2World.MAP_MIN_Y) >> 15) + Config.GEO_Y_FIRST;

		if(gx == 16 && gy == 12)
			msg = SystemMessage.CURRENT_LOCATION_S1_S2_S3_DIMENSION_GAP;
		else if(gx >= 17 && gx <= 19 && gy == 11)
			msg = SystemMessage.CURRENT_LOCATION_INSIDE_KAMALOKA;
		else if(gx >= 20 && gx <= 21 && gy == 11)
			msg = SystemMessage.CURRENT_LOCATION_INSIDE_RIM_KAMALOKA;
		else if(gx >= 17 && gx <= 20 && gy == 12)
			msg = SystemMessage.CURRENT_LOCATION_INSIDE_KAMALOKA;
		else if(gx == 24 && gy == 19)
			msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_THE_COLISEUM;
		else if(gx == 25 && gy == 18)
			msg = SystemMessage.CURRENT_LOCATION_S1_S2_S3_CEMETERY_OF_THE_EMPIRE;
		else if(gx == 18 && gy == 16)
			msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_FANTASY_ISLE;
		else if(gx == 16 && gy == 13)
			msg = SystemMessage.CURRENT_LOCATION_INSIDE_THE_CHAMBER_OF_DELUSION;
		else
			switch(nearestTown)
			{
				case 1:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_TALKING_ISLAND_VILLAGE;
					break;
				case 2:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_ELVEN_VILLAGE;
					break;
				case 3:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_DARK_ELVEN_VILLAGE;
					break;
				case 4:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_ORC_VILLAGE;
					break;
				case 5:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_DWARVEN_VILLAGE;
					break;
				case 6:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_GLUDIO_CASTLE_TOWN;
					break;
				case 7:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_GLUDIN_VILLAGE;
					break;
				case 8:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_DION_CASTLE_TOWN;
					break;
				case 9:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_GIRAN_CASTLE_TOWN;
					break;
				case 10:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_THE_TOWN_OF_OREN;
					break;
				case 11:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_ADEN_CASTLE_TOWN;
					break;
				case 12:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_HUNTERS_VILLAGE;
					break;
				case 13:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_HEINE;
					break;
				case 14:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_RUNE_VILLAGE;
					break;
				case 15:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_GODDARD_CASTLE_TOWN;
					break;
				case 16:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_THE_TOWN_OF_SCHUTTGART;
					break;
				case 17:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_KAMAEL_VILLAGE;
					break;
				case 18:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_PRIMEVAL_ISLAND;
					break;
				case 21:
					msg = SystemMessage.CURRENT_LOCATION_S1_S2_S3_NEAR_THE_KEUCEREUS_CLAN_ASSOCIATION_LOCATION;
					break;
				default:
					msg = SystemMessage.CURRENT_LOCATION__S1_S2_S3_NEAR_ADEN_CASTLE_TOWN;
			}
		SystemMessage sm = new SystemMessage(msg);
		sm.addNumber(activeChar.getX());
		sm.addNumber(activeChar.getY());
		sm.addNumber(activeChar.getZ());
		activeChar.sendPacket(sm);
		if(activeChar.isGM() && activeChar.getVehicle() != null)
		{
			sm = new SystemMessage(msg);
			sm.addNumber(activeChar.getLocInVehicle().getX());
			sm.addNumber(activeChar.getLocInVehicle().getY());
			sm.addNumber(activeChar.getLocInVehicle().getZ());
			activeChar.sendPacket(sm);
		}
		return true;
	}

	public final int[] getUserCommandList()
	{
		return COMMAND_IDS;
	}

	public void onLoad()
	{
		UserCommandHandler.getInstance().registerUserCommandHandler(this);
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}
}