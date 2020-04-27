package quests._10282_TotheSeedofAnnihilation;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 10.09.11 10:58
 */
public class _10282_TotheSeedofAnnihilation extends Quest
{
	private static final int kbarldire = 32733;
	private static final int clemis = 32734;
	private static final int q_letter_to_seed_of_annihilation = 15512;

	public _10282_TotheSeedofAnnihilation()
	{
		super(10282, "_10282_TotheSeedofAnnihilation", "To the Seed of Annihilation");

		addStartNpc(kbarldire);
		addTalkId(kbarldire, clemis);
		addQuestItem(q_letter_to_seed_of_annihilation);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		L2Player talker = st.getPlayer();

		if(npcId == kbarldire)
		{
			if(st.isCompleted())
				return "npchtm:kbarldire_q10282_02.htm";

			if(st.isCreated())
				if(talker.getLevel() >= 84)
					return "kbarldire_q10282_01.htm";
				else
					return "npchtm:kbarldire_q10282_03.htm";
			if(st.isStarted() && st.getMemoState() == 1)
					return "npchtm:kbarldire_q10282_09.htm";
		}
		else if(npcId == clemis)
		{
			if(st.isCompleted())
				return "npchtm:clemis_q10282_04.htm";

			if(st.isStarted() && st.getMemoState() == 1)
				if(st.getQuestItemsCount(q_letter_to_seed_of_annihilation) >= 1)
					return "npchtm:clemis_q10282_01.htm";
				else
					return "npchtm:clemis_q10282_03.htm";

		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("completed", st.getPlayer());
			return;
		}

		L2Player talker = st.getPlayer();
		int npcId = talker.getLastNpc().getNpcId();

		if(npcId == kbarldire)
		{
			if(st.isCreated())
			{
				if(reply == 10282 && talker.getLevel() >= 84)
				{
					st.giveItems(q_letter_to_seed_of_annihilation, 1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("kbarldire_q10282_08.htm", talker);
					st.setCond(1);
					showQuestMark(st.getPlayer());
				}
				else if( reply == 1 )
				{
					if(talker.getLevel() >= 84)
					{
						showPage("kbarldire_q10282_04.htm", talker);
					}
				}
				else if( reply == 2 )
				{
					if(talker.getLevel() >= 84)
					{
						showPage("kbarldire_q10282_05.htm", talker);
					}
				}
				else if( reply == 3 )
				{
					if(talker.getLevel() >= 84)
					{
						showPage("kbarldire_q10282_06.htm", talker);
					}
				}
				else if( reply == 4 )
				{
					if(talker.getLevel() >= 84)
					{

						showQuestPage("kbarldire_q10282_07.htm", talker);
					}
				}
				else if( reply == 5 )
				{
					if(talker.getLevel() >= 84)
					{
						showQuestPage("kbarldire_q10282_07a.htm", talker);
					}
				}
			}
		}
		else if(npcId == clemis)
		{
			if(st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(q_letter_to_seed_of_annihilation) >= 1)
			{
				st.giveItems(57, 212182);
				st.addExpAndSp(1148480, 99110);
				st.takeItems(q_letter_to_seed_of_annihilation, st.getQuestItemsCount(q_letter_to_seed_of_annihilation));
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
			}
		}
	}
}
