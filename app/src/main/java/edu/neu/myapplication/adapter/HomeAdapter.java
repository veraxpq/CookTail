package edu.neu.myapplication.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.neu.myapplication.R;
import edu.neu.myapplication.RecipeDetail;
import edu.neu.myapplication.model.HomeRecipeModel;
import edu.neu.myapplication.model.RecipeModel;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {

    private List<HomeRecipeModel> list;
    Context context;
    FirebaseFirestore db;

    public HomeAdapter(List<HomeRecipeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items, parent, false);
        return new HomeHolder(view);

    }

//    @Override
//    public void onBindViewHolder(@NonNull HomeAdapter.HomeHolder holder, int position) {
//
//    }

//    @Override
//    public int getItemCount() {
//        return 0;
//    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeHolder holder, int position) {

        holder.userNameTv.setText(list.get(position).getMenuName());

        holder.recipeNameTv.setText(list.get(position).getEmail());

//        Random random = new Random();

//        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));

//        if (context != null) {
//            Glide.with(holder.profileImage.getContext())
//                    .load(R.drawable.ic_avatar)
//                    .timeout(6500)
//                    .into(holder.profileImage);

//        Glide.with(holder.recipeImage.getContext())
//                .load(list.get(position).getImageUrl())
//                .placeholder(new ColorDrawable(color))
//                .timeout(7000)
//                .into(holder.recipeImage);

        Picasso.get().load(list.get(position).getImageUrl()).into(holder.recipeImage);

        holder.likeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                holder.OnLikeTheRecipe(position);
            }
        });

        holder.setClickListener();
    }



//        String recipeName = list.get(position).getRecipeName();
//        holder.textView2.setText(recipeName);



    @Override
    public int getItemCount() {
        return list.size();
    }
//
//    public class HomeHolder extends RecyclerView.ViewHolder {
//        TextView textView2;
//
//        public HomeHolder(@NonNull View itemView) {
//            super(itemView);
//            textView2 = itemView.findViewById(R.id.textView2);
//        }
//    }

    class HomeHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView userNameTv, recipeNameTv;
        private ImageView recipeImage;
        private ImageButton likeBtn;
        private boolean isLike = false;


        public HomeHolder(@NonNull View itemView) {
            super(itemView);
//            profileImage = itemView.findViewById(R.id.profileImageHome);
            userNameTv = itemView.findViewById(R.id.nameTv);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            recipeNameTv = itemView.findViewById(R.id.recipeName);
        }

        void setClickListener() {

            recipeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    Intent intent = new Intent(itemView.getContext().getApplicationContext(), RecipeDetail.class);
                    intent.putExtra("position", position);
                    intent.putExtra("id", list.get(position).getId());
                    intent.putExtra("userId", list.get(position).getUserId());
                    itemView.getContext().startActivity(intent);
                }
            });
        }

        public void OnLikeTheRecipe(int position) {
            if (isLike) {
                likeBtn.setBackgroundResource(R.drawable.ic_heart);
                isLike = false;
            } else {
                likeBtn.setBackgroundResource(R.drawable.ic_heart_fill);
                isLike = true;
            }
        }
    }
}