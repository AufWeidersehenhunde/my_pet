package com.example.experimentation.HomeFragment.items

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.experimentation.BindingItems
import com.example.experimentation.Items
import com.example.experimentation.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem


class SimpleItem(
    val itemsForRecycler: List<Items>? = null
) : AbstractItem<SimpleItem.ViewHolder>() {


    override val type: Int
        get() = R.id.identificator

    override val layoutRes: Int
        get() = R.layout.list_items


    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)

    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<SimpleItem>(view) {
        private val itemsAdapter = ItemAdapter<BindingItems>()
        private val fastAdapterItem = FastAdapter.with(itemsAdapter)
        private val adapt = view.findViewById<RecyclerView>(R.id.recyclerItems)
        override fun bindView(item: SimpleItem, payloads: List<Any>) {
            with(adapt) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = fastAdapterItem
            }
            item.itemsForRecycler?.map { BindingItems(name = it.name, icon = it.image) }
                ?.let { itemsAdapter.setNewList(it) }

        }


        override fun unbindView(item: SimpleItem) {

        }
    }
}