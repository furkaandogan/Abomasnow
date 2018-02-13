package com.bilgehankalkan.semihozturkhackathon.ui;

import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgehankalkan.semihozturkhackathon.R;
import com.bilgehankalkan.semihozturkhackathon.service.ApiClass;
import com.bilgehankalkan.semihozturkhackathon.service.ApiInterface;
import com.bilgehankalkan.semihozturkhackathon.service.models.Record;
import com.bilgehankalkan.semihozturkhackathon.service.models.RequestBody;
import com.bilgehankalkan.semihozturkhackathon.service.models.ResponseBody;
import com.bilgehankalkan.semihozturkhackathon.ui.adapter.ResultRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FilterFragment.OnFilterSelectedListener {

    RecyclerView recyclerViewResults;
    TextView textViewFilter, textViewBack;

    FilterFragment filterFragment;

    ResultRecyclerAdapter resultRecyclerAdapter;
    List<Record> listRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewResults = findViewById(R.id.recycler_view_main);
        textViewFilter = findViewById(R.id.text_view_main_filter);
        textViewBack = findViewById(R.id.text_view_main_back);

        textViewFilter.setOnClickListener(filterOnClickListener);

        recyclerViewResults.setLayoutManager(new LinearLayoutManager(this));
        listRecords = new ArrayList<>();
        resultRecyclerAdapter = new ResultRecyclerAdapter(getBaseContext(), listRecords);
        recyclerViewResults.setAdapter(resultRecyclerAdapter);

        RequestBody requestBodyBase = new RequestBody("2016-01-26", "2017-02-02", 2700, 3000);
        getResults(requestBodyBase);
    }

    private void showFilterFragment() {
        if (filterFragment == null)
            filterFragment = new FilterFragment();
        if (filterFragment.isHidden())
            getFragmentManager().beginTransaction().show(filterFragment).commit();
        else
            getFragmentManager().beginTransaction().add(R.id.layout_main_fragment_ccntainer, filterFragment).commit();
        textViewFilter.setText(R.string.done);
        textViewBack.setVisibility(View.VISIBLE);
        recyclerViewResults.setVisibility(View.GONE);
    }

    @Override
    public void onFilterSelected(String startDate, String endDate, int minCount, int maxCount) {
        RequestBody requestBody = new RequestBody(startDate, endDate, minCount, maxCount);
        getResults(requestBody);
    }

    private void getResults(RequestBody requestBody) {
        Call<ResponseBody> call = ApiClass.getRetrofit().create(ApiInterface.class).getSearchRecords(requestBody);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (requestBody != null) {
                        if (responseBody.getCode() == 0) {
                            listRecords.clear();
                            listRecords.addAll(responseBody.getRecords());
                            runOnUiThread(() -> resultRecyclerAdapter.notifyDataSetChanged());
                        } else
                            Toast.makeText(getBaseContext(), "Response has error: " + responseBody.getMsg(), Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getBaseContext(), "Empty response", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getBaseContext(), "Bad response", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Connection Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    public View.OnClickListener filterOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (textViewFilter.getText().equals(getString(R.string.filter)))
                showFilterFragment();
        }
    };
}
