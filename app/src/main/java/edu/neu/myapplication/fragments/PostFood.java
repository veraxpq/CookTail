package edu.neu.myapplication.fragments;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.model.Document;
import com.google.firebase.firestore.model.DocumentCollections;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.neu.myapplication.LevelTestActivity;
import edu.neu.myapplication.MainActivity;
import edu.neu.myapplication.R;
import edu.neu.myapplication.ReplacerActivity;
import edu.neu.myapplication.utils.PhotoUtil;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static edu.neu.myapplication.fragments.Profile.REQUEST_IMAGE_CAPTURE;


public class PostFood extends Fragment {
    FirebaseAuth auth;
    FirebaseUser user;
    EditText postInput;
    ImageButton addPhotoBtn;
    ImageView pic1, pic2, pic3, pic4, pic5, pic6, pic7, pic8;
    private ImageButton startCameraButton = null;
    private ImageButton choiceFromAlbumButton = null;
    private ImageView pictureImageView = null;

    private static final int TAKE_PHOTO_PERMISSION_REQUEST_CODE = 0; // 拍照的权限处理返回码
    private static final int WRITE_SDCARD_PERMISSION_REQUEST_CODE = 1; // 读储存卡内容的权限处理返回码

    private static final int TAKE_PHOTO_REQUEST_CODE = 3; // 拍照返回的 requestCode
    private static final int CHOICE_FROM_ALBUM_REQUEST_CODE = 4; // 相册选取返回的 requestCode
    private static final int CROP_PHOTO_REQUEST_CODE = 5; // 裁剪图片返回的 requestCode

    private Uri photoUri = null;
    private Uri photoOutputUri = null; // 图片最终的输出文件的 Uri
    Button submit;
    int numOfPic = 0;
    String currentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String timeStamp;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    String imageFileName;


    public PostFood() {
        // Required empty public constructor
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_post_food, container, false);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(view);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You have created a post successfully! Please go to profile page to see your posts!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void dispatchTakePictureIntent(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            ContentValues contentValues = new ContentValues(2);

            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imageFileName = "JPEG_" + timeStamp;

            //如果想拍完存在系统相机的默认目录,改为
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName + ".jpg");

            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void uploadToFirebaseCloud(Uri downloadUri) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data == null) {
            Toast.makeText(getContext(), "data null", Toast.LENGTH_LONG).show();
            return;
        }

        if (resultCode != RESULT_OK) {
            Toast.makeText(getContext(), "resultCode = " + resultCode, Toast.LENGTH_LONG).show();
            return;
        }

        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        PhotoUtil upload  = new PhotoUtil(timeStamp, imageBitmap, user, imageFileName);
        upload.start();

        if (numOfPic == 0) {
            pic1.setImageBitmap(imageBitmap);
            numOfPic++;
            pic1.setVisibility(View.VISIBLE);
        } else if (numOfPic == 1) {
            pic2.setImageBitmap(imageBitmap);
            pic2.setVisibility(View.VISIBLE);
            numOfPic++;
        } else if (numOfPic == 2) {
            pic3.setImageBitmap(imageBitmap);
            pic3.setVisibility(View.VISIBLE);
            numOfPic++;
        } else if (numOfPic == 3) {
            pic4.setImageBitmap(imageBitmap);
            pic4.setVisibility(View.VISIBLE);
            numOfPic++;
        } else if (numOfPic == 4) {
            pic5.setImageBitmap(imageBitmap);
            pic5.setVisibility(View.VISIBLE);
            numOfPic++;
        } else if (numOfPic == 5) {
            pic6.setImageBitmap(imageBitmap);
            pic6.setVisibility(View.VISIBLE);
            numOfPic++;
        } else if (numOfPic == 6) {
            pic7.setImageBitmap(imageBitmap);
            pic7.setVisibility(View.VISIBLE);
            numOfPic++;
        } else if (numOfPic == 7) {
            pic8.setImageBitmap(imageBitmap);
            pic8.setVisibility(View.VISIBLE);
            numOfPic++;
        }



    }

    public void init(View view) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        postInput = view.findViewById(R.id.postWords);
        addPhotoBtn = view.findViewById(R.id.addPhotoBtn);
        pic1 = view.findViewById(R.id.photoDisplay1);
        pic2 = view.findViewById(R.id.photoDisplay2);
        pic3 = view.findViewById(R.id.photoDisplay3);
        pic4 = view.findViewById(R.id.photoDisplay4);
        pic5 = view.findViewById(R.id.photoDisplay5);
        pic6 = view.findViewById(R.id.photoDisplay6);
        pic7 = view.findViewById(R.id.photoDisplay7);
        pic8 = view.findViewById(R.id.photoDisplay8);
        submit = view.findViewById(R.id.submit);
        pictureImageView = view.findViewById(R.id.photoDisplay1);
        pictureImageView.setVisibility(View.VISIBLE);

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 申请读写内存卡内容的权限
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_SDCARD_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_food, container, false);
    }

    private void startCamera() {
        /**
         * 设置拍照得到的照片的储存目录，因为我们访问应用的缓存路径并不需要读写内存卡的申请权限，
         * 因此，这里为了方便，将拍照得到的照片存在这个缓存目录中
         */
        File file = new File(getActivity().getExternalCacheDir(), "image.jpg");
        try {
            if(file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * 因 Android 7.0 开始，不能使用 file:// 类型的 Uri 访问跨应用文件，否则报异常，
         * 因此我们这里需要使用内容提供器，FileProvider 是 ContentProvider 的一个子类，
         * 我们可以轻松的使用 FileProvider 来在不同程序之间分享数据(相对于 ContentProvider 来说)
         */
        if(Build.VERSION.SDK_INT >= 24) {
            photoUri = FileProvider.getUriForFile(getActivity(), "edu.neu.myapplication.fileprovider", file);
        } else {
            photoUri = Uri.fromFile(file); // Android 7.0 以前使用原来的方法来获取文件的 Uri
        }
        // 打开系统相机的 Action，等同于："android.media.action.IMAGE_CAPTURE"
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 设置拍照所得照片的输出目录
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
    }
    private void choiceFromAlbum() {
        // 打开系统图库的 Action，等同于: "android.intent.action.GET_CONTENT"
        Intent choiceFromAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
        // 设置数据类型为图片类型
        choiceFromAlbumIntent.setType("image/*");
        startActivityForResult(choiceFromAlbumIntent, CHOICE_FROM_ALBUM_REQUEST_CODE);
    }

    private void setPic(ImageView iv, Bitmap pic) {
        // Get the dimensions of the View
        int targetW = 60;
        int targetH = 60;

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
        iv.setImageBitmap(bitmap);
    }
}