package me.alejandro.mtgspoileralert.ui.cardList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar

import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.databinding.FragmentCardListBinding
import me.alejandro.mtgspoileralert.injection.viewModelFactory

class CardListFragment : Fragment() {

    private lateinit var binding: FragmentCardListBinding
    private lateinit var viewModel: CardListViewModel
    private val args: CardListFragmentArgs by navArgs()

    private var errorSnackBar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_list, container, false)
        val root = binding.root
        binding.cardList.layoutManager = GridLayoutManager(activity, 2)

        viewModel =
            ViewModelProvider(this, viewModelFactory { CardListViewModel(args.setCode) }).get(
                CardListViewModel::class.java
            )
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.viewModel = viewModel
        return root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackBar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackBar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackBar?.show()
    }

    private fun hideError() {
        errorSnackBar?.dismiss()
    }

}
