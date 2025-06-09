package tsp.genetic.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import tsp.genetic.entitites.Chromosome;

/**
 * class that contains methods for applying different crossover 
 * types to be applied in the genetic algorithm
 * @auhtor Gökay Gülsoy 
 * @since 29/05/2025
 */
public class CrossoverManager { 

    private double crossoverRate; // crossover probability
    private Random crossoverProbGenerator; // generates probability for crossover

    public CrossoverManager(double crossoverRate) {
        this.crossoverRate = crossoverRate;
        this.crossoverProbGenerator = new Random();
    }

    /**
     * method to apply one of three crossover strategies
     * randomly (Order 1 Crossover, Partially Mapped Crossover, Cycle Crossover)
     * 
     * @param parent1 Chromosome representing first parent
     * @param parent2 Chromosome representing second parent
     * @return New children Chromosomes produced by selected crossover
     *         strategy
     */
    public ArrayList<Chromosome> applyCrossover(Chromosome parent1, Chromosome parent2) {
        ArrayList<Chromosome> children = new ArrayList<>();
        Chromosome child1 = null;
        Chromosome child2 = null;

        if (crossoverProbGenerator.nextDouble() < crossoverRate) {
            Random rng = new Random();
            int crossoverType = rng.nextInt(3);

            switch (crossoverType) {
                case 0:
                    child1 = applyOrderOneCrossover(parent1, parent2);
                    child2 = applyOrderOneCrossover(parent2, parent1);
                    break;
                case 1:
                    child1 = applyPartiallyMappedCrossover(parent1, parent2);
                    child2 = applyPartiallyMappedCrossover(parent2, parent1);
                    break;
                case 2:
                    child1 = applyCycleCrossover(parent1, parent2);
                    child2 = applyCycleCrossover(parent2, parent1);
            }

            children.add(child1);
            children.add(child2);

            return children;
        }

        // parents are returned as the children directly, in case of no crossover
        children.add(parent1);
        children.add(parent2);

        return children;
    }

    /**
     * method to perform Order 1 Crossover (OX1)
     * between two parents to produce a new child
     * 
     * @param parent1 Chromosome representing first parent
     * @param parent2 Chromosome representing second parent
     * @return New child Chromosome produced by Order 1 Crossover
     */
    private Chromosome applyOrderOneCrossover(Chromosome parent1, Chromosome parent2) {

        ArrayList<Integer> permutation1 = parent1.getLocOrder();
        ArrayList<Integer> permutation2 = parent2.getLocOrder();

        int permutationSize = permutation1.size();
        Random rng = new Random();
        int crossoverStart = rng.nextInt(permutationSize - 1);
        int crossoverEnd = (crossoverStart + 1) + rng.nextInt(permutationSize - crossoverStart - 1);

        ArrayList<Integer> childPermutation = new ArrayList<>(Collections.nCopies(permutationSize, null));
        for (int i = crossoverStart; i <= crossoverEnd; i++) {
            childPermutation.set(i, permutation1.get(i));
        }

        // filling remaining positions from parent2
        int insertIndex = (crossoverEnd + 1) % permutationSize;
        int permutation2Index = insertIndex;
        int childSize = crossoverEnd - crossoverStart + 1;

        while (childSize < permutationSize) {
            int gene = permutation2.get(permutation2Index);
            permutation2Index = (permutation2Index + 1) % permutationSize;

            if (!childPermutation.contains(gene)) {
                childPermutation.set(insertIndex, gene);
                insertIndex = (insertIndex + 1) % permutationSize;
                childSize++;
            }
        }

        Chromosome child = new Chromosome(childPermutation);
        return child;
    }

