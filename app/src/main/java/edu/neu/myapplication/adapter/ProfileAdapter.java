package edu.neu.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.neu.myapplication.R;
import edu.neu.myapplication.model.PostImageModel;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileHolder> {

    private List<PostImageModel> list;
    Context context;

    public ProfileAdapter(List<PostImageModel> list , Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileAdapter.ProfileHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_image_item, parent, false);
        return new ProfileHolder(view);


    }



    @Override
    public void onBindViewHolder(@NonNull ProfileHolder holder, int position) {

       //need list
//        Picasso.get().load(list.get(position).getImgUrl()).into(holder.profileImg);
        Picasso.get().load(list.get(position).getImageUrl()).into(holder.profileImg);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ProfileHolder extends RecyclerView.ViewHolder{

        ImageView profileImg;

        public ProfileHolder(@NonNull View itemView) {
            super(itemView);
            profileImg = itemView.findViewById(R.id.profile_imageView);

        }
    }


}
