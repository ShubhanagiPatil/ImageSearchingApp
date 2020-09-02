package com.cavista.imagesearchingapp.Adapters;

/**
 * Created by ravi on 31/01/18.
 */

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cavista.imagesearchingapp.Activities.ImageDetailActivity;
import com.cavista.imagesearchingapp.Database.DatabaseHandlerHandler;
import com.cavista.imagesearchingapp.Model.ImageData;
import com.cavista.imagesearchingapp.Model.ImageJsonObject;
import com.cavista.imagesearchingapp.Model.SearchImages;
import com.cavista.imagesearchingapp.R;

import java.util.List;



public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {
    private Context context;
    private List<ImageData> imageList;
    private ImagesAdapterListener listener;
    DatabaseHandlerHandler db;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public MyViewHolder(View view) {
            super(view);

            image = view.findViewById(R.id.image);

           /* view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(contactList));
                }
            });*/
        }
    }


    public ImagesAdapter(Context context, List<ImageData> imageList, ImagesAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.imageList = imageList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ImageData imageData = imageList.get(position);
        List<ImageJsonObject> imageJsonObjectList=imageData.getImageJsonObject();

        if(imageJsonObjectList!=null){
            Glide.with(context)
                    .load(imageJsonObjectList.get(0).getLink())
                    .apply(RequestOptions.fitCenterTransform())
                    .into(holder.image);
        }


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ImageDetailActivity.class);

                intent.putExtra("imageId",imageData.getId());
                if(imageData.getTital()!=null)
                intent.putExtra("imageName",imageData.getTital());
                intent.putExtra("imageLink",imageJsonObjectList.get(0).getLink());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public interface ImagesAdapterListener {
        void onImageSelected(SearchImages contact);

    }
}
