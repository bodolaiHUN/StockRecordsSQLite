package com.bodoo.stockrecordssqlite;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<Stock> parentItems;
    private LayoutInflater inflater;

    public MyExpandableAdapter(ArrayList<Stock> parents) {             //parents: termekek,
        this.parentItems = parents;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View view, ViewGroup parent) {

        ViewHolderChildern holder = new ViewHolderChildern();
        holder.groupPosition = groupPosition;
        holder.childPosition = childPosition;

        if (view == null) {
            view = inflater.inflate(R.layout.listview_singleitem, parent, false);
        }

        holder.termek = (TextView) view.findViewById(R.id.termek);
        holder.helye = (TextView) view.findViewById(R.id.helye);
        holder.minmennyiseg = (TextView) view.findViewById(R.id.minmennyiseg);
        holder.darab = (TextView) view.findViewById(R.id.darab);
        holder.ertekeles = (TextView) view.findViewById(R.id.ertekeles);
        holder.szavIdo = (TextView) view.findViewById(R.id.szavatossag);
        holder.barcode = (TextView) view.findViewById(R.id.barcode);
        view.setTag(holder);

        holder.termek.setText(parentItems.get(groupPosition).getChildern().getTermek());
        holder.helye.setText(parentItems.get(groupPosition).getChildern().getHelye());
        holder.minmennyiseg.setText(parentItems.get(groupPosition).getChildern().getMinDarab());
        holder.darab.setText(parentItems.get(groupPosition).getChildern().getDarab());
        holder.ertekeles.setText(parentItems.get(groupPosition).getChildern().getErtekeles());
        holder.szavIdo.setText(parentItems.get(groupPosition).getChildern().getSzavIdo());
        holder.barcode.setText(parentItems.get(groupPosition).getChildern().getBarcode());

        //return the entire view
        return view;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {

        ViewHolderGroup holder = new ViewHolderGroup();
        holder.groupPosition = groupPosition;

        if (view == null) {
            view = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }

        holder.termek = (TextView) view.findViewById(R.id.termek);
        holder.minmennyiseg = (TextView) view.findViewById(R.id.minmennyiseg);
        holder.darab = (TextView) view.findViewById(R.id.darab);
        view.setTag(holder);

        holder.termek.setText(parentItems.get(groupPosition).getTermek());
        holder.minmennyiseg.setText(parentItems.get(groupPosition).getMinDarab());
        holder.darab.setText(parentItems.get(groupPosition).getDarab());

        //return the entire view
        return view;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        long id = parentItems.get(groupPosition).getChildern().getId();
        return id;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return parentItems.get(groupPosition).getCount();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    protected class ViewHolderGroup {
        protected int childPosition;
        protected int groupPosition;
        TextView termek;
        TextView darab;
        TextView minmennyiseg;
    }

    protected class ViewHolderChildern {
        protected int childPosition;
        protected int groupPosition;
        TextView termek;
        TextView helye;
        TextView darab;
        TextView minmennyiseg;
        TextView ertekeles;
        TextView barcode;
        TextView szavIdo;
    }

}