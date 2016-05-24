package skillcourt;

import java.util.LinkedList;

/*
 * Creation of a step for the sequence File
 */
public class Step implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private long duration;				 	// Duration of tiles remaining on, seconds.
	private long interval;				 	// Duration before tiles light up, seconds.
	private LinkedList<GridCommand> tileCommands;	//Tiles involved and effects. REPLACE WITH HASHMAP OF String, LinkedListGridCommand
	
	public Step(){
		duration = -1L;
		interval = -1L;
		tileCommands = new LinkedList<GridCommand>();
	}
	public Step(long duration, long interval) throws Exception{
		if(duration < 0 || interval < 0){
			throw new Exception("Step:Constructor: Invalid values for time. FluxCapacitor.java not found.");
		}
		this.duration = duration;
		this.interval = interval;
		tileCommands = new LinkedList<GridCommand>();
	}
        
        public Step(long duration, long interval, LinkedList<GridCommand> gc) throws Exception{
		if(duration < 0 || interval < 0){
			throw new Exception("Step:Constructor: Invalid values for time. FluxCapacitor.java not found.");
		}
		this.duration = duration;
		this.interval = interval;
		tileCommands = gc;
	}
	
	public long getDuration(){
		return duration;
	}
	public void setDuration(long duration){
		this.duration = duration;
	}
	
	public long getInterval(){
		return interval;
	}
	
	public void setInterval(long interval){
		this.interval = interval;
	}
	
	public void setTileCommands(LinkedList<GridCommand> commands){
		tileCommands = commands;
	}

	public LinkedList<GridCommand> getTileCommands(){
		return tileCommands;
	}
	
	public void addCommand(GridCommand command){
		tileCommands.add(command);
	}
}
