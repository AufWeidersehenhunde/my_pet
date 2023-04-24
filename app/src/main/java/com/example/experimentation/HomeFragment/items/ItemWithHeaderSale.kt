package com.example.experimentation.HomeFragment.items

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.experimentation.API.RepositoryRemoteItemLatest
import com.example.experimentation.API.RepositoryRemoteItemSale
import com.example.experimentation.R
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem

class ItemWithHeaderSale(
    val itemsForRecycler: List<RepositoryRemoteItemSale>? = emptyList()
) : AbstractItem<ItemWithHeaderSale.ViewHolder>() {


    override val type: Int
        get() = R.id.sale

    override val layoutRes: Int
        get() = R.layout.sale_string


    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)

    }

    class ViewHolder(view: View) : FastAdapter.ViewHolder<ItemWithHeaderSale>(view) {
        private val itemsAdapter = ItemAdapter<BindingSaleItems>()
        private val fastAdapterItem = FastAdapter.with(itemsAdapter)
        private val adapt = view.findViewById<RecyclerView>(R.id.recyclerSale)
        override fun bindView(item: ItemWithHeaderSale, payloads: List<Any>) {
            with(adapt) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = fastAdapterItem
            }
            item.itemsForRecycler?.map { BindingSaleItems(name = it.name, icon = it.image_url) }
                .let {
                    if (it != null) {
                        itemsAdapter.setNewList(it)
                    }
                }

        }


        override fun unbindView(item: ItemWithHeaderSale) {

        }
    }
}