package com.afume.afume_android.ui.survey

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.afume.afume_android.databinding.FragmentSurveyIncenseBinding

class SurveyIncenseFragment : Fragment() {
    private lateinit var binding: FragmentSurveyIncenseBinding
    private lateinit var incenseAdapter: CircleRecyclerViewAdapter
    private val viewModel: SurveyViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return initBinding(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvIncense()

        incenseAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
//        incenseAdapter.setPerfumeData(viewModel.perfumeList.value)
        viewModel.setActiveButton(2)
        incenseAdapter.setSeriesData(viewModel.seriesList.value)
    }

    private fun initBinding(container: ViewGroup?): View {
        binding = FragmentSurveyIncenseBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        viewModel.setActiveButton(2)
        binding.vm = viewModel
        return binding.root
    }

    private fun initRvIncense() {
        incenseAdapter = CircleRecyclerViewAdapter(1,
            add = { index -> viewModel.addSeriesList(index) },
            remove = { index: Int -> viewModel.removeSeriesList(index) })
        binding.rvSurveyIncense.adapter = incenseAdapter
        incenseAdapter.notifyDataSetChanged()
    }


}