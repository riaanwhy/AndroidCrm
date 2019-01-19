package net.esindo.crmapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import net.esindo.crmapp.Model.Customer;
import net.esindo.crmapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riyan on 06/12/2018.
 */

public class CusAdapter extends  RecyclerView.Adapter<CusAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Customer> cusList;
    private List<Customer> cusListFiltered;
    private CusAdapterListener listener;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView id , nama ,alamat , tlp;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            nama = view.findViewById(R.id.nama);
            alamat = view.findViewById(R.id.alamat);
            tlp = view.findViewById(R.id.tlp);
//            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onCusSelected(cusListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    public CusAdapter(Context context, List<Customer> cusListt, CusAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.cusList = cusListt;
        this.cusListFiltered = cusList;
    }
    @Override
    public CusAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_cus, parent, false);

        return new CusAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CusAdapter.MyViewHolder holder, int position) {
        final Customer contact = cusListFiltered.get(position);
        holder.nama.setText(contact.getNama());
        holder.tlp.setText(contact.getTelpon());
        holder.alamat.setText(contact.getAlamat());


    }

    @Override
    public int getItemCount() {
        return cusListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    cusListFiltered = cusList;
                } else {
                    List<Customer> filteredList = new ArrayList<>();
                    for (Customer row : cusList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getNama().toLowerCase().contains(charString.toLowerCase()) ) {
                            filteredList.add(row);
                        }
                    }

                    cusListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = cusListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                cusListFiltered = (ArrayList<Customer>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface CusAdapterListener {
        void onCusSelected(Customer cus);
    }
}
