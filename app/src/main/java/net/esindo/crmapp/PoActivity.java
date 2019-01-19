package net.esindo.crmapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.esindo.crmapp.Dbconfig.SQLiteHandler;
import net.esindo.crmapp.Dbconfig.SessionManager;
import net.esindo.crmapp.Model.Customer;
import net.esindo.crmapp.Utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PoActivity extends AppCompatActivity {

    private static final String TAG = PoActivity.class.getSimpleName();
    Spinner spinner;
    Spinner spinner2;


    private static String URL    = Server.Url2 + "customer";

    private static String URL2    = Server.Url2 + "produk";

    private static String URL3    = Server.Url2 + "getpo";


    private static String URL4    = Server.Url2 + "addpo";


    ArrayList<Map<String, Object>> CountryName;
    ArrayList<Map<String, Object>> ProdName;
    private TextView hrg;
    private TextView nopo;
    private EditText tglpo;
    private EditText qty;
    private EditText total;
    private EditText diskon;
    private EditText cus;
    private EditText notice;
    private EditText harga;
    private EditText produk;
    private EditText ttlbayar;
    private Button btnBack;
    private Button btnSave;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;


    private String jsonData ;
    private String idusr;
    private SQLiteHandler db;
    private SessionManager session;
    private Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_po);

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);

        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());


        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
        idusr= user.get("id");
        String role = user.get("role");





        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        CountryName=new  ArrayList<Map<String, Object>>();
        ProdName=new ArrayList<Map<String, Object>>();

        spinner=findViewById(R.id.cus);
        spinner2=findViewById(R.id.pdk);
    //    cus = (EditText) findViewById(R.id.cus);
        notice = findViewById(R.id.notice);
        hrg = findViewById(R.id.hrg);
        tglpo = findViewById(R.id.tglpo);
        qty = findViewById(R.id.qty);
        total =  findViewById(R.id.total);
        total.setEnabled(false);
        diskon = findViewById(R.id.diskon);
        nopo = (TextView) findViewById(R.id.nopo);
        nopo.setEnabled(false);
         getListcus(idusr,role);
         loadSpinnerData2(URL2);
        loadNopo();

        Log.d(TAG, "onResponse:: " + idusr+nopo.getText().toString());
        tglpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogg();
            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> produks=   ProdName.get(spinner2.getSelectedItemPosition());
              //  Map<String, Object> selectedItem = rData.get(spinner.getSelectedItemPosition());
                String name = produks.get("name").toString();
                String id = produks.get("id").toString();

                String[] parts = name.split("#");
                String part1 = parts[0]; // 004
                String part2 = parts[1]; // 034556
                hrg.setText(part2);
             //   Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });
        qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               String qtty = qty.getText().toString();
               if (qtty.length() == 0) {
                   qtty = "0";
               }
               String hg =   hrg.getText().toString().trim();
               String sum = String.valueOf(Integer.parseInt(qtty) * Integer.parseInt(hg));
               if (qtty.length()>0){

                   total.setText(sum);
               }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        diskon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String dsskn = diskon.getText().toString();
                if (dsskn.length() == 0) {
                    dsskn = "0";
                }
                String ttl =   total.getText().toString().trim();
                String qttyy = qty.getText().toString().trim();
                String hg = hrg.getText().toString().trim();
                if (Integer.parseInt(qttyy) >0){
                   String tl = String.valueOf( Integer.parseInt(hg) * Integer.parseInt(qttyy)  - Integer.parseInt(dsskn) );

                    String sum = String.valueOf(Integer.parseInt(tl));
                    if (dsskn.length()>0){

                        total.setText(sum);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(ii);
                finish();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tgl = tglpo.getText().toString();
                String jml = hrg.getText().toString();
                String ket = notice.getText().toString();
                String nop = nopo.getText().toString();
                String qqty = qty.getText().toString();
                String dskn = diskon.getText().toString();
                String tllbuy = total.getText().toString();

                Map<String, Object> cus=   CountryName.get(spinner.getSelectedItemPosition());
                Map<String, Object> produks=   ProdName.get(spinner2.getSelectedItemPosition());
                String idcus = cus.get("id").toString();
                String idprod = produks.get("id").toString();

                String bulan  =  String.valueOf(spinner.getSelectedItemId()+1);

                Log.d("userid ","useridnya adalah  tgl,jml,kett "+tgl+jml+ket+bulan);

                if (!tgl.isEmpty() && !jml.isEmpty() && !qqty.isEmpty()) {
                    addPo(idusr,nop,idcus,tgl,qqty,dskn,idprod,jml,ket,tllbuy);
                }else {
                   Toast.makeText(getApplicationContext(),
                           "Data * Tidak boleh kosong", Toast.LENGTH_LONG)
                            .show();
               }

            }
        });

       // addPo(id,nopo.getText());


    }

    //fungsi untuk get nomor po
    private  void loadNopo(){
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL3, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String nop = response.getString("nopo");

                    nopo.setText(nop);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    // fungsi untuk membuat  po
    private void addPo(final String id,final String nopo,final  String cus,final String tglpo , final String qty, final String diskon,
        final  String produk,final  String harga,final  String notice, final String ttlbayar
    ){
        Log.d("iduser ",id);
        GsonBuilder gsonBuilder =  new GsonBuilder();
        // inisialisasi obj gson
        gson = gsonBuilder.create();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST,
                URL4,new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {



                            //                           JSONObject jObj = new JSONObject(response);
                            // response
                            Log.d("Response", response);

                            JSONObject jObj = null;
                            try {
                                jObj = new JSONObject(response);

                                int st =    jObj.getInt("status");
                                if(st == 1){
                                    Toast.makeText(getApplicationContext(), "Sukses Simpan data!", Toast.LENGTH_LONG).show();

                                    // Launch login activity
                                    Intent intent = new Intent(
                                            PoActivity.this,
                                            DashboardActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Gagal  Simpan data!", Toast.LENGTH_LONG).show();

                                }


                          //      CountryName.add(item);





                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userid",id);
                params.put("nopo",nopo);

                params.put("cus",cus);
                params.put("tglpo",tglpo);
                params.put("qty",qty);
                params.put("diskon",diskon);
                params.put("produk",produk);

                params.put("harga",harga);
                params.put("notice",notice);

                params.put("ttlbayar",ttlbayar);

                return params;
            }
        };
        //AppController.getInstance().addToRequestQueue(queue);
        queue.add(postRequest);
    }


    // fungsi untuk memamnnggil webservice
    private void getListcus(final String id,final String role ){
        Log.d("iduser ",id);
        GsonBuilder gsonBuilder =  new GsonBuilder();
        // inisialisasi obj gson
        gson = gsonBuilder.create();
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        try {

 //                           JSONObject jObj = new JSONObject(response);
                            JSONArray jobj = new JSONArray(response);

                            Log.d(TAG, "onResponse: " + response.toString()+jobj.length());
                            for (int i = 0; i < jobj.length(); i++) {

                                JSONObject person = (JSONObject) jobj.get(i);



                                Map<String, Object> item = new HashMap<>();

                                String idcus=person.getString("id");

                                String nmcus=person.getString("Name");

                                item.put("id",idcus);
                                item.put("Name",nmcus);

                                CountryName.add(item);

                            }

                            SimpleAdapter arrayAdapter = new SimpleAdapter(PoActivity.this, CountryName,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    new String[]{"Name"}, new int[]{android.R.id.text1});
                            spinner.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("userid",id);
                params.put("role",role);

                return params;
            }
        };
        //AppController.getInstance().addToRequestQueue(queue);
        queue.add(postRequest);
    }

    private void loadSpinnerData2(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {


                    @Override
                    public void onResponse(JSONArray response) {
                        try{


                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response.get(i);

                                Map<String, Object> item = new HashMap<>();

                                String idprod=person.getString("id");

                                String prod=person.getString("Name")+"#"+person.getString("harga");

                                item.put("id",idprod);
                                item.put("name",prod);

                                ProdName.add(item);

                            }

                            SimpleAdapter arrayAdapter = new SimpleAdapter(PoActivity.this, ProdName,
                                    android.R.layout.simple_spinner_dropdown_item,
                                    new String[]{"name"}, new int[]{android.R.id.text1});
                            spinner2.setAdapter(arrayAdapter);
                        }catch (JSONException e){e.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(policy);
        requestQueue.add(req);

    }


    private  void showDialogg(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /*
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                tglpo.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();

    }




}
