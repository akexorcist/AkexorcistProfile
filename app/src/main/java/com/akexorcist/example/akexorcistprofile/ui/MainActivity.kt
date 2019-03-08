package com.akexorcist.example.akexorcistprofile.ui

import android.content.Intent
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akexorcist.example.akexorcistprofile.R
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlinx.android.synthetic.main.activity_main.main_button_menu_blogger as buttonBlogger
import kotlinx.android.synthetic.main.activity_main.main_button_menu_github as buttonGithub
import kotlinx.android.synthetic.main.activity_main.main_button_menu_stackoverflow as buttonStackOverflow


class MainActivity : AppCompatActivity() {
    companion object {
        const val FEATURE_GITHUB = "feature_github"
        const val FEATURE_BLOGGER = "feature_blogger"
        const val FEATURE_STACKOVERFLOW = "feature_stackoverflow"
    }

    private val moduleState: MutableMap<String, Int> = mutableMapOf()

    private val manager: SplitInstallManager by lazy<SplitInstallManager> {
        SplitInstallManagerFactory.create(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.akexorcist.example.akexorcistprofile.R.layout.activity_main)

        buttonGithub.setOnClickListener {
            requestDynamicFeature(
                FEATURE_GITHUB,
                onSuccess = {
                    openActivity("com.akexorcist.example.feature_github.ui.github.GithubActivity")
                })
        }

        buttonBlogger.setOnClickListener {
            requestDynamicFeature(
                FEATURE_BLOGGER,
                onSuccess = {
                    openActivity("com.akexorcist.example.feature_blogger.ui.blogger.BloggerActivity")
                })
        }

        buttonStackOverflow.setOnClickListener {
            requestDynamicFeature(
                FEATURE_STACKOVERFLOW,
                onSuccess = {
                    openActivity("com.akexorcist.example.feature_stackoverflow.ui.stackoverflow.StackOverflowActivity")
                })
        }

        manager.registerListener(listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        manager.unregisterListener(listener)
    }

    private val listener = SplitInstallStateUpdatedListener { state: SplitInstallSessionState ->
        when (state.status()) {
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> {
                try {
                    startIntentSender(state.resolutionIntent().intentSender, null, 0, 0, 0)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
            SplitInstallSessionStatus.INSTALLED -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    SplitInstallHelper.updateAppInfo(applicationContext)
                }
                val modules = state.moduleNames().joinToString(transform = { getFeatureNameByModuleName(it) })
                onModuleInstallationSuccess(modules)
                state.moduleNames().map { module ->
                    moduleState.remove(module)
                }
            }
            SplitInstallSessionStatus.FAILED -> {
                val modules = state.moduleNames().joinToString(transform = { getFeatureNameByModuleName(it) })
                onModuleInstallationFailure(modules, state.errorCode())
                state.moduleNames().map { module ->
                    moduleState.remove(module)
                }
            }
            SplitInstallSessionStatus.DOWNLOADING -> {
                state.moduleNames().map { module ->
                    moduleState[module] = SplitInstallSessionStatus.DOWNLOADING
                }
            }
            SplitInstallSessionStatus.INSTALLING -> {
                state.moduleNames().map { module ->
                    moduleState[module] = SplitInstallSessionStatus.INSTALLING
                }
            }
            SplitInstallSessionStatus.PENDING -> {
                state.moduleNames().map { module ->
                    moduleState[module] = SplitInstallSessionStatus.PENDING
                }
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

    private fun requestDynamicFeature(module: String, onSuccess: () -> Unit = {}, onError: (Exception) -> Unit = {}) {
        val request = SplitInstallRequest.newBuilder().apply {
            addModule(module)
        }.build()
        if (!manager.installedModules.contains(module)) {
            manager.startInstall(request)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { exception -> onError(exception) }
        } else if (moduleState.contains(module)) {
            Toast.makeText(this, R.string.module_installing, Toast.LENGTH_SHORT).show()
        }
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
        FEATURE_GITHUB -> getString(R.string.github)
        FEATURE_BLOGGER -> getString(R.string.blogger)
        FEATURE_STACKOVERFLOW -> getString(R.string.stackoverflow)
        else -> feature
    }
}
