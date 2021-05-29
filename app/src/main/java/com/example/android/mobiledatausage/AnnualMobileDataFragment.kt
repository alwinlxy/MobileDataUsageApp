package com.example.android.mobiledatausage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.mobiledatausage.adapter.AnnualMobileDataRecyclerViewAdapter
import com.example.android.mobiledatausage.databinding.FragmentItemListBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentItemListBinding.inflate(inflater)

        val view = binding.root
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.recordList.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.adapter = AnnualMobileDataRecyclerViewAdapter(it)
        })

        return view
    }
}