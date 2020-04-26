package me.alejandro.mtgspoileralert.ui.cardList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.databinding.ItemCardBinding
import me.alejandro.mtgspoileralert.domain.model.card.Card

class CardListAdapter(val cardClickListener: CardClickListener) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>(), Filterable {
    private lateinit var cardList: List<Card>
    private var cardFilter: CardFilter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_card,
            parent,
            false
        )
        return ViewHolder(binding).apply {
            itemView.setOnClickListener {
                val card = cardList[this.adapterPosition]
                cardClickListener.cardClicked(card.image_uris.png)
            }
        }
    }

    override fun getItemCount() = if (::cardList.isInitialized) cardList.size else 0

    override fun getFilter(): Filter {
        cardFilter = cardFilter ?: CardFilter(this, cardList)
        return cardFilter as CardFilter
    }

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