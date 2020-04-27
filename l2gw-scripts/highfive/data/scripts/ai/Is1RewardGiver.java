package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 14.12.11 11:10
 */
public class Is1RewardGiver extends Citizen
{
	public int type = 0;

	public Is1RewardGiver(L2Character actor)
	{
		super(actor);
		fnHi = "ai_is_time_attack_reward001.htm";
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = (int) _thisActor.param1;
		_thisActor.i_ai2 = (int) _thisActor.param2;
		_thisActor.i_quest0 = 0;
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		L2Party party = Util.getParty(talker);
		if(party != null)
		{
			for(L2Player c0 : party.getPartyMembers())
			{
				Functions.sendUIEventFStr(c0, 1, 0, 0, "1", "1", "1", "60", "0", 1911119);
			}
		}

		if(_thisActor.i_ai2 == 0)
		{
			if(talker.isQuestStarted(694) && talker.getQuestState(694).getMemoState() == 2 && _thisActor.i_quest0 != 1)
			{
				QuestState st = talker.getQuestState(694);
				if(_thisActor.i_ai1 < 22 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_01.htm", 694);
					st.set("ex_1", 11);
					return true;
				}
				else if(_thisActor.i_ai1 <= 23 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_02.htm", 694);
					st.set("ex_1", 12);
					return true;
				}
				else if(_thisActor.i_ai1 <= 24 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_03.htm", 694);
					st.set("ex_1", 13);
					return true;
				}
				else if(_thisActor.i_ai1 <= 25 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_04.htm", 694);
					st.set("ex_1", 14);
					return true;
				}
				else if(_thisActor.i_ai1 <= 26 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_05.htm", 694);
					st.set("ex_1", 15);
					return true;
				}
				else if(_thisActor.i_ai1 <= 27 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_06.htm", 694);
					st.set("ex_1", 16);
					return true;
				}
				else if(_thisActor.i_ai1 <= 28 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_07.htm", 694);
					st.set("ex_1", 17);
					return true;
				}
				else if(_thisActor.i_ai1 <= 29 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_08.htm", 694);
					st.set("ex_1", 18);
					return true;
				}
				else if(_thisActor.i_ai1 <= 30 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_09.htm", 694);
					st.set("ex_1", 19);
					return true;
				}
				else if(_thisActor.i_ai1 > 30 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_10.htm", 694);
					st.set("ex_1", 20);
					return true;
				}
			}
			else if(_thisActor.i_quest0 == 1)
			{
				_thisActor.showPage(talker, "ai_is_time_attack_reward_q0694_13.htm", 694);
				return true;
			}
		}
		else if(_thisActor.i_ai2 == 1)
		{
			if(talker.isQuestStarted(695) && talker.getQuestState(695).getMemoState() == 2 && _thisActor.i_quest0 != 1)
			{
				QuestState st = talker.getQuestState(695);
				if(_thisActor.i_ai1 < 20 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_01.htm", 695);
					st.set("ex_1", 11);
					return true;
				}
				else if(_thisActor.i_ai1 <= 21 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_02.htm", 695);
					st.set("ex_1", 12);
					return true;
				}
				else if(_thisActor.i_ai1 <= 22 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_03.htm", 695);
					st.set("ex_1", 13);
					return true;
				}
				else if(_thisActor.i_ai1 <= 23 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_04.htm", 695);
					st.set("ex_1", 14);
					return true;
				}
				else if(_thisActor.i_ai1 <= 24 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_05.htm", 695);
					st.set("ex_1", 15);
					return true;
				}
				else if(_thisActor.i_ai1 <= 25 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_06.htm", 695);
					st.set("ex_1", 16);
					return true;
				}
				else if(_thisActor.i_ai1 <= 26 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_07.htm", 695);
					st.set("ex_1", 17);
					return true;
				}
				else if(_thisActor.i_ai1 <= 27 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_08.htm", 695);
					st.set("ex_1", 18);
					return true;
				}
				else if(_thisActor.i_ai1 <= 28 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_09.htm", 695);
					st.set("ex_1", 19);
					return true;
				}
				else if(_thisActor.i_ai1 > 28 * 60)
				{
					_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_10.htm", 695);
					st.set("ex_1", 20);
					return true;
				}
			}
			else if(_thisActor.i_quest0 == 1)
			{
				_thisActor.showPage(talker, "ai_is_time_attack_reward_q0695_13.htm", 695);
				return true;
			}
		}

		return super.onTalk(talker);
	}
}