package net.esindo.crmapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.esindo.crmapp.Model.Member;
import net.esindo.crmapp.Model.Penjualan;
import net.esindo.crmapp.R;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

/**
 * Created by riyan on 04/12/2018.
 */

public class AdapterMember extends ArrayAdapter<Member> {
    public AdapterMember(@NonNull Context context, List<Member> list_member) {
        super(context,0,list_member);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Member obj =  getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.list_row_member,parent,false);
        }


        TextView pt = (TextView) convertView.findViewById(R.id.pt);
        TextView no = (TextView) convertView.findViewById(R.id.no);
        TextView maktif = (TextView) convertView.findViewById(R.id.maktif);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        String aktif = obj.getAwal()+" / "+obj.getAkhir();

        pt.setText(obj.getPt());
        no.setText(obj.getNo());
        maktif.setText(aktif);

        return convertView;
    }
}
