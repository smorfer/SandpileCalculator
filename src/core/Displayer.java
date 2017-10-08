package core;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Displayer extends Application{

    static SandPileGrid displayer;

    static double offsetX, offsetY;
    static Label[] labels;
    public static void main(String[] args) {
        displayer = new SandPileGrid(29,0);
        displayer.setPile(14,14,1000000);
        offsetX = (displayer.sidelength / 2) * (-20);
        offsetY = offsetX;
        labels = new Label[displayer.sandpiles.length];
        launch(args);
    }



    @Override
    public void start(Stage primaryStage){
        Button StartTobble = new Button("Start");
        primaryStage.setTitle("Sandpile Displayer");
        BorderPane layout = new BorderPane();

        Group fields = new Group();
        for (int i = 0; i < labels.length; i++){
            labels[i] = new Label();
            fields.getChildren().add(labels[i]);
        }

        layout.setCenter(fields);
        layout.setTop(StartTobble);

        Scene CalculatorScene = new Scene(layout);

        primaryStage.setScene(CalculatorScene);

        primaryStage.setMaximized(true);
        primaryStage.show();

        for (int i = 0; i < labels.length; i++){
            if(i!=0){
                if (i % displayer.sidelength == 0){
                    offsetY += 20;
                    offsetX -= 20 * (displayer.sidelength-1);
                }else {
                    offsetX += 20;
                }
            }

            fields.getChildren().get(i).setLayoutY(offsetY);
            fields.getChildren().get(i).setLayoutX(offsetX);
        }

        for (int i = 0; i < labels.length; i++){
            labels[i].setText(Integer.toString(displayer.sandpiles[i].getSandpile()));
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                displayer.tobble();
                for (int i = 0; i < labels.length; i++){
                    labels[i].setText(Integer.toString(displayer.sandpiles[i].getSandpile()));

                }
            }
        };
        StartTobble.setOnAction(event -> timer.start());
    }


}

