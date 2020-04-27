package quests._696_ConquertheHallofErosion;

import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 15.12.11 20:46
 */
public class _696_ConquertheHallofErosion extends Quest
{
	// NPC
	private static final int officer_tepios = 32603;

	public _696_ConquertheHallofErosion()
	{
		super(696, "_696_ConquertheHallofErosion", "Conquer the Hall of Erosion");
		addStartNpc(officer_tepios);
		addTalkId(officer_tepios);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == officer_tepios)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 75)
					return "officer_tepios_q0696_01.htm";

				return "officer_tepios_q0696_02.htm";
			}
			if(st.isStarted() && st.getMemoState() == 4)
			{
				if(st.getQuestItemsCount(13692) == 0)
				{
					st.giveItems(13692, 1);
				}

				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				return "officer_tepios_q0696_07.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == officer_tepios)
		{
			if(reply == 696)
			{
				if(st.isCreated() && talker.getLevel() >= 75)
				{
					st.setMemoState(2);
					showQuestPage("officer_tepios_q0696_05.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 1)
			{
				if(st.getQuestItemsCount(13691) == 1)
				{
					if(FieldCycleManager.getStep(3) == 1)
					{
						showPage("officer_tepios_q0696_04.htm", talker);
					}
					else
					{
						showPage("officer_tepios_q0696_03.htm", talker);
					}
				}
				else
				{
					showPage("officer_tepios_q0696_02a.htm", talker);
				}
			}
		}
	}
}
