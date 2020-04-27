package quests._293_HiddenVein;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;
import ru.l2gw.commons.math.Rnd;

public class _293_HiddenVein extends Quest
{
	// NPCs
	private static int Filaur = 30535;
	private static int Chichirin = 30539;
	// Mobs
	private static int Utuku_Orc = 20446;
	private static int Utuku_Orc_Archer = 20447;
	private static int Utuku_Orc_Grunt = 20448;
	// Quest Items
	private static int Chrysolite_Ore = 1488;
	private static int Torn_Map_Fragment = 1489;
	private static int Hidden_Ore_Map = 1490;
	// Chances
	private static int Torn_Map_Fragment_Chance = 5;
	private static int Chrysolite_Ore_Chance = 45;

	public _293_HiddenVein()
	{
		super(293, "_293_HiddenVein", "Hidden Vein");
		addStartNpc(Filaur);
		addTalkId(Chichirin);
		addKillId(Utuku_Orc);
		addKillId(Utuku_Orc_Archer);
		addKillId(Utuku_Orc_Grunt);
		addQuestItem(Chrysolite_Ore);
		addQuestItem(Torn_Map_Fragment);
		addQuestItem(Hidden_Ore_Map);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("elder_filaur_q0293_03.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("elder_filaur_q0293_06.htm") && st.isStarted())
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("chichirin_q0293_03.htm") && st.isStarted())
		{
			if(st.getQuestItemsCount(Torn_Map_Fragment) < 4)
				return "chichirin_q0293_02.htm";
			st.takeItems(Torn_Map_Fragment, 4);
			st.giveItems(Hidden_Ore_Map, 1);
		}

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();

		if(st.isCreated())
		{
			if(npcId != Filaur)
				return "noquest";
			if(st.getPlayer().getRace() != Race.dwarf)
			{
				st.exitCurrentQuest(true);
				return "elder_filaur_q0293_00.htm";
			}
			if(st.getPlayer().getLevel() < 6)
			{
				st.exitCurrentQuest(true);
				return "elder_filaur_q0293_01.htm";
			}
			st.set("cond", "0");
			return "elder_filaur_q0293_02.htm";
		}

		if(!st.isStarted())
			return "noquest";

		if(npcId == Filaur)
		{
			long Chrysolite_Ore_count = st.getQuestItemsCount(Chrysolite_Ore);
			long Hidden_Ore_Map_count = st.getQuestItemsCount(Hidden_Ore_Map);
			long reward = st.getQuestItemsCount(Chrysolite_Ore) * 10 + st.getQuestItemsCount(Hidden_Ore_Map) * 1000L;
			if(reward == 0)
				return "elder_filaur_q0293_04.htm";

			if(Chrysolite_Ore_count > 0)
				st.takeItems(Chrysolite_Ore, -1);
			if(Hidden_Ore_Map_count > 0)
				st.takeItems(Hidden_Ore_Map, -1);
			st.rollAndGive(57, reward, 100);

			if(st.getPlayer().getLevel() < 25 && !st.getPlayer().isMageClass() && !st.getPlayer().getVarB("NR57"))
			{
				st.playTutorialVoice("tutorial_voice_026", 1000);
				st.giveItems(5789, 6000);
				st.getPlayer().setVar("NR57", "1");
				st.showQuestionMark(26);
			}

			if(st.getPlayer().getVarInt("NR41") % 10000 / 1000 == 0)
			{
				st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1000);
				st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4152", st.getPlayer()).toString(), 5000, ScreenMessageAlign.TOP_CENTER, true));
			}

			return Chrysolite_Ore_count > 0 && Hidden_Ore_Map_count > 0 ? "elder_filaur_q0293_09.htm" : Hidden_Ore_Map_count > 0 ? "elder_filaur_q0293_08.htm" : "elder_filaur_q0293_05.htm";
		}

		if(npcId == Chichirin)
			return "chichirin_q0293_01.htm";

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(Rnd.chance(Torn_Map_Fragment_Chance))
		{
			st.rollAndGive(Torn_Map_Fragment, 1, 100);
			st.playSound(SOUND_ITEMGET);
		}
		else if(Rnd.chance(Chrysolite_Ore_Chance))
		{
			st.rollAndGive(Chrysolite_Ore, 1, 100);
			st.playSound(SOUND_ITEMGET);
		}
	}
}