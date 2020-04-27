package ru.l2gw.gameserver.network;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.ccpGuard.ConfigProtect;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.clientpackets.*;
import ru.l2gw.gameserver.loginservercon.LSConnection;
import ru.l2gw.gameserver.loginservercon.gspackets.PlayerLogout;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.network.PacketFloodProtector.ActionType;
import ru.l2gw.commons.network.*;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.util.concurrent.RejectedExecutionException;

/**
 * Stateful Packet Handler<BR>
 * The Stateful approach prevents the server from handling inconsistent packets, examples:<BR>
 * <li>Clients sends a MoveToLocation packet without having a character attached. (Potential errors handling the packet).</li>
 * <li>Clients sends a RequestAuthLogin being already authed. (Potential exploit).</li>
 * <BR><BR>
 * Note: If for a given exception a packet needs to be handled on more then one state, then it should be added to all these states.
 */
public final class GamePacketHandler extends TCPHeaderHandler<GameClient> implements IPacketHandler<GameClient>, IClientFactory<GameClient>, IMMOExecutor<GameClient>
{
	protected static final Log _log = LogFactory.getLog("network");

	public GamePacketHandler()
	{
		super(null);
	}

	// implementation
	public ReceivablePacket<GameClient> handlePacket(ByteBuffer data, GameClient client)
	{
		L2Player player;
		int id = data.get() & 0xFF;
		if(client == null)
			return null;
		if(client.getUPTryes() > 4)
			return null;

		ReceivablePacket<GameClient> msg = null;
		int id2;
		int id3;

		switch(client.getState())
		{
			case CONNECTED:
				switch(id)
				{
					case 0x00:
						if(Config.ALLOW_SEND_STATUS)
							msg = new RequestStatus();
						else if(client.getPlayer() != null)
							msg = new Logout();
						break;
					case 0x0e:
						msg = new ProtocolVersion();
						break;
					case 0x2b:
						msg = new AuthLogin();
						break;
					case 0xcb:
						msg = new ReplyGameGuardQuery();
						break;
					default:
						try
						{
							_log.error("Unknown packet on state: CONNECTED, id: " + Integer.toHexString(id) + " from " + client.getConnection().getSocket().getInetAddress().getHostAddress());
							if(client.getLoginName() != null)
							{
								LSConnection.getInstance().sendPacket(new PlayerLogout(client.getLoginName()));
								LSConnection.getInstance().removeAccount(client);
							}
							client.closeNow(true);
							if(ConfigProtect.PROTECT_ENABLE && !client.getHWID().isEmpty())
								_log.error("Client HWID: " + client.getHWID());
						}
						catch(NullPointerException e)
						{
							if(_log == null)
								System.out.println("GamePacketHandler: WTF _log is null ???!!!");
							if(client != null)
								System.out.println("GamePacketHandler: " + client);
							e.printStackTrace();
						}
						break;
				}
				break;
			case AUTHED:
				if(Config.PACKET_FLOOD_PROTECTOR)
				{
					ActionType act = client.checkPacket(id);
					try
					{
						switch(act)
						{
							case log: // Log user
								_log.warn("FP: pkt(0x" + Integer.toHexString(id) + ") logg " + client);
								break;
							case drop_log: // Drop packet
								_log.warn("FP: pkt(0x" + Integer.toHexString(id) + ") drop " + client);
								return null;
							case drop: // Just drop
								return null;
						}
					}
					catch(Exception e)
					{
						return null;
					}
				}

				switch(id)
				{
					case 0x00:
						msg = new Logout();
						break;
					case 0x0c:
						msg = new CharacterCreate(); //RequestCharacterCreate();
						break;
					case 0x0d:
						msg = new CharacterDelete(); //RequestCharacterDelete();
						break;
					case 0x12:
						msg = new CharacterSelected(); //CharacterSelect();
						break;
					case 0x13:
						msg = new NewCharacter(); //RequestNewCharacter();
						break;
					case 0x7b:
						msg = new CharacterRestore(); //RequestCharacterRestore();
						break;
					case 0xcb:
						msg = new ReplyGameGuardQuery();
						break;
					case 0xd0:
						if(!data.hasRemaining())
						{
							handleIncompletePacket(client);
							break;
						}
						id3 = data.getShort() & 0xffff;
						switch(id3)
						{
							case 0x36:
								msg = new GotoLobby();
								break;
							case 0x5a:
								msg = new RequestExCubeGameChangeTeam();
								break;
							case 0x93:
								msg = new RequestEx2ndPasswordCheck();
								break;
							case 0x94:
								msg = new RequestEx2ndPasswordVerify();
								break;
							case 0x95:
								msg = new RequestEx2ndPasswordReq();
								break;
							default:
								//_log.error("Unknown packet on state: AUTHED, id: D0:" + Integer.toHexString(id3));
								break;
						}
						break;
					default:
						//_log.error("Unknown packet on state: AUTHED, id: " + Integer.toHexString(id));
						break;
				}
				break;
			case IN_GAME:
				player = client.getPlayer();
				if(player != null && L2ObjectsStorage.getPlayer(player.getName()) == null && !player.isTeleporting())
				{
					try
					{
						_log.info(player.getName() + " send packet " + id + " but player not in world, reflection: " + player.getReflection());
						player.logout(false, false, false);
					}
					catch(NullPointerException e)
					{
					}
					break;
				}

				if(Config.PACKET_FLOOD_PROTECTOR)
				{
					ActionType act = client.checkPacket(id);
					try
					{
						switch(act)
						{
							case log: // Log user
								if(!client.getPlayer().isDeleting())
									_log.warn("FP: pkt(0x" + Integer.toHexString(id) + ") logg " + client);
								break;
							case drop_log: // Drop packet
								if(!client.getPlayer().isDeleting())
									_log.warn("FP: pkt(0x" + Integer.toHexString(id) + ") drop " + client);
								return null;
							case kick_log: // Kick user
								if(!client.getPlayer().isDeleting())
								{
									_log.warn("FP: pkt(0x" + Integer.toHexString(id) + ") kick " + client);
									client.getPlayer().logout(false, false, true);
								}
								return null;
							case drop: // Just drop
								return null;
						}
					}
					catch(Exception e)
					{
						return null;
					}
				}

				switch(id)
				{
					case 0x00:
						msg = new Logout();
						break;
					case 0x01:
						msg = new AttackRequest();
						break;
					case 0x02:
						//	msg = new ?();
						break;
					case 0x03:
						msg = new RequestStartPledgeWar();
						break;
					case 0x04:
						//msg = new RequestReplyStartPledgeWar();
						break;
					case 0x05:
						msg = new RequestStopPledgeWar();
						break;
					case 0x06:
						//	msg = RequestSCCheck(); // ? Format: cdx
						//msg = new RequestReplyStopPledgeWar();
						break;
					case 0x07:
						msg = new ReplyGameGuardQuery();
						//msg = new RequestSurrenderPledgeWar();
						// сдесь совсем другой пакет ResponseAuthGameGuard[cddddd] (c) Drin
						break;
					case 0x08:
						//	msg = new ?();
						//msg = new RequestReplySurrenderPledgeWar();
						break;
					case 0x09:
						msg = new RequestSetPledgeCrest();
						break;
					case 0x0a:
						//	msg = new ?();
						break;
					case 0x0b:
						msg = new RequestGiveNickName();
						break;
					case 0x0c:
						//	wtf???
						break;
					case 0x0d:
						//	wtf???
						break;
					case 0x0f:
						msg = new MoveBackwardToLocation();
						break;
					case 0x10:
						//	msg = new Say(); Format: cS // старый ?
						break;
					case 0x11:
						msg = new EnterWorld();
						break;
					case 0x12:
						//	wtf???
						break;
					case 0x14:
						msg = new RequestItemList();
						break;
					case 0x15:
						//	msg = new RequestEquipItem(); // старый?
						//	Format: cdd server id = %d Slot = %d
						break;
					case 0x16:
						msg = new RequestUnEquipItem();
						break;
					case 0x17:
						msg = new RequestDropItem();
						break;
					case 0x18:
						//	msg = new ?();
						break;
					case 0x19:
						msg = new UseItem(); //RequestUseItem();
						break;
					case 0x1a:
						msg = new TradeRequest();
						break;
					case 0x1b:
						msg = new AddTradeItem(); //RequestAddTradeItem();
						break;
					case 0x1c:
						msg = new TradeDone(); //RequestTradeDone();
						break;
					case 0x1d:
						//	msg = new ?();
						break;
					case 0x1e:
						//	msg = new ?();
						break;
					case 0x1f:
						msg = new Action();
						break;
					case 0x20:
						//	msg = new ?();
						break;
					case 0x21:
						//	msg = new ?();
						break;
					case 0x22:
						msg = new RequestLinkHtml();
						break;
					case 0x23:
						msg = new RequestBypassToServer();
						break;
					case 0x24:
						msg = new RequestBBSwrite(); //RequestBBSWrite();
						break;
					case 0x25:
						msg = new RequestCreatePledge();
						break;
					case 0x26:
						msg = new RequestJoinPledge();
						break;
					case 0x27:
						msg = new RequestAnswerJoinPledge();
						break;
					case 0x28:
						msg = new RequestWithdrawalPledge(); //RequestWithDrawalPledge();
						break;
					case 0x29:
						msg = new RequestOustPledgeMember();
						break;
					case 0x2a:
						//	msg = new ?();
						break;
					case 0x2c:
						msg = new RequestGetItemFromPet();
						break;
					case 0x2d:
						//	RequestDismissParty
						break;
					case 0x2e:
						msg = new RequestAllyInfo();
						break;
					case 0x2f:
						msg = new RequestCrystallizeItem();
						break;
					case 0x30:
						// RequestPrivateStoreManage, устарел
						break;
					case 0x31:
						msg = new SetPrivateStoreListSell();
						break;
					case 0x32:
						msg = new AttackRequest();
						break;
					case 0x33:
						msg = new RequestTeleport();
						break;
					case 0x34:
						msg = new RequestSocialAction(); //SocialAction();
						break;
					case 0x35:
						// ChangeMoveType, устарел
						break;
					case 0x36:
						// ChangeWaitType, устарел
						break;
					case 0x37:
						msg = new RequestSellItem();
						break;
					case 0x38:
						msg = new RequestMagicSkillList();
						break;
					case 0x39:
						msg = new RequestMagicSkillUse();
						break;
					case 0x3a:
						msg = new Appearing(); //Appering();
						break;
					case 0x3b:
						if(Config.ALLOW_WAREHOUSE)
							msg = new SendWareHouseDepositList();
						break;
					case 0x3c:
						msg = new SendWareHouseWithDrawList();
						break;
					case 0x3d:
						msg = new RequestShortCutReg();
						break;
					case 0x3e:
						//	msg = new RequestShortCutUse(); // Format: cddc  ?
						break;
					case 0x3f:
						msg = new RequestShortCutDel();
						break;
					case 0x40:
						msg = new RequestBuyItem();
						break;
					case 0x41:
						//	msg = new RequestDismissPledge(); //Format: c ?
						break;
					case 0x42:
						msg = new RequestJoinParty();
						break;
					case 0x43:
						msg = new RequestAnswerJoinParty();
						break;
					case 0x44:
						msg = new RequestWithDrawalParty();
						break;
					case 0x45:
						msg = new RequestOustPartyMember();
						break;
					case 0x46:
						//msg = new RequestDismissParty(); // Format: c ?
						break;
					case 0x47:
						msg = new CannotMoveAnymore();
						break;
					case 0x48:
						msg = new RequestTargetCanceld(); //RequestTargetCancel();
						break;
					case 0x49:
						msg = new Say2C();
						break;
					// -- maybe GM packet's
					case 0x4a:
						if(!data.hasRemaining())
						{
							handleIncompletePacket(client);
							break;
						}
						id2 = data.get() & 0xff;
						switch(id2)
						{
							case 0x00:
								//	msg = new SendCharacterInfo(); // Format: ccS ?
								break;
							case 0x01:
								//	msg = new SendSummonCmd(); // Format: ccS ?
								break;
							case 0x02:
								//	msg = new SendServerStatus(); // Format: cc ?
								break;
							case 0x03:
								//	msg = new SendL2ParamSetting(); // Format: ccdd ?
								break;
							default:
								try
								{
									player = client.getPlayer();
									int size = data.remaining();
									_log.warn("Unknown Packet: 0x4A:" + Integer.toHexString(id2) + ", from ip: " + client.getIpAddr() + ", Char: " + player != null ? player.toString() : "null" + ", Login: " + client.getLoginName());
									byte[] array = new byte[size];
									data.get(array);
									_log.warn(printData(array, size));
									client.addUPTryes();
									client.closeNow(false);
								}
								catch(NullPointerException e)
								{
									if(_log == null)
										System.out.println("GamePacketHandler: WTF _log is null ???!!!");
									if(client != null)
										System.out.println("GamePacketHandler: " + client);
									e.printStackTrace();
								}
								break;
						}
						break;
					case 0x4b:
						//	msg = new ?();
						break;
					case 0x4c:
						//	msg = new ?();
						break;
					case 0x4d:
						msg = new RequestPledgeMemberList();
						break;
					case 0x4e:
						//	msg = new ?();
						break;
					case 0x4f:
						//	msg = new RequestMagicItem(); // Format: c ?
						break;
					case 0x50:
						msg = new RequestSkillList(); // trigger
						break;
					case 0x51:
						//	msg = new ?();
						break;
					case 0x52:
						msg = new MoveWithDelta(); // Format: cddd ?
						break;
					case 0x53:
						msg = new RequestGetOnVehicle(); //GetOnVehicle();
						break;
					case 0x54:
						msg = new RequestGetOffVehicle(); //GetOffVehicle();
						break;
					case 0x55:
						msg = new AnswerTradeRequest();
						break;
					case 0x56:
						msg = new RequestActionUse();
						break;
					case 0x57:
						msg = new RequestRestart();
						break;
					case 0x58:
						//msg = new RequestSiegeInfo();
						break;
					case 0x59:
						msg = new ValidatePosition();
						break;
					case 0x5a:
						msg = new RequestSEKCustom();
						// Format: cdd, SlotNum : %d Direction : %d
						break;
					case 0x5b:
						msg = new StartRotatingC();
						break;
					case 0x5c:
						msg = new FinishRotatingC();
						break;
					case 0x5d:
						//	msg = new ?();
						break;
					case 0x5e:
						msg = new RequestShowBoard(); //RequestShowboard();
						break;
					case 0x5f:
						msg = new RequestEnchantItem();
						break;
					case 0x60:
						msg = new RequestDestroyItem();
						break;
					case 0x61:
						//	msg = new ?();
						break;
					case 0x62:
						msg = new RequestQuestList();
						break;
					case 0x63:
						msg = new RequestQuestAbort(); //RequestDestroyQuest();
						break;
					case 0x64:
						//	msg = new ?();
						break;
					case 0x65:
						msg = new RequestPledgeInfo();
						break;
					case 0x66:
						msg = new RequestPledgeExtendedInfo();
						break;
					case 0x67:
						msg = new RequestPledgeCrest();
						break;
					case 0x68:
						//	msg = new ?();
						break;
					case 0x69:
						//	msg = new ?();
						break;
					case 0x6a:
						//	msg = new ?();
						break;
					case 0x6b:
						msg = new RequestSendL2FriendSay(); // ?
						break;
					case 0x6c:
						msg = new RequestShowMiniMap(); //RequestOpenMinimap();
						break;
					case 0x6d:
						msg = new RequestSendMsnChatLog();
						break;
					case 0x6e:
						msg = new RequestReload(); // record video
						break;
					case 0x6f:
						msg = new RequestHennaEquip();
						break;
					case 0x70:
						msg = new RequestHennaRemoveList();
						break;
					case 0x71:
						msg = new RequestHennaItemRemoveInfo();
						break;
					case 0x72:
						msg = new RequestHennaRemove();
						break;
					case 0x73:
						msg = new RequestAquireSkillInfo(); //RequestAcquireSkillInfo();
						break;
					case 0x74:
						msg = new SendBypassBuildCmd();
						break;
					case 0x75:
						msg = new RequestMoveToLocationInVehicle();
						break;
					case 0x76:
						msg = new CannotMoveAnymoreInVehicle();
						break;
					case 0x77:
						msg = new RequestFriendInvite();
						break;
					case 0x78:
						msg = new RequestFriendAddReply(); // ?
						break;
					case 0x79:
						msg = new RequestFriendList();
						break;
					case 0x7a:
						msg = new RequestFriendDel();
						break;
					case 0x7c:
						msg = new RequestAquireSkill(); //RequestAcquireSkill();
						break;
					case 0x7d:
						msg = new RequestRestartPoint();
						break;
					case 0x7e:
						msg = new RequestGMCommand();
						break;
					case 0x7f:
						msg = new RequestPartyMatchConfig();
						break;
					case 0x80:
						msg = new RequestPartyMatchList();
						break;
					case 0x81:
						msg = new RequestPartyMatchDetail();
						break;
					case 0x82:
						//msg = new RequestPrivateStoreBuy();
						break;
					case 0x83:
						msg = new RequestPrivateStoreBuy();
						break;
					case 0x84:
						//	msg = new ReviveReply(); // format: cd ?
						break;
					case 0x85:
						msg = new RequestTutorialLinkHtml();
						break;
					case 0x86:
						msg = new RequestTutorialPassCmdToServer();
						break;
					case 0x87:
						msg = new RequestTutorialQuestionMark(); //RequestTutorialQuestionMarkPressed();
						break;
					case 0x88:
						msg = new RequestTutorialClientEvent();
						break;
					case 0x89:
						msg = new RequestPetition();
						break;
					case 0x8a:
						msg = new RequestPetitionCancel();
						break;
					case 0x8b:
						msg = new RequestGmList(); //RequestGMList();
						break;
					case 0x8c:
						msg = new RequestJoinAlly();
						break;
					case 0x8d:
						msg = new RequestAnswerJoinAlly();
						break;
					case 0x8e:
						// Команда /allyleave - выйти из альянса
						msg = new RequestWithdrawAlly();
						break;
					case 0x8f:
						// Команда /allydismiss - выгнать клан из альянса
						msg = new RequestOustAlly();
						break;
					case 0x90:
						// Команда /allydissolve - распустить альянс
						msg = new RequestDismissAlly();
						break;
					case 0x91:
						msg = new RequestSetAllyCrest();
						break;
					case 0x92:
						msg = new RequestAllyCrest();
						break;
					case 0x93:
						msg = new RequestChangePetName();
						break;
					case 0x94:
						msg = new RequestPetUseItem();
						break;
					case 0x95:
						msg = new RequestGiveItemToPet();
						break;
					case 0x96:
						msg = new RequestPrivateStoreQuitSell(); // ?
						break;
					case 0x97:
						msg = new SetPrivateStoreMsgSell(); // ?
						break;
					case 0x98:
						msg = new RequestPetGetItem();
						break;
					case 0x99:
						// msg = new RequestPrivateStoreBuyManage(); // format: c
						break;
					case 0x9a:
						msg = new SetPrivateStoreListBuy(); // ?
						break;
					case 0x9b:
						//	msg = new RequestPrivateStoreBuyManageCancel(); // ?
						break;
					case 0x9c:
						msg = new RequestPrivateStoreQuitBuy();
						break;
					case 0x9d:
						msg = new SetPrivateStoreMsgBuy();
						break;
					case 0x9e:
						//	msg = new RequestPrivateStoreBuyList(); // ?
						break;
					case 0x9f:
						msg = new SendPrivateStoreBuyBuyList(); // ?
						break;
					case 0xa0:
						//	msg = new SendTimeCheckPacket(); // format: cdd ?
						break;
					case 0xa1:
						//	msg = new ?();
						break;
					case 0xa2:
						//	msg = new ?();
						break;
					case 0xa3:
						//	msg = new ?();
						break;
					case 0xa4:
						//	msg = new ?();
						break;
					case 0xa5:
						//	msg = new ?();
						break;
					case 0xa6:
						msg = new RequestSkillCoolTime();
						break;
					case 0xa7:
						msg = new RequestPackageSendableItemList();
						break;
					case 0xa8:
						msg = new RequestPackageSend();
						break;
					case 0xa9:
						msg = new RequestBlock();
						break;
					case 0xaa:
						//msg = new RequestCastleSiegeInfo(); // format: cd ?
						//msg = new RequestSiegeInfo();
						break;
					case 0xab:
						msg = new RequestSiegeAttackerList(); //RequestCastleSiegeAttackerList();
						break;
					case 0xac:
						msg = new RequestSiegeDefenderList(); //RequestCastleSiegeDefenderList();
						break;
					case 0xad:
						msg = new RequestJoinSiege(); //RequestJoinCastleSiege();
						break;
					case 0xae:
						msg = new RequestConfirmSiegeWaitingList(); //RequestConfirmCastleSiegeWaitingList();
						break;
					case 0xaf:
						//	msg = new RequestSetCastleSiegeTime(); // format: cdd ?
						break;
					case 0xb0:
						msg = new RequestMultiSellChoose();
						break;
					case 0xb1:
						msg = new NetPingResponse(); // format: cddd ?
						break;
					case 0xb2:
						//	msg = new RequestRemainTime(); //Format: c
						break;
					case 0xb3:
						msg = new BypassUserCmd();
						break;
					case 0xb4:
						msg = new SnoopQuit();
						break;
					case 0xb5:
						msg = new RequestRecipeBookOpen();
						break;
					case 0xb6:
						msg = new RequestRecipeItemDelete();
						break;
					case 0xb7:
						msg = new RequestRecipeItemMakeInfo();
						break;
					case 0xb8:
						msg = new RequestRecipeItemMakeSelf();
						break;
					case 0xb9:
						// msg = new RequestRecipeShopManageList(); deprecated // format: c
						break;
					case 0xba:
						msg = new RequestRecipeShopMessageSet();
						break;
					case 0xbb:
						msg = new RequestRecipeShopListSet();
						break;
					case 0xbc:
						msg = new RequestRecipeShopManageQuit();
						break;
					case 0xbd:
						msg = new RequestRecipeShopManageCancel();
						break;
					case 0xbe:
						msg = new RequestRecipeShopMakeInfo();
						break;
					case 0xbf:
						msg = new RequestRecipeShopMakeDo();
						break;
					case 0xc0:
						msg = new RequestRecipeShopSellList(); // format: cd ?
						break;
					case 0xc1:
						msg = new ObserverReturn(); //RequestObserverEndPacket();
						break;
					case 0xc2:
						//msg = new VoteSociality(); // Recommend
						break;
					case 0xc3:
						msg = new RequestHennaList(); //RequestHennaItemList();
						break;
					case 0xc4:
						msg = new RequestHennaItemInfo();
						break;
					case 0xc5:
						msg = new RequestBuySeed();
						break;
					case 0xc6:
						msg = new DlgAnswer(); //ConfirmDlg();
						break;
					case 0xc7:
						msg = new RequestPreviewItem();
						break;
					case 0xc8:
						msg = new RequestSSQStatus();
						break;
					case 0xc9:
						msg = new PetitionVote(); // format: cddS ?
						break;
					case 0xca:
						//	msg = new ?();
						break;
					case 0xcb:
						msg = new ReplyGameGuardQuery();
						break;
					case 0xcc:
						msg = new RequestPledgePower();
						break;
					case 0xcd:
						msg = new RequestMakeMacro();
						break;
					case 0xce:
						msg = new RequestDeleteMacro();
						break;
					case 0xcf:
						msg = new RequestProcureCrop(); // ?
						break;
					case 0xd0: // 824 protocol
						if(!data.hasRemaining())
						{
							handleIncompletePacket(client);
							break;
						}
						id3 = data.getShort() & 0xffff;

						if(Config.PACKET_FLOOD_PROTECTOR)
						{
							ActionType act = client.checkPacket(id << 8 | id3);
							try
							{
								switch(act)
								{
									case log: // Log user
										_log.warn("FP: pkt(0x" + Integer.toHexString(id << 8 | id3) + ") logg " + client);
										break;
									case drop_log: // Drop packet
										_log.warn("FP: pkt(0x" + Integer.toHexString(id << 8 | id3) + ") drop " + client);
										return null;
									case drop: // Just drop
										return null;
								}
							}
							catch(Exception e)
							{
								return null;
							}
						}

						switch(id3)
						{
							case 0x00:
								//	msg = new ?();
								break;
							case 0x01:
								msg = new RequestManorList();
								break;
							case 0x02:
								msg = new RequestProcureCropList();
								break;
							case 0x03:
								msg = new RequestSetSeed();
								break;
							case 0x04:
								msg = new RequestSetCrop();
								break;
							case 0x05:
								msg = new RequestWriteHeroWords();
								break;
							case 0x06:
								msg = new RequestExMPCCAskJoin();
								break;
							case 0x07:
								msg = new RequestExMPCCAcceptJoin();
								break;
							case 0x08:
								msg = new RequestExMPCCExit();
								break;
							case 0x09:
								msg = new RequestOustFromPartyRoom();
								break;
							case 0x0a:
								msg = new RequestDismissPartyRoom();
								break;
							case 0x0b:
								msg = new RequestWithdrawPartyRoom();
								break;
							case 0x0c:
								msg = new RequestHandOverPartyMaster();
								break;
							case 0x0d:
								msg = new RequestAutoSoulShot();
								break;
							case 0x0e:
								msg = new RequestExEnchantSkillInfo();
								break;
							case 0x0f:
								msg = new RequestExEnchantSkill();
								break;
							case 0x10:
								msg = new RequestPledgeCrestLarge(); //RequestExPledgeCrestLarge();
								break;
							case 0x11:
								msg = new RequestSetPledgeCrestLarge(); //RequestExSetPledgeCrestLarge();
								break;
							case 0x12:
								msg = new RequestPledgeSetAcademyMaster();
								break;
							case 0x13:
								msg = new RequestPledgePowerGradeList();
								break;
							case 0x14:
								msg = new RequestPledgeMemberPowerInfo();
								break;
							case 0x15:
								msg = new RequestPledgeSetMemberPowerGrade();
								break;
							case 0x16:
								msg = new RequestPledgeMemberInfo();
								break;
							case 0x17:
								msg = new RequestPledgeWarList();
								break;
							case 0x18:
								msg = new RequestExFishRanking();
								break;
							case 0x19:
								msg = new RequestPCCafeCouponUse();
								break;
							case 0x1a:
								//	msg = new ?();
								// format: (ch)b, b - array размером в 64 байта
								break;
							case 0x1b:
								msg = new RequestDuelStart();
								break;
							case 0x1c:
								msg = new RequestDuelAnswerStart();
								break;
							case 0x1d:
								//msg = new RequestTutorialClientEvent(); //RequestExSetTutorial();
								// требует отладки, ИМХО, это совсем другой пакет (с) Drin
								break;
							case 0x1e:
								msg = new RequestExRqItemLink(); // chat item links
								break;
							case 0x1f:
								 msg = new CannotMoveAnymoreInAirShip();
								// format: (ch)ddddd
								break;
							case 0x20:
								msg = new MoveToLocationInAirShip();
								break;
							case 0x21:
								msg = new RequestKeyMapping();
								break;
							case 0x22:
								msg = new RequestSaveKeyMapping();
								break;
							case 0x23:
								msg = new RequestExRemoveItemAttribute();
								break;
							case 0x24:
								msg = new RequestSaveInventoryOrder(); // сохранение порядка инвентаря
								break;
							case 0x25:
								msg = new RequestExitPartyMatchingWaitingRoom();
								break;
							case 0x26:
								msg = new RequestConfirmTargetItem();
								break;
							case 0x27:
								msg = new RequestConfirmRefinerItem();
								break;
							case 0x28:
								msg = new RequestConfirmGemStone();
								break;
							case 0x29:
								msg = new RequestOlympiadObserverEnd();
								break;
							case 0x2a:
								msg = new RequestCursedWeaponList();
								break;
							case 0x2b:
								msg = new RequestCursedWeaponLocation();
								break;
							case 0x2c:
								msg = new RequestPledgeReorganizeMember();
								break;
							case 0x2d:
								msg = new RequestExMPCCShowPartyMembersInfo();
								break;
							case 0x2e:
								msg = new RequestOlympiadMatchList(); // не уверен (в клиенте называется RequestOlympiadMatchList)
								break;
							case 0x2f:
								msg = new RequestAskJoinPartyRoom();
								break;
							case 0x30:
								msg = new AnswerJoinPartyRoom();
								break;
							case 0x31:
								msg = new RequestListPartyMatchingWaitingRoom();
								break;
							case 0x32:
								msg = new RequestExEnchantSkillSafe(); // format: (ch)dd ?
								break;
							case 0x33:
								msg = new RequestExEnchantSkillUntrain(); // format: (ch)dd ?
								break;
							case 0x34:
								msg = new RequestExEnchantSkillRouteChange(); // format: (ch)dd ?
								break;
							case 0x35:
								msg = new RequestExEnchantItemAttribute();
								break;
							case 0x36:
								//RequestGotoLobby - случается при многократном нажатии кнопки "вход"
								//msg = new RequestExGetOnAirShip(); // format: (ch)dddd ?
								break;
							case 0x37:
								msg = new RequestExGetOffAirShip(); // format: (ch)dddd ?
								break;
							case 0x38:
								msg = new RequestExMoveToLocationAirShip();
								break;
							case 0x39:
								msg = new RequestBidItemAuction();
								break;
							case 0x3a:
								msg = new RequestInfoItemAuction();
								break;
							case 0x3b:
								msg = new RequestExChangeName();
								break;
							case 0x3c:
								msg = new RequestAllCastleInfo();
								break;
							case 0x3d:
								msg = new RequestAllFortressInfo();
								break;
							case 0x3e:
								msg = new RequestAllAgitInfo();
								break;
							case 0x3f:
								msg = new RequestFortressSiegeInfo();
								break;
							case 0x40:
								msg = new RequestGetBossRecord();
								break;
							case 0x41:
								msg = new RequestRefine();
								break;
							case 0x42:
								msg = new RequestConfirmCancelItem();
								break;
							case 0x43:
								msg = new RequestRefineCancel();
								break;
							case 0x44:
								msg = new RequestExMagicSkillUseGround();
								break;
							case 0x45:
								msg = new RequestDuelSurrender();
								break;
							case 0x46:
								msg = new RequestExEnchantSkillInfoDetail();
								break;
							case 0x47:
								//msg = new RequestExMagicSkillUseGround();
								break;
							case 0x48:
								msg = new RequestFortressMapInfo();
								break;
							case 0x49:
								msg = new RequestPVPMatchRecord();
								break;
							case 0x4a:
								msg = new SetPrivateStoreWholeMsg();
								break;
							case 0x4b:
								msg = new RequestDispel();
								break;
							case 0x4c:
								msg = new RequestExTryToPutEnchantTargetItem();
								break;
							case 0x4d:
								msg = new RequestExTryToPutEnchantSupportItem();
								break;
							case 0x4e:
								msg = new RequestExCancelEnchantItem();
								break;
							case 0x4f:
								msg = new RequestChangeNicknameColor();
								break;
							case 0x50:
								msg = new RequestResetNickname();
								break;
							case 0x51:
								if(data.remaining() < 4)
								{
									handleIncompletePacket(client);
									break;
								}
								int id4 = data.getInt();
								switch(id4)
								{
									case 0x00:
										msg = new RequestBookMarkSlotInfo();
										break;
									case 0x01:
										msg = new RequestSaveBookMarkSlot();
										break;
									case 0x02:
										msg = new RequestModifyBookMarkSlot();
										break;
									case 0x03:
										msg = new RequestDeleteBookMarkSlot();
										break;
									case 0x04:
										msg = new RequestTeleportBookMark();
										break;
									case 0x05:
										msg = new RequestChangeBookMarkSlot();
										break;
									default:
										_log.warn("Unknown BookMark packet: " + id4);
										break;
								}
								break;
							case 0x52:
								msg = new RequestWithDrawPremiumItem();
								break;
							case 0x53:
								msg = new RequestExJump();
								break;
							case 0x54:
								msg = new RequestExStartShowCrataeCubeRank();
								break;
							case 0x55:
								msg = new RequestExStopShowCrataeCubeRank();
								break;
							case 0x56:
								msg = new NotifyStartMiniGame();
								break;
							case 0x57:
								msg = new RequestExJoinDominionWar();
								break;
							case 0x58:
								msg = new RequestExDominionInfo();
								break;
							case 0x59:
								msg = new RequestExCleftEnter();
								break;
							case 0x5a:
								//msg = new RequestExBlockGameEnter();
								msg = new RequestExCubeGameChangeTeam();
								break;
							case 0x5b:
								msg = new RequestExEndScenePlayer();
								break;
							case 0x5c:
								//msg = new RequestExBlockGameVote();
								msg = new RequestExCubeGameReadyAnswer();
								break;
							case 0x5D:
								msg = new RequestExListMpccWaiting();
								break;
							case 0x5E:
								msg = new RequestExManageMpccRoom();
								break;
							case 0x5F:
								msg = new RequestExJoinMpccRoom();
								break;
							case 0x60:
								msg = new RequestExOustFromMpccRoom();
								break;
							case 0x61:
								msg = new RequestExDismissMpccRoom();
								break;
							case 0x62:
								msg = new RequestExWithdrawMpccRoom();
								break;
							case 0x63:
								msg = new RequestExSeedPhase();
								break;
							case 0x64:
								msg = new RequestExMpccPartymasterList();
								break;
							case 0x65:
								msg = new RequestExPostItemList();
								break;
							case 0x66:
								msg = new RequestExSendPost();
								break;
							case 0x67:
								msg = new RequestExRequestReceivedPostList();
								break;
							case 0x68:
								msg = new RequestExDeleteReceivedPost();
								break;
							case 0x69:
								msg = new RequestExRequestReceivedPost();
								break;
							case 0x6A:
								msg = new RequestExReceivePost();
								break;
							case 0x6B:
								msg = new RequestExRejectPost();
								break;
							case 0x6C:
								msg = new RequestExRequestSentPostList();
								break;
							case 0x6D:
								msg = new RequestExDeleteSentPost();
								break;
							case 0x6E:
								msg = new RequestExRequestSentPost();
								break;
							case 0x6F:
								msg = new RequestExCancelSentPost();
								break;
							case 0x70:
								msg = new RequestExShowNewUserPetition();
								break;
							case 0x71:
								msg = new RequestExShowStepTwo();
								break;
							case 0x72:
								msg = new RequestExShowStepThree();
								break;
							case 0x73:
								// ExRaidReserveResult
								break;
							case 0x75:
								msg = new RequestExRefundItem();
								break;
							case 0x76:
								msg = new RequestExBuySellUIClose(); // закрытие окна торговли с npc, trigger
								break;
							case 0x77:
								msg = new RequestExEventMatchObserverEnd();
								break;
							case 0x78:
								msg = new RequestPartyLootModification();
								break;
							case 0x79:
								msg = new AnswerPartyLootModification();
								break;
							case 0x7A:
								msg = new AnswerCoupleAction();
								break;
							case 0x7B:
								msg = new RequestExBR_EventRankerList();
								break;
							case 0x7C:
								//msg = new AskMembership();
								break;
							case 0x7D:
								//msg = new RequestAddExpandQuestAlarm();
								break;
							case 0x7E:
								msg = new RequestVoteNew();
								break;
							case 0x7F:
								//msg = ???;
								break;
							case 0x80:
								//msg = ???;
								break;
							case 0x81:
								//msg = ???;
								break;
							case 0x82:
								//msg = ???;
								break;
							case 0x83:
								//Agit packets wtf?
								int id5 = data.getInt();
								switch(id5)
								{
									/**-- RequestExAgitInitialize chd 0x01
									-- RequestExAgitDetailInfo chdcd 0x02
									-- RequestExMyAgitState chd  0x03
									-- RequestExRegisterAgitForBidStep1 chd 0x04
									-- RequestExRegisterAgitForBidStep2 chddQd 0x05
									-- RequestExRegisterAgitForBidStep3 chddQd 0x05 -no error? 0x05
									-- RequestExConfirmCancelRegisteringAgit chd 0x07
									-- RequestExProceedCancelRegisteringAgit chd 0x08
									-- RequestExConfirmCancelAgitBid chdd 0x09
									-- RequestExReBid chdd 0x10
									-- RequestExAgitListForLot chd 0x11
									-- RequestExApplyForAgitLotStep1 chdc 0x12
									-- RequestExApplyForAgitLotStep2 chdc 0x13
									-- RequestExAgitListForBid chdd 0x14
									-- RequestExApplyForBidStep1 chdd  0x0D
									-- RequestExApplyForBidStep2 chddQ 0x0E
									-- RequestExApplyForBidStep3 chddQ 0x0F
									-- RequestExConfirmCancelAgitLot chdc 0x09
									-- RequestExProceedCancelAgitLot chdc 0x0A
									-- RequestExProceedCancelAgitBid chdd 0x0A**/
								}
								break;
							case 0x84:
								msg = new RequestExAddPostFriendForPostBox();
								break;
							case 0x85:
								msg = new RequestExDeletePostFriendForPostBox();
								break;
							case 0x86:
								msg = new RequestExShowPostFriendListForPostBox();
								break;
							case 0x87:
								msg = new RequestExFriendListForPostBox();
								break;
							case 0x88:
								msg = new RequestExOlympiadList();
								break;
							case 0x89:
								msg = new RequestExBR_GamePoint();
								break;
							case 0x8A:
								msg = new RequestExBR_ProductList();
								break;
							case 0x8B: 
								msg = new RequestExBR_ProductInfo();
								break; 
							case 0x8C: 
								msg = new RequestExBR_BuyProduct();
								break; 
							case 0x8D: 
								msg = new RequestExBR_RecentProductList();
								break; 
							case 0x8E: 
								msg = new RequestBR_MinigameLoadScores();
								break; 
							case 0x8F: 
								msg = new RequestBR_MinigameInsertScore();
								break; 
							case 0x90: 
								msg = new RequestBR_LectureMark();
								break; 
							default:
								try
								{
									int size = data.remaining();
									player = client.getPlayer();
									_log.warn("Unknown Packet: 0xd0:" +
											Integer.toHexString(id3) +
											", from ip: " + client.getIpAddr() +
											", Char: " + player != null ? player.toString() : "null" +
											", Login: " + client.getLoginName());
									if(ConfigProtect.PROTECT_ENABLE && !client.getHWID().isEmpty())
										_log.error("Client HWID: " + client.getHWID());
									byte[] array = new byte[size];
									data.get(array);
									_log.warn(printData(array, size));
									client.addUPTryes();
									client.closeNow(false);
								}
								catch(Exception e)
								{
									if(_log == null)
										System.out.println("GamePacketHandler: WTF _log is null ???!!!");
									if(client != null)
										System.out.println("GamePacketHandler: " + client);
									e.printStackTrace();
								}
								break;
						}
						break;

					default:
					{
						try
						{
							int sz = data.remaining();
							player = client.getPlayer();
							_log.warn("Unknown Packet:" +
									Integer.toHexString(id) +
									", from ip: " + client.getIpAddr() +
									", Char: " + player != null ? player.toString() : "null" +
									", Login: " + client.getLoginName());
							byte[] arr = new byte[sz];
							data.get(arr);
							System.out.println("packet " + Integer.toHexString(id) + " on " + client.getState().toString());
							if(ConfigProtect.PROTECT_ENABLE && !client.getHWID().isEmpty())
								_log.error("Client HWID: " + client.getHWID());
							_log.warn(printData(arr, sz));
							client.addUPTryes();
							client.closeNow(false);
						}
						catch(Exception e)
						{
							if(_log == null)
								System.out.println("GamePacketHandler: WTF _log is null ???!!!");
							if(client != null)
								System.out.println("GamePacketHandler: " + client);
							e.printStackTrace();
						}
						break;
					}
				}
				break;
		}
		return msg;
	}

