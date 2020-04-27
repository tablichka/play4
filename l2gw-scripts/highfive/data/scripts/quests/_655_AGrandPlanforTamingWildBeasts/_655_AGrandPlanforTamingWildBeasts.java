package quests._655_AGrandPlanforTamingWildBeasts;

import java.util.Date;

import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.siege.Siege;
import ru.l2gw.gameserver.model.entity.siege.SiegeUnit;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест на захват Клан Холла Wild Beast Reserve (63)
 *
 * @author Felixx спиздил viRUS
 */

//TODO 1) Тип Стартового НПС - L2NPC 2)Квестовые итемы в квест Инвентарь in SQL
//TODO При окончании работ поправить расчет времени до осады и убрать дебаг инфу.
public class _655_AGrandPlanforTamingWildBeasts extends Quest
{
	//NPC
	private static final int MESSENGER = 35627;
	//MOBS
	private static int BUFFALO = 16013;
	private static int COUGAR = 16015;
	private static int KUKABURA = 16017;

	//ITEMS
	private static int STONE = 8084;
	private static int TSTONE = 8293;

	//SHANCE
	private static int STONE_CHANCE = 30;

	public _655_AGrandPlanforTamingWildBeasts()
	{
		super(655, "_655_AGrandPlanforTamingWildBeasts", "A Grand Planfor Taming Wild Beasts"); // Party true

		addStartNpc(MESSENGER);
		addTalkId(MESSENGER);
		addKillId(BUFFALO);
		addKillId(COUGAR);
		addKillId(KUKABURA);
		addKillId(STONE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("35627-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();
		if(npcId == MESSENGER)
		{
			if(st.isCreated())
			{
				SiegeUnit ch = ResidenceManager.getInstance().getBuildingById(63);
				Siege chSiege = ch.getSiege();
				if(chSiege != null)
				{
					if(st.getPlayer().getClan().getLevel() >= 4)
					{
						long NextSiege = chSiege.getSiegeDate().getTimeInMillis() / 1000;
						long CurTime = ((new Date()).getTime() / 1000);
						long TimeToClanHallSiege = ((NextSiege - CurTime) / 60);
						System.out.println("Osada Budet: " + NextSiege);
						System.out.println("Seychas: " + CurTime);
						System.out.println("Do Nachala : " + TimeToClanHallSiege + " minut");
						System.out.println("**************************************");
						if(TimeToClanHallSiege != 0)
						{
							htmltext = "35627-01.htm";
						}
						else
						{
							htmltext = "notime.htm";
							st.exitCurrentQuest(true);
						}
					}
					else
						htmltext = "noclan.htm";
				}
			}
			else if(cond == 1)
			{
				if(st.getQuestItemsCount(STONE) <= 10)
					htmltext = "noitem.htm";
			}
			else if(cond == 2)
			{
				if(st.getQuestItemsCount(STONE) >= 10)
				{
					st.takeItems(STONE, -1);
					st.giveItems(TSTONE, 1);
					st.set("cond", "3");
					st.setState(STARTED);
				}
				else
					htmltext = "noitem.htm";
			}
			else if(cond == 3)
			{
				if(st.getQuestItemsCount(TSTONE) >= 1)
				{
					//Показать окно регистрации
				}
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null)
		{
			if(st.rollAndGiveLimited(STONE, 1, STONE_CHANCE, 10))
			{
				if(st.getQuestItemsCount(STONE) == 10)
				{
					st.set("cond", "2");
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}

		}
	}
}