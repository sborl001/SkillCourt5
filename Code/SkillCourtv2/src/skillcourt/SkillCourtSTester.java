/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skillcourt;

import gnu.io.CommPortIdentifier;  
import gnu.io.SerialPort;  
import java.util.Enumeration;
/**
 *
 * @author Underscore
 */
public class SkillCourtSTester {


  public void list() {  
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();  
          
        while(ports.hasMoreElements())  
            System.out.println(((CommPortIdentifier)ports.nextElement()).getName());  
    }  
   
    public static void main(String[] args) {  
        new SkillCourtSTester().list();  
    } 
    
}
