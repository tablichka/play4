package quests._325_GrimCollector;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _325_GrimCollector extends Quest
{
	int ZOMBIE_HEAD = 1350;
	int ZOMBIE_HEART = 1351;
	int ZOMBIE_LIVER = 1352;
	int SKULL = 1353;
	int RIB_BONE = 1354;
	int SPINE = 1355;
	int ARM_BONE = 1356;
	int THIGH_BONE = 1357;
	int COMPLETE_SKELETON = 1358;
	int ANATOMY_DIAGRAM = 1349;
	int ADENA = 57;

	public _325_GrimCollector()
	{
		super(325, "_325_GrimCollector", "Grim Collector");

		addStartNpc(30336);

		addTalkId(30336);
		addTalkId(30342);
		addTalkId(30434);

		addKillId(20026);
		addKillId(20029);
		addKillId(20035);
		addKillId(20042);
		addKillId(20045);
		addKillId(20457);
		addKillId(20458);
		addKillId(20051);
		addKillId(20514);
		addKillId(20515);

		addQuestItem(ZOMBIE_HEAD,
				ZOMBIE_HEART,
				ZOMBIE_LIVER,
				SKULL,
				RIB_BONE,
				SPINE,
				ARM_BONE,
				THIGH_BONE,
				COMPLETE_SKELETON,
				ANATOMY_DIAGRAM);
	}

	private long pieces(QuestState st)
	{
		return st.getQuestItemsCount(ZOMBIE_HEAD) + st.getQuestItemsCount(SPINE) + st.getQuestItemsCount(ARM_BONE) + st.getQuestItemsCount(ZOMBIE_HEART) + st.getQuestItemsCount(ZOMBIE_LIVER) + st.getQuestItemsCount(SKULL) + st.getQuestItemsCount(RIB_BONE) + st.getQuestItemsCount(THIGH_BONE) + st.getQuestItemsCount(COMPLETE_SKELETON);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30336-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30434-03.htm"))
			st.giveItems(ANATOMY_DIAGRAM, 1);
		else if(event.equalsIgnoreCase("30434-06.htm"))
		{
			if(pieces(st) > 0)
			{
				st.rollAndGive(ADENA, 30 * st.getQuestItemsCount(ZOMBIE_HEAD) + 20 * st.getQuestItemsCount(ZOMBIE_HEART) + 20 * st.getQuestItemsCount(ZOMBIE_LIVER) + 50 * st.getQuestItemsCount(SKULL) + 15 * st.getQuestItemsCount(RIB_BONE) + 10 * st.getQuestItemsCount(SPINE) + 10 * st.getQuestItemsCount(ARM_BONE) + 10 * st.getQuestItemsCount(THIGH_BONE) + 2000 * st.getQuestItemsCount(COMPLETE_SKELETON), 100);
				st.takeItems(ZOMBIE_HEAD, -1);
				st.takeItems(ZOMBIE_HEART, -1);
				st.takeItems(ZOMBIE_LIVER, -1);
				st.takeItems(SKULL, -1);
				st.takeItems(RIB_BONE, -1);
				st.takeItems(SPINE, -1);
				st.takeItems(ARM_BONE, -1);
				st.takeItems(THIGH_BONE, -1);
				st.takeItems(COMPLETE_SKELETON, -1);
			}
			st.takeItems(ANATOMY_DIAGRAM, -1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("30434-07.htm") && pieces(st) > 0)
		{
			st.rollAndGive(ADENA, 30 * st.getQuestItemsCount(ZOMBIE_HEAD) + 20 * st.getQuestItemsCount(ZOMBIE_HEART) + 20 * st.getQuestItemsCount(ZOMBIE_LIVER) + 50 * st.getQuestItemsCount(SKULL) + 15 * st.getQuestItemsCount(RIB_BONE) + 10 * st.getQuestItemsCount(SPINE) + 10 * st.getQuestItemsCount(ARM_BONE) + 10 * st.getQuestItemsCount(THIGH_BONE) + 2000 * st.getQuestItemsCount(COMPLETE_SKELETON), 100);
			st.takeItems(ZOMBIE_HEAD, -1);
			st.takeItems(ZOMBIE_HEART, -1);
			st.takeItems(ZOMBIE_LIVER, -1);
			st.takeItems(SKULL, -1);
			st.takeItems(RIB_BONE, -1);
			st.takeItems(SPINE, -1);
			st.takeItems(ARM_BONE, -1);
			st.takeItems(THIGH_BONE, -1);
			st.takeItems(COMPLETE_SKELETON, -1);
		}
		else if(event.equalsIgnoreCase("30434-09.htm"))
		{
			st.rollAndGive(ADENA, 2000 * st.getQuestItemsCount(COMPLETE_SKELETON), 100);
			st.takeItems(COMPLETE_SKELETON, -1);
		}
		else if(event.equalsIgnoreCase("30342-03.htm"))
			if(st.getQuestItemsCount(SPINE) != 0 && st.getQuestItemsCount(ARM_BONE) != 0 && st.getQuestItemsCount(SKULL) != 0 && st.getQuestItemsCount(RIB_BONE) != 0 && st.getQuestItemsCount(THIGH_BONE) != 0)
			{
				st.takeItems(SPINE, 1);
				st.takeItems(SKULL, 1);
				st.takeItems(ARM_BONE, 1);
				st.takeItems(RIB_BONE, 1);
				st.takeItems(THIGH_BONE, 1);
				if(Rnd.chance(80))
					st.giveItems(COMPLETE_SKELETON, 1);
				else
					htmltext = "30342-04.htm";
			}
			else
				htmltext = "30342-02.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(st.isCreated())
			st.set("cond", "0");
		if(npcId == 30336 && st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 15)
			{
				htmltext = "30336-02.htm";
				return htmltext;
			}
			htmltext = "30336-01.htm";
			st.exitCurrentQuest(true);
		}
		else if(npcId == 30336 && cond > 0)
			if(st.getQuestItemsCount(ANATOMY_DIAGRAM) == 0)
				htmltext = "30336-04.htm";
			else
				htmltext = "30336-05.htm";
		else if(npcId == 30434 && cond > 0)
		{
			if(st.getQuestItemsCount(ANATOMY_DIAGRAM) == 0)
				htmltext = "30434-01.htm";
			else if(st.getQuestItemsCount(ANATOMY_DIAGRAM) != 0 && pieces(st) == 0)
				htmltext = "30434-04.htm";
			else if(st.getQuestItemsCount(ANATOMY_DIAGRAM) != 0 && pieces(st) > 0 && st.getQuestItemsCount(COMPLETE_SKELETON) == 0)
				htmltext = "30434-05.htm";
			else if(st.getQuestItemsCount(ANATOMY_DIAGRAM) != 0 && pieces(st) > 0 && st.getQuestItemsCount(COMPLETE_SKELETON) > 0)
				htmltext = "30434-08.htm";
		}
		else if(npcId == 30342 && cond > 0 && st.getQuestItemsCount(ANATOMY_DIAGRAM) > 0)
			htmltext = "30342-01.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getQuestItemsCount(ANATOMY_DIAGRAM) == 0)
			return;
		int n = Rnd.get(100);
		if(npcId == 20026)
		{
			if(n < 90)
			{
				st.playSound(SOUND_ITEMGET);
				if(n < 40)
					st.rollAndGive(ZOMBIE_HEAD, 1, 100);
				else if(n < 60)
					st.rollAndGive(ZOMBIE_HEART, 1, 100);
				else
					st.rollAndGive(ZOMBIE_LIVER, 1, 100);
			}
		}
		else if(npcId == 20029)
		{
			st.playSound(SOUND_ITEMGET);
			if(n < 44)
				st.rollAndGive(ZOMBIE_HEAD, 1, 100);
			else if(n < 66)
				st.rollAndGive(ZOMBIE_HEART, 1, 100);
			else
				st.rollAndGive(ZOMBIE_LIVER, 1, 100);
		}
		else if(npcId == 20035)
		{
			if(n < 79)
			{
				st.playSound(SOUND_ITEMGET);
				if(n < 5)
					st.rollAndGive(SKULL, 1, 100);
				else if(n < 15)
					st.rollAndGive(RIB_BONE, 1, 100);
				else if(n < 29)
					st.rollAndGive(SPINE, 1, 100);
				else
					st.rollAndGive(THIGH_BONE, 1, 100);
			}
		}
		else if(npcId == 20042)
		{
			if(n < 86)
			{
				st.playSound(SOUND_ITEMGET);
				if(n < 6)
					st.giveItems(SKULL, 1);
				else if(n < 19)
					st.giveItems(RIB_BONE, 1);
				else if(n < 69)
					st.giveItems(ARM_BONE, 1);
				else
					st.giveItems(THIGH_BONE, 1);
			}
		}
		else if(npcId == 20045)
		{
			if(n < 97)
			{
				st.playSound(SOUND_ITEMGET);
				if(n < 9)
					st.giveItems(SKULL, 1);
				else if(n < 59)
					st.giveItems(SPINE, 1);
				else if(n < 77)
					st.giveItems(ARM_BONE, 1);
				else
					st.giveItems(THIGH_BONE, 1);
			}
		}
		else if(npcId == 20051)
		{
			if(n < 99)
			{
				st.playSound(SOUND_ITEMGET);
				if(n < 9)
					st.giveItems(SKULL, 1);
				else if(n < 59)
					st.giveItems(RIB_BONE, 1);
				else if(n < 79)
					st.giveItems(SPINE, 1);
				else
					st.giveItems(ARM_BONE, 1);
			}
		}
		else if(npcId == 20514)
		{
			if(n < 51)
			{
				st.playSound(SOUND_ITEMGET);
				if(n < 2)
					st.giveItems(SKULL, 1);
				else if(n < 8)
					st.giveItems(RIB_BONE, 1);
				else if(n < 17)
					st.giveItems(SPINE, 1);
				else if(n < 18)
					st.giveItems(ARM_BONE, 1);
				else
					st.giveItems(THIGH_BONE, 1);
			}
		}
		else if(npcId == 20515)
		{
			if(n < 60)
			{
				st.playSound(SOUND_ITEMGET);
				if(n < 3)
					st.giveItems(SKULL, 1);
				else if(n < 11)
					st.giveItems(RIB_BONE, 1);
				else if(n < 22)
					st.giveItems(SPINE, 1);
				else if(n < 24)
					st.giveItems(ARM_BONE, 1);
				else
					st.giveItems(THIGH_BONE, 1);
			}
		}
		else if(npcId == 20457 || npcId == 20458)
		{
			st.playSound(SOUND_ITEMGET);
			if(n < 42)
				st.giveItems(ZOMBIE_HEAD, 1);
			else if(n < 67)
				st.giveItems(ZOMBIE_HEART, 1);
			else
				st.giveItems(ZOMBIE_LIVER, 1);
		}
	}
}