package smy20011.lkreader.network

import android.content.Context
import android.graphics.Bitmap
import android.util.LruCache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley

public object VolleySingleton {
    var queue: RequestQueue? = null
    var imageLoader: ImageLoader? = null

    private class ImageLoaderCache(size: Int) : LruCache<String, Bitmap>(size), ImageLoader.ImageCache {
        override fun putBitmap(url: String?, bitmap: Bitmap?) {
            put(url, bitmap)
        }

        override fun getBitmap(url: String?): Bitmap? {
            return get(url)
        }

        override fun sizeOf(key: String?, value: Bitmap): Int {
            return value.getRowBytes() * value.getHeight()
        }
    }

    // Get default cache size of image loader
    fun getImageCacheSize(ctx: Context): Int {
        val metrics = ctx.getResources().getDisplayMetrics()
        val screenBytes = metrics.widthPixels * metrics.heightPixels * 4
        return screenBytes * 3
    }

    fun get(ctx: Context): VolleySingleton {
        if (queue == null) {
            queue = Volley.newRequestQueue(ctx)
            imageLoader = ImageLoader(queue, ImageLoaderCache(getImageCacheSize(ctx)))
        }
        return this
    }
}

fun Context.getQueue(): RequestQueue = VolleySingleton.get(this).queue!!
fun Context.getImageLoader(): ImageLoader = VolleySingleton.get(this).imageLoader!!
