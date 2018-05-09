import java.io.*;
import java.net.*;

public class FileReciever implements Runnable{

		String fileTitle;
		int port;
		ServerSocket serverSocket;
		Socket socket;

		public FileReciever(String _fileTitle,int _port){
			fileTitle = _fileTitle;
			port = _port+1;
		}
	
	public void run(){
			System.out.println("deg");
			File file = new File("D:/chordData1/" +fileTitle);

                if(file.exists()){
                    file.delete();
                }
               

	 try{

	 				serverSocket = new ServerSocket(port);
	 				Socket socket = serverSocket.accept();
	 				
                	file.createNewFile();
                	byte [] mybytearray  = new byte [661024];
                FileOutputStream fos = new FileOutputStream(file);
            	BufferedOutputStream bos = new BufferedOutputStream(fos);

            	InputStream is = socket.getInputStream();
            	int bytesRead = is.read(mybytearray,0,mybytearray.length);
            	int current = bytesRead;

            	do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      System.out.println("uploaded");
      fos.close();
      is.close();
      serverSocket.close();
                //BufferedInputStream bis = new BufferedInputStream(_socket.getInputStream());
             // ----  //byte[] chunk = filedata.getBytes();


//                int bytesRead = 0;
//
  //              while((bytesRead = input.read(chunk)) > 0) {
//
                    //System.out.println("\nREAD : " + Arrays.toString(chunk) +"    " + bytesRead);

                  //fos.write(chunk);
    //            }
                //  fos.flush();
      			//   fos.close();
//
                }catch(IOException e){
				  e.printStackTrace();
				}
		}
                
}