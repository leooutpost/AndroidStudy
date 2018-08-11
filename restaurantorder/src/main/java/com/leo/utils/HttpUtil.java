package com.leo.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by DELL on 2018/8/2.
 */
public class HttpUtil {

    /*
    * @ Map<String, String> sendInfoMap:要传送给服务器的参数
    * @ String address:服务器访问地址
    * */
    public static String sendHttpRequest(Map<String, String> sendInfoMap, String address,String opration) {
        HttpURLConnection httpURLConnection = null;
        Log.i("准备访问服务器---------","");
        try {
            URL url = new URL(address);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            Log.i("建立了链接---------","");
            //传递数据
           if(sendInfoMap!=null){
               OutputStream os=httpURLConnection.getOutputStream();
               StringBuffer mapValues=new StringBuffer("");
               Iterator<Map.Entry<String,String>> iterator=sendInfoMap.entrySet().iterator();
               int count=0;
               Log.i("准备拆解Map，封装到String----","");
               while (iterator.hasNext()){
                   Map.Entry<String,String> e=iterator.next();
                   if(count==0){
                       mapValues.append(e.getKey()+"="+e.getValue());
                       count++;
                   }else {
                       mapValues.append("&"+e.getKey()+"="+e.getValue());
                   }

               }
//               if(opration!=null){
//                   mapValues.append("&mainOpration"+"="+opration);
//               }


               Log.i("传递的数据：",mapValues.toString());
               os.write(mapValues.toString().getBytes());
               os.flush();
               os.close();
           }

            //获取数据
            BufferedReader br=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String temp="";
            StringBuffer confirmResult=new StringBuffer();

            while ((temp=br.readLine())!=null){
                Log.i("--","while excuted");
                confirmResult.append(temp);
            }
            Log.i("HttpUtil获得的数据：",confirmResult.toString());

            return confirmResult.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "数据访问出错";
    }


}
