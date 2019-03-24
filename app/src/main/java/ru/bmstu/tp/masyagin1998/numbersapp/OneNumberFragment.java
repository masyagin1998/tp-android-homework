package ru.bmstu.tp.masyagin1998.numbersapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OneNumberFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_number_one, container, false);

        if (getArguments() == null) return v;

        int color = getArguments().getInt("color", R.color.colorPrimary);
        int num = getArguments().getInt("num", -1);
        TextView textView = v.findViewById(R.id.one_number);
        textView.setTextColor(color);
        textView.setText(Integer.toString(num));

        return v;
    }
}
