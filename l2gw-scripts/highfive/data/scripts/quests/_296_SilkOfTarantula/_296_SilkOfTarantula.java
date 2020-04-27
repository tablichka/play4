package quests._296_SilkOfTarantula;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _296_SilkOfTarantula extends Quest
{
	private static final int TARANTULA_SPIDER_SILK = 1493;
	private static final int TARANTULA_SPINNERETTE = 1494;
	private static final int RING_OF_RACCOON = 1508;
	private static final int RING_OF_FIREFLY = 1509;

	public _296_SilkOfTarantula()
	{
		super(296, "_296_SilkOfTarantula", "Silk Of Tarantula");
		addStartNpc(30519);
		addTalkId(30548);

		addKillId(20394);
		addKillId(20403);
		addKillId(20508);

		addQuestItem(TARANTULA_SPIDER_SILK);
		addQuestItem(TARANTULA_SPINNERETTE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("trader_mion_q0296_03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("quit"))
		{
			htmltext = "trader_mion_q0296_06.htm";
			st.takeItems(TARANTULA_SPINNERETTE, -1);
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		else if(event.equalsIgnoreCase("exchange"))
			if(st.getQuestItemsCount(TARANTULA_SPINNERETTE) >= 1)
			{
				htmltext = "defender_nathan_q0296_03.htm";
				st.giveItems(TARANTULA_SPIDER_SILK, 15 + Rnd.get(9));
				st.takeItems(TARANTULA_SPINNERETTE, -1);
			}
			else
				htmltext = "defender_nathan_q0296_02.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == 30519)
		{
			if(cond == 0)
			{
				if(st.getPlayer().getLevel() >= 15)
				{
					if(st.getQuestItemsCount(RING_OF_RACCOON) > 0 || st.getQuestItemsCount(RING_OF_FIREFLY) > 0)
						htmltext = "trader_mion_q0296_02.htm";
					else
					{
						htmltext = "trader_mion_q0296_08.htm";
						return htmltext;
					}
				}
				else
				{
					htmltext = "trader_mion_q0296_01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				if(st.getQuestItemsCount(TARANTULA_SPIDER_SILK) < 1)
					htmltext = "trader_mion_q0296_04.htm";
				else if(st.getQuestItemsCount(TARANTULA_SPIDER_SILK) >= 1)
				{
					htmltext = "trader_mion_q0296_05.htm";
					if(st.getQuestItemsCount(TARANTULA_SPIDER_SILK) >= 10)
						st.rollAndGive(57, st.getQuestItemsCount(TARANTULA_SPIDER_SILK) * 30 + 2000, 100);
					else
						st.rollAndGive(57, st.getQuestItemsCount(TARANTULA_SPIDER_SILK) * 30, 100);

					st.takeItems(TARANTULA_SPIDER_SILK, -1);

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
				}
		}
		else if(npcId == 30548 && cond == 1)
			htmltext = "defender_nathan_q0296_01.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1)
			if(Rnd.chance(50))
				st.rollAndGive(TARANTULA_SPINNERETTE, 1, 45);
			else
				st.rollAndGive(TARANTULA_SPIDER_SILK, 1, 45);
	}
}