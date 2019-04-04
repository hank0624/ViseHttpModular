package com.example.aite.visehttpmodular;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.aite.visehttpmodular.databinding.ActivityMainBinding;
import com.example.common.app.Common;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

//        mBinding.btnARouter.setOnClickListener((v)->{
//            ARouter.getInstance().build(Common.ARouterAPI.GROUP_PATH).navigation();
//        });
        mBinding.btnARouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Object navigation = ARouter.getInstance().build(ARouterPath.GirlsListFgt).navigation();
//                if (navigation instanceof FragmentGirls){
//                    FragmentGirls girls = (FragmentGirls) navigation;
//                    String name = girls.name;
//                    Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
//                }
                ARouter.getInstance().build(Common.ARouterAPI.COMMON_PATH).navigation(MainActivity.this);
//                ARouter.getInstance().build(Common.ARouterAPI.GROUP_PATH).withString("key1", "value1").navigation(MainActivity.this,999);
            }
        });

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
