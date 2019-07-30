package com.akexorcist.example.akexorcistprofile.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.example.akexorcistprofile.R
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.rilixtech.materialfancybutton.MaterialFancyButton
import kotlinx.android.synthetic.main.activity_main.main_button_menu_blogger as buttonBlogger
import kotlinx.android.synthetic.main.activity_main.main_button_menu_github as buttonGithub
import kotlinx.android.synthetic.main.activity_main.main_button_menu_stackoverflow as buttonStackOverflow
import kotlinx.android.synthetic.main.activity_main.main_progress_bar_menu_blogger as progressBarMenuBlogger
import kotlinx.android.synthetic.main.activity_main.main_progress_bar_menu_github as progressBarMenuGithub
import kotlinx.android.synthetic.main.activity_main.main_progress_bar_menu_stackoverflow as progressBarMenuStackoverflow


class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE_INSTALL_MODULE = 2313

        const val MODULE_GITHUB = "feature_github"
        const val MODULE_BLOGGER = "feature_blogger"
        const val MODULE_STACKOVERFLOW = "feature_stackoverflow"

        const val PACKAGE_GITHUB = "com.akexorcist.example.feature_github.ui.github.GithubActivity"
        const val PACKAGE_BLOGGER = "com.akexorcist.example.feature_blogger.ui.blogger.BloggerActivity"
        const val PACKAGE_STACKOVERFLOW =
                "com.akexorcist.example.feature_stackoverflow.ui.stackoverflow.StackOverflowActivity"
    }

    private val manager: SplitInstallManager by lazy<SplitInstallManager> {
        SplitInstallManagerFactory.create(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonGithub.setOnClickListener {
            requestDynamicFeature(
                    MODULE_GITHUB,
                    onInstalling = {
                        showGithubModuleLoading()
                    },
                    onInstalled = {
                        openActivity(PACKAGE_GITHUB)
                    })
        }

        buttonBlogger.setOnClickListener {
            requestDynamicFeature(
                    MODULE_BLOGGER,
                    onInstalling = {
                        showBloggerModuleLoading()
                    },
                    onInstalled = {
                        openActivity(PACKAGE_BLOGGER)
                    })
        }

        buttonStackOverflow.setOnClickListener {
            requestDynamicFeature(
                    MODULE_STACKOVERFLOW,
                    onInstalling = {
                        showStackoverflowModuleLoading()
                    },
                    onInstalled = {
                        openActivity(PACKAGE_STACKOVERFLOW)
                    })
        }

        manager.registerListener(listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.unregisterListener(listener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_INSTALL_MODULE) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e("Confirmation Prompt", "Install Module OK ${data.toString()}")
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("Confirmation Prompt", "Install Module Canceled ${data.toString()}")
            }
        }
    }

    private val listener = SplitInstallStateUpdatedListener { state: SplitInstallSessionState ->
        when (state.status()) {
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                manager.startConfirmationDialogForResult(
                        state,
                        this@MainActivity,
                        REQUEST_CODE_INSTALL_MODULE
                )
            }
            SplitInstallSessionStatus.PENDING -> {
                updateModuleLoadingState(state.moduleNames())
            }
            SplitInstallSessionStatus.INSTALLING -> {

                updateModuleLoadingState(state.moduleNames())
            }
            SplitInstallSessionStatus.DOWNLOADING -> {
                updateModuleLoadingState(state.moduleNames())
            }
            SplitInstallSessionStatus.INSTALLED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    SplitInstallHelper.updateAppInfo(applicationContext)
                }
                val modules = state.moduleNames().joinToString(transform = { getFeatureNameByModuleName(it) })
                onModuleInstallationSuccess(modules)
                updateModuleContentState(state.moduleNames())
            }
            SplitInstallSessionStatus.FAILED -> {
                val modules = state.moduleNames().joinToString(transform = { getFeatureNameByModuleName(it) })
                onModuleInstallationFailure(modules, state.errorCode())
                updateModuleContentState(state.moduleNames())
            }
        }
    }

    private fun openActivity(className: String) {
        val intent = Intent().apply {
            setClassName(packageName, className)
        }
        if (packageManager.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, R.string.module_installing, Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestDynamicFeature(module: String, onInstalled: (String) -> Unit, onInstalling: () -> Unit) {
        if (manager.installedModules.contains(module)) {
            onInstalled(module)
            return
        }
        val request = SplitInstallRequest.newBuilder().apply {
            addModule(module)
        }.build()
        manager.startInstall(request)
        onInstalling()
    }

    private fun onModuleInstallationSuccess(module: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SplitInstallHelper.updateAppInfo(applicationContext)
        }
        Toast.makeText(
                this,
                getString(R.string.dynamic_module_success, module),
                Toast.LENGTH_SHORT
        ).show()
    }

    private fun onModuleInstallationFailure(module: String, errorCode: Int) {
        Toast.makeText(
                this,
                getString(R.string.dynamic_module_failure, module, errorCode),
                Toast.LENGTH_SHORT
        ).show()
    }

    private fun getFeatureNameByModuleName(feature: String): String = when (feature) {
        MODULE_GITHUB -> getString(R.string.github)
        MODULE_BLOGGER -> getString(R.string.blogger)
        MODULE_STACKOVERFLOW -> getString(R.string.stackoverflow)
        else -> feature
    }

    private fun updateModuleLoadingState(moduleNameList: List<String>) {
        if (moduleNameList.contains(MODULE_GITHUB)) {
            showGithubModuleLoading()
        }
        if (moduleNameList.contains(MODULE_BLOGGER)) {
            showBloggerModuleLoading()
        }
        if (moduleNameList.contains(MODULE_STACKOVERFLOW)) {
            showStackoverflowModuleLoading()
        }
    }

    private fun updateModuleContentState(moduleNameList: List<String>) {
        if (moduleNameList.contains(MODULE_GITHUB)) {
            showGithubModuleContent()
        }
        if (moduleNameList.contains(MODULE_BLOGGER)) {
            showBloggerModuleContent()
        }
        if (moduleNameList.contains(MODULE_STACKOVERFLOW)) {
            showStackoverflowModuleContent()
        }
    }

    private fun showGithubModuleContent() {
        showContent(buttonGithub, progressBarMenuGithub)
    }

    private fun showBloggerModuleContent() {
        showContent(buttonBlogger, progressBarMenuBlogger)
    }

    private fun showStackoverflowModuleContent() {
        showContent(buttonStackOverflow, progressBarMenuStackoverflow)
    }

    private fun showGithubModuleLoading() {
        showLoading(buttonGithub, progressBarMenuGithub)
    }

    private fun showBloggerModuleLoading() {
        showLoading(buttonBlogger, progressBarMenuBlogger)
    }

    private fun showStackoverflowModuleLoading() {
        showLoading(buttonStackOverflow, progressBarMenuStackoverflow)
    }

    private fun showLoading(button: MaterialFancyButton, progressBar: ProgressBar) {
        button.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    private fun showContent(button: MaterialFancyButton, progressBar: ProgressBar) {
        button.visibility = View.VISIBLE
        progressBar.visibility = View.INVISIBLE
    }
}
