package core;

import java.util.ArrayList;

/**
 * The handler class of the Sandpilegrid.
 * Is the part of the Program, the Main class interacts with.
 * Combines the little classes into the abstract definition of a Sandpilegrid.
 */
public class SandPileGrid {

    int[] sandpiles;
    int currentOps;
    int sideLength;

    public SandPileGrid(int sideLength, int fill){
        this.sideLength = sideLength;
        sandpiles = new int[(int)Math.pow(sideLength,2)];
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
    public int getSandpile(int row, int col){
        try {
            return sandpiles[col+(sideLength*row)];
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Specified pile location out of grid.");
            return 0;
        }
    }
    public void addtoRelative(int[] sandpiles, int index){
        sandpiles[index] += 1;
        //list.set(index,list.get(index)+1);
    }
}