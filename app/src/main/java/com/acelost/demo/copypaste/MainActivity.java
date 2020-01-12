package com.acelost.demo.copypaste;

import android.os.Bundle;

import com.acelost.copypaste.CopyPaste;
import com.acelost.copypaste.request.PasteRequests;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CopyPaste.getInstance().paste(this, PasteRequests.cl1pNetRequest("Hello, world!", null));
        CopyPaste.getInstance().paste(this, PasteRequests.friendPasteComRequest("Hello, world!", null));
    }
}
