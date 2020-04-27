package commands.voiced;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.instancemanager.CoupleManager;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.Couple;
import ru.l2gw.gameserver.serverpackets.*;
import ru.l2gw.gameserver.tables.PetDataTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static ru.l2gw.gameserver.model.zone.L2Zone.ZoneType.*;

public class Wedding implements IVoicedCommandHandler, ScriptFile
{
	private static String[] _voicedCommands = { "divorce", "engage", "gotolove" };

	/*
	 * (non-Javadoc)
	 *
	 * @see ru.l2gw.gameserver.handler.IUserCommandHandler#useUserCommand(int,
	 *      ru.l2gw.gameserver.model.L2Player)
	 */
	public boolean useVoicedCommand(String command, L2Player activeChar, String target)
	{
		if(command.startsWith("engage"))
			return engage(activeChar);
		else if(command.startsWith("divorce"))
			return divorce(activeChar);
		else if(command.startsWith("gotolove"))
			return goToLove(activeChar);
		return false;
	}

	public boolean divorce(L2Player activeChar)
	{
		if(activeChar.getPartnerId() == 0)
			return false;

		int _partnerId = activeChar.getPartnerId();
		long AdenaAmount = 0;

		if(activeChar.isMaried())
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.Divorced", activeChar));
			AdenaAmount = Math.abs(activeChar.getAdena() / 100 * Config.WEDDING_DIVORCE_COSTS - 10);
			activeChar.reduceAdena("Divorse", AdenaAmount, null, true);
		}
		else
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.Disengaged", activeChar));

		activeChar.setMaried(false);
		activeChar.setPartnerId(0);
		Couple couple = CoupleManager.getInstance().getCouple(activeChar.getCoupleId());
		couple.divorce();
		couple = null;

		L2Player partner = L2ObjectsStorage.getPlayer(_partnerId);

