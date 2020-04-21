package me.alejandro.mtgspoileralert.ui.cardList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.databinding.ItemCardBinding
import me.alejandro.mtgspoileralert.model.card.Card

class CardListAdapter : RecyclerView.Adapter<CardListAdapter.ViewHolder>() {
    private lateinit var cardList: List<Card>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_card,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = if (::cardList.isInitialized) cardList.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cardList[position])
    }

    fun updateSetList(cardList: List<Card>) {
        this.cardList = cardList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = CardItemViewModel()

        fun bind(card: Card) {
            viewModel.bind(card)
            binding.viewModel = viewModel
        }
    }
}