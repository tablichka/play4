package quests._416_PathToOrcShaman;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.quest.QuestTimer;
import ru.l2gw.commons.math.Rnd;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;

/**
 * <hr><em>Квест</em> <strong>Path To Orc Shaman</strong><hr>
 *
 * @author Sergey Ibryaev aka Artful
 * @version CT2
 * @lastfix HellSinger
 */
public class _416_PathToOrcShaman extends Quest
{
	//NPC
	private static final int Hestui = 30585;
	private static final int HestuiTotemSpirit = 30592;
	private static final int SeerUmos = 30502;
	private static final int DudaMaraTotemSpirit = 30593;
	private static final int SeerMoira = 31979;
	private static final int TotemSpiritOfGandi = 32057;
	private static final int DeadLeopardsCarcass = 32090;

	//Quest Items
	private static final int FireCharm = 1616;
	private static final int KashaBearPelt = 1617;
	private static final int KashaBladeSpiderHusk = 1618;
	private static final int FieryEgg1st = 1619;
	private static final int HestuiMask = 1620;
	private static final int FieryEgg2nd = 1621;
	private static final int TotemSpiritClaw = 1622;
	private static final int TatarusLetterOfRecommendation = 1623;
	private static final int FlameCharm = 1624;
	private static final int GrizzlyBlood = 1625;
	private static final int BloodCauldron = 1626;
	private static final int SpiritNet = 1627;
	private static final int BoundDurkaSpirit = 1628;
	private static final int DurkaParasite = 1629;
	private static final int TotemSpiritBlood = 1630;
	//Items
	private static final int MaskOfMedium = 1631;
	//MOB
	private static final int KashaBear = 20479;
	private static final int KashaBladeSpider = 20478;
	private static final int ScarletSalamander = 20415;
	private static final int GrizzlyBear = 20335;
	private static final int VenomousSpider = 20038;
	private static final int ArachnidTracker = 20043;
	private static final int QuestMonsterDurkaSpirit = 27056;
	private static final int QuestMonsterBlackLeopard = 27319;

	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{1, 0, KashaBear, FireCharm, KashaBearPelt, 1, 70, 1},
			{1, 0, KashaBladeSpider, FireCharm, KashaBladeSpiderHusk, 1, 70, 1},
			{1, 0, ScarletSalamander, FireCharm, FieryEgg1st, 1, 70, 1},
			{6, 7, GrizzlyBear, FlameCharm, GrizzlyBlood, 3, 70, 1}};

	private static boolean QuestProf = true;

	public _416_PathToOrcShaman()
	{
		super(416, "_416_PathToOrcShaman", "Path To Orc Shaman");
		addStartNpc(Hestui);
		addTalkId(HestuiTotemSpirit, SeerUmos, DudaMaraTotemSpirit, SeerMoira, TotemSpiritOfGandi, DeadLeopardsCarcass);
		//Mob Drop
		for(int[] e : DROPLIST_COND)
		{
			addKillId(e[2]);
			addQuestItem(e[4]);
		}
		addKillId(new int[]{VenomousSpider, ArachnidTracker, QuestMonsterBlackLeopard});
		addAttackId(QuestMonsterDurkaSpirit);
		addQuestItem(FireCharm,
				HestuiMask,
				FieryEgg2nd,
				TotemSpiritClaw,
				TatarusLetterOfRecommendation,
				FlameCharm,
				BloodCauldron,
				SpiritNet,
				BoundDurkaSpirit,
				DurkaParasite,
				TotemSpiritBlood);
	}

	private void reward(QuestState st)
	{
		st.takeItems(TotemSpiritBlood, -1);
		if(st.getPlayer().getClassId().getLevel() == 1)
		{
			st.giveItems(MaskOfMedium, 1);
			if(!st.getPlayer().getVarB("prof1"))
			{
				st.getPlayer().setVar("prof1", "1");
				if(st.getPlayer().getLevel() >= 20)
					st.addExpAndSp(160267, 11496);
				else if(st.getPlayer().getLevel() == 19)
					st.addExpAndSp(228064, 14845);
				else
					st.addExpAndSp(295862, 18194);
				st.rollAndGive(57, 81900, 100);
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
		if(event.equalsIgnoreCase("30585-06.htm"))
		{
			st.giveItems(FireCharm, 1);
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30592-03.htm"))
		{
			st.takeItems(HestuiMask, -1);
			st.takeItems(FieryEgg2nd, -1);
			st.giveItems(TotemSpiritClaw, 1);
			st.set("cond", "4");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30585-11.htm"))
		{
			st.takeItems(TotemSpiritClaw, -1);
			st.giveItems(TatarusLetterOfRecommendation, 1);
			st.set("cond", "5");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30585-11a.htm"))
		{
			st.takeItems(TotemSpiritClaw, -1);
			st.set("cond", "12");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30593-03.htm"))
		{
			st.takeItems(BloodCauldron, -1);
			st.giveItems(SpiritNet, 1);
			st.set("cond", "9");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30502-07.htm"))
			reward(st);
		else if(event.equalsIgnoreCase("32057-02.htm"))
		{
			st.set("cond", "14");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("32090-04.htm"))
		{
			st.set("cond", "18");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("32057-05.htm"))
		{
			st.set("cond", "21");
			st.setState(STARTED);
		}
		if(event.equalsIgnoreCase("QuestMonsterDurkaSpirit_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(QuestMonsterDurkaSpirit);
			if(isQuest != null)
				isQuest.deleteMe();
			return null;
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(st.isCreated() && npcId == Hestui)
		{
			if(st.getQuestItemsCount(MaskOfMedium) > 0)
			{
				htmltext = "30585-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getClassId().getId() != 0x31)
			{
				if(st.getPlayer().getClassId().getId() == 0x32)
					htmltext = "30585-02a.htm";
				else
					htmltext = "30585-02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30585-03.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30585-01.htm";
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case Hestui:
				{
					if(cond == 1)
						htmltext = "30585-07.htm";
					else if(cond == 2)
					{
						htmltext = "30585-08.htm";
						st.takeItems(KashaBearPelt, -1);
						st.takeItems(KashaBladeSpiderHusk, -1);
						st.takeItems(FieryEgg1st, -1);
						st.takeItems(FireCharm, -1);
						st.giveItems(HestuiMask, 1);
						st.giveItems(FieryEgg2nd, 1);
						st.set("cond", "3");
						st.setState(STARTED);
					}
					else if(cond == 3)
						htmltext = "30585-09.htm";
					else if(cond == 4)
						htmltext = "30585-10.htm";
					else if(cond == 5)
						htmltext = "30585-12.htm";
					else if(cond == 12)
						htmltext = "30585-12a.htm";
					else if(cond > 5 && cond < 12 || cond > 13)
						htmltext = "30585-13.htm";
					break;
				}
				case HestuiTotemSpirit:
				{
					if(cond == 3)
						htmltext = "30592-01.htm";
					else if(cond == 4)
						htmltext = "30592-04.htm";
					else if(st.getQuestItemsCount(GrizzlyBlood) > 0 || st.getQuestItemsCount(FlameCharm) > 0 || st.getQuestItemsCount(BloodCauldron) > 0 || st.getQuestItemsCount(SpiritNet) > 0 || st.getQuestItemsCount(BoundDurkaSpirit) > 0 || st.getQuestItemsCount(TotemSpiritBlood) > 0 || st.getQuestItemsCount(TatarusLetterOfRecommendation) > 0)
						htmltext = "30592-05.htm";
					break;
				}
				case SeerUmos:
				{
					if(cond == 5)
					{
						st.takeItems(TatarusLetterOfRecommendation, -1);
						st.giveItems(FlameCharm, 1);
						htmltext = "30502-01.htm";
						st.set("cond", "6");
						st.setState(STARTED);
					}
					else if(cond == 6)
						htmltext = "30502-02.htm";
					else if(cond == 7)
					{
						st.takeItems(GrizzlyBlood, -1);
						st.takeItems(FlameCharm, -1);
						st.giveItems(BloodCauldron, 1);
						htmltext = "30502-03.htm";
						st.set("cond", "8");
						st.setState(STARTED);
					}
					else if(cond == 8)
						htmltext = "30502-04.htm";
					else if(cond == 9 || cond == 10)
						htmltext = "30502-05.htm";
					else if(cond == 11)
						htmltext = "30502-06.htm";
					break;
				}
				case DudaMaraTotemSpirit:
				{
					if(cond == 8)
						htmltext = "30593-01.htm";
					else if(cond == 9)
						htmltext = "30593-04.htm";
					else if(cond == 10)
					{
						st.takeItems(BoundDurkaSpirit, -1);
						st.giveItems(TotemSpiritBlood, 1);
						htmltext = "30593-05.htm";
						st.set("cond", "11");
						st.setState(STARTED);
					}
					else if(cond == 11)
						htmltext = "30593-06.htm";
					break;
				}
				case SeerMoira:
				{
					if(cond == 12)
					{
						htmltext = "31979-01.htm";
						st.set("cond", "13");
						st.setState(STARTED);
					}
					else if(cond == 13)
						htmltext = "31979-02.htm";
					else if(cond == 21)
					{
						htmltext = "31979-03.htm";
						reward(st);
					}
					break;
				}
				case TotemSpiritOfGandi:
				{
					if(cond == 13)
					{
						htmltext = "32057-01.htm";
						st.set("cond", "14");
						st.setState(STARTED);
					}
					else if(cond == 14)
						htmltext = "32057-03.htm";
					else if(cond == 20)
						htmltext = "32057-04.htm";
					else if(cond == 21)
						htmltext = "32057-05.htm";
					break;
				}
				case DeadLeopardsCarcass:
				{
					if(cond == 15)
					{
						htmltext = "32090-01.htm";
						st.set("cond", "16");
						st.setState(STARTED);
					}
					else if(cond == 16)
						htmltext = "32090-01.htm";
					else if(cond == 17)
						htmltext = "32090-02.htm";
					else if(cond == 18)
						htmltext = "32090-05.htm";
					else if(cond == 19)
					{
						htmltext = "32090-06.htm";
						st.set("cond", "20");
						st.setState(STARTED);
					}
					else if(cond == 20)
						htmltext = "32090-06.htm";
					break;
				}
			}
		}
		return htmltext;
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		if(npc.getNpcId() == QuestMonsterDurkaSpirit)
		{
			QuestTimer timer = st.getQuestTimer("QuestMonsterDurkaSpirit_Fail");
			if(timer != null)
				timer.cancel();
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(QuestMonsterDurkaSpirit);
			if(isQuest != null)
			{
				isQuest.decayMe();
				isQuest.deleteMe();
			}
			if(st.getInt("cond") == 9)
			{
				st.takeItems(SpiritNet, -1);
				st.takeItems(DurkaParasite, -1);
				st.giveItems(BoundDurkaSpirit, 1);
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "10");
				st.setState(STARTED);
			}
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		for(int[] aDROPLIST_COND : DROPLIST_COND)
			if(cond == aDROPLIST_COND[0] && npcId == aDROPLIST_COND[2])
				if(aDROPLIST_COND[3] == 0 || st.getQuestItemsCount(aDROPLIST_COND[3]) > 0)
				{
					if(aDROPLIST_COND[5] == 0)
						st.rollAndGive(aDROPLIST_COND[4], aDROPLIST_COND[7], aDROPLIST_COND[6]);
					else if(st.rollAndGiveLimited(aDROPLIST_COND[4], aDROPLIST_COND[7], aDROPLIST_COND[6], aDROPLIST_COND[5]))
					{
						if(st.getQuestItemsCount(aDROPLIST_COND[4]) == aDROPLIST_COND[5] && aDROPLIST_COND[1] != cond && aDROPLIST_COND[1] != 0)
						{
							st.playSound(SOUND_MIDDLE);
							st.setCond(aDROPLIST_COND[1]);
							st.setState(STARTED);
						}
						else
							st.playSound(SOUND_ITEMGET);
					}
				}
		if(cond == 1 && st.getQuestItemsCount(KashaBearPelt) > 0 && st.getQuestItemsCount(KashaBladeSpiderHusk) > 0 && st.getQuestItemsCount(FieryEgg1st) > 0)
		{
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(cond == 9 && (npcId == VenomousSpider || npcId == ArachnidTracker))
		{
			if(st.getQuestItemsCount(DurkaParasite) < 8)
			{
				st.giveItems(DurkaParasite, 1);
				st.playSound(SOUND_ITEMGET);
			}
			if(st.getQuestItemsCount(DurkaParasite) == 8 || st.getQuestItemsCount(DurkaParasite) >= 5 && Rnd.chance(st.getQuestItemsCount(DurkaParasite) * 10))
			{
				L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(QuestMonsterDurkaSpirit);
				if(isQuest == null)
				{
					st.takeItems(DurkaParasite, -1);
					st.getPcSpawn().addSpawn(QuestMonsterDurkaSpirit);
					for(L2Spawn spawn : st.getPcSpawn().getSpawns())
					{
						L2NpcInstance durka = spawn.getLastSpawn();
						durka.addDamageHate(st.getPlayer(), 0, 999);
						durka.getAI().setIntention(AI_INTENTION_ATTACK, st.getPlayer());
					}
					st.startQuestTimer("QuestMonsterDurkaSpirit_Fail", 300000);
				}
			}
		}
		else if(npcId == QuestMonsterBlackLeopard)
		{
			if(cond == 14)
			{
				Functions.npcSay(npc, Say2C.ALL, "My dear friend of " + st.getPlayer().getName() + ", who has gone on ahead of me!");
				st.set("cond", "15");
				st.setState(STARTED);
			}
			else if(cond == 16)
			{
				Functions.npcSay(npc, Say2C.ALL, "Listen to Tejakar Gandi, young Oroka! The spirit of the slain leopard is calling you, " + st.getPlayer().getName() + "!");
				st.set("cond", "17");
				st.setState(STARTED);
			}
			else if(cond == 18)
			{
				st.set("cond", "19");
				st.setState(STARTED);
			}
		}
	}
}