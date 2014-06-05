package core.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ParseSamples
{
	private Integer bool_in = 0,
					real_in = 0,
					bool_out = 0,
					real_out = 0,
					training_samples,
					validation_samples,
					test_samples;
	private Integer inputs,
					outputs;

	public Benchmark parseFile(String filePath)
	{
		List<Sample> samples = new ArrayList<Sample>();
		String line;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filePath));
			
			while ((line = br.readLine()) != null) {
				if (line.contains("=")) {
					this.setParameters(line);
				} else {
					String[] split = line.split(" ");
					this.initParametersIfNeeded();
					Sample sample = new Sample();
					int i = 0;
					System.out.println("Entradas");
					for (i = 0; i < this.inputs; i++) {
						System.out.println(split[i]);
						sample.addInput(split[i]);
					}
					System.out.println("Salidas");
					for (int j = i; j < this.inputs + this.outputs; j++) {
						System.out.println(split[j]);
						sample.addOutput(split[j]);
					}
					
					samples.add(sample);
				}
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Benchmark benchmark = new Benchmark(this.training_samples, this.validation_samples, this.test_samples, samples);
		
		return benchmark;
	}
	
	public void setParameters(String line)
	{
		String[] split = line.split("=");
		if (split[0].contains("real_in")) {
			this.real_in = Integer.valueOf(split[1]);
		}
		
		if (split[0].contains("bool_in")) {
			this.bool_in = Integer.valueOf(split[1]);
		}
		
		if (split[0].contains("real_out")) {
			this.real_out = Integer.valueOf(split[1]);
		}
		
		if (split[0].contains("bool_out")) {
			this.bool_out = Integer.valueOf(split[1]);
		}
		
		if (split[0].contains("training_examples")) {
			this.training_samples = Integer.valueOf(split[1]);
		}
		
		if (split[0].contains("validation_examples")) {
			this.validation_samples = Integer.valueOf(split[1]);
		}
		
		if (split[0].contains("test_examples")) {
			this.test_samples = Integer.valueOf(split[1]);
		}
	}
	
	public void initParametersIfNeeded()
	{
		if (this.inputs == null && this.outputs == null) {
			if (this.bool_in != 0) {
				this.inputs = this.bool_in;
			} else {
				this.inputs = this.real_in;
			}
			
			if (this.bool_out != 0) {
				this.outputs = this.bool_out;
			} else {
				this.outputs = this.real_out;
			}
		}
	}
}
