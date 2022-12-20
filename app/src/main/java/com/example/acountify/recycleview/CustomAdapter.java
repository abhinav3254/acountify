package com.example.acountify.recycleview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.acountify.R;
import com.example.acountify.UpdateActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    Activity activity;

    private ArrayList expense_id,expense_title,expense_amount;

    public CustomAdapter(Activity activity ,Context context, ArrayList expense_id, ArrayList expense_title, ArrayList expense_amount) {
        this.activity = activity;
        this.context = context;
        this.expense_id = expense_id;
        this.expense_title = expense_title;
        this.expense_amount = expense_amount;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycle_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.expense_amount_txt.setText(String.valueOf(expense_amount.get(position)));
        holder.id_view_txt.setText(String.valueOf(expense_id.get(position)));
        holder.expense_name_txt.setText(String.valueOf(expense_title.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id",String.valueOf(expense_id.get(holder.getAdapterPosition())));
                intent.putExtra("title",String.valueOf(expense_title.get(holder.getAdapterPosition())));
                intent.putExtra("amount",String.valueOf(expense_amount.get(holder.getAdapterPosition())));
                activity.startActivityForResult(intent,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return expense_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_view_txt,expense_name_txt,expense_amount_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_view_txt = itemView.findViewById(R.id.id_view);
            expense_name_txt = itemView.findViewById(R.id.expense_name);
            expense_amount_txt = itemView.findViewById(R.id.expense_amount);

            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
