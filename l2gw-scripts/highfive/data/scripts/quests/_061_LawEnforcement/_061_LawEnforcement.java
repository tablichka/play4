package quests._061_LawEnforcement;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;

public class _061_LawEnforcement extends Quest
{
	int Liane = 32222;
	int Kekropus = 32138;
	int Eindburgh = 32469;

	public _061_LawEnforcement()
	{
		super(61, "_061_LawEnforcement", "Law Enforcement");

		addStartNpc(Liane);
		addTalkId(Kekropus);
		addTalkId(Eindburgh);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("32222-03.htm"))
		{
			if(st.getPlayer().getClassId() != ClassId.inspector || st.getPlayer().getLevel() < 76)
				htmltext = "32222-no.htm";
			else
			{
				st.setState(STARTED);
				st.set("cond", "1");
				st.playSound(SOUND_ACCEPT);
			}
		}
		if(event.equals("32138-08.htm"))
		{
			if(st.getPlayer().getClassId() != ClassId.inspector || st.getPlayer().getLevel() < 76)
				htmltext = "32138-no.htm";
			else
			{
				st.setState(STARTED);
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
			}
		}
		if(event.equals("32469-08a.htm"))
		{
			if(st.getPlayer().getClassId() != ClassId.inspector || st.getPlayer().getLevel() < 76)
				htmltext = "32469-no.htm";
			else
			{
				L2Player player = st.getPlayer();
				st.rollAndGive(57, 26000, 100);
				st.setState(COMPLETED);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
				player.setClassId((short) ClassId.judicator.getId());
				player.broadcastUserInfo(true);
				Cast(FindTemplate(Eindburgh), player, 4339, 1);
			}

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
		int cond = st.getInt("cond");
		if(npcId == Liane)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getClassId() != ClassId.inspector || st.getPlayer().getLevel() < 76)
				{
					htmltext = "32222-no.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "32222-01.htm";
			}
			else if(st.isStarted())
				htmltext = "32222-03a.htm";
		}
		else if(npcId == Kekropus)
		{
			if(cond == 1)
				htmltext = "32138-01.htm";
			else if(cond >= 2)
				htmltext = "32138-08a.htm";
		}
		else if(npcId == Eindburgh)
		{
			if(cond == 2)
				htmltext = "32469-01.htm";
		}
		return htmltext;
	}

	protected void Cast(L2NpcInstance npc, L2Character target, int skillId, int level)
	{
		target.broadcastPacket(new MagicSkillUse(target, target, skillId, level, 6000, 1));
		target.broadcastPacket(new MagicSkillUse(npc, npc, skillId, level, 6000, 1));
	}

	protected L2NpcInstance FindTemplate(int npcId)
	{
		return L2ObjectsStorage.getByNpcId(npcId);
	}

}