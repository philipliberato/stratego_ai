package edu.virginia.pnl8zp;

import java.util.Random;

public class GA_Population {
    final static int ELITISM_K = 2;					// always keep the 3 best
    final static int POP_SIZE = 3 + ELITISM_K;  	// population size - 23
    final static int MAX_ITER = 2000;             	// max number of iterations
    final static double MUTATION_RATE = 0.05;    	// probability of mutation
    final static double CROSSOVER_RATE = 0.7;     	// probability of crossover

    public static Random m_rand = new Random();
    public GA_Individual[] candidateWeights;
    public double totalFitness;
    public static int numberOfGenes = 0;

    public GA_Population(int numOfGenes, double[] previousSessionFindings) {
    	candidateWeights = new GA_Individual[POP_SIZE];
    	numberOfGenes = numOfGenes;

        // build the initial population
        for (int i = 0; i < POP_SIZE; i++) {
        	candidateWeights[i] = new GA_Individual(numberOfGenes);
        	// if previous findings are null, all should be random - but if not null, previousSessionFindings should be a factor
        	candidateWeights[i].randGenes();
        }

        evaluate(); // evaluate the current population
    }

    // total fitness is the sum of each individual's fitness
    public double evaluate() {
        this.totalFitness = 0.0;
        for (int i = 0; i < POP_SIZE; i++) {
            this.totalFitness += candidateWeights[i].evaluate();
        }
        return this.totalFitness;
    }

//    public GA_BoardSetup_Individual rouletteWheelSelection() {
//        double randNum = m_rand.nextDouble() * this.totalFitness;
//        int idx;
//        for (idx = 0; idx < POP_SIZE && randNum > 0; idx++) {
//            randNum -= m_population[idx].getFitnessValue();
//        }
//        return m_population[idx-1];
//    }

    public GA_Individual findBestIndividual() {
        int iBestIndividual = 0;
        double currentMax = 0.0;
        double currentMin = 1.0;
        double currentVal;

        for (int idx = 0; idx < POP_SIZE; idx++) {
            currentVal = candidateWeights[idx].getFitnessValue();
            if (currentMax < currentMin) {
                currentMax = currentMin = currentVal;
                iBestIndividual = idx;
            }
            if (currentVal > currentMax) {
                currentMax = currentVal;
                iBestIndividual = idx;
            }
            if (currentVal < currentMin) {
                currentMin = currentVal;
            }
        }

        return candidateWeights[iBestIndividual]; // return individual with highest fitness
    }

    public static GA_Individual[] crossover(GA_Individual indiv1, GA_Individual indiv2) {
    	
    	GA_Individual[] newIndiv = new GA_Individual[2];
        newIndiv[0] = new GA_Individual(numberOfGenes);
        newIndiv[1] = new GA_Individual(numberOfGenes);

        int randPoint = m_rand.nextInt(numberOfGenes);
        int i;
        for (i = 0; i < randPoint; i++) {
            newIndiv[0].setGene(i, indiv1.getGene(i));
            newIndiv[1].setGene(i, indiv2.getGene(i));
        }
        for (; i < numberOfGenes; i++) {
            newIndiv[0].setGene(i, indiv2.getGene(i));
            newIndiv[1].setGene(i, indiv1.getGene(i));
        }
        return newIndiv;
    }
    
    // GETTERS AND SETTERS
    
    public void setPopulation(GA_Individual[] newPop) {
        System.arraycopy(newPop, 0, this.candidateWeights, 0, POP_SIZE);
    }

    public GA_Individual[] getPopulation() {
        return this.candidateWeights;
    }
}