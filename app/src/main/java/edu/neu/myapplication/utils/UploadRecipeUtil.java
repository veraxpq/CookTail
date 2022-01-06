package edu.neu.myapplication.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class UploadRecipeUtil extends Thread{
    private String url, timeStamp, imageFileName;
    private Bitmap imageBitmap;
    private FirebaseStorage storage;
    private StorageReference storageRef,imageRef;
    private FirebaseUser user;
    private Map<String, Object> map;

    public UploadRecipeUtil(String timeStamp, Bitmap imageBitmap, FirebaseUser user, String imageFileName, Map<String, Object> map) {
        this.timeStamp = timeStamp;
        this.imageBitmap = imageBitmap;
        this.user = user;
        this.imageFileName = imageFileName;
        this.map = map;
    }

    public void run() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        imageRef = storageRef.child(user.getUid()).child(imageFileName);

        //storage the photo into firebase storage
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.d(TAG, "onFailure: " + exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.d(TAG, "onSuccess: store the picture in firebase storage successfully" );
            }
        });
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri uri = task.getResult();
                    url = uri.toString();
                    Log.d(TAG, "onComplete: upload successfully");
                    UploadRecipeToFirebaseCloud uploadToFirebaseCloud = new UploadRecipeToFirebaseCloud(url, user, timeStamp, map);
                    try {
                        uploadToFirebaseCloud.start();
                        uploadToFirebaseCloud.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                } else {
                    // Handle failures
                    // ...
                    Log.d(TAG, "onComplete: failure");
                }
            }
        });
    }
}

class UploadRecipeToFirebaseCloud extends Thread {

    private String url;
    private FirebaseUser user;
    private String timeStamp;
    private Map<String, Object> map;

    public UploadRecipeToFirebaseCloud(String url, FirebaseUser user, String timeStamp, Map<String, Object> map) {
        this.url = url;
        this.user = user;
        this.timeStamp = timeStamp;
        this.map = map;
    }

    public void run() {
        if (url != null) {
            DocumentReference reference = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
            DocumentReference photoRef = reference.collection("posts").document(timeStamp);
            map.put("postImage", url);
            photoRef.set(map, SetOptions.merge());
            Log.d(TAG, "uploadToFirebaseCloud: upload to cloud firebase successfully");


            FirebaseFirestore.getInstance().collection("Users").document(user.getUid()).collection("Recipes").add(map);
            Log.d(TAG, "run: post successfully");
        }
    }
}