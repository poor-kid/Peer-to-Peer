
import java.io.*;
import java.net.*;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class FileUploader implements Runnable{

	private String fileloc;
	private Node local;
	private final String LINE_SEPERATOR = "\n";

	public FileUploader(String fileloc,Node n){
		local = n;
		this.fileloc = fileloc;
	}
	@Override
	public void run()
	{
		File file = new File(fileloc);
		String fileName = file.getName();
		System.out.println("uploading  "+fileName);
		try{

				long id = Helper.hashString(fileName);
				System.out.println(id);
				InetSocketAddress to_node = local.find_successor(id);
				System.out.println(to_node.getAddress());
				System.out.println(to_node.getPort());


	
          InetSocketAddress str =  Helper.requestAddress(to_node,"UPLOAD_"+fileName);
           System.out.println("gggdhtdth");




///////////////------------------------------sending file content---------------------------////////////////////
           Socket socket  = new Socket(to_node.getAddress(),to_node.getPort()+1)	;	
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