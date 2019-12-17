package com.klimaatmobiel.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projecten3android.databinding.GridListHeaderBinding
import com.example.projecten3android.databinding.GridListItemBinding
import com.klimaatmobiel.domain.Animations
import com.klimaatmobiel.domain.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * Used to tell the [RecyclerView] which items it can reuse to load new data in
 */
private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class ProductListAdapter(private val onClickListener: OnClickListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffCallback), Filterable {

    private val adapterScore = CoroutineScope(Dispatchers.Default)

    /**
     * Filters only the list of [Product] and resubmits the list
     */
    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filtered: MutableList<DataItem> = ArrayList()
                var result = FilterResults()


                for (x in 0 until itemCount) {
                    if (getItemViewType(x) == 1 && getItem(x).productName.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filtered.add(getItem(x))
                    }
                }

                result.values = filtered
                return result
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results!!.values as List<DataItem>)
                notifyDataSetChanged()
            }

        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.equals(newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder(GridListHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            ITEM_VIEW_TYPE_ITEM -> ProductViewHolder(GridListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ProductViewHolder -> {
                val productItem = getItem(position) as DataItem.ProductItem
                holder.bind(productItem.product, onClickListener)
            }
            is TextViewHolder -> {
                val cat = getItem(position) as DataItem.Header
                holder.bind(cat)
            }
        }
    }

    /**
     * Return the type of the current item
     */
    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.ProductItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    /**
     * Add header to the front of the list
     */
    fun addHeaderAndSubmitList(list: List<Product>?) {
        adapterScore.launch {

            val test = list
            val sList: MutableList<DataItem> = ArrayList()
            var cat = list!![0].category!!.categoryName
            sList.add(DataItem.Header(cat))

            list!!.forEach {
                if(it.category!!.categoryName == cat) {
                    sList.add(DataItem.ProductItem(it))
                } else {
                    cat = it.category.categoryName
                    sList.add(DataItem.Header(cat))
                    sList.add(DataItem.ProductItem(it))
                }
            }

            withContext(Dispatchers.Main) {
                submitList(sList)
                notifyDataSetChanged()
            }
        }
    }

    fun submitListNoHeaders(list: List<Product>?) {
        adapterScore.launch {

            val result: MutableList<DataItem> = ArrayList()

            list!!.forEach {
                result.add(DataItem.ProductItem(it))
            }

            withContext(Dispatchers.Main) {
                submitList(result)
                notifyDataSetChanged()
            }
        }
    }

    class OnClickListener(val clickListener: (product: Product, action: Int) -> Unit) {
        fun onClick(product: Product, action: Int) = clickListener(product, action)
    }

    class ProductViewHolder(private var binding: GridListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, clickListener: OnClickListener) {
            binding.product = product
            //binding.addToCartImage.setOnClickListener {
            //    Animations().toggleArrow(it)
            //}
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridListItemBinding.inflate(layoutInflater, parent, false)
                return ProductViewHolder(binding)
            }
        }
    }

    class TextViewHolder(private var binding: GridListHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: DataItem.Header) {
            binding.name = product.name
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridListHeaderBinding.inflate(layoutInflater, parent, false)
                return TextViewHolder(binding)
            }
        }
    }
}

sealed class DataItem {
    data class ProductItem(val product: Product): DataItem() {
        override val id = product.productId
        override val productName = product.productName
    }
    data class Header(val name: String): DataItem() {
        override val id = Long.MIN_VALUE
        override val productName = name
    }

    abstract val id: Long
    abstract val productName: String
}