	// impl
	public GameClient create(MMOConnection<GameClient> con)
	{
		return new GameClient(con);
	}

	public void execute(ReceivablePacket<GameClient> rp)
	{
		try
		{
			//			if(rp.getClient().getState() == IN_GAME)
			//				ThreadPoolManager.getInstance().executePacket(rp);
			//			else
			//				ThreadPoolManager.getInstance().executeIOPacket(rp);

			ThreadPoolManager.getInstance().executeGameClientPacket((L2GameClientPacket) rp);
		}
		catch(RejectedExecutionException e)
		{
			// if the server is shutdown we ignore
			if(!ThreadPoolManager.getInstance().isShutdown())
				_log.error("Failed executing: " + rp.getClass().getSimpleName() + " for Client: " + rp.getClient().toString());
		}
	}

	public static String printData(byte[] data, int len)
	{
		StringBuffer result = new StringBuffer();

		int counter = 0;

		for(int i = 0; i < len; i++)
		{
			if(counter % 16 == 0)
				result.append(fillHex(i, 4)).append(": ");

			result.append(fillHex(data[i] & 0xff, 2)).append(" ");
			counter++;
			if(counter == 16)
			{
				result.append("   ");
				int charpoint = i - 15;
				for(int a = 0; a < 16; a++)
				{
					int t1 = data[charpoint++];
					if(t1 > 0x1f && t1 < 0x80)
						result.append((char) t1);
					else
						result.append('.');
				}
				result.append("\n");
				counter = 0;
			}
		}

		int rest = data.length % 16;
		if(rest > 0)
		{
			for(int i = 0; i < 17 - rest; i++)
				result.append("   ");

			int charpoint = data.length - rest;
			for(int a = 0; a < rest; a++)
			{
				int t1 = data[charpoint++];
				if(t1 > 0x1f && t1 < 0x80)
					result.append((char) t1);
				else
					result.append('.');
			}
			result.append("\n");
		}

		return result.toString();
	}

