package smy20011.lkreader.network

import android.content.Context
import android.os.AsyncTask
import com.android.volley.RequestQueue
import org.jsoup.Jsoup
import smy20011.lkreader.util.Future
import java.net.URL

public object BookUtil {
    public data class Book(val title: String, val series: String, val coverImg: String, val page: String)

    fun getBookList(html: String): Future<List<Book>> {
        val future = Future<List<Book>>()
        val task = object : AsyncTask<Void, Void, List<Book>>() {
            var exception: Exception? = null
            override fun doInBackground(vararg params: Void?): List<Book>? {
                try {
                    // Parse html
                    val doc = Jsoup.parse(html)
                    val books = doc.select(".lk-m-index-booklist li")
                    val result = IntRange(0, books.size() - 1)
                            .map {
                                val link = books.get(it).select("a").first()
                                val info = books.get(it).select("p")
                                Book(
                                        title = info.get(1).text(),
                                        series = info.get(0).text(),
                                        coverImg = link.attr("data-cover"),
                                        page = link.attr("href")
                                )
                            }
                    return result
                } catch (e: Exception) {
                    exception = e
                    return null
                }
            }

            override fun onPostExecute(result: List<Book>?) {
                super.onPostExecute(result)
                if (exception != null) {
                    future.fail(exception!!)
                } else {
                    future.success(result!!)
                }
            }
        }
        task.execute()
        return future
    }
}