package com.example.experimentation.HomeFragment.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.experimentation.R
import com.example.experimentation.databinding.ItemForListBinding
import com.example.experimentation.databinding.LatestItemBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class BindingLatestItems(
    val name: String? = null, val icon: String? = null
) : AbstractBindingItem<LatestItemBinding>() {

    override val type: Int
        get() = R.id.latest

    override fun bindView(binding: LatestItemBinding, payloads: List<Any>) {
        with(binding) {
            this.petName.text = name
            Glide.with(petPhoto.context)
                .load(icon)
                .into(petPhoto)
            this.documentIcon.isVisible = false
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): LatestItemBinding {
        return LatestItemBinding.inflate(inflater, parent, false)
    }
}