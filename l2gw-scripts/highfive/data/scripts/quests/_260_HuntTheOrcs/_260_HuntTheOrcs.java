package quests._260_HuntTheOrcs;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;

public class _260_HuntTheOrcs extends Quest
{
	private static final int ORC_AMULET = 1114;
	private static final int ORC_NECKLACE = 1115;

	public _260_HuntTheOrcs()
	{
		super(260, "_260_HuntTheOrcs", "Hunt The Orcs");

		addStartNpc(30221);

		addKillId(20468, 20469, 20470, 20471, 20472, 20473);

		addQuestItem(ORC_AMULET, ORC_NECKLACE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("sentinel_rayjien_q0260_03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("sentinel_rayjien_q0260_06.htm"))
		{
			st.set("cond", "0");
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == 30221)
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 6 && st.getPlayer().getRace() == Race.elf)
				{
					htmltext = "sentinel_rayjien_q0260_02.htm";
					return htmltext;
				}
				else if(st.getPlayer().getRace() != Race.elf)
				{
					htmltext = "sentinel_rayjien_q0260_00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() < 6)
				{
					htmltext = "sentinel_rayjien_q0260_01.htm";
					st.exitCurrentQuest(true);
				}
				else if(cond == 1 && st.getQuestItemsCount(ORC_AMULET) == 0 && st.getQuestItemsCount(ORC_NECKLACE) == 0)
					htmltext = "sentinel_rayjien_q0260_04.htm";
			}
			else if(cond == 1 && (st.getQuestItemsCount(ORC_AMULET) > 0 || st.getQuestItemsCount(ORC_NECKLACE) > 0))
			{
				htmltext = "sentinel_rayjien_q0260_05.htm";

				if(st.getQuestItemsCount(ORC_AMULET) + st.getQuestItemsCount(ORC_NECKLACE) >= 10)
					st.rollAndGive(57, st.getQuestItemsCount(ORC_AMULET) * 12 + st.getQuestItemsCount(ORC_NECKLACE) * 30 + 1000, 100);
				else
					st.rollAndGive(57, st.getQuestItemsCount(ORC_AMULET) * 12 + st.getQuestItemsCount(ORC_NECKLACE) * 30, 100);

				if(st.getPlayer().getLevel() < 25 && !st.getPlayer().getVarB("NR57"))
				{
					if(st.getPlayer().isMageClass())
					{
						st.playTutorialVoice("tutorial_voice_027", 1000);
						st.giveItems(5790, 3000);
					}
					else
					{
						st.playTutorialVoice("tutorial_voice_026", 1000);
						st.giveItems(5789, 6000);
					}
					st.getPlayer().setVar("NR57", "1");
					st.showQuestionMark(26);
				}
				
				if(st.getPlayer().getVarInt("NR41") % 10000 / 1000 == 0)
				{
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4152", st.getPlayer()).toString(), 5000, ScreenMessageAlign.TOP_CENTER, true));
				}

				st.takeItems(ORC_AMULET, -1);
				st.takeItems(ORC_NECKLACE, -1);
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getInt("cond") > 0)
			if(npcId == 20468 || npcId == 20469 || npcId == 20470)
				st.rollAndGive(ORC_AMULET, 1, 14);
			else if(npcId == 20471 || npcId == 20472 || npcId == 20473)
				st.rollAndGive(ORC_NECKLACE, 1, 14);
	}
}