package net.esindo.crmapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.esindo.crmapp.Model.Event;
import net.esindo.crmapp.R;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Esindo on 08/02/2018.
 */

public class AdapterEvent extends ArrayAdapter<Event> {

    public AdapterEvent(@NonNull Context context,  List<Event> list_event) {
        super(context, 0,list_event);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event obj =  getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_row,parent,false);
        }

        TextView txtgl = (TextView) convertView.findViewById(R.id.tgl);
        TextView txjml = (TextView) convertView.findViewById(R.id.jml);
        TextView txnote = (TextView) convertView.findViewById(R.id.note);
        DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);

        DateFormat outputFormat = new SimpleDateFormat("MMM dd ,yyyy");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        // Date d = fmt.parse(obj.getTgl());
        String inputText = obj.getTanggal();
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
        txtgl.setText("Tanggal "+inputText);
        txjml.setText(kursIndonesia.format(Integer.parseInt(obj.getHarga())));
        txnote.setText(obj.getNama());

        return convertView;
    }
}
