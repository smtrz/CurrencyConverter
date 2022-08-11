package com.tahir.paypay.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tahir.paypay.R
import com.tahir.paypay.databinding.ConverstionItemBinding
import com.tahir.paypay.models.CurrencyRates

class CurrencyAdapter(context: Context) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {
    private val context: Context = context
    private lateinit var binding: ConverstionItemBinding
    private lateinit var items: List<CurrencyRates>

    /**
     * sets the list and call notifyDataSetChanged()
     * @param currency_list
     */
    fun setList(currency_list: List<CurrencyRates>) {
        items = currency_list
        notifyDataSetChanged()
    }

    /**
     * Sets the binding instance using DataBindingUtil, Called when RecyclerView needs a new
     * RecyclerView.ViewHolder of the given type to represent an item.
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.converstion_item,
                parent,
                false
            )
        return ViewHolder(binding)
    }

    /** Class created to sets up the click listeners, update views. */
    inner class ViewHolder(val binding: ConverstionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CurrencyRates) {
            binding.rates = item
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return items!!.size
    }
}
