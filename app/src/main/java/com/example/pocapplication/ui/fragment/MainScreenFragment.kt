package com.example.pocapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pocapplication.R
import com.example.pocapplication.databinding.FragmentMainBinding
import com.example.pocapplication.adapter.MainScreenListAdapter
import com.example.pocapplication.models.BaseResult
import com.example.pocapplication.models.Response
import com.example.pocapplication.viewmodel.MainScreenViewModel
import com.example.pocapplication.ui.MainActivity

class MainScreenFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(MainScreenViewModel::class.java)
        observeFields()
    }

    private fun observeFields() {
        viewModel.getResult().observe(this, Observer<BaseResult<Response>> {
            if(viewModel.isSuccessful(it)) {
                (binding.rvItemList.adapter as MainScreenListAdapter).setListItems(it.response?.rows)
                (activity as MainActivity).setScreenTitle(it.response?.title)
            } else {
                setErrorMessage(it.errorMessage ?: "")
            }
            binding.srlSwipeRefresh.isRefreshing = false
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
        getResponseFromServer(false)
        binding.srlSwipeRefresh.setOnRefreshListener { getResponseFromServer(true) }
        binding.rvItemList.layoutManager = LinearLayoutManager(activity)
        activity?.let {
            binding.rvItemList.adapter = MainScreenListAdapter(it, null)
        }
    }

    private fun getResponseFromServer(force: Boolean) {
        if(viewModel.isNetworkAvailable(activity)) {
            binding.srlSwipeRefresh.isRefreshing = true
            viewModel.getResponse(force)
        } else
            setErrorMessage("It seems like internet is not available. Please connect and try again.")
    }

    companion object{
        fun newInstance(args: Bundle?): MainScreenFragment {
            val instance = MainScreenFragment()
            if(args != null)
                instance.arguments = args
            return instance
        }
    }
}