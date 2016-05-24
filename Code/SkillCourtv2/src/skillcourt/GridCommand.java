package skillcourt;

/**
 * Symbolizes information sent to a grid to set a single tile.
 *
 * @author Luis Puche
 *
 */
public class GridCommand implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String gridName;
    private int row, col;
    private int R, G, B;
    private int ID;	//Identification of specific command
    private String standardColor = "o";

    public String getStandardColor() {
        return standardColor;
    }

    public void setStandardColor(String standardColor) {
        this.standardColor = standardColor;
    }

    public GridCommand() {
        this.gridName = "DefaultGrid";
        this.row = -1;
        this.col = -1;
        this.R = -1;
        this.G = -1;
        this.B = -1;
        this.ID = -1;
    }
    
    public GridCommand(String gridName, int row, int column) throws Exception {

        if (row < 0 || column < 0) {
            throw new Exception("GridCommand:Construtor: Cannot use negative values");
        }
        this.gridName = gridName;
        this.row = row;
        this.col = column;
        this.R = -1;
        this.G = -1;
        this.B = -1;
        this.ID = -1;
    }

    public GridCommand(String gridName, int row, int column, int R, int G, int B, int ID) throws Exception {

        if (row < 0 || column < 0) {
            throw new Exception("GridCommand:Construtor: Cannot use negative values");
        }
        this.gridName = gridName;
        this.row = row;
        this.col = column;
        this.R = R;
        this.G = G;
        this.B = B;
        this.ID = ID;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getName() {
        return gridName;
    }

    public void setName(String name) {
        gridName = name;
    }

    public int getR() {
        return R;
    }

    public void setR(int R) {
        this.R = R;
    }

    public int getG() {
        return G;
    }

    public void setG(int G) {
        this.G = G;
    }

    public int getB() {
        return B;
    }

    public void setB(int B) {
        this.B = B;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setRed() {
        R = 255;
        G = 0;
        B = 0;
        standardColor = "r";
    }

    public void setGreen() {
        R = 0;
        G = 255;
        B = 0;
        standardColor = "g";
    }

    public void setBlue() {
        R = 0;
        G = 0;
        B = 255;
        standardColor = "b";
    }

    public void setYellow() {
        R = 255;
        G = 255;
        B = 0;
        standardColor = "y";
    }

    public void setOff() {
        R = 0;
        G = 0;
        B = 0;
        standardColor = "o";
    }
}
