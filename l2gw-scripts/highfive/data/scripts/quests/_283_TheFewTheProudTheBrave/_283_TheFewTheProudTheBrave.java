package quests._283_TheFewTheProudTheBrave;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _283_TheFewTheProudTheBrave extends Quest
{
	//NPCs
	private static int PERWAN = 32133;
	//Mobs
	private static int CRIMSON_SPIDER = 22244;
	//Quest Items
	private static int CRIMSON_SPIDER_CLAW = 9747;
	//Chances
	private static int CRIMSON_SPIDER_CLAW_CHANCE = 34;

	public _283_TheFewTheProudTheBrave()
	{
		super(283, "_283_TheFewTheProudTheBrave", "The Few, The Proud, The Brave");
		addStartNpc(PERWAN);
		addKillId(CRIMSON_SPIDER);
		addQuestItem(CRIMSON_SPIDER_CLAW);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("subelder_perwan_q0283_0103.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("subelder_perwan_q0283_0203.htm") && st.isStarted())
		{
			long count = st.getQuestItemsCount(CRIMSON_SPIDER_CLAW);
			if(count > 0)
			{
				st.takeItems(CRIMSON_SPIDER_CLAW, -1);
				if(count >= 10)
				{
					count *= 45;
					count += 2187;
				}
				else
					count *= 45;

				st.rollAndGive(57, count, 100);

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
			else
				return "subelder_perwan_q0283_0202.htm";
		}
		else if(event.equalsIgnoreCase("subelder_perwan_q0283_0204.htm") && st.isStarted())
		{
			st.takeItems(CRIMSON_SPIDER_CLAW, -1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != PERWAN)
			return htmltext;

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 15)
			{
				htmltext = "subelder_perwan_q0283_0101.htm";
				st.set("cond", "0");
			}
			else
			{
				htmltext = "subelder_perwan_q0283_0102.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
			htmltext = st.getQuestItemsCount(CRIMSON_SPIDER_CLAW) > 0 ? "subelder_perwan_q0283_0105.htm" : "subelder_perwan_q0283_0106.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(st.rollAndGive(CRIMSON_SPIDER_CLAW, 1, CRIMSON_SPIDER_CLAW_CHANCE))
			st.playSound(SOUND_ITEMGET);
	}
}