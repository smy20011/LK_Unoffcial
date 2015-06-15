package smy20011.lkreader.network

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import smy20011.lkreader.network.VolleySingleton
import smy20011.lkreader.util.Future
import java.net.URL
import java.net.URLConnection

public object HTTPLoader {
    fun get(ctx: Context, url: String): Future<String> {
        val future = Future<String>()
        val stringReq = StringRequest(Request.Method.GET, url, {
            future.success(it)
        }, {
            future.fail(it)
        })
        ctx.getQueue().add(stringReq)
        return future
    }
}