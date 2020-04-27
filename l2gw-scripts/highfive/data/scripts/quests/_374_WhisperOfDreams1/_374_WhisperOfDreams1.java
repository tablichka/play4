package quests._374_WhisperOfDreams1;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

public class _374_WhisperOfDreams1 extends Quest
{
	//Chance to obtain Sealed Mysterious Stone (in percent)
	private double SEALD_MSTONE_CHANCE = 0.4;
	//Rewards
	private static final String[][] REWARD_DESC = {
			{"etc_leather_yellow_i00", "Sealed Tallum Tunic Textures"},
			{"etc_leather_gray_i00", "Sealed Dark Crystal Robe Fabrics"},
			{"etc_leather_gray_i00", "Sealed Nightmare Robe Fabric"},
			{"etc_leather_gray_i00", "Sealed Majestic Robe Frabrics"},
			{"etc_leather_gray_i00", "Sealed Tallum Stockings Fabrics"}};

	private static final int[][] REWARDS = {
			//ITEM, COUNT, ADENA_AMOUNT
			{5485, 4, 10450},
			{5486, 3, 2950},
			{5487, 2, 18050},
			{5488, 2, 18050},
			{5489, 6, 15550}};

	//Quest items
	private int CB_TOOTH = 5884; //Cave Beast Tooth
	private int DW_LIGHT = 5885; //Death Wave Light
	private int SEALD_MSTONE = 5886; //Sealed Mysterious Stone
	private int MSTONE = 5887; //Mysterious Stone

	//NPCs
	private int MANAKIA = 30515;
	private int TORAI = 30557;

	//Mobs & Drop
	private int[][] DROPLIST = {{20620, CB_TOOTH}, {20621, DW_LIGHT}};

	private String render_shop()
	{
		String html = "<html><head><body><font color=\"LEVEL\">Robe Armor Fabrics:</font><table border=0 width=300>";
		for(int i = 0; i < REWARD_DESC.length; i++)
		{
			html += "<tr><td width=35 height=45><img src=icon." + REWARD_DESC[i][0] + " width=32 height=32 align=left></td><td width=365 valign=top><table border=0 width=100%>";
			html += "<tr><td><a action=\"bypass -h Quest _374_WhisperOfDreams1 " + REWARDS[i][0] + "\"><font color=\"FFFFFF\">" + REWARD_DESC[i][1] + " x" + REWARDS[i][1] + "</font></a></td></tr>";
			html += "<tr><td><a action=\"bypass -h Quest _374_WhisperOfDreams1 " + REWARDS[i][0] + "\"><font color=\"B09878\">" + REWARDS[i][2] + " adena</font></a></td></tr></table></td></tr>";
		}
		html += "</table></body></html>";
		return html;
	}

	public _374_WhisperOfDreams1()
	{
		super(374, "_374_WhisperOfDreams1", "Whisper Of Dreams - Part 1"); // Party true

		//Quest NPC starter initialization
		addStartNpc(MANAKIA);
		//Quest initialization
		addTalkId(MANAKIA);
		addTalkId(TORAI);

		for(int[] e : DROPLIST)
		{
			addKillId(e[0]);
			addQuestItem(e[1]);
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30515-4.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30515-5.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("30515-8.htm"))
		{
			if(st.getQuestItemsCount(SEALD_MSTONE) > 0 && st.getInt("cond") < 2)
			{
				st.set("cond", "2");
				htmltext = "30515-9.htm";
			}
			else if(st.getInt("cond") == 2)
				htmltext = "30515-10.htm";
		}
		else if(event.equalsIgnoreCase("buy"))
			htmltext = render_shop();
		else if(isdigit(event))
		{
			if(st.getQuestItemsCount(CB_TOOTH) < 65 || st.getQuestItemsCount(DW_LIGHT) < 65)
				htmltext = "30515-6.htm";
			else
			{
				for(int[] e : REWARDS)
				{
					int evt = Integer.parseInt(event);
					if(evt == e[0])
					{
						htmltext = "30515-11.htm";
						st.takeItems(CB_TOOTH, -1);
						st.takeItems(DW_LIGHT, -1);
						st.rollAndGive(57, e[2], 100);
						st.giveItems(e[0], e[1]);
						st.playSound(SOUND_FINISH);
						st.exitCurrentQuest(true);
					}
				}
			}
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		int npcid = npc.getNpcId();
		if(st.isCreated() && npcid == MANAKIA)
		{
			if(st.getPlayer().getLevel() < 56)
			{
				st.exitCurrentQuest(true);
				htmltext = "30515-2.htm";
			}
			else
				htmltext = "30515-1.htm";
		}
		else if(st.isStarted())
		{
			if(npcid == MANAKIA)
			{
				if(st.getQuestItemsCount(CB_TOOTH) < 65 || st.getQuestItemsCount(DW_LIGHT) < 65)
					htmltext = "30515-3a.htm";
				else if(cond == 1)
					htmltext = "30515-7.htm";
				else if(cond > 1)
					htmltext = "30515-3.htm";
			}
			else if(npcid == TORAI && st.getQuestItemsCount(SEALD_MSTONE) > 0)
			{
				htmltext = "30557-1.htm";
				st.takeItems(SEALD_MSTONE, -1);
				st.giveItems(MSTONE, 1);
				st.set("cond", "3");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcid = npc.getNpcId();
		int item = -1;
		for(int[] e : DROPLIST)
			if(npcid == e[0])
				item = e[1];
		if(item == -1)
			return;

		GArray<QuestState> pm = new GArray<QuestState>();

		for(int i = 1; i <= 3; i++)
		{
			for(QuestState qs : getPartyMembersWithQuest(killer, i))
			{
				if(qs.getQuestItemsCount(item) < 65)
					pm.add(qs);
			}
		}


		if(!pm.isEmpty())
		{
			QuestState st = pm.get(Rnd.get(pm.size()));
			if(st.rollAndGiveLimited(item, 1, 20, 65))
				st.playSound(st.getQuestItemsCount(item) == 65 ? SOUND_MIDDLE : SOUND_ITEMGET);
			if(st.rollAndGiveLimited(SEALD_MSTONE, 1, SEALD_MSTONE_CHANCE, 1))
				st.playSound(SOUND_MIDDLE);

		}

	}
}