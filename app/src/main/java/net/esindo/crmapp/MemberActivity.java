package net.esindo.crmapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import net.esindo.crmapp.Adapter.AdapterMember;
import net.esindo.crmapp.Adapter.AdapterPenjualan;
import net.esindo.crmapp.Model.Member;
import net.esindo.crmapp.Model.Penjualan;
import net.esindo.crmapp.Utils.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MemberActivity extends AppCompatActivity {
    public List<Member> list_member = new ArrayList<Member>();
    private static final String TAG = PenjualanActivity.class.getSimpleName();
    private RequestQueue requestQueue;
    private Gson gson;
    private static String url_select     = Server.Url + "membership.php";
    public ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        listView = findViewById(R.id.list_view);

        // definisi antrian akses data web service
        requestQueue = Volley.newRequestQueue(this);
        // definisikan objek gsonbuilder yang bertugas melakukan deserialisasi data json ke gson
        GsonBuilder gsonBuilder =  new GsonBuilder();
        // inisialisasi obj gson
        gson = gsonBuilder.create();
        try {
            getData();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Gagal Terhub",Toast.LENGTH_LONG).show();
        }

    }

    public  void getData(){
        //  Log.d("iduser ",id);
        GsonBuilder gsonBuilder =  new GsonBuilder();
        // inisialisasi obj gson
        gson = gsonBuilder.create();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url_select,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: " + response.toString());

                        list_member = Arrays.asList(gson.fromJson(response, Member[].class));



//                        adapter = new RecyclerAdapter(MainActivity.this,list_pengeluaran);
                        //                      recyclerView.setAdapter(adapter);

                        AdapterMember adapterr =  new AdapterMember(getApplicationContext(),list_member);
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
