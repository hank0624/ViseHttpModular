package com.example.common.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.common.R;


/**
 * 封装Bulider模式弹出框
 * Created by Hank on 2018/12/7.
 */

public class HintTwoDialog extends BaseDialog{

    TextView mTvTitle;
    TextView mTvContent;
    TextView mBtnLeft;
    TextView mBtnRight;
    View mView;
    Builder builder;

    public interface onLeftClickListener{
        void onClick();
    }

    public interface onRightClickListener{
        void onClick();
    }

    public HintTwoDialog(@NonNull Context context, Builder builder) {
        super(context);
        this.builder = builder;
    }

    public HintTwoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected HintTwoDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_title_hint_two);

        mTvTitle = findViewById(R.id.tv_hint_dialog_title);
        mTvContent = findViewById(R.id.tv_hint_dialog_content);
        mBtnLeft = findViewById(R.id.tv_hint_dialog_left);
        mBtnRight = findViewById(R.id.tv_hint_dialog_right);
        mView = findViewById(R.id.view_hint);

        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        if (builder.anim != -1){
            window.setWindowAnimations(builder.anim);
        }
        if (!TextUtils.isEmpty(builder.title)){
            mTvTitle.setText(builder.title);
        }
        if (!TextUtils.isEmpty(builder.content)){
            mTvContent.setText(builder.content);
        }
        if (!TextUtils.isEmpty(builder.left)){
            mBtnLeft.setText(builder.left);
        }
        if (!TextUtils.isEmpty(builder.right)){
            mBtnRight.setText(builder.right);
        }
        if (builder.isTitle){
            mTvTitle.setVisibility(View.GONE);
        }
        if (builder.isLeft){
            mBtnLeft.setVisibility(View.GONE);
            mView.setVisibility(View.GONE);
        }
        if (builder.isRight){
            mBtnRight.setVisibility(View.GONE);
            mView.setVisibility(View.GONE);
        }
        mBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.leftClick != null){
                    builder.leftClick.onClick();
                }
                closeDialog();
            }
        });
        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (builder.rightClick != null){
                    builder.rightClick.onClick();
                }
                closeDialog();
            }
        });
    }


    public static class Builder{
        private Context context;
        private int anim = -1;
        private String title;
        private String content;
        private String left;
        private String right;
        private boolean isTitle = true;
        private boolean isLeft;
        private boolean isRight;
        private boolean cancelable = true;
        private boolean outside;
        private onLeftClickListener leftClick;
        private onRightClickListener rightClick;

        public HintTwoDialog start(){
            HintTwoDialog dialog = new HintTwoDialog(context,this);
            dialog.setCancelable(cancelable);
            dialog.setCanceledOnTouchOutside(outside);
            dialog.showDialog();
            return dialog;
        }

        public Builder setAnimations(int styleAnimations){
            this.anim = styleAnimations;
            return this;
        }

        public Builder setRightClick(onRightClickListener rightClick){
            this.rightClick = rightClick;
            return this;
        }

        public Builder setLeftClick(onLeftClickListener leftClick){
            this.leftClick = leftClick;
            return this;
        }

        public Builder setCancelable(boolean cancelable){
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean outside){
            this.outside = outside;
            return this;
        }

        public Builder isTitle(boolean isTitle){
            this.isTitle = isTitle;
            return this;
        }

        public Builder isLeft(boolean isLeft){
            this.isLeft = isLeft;
            return this;
        }

        public Builder isRight(boolean isRight){
            this.isRight = isRight;
            return this;
        }

        public Builder(Context context){
            this.context = context;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setContent(String content){
            this.content = content;
            return this;
        }

        public Builder setBtnLeft(String left){
            this.left = left;
            return this;
        }

        public Builder setBtnRight(String right){
            this.right = right;
            return this;
        }
    }
}
