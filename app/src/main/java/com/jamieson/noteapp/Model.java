package com.jamieson.noteapp;

import android.app.Application;
import android.view.View;

import org.json.JSONException;

public class Model implements API {

    private static Model sInstance = null;
    private final API mApi;
    private static MainActivity ma;

    public static Model getInstance(Application application) {
        if (sInstance == null) {
            sInstance = new Model(application);
        }
        return sInstance;
    }

    private final Application mApplication;

    private Model(Application application) {
        mApplication = application;
        mApi = new ServerAPI(mApplication);
    }

    public Application getApplication() {
        return mApplication;
    }

   /* public void login(String email, String password) {
        mApi. login(email,password);

    }*/

    public String getToken(){
        return mApi.getToken();
    }

    public void login(String email, String password, MainActivity ma){
        mApi.login(email,password, ma);
    }

    public void getDocument(String docId){
        mApi.getDocument(docId);
    }

    public void delDocument(String docId){
        mApi.delDocument(docId);
    }

    public void createAccount(String email,String firstName, String lastName) {
        mApi.createAccount(email, firstName, lastName);
    }

    public void registerAccount(String password, String temp_password, String email) {
        mApi.registerAccount(password, temp_password, email);
    }

    public void setDocument( String text, String title) throws JSONException {
        mApi.setDocument(text,title);
    }

    @Override
    public void setDocumentAccessors(String[] emails, String docId) throws JSONException {
        mApi.setDocumentAccessors(emails, docId);
    }

    @Override
    public void forgetPassword(String email) {
        mApi.forgetPassword(email);
    }

}
