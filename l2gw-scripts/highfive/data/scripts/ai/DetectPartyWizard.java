package ai;

import ai.base.WizardUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 12:54
 */
public class DetectPartyWizard extends WizardUseSkill
{
	public int duelist = 30;
	public int dreadnought = 42;
	public int phoenix_knight = 24;
	public int hell_knight = 27;
	public int sagittarius = 23;
	public int adventurer = 20;
	public int archmage = 26;
	public int soultaker = 25;
	public int arcana_lord = 47;
	public int cardinal = 20;
	public int hierophant = 18;
	public int evas_templar = 39;
	public int sword_muse = 13;
	public int wind_rider = 27;
	public int moonlight_sentinel = 22;
	public int mystic_muse = 21;
	public int elemental_master = 45;
	public int evas_saint = 14;
	public int shillien_templar = 35;
	public int spectral_dancer = 10;
	public int ghost_hunter = 33;
	public int ghost_sentinel = 20;
	public int storm_screamer = 25;
	public int spectral_master = 49;
	public int shillien_saint = 21;
	public int titan = 26;
	public int grand_khavatari = 24;
	public int dominator = 29;
	public int doomcryer = 23;
	public int fortune_seeker = 42;
	public int maestro = 44;
	public int doombringer = 28;
	public int m_soul_hound = 36;
	public int f_soul_hound = 36;
	public int trickster = 30;
	public int judicator = 48;
	public int threshold = 33;
	public int max_threshold = 183;
	public int loner = 25;
	public int category_weight = 15;
	public L2Skill morale_up_display = SkillTable.getInstance().getInfo(451149825);
	public L2Skill morale_up_lv1 = SkillTable.getInstance().getInfo(451215361);
	public L2Skill morale_up_lv2 = SkillTable.getInstance().getInfo(451215362);
	public L2Skill morale_up_lv3 = SkillTable.getInstance().getInfo(451215363);

