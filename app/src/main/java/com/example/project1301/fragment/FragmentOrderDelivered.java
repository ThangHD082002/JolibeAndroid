package com.example.project1301.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1301.Adapter.RecycleViewOrderAdapter;
import com.example.project1301.DetailOrderActivity;
import com.example.project1301.R;
import com.example.project1301.model.ItemOrder;
import com.example.project1301.model.Order;
import com.example.project1301.model.UserOrder;
import com.example.project1301.spacing.SpacingItemDecoration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentOrderDelivered extends Fragment implements RecycleViewOrderAdapter.ItemListener{
    private RecycleViewOrderAdapter adapter;
    private RecyclerView recyclerView;

    private FirebaseDatabase firebaseDatabase;

    private FirebaseAuth firebaseAuth;

    private List<ItemOrder> listItemOrder;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preparing, container, false);
        recyclerView = view.findViewById(R.id.recycleView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        getFirebase();


        return view;
    }

    private void getFirebase() {
        String uuid = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userRef = firebaseDatabase.getReference().child("Registered User").child(uuid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserOrder userOrder = dataSnapshot.getValue(UserOrder.class);
                    if (userOrder != null) {
                        Order order = userOrder.getOrder();
                        listItemOrder = new ArrayList<>();
                        if (order != null) {
                            // Nếu danh sách listItemOrder đã tồn tại, bạn có thể thực hiện các thao tác cập nhật tại đây
//                            listItemOrder = new ArrayList<>(order.getListItemOrder());
                            for(int i = 1; i < order.getListItemOrder().size(); i++){
                                if(order.getListItemOrder().get(i).getState().equals("đã giao")){
                                    listItemOrder.add(order.getListItemOrder().get(i));
                                }
                            }
                            adapter = new RecycleViewOrderAdapter(getContext(), listItemOrder);
//                            Toast.makeText(getContext(), "Enter Order Success", Toast.LENGTH_SHORT).show();
                        }
                        SpacingItemDecoration itemDecoration = new SpacingItemDecoration(30);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.addItemDecoration(itemDecoration);
                        recyclerView.setAdapter(adapter);
                        adapter.setItemListener(FragmentOrderDelivered.this);
                    }
                } else {
//                    Toast.makeText(getContext(), "Enter Order Failer", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        ItemOrder ir = (ItemOrder) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), DetailOrderActivity.class);
        intent.putExtra("io", ir);
        startActivity(intent);
    }
}
