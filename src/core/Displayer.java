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

public class Displayer extends Application{

    static SandPileGrid displayer;

    static double offsetX, offsetY;
    static double scale;
    static int amount;
    static Rectangle[] rectangles;
    public static void main(String[] args) {
        amount = 100000;
        displayer = new SandPileGrid(199,0);
        displayer.setPile(displayer.sidelength/2,displayer.sidelength/2,amount);
        scale = 3;
        offsetX = (displayer.sidelength / 2) * (-scale);
        offsetY = offsetX;
        rectangles = new Rectangle[displayer.sandpiles.length];
        launch(args);
    }



    @Override
    public void start(Stage primaryStage){
        Label Progress = new Label();
        Progress.setText("Progressed "+ (amount-displayer.getSandpile(displayer.sidelength/2,displayer.sidelength/2))+ " of "+amount);
        Button StartButton = new Button("Start");
        Button StopButton = new Button("Stop");
        primaryStage.setTitle("Sandpile Displayer");
        BorderPane layout = new BorderPane();

        Group colourFields = new Group();
        for (int i = 0; i < rectangles.length; i++){
            rectangles[i] = new Rectangle(scale,scale, Color.BLUE);
            colourFields.getChildren().add(rectangles[i]);
        }

        Group center = new Group(colourFields);

        layout.setCenter(center);
        layout.setTop(new VBox(10,StartButton, StopButton,Progress));

        Scene CalculatorScene = new Scene(layout);

        primaryStage.setScene(CalculatorScene);

        primaryStage.setMaximized(true);


        for (int i = 0; i < rectangles.length; i++){
            if(i!=0){
                if (i % displayer.sidelength == 0){
                    offsetY += scale;
                    offsetX -= scale * (displayer.sidelength-1);
                }else {
                    offsetX += scale;
                }
            }

            colourFields.getChildren().get(i).setLayoutX(offsetX);
            colourFields.getChildren().get(i).setLayoutY(offsetY);
            System.out.println("Position Sandpile No. "+i);
        }

        for (int i = 0; i < rectangles.length; i++){
            rectangles[i].setFill(setColour(displayer.sandpiles[i].getSandpile()));
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Progress.setText("Progressed "+ displayer.getSandpile(displayer.sidelength/2,displayer.sidelength/2)+ " of "+amount);
                displayer.tobble();
                for (int i = 0; i < rectangles.length; i++){
                    rectangles[i].setFill(setColour(displayer.sandpiles[i].getSandpile()));
                }
                for (int i = 0; i < rectangles.length; i++){
                    if(i!=0){
                        if (i % displayer.sidelength == 0){
                            offsetY += scale;
                            offsetX -= scale * (displayer.sidelength-1);
                        }else {
                            offsetX += scale;
                        }
                    }


                    colourFields.getChildren().get(i).setLayoutX(offsetX);
                    colourFields.getChildren().get(i).setLayoutY(offsetY);
                    colourFields.getChildren().get(i).resize(scale,scale);

                }
            }
        };
        primaryStage.show();
        StartButton.setOnAction(event -> timer.start());
        StopButton.setOnAction(event -> timer.stop());

    }

    private Color setColour(int def){
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

