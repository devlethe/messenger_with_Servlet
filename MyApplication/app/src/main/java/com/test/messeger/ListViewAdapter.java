package com.test.messeger;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<UserItemView> listViewItemList = new ArrayList<UserItemView>() ;

    public ListViewAdapter() {

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.userdatalayout, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView name = (TextView) convertView.findViewById(R.id.usernames) ;
        TextView id = (TextView) convertView.findViewById(R.id.userids) ;
        TextView nickname = (TextView) convertView.findViewById(R.id.usernicknames) ;
        TextView status = (TextView) convertView.findViewById(R.id.userstatuss) ;

        UserItemView listViewItem = listViewItemList.get(position);

        id.setText(listViewItem.getId());
        name.setText(listViewItem.getName());
        nickname.setText(listViewItem.getNickname());
        status.setText(listViewItem.getStatus());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void addItem(Drawable icon) {
        UserItemView item = new UserItemView();
        item.setIcon(icon);
        listViewItemList.add(item);
    }


    public void addItem(String username, String usernickname, String userstatus, String userid) {
        UserItemView item = new UserItemView(userid, username, usernickname, userstatus);
        listViewItemList.add(item);
    }
}