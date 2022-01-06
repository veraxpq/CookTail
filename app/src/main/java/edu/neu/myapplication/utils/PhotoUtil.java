package edu.neu.myapplication.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class PhotoUtil extends Thread{
    private String url, timeStamp, imageFileName;
    private Bitmap imageBitmap;
    private FirebaseStorage storage;
    private StorageReference storageRef,imageRef;
    private FirebaseUser user;

    public PhotoUtil(String timeStamp, Bitmap imageBitmap, FirebaseUser user, String imageFileName) {
        this.timeStamp = timeStamp;
        this.imageBitmap = imageBitmap;
        this.user = user;
        this.imageFileName = imageFileName;
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
                    UploadToFirebaseCloud uploadToFirebaseCloud = new UploadToFirebaseCloud(url, user, timeStamp);
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

class UploadToFirebaseCloud extends Thread {

    private String url;
    private FirebaseUser user;
    private String timeStamp;

    public UploadToFirebaseCloud(String url, FirebaseUser user, String timeStamp) {
        this.url = url;
        this.user = user;
        this.timeStamp = timeStamp;
    }

    public void run() {
        if (url != null) {
            DocumentReference reference = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
            DocumentReference photoRef = reference.collection("posts").document(timeStamp);
            Map<String, Object> pictures = new HashMap<>();
            pictures.put("postImage", url);
            photoRef.set(pictures, SetOptions.merge());
            Log.d(TAG, "uploadToFirebaseCloud: upload to cloud firebase successfully");
//            reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                    if (error != null) {
//                        return;
//                    }
//                    assert value != null;
//                    int postNum = value.getLong("post").intValue();
//                    Map<String, String> postMap = new HashMap<>();
//                    postMap.put("post", String.valueOf(postNum + 1));
//                    reference.set(postMap, SetOptions.merge());
//                }
//            });
        }
    }
}