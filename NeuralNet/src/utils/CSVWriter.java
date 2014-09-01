package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import experiment.Experiment;
import experiment.data.Benchmark;

// TODO: complete CSVWriter.

public class CSVWriter {

	private Experiment experiment;
	private static final String[] header = {
		"Problem", "Training set mean", "Training set stddev",
		"Validation set mean", "Validation set stddev",
		"Test set mean", "Test set stddev", "p", "Test set classificaction mean",
		"Test set classification stddev", "Overfit mean", "Overfit stddev",
		"Total epochs mean", "Total epochs stddev", "Relevant epochs mean", "Relevant epochs stddev"
	};
	private BufferedWriter writer;


// Creation.


	public CSVWriter(Experiment experiment) {
		if (!experiment.isDone()) {
			throw new IllegalArgumentException("Must run the experiment first");
		} else {
			this.experiment = experiment;
		}
		try {
			String userHomeFolder = System.getProperty("user.home");
			File file = new File(userHomeFolder, "results.csv");
			this.writer = new BufferedWriter(new FileWriter(file));
			writeCSVLine(this.header);
			generateCSVResults();
			this.writer.close();
        } catch ( IOException e ) {
           e.printStackTrace();
        }
	}


	private void writeCSVLine(String[] line) {
		try {
			this.writer.write(this.prepareString(line));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private String prepareString(String[] line) {
		String result = "";
		for (int i = 0; i < line.length; i++) {
			result += line[i];
			if (i != line.length - 1) {
				result += ";";
			}
		}
		return result;
	}


	private void generateCSVResults() {
		List<Benchmark> benchs = this.experiment.getBenchmarks();
		for (Benchmark bench: benchs) {
			String[] csvLine = new String[this.header.length];
			/* PROBLEM */
			csvLine[0] = bench.getName();
			/* Training set  */
			csvLine[1] = this.experiment.getEtrsMean(bench).toString();
			csvLine[2] = this.experiment.getEtrsStdev(bench).toString();
			/* Validation set */
			csvLine[3] = this.experiment.getEvasMean(bench).toString();
			csvLine[4] = this.experiment.getEvasStdev(bench).toString();
			/* Test set */
			csvLine[5] = this.experiment.getEtesMean(bench).toString();
			csvLine[6] = this.experiment.getEtesStdev(bench).toString();
			/* p */
			csvLine[7] = "TODO";
			/* Test set classification */
			csvLine[8] = this.experiment.getTestClassificationMissesMean(bench).toString();
			csvLine[9] = this.experiment.getTestClassificationMissesStdev(bench).toString();
			/* Overfit */
			csvLine[10] = this.experiment.getOverfitMean(bench).toString();
			csvLine[11] = this.experiment.getOverfitStdev(bench).toString();
			/* Total epochs */
			csvLine[12] = this.experiment.getTrainedEpochsMean(bench).toString();
			csvLine[13] = this.experiment.getTrainedEpochsStdev(bench).toString();
			/* Relevant epochs */
			csvLine[14] = this.experiment.getRelevantEpochsMean(bench).toString();
			csvLine[15] = this.experiment.getRelevantEpochsStdev(bench).toString();

			/* Write line in csv */
			this.writeCSVLine(csvLine);
		}
	}


// Experiment.


	public Experiment getExperiment() {
		return this.experiment;
	}
	public void setExperiment(Experiment that) {
		this.experiment = that;
	}
}