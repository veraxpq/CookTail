package edu.neu.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.neu.myapplication.model.RecipeModel;

import static android.content.ContentValues.TAG;

public class RankingActivity extends AppCompatActivity {
    
    List<Map<String, Object>> rankingList = new ArrayList<Map<String, Object>>();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ListView ranking = (ListView)findViewById(R.id.ranking);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users")
                .orderBy("level", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String level = document.getData().get("level").toString();
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("avatar", R.drawable.ic_avatar);
                                map.put("userName", document.getData().get("email").toString());
                                map.put("level", "Level: " + level);
                                rankingList.add(map);
                            }
                            SimpleAdapter adapter = new SimpleAdapter(RankingActivity.this , rankingList, R.layout.ranking_item , new String[]{"avatar", "userName","level"} , new int[]{R.id.avatar,R.id.userName,R.id.level});
                            ranking.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }
}