package edu.virginia.pnl8zp;

import java.math.BigDecimal;
import java.util.Random;

public class TrainingDataInputs {
	
	public static Double[][] trainingDataInputs;
	
	public TrainingDataInputs() {
		trainingDataInputs();
	}

	public static void trainingDataInputs() {
		
		trainingDataInputs = new Double[100][6];
		
		Double w1 = 0.0;
		Double w2 = 0.0;
		Double w3 = 0.0;
		Double w4 = 0.0;
		Double w5 = 0.0;
		Double w6 = 0.0;
				
		Random weightGenerator = new Random();
		
		for(int i = 0; i < 100; i++) {
			w1 = new BigDecimal(weightGenerator.nextDouble()).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
			w2 = new BigDecimal(weightGenerator.nextDouble()).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
			w3 = new BigDecimal(weightGenerator.nextDouble()).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
			w4 = new BigDecimal(weightGenerator.nextDouble()).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
			w5 = new BigDecimal(weightGenerator.nextDouble()).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
			w6 = new BigDecimal(weightGenerator.nextDouble()).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
			trainingDataInputs[i][0] = w1;
			trainingDataInputs[i][1] = w2;
			trainingDataInputs[i][2] = w3;
			trainingDataInputs[i][3] = w4;
			trainingDataInputs[i][4] = w5;
			trainingDataInputs[i][5] = w6;
		}
	}
	
	public static void printTrainingDataInputs() {
		for(Double[] d : trainingDataInputs) {
			System.out.println(d[0] + " " + d[1] + " " + d[2] + " " + d[3] + " " + d[4] + " " + d[5]);
		}
	}
	
}
