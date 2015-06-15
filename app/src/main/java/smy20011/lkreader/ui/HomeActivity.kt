package smy20011.lkreader.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.Volley
import org.apache.http.client.methods.HttpTrace
import smy20011.lkreader.R
import smy20011.lkreader.network.BookUtil
import smy20011.lkreader.network.BookUtil.Book
import smy20011.lkreader.network.HTTPLoader
import smy20011.lkreader.network.getImageLoader
import smy20011.lkreader.ui.BaseActivity
import smy20011.lkreader.util.findById

public class HomeActivity: BaseActivity() {
    val bookList = arrayListOf<Book>()
    var recyclerView: RecyclerView? = null
    override val contentLayout: Int = R.layout.home_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView= findById(this, R.id.book_list)
        recyclerView?.setLayoutManager(LinearLayoutManager(this))
        recyclerView?.setAdapter(BookListAdapter(this, bookList))

        presenter.takeView(this)
        presenter.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView(this)
    }

    fun addBook(book: Book) {
        bookList.add(book)
        recyclerView?.getAdapter()?.notifyDataSetChanged()
        Log.d("AddBook!", book.toString())
    }

    class BookHolder: RecyclerView.ViewHolder {
        val bookInfo: TextView
        val bookCover: NetworkImageView

        constructor(view: View): super(view) {
            bookInfo = findById(view, R.id.book_info)
            bookCover = findById(view, R.id.book_cover)
        }
    }

    class BookListAdapter(val ctx: Context, val bookList: List<Book>): RecyclerView.Adapter<BookHolder>() {
        override fun onBindViewHolder(holder: BookHolder, position: Int) {
            val book = bookList[position]
            holder.bookInfo.setText("${book.series} ${book.title}")
            holder.bookCover.setImageUrl(book.coverImg, ctx.getImageLoader())
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder? {
            val view = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.book_item, parent, false)
            return BookHolder(view)
        }

        override fun getItemCount(): Int {
            return bookList.size()
        }
    }

    object presenter: BasePresenter<HomeActivity>() {
        fun init() {
            loadRecentBookList()
        }

        fun loadRecentBookList() {
            if (hasView()) {
                val view = getView()
                // Create a queue
                val res = view.getResources()!!
                // Parse content
                HTTPLoader.get(view, res.getString(R.string.url_home))
                        .then { BookUtil.getBookList(it) }
                        .success { it.forEach { view.addBook(it) } }
                        .fail { Log.d("LKReader", it.toString()) }
            }
        }
    }
}