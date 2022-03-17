package com.dice.core.abstraction

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivityBinding<B : ViewBinding> : AppCompatActivity() {

    private var _binding: B? = null
    val binding get() = _binding!!

    abstract fun getViewBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = getViewBinding()
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}