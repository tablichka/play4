package quests._320_BonesTellFuture;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _320_BonesTellFuture extends Quest
{
	//item
	public final int ADENA = 57;
	public final int BONE_FRAGMENT = 809;

	public _320_BonesTellFuture()
	{
		super(320, "_320_BonesTellFuture", "Bones Tell Future");

		addStartNpc(30359);
		addTalkId(30359);

		addKillId(20517);
		addKillId(20518);

		addQuestItem(BONE_FRAGMENT);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30359-04.htm"))
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
			if(st.getPlayer().getRace().ordinal() != 2)
			{
				htmltext = "30359-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() >= 10)
				htmltext = "30359-03.htm";
			else
			{
				htmltext = "30359-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.getQuestItemsCount(BONE_FRAGMENT) < 10)
			htmltext = "30359-05.htm";
		else
		{
			htmltext = "30359-06.htm";
			st.takeItems(BONE_FRAGMENT, -1);
			st.rollAndGive(ADENA, 8470, 100);
			st.exitCurrentQuest(true);
			st.unset("cond");
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 1 && st.rollAndGiveLimited(BONE_FRAGMENT, 1, 10, 10))
		{
			if(st.getQuestItemsCount(BONE_FRAGMENT) == 10)
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
