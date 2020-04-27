package ru.l2gw.commons.crontab;

/**
 * <p>
 * This interface describes the ValueMatcher behavior. A ValueMatcher is an
 * object that validate an integer value against a set of rules.
 * </p>
 *
 * @author Carlo Pelliccia
 */
interface ValueMatcher
{

	/**
	 * Validate the given integer value against a set of rules.
	 *
	 * @param value The value.
	 * @return true if the given value matches the rules of the ValueMatcher,
	 *         false otherwise.
	 */
	public boolean match(int value);

}
