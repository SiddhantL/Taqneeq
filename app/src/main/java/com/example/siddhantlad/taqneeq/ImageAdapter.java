package com.example.siddhantlad.taqneeq;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;


public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    FirebaseStorage storageRef;
    String url2,idEvent;
    private int[] mImageIds = new int[]{R.drawable.cssevent, R.drawable.rowboatevent, R.drawable.ohdamevent, R.drawable.lightupevent/*, R.drawable.flashcodingevent*/};

    ImageAdapter(Context context, String eventID) {
        mContext = context; idEvent=eventID;
    }

    @Override
    public int getCount() {
      //  return mImageIds.length;
    return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(mContext);
        storageRef=FirebaseStorage.getInstance();
            storageRef.getReference().child(idEvent+"/"+idEvent+Integer.toString(position+1)+".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    url2 = uri.toString();
                    Glide.with(mContext).load(url2).into(imageView);
                }
                });
        storageRef.getReference().child(idEvent+"/"+idEvent+Integer.toString(position+1)+".png").getDownloadUrl().addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
           storageRef.getReference().child(idEvent+"/"+idEvent+"1.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
               @Override
               public void onSuccess(Uri uri) {
                       Glide.with(mContext).load(uri.toString()).into(imageView);
    }
});
        }
        });
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // imageView.setImageResource(mImageIds[position]);
        String url="https://firebasestorage.googleapis.com/v0/b/taqneeq-51499.appspot.com/o/-Lndjnjd%2Ftesteventimage3.png?alt=media&token=fe1ad6b6-4fef-47dd-a47c-3a2f92024ad9";//Retrieved url as mentioned above
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}