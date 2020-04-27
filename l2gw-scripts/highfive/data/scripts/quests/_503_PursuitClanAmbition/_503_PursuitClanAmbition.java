package quests._503_PursuitClanAmbition;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.ClanTable;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.Location;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;

public class _503_PursuitClanAmbition extends Quest
{
	// Items
	private static final short Mi_Drake_Eggs = 3839;
	private static final short Bl_Wyrm_Eggs = 3840;
	private static final short Drake_Eggs = 3841;
	private static final short Th_Wyrm_Eggs = 3842;
	private static final int[] EggList = new int[]{Mi_Drake_Eggs, Bl_Wyrm_Eggs, Drake_Eggs, Th_Wyrm_Eggs};
	private static final short Brooch = 3843;
	private static final short Spiteful_Soul_Energy = 14855;
	private static final short Spiteful_Soul_Vengeance = 14856;
	private static final short Recipe_spiteful_energy = 14854;
	private static final short Imperial_Key = 3847;
	private static final short G_Let_Martien = 3866;
	private static final short G_Let_Balthazar = 3867;
	private static final short G_Let_Rodemai = 3868;
	private static final short Scepter_Judgement = 3869;
	private static final short Proof_Aspiration = 3870;
	private static final short Bl_Anvil_Coin = 3871;
	//NPCs
	private static final int Martien = 30645;
	private static final int Athrea = 30758;
	private static final int Kalis = 30759;
	private static final int Gustaf = 30760; //initial NPC
	private static final int Fritz = 30761;
	private static final int Lutz = 30762;
	private static final int Kurtz = 30763;
	private static final int Kusto = 30512;
	private static final int Balthazar = 30764;
	private static final int Rodemai = 30868;
	private static final int Coffer = 30765;
	private static final int Cleo = 30766;
	//MOBs
	private static final int ThunderWyrm1 = 20282;
	private static final int ThunderWyrm2 = 20243;
	private static final int Drake1 = 20137;
	private static final int Drake2 = 20285;
	private static final int BlitzWyrm = 27178;
	private static final int SpitefulSoulLeader = 20974;
	private static final int GraveGuard = 20668;
	private static final int GraveKeyKeeper = 27179;
	private static final int ImperialSlave = 27180;
	private static final int ImperialGravekeeper = 27181;

	//# [COND, MOB_ID, ITEM, NEED_COUNT, CHANCE]
	private static final int[][] DROPLIST_COND = {
			{2, ThunderWyrm1, Th_Wyrm_Eggs, 10, 20},
			{2, ThunderWyrm2, Th_Wyrm_Eggs, 10, 15},
			{2, Drake1, Drake_Eggs, 10, 20},
			{2, Drake2, Drake_Eggs, 10, 25},
			{2, BlitzWyrm, Bl_Wyrm_Eggs, 10, 100},
			{5, SpitefulSoulLeader, Spiteful_Soul_Vengeance, 1000, 25},
			{5, SpitefulSoulLeader, Spiteful_Soul_Energy, 10, 25},
			{10, GraveKeyKeeper, Imperial_Key, 6, 80}};

	public _503_PursuitClanAmbition()
	{
		super(503, "_503_PursuitClanAmbition", "Pursuit of Clan Ambition"); // Party true

		addStartNpc(Gustaf);
		addTalkId(Martien, Athrea, Kalis, Fritz, Lutz, Kurtz, Kusto, Balthazar, Rodemai, Coffer, Cleo);
		addKillId(ThunderWyrm1, ThunderWyrm2, Drake1, Drake2, BlitzWyrm, SpitefulSoulLeader, GraveGuard, GraveKeyKeeper, ImperialGravekeeper);
		addAttackId(ImperialGravekeeper);

		for(int i = 3839; i <= 3848; i++)
			addQuestItem(i);

		for(int i = 3866; i <= 3869; i++)
			addQuestItem(i);

		addQuestItem(Recipe_spiteful_energy, Spiteful_Soul_Energy, Spiteful_Soul_Vengeance);
	}

