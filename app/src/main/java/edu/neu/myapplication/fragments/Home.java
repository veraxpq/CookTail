package edu.neu.myapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.neu.myapplication.R;
import edu.neu.myapplication.adapter.HomeAdapter;
import edu.neu.myapplication.adapter.LearningMainAdapter;
import edu.neu.myapplication.model.HomeRecipeModel;
import edu.neu.myapplication.model.PostImageModel;
import edu.neu.myapplication.model.RecipeModel;

import static android.content.ContentValues.TAG;

public class Home extends Fragment {

    private RecyclerView mRvHome;

    HomeAdapter adapter;
    private List<HomeRecipeModel> list;
    FirebaseAuth auth;
    private FirebaseUser user;
    DatabaseReference reference;
    List<Map<String, Object>> userModelList = new ArrayList<java.util.Map<String, Object>>();
    List<Map<String, Object>> recipeList = new ArrayList<java.util.Map<String, Object>>();
    private TextView textView2;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        init(view);
        loadRecipes();


    }

    public void loadRecipes() {
        list = new ArrayList<>();
        mRvHome.setAdapter(new HomeAdapter(list,getContext()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users")
                .whereGreaterThan("recipes",0)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userModelList.add(document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        for(Map<String, Object> newUser: userModelList){
                            db.collection("Users").document(newUser.get("uid").toString()).collection("Recipes")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    Map<String, Object> recipe = document.getData();
                                                    String id = document.getId();
                                                    if (recipe.get("postImage") != null && recipe.get("recipeName") != null && recipe.get("email") != null) {

                                                        list.add(new HomeRecipeModel(recipe.get("postImage").toString(), recipe.get("recipeName").toString(), recipe.get("email").toString(), id, newUser.get("uid").toString()));
                                                        mRvHome.getAdapter().notifyDataSetChanged();
                                                    }

                                                }
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                        }
                    }
                });
    }

    private void init(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        }

        mRvHome= view.findViewById(R.id.recyclerView);
        mRvHome.setHasFixedSize(false);
        mRvHome.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

}
