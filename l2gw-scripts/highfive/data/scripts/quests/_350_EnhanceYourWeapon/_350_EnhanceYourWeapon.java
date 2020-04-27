package quests._350_EnhanceYourWeapon;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.arrays.GArray;

public class _350_EnhanceYourWeapon extends Quest
{
	private static final int RED_SOUL_CRYSTAL0_ID = 4629;
	private static final int GREEN_SOUL_CRYSTAL0_ID = 4640;
	private static final int BLUE_SOUL_CRYSTAL0_ID = 4651;
	static final SystemMessage THE_SOUL_CRYSTALS_CAUSED_RESONATION_AND_FAILED_AT_ABSORBING_A_SOUL = new SystemMessage(SystemMessage.THE_SOUL_CRYSTALS_CAUSED_RESONATION_AND_FAILED_AT_ABSORBING_A_SOUL);
	static final SystemMessage THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_A_SOUL = new SystemMessage(SystemMessage.THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_A_SOUL);
	static final SystemMessage THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_A_SOUL = new SystemMessage(SystemMessage.THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_A_SOUL);
	static final SystemMessage THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL = new SystemMessage(SystemMessage.THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
	static final SystemMessage YOU_CANT_ABSORB_SOULS_WITHOUT_A_SOUL_STONE = new SystemMessage(SystemMessage.YOU_CANT_ABSORB_SOULS_WITHOUT_A_SOUL_STONE);
	private static final int baylor = 29099;

	private static final int[][] SOUL_CRYSTALS =
			{       // 0     1     2     3     4     5     6     7     8     9    10    11    12    13    14     15     16     17     18
					{4629, 4630, 4631, 4632, 4633, 4634, 4635, 4636, 4637, 4638, 4639, 5577, 5580, 5908, 9570, 10480, 13071, 15541, 15826}, // Red
					{4640, 4641, 4642, 4643, 4644, 4645, 4646, 4647, 4648, 4649, 4650, 5578, 5581, 5911, 9572, 10482, 13073, 15543, 15828}, // Green
					{4651, 4652, 4653, 4654, 4655, 4656, 4657, 4658, 4659, 4660, 4661, 5579, 5582, 5914, 9571, 10481, 13072, 15542, 15827}  // Blue
			};

	public _350_EnhanceYourWeapon()
	{
		super(350, "_350_EnhanceYourWeapon", "Enhance Your Weapon");
		addStartNpc(30115);
		addStartNpc(30194);
		addStartNpc(30856);
		addTalkId(30115, 30194, 30856);
		addKillId(baylor);
		int c = 1;
		for(L2NpcTemplate npcTemplate : NpcTable.getAll())
			if(npcTemplate.getAIParams() != null)
				if(npcTemplate.getAIParams().getInteger("SA_absorb", 0) > 0)
				{
					addKillId(npcTemplate.getNpcId());
					if(npcTemplate.getAIParams().getInteger("SA_absorb", 0) == 3)
						addAttackId(npcTemplate.getNpcId());
					c++;
				}
		_log.info("Loaded: " + c + " Soul Crystal absorb Raid Bosses and monsters.");
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.endsWith("03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		// When we give 0-stage crystal to player, we should remember we did it.
		else if(event.endsWith("08.htm"))
		{
			st.set("sa_received", "1");
			st.giveItems(RED_SOUL_CRYSTAL0_ID, 1);
		}
		else if(event.endsWith("09.htm"))
		{
			st.set("sa_received", "1");
			st.giveItems(GREEN_SOUL_CRYSTAL0_ID, 1);
		}
		else if(event.endsWith("10.htm"))
		{
			st.set("sa_received", "1");
			st.giveItems(BLUE_SOUL_CRYSTAL0_ID, 1);
		}
		else if(event.endsWith("14.htm"))
		{
			st.set("sa_received", "0");
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext;
		String prefix;
		switch(npc.getNpcId())
		{
			case 30115:
				prefix = "jurek_q0350_";
				break;
			case 30194:
				prefix = "guyder_q0350_";
				break;
			case 30856:
				prefix = "magister_winonin_q0350_";
				break;
			default:
				prefix = "jurek_q0350_";
				break;
		}

		if(st.getPlayer().getLevel() < 40)
		{
			htmltext = prefix + "01.htm";
			st.exitCurrentQuest(true);
		}
		else if(st.getInt("cond") != 1)
			htmltext = prefix + "02.htm";
		else if(st.getInt("sa_received") == 0)
			htmltext = prefix + "03.htm";
		else if(st.getInt("sa_received") == 1 && getSACount(st) == 0)
			htmltext = prefix + "13.htm";
		else if(getPlayerMaxSoulCrystalLevel(st) < 10)
			htmltext = prefix + "11.htm";
		else if(getPlayerMaxSoulCrystalLevel(st) == 13)
			htmltext = prefix + "11e.htm";
		else
			htmltext = prefix + "11a.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> pq = getPartyMembersWithQuest(killer, 1);

		if(pq.isEmpty())
			return;

		if(npc.getNpcId() == baylor)
		{
			int chance = Rnd.get(1000);
			for(QuestState st : pq)
			{
				int sa_count = getSACount(st);
				if(sa_count == 1)
				{
					if(chance < 2)
					{
						if(st.getQuestItemsCount(SOUL_CRYSTALS[0][13]) == 1)
						{
							st.takeItems(SOUL_CRYSTALS[0][13], 1);
							st.giveItems(SOUL_CRYSTALS[0][14], 1);
							st.playSound(SOUND_ITEMGET);
							st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
							Functions.broadcastSystemMessageFStr(npc, 1500, 35052, st.getPlayer().getName(), "14");
						}
						else if(st.getQuestItemsCount(SOUL_CRYSTALS[1][13]) == 1)
						{
							st.takeItems(SOUL_CRYSTALS[1][13], 1);
							st.giveItems(SOUL_CRYSTALS[1][14], 1);
							st.playSound(SOUND_ITEMGET);
							st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
							Functions.broadcastSystemMessageFStr(npc, 1500, 35053, st.getPlayer().getName(), "14");
						}
						else if(st.getQuestItemsCount(SOUL_CRYSTALS[2][13]) == 1)
						{
							st.takeItems(SOUL_CRYSTALS[2][13], 1);
							st.giveItems(SOUL_CRYSTALS[2][14], 1);
							st.playSound(SOUND_ITEMGET);
							st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
							Functions.broadcastSystemMessageFStr(npc, 1500, 35051, st.getPlayer().getName(), "14");
						}
						else if(st.getQuestItemsCount(9571) >= 1 || st.getQuestItemsCount(10161) >= 1 || st.getQuestItemsCount(9570) >= 1 || st.getQuestItemsCount(10160) >= 1 ||
								st.getQuestItemsCount(9572) >= 1 || st.getQuestItemsCount(10162) >= 1 || st.getQuestItemsCount(10481) >= 1 || st.getQuestItemsCount(10480) >= 1 ||
								st.getQuestItemsCount(10482) >= 1)
						{
							st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_A_SOUL);
						}
						else
						{
							st.getPlayer().sendPacket(new SystemMessage(1264));
						}
					}
					else if(st.getQuestItemsCount(SOUL_CRYSTALS[0][13]) == 1)
					{
						st.takeItems(SOUL_CRYSTALS[0][13], 1);
						st.giveItems(10160, 1);
						st.playSound(SOUND_ITEMGET);
						st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
						Functions.broadcastSystemMessageFStr(npc, 1500, 35055, st.getPlayer().getName(), "14");
					}
					else if(st.getQuestItemsCount(SOUL_CRYSTALS[1][13]) == 1)
					{
						st.takeItems(SOUL_CRYSTALS[1][13], 1);
						st.giveItems(10162, 1);
						st.playSound(SOUND_ITEMGET);
						st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
						Functions.broadcastSystemMessageFStr(npc, 1500, 35056, st.getPlayer().getName(), "14");
					}
					else if(st.getQuestItemsCount(SOUL_CRYSTALS[2][13]) == 1)
					{
						st.takeItems(SOUL_CRYSTALS[2][13], 1);
						st.giveItems(10161, 1);
						st.playSound(SOUND_ITEMGET);
						st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
						Functions.broadcastSystemMessageFStr(npc, 1500, 35054, st.getPlayer().getName(), "14");
					}
					else if(st.getQuestItemsCount(9571) >= 1 || st.getQuestItemsCount(10161) >= 1 || st.getQuestItemsCount(9570) >= 1 || st.getQuestItemsCount(10160) >= 1 ||
							st.getQuestItemsCount(9572) >= 1 || st.getQuestItemsCount(10162) >= 1 || st.getQuestItemsCount(10481) >= 1 || st.getQuestItemsCount(10480) >= 1 ||
							st.getQuestItemsCount(10482) >= 1)
					{
						st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_A_SOUL);
					}
					else
					{
						st.getPlayer().sendPacket(new SystemMessage(1264));
					}
				}
				else if(sa_count >= 2)
				{
					st.getPlayer().sendPacket(THE_SOUL_CRYSTALS_CAUSED_RESONATION_AND_FAILED_AT_ABSORBING_A_SOUL);
				}
				else
				{
					st.getPlayer().sendPacket(YOU_CANT_ABSORB_SOULS_WITHOUT_A_SOUL_STONE);
				}
			}
		}
		else if(npc.getAIParams() != null)
		{
			int at = npc.getAIParams().getInteger("SA_absorb", 0);
			if(at == 1)
			{
				upCrystalsForOne(npc, pq, 11, 18);
			}
			else if(at == 2)
			{
				int min = 18;
				int max = 11;
				for(int sa_level = 11; sa_level < 19; sa_level++)
				{
					if(npc.getAIParams().getInteger("SA_level_" + sa_level, 0) > 0 && npc.getAIParams().getInteger("SA_absorb_" + sa_level, 0) == 1)
					{
						if(min > sa_level)
							min = sa_level;
						if(max < sa_level)
							max = sa_level;
					}
				}
				_log.info(npc + " SA level up for one: " + min + " " + max + " chance: " + npc.getAIParams().getInteger("SA_chance_" + min, 0));
				if(min <= max)
					upCrystalsForOne(npc, pq, min, max);

				min = 18;
				max = 11;
				for(int sa_level = 11; sa_level < 19; sa_level++)
				{
					if(npc.getAIParams().getInteger("SA_level_" + sa_level, 0) > 0 && npc.getAIParams().getInteger("SA_absorb_" + sa_level, 0) == 0)
					{
						if(min > sa_level)
							min = sa_level;
						if(max < sa_level)
							max = sa_level;
					}
				}

				_log.info(npc + " SA level up for party: " + min + " " + max);
				upCrystalsForParty(npc, pq, min, max);
			}
			else if(at == 3)
			{
				L2Player player = L2ObjectsStorage.getAsPlayer(npc.c_ai0);
				QuestState st;
				if(player != null && npc.isInRange(player, 1500) && npc.i_quest0 == 2 && (st = player.getQuestState(350)) != null)
				{
					int sa = getSACount(st);
					if(sa == 1)
					{
						int sa_level = getPlayerMaxSoulCrystalLevel(st);
						int chance = npc.getTemplate().getAIParams().getInteger("SA_level_" + sa_level, 0);
						if(chance > 0)
						{
							if(Rnd.chance(chance))
							{
								if(st.getQuestItemsCount(SOUL_CRYSTALS[0][sa_level]) == 1)
								{
									st.takeItems(SOUL_CRYSTALS[0][sa_level], 1);
									st.giveItems(SOUL_CRYSTALS[0][sa_level + 1], 1);
									st.playSound(SOUND_ITEMGET);
									player.sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
								}
								else if(st.getQuestItemsCount(SOUL_CRYSTALS[1][sa_level]) == 1)
								{
									st.takeItems(SOUL_CRYSTALS[1][sa_level], 1);
									st.giveItems(SOUL_CRYSTALS[1][sa_level + 1], 1);
									st.playSound(SOUND_ITEMGET);
									player.sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
								}
								else if(st.getQuestItemsCount(SOUL_CRYSTALS[2][sa_level]) == 1)
								{
									st.takeItems(SOUL_CRYSTALS[2][sa_level], 1);
									st.giveItems(SOUL_CRYSTALS[2][sa_level + 1], 1);
									st.playSound(SOUND_ITEMGET);
									player.sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
								}
								else
								{
									player.sendPacket(THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_A_SOUL);
								}
							}
							else
							{
								player.sendPacket(THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_A_SOUL);
							}
						}
						else
						{
							player.sendPacket(THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_A_SOUL);
						}
					}
					else if(sa > 1)
					{
						player.sendPacket(THE_SOUL_CRYSTALS_CAUSED_RESONATION_AND_FAILED_AT_ABSORBING_A_SOUL);
					}
				}
				npc.i_quest0 = 0;
				npc.c_ai0 = 0;
			}
		}
	}

	private static int getSACount(QuestState st)
	{
		int count = 0;
		for(int[] colors : SOUL_CRYSTALS)
			for(int sa : colors)
				count += st.getQuestItemsCount(sa);

		return count;
	}

	private static int getPlayerMaxSoulCrystalLevel(QuestState st)
	{
		int maxLvl = 0;
		for(int[] colors : SOUL_CRYSTALS)
			for(int level = 0; level < colors.length; level++)
				if(st.getQuestItemsCount(colors[level]) > 0 && level > maxLvl)
					maxLvl = level;

		return maxLvl;
	}

	private static boolean upCrystalForLevel(L2NpcInstance npc, QuestState st, int sa_level)
	{
		boolean absorbed = false;
		int chance = npc.getAIParams().getInteger("SA_level_" + sa_level, 0);
		if(chance > 0)
		{
			if(st.getQuestItemsCount(SOUL_CRYSTALS[0][sa_level - 1]) == 1)
			{
				if(Rnd.chance(chance))
				{
					st.takeItems(SOUL_CRYSTALS[0][sa_level - 1], 1);
					st.giveItems(SOUL_CRYSTALS[0][sa_level], 1);
					st.playSound(SOUND_ITEMGET);
					st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
					Functions.broadcastSystemMessageFStr(npc, 1500, 35052, st.getPlayer().getName(), String.valueOf(sa_level));
				}
				else
				{
					st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_A_SOUL);
				}
				absorbed = true;
			}
			else if(st.getQuestItemsCount(SOUL_CRYSTALS[1][sa_level - 1]) == 1)
			{
				if(Rnd.chance(chance))
				{
					st.takeItems(SOUL_CRYSTALS[1][sa_level - 1], 1);
					st.giveItems(SOUL_CRYSTALS[1][sa_level], 1);
					st.playSound(SOUND_ITEMGET);
					st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
					Functions.broadcastSystemMessageFStr(npc, 1500, 35053, st.getPlayer().getName(), String.valueOf(sa_level));
				}
				else
				{
					st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_A_SOUL);
				}
				absorbed = true;
			}
			else if(st.getQuestItemsCount(SOUL_CRYSTALS[2][sa_level - 1]) == 1)
			{
				if(Rnd.chance(chance))
				{
					st.takeItems(SOUL_CRYSTALS[2][sa_level - 1], 1);
					st.giveItems(SOUL_CRYSTALS[2][sa_level], 1);
					st.playSound(SOUND_ITEMGET);
					st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_SUCCEEDED_IN_ABSORBING_A_SOUL);
					Functions.broadcastSystemMessageFStr(npc, 1500, 35051, st.getPlayer().getName(), String.valueOf(sa_level));
				}
				else
				{
					st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_WAS_NOT_ABLE_TO_ABSORB_A_SOUL);
				}
				absorbed = true;
			}
		}
		return absorbed;
	}

	private static void upCrystalsForOne(L2NpcInstance npc, GArray<QuestState> pq, int min, int max)
	{
		QuestState st = pq.get(Rnd.get(pq.size()));
		int sa_count = getSACount(st);
		if(sa_count == 1)
		{
			boolean absorbed = false;
			for(int sa_level = min; sa_level <= max; sa_level++)
			{
				if(upCrystalForLevel(npc, st, sa_level))
				{
					absorbed = true;
					break;
				}
			}
			if(!absorbed)
			{
				st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_A_SOUL);
			}
		}
		else if(sa_count >= 2)
		{
			st.getPlayer().sendPacket(THE_SOUL_CRYSTALS_CAUSED_RESONATION_AND_FAILED_AT_ABSORBING_A_SOUL);
		}
		else
		{
			st.getPlayer().sendPacket(YOU_CANT_ABSORB_SOULS_WITHOUT_A_SOUL_STONE);
		}
	}

	private static void upCrystalsForParty(L2NpcInstance npc, GArray<QuestState> pq, int min, int max)
	{
		for(QuestState st : pq)
		{
			int sa_count = getSACount(st);
			if(sa_count == 1)
			{
				boolean absorbed = false;
				for(int sa_level = min; sa_level <= max; sa_level++)
				{
					if(upCrystalForLevel(npc, st, sa_level))
					{
						absorbed = true;
						break;
					}
				}
				if(!absorbed)
				{
					st.getPlayer().sendPacket(THE_SOUL_CRYSTAL_IS_REFUSING_TO_ABSORB_A_SOUL);
				}
			}
			else if(sa_count >= 2)
			{
				st.getPlayer().sendPacket(THE_SOUL_CRYSTALS_CAUSED_RESONATION_AND_FAILED_AT_ABSORBING_A_SOUL);
			}
			else
			{
				st.getPlayer().sendPacket(YOU_CANT_ABSORB_SOULS_WITHOUT_A_SOUL_STONE);
			}
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		if(npc.i_quest0 == 0)
		{
			npc.i_quest0 = 1;
			if(skill != null && skill.getId() == 2096 && npc.getCurrentHp() <= npc.getMaxHp() * 0.5)
			{
				npc.i_quest0 = 2;
				npc.c_ai0 = st.getPlayer().getStoredId();
			}
		}
		else if(npc.i_quest0 == 1 && skill != null && skill.getId() == 2096 && npc.getCurrentHp() <= npc.getMaxHp() * 0.5)
		{
			npc.i_quest0 = 2;
			npc.c_ai0 = st.getPlayer().getStoredId();
		}

		return null;
	}
}
