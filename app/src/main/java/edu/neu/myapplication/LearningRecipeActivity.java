package edu.neu.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class LearningRecipeActivity extends AppCompatActivity {


    StepsView mStepsView;
    Button buttonUp,buttonDown;
    TextView content,positionTextView;
    ImageView imgRecipe;
    FirebaseAuth auth;
    FirebaseUser user;
    String recipeNumber;
    int position;
    int level;
    int learnednumber;
    private LearningRecipes mLearningRecipes = new LearningRecipes();

    String[] descriptionData = {"Ingredients","Step1","Step2","Step3","Step4"};
    int current_state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning_recipe);

        content = (TextView)findViewById(R.id.content_tv);
        mStepsView = (StepsView)findViewById(R.id.stepsView);
        imgRecipe = (ImageView)findViewById(R.id.imgv);
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position",1);
        level = intent.getExtras().getInt("level",1);
        learnednumber = intent.getExtras().getInt("learned",0);

        if(level == 1){
            content.setText(mLearningRecipes.getlevel1Step(position,0));
        }else if(level ==2){
            content.setText(mLearningRecipes.getlevel2Step(position,0));
        }else{
            content.setText(mLearningRecipes.getlevel3Step(position,0));
        }

        mStepsView.setLabels(descriptionData)
                .setBarColorIndicator(Color.BLACK)
                .setProgressColorIndicator(getResources().getColor(R.color.colorGreen))
                .setLabelColorIndicator(getResources().getColor(R.color.colorGreen))
                .setCompletedPosition(0)
                .drawView();

        mStepsView.setCompletedPosition(current_state);
        buttonUp = (Button)findViewById(R.id.btn_up);
        buttonDown = (Button)findViewById(R.id.btn_down);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
//        DocumentReference Ref = FirebaseFirestore.getInstance().collection("Users")


        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                int userLevel = value.getLong("level").intValue();
                if(userLevel == 1){
                    if(position == 0){
                        getlevel1Step(0);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe1").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });
                    }else if(position ==1){
                        getlevel1Step(1);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe2").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });

                    }else if(position == 2){
                        getlevel1Step(2);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe3").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });

                    }else if(position == 3){
                        getlevel1Step(3);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe4").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });
                    }

                }
                else if(userLevel == 2){
                    if(position == 0){
                        getlevel2Step(0);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level2").collection("recipe1").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });
                    }else if(position ==1){
                        getlevel2Step(1);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level2").collection("recipe2").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });

                    }else if(position == 2){
                        getlevel2Step(2);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level2").collection("recipe3").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });

                    }else if(position ==3){
                        getlevel2Step(3);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level2").collection("recipe4").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });
                    }


                }else if(userLevel == 3){
                    if(position == 0){
                        getlevel3Step(0);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level3").collection("recipe1").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });
                    }else if(position ==1){
                        getlevel3Step(1);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level3").collection("recipe2").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });

                    }else if(position == 2){
                        getlevel3Step(2);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level3").collection("recipe3").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });

                    }else if(position ==3){
                        getlevel3Step(3);
                        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level3").collection("recipe4").document("info");
                        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                String url = value.getString("img");
                                String name = value.getString("name");
                                Picasso.get().load(url).into(imgRecipe);
                            }
                        });
                    }
                }
            }
        });


    }


    public void getlevel1Step(int position){
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_state < (descriptionData.length -1)){
                    current_state ++;
                    mStepsView.setCompletedPosition(current_state).drawView();
                    content.setText(mLearningRecipes.getlevel1Step(position,current_state));
                }else if(current_state == (descriptionData.length -1)){
                    mStepsView.setCompletedPosition(current_state).drawView();
                    content.setText(mLearningRecipes.getlevel1Step(position,current_state));
                    buttonUp.setText("Finish!");
                    current_state ++;
                }
                else{
                    learnednumber++;
                    DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
                    Map<String, Object> data = new HashMap<>();
                    data.put("learned", learnednumber);
                    userRef.set(data, SetOptions.merge());

                    Intent intent = new Intent(LearningRecipeActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LearningRecipeActivity.this, "Good Job!", Toast.LENGTH_SHORT).show();
                }
                Log.d("current state:",current_state+"");
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_state > 0){
                    current_state = current_state -1;
                    buttonUp.setText("Next");
                    mStepsView.setCompletedPosition(current_state).drawView();
                    content.setText(mLearningRecipes.getlevel1Step(position,current_state));
                }
                Log.d("current state:",current_state+"");
            }
        });

    }



    public void getlevel2Step(int position){
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_state < (descriptionData.length -1)){
                    current_state ++;
                    mStepsView.setCompletedPosition(current_state).drawView();
                    content.setText(mLearningRecipes.getlevel2Step(position,current_state));
                }else if(current_state == (descriptionData.length -1)){
                    mStepsView.setCompletedPosition(current_state).drawView();
                    content.setText(mLearningRecipes.getlevel2Step(position,current_state));
                    buttonUp.setText("Finish!");
                    current_state ++;
                }
                else{
                    learnednumber++;
                    DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
                    Map<String, Object> data = new HashMap<>();
                    data.put("learned", learnednumber);
                    userRef.set(data, SetOptions.merge());

                    Intent intent = new Intent(LearningRecipeActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LearningRecipeActivity.this, "Good Job!", Toast.LENGTH_SHORT).show();

                }

                Log.d("current state:",current_state+"");
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_state > 0){
                    current_state = current_state -1;
                    buttonUp.setText("Next");
                    mStepsView.setCompletedPosition(current_state).drawView();
                    content.setText(mLearningRecipes.getlevel2Step(position,current_state));
                }
                Log.d("current state:",current_state+"");
            }
        });

    }

    public void getlevel3Step(int position){
        buttonUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_state < (descriptionData.length -1)){
                    current_state ++;
                    mStepsView.setCompletedPosition(current_state).drawView();
                    content.setText(mLearningRecipes.getlevel3Step(position,current_state));
                }else if(current_state == (descriptionData.length -1)){
                    mStepsView.setCompletedPosition(current_state).drawView();
                    content.setText(mLearningRecipes.getlevel3Step(position,current_state));
                    buttonUp.setText("Finish!");
                    current_state ++;

                }
                else{
                    learnednumber++;
                    DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
                    Map<String, Object> data = new HashMap<>();
                    data.put("learned", learnednumber);
                    userRef.set(data, SetOptions.merge());

                    Intent intent = new Intent(LearningRecipeActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LearningRecipeActivity.this, "Good Job!", Toast.LENGTH_SHORT).show();
                }
                Log.d("current state:",current_state+"");
            }
        });

        buttonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_state > 0){
                    current_state = current_state -1;
                    buttonUp.setText("Next");
                    mStepsView.setCompletedPosition(current_state).drawView();
                    content.setText(mLearningRecipes.getlevel3Step(position,current_state));
                }
                Log.d("current state:",current_state+"");
            }
        });

    }
}