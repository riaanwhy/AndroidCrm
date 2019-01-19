package net.esindo.crmapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import net.esindo.crmapp.Model.Laporan;
import net.esindo.crmapp.Model.Member;
import net.esindo.crmapp.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by riyan on 11/12/2018.
 */

public class LaporanAdapter  extends RecyclerView.Adapter<LaporanAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Laporan> lapList;
    private List<Laporan> lapListFiltered;
    private LaporanAdapterListener listener;


    public LaporanAdapter(Context context, List<Laporan> lapList,LaporanAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.lapList = lapList;
        this.lapListFiltered = lapList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pt ,nopo,produk,total,harga,tgl,qty;

        public MyViewHolder(View view) {
            super(view);

            pt =  view.findViewById(R.id.pt);
            nopo =  view.findViewById(R.id.nopo);
            produk =  view.findViewById(R.id.produk);
            total =  view.findViewById(R.id.total);
            harga = view.findViewById(R.id.harga);
            qty =  view.findViewById(R.id.qty);
            tgl =  view.findViewById(R.id.tgl);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onCusSelected(lapListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }


    @Override
    public LaporanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_laporan, parent, false);

        return new LaporanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Laporan contact = lapListFiltered.get(position);
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);


        holder.pt.setText(contact.getPt());
        holder.nopo.setText(contact.getNopo());
        holder.produk.setText(contact.getProduk());
        holder.harga.setText(kursIndonesia.format(Integer.parseInt(contact.getHarga())));
        holder.tgl.setText(contact.getTgl());
        holder.qty.setText(contact.getQty());
        holder.total.setText(kursIndonesia.format(Integer.parseInt(contact.getTotal())));
    }

    @Override
    public int getItemCount() {
        return lapListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    lapListFiltered = lapList;
                } else {
                    List<Laporan> filteredList = new ArrayList<>();
                    for (Laporan row : lapList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPt().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    lapListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = lapListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                lapListFiltered = (ArrayList<Laporan>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface LaporanAdapterListener {
        void onCusSelected(Laporan lap);
    }

}
