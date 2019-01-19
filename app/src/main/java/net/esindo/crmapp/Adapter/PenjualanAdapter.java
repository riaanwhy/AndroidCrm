package net.esindo.crmapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import net.esindo.crmapp.Model.Penjualan;
import net.esindo.crmapp.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by riyan on 10/12/2018.
 */

public class PenjualanAdapter extends RecyclerView.Adapter<PenjualanAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Penjualan> pnjList;
    private List<Penjualan> pnjListFiltered;
    private PenjualanAdapterListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  pt , nopo ,produk , total;

        public MyViewHolder(View view) {
            super(view);
            pt = view.findViewById(R.id.pt);
            nopo = view.findViewById(R.id.nopo);
            produk = view.findViewById(R.id.produk);

            total = view.findViewById(R.id.total);
//            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onCusSelected(pnjListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public PenjualanAdapter(Context context, List<Penjualan> cusList, PenjualanAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.pnjList = cusList;
        this.pnjListFiltered = pnjList;
    }
    @Override
    public PenjualanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_pnj, parent, false);

        return new PenjualanAdapter.MyViewHolder(itemView);
    }

  

    @Override
    public void onBindViewHolder(PenjualanAdapter.MyViewHolder holder, int position) {
        final Penjualan contact = pnjListFiltered.get(position);
        holder.pt.setText(contact.getPt());
        holder.nopo.setText(contact.getNopo());
        holder.produk.setText(contact.getProduk());

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        //txjml.setText(kursIndonesia.format(Integer.parseInt(contact.getPhone())));
        holder.total.setText(kursIndonesia.format(Integer.parseInt(contact.getTotal())));


    }

    @Override
    public int getItemCount() {
        return pnjListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    pnjListFiltered = pnjList;
                } else {
                    List<Penjualan> filteredList = new ArrayList<>();
                    for (Penjualan row : pnjList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPt().toLowerCase().contains(charString.toLowerCase()) || row.getProduk().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    pnjListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = pnjListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pnjListFiltered = (ArrayList<Penjualan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface PenjualanAdapterListener {
        void onCusSelected(Penjualan pnj);
    }
}
