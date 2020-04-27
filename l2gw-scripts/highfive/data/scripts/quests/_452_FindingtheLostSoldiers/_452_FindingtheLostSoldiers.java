package quests._452_FindingtheLostSoldiers;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.crontab.Crontab;

/**
 * @author rage
 * @date 06.02.11 14:44
 */
public class _452_FindingtheLostSoldiers extends Quest
{
	// NPCs
	private static final int zaykhan = 32773;
	private static final int gracia_soldier_corpse1 = 32769;
	private static final int gracia_soldier_corpse2 = 32770;
	private static final int gracia_soldier_corpse3 = 32771;
	private static final int gracia_soldier_corpse4 = 32772;

	// Items
	private static final int q_id_tag_of_gracian_soldier = 15513;

	private static final Crontab resetTime = new Crontab("30 6 * * *");

	public _452_FindingtheLostSoldiers()
	{
		super(452, "_452_FindingtheLostSoldiers", "Finding the Lost Soldiers");

		addStartNpc(zaykhan);
		addTalkId(zaykhan, gracia_soldier_corpse1, gracia_soldier_corpse2, gracia_soldier_corpse3, gracia_soldier_corpse4);
		addQuestItem(q_id_tag_of_gracian_soldier);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == zaykhan)
		{
			if(st.isCreated())
			{
				if(reply == 452 && player.getLevel() >= 84 && !player.getVarB("q452"))
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("zaykhan_q0452_05.htm", player);
				}
				else if(reply == 1 && player.getLevel() >= 84 && !player.getVarB("q452"))
					showQuestPage("zaykhan_q0452_04.htm", player);
			}
		}
		else if(npcId >= gracia_soldier_corpse1 && npcId <= gracia_soldier_corpse4 && st.isStarted() && st.getMemoState() == 1)
		{
			L2NpcInstance npc = player.getLastNpc();
			if(npc.i_quest0 == 0)
			{
				npc.i_quest0 = 1;
				if(Rnd.chance(50))
				{
					st.giveItems(q_id_tag_of_gracian_soldier, 1);
					st.playSound(SOUND_MIDDLE);
					st.setMemoState(2);
					st.setCond(2);
					showPage("gracia_soldier_corpse1_q0452_02.htm", player);
				}
				else
					showPage("gracia_soldier_corpse1_q0452_04.htm", player);
			}
			else
				showPage("gracia_soldier_corpse1_q0452_04.htm", player);
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == zaykhan)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 84 && !st.getPlayer().getVarB("q452"))
					return "zaykhan_q0452_01.htm";

				st.exitCurrentQuest(true);
				if(st.getPlayer().getLevel() < 84)
					return "zaykhan_q0452_02.htm";

				return "zaykhan_q0452_03.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1 && !st.haveQuestItems(q_id_tag_of_gracian_soldier))
					return "npchtm:zaykhan_q0452_06.htm";
				if(cond == 2 && st.haveQuestItems(q_id_tag_of_gracian_soldier))
				{
					st.rollAndGive(57, 95200, 100);
					st.addExpAndSp(435024, 50366);
					st.takeItems(q_id_tag_of_gracian_soldier, -1);
					st.getPlayer().setVar("q452", "1", (int) (resetTime.timeNextUsage(System.currentTimeMillis()) / 1000));
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:zaykhan_q0452_07.htm";
				}
			}
		}
		else if(npcId >= gracia_soldier_corpse1 && npcId <= gracia_soldier_corpse4 && cond == 1)
			return "npchtm:gracia_soldier_corpse1_q0452_01.htm";

		return "noquest";
	}
}
