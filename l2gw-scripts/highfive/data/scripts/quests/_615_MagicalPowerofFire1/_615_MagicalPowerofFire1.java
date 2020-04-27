package quests._615_MagicalPowerofFire1;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 29.01.12 12:15
 */
public class _615_MagicalPowerofFire1 extends Quest
{
	// NPC
	private static final int herald_naran = 31378;
	private static final int shaman_udan = 31379;
	private static final int asefas_box = 31559;
	private static final int asefas_eye = 31685;

	// Mobs
	private static final int[] ketra_mobs = new int[]{21344, 21346, 21345, 21332, 21336, 21335, 21324, 21343, 21334,
			21339, 21342, 21340, 21328, 21338, 21329, 21327, 21331, 21347, 21325, 21349, 21348};
	private static final int[] varka_mobs = new int[]{21375, 21374, 25315, 25312, 21370, 25309, 21372, 21371, 21365,
			21351, 21369, 21350, 21354, 21361, 21360, 21366, 21368, 21357, 21353, 21364, 21362, 21355, 21358, 21373,
			25316};

	public _615_MagicalPowerofFire1()
	{
		super();
		addStartNpc(herald_naran);
		addTalkId(herald_naran, shaman_udan, asefas_box);

		addAttackId(ketra_mobs);
		addKillId(varka_mobs);
		addKillId(asefas_eye);
		addQuestItem(7242);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == herald_naran)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 74)
				{
					if(st.getQuestItemsCount(7221) > 0 || st.getQuestItemsCount(7222) > 0 || st.getQuestItemsCount(7223) > 0 || st.getQuestItemsCount(7224) > 0 || st.getQuestItemsCount(7225) > 0)
						return "herald_naran_q0615_01.htm";

					return "herald_naran_q0615_01a.htm";
				}

				return "herald_naran_q0615_01b.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:herald_naran_q0615_03.htm";
			}
		}
		else if(npc.getNpcId() == shaman_udan)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					st.setMemoState(2);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:shaman_udan_q0615_01.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:shaman_udan_q0615_02.htm";
				if(st.getMemoState() == 3)
				{
					npc.altUseSkill(SkillTable.getInstance().getInfo(298057729), talker);
					st.setMemoState(2);
					return "npchtm:shaman_udan_q0615_03.htm";
				}
				if(st.getMemoState() == 4)
				{
					st.giveItems(7243, 1);
					st.giveItems(7081, 1);
					st.takeItems(7242, -1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:shaman_udan_q0615_04.htm";
				}
			}
		}
		else if(npc.getNpcId() == asefas_box)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 2 || st.getMemoState() == 3)
					return "npchtm:asefas_box_q0615_01.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == herald_naran)
		{
			if(reply == 615)
			{
				if(st.isCreated() && talker.getLevel() >= 74)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("herald_naran_q0615_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == asefas_box)
		{
			if(reply == 1)
			{
				if(st.getQuestItemsCount(1661) < 1)
				{
					showPage("asefas_box_q0615_02.htm", talker);
				}
				else if(st.getQuestItemsCount(1661) >= 1 && st.getMemoState() == 2)
				{
					showPage("asefas_box_q0615_03.htm", talker);
					npc.altUseSkill(SkillTable.getInstance().getInfo(298057729), talker);
					st.giveItems(7242, 1);
					st.takeItems(1661, 1);
					st.setMemoState(4);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
				else if(st.getQuestItemsCount(1661) >= 1 && st.getMemoState() == 3)
				{
					showPage("asefas_box_q0615_04.htm", talker);
					st.takeItems(1661, 1);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == asefas_eye)
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, -1);
			if(st != null)
			{
				Functions.npcSay(npc, Say2C.ALL, 61504);
			}
		}
		else if(contains(varka_mobs, npc.getNpcId()))
		{
			QuestState st = killer.getQuestState(615);
			if(st != null)
			{
				st.takeItems(7242, -1);
				st.exitCurrentQuest(true);
			}
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState qs, L2Skill skill)
	{
		if(qs.getMemoState() == 2 && contains(ketra_mobs, npc.getNpcId()))
		{
			qs.setMemoState(3);
			npc.createOnePrivate(asefas_eye, "AsefasEye", 0, 0, npc.getX(), npc.getY(), npc.getZ(), 0, 0, 0, 0);
			npc.altUseSkill(SkillTable.getInstance().getInfo(297992193), qs.getPlayer());
		}
		return null;
	}
}