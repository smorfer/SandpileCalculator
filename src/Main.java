/**
 * Executing class of the program.
 * Simulates the behaviour of Sandpilegrids.
 * The Sandpilegrid is described in this video of Numberphile: Sandpiles - Numberphile
 * Link: https://www.youtube.com/watch?v=1MtEUErz7Gg
 */
public class Main {
    public static void main(String[] args) {
        SandPileGrid sandPileGrid = new SandPileGrid(3,0);
        sandPileGrid.setPiles();
        sandPileGrid.addPile(new SandPileGrid(3,3));
        sandPileGrid.tobble();
    }
}
