package quests._274_SkirmishWithTheWerewolves;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _274_SkirmishWithTheWerewolves extends Quest
{
	private static final int MARAKU_WEREWOLF_HEAD = 1477;
	private static final int NECKLACE_OF_VALOR = 1507;
	private static final int NECKLACE_OF_COURAGE = 1506;
	private static final int ADENA_ID = 57;
	private static final int MARAKU_WOLFMEN_TOTEM = 1501;

	public _274_SkirmishWithTheWerewolves()
	{
		super(274, "_274_SkirmishWithTheWerewolves", "Skirmish With The Werewolves");
		addStartNpc(30569);

		addKillId(20363);
		addKillId(20364);

		addQuestItem(MARAKU_WEREWOLF_HEAD);
		addQuestItem(MARAKU_WOLFMEN_TOTEM);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("30569-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");

		if(st.isCreated())
		{
			if(st.getPlayer().getRace() != Race.orc)
			{
				htmltext = "30569-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 9)
			{
				htmltext = "30569-01.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(NECKLACE_OF_VALOR) > 0 || st.getQuestItemsCount(NECKLACE_OF_COURAGE) > 0)
			{
				htmltext = "30569-02.htm";
				return htmltext;
			}
			else
				htmltext = "30569-07.htm";
		}
		else if(cond == 1)
		{
			if(st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD) < 40)
				htmltext = "30569-04.htm";
			else
			{
				st.takeItems(MARAKU_WEREWOLF_HEAD, -1);
				st.rollAndGive(ADENA_ID, 3500, 100);
				if(st.getQuestItemsCount(MARAKU_WOLFMEN_TOTEM) >= 1)
				{
					st.takeItems(MARAKU_WOLFMEN_TOTEM, -1);
					st.rollAndGive(ADENA_ID, st.getQuestItemsCount(MARAKU_WOLFMEN_TOTEM) * 600, 100);
				}
				htmltext = "30569-05.htm";
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(MARAKU_WEREWOLF_HEAD, 1, 100, 40))
		{
			if(st.getQuestItemsCount(MARAKU_WEREWOLF_HEAD) < 39)
				st.playSound(SOUND_ITEMGET);
			else
				st.playSound(SOUND_MIDDLE);

			if(Rnd.chance(5))
				st.giveItems(MARAKU_WOLFMEN_TOTEM, 1);
		}
	}
}