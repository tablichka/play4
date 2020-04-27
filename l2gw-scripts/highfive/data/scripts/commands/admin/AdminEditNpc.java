package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import static ru.l2gw.extensions.scripts.Functions.show;

public class AdminEditNpc extends AdminBase
{
	private static final AdminCommandDescription[] ADMIN_COMMANDS =
			{
					new AdminCommandDescription("admin_edit_npc", "usage: //edit_npc [npcId]"),
					new AdminCommandDescription("admin_cast_skill", "usage: //cast_skill <skillId> <level>"),
					new AdminCommandDescription("admin_show_droplist", "usage: //show_droplist [npcId]"),
					new AdminCommandDescription("admin_debug_ai", null),
					new AdminCommandDescription("admin_close_window", null),
					new AdminCommandDescription("admin_direction", null)
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access Denied.");
			return false;
		}

		if(command.equals("admin_edit_npc"))
		{
			try
			{
				int npcId = Integer.valueOf(args[0]);
				showNpcProperties(activeChar, L2ObjectsStorage.getByNpcId(npcId));
			}
			catch(Exception e)
			{
				if(activeChar.getTarget() != null && activeChar.getTarget() instanceof L2NpcInstance)
					showNpcProperties(activeChar, (L2NpcInstance) activeChar.getTarget());
				else
				{
					activeChar.sendMessage("Wrong usage: target mob and type //edit_npc or use //edit_npc <npcid>");
					return false;
				}
			}
		}
		else if(command.equals("admin_cast_skill"))
		{
			try
			{
				int skillId = Integer.valueOf(args[0]);
				int skillLevel = Integer.valueOf(args[1]);
				L2Skill skill = SkillTable.getInstance().getInfo(skillId, skillLevel);
				if(skill != null && activeChar.getTarget() != null && activeChar.getTarget().isMonster())
				{
					System.out.println("Target: " + activeChar.getTarget() + " should cast skill: " + skill + " on target: " + activeChar);
					L2MonsterInstance mob = (L2MonsterInstance) activeChar.getTarget();
					mob.setTarget(activeChar);
					mob.doCast(skill, skill.getAimingTarget(mob), true);
				}
			}
			catch(Exception e)
			{
				activeChar.sendMessage("Wrong usage: target mob and type //cast_skill <skillId> <skillLevel>");
				return false;
			}
		}
		else if(command.equals("admin_show_droplist"))
		{
			int npcId = 0;
			try
			{
				npcId = Integer.parseInt(args[0]);
			}
			catch(Exception e)
			{
				if(activeChar.getTarget() != null && activeChar.getTarget() instanceof L2NpcInstance)
				{
					L2NpcInstance npc = (L2NpcInstance) activeChar.getTarget();
					showNpcDropList(activeChar, npc.getNpcId());
					return true;
				}

				activeChar.sendMessage("Usage: target mob and type //show_droplist or type //show_droplist <npc_id>");
				return false;
			}

			if(npcId > 0)
				showNpcDropList(activeChar, npcId);
			else
			{
				activeChar.sendMessage("Usage: target mob and type //show_droplist or type //show_droplist <npc_id>");
				return false;
			}
		}
		else if(command.startsWith("admin_debug_ai"))
		{
			if(activeChar.getTarget() != null && activeChar.getTarget() instanceof L2NpcInstance)
			{
				L2NpcInstance npc = (L2NpcInstance) activeChar.getTarget();
				((DefaultAI) npc.getAI()).setDebug(!((DefaultAI) npc.getAI()).isDebug());
				activeChar.sendMessage("Debug for: " + npc.getName() + " turned " + (((DefaultAI) npc.getAI()).isDebug() ? "on." : "off."));
			}
			else
				activeChar.sendMessage("Debug AI: no target");
		}
		else if(command.startsWith("admin_direction"))
		{
			L2Object target = activeChar.getTarget();
			if(target == null)
			{
				Functions.sendSysMessage(activeChar, "Select a target.");
				return false;
			}

			Functions.sendSysMessage(activeChar, "angle: " + String.format("%.02f", activeChar.getDirection(target)));
		}

		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}

	private void showNpcProperties(final L2Player activeChar, L2NpcInstance npc)
	{

		String content = Files.read("data/html/admin/editnpc.htm");

		if(content != null)
		{
			content = content.replaceFirst("%npcId%", String.valueOf(npc.getTemplate().npcId));
			content = content.replaceFirst("%class%", String.valueOf(npc.getClass().getSimpleName().replaceFirst("L2", "").replaceFirst("Instance", "")));
			content = content.replaceFirst("%id%", String.valueOf(npc.getNpcId()));
			content = content.replaceFirst("%spawn%", String.valueOf(npc.getSpawn() != null ? npc.getSpawn().getId() : "0"));
			content = content.replaceFirst("%respawn%", String.valueOf(npc.getSpawn() != null ? Util.formatTime(npc.getSpawn().getRespawnDelay()) : "0"));
			content = content.replaceFirst("%walkSpeed%", String.valueOf(npc.getWalkSpeed()));
			content = content.replaceFirst("%evs%", String.valueOf(npc.getEvasionRate(null)));
			content = content.replaceFirst("%acc%", String.valueOf(npc.getAccuracy()));
			content = content.replaceFirst("%crt%", String.valueOf(npc.getCriticalHit(null, null)));
			content = content.replaceFirst("%aspd%", String.valueOf(npc.getPAtkSpd()));
			content = content.replaceFirst("%cspd%", String.valueOf(npc.getMAtkSpd()));
			content = content.replaceFirst("%loc%", String.valueOf(npc.getSpawn() != null ? npc.getSpawn().getLocation() : "0"));
			content = content.replaceFirst("%dist%", String.valueOf((int) npc.getDistance3D(activeChar)));
			content = content.replaceFirst("%spReward%", String.valueOf(npc.getSpReward()));
			content = content.replaceFirst("%STR%", String.valueOf(npc.getSTR()));
			content = content.replaceFirst("%DEX%", String.valueOf(npc.getDEX()));
			content = content.replaceFirst("%CON%", String.valueOf(npc.getCON()));
			content = content.replaceFirst("%INT%", String.valueOf(npc.getINT()));
			content = content.replaceFirst("%WIT%", String.valueOf(npc.getWIT()));
			content = content.replaceFirst("%MEN%", String.valueOf(npc.getMEN()));
			content = content.replaceFirst("%xyz%", npc.getLoc().getX() + " " + npc.getLoc().getY() + " " + npc.getLoc().getZ());
			content = content.replaceFirst("%heading%", String.valueOf(npc.getLoc().getHeading()));
			content = content.replaceFirst("%ai_type%", npc.getAI().getL2ClassShortName());
			content = content.replaceFirst("%name%", npc.getName());
			content = content.replaceFirst("%level%", String.valueOf(npc.getLevel()));
			content = content.replaceFirst("%factionId%", npc.getFactionId().equals("") ? "none" : npc.getFactionId());
			content = content.replaceFirst("%aggro%", String.valueOf(npc.getAggroRange()));
			content = content.replaceFirst("%maxHp%", String.valueOf(npc.getMaxHp()));
			content = content.replaceFirst("%maxMp%", String.valueOf(npc.getMaxMp()));
			content = content.replaceFirst("%curHp%", String.valueOf((int) npc.getCurrentHp()));
			content = content.replaceFirst("%curMp%", String.valueOf((int) npc.getCurrentMp()));
			content = content.replaceFirst("%pDef%", String.valueOf(npc.getPDef(null)));
			content = content.replaceFirst("%mDef%", String.valueOf(npc.getMDef(null, null)));
			content = content.replaceFirst("%pAtk%", String.valueOf(npc.getPAtk(null)));
			content = content.replaceFirst("%mAtk%", String.valueOf(npc.getMAtk(null, null)));
			content = content.replaceFirst("%expReward%", String.valueOf(npc.getExpReward()));
			content = content.replaceFirst("%runSpeed%", String.valueOf(npc.getRunSpeed()));
			content = content.replaceFirst("%AI%", String.valueOf(npc.getAI()) + "<br1>active: " + npc.getAI().isActive() + "<br1>intention: " + npc.getAI().getIntention());
		}
		else
			content = "<html><head><body>File not found: data/html/admin/editnpc.htm</body></html>";
		show(content, activeChar);
		return;
	}

	private void showNpcDropList(final L2Player activeChar, int npcId)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		L2NpcTemplate npcData = L2ObjectsStorage.getByNpcId(npcId).getTemplate();
		if(npcData == null)
		{
			activeChar.sendMessage("unknown npc template id" + npcId);
			return;
		}
		adminReply.setHtml(NpcTable.generateDroplist(npcData));
		activeChar.sendPacket(adminReply);
	}
}
