package quests._355_FamilyHonor;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _355_FamilyHonor extends Quest
{
	//Npc
	private static final int Galibredo = 30181;
	private static final int Patrin = 30929;
	//Items
	private static final int GalfRedoRomersBust = 4252;
	private static final int BustOfAncientGoddess = 4349;
	private static final int TheWorkOfTheAncientSculptorBerona = 4350;
	private static final int AncientStatueOfGoddessPrototype = 4351;
	private static final int AncientStatueOfGoddessOriginal = 4352;
	private static final int AncientStatueOfGoddessReplica = 4353;
	private static final int AncientStatueOfGoddessForgery = 4354;
	//Mobs
	private static final int TimakOrcTroopLeader = 20767;
	private static final int TimakOrcTroopShaman = 20768;
	private static final int TimakOrcTroopWarrior = 20769;
	private static final int TimakOrcTroopArcher = 20770;
	//Chances
	private static final int Chance_For_GalfRedoRomersBust = 80;
	private static final int Chance_For_BustOfAncientGoddess = 30;

	public _355_FamilyHonor()
	{
		super(355, "_355_FamilyHonor", "Family Honor"); // Party true

		addStartNpc(Galibredo);
		addTalkId(Patrin);

		// TIMAK ORC TROOPS
		addKillId(TimakOrcTroopLeader);
		addKillId(TimakOrcTroopShaman);
		addKillId(TimakOrcTroopWarrior);
		addKillId(TimakOrcTroopArcher);

		addQuestItem(GalfRedoRomersBust);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("30181-1.htm"))
			return htmltext;
		else if(event.equals("30181-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("30181-4.htm"))
		{
			long count = st.getQuestItemsCount(BustOfAncientGoddess);
			st.takeItems(BustOfAncientGoddess, -1);
			st.giveItems(TheWorkOfTheAncientSculptorBerona, count);
		}
		else if(event.equals("appraise"))
		{
			if(st.getQuestItemsCount(TheWorkOfTheAncientSculptorBerona) == 0)
				htmltext = "<html><head><body>You have nothing to appraise.</body></html>";
			else
			{
				st.takeItems(TheWorkOfTheAncientSculptorBerona, 1);
				int appraising = Rnd.get(100);
				if(appraising < 20)
					htmltext = "30929-2.htm";
				else if(appraising < 40)
				{
					htmltext = "30929-3.htm";
					st.giveItems(AncientStatueOfGoddessReplica, 1);
				}
				else if(appraising < 60)
				{
					htmltext = "30929-4.htm";
					st.giveItems(AncientStatueOfGoddessOriginal, 1);
				}
				else if(appraising < 80)
				{
					htmltext = "30929-5.htm";
					st.giveItems(AncientStatueOfGoddessForgery, 1);
				}
				else if(appraising < 100)
				{
					htmltext = "30929-6.htm";
					st.giveItems(AncientStatueOfGoddessPrototype, 1);
				}
			}
		}
		else if(event.equals("30181-5.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Galibredo)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 36)
					htmltext = "30181-0.htm";
				else
				{
					htmltext = "30181-0a.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
			{
				long count = st.getQuestItemsCount(GalfRedoRomersBust);
				if(count > 0)
				{
					long reward = count * 120;
					if(count >= 100)
						reward = reward + 7800;
					st.takeItems(GalfRedoRomersBust, -1);
					st.rollAndGive(57, reward, 100);
					htmltext = "30181-3.htm";
				}
				else
					htmltext = "30181-2a.htm";
			}
		}
		else if(npcId == Patrin)
			if(st.getQuestItemsCount(TheWorkOfTheAncientSculptorBerona) > 0)
				htmltext = "30929-0.htm";
			else
				htmltext = "<html><head><body>You have nothing to appraise.</body></html>";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null)
		{
			if(st.rollAndGive(GalfRedoRomersBust, 1, Chance_For_GalfRedoRomersBust))
				st.playSound(SOUND_ITEMGET);
			if(st.rollAndGive(BustOfAncientGoddess, 1, Chance_For_BustOfAncientGoddess))
				st.playSound(SOUND_ITEMGET);
		}
	}

}