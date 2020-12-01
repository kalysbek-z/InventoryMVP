package com.example.inventorymvp.Editor;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.inventorymvp.R;
import com.example.inventorymvp.Main.MainActivity;
import com.example.inventorymvp.data.Product;

import java.io.File;
import java.io.Serializable;

public class EditorActivity extends AppCompatActivity implements ContractEditor.View, Serializable {

    public static final String EXTRA_NAME = "package com.example.inventoryapp.EXTRA_NAME";
    public static final String EXTRA_PHOTO = "package com.example.inventoryapp.EXTRA_PHOTO";
    public static final String EXTRA_SUPPLIER = "package com.example.inventoryapp.EXTRA_SUPPLIER";
    public static final String EXTRA_QUANTITY = "package com.example.inventoryapp.EXTRA_QUANTITY";
    public static final String EXTRA_PRICE = "package com.example.inventoryapp.EXTRA_PRICE";
    public static final String EXTRA_ID = "package com.example.inventoryapp.EXTRA_ID";

    public static final int CAMERA_R_CODE = 20;
    public static final int GALLERY_R_CODE = 25;
    private final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 100;
    private final int UNDEFINED_ID_VALUE = -1;

    String photoUri;
    Bitmap bmpPhoto;
    Uri imageUri;
    String galleryImage;

    ImageView addPhoto;
    EditText Name;
    EditText Supplier;
    EditText Quantity;
    EditText Price;
    Button createButton;
    Button cameraButton;

    ContractEditor.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        addPhoto = findViewById(R.id.add_photo);
        Name = findViewById(R.id.nameEdit);
        Supplier = findViewById(R.id.supplierEdit);
        Quantity = findViewById(R.id.quantityEdit);
        Price = findViewById(R.id.priceEdit);
        createButton = findViewById(R.id.createButton);
        cameraButton = findViewById(R.id.camera);

        presenter = new EditorPresenter(this, getApplication());

        bmpPhoto = null;

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_R_CODE);
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGalleryPermission();

            }
        });

        //

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit");
            getProduct((Product) intent.getSerializableExtra("product"));
        } else {
            setTitle("Add");
        }
    }

    private void getProduct(Product product) {
        Name.setText(product.getName());
        galleryImage = product.getImageUri();

        Glide.with(getApplicationContext()).load(galleryImage).into(addPhoto);

        Supplier.setText(product.getSupplier());
        Quantity.setText(product.getQuantity());
        Price.setText(product.getPrice());
    }


    private void requestGalleryPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            pickImageFromGallery();
        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(this)
                        .setMessage("Need permission")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(EditorActivity.this,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                                        , PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(this, "Could not load image", Toast.LENGTH_LONG).show();
                }
        }
    }


    private void pickImageFromGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK);
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String path = directory.getPath();

        Uri imagePathUri = Uri.parse(path);

        gallery.setDataAndType(imagePathUri, "image/*");
        //gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery, GALLERY_R_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_R_CODE:
                bmpPhoto = (Bitmap) data.getExtras().get("data");
                if (bmpPhoto != null) {
//                    addPhoto.setImageBitmap(bmpPhoto);
                    Glide.with(getApplicationContext()).load(bmpPhoto).into(addPhoto);
                }
                break;
            case GALLERY_R_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    imageUri = data.getData();
                    photoUri = imageUri.toString();
                    Log.e("edit", photoUri.toString());
                    Glide.with(getApplicationContext()).load(photoUri).into(addPhoto);
                }
        }
    }

    //for toolbar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editor_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_product:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Do you want to delete this product?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent delete = new Intent();
                        int id = getIntent().getIntExtra(EXTRA_ID, UNDEFINED_ID_VALUE);

                        if (id != UNDEFINED_ID_VALUE) {
                            presenter.delete(id);
                        }
                        setResult(RESULT_CANCELED, delete);
                        finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", null);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveData() {
        String dataName = Name.getText().toString().trim();
        String dataSupplier = Supplier.getText().toString().trim();
        int dataQuantity = Integer.parseInt(Quantity.getText().toString().trim());
        String dataPrice = Price.getText().toString().trim();

        Product product = new Product(dataName, photoUri, dataSupplier, dataQuantity, dataPrice);
        Product product1 = (Product) getIntent().getSerializableExtra("product");

        if (Quantity.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Field is empty!", Toast.LENGTH_SHORT).show();
        }

        if (dataName.isEmpty() || dataSupplier.isEmpty() || dataPrice.isEmpty()) {
            Toast.makeText(this, "Field is empty!", Toast.LENGTH_SHORT).show();
        } else {
            if (product1 != null) {
                int id = product1.getId();
                presenter.update(id
                        , product.getName()
                        , product.getImageUri()
                        , product.getSupplier()
                        , Integer.parseInt(product.getQuantity())
                        , product.getPrice());
            } else {
                presenter.insert(product);
            }
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra(EXTRA_ID, UNDEFINED_ID_VALUE);
        setResult(MainActivity.RESULT_CANCELED, data);
        finish();
        super.onBackPressed();
    }
}