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
public class UploadBackgroundTask extends AsyncTask<String,Void,Void> {
    public final static int SOCKET_PORT = 8086;      // you may change this
    public static String SERVER;  // localhost
    public final static int FILE_SIZE = 6022386;
    // you may change this
    public interface AsyncResponse {
        void processFinish();
    }

    public AsyncResponse delegate = null;
    Context ctx;
    public UploadBackgroundTask(Context ctx,AsyncResponse delegate) {
        this.ctx=ctx;
        this.delegate=delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... params) {
        SERVER=ctx.getString(R.string.ip_address);
        String FILE_TO_RECEIVED = params[0];
        int bytesRead;
        int current = 0;
        int check_for_title=0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        BufferedReader br=null;
        Socket sock = null;
        String fileName="";
        String temp="";
        try {
            sock = new Socket(SERVER, SOCKET_PORT);

            File file = new File(Environment.getExternalStorageDirectory(), FILE_TO_RECEIVED);
            // String temp="shubham";
            byte[] bytes = new  byte[2048];
            OutputStream os = sock.getOutputStream();
            // Scanner s=new Scanner(is).useDelimiter("\\A");
            // String result=s.hasNext() ? s.next():"";
            // int temp=result.indexOf("#");

            os = sock.getOutputStream();
            StringBuilder sb=new StringBuilder();
            String line;
            br=new BufferedReader(new FileReader(file));
            while ((line=br.readLine())!=null)
            {
                sb.append(line);
            }
            temp=sb.toString();
            String finalString=FILE_TO_RECEIVED+"#"+temp;
            byte [] mybytearray  = finalString.getBytes("UTF-8");
            //FileOutputStream fos = new FileOutputStream(file);
            //BufferedOutputStream bos = new BufferedOutputStream(fos);
            //int bytesRead = is.read(bytes, 0, bytes.length);
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            br.close();
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