package quests._242_PossessorOfaPreciousSoul2;

import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _242_PossessorOfaPreciousSoul2 extends Quest
{
	private static final int VIRGILS_LETTER_1_PART = 7677;
	private static final int BLONDE_STRAND = 7590;
	private static final int SORCERY_INGREDIENT = 7596;
	private static final int CARADINE_LETTER = 7678;
	private static final int ORB_OF_BINDING = 7595;

	public _242_PossessorOfaPreciousSoul2()
	{
		super(242, "_242_PossessorOfaPreciousSoul2", "Possessor Of a Precious Soul 2");

		addStartNpc(31742);

		addTalkId(31743);
		addTalkId(31751);
		addTalkId(31752);
		addTalkId(30759);
		addTalkId(30738);
		addTalkId(31744);
		addTalkId(31748);
		addTalkId(31747);
		addTalkId(31746);

		addKillId(27317);

		addQuestItem(ORB_OF_BINDING, SORCERY_INGREDIENT, BLONDE_STRAND);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(event.equalsIgnoreCase("31742-2.htm"))
		{
			st.set("cond", "1");
			st.set("CoRObjId", "0");
			st.takeItems(VIRGILS_LETTER_1_PART, 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31743-5.htm"))
		{
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("31744-2.htm"))
		{
			st.set("cond", "3");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("31751-2.htm"))
		{
			st.set("cond", "4");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30759-2.htm"))
		{
			st.takeItems(BLONDE_STRAND, 1);
			st.set("cond", "7");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30759-4.htm"))
		{
			st.set("cond", "9");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30738-2.htm"))
		{
			st.set("cond", "8");
			st.giveItems(SORCERY_INGREDIENT, 1);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("31748-2.htm"))
		{
			st.takeItems(ORB_OF_BINDING, 1);
			L2Object obj = L2ObjectsStorage.findObject(st.getInt("CoRObjId"));
			if(obj instanceof L2NpcInstance)
				((L2NpcInstance) obj).doDie(null);
			st.set("talk", "0");
			if(st.getInt("prog") < 4)
			{
				st.set("prog", str(st.getInt("prog") + 1));
				st.playSound(SOUND_MIDDLE);
			}
			if(st.getInt("prog") == 4)
			{
				st.set("cond", "10");
				st.playSound(SOUND_MIDDLE);
			}
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == 31742)
		{
			if(st.isCreated())
			{
				if(st.getQuestItemsCount(VIRGILS_LETTER_1_PART) >= 1 && st.getPlayer().isSubClassActive() && st.getPlayer().getLevel() >= 60)
					htmltext = "31742-1.htm";
				else
				{
					htmltext = "31742-0.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "31742-2r.htm";
		}
		else if(npcId == 31743 && st.getPlayer().isSubClassActive())
		{
			if(cond == 1)
				htmltext = "31743-1.htm";
			else if(cond == 2)
				htmltext = "31743-2r.htm";
			else if(cond == 11)
			{
				htmltext = "31743-6.htm";
				st.giveItems(CARADINE_LETTER, 1);
				st.unset("cond");
				st.unset("CoRObjId");
				st.unset("prog");
				st.unset("talk");
				st.playSound(SOUND_FINISH);
				st.addExpAndSp(455764, 0);
				st.exitCurrentQuest(false);
			}
		}
		else if(npcId == 31744 && st.getPlayer().isSubClassActive())
		{
			if(cond == 2)
				htmltext = "31744-1.htm";
			else if(cond == 3)
				htmltext = "31744-2r.htm";
		}
		else if(npcId == 31751 && st.getPlayer().isSubClassActive())
		{
			if(cond == 3)
				htmltext = "31751-1.htm";
			else if(cond == 4)
				htmltext = "31751-2r.htm";
			else if(cond == 5 && st.getQuestItemsCount(BLONDE_STRAND) == 1)
			{
				st.set("cond", "6");
				htmltext = "31751-3.htm";
			}
			else if(cond == 6 && st.getQuestItemsCount(BLONDE_STRAND) == 1)
				htmltext = "31751-3r.htm";
		}
		else if(npcId == 31752 && st.getPlayer().isSubClassActive())
		{
			if(cond == 4)
			{
				st.giveItems(BLONDE_STRAND, 1);
				st.playSound(SOUND_ITEMGET);
				st.set("cond", "5");
				htmltext = "31752-2.htm";
			}
			else
				htmltext = "31752-n.htm";
		}
		else if(npcId == 30759 && st.getPlayer().isSubClassActive())
		{
			if(cond == 6 && st.getQuestItemsCount(BLONDE_STRAND) == 1)
				htmltext = "30759-1.htm";
			else if(cond == 7)
				htmltext = "30759-2r.htm";
			else if(cond == 8 && st.getQuestItemsCount(SORCERY_INGREDIENT) == 1)
				htmltext = "30759-3.htm";
		}
		else if(npcId == 30738 && st.getPlayer().isSubClassActive())
		{
			if(cond == 7)
				htmltext = "30738-1.htm";
			else if(cond == 8)
				htmltext = "30738-2r.htm";
		}
		else if(npcId == 31748 && st.getPlayer().isSubClassActive())
		{
			if(cond == 9)
				if(st.getQuestItemsCount(ORB_OF_BINDING) >= 1)
				{
					if(npc.getObjectId() != st.getInt("CoRObjId"))
					{
						st.set("CoRObjId", str(npc.getObjectId()));
						st.set("talk", "1");
						htmltext = "31748-1.htm";
					}
					else if(st.getInt("talk") == 1)
						htmltext = "31748-1.htm";
					else
						htmltext = "noquest";
				}
				else
					htmltext = "31748-0.htm";
		}
		else if(npcId == 31746 && st.getPlayer().isSubClassActive())
		{
			if(st.getInt("cond") == 9)
				htmltext = "31746-1.htm";
			else if(st.getInt("cond") == 10)
			{
				htmltext = "31746-1.htm";
				npc.doDie(npc);
				if(!st.getPcSpawn().isSpawnExists(31747))
					st.getPcSpawn().addSpawn(31747, npc.getX() + 10, npc.getY(), npc.getZ(), 120000);
			}
			else
				htmltext = "noquest";
		}
		else if(npcId == 31747 && st.getPlayer().isSubClassActive())
			if(st.getInt("cond") == 10)
			{
				htmltext = "31747-1.htm";
				st.set("cond", "11");
			}
			else if(st.getInt("cond") == 11)
				htmltext = "31747-2.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 9 && st.getQuestItemsCount(ORB_OF_BINDING) < 4 && st.getPlayer().isSubClassActive())
			st.giveItems(ORB_OF_BINDING, 1);
		if(st.getQuestItemsCount(ORB_OF_BINDING) < 4)
			st.playSound(SOUND_ITEMGET);
		else
			st.playSound(SOUND_MIDDLE);
	}
}