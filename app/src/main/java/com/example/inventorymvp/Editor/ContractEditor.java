package com.example.inventorymvp.Editor;

import com.example.inventorymvp.data.Product;

public interface ContractEditor {
    interface View {
    }

    interface Presenter {
        public void insert(Product product);

        public void update(int id, String name, String imageUri, String supplier, int quantity, String price);

        public void delete(int id);
    }
}
