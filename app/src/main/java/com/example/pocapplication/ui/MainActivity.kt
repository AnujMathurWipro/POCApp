package com.example.pocapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.pocapplication.R
import com.example.pocapplication.databinding.ActivityMainBinding
import com.example.pocapplication.ui.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        addMainFragment()
    }

    private fun addMainFragment() {
        val frag = MainFragment.newInstance(null)
        addFragment(frag, binding?.flFragmentContainer?.id)
    }

    private fun addFragment(frag: MainFragment, id: Int?) {
        addFragment(frag, id, false)
    }

    private fun addFragment(frag: MainFragment, id: Int?, addToBackStack: Boolean) {
        val fragTransaction = supportFragmentManager.beginTransaction()
        fragTransaction.replace(id ?: 0, frag, frag.javaClass.simpleName)
        if(addToBackStack)
            fragTransaction.addToBackStack(null)
        fragTransaction.commit()
    }

    fun setScreenTitle(title: String?) {
        binding?.toolbar?.title = title
    }
}
