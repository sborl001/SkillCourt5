package skillcourt;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class AuxFunction {

    HashMap<String, TileGrid> grids;

    public static SequenceFile XMLdecode(String filename) throws FileNotFoundException {

        XMLDecoder e = new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
        SequenceFile file = (SequenceFile) e.readObject();

        return file;

    }

    public static void printGrids(HashMap<String, TileGrid> grids) {
        for (String i : grids.keySet()) {
            printGrid(grids.get(i));

        }
    }

    public static void printGrids2(HashMap<String, TileGrid> grids) {

        int maxRows = 0;

        //Retrieve MaxCols
        for (String i : grids.keySet()) {
            int curRows = grids.get(i).getRows();
            if (maxRows < curRows) {
                maxRows = curRows;
            }

            System.out.print(grids.get(i).getName());

            //Add spacing after names
            int spacing = (grids.get(i).getCols() * 2) + 1;
            while (spacing > 0) {
                System.out.print(" ");
                spacing--;
            }

        }

        for (int i = 0; i < maxRows; i++) {
            if (i == 0) {
                for (String j : grids.keySet()) {
                    j = "d";
                }

            }

        }

    }

    public static void printGrid(TileGrid gridd) {
        System.out.println(gridd.getName());
        Tile[][] grid = gridd.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j].interpreter() + " ");
            }
            System.out.println();
        }
    }

}
