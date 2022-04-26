package com.jamieson.noteapp;

import static java.lang.System.currentTimeMillis;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerAPI implements API {

    public static final String BASE_URL = "http://192.168.86.20:12345";
    String MY_TOKEN = "";
    String current_doc_id = "";
    private static JSONObject m_Response = null;


    private final Application mApplication;
    private RequestQueue mRequestQueue;


    public ServerAPI(Application application) {
        mApplication = application;
        mRequestQueue = Volley.newRequestQueue(application);
    }

    public String getToken() {
        return MY_TOKEN;
    }

    public void login(String email, String password, MainActivity ma) {

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        params.put("time_span", "3600");
        params.put("method", "authenticate");
        params.put("time_unit", "SECONDS");
        makeLoginRequest(params, ma);

    }

    public void getDocument(String docId) {
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("method", "getDocument");
        params.put("document_id", docId);
        makeRequestWithHeaders(params);
    }

    public void createAccount(String email, String firstName, String lastName) {
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("method", "createAccount");
        params.put("email", email);
        params.put("first_name", firstName);
        params.put("last_name", lastName);
        makeRequest(params);
    }

    @Override
    public void registerAccount(String password, String temp_pword, String email) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("method", "registerAccount");
        params.put("password", password);
        params.put("temp_password", temp_pword);
        params.put("email", email);
        makeRequest(params);
    }

    private JSONObject getJsonODocument(String title, String text, String docId, String creationDate) throws JSONException {
        JSONObject docJson = new JSONObject();
        docJson.put("title", title);
        docJson.put("text", text);
        docJson.put("id", docId);
        docJson.put("creation_date", creationDate);
        return docJson;
    }

    private JSONArray getJsonAccessors( String[] accessors) throws JSONException {
        JSONArray accJson = new JSONArray();
        for(int i = 0; i < accessors.length; i++) {
            accJson.put(accessors[i]);
        }
        return accJson;
    }

    public void setDocumentAccessors(String[] accessors, String docId) throws JSONException{
        JSONObject requestObject = new JSONObject();
        requestObject.put("method", "setDocumentAccessors");
        requestObject.put("document_id", docId);
        requestObject.put("accessors",  getJsonAccessors(accessors));
        makeRequestWithHeadersJsonObject(requestObject);

    }

    @Override
    public void forgetPassword(String email) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("method", "forgotPassword");
        params.put("email", email);
        makeRequest(params);
    }

    public void setDocument( String text, String title) throws JSONException{
        String docId  =  randomUUID().toString();
        current_doc_id = docId;
        String currentTime = String.valueOf(currentTimeMillis());
        HashMap<String, String> params = new HashMap<String, String>();
        // params.put("method", "setDocument");
        // params.put("document", getJsonODocument(title, text, docId, currentTime));

        JSONObject requestObject = new JSONObject();
        requestObject.put("method", "setDocument");
        requestObject.put("document", getJsonODocument(title, text, docId, currentTime));
        Log.i("document args", requestObject.toString());
        makeRequestWithHeadersJsonObject(requestObject);

    }


    public void delDocument(String docId) {
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("method", "deleteDocument");
        params.put("document_id", docId);
        makeRequestWithHeaders(params);
    }

    public void makeRequest(HashMap<String, String> hm) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BASE_URL, new JSONObject(hm),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            Log.i("Response:", response.toString());
                            MY_TOKEN = response.getString("token");
                            Log.i("Token", MY_TOKEN);
                            m_Response = response;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", (Object) error.getStackTrace());
                if (error instanceof AuthFailureError) {
                    Toast.makeText(mApplication, "Login Failed. Incorrect email or Password", Toast.LENGTH_LONG).show();
                }

            }
        });

        // add the request object to the queue to be executed
        mRequestQueue.add(req);
    }


    public void makeRequestWithHeaders(HashMap<String, String> hmBody) {
        if (MY_TOKEN != "") {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BASE_URL, new JSONObject(hmBody),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                VolleyLog.v("Response:%n %s", response.toString(4));
                                Log.i("Response:", response.toString());
                                m_Response = response;
                                // Toast.makeText(mApplication, "TOKEN" + response.getString("token"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error: ", error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> hmHeaders = new HashMap<>();
                    hmHeaders.put("autho_token", MY_TOKEN);
                    hmHeaders.put("Content-Type", "application/json");
                    return hmHeaders;
                }
            };

            // add the request object to the queue to be executed
            mRequestQueue.add(req);
        } else {
            Log.i("Error:", "Attempting to call authenticated method without a token");
        }

    }

    public void makeRequestWithHeadersJsonObject(JSONObject jo) {
        if (MY_TOKEN != "") {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BASE_URL, jo,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                VolleyLog.v("Response:%n %s", response.toString(4));
                                Log.i("Response:", response.toString());
                                m_Response = response;
                                Toast.makeText(mApplication, "Success!", Toast.LENGTH_LONG).show();
                                // Toast.makeText(mApplication, "TOKEN" + response.getString("token"), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.e("Error ", error.getMessage());
                    Toast.makeText(mApplication, "Fail", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> hmHeaders = new HashMap<>();
                    hmHeaders.put("autho_token", MY_TOKEN);
                    hmHeaders.put("Content-Type", "application/json");
                    return hmHeaders;
                }
            };

            // add the request object to the queue to be executed
            mRequestQueue.add(req);
        } else {
            Log.i("Error:", "Attempting to call authenticated method without a token");
        }

    }

    public void makeLoginRequest(HashMap<String, String> hm, MainActivity ma) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BASE_URL, new JSONObject(hm),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            Log.i("Response:", response.toString());
                            MY_TOKEN = response.getString("token");
                            Log.i("Token", MY_TOKEN);
                            m_Response = response;

                            ma.openDocumentActivity();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", (Object) error.getStackTrace());
                if (error instanceof AuthFailureError) {
                    Toast.makeText(mApplication, "Login Failed. Incorrect email or Password", Toast.LENGTH_LONG).show();
                }

            }
        });
        // add the request object to the queue to be executed
        mRequestQueue.add(req);
    }

    public static UUID randomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid;
    }


}

