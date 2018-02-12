package com.bilgehankalkan.semihozturkhackathon.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bilgehankalkan.semihozturkhackathon.R;
import com.bilgehankalkan.semihozturkhackathon.service.models.Record;

import java.util.List;

/**
 * Created by Bilgehan on 11.02.2018.
 */

public class ResultRecyclerAdapter extends RecyclerView.Adapter<ResultRecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;

    private List<Record> listRecords;

    public ResultRecyclerAdapter(Context context, List<Record> listRecords) {
        this.listRecords = listRecords;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ResultRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_result, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Record record = listRecords.get(position);

        if (position%2 == 0)
            holder.layoutMain.setBackgroundResource(R.color.colorPrimary);
        else
            holder.layoutMain.setBackgroundResource(R.color.colorPrimaryDark);

        holder.textId.setText(record.getId().getId());
        holder.textDate.setText(record.getId().getCreatedAt());
        holder.textCount.setText(record.getTotalCount() + "");
    }

    @Override
    public int getItemCount() {
        return listRecords.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutMain;
        TextView textId, textDate, textCount;

        protected ViewHolder(View itemView) {
            super(itemView);
            layoutMain = itemView.findViewById(R.id.layout_result_item);
            textId = itemView.findViewById(R.id.text_view_result_item_id);
            textDate = itemView.findViewById(R.id.text_view_result_item_date);
            textCount = itemView.findViewById(R.id.text_view_result_item_count);
        }
    }
}
