package npc.model;

import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Summon;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.instances.L2SummonInstance;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

/**
 * @author rage
 * @date 02.07.2010 14:21:19
 */
public class NewbieGuideInstance extends L2NpcInstance
{
	protected static final String _path = "data/html/guide/";
	protected final String fnHi;
	protected final String fnHighLevel;
	protected final String fnRaceMisMatch;
	protected final String fnGuideF05;
	protected final String fnGuideF10;
	protected final String fnGuideF15;
	protected final String fnGuideF20;
	protected final String fnGuideM07;
	protected final String fnGuideM14;
	protected final String fnGuideM20;
	protected final String ShopName;
	protected final String fnCoupon1Ok;
	protected final String fnCoupon1Not1;
	protected final String fnCoupon1Not2;
	protected final String fnCoupon1Not3;
	protected final String fnCoupon2Ok;
	protected final String fnCoupon2Not1;
	protected final String fnCoupon2Not2;
	protected final String fnCoupon2Not3;
	protected final String fnNoSummonCreature;
	protected final int num_coupon1 = 5;
	protected final int num_coupon2 = 1;
	protected String myname;

	public NewbieGuideInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		if(getAIParams() != null)
		{
			fnHi = getAIParams().getString("fnHi", "");
			fnHighLevel = getAIParams().getString("fnHighLevel", "");
			fnRaceMisMatch = getAIParams().getString("fnRaceMisMatch", "");
			fnGuideF05 = getAIParams().getString("fnGuideF05", "");
			fnGuideF10 = getAIParams().getString("fnGuideF10", "");
			fnGuideF15 = getAIParams().getString("fnGuideF15", "");
			fnGuideF20 = getAIParams().getString("fnGuideF20", "");
			fnGuideM07 = getAIParams().getString("fnGuideM07", "");
			fnGuideM14 = getAIParams().getString("fnGuideM14", "");
			fnGuideM20 = getAIParams().getString("fnGuideM20", "");
			ShopName = getAIParams().getString("ShopName", "");
			fnCoupon1Ok = getAIParams().getString("fnCoupon1Ok", "newbie_guide002.htm");
			fnCoupon1Not1 = getAIParams().getString("fnCoupon1Not1", "newbie_guide003.htm");
			fnCoupon1Not2 = getAIParams().getString("fnCoupon1Not2", "newbie_guide004.htm");
			fnCoupon1Not3 = getAIParams().getString("fnCoupon1Not3", "newbie_guide005.htm");
			fnCoupon2Ok = getAIParams().getString("fnCoupon2Ok", "newbie_guide011.htm");
			fnCoupon2Not1 = getAIParams().getString("fnCoupon2Not1", "newbie_guide012.htm");
			fnCoupon2Not2 = getAIParams().getString("fnCoupon2Not2", "newbie_guide013.htm");
			fnCoupon2Not3 = getAIParams().getString("fnCoupon2Not3", "newbie_guide014.htm");
			fnNoSummonCreature = getAIParams().getString("fnNoSummonCreature", "blessing_list002b.htm");
		}
		else
		{
			fnHi = "";
			fnHighLevel = "";
			fnRaceMisMatch = "";
			fnGuideF05 = "";
			fnGuideF10 = "";
			fnGuideF15 = "";
			fnGuideF20 = "";
			fnGuideM07 = "";
			fnGuideM14 = "";
			fnGuideM20 = "";
			ShopName = "";
			fnCoupon1Ok = "newbie_guide002.htm";
			fnCoupon1Not1 = "newbie_guide003.htm";
			fnCoupon1Not2 = "newbie_guide004.htm";
			fnCoupon1Not3 = "newbie_guide005.htm";
			fnCoupon2Ok = "newbie_guide011.htm";
			fnCoupon2Not1 = "newbie_guide012.htm";
			fnCoupon2Not2 = "newbie_guide013.htm";
			fnCoupon2Not3 = "newbie_guide014.htm";
			fnNoSummonCreature = "blessing_list002b.htm";
		}
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		showPage(player, fnHi);
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		// Prevent a cursed weapon weilder of being buffed
		if(player.isCursedWeaponEquipped())
			return;

