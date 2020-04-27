package events.bountyhunters;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.arrays.GArray;

import java.util.ArrayList;

public class HuntersGuild extends Functions implements ScriptFile, IVoicedCommandHandler, IOnDieHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;

	private static final String[] _commandList = new String[] { "gettask", "declinetask" };

	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
		_log.info("Loaded Event: Bounty Hunters Guild");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public void getTask(L2Player player)
	{
		GArray<L2NpcTemplate> monsters = NpcTable.getAllOfLevel(player.getLevel());
		if(monsters == null || monsters.isEmpty())
		{
			show(new CustomMessage("scripts.events.bountyhunters.NoTargets", player), player);
			return;
		}
		ArrayList<L2NpcTemplate> targets = new ArrayList<L2NpcTemplate>();
		for(L2NpcTemplate npc : monsters)
			if(npc.type.equals("L2Monster") && !npc.title.contains("Quest Monster") && SpawnTable.getInstance().getSpawnsByNpcId(npc.npcId) != null)
				targets.add(npc);
		if(targets.isEmpty())
		{
			show(new CustomMessage("scripts.events.bountyhunters.NoTargets", player), player);
			return;
		}
		L2NpcTemplate target = targets.get(Rnd.get(targets.size()));
		int mobcount = target.level + Rnd.get(25, 50);
		player.setVar("bhMonstersId", String.valueOf(target.getNpcId()));
		player.setVar("bhMonstersNeeded", String.valueOf(mobcount));
		player.setVar("bhMonstersKilled", "0");

		int fails = player.getVar("bhfails") == null ? 0 : Integer.parseInt(player.getVar("bhfails")) * 5;
		int success = player.getVar("bhsuccess") == null ? 0 : Integer.parseInt(player.getVar("bhsuccess")) * 5;

		double reputation = Math.min(Math.max((100 + success - fails) / 100., .5), 2.);

		int adenarewardvalue = (int) ((target.level * Math.max(Math.log(target.level), 1) * 10 + Math.max((target.level - 60) * 33, 0) + Math.max((target.level - 65) * 50, 0)) * target.hp_mod * mobcount * reputation);
		int rewardid = 0;
		int rewardcount = 0;
		int random = Rnd.get(1, 100);
		if(random <= 30) // Адена, 30% случаев
		{
			player.setVar("bhRewardId", "57");
			player.setVar("bhRewardCount", String.valueOf(adenarewardvalue));
			rewardcount = adenarewardvalue;
			rewardid = 57;
		}
		else if(random <= 100) // Кристаллы, 70% случаев
		{
			int crystal = 0;
			if(target.level <= 39)
				crystal = 1458; // D
			else if(target.level <= 51)
				crystal = 1459; // C
			else if(target.level <= 60)
				crystal = 1460; // B
			else if(target.level <= 74)
				crystal = 1461; // A
			else
				crystal = 1462; // S
			player.setVar("bhRewardId", String.valueOf(crystal));
			player.setVar("bhRewardCount", String.valueOf(adenarewardvalue / ItemTable.getInstance().getTemplate(crystal).getReferencePrice()));
			rewardcount = adenarewardvalue / ItemTable.getInstance().getTemplate(crystal).getReferencePrice();
			rewardid = crystal;
		}
		show(new CustomMessage("scripts.events.bountyhunters.TaskGiven", player).addNumber(mobcount).addString(target.name).addNumber(rewardcount).addItemName(rewardid), player);
	}

	@Override
	public void onDie(L2Character cha, L2Character killer)
	{
		if(cha.isMonster() && !cha.isRaid() && killer != null && killer.getPlayer() != null && killer.getPlayer().getVar("bhMonstersId") != null && Integer.parseInt(killer.getPlayer().getVar("bhMonstersId")) == cha.getNpcId())
		{
			int count = Integer.parseInt(killer.getPlayer().getVar("bhMonstersKilled")) + 1;
			killer.getPlayer().setVar("bhMonstersKilled", String.valueOf(count));
			int needed = Integer.parseInt(killer.getPlayer().getVar("bhMonstersNeeded"));
			if(count >= needed)
				doReward(killer.getPlayer());
			else
				sendMessage(new CustomMessage("scripts.events.bountyhunters.NotifyKill", killer.getPlayer()).addNumber(needed - count), killer.getPlayer());
		}
	}

	private static void doReward(L2Player player)
	{
		int rewardid = Integer.parseInt(player.getVar("bhRewardId"));
		int rewardcount = (int) Double.parseDouble(player.getVar("bhRewardCount"));
		player.unsetVar("bhMonstersId");
		player.unsetVar("bhMonstersNeeded");
		player.unsetVar("bhMonstersKilled");
		player.unsetVar("bhRewardId");
		player.unsetVar("bhRewardCount");
		if(player.getVar("bhsuccess") != null)
			player.setVar("bhsuccess", String.valueOf(Integer.parseInt(player.getVar("bhsuccess")) + 1));
		else
			player.setVar("bhsuccess", "1");
		addItem(player, rewardid, rewardcount);
		show(new CustomMessage("scripts.events.bountyhunters.TaskCompleted", player).addNumber(rewardcount).addItemName(rewardid), player);
	}

	public String[] getVoicedCommandList()
	{
		return _commandList;
	}

	public boolean useVoicedCommand(String command, L2Player activeChar, String target)
	{
		if(activeChar == null)
			return false;
		if(activeChar.getLevel() < 20)
		{
			sendMessage(new CustomMessage("scripts.events.bountyhunters.TooLowLevel", activeChar), activeChar);
			return true;
		}
		if(command.equalsIgnoreCase("gettask"))
		{
			if(activeChar.getVar("bhMonstersId") != null)
			{
				int mobid = Integer.parseInt(activeChar.getVar("bhMonstersId"));
				int mobcount = Integer.parseInt(activeChar.getVar("bhMonstersNeeded")) - Integer.parseInt(activeChar.getVar("bhMonstersKilled"));
				int rewardid = Integer.parseInt(activeChar.getVar("bhRewardId"));
				int rewardcount = (int) Double.parseDouble(activeChar.getVar("bhRewardCount"));
				show(new CustomMessage("scripts.events.bountyhunters.TaskGiven", activeChar).addNumber(mobcount).addString(NpcTable.getTemplate(mobid).name).addNumber(rewardcount).addItemName(rewardid), activeChar);
				return true;
			}
			getTask(activeChar);
			return true;
		}
		if(command.equalsIgnoreCase("declinetask"))
		{
			if(activeChar.getVar("bhMonstersId") == null)
			{
				sendMessage(new CustomMessage("scripts.events.bountyhunters.NoTask", activeChar), activeChar);
				return true;
			}
			activeChar.unsetVar("bhMonstersId");
			activeChar.unsetVar("bhMonstersNeeded");
			activeChar.unsetVar("bhMonstersKilled");
			activeChar.unsetVar("bhRewardId");
			activeChar.unsetVar("bhRewardCount");
			if(activeChar.getVar("bhfails") != null)
				activeChar.setVar("bhfails", String.valueOf(Integer.parseInt(activeChar.getVar("bhfails")) + 1));
			else
				activeChar.setVar("bhfails", "1");
			show(new CustomMessage("scripts.events.bountyhunters.TaskCanceled", activeChar), activeChar);
			return true;
		}
		return false;
	}
}