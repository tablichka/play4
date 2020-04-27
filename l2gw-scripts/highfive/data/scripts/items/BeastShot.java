package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Summon;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

public class BeastShot implements IItemHandler, ScriptFile
{
	private final static int[] _itemIds = { 6645, 6646, 6647, 20332, 20333, 20334 };

	static final SystemMessage PETS_AND_SERVITORS_ARE_NOT_AVAILABLE_AT_THIS_TIME = new SystemMessage(SystemMessage.PETS_AND_SERVITORS_ARE_NOT_AVAILABLE_AT_THIS_TIME);
	static final SystemMessage WHEN_PET_OR_SERVITOR_IS_DEAD_SOULSHOTS_OR_SPIRITSHOTS_FOR_PET_OR_SERVITOR_ARE_NOT_AVAILABLE = new SystemMessage(SystemMessage.WHEN_PET_OR_SERVITOR_IS_DEAD_SOULSHOTS_OR_SPIRITSHOTS_FOR_PET_OR_SERVITOR_ARE_NOT_AVAILABLE);
	static final SystemMessage YOU_DONT_HAVE_ENOUGH_SOULSHOTS_NEEDED_FOR_A_PET_SERVITOR = new SystemMessage(SystemMessage.YOU_DONT_HAVE_ENOUGH_SOULSHOTS_NEEDED_FOR_A_PET_SERVITOR);
	static final SystemMessage YOU_DONT_HAVE_ENOUGH_SPIRITSHOTS_NEEDED_FOR_A_PET_SERVITOR = new SystemMessage(SystemMessage.YOU_DONT_HAVE_ENOUGH_SPIRITSHOTS_NEEDED_FOR_A_PET_SERVITOR);

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable == null || !playable.isPlayer())
			return true;
		L2Player player = (L2Player) playable;

		L2Summon pet = player.getPet();
		if(!player.isPetSummoned())
		{
			player.sendPacket(PETS_AND_SERVITORS_ARE_NOT_AVAILABLE_AT_THIS_TIME);
			return false;
		}

		if(pet.isDead())
		{
			player.sendPacket(WHEN_PET_OR_SERVITOR_IS_DEAD_SOULSHOTS_OR_SPIRITSHOTS_FOR_PET_OR_SERVITOR_ARE_NOT_AVAILABLE);
			return true;
		}

		int consumption = 0;
		int skillid = 0;

		switch(item.getItemId())
		{
			case 6645:
			case 20332:
				if(pet.getChargedSoulShot())
					return true;
				consumption = pet.getSoulshotConsumeCount();
				if(item.getCount() < consumption)
				{
					player.sendPacket(YOU_DONT_HAVE_ENOUGH_SOULSHOTS_NEEDED_FOR_A_PET_SERVITOR);
					return false;
				}
				pet.chargeSoulShot();
				skillid = 2033;
				break;
			case 6646:
			case 20333:
				if(pet.getChargedSpiritShot() > 0)
					return true;
				consumption = pet.getSpiritshotConsumeCount();
				if(item.getCount() < consumption)
				{
					player.sendPacket(YOU_DONT_HAVE_ENOUGH_SPIRITSHOTS_NEEDED_FOR_A_PET_SERVITOR);
					return false;
				}
				pet.chargeSpiritShot(L2ItemInstance.CHARGED_SPIRITSHOT);
				skillid = 2008;
				break;
			case 6647:
			case 20334:
				if(pet.getChargedSpiritShot() > 1)
					return true;
				consumption = pet.getSpiritshotConsumeCount();
				if(item.getCount() < consumption)
				{
					player.sendPacket(YOU_DONT_HAVE_ENOUGH_SPIRITSHOTS_NEEDED_FOR_A_PET_SERVITOR);
					return false;
				}
				pet.chargeSpiritShot(L2ItemInstance.CHARGED_BLESSED_SPIRITSHOT);
				skillid = 2009;
				break;
		}

		player.destroyItem("Consume", item.getObjectId(), consumption, null, false);
		pet.broadcastPacket(new MagicSkillUse(pet, pet, skillid, 1, 0, 0));
		return true;
	}

	public final int[] getItemIds()
	{
		return _itemIds;
	}

	public void onLoad()
	{
		ItemHandler.getInstance().registerItemHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}