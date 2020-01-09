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
    Button buttonAddProduct;
    Button transportOrder;
    Button modifyProfil;
    Button manageTypes;
    Button btn_authorizedUser;
    Button button_DisplayProductList;
    Button getSignature, takePicture;

    FirebaseAuth mAuth;
    private com.group3.kargobikeproject.ui.home.HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //Check the Bike if it is the first time this day to login
        checkBike();
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
    private void checkBike(){
        //get authentication
        mAuth = FirebaseAuth.getInstance();
        //Repository for the BikeService
        BikeServiceViewModel.Factory factory = new BikeServiceViewModel.Factory(getActivity().getApplication(),"0");
        BikeServiceViewModel viewModelService = ViewModelProviders.of(this, factory).get(BikeServiceViewModel.class);
        //Check bike alert every day
        Calendar calendar = Calendar.getInstance();
        //get current day
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferences settings = this.getActivity().getSharedPreferences("prefs",0);
        //get last day safed or default 0
        int lastDay = settings.getInt("day",0);

        // when the last day is not today, set today and show alert
        if (lastDay != currentDay){
            //alert to check the bike
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setCancelable(false)
                    .setTitle("Confirmation")
                    .setMessage("When you click \"checked\", you confirme that you check your bike " +
                            "and the bike is ready for use and nothing is defective.")
                    .setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user == null){
                                        builder.show();
                                    } else {
                                        // add line in db with user and date
                                        Date date = new Date();
                                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                        SimpleDateFormat formatterTime = new SimpleDateFormat("dd-MM-yyyy");
                                        String thisDay = formatter.format(date);
                                        String time = formatterTime.format(date);
                                        BikeService service = new BikeService(thisDay,user.getUid());
                                        viewModelService.createBikeService(service,time, new OnAsyncEventListener() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d("MainActivityBikeCheck", "createService: success");
                                                //the user have checked the bike for that day
                                                SharedPreferences.Editor editor = settings.edit();
                                                editor.putInt("day",currentDay);
                                                editor.commit();
                                            }
                                            @Override
                                            public void onFailure(Exception e) {
                                                Log.d("MainActivityBikeCheck", "createService: failure", e);
                                            }
                                        });

                                    }
                                }
                            });
            builder.show();
        }

    }

}