	private static String fillHex(int data, int digits)
	{
		String number = Integer.toHexString(data);

		for(int i = number.length(); i < digits; i++)
			number = "0" + number;

		return number;
	}

	public void handleIncompletePacket(GameClient client)
	{
		_log.warn("Packet not completed. Maybe cheater. IP:" + client.getIpAddr());
		if(ConfigProtect.PROTECT_ENABLE && !client.getHWID().isEmpty())
			_log.error("Client HWID: " + client.getHWID());
		if(client.getUPTryes() > 4)
		{
			L2Player player = client.getPlayer();
			if(player == null)
			{
				_log.warn("Too many incomplete packets, connection closed. IP: " + client.getIpAddr() + ", account:" + client.getLoginName());
				client.closeNow(true);
				return;
			}
			_log.warn("Too many incomplete packets, connection closed. IP: " + client.getIpAddr() + ", account:" + client.getLoginName() + ", character:" + player.getName());
			player.logout(false, false, true);
		}
		else
			client.addUPTryes();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HeaderInfo<GameClient> handleHeader(SelectionKey key, ByteBuffer buf)
	{
		if(buf.remaining() >= 2)
		{
			int dataPending = (buf.getShort() & 0xffff) - 2;
			GameClient client = ((MMOConnection<GameClient>) key.attachment()).getClient();
			return getHeaderInfoReturn().set(0, dataPending, false, client);
		}
		GameClient client = ((MMOConnection<GameClient>) key.attachment()).getClient();
		return getHeaderInfoReturn().set(2 - buf.remaining(), 0, false, client);
	}
}
