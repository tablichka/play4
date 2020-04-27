package ai;

import javolution.util.FastMap;
import npc.model.KashaEyeInstance;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.arrays.GCSArray;
import ru.l2gw.util.Location;

import java.util.Calendar;

/**
 * @author rage
 * @date 16.08.2010 16:23:45
 */
public class KashaControl extends DefaultAI
{
	private static final L2Skill SKILL_camp_destroy = SkillTable.getInstance().getInfo(6149, 1);
	private final GArray<Location> spawnLoc;
	private final GCSArray<Integer> kashas;
	private int groupNum;
	private final FastMap<Integer, FastMap<Integer, L2Zone>> zonesPc = new FastMap<Integer, FastMap<Integer, L2Zone>>();
	private final FastMap<Integer, FastMap<Integer, L2Zone>> zonesNpc = new FastMap<Integer, FastMap<Integer, L2Zone>>();
	private int[] currentLevel;
	private L2Zone my_terr;

	private static final int KASHA_RED = 18812;
	private static final int KASHA_GREEN = 18813;
	private static final int KASHA_BLUE = 18814;

	private static final int TIMER_CHECK_20SEC = 33120;
	private static final int TIMER_SPAWN_PRIVATE = 33124;
	private static final int TIMER_DESTROY_CAMP = 33125;

