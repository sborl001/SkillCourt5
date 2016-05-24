/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skillcourt;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Underscore
 */
public class SkillCourt {

    SequenceFile loadedSequence = new SequenceFile();
    Boolean sequenceLoaded = false;
    String SequenceName;

    final Object[] mainOptions = {
        "Create",
        "Run",
        "Settings",
        "Show Grid",
        "Exit"

    };

    final Object[] createOptions = {
        "Add",
        "Delete",
        "Next"
    };

    final Object[] colors = {
        "Red",
        "Blue",
        "Green"
    };

    public SkillCourt() {
    }

    public SkillCourt(SequenceFile file, String filename) {
        loadedSequence = file;
        sequenceLoaded = true;
        SequenceName = filename;
    }

    public void mainMenu() throws Exception {

        String message;
        if (sequenceLoaded) {
            message = "Make your selection: \nA Sequence is loaded";
        } else {
            message = "Make your selection: ";
        }

        int selection = JOptionPane.showOptionDialog(null,
                message,
                "SkillCourt SH v1",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                mainOptions,
                mainOptions[0]);

        if (selection == 0) {
            create();
        } else if (selection == 1) {
            run();
        } else if (selection == 2) {
            settings();
        } else if (selection == 3){
            viewGrid();
        }else{
            System.exit(0);
        }

    }

    public void run() throws Exception {

        try {
            String filename = JOptionPane.showInputDialog("Enter the sequence filename: ");
            loadedSequence = AuxFunction.XMLdecode(filename);
            sequenceLoaded = true;
            Simulator sim = new Simulator(loadedSequence);
            sim.runSimulation();
            mainMenu();
        } catch (FileNotFoundException ex) {
            System.out.println("RUN: File not found exception");
            mainMenu();
        }
    }

    public void settings() {

    }

    public void create() throws Exception {

        

        //Start of details
        String sequenceName = JOptionPane.showInputDialog("Enter the sequence name: ");
        String author = JOptionPane.showInputDialog("Enter the creator's name: ");

        HashMap<String, String> info = new HashMap<>();
        info.put("SequenceNam", sequenceName);
        info.put("Author", author);
        
        //Grid Selection
        LinkedList<GridInfo> grids = createGrids();

        //Step Creation
        LinkedList<Step> steps = createSteps();
        
        SequenceFile output = new SequenceFile(info, grids, steps);

        output.exportFile(sequenceName+ ".xml");
        
        System.out.println("Sequence Saved");
        mainMenu();
    }

    public LinkedList<GridInfo> createGrids() {
        LinkedList<GridInfo> out = new LinkedList<>();
        Boolean done = false;

        String message = "Current Grids: \n";
        while (!done) {

            String gridsPrint = "";

            for (GridInfo info : out) {
                gridsPrint += "[ " + info.getGridName() + " | " + info.getRows() + ", " + info.getCols() + "]\n";
            }

            int selection = JOptionPane.showOptionDialog(null, message + gridsPrint,
                    "Select an option for grids", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,
                    null, createOptions, createOptions[0]);

            //Add
            if (selection == 0) {
                String gridName = JOptionPane.showInputDialog("Enter the Grid's name: ");
                String rows = JOptionPane.showInputDialog("Enter the number of rows: ");
                String cols = JOptionPane.showInputDialog("Enter the number of columns: ");
                GridInfo newGrid = new GridInfo(gridName, Integer.decode(rows), Integer.decode(cols));
                out.add(newGrid);
            } //Remove
            else if (selection == 1) {
                //Delete function doesn't exist yet.
                return out;
            } //To Steps
            else {
                return out;
            }
        }
        System.out.println("Something happened");
        return null;

    }

    public LinkedList<Step> createSteps() throws Exception {
        LinkedList<Step> out = new LinkedList<>();
        Boolean done = false;

        int numberSteps = 1;

        String message = "Current Steps: \n";
        while (!done) {

            String gridsPrint = "";

            int count = 1;
            for (Step info : out) {
                gridsPrint += "[" + count + "] Dur: " + info.getDuration() + " | Intr: " + info.getInterval() + "\n";
                count++;
            }

            int selection = JOptionPane.showOptionDialog(null, message + gridsPrint,
                    "Select an option for Steps", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,
                    null, createOptions, createOptions[0]);

            //Add
            if (selection == 0) {
                String duration = JOptionPane.showInputDialog("Enter the step's duration: ");
                String interval = JOptionPane.showInputDialog("Enter the step's interval before it begins: ");

                //Off to create commands
                LinkedList<GridCommand> gc = createCommands();
                out.add(new Step(Integer.decode(duration), Integer.decode(interval), gc));
                numberSteps++;

            } //Remove
            else if (selection == 1) {
                //Delete function doesn't exist yet.
                return out;
            } //ended
            else {
                return out;
            }
        }
        System.out.println("Something happened");
        return null;
    }

    public LinkedList<GridCommand> createCommands() throws Exception {
        LinkedList<GridCommand> out = new LinkedList<>();
        Boolean done = false;

        String message = "Current GridsCommands for this step: \n";
        while (!done) {

            String gridsPrint = "";

            for (GridCommand info : out) {
                gridsPrint += "[ " + info.getName() + " | " + info.getRow() + ", " + info.getCol() + "] to " + info.getR() + ", " + info.getG() + ", " + info.getB() + "\n";
            }

            int selection = JOptionPane.showOptionDialog(null, message + gridsPrint,
                    "Add or Remove GridCommands.", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,
                    null, createOptions, createOptions[0]);

            //Add
            if (selection == 0) {
                String gridName = JOptionPane.showInputDialog("Enter the Grid's name: ");
                String rows = JOptionPane.showInputDialog("Enter the row: ");
                String cols = JOptionPane.showInputDialog("Enter the column: ");

                int color = JOptionPane.showOptionDialog(null, "Select a color",
                        "What do we do to grids under this step.", JOptionPane.DEFAULT_OPTION, JOptionPane.DEFAULT_OPTION,
                        null, colors, colors[0]);

                GridCommand newGridCommand = new GridCommand(gridName, Integer.decode(rows), Integer.decode(cols));
                if (color == 0) {
                    newGridCommand.setRed();
                } else if (color == 1) {
                    newGridCommand.setBlue();
                } else{
                    newGridCommand.setGreen();
                }

                out.add(newGridCommand);
            } //Remove
            else if (selection == 1) {
                //Delete function doesn't exist yet.
                return out;
            } //To Steps
            else {
                return out;
            }
        }
        System.out.println("Something happened");
        return null;

    }
    
    public void viewGrid() throws Exception{
        if(sequenceLoaded){
            
            GridView.gridUI(loadedSequence.getGrids().getFirst());
        }
        else{
            System.out.println("No Sequence Loaded");
        }
        
        mainMenu();
    }

    public static void main(String[] args) throws Exception {

        SkillCourt run = new SkillCourt();
        run.mainMenu();

    }
}
