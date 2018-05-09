import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
//import java.nio.file;
import java.io.*;
import java.net.*;

/**
 * Talker thread that processes request accepted by listener and writes
 * response to socket.
 * @author Chuan Xia
 *
 */

public class Talker implements Runnable{

	Socket talkSocket;
	private Node local;
	String requestedFile;
        String fileTitle;

         InputStream input = null;
		 OutputStream output = null;

        byte[] chunk = new byte[1024];

	public Talker(Socket _talkSocket, Node _local)
	{
		talkSocket = _talkSocket;
		local = _local;
	}

	public void run()
	{
		
		try {
			input = talkSocket.getInputStream();
			String request = Helper.inputStreamToString(input);
			String response = processRequest(request,talkSocket);
			
			if (response != null) {
				output = talkSocket.getOutputStream();
				output.write(response.getBytes());
			}
			input.close();
		} catch (IOException e) {
			throw new RuntimeException(
					"Cannot talk.\nServer port: "+local.getAddress().getPort()+"; Talker port: "+talkSocket.getPort(), e);
		}
	}

	private String processRequest(String request,Socket _talkSocket)
	{
		talkSocket = _talkSocket;
		InetSocketAddress result = null;
		String ret = null;
		if (request  == null) {
			return null;
		}
		if (request.startsWith("CLOSEST")) {
			long id = Long.parseLong(request.split("_")[1]);
			result = local.closest_preceding_finger(id);
			String ip = result.getAddress().toString();
			int port = result.getPort();
			ret = "MYCLOSEST_"+ip+":"+port;
		}
		else if (request.startsWith("YOURSUCC")) {
			result =local.getSuccessor();
			if (result != null) {
				String ip = result.getAddress().toString();
				int port = result.getPort();
				ret = "MYSUCC_"+ip+":"+port;
			}
			else {
				ret = "NOTHING";
			}
		}
		else if (request.startsWith("YOURPRE")) {
			result =local.getPredecessor();
			if (result != null) {
				String ip = result.getAddress().toString();
				int port = result.getPort();
				ret = "MYPRE_"+ip+":"+port;
			}
			else {
				ret = "NOTHING";
			}
		}
		else if (request.startsWith("FINDSUCC")) {
			System.out.println("aefafafe");	
			long id = Long.parseLong(request.split("_")[1]);
			result = local.find_successor(id);
			String ip = result.getAddress().toString();
			int port = result.getPort();
			ret = "FOUNDSUCC_"+ip+":"+port;
		}
		else if (request.startsWith("IAMPRE")) {
			System.out.println("IAMPRE     kgkkkkkkkkkgkkk");
			InetSocketAddress new_pre = Helper.createSocketAddress(request.split("_")[1]);
			local.notified(new_pre);
			NotifyRoutine Noti = new NotifyRoutine(local,new_pre);
			Thread notifyroutine = new Thread(Noti);
			notifyroutine.start();
			System.out.println("IAMPREEEEEE");
			ret = "NOTIFIED";
		}
		else if (request.startsWith("KEEP")) {
			ret = "ALIVE";
		}

		else if(request.startsWith("UPLOAD")){
					
			//fileTitle = Helper.inputStreamToString(input);
			//System.out.println(fileTitle);

			 File folder = new File("D:/chordData1/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                String fileTitle = request.split("_")[1];
                //String filedata = request.split("_")[2];
               
               	local.fileMap.put(Helper.hashString(fileTitle),fileTitle);


                FileReciever thread = new FileReciever(fileTitle, local.getAddress().getPort());
                Thread uploader = new Thread(thread);
            
                uploader.start();
                
            
                //bis.close();
                
                System.out.println("uploader thread on!");
                ret = "NOTHING";
            }
         else if(request.startsWith("DOWNLOAD")){
         		ret = "NOTFOUND";

         		String fileName = request.split("_")[1];

         		
         		InetSocketAddress requester = Helper.createSocketAddress(request.split("_")[2]);

         		System.out.println(fileName+"requested");

         		String fileloc = "D:/chordData1/"+fileName ;
				System.out.println(fileloc);
				File file = new File(fileloc);
         		

         		//File f1 = new File("D:/chordData/"+fileName);

         		if(file.exists()){

         			ret = "FOUND";
         			DownloadHandler thr2 = new DownloadHandler(fileName,requester);
         			Thread downloader = new Thread(thr2);
         			downloader.start();	
         		}


         		
         }
		

         		
         

		return ret;

	}
}