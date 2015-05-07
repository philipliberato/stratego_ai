import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class LogisticRegression {
	
	public static double[] thetas; //coefficients for htheta equation
	public static ArrayList<Integer> output; //stores values of output given the training data
	public static ArrayList<double[]> data; //stores values of training data
	public static double learningRate = 0.000005;

	public static void main(String[] args) {
		thetas = new double[]{1, 0.5, 0.5, 0.3, 0.5, 0.3, 1};
		output = new ArrayList<Integer>();
		data = new ArrayList<double[]>();
		readFile();
		printThetas();
		train();
		printThetas();
		findStrongWeights();
	}
	
	public static void printThetas() {
		for(double theta : thetas) {
			System.out.print(theta + " ");
		}
		System.out.println();
	}
	
	public static void readFile() {
		File f = new File("trainingData.txt");
		Scanner file = null;
		try {
			file = new Scanner(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(file.hasNextLine()) {
			String input = file.nextLine();
			String[] line = input.split("\\s+");
			double[] inputValues = new double[7];
			for(int i = 0; i < 6; i++) {
				inputValues[i] = Double.parseDouble(line[i]);
			}
			data.add(inputValues);
			output.add(Integer.parseInt(line[6]));
		}
	}
	
	public static void train() {		
		double[] temp = new double[thetas.length];
		for (int j = 0; j < 7; j++) {
			for (int k = 0; k < data.size(); k++) {
				for (int i = 0; i < thetas.length; i++) {
					temp[i] = thetas[i] - learningRate * costFunc(); //dCostFunction(i);
				}
				for (int i = 0; i < thetas.length; i++) {
					thetas[i] = temp[i];
				}		
			}
		}
	}

	// returns the Sigmoid of the hypothesis function 
	public static double hTheta(double[] xVar) {
		double result = 0;
		for (int i = 0; i < xVar.length; i++) {
			result += xVar[i] * thetas[i];
		}
		return sigmoid(result);
	}
	
	public static double sigmoid(double z) {
		return 1 / (1 + Math.exp(-z));
	}
	
	public static double costFunc() {
		int m = output.size();
		double result = 0;
		for (int i = 0; i < m; i++) {
			result += -output.get(i) * Math.log(hTheta(data.get(i))) - (1 - output.get(i)) * (1 - Math.log(hTheta(data.get(i))));
		}
		return result / m;
	}
	
	public static void findStrongWeights() {
		Random randomInput = new Random();
		double[] optimalInputs = {0, 0, 0, 0, 0, 0, 0};
		while(hTheta(optimalInputs) < 0.95) {
			for(int i = 0; i < 7; i++) {
				optimalInputs[i] = new BigDecimal(randomInput.nextDouble()).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
			System.out.println("hTheta: " + hTheta(optimalInputs));
		}
		
		System.out.print("Optimal Inputs: ");
		for(int i = 0; i < 7; i++) {
			System.out.print(optimalInputs[i] + " ");
		}
	}

}
