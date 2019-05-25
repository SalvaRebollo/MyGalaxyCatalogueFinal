package com.ceviche.sareb.salvisapp.Clases.ConexionMySQL;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class httpHandler {

    public String post(String posturl) {

        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(posturl);
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseText = EntityUtils.toString(entity);

            return responseText;

        } catch (Exception e) {

            return e.getMessage();
        }


    }

}
