package com.akexorcist.example.akexorcistprofile.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.example.akexorcistprofile.R
import kotlinx.android.synthetic.main.activity_main.main_button_menu_blogger as buttonBlogger
import kotlinx.android.synthetic.main.activity_main.main_button_menu_github as buttonGithub
import kotlinx.android.synthetic.main.activity_main.main_button_menu_stackoverflow as buttonStackOverflow

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonGithub.setOnClickListener {
            openActivity("com.akexorcist.example.feature_github.ui.github.GithubActivity")
        }

        buttonBlogger.setOnClickListener {
            openActivity("com.akexorcist.example.feature_blogger.ui.blogger.BloggerActivity")
        }

        buttonStackOverflow.setOnClickListener {
            openActivity("com.akexorcist.example.feature_stackoverflow.ui.stackoverflow.StackOverflowActivity")
        }
    }

    private fun openActivity(className: String) {
        val intent = Intent()
        intent.setClassName(packageName, className)
        startActivity(intent)
    }
}
