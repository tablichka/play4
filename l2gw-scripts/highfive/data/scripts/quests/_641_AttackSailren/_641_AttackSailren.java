package quests._641_AttackSailren;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _641_AttackSailren extends Quest
{
	//NPC
	private static int STATUE = 32109;

	//MOBS
	private static int VEL1 = 22196;
	private static int VEL2 = 22197;
	private static int VEL3 = 22198;
	private static int VEL4 = 22218;
	private static int VEL5 = 22223;
	private static int PTE = 22199;
	//items
	private static int FRAGMENTS = 8782;
	private static int GAZKH = 8784;

	public _641_AttackSailren()
	{
		super(641, "_641_AttackSailren", "Attack Sailren"); // Party true

		addStartNpc(STATUE);
		addTalkId(STATUE);

		addKillId(VEL1);
		addKillId(VEL2);
		addKillId(VEL3);
		addKillId(VEL4);
		addKillId(VEL5);
		addKillId(PTE);

		addQuestItem(FRAGMENTS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		int cond = st.getInt("cond");
		String prefix = "statue_of_shilen_q0641_";
		QuestState part1Quest = st.getPlayer().getQuestState("_126_TheNameOfEvil2");

		if(event.equalsIgnoreCase("accept"))
		{

			if(st.isCreated() && (part1Quest == null || !part1Quest.isCompleted()))
			{
				htmltext = prefix + "02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated() && st.getPlayer().getLevel() < 77)
			{
				htmltext = prefix + "03.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = prefix + "05.htm";
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
		}
		else if(event.equalsIgnoreCase("statue_of_shilen_q0641_08.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.takeItems(FRAGMENTS, -1);
			st.giveItems(GAZKH, 1);
			st.unset("cond");
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		String prefix = "statue_of_shilen_q0641_";
		QuestState part1Quest = st.getPlayer().getQuestState("_126_TheNameOfEvil2");

		int cond = st.getInt("cond");
		if(st.isCreated() && (part1Quest == null || !part1Quest.isCompleted()))
		{
			htmltext = prefix + "02.htm";
			st.exitCurrentQuest(true);
		}
		else if(st.isCreated() && st.getPlayer().getLevel() < 77)
		{
			htmltext = prefix + "03.htm";
			st.exitCurrentQuest(true);
		}
		else if(cond == 1)
			htmltext = prefix + "06.htm";
		else if(cond == 2)
			htmltext = prefix + "07.htm";
		else
			htmltext = prefix + "01.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);

		if(st != null)
			if(st.rollAndGiveLimited(FRAGMENTS, 1, 100, 30))
			{
				if(st.getQuestItemsCount(FRAGMENTS) == 30)
				{
					st.set("cond", "2");
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
	}
}