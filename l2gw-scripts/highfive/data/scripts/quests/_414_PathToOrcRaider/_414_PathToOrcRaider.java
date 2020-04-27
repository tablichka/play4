package quests._414_PathToOrcRaider;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;

/**
 * <hr><em>Квест</em> <strong>Path to Orc Raider</strong><hr>
 *
 * @author
 * @version CT2
 * @lastfix HellSinger
 */
public class _414_PathToOrcRaider extends Quest
{
	//npc
	public static final int KARUKIA = 30570;
	public static final int KASMAN = 30501;
	public static final int TAZEER = 31978;
	//mobs
	public static final int GOBLIN_TOMB_RAIDER_LEADER = 20320;
	public static final int KURUKA_RATMAN_LEADER = 27045;
	public static final int UMBAR_ORC = 27054;
	public static final int TIMORA_ORC = 27320;
	//items
	public static final int GREEN_BLOOD = 1578;
	public static final int GOBLIN_DWELLING_MAP = 1579;
	public static final int KURUKA_RATMAN_TOOTH = 1580;

	public static final int BETRAYER_UMBAR_REPORT = 1589;
	public static final int HEAD_OF_BETRAYER = 1591;
	public static final int TIMORA_ORCS_HEAD = 8544;
	public static final int MARK_OF_RAIDER = 1592;

	public _414_PathToOrcRaider()
	{
		super(414, "_414_PathToOrcRaider", "Path to Orc Raider");

		addStartNpc(KARUKIA);
		addTalkId(KASMAN, TAZEER);
		addKillId(GOBLIN_TOMB_RAIDER_LEADER, KURUKA_RATMAN_LEADER, UMBAR_ORC, TIMORA_ORC);
		addQuestItem(KURUKA_RATMAN_TOOTH, GOBLIN_DWELLING_MAP, GREEN_BLOOD, HEAD_OF_BETRAYER, BETRAYER_UMBAR_REPORT, TIMORA_ORCS_HEAD);
	}

