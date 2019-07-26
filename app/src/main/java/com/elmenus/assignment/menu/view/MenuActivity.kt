package com.elmenus.assignment.menu.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.elmenus.assignment.R
import com.elmenus.assignment.menu.viewModel.MenuViewModel

class MenuActivity : AppCompatActivity() {

    private lateinit var viewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)[MenuViewModel::class.java]
    }
}