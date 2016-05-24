package skillcourt;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

/* SequenceFile Version 0.60
 * The creation of this file holds everything necessary for a sequence to be executed
 */
public class SequenceFile implements java.io.Serializable {
	

	private static final long serialVersionUID = 1L;
	//HashMap Contents: sequenceName, author, version, difficulty, gameType;
	private HashMap<String, String> info ;
	//northGrid, southGrid, eastGrid, westGrid, floorGrid, ceilingGrid;
	private LinkedList<GridInfo> grids;
	//List of steps
	private LinkedList<Step> steps;
	
	public SequenceFile(){
		info = new HashMap<>();
		grids = new LinkedList<>();
		steps = new LinkedList<>();
	}
	
	public SequenceFile(HashMap<String,String> info, LinkedList<GridInfo> grids, LinkedList<Step> steps){
		this.info = info;
		this.grids = grids;
		this.steps = steps;
		
	}
	
	public HashMap<String, String> getInfo(){
		return info;
	}
	
	public void setInfo(HashMap<String, String> info){
		this.info = info;
	}
	
	public LinkedList<GridInfo> getGrids(){
		return grids;
	}
	
	public void setGrids(LinkedList<GridInfo> grids){
		this.grids = grids;
	}
	
	public LinkedList<Step> getSteps(){
		return steps;
	}
	
	public void setSteps(LinkedList<Step> steps){
		this.steps = steps;
	}
	
	public int exportFile(String filename) throws FileNotFoundException{

		XMLEncoder e = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		e.writeObject(this);
		e.close();
		return 1; // finished without errors
	}
	
	

}
