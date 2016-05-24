package origs;

/**Creates a connection for a serial
 * 
 * @author Luis Puche
 *
 */
public class SerialPadHandler {
	
	String port;
	int baud;
	boolean connection = false;
	pad linkedPad;
	Serial serial;
	
	public SerialPadHandler(){
		port = "COM0";
		baud = 0;
		connection = false;
	}
	
	
	public SerialPadHandler(Object parent, String port, int baud, boolean connection,
			pad linkedPad) throws Exception {
		super();
		this.port = port;
		if(baud < 0){
			throw new Exception("Error: Baud invalid");
		}
		this.baud = baud;
		this.connection = connection;
		this.linkedPad = linkedPad;
		this.serial = new Serial(parent,port,baud);
		
	}



	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public int getBaud() {
		return baud;
	}
	public void setBaud(int baud) throws Exception {
		if(baud < 0){
			throw new Exception("Error: Baud invalid");
		}
		else
		this.baud = baud;
	}
	public boolean isConnection() {
		return connection;
	}
	public void setConnection(boolean connection) {
		this.connection = connection;
	}
	public pad getLinkedPad() {
		return linkedPad;
	}
	public void setLinkedPad(pad linkedPad) {
		this.linkedPad = linkedPad;
	}
	public Serial getSerial() {
		return serial;
	}
	public void setSerial(Serial serial) {
		this.serial = serial;
	}
	
	

}
