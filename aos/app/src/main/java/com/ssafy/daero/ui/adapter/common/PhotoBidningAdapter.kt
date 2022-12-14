package com.ssafy.daero.ui.adapter.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ssafy.daero.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .override(1200, 1200)
        .placeholder(R.drawable.placeholder_trip_album)
        .apply(RequestOptions().centerCrop())
        .error(R.drawable.placeholder_trip_album)
        .into(view)
}

@BindingAdapter("imageFullUrl")
fun loadFullImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .placeholder(R.drawable.placeholder_trip_album)
        .apply(RequestOptions().centerCrop())
        .error(R.drawable.placeholder_trip_album)
        .into(view)
}

@BindingAdapter("roundImg")
fun loadPreference(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .override(400,400)
        .placeholder(R.drawable.placeholder_trip_album)
        .transform(CenterCrop(), RoundedCorners(16))
        .error(R.drawable.placeholder_trip_album)
        .into(view)
}

@BindingAdapter("circleImageUrl")
fun loadCircleImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .override(200,200)
        .placeholder(R.drawable.img_user)
        .apply(RequestOptions().centerCrop().circleCrop())
        .error(R.drawable.img_user)
        .into(view)
}

@BindingAdapter("likeState")
fun setLikeState(view: ImageView, likeYn: Char?) {
    likeYn?.let {
        if (it == 'y') {
            view.setImageResource(R.drawable.ic_like_full)
            view.clearColorFilter()
        } else {
            view.setImageResource(R.drawable.ic_like)
            view.setColorFilter(view.context.getColor(R.color.white))
        }
    }
}

@BindingAdapter("smallImageUrl")
fun loadSmallImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .override(400,400)
        .placeholder(R.drawable.placeholder_trip_album)
        .apply(RequestOptions().centerCrop())
        .error(R.drawable.placeholder_trip_album)
        .into(view)
}

@BindingAdapter("thumbnailImageUrl")
fun loadThumbnailImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .override(800,800)
        .placeholder(R.drawable.placeholder_trip_album)
        .apply(RequestOptions().centerCrop())
        .error(R.drawable.placeholder_trip_album)
        .into(view)
}

@BindingAdapter("roundThumbnailImg")
fun loadRoundThumbnailImage(view: ImageView, url: String?) {
    Glide.with(view)
        .load(url)
        .override(800,800)
        .placeholder(R.drawable.placeholder_trip_album)
        .transform(CenterCrop(), RoundedCorners(16))
        .error(R.drawable.placeholder_trip_album)
        .into(view)
}