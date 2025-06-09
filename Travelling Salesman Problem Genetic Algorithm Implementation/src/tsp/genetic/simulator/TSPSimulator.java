package tsp.genetic.simulator;

import javafx.application.Application;
import tsp.genetic.algorithm.TSPGA;
import tsp.genetic.animator.TSPAnimator;
import tsp.genetic.context.CrossoverManager;
import tsp.genetic.context.GAContextManager;
import tsp.genetic.context.MutationManager;

// Student Number: 323078045
// Student Name: Gökay Gülsoy

/**
 * TSPSimulator class is the simulator class that
 * contains main method to run genetic algorithm
 * for Travelling Salesman problem with certain
 * number of maximum iterations, population size,
 * and parameter to either enable or disable 2-opt
 * local search
 * 
 * @auhtor Gökay Gülsoy
 * @since 29/05/2025
 */

// NOTE: run all of the following commands from project root directory which is:
// "Travelling Salesman Problem Genetic Algorithm Implementation"
// beware that --module-path flag takes a path to lib folder of JavaFX
// installation,
// in your system replace it with the corresponding path according to your
// JavaFX installation directory in order for shortest path construction
// simulation to work correctly
// NOTE: if you just want to get results via running algorithm without
// simulation, comment out out the section in between
// which says "comment out for running without animation simulation"

// compile the application from terminal or cmd from with the following command:
// javac --module-path
// "C:\openjfx-21.0.7_windows-x64_bin-sdk\javafx-sdk-21.0.7\lib" --add-modules
// javafx.controls,javafx.fxml -d bin -cp bin
// .\src\tsp\genetic\simulator\TSPSimulator.java

// run the application from terminal or cmd with the following command:
// java --module-path
// "C:\openjfx-21.0.7_windows-x64_bin-sdk\javafx-sdk-21.0.7\lib" --add-modules
// javafx.controls,javafx.fxml -cp "bin;." tsp.genetic.simulator.TSPSimulator
public class TSPSimulator {
    public static void main(String[] args) { // Plot fitnesses and iterations

        /*
         * double averageTotalDistance = 0.0;
         * for (int i = 0; i < 100; i++) {
         * // creating instances of ContextManager, CrossoverManager,
         * // and MutationManager
         * GAContextManager contextManager = new GAContextManager();
         * CrossoverManager crossoverManager = new CrossoverManager(0.9);
         * MutationManager mutationManager = new MutationManager(0.3);
         * 
         * // creating an instance of TSPGA to run genetic algorithm
         * TSPGA tspGeneticAlgorithm = new TSPGA(contextManager, crossoverManager,
         * mutationManager);
         * tspGeneticAlgorithm.runGeneticAlgorithm(50,150, true);
         * double fitnessInCurrentRun = contextManager.getFitnesses().getLast();
         * 
         * averageTotalDistance += (1 / fitnessInCurrentRun);
         * }
         * 
         * averageTotalDistance = averageTotalDistance / 100;
         * System.out.
         * print("Average minimum distance over 100 runs of genetic algorithm: " +
         * averageTotalDistance);
         */
        
        // creating instances of ContextManager, CrossoverManager,
        // and MutationManager
        GAContextManager contextManager = new GAContextManager();
        CrossoverManager crossoverManager = new CrossoverManager(0.9);
        MutationManager mutationManager = new MutationManager(0.3);

        // creating an instance of TSPGA to run genetic algorithm
        TSPGA tspGeneticAlgorithm = new TSPGA(contextManager, crossoverManager, mutationManager);
        tspGeneticAlgorithm.runGeneticAlgorithm(50, 150, true);

        TSPAnimator.setData(contextManager.getBestRoutes(), contextManager.getFitnesses(),
                contextManager.getCoordinates());
        // launching JavaFX application to simulate best
        Application.launch(TSPAnimator.class, args);

    }
}
