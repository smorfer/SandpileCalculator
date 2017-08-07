package core;

/**
 * Represents the inner layer of the sandpile grid and connects to his four neighbours,
 *
 */
public class Sandpile extends  GridObject{
    private int sandpile;
    private GridObject UpPile;
    private GridObject DownPile;
    private GridObject RightPile;
    private GridObject LeftPile;

    public Sandpile(int sandpile) {
        this.sandpile = sandpile;
    }


    public Sandpile(int sandpile, GridObject upPile, GridObject downPile, GridObject rightPile, GridObject leftPile) {
        this.sandpile = sandpile;
        UpPile = upPile;
        DownPile = downPile;
        RightPile = rightPile;
        LeftPile = leftPile;
    }

    @Override
    public void collapse() {
        UpPile.receive();
        DownPile.receive();
        LeftPile.receive();
        RightPile.receive();

        sandpile -= 4;
    }

    @Override
    public boolean checkOverflow() {
        return sandpile > 3;
    }

    @Override
    public void receive() {
        sandpile++;
    }

    public void setRelations(GridObject upPile, GridObject downPile, GridObject rightPile, GridObject leftPile){
        UpPile = upPile;
        DownPile = downPile;
        RightPile = rightPile;
        LeftPile = leftPile;
    }

    public int getSandpile() {
        return sandpile;
    }

    public void setSandpile(int sandpile) {
        this.sandpile = sandpile;
    }

    public GridObject getUpPile() {
        return UpPile;
    }

    public void setUpPile(GridObject upPile) {
        UpPile = upPile;
    }

    public GridObject getDownPile() {
        return DownPile;
    }

    public void setDownPile(GridObject downPile) {
        DownPile = downPile;
    }

    public GridObject getRightPile() {
        return RightPile;
    }

    public void setRightPile(GridObject rightPile) {
        RightPile = rightPile;
    }

    public GridObject getLeftPile() {
        return LeftPile;
    }

    public void setLeftPile(GridObject leftPile) {
        LeftPile = leftPile;
    }
}
