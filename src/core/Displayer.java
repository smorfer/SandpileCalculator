package core;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Displayer extends Application{

    static SandPileGrid displayer;

    static boolean startTimer;
    static double offsetX, offsetY;
    static double scale;
    static int amount;
    static Rectangle[] rectangles;
    public static void main(String[] args) {
        startTimer = false;
        amount = 0;
        displayer = new SandPileGrid(199,0);
        displayer.setPile(displayer.sideLength/2,displayer.sideLength/2,amount);
        scale = 3;
        offsetX = (displayer.sideLength / 2) * (-scale);
        offsetY = offsetX;
        rectangles = new Rectangle[displayer.sandpiles.length];
        launch(args);
    }



    @Override
    public void start(Stage primaryStage){
        Label currentOps = new Label();
        TextField fillAllInput = new TextField();
        Button fillAllValidate = new Button("Fill all");
        TextField rowInput = new TextField();
        TextField colInput = new TextField();
        TextField pileInput = new TextField();
        Button validateInput = new Button("Set Pile");
        VBox pileSet = new VBox(20,fillAllInput,fillAllValidate, colInput,rowInput,pileInput,validateInput);
        Label Progress = new Label();
        Progress.setText("Progressed "+ (amount-displayer.getSandpile(displayer.sideLength/2,displayer.sideLength/2))+ " of "+amount);
        Button StartButton = new Button("Start");
        Button StopButton = new Button("Stop");
        Button StepButton = new Button("Step");
        primaryStage.setTitle("Sandpile Displayer");
        BorderPane layout = new BorderPane();

        Group colourFields = new Group();
        for (int i = 0; i < rectangles.length; i++){
            rectangles[i] = new Rectangle(scale,scale, Color.BLUE);
            colourFields.getChildren().add(rectangles[i]);
        }

        Group center = new Group(colourFields);

        layout.setCenter(center);
        layout.setTop(new HBox(20,StartButton, StepButton, StopButton,Progress, currentOps));

        layout.setRight(pileSet);

        Scene CalculatorScene = new Scene(layout);

        primaryStage.setScene(CalculatorScene);

        primaryStage.setMaximized(true);


        for (int i = 0; i < rectangles.length; i++){
            if(i!=0){
                if (i % displayer.sideLength == 0){
                    offsetY += scale;
                    offsetX -= scale * (displayer.sideLength-1);
                }else {
                    offsetX += scale;
                }
            }

            colourFields.getChildren().get(i).setLayoutX(offsetX);
            colourFields.getChildren().get(i).setLayoutY(offsetY);
            System.out.println("Position Sandpile No. "+i);
        }

        for (int i = 0; i < rectangles.length; i++){
            rectangles[i].setFill(setColour(displayer.sandpiles[i]));
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (startTimer){
                    displayer.tobble();
                }
                Progress.setText("Progressed "+ displayer.getSandpile(displayer.sideLength/2,displayer.sideLength/2)+ " of "+amount);
                currentOps.setText("Current Ops: " + displayer.currentOps);

                for (int i = 0; i < rectangles.length; i++){
                    rectangles[i].setFill(setColour(displayer.sandpiles[i]));
                }
                for (int i = 0; i < rectangles.length; i++){
                    if(i!=0){
                        if (i % displayer.sideLength == 0){
                            offsetY += scale;
                            offsetX -= scale * (displayer.sideLength-1);
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
        timer.start();
        primaryStage.show();
        StartButton.setOnAction(event -> startTimer= true);
        StepButton.setOnAction(event -> displayer.tobble());
        StopButton.setOnAction(event -> startTimer = false);
        validateInput.setOnAction(event -> {
            displayer.setPile(isInt(colInput),isInt(rowInput),isInt(pileInput));
        });
        fillAllValidate.setOnAction(event -> displayer.setAllPiles(isInt(fillAllInput)));

    }

    private Color setColour(int def){
        switch (def){
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.RED;
            default:
                return Color.MAGENTA;

        }
    }

    public int isInt(TextField input){
        try{
            return Integer.parseInt(input.getText());
        }catch (NumberFormatException e){
            System.out.println(e);
            return 0;
        }
    }


}

