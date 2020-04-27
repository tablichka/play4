package quests._104_SpiritOfMirror;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;

public class _104_SpiritOfMirror extends Quest
{
	static final int GALLINS_OAK_WAND = 748;
	static final int WAND_SPIRITBOUND1 = 1135;
	static final int WAND_SPIRITBOUND2 = 1136;
	static final int WAND_SPIRITBOUND3 = 1137;
	static final int WAND_OF_ADEPT = 747;

	public _104_SpiritOfMirror()
	{
		super(104, "_104_SpiritOfMirror", "Spirit of Mirrors");

		addStartNpc(30017);
		addTalkId(30041, 30043, 30045);
		addKillId(27003, 27004, 27005);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(event.equalsIgnoreCase("gallin_q0104_03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(GALLINS_OAK_WAND, 3);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == 30017)
		{
			if(st.isCreated())
				if(st.getPlayer().getRace() != Race.human)
				{
					htmltext = "gallin_q0104_00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() >= 10)
				{
					htmltext = "gallin_q0104_02.htm";
					return htmltext;
				}
				else
				{
					htmltext = "gallin_q0104_06.htm";
					st.exitCurrentQuest(true);
				}
			else if(cond == 1 && st.getQuestItemsCount(GALLINS_OAK_WAND) >= 1 && (st.getQuestItemsCount(WAND_SPIRITBOUND1) == 0 || st.getQuestItemsCount(WAND_SPIRITBOUND2) == 0 || st.getQuestItemsCount(WAND_SPIRITBOUND3) == 0))
				htmltext = "gallin_q0104_04.htm";
			else if(cond == 3 && st.getQuestItemsCount(WAND_SPIRITBOUND1) >= 1 && st.getQuestItemsCount(WAND_SPIRITBOUND2) >= 1 && st.getQuestItemsCount(WAND_SPIRITBOUND3) >= 1)
			{
				if(st.getPlayer().getLevel() < 25 && st.getPlayer().isMageClass())
				{
					st.playTutorialVoice("tutorial_voice_027", 1000);
					st.giveItems(5790, 3000);
				}

				st.addExpAndSp(39750, 3407);
				st.rollAndGive(57, 16866, 100);
				st.giveItems(1060, 100); // healing potion
				for(int item = 4412; item <= 4416; item++)
					st.giveItems(item, 10); // echo cry

				if(st.getPlayer().isMageClass())
					st.giveItems(2509, 500);
				else
					st.giveItems(1835, 1000);

				if(st.getPlayer().getVarInt("NR41") == 0)
				{
					st.getPlayer().setVar("NR41", 100000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4154", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
				}
				else if((st.getPlayer().getVarInt("NR41") % 1000000) / 100000 == 0)
				{
					st.getPlayer().setVar("NR41", st.getPlayer().getVarInt("NR41") + 100000);
					st.getPlayer().sendPacket(new ExShowScreenMessage(new CustomMessage("fs4154", st.getPlayer()).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
				}

				st.takeItems(WAND_SPIRITBOUND1, -1);
				st.takeItems(WAND_SPIRITBOUND2, -1);
				st.takeItems(WAND_SPIRITBOUND3, -1);

				st.giveItems(WAND_OF_ADEPT, 1);

				htmltext = "gallin_q0104_05.htm";
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
				st.showSocial(3);
			}
		}
		else if((npcId == 30041 || npcId == 30043 || npcId == 30045) && cond == 1)
		{
			if(npcId == 30041 && st.getInt("id1") == 0)
			{
				st.set("id1", "1");
				htmltext = "arnold_q0104_01.htm";
			}
			if(npcId == 30043 && st.getInt("id2") == 0)
			{
				st.set("id2", "1");
				htmltext = "johnson_q0104_01.htm";
			}
			if(npcId == 30045 && st.getInt("id3") == 0)
			{
				st.set("id3", "1");
				htmltext = "ken_q0104_01.htm";
			}
			if(st.getInt("id1") + st.getInt("id2") + st.getInt("id3") == 3)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
				st.unset("id1");
				st.unset("id2");
				st.unset("id3");
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getInt("cond");
		int npcId = npc.getNpcId();

		if((cond == 1 || cond == 2) && st.getPlayer().getActiveWeaponInstance() != null && st.getPlayer().getActiveWeaponInstance().getItemId() == GALLINS_OAK_WAND)
		{
			L2ItemInstance weapon = st.getPlayer().getActiveWeaponInstance();
			if(npcId == 27003 && st.getQuestItemsCount(WAND_SPIRITBOUND1) == 0)
			{
				st.getPlayer().getInventory().destroyItem(this.getName(), weapon, st.getPlayer(), npc);
				st.giveItems(WAND_SPIRITBOUND1, 1);
				long Collect = st.getQuestItemsCount(WAND_SPIRITBOUND1) + st.getQuestItemsCount(WAND_SPIRITBOUND2) + st.getQuestItemsCount(WAND_SPIRITBOUND3);
				if(Collect == 3)
				{
					st.set("cond", "3");
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
			else if(npcId == 27004 && st.getQuestItemsCount(WAND_SPIRITBOUND2) == 0)
			{
				st.getPlayer().getInventory().destroyItem(this.getName(), weapon, st.getPlayer(), npc);
				st.giveItems(WAND_SPIRITBOUND2, 1);
				long Collect = st.getQuestItemsCount(WAND_SPIRITBOUND1) + st.getQuestItemsCount(WAND_SPIRITBOUND2) + st.getQuestItemsCount(WAND_SPIRITBOUND3);
				if(Collect == 3)
				{
					st.set("cond", "3");
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
			else if(npcId == 27005 && st.getQuestItemsCount(WAND_SPIRITBOUND3) == 0)
			{
				st.getPlayer().getInventory().destroyItem(this.getName(), weapon, st.getPlayer(), npc);
				st.giveItems(WAND_SPIRITBOUND3, 1);
				long Collect = st.getQuestItemsCount(WAND_SPIRITBOUND1) + st.getQuestItemsCount(WAND_SPIRITBOUND2) + st.getQuestItemsCount(WAND_SPIRITBOUND3);
				if(Collect == 3)
				{
					st.set("cond", "3");
					st.playSound(SOUND_MIDDLE);
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
	}
}