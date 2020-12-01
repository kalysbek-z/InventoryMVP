package com.example.inventorymvp.Main;

import com.example.inventorymvp.data.Product;

import java.util.List;

public interface ContractMain {
    interface View {
    }

    interface Presenter {
        void deleteAll();

        List<Product> getAll();

        Product getProduct(int pos);
    }
}
