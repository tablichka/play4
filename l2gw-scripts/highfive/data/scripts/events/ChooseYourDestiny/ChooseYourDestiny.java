package events.ChooseYourDestiny;

import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2SubClass;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.tables.CharTemplateTable;
import ru.l2gw.gameserver.tables.SkillTreeTable;
import ru.l2gw.util.Files;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author: rage
 * @date: 24.07.2009 15:11:19
 */
public class ChooseYourDestiny extends Functions implements ScriptFile
{
	private static boolean _active = false;

	public void onLoad()
	{
		if(isActive())
		{
			_active = true;
			_log.info("Loaded Event: Choose Your Destiny [state: activated]");
		}
		else
			_log.info("Loaded Event: Choose Your Destiny [state: deactivated]");
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	private static boolean isActive()
	{
		return ServerVariables.getString("ChooseYourDes", "off").equalsIgnoreCase("on");
	}

	/**
	 * Запускает эвент
	 */
	public void startEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(!isActive())
		{
			ServerVariables.set("ChooseYourDes", "on");
			_active = true;
			_log.info("Event: Choose Your Destiny started.");
		}
		else
			player.sendMessage("Event: Choose Your Destiny already started.");
		_active = true;
		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	 * Останавливает эвент
	 */
	public void stopEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
		if(isActive())
		{
			ServerVariables.unset("ChooseYourDes");
			_log.info("Event: Choose Your Destiny stopped.");
		}
		else
			player.sendMessage("Event: Choose Your Destiny not started.");
		_active = false;
		show(Files.read("data/html/admin/events.htm", player), player);
	}

	public static String appendDialog()
	{
		if(!_active)
			return "";
		return "<br><a action=\"bypass -h scripts_events.ChooseYourDestiny.ChooseYourDestiny:beginDialog\">Choose Your Destiny</a>";
	}

