package com.bodoo.stockrecordssqlite;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    private List<Stock> stockdatalist = null;
    private ArrayList<Stock> arraylist;

    public ListViewAdapter(Context context, ArrayList<Stock> stockdatalist) {
        this.context = context;
        this.stockdatalist = stockdatalist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<Stock>();
        this.arraylist.addAll(stockdatalist);
    }

    public class ViewHolder {
        TextView termek;
        TextView helye;
        TextView darab;
    }

    @Override
    public int getCount() {
        return stockdatalist.size();
    }

    @Override
    public Object getItem(int position) {
        return stockdatalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_item, null);
            // Locate the TextViews in listview_item.xml
            holder.termek = (TextView) view.findViewById(R.id.termek);
            holder.helye = (TextView) view.findViewById(R.id.helye);
            holder.darab = (TextView) view.findViewById(R.id.darab);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.termek.setText(stockdatalist.get(position).getTermek());
        holder.helye.setText(stockdatalist.get(position).getHelye());
        holder.darab.setText(stockdatalist.get(position).getDarab());

        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(context, SingleItemView.class);
                intent.putExtra("id",
                        (stockdatalist.get(position).getId()));
                intent.putExtra("termek",
                        (stockdatalist.get(position).getTermek()));
                intent.putExtra("helye",
                        (stockdatalist.get(position).getHelye()));
                intent.putExtra("darab",
                        (stockdatalist.get(position).getDarab()));
                intent.putExtra("minDarab",
                        (stockdatalist.get(position).getMinDarab()));
                intent.putExtra("szavatossag",
                        (stockdatalist.get(position).getSzavIdoFigyel()));
                intent.putExtra("ertekeles",
                        (stockdatalist.get(position).getErtekeles()));
                // Start SingleItemView Class
                context.startActivity(intent);
            }
        });
        return view;
    }

}
