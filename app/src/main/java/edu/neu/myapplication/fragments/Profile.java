package edu.neu.myapplication.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.neu.myapplication.R;
import edu.neu.myapplication.RankingActivity;
import edu.neu.myapplication.adapter.ProfileAdapter;
import edu.neu.myapplication.model.PostImageModel;
import edu.neu.myapplication.model.RecipeModel;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class Profile extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextView usernameTV, statusTV, followerCountTv, followingCountTv, postCountTv, toolBarNameTV, textView3,editUsername;
    private EditText editStatus;
    private Button followBtn, updateStatusBtn, rankingEnter,updateUsernameBtn;
    private CircleImageView profileImage;
    private RecyclerView recyclerView;

    private LinearLayout countLayout;

    private FirebaseAuth auth;
    private FirebaseUser user;

    boolean isMyProfile = true;

    private RecyclerView mRvProfile;
    String uid;
//    FirestoreRecyclerAdapter<PostImageModel, PostImageHolder> adapter;
    List<Map<String, Object>> postList = new ArrayList<Map<String, Object>>();

    private List<PostImageModel> list;

    public Profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
        list = new ArrayList<>();

        mRvProfile.setAdapter(new ProfileAdapter(list,getContext()));

        if (isMyProfile) {
            followBtn.setVisibility(View.GONE);
            countLayout.setVisibility(View.VISIBLE);
        } else {
            followBtn.setVisibility(View.VISIBLE);
            countLayout.setVisibility(View.GONE);
        }

        loadBasicData();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent(view);
            }
        });
        rankingEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RankingActivity.class);
                startActivity(intent);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));

//        loadPostImages();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(user.getUid()).collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                postList.add(document.getData());
                                for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                                    list.add(new PostImageModel(entry.getKey(),entry.getValue().toString()));
                                    textView3.setVisibility(View.GONE);
                                }
                            }
                        } else {
                            textView3.setText("You do not have any post, please go to post page to create one!");
                        }
                    }
                });



//        recyclerView.setAdapter(adapter);
    }

    private void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(view.getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            updateProfile(imageBitmap);
            profileImage.setImageBitmap(imageBitmap);
        }
    }

    private void loadBasicData() {

        DocumentReference userRef = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());

        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                assert value != null;
                if (value.exists()) {
                    String name = value.getString("name");
                    String status = value.getString("status");
                    int followers = 0;
                    int following = 0;
                    int posts = 0;
                    if (value.getLong("followers") != null) {
                        followers = value.getLong("followers").intValue();
                    }
                    if (value.getLong("following") != null) {
                        following = value.getLong("following").intValue();
                    }
                    String profileURL = value.getString("profileImage");

                    if (name != null && name.length() != 0) {
                        usernameTV.setText(name);
                    } else {
                        usernameTV.setText("Create your username");
                    }
                    if (status != null && status.length() != 0) {
                        statusTV.setText(status);
                    } else {
                        statusTV.setText("Create your status");
                    }
                    if (value.getLong("posts") != null) {
                        posts = value.getLong("posts").intValue();
                    }
                    followerCountTv.setText(String.valueOf(followers));
                    followingCountTv.setText(String.valueOf(following));
                    postCountTv.setText("1");
                    toolBarNameTV.setText(name);

                    Glide.with(getContext().getApplicationContext())
                            .load(profileURL)
                            .placeholder(R.drawable.ic_avatar)
                            .timeout(6500)
                            .into(profileImage);

                    usernameTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            usernameTV.setVisibility(View.GONE);
                            editUsername.setVisibility(View.VISIBLE);
                            updateUsernameBtn.setVisibility(View.VISIBLE);

                            rankingEnter.setVisibility(View.GONE);
                        }
                    });

                    updateUsernameBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String username = editUsername.getText().toString();
                            editUsername.setVisibility(View.GONE);
                            updateUsernameBtn.setVisibility(View.GONE);
                            Map<String, Object> data = new HashMap<>();
                            data.put("name", username);
                            userRef.set(data, SetOptions.merge());
                            usernameTV.setText(username);
                            usernameTV.setVisibility(View.VISIBLE);

                            rankingEnter.setVisibility(View.VISIBLE);
                        }
                    });

                    statusTV.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            statusTV.setVisibility(View.GONE);
                            editStatus.setVisibility(View.VISIBLE);
                            updateStatusBtn.setVisibility(View.VISIBLE);

                            rankingEnter.setVisibility(View.GONE);
                        }
                    });
                    updateStatusBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String status = editStatus.getText().toString();
                            editStatus.setVisibility(View.GONE);
                            updateStatusBtn.setVisibility(View.GONE);
                            Map<String, Object> data = new HashMap<>();
                            data.put("status", status);
                            userRef.set(data, SetOptions.merge());
                            statusTV.setText(status);
                            statusTV.setVisibility(View.VISIBLE);

                            rankingEnter.setVisibility(View.VISIBLE);
                        }
                    });

                }

            }
        });
    }

