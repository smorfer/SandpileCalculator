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


    static Thread t;
    static boolean isRendering;
    static boolean startTimer;

    static double offsetX, offsetY;
    static double scale;
    static int amount;


    static Rectangle[] rectangles;
    public static void main(String[] args) {

        startTimer = false;
        isRendering = true;
        amount = 0;
        displayer = new SandPileGrid(9,0);
        scale = (int) (600.0/displayer.sideLength);
        offsetX = (displayer.sideLength / 2) * (-scale);
        offsetY = offsetX;
        rectangles = new Rectangle[displayer.sandpiles.length];
        launch(args);
    }



    @Override
    public void start(Stage primaryStage){
        t = new Thread(displayer);
        Label currentOps = new Label();
        TextField fillAllInput = new TextField();
        Button toggleRendering = new Button("Toggle Rendering");
        Button fillAllValidate = new Button("Fill all");
        TextField rowInput = new TextField();
        TextField colInput = new TextField();
        TextField pileInput = new TextField();
        Button validateInput = new Button("Set Pile");
        Button IdentCalcButton = new Button("Calculate Identity");
        VBox pileSet = new VBox(20,fillAllInput,fillAllValidate, colInput,rowInput,pileInput,validateInput, IdentCalcButton);
        Label Progress = new Label();
        Progress.setText("Needed Runs: " + displayer.identityRuns);
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
        layout.setTop(new HBox(20,StartButton, StepButton, StopButton, toggleRendering,Progress, currentOps));

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
                currentOps.setText("Current Ops: " + displayer.currentOps);
                Progress.setText("Needed Runs: " + displayer.identityRuns);

                if(!displayer.isRenderingRequested ) {
                    for (int i = 0; i < rectangles.length; i++){
                        rectangles[i].setFill(setColour(displayer.displayBuffer[i]));
                    }

                } else if (!t.isAlive()){
                    displayer.isRenderingRequested = displayer.updateDisplayBuffer(true);
                }

            }
        };
        timer.start();
        primaryStage.show();
        StartButton.setOnAction(event -> {
            t = new Thread(displayer);
            t.start();
        });
        StepButton.setOnAction(event -> displayer.tobble());
        StopButton.setOnAction(event -> t.stop());
        toggleRendering.setOnAction(event -> displayer.isRenderingRequested = true);
        validateInput.setOnAction(event -> displayer.setPile(isInt(colInput),isInt(rowInput),isInt(pileInput)));
        IdentCalcButton.setOnAction(event -> {
            displayer.getInput(isInt(colInput),isInt(rowInput),isInt(pileInput));
            displayer.Identity = 0;
            displayer.compareGrid = displayer.clone();
            displayer.identityRuns = 0;
            displayer.startIdentCalc = !displayer.startIdentCalc;
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
    public void fullTobbling(){
        do {
            displayer.tobble();
        }while (displayer.currentOps != 0);
    }



}

