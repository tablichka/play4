package quests._211_TrialOfChallenger;

import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _211_TrialOfChallenger extends Quest
{
	//NPC Id's
	private static final int KASH = 30644;
	private static final int MARTIEN = 30645;
	private static final int RALDO = 30646;
	private static final int CHEST_OF_SHYSLASSYS = 30647;
	private static final int FILAUR = 30535;

	//Monster Id's
	private static final int SHYSLASSYS = 27110;
	private static final int CAVE_BASILISK = 27111;
	private static final int GORR = 27112;
	private static final int BARAHAM = 27113;
	private static final int SUCCUBUS_QUEEN = 27114;

	//Item Id's
	private static final int DIMENSIONAL_DIAMOND = 7562;
	private static final int LETTER_OF_KASH_ID = 2628;
	private static final int SCROLL_OF_SHYSLASSY_ID = 2631;
	private static final int WATCHERS_EYE1_ID = 2629;
	private static final int BROKEN_KEY_ID = 2632;
	private static final int MITHRIL_SCALE_GAITERS_MATERIAL_ID = 2918;
	private static final int BRIGANDINE_GAUNTLET_PATTERN_ID = 2927;
	private static final int MANTICOR_SKIN_GAITERS_PATTERN_ID = 1943;
	private static final int GAUNTLET_OF_REPOSE_OF_THE_SOUL_PATTERN_ID = 1946;
	private static final int IRON_BOOTS_DESIGN_ID = 1940;
	private static final int TOME_OF_BLOOD_PAGE_ID = 2030;
	private static final int ELVEN_NECKLACE_BEADS_ID = 1904;
	private static final int WHITE_TUNIC_PATTERN_ID = 1936;
	private static final int ADENA_ID = 57;
	private static final int MARK_OF_CHALLENGER_ID = 2627;
	private static final int WATCHERS_EYE2_ID = 2630;

	//Rewards
	private static final int RewardExp = 1067606;
	private static final int RewardSP = 69242;
	private static final int RewardAdena = 194556;

	public _211_TrialOfChallenger()
	{
		super(211, "_211_TrialOfChallenger", "Trial Of Challenger");

		addStartNpc(KASH);
		addTalkId(KASH, MARTIEN, RALDO, CHEST_OF_SHYSLASSYS, FILAUR);
		addKillId(SHYSLASSYS, CAVE_BASILISK, GORR, BARAHAM, SUCCUBUS_QUEEN);
		addQuestItem(SCROLL_OF_SHYSLASSY_ID, LETTER_OF_KASH_ID, WATCHERS_EYE1_ID, BROKEN_KEY_ID, WATCHERS_EYE2_ID);
	}

	private static boolean isAvailableClass(ClassId val)
	{
		return val.ordinal() == 0x01 || val.ordinal() == 0x13 || val.ordinal() == 0x20 || val.ordinal() == 0x2d || val.ordinal() == 0x2f;
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		int npcId = player.getLastNpc().getNpcId();
		switch(npcId)
		{
			case KASH:
				ClassId classId = player.getClassId();
				if(reply == 211 && st.isCreated() && isAvailableClass(classId) && player.getLevel() >= 35)
				{
					st.setMemoState(1);
					st.playSound(SOUND_ACCEPT);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
					if(!player.getVarB("dd1"))
					{
						int ddCount = 0;
						switch(classId.getId())
						{
							case 1:
							case 19:
							case 32:
							case 45:
							case 47:
								ddCount = 61;
								break;
							case 4:
								ddCount = 45;
								break;
							case 7:
							case 22:
							case 35:
								ddCount = 128;
								break;
							case 11:
							case 26:
							case 39:
								ddCount = 168;
								break;
							case 15:
							case 29:
							case 42:
							case 50:
								ddCount = 49;
								break;
							case 54:
							case 56:
								ddCount = 85;
								break;
						}
						st.giveItems(DIMENSIONAL_DIAMOND, ddCount);
						player.setVar("dd1", "1");
						showQuestPage("kash_q0211_05a.htm", player);
					}
					else 
						showQuestPage("kash_q0211_05.htm", player);
				}
				else if(reply == 1)
					showQuestPage("kash_q0211_04.htm", player);
				return;
			case MARTIEN:
				if(reply == 1 && st.getQuestItemsCount(LETTER_OF_KASH_ID) > 0)
				{
					st.takeItems(LETTER_OF_KASH_ID, 1);
					st.setMemoState(4);
					st.setCond(4);
					st.playSound(SOUND_MIDDLE);
					showPage("martian_q0211_02.htm", player);
				}
				return;
			case RALDO:
				if(reply == 1)
					showPage("raldo_q0211_02.htm", player);
				else if(reply == 2)
					showPage("raldo_q0211_03.htm", player);
				else if(reply == 3 && st.getQuestItemsCount(WATCHERS_EYE2_ID) > 0)
				{
					st.setMemoState(7);
					st.setCond(8);
					st.playSound(SOUND_MIDDLE);
					st.takeItems(WATCHERS_EYE2_ID, 1);
					showPage("raldo_q0211_04.htm", player);
				}
				else if(reply == 4 && st.getQuestItemsCount(WATCHERS_EYE2_ID) > 0)
				{
					st.setMemoState(7);
					st.setCond(8);
					st.playSound(SOUND_MIDDLE);
					st.takeItems(WATCHERS_EYE2_ID, 1);
					showPage("raldo_q0211_06.htm", player);
				}
				return;
			case CHEST_OF_SHYSLASSYS:
				if(reply == 1)
				{
					if(st.getQuestItemsCount(BROKEN_KEY_ID) == 1)
					{
						if(Rnd.chance(20))
						{
							st.takeItems(BROKEN_KEY_ID, 1);
							st.playSound("ItemSound.quest_jackpot");

							int chance = Rnd.get(100);
							if(chance > 90)
							{
								st.giveItems(MITHRIL_SCALE_GAITERS_MATERIAL_ID, 1);
								st.giveItems(BRIGANDINE_GAUNTLET_PATTERN_ID, 1);
								st.giveItems(MANTICOR_SKIN_GAITERS_PATTERN_ID, 1);
								st.giveItems(GAUNTLET_OF_REPOSE_OF_THE_SOUL_PATTERN_ID, 1);
								st.giveItems(IRON_BOOTS_DESIGN_ID, 1);
							}
							else if(chance > 70)
							{
								st.giveItems(TOME_OF_BLOOD_PAGE_ID, 1);
								st.giveItems(ELVEN_NECKLACE_BEADS_ID, 1);
							}
							else if(chance > 40)
								st.giveItems(WHITE_TUNIC_PATTERN_ID, 1);
							else 
								st.giveItems(IRON_BOOTS_DESIGN_ID, 1);

							showPage("chest_of_shyslassys_q0211_03.htm", player);
						}
						else
						{
							int count = Rnd.get(1, 1000);
							st.giveItems(ADENA_ID, count);
							st.takeItems(BROKEN_KEY_ID, 1);
							showPage("chest_of_shyslassys_q0211_02.htm", player);
						}
					}
					else 
						showPage("chest_of_shyslassys_q0211_04.htm", player);
				}
				return;
			default:
				showPage("noquest", player);
				return;
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		if(st.getQuestItemsCount(MARK_OF_CHALLENGER_ID) > 0)
		{
			st.exitCurrentQuest(true);
			return "completed";
		}

		String htmltext = "noquest";
		L2Player player = st.getPlayer();

		int npcId = npc.getNpcId();
		switch(npcId)
		{
			case KASH:
				if(st.isCreated())
				{
					if(isAvailableClass(player.getClassId()) && player.getLevel() >= 35)
						htmltext = "kash_q0211_03.htm";
					else if(isAvailableClass(player.getClassId()))
						htmltext = "kash_q0211_01.htm";
					else 
						htmltext = "npchtm:kash_q0211_02.htm";
				}
				else if(st.isStarted())
				{
					if(st.getQuestItemsCount(SCROLL_OF_SHYSLASSY_ID) == 1)
					{
						st.setCond(3);
						st.playSound(SOUND_MIDDLE);
						st.takeItems(SCROLL_OF_SHYSLASSY_ID, 1);
						st.giveItems(LETTER_OF_KASH_ID, 1);
						st.setMemoState(3);
						htmltext = "npchtm:kash_q0211_07.htm";
					}
					else if(st.getQuestItemsCount(LETTER_OF_KASH_ID) == 1)
						htmltext = "npchtm:kash_q0211_08.htm";
					else if(st.getMemoState() == 1)
						htmltext = "npchtm:kash_q0211_06.htm";
					else if(st.getMemoState() >= 7)
						htmltext = "npchtm:kash_q0211_09.htm";
				}
				break;
			case MARTIEN:
				if(st.isStarted())
				{
					if(st.getQuestItemsCount(LETTER_OF_KASH_ID) == 1)
						htmltext = "npchtm:martian_q0211_01.htm";
					else if(st.getQuestItemsCount(WATCHERS_EYE1_ID) > 0)
					{
						st.takeItems(WATCHERS_EYE1_ID, 1);
						st.setMemoState(5);
						st.setCond(6);
						st.playSound(SOUND_MIDDLE);
						htmltext = "npchtm:martian_q0211_04.htm";
					}
					else if(st.getMemoState() == 4 && st.getQuestItemsCount(WATCHERS_EYE1_ID) == 0)
						htmltext = "npchtm:martian_q0211_03.htm";
					else if(st.getMemoState() == 5)
						htmltext = "npchtm:martian_q0211_05.htm";
					else if(st.getMemoState() == 6)
						htmltext = "npchtm:martian_q0211_07.htm";
					else if(st.getMemoState() >= 7)
						htmltext = "npchtm:martian_q0211_06.htm";
				}
				break;
			case RALDO:
				if(st.isStarted())
				{
					if(st.getQuestItemsCount(WATCHERS_EYE2_ID) > 0)
						htmltext = "npchtm:raldo_q0211_01.htm";
					else if(st.getMemoState() == 7)
						htmltext = "npchtm:raldo_q0211_06a.htm";
					else if(st.getMemoState() == 9)
					{
						L2NpcInstance questNpc = L2ObjectsStorage.getByNpcId(RALDO);
						if(questNpc != null)
							questNpc.deleteMe();
						if(!st.getPlayer().getVarB("q211"))
						{
							st.addExpAndSp(RewardExp, RewardSP);
							st.rollAndGive(ADENA_ID, RewardAdena, 100);
							st.getPlayer().setVar("q211", "1");
						}
						st.giveItems(MARK_OF_CHALLENGER_ID, 1);
						st.takeItems(BROKEN_KEY_ID, -1);
						st.playSound(SOUND_FINISH);
						st.setState(COMPLETED);
						st.exitCurrentQuest(false);
						st.showSocial(3);
						htmltext = "npchtm:raldo_q0211_07.htm";
					}
				}
				break;
			case CHEST_OF_SHYSLASSYS:
				if(st.isStarted())
					htmltext = "npchtm:chest_of_shyslassys_q0211_01.htm";
				break;
			case FILAUR:
				if(st.isStarted())
				{
					if(st.getMemoState() == 7)
					{
						if(player.getLevel() >= 0)
						{
							st.setMemoState(8);
							st.setCond(9);
							st.playSound(SOUND_MIDDLE);
							htmltext = "npchtm:elder_filaur_q0211_01.htm";
						}
						else 
							htmltext = "npchtm:elder_filaur_q0211_03.htm";
					}
					else if(st.getMemoState() == 8)
					{
						st.showRadar(151589, -174823, -1776, 2);
						htmltext = "npchtm:elder_filaur_q0211_02.htm";
					}
					else if(st.getMemoState() == 9)
						htmltext = "npchtm:elder_filaur_q0211_04.htm";
				}
				break;
		}

		return htmltext;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("DeleteRaldo"))
		{
			L2NpcInstance questNpc = L2ObjectsStorage.getByNpcId(RALDO);
			if(questNpc != null)
				questNpc.deleteMe();
			return null;
		}
		return "npchtm:" + event;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		switch(npcId)
		{
			case BARAHAM:
				if(st.getMemoState()  == 5 && st.getQuestItemsCount(WATCHERS_EYE2_ID) == 0)
				{
					st.giveItems(WATCHERS_EYE2_ID, 1);
					st.playSound(SOUND_MIDDLE);
					st.setMemoState(6);
					st.setCond(7);
				}
				if(L2ObjectsStorage.getByNpcId(RALDO) == null)
				{
					st.getPcSpawn().addSpawn(RALDO);
					st.startQuestTimer("DeleteRaldo", 300000);
				}
				break;
			case SHYSLASSYS:
				if(st.getMemoState() == 1 && st.getQuestItemsCount(SCROLL_OF_SHYSLASSY_ID) == 0 && st.getQuestItemsCount(BROKEN_KEY_ID) == 0)
				{
					st.giveItems(SCROLL_OF_SHYSLASSY_ID, 1);
					st.giveItems(BROKEN_KEY_ID, 1);
					st.getPcSpawn().addSpawn(CHEST_OF_SHYSLASSYS);
					st.playSound(SOUND_MIDDLE);
					st.setMemoState(2);
					st.setCond(2);
				}
				break;
			case GORR:
				if(st.getMemoState() == 4 && st.getQuestItemsCount(WATCHERS_EYE1_ID) == 0)
				{
					st.giveItems(WATCHERS_EYE1_ID, 1);
					st.playSound(SOUND_MIDDLE);
					st.setMemoState(4);
					st.setCond(5);
				}
				break;
			case SUCCUBUS_QUEEN:
				if(st.getMemoState() == 8)
				{
					st.setMemoState(9);
					st.playSound(SOUND_MIDDLE);
					st.setCond(10);
				}
				if(L2ObjectsStorage.getByNpcId(RALDO) == null)
				{
					st.getPcSpawn().addSpawn(RALDO);
					st.startQuestTimer("DeleteRaldo", 300000);
				}
				break;
		}
	}
}