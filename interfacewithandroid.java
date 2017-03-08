import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class interfacewithandroid {

  public final static int SOCKET_PORT = 8085;  // you may change this
 //public static String FILE_TO_SEND = "C:/Users/mayur/Desktop/interfacing/";  // you may change this
  public static void main (String [] args ) throws IOException {
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
	System.out.println();
	/*
	Scanner sc=new Scanner(System.in);
	String s=sc.next();
	String temp;
	temp=s+"#";*/
	File folder = new File("C:/Users/mayur/Desktop/interfacing/");
	File[] listOfFiles = folder.listFiles();
	String filenames="";
    for (int i = 0; i < listOfFiles.length; i++) {
      if (listOfFiles[i].isFile()) {
        filenames += listOfFiles[i].getName()+"#";
      } else if (listOfFiles[i].isDirectory()) {
        filenames  += listOfFiles[i].getName()+"#";
      }
    }
	System.out.println(filenames.substring(0,filenames.length()-1));
	//FILE_TO_SEND +=s;
	byte[] nameBytes=filenames.substring(0,filenames.length()-1).getBytes("UTF-8");
    try {
      servsock = new ServerSocket(SOCKET_PORT);
      while (true) {
        System.out.println("Waiting...");
        try {
          sock = servsock.accept();
          System.out.println("Accepted connection : " + sock);
          // send file
          //File myFile = new File (FILE_TO_SEND);
          //byte [] mybytearray  = new byte [(int)myFile.length()];

          os = sock.getOutputStream();
          os.write(nameBytes,0,nameBytes.length);
          os.flush();
		//System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
		 /* 
          fis = new FileInputStream(myFile);
          bis = new BufferedInputStream(fis);
          bis.read(mybytearray,0,mybytearray.length);
          os = sock.getOutputStream();
          
		    //os.write(nameBytes,0,nameBytes.length);
			byte[] combined = new byte[nameBytes.length + mybytearray.length];

			System.arraycopy(nameBytes,0,combined,0         ,nameBytes.length);
			System.arraycopy(mybytearray,0,combined,nameBytes.length,mybytearray.length);
          os.write(combined,0,combined.length);
          os.flush();*/
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
