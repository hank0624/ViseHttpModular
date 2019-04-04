package com.example.common.tool.utilsinterfaces;

import java.io.File;

/**
 *
 * @author Vondear
 * @date 2017/8/9
 */

public interface OnRxCamera {

    void onBefore();

    void onSuccessCompress(File filePhoto);

    void onSuccessExif(File filePhoto);
}