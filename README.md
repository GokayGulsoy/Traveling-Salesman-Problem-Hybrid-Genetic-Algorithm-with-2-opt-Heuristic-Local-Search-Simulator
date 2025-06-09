# Traveling Salesman Problem Hybrid Genetic-Algorithm with-2-opt-Heuristic Local Search Simulator

This repository provides an implementation of Traveling Salesman Problem (TSP) hybrid genetic algorithm
with 2-opt heuristic local search which provides different crossover and mutation strategies to be experimented.

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
