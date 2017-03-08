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
public class DownloadBackgroundTask extends AsyncTask<String,Void,String> {
    public final static int SOCKET_PORT = 8087;      // you may change this
    public static String SERVER;  // localhost
    public final static int FILE_SIZE = 6022386;
    // you may change this
    public interface AsyncResponse {
        void processFinish(String Output);
    }

    public AsyncResponse delegate = null;
    Context ctx;
    public DownloadBackgroundTask(Context ctx,AsyncResponse delegate) {
        this.ctx=ctx;
        this.delegate=delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        SERVER=ctx.getString(R.string.ip_address);
        String FILE_TO_RECEIVED = params[0];
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        BufferedReader br=null;
        OutputStream os = null;
        Socket sock = null;
        try {
            sock = new Socket(SERVER, SOCKET_PORT);
            os = sock.getOutputStream();
            BufferedWriter bwr=new BufferedWriter(new PrintWriter(os));
            byte [] mybytearray  = FILE_TO_RECEIVED.getBytes("UTF-8");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            sock.close();

        } catch (IOException e) {

        } finally {
            if(sock != null){
                try {
                    sock.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }if(br!=null)
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        ServerSocket servsock=null;
        try{
        servsock = new ServerSocket(8088);
        File myFile = new File (Environment.getExternalStorageDirectory(),FILE_TO_RECEIVED);
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
                InputStream is = sock.getInputStream();
                StringBuilder sb=new StringBuilder();
                String line;
                br=new BufferedReader(new InputStreamReader(is));
                while ((line=br.readLine())!=null)
                {
                    sb.append(line);
                }
                String temp=sb.toString();
                //String finalString=FILE_TO_SEND+"#"+temp;
                byte [] mybytearray  = temp.getBytes("UTF-8");
                //String fileName=temp.substring(0,temp.indexOf("#"));
                //fis = new FileInputStream(myFile);
                //bis = new BufferedInputStream(fis);
                //bis.read(mybytearray,0,mybytearray.length);

                //System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");*/
                BufferedWriter bwrr=new BufferedWriter(new FileWriter(myFile));
                bwrr.write(temp);
                bwrr.close();
                os.flush();
                servsock.close();
                sock.close();
                break;
                //System.out.println("Done.");

                //os = sock.getOutputStream();
                //os.write(nameBytes,0,nameBytes.length);
                //os.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
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
                if (servsock != null)
                    try {
                        servsock.close();
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
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}