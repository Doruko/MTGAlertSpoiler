package me.alejandro.mtgspoileralert.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import me.alejandro.mtgspoileralert.R
import me.alejandro.mtgspoileralert.databinding.FragmentSplashBinding
import me.alejandro.mtgspoileralert.injection.viewModelFactory

class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashViewModel

    override fun onResume() {
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        super.onResume()
    }

    override fun onPause() {
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.actionBar?.hide()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(
            this,
            viewModelFactory { SplashViewModel() }
        ).get(SplashViewModel::class.java)

        viewModel.nextStep.observe(viewLifecycleOwner, Observer {
            handleNavigation(it)
        })

        return binding.root
    }

    private fun handleNavigation(splashState: SplashState) {
        when (splashState) {
            SplashState.SetLists -> {
                findNavController().navigate(R.id.action_nav_splashFragment_to_setsFragment)
            }
        }
    }
}