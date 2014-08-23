package test;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

	private static List<Double> etrs, // errors training set.
                                  evas, // errors validation set.
                                  etts, // errors test set.
                                  gls,  // generalization losses.
                                  pks;  // training progresses.
	public Statistics() {
		Statistics.etrs = new ArrayList<Double>();
		Statistics.evas = new ArrayList<Double>();
		Statistics.etts = new ArrayList<Double>();
		Statistics.gls = new ArrayList<Double>();
		Statistics.pks = new ArrayList<Double>();
	}


// Errors on training set.


	public static void storeEtr(Double etr) {
		if (etr != null) {
			Statistics.etrs.add(etr);
		}
	}
	public static List<Double> getEtrs() {
		return Statistics.etrs;
	}


// // Errors on validation set.


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
}