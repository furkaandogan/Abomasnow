package com.bilgehankalkan.semihozturkhackathon.ui;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bilgehankalkan.semihozturkhackathon.R;

/**
 * Created by Bilgehan on 11.02.2018.
 */

public class FilterFragment extends Fragment {

    private TextView textViewFrom, textViewTo, textBack, textDone;
    private EditText editTextMinCount, editTextMaxCount;

    private String dateFrom = "2016-01-26", dateTo = "2017-02-02";

    private DatePickerDialog datePickerFrom, datePickerTo;

    OnFilterSelectedListener onFilterSelectedListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onFilterSelectedListener = (OnFilterSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnFilterSelectedListener");
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden)
            textDone.setOnClickListener(((MainActivity) getActivity()).filterOnClickListener);
        else
            textDone.setOnClickListener(controlTextViewListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter, container, false);

        textViewFrom = rootView.findViewById(R.id.text_view_filter_from);
        textViewTo = rootView.findViewById(R.id.text_view_filter_to);
        editTextMinCount = rootView.findViewById(R.id.edit_text_filter_min_count);
        editTextMaxCount = rootView.findViewById(R.id.edit_text_filter_max_count);

        DatePickerDialog.OnDateSetListener dateSetListenerFrom = (datePicker, i, i1, i2) -> dateFrom = formatDate(true, i, i1, i2);
        DatePickerDialog.OnDateSetListener dateSetListenerTo = (datePicker, i, i1, i2) -> dateTo = formatDate(false, i, i1, i2);

        datePickerFrom = new DatePickerDialog(getActivity(), dateSetListenerFrom, 2016, 0, 26);
        datePickerTo = new DatePickerDialog(getActivity(), dateSetListenerTo, 2017, 1, 2);

        textBack = getActivity().findViewById(R.id.text_view_main_back);
        textDone = getActivity().findViewById(R.id.text_view_main_filter);
        textBack.setOnClickListener(controlTextViewListener);
        textDone.setOnClickListener(controlTextViewListener);

        textViewFrom.setText(getString(R.string.select_date_from, dateFrom));
        textViewTo.setText(getString(R.string.select_date_to, dateTo));

        textViewFrom.setOnClickListener(v -> datePickerFrom.show());
        textViewTo.setOnClickListener(v -> datePickerTo.show());

        return rootView;
    }

    private View.OnClickListener controlTextViewListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == textDone.getId())
                onFilterSelectedListener.onFilterSelected(dateFrom, dateTo, Integer.valueOf(editTextMinCount.getText().toString()), Integer.valueOf(editTextMaxCount.getText().toString()));
            textBack.setVisibility(View.GONE);
            textDone.setText(getString(R.string.filter));
            textDone.setOnClickListener(((MainActivity) getActivity()).filterOnClickListener);
            getActivity().findViewById(R.id.recycler_view_main).setVisibility(View.VISIBLE);
            getActivity().getFragmentManager().beginTransaction().hide(FilterFragment.this).commit();
        }
    };

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
