package core;

/**
 * GridBoundary is the outer layer of the sandpile grid, thus voiding sand it's recieving.
 */
public class GridBoundary extends GridObject {
    @Override
    public void collapse() {}

    @Override
    public boolean checkOverflow() {
        return false;
    }

    @Override
    public void receive() {}
}
