package core;

import java.util.Random;

/*
 * Represents the synaptic strength of a 'Connection' with a 'value'.
 *
 * It stores the 'currentWeightChange' obtained from the current learning epoch
 * and the 'previousWeightChange' obtained from the previous learning epoch;
 * which are necessary to implement 'BackPropagation with momentum'.
 */

public class Weight {

	private double value,
                     currentWeightChange,
                     previousWeightChange;


// Creation.


	// Simple Weight.

	public Weight(double initial) {
		this.value = initial;
		this.currentWeightChange = 0.0;
		this.previousWeightChange = 0.0;
	}

	// Random Weight within a given range.

	public Weight(double min, double max, Random generator) {
		if (generator != null) {
			this.value = this.randomWithinRange(min, max, generator);
			this.currentWeightChange = 0.0;
			this.previousWeightChange = 0.0;
		}
	}


//	Configuration.


	// Value.

	public void decrease(double amount) {
		this.value -= amount;
	}
	public void increase(double amount) {
		this.value += amount;
	}
	public double getValue() {
		return this.value;
	}
	public void setValue(double value) {
		this.value = value;
	}

	// Current weight change.

	public double getCurrentChange() {
		return this.currentWeightChange;
	}
	public void setCurrentChange(double change) {
		this.currentWeightChange = change;
	}

	// Previous weight change.

	public double getPreviousChange() {
		return this.previousWeightChange;
	}
	public void setPreviousChange(double change) {
		this.previousWeightChange = change;
	}

	// Randomization.

	public double randomWithinRange(double min, double max, Random generator) {
		double random = 0.0;
		if (generator != null) {

			random = (min + (generator.nextDouble() * (max - min)));
		}
		return random;
	}
	public void randomize(double min, double max, Random generator) {
		this.value = this.randomWithinRange(min, max, generator);
	}

	// TODO: COMPLETE 'TOSTRING' METHOD. What format?
}