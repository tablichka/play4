package quests._169_OffspringOfNightmares;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _169_OffspringOfNightmares extends Quest
{
	//NPC
	private static final int Vlasty = 30145;
	//QuestItem
	private static final int CrackedSkull = 1030;
	private static final int PerfectSkull = 1031;
	//Item
	private static final int BoneGaiters = 31;
	//MOB
	private static final int DarkHorror = 20105;
	private static final int LesserDarkHorror = 20025;

	public _169_OffspringOfNightmares()
	{
		super(169, "_169_OffspringOfNightmares", "Offspring of Nightmares");

		addStartNpc(Vlasty);

		addTalkId(Vlasty);

		addKillId(DarkHorror);
		addKillId(LesserDarkHorror);

		addQuestItem(CrackedSkull, PerfectSkull);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("30145-04.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30145-08.htm"))
		{
			st.giveItems(BoneGaiters, 1);
			st.rollAndGive(57, 17030 + 10 * st.getQuestItemsCount(CrackedSkull), 100);
			st.addExpAndSp(17475, 818);
			st.takeItems(CrackedSkull, -1);
			st.takeItems(PerfectSkull, -1);

			if(st.getPlayer().getVarInt("NR41") == 0)
			{
				st.getPlayer().setVar("NR41", 100000);
				st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4155", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
			}
			else if(st.getPlayer().getVarInt("NR41") % 100000000 / 10000000 == 0)
			{
				st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 10000000);
				st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4155", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
			}

			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
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
		if(npcId == Vlasty)
			if(cond == 0)
			{
				if(st.getPlayer().getRace() != Race.darkelf)
				{
					htmltext = "30145-00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() >= 15)
					htmltext = "30145-03.htm";
				else
				{
					htmltext = "30145-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
			{
				if(st.getQuestItemsCount(CrackedSkull) == 0)
					htmltext = "30145-05.htm";
				else
					htmltext = "30145-06.htm";
			}
			else if(cond == 2)
				htmltext = "30145-07.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getInt("cond");
		if(cond == 1)
		{
			if(st.rollAndGive(CrackedSkull, 1, 70))
				st.playSound(SOUND_ITEMGET);

			if(st.rollAndGiveLimited(PerfectSkull, 1, 20, 1))
			{
				st.giveItems(PerfectSkull, 1);
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
		}
	}
}