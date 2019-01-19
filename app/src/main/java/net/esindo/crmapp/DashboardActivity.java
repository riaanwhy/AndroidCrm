package net.esindo.crmapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.esindo.crmapp.Adapter.ContactsAdapter;
import net.esindo.crmapp.Dbconfig.SQLiteHandler;
import net.esindo.crmapp.Dbconfig.SessionManager;
import net.esindo.crmapp.Model.Contact;
import net.esindo.crmapp.Model.MyDividerItemDecoration;
import net.esindo.crmapp.Utils.Server;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {

    private Button btnpo;
    private Button btncus;
    private Button btnlap;
    private Button btnmbr;
    private Button btneven;
    private Button btnpnj;


    private SQLiteHandler db;
    private SessionManager session;

    private TextView nama;
    private TextView jbtn;

    private static final String TAG = DashboardActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    private List<Contact> contactList;
    private ContactsAdapter mAdapter;
    private SearchView searchView;

//    private static final String URL = "http://192.168.43.72:8082/restapi/web/student/topmarket";//"https://api.androidhive.info/json/contacts.json";

    private static String URL    = Server.Url2 + "topmarket";
    private ActionBarDrawerToggle mDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btnpo = findViewById(R.id.btnpo);
        btncus = findViewById(R.id.btncus);
        btnlap = findViewById(R.id.btnlap);
        btnmbr = findViewById(R.id.btnmbr);
        btneven = findViewById(R.id.btneven);
        btnpnj = findViewById(R.id.btnpnj);
        nama  = findViewById(R.id.nama);
        jbtn = findViewById(R.id.jbtn);
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");
        String id = user.get("id");
        String role = user.get("role");
        //username.setText("USER : "+name);

        Log.d("da","username "+name);
        nama.setText(name);
        if (role.equals("2")){
            role = "Marketing";
        }else {
            role = "Admin";
        }
        jbtn.setText(role);


        recyclerView = findViewById(R.id.recycler_view);
        contactList = new ArrayList<>();
        mAdapter = new ContactsAdapter(this, contactList, this);

        // white background notification bar
        whiteNotificationBar(recyclerView);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(mAdapter);

        fetchContacts();

        btncus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent li = new Intent(getApplicationContext(),CusActivity.class);
                startActivity(li);
                finish();
            }
        });

        btnpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getApplicationContext(),PoActivity.class);
                startActivity(ii);
                finish();
            }
        });

        btnlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent li = new Intent(getApplicationContext(),LaporanActivity.class);
                startActivity(li);
                finish();
            }
        });
        btnmbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent li = new Intent(getApplicationContext(),MembersActivity.class);
                startActivity(li);
                finish();
            }
        });
        btneven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(getApplicationContext(),EventActivity.class);
                startActivity(ii);
                finish();
            }
        });
        btnpnj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent li = new Intent(getApplicationContext(),PenjualansActivity.class);
                startActivity(li);
                finish();
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title

        // Handle action bar actions click
        switch (item.getItemId()) {

            case R.id.action_logout:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }



    private void fetchContacts() {
        JsonArrayRequest request = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the contacts! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        List<Contact> items = new Gson().fromJson(response.toString(), new TypeToken<List<Contact>>() {
                        }.getType());

                        // adding contacts to contacts list
                        contactList.clear();
                        contactList.addAll(items);

                        Log.d("disini ","yaj "+items.get(1).getName());

                        // refreshing recycler view
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }


    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getWindow().setStatusBarColor(Color.WHITE);
        }
    }
    @Override
    public void onContactSelected(Contact contact) {

    }
}
