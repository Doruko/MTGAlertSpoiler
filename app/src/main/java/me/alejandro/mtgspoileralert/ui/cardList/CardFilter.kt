package me.alejandro.mtgspoileralert.ui.cardList

import android.widget.Filter
import me.alejandro.mtgspoileralert.domain.model.card.Card
import java.util.*


class CardFilter(private val adapter: CardListAdapter, private val list: List<Card>) : Filter() {
    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val results = FilterResults()
        if (constraint != null && constraint.isNotEmpty()) {
            val filteredCards = list.filter {
                it.name.toUpperCase(Locale.getDefault())
                    .contains(constraint.toString().toUpperCase(Locale.getDefault()))
            }
            results.count = filteredCards.size
            results.values = filteredCards
        } else {
            results.count = list.size
            results.values = list
        }
        return results
    }

    @Suppress("UNCHECKED_CAST")
    override fun publishResults(p0: CharSequence, p1: FilterResults) {
        adapter.updateSetList(p1.values as List<Card>)
        adapter.notifyDataSetChanged()
    }
}