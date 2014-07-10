package test;

import core.data.Benchmark;
import core.data.FileParser;
import core.data.Sample;

public class TestParse {
	public static void main(String[] args) {


// Data file.

		String cancer1_path = "/home/marcos/Escritorio/cancer1.dt";

// Parsing.

		FileParser parser = new FileParser(cancer1_path);
		Benchmark cancer1 = parser.parseFile();

System.out.println("============= TEST: DATA FILE PARSING  ==================");

		System.out.println(" Data file parsed: " + cancer1.getLabel());

		System.out.println(" Num. training samples: " +
                            cancer1.getNumberOfTrainingSamples());
		System.out.println(" Num. validation samples: " +
                cancer1.getNumberOfValidationSamples());
		System.out.println(" Num. test samples: " +
                cancer1.getNumberOfTestSamples());

		System.out.println(" First sample parsed:");
		Sample sample1 = cancer1.getSamples().get(0);

		System.out.println("\t --- Inputs ---");
		for (Double input: sample1.getInputVector()) {
			System.out.print(input + " ");
		}

		System.out.println();

		System.out.println("\t --- Desired Outputs ---");
		for (Double desired: sample1.getDesiredOutputVector()) {
			System.out.print(desired + " ");
		}
	}
}
