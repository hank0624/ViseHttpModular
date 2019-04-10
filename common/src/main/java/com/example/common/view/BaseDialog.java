package com.example.common.view;

import android.app.Dialog;
import android.content.Context;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * Created by Hank on 2018/11/3.
 */

public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void showDialog(){
        if (!isShowing())
            show();
    }

    public void closeDialog(){
        if (isShowing())
            cancel();
    }
}