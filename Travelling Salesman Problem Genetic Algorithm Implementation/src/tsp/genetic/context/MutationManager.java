package tsp.genetic.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import tsp.genetic.entitites.Chromosome;

/**
 * class that contains methods for applying different mutation 
 * types to be applied in the genetic algorithm
 * @auhtor Gökay Gülsoy 
 * @since 29/05/2025
 */
public class MutationManager {

    private double mutationRate; // mutation probability
    private Random mutationProbGenerator; // generates probability for mutation

    public MutationManager(double mutationRate) {
        this.mutationRate = mutationRate;
        this.mutationProbGenerator = new Random();
    }

    /**
     * method to apply one of four mutation strategies
     * randomly (Swap mutation, Insert Mutation, Scramble Mutation, Inversion
     * Mutation)
     * 
     * @param chr Chromosome object upon which selected mutation to be applied
     */
    public void applyMutation(Chromosome chr) {

        if (mutationProbGenerator.nextDouble() < mutationRate) {
            Random rng = new Random();
            int mutationType = rng.nextInt(4);

            switch (mutationType) {
                case 0:
                    applySwapMutation(chr);
                    break;
                case 1:
                    applyInsertMutation(chr);
                    break;
                case 2:
                    applyScrambleMutation(chr);
                    break;
                case 3:
                    applyInversionMutation(chr);
            }
        }

    }

    /**
     * method to apply swap mutation which randomly
     * swaps two genes in locOrder of Chromosome object
     * 
     * @param chr Chromosome object upon which swap mutation to be applied
     */
    private void applySwapMutation(Chromosome chr) {

        Random rng = new Random();
        ArrayList<Integer> locOrders = chr.getLocOrder();
        int geneIndex1 = rng.nextInt(locOrders.size());

        int geneIndex2 = rng.nextInt(locOrders.size());
        // find gene index that is different from geneIndex1
        while (geneIndex2 == geneIndex1) {
            geneIndex2 = rng.nextInt(locOrders.size());
        }

        // swap genes at indices geneIndex1 and geneIndex2
        int tempGeneValue = locOrders.get(geneIndex1);
        locOrders.set(geneIndex1, locOrders.get(geneIndex2));
        locOrders.set(geneIndex2, tempGeneValue);
    }

    /**
     * method to apply insert mutation which picks two gene
     * at random move the second to follow the first, shifting the
     * rest along to accomodate
     * 
     * @param chr Chromosome object upon which insert mutation to be
     *            applied
     */
    private void applyInsertMutation(Chromosome chr) {

        Random rng = new Random();
        ArrayList<Integer> locOrders = chr.getLocOrder();
        // geneIndex1 represents the beginning of insert mutation
        int geneIndex1 = rng.nextInt(locOrders.size() - 1);
        // geneIndex2 represents the end of insert mutation
        int geneIndex2 = (geneIndex1 + 1) + rng.nextInt(locOrders.size() - geneIndex1 - 1);

        ArrayList<Integer> entriesPreceedingGeneIndex1 = new ArrayList<>();
        ArrayList<Integer> entriesBetweenGeneIndex1AndGeneIndex2 = new ArrayList<>();
        ArrayList<Integer> entriesSucceedingGeneIndex2 = new ArrayList<>();

        // copying entries to corresponding Lists from locOrders List
        for (int i = 0; i < geneIndex1; i++) {
            entriesPreceedingGeneIndex1.add(locOrders.get(i));
        }

        for (int i = geneIndex1 + 1; i < geneIndex2; i++) {
            entriesBetweenGeneIndex1AndGeneIndex2.add(locOrders.get(i));
        }

        for (int i = geneIndex2 + 1; i < locOrders.size(); i++) {
            entriesSucceedingGeneIndex2.add(locOrders.get(i));
        }

        // keep track of fill index for locOrders entries
        int fillIndex = 0;
        for (Integer gene : entriesPreceedingGeneIndex1) {
            locOrders.set(fillIndex, gene);
            fillIndex++;
        }

        // copy genes located at geneIndex1 and geneIndex2
        Integer gene1 = locOrders.get(geneIndex1);
        Integer gene2 = locOrders.get(geneIndex2);

        locOrders.set(fillIndex, gene1);
        fillIndex++;
        locOrders.set(fillIndex, gene2);
        fillIndex++;

        for (Integer gene : entriesBetweenGeneIndex1AndGeneIndex2) {
            locOrders.set(fillIndex, gene);
            fillIndex++;
        }

        for (Integer gene : entriesSucceedingGeneIndex2) {
            locOrders.set(fillIndex, gene);
            fillIndex++;
        }

    }

    /**
     * method to apply scramble mutation which picks a subset
     * of genes at random and randomly rearranges the genes in
     * those positions
     * 
     * @param chr Chromosome object upon which scramble mutation to be
     *            applied
     */
    private void applyScrambleMutation(Chromosome chr) {

        Random rng = new Random();
        ArrayList<Integer> locOrders = chr.getLocOrder();
        // geneIndex1 represents the beginning of insert mutation
        int geneIndex1 = rng.nextInt(locOrders.size() - 1);
        // geneIndex2 represents the end of insert mutation
        int geneIndex2 = (geneIndex1 + 1) + rng.nextInt(locOrders.size() - geneIndex1 - 1);

        ArrayList<Integer> subsetOfGenes = new ArrayList<>();
        for (int i = geneIndex1; i <= geneIndex2; i++) {
            subsetOfGenes.add(locOrders.get(i));
        }

        Collections.shuffle(subsetOfGenes);
        int shuffleIndex = 0;
        // after shuffling, copy genes to locOrders
        for (int i = geneIndex1; i <= geneIndex2; i++) {
            locOrders.set(i, subsetOfGenes.get(shuffleIndex));
            shuffleIndex++;
        }

    }

    /**
     * method to apply inversion mutation which picks
     * two genes at random and inverting the genes between
     * them
     * 
     * @param chr Chromosome object upon which inversion mutation to be
     *            applied
     */
    private void applyInversionMutation(Chromosome chr) {

        Random rng = new Random();
        ArrayList<Integer> locOrders = chr.getLocOrder();
        // geneIndex1 represents the beginning of insert mutation
        int geneIndex1 = rng.nextInt(locOrders.size() - 1);
        // geneIndex2 represents the end of insert mutation
        int geneIndex2 = (geneIndex1 + 1) + rng.nextInt(locOrders.size() - geneIndex1 - 1);

        ArrayList<Integer> subsetOfGenes = new ArrayList<>();
        for (int i = geneIndex1; i <= geneIndex2; i++) {
            subsetOfGenes.add(locOrders.get(i));
        }

        Collections.reverse(subsetOfGenes);
        int invertedIndex = 0;
        // after inversion, copy genes to locOrders
        for (int i = geneIndex1; i <= geneIndex2; i++) {
            locOrders.set(i, subsetOfGenes.get(invertedIndex));
            invertedIndex++;
        }

    }

    @Override
    public String toString() {
        return "MutationManager(mutation rate: " + this.mutationRate + ")";
    }

}
