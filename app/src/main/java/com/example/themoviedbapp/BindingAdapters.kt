package com.example.themoviedbapp

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

@BindingAdapter("app:imageUrl")
fun ImageView.imageUrl(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(this)
            .load(it)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .error(R.drawable.ic_error)
            .into(this)
    }
}

@BindingAdapter("app:customLayoutManager")
fun RecyclerView.customLayoutManager(layoutManager: String?) {
    this.layoutManager =
        when (layoutManager) {
            "LinearLayoutManager" -> LinearLayoutManager(context)
            "GridLayoutManager" -> GridLayoutManager(context, 2)
            else -> LinearLayoutManager(context)
        }
}

@BindingAdapter("app:customSrc")
fun ImageButton.customSrc(layoutManager: String?) {
    this.setImageResource(
        when (layoutManager) {
            "LinearLayoutManager" -> R.drawable.ic_linear_layout
            "GridLayoutManager" -> R.drawable.ic_grid_layout
            else -> R.drawable.ic_grid_layout
        }
    )
}