	public DetectPartyWizard(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(skill != null && skill.getId() == 985)
		{
			removeAllAttackDesire();
			addUseSkillDesire(_thisActor, 453443585, 1, 0, 999999999999999999L);
			if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
				setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
		}

		if(attacker.getPlayer() != null && _thisActor.i_ai1 == 0)
		{
			_thisActor.i_ai1 = 1;
			L2Player player = attacker.getPlayer();
			L2Party party0 = player.getParty();
			if(party0 != null)
			{
				int i2 = 0;
				int i3 = 0;
				int i4 = 0;
				int i5 = 0;
				int i6 = 0;
				int i7 = 0;
				int i8 = 0;
				int i9 = 0;

				for(L2Player c0 : party0.getPartyMembers())
				{
					if(c0 != null)
					{
						switch(c0.getActiveClass())
						{
							case 88:
								if(duelist > loner)
									_thisActor.i_ai0 += duelist - loner;
								break;
							case 89:
								if(dreadnought > loner)
									_thisActor.i_ai0 += dreadnought - loner;
								break;
							case 90:
								if(phoenix_knight > loner)
									_thisActor.i_ai0 += phoenix_knight - loner;
								break;
							case 91:
								if(hell_knight > loner)
									_thisActor.i_ai0 += hell_knight - loner;
								break;
							case 92:
								if(sagittarius > loner)
									_thisActor.i_ai0 += sagittarius - loner;
								break;
							case 93:
								if(adventurer > loner)
									_thisActor.i_ai0 += adventurer - loner;
								break;
							case 94:
								if(archmage > loner)
									_thisActor.i_ai0 += archmage - loner;
								break;
							case 95:
								if(soultaker > loner)
									_thisActor.i_ai0 += soultaker - loner;
								break;
							case 96:
								if(arcana_lord > loner)
									_thisActor.i_ai0 += arcana_lord - loner;
								break;
							case 97:
								if(cardinal > loner)
									_thisActor.i_ai0 += cardinal - loner;
								break;
							case 98:
								if(hierophant > loner)
									_thisActor.i_ai0 += hierophant - loner;
								break;
							case 99:
								if(evas_templar > loner)
									_thisActor.i_ai0 += evas_templar - loner;
								break;
							case 100:
								if(sword_muse > loner)
									_thisActor.i_ai0 += sword_muse - loner;
								break;
							case 101:
								if(wind_rider > loner)
									_thisActor.i_ai0 += wind_rider - loner;
								break;
							case 102:
								if(moonlight_sentinel > loner)
									_thisActor.i_ai0 += moonlight_sentinel - loner;
								break;
							case 103:
								if(mystic_muse > loner)
									_thisActor.i_ai0 += mystic_muse - loner;
								break;
							case 104:
								if(elemental_master > loner)
									_thisActor.i_ai0 += elemental_master - loner;
								break;
							case 105:
								if(evas_saint > loner)
									_thisActor.i_ai0 += evas_saint - loner;
								break;
							case 106:
								if(shillien_templar > loner)
									_thisActor.i_ai0 += shillien_templar - loner;
								break;
							case 107:
								if(spectral_dancer > loner)
									_thisActor.i_ai0 += spectral_dancer - loner;
								break;
							case 108:
								if(ghost_hunter > loner)
									_thisActor.i_ai0 += ghost_hunter - loner;
								break;
							case 109:
								if(ghost_sentinel > loner)
									_thisActor.i_ai0 += ghost_sentinel - loner;
								break;
							case 110:
								if(storm_screamer > loner)
									_thisActor.i_ai0 += storm_screamer - loner;
								break;
							case 111:
								if(spectral_master > loner)
									_thisActor.i_ai0 += spectral_master - loner;
								break;
							case 112:
								if(shillien_saint > loner)
									_thisActor.i_ai0 += shillien_saint - loner;
								break;
							case 113:
								if(titan > loner)
									_thisActor.i_ai0 += titan - loner;
								break;
							case 114:
								if(grand_khavatari > loner)
									_thisActor.i_ai0 += grand_khavatari - loner;
								break;
							case 115:
								if(dominator > loner)
									_thisActor.i_ai0 += dominator - loner;
								break;
							case 116:
								if(doomcryer > loner)
									_thisActor.i_ai0 += doomcryer - loner;
								break;
							case 117:
								if(fortune_seeker > loner)
									_thisActor.i_ai0 += fortune_seeker - loner;
								break;
							case 118:
								if(maestro > loner)
									_thisActor.i_ai0 += maestro - loner;
								break;
							case 131:
								if(doombringer > loner)
									_thisActor.i_ai0 += doombringer - loner;
								break;
							case 132:
								if(m_soul_hound > loner)
									_thisActor.i_ai0 += m_soul_hound - loner;
								break;
							case 133:
								if(f_soul_hound > loner)
									_thisActor.i_ai0 += f_soul_hound - loner;
								break;
							case 134:
								if(trickster > loner)
									_thisActor.i_ai0 += trickster - loner;
								break;
							case 135:
							case 136:
								if(judicator > loner)
									_thisActor.i_ai0 += judicator - loner;
								break;
						}

						if(c0.getActiveClass() == 90 || c0.getActiveClass() == 91 || c0.getActiveClass() == 99 || c0.getActiveClass() == 106)
						{
							_thisActor.i_ai0 += category_weight;
							_thisActor.i_ai8 += category_weight;
						}
						if(c0.getActiveClass() == 95 || c0.getActiveClass() == 96 || c0.getActiveClass() == 104 || c0.getActiveClass() == 111)
						{
							_thisActor.i_ai0 += category_weight;
							_thisActor.i_ai8 += category_weight;
						}

						if(c0.getActiveClass() == 94 || c0.getActiveClass() == 103 || c0.getActiveClass() == 110 ||
								c0.getActiveClass() == 92 || c0.getActiveClass() == 102 || c0.getActiveClass() == 109 || c0.getActiveClass() == 134 ||
								c0.getActiveClass() == 93 || c0.getActiveClass() == 101 || c0.getActiveClass() == 108)
						{
							_thisActor.i_ai0 += 3;
							_thisActor.i_ai8 += 3;
						}
						if(c0.getActiveClass() == 97 || c0.getActiveClass() == 105 || c0.getActiveClass() == 112 || c0.getActiveClass() == 115)
						{
							_thisActor.i_ai0 += 1;
							_thisActor.i_ai8 += 1;
						}

						if(c0.getActiveClass() == 95 || c0.getActiveClass() == 96 || c0.getActiveClass() == 104 || c0.getActiveClass() == 111)
							i2++;
						if(c0.getActiveClass() == 94 || c0.getActiveClass() == 103 || c0.getActiveClass() == 110)
							i3++;
						if(c0.getActiveClass() == 92 || c0.getActiveClass() == 102 || c0.getActiveClass() == 109 || c0.getActiveClass() == 134)
							i4++;
						if(c0.getActiveClass() == 93 || c0.getActiveClass() == 101 || c0.getActiveClass() == 108)
							i5++;
						if(c0.getActiveClass() == 97 || c0.getActiveClass() == 105 || c0.getActiveClass() == 112 || c0.getActiveClass() == 115)
							i6++;
						if(c0.getActiveClass() == 90 || c0.getActiveClass() == 91 || c0.getActiveClass() == 99 || c0.getActiveClass() == 106)
							i7++;
						if(c0.getActiveClass() == 98 || c0.getActiveClass() == 100 || c0.getActiveClass() == 107 || c0.getActiveClass() == 116)
							i8++;
						if(c0.getActiveClass() == 88 || c0.getActiveClass() == 89 || c0.getActiveClass() == 113 || c0.getActiveClass() == 114 || c0.getActiveClass() == 118 || c0.getActiveClass() == 131 || c0.getActiveClass() == 132 || c0.getActiveClass() == 133 || c0.getActiveClass() == 117)
							i9++;
					}
				}

				if(i2 > 0 && i3 > 0 && i4 > 0 && i5 > 0 && i6 > 0 && i7 > 0 && i8 > 0 && i9 > 0)
				{
					addUseSkillDesire(attacker, morale_up_lv3, 1, 0, 99900000000L);
					for(L2Player c0 : party0.getPartyMembers())
						if(c0 != null)
							addUseSkillDesire(c0, morale_up_display, 1, 0, 99900000000L);

					_thisActor.i_ai0 = 0;
					_thisActor.i_ai8 = 0;
					addTimer(2321001, 55000);
					super.onEvtAttacked(attacker, damage, skill);
					return;
				}
			}

			if(_thisActor.i_ai0 > threshold)
			{
				if(_thisActor.i_ai0 > max_threshold * 0.450000)
					addUseSkillDesire(attacker, morale_up_lv3, 1, 0, 99900000000L);
				else if(_thisActor.i_ai0 > max_threshold * 0.300000)
					addUseSkillDesire(attacker, morale_up_lv2, 1, 0, 99900000000L);
				else
					addUseSkillDesire(attacker, morale_up_lv1, 1, 0, 99900000000L);

				if(party0 != null)
				{
					for(L2Player c0 : party0.getPartyMembers())
					{
						if(c0 != null)
						{
							int i4 = 0;
							switch(c0.getActiveClass())
							{
								case 88:
									if(0 < duelist - loner)
										i4 = 1;
									break;
								case 89:
									if(0 < dreadnought - loner)
										i4 = 1;
									break;
								case 90:
									if(0 < phoenix_knight - loner)
										i4 = 1;
									break;
								case 91:
									if(0 < hell_knight - loner)
										i4 = 1;
									break;
								case 92:
									if(0 < sagittarius - loner)
										i4 = 1;
									break;
								case 93:
									if(0 < adventurer - loner)
										i4 = 1;
									break;
								case 94:
									if(0 < archmage - loner)
										i4 = 1;
									break;
								case 95:
									if(0 < soultaker - loner)
										i4 = 1;
									break;
								case 96:
									if(0 < arcana_lord - loner)
										i4 = 1;
									break;
								case 97:
									if(0 < cardinal - loner)
										i4 = 1;
									break;
								case 98:
									if(0 < hierophant - loner)
										i4 = 1;
									break;
								case 99:
									if(0 < evas_templar - loner)
										i4 = 1;
									break;
								case 100:
									if(0 < sword_muse - loner)
										i4 = 1;
									break;
								case 101:
									if(0 < wind_rider - loner)
										i4 = 1;
									break;
								case 102:
									if(0 < moonlight_sentinel - loner)
										i4 = 1;
									break;
								case 103:
									if(0 < mystic_muse - loner)
										i4 = 1;
									break;
								case 104:
									if(0 < elemental_master - loner)
										i4 = 1;
									break;
								case 105:
									if(0 < evas_saint - loner)
										i4 = 1;
									break;
								case 106:
									if(0 < shillien_templar - loner)
										i4 = 1;
									break;
								case 107:
									if(0 < spectral_dancer - loner)
										i4 = 1;
									break;
								case 108:
									if(0 < ghost_hunter - loner)
										i4 = 1;
									break;
								case 109:
									if(0 < ghost_sentinel - loner)
										i4 = 1;
									break;
								case 110:
									if(0 < storm_screamer - loner)
										i4 = 1;
									break;
								case 111:
									if(0 < spectral_master - loner)
										i4 = 1;
									break;
								case 112:
									if(0 < shillien_saint - loner)
										i4 = 1;
									break;
								case 113:
									if(0 < titan - loner)
										i4 = 1;
									break;
								case 114:
									if(0 < grand_khavatari - loner)
										i4 = 1;
									break;
								case 115:
									if(0 < dominator - loner)
										i4 = 1;
									break;
								case 116:
									if(0 < doomcryer - loner)
										i4 = 1;
									break;
								case 117:
									if(0 < fortune_seeker - loner)
										i4 = 1;
									break;
								case 118:
									if(0 < maestro - loner)
										i4 = 1;
									break;
								case 131:
									if(0 < doombringer - loner)
										i4 = 1;
									break;
								case 132:
									if(0 < m_soul_hound - loner)
										i4 = 1;
									break;
								case 133:
									if(0 < f_soul_hound - loner)
										i4 = 1;
									break;
								case 134:
									if(0 < trickster - loner)
										i4 = 1;
									break;
								case 135:
								case 136:
									if(0 < judicator - loner)
										i4 = 1;
									break;
							}

							if(CategoryManager.isInCategory(5, c0.getActiveClass()) && CategoryManager.isInCategory(9, c0.getActiveClass()))
								i4 = 1;
							if(CategoryManager.isInCategory(94, c0.getActiveClass()) && CategoryManager.isInCategory(9, c0.getActiveClass()))
								i4 = 1;
							if(i4 == 1)
								addUseSkillDesire(c0, morale_up_display, 1, 0, 99900000000L);
						}
					}
				}
			}
			_thisActor.i_ai0 = 0;
			_thisActor.i_ai8 = 0;
			addTimer(2321001, 55000);
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2321001)
			_thisActor.i_ai1 = 0;
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		L2Player player = killer != null ? killer.getPlayer() : null;
		if(player != null)
		{
			if(Rnd.get(1000) < 5)
				_thisActor.createOnePrivate(18919, "AiPartyVitalityHerb", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, player.getStoredId(), 0, 0);

			_thisActor.dropItem(player, Rnd.chance(50) ? 8604 : 8605, 1);
		}
	}
}
