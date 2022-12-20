package com.example.acountify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.acountify.database.DBHelper;
import com.example.acountify.databinding.ActivityAddEntryBinding;

public class AddEntryActivity extends AppCompatActivity {

    ActivityAddEntryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        binding = ActivityAddEntryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.addAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.titleOfEntry.getText().toString().trim();
                String amount = binding.amountOfEntry.getText().toString().trim();

                if (title.isEmpty()) {
                    Toast.makeText(AddEntryActivity.this, "title can't be empty", Toast.LENGTH_SHORT).show();
                } if (amount.isEmpty()) {
                    Toast.makeText(AddEntryActivity.this, "amount can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    DBHelper dbHelper = new DBHelper(AddEntryActivity.this);
                    boolean ans = dbHelper.addEntry(title,amount);

                    if (ans) {
                        Toast.makeText(AddEntryActivity.this, "Added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddEntryActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(AddEntryActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}