    /**
     * method to perform Partially Mapped Crossover (PMX)
     * between two parents to produce a new child
     * 
     * @param parent1 Chromosome representing first parent
     * @param parent2 Chromosome representing second parent
     * @return New child Chromosome produced by Partially Mapped Crossover
     */
    private Chromosome applyPartiallyMappedCrossover(Chromosome parent1, Chromosome parent2) {

        ArrayList<Integer> permutation1 = parent1.getLocOrder();
        ArrayList<Integer> permutation2 = parent2.getLocOrder();

        int permutationSize = permutation1.size();
        Random rng = new Random();
        int crossoverStart = rng.nextInt(permutationSize - 1);
        int crossoverEnd = (crossoverStart + 1) + rng.nextInt(permutationSize - crossoverStart - 1);

        // List for keeping track of filled entries in child after
        // mapping genes from crossover segment
        ArrayList<Integer> filledIndexesInChild = new ArrayList<>();
        // List representing the new permutation after crossover
        ArrayList<Integer> childPermutation = new ArrayList<>(Collections.nCopies(permutationSize, null));
        // List that stores the genes from crossover segment
        ArrayList<Integer> childEntries = new ArrayList<>();

        for (int i = crossoverStart; i <= crossoverEnd; i++) {
            filledIndexesInChild.add(i);
            Integer crossoverGene = permutation1.get(i);
            childEntries.add(crossoverGene);
            childPermutation.set(i, crossoverGene);
        }

        ArrayList<Integer> nonCopiedEntryIndexesFromPerm2 = new ArrayList<>();
        // finding indexes of genes that haven't been added to
        // child from crossover segment for permutation2
        for (int i = crossoverStart; i <= crossoverEnd; i++) {
            if (!childEntries.contains(permutation2.get(i))) {
                nonCopiedEntryIndexesFromPerm2.add(i);
            }
        }

        for (int i = 0; i < nonCopiedEntryIndexesFromPerm2.size(); i++) {
            int index = nonCopiedEntryIndexesFromPerm2.get(i);
            // locating element in child for element i
            Integer childEntry = childPermutation.get(index);
            // finding index of childEntry in permutation2
            int indexOfCrossOverEntry = permutation2.indexOf(childEntry);
            // if place in child is already occupied by another gene
            // find that gene's index in permutation2 (thus in child) and
            // place i at that index if it is not occupied (recursively resolve dependencies)
            boolean cyclicDependencyFinished = false;
            while (!cyclicDependencyFinished) {
                if (filledIndexesInChild.contains(indexOfCrossOverEntry)) {
                    childEntry = childPermutation.get(indexOfCrossOverEntry);
                    indexOfCrossOverEntry = permutation2.indexOf(childEntry);
                }

                else {
                    cyclicDependencyFinished = true;
                }
            }

            childPermutation.set(indexOfCrossOverEntry, permutation2.get(index));
            filledIndexesInChild.add(indexOfCrossOverEntry);
        }

        // after having dealt with crossover segment, rest of
        // the child is filled from parent2
        for (int i = 0; i < permutationSize; i++) {
            if (!filledIndexesInChild.contains(i)) {
                childPermutation.set(i, permutation2.get(i));
            }
        }

        Chromosome child = new Chromosome(childPermutation);
        return child;
    }

    /**
     * method to apply Cycle crossover between
     * two parents to produce a new child
     * 
     * @param parent1 Chromosome representing first parent
     * @param parent2 Chromosome representing second parent
     * @return New child Chromosome produced by Cycle Crossover
     */
    private Chromosome applyCycleCrossover(Chromosome parent1, Chromosome parent2) {

        ArrayList<Integer> permutation1 = parent1.getLocOrder();
        ArrayList<Integer> permutation2 = parent2.getLocOrder();
        int permutationSize = permutation1.size();

        ArrayList<Integer> filledIndexesInChild = new ArrayList<>();
        ArrayList<Integer> childPermutation = new ArrayList<>(Collections.nCopies(permutationSize, null));

        for (int i = 0; i < permutationSize; i++) {
            if (!filledIndexesInChild.contains(i)) {
                filledIndexesInChild.add(i);
                boolean alternate = false;
                Integer firstGeneOfCycle = permutation1.get(i);
                Map<Integer, Integer> indexGeneMap = new HashMap<>();
                int currentIndex = i;
                Integer mutualEntry = permutation2.get(i);
                indexGeneMap.put(currentIndex, firstGeneOfCycle);

                while (firstGeneOfCycle.intValue() != mutualEntry.intValue()) {
                    currentIndex = permutation1.indexOf(mutualEntry);
                    filledIndexesInChild.add(currentIndex);
                    // alternate between parents when copying genes
                    if (alternate) {
                        indexGeneMap.put(currentIndex, permutation2.get(currentIndex));
                    }

                    else {
                        indexGeneMap.put(currentIndex, permutation1.get(currentIndex));
                    }

                    mutualEntry = permutation2.get(currentIndex);
                }

                // at the end of teach cycle alternate
                alternate = alternate ? false : true;

                // add genes in cycle to child
                for (Map.Entry<Integer, Integer> entry : indexGeneMap.entrySet()) {
                    childPermutation.set(entry.getKey(), entry.getValue());
                }

            }
        }

        Chromosome child = new Chromosome(childPermutation);
        return child;
    }

    @Override
    public String toString() {
        return "CrossoverManager(crossover rate: " + this.crossoverRate + ")";
    }

}
