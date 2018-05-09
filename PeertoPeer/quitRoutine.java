import java.io.*;
import java.net.*;
import java.util.*;


public class quitRoutine{
	Node local;
	public quitRoutine(Node _local){
		local = _local;
		InetSocketAddress succ = local.getSuccessor();
		for(Map.Entry<Long,String> entry : local.fileMap.entrySet()){
				 long l = entry.getKey();
				 

				 	String fileName = entry.getValue();
				 	InetSocketAddress str =  Helper.requestAddress(succ,"UPLOAD_"+fileName);
				 		try{
				 			File file = new File("D:/chordData1/"+fileName);
			///////////////------------------------------sending file content---------------------------////////////////////
           Socket socket  = new Socket(succ.getAddress(),succ.getPort()+1)	;	
           byte [] mybytearray = new byte[(int)file.length()];
          FileInputStream fis = new FileInputStream(file);
          BufferedInputStream bis = new BufferedInputStream(fis);
          OutputStream os = null;
          bis.read(mybytearray,0,mybytearray.length);
          os = socket.getOutputStream();
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
            socket.close();
            bis.close();
            fis.close();
            //local.fileMap.remove(l);
             if(file.delete())
        {
            System.out.println("File deleted successfully");
        }
        else
        {
            System.out.println("Failed to delete the file");
        }
        //}

		}catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 

				 }
	}
}