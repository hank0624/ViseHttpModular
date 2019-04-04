package com.example.common.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/7/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

//    private Unbinder mUnbinder;
//    protected PlaceHolderView mPlaceHolderView;

    public static final int STATUS_COLOR_TRANSPARENT = 0;
    public static final int STATUS_COLOR_WHITE = 1;
    public static final int STATUS_COLOR_TRANSPARENT_BLACK = 2;
    public static final int STATUS_COLOR_TRANSPARENT_BLACK_FULLSCREEN = 3;
    public static final int STATUS_COLOR_WHITE_BLACK = 4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTopCreate();
        super.onCreate(savedInstanceState);

        initWindows();
        if (initArgs(getIntent().getExtras())) {
            setContentView(getLayoutId());
            initWidget();
            initData();
        } else {
            finish();
        }
        initStateWindows();

    }

    private void initStateWindows() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            switch (setMyTitleColor()){
                case STATUS_COLOR_TRANSPARENT://透明
                    //给状态栏设置颜色。我设置主题色。
                    window.setStatusBarColor(Color.TRANSPARENT);
                    //给导航栏设置颜色，我设置
                    window.setNavigationBarColor(Color.TRANSPARENT);
                    break;
                case STATUS_COLOR_WHITE://白色
                    //给状态栏设置颜色。我设置主题色。
                    window.setStatusBarColor(Color.WHITE);
                    //设置字体为黑色
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    //给导航栏设置颜色，我设置黑色
                    window.setNavigationBarColor(Color.BLACK);
                    break;
                case STATUS_COLOR_TRANSPARENT_BLACK://状态栏透明，虚拟键不透明
                    //给状态栏设置颜色。我设置主题色。
                    window.setStatusBarColor(Color.TRANSPARENT);
                    //给导航栏设置颜色，我设置
                    window.setNavigationBarColor(Color.BLACK);
                    break;
                case STATUS_COLOR_TRANSPARENT_BLACK_FULLSCREEN:
                    window.setStatusBarColor(Color.TRANSPARENT);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    window.setNavigationBarColor(Color.BLACK);
                    break;

                case STATUS_COLOR_WHITE_BLACK:
                    //给状态栏设置颜色。我设置主题色。
//                    window.setStatusBarColor(getResources().getColor(R.color.white));
                    //给导航栏设置颜色，我设置黑色
                    window.setNavigationBarColor(Color.BLACK);
                    //window.setStatusBarColor(Color.BLACK);
                    break;
                default://主题色
                    //给状态栏设置颜色。我设置主题色。
                    window.setStatusBarColor(Color.WHITE);
                    //设置字体为黑色
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    //给导航栏设置颜色，我设置黑色
                    window.setNavigationBarColor(Color.BLACK);
                    break;
            }
        }
    }

    /**
     * 设置状态栏样式：0透明1白色2状态栏透明3状态栏透明字体黑色
     * @return
     */
    protected int setMyTitleColor(){
        return -1;
    }

    protected void setTopCreate(){
    }

    /**
     * 销毁当前页面同时空白刷新
     */
    protected void removes(){
        setResult(Activity.RESULT_OK,null);
        Application.getInstance().finishAll();
    }

    protected void removeResult(){
        setResult(Activity.RESULT_OK,null);
        remove();
    }

    protected void removeResult(Intent data){
        setResult(Activity.RESULT_OK,data);
        finish();
    }

    /**
     * 销毁当前页面
     */
    protected void remove(){
        finish();
    }

    protected void gotoAct(Class<?> z){
        Intent i = new Intent();
        i.setClass(this,z);
        startActivity(i);
    }

    protected void gotoAct(Class<?> z,int result){
        Intent i = new Intent();
        i.setClass(this,z);
        startActivityForResult(i,result);
    }

    protected void initBefore() {

    }

    protected void initWindows() {

    }

    /**
     * 初始化相关参数
     *
     * @return
     */
    protected Boolean initArgs(Bundle bundle) {

        return true;
    }

    /**
     * 获取xml 的id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected void initWidget() {

//        mUnbinder = ButterKnife.bind(this);
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 当点击导航栏上面的返回键
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {

        remove();
        return super.onSupportNavigateUp();
    }

    /**
     * 当返回键被点击
     */
    @Override
    public void onBackPressed() {
//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        if (fragments != null && fragments.size() > 0) {
//            for (Fragment ft : fragments) {
//                if (ft instanceof BaseFragment) {
//                    if (((BaseFragment) ft).onBackPressd()) {
//                        return;
//                    }
//                }
//            }
//
//        }
//        super.onBackPressed();
//        remove();

    }

    /**
     * 设置占位布局
     *
     * @param placeHolderView 继承了占位布局规范的View
     */
//    public void setPlaceHolderView(PlaceHolderView placeHolderView) {
//        this.mPlaceHolderView = placeHolderView;
//    }
}
