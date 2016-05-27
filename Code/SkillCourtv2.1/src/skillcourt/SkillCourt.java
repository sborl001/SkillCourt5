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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Underscore
 */
public class SkillCourt {

    SequenceFile loadedSequence = new SequenceFile();
    Boolean sequenceLoaded = false;
    String SequenceName;
    String loginName;
    String loginPass;
    
    final Object[] loginOptions = {
            "Login",
            "Create Account",
            "Forgot Password",
            "Exit"

    };

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
    
    public void loginMenu() throws Exception {
        String message = "Welcome to SkillCourt Please login or create an account";
        
        int selection = JOptionPane.showOptionDialog(null,
                message,
                "SkillCourt SH v1",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                loginOptions,
                loginOptions[0]);
        
        if(selection == 0)
            login(); 
        else if(selection == 1)
            createAccount(); 
        else if(selection == 2)
            recoverPassword(); //No implementation for Forgot Password yet
        else if(selection == 3)
            System.exit(0);
    }

    public void mainMenu() throws Exception {

        String message;
        if (sequenceLoaded) {
            message = "Hello " + loginName + ",\nMake your selection: \nA Sequence is loaded";
        } else {
            message = "Hello " + loginName + ",\nMake your selection: ";
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
    
    public void login () throws Exception {    
        try {
            loginName = JOptionPane.showInputDialog("Username: ");
            loginPass = JOptionPane.showInputDialog("Password: ");  
            Boolean loginSuccess = false;
            String host = "jdbc:derby://localhost:1527/SkillCourtUser";
            String userName = "Username";
            String password = "password";
            Connection con = DriverManager.getConnection(host, userName, password);
            Statement stmt = con.createStatement();
            String SQL = "SELECT * FROM USERNAME.USERS";
            ResultSet rs = stmt.executeQuery(SQL);
            
            while(rs.next())
            {
                if(rs.getString("USERNAME").equals(loginName) && rs.getString("PASSWORD").equals(loginPass))
                {
                    JOptionPane.showMessageDialog(null, "Succefully Logged in!");
                    loginSuccess = true;
                    mainMenu();
                    break;
                }            
            }
            if(!loginSuccess)
            {
                JOptionPane.showMessageDialog(null, "Login failed.");
                loginMenu();
            }
            
        } 
        catch (SQLException ex) {
            Logger.getLogger(SkillCourt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createAccount() throws Exception {
       try {         
            String host = "jdbc:derby://localhost:1527/SkillCourtUser";
            String userName = "Username";
            String password = "password";
            String newName = JOptionPane.showInputDialog("Enter desired username: ");
            String newPass = JOptionPane.showInputDialog("Enter desired password: ");
            Connection con = DriverManager.getConnection(host, userName, password);
            Statement stmt = con.createStatement();
            String SQL = "INSERT INTO USERNAME.USERS (USERNAME, PASSWORD)\nVALUES (" + "'" + newName + "'" + "," + "'" + newPass + "'" + ")";
            stmt.executeUpdate(SQL);
            JOptionPane.showMessageDialog(null, "Account succefully added using query:\n" + SQL);
            loginMenu();
       } 
       catch (SQLException ex) {
            Logger.getLogger(SkillCourt.class.getName()).log(Level.SEVERE, null, ex);
        } 
              
    }
    
    public void recoverPassword() throws Exception{
         try {         
            String host = "jdbc:derby://localhost:1527/SkillCourtUser";
            String userName = "Username";
            String password = "password";
            String recoveryAccount = JOptionPane.showInputDialog("Enter username of account for recovery: ");
            Connection con = DriverManager.getConnection(host, userName, password);
            Statement stmt = con.createStatement();
            String SQL = "SELECT USERS.PASSWORD FROM USERNAME.USERS WHERE USERS.USERNAME = " + "'" + recoveryAccount + "'";
            ResultSet rs = stmt.executeQuery(SQL);
            rs.next();
            JOptionPane.showMessageDialog(null, "Your password is: " + rs.getString("PASSWORD"));
            
            loginMenu();
       } 
       catch (SQLException ex) {
            Logger.getLogger(SkillCourt.class.getName()).log(Level.SEVERE, null, ex);
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
        run.loginMenu();

    }
}
