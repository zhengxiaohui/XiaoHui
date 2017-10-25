package com.zbase.util;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 创建人：郑晓辉
 * 创建日期：2017/10/23 0023
 * 描述：Http请求工具
 */
public class HttpUtil {

    public interface OnRequestCallBack{
        void onResponse(String response);
    }

    public static void getRequest(final String url, final OnRequestCallBack onRequestCallBack){
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        String data= String.valueOf(msg.obj);
                        onRequestCallBack.onResponse(data);
                        break;
                }
                super.handleMessage(msg);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                requestHttp(url,handler);
            }
        }).start();
    }

    private static void requestHttp(String baseUrl,Handler handler){
        String data = null;
        HttpURLConnection conn = null;
        BufferedInputStream input = null;
        OutputStream output = null;
        ByteArrayOutputStream baos = null;

        try {
            URL url = new URL(baseUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            output = conn.getOutputStream();
            input = new BufferedInputStream(conn.getInputStream());

            int len = 0;
            baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            while ((len = input.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            data = baos.toString("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                baos = null;
            }
            if (output != null) {
                try {
                    output.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                output = null;
            }
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {

                    e.printStackTrace();
                }
                input = null;
            }
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                conn = null;
            }
        }
        Message message=Message.obtain();
        message.what=0;
        message.obj=data;
        handler.sendMessage(message);
    }

}
