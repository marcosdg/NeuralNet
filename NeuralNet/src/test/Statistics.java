package test;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

	private static List<Double> etrs, // errors training set.
                                  evas, // errors validation set.
                                  etts, // errors test set.
                                  gls,  // generalization losses.
                                  pks;  // training progresses.
// Creation.


	public Statistics() {
		Statistics.etrs = new ArrayList<Double>();
		Statistics.evas = new ArrayList<Double>();
		Statistics.etts = new ArrayList<Double>();
		Statistics.gls = new ArrayList<Double>();
		Statistics.pks = new ArrayList<Double>();
	}


// Data to work on.


	// Errors on training set.

	public static void storeEtr(Double etr) {
		if (etr != null) {
			Statistics.etrs.add(etr);
		}
	}
	public static List<Double> getEtrs() {
		return Statistics.etrs;
	}

	// Errors on validation set.

	public static void storeEva(Double eva) {
		if (eva != null) {
			Statistics.evas.add(eva);
		}
	}
	public static List<Double> getEvas() {
		return Statistics.evas;
	}

	// Errors on test set.

	public static void storeEtt(Double ett) {
		if (ett != null) {
			Statistics.etts.add(ett);
		}
	}
	public static List<Double> getEtts() {
		return Statistics.etts;
	}

	// Generalization losses.

	public static void storeGL(Double gl) {
		if (gl != null) {
			Statistics.gls.add(gl);
		}
	}
	public static List<Double> getGLs() {
		return Statistics.gls;
	}

	// Training progresses.

	public static void storePK(Double pk) {
		if (pk != null) {
			Statistics.pks.add(pk);
		}
	}
	public static List<Double> getPKs() {
		return Statistics.pks;
	}


// Stats.


	public static Double sumAll(List<Double> values) {
		Double total = 0.0;

		for (Double value: values) {
			total += value;
		}
		return total;
	}
	public static Double getMin(List<Double> values) {
		Double min = values.get(0);

		for (Double value: values) {
			min = (value < min) ? value : min;
		}
		return min;
	}
	public static Double getMax(List<Double> values) {
		Double max = values.get(0);

		for (Double value: values) {
			max = (value > max) ? value : max;
		}
		return max;
	}

	public static Double getAverage(List<Double> values) {
		return (sumAll(values) / values.size());
	}
	public static List<Double> getSquareDifferencesFromAvg(List<Double> values) {
		Double avg = Statistics.getAverage(values);
		List<Double> sqdiffs = new ArrayList<Double>();

		for (Double value : values) {
			sqdiffs.add(Math.pow((value - avg), 2));
		}
		return sqdiffs;
}
	public static Double getStandarDeviation(List<Double> values) {
		List<Double> sqdiffs = Statistics.getSquareDifferencesFromAvg(values);

		return Math.sqrt(Statistics.getAverage(sqdiffs));
	}
}