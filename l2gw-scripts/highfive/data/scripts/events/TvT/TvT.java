package events.TvT;

import events.Capture.Capture;
import events.lastHero.LastHero;
import npc.model.NewbieGuideInstance;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.templates.StatsSet;
import ru.l2gw.util.Files;

public class TvT extends Functions implements ScriptFile, IOnDieHandler
{
	public L2Object self;
	public L2NpcInstance npc;

	private final static TvTEvent[] events = new TvTEvent[Config.EVENT_TvT_Config.length];

	public void onLoad()
	{
		for(int i = 0; i < events.length; i++)
			events[i] = new TvTEvent(Config.EVENT_TvT_Config[i]);

		if(Config.EVENT_TvT_Enabled)
		{
			_log.info("Loaded Event: TvT [state: activated]");
			for(TvTEvent event : events)
				event.onLoad();
		}
		else
			_log.info("Loaded Event: TvT [state: deactivated]");
	}

	public void onReload()
	{
		onLoad();
	}

	public void onShutdown()
	{
	}

	public void start(String[] param)
	{
		L2Player player = null;
		if(self != null)
		{
			player = (L2Player) self;
			if(!AdminTemplateManager.checkBoolean("eventMaster", player))
				return;
		}

		if(param.length < 1)
		{
			if(player != null)
				player.sendMessage("Please select TvT number to run.");
			return;
		}

		int type;
		try
		{
			type = Integer.parseInt(param[0]);
		}
		catch(NumberFormatException e)
		{
			if(player != null)
				player.sendMessage("Please select TvT number to run.");
			return;
		}

		type--;
		if(type < 0 || type >= events.length)
		{
			if(player != null)
				player.sendMessage("No TvT config for: " + (type + 1));
			return;
		}

		TvTEvent event = events[type];
		if(event.getStatus() == 0)
			event.start();
		else if(player != null)
			player.sendMessage("TvT " + (type + 1) + " already running, status: " + event.getStatus());
	}

	public static boolean isRegistered(L2Player player)
	{
		for(TvTEvent event : events)
			if(event.isRegistered(player))
				return true;

		return false;
	}

	public String DialogAppend_31225(Integer val)
	{
		if(val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/TvT/31225.html", player);
		}
		return "";
	}

	public void addPlayer()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		if(isRegistered(player))
		{
			player.sendMessage(new CustomMessage("scripts.events.TvT.alreadyRegistered", player));
			return;
		}
		if(LastHero.isRegistered(player) || Capture.isRegistered(player))
		{
			player.sendMessage(new CustomMessage("scripts.events.TvT.lh", player));
			return;
		}

		TvTEvent event = null;
		for(TvTEvent tvt : events)
			if(tvt.checkLevel(player))
			{
				event = tvt;
				break;
			}

		if(event == null || event.getStatus() != 1)
		{
			player.sendMessage(new CustomMessage("scripts.events.TvT.noEvent", player));
			return;
		}

		if(checkPlayerCondition(player))
		{
			event.register(player);
			player.sendMessage(new CustomMessage("scripts.events.TvT.registered", player));
		}
		else
			player.sendMessage(new CustomMessage("scripts.events.TvT.notRegistered", player));
	}

	public void supportMagic()
	{
		L2Player player = (L2Player) self;
		if(player == null || npc == null)
			return;

		TvTEvent event = null;
		for(TvTEvent tvt : events)
			if(tvt.isParticipant(player))
			{
				event = tvt;
				break;
			}

		if(event == null)
		{
			player.sendMessage(new CustomMessage("scripts.events.TvT.noEvent", player));
			return;
		}

		if(!player.isMageClass() || player.getActiveClass() == 49 || player.getActiveClass() == 50)
		{
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283246593), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283312129), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(369426433), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283377665), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283443201), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283508737), player);
			if(player.getLevel() >= 6 && player.getLevel() <= 39)
				npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283574273), player);
			if(player.getLevel() >= 40)
				npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(369098753), player);
			if(player.getLevel() >= 16 && player.getLevel() <= 34)
				npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(284295169), player);
		}
		else if(player.isMageClass())
		{
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283246593), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283312129), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(369426433), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283639809), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283705345), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283770881), player);
			npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(283836417), player);
			if(player.getLevel() >= 16 && player.getLevel() <= 34)
				npc.altUseSkill(NewbieGuideInstance.getSkillFromIndex(284295169), player);
		}
	}

	public void showStatistic()
	{
		L2Player player = (L2Player) self;
		if(player == null || npc == null)
			return;

		TvTEvent event = null;
		for(TvTEvent tvt : events)
			if(tvt.isParticipant(player))
			{
				event = tvt;
				break;
			}

		if(event == null)
		{
			player.sendMessage(new CustomMessage("scripts.events.TvT.noEvent", player));
			return;
		}

		StatsSet stat = event.getPlayerStat(player);
		String html = Files.read("data/scripts/events/TvT/tvtstat.htm", player, false);
		html = html.replace("<?kills?>", String.valueOf(stat != null ? stat.getInteger("kills", 0) : 0));
		html = html.replace("<?killed?>", String.valueOf(stat != null ? stat.getInteger("killed", 0) : 0));
		html = html.replace("<?red_kills?>", String.valueOf(event.getTeamKills(2)));
		html = html.replace("<?blue_kills?>", String.valueOf(event.getTeamKills(1)));
		stat = event.getTopKillerStat();
		html = html.replace("<?top_killer?>", stat != null ? stat.getString("name", "") : "N/A");
		html = html.replace("<?top_kills?>", String.valueOf(stat != null ? stat.getInteger("kills", 0) : 0));
		stat = event.getTopLooserStat();
		html = html.replace("<?top_killer?>", stat != null ? stat.getString("name", "") : "N/A");
		html = html.replace("<?top_deaths?>", String.valueOf(stat != null ? stat.getInteger("killed", 0) : 0));
		html = html.replace("<?top_looser?>", stat != null ? stat.getString("name", "") : "N/A");
		show(html, player);
	}

	@Override
	public void onDie(L2Character kd, L2Character kr)
	{
		if(kd instanceof L2Player && kr != null && kr.getPlayer() != null)
		{
			L2Player killer = kr.getPlayer();
			L2Player killed = (L2Player) kd;
			for(TvTEvent tvt : events)
				if(tvt.getStatus() == 3 && tvt.isParticipant(killer) && tvt.isParticipant(killed))
				{
					tvt.onDie(killed, killer);
					break;
				}
		}
	}

	public static void OnPlayerExit(L2Player player)
	{
		for(TvTEvent tvt : events)
			if(tvt.isRegistered(player))
			{
				tvt.unRegister(player);
				break;
			}
	}
}