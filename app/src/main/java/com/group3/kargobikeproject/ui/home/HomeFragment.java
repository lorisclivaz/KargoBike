package com.group3.kargobikeproject.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group3.kargobikeproject.AddAuthorizedUser;
import com.group3.kargobikeproject.AddProductActivity;
import com.group3.kargobikeproject.CameraActivity;
import com.group3.kargobikeproject.ListTypesActivity;
import com.group3.kargobikeproject.LoginActivity;
import com.group3.kargobikeproject.MainActivity;
import com.group3.kargobikeproject.Model.Entity.BikeService;
import com.group3.kargobikeproject.ModifyProfilActivity;
import com.group3.kargobikeproject.ProductListActivity;
import com.group3.kargobikeproject.R;
import com.group3.kargobikeproject.SignatureActivity;
import com.group3.kargobikeproject.Utils.OnAsyncEventListener;
import com.group3.kargobikeproject.ViewModel.BikeServiceViewModel;
import com.group3.kargobikeproject.ui.orders.OrderFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static androidx.lifecycle.ViewModelProviders.of;

public class HomeFragment extends Fragment {

    FirebaseAuth mAuth;
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
