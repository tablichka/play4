package quests.Instances;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;

/**
 * @author: rage
 * @date: 04.09.2009 21:39:10
 */
public class Kamaloka_39 extends Quest
{
	private static int BOSS_ID = 29132;

	public Kamaloka_39()
	{
		super(22039, "Kamaloka_39", "Kamaloka level 39", true);
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
				_log.warn(this + "Kamaloka_39: onKill keller has no instance! " + player + " reflection: " + player.getReflection());
				return;
			}
			inst.successEnd();
		}
	}
}
