package quests._308_ReedFieldMaintenance;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 15.08.2010 15:03:08
 */
public class _308_ReedFieldMaintenance extends Quest
{
	// NPCs
	private static final int contract_worker = 32646;

	// Mobs
	private static final int murcrokian_fanatic = 22650;
	private static final int murcrokian_ascetic = 22651;
	private static final int murcrokian_savior = 22652;
	private static final int murcrokian_foreseer = 22653;
	private static final int murcrokian_corrupted = 22654;
	private static final int murcrokian_awakened = 22655;

	public _308_ReedFieldMaintenance()
	{
		super(308, "_308_ReedFieldMaintenance", "Reed Field Maintenance");
		addStartNpc(contract_worker);
		addTalkId(contract_worker);

		addAttackId(murcrokian_fanatic);
		addAttackId(murcrokian_ascetic);
		addAttackId(murcrokian_savior);
		addAttackId(murcrokian_foreseer);
		addAttackId(murcrokian_corrupted);
		addAttackId(murcrokian_awakened);

		addKillId(murcrokian_fanatic);
		addKillId(murcrokian_ascetic);
		addKillId(murcrokian_savior);
		addKillId(murcrokian_foreseer);
		addKillId(murcrokian_corrupted);
		addKillId(murcrokian_awakened);

		addQuestItem(14871, 14872);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == contract_worker)
		{
			if(st.isCreated() && !talker.isQuestStarted(309))
				return "contract_worker_q0308_01.htm";
			if(st.isCreated() && talker.isQuestStarted(309))
				return "contract_worker_q0308_02.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(14871) < 1 && st.getQuestItemsCount(14872) < 1)
					return "npchtm:contract_worker_q0308_09.htm";
				if(st.getMemoState() == 1 && st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) >= 1)
					return "npchtm:contract_worker_q0308_10.htm";
			}
		}
		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == contract_worker)
		{
			if(reply == 308)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && !talker.isQuestStarted(309))
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("contract_worker_q0308_08.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && !talker.isQuestStarted(309))
				{
					showPage("contract_worker_q0308_03.htm", talker);
				}
				else if(st.isCreated() && talker.getLevel() < 82 && !talker.isQuestStarted(309))
				{
					showPage("contract_worker_q0308_04.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && !talker.isQuestStarted(309))
				{
					showQuestPage("contract_worker_q0308_06.htm", talker);
				}
			}
			else if(reply == 10)
			{
				if(st.isStarted() && st.getMemoState() == 1 && (st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872)) >= 1)
				{
					if(talker.isQuestComplete(238))
					{
						showPage("contract_worker_q0308_11.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_12.htm", talker);
					}
				}
			}
			else if(reply == 11)
			{
				if(st.isStarted() && st.getMemoState() == 1 && (st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872)) >= 1 && !talker.isQuestComplete(238))
				{
					showPage("contract_worker_q0308_11a.htm", talker);
				}
			}
			else if(reply == 20)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("contract_worker_q0308_13.htm", talker);
				}
			}
			else if(reply == 30)
			{
				if(st.isStarted() && st.getMemoState() == 1 && (st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872)) >= 1)
				{
					showPage("contract_worker_q0308_14.htm", talker);
				}
				else if(st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(14871) < 1 && st.getQuestItemsCount(14872) < 1)
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("contract_worker_q0308_15.htm", talker);
				}
			}
			else if(reply == 40)
			{
				if(st.isStarted() && st.getMemoState() == 1 && (st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872)) >= 1)
				{
					showPage("contract_worker_q0308_14.htm", talker);
				}
				else if(st.isStarted() && st.getMemoState() == 1 && st.getQuestItemsCount(14871) < 1 && st.getQuestItemsCount(14872) < 1)
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("contract_worker_q0308_15.htm", talker);
				}
			}
			else if(reply == 50)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("contract_worker_q0308_16.htm", talker);
				}
			}
			else if(reply >= 101 && reply <= 106 && st.isStarted() && st.getMemoState() == 1 && (st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872)) >= 1 && talker.isQuestComplete(238))
			{
				if(reply == 101)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 192)
					{
						st.giveItems(9985, 1);
						if(st.getQuestItemsCount(14872) >= 96)
						{
							st.takeItems(14872, 96);
						}
						else
						{
							long i0 = (192 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 102)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 256)
					{
						st.giveItems(9986, 1);
						if(st.getQuestItemsCount(14872) >= 128)
						{
							st.takeItems(14872, 128);
						}
						else
						{
							long i0 = (256 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 103)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 128)
					{
						st.giveItems(9987, 1);
						if(st.getQuestItemsCount(14872) >= 64)
						{
							st.takeItems(14872, 64);
						}
						else
						{
							long i0 = (128 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 104)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 206)
					{
						st.giveItems(10115, 1);
						if(st.getQuestItemsCount(14872) >= 103)
						{
							st.takeItems(14872, 103);
						}
						else
						{
							long i0 = (206 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 105)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 180)
					{
						int i0 = Rnd.get(9);
						switch(i0)
						{
							case 0:
								st.giveItems(15777, 1);
								break;
							case 1:
								st.giveItems(15780, 1);
								break;
							case 2:
								st.giveItems(15783, 1);
								break;
							case 3:
								st.giveItems(15786, 1);
								break;
							case 4:
								st.giveItems(15789, 1);
								break;
							case 5:
								st.giveItems(15790, 1);
								break;
							case 6:
								st.giveItems(15812, 1);
								break;
							case 7:
								st.giveItems(15813, 1);
								break;
							case 8:
								st.giveItems(15814, 1);
								break;
						}
						if(st.getQuestItemsCount(14872) >= 90)
						{
							st.takeItems(14872, 90);
						}
						else
						{
							i0 = (int) (180 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 106)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 100)
					{
						for(int i1 = 0; i1 < 5; i1++)
						{
							int i0 = Rnd.get(9);
							switch(i0)
							{
								case 0:
									st.giveItems(15647, 1);
									break;
								case 1:
									st.giveItems(15650, 1);
									break;
								case 2:
									st.giveItems(15653, 1);
									break;
								case 3:
									st.giveItems(15656, 1);
									break;
								case 4:
									st.giveItems(15659, 1);
									break;
								case 5:
									st.giveItems(15692, 1);
									break;
								case 6:
									st.giveItems(15772, 1);
									break;
								case 7:
									st.giveItems(15773, 1);
									break;
								case 8:
									st.giveItems(15774, 1);
									break;
							}
						}
						if(st.getQuestItemsCount(14872) >= 50)
						{
							st.takeItems(14872, 50);
						}
						else
						{
							long i0 = (100 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
			}
			else if(reply >= 201 && reply <= 206 && st.isStarted() && st.getMemoState() == 1 && (st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872)) >= 1 && !talker.isQuestComplete(238))
			{
				if(reply == 201)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 230)
					{
						st.giveItems(9985, 1);
						if(st.getQuestItemsCount(14872) >= 105)
						{
							st.takeItems(14872, 105);
						}
						else
						{
							long i0 = (230 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 202)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 308)
					{
						st.giveItems(9986, 1);
						if(st.getQuestItemsCount(14872) >= 154)
						{
							st.takeItems(14872, 154);
						}
						else
						{
							long i0 = (308 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 203)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 154)
					{
						st.giveItems(9987, 1);
						if(st.getQuestItemsCount(14872) >= 77)
						{
							st.takeItems(14872, 77);
						}
						else
						{
							long i0 = (154 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 204)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 248)
					{
						st.giveItems(10115, 1);
						if(st.getQuestItemsCount(14872) >= 124)
						{
							st.takeItems(14872, 124);
						}
						else
						{
							long i0 = (248 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 205)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 216)
					{
						int i0 = Rnd.get(9);
						switch(i0)
						{
							case 0:
								st.giveItems(15777, 1);
								break;
							case 1:
								st.giveItems(15780, 1);
								break;
							case 2:
								st.giveItems(15783, 1);
								break;
							case 3:
								st.giveItems(15786, 1);
								break;
							case 4:
								st.giveItems(15789, 1);
								break;
							case 5:
								st.giveItems(15790, 1);
								break;
							case 6:
								st.giveItems(15812, 1);
								break;
							case 7:
								st.giveItems(15813, 1);
								break;
							case 8:
								st.giveItems(15814, 1);
								break;
						}
						if(st.getQuestItemsCount(14872) >= 108)
						{
							st.takeItems(14872, 108);
						}
						else
						{
							i0 = (int) (216 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
				else if(reply == 206)
				{
					if(st.getQuestItemsCount(14871) + st.getQuestItemsCount(14872) * 2 >= 120)
					{
						for(int i1 = 0; i1 < 5; i1++)
						{
							int i0 = Rnd.get(9);
							switch(i0)
							{
								case 0:
									st.giveItems(15647, 1);
									break;
								case 1:
									st.giveItems(15650, 1);
									break;
								case 2:
									st.giveItems(15653, 1);
									break;
								case 3:
									st.giveItems(15656, 1);
									break;
								case 4:
									st.giveItems(15659, 1);
									break;
								case 5:
									st.giveItems(15692, 1);
									break;
								case 6:
									st.giveItems(15772, 1);
									break;
								case 7:
									st.giveItems(15773, 1);
									break;
								case 8:
									st.giveItems(15774, 1);
									break;
							}
						}
						if(st.getQuestItemsCount(14872) >= 60)
						{
							st.takeItems(14872, 60);
						}
						else
						{
							long i0 = (120 - (st.getQuestItemsCount(14872) * 2));
							st.takeItems(14872, st.getQuestItemsCount(14872));
							st.takeItems(14871, i0);
						}

						showPage("contract_worker_q0308_20.htm", talker);
					}
					else
					{
						showPage("contract_worker_q0308_21.htm", talker);
					}
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithMemoState(killer, 1);
		if(st != null)
		{
			int npcId = npc.getNpcId();
			switch(npcId)
			{
				case murcrokian_ascetic:
					st.rollAndGive(14871, Rnd.chance(25.8) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_awakened:
					st.rollAndGive(14872, Rnd.chance(12.4) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_corrupted:
					st.rollAndGive(14871, 1, 22);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_fanatic:
					st.rollAndGive(14871, Rnd.chance(21.8) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_foreseer:
					st.rollAndGive(14871, Rnd.chance(29.0) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_savior:
					st.rollAndGive(14871, Rnd.chance(24.8) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
			}
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		L2Player attacker = st.getPlayer();
		if(npc.getLevel() - attacker.getLevel() <= 5 && attacker.getAbnormalLevelByType(2900) > 0)
		{
			switch(npc.getNpcId())
			{
				case murcrokian_ascetic:
					st.rollAndGive(14873, Rnd.chance(25.8) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_awakened:
					st.rollAndGive(14871, Rnd.chance(12.4) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_corrupted:
					st.rollAndGive(14871, Rnd.chance(22.0) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_fanatic:
					st.rollAndGive(14871, Rnd.chance(21.8) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_foreseer:
					st.rollAndGive(14871, Rnd.chance(29.0) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
				case murcrokian_savior:
					st.rollAndGive(14871, Rnd.chance(24.8) ? 2 : 1, 100);
					st.playSound(SOUND_ITEMGET);
					break;
			}
		}
		return null;
	}
}

