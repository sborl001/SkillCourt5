package skillcourt;


/*
 * Symbolizes tile information
 */
public class Tile implements java.io.Serializable{


	private static final long serialVersionUID = -8222388681263589214L;

	private int R,G,B;  //Color values
	

	public Tile(){

		R = 0;
		G = 0;
		B = 0;	
	}
	

	public int getR(){
		return this.R;
	}
	
	public int getG(){
		return this.G;
	}
	public int getB(){
		return this.B;
	}
	

	public void setR(int R){
		this.R = R;
	}
	public void setG(int G){
		this.G = G;
	}
	
	public void setB(int B){
		this.B = B;
	}
	
	public void setColor(int R, int G, int B) throws Exception{
		
		if(R < 0 || G < 0 || B < 0|| R > 255 || G > 255 || B > 255){	//Check for validity of input. 0 to 255
			throw new Exception("Tile:setColor: Invalid value for colors");
		}
		else{

			this.R = R;
			this.G = G;
			this.B = B;
		}
	}
	
	
	public void zeroOut(){
		R = 0;
		G = 0;
		B = 0;
	}
	
	public String interpreter(){
		if(R == 0 && G == 0 && B == 0 ){
			return "X";
		}
		else if(R == 255 & G == 0 && B == 0){
			return "R";
		}
		else if(R == 0 && G == 255 && B == 0){
			return "G";
		}
		else if(R == 0 && G == 0 && B == 255){
			return "B";
		}
		else if(R == 255 && G == 255 && B == 0){
			return "Y";
		}
		return "O";
			
	}
	

}
