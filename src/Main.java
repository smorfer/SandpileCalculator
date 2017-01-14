/**
 * Executing class of the program.
 * Simulates the behaviour of Sandpilegrids.
 * The Sandpilegrid is described in this video of Numberphile: Sandpiles - Numberphile
 * Link: https://www.youtube.com/watch?v=1MtEUErz7Gg
 */
public class Main {
    public static void main(String[] args) {
        SandPileGrid grid = new SandPileGrid(2,1,3,1,2,1,1,2,1);
        SandPileGrid addGrind = new SandPileGrid(3,2,1,3,0,0,0,1,1);
        grid.display();
        addGrind.display();
        grid.addPile(addGrind);
        grid.tobble();
    }
}
