package quests._10271_TheEnvelopingDarkness;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 24.11.2010 15:21:40
 */
public class _10271_TheEnvelopingDarkness extends Quest
{
	// NPCs
	private static final int ORBIU = 32560;
	private static final int EL = 32556;
	private static final int MEDIVAL = 32528;

	// Items
	private static final int MEDIBALS_DOCUMENT = 13852;

	public _10271_TheEnvelopingDarkness()
	{
		super(10271, "_10271_TheEnvelopingDarkness", "The Enveloping Darkness");

		addStartNpc(ORBIU);
		addTalkId(ORBIU, EL, MEDIVAL);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();

		if(event.equals("wharf_soldier_orbiu_q10271_04.htm"))
		{
			if(player.getLevel() >= 75)
				return event;
		}
		else if(event.equals("wharf_soldier_orbiu_q10271_05.htm"))
		{
			if(player.getLevel() >= 75 && st.isCreated())
			{
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				return event;
			}
		}
		else if(event.equals("soldier_el_q10271_03.htm") || event.equals("soldier_el_q10271_04.htm") || event.equals("soldier_el_q10271_05.htm"))
		{
			if(st.getCond() == 1)
				return "npchtm:" + event;
		}
		else if(event.equals("soldier_el_q10271_06.htm"))
		{
			if(st.getCond() == 1)
			{
				st.setCond(2);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				return "npchtm:" + event;
			}
		}
		else if(event.equals("soldier_el_q10271_09.htm"))
		{
			if(st.getCond() == 3)
			{
				st.setCond(4);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:" + event;
			}
		}
		return null;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == ORBIU)
		{
			if(st.isCreated())
			{
				if(player.getLevel() >= 75)
					return "wharf_soldier_orbiu_q10271_01.htm";

				st.exitCurrentQuest(true);
				return "wharf_soldier_orbiu_q10271_02.htm";
			}
			if(st.isCompleted())
				return "npchtm:wharf_soldier_orbiu_q10271_03.htm";
			if(cond == 1)
				return "npchtm:wharf_soldier_orbiu_q10271_06.htm";
			if(cond == 2 || cond == 3)
				return "npchtm:wharf_soldier_orbiu_q10271_07.htm";
			if(cond == 4 && st.haveQuestItems(MEDIBALS_DOCUMENT))
			{
				st.rollAndGive(57, 62516, 100);
				st.addExpAndSp(377403, 37867);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
				return "npchtm:wharf_soldier_orbiu_q10271_08.htm";
			}
		}
		else if(npcId == EL)
		{
			if(st.isCompleted())
				return "npchtm:soldier_el_q10271_02.htm";
			if(cond == 1)
				return "npchtm:soldier_el_q10271_01.htm";
			if(cond == 2)
				return "npchtm:soldier_el_q10271_07.htm";
			if(cond == 3 && st.haveQuestItems(MEDIBALS_DOCUMENT))
				return "npchtm:soldier_el_q10271_08.htm";
			
		}
		else if(npcId == MEDIVAL)
		{
			if(st.isCompleted())
				return "npchtm:corpse_of_medival_q10271_02.htm";
			if(cond == 2)
			{
				st.giveItems(MEDIBALS_DOCUMENT, 1);
				st.setCond(3);
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				return "npchtm:corpse_of_medival_q10271_01.htm";
			}
			if(cond == 3)
				return "npchtm:corpse_of_medival_q10271_03.htm";
		}

		return "npchtm:noquest";
	}
}
