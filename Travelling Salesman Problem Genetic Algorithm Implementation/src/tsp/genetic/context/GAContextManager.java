package tsp.genetic.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import tsp.genetic.entitites.Chromosome;
import tsp.genetic.entitites.Point;
import tsp.genetic.fileio.FileIO;

/**
 * class that contains methods and data fields to manage 
 * general tasks when running genetic algorithm such as 
 * population generation, survivor selection, distance matrix creation etc.
 * It also also keeps the best route and best fitness found for each generation
 * It also provides an implementation of 2-opt heuristic local search algorithm 
 * for further fitness improvement in each generation
 * @auhtor Gökay Gülsoy 
 * @since 29/05/2025
 */
public class GAContextManager implements Comparator<Chromosome> {

    private ArrayList<Chromosome> population;
    private ArrayList<Point> coordinates;
    private double[][] distanceMatrix; // keeps the distance between each location pair (upper triangular matrix)
    private ArrayList<ArrayList<Integer>> bestRoutes;
    private ArrayList<Double> bestFitnesses;

    public GAContextManager() {
        this.population = new ArrayList<>();
        this.coordinates = new ArrayList<>();
        this.bestRoutes = null;
        this.bestFitnesses = null;
        // filling coordinates with points from csv file
        FileIO.parseCSVFileAndCreateCoordinates(
                "Travelling Salesman Problem Genetic Algorithm Implementation\\att48TSP.csv", coordinates);
        // instantiate distanceMatrix field after filling coordinates List
        this.distanceMatrix = new double[coordinates.size()][coordinates.size()];
        // fill the distance matrix with distances
        fillDistanceMatrix(distanceMatrix);
    }

    public ArrayList<Chromosome> getPopulation() {
        return this.population;
    }

