package com.example.inventorymvp.Main;

import android.app.Application;

import com.example.inventorymvp.data.Product;
import com.example.inventorymvp.data.ProductDatabase;

import java.util.List;

public class MainPresenter implements ContractMain.Presenter {
    ProductDatabase database;
    ContractMain.View view;

    public MainPresenter(ContractMain.View view, Application app) {
        this.view = view;
        database = ProductDatabase.getInstance(app);
    }

    @Override
    public void deleteAll() {
        database.productDao().deleteAllData();
    }

    @Override
    public List<Product> getAll() {
        return database.productDao().readAllData();
    }

    @Override
    public Product getProduct(int pos) {
        return getProduct(pos);
    }
}
