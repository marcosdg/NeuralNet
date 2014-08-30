package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import experiment.Experiment;
import experiment.data.Benchmark;

public class NeuralNet {

	public static boolean files_loaded = false;

	public static void main(String[] args) {

		printIntro();

		InputStreamReader converter = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(converter);

		whichCommand(reader);

	}

	public static void whichCommand(BufferedReader reader) {
		Experiment experiment = null;
		try {
			String input = reader.readLine();
			List<String> command = Arrays.asList(input.split(" "));
			String name = command.get(0);
			List<String> params = command.subList(1, command.size());

			if (name.equals("-help")) {

				printHelp();
				whichCommand(reader);

			} else if (name.equals("-load") && !files_loaded) {

				System.out.println("Loading files ...");
				experiment = load(params);
				System.out.println("Loaded and ready for training.");

				printFilesInfo(experiment);
				confirm(reader);

			} else if (name.equals("-train") && files_loaded) {
				// TODO

			} else if (name.equals("-q")) {

				goodBye();

			} else {

				System.out.println("Please, repeat your command.");
				whichCommand(reader);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


// Presentation.


	public static void printIntro() {
		System.out.println();
		System.out.println("=======================================");
		System.out.println("              NEURAL NET               ");
		System.out.println("                  by                   ");
		System.out.println("                                       ");
		System.out.println("          Manuel Casas Barrado         ");
		System.out.println("           Marcos Díez García          ");
		System.out.println("                                       ");
		System.out.println("  Type -help to list all the commands  ");
		System.out.println("  Type -q to quit this program now     ");
		System.out.println("                                       ");
		System.out.println("=======================================");
		System.out.println();
	}


// Help.


	public static void printHelp() {
		System.out.println();
		System.out.println(" COMMANDS");
		System.out.println();
		System.out.println(" -help");
		System.out.println(" -q");
		System.out.println(" -load  <net_dir> <net_file> <proben_dir> ");
		System.out.println("         <proben_file> [proben_files]     ");
		System.out.println(" -train <number_of_runs>");
		System.out.println();
		System.out.println(" DESCRIPTION");
		System.out.println();
		System.out.println(" -help:  displays this information");
		System.out.println();
		System.out.println(" -q:     quits the program");
		System.out.println();
		System.out.println(" -load:  loads the specified neural network");
		System.out.println("         and the specified PROBEN1 data files");
		System.out.println();
		System.out.println(" -train: trains the neural network on PROBEN1");
		System.out.println("         data files, passing through all the");
		System.out.println("         benchmarks the given <number of runs>");
		System.out.println("         The statistics will be automatically");
		System.out.println("         written into a file in your desktop");
	}


// Load.


	public static Experiment load(List<String> params) {
		Experiment experiment = null;
		if (params.size() < 3) {
			System.out.println("Please, specify all the parameters");
		} else {
			String net_dir = params.get(0),
                   net_file = params.get(1),
                   proben_dir = params.get(2);
			List<String> proben_files = params.subList(3, params.size());

			experiment = new Experiment(net_dir, net_file, proben_dir,
                                         proben_files);
			experiment.loadAllBenchmarks();
			experiment.loadNeuralNetwork();
			files_loaded = true;
		}
		return experiment;
	}


// Confirm.


	public static void confirm(BufferedReader reader) throws IOException {
		System.out.println("Do you wish to continue the experiment ? (y/n)");
		String choice = reader.readLine();

		if (choice.startsWith("y")) {
			whichCommand(reader);
		} else {
			goodBye();
		}
	}


// Train.

	// TODO: integrate CSV writer to print statistics.

	public static void train(Experiment experiment, List<String> params) {
		/*
		if (experiment == null) {
			System.out.println("Something went wrong. Quiting now ...");
			goodBye();
		} else {
		    experiment.setNumberOfRuns();
			experiment.run();
		}
		*/
	}


// Exit.


	public static void  goodBye() {
		System.out.println("Goodbye.");
	}


// Log.


	public static void printFilesInfo(Experiment experiment) {
		System.out.println();
		System.out.println("------[ EXPERIMENT SETUP ]------- ");
		System.out.println();

		System.out.println("[BENCHMARKS]");
		List<Benchmark> benchs =  experiment.getBenchmarks();
		for (Benchmark bench: benchs){
			System.out.println(bench.getPath());
		}
		System.out.println("[NEURAL NETWORK] ");
		System.out.println(experiment.getNeuralNetwork().getLabel());
		System.out.println();

	}
}