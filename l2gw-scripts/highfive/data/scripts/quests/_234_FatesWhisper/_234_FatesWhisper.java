package quests._234_FatesWhisper;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.Inventory;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

public class _234_FatesWhisper extends Quest
{
	// items
	private final static int PIPETTE_KNIFE = 4665;
	private final static int REIRIAS_SOUL_ORB = 4666;
	private final static int KERNONS_INFERNIUM_SCEPTER = 4667;
	private final static int GOLCONDAS_INFERNIUM_SCEPTER = 4668;
	private final static int HALLATES_INFERNIUM_SCEPTER = 4669;
	private final static int REORINS_HAMMER = 4670;
	private final static int REORINS_MOLD = 4671;
	private final static int INFERNIUM_VARNISH = 4672;
	private final static int RED_PIPETTE_KNIFE = 4673;
	private final static int STAR_OF_DESTINY = 5011;
	private final static int CRYSTAL_B = 1460;
	private final static int WHITE_CLOTHES = 14362;
	private final static int BLOODED_FABRICS = 14361;
	// Weapon B
	private final static int Damaskus = 79;
	private final static int Lance = 97;
	private final static int Samurai = 2626;
	private final static int Staff = 210;
	private final static int BOP = 287;
	private final static int Battle = 175;
	private final static int Demons = 234;
	private final static int Bellion = 268;
	private final static int Glory = 171;
	private final static int WizTear = 7889;
	private final static int GuardianSword = 7883;
	private final static int KaimVanulsBones = 7893;
	// Weapon A
	private final static int Tallum = 80;
	private final static int Infernal = 7884;
	private final static int Carnage = 288;
	private final static int Halberd = 98;
	private final static int Elemental = 150;
	private final static int Dasparion = 212;
	private final static int Spiritual = 7894;
	private final static int Bloody = 235;
	private final static int Blood = 269;
	private final static int Meteor = 2504;
	private final static int Destroyer = 7899;
	private final static int Keshanberk = 5233;
	// NPCs
	private final static int REORIN = 31002;
	private final static int CLIFF = 30182;
	private final static int FERRIS = 30847;
	private final static int ZENKIN = 30178;
	private final static int KASPAR = 30833;
	private final static int CABRIOCOFFER = 31027;
	private final static int CHEST_KERNON = 31028;
	private final static int CHEST_GOLKONDA = 31029;
	private final static int CHEST_HALLATE = 31030;
	// MOBs
	private final static int SHILLEN_MESSAGER = 25035;
	private final static int DEATH_LORD = 25220;
	private final static int KERNON = 25054;
	private final static int LONGHORN = 25126;
	private final static int BAIUM = 29020;

	private final static int PL_SOLDIER = 20823;
	private final static int PL_ARCHER = 20826;
	private final static int PL_WARRIOR = 20827;
	private final static int PL_SHAMAN = 20828;
	private final static int PL_OVERLORD = 20829;

	private final static int G_ANGEL = 20859;
	private final static int S_ANGEL = 20860;

