package com.example.experimentation.HomeFragment.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.experimentation.R
import com.example.experimentation.databinding.LatestItemBinding
import com.example.experimentation.databinding.SaleItemBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class BindingSaleItems(
    val name: String? = null, val icon: String? = null
) : AbstractBindingItem<SaleItemBinding>() {

    override val type: Int
        get() = R.id.sale

    override fun bindView(binding: SaleItemBinding, payloads: List<Any>) {
        with(binding) {
            this.petName.text = name
            this.documentIcon.isVisible = false
            Glide.with(petPhoto.context)
                .load(icon)
                .into(petPhoto)
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): SaleItemBinding {
        return SaleItemBinding.inflate(inflater, parent, false)
    }
}