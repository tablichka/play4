package quests._255_Tutorial;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Tutorial Epilogue off-like
 *
 * @author: rage
 * <p/>
 * Tutorial server events:
 * 0x1: Changing Point-Of-View
 * 0x2: Using the Mouse Wheel
 * 0x8: Conversation with a Newbie Helper
 * *0x100: HP Regeneration
 * *0x200: Penalty for Dying
 * ==============================================
 * 0x400: reached level 5
 * 0x8000000: reached level 6
 * 0x800: reached level 7
 * 0x10000000: Some quest ? reached level 9
 * 0x20000000: Some quest ? reached level 9
 * 0x40000000: Some quest ? reached level 9
 * 0x4000000: reaches level 15
 * 0x1000: reaches level 20
 * 0x1000000: reached level 35
 * 0x4000: reached level 40
 * 0x2000000: reached level 75
 * 0x8000: reached level 76
 * 0x20: reached level 36
 * 0x40: reached level 61
 * 0x80: reached level 73
 * ==============================================
 * *0x200000: Item get
 * *0x100000: Blue Gemestone
 * *0x800000: HP Regenarated
 */
public class _255_Tutorial extends Quest
{
	public _255_Tutorial()
	{
		super(255, "_255_Tutorial", "Tutorial", true);
		addKillId(18342);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.startsWith("EW"))
		{
			L2Player player = st.getPlayer();
			if(player.getLevel() < 6)
			{
				if(st.isCompleted())
					return null;

				int i0 = st.getInt("t");
				int i1 = 0;
				if(i0 > 0)
				{
					i1 = i0 & 0xFF;
					i0 &= 0x7FFFFF00;
				}

				switch(i1)
				{
					case 0:
						st.startQuestTimer("QT1", 10000);
						i0 = (2147483392 & ~(0x800000 | 0x100000));
						st.set("t", 1 | i0);
						if(st.getInt("t1") < 0)
							st.set("t1", -2);
						break;
					case 1:
						st.showQuestionMark(1);
						st.playTutorialVoice("tutorial_voice_006", 1000);
						st.playSound(SOUND_TUTORIAL);
						break;
					case 2:
						if(player.getQuestState("201-206") != null) // TODO Quest ?
							st.showQuestionMark(6);
						else
							st.showQuestionMark(2);
						st.playSound(SOUND_TUTORIAL);
						break;
					case 3:
						if(st.haveQuestItems(6353))
						{
							st.showQuestionMark(5);
							st.playSound(SOUND_TUTORIAL);
						}
						else if(st.getInt("t1") == 2)
						{
							st.showQuestionMark(3);
							st.playSound(SOUND_TUTORIAL);
						}
						break;
					case 4:
						st.showQuestionMark(12);
						st.playSound(SOUND_TUTORIAL);
						break;
				}
				st.onTutorialClientEvent(i0);
			}
			else if(player.getLevel() == 18 && (player.getQuestState("_10276_MutatedKaneusGludio") == null || !player.getQuestState("_10276_MutatedKaneusGludio").isCompleted()))
			{
				st.showQuestionMark(33);
				st.playSound(SOUND_TUTORIAL);
			}
			else if(player.getLevel() == 28 && (player.getQuestState("_10277_MutatedKaneusDion") == null || !player.getQuestState("_10277_MutatedKaneusDion").isCompleted()))
			{
				st.showQuestionMark(33);
				st.playSound(SOUND_TUTORIAL);
			}
			else if(player.getLevel() == 38 && (player.getQuestState("_10278_MutatedKaneusHeine") == null || !player.getQuestState("_10278_MutatedKaneusHeine").isCompleted()))
			{
				st.showQuestionMark(33);
				st.playSound(SOUND_TUTORIAL);
			}
			else if(player.getLevel() == 48 && (player.getQuestState("_10279_MutatedKaneusOren") == null || !player.getQuestState("_10279_MutatedKaneusOren").isCompleted()))
			{
				st.showQuestionMark(33);
				st.playSound(SOUND_TUTORIAL);
			}
			else if(player.getLevel() == 58 && (player.getQuestState("_10280_MutatedKaneusSchuttgart") == null || !player.getQuestState("_10280_MutatedKaneusSchuttgart").isCompleted()))
			{
				st.showQuestionMark(33);
				st.playSound(SOUND_TUTORIAL);
			}
			else if(player.getLevel() == 68 && (player.getQuestState("_10281_MutatedKaneusRune") == null || !player.getQuestState("_10281_MutatedKaneusRune").isCompleted()))
			{
				st.showQuestionMark(33);
				st.playSound(SOUND_TUTORIAL);
			}
			else if(player.getLevel() == 79 && (player.getQuestState("_192_SevenSignSeriesOfDoubt") == null || !player.getQuestState("_192_SevenSignSeriesOfDoubt").isCompleted()))
			{
				st.showQuestionMark(34);
				st.playSound(SOUND_TUTORIAL);
			}
			else if(player.getLevel() == 81 && !player.isQuestComplete(10292))
			{
				st.showQuestionMark(35);
				st.playSound(SOUND_TUTORIAL);
			}
		}
		else if(event.equals("QT1"))
		{
			if(st.getInt("t1") == -2)
			{
				L2Player player = st.getPlayer();
				switch(player.getClassId())
				{
					case fighter:
						st.playTutorialVoice("tutorial_voice_001a", 2000);
						st.showTutorialHTML("tutorial_human_fighter001.htm");
						break;
					case mage:
						st.playTutorialVoice("tutorial_voice_001b", 2000);
						st.showTutorialHTML("tutorial_human_mage001.htm");
						break;
					case elvenFighter:
						st.playTutorialVoice("tutorial_voice_001c", 2000);
						st.showTutorialHTML("tutorial_elven_fighter001.htm");
						break;
					case elvenMage:
						st.playTutorialVoice("tutorial_voice_001d", 2000);
						st.showTutorialHTML("tutorial_elven_mage001.htm");
						break;
					case darkFighter:
						st.playTutorialVoice("tutorial_voice_001e", 2000);
						st.showTutorialHTML("tutorial_delf_fighter001.htm");
						break;
					case darkMage:
						st.playTutorialVoice("tutorial_voice_001f", 2000);
						st.showTutorialHTML("tutorial_delf_mage001.htm");
						break;
					case orcFighter:
						st.playTutorialVoice("tutorial_voice_001g", 2000);
						st.showTutorialHTML("tutorial_orc_fighter001.htm");
						break;
					case orcMage:
						st.playTutorialVoice("tutorial_voice_001h", 2000);
						st.showTutorialHTML("tutorial_orc_mage001.htm");
						break;
					case dwarvenFighter:
						st.playTutorialVoice("tutorial_voice_001i", 2000);
						st.showTutorialHTML("tutorial_dwarven_fighter001.htm");
						break;
					case maleSoldier:
					case femaleSoldier:
						st.playTutorialVoice("tutorial_voice_001k", 2000);
						st.showTutorialHTML("tutorial_kamael001.htm");
						break;
				}
				if(!st.haveQuestItems(5588))
					st.giveItems(5566, 1);

				st.startQuestTimer("QT1", 30000);
				st.set("t1", -3);
			}
			else if(st.getInt("t1") == -3)
				st.playTutorialVoice("tutorial_voice_002", 1000);
			else if(st.getInt("t1") == -4)
			{
				st.playTutorialVoice("tutorial_voice_008", 1000);
				st.set("t1", -5);
			}
		}
		// Tutorial server events
		else if(event.startsWith("TE"))
			tutorialEvent(Integer.parseInt(event.substring(2)), st);
		else if(event.equals("LU"))
			levelUp(st);
		else if(event.startsWith("QM"))
			questionMark(Integer.parseInt(event.substring(2)), st);
		else if(event.startsWith("menu_select"))
		{
			String[] var = event.split(" ");
			menuSelect(Integer.parseInt(var[1]), Integer.parseInt(var[2]), st);
		}

