package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.12.11 21:16
 */
public class WarriorTreasureBox extends DefaultNpc
{
	public int CreviceOfDiminsion = 0;

	public WarriorTreasureBox(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(CreviceOfDiminsion != 0)
		{
			if(!_thisActor.inMyTerritory(attacker))
			{
				removeAttackDesire(attacker);
				return;
			}
		}

		int i2 = 0;
		if(skill != null)
		{
			if(skill.getId() == 27)
			{
				int i1 = skill.getId();
				int i0 = 0;
				if(i1 == 1)
				{
					i0 = 98;
				}
				else if(i1 == 2)
				{
					i0 = 84;
				}
				else if(i1 == 3)
				{
					i0 = 99;
				}
				else if(i1 == 4)
				{
					i0 = 84;
				}
				else if(i1 == 5)
				{
					i0 = 88;
				}
				else if(i1 == 6)
				{
					i0 = 90;
				}
				else if(i1 == 7)
				{
					i0 = 89;
				}
				else if(i1 == 8)
				{
					i0 = 88;
				}
				else if(i1 == 9)
				{
					i0 = 86;
				}
				else if(i1 == 10)
				{
					i0 = 90;
				}
				else if(i1 == 11)
				{
					i0 = 87;
				}
				else if(i1 == 12)
				{
					i0 = 89;
				}
				else if(i1 == 13)
				{
					i0 = 89;
				}
				else if(i1 == 14)
				{
					i0 = 89;
				}
				else if(i1 == 15)
				{
					i0 = 89;
				}

				i2 = (i0 - ((_thisActor.getLevel() - i1 * 4) - 16) * 6);
				if(i2 > i0)
				{
					i2 = i0;
				}
			}
			else if(skill.getId() == 2065)
			{
				int i1 = skill.getId();
				i2 = (int) ((60 - (_thisActor.getLevel() - (i1 - 1) * 10) * 1.500000));
				if(i2 > 60)
				{
					i2 = 60;
				}
			}
			else if(skill.getId() == 2229)
			{
				int i1 = skill.getId();
				if(i1 == 1)
				{
					int i3 = (_thisActor.getLevel() - 19);
					if(i3 <= 0)
					{
						i2 = 100;
					}
					else
					{
						i2 = (int) (((0.000200 * i3 * i3 - 0.026400 * i3) + 0.769500) * 100);
					}
				}
				else if(i1 == 2)
				{
					int i3 = (_thisActor.getLevel() - 29);
					if(i3 <= 0)
					{
						i2 = 100;
					}
					else
					{
						i2 = (int) (((0.000300 * i3 * i3 - 0.027900 * i3) + 0.756800) * 100);
					}
				}
				else if(i1 == 3)
				{
					int i3 = (_thisActor.getLevel() - 39);
					if(i3 <= 0)
					{
						i2 = 100;
					}
					else
					{
						i2 = (int) (((0.000300 * i3 * i3 - 0.026900 * i3) + 0.733400) * 100);
					}
				}
				else if(i1 == 4)
				{
					int i3 = (_thisActor.getLevel() - 49);
					if(i3 <= 0)
					{
						i2 = 100;
					}
					else
					{
						i2 = (int) (((0.000300 * i3 * i3 - 0.028400 * i3) + 0.803400) * 100);
					}
				}
				else if(i1 == 5)
				{
					int i3 = (_thisActor.getLevel() - 59);
					if(i3 <= 0)
					{
						i2 = 100;
					}
					else
					{
						i2 = (int) (((0.000500 * i3 * i3 - 0.035600 * i3) + 0.906500) * 100);
					}
				}
				else if(i1 == 6)
				{
					int i3 = (_thisActor.getLevel() - 69);
					if(i3 <= 0)
					{
						i2 = 100;
					}
					else
					{
						i2 = (int) (((0.000900 * i3 * i3 - 0.037300 * i3) + 0.857200) * 100);
					}
				}
				else if(i1 == 7)
				{
					int i3 = (_thisActor.getLevel() - 79);
					if(i3 <= 0)
					{
						i2 = 100;
					}
					else
					{
						i2 = (int) (((0.004300 * i3 * i3 - 0.067100 * i3) + 0.959300) * 100);
					}
				}
				else if(i1 == 8)
				{
					i2 = 100;
				}
			}
			else
			{
				int i0 = _thisActor.getLevel() / 10;
				i0 += 271515649;
				if(SkillTable.mpConsume(i0) < _thisActor.getCurrentMp() && SkillTable.hpConsume(i0) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(i0))
				{
					addUseSkillDesire(_thisActor, i0, 0, 1, 1000000);
				}
				return;
			}
		}
		else
		{
			int i0 = _thisActor.getLevel() / 10;
			i0 += 271515649;
			if(SkillTable.mpConsume(i0) < _thisActor.getCurrentMp() && SkillTable.hpConsume(i0) < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabledEx(i0))
			{
				addUseSkillDesire(_thisActor, i0, 0, 1, 1000000);
			}
			return;
		}

		if(Rnd.get(100) < i2)
		{
			_thisActor.doDie(attacker);
		}
		else
		{
			_thisActor.onDecay();
			attacker.sendPacket(new PlaySound("ItemSound2.broken_key"));
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		_thisActor.onDecay();
	}
}