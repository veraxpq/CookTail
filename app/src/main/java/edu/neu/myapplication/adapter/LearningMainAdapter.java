package edu.neu.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import edu.neu.myapplication.LearningRecipeActivity;
import edu.neu.myapplication.LearningRecipeEntranceActivity;
import edu.neu.myapplication.R;
import edu.neu.myapplication.model.LearningRecipes;

public class LearningMainAdapter extends RecyclerView.Adapter<LearningMainAdapter.LearningHolder> {

    private List<LearningRecipes> list;
    Context context;

    public LearningMainAdapter(List<LearningRecipes> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LearningHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_items, parent, false);
        return new LearningHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningMainAdapter.LearningHolder holder, int position) {
        String name = list.get(position).getRecipeName();
        holder.recipeName.setText(name);

        Picasso.get().load(list.get(position).getImgUrl()).into(holder.recipeImg);

        int level = list.get(position).getRecipeLevel();
        int learned = list.get(position).getLearnedNum();

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), LearningRecipeActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("level",level);
                    intent.putExtra("learned",learned);
                    context.startActivity(intent);
            }
        });

//        holder.setClickListener();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class LearningHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        ImageView recipeImg;
        LinearLayout linearLayout;

        public LearningHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.tv_name_item);
            recipeImg = itemView.findViewById(R.id.tv_recipe_img);
            linearLayout = itemView.findViewById(R.id.recipe_item);
        }

//        void setClickListener(){
//            recipeImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    int position = getAdapterPosition();
//                    Intent intent = new Intent(itemView.getContext().getApplicationContext(), LearningRecipeActivity.class);
//                    intent.putExtra("position",position);
//                    intent.putExtra("level",level);
//                    itemView.getContext().startActivity(intent);
//
//                }
//            });
//
//        }
    }
}
