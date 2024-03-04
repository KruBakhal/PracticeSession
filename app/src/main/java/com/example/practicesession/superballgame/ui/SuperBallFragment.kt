package com.example.practicesession.superballgame.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import com.example.practicesession.R
import com.example.practicesession.databinding.FragmentSuperBallBinding
import com.example.practicesession.superballgame.model.BallTypeModel
import com.example.practicesession.superballgame.model.SuperBallModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SuperBallFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class SuperBallFragment : Fragment() {
    private var selected: Int = 0
    private lateinit var binding: FragmentSuperBallBinding

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuperBallBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            val position = selected
            if (position == 0)
                return@setOnClickListener
            val bundle = Bundle()
            var superMOdel: SuperBallModel? = null
            var gameName = ""
            var maxNormal = 5;
            var maxSuper = 1;
            var nRangeMin: Int = 0
            var nRangeMax: Int = 0
            var sRangeMin: Int = 0
            var sRangeMax: Int = 0
            gameName =
                requireContext().resources.getStringArray(R.array.game_array).get(position)
            when (position) {


                1 -> {
                    maxNormal = 5;
                    maxSuper = 1;
                    nRangeMin = 1
                    nRangeMax = 40
                    sRangeMin = 1
                    sRangeMax = 12
                }

                2 -> {
                    maxNormal = 5;
                    maxSuper = 1;
                    nRangeMin = 10
                    nRangeMax = 40
                    sRangeMin = 1
                    sRangeMax = 12
                }

                3 -> {
                    maxNormal = 5;
                    maxSuper = 1;
                    nRangeMin = 15
                    nRangeMax = 30
                    sRangeMin = 1
                    sRangeMax = 15
                }

                4 -> {
                    maxNormal = 5;
                    maxSuper = 1;
                    nRangeMin = 1
                    nRangeMax = 40
                    sRangeMin = 0
                    sRangeMax = 0
                }

                5 -> {

                    maxNormal = 5;
                    maxSuper = 1;
                    nRangeMin = 55
                    nRangeMax = 85
                    sRangeMin = 0
                    sRangeMax = 0

                }
            }
            superMOdel = SuperBallModel(
                gameName = gameName,
                selectedList = arrayListOf<BallTypeModel>(),
                maxNormal = maxNormal,
                maxSuper = maxSuper,
                nRangeMin = nRangeMin,
                nRangeMax = nRangeMax,
                sRangeMin = sRangeMin,
                sRangeMax = sRangeMax
            )
            bundle.putString(ARG_PARAM1, Gson().toJson(superMOdel))

            findNavController().navigate(
                R.id.action_superBallFragment_to_gameListFragment,
                bundle
            )
        }
        binding.btnResult.setOnClickListener {
            findNavController().navigate(
                R.id.action_superBallFragment_to_resultantFragment
            )
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selected = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = SuperBallFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }
}