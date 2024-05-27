package com.example.progressdialogapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnDownload;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new btnDownloadCLickListner());

    }
    class btnDownloadCLickListner implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            String [] fileUrls={"file1","file2","file3","file4"};
            new DownloadThread().execute(fileUrls);
        }
    }
    class DownloadThread extends AsyncTask<String , Integer, Float>{
        @Override
        protected Float doInBackground(String... fileUrls) {
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
            return  11.20F;
        }
        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Downloading");
            progressDialog.setMessage("Four files gets downloading");
            progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Float result) {
            super.onPostExecute(result);
            btnDownload.setText("Float "+result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            btnDownload.setText("Progress"+values[0]+ "%d");
        }
    }
}