package quests._417_PathToScavenger;

import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _417_PathToScavenger extends Quest
{
	int RING_OF_RAVEN = 1642;
	int PIPIS_LETTER = 1643;
	int ROUTS_TP_SCROLL = 1644;
	int SUCCUBUS_UNDIES = 1645;
	int MIONS_LETTER = 1646;
	int BRONKS_INGOT = 1647;
	int CHALIS_AXE = 1648;
	int ZIMENFS_POTION = 1649;
	int BRONKS_PAY = 1650;
	int CHALIS_PAY = 1651;
	int ZIMENFS_PAY = 1652;
	int BEAR_PIC = 1653;
	int TARANTULA_PIC = 1654;
	int HONEY_JAR = 1655;
	int BEAD = 1656;
	int BEAD_PARCEL = 1657;

	public _417_PathToScavenger()
	{
		super(417, "_417_PathToScavenger", "Path To Scavenger");

		addStartNpc(30524);
		addTalkId(30524, 30316, 30517, 30519, 30524, 30525, 30538, 30556, 30557);

		addKillId(20403);
		addKillId(27058);
		addKillId(20508);
		addKillId(20777);

		addQuestItem(CHALIS_PAY,
				ZIMENFS_PAY,
				BRONKS_PAY,
				PIPIS_LETTER,
				CHALIS_AXE,
				ZIMENFS_POTION,
				BRONKS_INGOT,
				MIONS_LETTER,
				HONEY_JAR,
				BEAR_PIC,
				BEAD_PARCEL,
				BEAD,
				TARANTULA_PIC,
				SUCCUBUS_UNDIES,
				ROUTS_TP_SCROLL);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		int cond = st.getInt("cond");
		if(event.equals("1"))
		{
			st.set("id", "0");

			if(st.getPlayer().getClassId().getId() != 0x35)
			{
				if(st.getPlayer().getClassId().getId() == 0x36)
					htmltext = "30524-02a.htm";
				else
					htmltext = "30524-08.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(RING_OF_RAVEN) > 0)
			{
				htmltext = "30524-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30524-02.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				st.giveItems(PIPIS_LETTER, 1);
				htmltext = "30524-05.htm";
			}
		}
		else if(event.equals("30519_1"))
		{
			if(st.getQuestItemsCount(PIPIS_LETTER) > 0)
			{
				st.takeItems(PIPIS_LETTER, 1);
				st.set("cond", "2");
				int n = Rnd.get(3);
				if(n == 0)
				{
					htmltext = "30519-02.htm";
					st.giveItems(ZIMENFS_POTION, 1);
				}
				else if(n == 1)
				{
					htmltext = "30519-03.htm";
					st.giveItems(CHALIS_AXE, 1);
				}
				else if(n == 2)
				{
					htmltext = "30519-04.htm";
					st.giveItems(BRONKS_INGOT, 1);
				}
			}
			else
				htmltext = "noquest";
		}
		else if(event.equals("30519_2"))
			htmltext = "30519-06.htm";
		else if(event.equals("30519_3"))
		{
			htmltext = "30519-07.htm";
			st.set("id", String.valueOf(st.getInt("id") + 1));
		}
		else if(event.equals("30519_4"))
		{
			int n = Rnd.get(2);
			if(n == 0)
				htmltext = "30519-06.htm";
			else if(n == 1)
				htmltext = "30519-11.htm";
		}
		else if(event.equals("30519_5"))
		{
			if(st.getQuestItemsCount(ZIMENFS_POTION) > 0 || st.getQuestItemsCount(CHALIS_AXE) > 0 || st.getQuestItemsCount(BRONKS_INGOT) > 0)
			{
				if(st.getInt("id") / 10 < 2)
				{
					htmltext = "30519-07.htm";
					st.set("id", String.valueOf(st.getInt("id") + 1));
				}
				else if(st.getInt("id") / 10 >= 2 && st.isCreated())
				{
					htmltext = "30519-09.htm";
					if(st.getInt("id") / 10 < 3)
						st.set("id", String.valueOf(st.getInt("id") + 1));
				}
				else if(st.getInt("id") / 10 >= 3 && cond > 0)
				{
					htmltext = "30519-10.htm";
					st.giveItems(MIONS_LETTER, 1);
					st.takeItems(CHALIS_AXE, 1);
					st.takeItems(ZIMENFS_POTION, 1);
					st.takeItems(BRONKS_INGOT, 1);
				}
			}
			else
				htmltext = "noquest";
		}
		else if(event.equals("30519_6"))
		{
			if(st.getQuestItemsCount(ZIMENFS_PAY) > 0 || st.getQuestItemsCount(CHALIS_PAY) > 0 || st.getQuestItemsCount(BRONKS_PAY) > 0)
			{
				int n = Rnd.get(3);
				st.takeItems(ZIMENFS_PAY, 1);
				st.takeItems(CHALIS_PAY, 1);
				st.takeItems(BRONKS_PAY, 1);
				if(n == 0)
				{
					htmltext = "30519-02.htm";
					st.giveItems(ZIMENFS_POTION, 1);
				}
				else if(n == 1)
				{
					htmltext = "30519-03.htm";
					st.giveItems(CHALIS_AXE, 1);
				}
				else if(n == 2)
				{
					htmltext = "30519-04.htm";
					st.giveItems(BRONKS_INGOT, 1);
				}
			}
			else
				htmltext = "noquest";
		}
		else if(event.equals("30316_1"))
		{
			if(st.getQuestItemsCount(BEAD_PARCEL) > 0)
			{
				htmltext = "30316-02.htm";
				st.takeItems(BEAD_PARCEL, 1);
				st.giveItems(ROUTS_TP_SCROLL, 1);
				st.set("cond", "10");
			}
			else
				htmltext = "noquest";
		}
		else if(event.equals("30316_2"))
		{
			if(st.getQuestItemsCount(BEAD_PARCEL) > 0)
			{
				htmltext = "30316-03.htm";
				st.takeItems(BEAD_PARCEL, 1);
				st.giveItems(ROUTS_TP_SCROLL, 1);
				st.set("cond", "10");
			}
			else
				htmltext = "noquest";
		}
		else if(event.equals("30557_1"))
			htmltext = "30557-02.htm";
		else if(event.equals("30557_2"))
			if(st.getQuestItemsCount(ROUTS_TP_SCROLL) > 0)
			{
				htmltext = "30557-03.htm";
				st.takeItems(ROUTS_TP_SCROLL, 1);
				st.giveItems(SUCCUBUS_UNDIES, 1);
				st.set("cond", "11");
				st.getPcSpawn().removeAllSpawn();
			}
			else
				htmltext = "noquest";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		boolean cond = st.getInt("cond") > 0;
		if(st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "0");
			st.set("id", "0");
		}
		if(npcId == 30524 && !cond)
			htmltext = "30524-01.htm";
		else if(npcId == 30524 && cond && st.getQuestItemsCount(PIPIS_LETTER) > 0)
			htmltext = "30524-06.htm";
		else if(npcId == 30524 && cond && st.getQuestItemsCount(PIPIS_LETTER) == 0 && st.isStarted())
			htmltext = "30524-01.htm";
		else if(npcId == 30524 && cond && st.getQuestItemsCount(PIPIS_LETTER) == 0)
			htmltext = "30524-07.htm";
		else if(npcId == 30519 && cond && st.getQuestItemsCount(PIPIS_LETTER) > 0)
			htmltext = "30519-01.htm";
		else if(npcId == 30519 && cond && st.getQuestItemsCount(CHALIS_AXE) + st.getQuestItemsCount(BRONKS_INGOT) + st.getQuestItemsCount(ZIMENFS_POTION) == 1 && st.getInt("id") / 10 == 0)
			htmltext = "30519-05.htm";
		else if(npcId == 30519 && cond && st.getQuestItemsCount(CHALIS_AXE) + st.getQuestItemsCount(BRONKS_INGOT) + st.getQuestItemsCount(ZIMENFS_POTION) == 1 && st.getInt("id") / 10 > 0)
			htmltext = "30519-08.htm";
		else if(npcId == 30519 && cond && st.getQuestItemsCount(CHALIS_PAY) + st.getQuestItemsCount(BRONKS_PAY) + st.getQuestItemsCount(ZIMENFS_PAY) == 1 && st.getInt("id") < 50)
			htmltext = "30519-12.htm";
		else if(npcId == 30519 && cond && st.getQuestItemsCount(CHALIS_PAY) + st.getQuestItemsCount(BRONKS_PAY) + st.getQuestItemsCount(ZIMENFS_PAY) == 1 && st.getInt("id") >= 50)
		{
			htmltext = "30519-15.htm";
			st.giveItems(MIONS_LETTER, 1);
			st.takeItems(CHALIS_PAY, 1);
			st.takeItems(ZIMENFS_PAY, 1);
			st.takeItems(BRONKS_PAY, 1);
			st.set("cond", "4");
		}
		else if(npcId == 30519 && cond && st.getQuestItemsCount(MIONS_LETTER) > 0)
			htmltext = "30519-13.htm";
		else if(npcId == 30519 && cond && (st.getQuestItemsCount(BEAR_PIC) > 0 || st.getQuestItemsCount(TARANTULA_PIC) > 0 || st.getQuestItemsCount(BEAD_PARCEL) > 0 || st.getQuestItemsCount(ROUTS_TP_SCROLL) > 0 || st.getQuestItemsCount(SUCCUBUS_UNDIES) > 0))
			htmltext = "30519-14.htm";
		else if(npcId == 30517 && cond && st.getQuestItemsCount(CHALIS_AXE) == 1 && st.getInt("id") < 20)
		{
			htmltext = "30517-01.htm";
			st.takeItems(CHALIS_AXE, 1);
			st.giveItems(CHALIS_PAY, 1);
			if(st.getInt("id") >= 50)
				st.set("cond", "3");
			st.set("id", String.valueOf(st.getInt("id") + 10));
		}
		else if(npcId == 30517 && cond && st.getQuestItemsCount(CHALIS_AXE) == 1 && st.getInt("id") >= 20)
		{
			htmltext = "30517-02.htm";
			st.takeItems(CHALIS_AXE, 1);
			st.giveItems(CHALIS_PAY, 1);
			if(st.getInt("id") >= 50)
				st.set("cond", "3");
			st.set("id", String.valueOf(st.getInt("id") + 10));
		}
		else if(npcId == 30517 && cond && st.getQuestItemsCount(CHALIS_PAY) == 1)
			htmltext = "30517-03.htm";
		else if(npcId == 30525 && cond && st.getQuestItemsCount(BRONKS_INGOT) == 1 && st.getInt("id") < 20)
		{
			htmltext = "30525-01.htm";
			st.takeItems(BRONKS_INGOT, 1);
			st.giveItems(BRONKS_PAY, 1);
			if(st.getInt("id") >= 50)
				st.set("cond", "3");
			st.set("id", String.valueOf(st.getInt("id") + 10));
		}
		else if(npcId == 30525 && cond && st.getQuestItemsCount(BRONKS_INGOT) == 1 && st.getInt("id") >= 20)
		{
			htmltext = "30525-02.htm";
			st.takeItems(BRONKS_INGOT, 1);
			st.giveItems(BRONKS_PAY, 1);
			if(st.getInt("id") >= 50)
				st.set("cond", "3");
			st.set("id", String.valueOf(st.getInt("id") + 10));
		}
		else if(npcId == 30525 && cond && st.getQuestItemsCount(BRONKS_PAY) == 1)
			htmltext = "30525-03.htm";
		else if(npcId == 30538 && cond && st.getQuestItemsCount(ZIMENFS_POTION) == 1 && st.getInt("id") < 20)
		{
			htmltext = "30538-01.htm";
			st.takeItems(ZIMENFS_POTION, 1);
			st.giveItems(ZIMENFS_PAY, 1);
			if(st.getInt("id") >= 50)
				st.set("cond", "3");
			st.set("id", String.valueOf(st.getInt("id") + 10));
		}
		else if(npcId == 30538 && cond && st.getQuestItemsCount(ZIMENFS_POTION) == 1 && st.getInt("id") >= 20)
		{
			htmltext = "30538-02.htm";
			st.takeItems(ZIMENFS_POTION, 1);
			st.giveItems(ZIMENFS_PAY, 1);
			if(st.getInt("id") >= 50)
				st.set("cond", "3");
			st.set("id", String.valueOf(st.getInt("id") + 10));
		}
		else if(npcId == 30538 && cond && st.getQuestItemsCount(ZIMENFS_PAY) == 1)
			htmltext = "30538-03.htm";
		else if(npcId == 30556 && cond && st.getQuestItemsCount(MIONS_LETTER) == 1)
		{
			htmltext = "30556-01.htm";
			st.takeItems(MIONS_LETTER, 1);
			st.giveItems(BEAR_PIC, 1);
			st.set("cond", "5");
			st.set("id", String.valueOf(0));
		}
		else if(npcId == 30556 && cond && st.getQuestItemsCount(BEAR_PIC) == 1 && st.getQuestItemsCount(HONEY_JAR) < 5)
			htmltext = "30556-02.htm";
		else if(npcId == 30556 && cond && st.getQuestItemsCount(BEAR_PIC) == 1 && st.getQuestItemsCount(HONEY_JAR) >= 5)
		{
			htmltext = "30556-03.htm";
			st.takeItems(HONEY_JAR, st.getQuestItemsCount(HONEY_JAR));
			st.takeItems(BEAR_PIC, 1);
			st.giveItems(TARANTULA_PIC, 1);
			st.set("cond", "7");
		}
		else if(npcId == 30556 && cond && st.getQuestItemsCount(TARANTULA_PIC) == 1 && st.getQuestItemsCount(BEAD) < 20)
			htmltext = "30556-04.htm";
		else if(npcId == 30556 && cond && st.getQuestItemsCount(TARANTULA_PIC) == 1 && st.getQuestItemsCount(BEAD) >= 20)
		{
			htmltext = "30556-05.htm";
			st.takeItems(BEAD, st.getQuestItemsCount(BEAD));
			st.takeItems(TARANTULA_PIC, 1);
			st.giveItems(BEAD_PARCEL, 1);
			st.set("cond", "9");
		}
		else if(npcId == 30556 && cond && st.getQuestItemsCount(BEAD_PARCEL) > 0)
			htmltext = "30556-06.htm";
		else if(npcId == 30556 && cond && (st.getQuestItemsCount(ROUTS_TP_SCROLL) > 0 || st.getQuestItemsCount(SUCCUBUS_UNDIES) > 0))
			htmltext = "30556-07.htm";
		else if(npcId == 30316 && cond && st.getQuestItemsCount(BEAD_PARCEL) == 1)
			htmltext = "30316-01.htm";
		else if(npcId == 30316 && cond && st.getQuestItemsCount(ROUTS_TP_SCROLL) == 1)
			htmltext = "30316-04.htm";
		else if(npcId == 30316 && cond && st.getQuestItemsCount(SUCCUBUS_UNDIES) == 1)
		{
			htmltext = "30316-05.htm";
			st.takeItems(SUCCUBUS_UNDIES, 1);
			if(st.getPlayer().getClassId().getLevel() == 1)
			{
				st.giveItems(RING_OF_RAVEN, 1);
				if(!st.getPlayer().getVarB("prof1"))
				{
					st.getPlayer().setVar("prof1", "1");
					if(st.getPlayer().getLevel() >= 20)
						st.addExpAndSp(320534, 35412);
					else if(st.getPlayer().getLevel() == 19)
						st.addExpAndSp(456128, 42110);
					else
						st.addExpAndSp(591724, 48808);
					st.rollAndGive(57, 163800, 100);
				}
			}
			st.showSocial(3);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(npcId == 30557 && cond && st.getQuestItemsCount(ROUTS_TP_SCROLL) == 1)
			htmltext = "30557-01.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		L2MonsterInstance mob = (L2MonsterInstance) npc;
		boolean cond = st.getInt("cond") > 0;
		if(npcId == 20777)
		{
			if(cond && st.getQuestItemsCount(BEAR_PIC) == 1 && st.getQuestItemsCount(HONEY_JAR) < 5)
				if(st.getInt("id") > 20)
				{
					int n = (st.getInt("id") - 20) * 10;
					if(Rnd.chance(n))
					{
						st.getPcSpawn().addSpawn(27058);
						st.set("id", "0");
					}
					else
						st.set("id", String.valueOf(st.getInt("id") + 1));
				}
				else
					st.set("id", String.valueOf(st.getInt("id") + 1));
		}
		else if(npcId == 27058)
		{
			if(cond && st.getQuestItemsCount(BEAR_PIC) == 1 && st.getQuestItemsCount(HONEY_JAR) < 5)
				if(mob.isSpoiled())
				{
					st.giveItems(HONEY_JAR, 1);
					if(st.getQuestItemsCount(HONEY_JAR) == 5)
					{
						st.playSound(SOUND_MIDDLE);
						st.set("cond", "6");
						st.setState(STARTED);
					}
					else
						st.playSound(SOUND_ITEMGET);
				}
		}
		else if(npcId == 20403 || npcId == 20508)
		{
			if(cond && st.getQuestItemsCount(TARANTULA_PIC) == 1 && st.getQuestItemsCount(BEAD) < 20)
				if(mob.isSpoiled() && st.rollAndGiveLimited(BEAD, 1, 50, 20))
				{
					if(st.getQuestItemsCount(BEAD) == 20)
					{
						st.playSound(SOUND_MIDDLE);
						st.set("cond", "8");
						st.setState(STARTED);
					}
					else
						st.playSound(SOUND_ITEMGET);
				}
		}
	}
}