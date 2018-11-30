package com.buyint.mergerbot.UIs;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by lfu on 2017/4/18
 */

public class JavaScriptinterface {

    Context context;

    public JavaScriptinterface(Context c) {
        context = c;
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public void showToast(String ssss) {
        Toast.makeText(context, ssss, Toast.LENGTH_SHORT).show();
    }

}
