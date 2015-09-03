package ru.ID20.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ru.ID20.app.R;
import ru.ID20.app.constants.enums.DrawerItems;

/**
 * Created by s.shevchenko on 26.03.2015.
 */
public class DrawerItemsAdapter extends BaseAdapter {


    private DrawerItems[] itemsList;
    private LayoutInflater inflater;
    private Context context;

    public DrawerItemsAdapter(Context context) {
        this.itemsList = DrawerItems.values();
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemsList.length;
    }

    @Override
    public DrawerItems getItem(int position) {
        return itemsList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DrawerItems item = itemsList[position];
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.drawer_item, parent, false);
            holder.itemName = (TextView) convertView.findViewById(R.id.tvItemName);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.itemName.setText(context.getString(item.getItemNameStringId()));
        return convertView;
    }


    private class Holder {
        TextView itemName;
    }
}
