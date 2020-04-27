package quests._000_ShortName;

import ru.l2f.extensions.scripts.ScriptFile;
import ru.l2f.gameserver.model.instances.L2NpcInstance;
import ru.l2f.gameserver.model.quest.Quest;
import ru.l2f.gameserver.model.quest.QuestState;
import ru.l2f.gameserver.model.quest.State;

/**
 * <hr><em>Квест</em> <strong>Long_Name</strong><hr>
 * @author 
 * @see <a href="www.lineage2.com/corejava.html">Линейко дфа homepage</a>
 * @version 
 * @since 
 */

public class _000_ShortName extends Quest
{
	public void onLoad()
	{
		System.out.println("Loaded Quest: 000: Long_Name");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public _000_ShortName()
	{
		super(000, "_000_ShortName", "Long_Name");
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		State id = st.getState();
		int cond = st.getInt("cond");
		return htmltext;
	}

	@Override
	public String onKill(L2NpcInstance npc, QuestState st)
	{
		return null;
	}
}