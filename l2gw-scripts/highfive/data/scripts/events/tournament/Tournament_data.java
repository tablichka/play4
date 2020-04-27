package events.tournament;

import javolution.util.FastMap;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.database.mysql;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.model.L2Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Tournament_data extends Functions implements ScriptFile
{
	private static FastMap<Integer, Team> teams = new FastMap<Integer, Team>();

	public static Team getTeamById(Integer id)
	{
		return teams.get(id);
	}

	public static Team getTeamByName(String name)
	{
		for(Team team : teams.values())
			if(name.equalsIgnoreCase(team.getName()))
				return team;
		return null;
	}

	public static FastMap<Integer, Team> getTeams()
	{
		return teams;
	}

	public void onLoad()
	{
		loadTeams();
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public static void loadTeams()
	{
		teams = new FastMap<Integer, Team>();
		Connection con = null;
		PreparedStatement offline = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			offline = con.prepareStatement("SELECT * FROM tournament_teams");
			rs = offline.executeQuery();
			while(rs.next())
			{
				Integer team_id = rs.getInt("team_id");
				Team team = teams.get(team_id);
				if(team == null)
				{
					team = new Team();
					teams.put(team_id, team);
				}
				team.setName(rs.getString("team_name"));
				team.setId(team_id);
				team.setCategory(rs.getInt("category"));

				Integer leader = rs.getInt("leader");
				if(leader == 1)
					team.setLeader(rs.getInt("obj_id"));
				else
					team.addMember(rs.getInt("obj_id"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, offline, rs);
		}
	}

	public static Boolean createTeam(String name, L2Player leader)
	{
		Team team = new Team();
		team.setName(name);
		team.setId(IdFactory.getInstance().getNextId());
		team.setLeader(leader.getObjectId());
		team.setCategory(getCategory((int) leader.getLevel()));
		Integer type = getPlayerType(leader.getObjectId());
		Connection con = null;
		PreparedStatement insertion = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			insertion = con.prepareStatement("INSERT INTO tournament_teams (obj_id, type, team_id, team_name, leader, category) VALUES (?,?,?,?,1,?) ");
			insertion.setInt(1, leader.getObjectId());
			insertion.setInt(2, type);
			insertion.setInt(3, team.getId());
			insertion.setString(4, name);
			insertion.setInt(5, team.getCategory());
			insertion.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			DbUtils.closeQuietly(con, insertion);
		}
		teams.put(team.getId(), team);
		return true;
	}

	public static boolean register(L2Player player, Team team)
	{
		Connection con = null;
		PreparedStatement insertion = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			insertion = con.prepareStatement("INSERT INTO tournament_teams (obj_id, type, team_id, team_name, leader, category) VALUES (?,?,?,?,0,?) ");
			insertion.setInt(1, player.getObjectId());
			insertion.setInt(2, getPlayerType(player.getObjectId()));
			insertion.setInt(3, team.getId());
			insertion.setString(4, team.getName());
			insertion.setInt(5, team.getCategory());
			insertion.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			DbUtils.closeQuietly(con, insertion);
		}
		team.addMember(player.getObjectId());
		return true;
	}

	public static Boolean deleteTeam(Integer team_id)
	{
		teams.remove(team_id);
		return mysql.set("DELETE FROM tournament_teams WHERE team_id = " + team_id);
	}

	public static Boolean deleteMember(Integer team_id, Integer obj_id)
	{
		teams.get(team_id).removeMember(obj_id);
		return mysql.set("DELETE FROM tournament_teams WHERE obj_id = " + obj_id);
	}

	public static MemberInfo getMemberInfo(Integer obj_id)
	{
		MemberInfo info = new MemberInfo();
		String sql = "SELECT A.char_name, A.online, B.level, B.class_id, C.ClassName FROM characters AS A ";
		sql += "LEFT JOIN character_subclasses AS B ON (A.obj_Id = B.char_obj_id) ";
		sql += "LEFT JOIN char_templates AS C ON (B.class_id = C.ClassId) ";
		sql += "WHERE B.active = 1 AND obj_id = ?";

		Connection con = null;
		PreparedStatement offline = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			offline = con.prepareStatement(sql);
			offline.setInt(1, obj_id);
			rs = offline.executeQuery();
			if(rs.next())
			{
				if(rs.getInt("online") == 1)
					info.online = true;
				info.name = rs.getString("char_name");
				info.level = rs.getInt("level");
				info.class_id = rs.getInt("class_id");
				info.class_name = rs.getString("ClassName");
				info.category = getCategory(info.level);
				return info;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, offline, rs);
		}
		return null;
	}

	public static Integer getPlayerType(Integer obj_id)
	{
		Connection con = null;
		PreparedStatement offline = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			offline = con.prepareStatement("SELECT B.type FROM character_subclasses AS A LEFT JOIN tournament_class_list AS B ON (A.class_id = B.class_id) WHERE A.active = 1 AND A.char_obj_id = ?");
			offline.setInt(1, obj_id);
			rs = offline.executeQuery();
			if(rs.next())
				return rs.getInt("type");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, offline, rs);
		}
		return null;
	}

	public static String getPlayerName(Integer obj_id)
	{
		Connection con = null;
		PreparedStatement offline = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			offline = con.prepareStatement("SELECT char_name FROM characters WHERE obj_Id = ?");
			offline.setInt(1, obj_id);
			rs = offline.executeQuery();
			if(rs.next())
				return rs.getString("char_name");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, offline, rs);
		}
		return null;
	}

	public static Integer getCategory(Integer level)
	{
		if(level >= 20 && level <= 29)
			return 1;
		else if(level >= 30 && level <= 39)
			return 2;
		else if(level >= 40 && level <= 51)
			return 3;
		else if(level >= 52 && level <= 61)
			return 4;
		else if(level >= 62 && level <= 75)
			return 5;
		else if(level >= 76)
			return 6;
		return 0;
	}

	public static boolean createTournamentTable(int category)
	{
		mysql.set("DELETE FROM tournament_table");

		Connection con1 = null;
		Connection con2 = null;
		PreparedStatement statement1 = null, statement2 = null;
		ResultSet rs = null;
		try
		{
			con1 = DatabaseFactory.getInstance().getConnection();
			con2 = DatabaseFactory.getInstance().getConnection();

			statement1 = con1.prepareStatement("select * from tournament_teams where leader = 1 and category = ? and status = 1");

			statement1.setInt(1, category);
			rs = statement1.executeQuery();

			int i = 0;
			while(rs.next())
				i++;
			rs.beforeFirst();

			if(i == 0)
				return true;
			else if(i == 1)
			{
				rs.next();
				int team_id = rs.getInt("team_id");
				Team team = teams.get(team_id);
				Tournament_battle.announce("Команда " + team.getName() + " выиграла турнир в категории " + category);
				Tournament_battle.giveItemsToWinner(team);
				Tournament_battle.endTournament();
				// турнир окончен
				return true;
			}

			while(rs.next())
			{
				statement2 = con2.prepareStatement("insert into tournament_table (category, team1id, team1name, team2id, team2name) VALUES (?,?,?,?,?)");

				statement2.setInt(1, category);
				statement2.setInt(2, rs.getInt("team_id"));
				statement2.setString(3, rs.getString("team_name"));

				if(rs.next())
				{
					statement2.setInt(4, rs.getInt("team_id"));
					statement2.setString(5, rs.getString("team_name"));
				}
				else
				{
					statement2.setInt(4, 0);
					statement2.setString(5, "");
				}

				statement2.executeUpdate();
				DbUtils.closeQuietly(statement2);
			}

			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Tournament_battle.announce("Произошла ошибка, турнир завершен.");
			return true;
		}
		finally
		{
			DbUtils.closeQuietly(con1, statement1, rs);
			DbUtils.closeQuietly(con2);
		}
	}

	public static boolean fillNextTeams(int category)
	{
		Connection con = null;
		PreparedStatement offline = null;
		ResultSet rs = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			offline = con.prepareStatement("SELECT * FROM tournament_table WHERE category = ?");
			offline.setInt(1, category);
			rs = offline.executeQuery();
			while(rs.next())
			{
				Integer team1id = rs.getInt("team1id");
				Integer team2id = rs.getInt("team2id");

				if(team2id == 0)
				{
					// первая выиграла
					teamWin(team1id);
					removeRecordFromTournamentTable(team1id);
					continue;
				}

				Team team1 = teams.get(team1id);
				Team team2 = teams.get(team2id);

				if(team1 == null)
					continue;

				if(team2 == null)
				{
					// первая выиграла
					teamWin(team1id);
					removeRecordFromTournamentTable(team1id);
					continue;
				}

				if(team1.getOnlineCount() == 0 && team2.getOnlineCount() > 0)
				{
					// первая дисквалифицирована, вторая выиграла
					disqualifyTeam(team1id);
					teamWin(team2id);
					removeRecordFromTournamentTable(team1id);
					continue;
				}

				if(team1.getOnlineCount() > 0 && team2.getOnlineCount() == 0)
				{
					// вторая дисквалифицирована, первая выиграла
					disqualifyTeam(team2id);
					teamWin(team1id);
					removeRecordFromTournamentTable(team1id);
					continue;
				}

				if(team1.getOnlineCount() > 0 && team2.getOnlineCount() == 0)
				{
					// обе дисквалифицированы, проверить следующие
					disqualifyTeam(team1id);
					disqualifyTeam(team2id);
					removeRecordFromTournamentTable(team1id);
					continue;
				}

				// обе готовы, начать бой
				Tournament_battle.team1 = team1;
				Tournament_battle.team2 = team2;
				return true;
			}

			// Закончились бои в этом цикле
			return false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, offline, rs);
		}
		return false;
	}

	public static void removeRecordFromTournamentTable(int team1id)
	{
		mysql.set("DELETE FROM tournament_table WHERE team1id = " + team1id);
	}

	public static void disqualifyTeam(int teamId)
	{
		if(teamId > 0)
		{
			mysql.set("UPDATE tournament_teams SET status = 0 WHERE team_id = " + teamId);
			mysql.set("UPDATE tournament_teams SET losts = losts + 1 WHERE team_id = " + teamId);
			Team team = teams.get(teamId);
			if(team != null)
				Tournament_battle.announce("Команда " + team.getName() + " дисквалифицирована.");
		}
	}

	public static void teamWin(int teamId)
	{
		if(teamId > 0)
		{
			mysql.set("UPDATE tournament_teams SET wins = wins + 1 WHERE team_id = " + teamId);
			Team team = teams.get(teamId);
			if(team != null)
				Tournament_battle.announce("Команда " + team.getName() + " выиграла бой.");
		}
	}

	public static void teamLost(int teamId)
	{
		if(teamId > 0)
		{
			mysql.set("UPDATE tournament_teams SET status = 0 WHERE team_id = " + teamId);
			mysql.set("UPDATE tournament_teams SET losts = losts + 1 WHERE team_id = " + teamId);
		}
	}
}