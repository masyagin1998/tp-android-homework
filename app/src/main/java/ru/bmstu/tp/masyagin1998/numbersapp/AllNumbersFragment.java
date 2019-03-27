package ru.bmstu.tp.masyagin1998.numbersapp;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class AllNumbersFragment extends Fragment {
    private static final int PORTRAIT_COLUMNS = 3;
    private static final int LANDSCAPE_COLUMNS = 4;

    private static final int START_MIN_NUMBER = 1;
    private static final int START_MAX_NUMBER = 100;

    private int current_max_number;

    private NumbersAdapter numbersAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setRetainInstance(true);

        if (savedInstanceState != null) {
            current_max_number = savedInstanceState.getInt("currMaxNum", START_MAX_NUMBER);
        } else {
            current_max_number = START_MAX_NUMBER;
        }

        LinkedList<Integer> numbers = new LinkedList<>();
        fillList(numbers);
        numbersAdapter = new NumbersAdapter(numbers);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_numbers_all, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recycler_list);

        int columns = (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) ? PORTRAIT_COLUMNS : LANDSCAPE_COLUMNS;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), columns);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(numbersAdapter);

        v.findViewById(R.id.add_number_button).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        numbersAdapter.addNumber();
                    }
                });
        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currMaxNum", current_max_number);
    }


    private void fillList(List<Integer> numbers) {
        for (int i = START_MIN_NUMBER; i <= current_max_number; i++) numbers.add(i);
    }

    class NumbersViewHolder extends RecyclerView.ViewHolder {
        private TextView numberTextView;

        NumbersViewHolder(View itemView) {
            super(itemView);
            numberTextView = itemView.findViewById(R.id.text);
            numberTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() == null) return;
                    Bundle bundle = new Bundle();
                    bundle.putInt("num", Integer.parseInt(((TextView) v).getText().toString()));
                    bundle.putInt("color", ((TextView) v).getCurrentTextColor());
                    Fragment frag = new OneNumberFragment();
                    frag.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, frag).addToBackStack(null).commit();
                }
            });
        }

        public TextView getNumberTextView() {
            return numberTextView;
        }

        public void setNumberTextView(TextView numberTextView) {
            this.numberTextView = numberTextView;
        }
    }

    class NumbersAdapter extends RecyclerView.Adapter<NumbersViewHolder> {
        private List<Integer> numbers;

        NumbersAdapter(List<Integer> numbers) {
            this.numbers = numbers;
        }

        @Override
        public NumbersViewHolder onCreateViewHolder(ViewGroup container, int i) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View v = inflater.inflate(R.layout.fragment_numbers_all_part, container, false);
            return new NumbersViewHolder(v);
        }

        @Override
        public void onBindViewHolder(NumbersViewHolder numbersViewHolder, int i) {
            numbersViewHolder.getNumberTextView().setText(numbers.get(i).toString());
            if (numbers.get(i) % 2 == 0) {
                numbersViewHolder.getNumberTextView().setTextColor(Color.RED);
            } else {
                numbersViewHolder.getNumberTextView().setTextColor(Color.BLUE);
            }
        }

        @Override
        public int getItemCount() {
            return numbers.size();
        }

        void addNumber() {
            current_max_number++;
            if (numbers.size() > 0) numbers.add(numbers.get(numbers.size() - 1) + 1);
            else numbers.add(START_MIN_NUMBER);
            numbersAdapter.notifyItemInserted(numbers.size() - 1);
        }
    }
}
