package com.bodoo.stockrecordssqlite;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyExpandableAdapter extends BaseExpandableListAdapter implements View.OnLongClickListener{

    private Activity activity;
    private ArrayList<Object> childItems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;
    TableControllerStock mStock = new TableControllerStock(this);

    public MyExpandableAdapter(ArrayList<String> parents, ArrayList<Object> childern) {
        this.parentItems = parents;
        this.childItems = childern;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childItems.get(groupPosition);

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_singleitem, null);
        }

        //textView = (TextView) convertView.findViewById(R.id.textView1);
        //textView.setText(child.get(childPosition));

        // Locate the TextViews in singleitemview.xml
        TextView txttermek = convertView.findViewById(R.id.termek);
        txttermek.setOnLongClickListener(this);
        TextView txthelye = convertView.findViewById(R.id.helye);
        TextView txtdarab = convertView.findViewById(R.id.darab);
        TextView txtmindarab = convertView.findViewById(R.id.minmennyiseg);
        TextView txtszavatossag = convertView.findViewById(R.id.szavatossag);
        TextView txtertekeles = convertView.findViewById(R.id.ertekeles);

        // Set results to the TextViews
        txttermek.setText(termek);
        txthelye.setText(helye);
        txtdarab.setText(darab);
        txtmindarab.setText(minDarab);
        txtszavatossag.setText(szavatossag);
        txtertekeles.setText(ertekeles);

        convertView.setOnClickListener(new OnClickListener() {

            public boolean onLongClick(View arg0) {
                mStock.delete(id);
                this.finish();
                return false;
            }
        });

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_item, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childItems.get(groupPosition)).size();
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
        return false;
    }

}