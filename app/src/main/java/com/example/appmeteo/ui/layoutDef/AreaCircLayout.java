package com.example.appmeteo.ui.layoutDef;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appmeteo.LuogoActivity;
import com.example.appmeteo.MainActivity;
import com.example.appmeteo.R;
import com.example.appmeteo.ui.utils.Utils;
import com.example.appmeteo.ui.volley.VolleyRequest;
import com.example.appmeteo.ui.lista.ListMaker;
import com.example.appmeteo.ui.lista.LuoghiAdapter;
import com.example.appmeteo.ui.lista.Luogo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class AreaCircLayout implements View.OnClickListener, VolleyRequest.PassaRisultato, AdapterView.OnItemClickListener {

    private Context context;
    private Activity activity;
    private ProgressBar progressBar;
    private EditText et_areaC_lat;
    private EditText et_areaC_lon;
    private EditText et_areaC_cities;
    private Button bt_areaC;
    private ListView lv_areaC;
    private String urlRequest; //url completo da passare alla JsonObjectRequest

    public AreaCircLayout(Context ctx, Activity act, ProgressBar pb){
        Log.d("AreaCircLayout","costruttore");
        context = ctx;
        activity = act;
        progressBar = pb;
        et_areaC_lat = activity.findViewById(R.id.et_areaC_lat);
        et_areaC_lon = activity.findViewById(R.id.et_areaC_lon);
        et_areaC_cities = activity.findViewById(R.id.et_areaC_cities);
        et_areaC_cities.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_areaC_cities.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_areaC_cities.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                et_areaC_cities.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            }
        });
        bt_areaC = activity.findViewById(R.id.bt_areaC);
        lv_areaC = activity.findViewById(R.id.lv_areaC);
        bt_areaC.setOnClickListener(this);
        lv_areaC.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //View.OnClickListener
        switch(view.getId()){
            case R.id.bt_areaC:
                Log.d("AreaCircLayout","bt_areaC onClick");
                String lat = et_areaC_lat.getText().toString();
                String lon = et_areaC_lon.getText().toString();
                String cities = et_areaC_cities.getText().toString();
                urlRequest = MainActivity.URL_F+"&lat="+lat+"&lon="+lon+"&cnt="+cities;
                Log.d("AreaCircLayout","urlRequest => "+urlRequest);
                if(Utils.connesso(context)){
                    VolleyRequest vr = new VolleyRequest(context,urlRequest,progressBar);
                    vr.passa = this;
                }
                else{
                    Toast.makeText(context,"Collegati ad internet e riprova",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSuccess(Object result) {
        //VolleyResult.PassaRisultato
        JSONObject datiJson = (JSONObject) result;
        Log.d("CodicePostLayout", "onSuccess result => "+datiJson.toString());
        try {
            int code = datiJson.getInt("cod");
            if(code == 200){
                Log.d("onResponse","code = 200");
                //la ricerca ha prodotto dei risultati
                ListMaker<Luogo> listMaker = new ListMaker<>(datiJson);
                List<Luogo> listaLuoghi = listMaker.getList();
                LuoghiAdapter luoghiAdapter = new LuoghiAdapter(context,R.layout.riga_luogo,listaLuoghi);
                lv_areaC.setAdapter(luoghiAdapter);
            }
            else{
                Log.d("onResponse","code = "+code);
                //Errore
                String msg = "";
                if(datiJson.has("message")){
                    msg = datiJson.getString("message");
                    Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //AdapterView.OnItemClickListener
        Log.d("AreaCircLayout","onItemClick");
        switch(adapterView.getId()){
            case R.id.lv_areaC:
                Luogo luogo = (Luogo)adapterView.getItemAtPosition(i);
                Intent luogoIntent = new Intent(context, LuogoActivity.class);
                luogoIntent.putExtra("luogo",luogo);
                context.startActivity(luogoIntent);
                break;
            default:
                break;
        }
    }
}
