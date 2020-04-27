package quests._111_ElrokianHuntersProof;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author rage
 * @date 04.02.11 14:02
 */
public class _111_ElrokianHuntersProof extends Quest
{
	// NPCs
	private static final int marquez = 32113;
	private static final int mushika = 32114;
	private static final int asama = 32115;
	private static final int kirikachin = 32116;

	// Items
	private static final int q_letter_to_kirikachin = 8769;
	private static final int capture_of_elcrok = 8763;
	private static final int capture_stone = 8764;
	private static final int q_capture_train_weapon = 8773;
	private static final int q_dyno_dairy = 8768;
	private static final int q_capture_claw = 8770;
	private static final int q_capture_bone = 8771;
	private static final int q_capture_skin = 8772;

	// MOBs
	private static final HashMap<Integer, int[]> _mobs1 = new HashMap<Integer, int[]>();
	private static final HashMap<Integer, Integer> _mobs2 = new HashMap<Integer, Integer>();
	static
	{
		_mobs1.put(22201, new int[]{33, q_capture_claw});
		_mobs1.put(22200, new int[]{66, q_capture_claw});
		_mobs1.put(22224, new int[]{33, q_capture_claw});
		_mobs1.put(22219, new int[]{33, q_capture_claw});
		_mobs1.put(22202, new int[]{66, q_capture_claw});
		_mobs1.put(22204, new int[]{32, q_capture_bone});
		_mobs1.put(22203, new int[]{65, q_capture_bone});
		_mobs1.put(22225, new int[]{32, q_capture_bone});
		_mobs1.put(22220, new int[]{32, q_capture_bone});
		_mobs1.put(22205, new int[]{66, q_capture_bone});
		_mobs1.put(22209, new int[]{50, q_capture_skin});
		_mobs1.put(22208, new int[]{50, q_capture_skin});
		_mobs1.put(22226, new int[]{50, q_capture_skin});
		_mobs1.put(22221, new int[]{49, q_capture_skin});
		_mobs1.put(22210, new int[]{50, q_capture_skin});
		_mobs2.put(22197, 51);
		_mobs2.put(22196, 51);
		_mobs2.put(22223, 26);
		_mobs2.put(22218, 25);
		_mobs2.put(22198, 51);
	}
	public _111_ElrokianHuntersProof()
	{
		super(111, "_111_ElrokianHuntersProof", "Elrokian Hunter's Proof");

		addStartNpc(marquez);
		addTalkId(marquez, asama, kirikachin, mushika);
		addKillId(22204, 22203, 22225, 22220, 22205, 22201, 22200, 22224, 22219, 22202, 22209, 22208, 22226, 22221, 22210, 22197, 22196, 22223, 22218, 22198);
		addQuestItem(q_dyno_dairy, q_letter_to_kirikachin, q_capture_claw, q_capture_bone, q_capture_skin, q_capture_train_weapon);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("completed", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == marquez)
		{
			if(reply == 1111 && st.isCreated() && player.getLevel() >= 75)
			{
				st.setCond(1);
				st.setMemoState(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("marquez_q0111_05.htm", player);
			}
			else if(reply == 101)
				showPage("marquez_q0111_03.htm", player);
			else if(reply == 102)
				showPage("marquez_q0111_04.htm", player);
			else if(reply == 103)
				showPage("marquez_q0111_08.htm", player);
			else if(reply == 104)
				showPage("marquez_q0111_09.htm", player);
			else if(reply == 105)
				showPage("marquez_q0111_10.htm", player);
			else if(reply == 106)
				showPage("marquez_q0111_11.htm", player);
			else if(reply == 107)
				showPage("marquez_q0111_12.htm", player);
			else if(reply == 108)
			{
				if(st.getMemoState() == 3)
				{
					st.setMemoState(4);
					st.setCond(4);
					showQuestMark(player);
					showPage("marquez_q0111_13.htm", player);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 109)
				showPage("marquez_q0111_16.htm", player);
			else if(reply == 110)
				showPage("marquez_q0111_17.htm", player);
			else if(reply == 111)
				showPage("marquez_q0111_18.htm", player);
			else if(reply == 112)
				showPage("marquez_q0111_19.htm", player);
			else if(reply == 113)
				showPage("marquez_q0111_20.htm", player);
			else if(reply == 114)
				showPage("marquez_q0111_21.htm", player);
			else if(reply == 115)
				showPage("marquez_q0111_22.htm", player);
			else if(reply == 116)
			{
				if(st.getMemoState() == 5)
				{
					st.setCond(6);
					st.setMemoState(6);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
					st.giveItems(q_letter_to_kirikachin, 1);
					showPage("marquez_q0111_23.htm", player);
				}
			}
		}
		else if(npcId == asama)
		{
			if(reply == 301)
			{
				if(st.getMemoState() == 2)
				{
					st.setMemoState(3);
					showPage("asama_q0111_03.htm", player);
					st.setCond(3);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 302)
			{
				if(st.getMemoState() == 9)
				{
					st.setMemoState(10);
					showPage("asama_q0111_06.htm", player);
					st.setCond(9);
					showQuestMark(player);
					st.playSound("EtcSound.elcroki_song_full");
				}
			}
			else if(reply == 303)
				showPage("asama_q0111_08.htm", player);
			else if(reply == 304)
			{
				if(st.getMemoState() == 10)
				{
					st.setMemoState(11);
					showPage("asama_q0111_09.htm", player);
					st.setCond(10);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npcId == kirikachin)
		{
			if(reply == 401)
				showPage("kirikachin_q0111_03.htm", player);
			else if(reply == 402)
			{
				if(st.getMemoState() == 7)
				{
					st.setMemoState(8);
					st.playSound("EtcSound.elcroki_song_full");
					showPage("kirikachin_q0111_05.htm", player);
				}
			}
			else if(reply == 403)
			{
				if(st.getMemoState() == 8)
				{
					st.setMemoState(9);
					showPage("kirikachin_q0111_07.htm", player);
					st.setCond(8);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 404)
			{
				if(st.getMemoState() == 12 && st.haveQuestItems(q_capture_train_weapon))
				{
					st.takeItems(q_capture_train_weapon, -1);
					st.giveItems(capture_of_elcrok, 1);
					st.giveItems(capture_stone, 100);
					st.rollAndGive(57, 1071691, 100);
					st.addExpAndSp(553524, 55538);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("kirikachin_q0111_10.htm", player);
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		return "npchtm:" + event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == marquez)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 75)
					return "marquez_q0111_01.htm";

				st.exitCurrentQuest(true);
				return "npchtm:marquez_q0111_02.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:marquez_q0111_06.htm";
				if(cond == 2)
					return "npchtm:marquez_q0111_06a.htm";
				if(cond == 3)
					return "npchtm:marquez_q0111_07.htm";
				if(cond == 4)
				{
					if(st.getQuestItemsCount(q_dyno_dairy) < 50)
						return "npchtm:marquez_q0111_14.htm";

					st.takeItems(q_dyno_dairy, -1);
					st.setMemoState(5);
					return "npchtm:marquez_q0111_15.htm";
				}
				if(cond == 5)
					return "npchtm:marquez_q0111_15a.htm";
				if(cond == 6)
					return "npchtm:marquez_q0111_24.htm";
				if(cond == 7 || cond == 8)
					return "npchtm:marquez_q0111_25.htm";
				if(cond == 9)
					return "npchtm:marquez_q0111_26.htm";
				if(cond >= 10)
					return "npchtm:marquez_q0111_27.htm";
			}
		}
		else if(st.isStarted())
		{
			if(npcId == asama)
			{
				if(cond == 1)
					return "npchtm:asama_q0111_01.htm";
				if(cond == 2)
					return "npchtm:asama_q0111_02.htm";
				if(cond >= 3 && cond < 9)
					return "npchtm:asama_q0111_04.htm";
				if(cond == 9)
					return "npchtm:asama_q0111_05.htm";
				if(cond == 10)
					return "npchtm:asama_q0111_07.htm";
				if(cond == 11)
				{
					if(st.getQuestItemsCount(q_capture_claw) < 10 || st.getQuestItemsCount(q_capture_bone) < 10 || st.getQuestItemsCount(q_capture_skin) < 10)
						return "npchtm:asama_q0111_10.htm";

					st.setCond(12);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					st.giveItems(q_capture_train_weapon, 1);
					st.takeItems(q_capture_claw, -1);
					st.takeItems(q_capture_bone, -1);
					st.takeItems(q_capture_skin, -1);
					st.setMemoState(12);
					return "npchtm:asama_q0111_11.htm";
				}
				if(cond >= 12)
					return "npchtm:asama_q0111_12.htm";
			}
			else if(npcId == kirikachin)
			{
				if(cond < 6)
					return "npchtm:kirikachin_q0111_01.htm";
				if(cond == 6 && st.haveQuestItems(q_letter_to_kirikachin))
				{
					st.setMemoState(7);
					st.takeItems(q_letter_to_kirikachin, -1);
					st.setCond(7);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:kirikachin_q0111_02.htm";
				}
				if(cond == 7)
					return "npchtm:kirikachin_q0111_04.htm";
				if(cond == 8)
					return "npchtm:kirikachin_q0111_06.htm";
				if(cond >= 9 && cond < 12)
					return "npchtm:kirikachin_q0111_08.htm";
				if(cond == 12)
					return "npchtm:kirikachin_q0111_09.htm";
			}
			else if(npcId == mushika)
			{
				if(cond == 1)
				{
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					st.setMemoState(2);
					return "npchtm:mushika_q0111_01.htm";
				}
				if(cond > 1 && cond < 10)
					return "npchtm:mushika_q0111_02.htm";
				if(cond >= 10)
					return "npchtm:mushika_q0111_03.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(_mobs1.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithMemoState(killer, 11);
			if(qs != null)
			{
				int[] drop = _mobs1.get(npc.getNpcId());
				if(qs.rollAndGiveLimited(drop[1], 1, drop[0], 10))
				{
					if(qs.getQuestItemsCount(q_capture_claw) >= 10 && qs.getQuestItemsCount(q_capture_bone) >= 10 && qs.getQuestItemsCount(q_capture_skin) >= 10)
					{
						qs.playSound(SOUND_MIDDLE);
						qs.setCond(11);
						showQuestMark(qs.getPlayer());
					}
					else
						qs.playSound(SOUND_ITEMGET);
				}
			}
		}
		else if(_mobs2.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithMemoState(killer, 4);
			if(qs != null && qs.rollAndGiveLimited(q_dyno_dairy, 1, _mobs2.get(npc.getNpcId()), 50))
			{
				if(qs.getQuestItemsCount(q_dyno_dairy) >= 50)
				{
					qs.playSound(SOUND_MIDDLE);
					qs.setCond(5);
					showQuestMark(qs.getPlayer());
				}
				else
					qs.playSound(SOUND_ITEMGET);
			}
		}
	}
}
