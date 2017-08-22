package br.com.ivanzao.faccampcrawler.student.crawler

import br.com.ivanzao.faccampcrawler.student.exception.FaccampConnectionException
import org.jsoup.Connection
import org.jsoup.Connection.Response
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import java.io.IOException

abstract class AbstractConnector {

    private val LOGGER = LoggerFactory.getLogger(AbstractConnector::class.java)
    private val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2"

    protected fun connect(url: String, method: Connection.Method): Response {
        try {
            LOGGER.info("Executing $method request on url -> '$url'")
            return Jsoup.connect(url)
                    //.userAgent(USER_AGENT)
                    .method(method)
                    .execute()
        } catch (e: IOException) {
            LOGGER.error("There was an error executing $method request on url -> '$url'\"", e)
            throw FaccampConnectionException("There was an error executing $method request on url -> '$url'\"", e)
        }
    }

    protected fun connect(url: String,
                          method: Connection.Method,
                          params: Map<String, String>,
                          cookies: Map<String, String>,
                          headers: Map<String, String>): Response {
        try {
            LOGGER.info("Executing $method request on url -> '$url'")
            val request = Jsoup.connect(url)
                    .data(params)
                    //.userAgent(USER_AGENT)
                    .cookies(cookies)
                    .method(method)
                    .followRedirects(true)

            headers.entries.forEach { request.header(it.key, it.value) }

            return request.execute()
        } catch (e: IOException) {
            LOGGER.error("There was an error executing $method request on url -> '$url'\"", e)
            throw FaccampConnectionException("There was an error executing $method request on url -> '$url'\"", e)
        }
    }

}