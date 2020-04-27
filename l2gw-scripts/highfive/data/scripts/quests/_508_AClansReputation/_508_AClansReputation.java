package quests._508_AClansReputation;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 22.01.12 14:50
 */
public class _508_AClansReputation extends Quest
{
	// NPC
	private static final int sir_eric_rodemai = 30868;

	// Mobs
	private static final int flame_stone_golem = 25524;
	private static final int gargoyle_lord_tiphon = 25255;
	private static final int last_lesser_glaki = 25245;
	private static final int palibati_queen_themis = 25252;
	private static final int priest_hisilrome = 25478;
	private static final int rahha = 25051;

	public _508_AClansReputation()
	{
		super(508, "_508_AClansReputation", "A Clan's Reputation");
		addStartNpc(sir_eric_rodemai);
		addTalkId(sir_eric_rodemai);

		addKillId(flame_stone_golem, gargoyle_lord_tiphon, last_lesser_glaki, palibati_queen_themis, priest_hisilrome, rahha);
		addQuestItem(8277, 8279, 8280, 8281, 8282, 8494, 14883);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == sir_eric_rodemai)
		{
			if(st.isCreated())
			{
				if(talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					if(clan != null)
					{
						if(clan.getLevel() >= 5)
							return "sir_eric_rodemai_q0508_01.htm";

						return "sir_eric_rodemai_q0508_02.htm";
					}
				}
				else
					return "sir_eric_rodemai_q0508_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 0 && talker.isClanLeader())
					return "npchtm:sir_eric_rodemai_q0508_05.htm";
				if(!talker.isClanLeader())
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:sir_eric_rodemai_q0508_05a.htm";
				}
				if(st.getMemoState() == 2 && st.getQuestItemsCount(8277) == 0 && talker.isClanLeader())
					return "npchtm:sir_eric_rodemai_q0508_18.htm";
				if(st.getMemoState() == 2 && st.getQuestItemsCount(8277) >= 1 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(560, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "560");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8277, -1);
					st.setMemoState(0);
					return "npchtm:sir_eric_rodemai_q0508_19.htm";
				}
				if(st.getMemoState() == 4 && st.getQuestItemsCount(14883) == 0 && talker.isClanLeader())
					return "npchtm:sir_eric_rodemai_q0508_22.htm";
				if(st.getMemoState() == 4 && st.getQuestItemsCount(14883) >= 1 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(584, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "584");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(14883, -1);
					st.setMemoState(0);
					return "npchtm:sir_eric_rodemai_q0508_23a.htm";
				}
				if(st.getMemoState() == 4 && st.getQuestItemsCount(8279) >= 1 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(618, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "618");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8279, -1);
					st.setMemoState(0);
					return "npchtm:sir_eric_rodemai_q0508_23.htm";
				}
				if(st.getMemoState() == 5 && st.getQuestItemsCount(8280) == 0 && talker.isClanLeader())
					return "npchtm:sir_eric_rodemai_q0508_24.htm";
				if(st.getMemoState() == 5 && st.getQuestItemsCount(8280) >= 1 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(602, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "602");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8280, -1);
					st.setMemoState(0);
					return "npchtm:sir_eric_rodemai_q0508_25.htm";
				}
				if(st.getMemoState() == 6 && st.getQuestItemsCount(8281) == 0 && talker.isClanLeader())
					return "npchtm:sir_eric_rodemai_q0508_26.htm";
				if(st.getMemoState() == 6 && st.getQuestItemsCount(8281) >= 1 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(784, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "784");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8281, -1);
					st.setMemoState(0);
					return "npchtm:sir_eric_rodemai_q0508_27.htm";
				}
				if(st.getMemoState() == 7 && st.getQuestItemsCount(8282) == 0 && talker.isClanLeader())
					return "npchtm:sir_eric_rodemai_q0508_28.htm";
				if(st.getMemoState() == 7 && st.getQuestItemsCount(8282) >= 1 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(558, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "558");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8282, -1);
					st.setMemoState(0);
					return "npchtm:sir_eric_rodemai_q0508_29.htm";
				}
				if(st.getMemoState() == 8 && st.getQuestItemsCount(8494) == 0 && talker.isClanLeader())
					return "npchtm:sir_eric_rodemai_q0508_30.htm";
				if(st.getMemoState() == 8 && st.getQuestItemsCount(8494) >= 1 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(768, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "768");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8494, -1);
					st.setMemoState(0);
					return "npchtm:sir_eric_rodemai_q0508_31.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == sir_eric_rodemai)
		{
			if(reply == 508)
			{
				if(st.isCreated() && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					if(clan != null && clan.getLevel() >= 5)
					{
						st.setMemoState(0);
						st.setCond(1);
						st.setState(STARTED);
						st.playSound(SOUND_ACCEPT);
						showQuestPage("sir_eric_rodemai_q0508_04.htm", talker);
					}
				}
			}
			else if(reply == 100)
			{
				if(st.isStarted())
				{
					showPage("sir_eric_rodemai_q0508_06.htm", talker);
				}
			}
			else if(reply == 101)
			{
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				showPage("sir_eric_rodemai_q0508_07.htm", talker);
			}
			else if(reply == 102)
			{
				if(st.isStarted())
				{
					st.setMemoState(0);
					showPage("sir_eric_rodemai_q0508_08.htm", talker);
				}
			}
			else if(reply == 110)
			{
				if(talker.isClanLeader())
				{
					L2Clan pledge0 = talker.getClan();
					if(pledge0 != null)
					{
						if(pledge0.getLevel() >= 5)
						{
							showQuestPage("sir_eric_rodemai_q0508_01a.htm", talker);
						}
					}
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(2);
					showPage("sir_eric_rodemai_q0508_10.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(4);
					showPage("sir_eric_rodemai_q0508_12.htm", talker);
				}
			}
			else if(reply == 5)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(5);
					showPage("sir_eric_rodemai_q0508_13.htm", talker);
				}
			}
			else if(reply == 6)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(6);
					showPage("sir_eric_rodemai_q0508_14.htm", talker);
				}
			}
			else if(reply == 7)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(7);
					showPage("sir_eric_rodemai_q0508_15.htm", talker);
				}
			}
			else if(reply == 8)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(8);
					showPage("sir_eric_rodemai_q0508_15a.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Player c0 = Util.getClanLeader(killer);
		if(c0 == null || !npc.isInRange(c0, 1500))
			return;

		QuestState st = c0.getQuestState(508);
		if(st == null || !st.isStarted())
			return;

		if(npc.getNpcId() == flame_stone_golem && st.getMemoState() == 8 && st.getQuestItemsCount(8494) == 0)
		{
			st.giveItems(8494, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npc.getNpcId() == gargoyle_lord_tiphon && st.getMemoState() == 5 && st.getQuestItemsCount(8280) == 0)
		{
			st.giveItems(8280, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npc.getNpcId() == last_lesser_glaki && st.getMemoState() == 6 && st.getQuestItemsCount(8281) == 0)
		{
			st.giveItems(8281, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npc.getNpcId() == palibati_queen_themis && st.getMemoState() == 2 && st.getQuestItemsCount(8277) == 0)
		{
			st.giveItems(8277, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npc.getNpcId() == priest_hisilrome && st.getMemoState() == 4 && st.getQuestItemsCount(14883) == 0)
		{
			st.giveItems(14883, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npc.getNpcId() == rahha && st.getMemoState() == 7 && st.getQuestItemsCount(8282) == 0)
		{
			st.giveItems(8282, 1);
			st.playSound(SOUND_ITEMGET);
		}
	}
}