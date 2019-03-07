package com.akexorcist.example.akexorcistprofile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.example.akexorcistprofile.R
import com.google.android.play.core.splitinstall.SplitInstallException
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import kotlinx.android.synthetic.main.activity_main.main_button_menu_blogger as buttonBlogger
import kotlinx.android.synthetic.main.activity_main.main_button_menu_github as buttonGithub
import kotlinx.android.synthetic.main.activity_main.main_button_menu_stackoverflow as buttonStackOverflow

class MainActivity : AppCompatActivity() {
    companion object {
        const val FEATURE_GITHUB = "feature_github"
        const val FEATURE_BLOGGER = "feature_blogger"
        const val FEATURE_STACKOVERFLOW = "feature_stackoverflow"
    }

    private val manager: SplitInstallManager by lazy<SplitInstallManager> {
        SplitInstallManagerFactory.create(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonGithub.setOnClickListener {
            requestDynamicFeature(
                FEATURE_GITHUB,
                onSuccess = {
                    onModuleInstallationSuccess(getString(R.string.github))
                    openActivity("com.akexorcist.example.feature_github.ui.github.GithubActivity")
                },
                onError = { exception ->
                    onModuleInstallationFailure(getString(R.string.github), exception)
                })
        }

        buttonBlogger.setOnClickListener {
            requestDynamicFeature(
                FEATURE_BLOGGER,
                onSuccess = {
                    onModuleInstallationSuccess(getString(R.string.blogger))
                    openActivity("com.akexorcist.example.feature_blogger.ui.blogger.BloggerActivity")
                },
                onError = { exception ->
                    onModuleInstallationFailure(getString(R.string.blogger), exception)
                })
        }

        buttonStackOverflow.setOnClickListener {
            requestDynamicFeature(
                FEATURE_BLOGGER,
                onSuccess = {
                    onModuleInstallationSuccess(getString(R.string.stackoverflow))
                    openActivity("com.akexorcist.example.feature_stackoverflow.ui.stackoverflow.StackOverflowActivity")
                },
                onError = { exception ->
                    onModuleInstallationFailure(getString(R.string.stackoverflow), exception)
                })
        }
    }

    private fun openActivity(className: String) {
        val intent = Intent()
        intent.setClassName(packageName, className)
        startActivity(intent)
    }

    private fun requestDynamicFeature(module: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val request = SplitInstallRequest.newBuilder().apply {
            addModule(module)
        }.build()
        manager.startInstall(request)
            .addOnSuccessListener { _ -> onSuccess() }
            .addOnFailureListener { exception -> onError(exception) }
    }

    private fun onModuleInstallationSuccess(module: String) {
        Toast.makeText(this, getString(R.string.dynamic_module_success, module), Toast.LENGTH_SHORT).show()
    }

    private fun onModuleInstallationFailure(module: String, exception: Exception) {
        Toast.makeText(this, getString(R.string.dynamic_module_failure, module, exception.message), Toast.LENGTH_SHORT)
            .show()
    }
}
