package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author rage
 * @date 18.07.11 14:59
 */
public class SoAMarguene extends DefaultAI
{
	private static final int FIRST_TIMER = 1111;
	private static final int SECOND_TIMER = 1112;
	private static final int THIRD_TIMER = 1113;
	private static final int FORTH_TIMER = 1114;
	private static final int END_TIMER = 1115;
	private static final int DIST_CHECK_TIMER = 1116;
	private static final L2Skill B_PLASMA1 = SkillTable.getInstance().getInfo(417267713);
	private static final L2Skill B_PLASMA2 = SkillTable.getInstance().getInfo(417267714);
	private static final L2Skill B_PLASMA3 = SkillTable.getInstance().getInfo(417267715);
	private static final L2Skill C_PLASMA1 = SkillTable.getInstance().getInfo(417333249);
	private static final L2Skill C_PLASMA2 = SkillTable.getInstance().getInfo(417333250);
	private static final L2Skill C_PLASMA3 = SkillTable.getInstance().getInfo(417333251);
	private static final L2Skill R_PLASMA1 = SkillTable.getInstance().getInfo(417398785);
	private static final L2Skill R_PLASMA2 = SkillTable.getInstance().getInfo(417398786);
	private static final L2Skill R_PLASMA3 = SkillTable.getInstance().getInfo(417398787);
	private static final L2Skill B_BUFF_1 = SkillTable.getInstance().getInfo(415694849);
	private static final L2Skill B_BUFF_2 = SkillTable.getInstance().getInfo(415694850);
	private static final L2Skill C_BUFF_1 = SkillTable.getInstance().getInfo(417136641);
	private static final L2Skill C_BUFF_2 = SkillTable.getInstance().getInfo(417136642);
	private static final L2Skill R_BUFF_1 = SkillTable.getInstance().getInfo(417202177);
	private static final L2Skill R_BUFF_2 = SkillTable.getInstance().getInfo(417202178);

