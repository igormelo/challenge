package com.igormelo.challenge.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by root on 26/01/17.
 */

public class ImageUtils {
    public static void loadImage(Context context,String url, ImageView imgUser){
        Glide.with(context)
                .load(url)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imgUser);
    }
}
