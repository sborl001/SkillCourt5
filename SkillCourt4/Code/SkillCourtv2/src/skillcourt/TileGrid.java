package skillcourt;

import java.util.LinkedList;


/**This symbolizes a grid for testing functionality.
 * 
 * @author Luis Puche
 *
 */
public class TileGrid implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	private Tile[][] grid;
	private String name;
	private int rows;
	private int cols;
	
	//No arg constructor
	public TileGrid(){
		
		rows = -1;
		cols = -1;

		grid = new Tile[1][1];
		
		this.name = "default";
		
		//Initialization of grid objects.
		for(int j = 0; j<grid[0].length; j++){
			for(int i=0; i<grid.length; i++){
				grid[i][j] = new Tile();
			}
		}
	}
	
	public TileGrid(String name, Integer rows, Integer columns){
		this.rows = rows;
		this.cols = columns;
		
		grid = new Tile[rows][columns];
		
		this.name = name;
		
		//Initialization of grid objects.
		for(int j = 0; j<grid[0].length; j++){
			for(int i=0; i<grid.length; i++){
				grid[i][j] = new Tile();
			}
		}
		
	}
	
	public void setGrid(Tile[][] grid){
		this.grid  = grid;
	}
	
	public Tile[][] getGrid(){
		return grid;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	
	public int getRows(){
		return rows;
	}
	
	public int getCols(){
		return cols;
	}
	
	
	//zero out the array. Can be improved by using a list of modified block coordinates.
	public void zeroOut(){
		for(int j = 0; j<grid[0].length; j++){
			for(int i=0; i<grid.length; i++){
				grid[i][j].zeroOut();
			}
		}
	}

	
	//Takes a step
	public void takeStep(Step step) throws Exception{
		LinkedList<GridCommand> commands = step.getTileCommands();		//Fetches the commands from the step.
		if(!commands.isEmpty()){
			// perform actions only if commands is not empty
			for(GridCommand i: commands){
				grid[i.getRow()][i.getCol()].setColor(i.getR(), i.getG(), i.getB());
			}
			
		}
	}
	
	public void takeCommand(GridCommand i) throws Exception{
		
		grid[i.getRow()][i.getCol()].setColor(i.getR(), i.getG(), i.getB());
		
	}
	
	
	
	
}
