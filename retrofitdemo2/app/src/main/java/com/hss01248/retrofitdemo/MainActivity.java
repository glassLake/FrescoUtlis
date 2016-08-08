package com.hss01248.retrofitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private String ip = "http://gc.ditu.aliyun.com/geocoding?a=上海市&aa=松江区&aaa=车墩镇";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RetrofitManage.getInstance().sendRequest();
    }
}
