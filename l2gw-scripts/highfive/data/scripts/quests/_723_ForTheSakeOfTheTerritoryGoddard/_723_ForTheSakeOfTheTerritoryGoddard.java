package quests._723_ForTheSakeOfTheTerritoryGoddard;

import quests.ForTheSakeOfTheTerritorySuperclass.ForTheSakeOfTheTerritorySuperclass;

/**
 * @author: ic
 * @date: 11.07.2010 11:44:08
 */
public class _723_ForTheSakeOfTheTerritoryGoddard extends ForTheSakeOfTheTerritorySuperclass
{
	private static final int[] CATAPULTS = {
			36499, // Gludio
			36500, // Dion
			36501, // Giran
			36502, // Oren
			36503, // Aden
			36504, // Innadril
			36506, // Rune
			36507  // Schuttgart
	};

	private static final int[] LEADERS = {
			36508, // Gludio Military Association Leader
			36510, // Gludio  Religious Association Leader
			36513, // Gludio Economic Association Leader

			36514, // Dion Military Association Leader
			36516, // Dion  Religious Association Leader
			36519, // Dion Economic Association Leader

			36520, // Giran Military Association Leader
			36522, // Giran Religious Association Leader
			36525, // Giran Economic Association Leader

			36526, // Oren Military Association Leader
			36528, // Oren Religious Association Leader
			36531, // Oren Economic Association Leader

			36532, // Aden Military Association Leader
			36534, // Aden Religious Association Leader
			36537, // Aden Economic Association Leader

			36538, // Innadril Military Association Leader
			36540, // Innadril Religious Association Leader
			36543, // Innadril Economic Association Leader

			36550, // Rune Military Association Leader
			36552, // Rune Religious Association Leader
			36555, // Rune Economic Association Leader

			36556,  // Schuttgart Military Association Leader
			36558,  // Schuttgart Religious Association Leader
			36561   // Schuttgart Economic Association Leader
	};

	public static final int[] SUPPLIES = {
			36591, // Gludio
			36592, // Dion
			36593, // Giran
			36594, // Oren
			36595, // Aden
			36596, // Innadril
			36598, // Rune
			36599  // Schuttgart
	};

	public _723_ForTheSakeOfTheTerritoryGoddard()
	{
		super(723, "_723_ForTheSakeOfTheTerritoryGoddard", "For the Sake of the Territory - Goddard");
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
