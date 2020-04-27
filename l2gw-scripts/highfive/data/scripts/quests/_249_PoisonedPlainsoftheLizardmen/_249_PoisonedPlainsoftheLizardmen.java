package quests._249_PoisonedPlainsoftheLizardmen;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 04.02.11 17:31
 */
public class _249_PoisonedPlainsoftheLizardmen extends Quest
{
	// NPCs
	private static final int mouen = 30196;
	private static final int johny = 32744;

	public _249_PoisonedPlainsoftheLizardmen()
	{
		super(249, "_249_PoisonedPlainsoftheLizardmen", "Poisoned Plains of the Lizardmen");

		addStartNpc(mouen);
		addTalkId(mouen, johny);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();
		if(st.isCompleted())
		{
			if(npcId == johny)
				showPage("johny_q0249_03.htm", player);
			else
				showPage("mouen_q0249_06.htm", player);
			return;
		}

		if(npcId == mouen && st.isCreated() && player.getLevel() >= 82)
		{
			if(reply == 249)
			{
				st.setMemoState(1);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("mouen_q0249_04.htm", player);
			}
			else if(reply == 1)
				showQuestPage("mouen_q0249_03.htm", player);
		}
		else if(npcId == johny && st.getMemoState() == 1 && st.isStarted())
		{
			if(reply == 3)
				showPage("johny_q0249_04.htm", player);
			else if(reply == 4)
			{
				st.rollAndGive(57, 83056, 100);
				st.addExpAndSp(477496, 58743);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
				showPage("johny_q0249_05.htm", player);
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.isCompleted())
		{
			if(npcId == johny)
				return "npchtm:johny_q0249_03.htm";

			return "npchtm:mouen_q0249_06.htm";
		}

		int cond = st.getMemoState();

		if(npcId == mouen)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82)
					return "mouen_q0249_01.htm";

				st.exitCurrentQuest(true);
				return "mouen_q0249_02.htm";
			}
			if(st.isStarted() && cond == 1)
				return "npchtm:mouen_q0249_05.htm";
		}
		else if(npcId == johny)
		{
			if(cond == 1)
				return "npchtm:johny_q0249_01.htm";
			return "npchtm:johny_q0249_02.htm";
		}

		return "noquest";
	}
}
