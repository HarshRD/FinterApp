import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class upload {

  public final static int SOCKET_PORT = 8087;  // you may change this
  public static String FILE_TO_SEND = "C:/Users/mayur/Desktop/interfacing/";  // you may change this
  public static void main (String [] args ) throws IOException {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
	BufferedReader br=null;
	System.out.println();
	/*
	Scanner sc=new Scanner(System.in);
	String s=sc.next();
	String temp;
	temp=s+"#";
	*/
    try {
      servsock = new ServerSocket(SOCKET_PORT);
      while (true) {
        System.out.println("Waiting...");
        try {
          sock = servsock.accept();
          System.out.println("Accepted connection : " + sock);
		  InputStream is = sock.getInputStream();
          // send file
          //File myFile = new File (FILE_TO_SEND);
          //byte [] mybytearray  = new byte [(int)myFile.length()];
		
         // os = sock.getOutputStream();
         // os.write(nameBytes,0,nameBytes.length);
        //  os.flush();
		  
        StringBuilder sb=new StringBuilder();
		String line;
		br=new BufferedReader(new InputStreamReader(is));
		while((line=br.readLine())!=null)
		{
			sb.append(line);
			
		}
		String temp1=sb.toString();
		//System.out.println(temp1+"abc");
	  //fos = new FileOutputStream(FILE_TO_RECEIVED+fileName);
      //bos = new BufferedOutputStream(fos);
   //   bos.write(mybytearray, 0 , current);
    //  bos.flush();
		is.close();
		  //sock.close();
		  
		  
		  
		  sock = new Socket("192.168.1.65", 8088);
		  
          System.out.println("Accepted connection : " + sock);
          // send file
          File myFile = new File (FILE_TO_SEND+temp1);
          byte [] mybytearray  = new byte [(int)myFile.length()];

         // os = sock.getOutputStream();
         // os.write(nameBytes,0,nameBytes.length);
        //  os.flush();
		  
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray,0,mybytearray.length);
          os = sock.getOutputStream();
          System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
		  os.write(mybytearray,0,mybytearray.length);
          os.flush();
		  sock.close();
          System.out.println("Done.");
        }
        finally {
          if (bis != null) bis.close();
          if (os != null) os.close();
          if (sock!=null) sock.close();
        }
      }
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
}
