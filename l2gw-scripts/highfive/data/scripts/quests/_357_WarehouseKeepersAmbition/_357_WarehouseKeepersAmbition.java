package quests._357_WarehouseKeepersAmbition;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _357_WarehouseKeepersAmbition extends Quest
{

	//CUSTOM VALUES
	private static final int DROPRATE = 50;
	private static final int REWARD1 = 900;//  This is paid per item
	private static final int REWARD2 = 10000;// #Extra reward, if > 100

	//NPC
	private static final int SILVA = 30686;
	//Mobs
	private static final int MOB1 = 20594;
	private static final int MOB2 = 20595;
	private static final int MOB3 = 20596;
	private static final int MOB4 = 20597;
	private static final int MOB5 = 20598;

	//ITEMS
	private static final int JADE_CRYSTAL = 5867;

	public _357_WarehouseKeepersAmbition()
	{
		super(357, "_357_WarehouseKeepersAmbition", "Warehouse Keepers Ambition");
		addStartNpc(SILVA);

		addKillId(MOB1);
		addKillId(MOB2);
		addKillId(MOB3);
		addKillId(MOB4);
		addKillId(MOB5);

		addQuestItem(JADE_CRYSTAL);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30686-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30686-7.htm"))
		{
			long count = st.getQuestItemsCount(JADE_CRYSTAL);
			if(count > 0)
			{
				long reward = count * REWARD1;
				if(count >= 100)
					reward = reward + REWARD2;
				st.takeItems(JADE_CRYSTAL, -1);
				st.rollAndGive(57, reward, 100);
			}
			else
				htmltext = "30686-4.htm";
		}
		else if(event.equalsIgnoreCase("30686-8.htm"))
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
		long jade = st.getQuestItemsCount(JADE_CRYSTAL);
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 47)
				htmltext = "30686-0.htm";
			else
			{
				htmltext = "30686-0a.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(jade == 0)
			htmltext = "30686-4.htm";
		else if(jade > 0)
			htmltext = "30686-6.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 1 && st.rollAndGive(JADE_CRYSTAL, 1, DROPRATE))
			st.playSound(SOUND_ITEMGET);
	}
}