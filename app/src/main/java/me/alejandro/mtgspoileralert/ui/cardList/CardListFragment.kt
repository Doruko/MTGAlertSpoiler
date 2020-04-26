package me.alejandro.mtgspoileralert.ui.cardList

import android.os.Bundle
import android.view.*
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.databinding.FragmentCardListBinding
import me.alejandro.mtgspoileralert.injection.viewModelFactory
import me.alejandro.mtgspoileralert.ui.cardList.cardDialog.CardDialog
import me.alejandro.mtgspoileralert.ui.cardList.cardDialog.CardDialogViewModel


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
        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_list, container, false)
        binding.lifecycleOwner = this

        val root = binding.root
        binding.cardList.layoutManager = GridLayoutManager(activity, 2)

        viewModel =
            ViewModelProvider(this, viewModelFactory { CardListViewModel(args.setCode) }).get(
                CardListViewModel::class.java
            )

        with(viewLifecycleOwner) {
            viewModel.errorMessage.observe(this, Observer { errorMessage ->
                if (errorMessage != null) showError(errorMessage) else hideError()
            })
            viewModel.cardBigUrl.observe(this, Observer { url ->
                showDialog(url)
            })
        }

        binding.viewModel = viewModel

        return root
    }

    private fun showDialog(url: String) {
        CardDialog(requireActivity()).apply {
            show()
        }.also {
            it.setViewModel(
                ViewModelProvider(this, viewModelFactory { CardDialogViewModel() }).get(
                    CardDialogViewModel::class.java
                )
            )
            it.loadCardImage(url)
        }
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackBar =
            Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE).apply {
                setAction(R.string.retry, viewModel.errorClickListener)
                show()
            }
    }

    private fun hideError() {
        errorSnackBar?.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView: SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.cardListAdapter.filter.filter(query)
                return false
            }
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) item.collapseActionView()

        })
    }
}
