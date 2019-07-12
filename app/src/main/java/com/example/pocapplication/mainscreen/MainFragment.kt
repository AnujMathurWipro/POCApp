package com.example.pocapplication.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocapplication.MainActivity
import com.example.pocapplication.R
import com.example.pocapplication.databinding.FragmentMainBinding
import com.example.pocapplication.models.RowsItem

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainScreenViewModel::class.java)
        observeFields()
    }

    private fun observeFields() {
        viewModel.getResponseList().observe(this, Observer<List<RowsItem?>> {
            (binding.rvItemList.adapter as MainScreenListAdapter).setListItems(it)
        })

        viewModel.getResponseTitle().observe(this, Observer<String> {
            (activity as MainActivity).setScreenTitle(it)
        })

        viewModel.getResponseError().observe(this, Observer<String> {
            binding.srlSwipeRefresh.isRefreshing = false
            setErrorMessage(it)
        })
    }

    private fun setErrorMessage(message: String) {
        binding.tvErrorText.text = message
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getResponseFromServer()
        binding.srlSwipeRefresh.setOnRefreshListener { getResponseFromServer() }
        binding.rvItemList.layoutManager = LinearLayoutManager(activity)
        binding.rvItemList.adapter = MainScreenListAdapter(activity, null)
    }

    private fun getResponseFromServer() {
        if(viewModel.isNetworkAvailable(activity)) {
            binding.srlSwipeRefresh.isRefreshing = true
            viewModel.getResponse()
        } else
            setErrorMessage("It seems like internet is not available. Please connect and try again.")
    }

    companion object{
        fun newInstance(args: Bundle?): MainFragment {
            val instance = MainFragment()
            if(args != null)
                instance.arguments = args
            return instance
        }
    }
}