package quests.Instances;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;

/**
 * @author: rage
 * @date: 05.09.2009 9:56:49
 */
public class Kamaloka_69 extends Quest
{
	private static int BOSS_ID = 29141;

	public Kamaloka_69()
	{
		super(22069, "Kamaloka_69", "Kamaloka level 69", true);
		addKillId(BOSS_ID);
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player player)
	{
		if(npc.getNpcId() == BOSS_ID)
		{
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst == null)
			{
				_log.warn(this + " onKill killer has no instance! " + player + " reflection: " + player.getReflection());
				return;
			}
			inst.successEnd();
		}
	}
}
