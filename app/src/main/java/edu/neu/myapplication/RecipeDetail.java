package edu.neu.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class RecipeDetail extends AppCompatActivity {
    int position;
    String userId, id, bakingTime,description,difficulty,ingredients,prepareTime,recipeName,restingTime;
    TextView recipeDetailName, ingredientsDetail, prepareTimeMinDetail, bakingTimeMinDetail, restingTimeMinDetail, descriptionDetail;
    ImageView recipeDetailImage, difficulty1, difficulty2, difficulty3, difficulty4, difficulty5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        //get info from home fragment
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position",0);
        userId = intent.getExtras().getString("userId","no user id");
        id = intent.getExtras().getString("id","no id");


        recipeDetailName = (TextView) findViewById(R.id.recipeDetailName);
        ingredientsDetail = (TextView) findViewById(R.id.ingredientsDetail);
        prepareTimeMinDetail = (TextView) findViewById(R.id.prepareTimeMinDetail);
        bakingTimeMinDetail = (TextView) findViewById(R.id.bakingTimeMinDetail);
        restingTimeMinDetail = (TextView) findViewById(R.id.restingTimeMinDetail);
        descriptionDetail = (TextView) findViewById(R.id.descriptionDetail);
        recipeDetailImage = (ImageView) findViewById(R.id.recipeDetailImage);
        difficulty1 = (ImageView) findViewById(R.id.difficulty1);
        difficulty2 = (ImageView) findViewById(R.id.difficulty2);
        difficulty3 = (ImageView) findViewById(R.id.difficulty3);
        difficulty4 = (ImageView) findViewById(R.id.difficulty4);
        difficulty5 = (ImageView) findViewById(R.id.difficulty5);


        DocumentReference recipeRef = FirebaseFirestore.getInstance().collection("Users").document(userId).collection("Recipes").document(id);

        recipeRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    return;
                }
                assert value != null;
                if (value.exists()) {
                    Picasso.get().load(value.getString("postImage")).into(recipeDetailImage);
                    recipeDetailName.setText(value.getString("recipeName"));
                    ingredientsDetail.setText("Ingredients:  " + value.getString("ingredients"));
                    prepareTimeMinDetail.setText(value.getString("prepareTime") + "  min");
                    bakingTimeMinDetail.setText(value.getString("bakingTime") + "  min");
                    restingTimeMinDetail.setText(value.getString("restingTime") + "  min");
                    descriptionDetail.setText("Description:  " + value.getString("description"));
                    difficulty = value.getString("difficulty");
                    if(difficulty.equals("1")){
                        difficulty1.setBackgroundResource(R.drawable.ic_star_fill);
                    }
                    if(difficulty.equals("2")){
                        difficulty1.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty2.setBackgroundResource(R.drawable.ic_star_fill);
                    }
                    if(difficulty.equals("3")){
                        difficulty1.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty2.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty3.setBackgroundResource(R.drawable.ic_star_fill);
                    }
                    if(difficulty.equals("4")){
                        difficulty1.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty2.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty3.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty4.setBackgroundResource(R.drawable.ic_star_fill);
                    }
                    if(difficulty.equals("5")){
                        difficulty1.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty2.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty3.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty4.setBackgroundResource(R.drawable.ic_star_fill);
                        difficulty5.setBackgroundResource(R.drawable.ic_star_fill);
                    }
                }
            }
        });

        // Enables Always-on
    }
}