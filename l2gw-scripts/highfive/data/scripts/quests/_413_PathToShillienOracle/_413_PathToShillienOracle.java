package quests._413_PathToShillienOracle;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _413_PathToShillienOracle extends Quest
{
	//npc
	public final int SIDRA = 30330;
	public final int ADONIUS = 30375;
	public final int TALBOT = 30377;
	//mobs
	public final int ZOMBIE_SOLDIER = 20457;
	public final int ZOMBIE_WARRIOR = 20458;
	public final int SHIELD_SKELETON = 20514;
	public final int SKELETON_INFANTRYMAN = 20515;
	public final int DARK_SUCCUBUS = 20776;
	//items
	public final int SIDRAS_LETTER1_ID = 1262;
	public final int BLANK_SHEET1_ID = 1263;
	public final int BLOODY_RUNE1_ID = 1264;
	public final int GARMIEL_BOOK_ID = 1265;
	public final int PRAYER_OF_ADON_ID = 1266;
	public final int PENITENTS_MARK_ID = 1267;
	public final int ASHEN_BONES_ID = 1268;
	public final int ANDARIEL_BOOK_ID = 1269;
	public final int ORB_OF_ABYSS_ID = 1270;
	//ASHEN_BONES_DROP [moblist]
	public final int[] ASHEN_BONES_DROP = {ZOMBIE_SOLDIER, ZOMBIE_WARRIOR, SHIELD_SKELETON, SKELETON_INFANTRYMAN};

	public _413_PathToShillienOracle()
	{
		super(413, "_413_PathToShillienOracle", "Path to Shillien Oracle");

		addStartNpc(SIDRA);

		addTalkId(SIDRA);
		addTalkId(ADONIUS);
		addTalkId(TALBOT);

		addKillId(DARK_SUCCUBUS);

		for(int i : ASHEN_BONES_DROP)
			addKillId(i);

		addQuestItem(ASHEN_BONES_ID);

		addQuestItem(SIDRAS_LETTER1_ID,
				ANDARIEL_BOOK_ID,
				PENITENTS_MARK_ID,
				GARMIEL_BOOK_ID,
				PRAYER_OF_ADON_ID,
				BLANK_SHEET1_ID,
				BLOODY_RUNE1_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("1"))
		{
			htmltext = "30330-06.htm";
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(SIDRAS_LETTER1_ID, 1);
		}
		else if(event.equalsIgnoreCase("413_1"))
		{
			if(st.getPlayer().getClassId().getId() != 0x26)
			{
				if(st.getPlayer().getClassId().getId() == 0x2a)
					htmltext = "30330-02a.htm";
				else
					htmltext = "30330-03.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(ORB_OF_ABYSS_ID) > 0)
			{
				htmltext = "30330-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30330-02.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30330-05.htm";
		}
		else if(event.equalsIgnoreCase("30377_1"))
		{
			htmltext = "30377-02.htm";
			st.takeItems(SIDRAS_LETTER1_ID, -1);
			st.giveItems(BLANK_SHEET1_ID, 5);
			st.playSound(SOUND_ITEMGET);
			st.set("cond", "2");
		}
		else if(event.equalsIgnoreCase("30375_1"))
			htmltext = "30375-02.htm";
		else if(event.equalsIgnoreCase("30375_2"))
			htmltext = "30375-03.htm";
		else if(event.equalsIgnoreCase("30375_3"))
		{
			htmltext = "30375-04.htm";
			st.takeItems(PRAYER_OF_ADON_ID, -1);
			st.giveItems(PENITENTS_MARK_ID, 1);
			st.playSound(SOUND_ITEMGET);
			st.set("cond", "5");
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == SIDRA)
		{
			if(cond < 1)
				htmltext = "30330-01.htm";
			else if(cond == 1)
				htmltext = "30330-07.htm";
			else if(cond == 2 | cond == 3)
				htmltext = "30330-08.htm";
			else if(cond > 3 && cond < 7)
				htmltext = "30330-09.htm";
			else if(cond == 7 && st.getQuestItemsCount(ANDARIEL_BOOK_ID) > 0 && st.getQuestItemsCount(GARMIEL_BOOK_ID) > 0)
			{
				htmltext = "30330-10.htm";
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					st.giveItems(ORB_OF_ABYSS_ID, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 26532);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 33230);
						else
							st.addExpAndSp(591724, 39928);
						st.rollAndGive(57, 163800, 100);
					}
				}
				st.showSocial(3);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == TALBOT)
		{
			if(cond == 1 && st.getQuestItemsCount(SIDRAS_LETTER1_ID) > 0)
				htmltext = "30377-01.htm";
			else if(cond == 2)
			{
				if(st.getQuestItemsCount(BLOODY_RUNE1_ID) < 1)
					htmltext = "30377-03.htm";
				else if(st.getQuestItemsCount(BLOODY_RUNE1_ID) > 0)
					htmltext = "30377-04.htm";
			}
			else if(cond == 3 && st.getQuestItemsCount(BLOODY_RUNE1_ID) > 4)
			{
				htmltext = "30377-05.htm";
				st.takeItems(BLOODY_RUNE1_ID, -1);
				st.giveItems(GARMIEL_BOOK_ID, 1);
				st.giveItems(PRAYER_OF_ADON_ID, 1);
				st.playSound(SOUND_ITEMGET);
				st.set("cond", "4");
			}
			else if(cond > 3 && cond < 7)
				htmltext = "30377-06.htm";
			else if(cond == 7)
				htmltext = "30377-07.htm";
		}
		else if(npcId == ADONIUS)
			if(cond == 4 && st.getQuestItemsCount(PRAYER_OF_ADON_ID) > 0)
				htmltext = "30375-01.htm";
			else if(cond == 5 && st.getQuestItemsCount(ASHEN_BONES_ID) < 1)
				htmltext = "30375-05.htm";
			else if(cond == 5 && st.getQuestItemsCount(ASHEN_BONES_ID) < 10)
				htmltext = "30375-06.htm";
			else if(cond == 6 && st.getQuestItemsCount(ASHEN_BONES_ID) > 9)
			{
				htmltext = "30375-07.htm";
				st.takeItems(ASHEN_BONES_ID, -1);
				st.takeItems(PENITENTS_MARK_ID, -1);
				st.giveItems(ANDARIEL_BOOK_ID, 1);
				st.playSound(SOUND_ITEMGET);
				st.set("cond", "7");
			}
			else if(cond == 7 && st.getQuestItemsCount(ANDARIEL_BOOK_ID) > 0)
				htmltext = "30375-08.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == DARK_SUCCUBUS)
			if(cond == 2 && st.getQuestItemsCount(BLANK_SHEET1_ID) > 0)
			{
				st.giveItems(BLOODY_RUNE1_ID, 1);
				st.takeItems(BLANK_SHEET1_ID, 1);
				if(st.getQuestItemsCount(BLANK_SHEET1_ID) < 1)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "3");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		for(int i : ASHEN_BONES_DROP)
			if(npcId == i && cond == 5 && st.rollAndGiveLimited(ASHEN_BONES_ID, 1, 100, 10))
			{
				if(st.getQuestItemsCount(ASHEN_BONES_ID) == 10)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "6");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
	}
}