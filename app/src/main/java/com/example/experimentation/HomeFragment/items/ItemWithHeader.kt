package com.example.experimentation.HomeFragment.items

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.experimentation.API.RepositoryRemoteItemLatest
import com.example.experimentation.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem

class ItemWithHeader(
    val itemsForRecycler: List<RepositoryRemoteItemLatest>? = emptyList()
) : AbstractItem<ItemWithHeader.ViewHolder>() {


    override val type: Int
        get() = R.id.latest

    override val layoutRes: Int
        get() = R.layout.latest_string


    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)

    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<ItemWithHeader>(view) {
        private val itemsAdapter = ItemAdapter<BindingLatestItems>()
        private val fastAdapterItem = FastAdapter.with(itemsAdapter)
        private val adapt = view.findViewById<RecyclerView>(R.id.recyclerLatest)
        override fun bindView(item: ItemWithHeader, payloads: List<Any>) {
            with(adapt) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = fastAdapterItem
            }
            item.itemsForRecycler?.map { BindingLatestItems(name = it.name, icon = it.image_url) }
                .let {
                    if (it != null) {
                        itemsAdapter.setNewList(it)
                    }
                }

        }


        override fun unbindView(item: ItemWithHeader) {

        }
    }
}