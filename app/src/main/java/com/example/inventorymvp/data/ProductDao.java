package com.example.inventorymvp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addProduct(Product product);

    @Query("UPDATE product_data SET name = :name, image =:imageUri, supplier =:supplier, quantity =:quantity, price =:price WHERE id =:id")
    void update(int id, String name, String imageUri, String supplier, int quantity, String price);

    @Delete
    void deleteProduct(Product product);

    @Query("DELETE FROM product_data WHERE id = :id")
    void delete(int id);

    @Query("DELETE FROM product_data")
    void deleteAllData();

    @Query("SELECT * FROM product_data")
    List<Product> readAllData();

    @Update
    void update(Product product);
}
