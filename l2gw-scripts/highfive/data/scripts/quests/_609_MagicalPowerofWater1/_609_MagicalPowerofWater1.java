package quests._609_MagicalPowerofWater1;

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
 * @date: 26.01.12 17:16
 */
public class _609_MagicalPowerofWater1 extends Quest
{
	// NPC
	private static final int herald_wakan = 31371;
	private static final int shaman_asefa = 31372;
	private static final int udans_box = 31561;

	// Mobs
	private static final int ketra_mobs[] = new int[]{25306, 25305, 25302, 21344, 25299, 21346, 21345, 21332, 21336, 21324, 21343, 21334,
			21339, 21342, 21340, 21328, 21338, 21329, 21327, 21331, 21347, 21325, 21349, 21348};
	private static final int varka_mobs[] = new int[]{21375, 21374, 21370, 21372, 21371, 21365, 21351, 21369, 21350, 21354, 21361, 21360,
			21366, 21368, 21357, 21353, 21364, 21362, 21355, 21358, 21373};
	private static final int udans_eye = 31684;

	public _609_MagicalPowerofWater1()
	{
		super(609, "_609_MagicalPowerofWater1", "Magical Power of Water part 1");
		addStartNpc(herald_wakan);
		addTalkId(herald_wakan, shaman_asefa, udans_box);

		addKillId(ketra_mobs);
		addKillId(udans_eye);
		addAttackId(varka_mobs);
		addQuestItem(7237);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == herald_wakan)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 74)
				{
					if(st.getQuestItemsCount(7211) > 0 || st.getQuestItemsCount(7212) > 0 || st.getQuestItemsCount(7213) > 0 || st.getQuestItemsCount(7214) > 0 || st.getQuestItemsCount(7215) > 0)
						return "herald_wakan_q0609_01.htm";

					return "herald_wakan_q0609_01a.htm";
				}
				return "herald_wakan_q0609_01b.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:herald_wakan_q0609_03.htm";
			}
		}
		else if(npc.getNpcId() == shaman_asefa)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					st.setMemoState(2);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:shaman_asefa_q0609_01.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:shaman_asefa_q0609_02.htm";
				if(st.getMemoState() == 3)
				{
					npc.altUseSkill(SkillTable.getInstance().getInfo(298057729), talker);
					st.setMemoState(2);
					return "npchtm:shaman_asefa_q0609_03.htm";
				}
				if(st.getMemoState() == 4)
				{
					st.giveItems(7238, 1);
					st.giveItems(7081, 1);
					st.takeItems(7237, -1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:shaman_asefa_q0609_04.htm";
				}
			}
		}
		else if(npc.getNpcId() == udans_box)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 2 || st.getMemoState() == 3)
					return "npchtm:udans_box_q0609_01.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == herald_wakan)
		{
			if(reply == 609)
			{
				if(st.isCreated() && talker.getLevel() >= 74)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("herald_wakan_q0609_02.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == udans_box)
		{
			if(reply == 1)
			{
				if(st.getQuestItemsCount(1661) < 1)
				{
					showPage("udans_box_q0609_02.htm", talker);
				}
				else if(st.getQuestItemsCount(1661) >= 1 && st.getMemoState() == 2)
				{
					showPage("udans_box_q0609_03.htm", talker);
					npc.altUseSkill(SkillTable.getInstance().getInfo(298057729), talker);
					st.giveItems(7237, 1);
					st.takeItems(1661, 1);
					st.setMemoState(4);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
				else if(st.getQuestItemsCount(1661) >= 1 && st.getMemoState() == 3)
				{
					showPage("udans_box_q0609_04.htm", talker);
					st.takeItems(1661, 1);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == udans_eye)
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, -1);
			if(st != null)
			{
				Functions.npcSay(npc, Say2C.ALL, 60904);
			}
		}
		else if(contains(ketra_mobs, npc.getNpcId()))
		{
			QuestState st = killer.getQuestState(609);
			if(st != null)
			{
				st.takeItems(7237, -1);
				st.exitCurrentQuest(true);
			}
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, QuestState qs, L2Skill skill)
	{
		if(qs.getMemoState() == 2 && contains(varka_mobs, npc.getNpcId()))
		{
			qs.setMemoState(3);
			npc.createOnePrivate(udans_eye, "UdansEye", 0, 0, npc.getX(), npc.getY(), npc.getZ(), 0, 0, 0, 0);
			npc.altUseSkill(SkillTable.getInstance().getInfo(297992193), qs.getPlayer());
		}
		return null;
	}
}