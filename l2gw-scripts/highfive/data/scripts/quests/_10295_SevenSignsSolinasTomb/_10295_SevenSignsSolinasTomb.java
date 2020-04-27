package quests._10295_SevenSignsSolinasTomb;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 06.10.11 12:04
 */
public class _10295_SevenSignsSolinasTomb extends Quest
{
	// NPC
	private static final int ssq2_eris_silence = 32792;
	private static final int ssq2_elcardia1_silence = 32787;
	private static final int ssq2_cl1_theme = 32838;
	private static final int ssq2_cl2_theme = 32839;
	private static final int ssq2_cl3_theme = 32840;
	private static final int ssq2_cl4_theme = 32841;
	private static final int ssq2_cl2_tomb_real = 32844;
	private static final int ssq2_cl_teleporter_theme = 32837;
	private static final int ssq2_cl_teleporter_tomb = 32842;
	private static final int ssq2_cl_tel_silence = 32815;
	private static final int ssq2_cl_tomb_fake = 32843;
	private static final int ssq2_solina_silence = 32793;
	private static final int ssq2_solina_past = 32794;
	private static final int ssq2_eris_past = 32795;
	private static final int ssq2_anais_past = 32796;
	private static final int ssq2_judith_past = 32797;
	private static final int ssq2_wand_theme = 32857;
	private static final int ssq2_sword_theme = 32858;
	private static final int ssq2_book_theme = 32859;
	private static final int ssq2_shield_theme = 32860;

	// Mobs
	private static final int ssq2_tomb_guardian1 = 18956;
	private static final int ssq2_tomb_guardian2 = 18957;
	private static final int ssq2_tomb_guardian3 = 18958;
	private static final int ssq2_tomb_guardian4 = 18959;

	// Items
	private static final int q10295_ssq2_hammer = 17228;
	private static final int q10295_ssq2_axe = 17229;
	private static final int q10295_ssq2_sword = 17230;
	private static final int q10295_ssq2_wand = 17231;