	private boolean check_KM(QuestState st)
	{
		L2Player player = st.getPlayer();

		if(player != null)
		{
			L2Clan clan = ClanTable.getInstance().getClan(player.getClanId());
			if(clan != null && player.isClanLeader() && clan.getLevel() > 3)
			{
				GArray<L2Player> members = clan.getOnlineMembers(clan.getLeader().getPlayer().getName());
				if(members != null && members.size() > 0)
					return true;
				else
				{
					if(player.getVar("lang@").equalsIgnoreCase("en"))
						player.sendMessage("You can not perform the quest \"" + getDescr() + "\" alone.");
					else
						player.sendMessage("Вы не можете выполнять задание \"" + getDescr() + "\" в одиночку.");
					return false;
				}
			}
			else if(!player.isClanLeader())
			{
				if(player.getVar("lang@").equalsIgnoreCase("en"))
					player.sendMessage("bypass -h Quest \"" + getDescr() + "\" has been interrupted because you are not clan leader.");
				else
					player.sendMessage("Задание \"" + getDescr() + "\" отменено, потому что Вы не являетесь лидером клана.");
			}
			st.exitCurrentQuest(true);
		}
		return false;
	}

	private boolean checkEggs(QuestState st)
	{
		int count = 0;
		for(int item : EggList)
			if(st.getQuestItemsCount(item) > 9)
				count += 1;
		return count > 3;
	}

	private boolean haveScepter(QuestState st)
	{
		if(st.getQuestItemsCount(Scepter_Judgement) < 1)
		{
			if(st.getPlayer().getVar("lang@").equalsIgnoreCase("en"))
				st.getPlayer().sendMessage("You have lost Scepter of Judgement!");
			else
				st.getPlayer().sendMessage("Как Вы могли потерять Scepter of Judgement?!");
			st.set("cond", "10");
			st.setState(STARTED);
			return false;
		}
		return true;
	}

	private void removePcSpawn(QuestState st)
	{
		for(L2Spawn spawn : st.getPcSpawn().getSpawns())
			spawn.getLastSpawn().deleteMe();
	}

	private void attack(QuestState st)
	{
		for(L2Spawn spawn : st.getPcSpawn().getSpawns())
		{
			if(spawn != null)
			{
				L2NpcInstance mob = spawn.getLastSpawn();
				mob.addDamageHate(mob, 0, 999999);
				mob.getAI().setIntention(AI_INTENTION_ATTACK, st.getPlayer());
			}
		}
	}

	private void shoutFromTheSpawn(QuestState st, int npcId, String msg)
	{
		for(L2Spawn spawn : st.getPcSpawn().getSpawns())
		{
			if(spawn != null)
			{
				L2NpcInstance npc = spawn.getLastSpawn();
				if(npc.getNpcId() == npcId)
					Functions.npcSay(npc, Say2C.ALL, msg);
			}
		}
	}

