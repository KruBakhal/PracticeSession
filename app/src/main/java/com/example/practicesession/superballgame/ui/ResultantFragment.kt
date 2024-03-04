package com.example.practicesession.superballgame.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newappdi.NewsApp.Adapter.GameListAdapter
import com.example.newappdi.NewsApp.Adapter.ResultantAdapter
import com.example.practicesession.R
import com.example.practicesession.databinding.FragmentGameListBinding
import com.example.practicesession.databinding.FragmentResultantBinding
import com.example.practicesession.superballgame.viewmodel.GameListViewModel
import com.example.practicesession.superballgame.viewmodel.ResultantViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ResultantFragment : Fragment() {
    private lateinit var gameListAdapter: ResultantAdapter
    private lateinit var binding: FragmentResultantBinding
    val viewModel: ResultantViewModel by viewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.title = "All Game Result"
        setAdapter()
        viewModel.listOfGame.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size != 0)
                gameListAdapter.differ.submitList(it.map { it.copy() })
        })
        viewModel.getGameList()
    }

    private fun setAdapter() {
        gameListAdapter = ResultantAdapter();
        gameListAdapter.setOnItemClickListner { superBallModel, b ->

        }
        binding.rvResultant.adapter = gameListAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentResultantBinding.inflate(inflater, container, false)

        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResultantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}