	public KashaControl(L2Character actor)
	{
		super(actor);
		spawnLoc = new GArray<Location>(4);
		kashas = new GCSArray<Integer>(4);
		_thisActor.setIsInvul(true);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		groupNum = getInt("GroupNum", -1);
		currentLevel = new int[4];
		for(int i = 1; i < 5; i++)
		{
			spawnLoc.add(Location.parseLoc(getString("spot" + i, "-1,-1,-1")));
			kashas.add(i - 1);
		}

		my_terr = ZoneManager.getInstance().getZoneByName(getString("my_trr", ""));
		if(my_terr == null)
			_log.info(_thisActor + " no zone: " + getString("my_trr", ""));

		for(int i = 1; i <= 3; i++)
			for(int l = 1; l <= 4; l++)
			{
				FastMap<Integer, L2Zone> zones = zonesPc.get(i);
				if(zones == null)
				{
					zones = new FastMap<Integer, L2Zone>();
					zonesPc.put(i, zones);
				}
				L2Zone zone = ZoneManager.getInstance().getZoneByName("22_14_camp_" + String.format("%02d", groupNum) + "_pc_" + i + "_lv" + l);
				if(zone == null)
					_log.info(_thisActor + " no zone: " + "22_14_camp_" + String.format("%02d", groupNum) + "_pc_" + i + "_lv" + l);
				zones.put(l, zone);

				zones = zonesNpc.get(i);
				if(zones == null)
				{
					zones = new FastMap<Integer, L2Zone>();
					zonesNpc.put(i, zones);
				}
				zone = ZoneManager.getInstance().getZoneByName("22_14_camp_" + String.format("%02d", groupNum) + "_npc_" + i + "_lv" + l);
				zones.put(l, zone);
				if(zone == null)
					_log.info(_thisActor + " no zone: " + "22_14_camp_" + String.format("%02d", groupNum) + "_npc_" + i + "_lv" + l);
			}

		addTimer(TIMER_SPAWN_PRIVATE, 1);
		int sec = Calendar.getInstance().get(Calendar.SECOND);
		if(sec < 20)
			sec = 20 - sec;
		else if(sec < 40)
			sec = 40 - sec;
		else
			sec = 60 - sec;

		addTimer(TIMER_CHECK_20SEC, sec * 1000L);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_CHECK_20SEC)
		{
			addTimer(TIMER_SPAWN_PRIVATE, 1);
			addTimer(TIMER_CHECK_20SEC, 20000);
		}
		else if(timerId == TIMER_SPAWN_PRIVATE)
		{
			if(kashas.size() > 0)
				for(int i : kashas)
					createPrivate(Rnd.get(KASHA_RED, KASHA_BLUE), spawnLoc.get(i), groupNum, i);
			kashas.clear();
		}
		else if(timerId == TIMER_DESTROY_CAMP)
		{
			if(!_thisActor.isSkillDisabled(SKILL_camp_destroy.getId()) && _thisActor.getCurrentMp() > SKILL_camp_destroy.getMpConsume())
				_thisActor.altUseSkill(SKILL_camp_destroy, _thisActor);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2214004)
		{
			if(arg1 instanceof Integer)
			{
				int group = (Integer) arg1;
				if(groupNum == group && !kashas.contains((Integer) arg2))
					kashas.add((Integer) arg2);
			}
		}
		else if(eventId == 2214010)
		{
			synchronized(currentLevel)
			{
				FastMap<Integer, L2Zone> pc = zonesPc.get((Integer) arg1);
				FastMap<Integer, L2Zone> npc = zonesPc.get((Integer) arg1);
				switch(currentLevel[(Integer) arg1])
				{
					case 0:
						pc.get(1).setActive(true);
						npc.get(1).setActive(true);
						currentLevel[(Integer) arg1] = 1;
						break;
					case 1:
						pc.get(1).setActive(false);
						npc.get(1).setActive(false);
						pc.get(2).setActive(true);
						npc.get(2).setActive(true);
						currentLevel[(Integer) arg1] = 2;
						break;
					case 2:
						pc.get(2).setActive(false);
						npc.get(2).setActive(false);
						pc.get(3).setActive(true);
						npc.get(3).setActive(true);
						currentLevel[(Integer) arg1] = 3;
						break;
					case 3:
						pc.get(3).setActive(false);
						npc.get(3).setActive(false);
						pc.get(4).setActive(true);
						npc.get(4).setActive(true);
						currentLevel[(Integer) arg1] = 4;
						break;
				}
			}
		}
		else if(eventId == 2214011)
		{
			int group = (Integer) arg2;
			if(groupNum == group)
				synchronized(currentLevel)
				{
					FastMap<Integer, L2Zone> pc = zonesPc.get((Integer) arg1);
					FastMap<Integer, L2Zone> npc = zonesPc.get((Integer) arg1);
					switch(currentLevel[(Integer) arg1])
					{
						case 1:
							pc.get(1).setActive(false);
							npc.get(1).setActive(false);
							currentLevel[(Integer) arg1] = 0;
							break;
						case 2:
							pc.get(1).setActive(true);
							npc.get(1).setActive(true);
							pc.get(2).setActive(false);
							npc.get(2).setActive(false);
							currentLevel[(Integer) arg1] = 1;
							break;
						case 3:
							pc.get(2).setActive(true);
							npc.get(2).setActive(true);
							pc.get(3).setActive(false);
							npc.get(3).setActive(false);
							currentLevel[(Integer) arg1] = 2;
							break;
						case 4:
							pc.get(3).setActive(true);
							npc.get(3).setActive(true);
							pc.get(4).setActive(false);
							npc.get(4).setActive(false);
							currentLevel[(Integer) arg1] = 3;
							break;
					}
				}
		}
		else if(eventId == 2214002)
		{
			int group = (Integer) arg1;
			int num = (Integer) arg2;
			if(groupNum == group)
			{
				if(num == 0)
				{
					broadcastScriptEvent(2214003, groupNum, null, 4000);
					addTimer(TIMER_DESTROY_CAMP, 3000);
				}
				else if(num == 3)
					my_terr.broadcastPacket(Msg.THE_KASHA_S_EYE_GIVES_YOU_A_STRANGE_FEELING);
				else if(num == 2)
					my_terr.broadcastPacket(Msg.I_CAN_FEEL_THAT_THE_ENERGY_BEING_FLOWN_IN_THE_KASHA_S_EYE_IS_GETTING_STRONGER_RAPIDLY);
				else if(num == 1)
					my_terr.broadcastPacket(Msg.KASHA_S_EYE_PITCHES_AND_TOSSES_LIKE_IT_S_ABOUT_TO_EXPLODE);
			}
		}
	}

	private void createPrivate(int npcId, Location loc, int group, int num)
	{
		try
		{
			L2NpcTemplate template = NpcTable.getTemplate(npcId);
			KashaEyeInstance kasha = new KashaEyeInstance(IdFactory.getInstance().getNextId(), template, 0, 0, 0, 0);
			kasha.setCurrentHp(kasha.getMaxHp());
			kasha.setCurrentMp(kasha.getMaxMp());
			kasha.i_ai0 = group;
			kasha.i_ai1 = num;
			kasha.c_ai0 = _thisActor.getStoredId();
			kasha.setHeading(loc.getHeading());
			kasha.spawnMe(loc);
			kasha.onSpawn();
		}
		catch(Exception e)
		{
			_log.info(this + " can't spawn: " + npcId + " " + loc + " group: " + group + " num: " + num + " " + e);
			e.printStackTrace();
		}
	}
}
