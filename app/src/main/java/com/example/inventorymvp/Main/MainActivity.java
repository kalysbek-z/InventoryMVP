package com.example.inventorymvp.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.inventorymvp.R;
import com.example.inventorymvp.data.Product;
import com.example.inventorymvp.Editor.EditorActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements ContractMain.View {
    public static final int ADD_DATA_REQUEST = 1;
    public static final int EDIT_DATA_REQUEST = 2;

    private final int UNDEFINED_ID_VALUE = -1;

    ContractMain.Presenter presenter;

    FloatingActionButton DataButton;

    ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataButton = findViewById(R.id.add_data);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        presenter = new MainPresenter(this, getApplication());

        productAdapter = new ProductAdapter(this);
        productAdapter.setDataList(presenter.getAll());

        recyclerView.setAdapter(productAdapter);
        recyclerView.invalidateItemDecorations();

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, Product product) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra(EditorActivity.EXTRA_ID, product.getId());
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });

        DataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        presenter = new MainPresenter(this, getApplication());

        productAdapter = new ProductAdapter(this);
        productAdapter.setDataList(presenter.getAll());

        recyclerView.setAdapter(productAdapter);
        recyclerView.invalidateItemDecorations();

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos, Product product) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra(EditorActivity.EXTRA_ID, product.getId());
                intent.putExtra("product", product);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteAll:
                presenter.deleteAll();
                RecyclerView recyclerView = findViewById(R.id.recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setHasFixedSize(true);

                presenter = new MainPresenter(this, getApplication());

                productAdapter = new ProductAdapter(this);
                productAdapter.setDataList(presenter.getAll());

                recyclerView.setAdapter(productAdapter);
                recyclerView.invalidateItemDecorations();

                productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int pos, Product product) {
                        Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                        intent.putExtra(EditorActivity.EXTRA_ID, product.getId());
                        intent.putExtra("product", product);
                        startActivity(intent);
                    }
                });

                Toast.makeText(this, "All products were deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}