import java.io.*;
import java.net.*;

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
            
            byte[] buffer =new byte[8*1024];
            int bytesRead;
            int fileLength = dataInBuffer.readInt();
            System.out.println("file length: " + fileLength);
            
            int fileBytesReceived = 0;
            while ((bytesRead = inBuffer.read(buffer)) >0) {
            	fileBytesReceived += bytesRead;
		    	fos.write(buffer, 0, bytesRead);
		    	
		    	if (fileBytesReceived  == fileLength) {
		    		fos.close();
		    		count++;
		    		fileLength = dataInBuffer.readInt();
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
}
