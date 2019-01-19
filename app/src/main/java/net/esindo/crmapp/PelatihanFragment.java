package net.esindo.crmapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import net.esindo.crmapp.Model.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;


public class PelatihanFragment extends Fragment {


    public static final String URLS ="http://esindo.net/ws/event.php";// "http://192.168.1.150/android_biodata/getdata.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private RequestQueue requestQueue;
    private Gson gson;
    public ListView listView;
    public List<Event> list_event = new ArrayList<Event>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pelatihan, container, false);

        listView =  view.findViewById(R.id.list_view);
        // definisi antrian akses data web service
        requestQueue = Volley.newRequestQueue(getActivity());
        // definisikan objek gsonbuilder yang bertugas melakukan deserialisasi data json ke gson
        GsonBuilder gsonBuilder =  new GsonBuilder();
        // inisialisasi obj gson
        gson = gsonBuilder.create();
        getData();
        return  view;
    }


    private  void getData(){
        //  Log.d("iduser ",id);
        GsonBuilder gsonBuilder =  new GsonBuilder();
        // inisialisasi obj gson
        gson = gsonBuilder.create();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest postRequest = new StringRequest(Request.Method.POST, URLS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("tag", "onResponse: " + response.toString());

                        list_event = Arrays.asList(gson.fromJson(response, Event[].class));

                        for (Event prop : list_event) {
                            Log.d("List prngeluaaaaran", prop.getNama() + ": " + prop.getHarga());
                        }


//                        adapter = new RecyclerAdapter(MainActivity.this,list_pengeluaran);
                        //                      recyclerView.setAdapter(adapter);

                        AdapterEvent adapterr =  new AdapterEvent(getActivity(),list_event);
                        listView.setAdapter(adapterr);


                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(postRequest);

    }


}
