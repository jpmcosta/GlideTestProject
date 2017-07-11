package com.jpmcosta.test.glidetestproject.glide

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class TestGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setMemoryCache(LruResourceCache(10 * 1024 * 1024))
    }

    override fun registerComponents(context: Context, registry: Registry) {
        registry.append(ApplicationIcon::class.java, Bitmap::class.java, ApplicationIconDecoder(context))
        registry.append(Any::class.java, ApplicationIcon::class.java, ApplicationIconModelLoader.Factory())
    }
}