import java.util.Scanner;

/**
 * The handler class of the Sandpilegrid.
 * Is the part of the Program, the Main class interacts with.
 * Combines the little classes into the abstract definition of a Sandpilegrid.
 */
public class SandPileGrid {

    Sandpile uplft,upmid,uprght;
    Sandpile midlft,midmid,midrght;
    Sandpile dwnlft,dwnmid,dwnrght;

    Sandpile[] sandpiles;
    int sidelength;

    @Deprecated
    public SandPileGrid(int ul,int um,int ur,int ml,int mm,int mr, int dl, int dm, int dr) {
        uplft = new Sandpile(ul);
        upmid = new Sandpile(um);
        uprght = new Sandpile(ur);
        midlft = new Sandpile(ml);
        midmid = new Sandpile(mm);
        midrght = new Sandpile(mr);
        dwnlft = new Sandpile(dl);
        dwnmid = new Sandpile(dm);
        dwnrght = new Sandpile(dr);

        uplft.setRelations(new GridBoundary(),midlft,upmid,new GridBoundary());
        upmid.setRelations(new GridBoundary(),midmid,uprght,uplft);
        uprght.setRelations(new GridBoundary(),midrght,new GridBoundary(),upmid);
        midlft.setRelations(uplft,dwnlft,midmid,new GridBoundary());
        midmid.setRelations(upmid,dwnmid,midrght,midlft);
        midrght.setRelations(uprght,dwnrght,new GridBoundary(),midmid);
        dwnlft.setRelations(midlft,new GridBoundary(),dwnmid,new GridBoundary());
        dwnmid.setRelations(midmid,new GridBoundary(),dwnrght,dwnlft);
        dwnrght.setRelations(midrght,new GridBoundary(),new GridBoundary(),dwnmid);

        sandpiles = new Sandpile[]{uplft,upmid,uprght,midlft,midmid,midrght,dwnlft,dwnmid,dwnrght};
    }

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
        }

    }


    public void tobble(){
        for(Sandpile s : sandpiles){
            if(s.checkOverflow()){
                s.collapse();
                display();
                tobble();
                break;
            }
        }
    }

    public void addPile(SandPileGrid sandPileGrid){
        for(int i = 0; i < sandpiles.length; i++){
            this.sandpiles[i].setSandpile(sandpiles[i].getSandpile() + sandPileGrid.sandpiles[i].getSandpile());
        }
        display();
    }

    public void setPiles(){
        Scanner scannerIn = new Scanner(System.in);
        for (int i = 0; i < sandpiles.length;i++){
            if ((i+1) % sidelength == 0){
                sandpiles[i].setSandpile(scannerIn.nextInt());
                System.out.println();
            }else {
                sandpiles[i].setSandpile(scannerIn.nextInt());
            }
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
}