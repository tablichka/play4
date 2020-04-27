package commands.admin;

import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2SkillLearn;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.serverpackets.SkillCoolTime;
import ru.l2gw.gameserver.serverpackets.SkillList;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SkillTreeTable;

import java.util.Collection;

/**
 * This class handles following admin commands: - show_skills - remove_skills -
 * skill_list - skill_index - add_skill - remove_skill - get_skills -
 * reset_skills - give_all_skills
 */
public class AdminSkill extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = {
			new AdminCommandDescription("admin_show_skills", null),
			new AdminCommandDescription("admin_remove_skills", null),
			new AdminCommandDescription("admin_skill_list", null),
			new AdminCommandDescription("admin_skill_index", null),
			new AdminCommandDescription("admin_add_skill", "usage: //add_skill <skillId> <level>"),
			new AdminCommandDescription("admin_setskill", "usage: //setskill <skillId> <level>"),
			new AdminCommandDescription("admin_remove_skill", "usage: //remove_skill <skillId>"),
			new AdminCommandDescription("admin_get_skills", null),
			new AdminCommandDescription("admin_reset_skills", null),
			new AdminCommandDescription("admin_give_all_skills", null),
			new AdminCommandDescription("admin_restore_skills", null),
			new AdminCommandDescription("admin_remove_all_skills", null)};

	private static Collection<L2Skill> adminSkills;

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_show_skills"))
		{
			L2Player target = activeChar.getTargetPlayer();

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			showSkillsPage(activeChar, target);
		}	
		else if(command.equals("admin_remove_skills"))
		{
			L2Player target = activeChar.getTargetPlayer();

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			removeSkillsPage(activeChar, target);
		}	
		else if(command.equals("admin_skill_list"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			AdminHelpPage.showHelpPage(activeChar, "skills.htm");
		}	
		else if(command.equals("admin_skill_index"))
			try
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				String val = args[0];
				AdminHelpPage.showHelpPage(activeChar, "skills/" + val + ".htm");
			}
			catch(Exception e)
			{}
		else if(command.equals("admin_add_skill") || command.equals("admin_setskill"))
			try
			{
				L2Player target = activeChar.getTargetPlayer();
				
				if(target == null)
				{
					Functions.sendSysMessage(activeChar, "Select a player target.");
					return false;
				}

				if(args.length < 2)
				{
					if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					showSkillsPage(activeChar, target);
					return true;
				}
				
				int skillId = Integer.parseInt(args[0]);
				int level = Integer.parseInt(args[1]);
				
				if(!AdminTemplateManager.checkCommand(command, activeChar, target, skillId, level, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}
				
				adminAddSkill(activeChar, target, skillId, level);

				if(command.equals("admin_add_skill"))
					showSkillsPage(activeChar, target);

				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.startsWith("admin_remove_skill"))
			try
			{
				L2Player target = activeChar.getTargetPlayer();

				if(target == null)
				{
					Functions.sendSysMessage(activeChar, "Select a player target.");
					return false;
				}

				int idval = Integer.parseInt(args[0]);

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, idval, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				adminRemoveSkill(activeChar, target, idval);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.equals("admin_get_skills"))
		{
			L2Player target = activeChar.getTargetPlayer();

			if(target == null)
			{
				Functions.sendSysMessage(activeChar, "Select a player target.");
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			adminGetSkills(activeChar, target);
		}
		else if(command.equals("admin_reset_skills"))
		{
			L2Player target = activeChar.getTargetPlayer();

			if(target == null)
			{
				Functions.sendSysMessage(activeChar, "Select a player target.");
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			adminResetSkills(activeChar, target);
		}
		else if(command.equals("admin_give_all_skills"))
		{
			L2Player target = activeChar.getTargetPlayer();

			if(target == null)
			{
				Functions.sendSysMessage(activeChar, "Select a player target.");
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			adminGiveAllSkills(activeChar, target);
		}
		else if(command.equals("admin_restore_skills"))
		{
			L2Player target = activeChar.getTargetPlayer();

			if(target == null)
			{
				Functions.sendSysMessage(activeChar, "Select a player target.");
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			adminRestoreSkills(activeChar, target);
		}
		else if(command.equals("admin_remove_all_skills"))
		{
			L2Player target = activeChar.getTargetPlayer();

			if(target == null)
			{
				Functions.sendSysMessage(activeChar, "Select a player target.");
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			int i = 0;
			for(L2Skill skill : target.getAllSkills())
			{
				target.removeSkill(skill, true);
				i++;
			}

			activeChar.sendMessage("Removed " + i + " skills from " + target.getName());
		}

		return true;
	}

	/**
	 * This function will give all the skills that the gm target can have at its
	 * level to the traget
	 *
	 * @param activeChar: the gm char
	 */
	private void adminGiveAllSkills(L2Player activeChar, L2Player target)
	{
		int unLearnable = 0;
		int skillCounter = 0;
		GArray<L2SkillLearn> skills = SkillTreeTable.getInstance().getAvailableSkills(target, target.getClassId());
		while(skills.size() > unLearnable)
		{
			unLearnable = 0;
			for(L2SkillLearn s : skills)
			{
				L2Skill sk = SkillTable.getInstance().getInfo(s.getId(), s.getLevel());
				if(sk == null || !sk.getCanLearn(target.getClassId()))
				{
					unLearnable++;
					continue;
				}
				if(target.getSkillLevel(sk.getId()) == -1)
					skillCounter++;
				target.addSkill(sk, true);
			}
			skills = SkillTreeTable.getInstance().getAvailableSkills(target, target.getClassId());
		}

		target.sendMessage("Admin gave you " + skillCounter + " skills.");
		target.sendPacket(new SkillList(target));
		activeChar.sendMessage("You gave " + skillCounter + " skills to " + target.getName());

		logGM.info(activeChar.toFullString() + " " + "gave " + skillCounter + " skills to " + target.getName());
	}

	// ok
	private void removeSkillsPage(L2Player activeChar, L2Player target)
	{
		if(target == null)
		{
			activeChar.sendPacket(Msg.INVALID_TARGET);
			return;
		}

		Collection<L2Skill> skills = target.getAllSkills();

		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center>Character Selection Menu</center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_show_skills\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><br>");
		replyMSG.append("<center>Editing character: " + target.getName() + "</center>");
		replyMSG.append("<br><table width=270><tr><td>Lv: " + target.getLevel() + " " + target.getTemplate().className + "</td></tr></table>");
		replyMSG.append("<br><table width=270><tr><td>Note: Dont forget that modifying players skills can</td></tr>");
		replyMSG.append("<tr><td>ruin the game...</td></tr></table>");
		replyMSG.append("<br><center>Click on the skill you wish to remove:</center>");
		replyMSG.append("<br><table width=270>");
		replyMSG.append("<tr><td width=80>Name:</td><td width=60>Level:</td><td width=40>Id:</td></tr>");
		for(L2Skill element : skills)
			replyMSG.append("<tr><td width=80><a action=\"bypass -h admin_remove_skill " + element.getId() + "\">" + element.getName() + "</a></td><td width=60>" + element.getLevel() + "</td><td width=40>" + element.getId() + "</td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("<br><center><table>");
		replyMSG.append("Remove custom skill:");
		replyMSG.append("<tr><td>Id: </td>");
		replyMSG.append("<td><edit var=\"id_to_remove\" width=110></td></tr>");
		replyMSG.append("</table></center>");
		replyMSG.append("<center><button value=\"Remove skill\" action=\"bypass -h admin_remove_skill $id_to_remove\" width=110 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></center>");
		replyMSG.append("<br><center><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15></center>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	// ok
	private void showSkillsPage(L2Player activeChar, L2Player target)
	{
		if(target == null)
		{
			activeChar.sendPacket(Msg.INVALID_TARGET);
			return;
		}

		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center>Character Selection Menu</center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><br>");
		replyMSG.append("<center>Editing character: " + target.getName() + "</center>");
		replyMSG.append("<br><table width=270><tr><td>Lv: " + target.getLevel() + " " + target.getTemplate().className + "</td></tr></table>");
		replyMSG.append("<br><table width=270><tr><td>Note: Dont forget that modifying players skills can</td></tr>");
		replyMSG.append("<tr><td>ruin the game...</td></tr></table>");
		replyMSG.append("<br><center><table>");
		replyMSG.append("<tr><td><button value=\"Add skills\" action=\"bypass -h admin_skill_list\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Get skills\" action=\"bypass -h admin_get_skills\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("<tr><td><button value=\"Delete skills\" action=\"bypass -h admin_remove_skills\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Reset skills\" action=\"bypass -h admin_reset_skills\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("<tr><td><button value=\"Give All Skills\" action=\"bypass -h admin_give_all_skills\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("</table></center>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	private void adminGetSkills(L2Player activeChar, L2Player target)
	{
		if(target.getName().equals(activeChar.getName()))
			target.sendMessage("There is no point in doing it on your character.");
		else
		{
			adminSkills = activeChar.getAllSkills();
			for(L2Skill element : adminSkills)
				activeChar.removeSkill(element, true);

			Collection<L2Skill> skills = target.getAllSkills();
			for(L2Skill element : skills)
				activeChar.addSkill(element, true);

			activeChar.sendMessage("You now have all the skills of  " + target.getName() + ".");
		}
		showSkillsPage(activeChar, target);
	}

	private void adminResetSkills(L2Player activeChar, L2Player target)
	{
		if(adminSkills == null)
			activeChar.sendMessage("You must first get the skills of someone to do this.");
		else
		{
			Collection<L2Skill> skills = target.getAllSkills();
			for(L2Skill element : skills)
				target.removeSkill(element, true);
			for(L2Skill s : activeChar.getAllSkills())
				target.addSkill(s, true);
			for(L2Skill element : skills)
				activeChar.removeSkill(element, true);
			for(L2Skill element : adminSkills)
				activeChar.addSkill(element);
			target.sendMessage("[GM]" + activeChar.getName() + " has updated your skills.");
			activeChar.sendMessage("You now have all your skills back.");
			adminSkills = null;
		}
		showSkillsPage(activeChar, target);
	}

	private void adminAddSkill(L2Player activeChar, L2Player target, int skillId, int level)
	{
			L2Skill skill = SkillTable.getInstance().getInfo(skillId, level);

			if(skill != null)
			{
				target.sendMessage("Admin gave you the skill " + skill.getName() + ".");
				target.addSkill(skill, true);
				target.sendPacket(new SkillList(target));

				// Admin information
				activeChar.sendMessage("You gave the skill " + skill.getName() + " to " + target.getName() + ".");

				logGM.info(activeChar.toFullString() + " " + "gave the skill " + skill.getName() + " to " + target.getName());
			}
			else
				activeChar.sendMessage("Error: there is no such skill.");
	}

	private void adminRemoveSkill(L2Player activeChar, L2Player target, int idval)
	{
		L2Skill skill = SkillTable.getInstance().getInfo(idval, target.getSkillLevel(idval));

		if(skill != null)
		{
			target.sendMessage("Admin removed the skill " + skill.getName() + ".");
			target.removeSkill(skill, true);

			// Admin information
			activeChar.sendMessage("You removed the skill " + skill.getName() + " from " + target.getName() + ".");
			target.sendPacket(new SkillList(target));
			logGM.info(activeChar.toFullString() + " " + "removed the skill " + skill.getName() + " from " + target.getName());
		}
		else
			activeChar.sendMessage("Error: there is no such skill.");

		removeSkillsPage(activeChar, target); // Back to start
	}

	private void adminRestoreSkills(L2Player activeChar, L2Player target)
	{
		target.enableAllSkills();
		target.sendPacket(new SkillCoolTime(target));
		target.sendMessage("[GM]" + activeChar.getName() + " has restore your skills.");
		activeChar.sendMessage("All skills restored for char " + target.getName());
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}