package com.example.project1301;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.project1301.Adapter.AddressAdapter;
import com.example.project1301.Adapter.RecycleViewPaymentAdapter;
import com.example.project1301.model.Cart;
import com.example.project1301.model.ItemCart;
import com.example.project1301.model.ItemOrder;
import com.example.project1301.model.Order;
import com.example.project1301.model.Payment;
import com.example.project1301.model.Shipment;
import com.example.project1301.model.UserOrder;
import com.example.project1301.spacing.SpacingItemDecoration;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, AddressAdapter.ItemListener, View.OnClickListener {

    private GoogleMap gMap;
    private SearchView searchView;

    private EditText etName, etSdt;

    private Button btnSubmit;
    private RecyclerView recyclerView;
    private Geocoder geocoder;

    private Shipment smp;

    private ItemOrder itemOrder;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    private Shipment smdo;

    private AddressAdapter addressAdapter;
    private List<Address> addressList;

    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler_view);
        etName = findViewById(R.id.etName);
        etSdt = findViewById(R.id.etSdt);
        btnSubmit = findViewById(R.id.btSubmit);
        btnSubmit.setOnClickListener(this);

        Intent intent = getIntent();
        smp = (Shipment) intent.getSerializableExtra("item");
        itemOrder = (ItemOrder) intent.getSerializableExtra("do_item");
        if(itemOrder != null){
            smdo = itemOrder.getShipment();
        }
        if(smp != null) {
            check = true;
            etName.setText(smp.getName() + "");
            etSdt.setText(smp.getPhone() + "");
            searchView.setQuery(smp.getAddress() + "", true);
        } else{
            etName.setText("Nhập tên");
            etSdt.setText("Nhập số điện thoại");
            searchView.setQuery("Nhập địa chỉ", true);
        }

        if(smdo != null) {
            check = false;
            etName.setText(smdo.getName() + "");
            etSdt.setText(smdo.getPhone() + "");
            searchView.setQuery(smdo.getAddress() + "", true);
        } else{
            etName.setText("Nhập tên");
            etSdt.setText("Nhập số điện thoại");
            searchView.setQuery("Nhập địa chỉ", true);
        }



        geocoder = new Geocoder(this, new Locale("vi", "VN")); // Set locale to Vietnamese

        addressList = new ArrayList<>();
        addressAdapter = new AddressAdapter();
        addressAdapter.setAddressList(addressList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(addressAdapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    searchLocation(newText);
                } else {
                    addressList.clear();
                    addressAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.GONE);
                }
                return true;
            }
        });
        addressAdapter.setItemListener(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;
        LatLng defaultLocation = new LatLng(15.9030623, 105.8066925);
        gMap.addMarker(new MarkerOptions().position(defaultLocation).title("Viet Nam"));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15));
    }

    private void searchLocation(String location) {
        try {
            List<Address> addresses = geocoder.getFromLocationName(location, 5);
            if (!addresses.isEmpty()) {
                addressList.clear();
                addressList.addAll(addresses);
                addressAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.GONE);
            }
        } catch (IOException e) {
            e.printStackTrace();
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void showLocationOnMap(Address address) {
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
        gMap.clear(); // Clear previous markers
        gMap.addMarker(new MarkerOptions().position(latLng).title(address.getAddressLine(0)));
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(View view, int position) {
        Address address = (Address) addressAdapter.getAddress(position);
        showLocationOnMap(address);
        searchView.setQuery(address.getAddressLine(0), false);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSubmit){
            if(check == true){
                Shipment s = new Shipment();
                s.setName(etName.getText().toString());
                s.setPhone(etSdt.getText().toString());
                s.setAddress(searchView.getQuery().toString());
                Intent intent = new Intent(this, PaymentActivity.class);
                intent.putExtra("item", s);
                startActivity(intent);
            } else{
                getFireBase();
            }

        }
    }

    public void getFireBase() {
        String uuid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userRef = firebaseDatabase.getReference().child("Registered User").child(uuid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserOrder userOrder = dataSnapshot.getValue(UserOrder.class);
                    if (userOrder != null) {
                        Order order = userOrder.getOrder();
                        if (order != null) {
                            Shipment s = new Shipment();
                            s.setName(etName.getText().toString());
                            s.setPhone(etSdt.getText().toString());
                            s.setAddress(searchView.getQuery().toString());
                            for(ItemOrder i: userOrder.getOrder().getListItemOrder()){
                                if(i.getId().toLowerCase().toString().equals(itemOrder.getId().toLowerCase().toString())){
                                    i.setShipment(s);
                                }
                            }
                            userRef.setValue(userOrder);
                            itemOrder.setShipment(s);
                            Intent intent = new Intent(MapActivity.this, DetailOrderActivity.class);
                            intent.putExtra("io", itemOrder);
                            startActivity(intent);
                            Toast.makeText(MapActivity.this, "sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(MapActivity.this, "sửa thông tin thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
            }
        });
    }
}