package com.learn.jetpack.navigation;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        Button btn_to_fragment1 = view.findViewById(R.id.btn_to_fragment1);
        Button btn_to_activity2 = view.findViewById(R.id.btn_to_activity2);
        btn_to_fragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("test", "test");
                // 跳转到第二个Fragment
                Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment, bundle);

            }
        });
        btn_to_activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 跳转到第二个Activity
                Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondActivity);
            }
        });
    }
}