	private void reward(QuestState st)
	{
		if(st.getPlayer().getClassId().getLevel() == 1)
		{
			st.giveItems(MARK_OF_RAIDER, 1);
			if(!st.getPlayer().getVarB("prof1"))
			{
				st.getPlayer().setVar("prof1", "1");
				if(st.getPlayer().getLevel() >= 20)
					st.addExpAndSp(320534, 21312);
				else if(st.getPlayer().getLevel() == 19)
					st.addExpAndSp(456128, 28010);
				else
					st.addExpAndSp(591724, 34708);
				st.rollAndGive(57, 163800, 100);
			}
		}
		st.showSocial(3);
		st.playSound(SOUND_FINISH);
		st.exitCurrentQuest(true);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30570-05.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.giveItems(GOBLIN_DWELLING_MAP, 1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("to_Gludin"))
		{
			htmltext = "30570-07.htm";
			st.takeItems(KURUKA_RATMAN_TOOTH, -1);
			st.takeItems(GOBLIN_DWELLING_MAP, -1);
			st.giveItems(BETRAYER_UMBAR_REPORT, 1);
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("to_Schuttgart"))
		{
			htmltext = "30570-07a.htm";
			st.takeItems(KURUKA_RATMAN_TOOTH, -1);
			st.takeItems(GOBLIN_DWELLING_MAP, -1);
			st.set("cond", "5");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("31978-02.htm"))
		{
			st.set("cond", "6");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		int playerClassID = st.getPlayer().getClassId().getId();
		if(st.isCreated() && npcId == KARUKIA)
		{
			if(st.getQuestItemsCount(MARK_OF_RAIDER) > 0)
			{
				htmltext = "30570-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(playerClassID != 0x2c)
			{
				if(playerClassID == 0x2d)
					htmltext = "30570-02a.htm";
				else
					htmltext = "30570-03.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30570-02.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30570-01.htm";
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case KARUKIA:
				{
					if(cond == 1 && st.getQuestItemsCount(GOBLIN_DWELLING_MAP) > 0 && st.getQuestItemsCount(KURUKA_RATMAN_TOOTH) < 10)
						htmltext = "30570-06.htm";
					else if(cond == 2 && st.getQuestItemsCount(GOBLIN_DWELLING_MAP) > 0 && st.getQuestItemsCount(KURUKA_RATMAN_TOOTH) > 9)
						htmltext = "30570-10.htm";
					else if(cond == 3 && st.getQuestItemsCount(BETRAYER_UMBAR_REPORT) > 0 && st.getQuestItemsCount(HEAD_OF_BETRAYER) < 2)
						htmltext = "30570-08.htm";
					else if(cond == 4 && st.getQuestItemsCount(BETRAYER_UMBAR_REPORT) > 0 && st.getQuestItemsCount(HEAD_OF_BETRAYER) > 1)
						htmltext = "30570-09.htm";
					break;
				}
				case KASMAN:
				{
					if(cond == 3 && st.getQuestItemsCount(BETRAYER_UMBAR_REPORT) > 0 && st.getQuestItemsCount(HEAD_OF_BETRAYER) < 1)
						htmltext = "30501-01.htm";
					else if(cond == 3 && st.getQuestItemsCount(HEAD_OF_BETRAYER) > 0 && st.getQuestItemsCount(HEAD_OF_BETRAYER) < 2)
						htmltext = "30501-02.htm";
					else if(cond == 4 && st.getQuestItemsCount(HEAD_OF_BETRAYER) > 1)
					{
						htmltext = "30501-03.htm";
						reward(st);
					}
					break;
				}
				case TAZEER:
				{
					if(cond == 5)
						htmltext = "31978-01.htm";
					else if(cond == 6 && st.getQuestItemsCount(TIMORA_ORCS_HEAD) < 1)
						htmltext = "31978-03.htm";
					else if(cond == 7 && st.getQuestItemsCount(TIMORA_ORCS_HEAD) > 0)
					{
						htmltext = "31978-04.htm";
						reward(st);
					}
					break;
				}
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == GOBLIN_TOMB_RAIDER_LEADER && cond == 1 && st.getQuestItemsCount(GOBLIN_DWELLING_MAP) > 0 && st.getQuestItemsCount(KURUKA_RATMAN_TOOTH) < 10)
		{
			st.rollAndGiveLimited(GREEN_BLOOD, 1, 100, 10);
			if(Rnd.chance(st.getQuestItemsCount(GREEN_BLOOD) * 10))
			{
				st.takeItems(GREEN_BLOOD, -1);
				st.getPcSpawn().addSpawn(KURUKA_RATMAN_LEADER);
				for(L2Spawn spawn : st.getPcSpawn().getSpawns())
				{
					L2NpcInstance ratman = spawn.getLastSpawn();
					ratman.addDamageHate(st.getPlayer(), 0, 999);
					ratman.getAI().setIntention(AI_INTENTION_ATTACK, st.getPlayer());
				}
			}
		}
		else if(npcId == KURUKA_RATMAN_LEADER && cond == 1 && st.getQuestItemsCount(GOBLIN_DWELLING_MAP) > 0)
		{
			if(st.rollAndGiveLimited(KURUKA_RATMAN_TOOTH, 1, 100, 10))
			{
				if(st.getQuestItemsCount(KURUKA_RATMAN_TOOTH) == 10)
				{
					st.set("cond", "2");
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == UMBAR_ORC && cond == 3 && st.getQuestItemsCount(BETRAYER_UMBAR_REPORT) > 0)
		{
			if(st.rollAndGiveLimited(HEAD_OF_BETRAYER, 1, 100, 2))
			{
				if(st.getQuestItemsCount(HEAD_OF_BETRAYER) == 2)
				{
					st.set("cond", "4");
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == TIMORA_ORC && cond == 6)
			if(st.rollAndGiveLimited(TIMORA_ORCS_HEAD, 1, 50, 1))
			{
				st.set("cond", "7");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
	}
}