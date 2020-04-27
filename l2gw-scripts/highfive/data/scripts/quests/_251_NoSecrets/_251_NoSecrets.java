package quests._251_NoSecrets;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author rage
 * @date 04.02.11 18:32
 */
public class _251_NoSecrets extends Quest
{
	// NPCs
	private static final int pinaps = 30201;

	// Items
	private static final int q_training_diary = 15508;
	private static final int q_training_timetable = 15509;

	// Mobs
	private static final HashMap<Integer, int[]> _mobs = new HashMap<Integer, int[]>();
	static
	{
		_mobs.put(22781, new int[] {64, q_training_diary, 10});
		_mobs.put(22785, new int[] {22, q_training_diary, 10});
		_mobs.put(22783, new int[] {46, q_training_diary, 10});
		_mobs.put(22780, new int[] {48, q_training_diary, 10});
		_mobs.put(22784, new int[] {48, q_training_diary, 10});
		_mobs.put(22782, new int[] {48, q_training_diary, 10});
		_mobs.put(22775, new int[] {87, q_training_timetable, 5});
		_mobs.put(22778, new int[] {87, q_training_timetable, 5});
		_mobs.put(22777, new int[] {87, q_training_timetable, 5});
	}

	public _251_NoSecrets()
	{
		super(251, "_251_NoSecrets", "No Secrets");

		addStartNpc(pinaps);
		addTalkId(pinaps);
		for(int npcId : _mobs.keySet())
			addKillId(npcId);
		addQuestItem(q_training_diary, q_training_timetable);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("cute_harry_q0250_12.htm", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == pinaps && st.isCreated() && player.getLevel() >= 82)
		{
			if(reply == 251)
			{
				st.setMemoState(1);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("pinaps_q0251_05.htm", player);
			}
			else if(reply == 1)
				showQuestPage("pinaps_q0251_04.htm", player);
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:pinaps_q0251_03.htm";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == pinaps)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82)
					return "pinaps_q0251_01.htm";

				st.exitCurrentQuest(true);
				return "pinaps_q0251_02.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
				{
					if(st.getQuestItemsCount(q_training_diary) < 10 || st.getQuestItemsCount(q_training_timetable) < 5)
						return "npchtm:pinaps_q0251_06.htm";

					st.rollAndGive(57, 313355, 100);
					st.addExpAndSp(56787, 160578);
					st.takeItems(q_training_diary, -1);
					st.takeItems(q_training_timetable, -1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					return "npchtm:pinaps_q0251_07.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(_mobs.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithQuest(killer, 1);
			int[] drop = _mobs.get(npc.getNpcId());

			if(qs != null && qs.rollAndGiveLimited(drop[1], 1, drop[0], drop[2]))
			{
				if(qs.getQuestItemsCount(q_training_diary) >= 10 && qs.getQuestItemsCount(q_training_timetable) >= 5)
				{
					qs.setCond(2);
					showQuestMark(qs.getPlayer());
					qs.playSound(SOUND_MIDDLE);
				}
				else
					qs.playSound(SOUND_ITEMGET);
			}
		}
	}
}
