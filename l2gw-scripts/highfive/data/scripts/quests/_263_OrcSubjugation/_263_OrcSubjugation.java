package quests._263_OrcSubjugation;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _263_OrcSubjugation extends Quest
{
	// NPC
	public final int KAYLEEN = 30346;

	// MOBS
	public final int BALOR_ORC_ARCHER = 20385;
	public final int BALOR_ORC_FIGHTER = 20386;
	public final int BALOR_ORC_FIGHTER_LEADER = 20387;
	public final int BALOR_ORC_LIEUTENANT = 20388;

	public final int ORC_AMULET = 1116;
	public final int ORC_NECKLACE = 1117;
	public final int ADENA = 57;

	public _263_OrcSubjugation()
	{
		super(263, "_263_OrcSubjugation", "Orc Subjugation");
		addStartNpc(KAYLEEN);
		addKillId(BALOR_ORC_ARCHER, BALOR_ORC_FIGHTER, BALOR_ORC_FIGHTER_LEADER, BALOR_ORC_LIEUTENANT);
		addQuestItem(ORC_AMULET, ORC_NECKLACE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("30346-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("30346-06.htm"))
		{
			st.set("cond", "0");
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
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
			if(st.getPlayer().getLevel() >= 8 && st.getPlayer().getRace().ordinal() == 2)
			{
				htmltext = "30346-02.htm";
				return htmltext;
			}
			else if(st.getPlayer().getRace().ordinal() != 2)
			{
				htmltext = "30346-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 8)
			{
				htmltext = "30346-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1)
			if(st.getQuestItemsCount(ORC_AMULET) == 0 && st.getQuestItemsCount(ORC_NECKLACE) == 0)
				htmltext = "30346-04.htm";
			else if(st.getQuestItemsCount(ORC_AMULET) + st.getQuestItemsCount(ORC_NECKLACE) >= 10)
			{
				htmltext = "30346-05.htm";
				st.giveItems(ADENA, st.getQuestItemsCount(ORC_AMULET) * 20 + st.getQuestItemsCount(ORC_NECKLACE) * 30 + 1100);
				st.takeItems(ORC_AMULET, -1);
				st.takeItems(ORC_NECKLACE, -1);
			}
			else
			{
				htmltext = "30346-05.htm";
				st.giveItems(ADENA, st.getQuestItemsCount(ORC_AMULET) * 20 + st.getQuestItemsCount(ORC_NECKLACE) * 30);
				st.takeItems(ORC_AMULET, -1);
				st.takeItems(ORC_NECKLACE, -1);
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getInt("cond") == 1)
		{
			if(npcId == BALOR_ORC_ARCHER && st.rollAndGive(ORC_AMULET, 1, 60))
				st.playSound(SOUND_ITEMGET);
			else if((npcId == BALOR_ORC_FIGHTER || npcId == BALOR_ORC_FIGHTER_LEADER || npcId == BALOR_ORC_LIEUTENANT) && st.rollAndGive(ORC_NECKLACE, 1, 60))
				st.playSound(SOUND_ITEMGET);
		}
	}
}