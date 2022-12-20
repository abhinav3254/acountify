package com.example.acountify;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.acountify.database.DBHelper;
import com.example.acountify.databinding.ActivityUpdateBinding;

public class UpdateActivity extends AppCompatActivity {

    ActivityUpdateBinding binding;

    String id,title,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_update);

        binding = ActivityUpdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().hide();

        getAndSetIntentData();

        binding.updateAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper dbHelper = new DBHelper(UpdateActivity.this);
                title = binding.titleOfEntry2.getText().toString().trim();
                amount = binding.amountOfEntry2.getText().toString().trim();
                dbHelper.updateData(id,title,amount);

                Intent intent = new Intent(UpdateActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getConfirmDialog();
            }
        });

    }

    void getAndSetIntentData() {
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("amount")) {
//            getting the data from Intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            amount = getIntent().getStringExtra("amount");

            //setting intent data

            binding.titleOfEntry2.setText(title);
            binding.amountOfEntry2.setText(amount);

        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void getConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
        builder.setTitle("Delete"+title+" ?");
        builder.setMessage("Are you sure to delete "+title+" ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDatabaseHelper = new DBHelper(UpdateActivity.this);
                myDatabaseHelper.deleteDataOne(id);

                Intent intent = new Intent(UpdateActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

}