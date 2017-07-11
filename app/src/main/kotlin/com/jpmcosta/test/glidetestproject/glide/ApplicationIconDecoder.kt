package com.jpmcosta.test.glidetestproject.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapResource

class ApplicationIconDecoder(private val context: Context) : ResourceDecoder<ApplicationIcon, Bitmap> {

    private val bitmapPool: BitmapPool = GlideApp.get(context).bitmapPool


    override fun handles(source: ApplicationIcon, options: Options?): Boolean = true

    override fun decode(source: ApplicationIcon, with: Int, height: Int, options: Options?): Resource<Bitmap>? {
        val packageName = source.packageName
        val packageManager = context.packageManager
        val icon = packageManager.getApplicationIcon(packageName)
        return BitmapResource.obtain(icon.bitmap, bitmapPool)
    }


    private val Drawable.bitmap: Bitmap
        get() {
            if (this is BitmapDrawable) {
                val bitmapDrawable = this
                if (bitmapDrawable.bitmap != null) {
                    return Bitmap.createBitmap(bitmapDrawable.bitmap)
                }
            }

            val bitmap = if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
                // Single color bitmap will be created of 1x1 pixel.
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
            } else {
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            }

            val canvas = Canvas(bitmap)
            setBounds(0, 0, canvas.width, canvas.height)
            draw(canvas)

            return bitmap
        }
}