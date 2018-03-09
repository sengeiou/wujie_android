//package com.txd.hzj.wjlp.wjyp;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//public class MainAty extends Activity {
//
//    Button button;
//    Button button2;
//    Button button3;
//    Button button4;
//    Button button5;
//    Button button6;
//    Button button7;
//    Button button8;
//
//    private Intent intent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.aty_main);
//        button = (Button) findViewById(R.id.button);
//        button2 = (Button) findViewById(R.id.button2);
//        button3 = (Button) findViewById(R.id.button3);
//        button4 = (Button) findViewById(R.id.button4);
//        button5 = (Button) findViewById(R.id.button5);
//        button6 = (Button) findViewById(R.id.button6);
//        button7 = (Button) findViewById(R.id.button7);
//        button8 = (Button) findViewById(R.id.button8);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent();
//                intent.setClass(MainAty.this, WebViewAty.class);
//                intent.putExtra("type", "0");
//                startActivity(intent);
//            }
//        });
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent();
//                intent.setClass(MainAty.this, WebViewAty.class);
//                intent.putExtra("type", "1");
//                startActivity(intent);
//            }
//        });
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent();
//                intent.setClass(MainAty.this, WebViewAty.class);
//                intent.putExtra("type", "2");
//                startActivity(intent);
//            }
//        });
//        button4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent();
//                intent.putExtra("type", "0");
//                intent.setClass(MainAty.this, ApplyAty.class);
//                startActivity(intent);
//            }
//        });
//        button5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                intent = new Intent();
//                intent.putExtra("type", "1");
//                intent.setClass(MainAty.this, ApplyAty.class);
//                startActivity(intent);
//            }
//        });
//        button6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainAty.this, TZSAty.class));
//            }
//        });
//        button7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainAty.this, ExpandTheMemberAty.class));
//            }
//        });
//        button8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainAty.this, LMSJAty.class));
//            }
//        });
//    }
//}
