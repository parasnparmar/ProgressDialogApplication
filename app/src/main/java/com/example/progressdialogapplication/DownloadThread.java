package com.example.progressdialogapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.nio.channels.AsynchronousChannelGroup;

public class DownloadThread extends AsyncTask<String, Integer, Float> {
    Handler handler;
    Context context;

    String []fileUrls = {"file1","file2","file3","file4"};

    ProgressDialog progressDialog;

    public DownloadThread(Context context, Handler handler){
        this.context = context;
        this.handler = handler;
    }
    @Override
    protected Float doInBackground(String... strings) {
        for (String fileUrl: fileUrls) {
            for (int i=0;i<100;i++){
                Log.e("tag","downloading"+fileUrls +"--"+ i+ "%");
                try {
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    throw new RuntimeException(e);
                }
                Integer [] progress = new Integer[1];
                progress[0] = i;
                Log.e("tag","Progress"+progress[0]);
                publishProgress(i);
                progressDialog.setProgress(i);

            }

        }
        return 100.0F;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Files Downloading");
        progressDialog.setMessage("Downloading...");
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Float result) {
        super.onPostExecute(result);
        Message message = new Message();
        message.what = 1;
        message.obj = result;
        handler.sendMessage(message);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Message message = new Message();
        message.what = 2;
        message.obj = values;
        handler.sendMessage(message);
    }
}
