package com.fiek.helloworldfiek_2;

import android.view.View;
import android.widget.TextView;

public class UniversityViewHolder {
    private TextView tvUniversityName, tvUniversityMainDomain;

    public UniversityViewHolder(View view)
    {
        tvUniversityName = view.findViewById(R.id.tvUniversityName);
        tvUniversityMainDomain = view.findViewById(R.id.tvUniversityMainDomain);
    }

    public TextView getTvUniversityName() {
        return tvUniversityName;
    }

    public TextView getTvUniversityMainDomain() {
        return tvUniversityMainDomain;
    }
}
