package core.learning.stop;

abstract public class StopCriteria {

	abstract public boolean isMet();

	public static boolean isMaxEpochsStop(StopCriteria criteria) {
		return (criteria instanceof MaxEpochsStop);
	}
	public static boolean isEarlyStop(StopCriteria criteria) {
		return (criteria instanceof EarlyStop);
	}

}