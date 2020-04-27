package quests._001_LettersOfLove;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _001_LettersOfLove extends Quest
{
	private final static int DARIN = 30048;
	private final static int ROXXY = 30006;
	private final static int BAULRO = 30033;

	private final static int DARINGS_LETTER = 687;
	private final static int ROXXY_KERCHIEF = 688;
	private final static int DARINGS_RECEIPT = 1079;
	private final static int BAULS_POTION = 1080;
	private final static int NECKLACE = 906;
	private final static int ADENA_ID = 57;

	public _001_LettersOfLove()
	{
		super(1, "_001_LettersOfLove", "Letters of Love");

		addStartNpc(DARIN);
		addTalkId(ROXXY);
		addTalkId(BAULRO);
		addQuestItem(DARINGS_LETTER);
		addQuestItem(ROXXY_KERCHIEF);
		addQuestItem(DARINGS_RECEIPT);
		addQuestItem(BAULS_POTION);
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		if(qs.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("quest_accept"))
		{
			htmltext = "daring_q0001_06.htm";
			qs.setCond(1);
			qs.setState(STARTED);
			qs.giveItems(DARINGS_LETTER, 1);
			qs.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getCond();
		switch(npcId)
		{
			case DARIN:
				if(st.isCreated())
				{
					if(st.getPlayer().getLevel() >= 2)
						htmltext = "daring_q0001_02.htm";
					else
					{
						htmltext = "daring_q0001_01.htm";
						st.exitCurrentQuest(true);
					}
				}
				else if(cond == 1)
					htmltext = "daring_q0001_07.htm";
				else if(cond == 2 && st.getQuestItemsCount(ROXXY_KERCHIEF) == 1)
				{
					htmltext = "daring_q0001_08.htm";
					st.takeItems(ROXXY_KERCHIEF, -1);
					st.giveItems(DARINGS_RECEIPT, 1);
					st.setCond(3);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
				else if(cond == 3)
					htmltext = "daring_q0001_09.htm";
				else if(cond == 4 && st.getQuestItemsCount(BAULS_POTION) == 1)
				{
					htmltext = "daring_q0001_10.htm";
					st.takeItems(BAULS_POTION, -1);
					if(st.getPlayer().getVarInt("NR41") % 10 == 0)
					{
						st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 1);
						st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4151", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
					}
					st.playSound(SOUND_FINISH);
					st.giveItems(NECKLACE, 1);
					st.getPlayer().addExpAndSp(5672, 446);
					st.rollAndGive(ADENA_ID, 2466, 100);
					st.exitCurrentQuest(false);
				}
				break;
			case ROXXY:
				if(cond == 1 && st.getQuestItemsCount(ROXXY_KERCHIEF) == 0 && st.getQuestItemsCount(DARINGS_LETTER) > 0)
				{
					htmltext = "rapunzel_q0001_01.htm";
					st.takeItems(DARINGS_LETTER, -1);
					st.giveItems(ROXXY_KERCHIEF, 1);
					st.setCond(2);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
				else if(cond == 2 && st.getQuestItemsCount(ROXXY_KERCHIEF) > 0)
					htmltext = "rapunzel_q0001_02.htm";
				else if(cond > 2 && (st.getQuestItemsCount(BAULS_POTION) > 0 || st.getQuestItemsCount(DARINGS_RECEIPT) > 0))
					htmltext = "rapunzel_q0001_03.htm";
				break;
			case BAULRO:
				if(cond == 3 && st.getQuestItemsCount(DARINGS_RECEIPT) == 1)
				{
					htmltext = "baul_q0001_01.htm";
					st.takeItems(DARINGS_RECEIPT, -1);
					st.giveItems(BAULS_POTION, 1);
					st.setCond(4);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
				else if(cond == 4)
					htmltext = "baul_q0001_02.htm";
				break;
		}
		return htmltext;
	}
}