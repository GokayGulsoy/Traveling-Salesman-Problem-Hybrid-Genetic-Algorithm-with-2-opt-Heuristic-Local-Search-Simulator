# Traveling Salesman Problem Hybrid Genetic Algorithm with-2-opt-Heuristic Local Search Simulator

## Project Structure 
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

TSPSimulator class contains main method which creates an instance of TSPGA class, 
instance of TSPGA class provides a method named `runGeneticAlgorithm` which takes three arguments 
`maxIter` that determines number of iterations for genetic algorithm to run, `populationSize` that 
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
\---Travelling Salesman Problem Genetic Algorithm Implementation
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

## Dependencies
In order to run simulator with shortest path construction simulation only dependency required is any JavaFX
version compatible with Java JRE version installed in your system. When the simulator was developed, it was 
tested with `java version 21.0.2` and `javafx version 21.0.7` in Visual Studion Code (VSCode). 

## Running Simulator
Simulator can be executed in two ways, one is running directly from terminal (cmd in windows) and other is 
running within Visual Studio Code.

### Running from Terminal (Command Line in Windows)
run all of the following commands from project root directory which is: "Travelling Salesman Problem Genetic Algorithm Implementation"
beware that --module-path flag takes a path to lib folder of JavaFX installation, in your system replace it with the corresponding path 
according to your JavaFX installation directory in order for shortest path construction simulation to work correctly. 

 compile the application from terminal or cmd from with the following command:

 ```
    javac --module-path <path_to_javafx_lib_folder> --add-modules javafx.controls,javafx.fxml -d bin -cp bin .\src\tsp\genetic\simulator\TSPSimulator.java
 ```

run the application from terminal or cmd with the following command:

 ```
     java --module-path <path_to_javafx_lib_folder> --add-modules javafx.controls,javafx.fxml -cp "bin;." tsp.genetic.simulator.TSPSimulator
 ```

### Running within Visual Studion Code
In order to run simulator within Visual Studio Code create launch.json file which provides launch configurations.
Example launch.json is provided as follows: 

```bash
    {
    "version": "0.2.0",
    "configurations": [

        {
            "type": "java",
            "name": "Current File",
            "request": "launch",
            "mainClass": "${file}"
        },
        {
            "type": "java",
            "name": "TSPSimulator",
            "request": "launch",
            "mainClass": "tsp.genetic.simulator.TSPSimulator",
            "projectName": "Travelling Salesman Problem Genetic Algorithm Implementation",
            "vmArgs": "--module-path <path_to_javafx_lib_folder> --add-modules javafx.controls,javafx.fxml"
        }
    ]
}
```

Then add JavaFX jar libraries from explorer tab referenced libraries section clicking `+` icon.
After creating launch.json and adding required JavaFX jar libraries to Java project simulator can
be executed from top right symbol named as `Run Java`