		return null;
	}

	private void questionMark(int questionId, QuestState st)
	{
		L2Player player = st.getPlayer();
		int i0 = st.getInt("t") & 0x7FFFFF00;
		switch(questionId)
		{
			case 1:
				st.playTutorialVoice("tutorial_voice_007", 3500);
				if(st.getInt("t1") < 0)
					st.set("t1", -5);

				switch(player.getClassId())
				{
					case fighter:
						st.showTutorialHTML("tutorial_human_fighter007.htm");
						st.showRadar(-71424, 258336, -3109, 2);
						break;
					case mage:
						st.showTutorialHTML("tutorial_human_fighter007.htm");
						st.showRadar(-91036, 248044, -3568, 2);
						break;
					case elvenFighter:
					case elvenMage:
						st.showTutorialHTML("tutorial_human_fighter007.htm");
						st.showRadar(46112, 41200, -3504, 2);
						break;
					case darkFighter:
					case darkMage:
						st.showTutorialHTML("tutorial_human_fighter007.htm");
						st.showRadar(28384, 11056, -4233, 2);
						break;
					case orcFighter:
					case orcMage:
						st.showTutorialHTML("tutorial_human_fighter007.htm");
						st.showRadar(-56736, -113680, -672, 2);
						break;
					case dwarvenFighter:
						st.showTutorialHTML("tutorial_human_fighter007.htm");
						st.showRadar(108567, -173994, -406, 2);
						break;
					case maleSoldier:
					case femaleSoldier:
						st.showTutorialHTML("tutorial_human_fighter007.htm");
						st.showRadar(-125872, 38016, 1251, 2);
						break;
				}

				st.set("t", i0 | 2);
				break;
			case 2:
				if(player.getClassId() == ClassId.fighter)
					st.showTutorialHTML("tutorial_human_fighter008.htm");
				else if(player.getClassId() == ClassId.mage)
					st.showTutorialHTML("tutorial_human_mage008.htm");
				else if(player.getClassId() == ClassId.elvenFighter || player.getClassId() == ClassId.elvenMage)
					st.showTutorialHTML("tutorial_elf008.htm");
				else if(player.getClassId() == ClassId.darkFighter || player.getClassId() == ClassId.darkMage)
					st.showTutorialHTML("tutorial_delf008.htm");
				else if(player.getClassId() == ClassId.orcFighter || player.getClassId() == ClassId.orcMage)
					st.showTutorialHTML("tutorial_orc008.htm");
				else if(player.getClassId() == ClassId.dwarvenFighter)
					st.showTutorialHTML("tutorial_dwarven_fighter008.htm");
				else if(player.getClassId() == ClassId.maleSoldier || player.getClassId() == ClassId.femaleSoldier)
					st.showTutorialHTML("tutorial_kamael008.htm");
				break;
			case 3:
				st.showTutorialHTML("tutorial_09.htm");
				st.set("t", st.getInt("t") | 0x100000);
				st.onTutorialClientEvent(i0 | 0x100000);
				break;
			case 4:
				st.showTutorialHTML("tutorial_10.htm");
				break;
			case 5:
				if(player.getClassId() == ClassId.fighter)
					st.showRadar(-71424, 258336, -3109, 2);
				else if(player.getClassId() == ClassId.mage)
					st.showRadar(-91036, 248044, -3568, 2);
				else if(player.getClassId() == ClassId.elvenFighter || player.getClassId() == ClassId.elvenMage)
					st.showRadar(46112, 41200, -3504, 2);
				else if(player.getClassId() == ClassId.darkFighter || player.getClassId() == ClassId.darkMage)
					st.showRadar(28384, 11056, -4233, 2);
				else if(player.getClassId() == ClassId.orcFighter || player.getClassId() == ClassId.orcMage)
					st.showRadar(-56736, -113680, -672, 2);
				else if(player.getClassId() == ClassId.dwarvenFighter)
					st.showRadar(108567, -173994, -406, 2);
				else if(player.getClassId() == ClassId.maleSoldier || player.getClassId() == ClassId.femaleSoldier)
					st.showRadar(-125872, 38016, 1251, 2);
				st.showTutorialHTML("tutorial_11.htm");
				break;
			case 7:
				st.showTutorialHTML("tutorial_15.htm");
				st.set("t", i0 | 5);
				break;
			case 8:
				st.showTutorialHTML("tutorial_18.htm");
				break;
			case 9:
				if(!player.isMageClass())
				{
					if(player.getRace() == Race.human || player.getRace() == Race.elf || player.getRace() == Race.darkelf)
						st.showTutorialHTML("tutorial_fighter017.htm");
					else if(player.getRace() == Race.dwarf)
						st.showTutorialHTML("tutorial_fighter_dwarf017.htm");
					else if(player.getRace() == Race.orc)
						st.showTutorialHTML("tutorial_fighter_orc017.htm");
				}
				if(player.getRace() == Race.kamael)
					st.showTutorialHTML("tutorial_kamael017.htm");
				break;
			case 10:
				st.showTutorialHTML("tutorial_19.htm");
				break;
			case 11:
				if(player.getRace() == Race.human)
					st.showTutorialHTML("tutorial_mage020.htm");
				else if(player.getRace() == Race.elf || player.getRace() == Race.darkelf)
					st.showTutorialHTML("tutorial_mage_elf020.htm");
				else if(player.getRace() == Race.orc)
					st.showTutorialHTML("tutorial_mage_orc020.htm");
				break;
			case 12:
				st.showTutorialHTML("tutorial_15.htm");
				break;
			case 13:
				if(player.getClassId() == ClassId.fighter)
					st.showTutorialHTML("tutorial_21.htm");
				else if(player.getClassId() == ClassId.mage)
					st.showTutorialHTML("tutorial_21a.htm");
				else if(player.getClassId() == ClassId.elvenFighter)
					st.showTutorialHTML("tutorial_21b.htm");
				else if(player.getClassId() == ClassId.elvenMage)
					st.showTutorialHTML("tutorial_21c.htm");
				else if(player.getClassId() == ClassId.orcFighter)
					st.showTutorialHTML("tutorial_21d.htm");
				else if(player.getClassId() == ClassId.orcMage)
					st.showTutorialHTML("tutorial_21e.htm");
				else if(player.getClassId() == ClassId.dwarvenFighter)
					st.showTutorialHTML("tutorial_21f.htm");
				else if(player.getClassId() == ClassId.darkFighter)
					st.showTutorialHTML("tutorial_21g.htm");
				else if(player.getClassId() == ClassId.darkMage)
					st.showTutorialHTML("tutorial_21h.htm");
				else if(player.getClassId() == ClassId.maleSoldier)
					st.showTutorialHTML("tutorial_21i.htm");
				else if(player.getClassId() == ClassId.femaleSoldier)
					st.showTutorialHTML("tutorial_21j.htm");
				break;
			case 15:
				if(player.getRace() != Race.kamael)
					st.showTutorialHTML("tutorial_28.htm");
				else if(player.getClassId() == ClassId.trooper)
					st.showTutorialHTML("tutorial_28a.htm");
				else if(player.getClassId() == ClassId.warder)
					st.showTutorialHTML("tutorial_28b.htm");
				break;
			case 16:
				st.showTutorialHTML("tutorial_30.htm");
				break;
			case 17:
				st.showTutorialHTML("tutorial_27.htm");
				break;
			case 19:
				st.showTutorialHTML("tutorial_07.htm");
				break;
			case 20:
				st.showTutorialHTML("tutorial_14.htm");
				break;
			case 21:
				st.showTutorialHTML("tutorial_newbie001.htm");
				break;
			case 22:
				st.showTutorialHTML("tutorial_14.htm");
				break;
			case 23:
				st.showTutorialHTML("tutorial_24.htm");
				break;
			case 24:
				if(player.getRace() == Race.human)
					st.showTutorialHTML("tutorial_newbie003a.htm");
				if(player.getRace() == Race.elf)
					st.showTutorialHTML("tutorial_newbie003b.htm");
				if(player.getRace() == Race.darkelf)
					st.showTutorialHTML("tutorial_newbie003c.htm");
				if(player.getRace() == Race.orc)
					st.showTutorialHTML("tutorial_newbie003d.htm");
				if(player.getRace() == Race.dwarf)
					st.showTutorialHTML("tutorial_newbie003e.htm");
				if(player.getRace() == Race.kamael)
					st.showTutorialHTML("tutorial_newbie003f.htm");
				break;
			case 25:
				if(player.getClassId() == ClassId.fighter)
					st.showTutorialHTML("tutorial_newbie002a.htm");
				if(player.getClassId() == ClassId.mage)
					st.showTutorialHTML("tutorial_newbie002b.htm");
				if(player.getClassId() == ClassId.elvenFighter || player.getClassId() == ClassId.elvenMage)
					st.showTutorialHTML("tutorial_newbie002c.htm");
				if(player.getClassId() == ClassId.darkMage)
					st.showTutorialHTML("tutorial_newbie002d.htm");
				if(player.getClassId() == ClassId.darkFighter)
					st.showTutorialHTML("tutorial_newbie002e.htm");
				if(player.getClassId() == ClassId.dwarvenFighter)
					st.showTutorialHTML("tutorial_newbie002g.htm");
				if(player.getClassId() == ClassId.orcMage || player.getClassId() == ClassId.orcFighter)
					st.showTutorialHTML("tutorial_newbie002f.htm");
				if(player.getClassId() == ClassId.maleSoldier || player.getClassId() == ClassId.femaleSoldier)
					st.showTutorialHTML("tutorial_newbie002i.htm");
				break;
			case 26:
				if(!player.isMageClass() || player.getClassId() == ClassId.orcMage)
					st.showTutorialHTML("tutorial_newbie004a.htm");
				else
					st.showTutorialHTML("tutorial_newbie004b.htm");
				break;
			case 27:
				if(player.getClassId() == ClassId.fighter || player.getClassId() == ClassId.orcMage || player.getClassId() == ClassId.orcFighter)
					st.showTutorialHTML("tutorial_newbie002h.htm");
				break;
			case 28:
				st.showTutorialHTML("tutorial_31.htm");
				break;
			case 29:
				st.showTutorialHTML("tutorial_32.htm");
				break;
			case 30:
				st.showTutorialHTML("tutorial_33.htm");
				break;
			case 31:
				st.showTutorialHTML("tutorial_34.htm");
				break;
			case 32:
				st.showTutorialHTML("tutorial_35.htm");
				break;
			case 33:
				if(player.getLevel() == 18)
					st.showTutorialHTML("kanooth_gludio.htm");
				else if(player.getLevel() == 28)
					st.showTutorialHTML("kanooth_dion.htm");
				else if(player.getLevel() == 38)
					st.showTutorialHTML("kanooth_heiness.htm");
				else if(player.getLevel() == 48)
					st.showTutorialHTML("kanooth_oren.htm");
				else if(player.getLevel() == 58)
					st.showTutorialHTML("kanooth_shuttgart.htm");
				else if(player.getLevel() == 68)
					st.showTutorialHTML("kanooth_rune.htm");
				break;
			case 34:
				if(player.getLevel() == 79)
				{
					st.showTutorialHTML("ssq_tutorial_q0192_02.htm");
					st.playSound("ItemSound.quest_tutorial");
					st.showRadar(81655, 54736, -1509, 2);
				}
				break;
			case 35:
				if(player.getLevel() == 81)
				{
					st.showTutorialHTML("ssq_tutorial_q10292_01.htm");
					st.playSound("ItemSound.quest_tutorial");
					st.showRadar(146995, 23755, -1984, 2);
				}
		}
	}

	private void levelUp(QuestState st)
	{
		L2Player player = st.getPlayer();
		int level = player.getLevel();
		switch(level)
		{
			case 18:
				if(player.getQuestState("_10276_MutatedKaneusGludio") == null || !player.getQuestState("_10276_MutatedKaneusGludio").isCompleted())
				{
					st.showTutorialHTML("kanooth_gludio.htm");
					st.playSound(SOUND_TUTORIAL);
					st.showRadar(-13900, 123822, -3112, 2);
				}
				break;
			case 28:
				if(player.getQuestState("_10277_MutatedKaneusDion") == null || !player.getQuestState("_10277_MutatedKaneusDion").isCompleted())
				{
					st.showTutorialHTML("kanooth_dion.htm");
					st.playSound(SOUND_TUTORIAL);
					st.showRadar(18199, 146081, -3080, 2);
				}
				break;
			case 38:
				if(player.getQuestState("_10278_MutatedKaneusHeine") == null || !player.getQuestState("_10278_MutatedKaneusHeine").isCompleted())
				{
					st.showTutorialHTML("kanooth_heiness.htm");
					st.playSound(SOUND_TUTORIAL);
					st.showRadar(108384, 221563, -3592, 2);
				}
				break;
			case 48:
				if(player.getQuestState("_10279_MutatedKaneusOren") == null || !player.getQuestState("_10279_MutatedKaneusOren").isCompleted())
				{
					st.showTutorialHTML("kanooth_oren.htm");
					st.playSound(SOUND_TUTORIAL);
					st.showRadar(81023, 56456, -1552, 2);
				}
				break;
			case 58:
				if(player.getQuestState("_10280_MutatedKaneusSchuttgart") == null || !player.getQuestState("_10280_MutatedKaneusSchuttgart").isCompleted())
				{
					st.showTutorialHTML("kanooth_shuttgart.htm");
					st.playSound(SOUND_TUTORIAL);
					st.showRadar(85868, -142164, -1342, 2);
				}
				break;
			case 68:
				if(player.getQuestState("_10281_MutatedKaneusRune") == null || !player.getQuestState("_10281_MutatedKaneusRune").isCompleted())
				{
					st.showTutorialHTML("kanooth_rune.htm");
					st.playSound(SOUND_TUTORIAL);
					st.showRadar(42596, -47988, -800, 2);
				}
				break;
			case 79:
				if(player.getQuestState("_192_SevenSignSeriesOfDoubt") == null || !player.getQuestState("_192_SevenSignSeriesOfDoubt").isCompleted())
				{
					st.showTutorialHTML("ssq_tutorial_q0192_02.htm");
					st.playSound(SOUND_TUTORIAL);
					st.showRadar(81655, 54736, -1509, 2);
				}
				break;
			default:
				int i0 = st.getInt("t");
				if(level == 5 && (i0 & 0x400) == 0x400)
					tutorialEvent(0x400, st);
				else if(level == 6 && (i0 & 0x8000000) == 0x8000000)
					tutorialEvent(0x8000000, st);
				else if(level == 7 && (i0 & 0x800) == 0x800)
					tutorialEvent(0x800, st);
				else if(level == 9)
				{
					if((i0 & 0x10000000) == 0x10000000)
						tutorialEvent(0x10000000, st);
					else if((i0 & 0x20000000) == 0x20000000)
						tutorialEvent(0x20000000, st);
					else if((i0 & 0x40000000) == 0x40000000)
						tutorialEvent(0x40000000, st);
				}
				else if(level == 15 && (i0 & 0x4000000) == 0x4000000)
					tutorialEvent(0x4000000, st);
				else if(level == 20 && (i0 & 0x1000) == 0x1000)
					tutorialEvent(0x1000, st);
				else if(level == 35 && (i0 & 0x1000000) == 0x1000000)
					tutorialEvent(0x1000000, st);
				else if(level == 40 && (i0 & 0x4000) == 0x4000)
					tutorialEvent(0x4000, st);
				else if(level == 75 && (i0 & 0x2000000) == 0x2000000)
					tutorialEvent(0x2000000, st);
				else if(level == 76 && (i0 & 0x8000) == 0x8000)
					tutorialEvent(0x1000, st);
				else if(level == 36 && (i0 & 0x20) == 0x20)
					tutorialEvent(0x20, st);
				else if(level == 61 && (i0 & 0x40) == 0x40)
					tutorialEvent(0x40, st);
				else if(level == 73 && (i0 & 0x80) == 0x80)
					tutorialEvent(0x80, st);
				break;
		}
	}

	private void tutorialEvent(int event_id, QuestState st)
	{
		int i1 = st.getInt("t");
		int i0 = i1 & 0x7FFFFFF0;
		if(event_id < 0) // tutorial_close_x links
		{
			switch(-event_id)
			{
				case 1:
					st.closeTutorialHTML();
					st.playTutorialVoice("tutorial_voice_006", 3500);
					st.showQuestionMark(1);
					st.playSound(SOUND_TUTORIAL);
					st.startQuestTimer("QT1", 30000);
					if(st.getInt("t1") < 0)
						st.set("t1", -4);
					break;
				case 2:
					st.playTutorialVoice("tutorial_voice_003", 2000);
					st.showTutorialHTML("tutorial_02.htm");
					st.onTutorialClientEvent(i0 | 1);
					if(st.getInt("t1") < 0)
						st.set("t1", -5);
					break;
				case 3:
					st.showTutorialHTML("tutorial_03.htm");
					st.set("t", i0 | 2);
					st.onTutorialClientEvent(i0 | 2);
					break;
				case 4:
					st.showTutorialHTML("tutorial_04.htm");
					st.set("t", i0 | 4);
					st.onTutorialClientEvent(i0 | 4);
					break;
				case 5:
					st.showTutorialHTML("tutorial_05.htm");
					st.set("t", i0 | 8);
					st.onTutorialClientEvent(i0 | 8);
					break;
				case 6:
					st.showTutorialHTML("tutorial_06.htm");
					st.set("t", i0 | 16);
					st.onTutorialClientEvent(i0 | 16);
					break;
				case 7:
					st.showTutorialHTML("tutorial_100.htm");
					st.onTutorialClientEvent(i0);
					break;
				case 8:
					st.showTutorialHTML("tutorial_101.htm");
					st.onTutorialClientEvent(i0);
					break;
				case 9:
					st.showTutorialHTML("tutorial_102.htm");
					st.onTutorialClientEvent(i0);
					break;
				case 10:
					st.showTutorialHTML("tutorial_103.htm");
					st.onTutorialClientEvent(i0);
					break;
				case 11:
					st.showTutorialHTML("tutorial_104.htm");
					st.onTutorialClientEvent(i0);
					break;
			}
			return;
		}

		L2Player player = st.getPlayer();

		switch(event_id)
		{
			case 0x1: // Changing Point-Of-View
				if(player.getLevel() < 6)
				{
					st.playTutorialVoice("tutorial_voice_004", 5000);
					st.showTutorialHTML("tutorial_03.htm");
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i0 | 2);
					st.onTutorialClientEvent(i0 | 2);
				}
				break;
			case 0x2: // Using the Mouse Wheel
				if(player.getLevel() < 6)
				{
					st.playTutorialVoice("tutorial_voice_005", (1000 * 5));
					st.showTutorialHTML("tutorial_05.htm");
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i0 | 8);
					st.onTutorialClientEvent(i0 | 8);
				}
				break;
			case 0x8: // Conversation with a Newbie Helper
				if(player.getLevel() < 6)
				{
					st.showTutorialHTML("tutorial_human_fighter007.htm");
					st.playSound(SOUND_TUTORIAL);
					if(player.getClassId() == ClassId.fighter)
						st.showRadar(-71424, 258336, -3109, 2);
					else if(player.getClassId() == ClassId.mage)
						st.showRadar(-91036, 248044, -3568, 2);
					else if(player.getClassId() == ClassId.elvenFighter || player.getClassId() == ClassId.elvenMage)
						st.showRadar(46112, 41200, -3504, 2);
					else if(player.getClassId() == ClassId.darkFighter || player.getClassId() == ClassId.darkMage)
						st.showRadar(28384, 11056, -4233, 2);
					else if(player.getClassId() == ClassId.orcFighter || player.getClassId() == ClassId.orcMage)
						st.showRadar(-56736, -113680, -672, 2);
					else if(player.getClassId() == ClassId.dwarvenFighter)
						st.showRadar(108567, -173994, -406, 2);
					else if(player.getClassId() == ClassId.maleSoldier || player.getClassId() == ClassId.femaleSoldier)
						st.showRadar(-125872, 38016, 1251, 2);

					st.playTutorialVoice("tutorial_voice_007", 3500);

					st.set("t", i0 | 2);
					if(st.getInt("t1") < 0)
						st.set("t1", -5);
				}
				break;
			case 0x100: // HP Regeneration
				if(player.getLevel() < 6)
				{
					st.playTutorialVoice("tutorial_voice_017", 1000);
					st.showQuestionMark(10);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", (i1 & ~0x100) | 0x800000);
					st.onTutorialClientEvent((i0 & ~0x100) | 0x800000);
				}
				break;
			case 0x200: // Penalty for Dying
				st.showQuestionMark(8);
				st.playTutorialVoice("tutorial_voice_016", 1000);
				st.playSound(SOUND_TUTORIAL);
				st.set("t", i1 & ~0x200);
				break;
			case 0x400: // reached level 5
				st.set("t", i1 & ~0x400);
				if(player.getClassId() == ClassId.fighter)
					st.showRadar(-83020, 242553, -3718, 2);
				else if(player.getClassId() == ClassId.mage)
					st.showRadar(45061, 52468, -2796, 2);
				else if(player.getClassId() == ClassId.darkFighter)
					st.showRadar(10447, 14620, -4242, 2);
				else if(player.getClassId() == ClassId.orcFighter)
					st.showRadar(-46389, -113905, -21, 2);
				else if(player.getClassId() == ClassId.dwarvenFighter)
					st.showRadar(115271, -182692, -1445, 2);
				else if(player.getClassId() == ClassId.maleSoldier || player.getClassId() == ClassId.femaleSoldier)
					st.showRadar(-118132, 42788, 723, 2);

				if(!player.isMageClass())
				{
					st.playTutorialVoice("tutorial_voice_014", 1000);
					st.showQuestionMark(9);
					st.playSound(SOUND_TUTORIAL);
				}
				st.set("t", i0 | 0x8000000);
				st.onTutorialClientEvent((i0 | 0x8000000));
				break;
			case 0x8000000: // reached level 6
				st.showQuestionMark(24);
				st.playTutorialVoice("tutorial_voice_020", 1000);
				st.playSound(SOUND_TUTORIAL);
				st.onTutorialClientEvent(i0 & ~0x8000000);
				st.set("t", i1 & ~0x8000000);
				st.onTutorialClientEvent(i0 | 0x800);
				break;
			case 0x800: // reached level 7
				if(player.isMageClass())
				{
					st.playTutorialVoice("tutorial_voice_019", 1000);
					st.showQuestionMark(11);
					st.playSound(SOUND_TUTORIAL);
					if(player.getClassId() == ClassId.mage)
					{
						st.showRadar(-84981, 244764, -3726, 2);
					}
					else if(player.getClassId() == ClassId.elvenMage)
					{
						st.showRadar(45701, 52459, -2796, 2);
					}
					else if(player.getClassId() == ClassId.darkMage)
					{
						st.showRadar(10344, 14445, -4242, 2);
					}
					else if(player.getClassId() == ClassId.orcMage)
					{
						st.showRadar(-46225, -113312, -21, 2);
					}

					st.set("t", i1 & ~0x800);
				}
				st.onTutorialClientEvent(i0 | 0x10000000);
				break;
			case 0x10000000: // Some quest ? reached level 9
				if(player.getClassId() == ClassId.fighter)
				{
					st.playTutorialVoice("tutorial_voice_021", 1000);
					st.showQuestionMark(25);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x10000000);
				}
				st.onTutorialClientEvent(i0 | 0x20000000);
				break;
			case 0x20000000: // Some quest ? reached level 9
				switch(player.getClassId())
				{
					case dwarvenFighter:
					case mage:
					case elvenFighter:
					case elvenMage:
					case darkMage:
					case darkFighter:
					case maleSoldier:
					case femaleSoldier:
						st.playTutorialVoice("tutorial_voice_021", 1000);
						st.showQuestionMark(25);
						st.playSound(SOUND_TUTORIAL);
						break;
					default:
						st.playTutorialVoice("tutorial_voice_030", 1000);
						st.showQuestionMark(27);
						st.playSound(SOUND_TUTORIAL);
						break;
				}
				st.set("t", i1 & ~0x20000000);
				st.onTutorialClientEvent(i0 | 0x20000000);
				break;
			case 0x40000000: // Some quest ? reached level 9
				if(player.getClassId() == ClassId.orcFighter || player.getClassId() == ClassId.orcMage)
				{
					st.playTutorialVoice("tutorial_voice_021", 1000);
					st.showQuestionMark(25);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x40000000);
				}
				st.onTutorialClientEvent(i0 | 0x4000000);
				break;
			case 0x4000000: // reaches level 15
				st.showQuestionMark(17);
				st.playSound(SOUND_TUTORIAL);
				st.set("t", i1 & ~0x4000000);
				st.onTutorialClientEvent(i0 | 4096);
				break;
			case 0x1000: // reaches level 20
				st.showQuestionMark(13);
				st.playSound(SOUND_TUTORIAL);
				st.set("t", i1 & ~4096);
				st.onTutorialClientEvent(i0 | 16777216);
				break;
			case 0x1000000: // reached level 35
				if(player.getRace() != Race.kamael)
				{
					st.playTutorialVoice("tutorial_voice_023", 1000);
					st.showQuestionMark(15);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x1000000);
				}
				st.onTutorialClientEvent(i0 | 32);
				break;
			case 0x4000: // reached level 40
				if(player.getRace() == Race.kamael && player.getClassId().getLevel() == 2)
				{
					st.playTutorialVoice("tutorial_voice_028", 1000);
					st.showQuestionMark(15);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x4000);
				}
				st.onTutorialClientEvent((i0 | 64));
				break;
			case 0x2000000: // reached level 75
				if(player.getQuestState("_234_FatesWhisper") == null || !player.getQuestState("_234_FatesWhisper").isCompleted())
				{
					st.playTutorialVoice("tutorial_voice_024", 1000);
					st.showQuestionMark(16);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x2000000);
				}
				st.onTutorialClientEvent((i0 | 32768));
				break;
			case 0x8000: // reached level 76
				if(player.getQuestState("_234_FatesWhisper") != null && player.getQuestState("_234_FatesWhisper").isCompleted())
				{
					st.showQuestionMark(29);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x8000);
				}
				break;
			case 0x20: // reached level 36
				if(player.getQuestState("_128_PailakaSongofIceandFire") == null || !player.getQuestState("_128_PailakaSongofIceandFire").isCompleted())
				{
					st.showQuestionMark(30);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x20);
				}
				st.onTutorialClientEvent(i0 | 16384);
				break;
			case 0x40: // reached level 61
				if(player.getQuestState("_129_PailakaDevilsLegacy") == null || !player.getQuestState("_129_PailakaDevilsLegacy").isCompleted())
				{
					st.showQuestionMark(31);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x40);
				}
				st.onTutorialClientEvent(i0 | 128);
				break;
			case 0x80: // reached level 73
				if(player.getQuestState("_144_PailakaInjuredDragon") == null || !player.getQuestState("_144_PailakaInjuredDragon").isCompleted())
				{
					st.showQuestionMark(32);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x80);
				}
				st.onTutorialClientEvent(i0 | 33554432);
				break;
			case 0x200000: // Item get
				if(player.getLevel() < 6)
				{
					st.showQuestionMark(23);
					st.playTutorialVoice("tutorial_voice_012", 1000);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x200000);
				}
				break;
			case 0x100000: // Blue Gemestone
				if(player.getLevel() < 6)
				{
					st.showQuestionMark(5);
					st.playTutorialVoice("tutorial_voice_013", 1000);
					st.playSound(SOUND_TUTORIAL);
					st.set("t", i1 & ~0x100000);
				}
				break;
			case 0x800000: // HP Regenarated
				if(player.getLevel() < 6)
				{
					st.playTutorialVoice("tutorial_voice_018", 1000);
					st.showTutorialHTML("tutorial_21z.htm");
					st.set("t", i1 & ~0x800000);
				}
				break;
		}
	}

	private void menuSelect(int ask, int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(ask == 420)
		{
			if(reply == 1)
				st.showTutorialHTML("tutorial_22g.htm");
			else if(reply == 2)
				st.showTutorialHTML("tutorial_22w.htm");
			else if(reply == 3)
				st.showTutorialHTML("tutorial_22ap.htm");
			else if(reply == 4)
				st.showTutorialHTML("tutorial_22ad.htm");
			else if(reply == 5)
				st.showTutorialHTML("tutorial_22bt.htm");
			else if(reply == 6)
				st.showTutorialHTML("tutorial_22bh.htm");
			else if(reply == 7)
				st.showTutorialHTML("tutorial_22cs.htm");
			else if(reply == 8)
				st.showTutorialHTML("tutorial_22cn.htm");
			else if(reply == 9)
				st.showTutorialHTML("tutorial_22cw.htm");
			else if(reply == 10)
				st.showTutorialHTML("tutorial_22db.htm");
			else if(reply == 11)
				st.showTutorialHTML("tutorial_22dp.htm");
			else if(reply == 12)
				st.showTutorialHTML("tutorial_22et.htm");
			else if(reply == 13)
				st.showTutorialHTML("tutorial_22es.htm");
			else if(reply == 14)
				st.showTutorialHTML("tutorial_22fp.htm");
			else if(reply == 15)
				st.showTutorialHTML("tutorial_22fs.htm");
			else if(reply == 16)
				st.showTutorialHTML("tutorial_22gs.htm");
			else if(reply == 17)
				st.showTutorialHTML("tutorial_22ge.htm");
			else if(reply == 18)
				st.showTutorialHTML("tutorial_22ko.htm");
			else if(reply == 19)
				st.showTutorialHTML("tutorial_22kw.htm");
			else if(reply == 20)
				st.showTutorialHTML("tutorial_22ns.htm");
			else if(reply == 21)
				st.showTutorialHTML("tutorial_22nb.htm");
			else if(reply == 22)
				st.showTutorialHTML("tutorial_22oa.htm");
			else if(reply == 23)
				st.showTutorialHTML("tutorial_22op.htm");
			else if(reply == 24)
				st.showTutorialHTML("tutorial_22ps.htm");
			else if(reply == 24)
				st.showTutorialHTML("tutorial_22pp.htm");
			else if(reply == 26)
			{
				if(player.getClassId() == ClassId.warrior)
					st.showTutorialHTML("tutorial_22.htm");
				else if(player.getClassId() == ClassId.knight)
					st.showTutorialHTML("tutorial_22a.htm");
				else if(player.getClassId() == ClassId.rogue)
					st.showTutorialHTML("tutorial_22b.htm");
				else if(player.getClassId() == ClassId.wizard)
					st.showTutorialHTML("tutorial_22c.htm");
				else if(player.getClassId() == ClassId.cleric)
					st.showTutorialHTML("tutorial_22d.htm");
				else if(player.getClassId() == ClassId.elvenKnight)
					st.showTutorialHTML("tutorial_22e.htm");
				else if(player.getClassId() == ClassId.elvenScout)
					st.showTutorialHTML("tutorial_22f.htm");
				else if(player.getClassId() == ClassId.elvenWizard)
					st.showTutorialHTML("tutorial_22g.htm");
				else if(player.getClassId() == ClassId.oracle)
					st.showTutorialHTML("tutorial_22h.htm");
				else if(player.getClassId() == ClassId.orcRaider)
					st.showTutorialHTML("tutorial_22i.htm");
				else if(player.getClassId() == ClassId.orcMonk)
					st.showTutorialHTML("tutorial_22j.htm");
				else if(player.getClassId() == ClassId.orcShaman)
					st.showTutorialHTML("tutorial_22k.htm");
				else if(player.getClassId() == ClassId.scavenger)
					st.showTutorialHTML("tutorial_22l.htm");
				else if(player.getClassId() == ClassId.artisan)
					st.showTutorialHTML("tutorial_22m.htm");
				else if(player.getClassId() == ClassId.palusKnight)
					st.showTutorialHTML("tutorial_22n.htm");
				else if(player.getClassId() == ClassId.assassin)
					st.showTutorialHTML("tutorial_22o.htm");
				else if(player.getClassId() == ClassId.darkWizard)
					st.showTutorialHTML("tutorial_22p.htm");
				else if(player.getClassId() == ClassId.shillienOracle)
					st.showTutorialHTML("tutorial_22q.htm");
				else
					st.showTutorialHTML("tutorial_22qe.htm");
			}
			else if(reply == 27)
				st.showTutorialHTML("tutorial_29.htm");
			else if(reply == 28)
				st.showTutorialHTML("tutorial_28.htm");
			else if(reply == 29)
				st.showTutorialHTML("tutorial_07a.htm");
			else if(reply == 30)
				st.showTutorialHTML("tutorial_07b.htm");
			else if(reply == 31)
			{
				if(player.getClassId() == ClassId.trooper)
					st.showTutorialHTML("tutorial_28a.htm");
				else if(player.getClassId() == ClassId.warder)
					st.showTutorialHTML("tutorial_28b.htm");
			}
			else if(reply == 32)
				st.showTutorialHTML("tutorial_22qa.htm");
			else if(reply == 33)
			{
				if(player.getClassId() == ClassId.trooper)
					st.showTutorialHTML("tutorial_22qb.htm");
				else if(player.getClassId() == ClassId.warder)
					st.showTutorialHTML("tutorial_22qc.htm");
			}
			else if(reply == 34)
				st.showTutorialHTML("tutorial_22qd.htm");
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("t1") == 1 || st.getInt("t1") == 0)
		{
			st.playTutorialVoice("tutorial_voice_011", 1000);
			st.showQuestionMark(3);
			st.set("t1", 2);
		}
		if((st.getInt("t1") == 1 || st.getInt("t1") == 2 || st.getInt("t1") == 0) && !st.haveQuestItems(6353) && Rnd.chance(50))
		{
			npc.dropItem(st.getPlayer(), 6353, 1);
			st.playSound(SOUND_TUTORIAL);
		}
	}
}