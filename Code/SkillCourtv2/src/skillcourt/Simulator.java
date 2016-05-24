package skillcourt;

import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JOptionPane;

public class Simulator {

    boolean fileLoad = false;
    HashMap<String, String> info = new HashMap<String, String>();
    HashMap<String, TileGrid> grids = new HashMap<String, TileGrid>();
    LinkedList<Step> steps = new LinkedList<Step>();

    public Simulator() {

    }

    public Simulator(SequenceFile input) {
        //process info
        info = input.getInfo();
        //process steps
        steps = input.getSteps();
        //process grids
        for (GridInfo i : input.getGrids()) {
            grids.put(i.getGridName(), new TileGrid(i.getGridName(), i.getRows(), i.getCols()));
        }

        fileLoad = true;
    }

    public void loadFile(SequenceFile input) {
        //process info
        info = input.getInfo();
        //process steps
        steps = input.getSteps();
        //process grids
        for (GridInfo i : input.getGrids()) {
            grids.put(i.getGridName(), new TileGrid(i.getGridName(), i.getRows(), i.getCols()));
        }

        fileLoad = true;

    }

    public void runSimulation() throws Exception {

        Arduino test = new Arduino();
        if (test.initialize()) {
            System.out.println("it worked");
            
        }

        //Arduino end
        if (!fileLoad) {
            System.out.println("No file loaded!");
        } else {
            //INFORMATION SECTION
            System.out.println("Author: " + info.get("Author"));
            System.out.println("Type: " + info.get("Type"));
            System.out.println("Name: " + info.get("Name"));

            AuxFunction.printGrids(grids);

            for (Step i : steps) {

                System.out.println("Duration: " + i.getDuration() + "| Interval: " + i.getInterval());

                for (GridCommand j : i.getTileCommands()) {
                    //Fetch the grid

                    if (grids.containsKey(j.getName())) {
                        TileGrid temp = grids.get(j.getName());
                        //Command code
                        if (j.getName().equals("North") && j.getCol() == 0 && j.getRow() == 0) {
                            test.sendData(j.getStandardColor());
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ie) {
                            }
                        }
                        temp.takeCommand(j);
                    } else {
                        throw new Exception("Simulator:runSimulation: GridCommand without a matching grid. Possible corruption");
                    }

                }
                JOptionPane.showMessageDialog(null, "Step Completed");
                AuxFunction.printGrids(grids);
                test.sendData("o");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ie) {
                            }
            }

        }
        test.close();
    }

}
