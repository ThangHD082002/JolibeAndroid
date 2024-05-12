package com.example.project1301.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project1301.ActivitySignIn;
import com.example.project1301.R;

public class FragmentThree extends Fragment implements View.OnClickListener{

    private Button btFinish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_start_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btFinish = view.findViewById(R.id.btSignin);
        btFinish.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        if(view == btFinish){
            Intent intent = new Intent(getActivity(), ActivitySignIn.class);
            startActivity(intent);
        }
    }
}
