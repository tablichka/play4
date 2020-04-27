package quests._717_ForTheSakeOfTheTerritoryGludio;

import quests.ForTheSakeOfTheTerritorySuperclass.ForTheSakeOfTheTerritorySuperclass;

/**
 * @author: rage
 * @date: 11.07.2010 11:44:08
 */
public class _717_ForTheSakeOfTheTerritoryGludio extends ForTheSakeOfTheTerritorySuperclass
{
	private static final int[] CATAPULTS = {
			36500,
			36501,
			36502,
			36503,
			36504,
			36505,
			36506,
			36507
	};

	private static final int[] LEADERS = {
			36514, // Military Association Leader
			36516, // Religious Association Leader
			36519, // Economic Association Leader

			36520,
			36522,
			36525,

			36526,
			36528,
			36531,

			36532,
			36534,
			36537,

			36538,
			36540,
			36543,

			36544,
			36546,
			36549,

			36550,
			36552,
			36555,

			36556,
			36558,
			36561
	};

	private static final int[] SUPPLIES = {
			36592,
			36593,
			36594,
			36595,
			36596,
			36597,
			36598,
			36599
	};

	public _717_ForTheSakeOfTheTerritoryGludio()
	{
		super(717, "_717_ForTheSakeOfTheTerritoryGludio", "For the Sake of the Territory - Gludio");
		addKillId(CATAPULTS);
		addKillId(LEADERS);
		addKillId(SUPPLIES);
		addAttackId(CATAPULTS);
		addAttackId(LEADERS);
		addAttackId(SUPPLIES);
	}

	public int[] getCatapults()
	{
		return CATAPULTS;
	}

	public int[] getLeaders()
	{
		return LEADERS;
	}

	public int[] getSupplies()
	{
		return SUPPLIES;
	}
}
