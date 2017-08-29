package br.com.ivanzao.faccampcrawler.student.aynsc

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.Closeable
import java.lang.Exception
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

@Component
class AsyncExecutor : Closeable {

    private val LOGGER = LoggerFactory.getLogger(AsyncExecutor::class.java)

    private val executor = Executors.newCachedThreadPool()

    fun run(function: () -> Unit) {
        try {
            executor.submit({ function() })
        } catch (e: Exception) {
            LOGGER.error(e.message, e)
            throw e
        }
    }

    fun <T> submit(function: () -> T): Future<T> {
        try {
            return executor.submit(Callable { function() })
        } catch (e: Exception) {
            LOGGER.error(e.message, e)
            throw e
        }
    }

    override fun close() {
        executor.shutdown()
    }
}