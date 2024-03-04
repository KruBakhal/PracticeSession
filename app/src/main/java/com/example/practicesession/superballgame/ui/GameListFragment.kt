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
import com.example.practicesession.R
import com.example.practicesession.databinding.FragmentGameListBinding
import com.example.practicesession.databinding.FragmentSuperBallBinding
import com.example.practicesession.superballgame.model.SuperBallModel
import com.example.practicesession.superballgame.viewmodel.GameListViewModel
import com.example.practicesession.superballgame.viewmodel.SelectionViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class GameListFragment : Fragment() {
    private lateinit var binding: FragmentGameListBinding

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val viewModel: GameListViewModel by viewModels()
    lateinit var gameListAdapter: GameListAdapter
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
        binding = FragmentGameListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: SuperBallModel =
            Gson().fromJson(param1, object : TypeToken<SuperBallModel>() {}.type)

        if (model != null) {
            viewModel.superBallModel = model
            binding.toolbar.title = model.gameName
            binding.toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            setAdapter()
            viewModel.getGameList().observe(viewLifecycleOwner, Observer {
                if (it != null && it.size != 0)
                    gameListAdapter.differ.submitList(it.map { it.copy() })
            })

            binding.toolbar.setOnMenuItemClickListener { listner ->
                when (listner.itemId) {
                    R.id.btn_add -> {
                        val bundle = Bundle()
                        bundle.putString(ARG_PARAM1, Gson().toJson(model))
                        findNavController().navigate(
                            R.id.action_gameListFragment_to_selectionBallFragment,
                            bundle
                        )
                        return@setOnMenuItemClickListener true
                    }
                }
                return@setOnMenuItemClickListener false
            }
        }

    }


    private fun setAdapter() {
        gameListAdapter = GameListAdapter();
        gameListAdapter.setOnItemClickListner { superBallModel, b ->
            if (b) {
                val bundle = Bundle()
                bundle.putString(ARG_PARAM1, Gson().toJson(superBallModel))
                bundle.putBoolean(ARG_PARAM2, true)
                findNavController().navigate(
                    R.id.action_gameListFragment_to_selectionBallFragment,
                    bundle
                )
            } else {
                viewModel.deleteModel(superBallModel);
            }
        }
        binding.rvGames.adapter = gameListAdapter
    }

    private fun viewModel() {
        viewModel.listOfGame.observe(viewLifecycleOwner, Observer {
            if (it != null && it.size != 0)
                gameListAdapter.differ.submitList(it.map { it.copy() })
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment GameListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}