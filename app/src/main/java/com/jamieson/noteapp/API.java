package com.jamieson.noteapp;

import org.json.JSONException;

public interface API {
      void login(String email, String password, MainActivity ma);
      //void login();
      void getDocument(String docId);
      void delDocument(String docId);
      void createAccount(String email,String firstName, String lastName);
      void registerAccount(String password, String temp_pword, String email);
      void setDocument( String text, String title) throws JSONException;
      void setDocumentAccessors(String[] emails, String docId) throws JSONException;
      void forgetPassword(String email);
      String getToken();


}
