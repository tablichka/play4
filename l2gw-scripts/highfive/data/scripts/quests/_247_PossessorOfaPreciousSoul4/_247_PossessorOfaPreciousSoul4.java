package quests._247_PossessorOfaPreciousSoul4;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.tables.SkillTable;

public class _247_PossessorOfaPreciousSoul4 extends Quest
{
	private static int CARADINE = 31740;
	private static int LADY_OF_LAKE = 31745;

	private static int CARADINE_LETTER_LAST = 7679;
	private static int NOBLESS_TIARA = 7694;

	public _247_PossessorOfaPreciousSoul4()
	{
		super(247, "_247_PossessorOfaPreciousSoul4", "Possessor Of a Precious Soul 4");

		addStartNpc(CARADINE);

		addTalkId(CARADINE);

		addTalkId(LADY_OF_LAKE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		int cond = st.getInt("cond");
		if(st.isCreated() && event.equals("31740-3.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(cond == 1)
		{
			if(event.equals("31740-4.htm"))
				return htmltext;
			else if(event.equals("31740-5.htm"))
			{
				st.set("cond", "2");
				st.takeItems(CARADINE_LETTER_LAST, 1);
				st.getPlayer().teleToLocation(143230, 44030, -3030);
				return htmltext;
			}
		}
		else if(cond == 2)
			if(event.equals("31740-6.htm"))
				return htmltext;
			else if(event.equals("31740-5.htm"))
			{
				st.getPlayer().teleToLocation(143230, 44030, -3030);
				return htmltext;
			}
			else if(event.equals("31745-2.htm"))
				return htmltext;
			else if(event.equals("31745-3.htm"))
				return htmltext;
			else if(event.equals("31745-4.htm"))
				return htmltext;
			else if(event.equals("31745-5.htm"))
				if(st.getPlayer().getLevel() >= 75)
				{
					st.getPlayer().setNoble(true);
					st.getPlayer().addSkill(SkillTable.getInstance().getInfo(1323, 1));
					st.getPlayer().addSkill(SkillTable.getInstance().getInfo(325, 1));
					st.getPlayer().addSkill(SkillTable.getInstance().getInfo(326, 1));
					st.getPlayer().addSkill(SkillTable.getInstance().getInfo(327, 1));
					st.getPlayer().addSkill(SkillTable.getInstance().getInfo(1324, 1));
					st.getPlayer().addSkill(SkillTable.getInstance().getInfo(1325, 1));
					st.getPlayer().addSkill(SkillTable.getInstance().getInfo(1326, 1));
					st.getPlayer().addSkill(SkillTable.getInstance().getInfo(1327, 1));
					st.giveItems(NOBLESS_TIARA, 1);
					st.addExpAndSp(93836, 0);
					st.getPlayer().broadcastPacket(new SocialAction(st.getPlayer().getObjectId(), SocialAction.SocialType.VICTORY));
					st.getPlayer().getLastNpc().altUseSkill(SkillTable.getInstance().getInfo(4339, 1), st.getPlayer());
					st.playSound(SOUND_FINISH);
					st.unset("cond");
					st.exitCurrentQuest(false);
				}
				else
					htmltext = "31745-6.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == CARADINE && st.getPlayer().isSubClassActive())
		{
			if(st.isCreated() && st.getQuestItemsCount(CARADINE_LETTER_LAST) == 1)
			{
				st.set("cond", "0");
				if(st.getPlayer().getLevel() < 75)
				{
					htmltext = "31740-2.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "31740-1.htm";
			}
			else if(cond == 1)
				htmltext = "31740-3.htm";
			else if(cond == 2)
				htmltext = "31740-6.htm";
		}
		else if(npcId == LADY_OF_LAKE && cond == 2 && st.getPlayer().isSubClassActive())
			if(st.getPlayer().getLevel() >= 75)
				htmltext = "31745-1.htm";
			else
				htmltext = "31745-6.htm";
		return htmltext;
	}
}