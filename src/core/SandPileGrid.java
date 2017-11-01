package core;

import java.util.ArrayList;

/**
 * The handler class of the Sandpilegrid.
 * Is the part of the Program, the Main class interacts with.
 * Combines the little classes into the abstract definition of a Sandpilegrid.
 */
public class SandPileGrid implements Runnable{

    @Override
    public void run() {

        do {
            if(startIdentCalc){
                if (startIdentCalc && currentOps == 0){

                    Identity += calculateIdentity(inputPackage.getColumnInput(),inputPackage.getRowInput(),inputPackage.getAmountInput());
                    identityRuns++;
                }
                tobble();

                try {
                    if (equals(compareGrid) && currentOps == 0 && startIdentCalc){
                        startIdentCalc = !startIdentCalc;
                        setAllPiles(0);
                        setPile(inputPackage.getColumnInput(),inputPackage.getRowInput(),Identity+getSandpile(inputPackage.getColumnInput(),inputPackage.getRowInput()));
                    }
                } catch (Exception e) {
                }

            } else {
                tobble();
            }

        }while (currentOps != 0 || startIdentCalc);
        updateDisplayBuffer(true);
    }

    boolean startIdentCalc;
    boolean isRenderingRequested;
    int[] sandpiles;
    int[] displayBuffer;
    int Identity;
    int identityRuns;
    int currentOps;
    int sideLength;

    UserInputPackage inputPackage;
    SandPileGrid compareGrid;

    public SandPileGrid(int sideLength, int fill){
        identityRuns = 0;
        startIdentCalc = false;
        Identity = 0;
        this.sideLength = sideLength;
        sandpiles = new int[(int)Math.pow(sideLength,2)];
        displayBuffer = new int[(int)Math.pow(sideLength,2)];
        for (int i = 0;i < sandpiles.length; i++){
            sandpiles[i] = fill;
        }

    }

    public void tobble(){
        tobblePrioritized(checkGridOverflow());
    }

    public ArrayList<Integer> checkGridOverflow(){
        ArrayList<Integer> overflowing = new ArrayList<>();
        for(int i = 0; i < sandpiles.length; i++){
            if (sandpiles[i] > 3){
                overflowing.add(i);
            }
        }
        currentOps=overflowing.size();
        return overflowing;
    }

    private void tobblePrioritized(ArrayList<Integer> overflowing){
        if(!overflowing.isEmpty()){
            for (int i : overflowing){
                if (i == 0) {
                    //sandpiles[i].setRelations(new GridBoundary(),sandpiles[sideLength],sandpiles[i+1],new GridBoundary());
                    addtoRelative(sandpiles,sideLength);
                    addtoRelative(sandpiles,i+1);
                }
                else if (i < sideLength-1){
                    //sandpiles[i].setRelations(new GridBoundary(),sandpiles[i+sideLength],sandpiles[i+1],sandpiles[i-1]);
                    addtoRelative(sandpiles,i+sideLength);
                    addtoRelative(sandpiles,i+1);
                    addtoRelative(sandpiles, i-1);

                }
                else if ((i+1) % sideLength == 0) {
                    if ((i+1)==Math.pow(sideLength,2)){
                        addtoRelative(sandpiles,i-sideLength);
                        addtoRelative(sandpiles,i-1);
                    }//sandpiles[i].setRelations(sandpiles[i - sideLength], new GridBoundary(), new GridBoundary(), sandpiles[i - 1]);
                    else if (i+1 == sideLength){
                        addtoRelative(sandpiles,i+sideLength);
                        addtoRelative(sandpiles,i-1);
                        //sandpiles[i].setRelations(new GridBoundary(),sandpiles[i+sideLength],new GridBoundary(),sandpiles[i-1]);
                    }
                    else{
                        addtoRelative(sandpiles,i-sideLength);
                        addtoRelative(sandpiles,i+sideLength);
                        addtoRelative(sandpiles,i-1);
                        //sandpiles[i].setRelations(sandpiles[i-sideLength],sandpiles[i+sideLength],new GridBoundary(),sandpiles[i-1]);
                    }
                }
                else if (i % sideLength == 0){
                    if (i + sideLength == Math.pow(sideLength,2)){
                        addtoRelative(sandpiles,i-sideLength);
                        addtoRelative(sandpiles,i+1);
                        //sandpiles[i].setRelations(sandpiles[i-sideLength],new GridBoundary(),sandpiles[i+1],new GridBoundary());
                    }
                    else{
                        addtoRelative(sandpiles,i-sideLength);
                        addtoRelative(sandpiles,i+sideLength);
                        addtoRelative(sandpiles,i+1);
                        //sandpiles[i].setRelations(sandpiles[i-sideLength],sandpiles[i+sideLength],sandpiles[i+1],new GridBoundary());
                    }
                }
                else if (i < Math.pow(sideLength,2)&& i > Math.pow(sideLength,2)-sideLength) {
                    addtoRelative(sandpiles,i-sideLength);
                    addtoRelative(sandpiles,i+1);
                    addtoRelative(sandpiles,i-1);
                    //sandpiles[i].setRelations(sandpiles[i-sideLength],new GridBoundary(),sandpiles[i+1],sandpiles[i-1]);
                }
                else {
                    addtoRelative(sandpiles,i-sideLength);
                    addtoRelative(sandpiles,i+sideLength);
                    addtoRelative(sandpiles,i+1);
                    addtoRelative(sandpiles,i-1);
                    //sandpiles[i].setRelations(sandpiles[i-sideLength],sandpiles[i+sideLength],sandpiles[i+1],sandpiles[i-1]);
                }

                sandpiles[i] -=4;
            }
            isRenderingRequested = updateDisplayBuffer(isRenderingRequested);

        }

    }