    public ArrayList<Point> getCoordinates() {
        return this.coordinates;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public ArrayList<ArrayList<Integer>> getBestRoutes() {
        return this.bestRoutes;
    }

    public ArrayList<Double> getFitnesses() {
        return this.bestFitnesses;
    }

    public void setPopulation(ArrayList<Chromosome> newPopulation) {
        this.population = newPopulation;
    }

    public void setBestRoutes(ArrayList<ArrayList<Integer>> bestRoutes) {
        this.bestRoutes = bestRoutes;
    }

    public void setBestFitnesses(ArrayList<Double> bestFitnesses) {
        this.bestFitnesses = bestFitnesses;
    }

    /**
     * method to generate population with {@code PopulationSize} number
     * of chromosomes
     * 
     * @param populationSize number of chromosomes to be generated
     */
    public void generatePopulation(int populationSize) {
        for (int i = 0; i < populationSize; i++) {
            ArrayList<Integer> locOrders = sampleLocations();
            Chromosome chr = new Chromosome(locOrders);
            chr.computeFitness(distanceMatrix);
            population.add(chr);
        }

    }

    /**
     * method to select two parents among population
     * where each Chromosome has an sampling probability
     * proportional to its fitness
     * 
     * @return ArrayList with two selected parents
     */
    public ArrayList<Chromosome> selectParents() {
        Map<Chromosome, Double> chromosomeFitnessMap = new HashMap<>();
        for (int i = 0; i < population.size(); i++) {
            Chromosome chr = population.get(i);
            chromosomeFitnessMap.put(chr, chr.getFitness());
        }

        // randomly select first parent
        Chromosome parent1 = selectRandomParentWithWeight(chromosomeFitnessMap);
        // remove the already selected parent from Map
        chromosomeFitnessMap.remove(parent1);
        // randomly select second parent
        Chromosome parent2 = selectRandomParentWithWeight(chromosomeFitnessMap);
        ArrayList<Chromosome> selectedParents = new ArrayList<>();
        selectedParents.add(parent1);
        selectedParents.add(parent2);

        return selectedParents;
    }

    /**
     * method to apply 2-opt local search algorithm
     * which improves the solution by reversing the
     * sub-route between two locations
     * 
     * @param chr Chromosome for which 2-opt local search algorithm
     *            to be applied
     */
    public void applyTwoOptLocalSeach(Chromosome chr) {
        boolean imrpovedFitness = true;

        int numberOfImprovements = 0;
        fitnessLoop: while (imrpovedFitness) {
            imrpovedFitness = false;
            ArrayList<Integer> locOrders = chr.getLocOrder();

            for (int i = 1; i < locOrders.size() - 2; i++) {
                for (int j = i + 1; j < locOrders.size(); j++) {
                    if (j - i == 1) { // skip adjacent locations (no sub-route exists)
                        continue;
                    }

                    ArrayList<Integer> newLocOrders = new ArrayList<>(locOrders);
                    Collections.reverse(newLocOrders.subList(i, j)); 

                    Chromosome newChromosome = new Chromosome(newLocOrders);
                    newChromosome.computeFitness(distanceMatrix);

                    double originalFitness = chr.getFitness();
                    double newFitness = newChromosome.getFitness();

                    if (newFitness > originalFitness) {
                        chr.setLocOrder(newLocOrders);
                        chr.setFitness(newFitness);
                        imrpovedFitness = true;
                        numberOfImprovements++;
                    }

                    // in order to keep the cost of 2-opt local search small
                    // stop iteration after 5 fitness improvements
                    if (numberOfImprovements == 5) {
                        imrpovedFitness = false;
                        break fitnessLoop;
                    }

                }

            }

        }

    }

    /**
     * method to compare two Chromosome objects with
     * respect to their fitness values. Chromosome that
     * has higher fitness value come first.
     * 
     * @param chr1 First Chromosome object to be compared
     * @param chr2 Second Chromosome object to be compared
     * @return positive int value if o1 has lower fitness
     *         value than o2, negative value if o1 has higher fitness
     *         value than o2, 0 if o1 and o2 are equal
     */
    @Override
    public int compare(Chromosome chr1, Chromosome chr2) {

        if (chr1.getFitness() < chr2.getFitness()) {
            return 1;
        }

        else if (chr1.getFitness() > chr2.getFitness()) {
            return -1;
        }

        else { // chr1 and chr2 has equal fitness values
            return 0;
        }

    }

    /**
     * method to grow existing population by
     * extending it with newGeneration
     * 
     * @param newGeneration ArrayList of Chromosome objects that
     *                      represent the children created by one iteration of
     *                      genetic algoritm
     */
    public void growPopulation(ArrayList<Chromosome> newGeneration) {
        population.addAll(newGeneration);
    }

    /**
     * method to randomly sample coordinates.size()
     * number of locations in {@code range 1 to coordinates.size() + 1}
     * both start and end values inclusive
     * 
     * @return Arraylist of order labels
     */
    private ArrayList<Integer> sampleLocations() {
        Random rng = new Random();
        ArrayList<Integer> locOrders = new ArrayList<>();
        ArrayList<Integer> labels = new ArrayList<>();
        // initializing labels
        Integer label = 1;
        for (int i = 0; i < coordinates.size(); i++) {
            labels.add(label);
            label++;
        }

        for (int i = 0; i < coordinates.size(); i++) {
            int locationIndex = rng.nextInt(labels.size());
            Integer location = labels.get(locationIndex);
            labels.remove(location);

            locOrders.add(location);
        }

        return locOrders;
    }

    /**
     * method to randomly select parent from population
     * with weight where weight is proportional to Chromosome
     * objects fitness value
     * 
     * @param chromosomeFitnessMap that contains Chromosome-fitness value mappings
     * @return Chromosome randomly selected from population
     */
    private Chromosome selectRandomParentWithWeight(Map<Chromosome, Double> chromosomeFitnessMap) { 
        // calculate total weight
        double totalWeight = chromosomeFitnessMap.values().stream().mapToDouble(Double::doubleValue).sum();

        // generate random number in the range of 0 to totalWeight
        Random rng = new Random();
        double randomValue = rng.nextDouble(totalWeight);
        // iterate through chromosomes and subtract weights to find selected item
        for (Map.Entry<Chromosome, Double> entry : chromosomeFitnessMap.entrySet()) {
            randomValue -= entry.getValue();

            if (randomValue < 0.0) {
                return entry.getKey();
            }
        }

        throw new RuntimeException("Should never reach this scope if weights are correctly defined !!");
    }

    /**
     * method to fill distance matrix for each location.
     * Each row represents a location and column represents
     * the connected location. At the end of this method call
     * Entry [i][j] contain double value representing distance
     * between location i and location j
     * 
     * @param firstPoint
     */
    private void fillDistanceMatrix(double[][] distanceMatrix) {
        for (int i = 0; i < coordinates.size(); i++) {
            Point loci = coordinates.get(i);
            for (int j = i + 1; j < coordinates.size(); j++) {
                Point locj = coordinates.get(j);
                double distance = computeDistance(loci, locj);
                distanceMatrix[i][j] = distance;
            }
        }
    }

    /**
     * method to compute euclidean distance between two points
     * used as a distance metric for berlin52 dataset
     * 
     * @param firstPoint  Point object reprsenting first location
     * @param secondPoint Point object representing second location
     */
/*     private double computeDistance(Point firstPoint, Point secondPoint) {
        double squaredDifferenceX = Math.pow((firstPoint.getXCoordinate() - secondPoint.getXCoordinate()), 2);
        double squaredDifferenceY = Math.pow((firstPoint.getYCoordinate() - secondPoint.getYCoordinate()), 2);

        double sqrtDistance = Math.sqrt(squaredDifferenceX + squaredDifferenceY);
        return Math.round(sqrtDistance);
    }
 */
    /**
     * method to compute pseudo-euclidean distance between two points
     * used as a distance metric by att48 dataset
     * 
     * @param firstPoint  Point object reprsenting first location
     * @param secondPoint Point object representing second location
     */
    private double computeDistance(Point firstPoint, Point secondPoint) {
        double dx = firstPoint.getXCoordinate() -
                secondPoint.getXCoordinate();
        double dy = firstPoint.getYCoordinate() -
                secondPoint.getYCoordinate();

        double rij = Math.sqrt((dx * dx + dy * dy) / 10.0);
        double tij = Math.round(rij);

        return (tij < rij) ? (tij + 1) : (tij);
    }

}
