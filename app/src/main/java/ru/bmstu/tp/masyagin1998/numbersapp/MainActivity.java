package ru.bmstu.tp.masyagin1998.numbersapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (frag != null) return;
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new AllNumbersFragment()).commit();
    }
}
