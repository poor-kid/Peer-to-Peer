import java.io.*;
import java.net.*;

public class FileDownloader implements Runnable{

	private String fileName ;
	private Node local ;
	private final String LINE_SEPERATOR = "\n";
	ServerSocket serverSocket;

	public FileDownloader(String _fileName , Node n)
	{
		local = n;
		fileName = _fileName;

	}

	public void run()
	{
		System.out.println("requesting download  - "+ fileName);

		
			long id = Helper.hashString(fileName);
			System.out.println(id);
			InetSocketAddress node = local.find_successor(id);
			System.out.println(node.getAddress());
			System.out.println(node.getPort());
			int port = node.getPort();
			int portLocal = local.getAddress().getPort();
			//requesting the file
			String  str =  Helper.sendRequest(node,"DOWNLOAD_"+fileName+"_"+local.getAddress().toString()+":"+portLocal);
			System.out.println(str);
			if(str.startsWith("FOUND")){
				 System.out.println("fou    fou");

				}


				
			else if(str.startsWith("NOTFOUND")){
				System.out.println("requested file not found");
			}


	}

}