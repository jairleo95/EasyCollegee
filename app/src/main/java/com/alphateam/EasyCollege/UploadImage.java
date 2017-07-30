package com.alphateam.easycollege;

/**
 * Created by Jairleo95 on 07/09/2015.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class UploadImage {
    private final String HTTP_EVENT="http://imageserver.webcindario.com/APIREST.php";
    public Boolean uploadPhoto(String encodedImage) throws ClientProtocolException, IOException, JSONException{
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(HTTP_EVENT);
        httppost.addHeader("Content-Type", "application/json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("encodedImage", encodedImage );
        StringEntity stringEntity = new StringEntity( jsonObject.toString());
        stringEntity.setContentType( (Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httppost.setEntity(stringEntity);
        HttpResponse response = httpclient.execute(httppost);
        String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
        JSONObject object = new JSONObject(jsonResult);
        if( object.getString("Result").equals("200")) {
            return true;
        }
        return false;
    }

    private StringBuilder inputStreamToString(InputStream is)
    {
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader rd = new BufferedReader( new InputStreamReader(is) );
        try
        {
            while( (line = rd.readLine()) != null ) {
                stringBuilder.append(line);
            }
        }
        catch( IOException e){
            e.printStackTrace();
        }
        return stringBuilder;
    }

}