		if(command.startsWith("menu_select "))
		{
			String[] var = command.replace("menu_select ", "").split(" ");
			int ask = Integer.parseInt(var[0]);
			int reply = Integer.parseInt(var[1]);
			if(ask == -7 && reply == 1)
			{
				if(getRace() == Race.kamael)
				{
					if(player.getRace() != getRace())
						showPage(player, "guide_krenisk003.htm");
					else if(player.getLevel() > 20 || player.getClassId().getLevel() != 1)
						showPage(player, "guide_krenisk002.htm");
					else if(player.getClassId() == ClassId.maleSoldier)
					{
						if(player.getLevel() <= 5)
							showPage(player, "guide_krenisk_kmf05.htm");
						else if(player.getLevel() <= 10)
							showPage(player, "guide_krenisk_kmf10.htm");
						else if(player.getLevel() <= 15)
							showPage(player, "guide_krenisk_kmf15.htm");
						else
							showPage(player, "guide_krenisk_kmf20.htm");
					}
					else if(player.getClassId() == ClassId.femaleSoldier)
					{
						if(player.getLevel() <= 5)
							showPage(player, "guide_krenisk_kff05.htm");
						else if(player.getLevel() <= 10)
							showPage(player, "guide_krenisk_kff10.htm");
						else if(player.getLevel() <= 15)
							showPage(player, "guide_krenisk_kff15.htm");
						else
							showPage(player, "guide_krenisk_kff20.htm");
					}
				}
				else if(player.getRace() != getRace())
					showPage(player, fnRaceMisMatch);
				else if(player.getLevel() > 20 || player.getClassId().getLevel() != 1)
					showPage(player, fnHighLevel);
				else if(!player.isMageClass())
				{
					if(player.getLevel() <= 5)
						showPage(player, fnGuideF05);
					else if(player.getLevel() <= 10)
						showPage(player, fnGuideF10);
					else if(player.getLevel() <= 15)
						showPage(player, fnGuideF15);
					else
						showPage(player, fnGuideF20);
				}
				else if(player.getLevel() <= 7)
					showPage(player, fnGuideM07);
				else if(player.getLevel() <= 14)
					showPage(player, fnGuideM14);
				else
					showPage(player, fnGuideM20);
			}
			else if(ask == -7 && reply == 2)
			{
				if(player.getLevel() <= 75)
				{
					if(player.getLevel() < 6)
						showPage(player, "guide_for_newbie002.htm");
					else if(!player.isMageClass() || player.getActiveClass() == 49 || player.getActiveClass() == 50)
					{
						altUseSkill(getSkillFromIndex(283246593), player);
						altUseSkill(getSkillFromIndex(283312129), player);
						altUseSkill(getSkillFromIndex(369426433), player);
						altUseSkill(getSkillFromIndex(283377665), player);
						altUseSkill(getSkillFromIndex(283443201), player);
						altUseSkill(getSkillFromIndex(283508737), player);
						if(player.getLevel() >= 6 && player.getLevel() <= 39)
							altUseSkill(getSkillFromIndex(283574273), player);
						if(player.getLevel() >= 40 && player.getLevel() <= 75)
							altUseSkill(getSkillFromIndex(369098753), player);
						if(player.getLevel() >= 16 && player.getLevel() <= 34)
							altUseSkill(getSkillFromIndex(284295169), player);
					}
					else if(player.isMageClass())
					{
						altUseSkill(getSkillFromIndex(283246593), player);
						altUseSkill(getSkillFromIndex(283312129), player);
						altUseSkill(getSkillFromIndex(369426433), player);
						altUseSkill(getSkillFromIndex(283639809), player);
						altUseSkill(getSkillFromIndex(283705345), player);
						altUseSkill(getSkillFromIndex(283770881), player);
						altUseSkill(getSkillFromIndex(283836417), player);
						if(player.getLevel() >= 16 && player.getLevel() <= 34)
							altUseSkill(getSkillFromIndex(284295169), player);
					}
				}
				else
					showPage(player, "guide_for_newbie003.htm");
			}
			else if(ask == -7 && reply == 3)
			{
				if(player.getLevel() <= 39 && player.getClassId().getLevel() < 3)
					altUseSkill(getSkillFromIndex(339607553), player);
				else
					showPage(player, "pk_protect002.htm");
			}
			else if(ask == -7 && reply == 4)
			{
				L2Summon summon = player.getPet();
				if(summon instanceof L2SummonInstance)
				{
					if(player.getLevel() < 6 || player.getLevel() > 75)
						showPage(player, "guide_for_newbie003.htm");
					else
					{
						altUseSkill(getSkillFromIndex(283246593), summon);
						altUseSkill(getSkillFromIndex(283312129), summon);
						altUseSkill(getSkillFromIndex(369426433), summon);
						altUseSkill(getSkillFromIndex(283377665), summon);
						altUseSkill(getSkillFromIndex(283443201), summon);
						altUseSkill(getSkillFromIndex(283508737), summon);
						altUseSkill(getSkillFromIndex(283639809), summon);
						altUseSkill(getSkillFromIndex(283705345), summon);
						altUseSkill(getSkillFromIndex(283770881), summon);
						altUseSkill(getSkillFromIndex(283836417), summon);
						if(player.getLevel() >= 6 && player.getLevel() <= 39)
							altUseSkill(getSkillFromIndex(283574273), summon);
						if(player.getLevel() >= 40 && player.getLevel() <= 75)
							altUseSkill(getSkillFromIndex(369098753), summon);
					}
				}
				else
					showPage(player, fnNoSummonCreature);
			}
			if(ask == -1000)
			{
				switch(reply)
				{
					case 1:
						if(player.getLevel() > 5 && player.getLevel() < 20 && player.getClassId().getLevel() == 1)
						{
							if(!player.getVarB("coupon1"))
							{
								if(!player.isQuestContinuationPossible(true))
									return;

								player.setVar("coupon1", "1");
								for(int i = 0; i < num_coupon1; i++)
									player.addItem("NewbewGuide", 7832, 1, this, true);

								showPage(player, fnCoupon1Ok);

								player.setVar("NR41", player.getVarInt("NR41") + 100);
								player.sendPacket(new ExShowScreenMessage(new CustomMessage("fs4153", player).toString(), 5000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true));
							}
							else
								showPage(player, fnCoupon1Not2);
						}
						else
							showPage(player, fnCoupon1Not1);
						break;
					case 2:
						if(player.getClassId().getLevel() > 1 && player.getLevel() < 40)
						{
							if(!player.getVarB("coupon2"))
							{
								if(!player.isQuestContinuationPossible(true))
									return;

								player.setVar("coupon2", "1");
								for(int i = 0; i < num_coupon2; i++)
									player.addItem("NewbewGuide", 7833, 1, this, true);

								showPage(player, fnCoupon2Ok);
							}
							else
								showPage(player, fnCoupon2Not2);
						}
						else
							showPage(player, fnCoupon2Not1);
						break;
				}
			}
			if(ask == -303)
			{
				switch(reply)
				{
					case 528:
						if(player.getLevel() > 5 && player.getLevel() < 20 && player.getClassId().getLevel() == 1)
							super.onBypassFeedback(player, "multisell 528");
						else
							showPage(player, fnCoupon1Not3);
						break;
					case 529:
						if(player.getClassId().getLevel() > 1 && player.getLevel() < 40)
							super.onBypassFeedback(player, "multisell 529");
						else
							showPage(player, fnCoupon2Not3);
						break;
				}
			}
		}
		else if(command.startsWith("teleport_request"))
		{
			if(player.getLevel() >= 20)
				showPage(player, "guide_teleport_over001.htm");
			else if(player.getTransformation() == 111 || player.getTransformation() == 112 || player.getTransformation() == 124)
				showChatWindow(player, "data/html/teleporter/q194_noteleport.htm");
			else
				showPage(player, myname + "010.htm");
			
		}
		else
			super.onBypassFeedback(player, command);
	}

	@Override
	public void showPage(L2Player player, String page)
	{
		player.sendPacket(new NpcHtmlMessage(player, this, _path + page, 0));
	}

	protected Race getRace()
	{
		switch(getTemplate().getRace())
		{
			case 14:
				return Race.human;
			case 15:
				return Race.elf;
			case 16:
				return Race.darkelf;
			case 17:
				return Race.orc;
			case 18:
				return Race.dwarf;
			case 25:
				return Race.kamael;
		}
		return null;
	}

	public static L2Skill getSkillFromIndex(int index)
	{
		return SkillTable.getInstance().getInfo(index >> 16, index & 0xFFFF);
	}
}
