package edu.neu.myapplication.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import edu.neu.myapplication.LearningRecipeActivity;
import edu.neu.myapplication.LearningRecipeEntranceActivity;
import edu.neu.myapplication.R;
import edu.neu.myapplication.ReplacerActivity;
import edu.neu.myapplication.adapter.LearningMainAdapter;
import edu.neu.myapplication.model.LearningRecipes;

public class LearningMainFragment extends Fragment {
    TextView text,username_tv,learningNum;
    Button btn;
    ImageView img,img2,img3,img4;
    private RecyclerView mRvLearningMain;
    private List<LearningRecipes> list;
    int userLevel = 0;
    int learnedNum;

    FirebaseAuth auth;
    private FirebaseUser user;
    DatabaseReference reference,referenceRecipe;
    private String uid;
    String level;

    LearningMainAdapter adapter;


    public LearningMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.getLong("level") != null) {
                    userLevel = value.getLong("level").intValue();
                }
                if(value.getLong("learned") !=null){
                    learnedNum = value.getLong("learned").intValue();
            }
                String nameUser = value.getString("name");
                text.setText(String.valueOf("Welcome to Level "+userLevel+ " Learning Studio"));
                if(learnedNum < 2){
                    learningNum.setText("You have cooked "+learnedNum+" Time");
                }else{
                    learningNum.setText("You have cooked "+learnedNum+" Times");
                }

//                username_tv.setText(String.valueOf("Hello, "+nameUser));
                if(userLevel == 1){

//                    level = "level1";
                    DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe1").document("info");
                    DocumentReference recipe2Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe2").document("info");
                    DocumentReference recipe3Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe3").document("info");
                    DocumentReference recipe4Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe4").document("info");

                    recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });

                    recipe2Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });

                    recipe3Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });

                    recipe4Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });
                }else if(userLevel ==2){
                    DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level2").collection("recipe1").document("info");
                    DocumentReference recipe2Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level2").collection("recipe2").document("info");
                    DocumentReference recipe3Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level2").collection("recipe3").document("info");
                    DocumentReference recipe4Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level2").collection("recipe4").document("info");

                    recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });

                    recipe2Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });

                    recipe3Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });

                    recipe4Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });
                }else if (userLevel == 3){
                    DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level3").collection("recipe1").document("info");
                    DocumentReference recipe2Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level3").collection("recipe2").document("info");
                    DocumentReference recipe3Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level3").collection("recipe3").document("info");
                    DocumentReference recipe4Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level3").collection("recipe4").document("info");

                    recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });

                    recipe2Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });

                    recipe3Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });

                    recipe4Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                            String url = value.getString("img");
                            String name = value.getString("name");
                            list.add(new LearningRecipes(name,url,userLevel,learnedNum));
                        }
                    });
                }
            }
        });

        init(view);

        list = new ArrayList<>();

        mRvLearningMain.setAdapter(new LearningMainAdapter(list,getContext()));


    }

    private void init(View view) {

        reference = FirebaseDatabase.getInstance().getReference().child("LearningSystem");

        text = view.findViewById(R.id.hello);
        learningNum = view.findViewById(R.id.learningNum);

        mRvLearningMain = view.findViewById(R.id.rv_learning_main);
//        username_tv = view.findViewById(R.id.username);

        mRvLearningMain.setHasFixedSize(true);
        mRvLearningMain.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));

    }




//
//        DocumentReference recipe1Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe1").document("info");
//        DocumentReference recipe2Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe2").document("info");
//        DocumentReference recipe3Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe3").document("info");
//        DocumentReference recipe4Ref = FirebaseFirestore.getInstance().collection("LearningSystem").document("level1").collection("recipe4").document("info");



//        recipe1Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                String url = value.getString("img");
//                String name = value.getString("name");
////                String ingredients = value.getString("ingredients");
////                String step1 = value.getString("step1");
////                String step2 = value.getString("step2");
////                String step3 = value.getString("step3");
////                String step4 = value.getString("step4");
////                list.add(new LearningRecipes(name,url,ingredients,step1,step2,step3,step4));
//                list.add(new LearningRecipes(name,url));
//            }
//        });
//
//        recipe2Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                String url = value.getString("img");
//                String name = value.getString("name");
//                list.add(new LearningRecipes(name,url));
//            }
//        });
//
//        recipe3Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                String url = value.getString("img");
//                String name = value.getString("name");
//                list.add(new LearningRecipes(name,url));
//            }
//        });
//
//        recipe4Ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                String url = value.getString("img");
//                String name = value.getString("name");
//                list.add(new LearningRecipes(name,url));
//            }
//        });





}