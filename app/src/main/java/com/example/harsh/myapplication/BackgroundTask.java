package com.example.harsh.myapplication;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedInputStream;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
/**
 * Created by $hubham on 13/10/2016.
 */
public class BackgroundTask extends AsyncTask<String,Void,Void> {
    public final static int SOCKET_PORT = 43215;  // you may change this
    public static String FILE_TO_SEND = "/root/sdcard/CV.doc";  // you may change this
      // you may change this
    public interface AsyncResponse {
        void processFinish();
    }

    public AsyncResponse delegate = null;
    Context ctx;
    public BackgroundTask(Context ctx,AsyncResponse delegate) {
        this.ctx=ctx;
        this.delegate=delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        String FILE_TO_SEND = params[0];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;
        BufferedReader br=null;
        //String temp;
        // temp=s+"#";
        //FILE_TO_SEND +=s;
        byte[] nameBytes;
        try {
             //nameBytes="shubham19123".getBytes("UTF-8");
            servsock = new ServerSocket(SOCKET_PORT);
            File myFile = new File (Environment.getExternalStorageDirectory(),FILE_TO_SEND);
            //nameBytes="shubham".getBytes("UTF-8");

            while (true) {
                //Toast.makeText(ctx, "Reached", Toast.LENGTH_SHORT).show();
                //Toast.makeText(ctx,"Waiting",Toast.LENGTH_LONG).show();
                try {
                    sock = servsock.accept();
                    //System.out.println("Accepted connection : " + sock);
                    // send file


                     //os = sock.getOutputStream();
                    // os.write(nameBytes,0,nameBytes.length);
                     //os.flush();
                   // nameBytes=temp.getBytes("UTF-8");
                    os = sock.getOutputStream();
                    StringBuilder sb=new StringBuilder();
                    String line;
                    br=new BufferedReader(new FileReader(myFile));
                    while ((line=br.readLine())!=null)
                    {
                        sb.append(line);
                    }
                    String temp=sb.toString();
                    String finalString=FILE_TO_SEND+"#"+temp;
                    byte [] mybytearray  = finalString.getBytes("");
                    //String fileName=temp.substring(0,temp.indexOf("#"));
                    //fis = new FileInputStream(myFile);
                    //bis = new BufferedInputStream(fis);
                    //bis.read(mybytearray,0,mybytearray.length);

                    //System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");*/
                    os.write(mybytearray,0,mybytearray.length);
                    os.flush();
                    //System.out.println("Done.");

                     //os = sock.getOutputStream();
                     //os.write(nameBytes,0,nameBytes.length);
                     //os.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();

                } finally {
                    if (bis != null) try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (os != null) try {
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (sock!=null) try {
                        sock.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            if (servsock != null)
                try {
                    servsock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }



        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}