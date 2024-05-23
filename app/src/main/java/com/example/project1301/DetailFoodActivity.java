package com.example.project1301;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1301.model.Cart;
import com.example.project1301.model.Food;
import com.example.project1301.model.ItemCart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetailFoodActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tName, tPrice, tGiam, tTang, tShowSl, totalPrice;
    private Button btAddCart, btPay;
    private ImageView img;

    private Food food;

    private String name = "";
    private String price = "";
    private int imgInput = 0;

    private long tong;
    private int sl=1;

    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);
        initView();
        Intent intent = getIntent();
        food =  (Food) intent.getSerializableExtra("item");

        if(food != null){
            name = food.getName();
            price = food.getPrice();
            imgInput = food.getImg();
        }

        tName.setText(name);
        tPrice.setText(price+ " đ");
        img.setImageResource(imgInput);
        tong = Integer.parseInt(price) * sl;
        totalPrice.setText(String.valueOf(tong)+" đ");
        tGiam.setOnClickListener(this);
        tTang.setOnClickListener(this);
        tShowSl.setText(sl+"");
        btAddCart.setOnClickListener(this);
        btPay.setOnClickListener(this);
    }

    private void initView() {
        img = findViewById(R.id.img);
        tName = findViewById(R.id.tname);
        tPrice = findViewById(R.id.tprice);
        tGiam = findViewById(R.id.giam);
        tTang = findViewById(R.id.tangSl);
        tShowSl = findViewById(R.id.showSl);
        totalPrice = findViewById(R.id.toalPrice);
        btAddCart = findViewById(R.id.btnAddcart);
        btPay = findViewById(R.id.btnPayment);

    }

    @Override
    public void onClick(View v) {
        if(v == tGiam){
            if(sl > 1){
                sl -= 1;
                tShowSl.setText(sl+"");
                tong = Integer.parseInt(price) * sl;
                totalPrice.setText(String.valueOf(tong)+" đ");
            }
        }
        if(v == tTang){
            sl += 1;
            tShowSl.setText(sl+"");
            tong = Integer.parseInt(price) * sl;
            totalPrice.setText(String.valueOf(tong)+" đ");

        }

        if(v == btAddCart){
            firebaseDatabase = FirebaseDatabase.getInstance();


            DatabaseReference cartRef = firebaseDatabase.getReference().child("Cart");
            cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UUID uuid = UUID.randomUUID();
                    String id = uuid.toString();
                    ItemCart itemCart = new ItemCart(id, food, sl);
                    if (dataSnapshot.exists()) {
                        Cart cart = dataSnapshot.getValue(Cart.class);
                        if (cart != null) {
                            boolean checkIs = true;
                            for(ItemCart i: cart.getListItemCart()){
                                if(i.getFood().getId().toLowerCase().equals(itemCart.getFood().getId().toLowerCase())){
                                    checkIs = false;
                                    int m = i.getSl();
                                    int n = m+itemCart.getSl();
                                    i.setSl(n);
                                    break;
                                }
                            }
                            if(checkIs == false){
                                long f = cart.getTotal();
                                f += Integer.parseInt(food.getPrice())*sl;
                                cart.setTotal(f);
                            }
                            else{
                            cart.getListItemCart().add(itemCart);
                            long t = cart.getTotal();
                            t += Integer.parseInt(food.getPrice())*sl;
                            cart.setTotal(t);
                            }
                            cartRef.setValue(cart);
                        }
                    } else {
                        // Child "Cart" chưa tồn tại
                        List<ItemCart> itemList = new ArrayList<>();
                        itemList.add(itemCart);
                        Cart newCart = new Cart(itemList, itemCart.getSl()*tong);
                        // Đẩy dữ liệu lên Firebase
                        cartRef.setValue(newCart);
                    }
                    Toast.makeText(DetailFoodActivity.this, "sp da duoc them vao cart", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Xử lý khi có lỗi xảy ra
                }
            });


        }
    }
}