	public static void beginDialog()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		String html;
		html = Files.read("data/scripts/events/ChooseYourDestiny/begin.htm", player);
		show(html, player);
	}

	public static void chooseDialog()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		if(!checkConditions(player))
			return;

		String html;
		html = Files.read("data/scripts/events/ChooseYourDestiny/choose-sub.htm", player);
		html = html.replace("%classId%", String.valueOf(player.getBaseClass()));
		String sublist = "";

		for(L2SubClass sub : player.getSubClasses().values())
			if(!sub.isBase())
				sublist += "<a action=\"bypass -h scripts_events.ChooseYourDestiny.ChooseYourDestiny:checkSub " + sub.getSlot() + "\"><ClassId>" + sub.getClassId() + "</ClassId></a><br>";

		html = html.replace("%subclasslist%", sublist.isEmpty() ? "no subclasses" : sublist);

		show(html, player);
	}

	public static void checkSub(String[] args)
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		L2SubClass sub = null;
		for(L2SubClass s : player.getSubClasses().values())
			if(s.getSlot() == Integer.parseInt(args[0]))
			{
				sub = s;
				break;
			}

		if(sub == null)
			return;

		if(sub.getSlot() == 0)
			return;

		if(sub.getLevel() < 75)
		{
			show(Files.read("data/scripts/events/ChooseYourDestiny/no-level.htm", player), player);
			return;
		}

		if(ClassId.values()[sub.getClassId()].getRace() == Race.elf || ClassId.values()[sub.getClassId()].getRace() == Race.darkelf)
			for(L2SubClass s : player.getSubClasses().values())
			{
				if(ClassId.values()[sub.getClassId()].getRace() == Race.elf && ClassId.values()[s.getClassId()].getRace() == Race.darkelf ||
					ClassId.values()[sub.getClassId()].getRace() == Race.darkelf && ClassId.values()[s.getClassId()].getRace() == Race.elf)
				{
					show(Files.read("data/scripts/events/ChooseYourDestiny/no-elfdeld.htm", player), player);
					return;
				}
			}

		if(sub.getClassId() == ClassId.inspector.getId() || sub.getClassId() == ClassId.judicator.getId())
		{
			show(Files.read("data/scripts/events/ChooseYourDestiny/no-inspector.htm", player), player);
			return;
		}

		String html;
		html = Files.read("data/scripts/events/ChooseYourDestiny/change-sub.htm", player);
		html = html.replace("%className%", CharTemplateTable.getClassNameById(sub.getClassId()));
		html = html.replace("%slot%", String.valueOf(sub.getSlot()));
		show(html, player);
	}

	public static void changeSub(String[] args)
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		if(!player.destroyItemByItemId("ChooseYourDes", 4037, 20, npc, true))
			return;

		L2SubClass sub = null;
		for(L2SubClass s : player.getSubClasses().values())
			if(s.getSlot() == Integer.parseInt(args[0]))
			{
				sub = s;
				break;
			}

		if(sub == null)
			return;

		if(sub.getSlot() == 0)
			return;

		player.unsetVar("cert-specific");
		for(int i = 1; i < 4; i++)
		{
			player.unsetVar("cert-" + i + "-" + 65);
			player.unsetVar("cert-" + i + "-" + 70);
			player.unsetVar("cert-" + i + "-" + 75);
			player.unsetVar("cert-" + i + "-" + 80);
		}

		SkillTreeTable.getInstance().deleteSubclassSkills(player);

		int items[] = {10280, 10281, 10282, 10283, 10284, 10285, 10286, 10287, 10289, 10288, 10290, 10292, 10291, 10294, 10293, 10612};

		for(Integer itemId : items)
		{
			L2ItemInstance item = player.getInventory().getItemByItemId(itemId);
			if(item != null)
				player.destroyItem("CancelCertification", item.getObjectId(), item.getCount(), npc, true);
		}

		for(L2Skill skill : player.getAllSkills())
			if(skill.isForgotten() && skill.getMagicLevel() > 80)
				player.removeSkill(skill, true);

		int objectId = player.getObjectId();
		int slot = sub.getSlot();
		Connection con = null;
		try
		{
			String sql = "UPDATE character_subclasses SET slot = 0, active = 1, isBase = 1 WHERE char_obj_id = ? and class_id = ?";
			con = DatabaseFactory.getInstance().getConnection();

			PreparedStatement statement = con.prepareStatement(sql);
			statement.setInt(1, objectId);
			statement.setInt(2, sub.getClassId());
			statement.execute();
			statement.close();

			sql = "UPDATE character_subclasses SET slot = ?, active = 0, isBase = 0 WHERE char_obj_id = ? and class_id = ?";

			statement = con.prepareStatement(sql);
			statement.setInt(1, slot);
			statement.setInt(2, objectId);
			statement.setInt(3, player.getClassId().getId());
			statement.execute();
			statement.close();

			if(player.isNoble())
			{
				sql = "UPDATE olymp_nobles SET class_id = ?, points = 0, wins = 0, loos = 0 WHERE char_id = ?";

				statement = con.prepareStatement(sql);
				statement.setInt(1, sub.getClassId());
				statement.setInt(2, objectId);
				statement.execute();
				statement.close();
			}

			if(player.getRace() == Race.kamael)
			{
				boolean changeSex = false;
				if(player.getSex() == 1 && (sub.getClassId() == ClassId.berserker.getId() || sub.getClassId() == ClassId.doombringer.getId())) // Female
					changeSex = true;
				else if(player.getSex() == 0 && (sub.getClassId() == ClassId.arbalester.getId() || sub.getClassId() == ClassId.trickster.getId()))
					changeSex = true;

				if(changeSex)
				{
					sql = "UPDATE characters SET sex = ? WHERE obj_id = ?";
					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 1 : 0);
					statement.setInt(2, objectId);
					statement.execute();
					statement.close();

					sql = "UPDATE character_subclasses SET class_id = ? WHERE char_obj_id = ? and class_id = ?";
					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 129 : 128);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 128 : 129);
					statement.execute();
					statement.close();

					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 133 : 132);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 132 : 133);
					statement.execute();
					statement.close();

					sql = "UPDATE character_effects_save SET class_index = ? WHERE char_obj_id = ? and class_index = ?";
					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 129 : 128);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 128 : 129);
					statement.execute();
					statement.close();

					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 133 : 132);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 132 : 133);
					statement.execute();
					statement.close();

					sql = "UPDATE character_hennas SET class_index = ? WHERE char_obj_id = ? and class_index = ?";
					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 129 : 128);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 128 : 129);
					statement.execute();
					statement.close();

					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 133 : 132);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 132 : 133);
					statement.execute();
					statement.close();

					sql = "UPDATE character_shortcuts SET class_index = ? WHERE char_obj_id = ? and class_index = ?";
					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 129 : 128);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 128 : 129);
					statement.execute();
					statement.close();

					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 133 : 132);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 132 : 133);
					statement.execute();
					statement.close();

					sql = "UPDATE character_skills SET class_index = ? WHERE char_obj_id = ? and class_index = ?";
					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 129 : 128);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 128 : 129);
					statement.execute();
					statement.close();

					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 133 : 132);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 132 : 133);
					statement.execute();
					statement.close();

					sql = "UPDATE character_skills_save SET class_index = ? WHERE char_obj_id = ? and class_index = ?";
					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 129 : 128);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 128 : 129);
					statement.execute();
					statement.close();

					statement = con.prepareStatement(sql);
					statement.setInt(1, player.getSex() == 0 ? 133 : 132);
					statement.setInt(2, objectId);
					statement.setInt(3, player.getSex() == 0 ? 132 : 133);
					statement.execute();
					statement.close();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				con.close();
				player.setVar("ChooseYourDes", "1");
				player.logout(false, false, true);
			}
			catch(Exception e)
			{
			}
		}
	}

	public static boolean checkConditions(L2Player player)
	{
		if(player.getItemCountByItemId(4037) < 20)
		{
			show(Files.read("data/scripts/events/ChooseYourDestiny/no-col.htm", player), player);
			return false;
		}
//		if(player.getVar("ChooseYourDes") != null && !player.getVar("ChooseYourDes").isEmpty())
//		{
//			show(Files.read("data/scripts/events/ChooseYourDestiny/no-already.htm", player), player);
//			return false;
//		}
		else if(player.isSubClassActive())
		{
			show(Files.read("data/scripts/events/ChooseYourDestiny/no-activesub.htm", player), player);
			return false;
		}
		else if(player.getLevel() < 75 || player.getLevel() > 80)
		{
			show(Files.read("data/scripts/events/ChooseYourDestiny/no-level.htm", player), player);
			return false;
		}
		else if(player.isHero())
		{
			show(Files.read("data/scripts/events/ChooseYourDestiny/no-hero.htm", player), player);
			return false;
		}
		else if(player.getInventory().getSize() > 80)
		{
			show(Files.read("data/scripts/events/ChooseYourDestiny/no-inventory.htm", player), player);
			return false;
		}

		ClassId classId = player.getClassId();
		if(classId.getLevel() < 3)
		{
			show(Files.read("data/scripts/events/ChooseYourDestiny/no-3class.htm", player), player);
			return false;
		}

		if(classId.getLevel() == 4)
			classId = classId.getParent(player.getSex());

		if(classId.equals(ClassId.warsmith) || classId.equals(ClassId.overlord))
		{
			show(Files.read("data/scripts/events/ChooseYourDestiny/no-class.htm", player), player);
			return false;
		}

		return true;
	}
/*
	public static String DialogAppend_30175(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30195(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30862(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30474(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30910(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31285(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31974(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31324(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31334(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30699(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30176(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30174(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30854(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30115(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31755(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31996(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31331(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30694(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30187(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30849(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30109(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30900(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31276(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31965(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31321(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30689(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30676(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30845(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30511(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30894(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31269(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31958(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31314(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30685(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30512(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30677(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30687(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30847(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_32093(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31317(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30897(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31272(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30681(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30865(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30513(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30913(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31288(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31977(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31326(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31336(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30704(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_32221(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_32222(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_32229(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_32230(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30191(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30857(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30120(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_30905(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31279(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31968(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}

	public static String DialogAppend_31328(Integer val)
	{
		if(val != 0)
			return "";
		return appendDialog();
	}
*/
}
