package net.esindo.crmapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.esindo.crmapp.Adapter.AdapterEvent;
import net.esindo.crmapp.Model.Customer;
import net.esindo.crmapp.Model.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Button btnLogin;

    public static final String URLS ="http://esindo.net/ws/event.php";// "http://192.168.1.150/android_biodata/getdata.php";
    public List<Event> list_event = new ArrayList<Event>();
    private static final String TAG = MainActivity.class.getSimpleName();
    private RequestQueue requestQueue;
    private Gson gson;
    public ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogin = findViewById(R.id.btnLogin);
        listView = findViewById(R.id.list_view);

        // definisi antrian akses data web service
        requestQueue = Volley.newRequestQueue(this);
        // definisikan objek gsonbuilder yang bertugas melakukan deserialisasi data json ke gson
        GsonBuilder gsonBuilder =  new GsonBuilder();
        // inisialisasi obj gson
        gson = gsonBuilder.create();
        getData();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getApplicationContext(), CustomerActivity.class);
                startActivity(ii);
                finish();
            }
        });
    }


    private  void getData(){
        //  Log.d("iduser ",id);
        GsonBuilder gsonBuilder =  new GsonBuilder();
        // inisialisasi obj gson
        gson = gsonBuilder.create();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response.toString());

                        list_event = Arrays.asList(gson.fromJson(response, Event[].class));

                        for (Event prop : list_event) {
                            Log.d("List prngeluaaaaran", prop.getNama() + ": " + prop.getHarga());
                        }


//                        adapter = new RecyclerAdapter(MainActivity.this,list_pengeluaran);
                        //                      recyclerView.setAdapter(adapter);

                        AdapterEvent adapterr =  new AdapterEvent(getApplicationContext(),list_event);
                        listView.setAdapter(adapterr);


                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(postRequest);

    }

}
