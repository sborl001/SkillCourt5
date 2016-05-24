/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skillcourt;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author Underscore
 */
public class xmlGrid {

    public String gridOut(GridInfo grid) throws FileNotFoundException, UnsupportedEncodingException, Exception {

        if(grid.getCols()<= 0 || grid.getRows() <= 0){
            throw new Exception("ERROR: Invalid grid definitions. Closing");
        }
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                + "<nifty>\n"
                + "    <useControls filename=\"nifty-default-controls.xml\"/>\n"
                + "    <useStyles filename=\"nifty-default-styles.xml\"/>\n";

        String tail = "            </panel>\n"
                + "        </layer>\n"
                + "    </screen>\n"
                + "</nifty>\n";

        int rows = grid.getRows();
        int columns = grid.getCols();
        String name = grid.getGridName();

        String controller = "   <screen id=\"" + name + "\" controller=\"view." + name + "\">\n";
        String layer = "        <layer id=\"L" + name + "\" childLayout=\"absolute\">\n";
        String panel = "            <panel id=\"P" + name + "\" childLayout=\"absolute\" backgroundColor=\"#00ccccff\" width=\"713px\" x=\"80\" y=\"23\" style=\"nifty-panel-simple\" height=\"713px\">\n";

        String body = "";

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                body += "           <control name=\"button\" id=\""+i + j+"\" childLayout=\"center\" backgroundColor=\"#666666ff\" width=\"60px\" x=\""+ (j*80 + 80) +"\" y=\""+ (i*80 + 60) +"\" label=\""+i + j+"\" height=\"60px\"/>\n";
            }
        }

        
        PrintWriter writer = new PrintWriter("xml_screens\\"+name+".xml", "UTF-8");
        writer.println(header + controller + layer + panel + body + tail);
        writer.close();
        
        return header + controller + layer + panel + body + tail;

    }

   

}