	public SoAMarguene(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();

		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
		if(c0 != null)
		{
			_thisActor.setTitle(c0.getName());
			_thisActor.moveToLocation(c0.getLoc(), 40, true);
			c0.sendPacket(new ExShowScreenMessage(new CustomMessage("fs1801149", c0.getPlayer()).toString(), 4000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true, false));
		}

		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.updateAbnormalEffect();
		addTimer(DIST_CHECK_TIMER, 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == DIST_CHECK_TIMER)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(_thisActor.isInRange(c0, 100) && _thisActor.i_ai2 == 0)
			{
				_thisActor.i_ai2 = 1;
				addTimer(FIRST_TIMER, 4000);
			}
			else if(!_thisActor.isInRange(c0, 100))
				_thisActor.moveToLocation(c0.getLoc(), 40, true);

			addTimer(DIST_CHECK_TIMER, 1000);
		}
		else if(timerId == FIRST_TIMER)
		{
			_thisActor.i_ai1 = Rnd.get(3) + 1;
			_thisActor.changeNpcState(_thisActor.i_ai1);
			addTimer(SECOND_TIMER, 5000);
			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), Rnd.get(3) + 1));
		}
		else if(timerId == SECOND_TIMER)
		{
			_thisActor.changeNpcState(4);
			_thisActor.i_ai1 = Rnd.get(3) + 1;
			_thisActor.changeNpcState(_thisActor.i_ai1);
			addTimer(THIRD_TIMER, 4600 + Rnd.get(600));
			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), Rnd.get(3) + 1));
		}
		else if(timerId == THIRD_TIMER)
		{
			_thisActor.changeNpcState(4);
			_thisActor.i_ai1 = Rnd.get(3) + 1;
			_thisActor.changeNpcState(_thisActor.i_ai1);
			addTimer(FORTH_TIMER, 4200 + Rnd.get(900));
			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), Rnd.get(3) + 1));
		}
		else if(timerId == FORTH_TIMER)
		{
			_thisActor.i_ai1 = 0;
			_thisActor.changeNpcState(4);
			addTimer(END_TIMER, 500);
			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), Rnd.get(3) + 1));
		}
		else if(timerId == END_TIMER)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 instanceof L2Player)
			{
				L2Player player = (L2Player) c0;
				if(_thisActor.isInRange(player, 300) && !player.isDead() && !player.isDeleting())
				{
					player.stopEffects(B_PLASMA1.getAbnormals());
					player.stopEffects(C_PLASMA1.getAbnormals());
					player.stopEffects(R_PLASMA1.getAbnormals());
				}
			}
			_thisActor.c_ai0 = 0;
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();
		if(target instanceof L2Player)
		{
			L2Player player = (L2Player) target;
			int i1, i2, i3;
			L2Effect effect = player.getEffectBySkill(B_PLASMA1);
			i1 = effect == null ? 0 : effect.getAbnormalLevel();
			effect = player.getEffectBySkill(C_PLASMA1);
			i2 = effect == null ? 0 : effect.getAbnormalLevel();
			effect = player.getEffectBySkill(R_PLASMA1);
			i3 = effect == null ? 0 : effect.getAbnormalLevel();
			if(i1 == 3 && i2 == 0 && i3 == 0)
			{
				player.sendMessage(new CustomMessage("fs1801150", player));
				player.stopEffect(B_PLASMA1.getId());
				_thisActor.altUseSkill(Rnd.chance(70) ? B_BUFF_1 : B_BUFF_2, player);
				int c1 = Rnd.get(10000);
				int c2 = Rnd.get(20);
				if(c1 == 0 && c2 != 0)
				{
					if(player.getInventoryLimit() - player.getInventory().getSize() > 1)
						player.addItem("Loot", 15488, 1, _thisActor, true);
				}
				else if(c1 == 0)
					if(player.getInventoryLimit() - player.getInventory().getSize() > 1)
						player.addItem("Loot", 15489, 1, _thisActor, true);

				addTimer(END_TIMER, 3000);
			}
			else if(i1 == 0 && i2 == 3 && i3 == 0)
			{
				player.sendMessage(new CustomMessage("fs1801151", player));
				player.stopEffect(C_PLASMA1.getId());
				_thisActor.altUseSkill(Rnd.chance(70) ? C_BUFF_1 : C_BUFF_2, player);
				int c1 = Rnd.get(10000);
				int c2 = Rnd.get(20);
				if(c1 == 0 && c2 != 0)
				{
					if(player.getInventoryLimit() - player.getInventory().getSize() > 1)
						player.addItem("Loot", 15488, 1, _thisActor, true);
				}
				else if(c1 == 0)
					if(player.getInventoryLimit() - player.getInventory().getSize() > 1)
						player.addItem("Loot", 15489, 1, _thisActor, true);

				addTimer(END_TIMER, 3000);
			}
			else if(i1 == 0 && i2 == 0 && i3 == 3)
			{
				player.sendMessage(new CustomMessage("fs1801152", player));
				player.stopEffect(R_PLASMA1.getId());
				_thisActor.altUseSkill(Rnd.chance(70) ? R_BUFF_1 : R_BUFF_2, player);
				int c1 = Rnd.get(10000);
				int c2 = Rnd.get(20);
				if(c1 == 0 && c2 != 0)
				{
					if(player.getInventoryLimit() - player.getInventory().getSize() > 1)
						player.addItem("Loot", 15488, 1, _thisActor, true);
				}
				else if(c1 == 0)
					if(player.getInventoryLimit() - player.getInventory().getSize() > 1)
						player.addItem("Loot", 15489, 1, _thisActor, true);

				addTimer(END_TIMER, 3000);
			}
			else if(i1 + i2 + i3 == 3)
			{
				if(i1 == 1 && i2 == 1 && i3 == 1)
				{
					player.stopEffect(C_PLASMA1.getId());
					player.stopEffect(B_PLASMA1.getId());
					player.stopEffect(R_PLASMA1.getId());
					player.sendMessage(new CustomMessage("fs1801153", player));
					switch(Rnd.get(3))
					{
						case 0:
							_thisActor.altUseSkill(Rnd.chance(70) ? B_BUFF_1 : B_BUFF_2, player);
							break;
						case 1:
							_thisActor.altUseSkill(Rnd.chance(70) ? C_BUFF_1 : C_BUFF_2, player);
							break;
						case 2:
							_thisActor.altUseSkill(Rnd.chance(70) ? R_BUFF_1 : R_BUFF_2, player);
							break;
					}
					int c1 = Rnd.get(10000);
					int c2 = Rnd.get(20);
					if(c1 == 0 && c2 != 0)
					{
						if(player.getInventoryLimit() - player.getInventory().getSize() > 1)
							player.addItem("Loot", 15488, 1, _thisActor, true);
					}
					else if(c1 == 0)
						if(player.getInventoryLimit() - player.getInventory().getSize() > 1)
							player.addItem("Loot", 15489, 1, _thisActor, true);

					addTimer(END_TIMER, 3000);
				}
				else
				{
					player.stopEffect(C_PLASMA1.getId());
					player.stopEffect(B_PLASMA1.getId());
					player.stopEffect(R_PLASMA1.getId());
					player.sendMessage(new CustomMessage("fs1801154", player));
					addTimer(END_TIMER, 3000);
				}
			}
			else
				addTimer(END_TIMER, 1000);
		}
	}

	@Override
	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		if(caster instanceof L2Player && skill.getId() == 9060 && caster.getStoredId() == _thisActor.c_ai0 && _thisActor.i_ai1 != 0 && _thisActor.i_ai0 == 0)
		{
			int i1, i2, i3;
			L2Effect effect = caster.getEffectBySkill(B_PLASMA1);
			i1 = effect == null ? 0 : effect.getAbnormalLevel();
			effect = caster.getEffectBySkill(C_PLASMA1);
			i2 = effect == null ? 0 : effect.getAbnormalLevel();
			effect = caster.getEffectBySkill(R_PLASMA1);
			i3 = effect == null ? 0 : effect.getAbnormalLevel();
			blockTimer(FIRST_TIMER);
			blockTimer(SECOND_TIMER);
			blockTimer(THIRD_TIMER);
			blockTimer(FORTH_TIMER);
			_thisActor.i_ai0 = 1;
			if(_thisActor.i_ai1 == 1)
			{
				if(i1 == 0)
					_thisActor.altUseSkill(B_PLASMA1, caster);
				else if(i1 == 1)
					_thisActor.altUseSkill(B_PLASMA2, caster);
				else if(i1 == 2)
					_thisActor.altUseSkill(B_PLASMA3, caster);
			}
			else if(_thisActor.i_ai1 == 2)
			{
				if(i2 == 0)
					_thisActor.altUseSkill(C_PLASMA1, caster);
				else if(i2 == 1)
					_thisActor.altUseSkill(C_PLASMA2, caster);
				else if(i2 == 2)
					_thisActor.altUseSkill(C_PLASMA3, caster);
			}
			else if(_thisActor.i_ai1 == 3)
			{
				if(i3 == 0)
					_thisActor.altUseSkill(R_PLASMA1, caster);
				else if(i3 == 1)
					_thisActor.altUseSkill(R_PLASMA2, caster);
				else if(i3 == 2)
					_thisActor.altUseSkill(R_PLASMA3, caster);
			}
		}
	}

	public boolean onTalk(L2Player player)
	{
		return true;
	}
}
