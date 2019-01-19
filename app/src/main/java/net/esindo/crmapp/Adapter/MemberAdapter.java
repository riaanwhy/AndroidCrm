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

import net.esindo.crmapp.Model.Member;
import net.esindo.crmapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by riyan on 10/12/2018.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Member> mbrList;
    private List<Member> mbrListFiltered;
    private MemberAdapterListener listener;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView pt ,no,  maktif ;

        public MyViewHolder(View view) {
            super(view);
            pt = view.findViewById(R.id.pt);
            no = view.findViewById(R.id.no);
            maktif = view.findViewById(R.id.maktif);
//            thumbnail = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onCusSelected(mbrListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public MemberAdapter(Context context, List<Member> cusList, MemberAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.mbrList = cusList;
        this.mbrListFiltered = mbrList;
    }
    @Override
    public MemberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_member, parent, false);

        return new MemberAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Member contact = mbrListFiltered.get(position);
        holder.pt.setText(contact.getPt());
        holder.no.setText(contact.getNo());
        String aktif = contact.getAwal()+" / "+contact.getAkhir();
        holder.maktif.setText(aktif);
    }

    @Override
    public int getItemCount() {
        return mbrListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mbrListFiltered = mbrList;
                } else {
                    List<Member> filteredList = new ArrayList<>();
                    for (Member row : mbrList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getPt().toLowerCase().contains(charString.toLowerCase()) || row.getAwal().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mbrListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mbrListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mbrListFiltered = (ArrayList<Member>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }



    public interface MemberAdapterListener {
        void onCusSelected(Member mbr);
    }

}
