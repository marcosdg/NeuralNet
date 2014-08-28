package dev_tests;

import java.util.List;

import experiment.data.Benchmark;
import experiment.data.ProbenFileParser;
import experiment.data.Sample;

public class TestParse {
	public static void main(String[] args) {


// Data file.

		String proben_dir = "experiment";
		String proben_file_name = "experiment1.dt";

// Parsing.

		ProbenFileParser proben_parser = new ProbenFileParser(proben_dir, proben_file_name);

		proben_parser.parse();

		Benchmark benchmark = proben_parser.getBenchmark();

System.out.println("============= TEST: DATA FILE PARSING  ==================");

		System.out.println(" Data file parsed: " + benchmark.getPath());

		System.out.println(" Num. training samples: " +
				benchmark.getNumberOfTrainingSamples());

		System.out.println(" Num. validation samples: " +
				benchmark.getNumberOfValidationSamples());

		System.out.println(" Num. test samples: " +
				benchmark.getNumberOfTestSamples());

		List<Sample> samples = benchmark.getSamples();
		Sample sample = null;

		System.out.println("\t" + "FIRST 3 SAMPLES PARSED");

		for (int i = 0; i < 3; i += 1) {
			sample = samples.get(i);
			System.out.println("----- SAMPLE " + i + " -----");

			System.out.println("  * Inputs *");
			for (Double input: sample.getInputVector()) {
				System.out.print(input + " ");
			}
			System.out.println();

			System.out.println("  * Desired Outputs *");
			for (Double desired: sample.getDesiredOutputVector()) {
				System.out.print(desired + " ");
			}
			System.out.println();
		}
	}
}