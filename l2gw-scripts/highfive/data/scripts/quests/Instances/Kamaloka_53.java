package quests.Instances;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;

/**
 * @author rage
 * @date 26.08.2009 16:13:07
 */
public class Kamaloka_53 extends Quest
{
	private static int BOSS_ID = 18566;

	public Kamaloka_53()
	{
		super(22053, "Kamaloka_53", "Kamaloka level 53", true);
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
