# Traveling Salesman Problem Hybrid Genetic-Algorithm with-2-opt-Heuristic Local Search Simulator

This repository provides an implementation of Traveling Salesman Problem (TSP) hybrid genetic algorithm
with 2-opt heuristic local search which provides different crossover and mutation strategies to be experimented.
Crossover and mutation strategies used are given as follows:

Crossover Strategies Implemented:
- Order-1 (O1) Crossover
- Partially Mapped Crossover
- Cycle Crossover

Mutation Strategies Implemented:
- Swap Mutation
- Insert Mutation
- Scramble Mutation
- Inversion Mutation

TSPSimulator.java file contains main method which creates an instance of TSPGA class, 
instance of TSPGA class provides a method named `runGeneticAlgorithm` which takes three arguments 
`maxIter` that determines number of iteration for genetic algorithm to run, `populationSize` that 
determines the number of chromosomes (individuals) to be used in the solution of TSP, and `performLocalSearch`
that is a boolean flag which runs 2-opt heuristic local search if set to true, runs default genetic algoritm 
if set to false.

CrossoverManager class provides a method named `applyCrossover`, this method's switch case statement and 
random number generator for determining which crossover type to be applied can be adjusted to experiment 
different crossover combinations.


MutationManager class provides a method named `applyMutation`, this method's switch case statement and 
random number generator for determining which mutation type to be applied can be adjusted to experiment 
different mutations.


```bash
|   att48TSP.csv  
|   berlin52TSP.csv  
|  
+---.vscode  
|       launch.json  
|       settings.json  
|  
+---bin  
|   \---tsp  
|       \---genetic  
|           +---algorithm  
|           |       TSPGA.class  
|           |  
|           +---animator  
|           |       TSPAnimator.class  
|           |  
|           +---context  
|           |       CrossoverManager.class  
|           |       GAContextManager.class  
|           |       MutationManager.class  
|           |  
|           +---entitites  
|           |       Chromosome.class  
|           |       Point.class  
|           |  
|           +---fileio  
|           |       FileIO.class  
|           |  
|           \---simulator  
|                   TSPSimulator.class  
|  
\---src  
    \---tsp  
        \---genetic  
            +---algorithm  
            |       TSPGA.java  
            |  
            +---animator  
            |       TSPAnimator.java  
            |  
            +---context  
            |       CrossoverManager.java  
            |       GAContextManager.java  
            |       MutationManager.java  
            |  
            +---entitites  
            |       Chromosome.java  
            |       Point.java  
            |  
            +---fileio  
            |       FileIO.java  
            |  
            \---simulator  
                    TSPSimulator.java  
```
