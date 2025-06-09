package tsp.genetic.entitites;

import java.util.ArrayList;

/**
 * Chromosome class represents an individual
 * used in Travelling salesman problem genetic
 * algorithm implementation. Each instance of Chromosome class
 * represents a different permutation of loctions
 * on the route
 * @auhtor Gökay Gülsoy 
 * @since 29/05/2025
 */
public class Chromosome {

    private ArrayList<Integer> locOrder; // permutation of locations
    private double fitness; // fitness value for specific locOrder

    public Chromosome(ArrayList<Integer> locOrder) {
        this.locOrder = locOrder;
        fitness = 0.0;
    }

    public ArrayList<Integer> getLocOrder() {
        return this.locOrder;
    }

    public double getFitness() {
        return this.fitness;
    }

    public void setLocOrder(ArrayList<Integer> locOrder) {
        this.locOrder = locOrder;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    /**
     * method to compute fitness for chromosome, higher fitness
     * values mean better solution. Fitness values are inversely
     * proportional to total distance between point pairs in route
     * 
     * @param distanceMatrix two dimensional symmetric square matrix
     *                       in which each row i represents a location and j is
     *                       representing other location such that i is adjacent to
     *                       i
     */
    public void computeFitness(double[][] distanceMatrix) {
        double totalDistance = 0.0;

        for (int i = 0; i < locOrder.size() - 1; i++) {
            int loc1Label = locOrder.get(i);
            int loc2Label = locOrder.get(i + 1);    

            // adding distance between two adjacent locations to total distance
            // reversing order to access if first location label is greater than second
            // location label because
            // all etnries where i > j is 0 as the distanceMatrix is symmetric same value
            // can be accessed at [j][i] rather than [i][j]
            totalDistance += (loc1Label < loc2Label) ? (distanceMatrix[loc1Label - 1][loc2Label - 1])
                    : (distanceMatrix[loc2Label - 1][loc1Label - 1]);
        }

        // closing loop for TSP via computing distance from
        // last location to first location on route
        int lastLocLabel = locOrder.getLast();
        int startLocLabel = locOrder.get(0);

        totalDistance += (lastLocLabel < startLocLabel) ? (distanceMatrix[lastLocLabel - 1][startLocLabel - 1])
                : (distanceMatrix[startLocLabel - 1][lastLocLabel - 1]);
        fitness = (totalDistance != 0) ? (1 / totalDistance) : Double.POSITIVE_INFINITY;
        setFitness(fitness);
    }

    @Override
    public String toString() {
        return "Chromosome(" + this.locOrder + ", Fitness: " + this.fitness + ")";
    }

}
