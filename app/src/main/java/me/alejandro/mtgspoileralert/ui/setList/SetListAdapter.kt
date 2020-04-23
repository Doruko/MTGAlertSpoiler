package me.alejandro.mtgspoileralert.ui.setList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.databinding.ItemSetBinding
import me.alejandro.mtgspoileralert.model.set.Set

class SetListAdapter() : RecyclerView.Adapter<SetListAdapter.ViewHolder>() {
    private lateinit var setList: List<Set>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSetBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_set,
            parent,
            false
        )
        return ViewHolder(binding).apply {
            itemView.setOnClickListener { view ->
                val code = setList[this.adapterPosition].code
                val action = SetListFragmentDirections.actionNavSetListToSetFragment(code)
                view.findNavController().navigate(action)
            }
        }
    }

    override fun getItemCount() = if (::setList.isInitialized) setList.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(setList[position])
    }

    fun updateSetList(setList: List<Set>) {
        this.setList = setList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemSetBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = SetItemViewModel()

        fun bind(set: Set) {
            viewModel.bind(set)
            binding.viewModel = viewModel
        }
    }
}