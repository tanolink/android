package com.lanolink.zouni.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.lanolink.zouni.R;

/**
 * Created by wangduo on 15/5/11.
 */
public class BaseActivity extends Activity {

    protected AsyncTask<?, ?, ?> loadingTask;
    protected boolean isLoadingData;
    private AlertDialog loadingDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showLoadingDialog() {
        showLoadingDialog(getResources().getString(R.string.loading));
    }

    protected void showLoadingDialog(String msg) {
        if (null == loadingDlg) {
            loadingDlg = new AlertDialog.Builder(this).create();
        }
        if (loadingDlg.isShowing()) {
            return;
        }
        loadingDlg.setMessage(msg);
        loadingDlg.setCanceledOnTouchOutside(false);
        loadingDlg.setOnCancelListener(mOnCancelListener);
        loadingDlg.show();
        isLoadingData = true;
    }

    private DialogInterface.OnCancelListener mOnCancelListener = new DialogInterface.OnCancelListener() {

        @Override
        public void onCancel(DialogInterface dialog) {
            // TODO Auto-generated method stub
            cancelLoading();
        }
    };

    protected void hideLoadingDialog() {
        if (null != loadingDlg) {
            isLoadingData = false;
            loadingDlg.dismiss();
            loadingDlg = null;
        }
    }

    protected void cancelLoading() {
        if (isLoadingData == true && loadingTask != null
                && !loadingTask.isCancelled()) {
            loadingTask.cancel(true);
        }
        isLoadingData = false;
    }

}
