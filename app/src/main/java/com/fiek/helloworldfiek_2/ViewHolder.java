package com.fiek.helloworldfiek_2;

import android.view.View;
import android.widget.TextView;

public class ViewHolder {
    private TextView tvNameRow;

    public TextView getTvNameRow() {
        return tvNameRow;
    }

    public ViewHolder(View v)
    {
        tvNameRow = v.findViewById(R.id.tvNameRow);
    }
}
