import java.io.*;
import java.net.*;
import java.util.*;
public class NotifyRoutine implements Runnable{


		Node local;
		InetSocketAddress pre;

		public NotifyRoutine(Node _local , InetSocketAddress _pre)
		{
			local = _local;
			pre  = _pre;
		}

		public void run(){

			long prehash = Helper.hashSocketAddress(pre);
			long localhash = Helper.hashSocketAddress(local.getAddress());
			long [] peru = new long[20];
			int x = 0;
				for(Map.Entry<Long,String> entry : local.fileMap.entrySet()){
				 long l = entry.getKey();
				 
				
				 	System.out.println(l);
				 }



			for(Map.Entry<Long,String> entry : local.fileMap.entrySet()){
				 long l = entry.getKey();
				 long local_rel = Helper.computeRelativeId(localhash,l);
				 long pre_rel = Helper.computeRelativeId(localhash,prehash);
				 if(local_rel>pre_rel)
				 {
				 	peru[x] = l;
				 	x = x  +  1 ;
				 	System.out.println(l);
				 	String fileName = entry.getValue();
				 	InetSocketAddress str =  Helper.requestAddress(pre,"UPLOAD_"+fileName);
				 		try{
				 			File file = new File("D:/chordData1/"+fileName);
			///////////////------------------------------sending file content---------------------------////////////////////
           Socket socket  = new Socket(pre.getAddress(),pre.getPort()+1)	;	
           byte [] mybytearray = new byte[(int)file.length()];
          FileInputStream fis = new FileInputStream(file);
          BufferedInputStream bis = new BufferedInputStream(fis);
          OutputStream os = null;
          bis.read(mybytearray,0,mybytearray.length);
          os = socket.getOutputStream();
          os.write(mybytearray,0,mybytearray.length);
          os.flush();
            socket.close();
            fis.close();
            bis.close();
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

			for(int i=0;i<x;i++){
				local.fileMap.remove(peru[i]);
			}
		}

}