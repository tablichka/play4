package quests.Instances;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;

/**
 * @author rage
 * @date 01.09.2009 11:04:33
 */
public class Kamaloka_73 extends Quest
{
	private static int BOSS_ID = 18577;

	public Kamaloka_73()
	{
		super(22073, "Kamaloka_73", "Kamaloka level 73", true);
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
