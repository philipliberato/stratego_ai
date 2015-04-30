package edu.virginia.pnl8zp;

import java.util.Random;

public class GA_BoardSetup_Individual
{
    public int NUMBER_OF_WEIGHTS = 5;							// keeping this general so I can use GA in multiple places
    private double[] genes = new double[NUMBER_OF_WEIGHTS];		// genes are the weights I think
    private double fitnessValue;

    public GA_BoardSetup_Individual(int numOfWeights) {
    	this.NUMBER_OF_WEIGHTS = numOfWeights;
    }

    public void randGenes() {
        Random rand = new Random();
        for(int i = 0; i < NUMBER_OF_WEIGHTS; i++) {
            this.setGene(i, rand.nextDouble());
        }
    }

    // mutate: flip-recombination
    public void mutate() {
        Random rand = new Random();
        int index = rand.nextInt(NUMBER_OF_WEIGHTS);
        this.setGene(index, 1 - this.getGene(index));
    }

    public double evaluate() {
        double fitness = 0;
        for(int i = 0; i < NUMBER_OF_WEIGHTS; i++) {
            fitness += this.getGene(i);
        }
        this.fitnessValue = fitness;
        return fitness;
    }
    
    // GETTERS AND SETTERS
    
    public int getNumOfWeights() {
    	return NUMBER_OF_WEIGHTS;
    }

    public double getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(double fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    public double getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, double gene) {
        this.genes[index] = gene;
    }
}