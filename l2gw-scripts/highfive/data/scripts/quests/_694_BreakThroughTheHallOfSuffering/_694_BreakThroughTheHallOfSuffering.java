package quests._694_BreakThroughTheHallOfSuffering;

import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 14.12.2011 14:08:34
 */
public class _694_BreakThroughTheHallOfSuffering extends Quest
{
	// NPC
	private static final int officer_tepios = 32603;
	private static final int time_attack_reward = 32530;

	public _694_BreakThroughTheHallOfSuffering()
	{
		super(694, "_694_BreakThroughTheHallOfSuffering", "Break Through The Hall Of Suffering");
		addStartNpc(officer_tepios);
		addTalkId(officer_tepios, time_attack_reward);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == officer_tepios)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 75 && talker.getLevel() < 83)
					return "officer_tepios_q0694_01.htm";
				if(talker.getLevel() < 75)
					return "officer_tepios_q0694_02.htm";
				if(talker.getLevel() >= 83)
				{
					if(st.getQuestItemsCount(13691) == 0)
						st.giveItems(13691, 1);

					return "officer_tepios_q0694_02a.htm";
				}
			}
			if(st.isStarted() && st.getMemoState() == 2)
				return "officer_tepios_q0694_07.htm";
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
			if(reply == 694)
			{
				if(st.isCreated() && talker.getLevel() >= 75)
				{
					st.setMemoState(2);
					showQuestPage("officer_tepios_q0694_05.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 1)
			{
				int i0 = FieldCycleManager.getStep(3);
				if(i0 == 1 || i0 == 2)
				{
					showPage("officer_tepios_q0694_04.htm", talker);
				}
				else
				{
					showPage("officer_tepios_q0694_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				int i0 = FieldCycleManager.getStep(3);
				if(i0 == 1 || i0 == 2)
				{
					showPage("officer_tepios_q0694_04a.htm", talker);
				}
				else
				{
					showPage("officer_tepios_q0694_03.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == time_attack_reward)
		{
			L2Party party = talker.getParty();
			L2Player c1 = party != null ? party.getPartyLeader() : null;
			if(c1 != null)
			{
				if(c1 != talker)
				{
					if(reply >= 11 && reply <= 20)
					{
						showHtmlFile(talker, "ai_is_time_attack_reward_q0694_12.htm", new String[]{"<?name?>"}, new String[]{c1.getName()}, true);
					}
				}
				else if(reply == 11)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13777, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11a.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
				else if(reply == 12)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13778, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11b.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
				else if(reply == 13)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13779, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11c.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
				else if(reply == 14)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13780, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11d.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
				else if(reply == 15)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13781, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11e.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
				else if(reply == 16)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13782, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11f.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
				else if(reply == 17)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13783, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11g.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
				else if(reply == 18)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13784, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11h.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
				else if(reply == 19)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13785, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11i.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
				else if(reply == 20)
				{
					int i2 = npc.getReflection();
					if(st.isStarted() && st.getInt("ex_1") == 11)
					{
						for(L2Player c0 : party.getPartyMembers())
						{
							if(c0 != null && c0.getReflection() == i2)
							{
								c0.addItem("Quest", 736, 1, npc, true);
								c0.addItem("Quest", 13786, 1, npc, true);
								if(c0.getItemCountByItemId(13691) == 0)
								{
									c0.addItem("Quest", 13691, 1, npc, true);
								}
								QuestState qs = c0.getQuestState(694);
								if(qs != null)
								{
									qs.exitCurrentQuest(true);
									qs.playSound(SOUND_FINISH);
								}
								npc.i_quest0 = 1;
								showQuestPage("ai_is_time_attack_reward_q0694_11j.htm", talker);
							}
						}
					}
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.markRestriction();
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}
}
