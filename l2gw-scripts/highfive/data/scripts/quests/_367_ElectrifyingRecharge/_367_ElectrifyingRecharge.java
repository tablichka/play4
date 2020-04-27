package quests._367_ElectrifyingRecharge;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;

public class _367_ElectrifyingRecharge extends Quest
{
	//NPCs
	private static int LORAIN = 30673;
	//Mobs
	private static int CATHEROK = 21035;
	//Quest Items
	private static int Titan_Lamp_First = 5875;
	private static int Titan_Lamp_Last = 5879;
	private static int Broken_Titan_Lamp = 5880;
	//Chances
	private static int broke_chance = 3;
	private static int uplight_chance = 7;

	public _367_ElectrifyingRecharge()
	{
		super(367, "_367_ElectrifyingRecharge", "Electrifying Recharge!");
		addStartNpc(LORAIN);
		addKillId(CATHEROK);
		for(int Titan_Lamp_id = Titan_Lamp_First; Titan_Lamp_id <= Titan_Lamp_Last; Titan_Lamp_id++)
			addQuestItem(Titan_Lamp_id);
		addQuestItem(Broken_Titan_Lamp);
	}

	private static boolean takeAllLamps(QuestState st)
	{
		boolean result = false;
		for(int Titan_Lamp_id = Titan_Lamp_First; Titan_Lamp_id <= Titan_Lamp_Last; Titan_Lamp_id++)
			if(st.getQuestItemsCount(Titan_Lamp_id) > 0)
			{
				result = true;
				st.takeItems(Titan_Lamp_id, -1);
			}
		if(st.getQuestItemsCount(Broken_Titan_Lamp) > 0)
		{
			result = true;
			st.takeItems(Broken_Titan_Lamp, -1);
		}
		return result;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30673-03.htm") && st.isCreated())
		{
			takeAllLamps(st);
			st.giveItems(Titan_Lamp_First, 1);
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30673-07.htm") && st.isStarted())
		{
			takeAllLamps(st);
			st.giveItems(Titan_Lamp_First, 1);
		}
		else if(event.equalsIgnoreCase("30673-08.htm") && st.isStarted())
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != LORAIN)
			return htmltext;

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 37)
			{
				htmltext = "30673-02.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "30673-01.htm";
				st.set("cond", "0");
			}
		}
		else if(st.isStarted())
			if(st.getQuestItemsCount(Titan_Lamp_Last) > 0)
			{
				htmltext = "30673-06.htm";
				takeAllLamps(st);
				st.rollAndGive(4553 + Rnd.get(12), 1, 100);
				st.playSound(SOUND_MIDDLE);
			}
			else if(st.getQuestItemsCount(Broken_Titan_Lamp) > 0)
			{
				htmltext = "30673-05.htm";
				takeAllLamps(st);
				st.giveItems(Titan_Lamp_First, 1);
			}
			else
				htmltext = "30673-04.htm";

		return htmltext;
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState qs, L2Skill skill)
	{
		if(!qs.isStarted())
			return null;
		if(qs.getQuestItemsCount(Broken_Titan_Lamp) > 0)
			return null;

		if(Rnd.chance(uplight_chance))
			for(int Titan_Lamp_id = Titan_Lamp_First; Titan_Lamp_id < Titan_Lamp_Last; Titan_Lamp_id++)
				if(qs.getQuestItemsCount(Titan_Lamp_id) > 0)
				{
					int Titan_Lamp_Next = Titan_Lamp_id + 1;
					takeAllLamps(qs);
					qs.giveItems(Titan_Lamp_Next, 1);
					if(Titan_Lamp_Next == Titan_Lamp_Last)
					{
						qs.set("cond", "2");
						qs.playSound(SOUND_MIDDLE);
					}
					else
						qs.playSound(SOUND_ITEMGET);
					npc.doCast(SkillTable.getInstance().getInfo(4072, 4), qs.getPlayer(), true);
					return null;
				}
				else if(Rnd.chance(broke_chance))
					if(takeAllLamps(qs))
						qs.giveItems(Broken_Titan_Lamp, 1);

		return null;
	}
}