	public _234_FatesWhisper()
	{
		super(234, "_234_FatesWhisper", "Fates Whisper");

		addStartNpc(REORIN);
		addTalkId(REORIN);
		addTalkId(CLIFF);
		addTalkId(FERRIS);
		addTalkId(ZENKIN);
		addTalkId(KASPAR);
		addTalkId(CABRIOCOFFER);
		addTalkId(CHEST_KERNON);
		addTalkId(CHEST_GOLKONDA);
		addTalkId(CHEST_HALLATE);

		addKillId(SHILLEN_MESSAGER);
		addKillId(DEATH_LORD);
		addKillId(KERNON);
		addKillId(LONGHORN);
		addKillId(PL_SOLDIER, PL_ARCHER, PL_WARRIOR, PL_SHAMAN, PL_OVERLORD, G_ANGEL, S_ANGEL);
		addAttackId(BAIUM);

		addQuestItem(REIRIAS_SOUL_ORB,
				HALLATES_INFERNIUM_SCEPTER,
				KERNONS_INFERNIUM_SCEPTER,
				GOLCONDAS_INFERNIUM_SCEPTER,
				INFERNIUM_VARNISH,
				REORINS_HAMMER,
				REORINS_MOLD,
				PIPETTE_KNIFE,
				RED_PIPETTE_KNIFE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		int oldweapon = 0;
		int newweapon = 0;
		if(event.equalsIgnoreCase("31002-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("31002-05b.htm"))
		{
			st.takeItems(REIRIAS_SOUL_ORB, -1);
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("31030-02.htm"))
		{
			st.setState(STARTED);
			st.giveItems(HALLATES_INFERNIUM_SCEPTER, 1);
		}
		else if(event.equalsIgnoreCase("31028-02.htm"))
		{
			st.setState(STARTED);
			st.giveItems(KERNONS_INFERNIUM_SCEPTER, 1);
		}
		else if(event.equalsIgnoreCase("31029-02.htm"))
		{
			st.setState(STARTED);
			st.giveItems(GOLCONDAS_INFERNIUM_SCEPTER, 1);
		}
		else if(event.equalsIgnoreCase("31002-06a.htm"))
		{
			st.takeItems(HALLATES_INFERNIUM_SCEPTER, -1);
			st.takeItems(KERNONS_INFERNIUM_SCEPTER, -1);
			st.takeItems(GOLCONDAS_INFERNIUM_SCEPTER, -1);
			st.set("cond", "3");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30182-01c.htm"))
		{
			st.setState(STARTED);
			st.giveItems(INFERNIUM_VARNISH, 1);
		}
		else if(event.equalsIgnoreCase("31002-07a.htm"))
		{
			st.takeItems(INFERNIUM_VARNISH, -1);
			st.set("cond", "4");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("31002-08a.htm"))
		{
			st.takeItems(REORINS_HAMMER, -1);
			st.set("cond", "5");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30833-01c.htm"))
		{
			st.set("cond", "7");
			st.giveItems(PIPETTE_KNIFE, 1);
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30833-01d.htm"))
		{
			st.set("cond", "8");
			st.giveItems(WHITE_CLOTHES, 30);
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("Damaskus.htm"))
			oldweapon = Damaskus;
		else if(event.equalsIgnoreCase("Samurai.htm"))
			oldweapon = Samurai;
		else if(event.equalsIgnoreCase("BOP.htm"))
			oldweapon = BOP;
		else if(event.equalsIgnoreCase("Lance.htm"))
			oldweapon = Lance;
		else if(event.equalsIgnoreCase("Battle.htm"))
			oldweapon = Battle;
		else if(event.equalsIgnoreCase("Staff.htm"))
			oldweapon = Staff;
		else if(event.equalsIgnoreCase("Demons.htm"))
			oldweapon = Demons;
		else if(event.equalsIgnoreCase("Bellion.htm"))
			oldweapon = Bellion;
		else if(event.equalsIgnoreCase("Glory.htm"))
			oldweapon = Glory;
		else if(event.equalsIgnoreCase("WizTear.htm"))
			oldweapon = WizTear;
		else if(event.equalsIgnoreCase("GuardianSword.htm"))
			oldweapon = GuardianSword;
		else if(event.equalsIgnoreCase("KaimVanulsBones.htm"))
			oldweapon = KaimVanulsBones;
		else if(event.equalsIgnoreCase("Tallum"))
			newweapon = Tallum;
		else if(event.equalsIgnoreCase("Infernal"))
			newweapon = Infernal;
		else if(event.equalsIgnoreCase("Carnage"))
			newweapon = Carnage;
		else if(event.equalsIgnoreCase("Halberd"))
			newweapon = Halberd;
		else if(event.equalsIgnoreCase("Elemental"))
			newweapon = Elemental;
		else if(event.equalsIgnoreCase("Dasparion"))
			newweapon = Dasparion;
		else if(event.equalsIgnoreCase("Spiritual"))
			newweapon = Spiritual;
		else if(event.equalsIgnoreCase("Bloody"))
			newweapon = Bloody;
		else if(event.equalsIgnoreCase("Blood"))
			newweapon = Blood;
		else if(event.equalsIgnoreCase("Meteor"))
			newweapon = Meteor;
		else if(event.equalsIgnoreCase("Destroyer"))
			newweapon = Destroyer;
		else if(event.equalsIgnoreCase("Keshanberk"))
			newweapon = Keshanberk;
		else if(event.equalsIgnoreCase("CABRIOCOFFER_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(CABRIOCOFFER);
			if(isQuest != null)
				isQuest.deleteMe();
		}
		else if(event.equalsIgnoreCase("CHEST_HALLATE_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(CHEST_HALLATE);
			if(isQuest != null)
				isQuest.deleteMe();
		}
		else if(event.equalsIgnoreCase("CHEST_KERNON_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(CHEST_KERNON);
			if(isQuest != null)
				isQuest.deleteMe();
		}
		else if(event.equalsIgnoreCase("CHEST_GOLKONDA_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(CHEST_GOLKONDA);
			if(isQuest != null)
				isQuest.deleteMe();
		}
		if(oldweapon != 0)
			if(st.getQuestItemsCount(oldweapon) >= 1)
			{
				if(st.getQuestItemsCount(CRYSTAL_B) >= 984)
				{
					st.set("oldweapon", String.valueOf(oldweapon));
					st.takeItems(CRYSTAL_B, 984);
					st.set("cond", "12");
					st.setState(STARTED);
				}
				else
					htmltext = "cheeter.htm";
			}
			else
				htmltext = "noweapon.htm";
		if(newweapon != 0)
			if(st.getQuestItemsCount(st.getInt("oldweapon")) >= 1)
			{
				st.takeItems(st.getInt("oldweapon"), 1);
				st.giveItems(newweapon, 1);
				st.giveItems(STAR_OF_DESTINY, 1);
				st.unset("cond");
				st.unset("oldweapon");
				st.playSound(SOUND_FINISH);
				htmltext = "make.htm";
				st.exitCurrentQuest(false);
			}
			else
				htmltext = "noweapon.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == REORIN)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 75)
				{
					htmltext = "31002-01.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "31002-02.htm";
			}
			else if(cond == 1 && st.getQuestItemsCount(REIRIAS_SOUL_ORB) > 0)
				htmltext = "31002-05.htm";
			else if(cond == 2 && st.getQuestItemsCount(HALLATES_INFERNIUM_SCEPTER) > 0 && st.getQuestItemsCount(KERNONS_INFERNIUM_SCEPTER) > 0 && st.getQuestItemsCount(GOLCONDAS_INFERNIUM_SCEPTER) > 0)
				htmltext = "31002-06.htm";
			else if(cond == 3 && st.getQuestItemsCount(INFERNIUM_VARNISH) > 0)
				htmltext = "31002-07.htm";
			else if(cond == 4 && st.getQuestItemsCount(REORINS_HAMMER) > 0)
				htmltext = "31002-08.htm";
			else if(cond == 5)
				htmltext = "31002-08a.htm";
			else if(cond == 10 && st.getQuestItemsCount(REORINS_MOLD) > 0)
			{
				st.takeItems(REORINS_MOLD, -1);
				st.set("cond", "11");
				st.setState(STARTED);
				htmltext = "31002-09.htm";
			}
			else if(cond == 11)
			{
				if(st.getQuestItemsCount(CRYSTAL_B) > 983)
					htmltext = "31002-10.htm";
				else
					htmltext = "31002-09.htm";
			}
			else if(cond == 12)
				htmltext = "a-grade.htm";
		}
		else if(npcId == CABRIOCOFFER && cond == 1 && st.getQuestItemsCount(REIRIAS_SOUL_ORB) < 1)
		{
			//st.setState(STARTED);
			st.giveItems(REIRIAS_SOUL_ORB, 1);
			htmltext = "31027-01.htm";
		}
		else if(npcId == CHEST_HALLATE && cond == 2 && st.getQuestItemsCount(HALLATES_INFERNIUM_SCEPTER) < 1)
			htmltext = "31030-01.htm";
		else if(npcId == CHEST_KERNON && cond == 2 && st.getQuestItemsCount(KERNONS_INFERNIUM_SCEPTER) < 1)
			htmltext = "31028-01.htm";
		else if(npcId == CHEST_GOLKONDA && cond == 2 && st.getQuestItemsCount(GOLCONDAS_INFERNIUM_SCEPTER) < 1)
			htmltext = "31029-01.htm";
		else if(npcId == CLIFF && cond == 3 && st.getQuestItemsCount(INFERNIUM_VARNISH) < 1)
			htmltext = "30182-01.htm";
		else if(npcId == FERRIS && cond == 4)
		{
			if(st.getQuestItemsCount(REORINS_HAMMER) < 1)
			{
				st.giveItems(REORINS_HAMMER, 1);
			}
			htmltext = "30847-01.htm";
		}
		else if(npcId == ZENKIN && cond == 5)
		{
			st.set("cond", "6");
			st.setState(STARTED);
			htmltext = "30178-01.htm";
		}
		else if(npcId == KASPAR)
		{
			if(cond == 6)
				htmltext = "30833-01.htm";
			else if(cond == 7 && st.getQuestItemsCount(RED_PIPETTE_KNIFE) > 0)
			{
				st.set("cond", "10");
				st.setState(STARTED);
				st.takeItems(RED_PIPETTE_KNIFE, -1);
				st.giveItems(REORINS_MOLD, 1);
				htmltext = "30833-03.htm";
			}
			else if(cond == 8)
			{
				htmltext = "30833-04.htm";
			}
			else if(cond == 9)
			{
				st.set("cond", "10");
				st.setState(STARTED);
				st.takeItems(BLOODED_FABRICS, -1);
				st.giveItems(REORINS_MOLD, 1);
				htmltext = "30833-03.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();

		if(npcId == SHILLEN_MESSAGER)
			addSpawn(CABRIOCOFFER, npc.getLoc(), false, 120000);
		if(npcId == DEATH_LORD)
			addSpawn(CHEST_HALLATE, npc.getLoc(), false, 120000);
		if(npcId == KERNON)
			addSpawn(CHEST_KERNON, npc.getLoc(), false, 120000);
		if(npcId == LONGHORN)
			addSpawn(CHEST_GOLKONDA, npc.getLoc(), false, 120000);


		if(npcId == PL_SOLDIER || npcId == PL_ARCHER || npcId == PL_WARRIOR || npcId == PL_SHAMAN || npcId == PL_OVERLORD || npcId == G_ANGEL || npcId == S_ANGEL)
		{
			GArray<QuestState> party = getPartyMembersWithQuest(killer, 8);
			if(party.size() > 0)
			{
				for(int i = 0; i < party.size(); i++)
				{
					QuestState qs = party.get(i);
					if(!qs.haveQuestItems(WHITE_CLOTHES))
						party.remove(i);
				}

				if(party.size() > 0)
				{
					QuestState qs = party.get(Rnd.get(party.size()));
					if(qs.getQuestItemsCount(WHITE_CLOTHES) > 0)
					{
						qs.takeItems(WHITE_CLOTHES, 1);
						qs.giveItems(BLOODED_FABRICS, 1);
						if(qs.getQuestItemsCount(BLOODED_FABRICS) == 30)
						{
							qs.setCond(9);
							showQuestMark(qs.getPlayer());
							qs.playSound(SOUND_MIDDLE);
						}
						else
							qs.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 7 && npcId == BAIUM && st.getQuestItemsCount(PIPETTE_KNIFE) > 0 && st.getQuestItemsCount(RED_PIPETTE_KNIFE) < 1 && st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == PIPETTE_KNIFE)
		{
			if(Rnd.chance(50))
				Functions.npcSay(npc, Say2C.ALL, "Who dares to try steal my blood?");
			st.takeItems(PIPETTE_KNIFE, -1);
			st.giveItems(RED_PIPETTE_KNIFE, 1);
			st.playSound(SOUND_ITEMGET);
		}
		return null;
	}
}