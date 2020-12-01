package com.example.inventorymvp.Editor;

import android.app.Application;
import android.os.AsyncTask;

import com.example.inventorymvp.data.Product;
import com.example.inventorymvp.data.ProductDao;
import com.example.inventorymvp.data.ProductDatabase;

import java.util.List;

public class EditorPresenter implements ContractEditor.Presenter {
    ProductDatabase database;
    ContractEditor.View view;
    List<Product> products;
    ProductDao productDao;

    public EditorPresenter(ContractEditor.View view, Application app) {
        this.view = view;
        database = ProductDatabase.getInstance(app);
    }

    @Override
    public void insert(Product product) {
//        new InsertProductAsyncTask(productDao).execute(product);
        database.productDao().addProduct(product);
    }

    @Override
    public void update(int id, String name, String imageUri, String supplier, int quantity, String price) {
        database.productDao().update(id, name, imageUri, supplier, quantity, price);
    }

    @Override
    public void delete(int id) {
        database.productDao().delete(id);
//        new DeleteAsyncTask(productDao).execute(id);
    }

//    private static class InsertProductAsyncTask extends AsyncTask<Product, Void, Void> {
//        private ProductDao productDao;
//
//        private InsertProductAsyncTask(ProductDao productDao) {
//            this.productDao = productDao;
//        }
//
//        @Override
//        protected Void doInBackground(Product... products) {
//            productDao.addProduct(products[0]);
//            return null;
//        }
//    }
//
//    private static class UpdateProductAsyncTask extends AsyncTask<Product, Void, Void> {
//        private ProductDao productDao;
//
//        private UpdateProductAsyncTask(ProductDao productDao) {
//            this.productDao = productDao;
//        }
//
//        @Override
//        protected Void doInBackground(Product... products) {
//            productDao.update(products[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteAsyncTask extends AsyncTask<Integer, Void, Void> {
//        private ProductDao productDao;
//
//        private DeleteAsyncTask(ProductDao productDao) {
//            this.productDao = productDao;
//        }
//
//        @Override
//        protected Void doInBackground(Integer... id) {
//            productDao.delete(id[0]);
//            return null;
//        }
//    }
}