		if(partner != null)
		{
			partner.setPartnerId(0);
			if(partner.isMaried())
				partner.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.PartnerDivorce", partner));
			else
				partner.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.PartnerDisengage", partner));
			partner.setMaried(false);

			// give adena
			if(AdenaAmount > 0)
				partner.addAdena("Devorse", AdenaAmount, null, true);
		}
		return true;
	}

	public boolean engage(L2Player activeChar)
	{
		// check target
		if(activeChar.getTarget() == null)
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.NoneTargeted", activeChar));
			return false;
		}
		// check if target is a L2Player
		if(!activeChar.getTarget().isPlayer())
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.OnlyAnotherPlayer", activeChar));
			return false;
		}
		// check if player is already engaged
		if(activeChar.getPartnerId() != 0)
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.AlreadyEngaged", activeChar));
			if(Config.WEDDING_PUNISH_INFIDELITY)
			{
				activeChar.startAbnormalEffect(L2Skill.AbnormalVisualEffect.bighead); // give player a Big
				// Head
				// lets recycle the sevensigns debuffs
				int skillId;

				int skillLevel = 1;

				if(activeChar.getLevel() > 40)
					skillLevel = 2;

				if(activeChar.isMageClass())
					skillId = 4361;
				else
					skillId = 4362;

				L2Skill skill = SkillTable.getInstance().getInfo(skillId, skillLevel);

				if(activeChar.getEffectBySkill(skill) == null)
				{
					skill.applyEffects(activeChar, activeChar, false);
					SystemMessage sm = new SystemMessage(SystemMessage.YOU_CAN_FEEL_S1S_EFFECT);
					sm.addSkillName(skillId, (short) skillLevel);
					activeChar.sendPacket(sm);
				}
			}
			return false;
		}

		L2Player ptarget = (L2Player) activeChar.getTarget();

		// check if player target himself
		if(ptarget.getObjectId() == activeChar.getObjectId())
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.EngagingYourself", activeChar));
			return false;
		}

		if(ptarget.isMaried())
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.PlayerAlreadyMarried", activeChar));
			return false;
		}

		if(ptarget.getPartnerId() != 0)
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.PlayerAlreadyEngaged", activeChar));
			return false;
		}

		if(ptarget.isEngageRequest())
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.PlayerAlreadyAsked", activeChar));
			return false;
		}

		if(ptarget.getPartnerId() != 0)
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.PlayerAlreadyEngaged", activeChar));
			return false;
		}

		if(ptarget.getSex() == activeChar.getSex() && !Config.WEDDING_SAMESEX)
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.SameSex", activeChar));
			return false;
		}

		// check if target has player on friendlist
		boolean FoundOnFriendList = false;
		int objectId;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rset = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();

			statement = con.prepareStatement("SELECT friend_id FROM character_friends WHERE char_id=?");
			statement.setInt(1, ptarget.getObjectId());
			rset = statement.executeQuery();

			while(rset.next())
			{
				objectId = rset.getInt("friend_id");
				if(objectId == activeChar.getObjectId())
				{
					FoundOnFriendList = true;
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, statement, rset);
		}

		if(!FoundOnFriendList)
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.NotInFriendlist", activeChar));
			return false;
		}

		ptarget.setEngageRequest(true, activeChar.getObjectId());
		// ptarget.sendMessage("Player "+activeChar.getName()+" wants to engage with you.");
		ptarget.sendPacket(new ConfirmDlg(SystemMessage.S1_S2, 60000, 4).addString("Player " + activeChar.getName() + " asking you to engage. Do you want to start new relationship?"));
		return true;
	}

	public boolean goToLove(L2Player activeChar)
	{
		if(!activeChar.isMaried())
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.YoureNotMarried", activeChar));
			return false;
		}

		if(activeChar.getPartnerId() == 0)
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.PartnerNotInDB", activeChar));
			return false;
		}

		L2Player partner = L2ObjectsStorage.getPlayer(activeChar.getPartnerId());
		if(partner == null)
		{
			activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.PartnerOffline", activeChar));
			return false;
		}

		if(partner.isInOlympiadMode() || activeChar.isMovementDisabled() || activeChar.isMuted() || activeChar.isInOlympiadMode() || activeChar.isInDuel() || activeChar.isCombatFlagEquipped() || activeChar.getReflection() > 0)
		{
			activeChar.sendMessage(new CustomMessage("common.TryLater", activeChar));
			return false;
		}

		if(activeChar.isInParty() && activeChar.getParty().isInDimensionalRift() || partner.isInParty() && partner.getParty().isInDimensionalRift())
		{
			activeChar.sendMessage(new CustomMessage("common.TryLater", activeChar));
			return false;
		}

		if(activeChar.getTeleMode() != 0 || activeChar.getUnstuck() != 0)
		{
			activeChar.sendMessage(new CustomMessage("common.TryLater", activeChar));
			return false;
		}
		if(activeChar.isFlying() || (activeChar.getMountEngine().isMounted() && activeChar.getMountEngine().getMountNpcId() == PetDataTable.WYVERN_ID))
		{
			activeChar.sendMessage(new CustomMessage("common.TryLater", activeChar));
			return false;
		}
		// "Нельзя вызывать персонажей в/из зоны свободного PvP"
		// "в зоны осад"
		// "на Олимпийский стадион"
		// "в зоны определенных рейд-боссов и эпик-боссов"
		if(partner.isInZoneBattle() || partner.isInSiege() || partner.isCombatFlagEquipped() || partner.isInZone(no_summon)|| partner.isInZone(no_restart) || partner.isInZone(no_escape) || partner.getX() < -166168 || partner.getReflection() > 0 || activeChar.isInZoneBattle() || activeChar.isInZone(no_restart)  || activeChar.isInZone(no_summon) || activeChar.isInZone(no_escape) || activeChar.getX() < -166168)
		{
			activeChar.sendPacket(new SystemMessage(SystemMessage.YOUR_TARGET_IS_IN_AN_AREA_WHICH_BLOCKS_SUMMONING));
			return false;
		}

		/*
		 if(activeChar.getVarB("jailed?"))
		 {
		 activeChar.sendMessage("You are jailed.", "Вы не можете это сделать, находясь в тюрьме.");
		 return false;
		 }
		 */

		activeChar.abortCast();
		activeChar.abortAttack();
		activeChar.sendActionFailed();
		activeChar.broadcastPacket(new StopMove(activeChar));
		activeChar.block();
		activeChar.setUnstuck(1);

		int teleportTimer = Config.WEDDING_TELEPORT_INTERVAL * 1000;

		if(!activeChar.reduceAdena("Teleport", Config.WEDDING_TELEPORT_PRICE, null, true))
			return false;

		activeChar.sendMessage(new CustomMessage("scripts.commands.voiced.Wedding.Teleport", activeChar).addNumber(teleportTimer / 60000));
		activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);

		// SoE Animation section
		activeChar.broadcastPacket(new MagicSkillUse(activeChar, activeChar, 1050, 1, teleportTimer, 0));
		activeChar.sendPacket(new SetupGauge(0, teleportTimer));
		// End SoE Animation section

		// continue execution later
		ThreadPoolManager.getInstance().scheduleGeneral(new EscapeFinalizer(activeChar, partner.getLoc()), teleportTimer);
		return true;
	}

	static class EscapeFinalizer implements Runnable
	{
		private L2Player _activeChar;
		private Location _loc;

		EscapeFinalizer(L2Player activeChar, Location loc)
		{
			_activeChar = activeChar;
			_loc = loc;
		}

		public void run()
		{
			if(_activeChar.isDead() || _activeChar.getUnstuck() == 0)
				return;
			_activeChar.unblock();
			_activeChar.setUnstuck(0);
			_activeChar.teleToLocation(_loc);
		}
	}

	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}

	public void onLoad()
	{
		if(Config.ALLOW_WEDDING)
			VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}