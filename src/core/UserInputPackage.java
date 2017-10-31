package core;

public class UserInputPackage {
    private int columnInput;
    private int rowInput;
    private int amountInput;

    public UserInputPackage(int columnInput, int rowInput, int amountInput) {
        this.columnInput = columnInput;
        this.rowInput = rowInput;
        this.amountInput = amountInput;
    }

    public int getColumnInput() {
        return columnInput;
    }

    public void setColumnInput(int columnInput) {
        this.columnInput = columnInput;
    }

    public int getRowInput() {
        return rowInput;
    }

    public void setRowInput(int rowInput) {
        this.rowInput = rowInput;
    }

    public int getAmountInput() {
        return amountInput;
    }

    public void setAmountInput(int amountInput) {
        this.amountInput = amountInput;
    }
}
