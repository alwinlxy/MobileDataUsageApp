package com.example.android.mobiledatausage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.mobiledatausage.adapter.AnnualMobileDataRecyclerViewAdapter
import com.example.android.mobiledatausage.databinding.FragmentItemListBinding
import com.example.android.mobiledatausage.util.ERROR
import com.example.android.mobiledatausage.util.LOADING
import com.example.android.mobiledatausage.util.SUCCESS
import com.example.android.mobiledatausage.viewmodel.DataViewModel

/**
 * A fragment representing a list of Items.
 */
class AnnualMobileDataFragment : Fragment() {

    //get ViewModel from ViewModelProvider
    private val viewModel: DataViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, DataViewModel.Factory(activity.application))
            .get(DataViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentItemListBinding.inflate(inflater)

        val view = binding.root
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.recordList.observe(viewLifecycleOwner, {
            binding.recyclerView.adapter = AnnualMobileDataRecyclerViewAdapter(it)
        })

        viewModel.status.observe(viewLifecycleOwner, {
            when(it) {
                LOADING -> {
                    binding.status.text = getString(R.string.loading)
                }
                SUCCESS -> {
                    binding.status.text = ""
                    binding.status.visibility = View.GONE
                }
                ERROR -> {
                    binding.status.text = getString(R.string.error)
                }
            }
        })

        return view
    }
}