	private void exit(boolean addSP, QuestState st)
	{
		removePcSpawn(st);
		st.takeItems(Scepter_Judgement, -1);
		st.giveItems(Proof_Aspiration, 1);
		if(addSP)
			st.addExpAndSp(0, 250000);
		st.exitCurrentQuest(true);
		if(st.getPlayer().getVar("lang@").equalsIgnoreCase("en"))
			st.getPlayer().sendMessage("Congratulations, you have finished the \"" + getDescr() + "\".");
		else
			st.getPlayer().sendMessage("Поздравляю, Вы завершили задание \"" + getDescr() + "\".");
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(!check_KM(st))
			return null;
		String htmltext = event;
		// Events Gustaf
		if(event.equalsIgnoreCase("30760-08.htm"))
		{
			st.giveItems(G_Let_Martien, 1);
			for(String var : new String[]{"cond", "scepter", "Fritz", "Lutz", "Kurtz"})
				st.set(var, "1");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30760-12.htm"))
		{
			st.giveItems(G_Let_Balthazar, 1);
			st.set("cond", "4");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30760-16.htm"))
		{
			st.giveItems(G_Let_Rodemai, 1);
			st.set("cond", "7");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30760-20.htm"))
			exit(true, st);
		else if(event.equalsIgnoreCase("30760-22.htm"))
			st.set("scepter", "3");
		else if(event.equalsIgnoreCase("30760-23.htm"))
			exit(true, st);
			// Events Martien
		else if(event.equalsIgnoreCase("30645-03.htm"))
		{
			st.takeItems(G_Let_Martien, -1);
			st.set("cond", "2");
			st.setState(STARTED);
			GArray<L2Player> members = st.getPlayer().getClan().getOnlineMembers(st.getPlayer().getName());
			for(L2Player player : members)
				new QuestState(this, player, STARTED);
		}
		// Events Kurtz
		else if(event.equalsIgnoreCase("30763-03.htm"))
		{
			if(st.getInt("Kurtz") == 1)
			{
				htmltext = "30763-02.htm";
				st.giveItems(Mi_Drake_Eggs, 6);
				st.giveItems(Brooch, 1);
				st.set("Kurtz", "2");
			}
		}
		// Events Lutz
		else if(event.equalsIgnoreCase("30762-03.htm"))
		{
			int lutz = st.getInt("Lutz");
			if(lutz == 1)
			{
				htmltext = "30762-02.htm";
				st.giveItems(Mi_Drake_Eggs, 4);
				if(st.getQuestItemsCount(Bl_Wyrm_Eggs) < 10)
					st.giveItems(Bl_Wyrm_Eggs, (10 - st.getQuestItemsCount(Bl_Wyrm_Eggs) > 3) ? 3 : 10 - st.getQuestItemsCount(Bl_Wyrm_Eggs));
				st.set("Lutz", "2");
			}
			st.getPcSpawn().addSpawn(BlitzWyrm, 120000);
			st.getPcSpawn().addSpawn(BlitzWyrm, 120000);
			attack(st);
		}
		// Events Fritz
		else if(event.equalsIgnoreCase("30761-03.htm"))
		{
			int fritz = st.getInt("Fritz");
			if(fritz == 1)
			{
				htmltext = "30761-02.htm";
				if(st.getQuestItemsCount(Bl_Wyrm_Eggs) < 10)
					st.giveItems(Bl_Wyrm_Eggs, (10 - st.getQuestItemsCount(Bl_Wyrm_Eggs) > 3) ? 3 : 10 - st.getQuestItemsCount(Bl_Wyrm_Eggs));
				st.set("Fritz", "2");
			}
			st.getPcSpawn().addSpawn(BlitzWyrm, 120000);
			st.getPcSpawn().addSpawn(BlitzWyrm, 120000);
			attack(st);
		}
		// Events Kusto
		else if(event.equalsIgnoreCase("30512-03.htm"))
		{
			if(st.getQuestItemsCount(Brooch) > 0)
			{
				st.takeItems(Brooch, -1);
				st.giveItems(Bl_Anvil_Coin, 1);
			}
		}
		// Events Balthazar
		else if(event.equalsIgnoreCase("30764-03.htm"))
		{
			st.takeItems(G_Let_Balthazar, -1);
			st.set("cond", "5");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30764-06.htm"))
		{
			st.takeItems(G_Let_Balthazar, -1);
			st.set("cond", "5");
			st.setState(STARTED);
			if(st.getQuestItemsCount(Bl_Anvil_Coin) > 0)
			{
				st.takeItems(Bl_Anvil_Coin, -1);
				st.giveItems(Recipe_spiteful_energy, 1);
			}
			else
				return null;
		}
		// Events Rodemai
		else if(event.equalsIgnoreCase("30868-04.htm"))
		{
			st.takeItems(G_Let_Rodemai, -1);
			st.set("cond", "8");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30868-06a.htm"))
		{
			if(st.getInt("cond") == 9)
			{
				st.set("cond", "10");
				st.setState(STARTED);
			}
		}
		else if(event.equalsIgnoreCase("30868-10.htm"))
		{
			st.set("cond", "12");
			st.setState(STARTED);
		}
		// Events Cleo
		else if(event.equalsIgnoreCase("30766-04.htm"))
		{
			st.set("cond", "9");
			st.setState(STARTED);
			L2NpcInstance cleo = L2ObjectsStorage.getByNpcId(Cleo);
			Functions.npcSay(cleo, Say2C.ALL, "Blood and Honour");
			st.getPcSpawn().addSpawn(Kalis, 160665, 21209, -3710, 9999);
			st.getPcSpawn().addSpawn(Athrea, 160665, 21291, -3710, 9999);
			shoutFromTheSpawn(st, Kalis, "Ambition and Power");
			shoutFromTheSpawn(st, Athrea, "War and Death");
		}
		else if(event.equalsIgnoreCase("30766-08.htm"))
			exit(false, st);
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		int kurtz = st.getInt("Kurtz");
		int lutz = st.getInt("Lutz");
		int fritz = st.getInt("Fritz");
		if(st.getPlayer().getClanId() != 0 && st.getPlayer().getClan().getLevel() > 4) // player has level 5 clan already
			return "completed";
		if(st.isCreated() && npcId == Gustaf)
		{
			if(st.getPlayer().getClanId() != 0) // has Clan
			{
				if(st.getPlayer().isClanLeader()) // check if player is clan leader
				{
					if(st.getQuestItemsCount(Proof_Aspiration) > 0) // if he has the proof already, tell him what to do now
					{
						htmltext = "30760-03.htm";
						st.exitCurrentQuest(true);
					}
					else if(st.getPlayer().getClan().getLevel() > 3) // if clanLevel > 3 you can take this quest,
						htmltext = "30760-04.htm"; // because repeatable
					else
					{ // if clanLevel < 4 you cant take it
						htmltext = "30760-02.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
				// player is't a leader
				{
					htmltext = "30760-04t.htm";
					st.exitCurrentQuest(true);
				}
			}
			else
			{
				htmltext = "30760-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
		{
			if(!check_KM(st))
				return null;
			switch(npcId)
			{
				case Gustaf:
				{
					if(cond == 1)
						htmltext = "30760-09.htm";
					else if(cond == 2)
						htmltext = "30760-10.htm";
					else if(cond == 3)
						htmltext = "30760-11.htm";
					else if(cond == 4)
						htmltext = "30760-13.htm";
					else if(cond == 5)
						htmltext = "30760-14.htm";
					else if(cond == 6)
						htmltext = "30760-15.htm";
					else if(cond == 7)
						htmltext = "30760-17.htm";
					else if(cond == 12 && st.getInt("scepter") < 3)
					{
						if(haveScepter(st))
							htmltext = "30760-19.htm";
						else
							return null;
					}
					else if(cond == 12)
					{
						if(haveScepter(st))
							htmltext = "30760-24.htm";
						else
							return null;
					}
					else
						htmltext = "30760-18.htm";
					break;
				}
				case Martien:
				{
					if(cond == 1)
						htmltext = "30645-02.htm";
					else if(cond == 2)
						if(checkEggs(st) && kurtz > 1 && lutz > 1 && fritz > 1)
						{
							htmltext = "30645-05.htm";
							st.set("cond", "3");
							st.setState(STARTED);
							for(int item : EggList)
								st.takeItems(item, -1);
						}
						else
							htmltext = "30645-04.htm";
					else if(cond == 3)
						htmltext = "30645-07.htm";
					else
						htmltext = "30645-08.htm";
					break;
				}
				case Fritz:
				{
					if(cond == 2)
						htmltext = "30761-01.htm";
					break;
				}
				case Lutz:
				{
					if(cond == 2)
						htmltext = "30762-01.htm";
					break;
				}
				case Kurtz:
				{
					if(cond == 2)
						htmltext = "30763-01.htm";
					break;
				}
				case Kusto:
				{
					if(cond > 1 && kurtz == 1)
						htmltext = "30512-01.htm";
					else if(st.getQuestItemsCount(Brooch) > 0)
						htmltext = "30512-02.htm";
					else if(st.getQuestItemsCount(Bl_Anvil_Coin) > 0 || cond > 4)
						htmltext = "30512-04.htm";
					break;
				}
				case Balthazar:
				{
					if(cond == 4)
						if(st.getQuestItemsCount(Bl_Anvil_Coin) > 0)
							htmltext = "30764-04.htm";
						else
							htmltext = "30764-02.htm";
					else if(cond == 5)
						if(st.getQuestItemsCount(Spiteful_Soul_Energy) > 9)
						{
							htmltext = "30764-08.htm";
							st.takeItems(Spiteful_Soul_Energy, -1);
							st.takeItems(Brooch, -1);
							st.set("cond", "6");
							st.setState(STARTED);
						}
						else
							htmltext = "30764-07.htm";
					else if(cond > 5)
						htmltext = "30764-09.htm";
					break;
				}
				case Rodemai:
				{
					if(cond == 7)
						htmltext = "30868-02.htm";
					else if(cond == 8)
						htmltext = "30868-05.htm";
					else if(cond == 9)
						htmltext = "30868-06.htm";
					else if(cond == 10)
						htmltext = "30868-08.htm";
					else if(cond == 11)
					{
						if(haveScepter(st))
							htmltext = "30868-09.htm";
						else
							return null;
					}
					else if(cond == 12)
					{
						if(haveScepter(st))
							htmltext = "30868-11.htm";
						else
							return null;
					}
					break;
				}
				case Cleo:
				{
					if(cond == 8)
						htmltext = "30766-02.htm";
					else if(cond == 9)
						htmltext = "30766-05.htm";
					else if(cond == 10)
						htmltext = "30766-06.htm";
					else if(cond == 11 || cond == 12)
					{
						if(haveScepter(st))
							htmltext = "30766-07.htm";
						else
							return null;
					}
					break;
				}
				case Coffer:
				{
					if(st.getInt("cond") == 10)
					{
						if(st.getQuestItemsCount(Imperial_Key) < 6)
							htmltext = "30765-03a.htm";
						else if(st.getInt("scepter") == 2)
						{
							htmltext = "30765-02.htm";
							st.set("cond", "11");
							st.setState(STARTED);
							st.takeItems(Imperial_Key, -1);
							st.giveItems(Scepter_Judgement, 1);
							removePcSpawn(st);
						}
						else
							htmltext = "<font color=\"LEVEL\">Imperial Coffer:</font><br><br>You and your Clan didn't kill the Imperial Gravekeeper by your own, do it try again.";
					}
					else if(cond > 10)
						htmltext = "<font color=\"LEVEL\">Imperial Coffer:</font><br><br>You already have the Scepter of Judgement.";
					break;
				}
				case Kalis:
				{
					if(cond == 9)
						htmltext = "30759-01.htm";
					break;
				}
				case Athrea:
				{
					if(cond == 9)
						htmltext = "30758-01.htm";
					break;
				}
			}
		}
		return htmltext;
	}

	//TODO перенести в AI "ImperialGravekeeper", чтобы спавн срабатывал и от ударов сокланов тоже.
	// сразу не сделал, т.к. ХЗ как можно нормально деспавнить из-под AI. 
	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		if(st.getInt("cond") == 10 && npc.getMaxHp() / 2 > npc.getCurrentHp() && Rnd.chance(20))
		{
			for(int i = 1; i <= 4; i++)
			{
				Location pos = Location.coordsRandomize(npc.getX(), npc.getY(), 0, 0, 100, 200);
				st.getPcSpawn().addSpawn(ImperialSlave, pos.getX(), pos.getY(), npc.getZ(), 600000);
			}

			attack(st);
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		if(npcId == SpitefulSoulLeader)
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, 5);
			if(st != null && st.getPlayer().isClanLeader())
				onKill(npc, st);
		}
		else
			super.onKill(npc, killer);
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		for(int[] aDROPLIST_COND : DROPLIST_COND)
		{
			if(cond == aDROPLIST_COND[0] && npcId == aDROPLIST_COND[1])
				if(st.rollAndGiveLimited(aDROPLIST_COND[2], 1, aDROPLIST_COND[4], aDROPLIST_COND[3]))
					st.playSound(st.getQuestItemsCount(aDROPLIST_COND[2]) == aDROPLIST_COND[3] ? SOUND_MIDDLE : SOUND_ITEMGET);
		}
		if(cond == 10)
		{
			if(npcId == GraveGuard && Rnd.chance(15))
				st.getPcSpawn().addSpawn(GraveKeyKeeper, 120000);
			else if(npcId == ImperialGravekeeper)
			{
				st.getPcSpawn().addSpawn(Coffer, 3600000); //спавнится на час
				shoutFromTheSpawn(st, Coffer, "Curse of the gods on the one that defiles the property of the empire!");
				st.set("scepter", "2");
			}
		}
	}
}
