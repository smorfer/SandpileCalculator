package core;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The handler class of the Sandpilegrid.
 * Is the part of the Program, the Main class interacts with.
 * Combines the little classes into the abstract definition of a Sandpilegrid.
 */
public class SandPileGrid {

    Sandpile[] sandpiles;
    int sidelength;

    public SandPileGrid(int sideLength, int fill){
        this.sidelength = sideLength;
        sandpiles = new Sandpile[(int)Math.pow(sideLength,2)];
        for (int i = 0;i < sandpiles.length; i++){
            sandpiles[i] = new Sandpile(fill);
        }
        for (int i = 0;i < sandpiles.length; i++){
            if (i == 0) {
                sandpiles[i].setRelations(new GridBoundary(),sandpiles[sideLength],sandpiles[i+1],new GridBoundary());
            }
            else if (i < sideLength-1){
                sandpiles[i].setRelations(new GridBoundary(),sandpiles[i+sideLength],sandpiles[i+1],sandpiles[i-1]);
            }
            else if ((i+1) % sideLength == 0) {
                if ((i+1)==Math.pow(sideLength,2)) sandpiles[i].setRelations(sandpiles[i - sideLength], new GridBoundary(), new GridBoundary(), sandpiles[i - 1]);
                else if (i+1 == sideLength) sandpiles[i].setRelations(new GridBoundary(),sandpiles[i+sideLength],new GridBoundary(),sandpiles[i-1]);
                else sandpiles[i].setRelations(sandpiles[i-sideLength],sandpiles[i+sideLength],new GridBoundary(),sandpiles[i-1]);
            }
            else if (i % sideLength == 0){
                if (i + sideLength == Math.pow(sideLength,2)) sandpiles[i].setRelations(sandpiles[i-sideLength],new GridBoundary(),sandpiles[i+1],new GridBoundary());
                else sandpiles[i].setRelations(sandpiles[i-sideLength],sandpiles[i+sideLength],sandpiles[i+1],new GridBoundary());
            }
            else if (i < Math.pow(sideLength,2)&& i > Math.pow(sideLength,2)-sideLength) {
                sandpiles[i].setRelations(sandpiles[i-sideLength],new GridBoundary(),sandpiles[i+1],sandpiles[i-1]);
            }
            else {
                sandpiles[i].setRelations(sandpiles[i-sideLength],sandpiles[i+sideLength],sandpiles[i+1],sandpiles[i-1]);
            }
            System.out.println("Linking sandpile No. "+i);
        }

    }

    public void tobble(){
        tobblePrioritized(checkGridOverflow());
    }

    public ArrayList<Sandpile> checkGridOverflow(){
        ArrayList<Sandpile> overflowing = new ArrayList<>();
        for(Sandpile s : sandpiles){
            if (s.checkOverflow()){
                overflowing.add(s);
            }
        }
        return overflowing;
    }

    private void tobblePrioritized(ArrayList<Sandpile> overflowing){
        if(!overflowing.isEmpty()){
            for (Sandpile s : overflowing){
                s.collapse();
            }

        }

    }

    public void addPile(SandPileGrid sandPileGrid){
        for(int i = 0; i < sandpiles.length; i++){
            this.sandpiles[i].setSandpile(sandpiles[i].getSandpile() + sandPileGrid.sandpiles[i].getSandpile());
        }
        display();
    }

    public void setAllPiles(){
        Scanner scannerIn = new Scanner(System.in);
        for (int i = 0; i < sandpiles.length;i++){
            if ((i+1) % sidelength == 0){
                sandpiles[i].setSandpile(scannerIn.nextInt());
                System.out.println();
            }else {
                sandpiles[i].setSandpile(scannerIn.nextInt());
            }
        }
        scannerIn.close();
    }

    public void setPile(int col, int row, int amount){
        try {
            sandpiles[col+(sidelength*row)].setSandpile(amount);
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Specified pile location out of grid.");
        }
    }

    public void display(){
        for (int i = 0; i < sandpiles.length;i++){
            if ((i+1) % sidelength == 0){
                System.out.println(sandpiles[i].getSandpile() + "\n");
            }else {
                System.out.print(sandpiles[i].getSandpile() + "\t");
            }
        }
        System.out.println();
    }
    public int getSandpile(int row, int col){
        try {
            return sandpiles[col+(sidelength*row)].getSandpile();
        }catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Specified pile location out of grid.");
            return 0;
        }
    }
}