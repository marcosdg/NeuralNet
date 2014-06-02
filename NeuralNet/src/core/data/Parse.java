package core.data;

import java.util.List;

public class Parse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ParseSamples ps = new ParseSamples();
		List<Sample> samples = ps.parseFile("/home/manu/cancer1.dt");
		System.out.println("Ejemplos parseados: " + samples.size());
	}

}
