package com.group3.kargobikeproject.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.group3.kargobikeproject.AddAuthorizedUser;
import com.group3.kargobikeproject.AddProductActivity;
import com.group3.kargobikeproject.CameraActivity;
import com.group3.kargobikeproject.ListTypesActivity;
import com.group3.kargobikeproject.MainActivity;
import com.group3.kargobikeproject.ModifyProfilActivity;
import com.group3.kargobikeproject.ProductListActivity;
import com.group3.kargobikeproject.R;
import com.group3.kargobikeproject.SignatureActivity;
import com.group3.kargobikeproject.ui.orders.OrderFragment;

import static androidx.lifecycle.ViewModelProviders.of;

public class HomeFragment extends Fragment {
    Button buttonAddProduct;
    Button transportOrder;
    Button modifyProfil;
    Button manageTypes;
    Button btn_authorizedUser;
    Button button_DisplayProductList;
    Button getSignature, takePicture;
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
        buttonAddProduct=getView().findViewById(R.id.buttonAddProduct);
        modifyProfil=getView().findViewById(R.id.buttonModifyProfil);
        transportOrder=getView().findViewById(R.id.TransportOrder);
        button_DisplayProductList= getView().findViewById(R.id.button_DisplayProductList);
        manageTypes = getView().findViewById(R.id.buttonManageType);
        btn_authorizedUser =getView().findViewById(R.id.button_authorizeUser);
        getSignature = getView().findViewById(R.id.signatureButton);
        takePicture =getView().findViewById(R.id.takePictureButton);

        btn_authorizedUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddAuthorizedUser.class));
            }
        });

        transportOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrderFragment.class));
            }
        });

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddProductActivity.class));
            }
        });
        button_DisplayProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProductListActivity.class));
            }
        });
        modifyProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ModifyProfilActivity.class));
            }
        });

        manageTypes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListTypesActivity.class));
            }
        });
        getSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SignatureActivity.class));
            }
        });
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CameraActivity.class));
            }
        });
        // TODO: Use the ViewModel
    }

}
