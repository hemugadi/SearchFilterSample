package com.android.searchfiltersample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.searchfiltersample.R;
import com.android.searchfiltersample.model.BankDetails;

import java.util.ArrayList;
import java.util.List;

public class BankDetailsAdapter extends RecyclerView.Adapter<BankDetailsAdapter.BankDetailsViewHolder>
                                implements Filterable {

    private List<BankDetails> banks;
    private List<BankDetails> banksFiltered;
    private int rowLayout;
    private Context context;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    banksFiltered = banks;
                } else {
                    List<BankDetails> filteredList = new ArrayList<>();
                    for (BankDetails row : banks) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getBankName().toLowerCase().contains(charString.toLowerCase())
                                || row.getBranch().toLowerCase().contains(charString.toLowerCase())
                                || row.getIfsc().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    banksFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = banksFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                banksFiltered = (ArrayList<BankDetails>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public static class BankDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView bankName;
        TextView branch;
        TextView ifsc;

        public BankDetailsViewHolder(View v) {
            super(v);
            bankName = (TextView) v.findViewById(R.id.bank_name);
            branch = (TextView) v.findViewById(R.id.branch);
            ifsc = (TextView) v.findViewById(R.id.ifsc);
        }
    }

    public BankDetailsAdapter(List<BankDetails> banks, int rowLayout, Context context) {
        this.banks = banks;
        this.banksFiltered = banks;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public BankDetailsAdapter.BankDetailsViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new BankDetailsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BankDetailsViewHolder holder, final int position) {
        holder.bankName.setText(context.getResources().getString(R.string.bank_name,
                banksFiltered.get(position).getBankName()));
        holder.branch.setText(context.getResources().getString(R.string.branch,
                banksFiltered.get(position).getBranch()));
        holder.ifsc.setText(context.getResources().getString(R.string.ifsc,
                banksFiltered.get(position).getIfsc()));
    }

    @Override
    public int getItemCount() {
        return banksFiltered.size();
    }
}