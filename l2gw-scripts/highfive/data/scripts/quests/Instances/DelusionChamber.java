package quests.Instances;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;

/**
 * @autor: rage
 * @date: 01.09.2010 19:38:32
 */
public class DelusionChamber extends Quest
{
	public DelusionChamber()
	{
		super(23000, "DelusionChamber", "Delusion Chamber", true);
		addKillId(25690, 25691, 25692, 25693, 25694, 25695);
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player player)
	{
		if(npc.getNpcId() >= 25690 && npc.getNpcId() <= 25695)
		{
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst == null)
			{
				_log.warn(this + " onKill keller has no instance! " + player + " reflection: " + player.getReflection());
				return;
			}

			inst.spawnEvent("dc_" + inst.getTemplate().getId() + "_win");
			inst.notifyEvent("box_spawn", null, null);
		}
	}
}
