package com.jpmcosta.test.glidetestproject.glide

import android.net.Uri
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoader.LoadData
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.bumptech.glide.signature.ObjectKey
import com.jpmcosta.test.glidetestproject.glide.ApplicationIcon.Companion.URI_SCHEME_APPLICATION_ICON

class ApplicationIconModelLoader : ModelLoader<Any, ApplicationIcon> {

    override fun buildLoadData(model: Any, width: Int, height: Int, options: Options?): LoadData<ApplicationIcon>? {
        val uriStr: String
        val uri: Uri

        when (model) {
            is String -> {
                uriStr = model
                uri = Uri.parse(uriStr)
            }
            is Uri -> {
                uri = model
                uriStr = uri.toString()
            }
            else -> throw IllegalStateException("model must either be Uri or String.")
        }

        val packageName = uri.schemeSpecificPart

        return LoadData(ObjectKey(uriStr), ApplicationIconDataFetcher(packageName))
    }

    override fun handles(model: Any): Boolean =
            when (model) {
                is String -> Uri.parse(model).scheme == URI_SCHEME_APPLICATION_ICON
                is Uri -> model.scheme == URI_SCHEME_APPLICATION_ICON
                else -> false
            }


    class Factory : ModelLoaderFactory<Any, ApplicationIcon> {

        override fun build(multiFactory: MultiModelLoaderFactory?): ModelLoader<Any, ApplicationIcon> =
                ApplicationIconModelLoader()

        override fun teardown() {
            // Do nothing.
        }
    }
}