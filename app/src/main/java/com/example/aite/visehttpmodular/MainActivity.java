package com.example.aite.visehttpmodular;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.aite.visehttpmodular.databinding.ActivityMainBinding;
import com.example.common.app.Common;
import com.example.nichcomponent.ActivityScanerCode;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding viewDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //调用扫一扫
//        startActivityForResult(ActivityScanerCode.class, Common.ForActivityApi.SCAN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && null != data) {
            switch (requestCode) {
//                case Common.ForActivityApi.SCAN:
//                    handlerScanResult(data);
//                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 二维码/条形码扫描结果
     */
    private void handlerScanResult(Intent data) {
        if (data.getBooleanExtra("isSuccess",false)){
            String codeType=data.getStringExtra("codeType");
            String codeResult=data.getStringExtra("codeResult");
            MyApplication.showToast(codeResult);
        }

    }
}
