package com.example.practicesession.superballgame.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.newappdi.NewsApp.Adapter.NormalSuperAdapter
import com.example.newappdi.NewsApp.Adapter.SelectorAdapter
import com.example.newappdi.NewsApp.Adapter.SuperAdapter
import com.example.practicesession.R
import com.example.practicesession.databinding.FragmentSelectionBallBinding
import com.example.practicesession.superballgame.model.BallTypeModel
import com.example.practicesession.superballgame.model.SuperBallModel
import com.example.practicesession.superballgame.viewmodel.SelectionViewModel
import com.example.practicesession.utils.AdaptiveSpacingItemDecoration
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SelectionBallFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SelectionBallFragment : Fragment() {
    private lateinit var selectedAdapter: SelectorAdapter
    private lateinit var normalAdapter: NormalSuperAdapter
    private lateinit var superAdapter: SuperAdapter
    private lateinit var binding: FragmentSelectionBallBinding
    val viewModel: SelectionViewModel by viewModels()

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getBoolean(ARG_PARAM2, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSelectionBallBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val model: SuperBallModel =
            Gson().fromJson(param1, object : TypeToken<SuperBallModel>() {}.type)
        if (model != null) {
            viewModel.superBallModel = model
            binding.toolbar.title = model.gameName
            if (model.maxSuper == 0)
                binding.rvSuper.visibility = View.GONE
            binding.tvSelected.setText("Select Total: ${model.maxNormal + model.maxSuper}")
            binding.tvNormal.setText("Normal Range: ${model.nRangeMin} to + ${model.nRangeMax}  Max: ${model.maxNormal}")
            binding.tvSuper.setText("Super Range: ${model.sRangeMin} to + ${model.sRangeMax} Max: ${model.maxSuper}")
            setAdapter();
            viewModel();
            click();
            viewModel.setupData(model.maxNormal, model.maxSuper)
            if (param2) {
                viewModel.syncWithDBGameData()
            }
        }
    }

    private fun click() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.setOnMenuItemClickListener { listner ->
            when (listner.itemId) {
                R.id.btn_save -> {
                    viewModel.saveToDB()
                    return@setOnMenuItemClickListener true
                }
            }
            return@setOnMenuItemClickListener false
        }

    }

    private fun viewModel() {
        viewModel.updateSelected.observe(viewLifecycleOwner, Observer {
            if (it) {
                val list = mutableListOf<BallTypeModel>()
                list.addAll(viewModel.listSelected as Collection<BallTypeModel>)
                selectedAdapter.submitList(list.map {
                    it.copy()
                })
                selectedAdapter.notifyDataSetChanged()
            }
        })
        viewModel.updateNormal.observe(viewLifecycleOwner, Observer {
            if (it) {
                val list = mutableListOf<BallTypeModel>()
                list.addAll(viewModel.listNormal.clone() as Collection<BallTypeModel>)
                normalAdapter.submitList(list.map {
                    it.copy()
                })
                normalAdapter.notifyDataSetChanged()
            }
        })
        viewModel.updateSuper.observe(viewLifecycleOwner, Observer {
            if (it) {
                val list = mutableListOf<BallTypeModel>()
                list.addAll(viewModel.listSuper as Collection<BallTypeModel>)
                superAdapter.submitList(list.map {
                    it.copy()
                })
                superAdapter.notifyDataSetChanged()
            }
        })
        viewModel.saveStatus.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().popBackStack()
            }
        })
    }

    private fun setAdapter() {
        selectedAdapter = SelectorAdapter()
        selectedAdapter.setOnItemClickListner { model, pos ->

        }
        binding.rvSelected.adapter = selectedAdapter

        normalAdapter = NormalSuperAdapter()
        normalAdapter.setOnItemClickListner { model, pos ->
            viewModel.addToNormal(pos, model)
        }
        binding.rvNormal.adapter = normalAdapter

        superAdapter = SuperAdapter()
        superAdapter.setOnItemClickListner { model, pos ->
            viewModel.addToSuper(pos, model)

        }
        binding.rvSuper.adapter = superAdapter
        binding.rvNormal.addItemDecoration(AdaptiveSpacingItemDecoration(16, true))
        binding.rvSuper.addItemDecoration(AdaptiveSpacingItemDecoration(16, true))
        binding.rvSelected.addItemDecoration(AdaptiveSpacingItemDecoration(10, true))
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: Boolean) =
            SelectionBallFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putBoolean(ARG_PARAM2, param2)
                }
            }
    }
}