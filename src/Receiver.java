import java.io.*;
import java.net.*;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

public class Receiver {
	public static void main(String args[]) throws IOException {

        ServerSocket echoServer = null;
        Socket clientSocket = null;
        InputStream inBuffer = null;
        FileOutputStream fos = null;
        DataInputStream dataInBuffer;
        int count = 0;
        
        try {
            echoServer = new ServerSocket(9000);
        } catch (IOException e) {
            System.out.println(e);
        }
   
        try {
            clientSocket = echoServer.accept();
            System.out.println("Accept connection from " + clientSocket.toString());

            inBuffer = clientSocket.getInputStream();
            dataInBuffer = new DataInputStream(inBuffer);
            String fileName = "ReceiverFile" + count + ".xml";
            fos = new FileOutputStream(fileName);
            
            byte[] buffer;
            int bytesRead;
            int fileLength = dataInBuffer.readInt();
            buffer =new byte[fileLength];
            System.out.println("file length: " + fileLength);
            
            int fileBytesReceived = 0;
            while ((bytesRead = inBuffer.read(buffer)) >0) {
            	fileBytesReceived += bytesRead;
		    	fos.write(buffer, 0, bytesRead);
		    	System.out.println("file bytes recieved: " + fileBytesReceived);
		    	if (fileBytesReceived  == fileLength) {
		    		fos.close();
		    		System.out.println("XML file recieved over the network!");
					System.out.println("*********************************************");
		    		deserializeDocument(fileName);
		    		System.out.println("Deserialization of the recieved XML document finished!");
					System.out.println("*********************************************");
		    		count++;
		    		fileLength = dataInBuffer.readInt();
		    		buffer =new byte[fileLength];
		            System.out.println("file length: " + fileLength);
		            fileName = "ReceiverFile" + count + ".xml";
		    		fos = new FileOutputStream(fileName);
		            fileBytesReceived = 0;
		    	}
		    }
            
            //fos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        
        inBuffer.close();
        //fos.close();
        clientSocket.close();
        echoServer.close();
        
        
	}
	
	public static void deserializeDocument(String fileName) {
		
		try {
			File inputFile = new File(fileName);
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputFile);
			Object object = new Deserializer().deserialize(document);
			new Inspector().inspect(object, true);
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		
		
	}
}
