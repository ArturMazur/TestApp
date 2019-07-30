package com.example.testapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.testapp.MainActivity.PAGE_COUNT;

public class PageFragment extends Fragment {

    private int pageNumber;

    public static PageFragment newInstance(int pageNum) {
        PageFragment pageFragment = new PageFragment();
        Bundle argument = new Bundle();
        argument.putInt(PAGE_COUNT, pageNum);
        pageFragment.setArguments(argument);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            pageNumber = getArguments().getInt("PAGE_NUMBER");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        getDataFromArguments();
        initUI(view);
        return view;
    }

    private void getDataFromArguments() {
        if (getArguments() != null) {
            pageNumber = getArguments().getInt(PAGE_COUNT);
        }
    }

    private void initUI(View view) {
        TextView numberTextView = view.findViewById(R.id.numberPage);
        numberTextView.setText(String.valueOf(pageNumber));
    }
}
