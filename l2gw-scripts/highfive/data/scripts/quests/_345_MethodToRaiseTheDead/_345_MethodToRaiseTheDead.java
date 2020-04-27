package quests._345_MethodToRaiseTheDead;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _345_MethodToRaiseTheDead extends Quest
{

	int ADENA = 57;
	int VICTIMS_ARM_BONE = 4274;
	int VICTIMS_THIGH_BONE = 4275;
	int VICTIMS_SKULL = 4276;
	int VICTIMS_RIB_BONE = 4277;
	int VICTIMS_SPINE = 4278;
	int USELESS_BONE_PIECES = 4280;
	int POWDER_TO_SUMMON_DEAD_SOULS = 4281;
	int BILL_OF_IASON_HEINE = 4310;
	int CHANCE = 15;
	int CHANCE2 = 50;

	public _345_MethodToRaiseTheDead()
	{
		super(345, "_345_MethodToRaiseTheDead", "Method To Raise The Dead");

		addStartNpc(30970);

		addTalkId(30970);
		addTalkId(30970);
		addTalkId(30912);
		addTalkId(30973);

		addQuestItem(VICTIMS_ARM_BONE, VICTIMS_THIGH_BONE, VICTIMS_SKULL, VICTIMS_RIB_BONE, VICTIMS_SPINE, POWDER_TO_SUMMON_DEAD_SOULS);

		addKillId(20789);
		addKillId(20791);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			htmltext = "30970-02.htm";
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("2"))
		{
			st.set("cond", "2");
			htmltext = "30970-06.htm";
		}
		else if(event.equals("3"))
		{
			if(st.getQuestItemsCount(ADENA) >= 1000)
			{
				st.takeItems(ADENA, 1000);
				st.giveItems(POWDER_TO_SUMMON_DEAD_SOULS, 1);
				st.set("cond", "3");
				htmltext = "30912-03.htm";
				st.playSound(SOUND_ITEMGET);
			}
			else
				htmltext = "<html><head><body>You dont have enough adena!</body></html>";
		}
		else if(event.equals("4"))
		{
			htmltext = "30973-02.htm";
			st.takeItems(POWDER_TO_SUMMON_DEAD_SOULS, -1);
			st.takeItems(VICTIMS_ARM_BONE, -1);
			st.takeItems(VICTIMS_THIGH_BONE, -1);
			st.takeItems(VICTIMS_SKULL, -1);
			st.takeItems(VICTIMS_RIB_BONE, -1);
			st.takeItems(VICTIMS_SPINE, -1);
			st.set("cond", "6");
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int level = st.getPlayer().getLevel();
		int cond = st.getInt("cond");
		long amount = st.getQuestItemsCount(USELESS_BONE_PIECES);
		if(npcId == 30970)
			if(st.isCreated())
			{
				if(level >= 35)
					htmltext = "30970-01.htm";
				else
				{
					htmltext = "<html><head><body>(This is a quest that can only be performed by players of level 35 and above.)</body></html>";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1 && st.getQuestItemsCount(VICTIMS_ARM_BONE) > 0 && st.getQuestItemsCount(VICTIMS_THIGH_BONE) > 0 && st.getQuestItemsCount(VICTIMS_SKULL) > 0 && st.getQuestItemsCount(VICTIMS_RIB_BONE) > 0 && st.getQuestItemsCount(VICTIMS_SPINE) > 0)
				htmltext = "30970-05.htm";
			else if(cond == 1 && st.getQuestItemsCount(VICTIMS_ARM_BONE) + st.getQuestItemsCount(VICTIMS_THIGH_BONE) + st.getQuestItemsCount(VICTIMS_SKULL) + st.getQuestItemsCount(VICTIMS_RIB_BONE) + st.getQuestItemsCount(VICTIMS_SPINE) < 5)
				htmltext = "30970-04.htm";
			else if(cond == 7)
			{
				htmltext = "30970-07.htm";
				st.set("cond", "1");
				st.rollAndGive(ADENA, amount * 238, 100);
				st.giveItems(BILL_OF_IASON_HEINE, Rnd.get(7) + 1);
				st.takeItems(USELESS_BONE_PIECES, -1);
			}
		if(npcId == 30912)
			if(cond == 2)
			{
				htmltext = "30912-01.htm";
				st.playSound(SOUND_MIDDLE);
			}
			else if(cond == 3)
				htmltext = "<html><head><body>What did the urn say?</body></html>";
			else if(cond == 6)
			{
				htmltext = "30912-04.htm";
				st.set("cond", "7");
			}
		if(npcId == 30973)
			if(cond == 3)
				htmltext = "30973-01.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.rollAndGiveLimited(VICTIMS_ARM_BONE, 1, CHANCE, 1))
			st.playSound(SOUND_ITEMGET);
		else if(st.rollAndGiveLimited(VICTIMS_THIGH_BONE, 1, CHANCE, 1))
			st.playSound(SOUND_ITEMGET);
		else if(st.rollAndGiveLimited(VICTIMS_SKULL, 1, CHANCE, 1))
			st.playSound(SOUND_ITEMGET);
		else if(st.rollAndGiveLimited(VICTIMS_RIB_BONE, 1, CHANCE, 1))
			st.playSound(SOUND_ITEMGET);
		else if(st.rollAndGiveLimited(VICTIMS_SPINE, 1, CHANCE, 1))
			st.playSound(SOUND_ITEMGET);

		st.rollAndGive(USELESS_BONE_PIECES, Rnd.get(8) + 1, CHANCE2);
	}
}