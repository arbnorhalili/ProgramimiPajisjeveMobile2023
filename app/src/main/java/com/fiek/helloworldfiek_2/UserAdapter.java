package com.fiek.helloworldfiek_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseAdapter {
    List<User> userList = new ArrayList<>();
    Context c;

    public UserAdapter(Context _c)
    {
        c = _c;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {
        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return userList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null)
        {
            view = LayoutInflater.from(c).inflate(R.layout.row_layout,
                    viewGroup,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.getTvNameRow().setText(userList.get(i).getName());

        if(i%2==0)
            viewHolder.getTvNameRow().setTextColor(c.getColor(android.R.color.holo_red_dark));
        else
            viewHolder.getTvNameRow().setTextColor(c.getColor(android.R.color.black));
        return view;
    }
}
