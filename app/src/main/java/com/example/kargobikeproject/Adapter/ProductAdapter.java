package com.example.kargobikeproject.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kargobikeproject.Model.Entity.Product;
import com.example.kargobikeproject.Utils.RecyclerViewItemClickListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder1> {

    private List<Product> products= new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private Context mContext;
    public static List<Product> productsList;

    private Uri mImageUri;



    public ProductAdapter(List<String> keys, List<Product> products, Context mContext) {
        this.keys = keys;
        this.products = products;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Product actualProduct = products.get(position);

        holder.productName.setText(actualProduct.getProduct_name());
        holder.productQty.setText(actualProduct.getProduct_qty().toString());
        holder.productAlarmQty.setText(actualProduct.getProduct_qty_alarm().toString());
        holder.productCapacity.setText(actualProduct.getProduct_capacity().toString());
        holder.productPrice.setText(actualProduct.getProduct_price());


        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Create a reference with an initial file path and name
        StorageReference pathReference = storageRef.child(actualProduct.getProduct_img_name());

        final long ONE_MEGABYTE = 1024 * 1024;
        pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.productImg.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });



        holder.eraseBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Effacer");
                alert.setMessage("Etes-vous sur de vouloir effacer ce produit ? (Cette action est irréversible)");
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new FirebaseDatabaseHelper().eraseProduct(keys.get(position), new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Product> products, List<String> keys) {

                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {

                            }

                            @Override
                            public void DataIsDeleted() {
                                Toast.makeText(mContext, "Le produit a bien été effacé !" , Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });

        holder.saveBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                final Product updatedProduct = new Product();
                updatedProduct.setCategory_id(actualProduct.getCategory_id());
                updatedProduct.setProduct_capacity(Long.valueOf(holder.productCapacity.getText().toString()));

                updatedProduct.setProduct_img_name(actualProduct.getProduct_img_name());
                updatedProduct.setProduct_price(holder.productPrice.getText().toString());

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference();
                StorageReference pathReference = storageRef.child(actualProduct.getProduct_img_name());

                final long ONE_MEGABYTE = 1024 * 1024;
                pathReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.productImg.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });


                updatedProduct.setProduct_name(holder.productName.getText().toString());
                updatedProduct.setProduct_qty(Long.valueOf(holder.productQty.getText().toString()));
                updatedProduct.setProduct_qty_alarm(Long.valueOf(holder.productAlarmQty.getText().toString()));

                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Sauvegarder");
                alert.setMessage("Etes-vous sur de vouloir mettre à jour le produit ?");
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new FirebaseDatabaseHelper().updateProduct(keys.get(position),updatedProduct ,new FirebaseDatabaseHelper.DataStatus() {
                            @Override
                            public void DataIsLoaded(List<Product> products, List<String> keys) {

                            }

                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {
                                Toast.makeText(mContext, "Le produit a bien été mis à jour !" , Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                        dialog.dismiss();
                    }
                });

                alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.saveBtn.setEnabled(false);
                holder.cancelBtn.setEnabled(false);

                holder.productName.setEnabled(false);
                holder.productQty.setEnabled(false);
                holder.productAlarmQty.setEnabled(false);
                holder.productCapacity.setEnabled(false);
                holder.productPrice.setEnabled(false);

                holder.productName.setText(actualProduct.getProduct_name());
                holder.productQty.setText(actualProduct.getProduct_qty().toString());
                holder.productAlarmQty.setText(actualProduct.getProduct_qty_alarm().toString());
                holder.productCapacity.setText(actualProduct.getProduct_capacity().toString());
            }
        });


        /* Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (mContext instanceof Activity) {
                    System.out.println("PRODUCT NAME : " + productNamePicture);
                    ((Activity) mContext).startActivityForResult(intent, PICK_IMAGE_REQUEST);
                    Toast.makeText(mContext, "N'oubliez pas de sauvegarder pour appliquer la modification d'image !" , Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("mContext should be an instanceof Activity.");
                } */

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        EditText productName;
        EditText productQty;
        EditText productAlarmQty;
        EditText productCapacity;
        EditText productPrice;

        Button modifyBtn;
        Button saveBtn;
        Button eraseBtn;
        Button cancelBtn;

        ImageView productImg;


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName_ET);
            productQty = itemView.findViewById(R.id.productQty_ET);
            productAlarmQty = itemView.findViewById(R.id.productQtyAlarm_ET);
            productCapacity = itemView.findViewById(R.id.productCapacity_ET);
            productPrice = itemView.findViewById(R.id.productPrice_ET);


            cancelBtn = itemView.findViewById(R.id.cancelBtn);
            modifyBtn = itemView.findViewById(R.id.modifyBtn);
            saveBtn = itemView.findViewById(R.id.saveBtn);

            productImg = itemView.findViewById(R.id.product_imgview);
            eraseBtn = itemView.findViewById(R.id.eraseBtn);





            modifyBtn.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    saveBtn.setEnabled(true);
                    cancelBtn.setEnabled(true);

                    productName.setEnabled(true);
                    productQty.setEnabled(true);
                    productAlarmQty.setEnabled(true);
                    productCapacity.setEnabled(true);
                    productPrice.setEnabled(true);

                    Toast.makeText(itemView.getContext(),"Vous pouvez maintenant modifier les valeurs de " + productName.getText().toString() + " !", Toast.LENGTH_SHORT).show();
                   /* SharedPreferences prefs = getActivity(itemView.getContext()).getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("ImageProductName", productName.getText().toString());
                    editor.commit(); */
                    System.out.println("LOG : product name saved " + productName.getText().toString());
                }
            });










        }
    }

    private static Activity getActivity(Context context) {
        if (context == null) return null;
        if (context instanceof Activity) return (Activity) context;
        if (context instanceof ContextWrapper) return getActivity(((ContextWrapper)context).getBaseContext());
        return null;
    }


}