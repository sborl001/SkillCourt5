package skillcourt;


public class GridInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String gridName;
	private int rows;
	private int cols;
	
	public GridInfo(){
		gridName = "defaultName";
		rows = -1;
		cols = -1;
	}
	
	public GridInfo(String gridName, int rows, int cols){
		this.gridName = gridName;
		this.rows = rows;
		this.cols = cols;
	}
	
	public String getGridName(){
		return gridName;
	}
	
	public void setGridName(String gridName){
		this.gridName = gridName;
	}
	
	public int getRows(){
		return rows;
	}
	
	public void setRows(int rows){
		this.rows = rows;
	}
	
	public int getCols(){
		return cols;	
	}
	
	public void setCols(int cols){
		this.cols = cols;
	}
	
	

}
