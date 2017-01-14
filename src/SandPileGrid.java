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

    public void tobble(){
        for(Sandpile s : sandpiles){
            if(s.checkOverflow()){
                s.collapse();
                display();
                tobble();
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                }
                break;
            }
        }
    }

    public void addPile(SandPileGrid sandPileGrid){
        for(int i = 0; i < 9; i++){
            this.sandpiles[i].setSandpile(sandpiles[i].getSandpile() + sandPileGrid.sandpiles[i].getSandpile());
        }
        display();
    }

    public void display(){
        System.out.println(uplft.getSandpile() + "    " + upmid.getSandpile() + "    " + uprght.getSandpile() + "\n");
        System.out.println(midlft.getSandpile() + "    " + midmid.getSandpile() + "    " + midrght.getSandpile() + "\n");
        System.out.println(dwnlft.getSandpile() + "    " + dwnmid.getSandpile() + "    " + dwnrght.getSandpile() + "\n \n");
    }
}