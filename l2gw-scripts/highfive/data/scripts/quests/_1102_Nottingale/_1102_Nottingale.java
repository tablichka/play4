package quests._1102_Nottingale;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.RadarControl;

public class _1102_Nottingale extends Quest
{
	public _1102_Nottingale()
	{
		super(1102, "_1102_Nottingale", "Nottingale", true);
		addTalkId(32627);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		L2Player player = st.getPlayer();
		QuestState qs = player.getQuestState("_10273_GoodDayToFly");
		if(qs == null || !qs.isCompleted())
		{
			player.sendPacket(new RadarControl(2, 2, 0, 0, 0));
			player.sendPacket(new RadarControl(0, 2, -184545, 243120, 1581));
			htmltext = "32627.htm";
		}
		else if(event.equalsIgnoreCase("32627-3.htm"))
		{
			player.sendPacket(new RadarControl(2, 2, 0, 0, 0));
			player.sendPacket(new RadarControl(0, 2, -192361, 254528, 3598));
		}
		else if(event.equalsIgnoreCase("32627-4.htm"))
		{
			player.sendPacket(new RadarControl(2, 2, 0, 0, 0));
			player.sendPacket(new RadarControl(0, 2, -174600, 219711, 4424));
		}
		else if(event.equalsIgnoreCase("32627-5.htm"))
		{
			player.sendPacket(new RadarControl(2, 2, 0, 0, 0));
			player.sendPacket(new RadarControl(0, 2, -181989, 208968, 4424));
		}
		else if(event.equalsIgnoreCase("32627-6.htm"))
		{
			player.sendPacket(new RadarControl(2, 2, 0, 0, 0));
			player.sendPacket(new RadarControl(0, 2, -252898, 235845, 5343));
		}
		else if(event.equalsIgnoreCase("32627-8.htm"))
		{
			player.sendPacket(new RadarControl(2, 2, 0, 0, 0));
			player.sendPacket(new RadarControl(0, 2, -212819, 209813, 4288));
		}
		else if(event.equalsIgnoreCase("32627-9.htm"))
		{
			player.sendPacket(new RadarControl(2, 2, 0, 0, 0));
			player.sendPacket(new RadarControl(0, 2, -246899, 251918, 4352));
		}
		return "npchtm:" + htmltext;
	}
}