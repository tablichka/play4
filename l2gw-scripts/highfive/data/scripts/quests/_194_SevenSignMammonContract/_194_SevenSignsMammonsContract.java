package quests._194_SevenSignMammonContract;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExStartScenePlayer;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 21.10.2010 21:32:50
 */
public class _194_SevenSignsMammonsContract extends Quest
{
	// NPCs
	private static final int ATHEBALDT = 30760;
	private static final int COLIN = 32571;
	private static final int FROG = 32572;
	private static final int TESS = 32573;
	private static final int KUTA = 32574;
	private static final int CLAUDIA = 31001;

	// ITEMS
	private static final int INTRODUCTION = 13818;
	private static final int FROG_KING_BEAD = 13820;
	private static final int CANDY_POUCH = 13821;
	private static final int NATIVES_GLOVE = 13819;

	public _194_SevenSignsMammonsContract()
	{
		super(194, "_194_SevenSignMammonContract", "Seven Signs, Mammon's Contract");

		addQuestItem(INTRODUCTION, FROG_KING_BEAD, CANDY_POUCH, NATIVES_GLOVE);
		addStartNpc(ATHEBALDT);
		addTalkId(ATHEBALDT, COLIN, FROG, TESS, KUTA, CLAUDIA);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2Player player = st.getPlayer();
		L2NpcInstance npc = player.getLastNpc();

		if(event.equals("30760-02.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("30760-07.htm"))
		{
			st.setCond(3);
			st.setState(STARTED);
			st.giveItems(INTRODUCTION, 1);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("32571-04.htm"))
		{
			if(player.getTransformation() > 0)
				return "npchtm:32571-trans.htm";

			st.setCond(4);
			st.setState(STARTED);
			st.takeItems(INTRODUCTION, 1);
			transformPlayer(npc, player, 6201);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("32571-06.htm") || event.equals("32571-14.htm") || event.equals("32571-22.htm") && player.getTransformation() > 0)
			npc.altUseSkill(SkillTable.getInstance().getInfo(6200, 1), player);
		else if(event.equals("32571-08.htm"))
		{
			if(player.getTransformation() > 0)
				return "npchtm:32571-trans.htm";
			transformPlayer(npc, player, 6201);
		}
		else if(event.equals("32572-04.htm"))
		{
			st.setCond(5);
			st.setState(STARTED);
			st.giveItems(FROG_KING_BEAD, 1);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("32571-10.htm"))
		{
			st.setCond(6);
			st.takeItems(FROG_KING_BEAD, 1);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("32571-12.htm"))
		{
			if(player.getTransformation() > 0)
				return "npchtm:32571-trans.htm";
			st.setCond(7);
			transformPlayer(npc, player, 6202);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("32571-16.htm"))
		{
			if(player.getTransformation() > 0)
				return "npchtm:32571-trans.htm";
			transformPlayer(npc, player, 6202);
		}
		else if(event.equals("32573-03.htm"))
		{
			st.setCond(8);
			st.giveItems(CANDY_POUCH, 1);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("32571-18.htm"))
		{
			st.setCond(9);
			st.takeItems(CANDY_POUCH, 1);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("32571-20.htm"))
		{
			if(player.getTransformation() > 0)
				return "npchtm:32571-trans.htm";

			st.setCond(10);
			transformPlayer(npc, player, 6203);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("32571-24.htm"))
		{
			if(player.getTransformation() > 0)
				return "npchtm:32571-trans.htm";

			transformPlayer(npc, player, 6203);
		}
		else if(event.equals("32574-04.htm"))
		{
			st.setCond(11);
			st.giveItems(NATIVES_GLOVE, 1);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}

		else if(event.equals("32571-26.htm"))
		{
			st.setCond(12);
			st.takeItems(NATIVES_GLOVE, 1);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("10"))
		{
			st.setCond(2);
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
			player.showQuestMovie(ExStartScenePlayer.SCENE_SSQ_CONTRACT_OF_MAMMON);
			return null;
		}
		else if(event.equals("31001-03.htm"))
		{
			if(st.getPlayer().getLevel() < 79)
				return "<html><body>Only characters who are <font color=\"LEVEL\">level 79</font> or higher may complete this quest.</body></html>";
			st.addExpAndSp(52518015, 5817677);
			st.setState(COMPLETED);
			st.exitCurrentQuest(false);
			st.playSound(SOUND_FINISH);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(npcId == ATHEBALDT)
		{
			if(st.isCompleted())
				return "npchtm:completed";

			QuestState second = player.getQuestState("_193_SevenSignDyingMessage");
			if(second != null && second.isCompleted() && st.isCreated() && player.getLevel() >= 79)
				return "30760-01.htm";
			if(cond == 1)
				return "30760-03.htm";
			if(cond == 2)
				return "30760-05.htm";
			if(cond == 3)
				return "30760-08.htm";

			st.exitCurrentQuest(true);
			return "npchtm:30760-00.htm";
		}
		else if(npcId == COLIN)
		{
			if(cond == 3)
				return "32571-01.htm";
			if(cond == 4)
			{
				if(checkPlayer(player, 6201))
					return "32571-05.htm";

				return "32571-07.htm";
			}
			if(cond == 5)
				return "32571-09.htm";
			if(cond == 6)
				return "32571-11.htm";
			if(cond == 7)
			{
				if(checkPlayer(player, 6202))
					return "32571-13.htm";

				return "32571-15.htm";
			}
			if(cond == 8)
				return "32571-17.htm";
			if(cond == 9)
				return "32571-19.htm";
			if(cond == 10)
			{
				if(checkPlayer(player, 6203))
					return "32571-21.htm";

				return "32571-23.htm";
			}
			if(cond == 11)
				return "32571-25.htm";
		}
		else if(npcId == FROG)
		{
			if(checkPlayer(player, 6201))
			{
				if(cond == 4)
					return "32572-01.htm";
				else if(cond == 5)
					return "32572-05.htm";
			}
			return "32572-00.htm";
		}
		else if(npcId == TESS)
		{
			if(checkPlayer(player, 6202))
			{
				if(cond == 7)
					return "32573-01.htm";
				else if(cond == 8)
					return "32573-04.htm";
			}
			return "32573-00.htm";
		}
		else if(npcId==KUTA)
		{
			if(checkPlayer(player, 6203))
			{
				if(cond == 10)
					return "32574-01.htm";
				else if(cond==11)
					return "32574-05.htm";

			}
			return "32574-00.htm";
		}
		else if(npcId==CLAUDIA)
		{
			if(cond==12)
				return "31001-01.htm";
		}

		return "noquest";
	}

	private boolean checkPlayer(L2Player player, int skillId)
	{
		return player.getEffectBySkillId(skillId) != null;
	}

	private void transformPlayer(L2NpcInstance npc, L2Player player, int skillId)
	{
		npc.altUseSkill(SkillTable.getInstance().getInfo(skillId, 1), player);
	}
}
