package com.lanolink.zouni.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.lanolink.zouni.core.ZouNiApplication;
import com.lanolink.zouni.exception.NetworkException;
import com.lanolink.zouni.util.TaskResultCode;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class NetUtils {

    private static String LOG_TAG = "NetUtils";
    private static String cookie;

    private static Context context;
    private static int REQUEST_MAX_TIME = 30000;

    public enum NetState {
        NO_CONNECT, CMWAP, CMNET, WIFI, UNKNOWN,
    }

    private static boolean isCMWAP(NetworkInfo info) {
        if (info.getExtraInfo().toLowerCase().equals("cmwap")
                && "10.0.0.172".equals(android.net.Proxy.getDefaultHost())
                && 80 == android.net.Proxy.getDefaultPort()) {
            return true;
        }
        return false;
    }

    public static NetState connectState(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return NetState.NO_CONNECT;
        }

        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return NetState.WIFI;

        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            if (isCMWAP(info)) {
                return NetState.CMWAP;
            } else {
                return NetState.CMNET;
            }
        }
        return NetState.UNKNOWN;
    }

    /*
     * 在使用Android连接网络的时候，并不是每次都能连接到网络，在这个时候，我们最好是在程序启动的时候对网络的状态进行一下判断，
     * 如果没有网络则进行即时提醒用户进行设置。 要判断网络状态，首先需要有相应的权限，下面为权限代码： 即允许访问网络状态：
     * <uses-permission
     * android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
     * 下面为判断代码：
     */
    private static boolean NetWorkStatus() {

        boolean netSataus = false;
        ConnectivityManager cwjManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        cwjManager.getActiveNetworkInfo();

        if (cwjManager.getActiveNetworkInfo() != null) {
            netSataus = cwjManager.getActiveNetworkInfo().isAvailable();
        }

        if (netSataus) {
            // Builder b = new
            // AlertDialog.Builder(this).setTitle("没有可用的网络").setMessage("是否对网络进行设置？");
            // b.setPositiveButton("是", new DialogInterface.OnClickListener()
            // {
            // public void onClick(DialogInterface dialog, int whichButton)
            // {
            // Intent mIntent = new Intent("/");
            // ComponentName comp = new ComponentName(
            // "com.android.settings",
            // "com.android.settings.WirelessSettings");
            // mIntent.setComponent(comp);
            // mIntent.setAction("android.intent.action.VIEW");
            // startActivityForResult(mIntent, 0); //
            // 如果在设置完成后需要再次进行操作，可以重写操作代码，在这里不再重写
            // }
            // }).setNeutralButton("否", new DialogInterface.OnClickListener()
            // {
            // public void onClick(DialogInterface dialog, int whichButton)
            // {
            // dialog.cancel();
            // }
            // }).show();
        }

        return netSataus;
    }

    private static boolean hasConnect() {
        ConnectivityManager manager = (ConnectivityManager) NetUtils.context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();
        // Log.i("con", "NoConnect") ;
        // Log.i("con", manager.toString()) ;
        // Log.i("con", ""+info.toString()) ;
        if (info != null && info.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 超时
     *
     * @param url    http url
     * @param method http的方法
     * @return {@link String}， 超时或者其他网络错误返回null
     * @throws NetworkException    无法连接
     * @throws java.io.IOException 网络传输错误
     */
    public static synchronized String openUrl(final String url, final String method)
            throws NetworkException {
        NetUtils.context = ZouNiApplication.getInstance().getApplicationContext();
        NetState netState = connectState(context);

        if (netState == NetState.NO_CONNECT) {
            throw new NetworkException(TaskResultCode.NO_CONNECTION, null);
        }

        String ret = null;

        HttpClient httpclient = null;
        HttpResponse httpResponse = null;

        httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(
                CoreConnectionPNames.CONNECTION_TIMEOUT, 8000);

        Log.v("net " + CoreConnectionPNames.CONNECTION_TIMEOUT,
                String.valueOf(REQUEST_MAX_TIME));
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
                REQUEST_MAX_TIME);
        Log.v("net " + CoreConnectionPNames.SO_TIMEOUT,
                String.valueOf(REQUEST_MAX_TIME));
        int statusCode = 0;
        try {
            if (method.equalsIgnoreCase("get")) {
                HttpGet request = new HttpGet(url);
                httpResponse = new DefaultHttpClient().execute(request);
            } else if (method.equalsIgnoreCase("post")) {
                HttpPost request = new HttpPost(url);
                httpResponse = new DefaultHttpClient().execute(request);
            }
            statusCode = httpResponse.getStatusLine().getStatusCode();
            // 200请求成功 303重定向 400请求错误 401未授权 403禁止访问 404文件未找到 500服务器错误
            if (statusCode == HttpStatus.SC_OK) {
                ret = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (UnsupportedEncodingException e) {
            throw new NetworkException(TaskResultCode.DATA_ERROR, null, e);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            throw new NetworkException(TaskResultCode.HTTP_ERROR, null, e);
        } catch (ClientProtocolException e) {
            throw new NetworkException(TaskResultCode.HTTP_ERROR, null, e);
        } catch (IOException e) {
            throw new NetworkException(TaskResultCode.TIME_OUT, null, e);
        } catch (Exception e) {
            throw new NetworkException(TaskResultCode.HTTP_ERROR, null, e);
        }
        return ret;
    }

    private static String getHsost(String url) {
        int a = url.indexOf("//");
        String a_str = url.substring(7);
        int b = a_str.indexOf("/");
        a_str = a_str.substring(0, b);
        String addr = a_str.split(":")[0];

        return addr;
    }

    private static int getPort(String url) {
        int a = url.indexOf("//");
        String a_str = url.substring(7);
        int b = a_str.indexOf("/");
        a_str = a_str.substring(0, b);
        int port = Integer.parseInt(a_str.split(":")[1]);
        return port;
    }

    private static int getPort() {
        // TODO Auto-generated method stub
        return 0;
    }

    private static String getHsost() {
        // TODO Auto-generated method stub
        return null;
    }

    private static synchronized String encodeUrl(Bundle b) {

        if (b.isEmpty()) {
            return "";
        }
        Bundle bundle = b;
        // bundle = b;
        StringBuilder params = null;

        params = new StringBuilder();
        Set<String> keySet = bundle.keySet();
        Iterator<String> it = keySet.iterator();
        StringBuilder key = null;
        while (it.hasNext()) {
            key = new StringBuilder(it.next());
            // Log.i(LOG_TAG, key.toString());
            // Log.i(LOG_TAG, bundle.getString(key.toString()));
            try {
                params.append('&');
                params.append(URLEncoder.encode(key.toString(), "UTF-8"));
                params.append('=');
                params.append(URLEncoder.encode(
                        bundle.getString(key.toString()), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Log.e(LOG_TAG, "UnsupportedEncoding", e);
                Log.e(LOG_TAG, "UnsupportedEncoding Key : " + key.toString());
                Log.e(LOG_TAG,
                        "UnsupportedEncoding Value"
                                + bundle.getString(key.toString()));
            }

        }

        return params.toString().replaceFirst("&", "?");
    }

    private static List<NameValuePair> formatPostData(Bundle bundle) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<String> keySet = bundle.keySet();
        Iterator<String> it = keySet.iterator();
        StringBuilder key = null;
        while (it.hasNext()) {
            key = new StringBuilder(it.next());
            Object object = bundle.get(key.toString());
            if (object != null) {
                String value = null;
                value = String.valueOf(object);
                Log.v("Post Data Type", key.toString() + "|" + value);
                params.add(new BasicNameValuePair(key.toString(), value));
            }
        }
        // Log.v("End___middle:", params.toString());
        return params;
    }

}
