package com.example.experimentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.experimentation.databinding.ItemForListBinding
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class BindingItems(
    val name: String? = null, val icon: Int? = null
        ) : AbstractBindingItem<ItemForListBinding>() {

    override val type: Int
        get() = R.id.identificator2

    override fun bindView(binding: ItemForListBinding, payloads: List<Any>) {
        with(binding) {
            this.textItemName.text = name
            Glide.with(imageViewItem.context)
                .load(icon)
                .into(imageViewItem)
        }
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ItemForListBinding {
        return ItemForListBinding.inflate(inflater, parent, false)
    }
}