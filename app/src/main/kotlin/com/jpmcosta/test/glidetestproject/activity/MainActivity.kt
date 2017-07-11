package com.jpmcosta.test.glidetestproject.activity

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.jpmcosta.test.glidetestproject.R
import com.jpmcosta.test.glidetestproject.glide.ApplicationIcon
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    companion object {

        val LOG_TAG = MainActivity::class.java.name
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val iconImageView = findViewById<ImageView>(android.R.id.icon)
        val icon2ImageView = findViewById<ImageView>(android.R.id.icon2)

        testGlide(iconImageView, icon2ImageView)
    }

    private fun testGlide(iconImageView: ImageView, icon2ImageView: ImageView) {
        thread {
            val packageManager = packageManager
            val requestManager = Glide.with(this)
            packageManager.getInstalledPackages(0).forEach { packageInfo ->
                val packageName = packageInfo.packageName
                val iconUri = ApplicationIcon.createUri(packageName)
                val iconBitmap = if (applicationInfo.icon != 0) {
                    try {
                        // Load bitmap without using caches to make sure it loads the latest version.
                        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true)
                        requestManager.getBitmap(iconUri, options)
                    } catch (e: Exception) {
                        Log.e(LOG_TAG, "GlideException", e)
                        null
                    }
                } else {
                    null
                }

                // In my use case I create a signature based on iconBitmap and store it.
                // I will also check if the signature changed and update some objects if it did.

                Log.i(LOG_TAG, "iconBitmap: $iconBitmap ${iconBitmap?.isRecycled}")

                iconImageView.post {
                    // Load the bitmap into two *different sized* ImageViews.
                    // That seems to trigger the issue.
                    requestManager.loadInto(iconImageView, iconUri.toString(), options = null)
                    requestManager.loadInto(icon2ImageView, iconUri.toString(), options = null)
                }
            }
        }
    }


    private fun RequestManager.getBitmap(uri: Uri?, options: RequestOptions? = null): Bitmap? {
        if (uri == null) {
            return null
        }
        val requestBuilder = asBitmap().load(uri)
        if (options != null) {
            requestBuilder.apply(options)
        }
        return requestBuilder.submit().get()
    }

    private fun RequestManager.loadInto(view: ImageView, uriStr: String?, options: RequestOptions? = null) {
        if (uriStr == null) {
            clear(view)
            return
        }
        val requestBuilder = asDrawable().load(uriStr)
        if (options != null) {
            requestBuilder.apply(options)
        }
        requestBuilder.into(view)
    }
}
