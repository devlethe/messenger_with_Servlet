package com.test.messeger;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Web {

    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static JSONObject getPOST(String Url, Map<String, String> parm) {
        try {
            URL url = new URL(Url);
            String postData = null;
            for (Map.Entry<String, String> elem : parm.entrySet()) {
                if (postData == null) {
                    postData = elem.getKey() + "=" + elem.getValue();
                } else {
                    postData = postData + "&" + elem.getKey() + "=" + elem.getValue();
                }
            }
            Log.d("postdata", postData);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(postData.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));
            conn.disconnect();
            Log.d("post_data", json.toString());
            return json;
        } catch (Exception ex) {
            Log.d("error_post", ex.toString());
            return null;
        }
    }

    public static JSONObject getGET(String Url, Map<String, String> parm) {
        try {
            String getData = null;
            for (Map.Entry<String, String> elem : parm.entrySet()) {
                if (getData == null) {
                    getData = elem.getKey() + "=" + elem.getValue();
                } else {
                    getData = getData + "&" + elem.getKey() + "=" + elem.getValue();
                }
            }
            Url = Url + "?" + getData;
            Log.d("getdata", Url);
            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));
            return json;
        } catch (Exception ex) {
            Log.d("error_get", ex.toString());
            return null;
        }
    }
}
