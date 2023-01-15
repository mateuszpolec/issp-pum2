package com.example.restaurantapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderUserActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    OrderContainer mOrderContainer;
    Order mCurrentOrder;

    private List<Order.Status> mStatuses;

    private TextView tvDishNames;
    private TextView tvComments;
    private TextView tvAddress;
    private TextView tvTelephoneNumber;
    private TextView tvTotalPrice;
    private TextView tvStatus;
    private TextView tvWayOfServing;
    private TextView tvPickupTime;

    // For user.
    private Button bCancelOrder;

    // For employee.
    private TableRow trStatus;
    private Spinner sSelectStatus;
    private Button bUpdateOrder;
    private int mCurrentSpinnerIndex;

    private DbOrders mDbOrders;

    @Override
    protected void onCreate(Bundle savedInstanceBundle)
    {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.order_user_activity);

        mDbOrders = new DbOrders(this);

        tvDishNames = findViewById(R.id.tvDishNames);
        tvComments = findViewById(R.id.tvComments);
        tvAddress = findViewById(R.id.tvAddress);
        tvTelephoneNumber = findViewById(R.id.tvTelephoneNumber);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvStatus = findViewById(R.id.tvStatus);
        tvWayOfServing = findViewById(R.id.tvWayOfServing);
        tvPickupTime = findViewById(R.id.tvPickupTime);

        UUID OrderUUID = UUID.fromString(getIntent().getStringExtra("OrderUUID"));

        mOrderContainer = mOrderContainer.get(this);
        mCurrentOrder = mOrderContainer.getOrder(OrderUUID);

        tvDishNames.setText("Dania: " + mCurrentOrder.getDishNames());
        tvComments.setText("Komentarze: " + mCurrentOrder.getComments());
        tvAddress.setText("Adres: " + mCurrentOrder.getAddress());
        tvTelephoneNumber.setText("Numer telefonu: " + mCurrentOrder.getTelephone());
        tvTotalPrice.setText("Cena: " + mCurrentOrder.getTotalPrice());
        tvStatus.setText("Status: " + Order.getOrderStatus(mCurrentOrder.getStatus()));
        tvWayOfServing.setText("Sposób podania: " + Order.getOrderWayOfServing(mCurrentOrder.getWayOfServing()));
        tvPickupTime.setText("Godzina odbioru: " + mCurrentOrder.getPickupTime());

        // For user.
        bCancelOrder = findViewById(R.id.bCancelOrder);

        if (mCurrentOrder.getStatus().equals(Order.Status.ORDERED) && !CurrentUser.user.IsEmployee())
        {
            bCancelOrder.setVisibility(View.VISIBLE);
        }

        // For employee.
        trStatus = findViewById(R.id.trStatus);
        sSelectStatus = findViewById(R.id.sSelectStatus);
        bUpdateOrder = findViewById(R.id.bUpdateOrder);

        if (CurrentUser.user.IsEmployee())
        {
            trStatus.setVisibility(View.VISIBLE);
            sSelectStatus.setVisibility(View.VISIBLE);
            bUpdateOrder.setVisibility(View.VISIBLE);

            initializeSpinner();
        }

        if (mCurrentOrder.getWayOfServing().equals(Order.WayOfServing.IN_RESTAURANT))
        {
            tvTelephoneNumber.setVisibility(View.GONE);
            tvPickupTime.setVisibility(View.GONE);
        }

        if (mCurrentOrder.getWayOfServing().equals(Order.WayOfServing.DELIVERY))
        {
            tvPickupTime.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
    {
        mCurrentSpinnerIndex = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // TODO Auto-generated method stub
    }

    public void buttonCancelPressed(View view)
    {
        mDbOrders.deleteOrder(mCurrentOrder);

        finish();
    }

    public void buttonUpdateOrderPressed(View view)
    {
        mCurrentOrder.setStatus(Order.Status.values()[mCurrentSpinnerIndex]);

        Toast.makeText(OrderUserActivity.this, "Zamówienie pomyślnie zaktualizowane!", Toast.LENGTH_LONG).show();

        mDbOrders.updateOrder(mCurrentOrder);
    }

    private void initializeSpinner()
    {
        ArrayList<String> statuses = new ArrayList<String>();

        for (Order.Status status : Order.Status.values())
        {
            statuses.add(Order.getOrderStatus(status));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrderUserActivity.this, android.R.layout.simple_spinner_dropdown_item, statuses);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSelectStatus.setAdapter(adapter);
        sSelectStatus.setOnItemSelectedListener(this);
        
        sSelectStatus.setSelection(mCurrentOrder.getStatus().ordinal());
    }


}
