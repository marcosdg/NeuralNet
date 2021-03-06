package core;

import java.util.Random;

/*
 * Represents the synaptic strength of a 'Connection' with a 'value'.
 *
 */

public class Weight {

	private double value,
                     correction,  // from the now epoch.
                     last_correction; // from the previous epoch.


// Creation.


	// Simple Weight.

	public Weight(double initial) {
		this.value = initial;
		this.correction = 0.0;
		this.last_correction = 0.0;
	}

	// Random Weight within a given range.

	public Weight(double min, double max, Random generator) {
		if (generator != null) {
			this.value = this.randomWithinRange(min, max, generator);
			this.correction = 0.0;
			this.last_correction = 0.0;
		}
	}
	public Weight copy() {
		Weight copy = new Weight(this.value);
		copy.setCorrection(this.correction);
		copy.setLastCorrection(this.last_correction);

		return copy;
	}

	public void reset() {
		this.value = 0.0;
		this.correction = 0.0;
		this.last_correction = 0.0;
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

	public double getCorrection() {
		return this.correction;
	}
	public void setCorrection(double value) {
		this.correction = value;
	}

	// Previous weight change.

	public double getLastCorrection() {
		return this.last_correction;
	}
	public void setLastCorrection(double value) {
		this.last_correction = value;
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
}