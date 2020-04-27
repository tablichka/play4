package events.MightOfGods;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.serverpackets.SkillList;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.StatsSet;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class MightOfGods extends Functions implements ScriptFile, ICommunityBoardHandler, IOnDieHandler, IItemHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;

    private static final int MOG_CANCEL_PRICE[] = {1, 3, 5, 10, 50, 100};
    private static final int MOG_CANCEL_ALL_PRICE[] = {1, 2, 3, 5, 7, 50};
    private static final int MOG_CANCEL_ITEM_ID = 4037;
	private static final int MAX_SKILLS_IN_GROUP = 5;

    private static double LOW1_CHANCE = 100;
	private static double LOW2_CHANCE = 50;
	private static double LOW3_CHANCE = 25;
	private static double LOW4_CHANCE = 13;

	private static double MIDDLE_CHANCE = 20;
	private static double HIGH_CHANCE = 100;

	private static int LOW1_ITEM = 10254;//1st
	private static int LOW2_ITEM = 10255;//2nd
	private static int LOW3_ITEM = 10256;//3th
	private static int LOW4_ITEM = 10257;//4th

	private static int MIDDLE_ITEM = 10258;//5th
	private static int HIGH_ITEM = 10259;//6th

	private static boolean _active = false;

    private static final Map<Integer, Integer> items_map = new HashMap<>();
    static
    {
        items_map.put(10254, 1);
        items_map.put(10255, 2);
        items_map.put(10256, 3);
        items_map.put(10257, 4);
        items_map.put(10258, 5);
        items_map.put(10259, 6);

        items_map.put(23254, 1);
        items_map.put(23255, 2);
        items_map.put(23256, 3);
        items_map.put(23257, 4);
        items_map.put(23258, 5);
        items_map.put(23259, 6);
    }

	public void onLoad()
	{
        ItemHandler.getInstance().registerItemHandler(this);

        if(isActive())
		{
			_active = true;
			_log.info("Loaded Event: L2 MIGHT OF GODS [state: activated]");
			if(Config.COMMUNITYBOARD_ENABLED)
				CommunityBoardManager.getInstance().registerHandler(this);

            Connection con = null;
            PreparedStatement stmt = null;

            try
            {
                con = DatabaseFactory.getInstance().getConnection();
                stmt = con.prepareStatement("DELETE FROM event_mog_skills WHERE owner_id NOT IN (SELECT obj_id FROM characters)");
                stmt.execute();
            }
            catch(Exception e)
            {
                _log.error("Error: " + e, e);
            }
            finally
            {
                DbUtils.closeQuietly(con, stmt);
            }
		}
		else
			_log.info("Loaded Event: L2 MIGHT OF GODS [state: deactivated]");
	}

	public void onReload()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
			CommunityBoardManager.getInstance().unregisterHandler(this);

		onLoad();
	}

	public void onShutdown()
	{
		if(isActive())
			_log.info("Loaded Event: L2 MIGHT OF GODS [state: deactivated]");

	}

	public String[] getBypassCommands()
	{
		return new String[]{"_bbsmogquestion", "_bbsmoglistgroup_", "_bbsmogcancelgroup_", "_bbsmogcancelskill_", "_bbsmoglist"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		if("bbsmogquestion".equals(cmd))
        {
			player.scriptRequest("Все эвентовые скиллы со данного класса будут удалены!!! Продолжить?", "events.MightOfGods.MightOfGods:cancelSkills", new Object[0]);
            CommunityBoardManager.getInstance().getCommunityHandler("_bbsaccount").onBypassCommand(player, "_bbsaccount");
        }
        else if("bbsmoglistgroup".equals(cmd))
        {
            int group = Integer.parseInt(st.nextToken());

            Map<Integer, String> tpls;
            tpls = Util.parseTemplate(Files.read("data/scripts/events/MightOfGods/html/list_group.htm", player, false));
            String html = tpls.get(0);
            html = html.replace("<?price_all?>", String.valueOf(MOG_CANCEL_ALL_PRICE[group - 1]));

            Connection con = null;
            PreparedStatement stmt = null;
            ResultSet rset = null;

            try
            {
                con = DatabaseFactory.getInstance().getConnection();
                stmt = con.prepareStatement("SELECT skill_id, skill_lvl FROM event_mog_skilltable ems WHERE skill_id IN (SELECT skill_id FROM event_mog_skills WHERE owner_id=? and class_id=?) and event_group=?");
                stmt.setInt(1, player.getObjectId());
                stmt.setInt(2, player.getActiveClass());
                stmt.setInt(3, group);
                rset = stmt.executeQuery();

                String rows = "";
                while(rset.next())
                {
                    L2Skill skill = SkillTable.getInstance().getInfo(rset.getInt("skill_id"), rset.getInt("skill_lvl"));
                    rows += tpls.get(1).replace("<?skill_name?>", skill.getName());
                    rows = rows.replace("<?skill_mog_id?>", String.valueOf(rset.getInt("skill_id")));
                    rows = rows.replace("<?price?>", String.valueOf(MOG_CANCEL_PRICE[group - 1]));
                    rows = rows.replace("<?skill_icon?>", String.format("icon.skill%04d", skill.getId()));
                }

                html = html.replace("<?rows?>", rows);
                html = html.replace("<?mog_group?>", String.valueOf(group));
            }
            catch(Exception e)
            {
                _log.error("Error: " + e, e);
            }
            finally
            {
                DbUtils.closeQuietly(con, stmt, rset);
            }

            ShowBoard.separateAndSend(html, player);
        }
        else if("bbsmogcancelskill".equals(cmd))
        {
            Integer group = Integer.parseInt(st.nextToken());
            Integer mog_id = Integer.parseInt(st.nextToken());

            player.scriptRequest("Вы уверены, что хотите отменить это умение?", "events.MightOfGods.MightOfGods:cancelSkill", new Integer[] {group, mog_id});

            onBypassCommand(player, "_bbsmoglistgroup_" + group);
        }
        else if("bbsmogcancelgroup".equals(cmd))
        {
            Integer group = Integer.parseInt(st.nextToken());

            player.scriptRequest("Вы уверены, что хотите отменить все умения в этой группе?", "events.MightOfGods.MightOfGods:cancelGroup", new Integer[] {group});

            onBypassCommand(player, "_bbsmoglistgroup_" + group);
        }
        else if("bbsmoglist".equals(cmd))
        {
            Map<Integer, String> tpls;
            tpls = Util.parseTemplate(Files.read("data/scripts/events/MightOfGods/html/list_skills.htm", player, false));
            String html = tpls.get(0);

            Connection con = null;
            PreparedStatement stmt = null;
            ResultSet rset = null;

            try
            {
                con = DatabaseFactory.getInstance().getConnection();
                stmt = con.prepareStatement("SELECT skill_id, skill_lvl, event_group FROM event_mog_skilltable ems WHERE skill_id IN (SELECT skill_id FROM event_mog_skills WHERE owner_id=? and class_id=?) ORDER BY event_group DESC");
                stmt.setInt(1, player.getObjectId());
                stmt.setInt(2, player.getActiveClass());
                rset = stmt.executeQuery();

                String rows = "";
                while(rset.next())
                {
                    L2Skill skill = SkillTable.getInstance().getInfo(rset.getInt("skill_id"), rset.getInt("skill_lvl"));
                    int group = rset.getInt("event_group");

                    rows += tpls.get(1).replace("<?skill_name?>", skill.getName());
                    rows = rows.replace("<?skill_mog_id?>", String.valueOf(rset.getInt("skill_id")));
                    rows = rows.replace("<?price?>", String.valueOf(MOG_CANCEL_PRICE[group - 1]));
                    rows = rows.replace("<?skill_icon?>", String.format("icon.skill%04d", skill.getId()));
                    rows = rows.replace("<?skill_group?>", String.valueOf(group));
                }

                html = html.replace("<?rows?>", rows);
            }
            catch(Exception e)
            {
                _log.error("Error: " + e, e);
            }
            finally
            {
                DbUtils.closeQuietly(con, stmt, rset);
            }

            ShowBoard.separateAndSend(html, player);
        }
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}

	private static boolean isActive()
	{
		return ServerVariables.getString("might_of_gods", "off").equalsIgnoreCase("on");
	}

	public static void cancelSkills()
	{
		L2Player player = (L2Player) self;
		deleteEventSkills(player);
	}

	public static void cancelSkill(Integer group, Integer mog_id)
	{
		L2Player player = (L2Player) self;

        if(group - 1 < 0 || group - 1 >= MOG_CANCEL_PRICE.length)
        {
            CommunityBoardManager.getInstance().getCommunityHandler("_bbsmoglistgroup_" + group).onBypassCommand(player, "_bbsmoglistgroup_" + group);
            return;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rset = null;

        try
        {
            player.skillEnchantLock.lock();
            con = DatabaseFactory.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT skill_id, skill_lvl, event_group FROM event_mog_skilltable ems WHERE skill_id = ?");
            stmt.setInt(1, mog_id);
            rset = stmt.executeQuery();

            if(rset.next())
            {
                group = rset.getInt("event_group");

                int price = MOG_CANCEL_PRICE[group - 1];
                if(player.getItemCountByItemId(MOG_CANCEL_ITEM_ID) < price)
                {
                    player.sendMessage("Не достаточно предметов для отмены скила.");
                    CommunityBoardManager.getInstance().getCommunityHandler("_bbsmoglistgroup_" + group).onBypassCommand(player, "_bbsmoglistgroup_" + group);
                    return;
                }

                if(player.destroyItemByItemId("CancelMogSkill", MOG_CANCEL_ITEM_ID, price, null, true))
                {
                    L2Skill skill = SkillTable.getInstance().getInfo(rset.getInt("skill_id"), rset.getInt("skill_lvl"));

                    stmt2 = con.prepareStatement("DELETE FROM event_mog_skills WHERE owner_id = ? and class_id = ? and skill_id = ?");
                    stmt2.setInt(1, player.getObjectId());
                    stmt2.setInt(2, player.getActiveClass());
                    stmt2.setInt(3, rset.getInt("skill_id"));

                    stmt2.execute();
                    DbUtils.closeQuietly(stmt2);

                    player.removeSkill(skill, false);
                    player.sendPacket(new SkillList(player));

                    player.sendMessage("Скил " + skill.getName() + " успешно отменен.");
                }
            }
            else
                player.sendMessage("Не удалось отменить скилл.");

        }
        catch(Exception e)
        {
            _log.error("Error: " + e, e);
        }
        finally
        {
            player.skillEnchantLock.unlock();
            DbUtils.closeQuietly(con, stmt, rset);
        }

        CommunityBoardManager.getInstance().getCommunityHandler("_bbsmoglistgroup_" + group).onBypassCommand(player, "_bbsmoglistgroup_" + group);
    }

    public static void cancelGroup(Integer group)
    {
        L2Player player = (L2Player) self;

        if(group - 1 < 0 || group - 1 >= MOG_CANCEL_PRICE.length)
        {
            CommunityBoardManager.getInstance().getCommunityHandler("_bbsmoglistgroup_" + group).onBypassCommand(player, "_bbsmoglistgroup_" + group);
            return;
        }

        int price = MOG_CANCEL_ALL_PRICE[group - 1];
        if(player.getItemCountByItemId(MOG_CANCEL_ITEM_ID) < price)
        {
            player.sendMessage("Не достаточно предметов для отмены скилов");
            CommunityBoardManager.getInstance().getCommunityHandler("_bbsmoglistgroup_" + group).onBypassCommand(player, "_bbsmoglistgroup_" + group);
            return;
        }

        Connection con = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rset = null;

        try
        {
            player.skillEnchantLock.lock();
            con = DatabaseFactory.getInstance().getConnection();
            stmt = con.prepareStatement("SELECT skill_id, skill_lvl, event_group FROM event_mog_skilltable ems WHERE skill_id IN (SELECT skill_id FROM event_mog_skills WHERE owner_id=? and class_id=?) and event_group=?");
            stmt.setInt(1, player.getObjectId());
            stmt.setInt(2, player.getActiveClass());
            stmt.setInt(3, group);
            rset = stmt.executeQuery();

            boolean f = true;
            while(rset.next())
            {
                if(f)
                {
                    if(player.destroyItemByItemId("CancelMogSkill", MOG_CANCEL_ITEM_ID, price, null, true))
                        f = false;
                    else
                        break;
                }

                L2Skill skill = SkillTable.getInstance().getInfo(rset.getInt("skill_id"), rset.getInt("skill_lvl"));

                stmt2 = con.prepareStatement("DELETE FROM event_mog_skills WHERE owner_id = ? and class_id = ? and skill_id = ?");
                stmt2.setInt(1, player.getObjectId());
                stmt2.setInt(2, player.getActiveClass());
                stmt2.setInt(3, rset.getInt("skill_id"));

                stmt2.execute();
                DbUtils.closeQuietly(stmt2);

                player.removeSkill(skill, false);
                player.sendMessage("Скил " + skill.getName() + " успешно отменен.");
            }
            if(!f)
                player.sendPacket(new SkillList(player));

            DbUtils.closeQuietly(stmt);
        }
        catch(Exception e)
        {
            _log.error("Error: " + e, e);
        }
        finally
        {
            player.skillEnchantLock.unlock();
            DbUtils.closeQuietly(con, stmt, rset);
        }

        CommunityBoardManager.getInstance().getCommunityHandler("_bbsmoglistgroup_" + group).onBypassCommand(player, "_bbsmoglistgroup_" + group);
    }

	public void startEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(!isActive())
		{
			ServerVariables.set("might_of_gods", "on");
			_log.info("Event 'L2 MIGHT OF GODS' started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.MightOfGods.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'L2 MIGHT OF GODS' already started.");

		_active = true;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	public void stopEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
		if(isActive())
		{
			ServerVariables.unset("might_of_gods");
			_log.info("Event 'MIGHT OF GODS' stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.MightOfGods.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'MIGHT OF GODS' not started.");

		_active = false;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	public static void OnPlayerEnter(L2Player player)
	{
		if(_active)
			Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.MightOfGods.AnnounceEventStarted", null);
    }

    public static void onPlayerSkillsRestored(L2Player player)
    {
        if(_active && player.getVar("offline") == null)
        {
            Connection con = null;
            PreparedStatement statement = null;
            ResultSet rset = null;

            try
            {
                con = DatabaseFactory.getInstance().getConnection();
                statement = con.prepareStatement("SELECT cs.skill_id, cs.skill_lvl, es.event_group FROM event_mog_skills cs, event_mog_skilltable es WHERE cs.owner_id=? AND cs.class_id=? AND cs.skill_id = es.skill_id");
                statement.setInt(1, player.getObjectId());
                statement.setInt(2, player.getActiveClass());

                rset = statement.executeQuery();
				Map<Integer, Integer> count = new HashMap<>();
                while(rset.next())
				{
					Integer c = count.get(rset.getInt("event_group"));
					if(c == null)
						c = 0;

					c++;

					if(c > MAX_SKILLS_IN_GROUP)
					{
						_log.warn("Event 'Might of Gods': cheater found: " + player + " got skill: " + SkillTable.getInstance().getInfo(rset.getInt("skill_id"), rset.getInt("skill_lvl")) + " removed.");

						PreparedStatement st2 = con.prepareStatement("DELETE FROM event_mog_skills WHERE owner_id=? and class_id=? and skill_id=?");
						st2.setInt(1, player.getObjectId());
						st2.setInt(2, player.getActiveClass());
						st2.setInt(3, rset.getInt("skill_id"));
						st2.execute();

						DbUtils.closeQuietly(st2);
					}
					else
						player.addSkill(SkillTable.getInstance().getInfo(rset.getInt("skill_id"), rset.getInt("skill_lvl")), false);

					count.put(rset.getInt("event_group"), c);
				}
            }
            catch(Exception e)
            {
                _log.error("Error loading MoG skills for " + player + " " + e, e);
            }
            finally
            {
                DbUtils.closeQuietly(con, statement, rset);
            }
        }
	}

    public static void onPlayerSkillAdd(L2Player player, L2Skill newSkill, L2Skill oldSkill)
    {
        if(_active)
        {
            Connection con = null;
            PreparedStatement stmt = null;
            PreparedStatement stmt2 = null;
            ResultSet rset = null;

            try
            {
                con = DatabaseFactory.getInstance().getConnection();
                stmt = con.prepareStatement("SELECT skill_id, skill_lvl FROM event_mog_skills WHERE owner_id=? AND class_id=? AND skill_id=?");
                stmt.setInt(1, player.getObjectId());
                stmt.setInt(2, player.getActiveClass());
                stmt.setInt(3, newSkill.getId());
                rset = stmt.executeQuery();

                if(rset.next() && newSkill.getLevel() != rset.getInt("skill_lvl"))
                {
                    stmt2 = con.prepareStatement("UPDATE event_mog_skills SET skill_lvl = ? WHERE owner_id = ? AND class_id = ? AND skill_id = ?");
                    stmt2.setInt(1, newSkill.getLevel());
                    stmt2.setInt(2, player.getObjectId());
                    stmt2.setInt(3, player.getActiveClass());
                    stmt2.setInt(4, rset.getInt("skill_id"));
                    stmt2.execute();
                }
            }
            catch(Exception e)
            {
                _log.error("Error storing MoG skills for " + player + " " + e, e);
            }
            finally
            {
                DbUtils.closeQuietly(stmt2);
                DbUtils.closeQuietly(con, stmt, rset);
            }
        }
    }

    public static void onPlayerClassChange(L2Player player, short oldClass, short newClass)
    {
        if(_active)
        {
            Connection con = null;
            PreparedStatement stmt = null;

            try
            {
                con = DatabaseFactory.getInstance().getConnection();
                stmt = con.prepareStatement("UPDATE event_mog_skills SET class_id=? WHERE owner_id=? AND class_id=?");
                stmt.setInt(1, newClass);
                stmt.setInt(2, player.getObjectId());
                stmt.setInt(3, oldClass);
                stmt.execute();
            }
            catch(Exception e)
            {
                _log.error("Error updating class MoG " + player + " " + e, e);
            }
            finally
            {
                DbUtils.closeQuietly(con, stmt);
            }
        }
    }

	@Override
	public void onDie(L2Character cha, L2Character killer)
	{
		if(_active && cha.isMonster() && killer != null && killer.getPlayer() != null && Math.abs(cha.getLevel() - killer.getLevel()) < 10)
		{
			if(!cha.isRaid())
			{

				if(killer.getLevel() <= 20)
				{
					if(Rnd.chance(LOW1_CHANCE))
					{
						L2ItemInstance item = ItemTable.getInstance().createItem("MightOfGods", LOW1_ITEM, 1, killer.getPlayer(), cha);

						((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
					}
				}
				else if(killer.getLevel() > 20 && killer.getLevel() <= 40)
				{
					if(Rnd.chance(LOW2_CHANCE))
					{
						L2ItemInstance item = ItemTable.getInstance().createItem("MightOfGods", LOW2_ITEM, 1, killer.getPlayer(), cha);

						((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
					}
				}
				else if(killer.getLevel() > 40 && killer.getLevel() <= 61)
				{
					if(Rnd.chance(LOW3_CHANCE))
					{
						L2ItemInstance item = ItemTable.getInstance().createItem("MightOfGods", LOW3_ITEM, 1, killer.getPlayer(), cha);
						item.setCount(1);

						((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
					}
				}
				else if(killer.getLevel() > 61 && killer.getLevel() <= 85)
				{
					if(Rnd.chance(LOW4_CHANCE))
					{
						L2ItemInstance item = ItemTable.getInstance().createItem("MightOfGods", LOW4_ITEM, 1, killer.getPlayer(), cha);

						((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
					}
				}

			}
			else
			{
				if(!cha.isBoss())
					if(Rnd.chance(MIDDLE_CHANCE))
					{
						L2ItemInstance item = ItemTable.getInstance().createItem("MightOfGods", MIDDLE_ITEM, 1, killer.getPlayer(), cha);

						((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
					}

				if(cha.isBoss())
					if(Rnd.chance(HIGH_CHANCE))
					{
						L2ItemInstance item = ItemTable.getInstance().createItem("MightOfGods", HIGH_ITEM, 1, killer.getPlayer(), cha);

						((L2NpcInstance) cha).dropItem(killer.getPlayer(), item);
					}
			}
		}
	}

    public static boolean addEventSkill(L2Player player, int skillGroup)
    {
        if(player == null)
            return false;

        Connection con = null;
        ResultSet rs = null;
        PreparedStatement st = null;

        _log.debug("add mog skill group: " + skillGroup);

        List<StatsSet> skills = new LinkedList<>();

        try
        {
            player.skillEnchantLock.lock();
            con = DatabaseFactory.getInstance().getConnection();
            st = con.prepareStatement("SELECT ems.id, ems.skill_id, ems.skill_lvl, ems.probability FROM event_mog_skilltable ems WHERE skill_id NOT IN (SELECT skill_id FROM event_mog_skills WHERE owner_id=? AND class_id=?) and event_group=?");
            st.setInt(1, player.getObjectId());
            st.setInt(2, player.getActiveClass());
            st.setInt(3, skillGroup);
            rs = st.executeQuery();

            double sum = 0;

            while(rs.next())
            {
                _log.debug("check existing skill: " + rs.getInt("skill_id") + " level: " + player.getSkillLevel(rs.getInt("skill_id")));
                if(player.getSkillLevel(rs.getInt("skill_id")) > 0)
                    continue;

                StatsSet info = new StatsSet();
                info.set("id", rs.getInt("id"));
                info.set("skill", rs.getInt("skill_id") + "-" + rs.getInt("skill_lvl"));
                info.set("chance", rs.getInt("probability"));
                skills.add(info);
                sum += rs.getInt("probability");
            }

            double k = 100 / sum;

            st = con.prepareStatement("SELECT COUNT(*) cnt FROM event_mog_skills ems LEFT JOIN event_mog_skilltable emst ON ems.skill_id=emst.skill_id WHERE owner_id=? and class_id=? and event_group=?");
            st.setInt(1, player.getObjectId());
            st.setInt(2, player.getActiveClass());
            st.setInt(3, skillGroup);
            rs = st.executeQuery();
            rs.next();

            if(skills.size() < 1 || rs.getInt("cnt") >= MAX_SKILLS_IN_GROUP)
            {
                sendMessage("Вы изучили все доступные умения этой группы для текущего класса", player);
                return false;
            }

            double chance = Rnd.get(100);
            _log.debug("Start chance: " + chance);

            while(skills.size() > 0)
            {
                StatsSet info = skills.remove(Rnd.get(skills.size()));
                _log.debug("try skill: " + info.getSkill("skill").getName() + " chance: " + chance + " skill chance: " + (info.getInteger("chance") * k));
                if(chance < info.getInteger("chance") * k)
                {
                    L2Skill skill = info.getSkill("skill");
                    st = con.prepareStatement("INSERT INTO event_mog_skills (owner_id,skill_id,skill_lvl,class_id) VALUES (?,?,?,?)");
                    st.setInt(1, player.getObjectId());
                    st.setInt(2, skill.getId());
                    st.setInt(3, skill.getLevel());
                    st.setInt(4, player.getActiveClass());
                    st.execute();

                    player.addSkill(skill, false);
                    player.sendPacket(new SkillList(player));
                    sendMessage("Вы получили новое умение Богов: " + skill.getName(), player);
                    return true;
                }
                chance -= info.getInteger("chance") * k;
            }

            sendMessage("В этот раз Боги не наградили Вас своей силой. Попробуйте еще раз!", player);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            player.skillEnchantLock.unlock();
            DbUtils.closeQuietly(con, st, rs);
        }

        return false;
    }

    public static boolean deleteEventSkills(L2Player player)
    {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement st = null;
        PreparedStatement st2 = null;
        L2Skill skill;

        try
        {
            player.skillEnchantLock.lock();
            con = DatabaseFactory.getInstance().getConnection();
            st = con.prepareStatement("SELECT skill_id, skill_lvl FROM event_mog_skills WHERE owner_id=? and class_id=?");
            st.setInt(1, player.getObjectId());
            st.setInt(2, player.getActiveClass());
            rs = st.executeQuery();

            while(rs.next())
            {
                skill = SkillTable.getInstance().getInfo(rs.getInt("skill_id"), rs.getInt("skill_lvl"));
                player.removeSkill(skill, true);
                player.removeSkillFromShortCut(rs.getInt("skill_id"));
            }

            player.sendPacket(new SkillList(player));

            st2 = con.prepareStatement("DELETE FROM event_mog_skills WHERE owner_id=? and class_id=?");
            st2.setInt(1, player.getObjectId());
            st2.setInt(2, player.getActiveClass());
            st2.execute();

            DbUtils.closeQuietly(st2);

            sendMessage("Силы богов покидают ваше тело", player);

            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            player.skillEnchantLock.unlock();
            DbUtils.closeQuietly(con, st, rs);
        }

        return false;
    }

    @Override
    public boolean useItem(L2Playable playable, L2ItemInstance item)
    {
        if(!ServerVariables.getString("might_of_gods", "off").equalsIgnoreCase("on") || !(playable instanceof L2Player))
            return false;

        L2Player player = (L2Player) playable;

        if(!player.isQuestContinuationPossible(false))
        {
            player.sendPacket(Msg.YOUR_INVENTORY_IS_FULL);
            player.sendPacket(new SystemMessage(SystemMessage.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addItemName(item.getItemId()));
            return false;
        }

        if(addEventSkill(player, items_map.get(item.getItemId())))
            removeItem(player, item.getItemId(), 1);

        return true;
    }

    @Override
    public int[] getItemIds()
    {
        int[] items = new int[items_map.size()];
		int i = 0;
		for(Integer itemId : items_map.keySet())
        	items[i++] = itemId;

        return items;
    }
}
