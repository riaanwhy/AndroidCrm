package net.esindo.crmapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.esindo.crmapp.Model.Customer;
import net.esindo.crmapp.R;

import java.util.List;

/**
 * Created by riyan on 17/08/2018.
 */

public class AdapterCustomer extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Customer> items;

    public AdapterCustomer(Activity activity, List<Customer> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_cus, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView nama = (TextView) convertView.findViewById(R.id.nama);
        TextView alamat = (TextView) convertView.findViewById(R.id.alamat);
        TextView tlp = (TextView) convertView.findViewById(R.id.tlp);

        Customer data = items.get(position);

        id.setText(data.getId());
        nama.setText(data.getNama());
        alamat.setText(data.getAlamat());
        tlp.setText(data.getTelpon());

        return convertView;
    }
}
