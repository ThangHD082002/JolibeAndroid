package com.example.project1301;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1301.Adapter.RecycleViewCartAdapter;
import com.example.project1301.Adapter.RecycleViewOrderAdapter;
import com.example.project1301.Adapter.RecycleViewPaymentAdapter;
import com.example.project1301.fragment.FragmentCart;
import com.example.project1301.fragment.FragmentOrderWaitConfirm;
import com.example.project1301.model.Cart;
import com.example.project1301.model.Food;
import com.example.project1301.model.ItemCart;
import com.example.project1301.model.ItemOrder;
import com.example.project1301.model.Order;
import com.example.project1301.model.Payment;
import com.example.project1301.model.Shipment;
import com.example.project1301.model.UserOrder;
import com.example.project1301.spacing.SpacingItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, RecycleViewPaymentAdapter.ItemListener{

    private RecycleViewPaymentAdapter adapter;
    private RecyclerView recyclerView;
    private TextView totalPrice;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    private List<ItemCart> listItemCart;

    private ItemOrder itemOrder;
    private Button btAddFood, btPayment;

    private TextView enterAddAddress, tvName, tvPhone, tvAddress;

    private Food food;

    private long tong = 0;
    private int sl=1;
    private Shipment s;
    private String pay="";

    private String name="", phone="", addr ="";

    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        totalPrice =findViewById(R.id.totalPrice);
        btAddFood =  findViewById(R.id.btnAddFood);
        btPayment = findViewById(R.id.btnPayment);
        recyclerView = findViewById(R.id.recycleView);
        sp = findViewById(R.id.spinner);
        enterAddAddress = findViewById(R.id.tvEnterAddress);
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);

        Intent intent = getIntent();
         s = (Shipment) intent.getSerializableExtra("item");
        if(s != null){
            if(s.getName() == "" || s.getName() == null){
                tvName.setText("Nhập tên");
            } else{
                tvName.setText(s.getName()+"");
                name = s.getName();
            }
            if(s.getPhone() == "" || s.getPhone() == null){
                tvPhone.setText("Nhập số điện thoại");
            } else{
                tvPhone.setText(s.getPhone()+"");
                phone = s.getPhone();
            }
            if(s.getAddress() == "" || s.getAddress() == null){
                tvAddress.setText("Nhập địa chỉ");
            } else{
                tvAddress.setText(s.getAddress()+"");
                addr = s.getAddress().toString();
            }
        } else{
            tvName.setText("Nhập tên");
            tvPhone.setText("Nhập số điện thoại");
            tvAddress.setText("Nhập địa chỉ");
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        btPayment.setOnClickListener(this);
        btAddFood.setOnClickListener(this);
        enterAddAddress.setOnClickListener(this);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pay = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có mục nào được chọn
            }
        });

        getFirebase();
    }

    private void getFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference cartRef = firebaseDatabase.getReference().child("Cart");
        cartRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Cart cart = dataSnapshot.getValue(Cart.class);
                    listItemCart = new ArrayList<>();
                    List<ItemCart> list1 = new ArrayList<>();
                    if (cart != null) {
                        tong = cart.getTotal();
                        listItemCart = new ArrayList<>(cart.getListItemCart());
                        totalPrice.setText(tong+" đ");

                    }

                    adapter = new RecycleViewPaymentAdapter(PaymentActivity.this, listItemCart);
                    SpacingItemDecoration itemDecoration = new SpacingItemDecoration(30);
                    LinearLayoutManager manager = new LinearLayoutManager(PaymentActivity.this, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.addItemDecoration(itemDecoration);
                    recyclerView.setAdapter(adapter);
                    adapter.setItemListener(PaymentActivity.this);



                } else {
                    totalPrice.setText("0 đ");

                    Toast.makeText(PaymentActivity.this, "No payment", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(PaymentActivity.this, "enter payment success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }

        });

    }

    @Override
    public void onClick(View v) {
        if(v == btPayment) {
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
                                UUID uuid = UUID.randomUUID();
                                String idp = uuid.toString();
                                String idio = uuid.toString();
                                String ids = uuid.toString();
                                Payment payment = new Payment(idp, pay);
                                long currentTimeMillis = System.currentTimeMillis();
                                String currentTimeString = convertTimeMillisToString(currentTimeMillis);
                                Shipment shipment = new Shipment(ids,name, phone, addr);
                                itemOrder = new ItemOrder(idio, listItemCart,tong,payment, currentTimeString, "chờ xác nhận",shipment);
                                userOrder.getOrder().getListItemOrder().add(itemOrder);
                                userRef.setValue(userOrder);
                                Toast.makeText(PaymentActivity.this, "đặt hàng thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                    Toast.makeText(PaymentActivity.this, "đặt hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
                }
            });
        }
        if(v == btAddFood){

        }

        if(v == enterAddAddress) {
            Shipment si = new Shipment();
            si.setName(name);
            si.setPhone(phone);
            si.setAddress(addr);
            Toast.makeText(this, addr+": address", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("item", si);
            startActivity(intent);

        }
    }

    private String convertTimeMillisToString(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultDate = new Date(timeMillis);
        return sdf.format(resultDate);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}