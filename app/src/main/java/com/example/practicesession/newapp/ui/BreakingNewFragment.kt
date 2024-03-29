package com.example.practicesession.newapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.practicesession.R
import com.example.practicesession.newapp.datamodel.Resource
import com.example.practicesession.databinding.FragmentBreakingNewBinding
import com.example.practicesession.newapp.viewmodel.NewAppViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BreakingNewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class BreakingNewFragment : Fragment() {
    private lateinit var binding: FragmentBreakingNewBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val viewModel: NewAppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBreakingNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        intViewModel()
        viewModel.getBreakingNew()

    }

    private fun initAdapter() {

        binding.recyclerView.adapter = viewModel.getBreakingAdapter()
        viewModel.getBreakingAdapter().setOnItemClickListner {
            val bundle = Bundle().apply {
                putString(DetailFragment.PARAM_DATA, Gson().toJson(it))
            }
            findNavController().navigate(
                R.id.action_breakingNewFragment_to_detailFragment,
                bundle
            )
        }
    }

    private fun intViewModel() {
        viewModel.breakingNewList.observe(viewLifecycleOwner, Observer { data ->
            when (data) {
                is Resource.Error -> {
                    binding.progressCircular.visibility = View.GONE
                    Toast.makeText(context, "Something Wrong", Toast.LENGTH_LONG)
                        .show()
                }

                is Resource.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressCircular.visibility = View.GONE
                    viewModel._adapter!!.differ.submitList(data.data)
                }
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BreakingNewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BreakingNewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}