package ru.ID20.app.net.requests;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import ru.ID20.app.R;
import ru.ID20.app.constants.Constants;
import ru.ID20.app.constants.enums.RequestType;
import ru.ID20.app.db.ResponseData;
import ru.ID20.app.db.models.CollectionsModel;
import ru.ID20.app.tools.Logger;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
public abstract class BaseRequest implements Runnable {

    public boolean isLogged = false;

    protected HttpClient httpClient;
    protected HttpResponse response;
    protected HttpEntity responseEntity;
    protected HttpPost httpPost;
    protected Logger log;
    protected Gson gson;
    private List<NameValuePair> nameValuePairs;
    protected ResponseData responseData;
    protected RequestType requestType;
    protected Intent intent;
    protected Context context;

    public BaseRequest(Context context, Intent intent) {
        this.intent = intent;
        this.context = context;
        setRequestType((RequestType) getArg().getSerializable(Constants.REQUEST_TYPE_ARG));
        httpClient = new DefaultHttpClient();
        log = Logger.getLogger(getLogTag(), isLogged);
        gson = new Gson();
        nameValuePairs = new ArrayList<>();
    }

    abstract String getLogTag();

    public void setData() {

        httpPost = new HttpPost(getRequestType().getUrl());
        UrlEncodedFormEntity form = null;
        try {
            form = new UrlEncodedFormEntity(nameValuePairs, "UTF-8");
            form.setContentEncoding(HTTP.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpPost.setEntity(form);
    }

    protected String getResponseString() throws Exception {
        String responseString = null;
        if (isOnline()){
            response = httpClient.execute(httpPost);
            log.debug("getStatusLine = " + response.getStatusLine());
            responseEntity = response.getEntity();
            responseString = EntityUtils.toString(responseEntity, "");
            return responseString;
        } else {
            throw new Exception(context.getResources().getString(R.string.network_is_not_available));
        }
    }

    protected void addNewValuePair(String key, String value){
       nameValuePairs.add(new BasicNameValuePair(key, value));
    }

    protected CollectionsModel getCollectionModel(JSONObject response) {
       return getGson().fromJson(response.toString(), CollectionsModel.class);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    protected Bundle getArg() {
        return intent.getBundleExtra(Constants.REQUEST_ARGS);
    }

    protected void clearData() {
        nameValuePairs.clear();
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public Gson getGson() {
        return gson;
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
