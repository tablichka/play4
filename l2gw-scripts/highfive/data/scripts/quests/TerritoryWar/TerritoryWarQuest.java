package quests.TerritoryWar;

import javolution.util.FastMap;
import ru.l2gw.gameserver.instancemanager.QuestManager;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 12.07.2010 9:21:42
 */
public class TerritoryWarQuest extends Quest
{
	public static final FastMap<ClassId, Integer> _classQuest = new FastMap<ClassId, Integer>();

	static
	{
		_classQuest.put(ClassId.darkAvenger, 734);
		_classQuest.put(ClassId.hellKnight, 734);
		_classQuest.put(ClassId.paladin, 734);
		_classQuest.put(ClassId.phoenixKnight, 734);
		_classQuest.put(ClassId.templeKnight, 734);
		_classQuest.put(ClassId.evaTemplar, 734);
		_classQuest.put(ClassId.shillienKnight, 734);
		_classQuest.put(ClassId.shillienTemplar, 734);

		_classQuest.put(ClassId.gladiator, 735);
		_classQuest.put(ClassId.warlord, 735);
		_classQuest.put(ClassId.treasureHunter, 735);
		_classQuest.put(ClassId.hawkeye, 735);
		_classQuest.put(ClassId.plainsWalker, 735);
		_classQuest.put(ClassId.silverRanger, 735);
		_classQuest.put(ClassId.abyssWalker, 735);
		_classQuest.put(ClassId.phantomRanger, 735);
		_classQuest.put(ClassId.destroyer, 735);
		_classQuest.put(ClassId.tyrant, 735);
		_classQuest.put(ClassId.bountyHunter, 735);
		_classQuest.put(ClassId.duelist, 735);
		_classQuest.put(ClassId.dreadnought, 735);
		_classQuest.put(ClassId.sagittarius, 735);
		_classQuest.put(ClassId.adventurer, 735);
		_classQuest.put(ClassId.windRider, 735);
		_classQuest.put(ClassId.moonlightSentinel, 735);
		_classQuest.put(ClassId.ghostHunter, 735);
		_classQuest.put(ClassId.ghostSentinel, 735);
		_classQuest.put(ClassId.titan, 735);
		_classQuest.put(ClassId.grandKhauatari, 735);
		_classQuest.put(ClassId.fortuneSeeker, 735);
		_classQuest.put(ClassId.berserker, 735);
		_classQuest.put(ClassId.maleSoulbreaker, 735);
		_classQuest.put(ClassId.femaleSoulbreaker, 735);
		_classQuest.put(ClassId.arbalester, 735);
		_classQuest.put(ClassId.doombringer, 735);
		_classQuest.put(ClassId.maleSoulhound, 735);
		_classQuest.put(ClassId.femaleSoulhound, 735);
		_classQuest.put(ClassId.trickster, 735);
		_classQuest.put(ClassId.swordSinger, 735);
		_classQuest.put(ClassId.swordMuse, 735);
		_classQuest.put(ClassId.bladedancer, 735);
		_classQuest.put(ClassId.spectralDancer, 735);

		_classQuest.put(ClassId.sorceror, 736);
		_classQuest.put(ClassId.warlock, 736);
		_classQuest.put(ClassId.spellsinger, 736);
		_classQuest.put(ClassId.elementalSummoner, 736);
		_classQuest.put(ClassId.spellhowler, 736);
		_classQuest.put(ClassId.phantomSummoner, 736);
		_classQuest.put(ClassId.archmage, 736);
		_classQuest.put(ClassId.arcanaLord, 736);
		_classQuest.put(ClassId.mysticMuse, 736);
		_classQuest.put(ClassId.elementalMaster, 736);
		_classQuest.put(ClassId.stormScreamer, 736);
		_classQuest.put(ClassId.spectralMaster, 736);
		_classQuest.put(ClassId.necromancer, 736);
		_classQuest.put(ClassId.soultaker, 736);

		_classQuest.put(ClassId.bishop, 737);
		_classQuest.put(ClassId.prophet, 737);
		_classQuest.put(ClassId.elder, 737);
		_classQuest.put(ClassId.shillienElder, 737);
		_classQuest.put(ClassId.warcryer, 737);
		_classQuest.put(ClassId.cardinal, 737);
		_classQuest.put(ClassId.hierophant, 737);
		_classQuest.put(ClassId.evaSaint, 737);
		_classQuest.put(ClassId.shillienSaint, 737);
		_classQuest.put(ClassId.doomcryer, 737);
		_classQuest.put(ClassId.inspector, 737);
		_classQuest.put(ClassId.judicator, 737);

		_classQuest.put(ClassId.overlord, 738);
		_classQuest.put(ClassId.warsmith, 738);
		_classQuest.put(ClassId.dominator, 738);
		_classQuest.put(ClassId.maestro, 738);
	}

	private static final String[] _questNames = {
			"_734_PierceThroughAShield",
			"_735_MakeSpearsDull",
			"_736_WeakenMagic",
			"_737_DenyBlessings",
			"_738_DestroyKeyTargets"
	};

	public TerritoryWarQuest()
	{
		super(20100, "TerritoryWarQuest", "Territory War Quest", true);
	}

	@Override
	public void onPlayerKill(L2Player killer, L2Player killed)
	{
		if(!TerritoryWarManager.getWar().isInProgress() || !checkCondition(killer, killed))
			return;

		int questId = _classQuest.get(killed.getClassId());
		if(!killer.getVarB("twq_" + questId))
			giveQuest(questId, killer);
	}

	@Override
	public void onPlayerKillParty(L2Player killer, L2Player killed, QuestState qs)
	{
		onPlayerKill(qs.getPlayer(), killed);
	}

	private static void giveQuest(int questId, L2Player player)
	{
		player.setVar("twq_" + questId, "true", (int) (TerritoryWarManager.getWar().getWardEndDate() / 1000));
		Quest q = QuestManager.getQuest(_questNames[questId - 734]);
		if(q != null)
		{
			QuestState qs = q.newQuestState(player);
			qs.set("cond", "1");
			qs.setState(STARTED, false);
			q.notifyEvent("start", qs);
		}
	}

	public static boolean checkCondition(L2Player killer, L2Player killed)
	{
		return !(killer.getTerritoryId() == 0 || killed.getTerritoryId() == 0 || killer.getTerritoryId() == killed.getTerritoryId() ||
				killed.getLevel() < 61 || killed.getClassId().getLevel() < 3 ||
				killer.getClanId() > 0 && killer.getClanId() == killed.getClanId() || !_classQuest.containsKey(killed.getClassId()));
	}
}
