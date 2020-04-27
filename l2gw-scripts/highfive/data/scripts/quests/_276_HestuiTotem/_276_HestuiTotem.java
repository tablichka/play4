package quests._276_HestuiTotem;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _276_HestuiTotem extends Quest
{
	//NPCs
	private static int Tanapi = 30571;
	//Mobs
	private static int Kasha_Bear = 20479;
	private static int Kasha_Bear_Totem_Spirit = 27044;
	//Items
	private static int Leather_Pants = 29;
	private static int Totem_of_Hestui = 1500;
	//Quest Items
	private static int Kasha_Parasite = 1480;
	private static int Kasha_Crystal = 1481;

	public _276_HestuiTotem()
	{
		super(276, "_276_HestuiTotem", "Hestui Totem");
		addStartNpc(Tanapi);
		addKillId(Kasha_Bear);
		addKillId(Kasha_Bear_Totem_Spirit);
		addQuestItem(Kasha_Parasite);
		addQuestItem(Kasha_Crystal);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("seer_tanapi_q0276_03.htm") && st.isCreated() && st.getPlayer().getRace() == Race.orc && st.getPlayer().getLevel() >= 15)
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != Tanapi)
			return htmltext;

		if(st.isCreated())
		{
			if(st.getPlayer().getRace() != Race.orc)
			{
				htmltext = "seer_tanapi_q0276_00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 15)
			{
				htmltext = "seer_tanapi_q0276_01.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "seer_tanapi_q0276_02.htm";
				st.set("cond", "0");
			}
		}
		else if(st.isStarted())
			if(st.getQuestItemsCount(Kasha_Crystal) > 0)
			{
				htmltext = "seer_tanapi_q0276_05.htm";
				st.takeItems(Kasha_Crystal, -1);
				st.takeItems(Kasha_Parasite, -1);

				st.giveItems(Totem_of_Hestui, 1);
				st.giveItems(Leather_Pants, 1);

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
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "seer_tanapi_q0276_04.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();

		if(npcId == Kasha_Bear && st.getQuestItemsCount(Kasha_Crystal) == 0)
		{
			if(st.rollAndGiveLimited(Kasha_Parasite, 1, 100, 50))
			{
				if(st.getQuestItemsCount(Kasha_Parasite) == 50)
				{
					st.takeItems(Kasha_Parasite, -1);
					st.getPcSpawn().addSpawn(Kasha_Bear_Totem_Spirit);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == Kasha_Bear_Totem_Spirit && st.getQuestItemsCount(Kasha_Crystal) == 0)
		{
			st.giveItems(Kasha_Crystal, 1);
			st.playSound(SOUND_MIDDLE);
		}
	}
}