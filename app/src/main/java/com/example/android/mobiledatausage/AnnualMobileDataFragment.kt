package com.example.android.mobiledatausage

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.android.mobiledatausage.databinding.FragmentItemListBinding
import com.example.android.mobiledatausage.viewmodel.DataViewModel

/**
 * A fragment representing a list of Items.
 */
class AnnualMobileDataFragment : Fragment() {

    private val viewModel: DataViewModel by viewModels()

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

        viewModel.response.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.adapter = AnnualMobileDataRecyclerViewAdapter(it)
        })

        return view
    }
}