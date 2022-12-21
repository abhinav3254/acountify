package com.example.acountify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.acountify.database.DBHelper;
import com.example.acountify.databinding.ActivityHomeBinding;
import com.example.acountify.recycleview.CustomAdapter;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    ArrayList<String> expense_id,expense_title,expense_amount;
    DBHelper dbHelper;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);

//        getSupportActionBar().hide();

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AddEntryActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DBHelper(HomeActivity.this);

        expense_id = new ArrayList<>();
        expense_title = new ArrayList<>();
        expense_amount = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(HomeActivity.this,HomeActivity.this,expense_id,expense_title,expense_amount);
        binding.recyclerView.setAdapter(customAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));






    }

    //    menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all_btn) {
            getConfirmDialog();
        }

        if(item.getItemId() == R.id.contact_developer) {
            Uri uri = Uri.parse("http://github.com/abhinav3254");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    void getConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure to delete all Data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBHelper myDatabaseHelper = new DBHelper(HomeActivity.this);
                myDatabaseHelper.deleteAllData();
//            recreate();
                Intent intent = new Intent(HomeActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

    void storeDataInArrays() {
        Cursor cursor = dbHelper.readAllData();
        if (cursor.getCount() == 0) {
//            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
            binding.emptyImageview1.setVisibility(View.VISIBLE);
            binding.noData1.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                expense_id.add(cursor.getString(0));
                expense_title.add(cursor.getString(1));
                expense_amount.add(cursor.getString(2));
            }
            binding.emptyImageview1.setVisibility(View.GONE);
            binding.noData1.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}