package smy20011.lkreader.util

public class Future<T> {
    var onSuccess: (T) -> Unit = { }
    var onFailure: (Exception) -> Unit = { }

    fun success(data: T) = onSuccess(data)
    fun success(func: (T) -> Unit): Future<T> {
        onSuccess = func
        return this
    }
    fun fail(e: Exception) = onFailure(e)
    fun fail(func: (Exception) -> Unit): Future<T> {
        onFailure = func
        return this
    }

    fun then<A>(func: (T) -> Future<A>): Future<A> {
        val future = Future<A>()
        onSuccess = {
            func(it).onSuccess = {
                future.success(it)
            }
        }
        onFailure = {
            future.fail(it)
        }
        return future
    }
}
