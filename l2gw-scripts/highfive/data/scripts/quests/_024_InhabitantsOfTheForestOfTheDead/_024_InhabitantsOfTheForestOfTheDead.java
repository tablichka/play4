package quests._024_InhabitantsOfTheForestOfTheDead;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _024_InhabitantsOfTheForestOfTheDead extends Quest
{
	// Список NPC
	private final int DORIAN = 31389;
	private final int TOMBSTONE = 31531;
	private final int MAID_OF_LIDIA = 31532;
	private final int MYSTERIOUS_WIZARD = 31522;

	// Список итемов
	private final int LIDIA_HAIR_PIN = 7148;
	private final int SUSPICIOUS_TOTEM_DOLL = 7151;
	private final int FLOWER_BOUQUET = 7152;
	private final int SILVER_CROSS_OF_EINHASAD = 7153;
	private final int BROKEN_SILVER_CROSS_OF_EINHASAD = 7154;
	private final int LIDIAS_LETTER = 7065;

	// Bone Snatchers, Bone Shapers, Bone Collectors, Bone Animators, Bone Slayers, Skull Collectors, Skull Animators
	private final int[] MOBS = new int[]{21557, 21558, 21560, 21561, 21562, 21563, 21564, 21565, 21566, 21567};

	public _024_InhabitantsOfTheForestOfTheDead()
	{
		super(24, "_024_InhabitantsOfTheForestOfTheDead", "Inhabitants of the Forest of the Dead");

		addStartNpc(DORIAN);

		addTalkId(TOMBSTONE);
		addTalkId(MAID_OF_LIDIA);
		addTalkId(MYSTERIOUS_WIZARD);

		for(int npcId : MOBS)
			addKillId(npcId);

		addQuestItem(LIDIA_HAIR_PIN,
				SUSPICIOUS_TOTEM_DOLL,
				FLOWER_BOUQUET,
				SILVER_CROSS_OF_EINHASAD,
				BROKEN_SILVER_CROSS_OF_EINHASAD,
				LIDIAS_LETTER);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:completed";

		return event;
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("completed", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == DORIAN)
		{
			if(reply == 24) // quest accept
			{
				if(st.isCreated() && !player.isQuestComplete(23) || player.getLevel() < 65)
				{
					showQuestPage("day_dorian_q0024_02.htm", player);
					return;
				}
				else if(st.isCreated() && player.isQuestComplete(23) && player.getLevel() >= 65)
				{
					st.giveItems(FLOWER_BOUQUET, 1);
					st.setState(STARTED);
					showQuestMark(player);
					st.setMemoState(1);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("day_dorian_q0024_03.htm", player);
					st.setCond(1);
					return;
				}
			}
			else if(reply == 1)
			{
				st.setMemoState(3);
				showPage("day_dorian_q0024_08.htm", player);
				return;
			}
			else if(reply == 2)
			{
				showPage("day_dorian_q0024_12.htm", player);
				return;
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 3)
				{
					st.giveItems(SILVER_CROSS_OF_EINHASAD, 1);
					st.setMemoState(4);
					showPage("day_dorian_q0024_13.htm", player);
					st.setCond(3);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
					return;
				}
			}
			else if(reply == 4)
			{
				showPage("day_dorian_q0024_17.htm", player);
				return;
			}
			else if(reply == 5)
			{
				showPage("day_dorian_q0024_18.htm", player);
				st.playSound("InterfaceSound.charstat_open_01");
				return;
			}
			else if(reply == 6)
			{
				if(st.isStarted() && st.getMemoState() == 4 && st.getQuestItemsCount(BROKEN_SILVER_CROSS_OF_EINHASAD) >= 1)
				{
					st.takeItems(BROKEN_SILVER_CROSS_OF_EINHASAD, -1);
					st.setMemoState(5);
					showPage("day_dorian_q0024_19.htm", player);
					st.setCond(5);
					showQuestMark(player);
					return;
				}
			}
		}
		else if(npcId == TOMBSTONE)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(FLOWER_BOUQUET) >= 1)
				{
					st.takeItems(FLOWER_BOUQUET, -1);
					st.setMemoState(2);
					showPage("q_forest_stone2_q0024_02.htm", player);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
					return;
				}
			}
		}
		else if(npcId == MAID_OF_LIDIA)
		{
			if(reply == 7)
			{
				if(st.isStarted() && st.getMemoState() == 5)
				{
					st.giveItems(LIDIAS_LETTER, 1);
					st.setMemoState(6);
					showPage("maid_of_ridia_q0024_04.htm", player);
					st.setCond(6);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
					return;
				}
			}
			else if(reply == 8)
			{
				if(st.isStarted() && st.getMemoState() == 6 || (st.getMemoState() == 7 && st.getQuestItemsCount(LIDIA_HAIR_PIN) >= 1))
				{
					st.takeItems(LIDIAS_LETTER, -1);
					st.takeItems(LIDIA_HAIR_PIN, -1);
					st.setMemoState(8);
					showPage("maid_of_ridia_q0024_06.htm", player);
					return;
				}
				else if(st.isStarted() && st.getMemoState() == 6 || (st.getMemoState() == 7 && st.getQuestItemsCount(LIDIA_HAIR_PIN) == 0))
				{
					st.setMemoState(7);
					showPage("maid_of_ridia_q0024_07.htm", player);
					st.setCond(7);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
					return;
				}
			}
			else if(reply == 9)
			{
				showPage("maid_of_ridia_q0024_09.htm", player);
				return;
			}
			else if(reply == 10)
			{
				if(st.isStarted() && st.getMemoState() == 8)
				{
					st.setMemoState(9);
					showPage("maid_of_ridia_q0024_10.htm", player);
					return;
				}
			}
			else if(reply == 11)
			{
				if(st.isStarted() && st.getMemoState() == 9)
				{
					st.setMemoState(10);
					showPage("maid_of_ridia_q0024_14.htm", player);
					return;
				}
			}
			else if(reply == 12)
			{
				if(st.isStarted() && st.getMemoState() == 10)
				{
					st.setMemoState(11);
					showPage("maid_of_ridia_q0024_19.htm", player);
					st.setCond(9);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
					return;
				}
			}
		}
		else if(npcId == MYSTERIOUS_WIZARD)
		{
			if(reply == 13)
			{
				showPage("shadow_hardin_q0024_02.htm", player);
				return;
			}
			else if(reply == 14)
			{
				if(st.isStarted() && st.getMemoState() == 11 && st.getQuestItemsCount(SUSPICIOUS_TOTEM_DOLL) >= 1)
				{
					st.takeItems(SUSPICIOUS_TOTEM_DOLL, -1);
					st.setMemoState(12);
					showPage("shadow_hardin_q0024_03.htm", player);
					return;
				}
			}
			else if(reply == 15)
			{
				showPage("shadow_hardin_q0024_05.htm", player);
				return;
			}
			else if(reply == 16)
			{
				if(st.isStarted() && st.getMemoState() == 12)
				{
					st.setMemoState(13);
					showPage("shadow_hardin_q0024_08.htm", player);
					st.setCond(11);
					showQuestMark(player);
					return;
				}
			}
			else if(reply == 17)
			{
				showPage("shadow_hardin_q0024_16.htm", player);
				return;
			}
			else if(reply == 18)
			{
				if(st.isStarted() && st.getMemoState() == 13)
				{
					st.setMemoState(14);
					showPage("shadow_hardin_q0024_17.htm", player);
					return;
				}
			}
			else if(reply == 19)
			{
				if(st.isStarted() && st.getMemoState() == 14)
				{
					int SUSPICIOUS_TOTEM_DOLL1 = 7156;
					st.giveItems(SUSPICIOUS_TOTEM_DOLL1, 1);
					st.addExpAndSp(242105, 22529);
					st.setState(COMPLETED);
					st.playSound(SOUND_FINISH);
					showPage("shadow_hardin_q0024_21.htm", player);
					st.exitCurrentQuest(false);
					return;
				}
			}
		}
		showPage("noquest", player);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(npc.getNpcId() == MYSTERIOUS_WIZARD && st.isCompleted() && !st.getPlayer().isQuestComplete(25) && !st.getPlayer().isQuestStarted(25))
			return "npchtm:shadow_hardin_q0024_22.htm";

		if(st.isCompleted())
			return "completed";

		String htmltext = "noquest";
		int npcId = npc.getNpcId();

		if(npcId == DORIAN)
		{
			if(st.isCreated())
			{
				htmltext = "day_dorian_q0024_01.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				htmltext = "npchtm:day_dorian_q0024_04.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 2)
			{
				htmltext = "npchtm:day_dorian_q0024_05.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 3)
			{
				htmltext = "npchtm:day_dorian_q0024_09.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 4 && st.getQuestItemsCount(SILVER_CROSS_OF_EINHASAD) >= 1)
			{
				htmltext = "npchtm:day_dorian_q0024_14.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 4 && st.getQuestItemsCount(BROKEN_SILVER_CROSS_OF_EINHASAD) >= 1)
			{
				htmltext = "npchtm:day_dorian_q0024_15.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 5)
			{
				htmltext = "npchtm:day_dorian_q0024_20.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 7 && st.getQuestItemsCount(LIDIA_HAIR_PIN) == 0)
			{
				st.giveItems(LIDIA_HAIR_PIN, 1);
				st.setCond(8);
				showQuestMark(st.getPlayer());
				st.playSound(SOUND_MIDDLE);
				htmltext = "npchtm:day_dorian_q0024_21.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 6 || (st.getMemoState() == 7 && st.getQuestItemsCount(LIDIA_HAIR_PIN) >= 1))
			{
				htmltext = "npchtm:day_dorian_q0024_22.htm";
			}
		}
		else if(npcId == TOMBSTONE)
		{
			if(st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(FLOWER_BOUQUET) >= 1)
			{
				htmltext = "npchtm:q_forest_stone2_q0024_01.htm";
				st.playSound("AmdSound.d_wind_loot_02");
			}
			else if(st.isStarted() && st.getMemoState() == 2)
			{
				htmltext = "npchtm:q_forest_stone2_q0024_03.htm";
			}
		}
		else if(npcId == MAID_OF_LIDIA)
		{
			if(st.isStarted() && st.getMemoState() == 5)
			{
				htmltext = "npchtm:maid_of_ridia_q0024_01.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 6 && st.getQuestItemsCount(LIDIAS_LETTER) >= 1)
			{
				htmltext = "npchtm:maid_of_ridia_q0024_05.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 7)
			{
				htmltext = "npchtm:maid_of_ridia_q0024_07a.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 8)
			{
				htmltext = "npchtm:maid_of_ridia_q0024_08.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 9)
			{
				htmltext = "npchtm:maid_of_ridia_q0024_11.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 10)
			{
				htmltext = "npchtm:maid_of_ridia_q0024_15.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 11)
			{
				htmltext = "npchtm:maid_of_ridia_q0024_20.htm";
			}
		}
		else if(npcId == MYSTERIOUS_WIZARD)
		{
			if(st.isStarted() && st.getMemoState() == 11 && st.getQuestItemsCount(SUSPICIOUS_TOTEM_DOLL) >= 1)
			{
				htmltext = "npchtm:shadow_hardin_q0024_01.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 12)
			{
				htmltext = "npchtm:shadow_hardin_q0024_04.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 13)
			{
				htmltext = "npchtm:shadow_hardin_q0024_09.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 14)
			{
				htmltext = "npchtm:shadow_hardin_q0024_18.htm";
			}
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(arrayContains(MOBS, npcId))
			if(st.getMemoState() == 11 && st.getQuestItemsCount(SUSPICIOUS_TOTEM_DOLL) == 0 && st.rollAndGiveLimited(SUSPICIOUS_TOTEM_DOLL, 1, 10, 1))
			{
				st.playSound(SOUND_ITEMGET);
				st.setCond(10);
				showQuestMark(st.getPlayer());
			}
	}

	private boolean arrayContains(int[] array, int id)
	{
		for(int i : array)
			if(i == id)
				return true;
		return false;
	}
}
