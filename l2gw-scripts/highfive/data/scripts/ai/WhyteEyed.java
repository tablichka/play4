package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.instancemanager.QuestManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.SkillTable;

public class WhyteEyed extends DefaultAI
{

	public WhyteEyed(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		return super.thinkActive();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
	
	Quest q604 = QuestManager.getQuest(604);
	QuestState st = ((L2Player) attacker).getQuestState(q604.getName());
	
	if (st!= null)
	if(st.getInt("cond") == 2 && st.getQuestItemsCount(7193) > 0)
	    return;
	
	L2Skill _para = SkillTable.getInstance().getInfo(4515, 1);
	_para.applyEffects(attacker, attacker, false);
	
	
	}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}