//    private void updateProfile(Bitmap imageBitmap) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("profileImage", imageBitmap);
//        FirebaseFirestore.getInstance().collection("Users").document(user.getUid())
//                .set(map)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            assert getContext() != null;
//                            startActivity(new Intent(getContext().getApplicationContext(), MainActivity.class));
//                            getActivity().finish();
//                        } else {
//                            Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//        profileImage.setImageBitmap(imageBitmap);
//    }

    private void init(View view) {

        mRvProfile = view.findViewById(R.id.recyclerView2);
        mRvProfile.setHasFixedSize(true);
        mRvProfile.setLayoutManager(new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false));

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        assert getActivity() != null;
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        profileImage = view.findViewById(R.id.profileImageHome);
        usernameTV = view.findViewById(R.id.usernameTV);
        editUsername = view.findViewById(R.id.editUsername);
        updateUsernameBtn = view.findViewById(R.id.updateUsernameBtn);
        statusTV = view.findViewById(R.id.status);
        toolBarNameTV = view.findViewById(R.id.toolbarNameTV);
        followerCountTv = view.findViewById(R.id.followerCountTv);
        followingCountTv = view.findViewById(R.id.followingCountTv);
        postCountTv = view.findViewById(R.id.postCountTv);
        followBtn = view.findViewById(R.id.followBtn);
        recyclerView = view.findViewById(R.id.recyclerView2);
        countLayout = view.findViewById(R.id.countLayout);
        editStatus = view.findViewById(R.id.editStatus);
        updateStatusBtn = view.findViewById(R.id.updateStatusBtn);
        rankingEnter = view.findViewById(R.id.rankingEnter);
        textView3 = view.findViewById(R.id.textView3);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

//    private void loadPostImages() {
//
//        if (isMyProfile) {
//            uid = user.getUid();
//        } else {
//
//        }
//
//        uid = user.getUid();
//
//        DocumentReference reference = FirebaseFirestore.getInstance().collection("Users").document(uid);
//
//        Query query = reference.collection("Images");
//
//        FirestoreRecyclerOptions<PostImageModel> options = new FirestoreRecyclerOptions.Builder<PostImageModel>()
//                .setQuery(query, PostImageModel.class)
//                .build();
//
//        adapter = new FirestoreRecyclerAdapter<PostImageModel, PostImageHolder>(options) {
//            @NonNull
//            @Override
//            public PostImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_image_item, parent, false);
//                return new PostImageHolder(view);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull PostImageHolder holder, int position, @NonNull PostImageModel model) {
//
//                Glide.with(holder.itemView.getContext().getApplicationContext())
//                        .load(model.getImageUrl())
//                        .into(holder.imageView);
//            }
//        };
//
//    }

//    private static class PostImageHolder extends RecyclerView.ViewHolder {
//
//        private ImageView imageView;
//
//
//        public PostImageHolder(@NonNull View itemView) {
//            super(itemView);
//
//            imageView = itemView.findViewById(R.id.profile_imageView);
//        }
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}

