package core;

/**
 * Executing class of the program.
 * Simulates the behaviour of Sandpilegrids.
 * The Sandpilegrid is described in this video of Numberphile: Sandpiles - Numberphile
 * Link: https://www.youtube.com/watch?v=1MtEUErz7Gg
 */
public class Main {
    public static void main(String[] args) {
        SandPileGrid sandPileGrid = new SandPileGrid(99,0);
        sandPileGrid.setPile(49,100,100000);
        //sandPileGrid.addPile(new SandPileGrid(3,3));
        sandPileGrid.tobble();
    }
}
