package commands.voiced;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IVoicedCommandHandler;
import ru.l2gw.gameserver.handler.VoicedCommandHandler;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.skills.Formulas;
import ru.l2gw.gameserver.skills.Stats;
import ru.l2gw.util.Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Help extends Functions implements IVoicedCommandHandler, ScriptFile
{
	public static L2Object self;
	public static L2NpcInstance npc;

	private String[] _commandList = new String[] { "help", "whoami", "whoiam", "heading", "whofake", "whofaketrue", "sweep" };

	public void onLoad()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}

	public boolean useVoicedCommand(String command, L2Player activeChar, String args)
	{
		command = command.intern();
		if(command.equalsIgnoreCase("help"))
		{
			String dialog = Files.read("data/scripts/commands/voiced/help.htm");
			show(dialog, activeChar);
			return true;
		}
		if(command.equalsIgnoreCase("whoami") || command.equalsIgnoreCase("whoiam"))
		{
			showInfo(activeChar);
			return true;
		}
		if(command.equalsIgnoreCase("heading"))
		{
			activeChar.sendMessage(String.valueOf(activeChar.getHeading()));
			return true;
		}
		if(command.equalsIgnoreCase("whofake"))
		{

			activeChar.sendMessage("No fake players, all real");

			StringBuilder sb = new StringBuilder();
			sb.append("Attempt to see fake players.\n");
			sb.append("Date: ").append(new Date()).append("\n");
			sb.append("Player: ").append(activeChar.getName()).append("\n");
			sb.append("Account: ").append(activeChar.getNetConnection().getLoginName()).append("\n");
			sb.append("IP: ").append(activeChar.getNetConnection().getIpAddr()).append("\n\n");

			synchronized (this)
			{

				File f = new File("whofake.txt");
				if(!f.exists())
				{
					try
					{
						f.createNewFile();
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}

				FileOutputStream fis = null;
				try
				{
					fis = new FileOutputStream(f, true);
					fis.write(sb.toString().getBytes("UTF-8"));
				}
				catch(FileNotFoundException e)
				{
					e.printStackTrace();
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						if(fis != null)
						{
							fis.close();
						}
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
				}
			}

			return false;
		}
		if(command.equalsIgnoreCase("sweep"))
		{
			if(activeChar.getSkillLevel(42) > 0)
				for(L2Character target : activeChar.getKnownCharacters(300, 200))
					if(target.isMonster() && target.isDead() && ((L2MonsterInstance) target).isSweepActive())
					{
						activeChar.getAI().Cast(activeChar.getKnownSkill(42), target);
						return true;
					}
			return false;
		}
		return false;
	}

	public static void showInfo(L2Player player)
	{
		if(player == null)
			return;
		StringBuilder dialog = new StringBuilder("<html><body>");

		NumberFormat df = NumberFormat.getNumberInstance(Locale.ENGLISH);
		df.setMaximumFractionDigits(4);
		df.setMinimumFractionDigits(1);

		dialog.append("<center><font color=\"LEVEL\">Basic info</font></center><br><table width=\"70%\">");

		dialog.append("<tr><td>Name</td><td>").append(player.getName()).append("</td></tr>");
		dialog.append("<tr><td>Level</td><td>").append(player.getLevel()).append("</td></tr>");
		dialog.append("<tr><td>Class</td><td>").append(player.getClassId().name()).append("</td></tr>");
		dialog.append("<tr><td>Object id</td><td>").append(player.getObjectId()).append("</td></tr>");
		dialog.append("<tr><td>IP</td><td>").append(player.getNetConnection().getIpAddr()).append("</td></tr>");
		dialog.append("<tr><td>Login</td><td>").append(player.getAccountName()).append("</td></tr>");

		dialog.append("</table><br><center><font color=\"LEVEL\">Stats</font></center><br><table width=\"70%\">");

		dialog.append("<tr><td>HP regeneration</td><td>").append(df.format(Formulas.calcHpRegen(player))).append("</td></tr>");
		dialog.append("<tr><td>MP regeneration</td><td>").append(df.format(Formulas.calcMpRegen(player))).append("</td></tr>");
		dialog.append("<tr><td>CP regeneration</td><td>").append(df.format(Formulas.calcCpRegen(player))).append("</td></tr>");
		dialog.append("<tr><td>HP drain</td><td>").append(df.format(player.calcStat(Stats.ABSORB_DAMAGE_PERCENT, 0, null, null))).append("%</td></tr>");
		dialog.append("<tr><td>HP gain bonus</td><td>").append(df.format(player.calcStat(Stats.HEAL_EFFECTIVNESS, 100, null, null) - 100)).append("%</td></tr>");
		dialog.append("<tr><td>MP gain bonus</td><td>").append(df.format(player.calcStat(Stats.MANAHEAL_EFFECTIVNESS, 100, null, null) - 100)).append("%</td></tr>");
		dialog.append("<tr><td>Critical damage</td><td>").append(df.format(player.calcStat(Stats.CRITICAL_DAMAGE, 100, null, null) + 100)).append("% + ").append((int) player.calcStat(Stats.CRITICAL_DAMAGE_STATIC, 0, null, null)).append("</td></tr>");
		dialog.append("<tr><td>Magic critical</td><td>").append(df.format(player.getCriticalMagic(null, null) / 10)).append("%</td></tr>");

		dialog.append("</table><br><center><font color=\"LEVEL\">Resists</font></center><br><table width=\"70%\">");

		int FIRE_ATTRIBUTE = (int) player.calcStat(Stats.FIRE_ATTRIBUTE, 0, null, null);
		if(FIRE_ATTRIBUTE != 0)
			dialog.append("<tr><td>Fire</td><td>").append(FIRE_ATTRIBUTE).append("%</td></tr>");

		int WIND_ATTRIBUTE = (int) player.calcStat(Stats.WIND_ATTRIBUTE, 0, null, null);
		if(WIND_ATTRIBUTE != 0)
			dialog.append("<tr><td>Wind</td><td>").append(WIND_ATTRIBUTE).append("%</td></tr>");

		int WATER_ATTRIBUTE = (int) player.calcStat(Stats.WATER_ATTRIBUTE, 0, null, null);
		if(WATER_ATTRIBUTE != 0)
			dialog.append("<tr><td>Water</td><td>").append(WATER_ATTRIBUTE).append("%</td></tr>");

		int EARTH_ATTRIBUTE = (int) player.calcStat(Stats.EARTH_ATTRIBUTE, 0, null, null);
		if(EARTH_ATTRIBUTE != 0)
			dialog.append("<tr><td>Earth</td><td>").append(EARTH_ATTRIBUTE).append("%</td></tr>");

		int HOLY_ATTRIBUTE = (int) player.calcStat(Stats.HOLY_ATTRIBUTE, 0, null, null);
		if(HOLY_ATTRIBUTE != 0)
			dialog.append("<tr><td>Light</td><td>").append(HOLY_ATTRIBUTE).append("%</td></tr>");

		int DARK_ATTRIBUTE = (int) player.calcStat(Stats.DARK_ATTRIBUTE, 0, null, null);
		if(DARK_ATTRIBUTE != 0)
			dialog.append("<tr><td>Darkness</td><td>").append(DARK_ATTRIBUTE).append("%</td></tr>");

		int BLEED_RECEPTIVE = 100 - (int) player.calcStat(Stats.BLEED_RECEPTIVE, 100, null, null);
		if(BLEED_RECEPTIVE != 0)
			dialog.append("<tr><td>Bleed</td><td>").append(BLEED_RECEPTIVE).append("%</td></tr>");

		int POISON_RECEPTIVE = 100 - (int) player.calcStat(Stats.POISON_RECEPTIVE, 100, null, null);
		if(POISON_RECEPTIVE != 0)
			dialog.append("<tr><td>Poison</td><td>").append(POISON_RECEPTIVE).append("%</td></tr>");

		int DEATH_RECEPTIVE = 100 - (int) player.calcStat(Stats.DEATH_RECEPTIVE, 100, null, null);
		if(DEATH_RECEPTIVE != 0)
			dialog.append("<tr><td>Death</td><td>").append(DEATH_RECEPTIVE).append("%</td></tr>");

		int STUN_RECEPTIVE = 100 - (int) player.calcStat(Stats.STUN_RECEPTIVE, 100, null, null);
		if(STUN_RECEPTIVE != 0)
			dialog.append("<tr><td>Stun</td><td>").append(STUN_RECEPTIVE).append("%</td></tr>");

		int ROOT_RECEPTIVE = 100 - (int) player.calcStat(Stats.ROOT_RECEPTIVE, 100, null, null);
		if(ROOT_RECEPTIVE != 0)
			dialog.append("<tr><td>Root</td><td>").append(ROOT_RECEPTIVE).append("%</td></tr>");

		int SLEEP_RECEPTIVE = 100 - (int) player.calcStat(Stats.SLEEP_RECEPTIVE, 100, null, null);
		if(SLEEP_RECEPTIVE != 0)
			dialog.append("<tr><td>Sleep</td><td>").append(SLEEP_RECEPTIVE).append("%</td></tr>");

		int PARALYZE_RECEPTIVE = 100 - (int) player.calcStat(Stats.PARALYZE_RECEPTIVE, 100, null, null);
		if(PARALYZE_RECEPTIVE != 0)
			dialog.append("<tr><td>Paralyze</td><td>").append(PARALYZE_RECEPTIVE).append("%</td></tr>");

		int FEAR_RECEPTIVE = 100 - (int) player.calcStat(Stats.FEAR_RECEPTIVE, 100, null, null);
		if(FEAR_RECEPTIVE != 0)
			dialog.append("<tr><td>Fear</td><td>").append(FEAR_RECEPTIVE).append("%</td></tr>");

		int DEBUFF_RECEPTIVE = 100 - (int) player.calcStat(Stats.DEBUFF_RECEPTIVE, 100, null, null);
		if(DEBUFF_RECEPTIVE != 0)
			dialog.append("<tr><td>Debuff</td><td>").append(DEBUFF_RECEPTIVE).append("%</td></tr>");

		int CANCEL_RECEPTIVE = 100 - (int) player.calcStat(Stats.CANCEL_RECEPTIVE, 100, null, null);
		if(CANCEL_RECEPTIVE != 0)
			dialog.append("<tr><td>Cancel</td><td>").append(CANCEL_RECEPTIVE).append("%</td></tr>");

		int SWORD_WPN_RECEPTIVE = 100 - (int) player.calcStat(Stats.SWORD_WPN_RECEPTIVE, 100, null, null);
		if(SWORD_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Sword</td><td>").append(SWORD_WPN_RECEPTIVE).append("%</td></tr>");

		int DUAL_WPN_RECEPTIVE = 100 - (int) player.calcStat(Stats.DUAL_WPN_RECEPTIVE, 100, null, null);
		if(DUAL_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Dual Sword</td><td>").append(DUAL_WPN_RECEPTIVE).append("%</td></tr>");

		int BLUNT_WPN_RECEPTIVE = 100 - (int) player.calcStat(Stats.BLUNT_WPN_RECEPTIVE, 100, null, null);
		if(BLUNT_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Blunt</td><td>").append(BLUNT_WPN_RECEPTIVE).append("%</td></tr>");

		int DAGGER_WPN_RECEPTIVE = 100 - (int) player.calcStat(Stats.DAGGER_WPN_RECEPTIVE, 100, null, null);
		if(DAGGER_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Dagger/Rapier</td><td>").append(DAGGER_WPN_RECEPTIVE).append("%</td></tr>");

		int BOW_WPN_RECEPTIVE = 100 - (int) player.calcStat(Stats.BOW_WPN_RECEPTIVE, 100, null, null);
		if(BOW_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Bow/Crossbow</td><td>").append(BOW_WPN_RECEPTIVE).append("%</td></tr>");

		int CROSSBOW_WPN_RECEPTIVE = 100 - (int) player.calcStat(Stats.CROSSBOW_WPN_RECEPTIVE, 100, null, null);
		if(CROSSBOW_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Crossbow</td><td>").append(CROSSBOW_WPN_RECEPTIVE).append("%</td></tr>");

		int POLE_WPN_RECEPTIVE = 100 - (int) player.calcStat(Stats.POLE_WPN_RECEPTIVE, 100, null, null);
		if(POLE_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Polearm</td><td>").append(POLE_WPN_RECEPTIVE).append("%</td></tr>");

		int FIST_WPN_RECEPTIVE = 100 - (int) player.calcStat(Stats.FIST_WPN_RECEPTIVE, 100, null, null);
		if(FIST_WPN_RECEPTIVE != 0)
			dialog.append("<tr><td>Fist weapons</td><td>").append(FIST_WPN_RECEPTIVE).append("%</td></tr>");

		int CRIT_CHANCE_RECEPTIVE = 100 - (int) player.calcStat(Stats.CRIT_CHANCE_RECEPTIVE, 100, null, null);
		if(CRIT_CHANCE_RECEPTIVE != 0)
			dialog.append("<tr><td>Crit get chance</td><td>").append(CRIT_CHANCE_RECEPTIVE).append("%</td></tr>");

		int CRIT_DAMAGE_RECEPTIVE = 100 - (int) player.calcStat(Stats.CRIT_DAMAGE_RECEPTIVE, 100, null, null);
		if(CRIT_DAMAGE_RECEPTIVE != 0)
			dialog.append("<tr><td>Crit get damage</td><td>").append(CRIT_DAMAGE_RECEPTIVE).append("%</td></tr>");

		if(FIRE_ATTRIBUTE == 0 && WIND_ATTRIBUTE == 0 && WATER_ATTRIBUTE == 0 && EARTH_ATTRIBUTE == 0 && DARK_ATTRIBUTE == 0 && HOLY_ATTRIBUTE // primary elements
		== 0 && BLEED_RECEPTIVE == 0 && DEATH_RECEPTIVE == 0 && STUN_RECEPTIVE // phys debuff
		== 0 && POISON_RECEPTIVE == 0 && ROOT_RECEPTIVE == 0 && SLEEP_RECEPTIVE == 0 && PARALYZE_RECEPTIVE == 0 && FEAR_RECEPTIVE == 0 && DEBUFF_RECEPTIVE == 0 && CANCEL_RECEPTIVE // mag debuff
		== 0 && SWORD_WPN_RECEPTIVE == 0 && DUAL_WPN_RECEPTIVE == 0 && BLUNT_WPN_RECEPTIVE == 0 && DAGGER_WPN_RECEPTIVE == 0 && BOW_WPN_RECEPTIVE == 0 && CROSSBOW_WPN_RECEPTIVE == 0 && POLE_WPN_RECEPTIVE == 0 && FIST_WPN_RECEPTIVE // weapons
		== 0 && CRIT_CHANCE_RECEPTIVE == 0 && CRIT_DAMAGE_RECEPTIVE == 0 // other
		)
			dialog.append("</table>No resists</body></html>");
		else
			dialog.append("</table></body></html>");
		show(dialog.toString(), player);
	}

	public String[] getVoicedCommandList()
	{
		return _commandList;
	}
}
