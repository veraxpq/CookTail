package edu.neu.myapplication.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.neu.myapplication.MainActivity;
import edu.neu.myapplication.R;
import edu.neu.myapplication.ReplacerActivity;
import edu.neu.myapplication.adapter.ViewPagerAdapter;
import edu.neu.myapplication.utils.PhotoUtil;
import edu.neu.myapplication.utils.UploadRecipeUtil;

import static android.app.Activity.RESULT_OK;

public class Add extends Fragment {
    private TextView cancel, post, diff;
    private ImageView recipeImage, createRecipeBackground;
    private EditText recipeNameET, ingredientsET, prepareTimeET, bakingTimeET, restingTimeET, description;
    private ImageButton difficultyBtn1, difficultyBtn2, difficultyBtn3, difficultyBtn4, difficultyBtn5;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private String difficulty = "0";
    private ProgressBar progressBar;
    private String timeStamp;
    private String imageFileName;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String url;
    Bitmap imageBitmap;

    public Add() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        clickListener();
    }

    private void init(View view) {

        progressBar = view.findViewById(R.id.progressBar);
        cancel = view.findViewById(R.id.cancel);
        post = view.findViewById(R.id.post);
        recipeImage = view.findViewById(R.id.createRecipeImage);
        createRecipeBackground = view.findViewById(R.id.createRecipeBackground);
        recipeNameET = view.findViewById(R.id.recipeNameET);
        ingredientsET = view.findViewById(R.id.ingredientsET);
        prepareTimeET = view.findViewById(R.id.prepareTimeET);
        bakingTimeET = view.findViewById(R.id.bakingTimeET);
        restingTimeET = view.findViewById(R.id.restingTimeET);
        description = view.findViewById(R.id.description);
        difficultyBtn1 = view.findViewById(R.id.difficultyBtn1);
        difficultyBtn2 = view.findViewById(R.id.difficultyBtn2);
        difficultyBtn3 = view.findViewById(R.id.difficultyBtn3);
        difficultyBtn4 = view.findViewById(R.id.difficultyBtn4);
        difficultyBtn5 = view.findViewById(R.id.difficultyBtn5);
        diff = view.findViewById(R.id.difficulty);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    private void clickListener() {
        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(view);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearPage();
            }
        });
        difficultyBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultyBtn1.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn2.setBackgroundResource(R.drawable.ic_star);
                difficultyBtn3.setBackgroundResource(R.drawable.ic_star);
                difficultyBtn4.setBackgroundResource(R.drawable.ic_star);
                difficultyBtn5.setBackgroundResource(R.drawable.ic_star);
                difficulty = "1";
            }
        });
        difficultyBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultyBtn1.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn2.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn3.setBackgroundResource(R.drawable.ic_star);
                difficultyBtn4.setBackgroundResource(R.drawable.ic_star);
                difficultyBtn5.setBackgroundResource(R.drawable.ic_star);
                difficulty = "2";
            }
        });
        difficultyBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultyBtn1.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn2.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn3.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn4.setBackgroundResource(R.drawable.ic_star);
                difficultyBtn5.setBackgroundResource(R.drawable.ic_star);
                difficulty = "3";
            }
        });
        difficultyBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultyBtn1.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn2.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn3.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn4.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn5.setBackgroundResource(R.drawable.ic_star);
                difficulty = "4";
            }
        });
        difficultyBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficultyBtn1.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn2.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn3.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn4.setBackgroundResource(R.drawable.ic_star_fill);
                difficultyBtn5.setBackgroundResource(R.drawable.ic_star_fill);
                difficulty = "5";
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeName = recipeNameET.getText().toString();
                String ingredients = ingredientsET.getText().toString();
                String prepareTime = prepareTimeET.getText().toString();
                String bakingTime = bakingTimeET.getText().toString();
                String restingTime = restingTimeET.getText().toString();
                String des = description.getText().toString();
                if (recipeName.isEmpty()) {
                    recipeNameET.setError("please input valid recipe name");
                    return;
                }
                if (ingredients.isEmpty()) {
                    ingredientsET.setError("please input valid ingredients");
                    return;
                }
                if(difficulty.equals("0")) {
                    diff.setError("please choose difficulty");
                    return;
                }
                if (prepareTime.isEmpty()) {
                    prepareTimeET.setError("please input valid prepare time");
                    return;
                }
                if (bakingTime.isEmpty()) {
                    bakingTimeET.setError("please input valid baking time");
                    return;
                }
                if (restingTime.isEmpty()) {
                    restingTimeET.setError("please input valid resting time");
                    return;
                }
                if (des.isEmpty()) {
                    description.setError("please input valid description");
                    return;
                }
                if (imageBitmap == null) {
                    Toast.makeText(getContext(), "You forgot to upload picture!", Toast.LENGTH_SHORT).show();
                    return;
                }
                createRecipe(recipeName, ingredients, prepareTime, bakingTime, restingTime, des, difficulty);
            }
        });

    }


    private void createRecipe(String recipeName, String ingredients, String prepareTime,  String bakingTime, String restingTime, String description, String difficulty) {
        Map<String, Object> map = new HashMap<>();
        map.put("recipeName", recipeName);
        map.put("ingredients", ingredients);
        map.put("prepareTime", prepareTime);
        map.put("bakingTime", bakingTime);
        map.put("restingTime", restingTime);
        map.put("description", description);
        map.put("difficulty", difficulty);
        map.put("email", user.getEmail());
        map.put("userId",user.getUid());
        UploadRecipeUtil upload = new UploadRecipeUtil(timeStamp, imageBitmap, user, imageFileName, map);
        upload.start();
        Toast.makeText(getContext(), "You have post a new recipe successfully!", Toast.LENGTH_SHORT).show();
        clearPage();

        DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
        Map<String, Object> data = new HashMap<>();
        data.put("recipes", 1);
        userRef.set(data, SetOptions.merge());

    }

    private void clearPage(){
        recipeNameET.setText("");
        ingredientsET.setText("");
        prepareTimeET.setText("");
        bakingTimeET.setText("");
        restingTimeET.setText("");
        description.setText("");
        difficultyBtn1.setBackgroundResource(R.drawable.ic_star);
        difficultyBtn2.setBackgroundResource(R.drawable.ic_star);
        difficultyBtn3.setBackgroundResource(R.drawable.ic_star);
        difficultyBtn4.setBackgroundResource(R.drawable.ic_star);
        difficultyBtn5.setBackgroundResource(R.drawable.ic_star);
        imageBitmap = null;
        createRecipeBackground.setImageBitmap(null);
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
        imageBitmap = (Bitmap) extras.get("data");
        createRecipeBackground.setImageBitmap(imageBitmap);
    }
}
