package com.istiak.retrofitcrudcontact;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.istiak.retrofitcrudcontact.model.Contacts;
import com.istiak.retrofitcrudcontact.remote.ApiClient;
import com.istiak.retrofitcrudcontact.remote.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private List<Contacts> contactsList;
    private ApiInterface apiInterface;

    private ProgressBar progressBar;
    private int splashTimeOut = 30; // Time in mili second
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);



        fetchData();

    }

    public void runtimeData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchData();
            }
        },splashTimeOut);
    }

    public void fetchData(){
        Call<List<Contacts>> call = apiInterface.getContacts();
        call.enqueue(new Callback<List<Contacts>>() {
            @Override
            public void onResponse(Call<List<Contacts>> call, Response<List<Contacts>> response) {
                progressBar.setVisibility(View.GONE);
                contactsList = response.body();
                Log.d("RESPONSE", String.valueOf(contactsList));
                adapter = new Adapter(MainActivity.this, contactsList);
                recyclerView.setAdapter(adapter);

                runtimeData();

                adapter.notifyDataSetChanged();//for search
            }

            @Override
            public void onFailure(Call<List<Contacts>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error : "+ t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);

//        MenuItem searchItem = menu.findItem(R.id.action_search);
//
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setQueryHint("Search People");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                fetchData(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                fetchData(newText);
//                return false;
//            }
//        });
//        searchView.setIconified(false);
//        return true;
//    }


}
