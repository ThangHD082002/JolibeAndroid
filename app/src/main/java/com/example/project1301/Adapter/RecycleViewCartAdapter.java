package com.example.project1301.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project1301.DetailFoodActivity;
import com.example.project1301.R;
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

public class RecycleViewCartAdapter extends RecyclerView.Adapter<RecycleViewCartAdapter.ViewHolder>{

    private Context context;
    private List<ItemCart> list;

    private ItemListener itemListener;

    private int sl = 1;

    private FirebaseDatabase firebaseDatabase;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public ItemCart getItem(int position){
        return list.get(position);
    }


    public RecycleViewCartAdapter(Context context, List<ItemCart> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemCart item = list.get(position);
        holder.img.setImageResource(item.getFood().getImg());
        holder.name.setText(item.getFood().getName());
        holder.price.setText(item.getFood().getPrice());
        holder.moTa.setText(item.getFood().getMoTa());
        holder.showSl.setText(item.getSl()+"");
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban co chac chan xoa hay khong ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(position);
                        notifyDataSetChanged();

                        // Cập nhật dữ liệu trên Firebase
                        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart");
                        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Cart cart = dataSnapshot.getValue(Cart.class);
                                    if (cart != null) {
                                        // Xóa mục khỏi danh sách mục trong Cart trên Firebase
                                        cart.setTotal(cart.getTotal() - Long.parseLong(item.getFood().getPrice())*item.getSl());
                                        cart.getListItemCart().remove(position);
                                        // Kiểm tra nếu danh sách trở thành trống
                                        if (cart.getListItemCart().isEmpty()) {
                                            // Xóa child "Cart" trên Firebase
                                            cartRef.removeValue();
                                        } else {
                                            // Cập nhật dữ liệu trên Firebase
                                            cartRef.setValue(cart);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Xử lý khi có lỗi xảy ra
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.tTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setSl(item.getSl());
                notifyDataSetChanged();

                // Cập nhật dữ liệu trên Firebase
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart");
                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Cart cart = dataSnapshot.getValue(Cart.class);
                            if (cart != null) {

                                cart.setTotal(cart.getTotal() + Long.parseLong(item.getFood().getPrice()));
                                for(ItemCart i : cart.getListItemCart()){
                                    if(i.getId().toLowerCase().equals(item.getId().toLowerCase())){
                                        i.setSl(item.getSl()+1);
                                        break;
                                    }
                                }
                                cartRef.setValue(cart);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
            }
        });

        holder.tGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setSl(item.getSl());
                notifyDataSetChanged();

                // Cập nhật dữ liệu trên Firebase
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart");
                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Cart cart = dataSnapshot.getValue(Cart.class);
                            if (cart != null) {
                                // Xóa mục khỏi danh sách mục trong Cart trên Firebase
                                cart.setTotal(cart.getTotal() - Long.parseLong(item.getFood().getPrice()));
                                for(ItemCart i : cart.getListItemCart()){
                                    if(i.getId().toLowerCase().equals(item.getId().toLowerCase())){
                                        if(i.getSl() >=2){
                                            i.setSl(item.getSl()-1);
                                            cartRef.setValue(cart);
                                            break;
                                        } else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                            builder.setTitle("Thong bao xoa");
                                            builder.setMessage("Sản phẩm sẽ bị xóa khỏi giỏ hàng ?");
                                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    list.remove(position);
                                                    notifyDataSetChanged();
                                                    cart.setTotal(cart.getTotal() - Long.parseLong(item.getFood().getPrice())*item.getSl());
                                                    cart.getListItemCart().remove(position);
                                                    // Kiểm tra nếu danh sách trở thành trống
                                                    if (cart.getListItemCart().isEmpty()) {
                                                        // Xóa child "Cart" trên Firebase
                                                        cartRef.removeValue();
                                                    } else {
                                                        // Cập nhật dữ liệu trên Firebase
                                                        cartRef.setValue(cart);
                                                    }

                                                }
                                            });
                                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {

                                                }
                                            });

                                            AlertDialog dialog = builder.create();
                                            dialog.show();
                                        }

                                    }
                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Xử lý khi có lỗi xảy ra
                    }
                });
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView img;
        private TextView name, price, moTa,tGiam ,showSl,tTang;
        private Button btDelete;

        public ViewHolder(@NonNull View view) {
            super(view);
            img = view.findViewById(R.id.img);
            name = view.findViewById(R.id.tname);
            price = view.findViewById(R.id.tprice);
            moTa = view.findViewById(R.id.tdesc);
            tGiam = view.findViewById(R.id.giamSl);
            tTang = view.findViewById(R.id.tangSl);
            showSl = view.findViewById(R.id.showSl);
            btDelete = view.findViewById(R.id.btdelete);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener != null){
                itemListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public interface  ItemListener{
        void onItemClick(View view, int position);
    }

}
