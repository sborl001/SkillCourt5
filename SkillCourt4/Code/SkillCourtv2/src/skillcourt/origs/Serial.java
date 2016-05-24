package origs;

/**
 * Dummy class for programming serial
 * @author Underscore
 *
 */
public class Serial {
	Object init;
	String port;
	int baud;
	
	
	public Serial(){
		
	}
	
	public Serial (Object init, String port, int baud){
		this.init = init;
		this.port = port;
		this.baud = baud;
	}

}
