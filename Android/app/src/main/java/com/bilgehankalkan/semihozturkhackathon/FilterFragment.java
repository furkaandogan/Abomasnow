package com.bilgehankalkan.semihozturkhackathon;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;
/**
 * Created by Bilgehan on 11.02.2018.
 */

public class FilterFragment extends Fragment {

    TextView textViewFrom, textViewTo;
    EditText editTextMinCount, editTextMaxCount;

    private String dateFrom = "2016-01-26", dateTo = "2017-02-02";

    private DatePickerDialog datePickerFrom, datePickerTo;
    private DatePickerDialog.OnDateSetListener dateSetListenerFrom, dateSetListenerTo;

    OnFilterSelectedListener onFilterSelectedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFilterSelectedListener = (OnFilterSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnFilterSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);

        textViewFrom = rootView.findViewById(R.id.text_view_filter_from);
        textViewTo = rootView.findViewById(R.id.text_view_filter_to);
        editTextMinCount = rootView.findViewById(R.id.edit_text_filter_min_count);
        editTextMaxCount = rootView.findViewById(R.id.edit_text_filter_max_count);

        dateSetListenerFrom = (datePicker, i, i1, i2) -> dateFrom = formatDate(true, i, i1, i2);
        dateSetListenerTo = (datePicker, i, i1, i2) -> dateTo = formatDate(false, i, i1, i2);

        datePickerFrom = new DatePickerDialog(getActivity(), dateSetListenerFrom, 2016, 0, 26);
        datePickerTo = new DatePickerDialog(getActivity(), dateSetListenerTo, 2017, 1, 2);

        textViewFrom.setText(getString(R.string.select_date_from, dateFrom));
        textViewTo.setText(getString(R.string.select_date_to, dateTo));

        textViewFrom.setOnClickListener(v -> datePickerFrom.show());
        textViewTo.setOnClickListener(v -> datePickerTo.show());

        TextView textBack = getActivity().findViewById(R.id.text_view_main_back);
        TextView textDone = getActivity().findViewById(R.id.text_view_main_filter);

        textBack.setOnClickListener(v -> {
            textBack.setVisibility(View.GONE);
            onFilterSelectedListener.onFilterSelected(dateFrom, dateTo, Integer.valueOf(editTextMinCount.getText().toString()), Integer.valueOf(editTextMaxCount.getText().toString()));
            FilterFragment.this.onDestroy();
        });
        textDone.setOnClickListener(v ->{
            textDone.setText(getString(R.string.filter));
            onFilterSelectedListener.onFilterSelected(dateFrom, dateTo, Integer.valueOf(editTextMinCount.getText().toString()), Integer.valueOf(editTextMaxCount.getText().toString()));
            FilterFragment.this.onDestroy();
        });

        return rootView;
    }

    public interface OnFilterSelectedListener {
        void onFilterSelected(String startDate, String endDate, int minCount, int maxCount);
    }

    private String formatDate(boolean isFrom, int year, int month, int day) {
        String formattedDate = year + "-" + month + 1 + "-" + day;
        if (isFrom)
            textViewFrom.setText(getString(R.string.select_date_from, formattedDate));
        else
            textViewTo.setText(getString(R.string.select_date_to, formattedDate));
        return formattedDate;
    }
}
