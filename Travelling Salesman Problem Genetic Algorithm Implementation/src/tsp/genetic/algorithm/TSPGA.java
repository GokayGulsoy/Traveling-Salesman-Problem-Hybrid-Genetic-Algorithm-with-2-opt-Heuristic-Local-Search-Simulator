package tsp.genetic.algorithm;

import java.util.ArrayList;
import java.util.Collections;

import tsp.genetic.context.CrossoverManager;
import tsp.genetic.context.GAContextManager;
import tsp.genetic.context.MutationManager;
import tsp.genetic.entitites.Chromosome;

/**
 * class that implements the genetic algorithm 
 * for Travelling Salesman problem. It applies the 
 * steps of population generation, applying crossover and 
 * mutation with certain probabilities, then survivor selection 
 * respectively. It iterates {@code maxIter} number of times as  
 * a termination condition.  
 */
public class TSPGA {

    private GAContextManager contextManager;
    private CrossoverManager crossoverManager;
    private MutationManager mutationManager;

    public TSPGA(GAContextManager contextManager, CrossoverManager crossoverManager, MutationManager mutationManager) {
        this.contextManager = contextManager;
        this.crossoverManager = crossoverManager;
        this.mutationManager = mutationManager;
    }

    /**
     * method to run genetic algorithm for {@code maxIter}
     * number of times
     * 
     * @param maxIter maximum number of iterations to run genetic algorithm
     * @param populationSize number of individuals exist in population
     * @param performLocalSearch boolean parameter to indicate whether 2-opt
     * local search must be performed at each generation to improve fitness values
     */
    public void runGeneticAlgorithm(int maxIter, int populationSize, boolean performLocalSearch) {
        contextManager.generatePopulation(populationSize);
        Chromosome bestIndividual = null;
        ArrayList<ArrayList<Integer>> bestRoutes = new ArrayList<>();
        ArrayList<Double> bestFitnesses = new ArrayList<>();

        for (int i = 1; i <= maxIter; i++) {
            ArrayList<Chromosome> generation = new ArrayList<>();

            while (generation.size() < populationSize) {
                // selecting parents
                ArrayList<Chromosome> selectedParents = contextManager.selectParents();
                Chromosome parent1 = selectedParents.get(0);
                Chromosome parent2 = selectedParents.get(1);

                // applying crossover to produce children
                ArrayList<Chromosome> children = crossoverManager.applyCrossover(parent1, parent2);
                Chromosome child1 = children.get(0);
                Chromosome child2 = children.get(1);

                // applying mutation to children
                mutationManager.applyMutation(child1);
                mutationManager.applyMutation(child2);

                // calculating fitness values for children
                child1.computeFitness(contextManager.getDistanceMatrix());
                child2.computeFitness(contextManager.getDistanceMatrix());

                // apply 2-opt local search if performLocalSearch is true
                if (performLocalSearch) {
                    contextManager.applyTwoOptLocalSeach(child1);
                    contextManager.applyTwoOptLocalSeach(child2);
                }

                // add children to new generation
                generation.add(child1);
                generation.add(child2);
            }

            // selecting survivors via combining new generation with existing
            // population and selecting populationSize individuals from combined
            // population
            contextManager.growPopulation(generation);
            Collections.sort(contextManager.getPopulation(), contextManager);

            ArrayList<Chromosome> newPopulation = new ArrayList<>();
            for (int j = 0; j < populationSize; j++) {
                newPopulation.add(contextManager.getPopulation().get(j));
            }

            contextManager.setPopulation(newPopulation);
            // Chromosome object having highest fitness for current generation
            bestIndividual = contextManager.getPopulation().get(0);
            bestRoutes.add(bestIndividual.getLocOrder());
            bestFitnesses.add(bestIndividual.getFitness());

            // displaying bestInvidividual and its fitness value for current generation
            System.out.printf("Generation: %d Best Individual: %s | Total Distance: %.4f%n", i, bestIndividual.toString(),
                    (1 / bestIndividual.getFitness()));

        }

        // set the bestRoutes and bestFitnesses for ContextManager object
        contextManager.setBestRoutes(bestRoutes);
        contextManager.setBestFitnesses(bestFitnesses);
    }

}
