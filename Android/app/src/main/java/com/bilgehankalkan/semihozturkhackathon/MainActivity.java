package com.bilgehankalkan.semihozturkhackathon;

import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bilgehankalkan.semihozturkhackathon.service.ApiClass;
import com.bilgehankalkan.semihozturkhackathon.service.ApiInterface;
import com.bilgehankalkan.semihozturkhackathon.service.models.RequestBody;
import com.bilgehankalkan.semihozturkhackathon.service.models.ResponseBody;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements FilterFragment.OnFilterSelectedListener {

    RecyclerView recyclerViewResults;
    TextView textViewFilter, textViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewResults = findViewById(R.id.recycler_view_main);
        textViewFilter = findViewById(R.id.text_view_main_filter);
        textViewBack = findViewById(R.id.text_view_main_back);

        recyclerViewResults.setLayoutManager(new LinearLayoutManager(this));

        textViewFilter.setOnClickListener(v -> {
            if (textViewFilter.getText().equals(getString(R.string.filter)))
                showFilterFragment();
        });
    }

    private void showFilterFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        FilterFragment filterFragment = new FilterFragment();
        fragmentTransaction.add(R.id.layout_main_fragment_ccntainer, filterFragment);
        fragmentTransaction.commit();
        textViewFilter.setText(R.string.done);
        textViewBack.setVisibility(View.VISIBLE);
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
                            //TODO: Handle response
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Connection Error", Toast.LENGTH_LONG).show();
            }
        });

    }
}
