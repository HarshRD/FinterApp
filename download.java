import java.net.*;
import java.io.*;

public class download {

  public final static int SOCKET_PORT = 8086;      // you may change this
  public final static String FILE_TO_RECEIVED = "C:/Users/mayur/Desktop/interfacing/";  // you may change this, I give a
                                                            // different name because i don't want to
                                                            // overwrite the one used by server...
public static String temp="";
  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

  public static void main (String [] args ) throws IOException {
	FileInputStream fis = null;
    BufferedInputStream bis = null;
	BufferedReader br=null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
	System.out.println();

	byte[] nameBytes=temp.getBytes("UTF-8");
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
		String temp=sb.toString();
		String fileName=temp.substring(0,temp.indexOf("#"));
		File file=new File(FILE_TO_RECEIVED+fileName);
		BufferedWriter bwr=new BufferedWriter(new FileWriter(file));
		bwr.write(temp.substring(temp.indexOf("#")+1,temp.length()));
	  //fos = new FileOutputStream(FILE_TO_RECEIVED+fileName);
      //bos = new BufferedOutputStream(fos);
   //   bos.write(mybytearray, 0 , current);
    //  bos.flush();
		bwr.close();
		is.close();
		System.out.println("File " + FILE_TO_RECEIVED+ " downloaded (" + temp.substring(temp.indexOf("#")+1,temp.length()).length() + " bytes read)");
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