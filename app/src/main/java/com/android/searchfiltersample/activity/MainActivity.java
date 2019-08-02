package com.android.searchfiltersample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.searchfiltersample.R;
import com.android.searchfiltersample.adapter.BankDetailsAdapter;
import com.android.searchfiltersample.model.BankDetails;
import com.android.searchfiltersample.rest.ApiClient;
import com.android.searchfiltersample.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String DEFAULT_CITY = "BANGALORE";

    private ApiInterface apiService;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private BankDetailsAdapter mAdapter;
    private List<BankDetails> banks;
    private ProgressBar mProgressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressbar = findViewById(R.id.progressBar_cyclic);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> cities = new ArrayList<String>();
        cities.add("BANGALORE");
        cities.add("MUMBAI");
        cities.add("CHENNAI");

        searchView = findViewById(R.id.search);
       searchView.setActivated(true);
       searchView.setQueryHint("Type your keyword here");
       searchView.onActionViewExpanded();
       searchView.setIconified(false);
       searchView.clearFocus();

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                                                        android.R.layout.simple_spinner_item, cities);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        banks = new ArrayList<>();
        mAdapter = new BankDetailsAdapter(banks,
                R.layout.list_item_bank_details,
                getApplicationContext());
        recyclerView.setAdapter(mAdapter);

        apiService =
                ApiClient.getClient().create(ApiInterface.class);
        mProgressbar.setVisibility(View.VISIBLE);
        fetchBanksInCity(DEFAULT_CITY);
    }

    private void fetchBanksInCity(String city){
        Call<List<BankDetails>> call = apiService.getBankDetails(city);
        call.enqueue(new Callback<List<BankDetails>>() {
            @Override
            public void onResponse(Call<List<BankDetails>>call, Response<List<BankDetails>> response) {
                mProgressbar.setVisibility(View.INVISIBLE);
                List<BankDetails> banksList = response.body();
                Log.d(TAG, "Number of banks received: " + banksList.size());
                banks.clear();
                banks.addAll(banksList);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<BankDetails>>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        mProgressbar.setVisibility(View.VISIBLE);
        banks.clear();
        mAdapter.notifyDataSetChanged();
        fetchBanksInCity(item);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        mProgressbar.setVisibility(View.VISIBLE);
        banks.clear();
        mAdapter.notifyDataSetChanged();
        fetchBanksInCity(DEFAULT_CITY);
    }
}
