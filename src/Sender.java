import java.io.*; 

import java.net.*;
import java.util.ArrayList;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;



public class Sender {
	private static String input;
    String response;
    int portNumber;
    int size;
    int bytesSent;
    private static boolean terminate;
    private static Socket clientSocket;
    private static OutputStream outBuffer; 
    private static DataOutputStream dataOutBuffer;

    
	public static void main(String args[]) throws Exception { 
		
		// Initialize a client socket connection to the server
	    clientSocket = new Socket("localhost", 9000); 
	    outBuffer = clientSocket.getOutputStream();
	    dataOutBuffer = new DataOutputStream(outBuffer);
		
		BufferedReader inFromUser = 
		        new BufferedReader(new InputStreamReader(System.in)); 

        // Initialize user input stream
        terminate = false;

        ArrayList<Object> objectList = new ArrayList<Object>();
        
     // Get user input
        System.out.println("Please choose from the following commands by entring the number."
        		+ " You can create more than one object. ");
        System.out.println("1. Create a simple object. ");
        System.out.println("2. Create an object that reference to other objects. ");
        System.out.println("3. Create an object that contains an array of primitives. ");
        System.out.println("4. Create an object that contains an array of object references. ");
        System.out.println("5. Create an object that contains Java's collection classes. ");
        System.out.println("6. Serialize the object that you created.");
        System.out.println("7. Exit ");
        
        input = inFromUser.readLine();  
        
        while (!input.equals("7")) {
	        if (input.equals("1")) {
	        	objectList.add(new ObjectCreator().simpleObject());
	        }else if (input.equals("2")) {
	        	objectList.add(new ObjectCreator().refObject());
	        }else if (input.equals("3")) {
	        	objectList.add(new ObjectCreator().arrayObject());
	        }else if (input.equals("4")) {
	        	objectList.add(new ObjectCreator().arrayRefObject());
	        }else if (input.equals("5")) {
	        	objectList.add(new ObjectCreator().collectionObject());
	        }else if (input.equals("6")) {
	        	serilizeObjectList(objectList);
	        } else {
	        	System.out.println("Invalid input! Please enter a number between 1 to 6");
	        }
	        
	        input = inFromUser.readLine();  
        }
        
        clientSocket.close();
        outBuffer.close();
       
	}
	
	public static void serilizeObjectList(ArrayList<Object> objectList) throws Exception {
		Document document;
		for (int i=0; i< objectList.size(); i++) {
			document = new Serializer().serialize(objectList.get(i));
			String filename = "senderFile" + String.valueOf(i) + ".xml"; 
			printOutput(document, filename);
			
		}
	}
	
	public static void  printOutput(Document doc, String filename) throws Exception {
		XMLOutputter outputter = new XMLOutputter();
	
		try {
			
			
		    
			FileOutputStream fos = new FileOutputStream(filename);
			outputter.setFormat(Format.getPrettyFormat());
			
			outputter.output(doc, System.out);
			System.out.println("*********************************************");
			outputter.output(doc, fos);
			System.out.println("XML file for the current object has created!");
			System.out.println("*********************************************");
			client(filename);
			System.out.println("XML file sent to the network!");
			System.out.println("*********************************************");
		} catch (IOException e) {
			System.out.println(e);
		} 
		
	}
	
	public static void client(String filename) throws Exception {
		
		File file = new File(filename);
		
		if (file.exists()) {
			byte[] buffer =new byte[8*1024];
		    // Initialize a client socket connection to the server
		    //Socket clientSocket = new Socket("localhost", 9002); 
        
		    FileInputStream fis = new FileInputStream(filename);
		    
		    
		    int bytesRead;
		    int fileLength = (int) file.length();
		    dataOutBuffer.writeInt(fileLength);
		    
		    while ((bytesRead = fis.read(buffer)) >0) {
		    	outBuffer.write(buffer, 0, bytesRead);
		    	//System.out.println("bytesRead: " + bytesRead);
		    }
        
		    fis.close();
		    
		} else {
			System.out.println("File not found!");
		}        
	}
}
