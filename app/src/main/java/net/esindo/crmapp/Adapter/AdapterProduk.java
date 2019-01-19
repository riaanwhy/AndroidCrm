package net.esindo.crmapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.esindo.crmapp.Model.Produk;
import net.esindo.crmapp.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by riyan on 15/12/2018.
 */

public class AdapterProduk extends ArrayAdapter<Produk> {

    public AdapterProduk(@NonNull Context context, List<Produk> list_event) {
        super(context, 0,list_event);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Produk obj =  getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_prod,parent,false);
        }

        TextView txjml = (TextView) convertView.findViewById(R.id.hrg);
        TextView txname = (TextView) convertView.findViewById(R.id.name);
        DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

        DateFormat outputFormat = new SimpleDateFormat("MMM dd ,yyyy");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Date d = fmt.parse(obj.getTgl());
     //   String inputText = obj.getTanggal();
     /*   Date date = null;
        try {
            date = inputFormat.parse(inputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputText = outputFormat.format(date);
*/
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
       // txtgl.setText("Tanggal "+inputText);
        txjml.setText(kursIndonesia.format(Integer.parseInt(obj.getHarga())));
        txname.setText(obj.getName());

        return convertView;
    }
}