    public void addPile(SandPileGrid sandPileGrid){
        for(int i = 0; i < sandpiles.length; i++){
            this.sandpiles[i] += sandPileGrid.sandpiles[i];
        }
    }

    public void setAllPiles(int in){
        for (int i = 0; i < sandpiles.length;i++){
            sandpiles[i] = in;
        }
    }

    public void setPile(int col, int row, int amount){
        try {
            sandpiles[col+(sideLength*row)] = amount;
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Specified pile location out of grid.");
        }
    }

    public void display(){
        for (int i = 0; i < sandpiles.length;i++){
            if ((i+1) % sideLength == 0){
                System.out.println(sandpiles[i] + "\n");
            }else {
                System.out.print(sandpiles[i] + "\t");
            }
        }
        System.out.println();
    }
    public int getSandpile(int col, int row){
        try {
            return sandpiles[col+(sideLength*row)];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Specified pile location out of grid.");
            return 0;
        }
    }
    public void addtoRelative(int[] sandpiles, int index){
        sandpiles[index] += 1;
    }

    public int calculateIdentity(int col, int row, int amount){
        setPile(col, row, amount);
        return amount - 3;
    }

    public void startIdentityCalculation(int col, int row, int amount){
        getInput(col,row,amount);
        Identity = 0;
        compareGrid = clone();
        identityRuns = 0;
        startIdentCalc = !startIdentCalc;
    }

    public boolean updateDisplayBuffer(boolean updateRequested){
        if(updateRequested){
            for (int i = 0; i < sandpiles.length;i++){
                displayBuffer[i] = sandpiles[i];
            }
        }
        return false;
    }
    public void getInput(int colInput, int rowInput, int amountInput){
        inputPackage = new UserInputPackage(colInput,rowInput,amountInput);
    }


    @Override
    public boolean equals(Object obj) {
        for (int i = 0; i < this.sandpiles.length; i++){
            if(this.sandpiles[i] != ((SandPileGrid)obj).sandpiles[i]){
                return false;
            }

        }
        return true;
    }

    @Override
    protected SandPileGrid clone() {
        SandPileGrid clonedGrid = new SandPileGrid(sideLength,0);
        for (int i = 0;i < this.sandpiles.length; i++){
            clonedGrid.sandpiles[i] = this.sandpiles[i];
        }
        return clonedGrid;
    }
}