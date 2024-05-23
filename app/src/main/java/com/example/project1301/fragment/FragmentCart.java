package com.example.project1301.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1301.Adapter.RecycleViewCartAdapter;
import com.example.project1301.DetailFoodActivity;
import com.example.project1301.DetailMenuActivity;
import com.example.project1301.MenuActivity;
import com.example.project1301.PaymentActivity;
import com.example.project1301.R;
import com.example.project1301.model.Cart;
import com.example.project1301.model.Food;
import com.example.project1301.model.ItemCart;
import com.example.project1301.spacing.SpacingItemDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FragmentCart extends Fragment implements RecycleViewCartAdapter.ItemListener, View.OnClickListener{

    private RecycleViewCartAdapter adapter;
    private RecyclerView recyclerView;
    private TextView totalPrice;

    private List<ItemCart> listItemCart;
    private Button btAddFood, btPayment;

    private Food food;

    private long tong = 0;
    private int sl=1;

    private FirebaseDatabase firebaseDatabase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_cart, container, false);

        totalPrice =view.findViewById(R.id.totalPrice);
        btAddFood =  view.findViewById(R.id.btnAddFood);
        btPayment = view.findViewById(R.id.btnPayment);
        recyclerView = view.findViewById(R.id.recycleView);

        getFirebase();



        return view;
    }

    private void  getFirebase() {
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
                        adapter = new RecycleViewCartAdapter(getContext(), listItemCart);
                    }


                    SpacingItemDecoration itemDecoration = new SpacingItemDecoration(30);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
//                    recyclerView.addItemDecoration(itemDecoration);
                    recyclerView.setAdapter(adapter);
                    adapter.setItemListener(FragmentCart.this);
                } else {
                    totalPrice.setText("0 đ");
                    tong = 0;
                    Toast.makeText(getContext(), "No Cart Item", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(getContext(), "enter cart success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }

        });

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btAddFood.setOnClickListener(this);
        btPayment.setOnClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onClick(View v) {
        if (v == btAddFood){
            Intent intent1 = new Intent(getContext(), MenuActivity.class);
            intent1.putExtra("item", "Thực Đơn");
            startActivity(intent1);
        }
        if(v == btPayment){
//            firebaseDatabase = FirebaseDatabase.getInstance();
//            DatabaseReference cartRef = firebaseDatabase.getReference().child("Cart");
//            cartRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        Cart cart = dataSnapshot.getValue(Cart.class);
//                        if (cart != null) {
////                            Intent intent2= new Intent(getContext(), PaymentActivity.class);
////                            startActivity(intent2);
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "Bạn cần thêm ít nhất một sản phầm vào giỏ hàng", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//                    // Xử lý khi có lỗi xảy ra
//                }
//
//            });
            if(tong == 0){
                Toast.makeText(getContext(), "Bạn cần thêm ít nhất một sản phầm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            } else{
                Intent intent2= new Intent(getContext(), PaymentActivity.class);
                startActivity(intent2);
            }

        }
    }
}
