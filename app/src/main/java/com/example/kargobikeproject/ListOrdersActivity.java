package com.example.kargobikeproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.kargobikeproject.Adapter.OrderAdapter;

import java.util.ArrayList;

public class ListOrdersActivity extends AppCompatActivity {

   // ArrayList<Order> orders;
    private ListView maListeV;
    private OrderAdapter orderAdapter;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_orders);

        maListeV = (ListView)findViewById(R.id.ListOrder) ;

        delete = (Button) maListeV.findViewById(R.id.DeleteButtonOrder);


        /*
        orderAdapter = new OrderAdapter(getApplicationContext(), 0);
        orders = new ArrayList<>();

      orders.add(new Order(1,1,1,1,"Chemin des planettes",
              "12.12.2019","13.12.2019",2));
        orders.add(new Order(1,1,1,1,"Chemin des planettes",
                "12.12.2019","13.12.2019",2));
        orders.add(new Order(1,1,1,1,"Chemin des planettes",
                "12.12.2019","13.12.2019",2));

        orders.add(new Order(1,1,1,1,"Chemin des planettes",
                "12.12.2019","13.12.2019",2));
        orders.add(new Order(1,1,1,1,"Chemin des planettes",
                "12.12.2019","13.12.2019",2));
        orders.add(new Order(1,1,1,1,"Chemin des planettes",
                "12.12.2019","13.12.2019",2));

        maListeV.setAdapter(orderAdapter);
        orderAdapter.addAll(orders );
*/


    }
}
