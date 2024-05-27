package com.example.progressdialogapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class SecondActivity extends Activity {
    Button btnDownload;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        btnDownload = findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new BtnDownloadClickListener());
    }

    class BtnDownloadClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String [] fileUrls ={
                    "file1",
                    "file2",
                    "file3",
                    "file4"
            };
            new DownloadThread(SecondActivity.this, new Message()).execute(fileUrls);
        }
    }

    class Message extends Handler {
        public void handleMessage(Message msg){
            super.handleMessage(msg.obtainMessage());
            if(msg == null || msg.obtainMessage().obj == null){
                return;
            }

            if(msg.hasMessages(1)){
                Float result = (Float) msg.obtainMessage().obj;
                btnDownload.setText("Result" + result);
            }

            if(msg.hasMessages(2)){
                Integer [] progress = (Integer[]) msg.obtainMessage().obj;
                btnDownload.setText("Progress" + progress[0] + "%");
            }
        }
    }
}