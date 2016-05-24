/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skillcourt;

import view.BasicNifty;
import view.ScScreenController;


/**
 *
 * @author Underscore
 */
public class GridView {
    
   
    
    public GridView(){
        
    }
    
    public static void gridUI(GridInfo grid) throws Exception{
        xmlGrid gc = new xmlGrid();
        System.out.println(gc.gridOut(grid));
        
        
        ScScreenController SSC = new ScScreenController();

        BasicNifty runner = new BasicNifty(1024, 768);

        runner.getNifty().loadStyleFile("nifty-default-styles.xml");
        runner.getNifty().loadControlFile("nifty-default-controls.xml");

        runner.getNifty().fromXml("xml_screens/"+grid.getGridName()+".xml", grid.getGridName(), SSC);
        
        runner.gotoScreen(grid.getGridName());

        runner.renderLoop();
        runner.shutDown();
        
    }
    
    //Tester Code
    public static void main(String[] args) throws Exception {
        
        
        
        GridInfo testGrid = new GridInfo("griddy", 3,5);
       
        gridUI(testGrid);
    }
    
    
    
}
