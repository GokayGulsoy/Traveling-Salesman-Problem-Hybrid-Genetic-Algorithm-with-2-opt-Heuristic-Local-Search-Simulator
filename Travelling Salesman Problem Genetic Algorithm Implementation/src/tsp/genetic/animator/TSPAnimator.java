package tsp.genetic.animator;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import tsp.genetic.entitites.Point;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * class that runs the animation for constructed   
 * best route for each generation
 */
public class TSPAnimator extends Application {

    // keep data fields as static as JavaFX application's launch
    // method internally creates an instance of TSPAnimator
    private static ArrayList<ArrayList<Integer>> bestRoutes;
    private static ArrayList<Double> bestFitnesses;
    private static ArrayList<Point> coordinates;
    private static final double X_SCALE = 840.0 / 7800.0; // scaling factor specific to dataset for X axis
    private static final double Y_SCALE = 840.0 / 5200.0; // scaling factor specific to dataset for Y axis
    /**
     * For `berlin52TSP.csv`
     * X_SCALE = 840.0 / 1800.0;
     * Y_SCALE = 840.0 / 1200.0;
     * 
     * For `att48TSP.csv`
     * Y_SCALE = 840.0 / 7800.0;
     * Y_SCALE = 840.0 / 5200.0;
     */

    public static void setData(ArrayList<ArrayList<Integer>> routes, ArrayList<Double> fitnesses,
            ArrayList<Point> coords) {
        bestRoutes = routes;
        bestFitnesses = fitnesses;
        coordinates = coords;
    }

    @Override
    public void start(Stage primaryStage) {
        Canvas canvas = new Canvas(840, 840);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // setting Scene for primaryStage and displaying stage
        StackPane canvasHolder = new StackPane(canvas);
        canvasHolder.setStyle("-fx-background-color: #cccccc;");
        BorderPane root = new BorderPane();
        root.setCenter(canvasHolder);
        root.setStyle("-fx-background-color: #cccccc;");

        primaryStage.setScene(new Scene(root, 950, 950));
        primaryStage.show();

        // animating best routes
        final int[] frame = {0};
        Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (frame[0] < bestRoutes.size()) {    
                drawRoute(gc, bestRoutes.get(frame[0]));
                primaryStage.setTitle("Generation: " + (frame[0] + 1) +
                        " Fitness: " + bestFitnesses.get(frame[0]) +
                        " Total Distance: " + (1.0 / bestFitnesses.get(frame[0])));

                frame[0]++;
            }
        }));

        timeLine.setCycleCount(bestRoutes.size());
        timeLine.play();
    }

    /**
     * helper method to draw the best route for current
     * frame
     * 
     * @param gc    GraphicsContext object to draw line between two points
     * @param route ArrayList of labels
     */
    private void drawRoute(GraphicsContext gc, ArrayList<Integer> route) {
        gc.clearRect(0, 0, 840, 840);
        for (int i = 0; i < route.size(); i++) {
            Point from = coordinates.get(route.get(i) - 1);
            Point to = coordinates.get(route.get(((i + 1) % route.size())) - 1);
            // scaling coordinates to fit in canvas
            double x1 = from.getXCoordinate() * X_SCALE;
            double y1 = from.getYCoordinate() * Y_SCALE;
            double x2 = to.getXCoordinate() * X_SCALE;
            double y2 = to.getYCoordinate() * Y_SCALE;

            gc.strokeLine(x1, y1, x2, y2);
            gc.fillOval(x1 - 3, y1 - 3, 6, 6);
            gc.fillText(String.valueOf(route.get(i)), x1 + 8, y1 + 8);
        }
    }

}
