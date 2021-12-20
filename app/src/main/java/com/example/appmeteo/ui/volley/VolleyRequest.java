package com.example.appmeteo.ui.volley;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.appmeteo.ui.lista.ListMaker;
import com.example.appmeteo.ui.lista.LuoghiAdapter;
import com.example.appmeteo.ui.lista.Luogo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

public class VolleyRequest implements Response.Listener,Response.ErrorListener {

    //passa il risultato della chiamata con Volley ad una classe esterna
    public interface PassaRisultato{
        void onSuccess(Object result);
    }

    private Context context;
    private String url; //url da aprire
    private RequestQueue rq;
    private ProgressBar progressBar;
    public PassaRisultato passa = null;

    public VolleyRequest(Context ctx, String url,ProgressBar pb){
        Log.d("VolleyRequest","costruttore");
        context = ctx;
        this.url = url;
        progressBar = pb;
        rq = Volley.newRequestQueue(context);
        JsonObjectRequest richiesta = new JsonObjectRequest(this.url,this,this);
        rq.add(richiesta);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResponse(Object response) {
        //Response.Listener
        Log.d("VolleyRequest","onResponse");
        progressBar.setVisibility(View.GONE);
        passa.onSuccess(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Response.ErrorListener
        Log.d("VolleyRequest","onErrorResponse");
        /*if(error.networkResponse.data != null){
            try {
                String body = new String(error.networkResponse.data,"UTF-8");
                Log.d("VolleyRequest","onErrorResponse messaggio => "+body);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }*/
        progressBar.setVisibility(View.GONE);
        String msg = "Errore";
        if(error instanceof ParseError){
            msg = "Nessun dato ottenuto dalla ricerca";
        }
        else if(error instanceof ClientError){
            msg = "Impossibile elaborare la richiesta inviata";
        }
        else if(error instanceof ServerError){
            msg = "Errore sconosciuto";
        }
        else if(error instanceof NetworkError){
            msg = "Errore di rete";
        }
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
        error.printStackTrace();
    }
}
