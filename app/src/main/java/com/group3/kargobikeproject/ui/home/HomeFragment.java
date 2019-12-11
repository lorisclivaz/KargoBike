package com.group3.kargobikeproject.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.group3.kargobikeproject.R;

import static androidx.lifecycle.ViewModelProviders.of;

public class HomeFragment extends Fragment {

    private com.group3.kargobikeproject.ui.home.HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = of(this).get(com.group3.kargobikeproject.ui.home.HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}
