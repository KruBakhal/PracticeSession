package com.example.practicesession.newapp.ui

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.practicesession.databinding.FragmentDetailBinding
import com.example.practicesession.newapp.datamodel.Article
import com.example.practicesession.newapp.viewmodel.NewAppViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var artcile: Article? = null
    private lateinit var binding: FragmentDetailBinding

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
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favStaus.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.btnFav.imageTintList = ColorStateList.valueOf(Color.RED)
            } else {
                binding.btnFav.imageTintList = ColorStateList.valueOf(Color.WHITE)
            }
        })
        arguments?.getString(PARAM_DATA)?.let {
            binding.webview.apply {
                webViewClient = WebViewClient()
                val json = it
                artcile = Gson().fromJson(json, Article::class.java)
                artcile?.url?.let { it1 -> loadUrl(it1) }
                viewModel.getStatusArticle(artcile!!)
                binding.btnFav.setOnClickListener {
                    viewModel.performFavOurite(artcile!!)
                }
            }
        }

    }

    companion object {

        val PARAM_DATA = "articeles"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}