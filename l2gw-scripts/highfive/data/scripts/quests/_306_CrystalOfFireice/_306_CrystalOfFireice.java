package quests._306_CrystalOfFireice;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _306_CrystalOfFireice extends Quest
{
	//NPCs
	private static int Katerina = 30004;
	//Mobs
	private static int Salamander = 20109;
	private static int Undine = 20110;
	private static int Salamander_Elder = 20112;
	private static int Undine_Elder = 20113;
	private static int Salamander_Noble = 20114;
	private static int Undine_Noble = 20115;
	//Items
	private static int ADENA = 57;
	//Quest Items
	private static int Flame_Shard = 1020;
	private static int Ice_Shard = 1021;
	//Chances
	private static int Chance = 30;
	private static int Elder_Chance = 40;
	private static int Noble_Chance = 50;

	public _306_CrystalOfFireice()
	{
		super(306, "_306_CrystalOfFireice", "Crystals of Fire and Ice");
		addStartNpc(Katerina);
		addKillId(Salamander);
		addKillId(Undine);
		addKillId(Salamander_Elder);
		addKillId(Undine_Elder);
		addKillId(Salamander_Noble);
		addKillId(Undine_Noble);
		addQuestItem(Flame_Shard, Ice_Shard);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30004-04.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30004-08.htm") && st.isStarted())
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
		if(npc.getNpcId() != Katerina)
			return htmltext;

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 17)
			{
				htmltext = "30004-02.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "30004-03.htm";
				st.set("cond", "0");
			}
		}
		else if(st.isStarted())
		{
			long Shrads_count = st.getQuestItemsCount(Flame_Shard) + st.getQuestItemsCount(Ice_Shard);
			long Reward = Shrads_count * 30 + (Shrads_count >= 10 ? 5000 : 0);
			if(Reward > 0)
			{
				htmltext = "30004-07.htm";
				st.takeItems(Flame_Shard, -1);
				st.takeItems(Ice_Shard, -1);
				st.rollAndGive(ADENA, Reward, 100);
			}
			else
				htmltext = "30004-05.htm";
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		int npcId = npc.getNpcId();

		if((npcId == Salamander || npcId == Undine) && !Rnd.chance(Chance))
			return;
		if((npcId == Salamander_Elder || npcId == Undine_Elder) && !Rnd.chance(Elder_Chance))
			return;
		if((npcId == Salamander_Noble || npcId == Undine_Noble) && !Rnd.chance(Noble_Chance))
			return;

		st.rollAndGive(npcId == Salamander || npcId == Salamander_Elder || npcId == Salamander_Noble ? Flame_Shard : Ice_Shard, 1, 100);
		st.playSound(SOUND_ITEMGET);
	}
}