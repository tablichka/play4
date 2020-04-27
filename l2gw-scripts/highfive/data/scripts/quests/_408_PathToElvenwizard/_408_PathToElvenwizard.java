package quests._408_PathToElvenwizard;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _408_PathToElvenwizard extends Quest
{
	//npc
	public final int GREENIS = 30157;
	public final int THALIA = 30371;
	public final int ROSELLA = 30414;
	public final int NORTHWIND = 30423;
	//mobs
	public final int DRYAD_ELDER = 20019;
	public final int PINCER_SPIDER = 20466;
	public final int SUKAR_WERERAT_LEADER = 20047;
	//items
	public final int ROGELLIAS_LETTER_ID = 1218;
	public final int RED_DOWN_ID = 1219;
	public final int MAGICAL_POWERS_RUBY_ID = 1220;
	public final int PURE_AQUAMARINE_ID = 1221;
	public final int APPETIZING_APPLE_ID = 1222;
	public final int GOLD_LEAVES_ID = 1223;
	public final int IMMORTAL_LOVE_ID = 1224;
	public final int AMETHYST_ID = 1225;
	public final int NOBILITY_AMETHYST_ID = 1226;
	public final int FERTILITY_PERIDOT_ID = 1229;
	public final int ETERNITY_DIAMOND_ID = 1230;
	public final int CHARM_OF_GRAIN_ID = 1272;
	public final int SAP_OF_WORLD_TREE_ID = 1273;
	public final int LUCKY_POTPOURI_ID = 1274;

	public _408_PathToElvenwizard()
	{
		super(408, "_408_PathToElvenwizard", "Path to Elven Wizard");

		addStartNpc(ROSELLA);

		addTalkId(GREENIS);
		addTalkId(THALIA);
		addTalkId(ROSELLA);
		addTalkId(NORTHWIND);

		addKillId(DRYAD_ELDER);
		addKillId(PINCER_SPIDER);
		addKillId(SUKAR_WERERAT_LEADER);

		addQuestItem(ROGELLIAS_LETTER_ID,
				FERTILITY_PERIDOT_ID,
				IMMORTAL_LOVE_ID,
				APPETIZING_APPLE_ID,
				CHARM_OF_GRAIN_ID,
				MAGICAL_POWERS_RUBY_ID,
				SAP_OF_WORLD_TREE_ID,
				PURE_AQUAMARINE_ID,
				LUCKY_POTPOURI_ID,
				NOBILITY_AMETHYST_ID,
				GOLD_LEAVES_ID,
				RED_DOWN_ID,
				AMETHYST_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("1"))
		{
			if(st.getPlayer().getClassId().getId() != 0x19)
			{
				if(st.getPlayer().getClassId().getId() == 0x1a)
					htmltext = "30414-02a.htm";
				else
					htmltext = "30414-03.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30414-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(ETERNITY_DIAMOND_ID) > 0)
			{
				htmltext = "30414-05.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				st.setState(STARTED);
				st.set("cond", "1");
				st.playSound(SOUND_ACCEPT);
				st.giveItems(FERTILITY_PERIDOT_ID, 1);
				htmltext = "30414-06.htm";
			}
		}
		else if(event.equalsIgnoreCase("408_1"))
		{
			if(st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID) > 0)
				htmltext = "30414-10.htm";
			else if(st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID) < 1 && st.getQuestItemsCount(FERTILITY_PERIDOT_ID) > 0)
			{
				st.giveItems(ROGELLIAS_LETTER_ID, 1);
				htmltext = "30414-07.htm";
			}
		}
		else if(event.equalsIgnoreCase("408_4"))
		{
			if(st.getQuestItemsCount(ROGELLIAS_LETTER_ID) > 0)
			{
				st.takeItems(ROGELLIAS_LETTER_ID, -1);
				st.giveItems(CHARM_OF_GRAIN_ID, 1);
				htmltext = "30157-02.htm";
			}
		}
		else if(event.equalsIgnoreCase("408_2"))
		{
			if(st.getQuestItemsCount(PURE_AQUAMARINE_ID) > 0)
				htmltext = "30414-13.htm";
			else if(st.getQuestItemsCount(PURE_AQUAMARINE_ID) < 1 && st.getQuestItemsCount(FERTILITY_PERIDOT_ID) > 0)
			{
				st.giveItems(APPETIZING_APPLE_ID, 1);
				htmltext = "30414-14.htm";
			}
		}
		else if(event.equalsIgnoreCase("408_5"))
		{
			if(st.getQuestItemsCount(APPETIZING_APPLE_ID) > 0)
			{
				st.takeItems(APPETIZING_APPLE_ID, -1);
				st.giveItems(SAP_OF_WORLD_TREE_ID, 1);
				htmltext = "30371-02.htm";
			}
		}
		else if(event.equalsIgnoreCase("408_3"))
			if(st.getQuestItemsCount(NOBILITY_AMETHYST_ID) > 0)
				htmltext = "30414-17.htm";
			else if(st.getQuestItemsCount(NOBILITY_AMETHYST_ID) < 1 && st.getQuestItemsCount(FERTILITY_PERIDOT_ID) > 0)
			{
				st.giveItems(IMMORTAL_LOVE_ID, 1);
				htmltext = "30414-18.htm";
			}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == ROSELLA)
		{
			if(cond < 1)
				htmltext = "30414-01.htm";
			else if(st.getQuestItemsCount(CHARM_OF_GRAIN_ID) > 0)
			{
				if(st.getQuestItemsCount(RED_DOWN_ID) < 5)
					htmltext = "30414-09.htm";
				else if(st.getQuestItemsCount(RED_DOWN_ID) > 4)
					htmltext = "30414-25.htm";
				else if(st.getQuestItemsCount(GOLD_LEAVES_ID) > 4)
					htmltext = "30414-26.htm";
			}
			else if(st.getQuestItemsCount(APPETIZING_APPLE_ID) > 0)
				htmltext = "30414-15.htm";
			else if(st.getQuestItemsCount(IMMORTAL_LOVE_ID) > 0)
				htmltext = "30414-19.htm";
			else if(st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID) > 0 && st.getQuestItemsCount(GOLD_LEAVES_ID) < 5)
				htmltext = "30414-16.htm";
			else if(st.getQuestItemsCount(LUCKY_POTPOURI_ID) > 0)
			{
				if(st.getQuestItemsCount(AMETHYST_ID) < 2)
					htmltext = "30414-20.htm";
				else
					htmltext = "30414-27.htm";
			}
			else if(st.getQuestItemsCount(ROGELLIAS_LETTER_ID) > 0)
				htmltext = "30414-08.htm";
			else if(st.getQuestItemsCount(ROGELLIAS_LETTER_ID) < 1 && st.getQuestItemsCount(APPETIZING_APPLE_ID) < 1 && st.getQuestItemsCount(IMMORTAL_LOVE_ID) < 1 && st.getQuestItemsCount(CHARM_OF_GRAIN_ID) < 1 && st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID) < 1 && st.getQuestItemsCount(LUCKY_POTPOURI_ID) < 1 && st.getQuestItemsCount(FERTILITY_PERIDOT_ID) > 0)
				if(st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID) < 1 | st.getQuestItemsCount(NOBILITY_AMETHYST_ID) < 1 | st.getQuestItemsCount(PURE_AQUAMARINE_ID) < 1)
					htmltext = "30414-11.htm";
				else if(st.getQuestItemsCount(MAGICAL_POWERS_RUBY_ID) > 0 && st.getQuestItemsCount(NOBILITY_AMETHYST_ID) > 0 && st.getQuestItemsCount(PURE_AQUAMARINE_ID) > 0)
				{
					st.takeItems(MAGICAL_POWERS_RUBY_ID, -1);
					st.takeItems(PURE_AQUAMARINE_ID, st.getQuestItemsCount(PURE_AQUAMARINE_ID));
					st.takeItems(NOBILITY_AMETHYST_ID, st.getQuestItemsCount(NOBILITY_AMETHYST_ID));
					st.takeItems(FERTILITY_PERIDOT_ID, st.getQuestItemsCount(FERTILITY_PERIDOT_ID));
					htmltext = "30414-24.htm";
					if(st.getPlayer().getClassId().getLevel() == 1)
					{
						st.giveItems(ETERNITY_DIAMOND_ID, 1);
						if(!st.getPlayer().getVarB("prof1"))
						{
							st.getPlayer().setVar("prof1", "1");
							if(st.getPlayer().getLevel() >= 20)
								st.addExpAndSp(320534, 22532);
							else if(st.getPlayer().getLevel() == 19)
								st.addExpAndSp(456128, 29230);
							else
								st.addExpAndSp(591724, 35928);
							st.rollAndGive(57, 163800, 100);
						}
					}
					st.showSocial(3);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(true);
				}
		}
		else if(npcId == GREENIS && cond > 0)
		{
			if(st.getQuestItemsCount(ROGELLIAS_LETTER_ID) > 0)
				htmltext = "30157-01.htm";
			else if(st.getQuestItemsCount(CHARM_OF_GRAIN_ID) > 0)
				if(st.getQuestItemsCount(RED_DOWN_ID) < 5)
					htmltext = "30157-03.htm";
				else
				{
					st.takeItems(RED_DOWN_ID, -1);
					st.takeItems(CHARM_OF_GRAIN_ID, -1);
					st.giveItems(MAGICAL_POWERS_RUBY_ID, 1);
					htmltext = "30157-04.htm";
				}
		}
		else if(npcId == THALIA && cond > 0)
		{
			if(st.getQuestItemsCount(APPETIZING_APPLE_ID) > 0)
				htmltext = "30371-01.htm";
			else if(st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID) > 0)
				if(st.getQuestItemsCount(GOLD_LEAVES_ID) < 5)
					htmltext = "30371-03.htm";
				else
				{
					st.takeItems(GOLD_LEAVES_ID, -1);
					st.takeItems(SAP_OF_WORLD_TREE_ID, -1);
					st.giveItems(PURE_AQUAMARINE_ID, 1);
					htmltext = "30371-04.htm";
				}
		}
		else if(npcId == NORTHWIND && cond > 0)
			if(st.getQuestItemsCount(IMMORTAL_LOVE_ID) > 0)
			{
				st.takeItems(IMMORTAL_LOVE_ID, -1);
				st.giveItems(LUCKY_POTPOURI_ID, 1);
				htmltext = "30423-01.htm";
			}
			else if(st.getQuestItemsCount(LUCKY_POTPOURI_ID) > 0)
				if(st.getQuestItemsCount(AMETHYST_ID) < 2)
					htmltext = "30423-02.htm";
				else
				{
					st.takeItems(AMETHYST_ID, -1);
					st.takeItems(LUCKY_POTPOURI_ID, -1);
					st.giveItems(NOBILITY_AMETHYST_ID, 1);
					htmltext = "30423-03.htm";
				}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == PINCER_SPIDER)
		{
			if(cond > 0 && st.getQuestItemsCount(CHARM_OF_GRAIN_ID) > 0 && st.rollAndGiveLimited(RED_DOWN_ID, 1, 70, 5))
			{
				if(st.getQuestItemsCount(RED_DOWN_ID) < 5)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npcId == DRYAD_ELDER)
		{
			if(cond > 0 && st.getQuestItemsCount(SAP_OF_WORLD_TREE_ID) > 0 && st.rollAndGiveLimited(GOLD_LEAVES_ID, 1, 40, 5))
			{
				if(st.getQuestItemsCount(GOLD_LEAVES_ID) < 5)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npcId == SUKAR_WERERAT_LEADER)
			if(cond > 0 && st.getQuestItemsCount(LUCKY_POTPOURI_ID) > 0 && st.rollAndGiveLimited(AMETHYST_ID, 1, 40, 2))
			{
				if(st.getQuestItemsCount(AMETHYST_ID) < 2)
					st.playSound(SOUND_ITEMGET);
				else
					st.playSound(SOUND_MIDDLE);
			}
	}
}