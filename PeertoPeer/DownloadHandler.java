import java.io.*;
import java.net.*;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class DownloadHandler implements Runnable{

String filename;
 InetSocketAddress requester;
  public DownloadHandler(String fileName_ , InetSocketAddress requester_){
   filename = fileName_;
   requester = requester_;
  }
  @Override
  public void run()
  {

    File file = new File("D:/chordData1/"+filename);
    String fileName = file.getName();
    System.out.println("uploading  "+fileName);
    try{

       InetSocketAddress to_node = requester;

          
          InetSocketAddress str =  Helper.requestAddress(to_node,"UPLOAD_"+fileName);
           System.out.println("gggdhtdth");




///////////////------------------------------sending file content---------------------------////////////////////
           Socket socket  = new Socket(to_node.getAddress(),to_node.getPort()+1)  ; 
           byte [] mybytearray = new byte[(int)file.length()];
          FileInputStream fis = new FileInputStream(file);
          BufferedInputStream bis = new BufferedInputStream(fis);
          OutputStream os = null;
          bis.read(mybytearray,0,mybytearray.length);
          os = socket.getOutputStream();
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
            System.out.println("sent ---");
            //out.close();
            //in.close();
            socket.close();

        //}

    }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 

  }
}