	public _10295_SevenSignsSolinasTomb()
	{
		super(10295, "_10295_SevenSignsSolinasTomb", "Seven Signs, Solina's Tomb");
		addStartNpc(ssq2_eris_silence);
		addStartNpc(ssq2_cl_tel_silence);
		addTalkId(ssq2_eris_silence, ssq2_cl_tel_silence, ssq2_solina_silence);
		addTalkId(ssq2_cl1_theme, ssq2_cl2_theme, ssq2_cl3_theme, ssq2_cl4_theme);
		addTalkId(ssq2_cl2_tomb_real, ssq2_cl_tomb_fake, ssq2_cl_teleporter_theme, ssq2_cl_teleporter_tomb);
		addTalkId(ssq2_solina_past, ssq2_eris_past, ssq2_anais_past, ssq2_judith_past);
		addTalkId(ssq2_wand_theme, ssq2_sword_theme, ssq2_book_theme, ssq2_shield_theme);

		addKillId(ssq2_tomb_guardian1, ssq2_tomb_guardian2, ssq2_tomb_guardian3, ssq2_tomb_guardian4);
		addQuestItem(q10295_ssq2_hammer, q10295_ssq2_axe, q10295_ssq2_sword, q10295_ssq2_wand);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == ssq2_eris_silence)
		{
			if(st.isCreated() && talker.isQuestComplete(10294) && talker.getLevel() >= 81)
				return "ssq2_eris_silence_q10295_01.htm";
			if(st.isCompleted())
				return "npchtm:ssq2_eris_silence_q10295_02.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 2)
					return "npchtm:ssq2_eris_silence_q10295_06.htm";
				if(st.getMemoState() > 2 && st.getMemoState() < 6)
					return "npchtm:ssq2_eris_silence_q10295_07.htm";
				if(st.getMemoState() == 6)
				{
					if(talker.isSubClassActive())
						return "npchtm:ssq2_eris_silence_q10295_13.htm";
					st.addExpAndSp(125000000, 12500000);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					return "npchtm:ssq2_eris_silence_q10295_11.htm";
				}
				if(st.getMemoState() == 1)
					return "npchtm:ssq2_eris_silence_q10295_12.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_anais_past)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 4)
					return "npchtm:ssq2_anais_past_q10295_01.htm";
				if(st.getMemoState() == 5)
					return "npchtm:ssq2_anais_past_q10295_02.htm";
			}

		}
		else if(npc.getNpcId() == ssq2_book_theme)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:ssq2_book_theme_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl1_theme)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:ssq2_cl1_theme_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl2_theme)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:ssq2_cl2_theme_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl3_theme)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:ssq2_cl3_theme_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl4_theme)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:ssq2_cl4_theme_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl2_tomb_real)
		{
			if(st.isStarted() && st.getMemoState() > 2)
				return "npchtm:ssq2_cl2_tomb_real_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_teleporter_theme)
		{
			if(st.isStarted() && st.getMemoState() > 1)
			{
				st.takeItems(q10295_ssq2_hammer, -1);
				st.takeItems(q10295_ssq2_axe, -1);
				st.takeItems(q10295_ssq2_sword, -1);
				st.takeItems(q10295_ssq2_wand, -1);
				return "npchtm:ssq2_cl_teleporter_theme_q10295_01.htm";
			}
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:ssq2_cl_teleporter_theme_q10295_03.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_teleporter_tomb)
		{
			if(st.isStarted() && st.getMemoState() > 2)
				return "npchtm:ssq2_cl_teleporter_tomb_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_tel_silence)
		{
			if(!st.isCompleted() && talker.isQuestComplete(10294) && talker.getLevel() >= 81)
				return "npchtm:ssq2_cl_tel_silence_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_tomb_fake)
		{
			if(st.isStarted() && st.getMemoState() == 2)
				return "npchtm:ssq2_cl_tomb_fake_q10295_01.htm";
			if(st.isStarted() && st.getMemoState() > 2)
				return "npchtm:ssq2_cl_tomb_fake_q10295_03.htm";
		}
		else if(npc.getNpcId() == ssq2_anais_past)
		{
			if(st.isStarted() && st.getMemoState() == 4)
				return "npchtm:ssq2_eris_past_q10295_01.htm";
			if(st.isStarted() && st.getMemoState() == 5)
				return "npchtm:ssq2_eris_past_q10295_02.htm";
		}
		else if(npc.getNpcId() == ssq2_judith_past)
		{
			if(st.isStarted() && st.getMemoState() == 4)
				return "npchtm:ssq2_judith_past_q10295_01.htm";
			if(st.isStarted() && st.getMemoState() == 5)
				return "npchtm:ssq2_judith_past_q10295_02.htm";
		}
		else if(npc.getNpcId() == ssq2_shield_theme)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:ssq2_shield_theme_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_solina_past)
		{
			if(st.isStarted() && st.getMemoState() == 4)
				return "npchtm:ssq2_solina_past_q10295_01.htm";
			if(st.isStarted() && st.getMemoState() == 5)
				return "npchtm:ssq2_solina_past_q10295_03.htm";
		}
		else if(npc.getNpcId() == ssq2_sword_theme)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:ssq2_sword_theme_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_wand_theme)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:ssq2_wand_theme_q10295_01.htm";
		}
		else if(npc.getNpcId() == ssq2_solina_silence)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 3)
					return "npchtm:ssq2_solina_silence_q10295_01.htm";
				if(st.getMemoState() == 4)
					return "npchtm:ssq2_solina_silence_q10295_05.htm";
				if(st.getMemoState() == 5)
					return "npchtm:ssq2_solina_silence_q10295_07.htm";
				if(st.getMemoState() == 6)
					return "npchtm:ssq2_solina_silence_q10295_10.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == ssq2_eris_silence)
		{
			if(reply == 10295)
			{
				if(st.isCreated() && talker.isQuestComplete(10294) && talker.getLevel() >= 81)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("ssq2_eris_silence_q10295_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.isQuestComplete(10294) && talker.getLevel() >= 81)
				{
					showQuestPage("ssq2_eris_silence_q10295_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				talker.teleToLocation(45545, -249423, -6788);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(45545, -249423, -6788);
					npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90312, 0, null);
				}
			}
			else if(reply == 3)
			{
				talker.teleToLocation(56033, -252944, -6792);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(56033, -252944, -6792);
				}
			}
			else if(reply == 4)
			{
				talker.teleToLocation(55955, -250394, -6792);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(55955, -250394, -6792);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_book_theme)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					if(st.getQuestItemsCount(q10295_ssq2_hammer) >= 1)
					{
						showPage("ssq2_book_theme_q10295_02.htm", talker);
					}
					else
					{
						st.giveItems(q10295_ssq2_hammer, 1);
						showPage("ssq2_book_theme_q10295_03.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl1_theme)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					if(st.getQuestItemsCount(q10295_ssq2_hammer) >= 1)
					{
						showPage("ssq2_cl1_theme_q10295_02.htm", talker);
					}
					else
					{
						showPage("ssq2_cl1_theme_q10295_04.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl2_theme)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					if(st.getQuestItemsCount(q10295_ssq2_axe) >= 1)
					{
						showPage("ssq2_cl2_theme_q10295_02.htm", talker);
					}
					else
					{
						showPage("ssq2_cl2_theme_q10295_04.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl3_theme)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					if(st.getQuestItemsCount(q10295_ssq2_sword) >= 1)
					{
						showPage("ssq2_cl3_theme_q10295_02.htm", talker);
					}
					else
					{
						showPage("ssq2_cl3_theme_q10295_04.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl4_theme)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					if(st.getQuestItemsCount(q10295_ssq2_wand) >= 1)
					{
						showPage("ssq2_cl4_theme_q10295_02.htm", talker);
					}
					else
					{
						showPage("ssq2_cl4_theme_q10295_04.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl2_tomb_real)
		{
			if(reply == 1)
			{
				talker.teleToLocation(120717, -86879, -3424);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(120717, -86879, -3424);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_teleporter_theme)
		{
			if(reply == 1)
			{
				talker.teleToLocation(120717, -86879, -3424);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(120717, -86879, -3424);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() > 1)
				{
					st.takeItems(q10295_ssq2_hammer, -1);
					st.takeItems(q10295_ssq2_axe, -1);
					st.takeItems(q10295_ssq2_sword, -1);
					st.takeItems(q10295_ssq2_wand, -1);
					showPage("ssq2_cl_teleporter_theme_q10295_02.htm", talker);
				}
			}
			else if(reply == 3)
			{
				talker.teleToLocation(56033, -252944, -6792);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(56033, -252944, -6792);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_teleporter_tomb)
		{
			if(reply == 1)
			{
				talker.teleToLocation(55955, -250394, -6792);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(55955, -250394, -6792);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_tel_silence)
		{
			if(reply == 1)
			{
				InstanceManager.enterInstance(151, talker, npc, 0);
			}
		}
		else if(npc.getNpcId() == ssq2_cl_tomb_fake)
		{
			if(reply == 1)
			{
				Instance inst = npc.getInstanceZone();
				if(inst != null)
				{
					inst.openCloseDoor("ssq2_tomb_door_1", 0);
					inst.openCloseDoor("ssq2_tomb_door_2", 0);
					inst.openCloseDoor("ssq2_tomb_door_3", 0);
					inst.openCloseDoor("ssq2_tomb_door_4", 0);
				}

				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_tomb_guardian1);
				if(c0 != null)
				{
					npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90208, 0, null);
				}

				c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_tomb_guardian2);
				if(c0 != null)
				{
					npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90208, 0, null);
				}

				c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_tomb_guardian3);
				if(c0 != null)
				{
					npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90208, 0, null);
				}

				c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_tomb_guardian4);
				if(c0 != null)
				{
					npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90208, 0, null);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_shield_theme)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					if(st.getQuestItemsCount(q10295_ssq2_axe) >= 1)
					{
						showPage("ssq2_shield_theme_q10295_02.htm", talker);
					}
					else
					{
						st.giveItems(q10295_ssq2_axe, 1);
						showPage("ssq2_shield_theme_q10295_03.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ssq2_solina_past)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 4)
				{
					st.setMemoState(5);
					showPage("ssq2_solina_past_q10295_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_sword_theme)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					if(st.getQuestItemsCount(q10295_ssq2_sword) >= 1)
					{
						showPage("ssq2_sword_theme_q10295_02.htm", talker);
					}
					else
					{
						st.giveItems(q10295_ssq2_sword, 1);
						showPage("ssq2_sword_theme_q10295_03.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ssq2_wand_theme)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					if(st.getQuestItemsCount(q10295_ssq2_wand) >= 1)
					{
						showPage("ssq2_wand_theme_q10295_02.htm", talker);
					}
					else
					{
						st.giveItems(q10295_ssq2_wand, 1);
						showPage("ssq2_wand_theme_q10295_03.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ssq2_solina_silence)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 3)
				{
					showPage("ssq2_solina_silence_q10295_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 3)
				{
					showPage("ssq2_solina_silence_q10295_03.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 3)
				{
					st.setMemoState(4);
					showPage("ssq2_solina_silence_q10295_04.htm", talker);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 4)
				{
					st.setMemoState(5);
					showPage("ssq2_solina_silence_q10295_06.htm", talker);
				}
			}
			else if(reply == 5)
			{
				talker.teleToLocation(56231, -239347, -7257);
				//i0 = myself.GetGlobalMap(80008);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(56231, -239347, -7257);
				}
			}
			else if(reply == 6)
			{
				if(st.isStarted() && st.getMemoState() == 5)
				{
					showPage("ssq2_solina_silence_q10295_08.htm", talker);
				}
			}
			else if(reply == 7)
			{
				if(st.isStarted() && st.getMemoState() == 5)
				{
					st.setMemoState(6);
					showPage("ssq2_solina_silence_q10295_09.htm", talker);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 8)
			{
				talker.teleToLocation(120717, -86879, -3424);
				//i0 = myself.GetGlobalMap(80008);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(120717, -86879, -3424);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia1_silence)
		{
			if(reply == 1)
			{
				talker.teleToLocation( 55955, -250394, -6792);
				//int i0 = ServerVariables.getInt("GM_" + 80008);
				npc.teleToLocation( 55955, -250394, -6792);
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance killed, L2Player killer)
	{
		if(killed.getNpcId() >= ssq2_tomb_guardian1 && killed.getNpcId() <= ssq2_tomb_guardian4)
		{
			L2NpcInstance npc = InstanceManager.getInstance().getNpcById(killed, ssq2_cl_tomb_fake);
			if(npc != null)
			{
				npc.i_ai0++;
				if(npc.i_ai0 >= 4)
				{
					L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
					if(c0 != null)
					{
						npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90207, 0, null);
					}

					Instance inst = npc.getInstanceZone();
					if(inst != null)
					{
						inst.openCloseDoor("ssq2_tomb_r_door_1", 0);
					}
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
