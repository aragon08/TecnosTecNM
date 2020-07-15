package com.example.orion.tecnostecnm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.koushikdutta.ion.ResponseServedFrom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listado extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    RequestQueue qSolicitudes;
    List<Tecnos> listaTecnos;

    private RecyclerView reciclador;
    private RecyclerView.Adapter adaptador;
    private  RecyclerView.LayoutManager adminLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        listaTecnos =new ArrayList<>();
        qSolicitudes= Volley.newRequestQueue(this);
        getTecnologicos();

        reciclador= (RecyclerView) findViewById(R.id.reciclador);
        reciclador.setHasFixedSize(true);
        adminLayout=new LinearLayoutManager(this);
        reciclador.setLayoutManager(adminLayout);
    }

    private void getTecnologicos(){
        String URL="http://ws.itcelaya.edu.mx:8080/intertecs/apirest/institucion/listado";
        StringRequest solListaTecnos =new StringRequest(Request.Method.GET,URL,this,this){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", "intertecs", "1nt3rt3c5").getBytes(), Base64.DEFAULT)));
                return params;
            }
        };
        qSolicitudes.add(solListaTecnos);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Tecnos objTecno;
        Log.e("Error:", response);
        try{
            JSONObject objJSON =new JSONObject(response);
            JSONArray arrJSON = objJSON.getJSONArray("institucion");
            for (int i=0; i<arrJSON.length();i++){
                objTecno=new Tecnos();
                JSONObject objJSONTecno= arrJSON.getJSONObject(i);
                objTecno.setIdTecno(objJSONTecno.getInt("id_institucion"));
                objTecno.setLogoTecno(objJSONTecno.getString("logotipo"));
                objTecno.setNomCortoTecno(objJSONTecno.getString("nombre_corto"));
                objTecno.setNomTecno(objJSONTecno.getString("institucion"));
                listaTecnos.add(objTecno);
            }

            adaptador=new TecnosAdapter(listaTecnos,this);
            reciclador.setAdapter(adaptador);

        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
