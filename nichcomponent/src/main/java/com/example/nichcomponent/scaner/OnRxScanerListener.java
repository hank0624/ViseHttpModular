package com.example.nichcomponent.scaner;


import com.google.zxing.Result;

/**
 * @author lpp
 */

public interface OnRxScanerListener {
    void onSuccess(String type, Result result);

    void onFail(String type, String message);
}
