package net.esindo.crmapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.esindo.crmapp.Model.Laporan;
import net.esindo.crmapp.Model.Penjualan;
import net.esindo.crmapp.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 * Created by riyan on 05/12/2018.
 */

public class AdapterLaporan extends ArrayAdapter<Laporan> {
    public AdapterLaporan(@NonNull Context context, List<Laporan> list_laporan) {
        super(context,0,list_laporan);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Laporan obj =  getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_row_laporan,parent,false);
        }


        TextView pt = (TextView) convertView.findViewById(R.id.pt);
        TextView nopo = (TextView) convertView.findViewById(R.id.nopo);
        TextView produk = (TextView) convertView.findViewById(R.id.produk);
        TextView total = (TextView) convertView.findViewById(R.id.total);
        TextView harga = (TextView) convertView.findViewById(R.id.harga);
        TextView qty = (TextView) convertView.findViewById(R.id.qty);
        TextView tgl = (TextView) convertView.findViewById(R.id.tgl);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);


        total.setText(kursIndonesia.format((Integer.parseInt(obj.getTotal()))));
        pt.setText(obj.getPt());
        nopo.setText(obj.getNopo());
        produk.setText(obj.getProduk());
        harga.setText(kursIndonesia.format((Integer.parseInt(obj.getHarga()))));
        qty.setText(obj.getQty());
        tgl.setText(obj.getTgl());
        return convertView;

    }
}
