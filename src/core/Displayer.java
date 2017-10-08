package core;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Displayer extends Application{

    static SandPileGrid displayer;

    static double offsetX, offsetY;
    static Label[] labels;
    static Rectangle[] rectangles;
    public static void main(String[] args) {
        displayer = new SandPileGrid(29,0);
        displayer.setPile(14,14,10000);
        offsetX = (displayer.sidelength / 2) * (-20);
        offsetY = offsetX;
        labels = new Label[displayer.sandpiles.length];
        rectangles = new Rectangle[labels.length];
        launch(args);
    }



    @Override
    public void start(Stage primaryStage){
        Button StartButton = new Button("Start");
        Button StopButton = new Button("Stop");
        Button SwitchView = new Button("Switch View");
        primaryStage.setTitle("Sandpile Displayer");
        BorderPane layout = new BorderPane();

        Group fields = new Group();
        Group colourFields = new Group();
        for (int i = 0; i < labels.length; i++){
            labels[i] = new Label();
            fields.getChildren().add(labels[i]);
            rectangles[i] = new Rectangle(20,20, Color.BLUE);
            colourFields.getChildren().add(rectangles[i]);
        }

        Group center = new Group(fields,colourFields);
        colourFields.setVisible(false);
        layout.setCenter(center);
        layout.setTop(new VBox(10,StartButton, StopButton));
        layout.setBottom(SwitchView);

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
            colourFields.getChildren().get(i).setLayoutX(offsetX);
            colourFields.getChildren().get(i).setLayoutY(offsetY);
        }

        for (int i = 0; i < labels.length; i++){
            labels[i].setText(Integer.toString(displayer.sandpiles[i].getSandpile()));
            rectangles[i].setFill(setColour(Integer.parseInt(labels[i].getText())));
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                displayer.tobble();
                for (int i = 0; i < labels.length; i++){
                    labels[i].setText(Integer.toString(displayer.sandpiles[i].getSandpile()));
                    rectangles[i].setFill(setColour(Integer.parseInt(labels[i].getText())));
                }
            }
        };
        StartButton.setOnAction(event -> timer.start());
        StopButton.setOnAction(event -> timer.stop());
        SwitchView.setOnAction(event -> {
            fields.setVisible(!fields.isVisible());
            colourFields.setVisible(!colourFields.isVisible());
        });
    }

    public Color setColour(int def){
        switch (def){
            case 0:
                return Color.BLACK;
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.RED;
            default:
                return Color.MAGENTA;

        }
    }


}

