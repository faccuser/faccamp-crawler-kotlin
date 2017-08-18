package br.com.ivanzao.faccampcrawler.student.crawler

import br.com.ivanzao.faccampcrawler.student.config.ConfigService
import br.com.ivanzao.faccampcrawler.student.crawler.model.LoginData
import br.com.ivanzao.faccampcrawler.student.exception.FaccampConnectionException
import org.jsoup.Connection.Method
import org.jsoup.Connection.Method.GET
import org.jsoup.Connection.Method.POST
import org.jsoup.Connection.Response
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class FaccampConnector(configService: ConfigService) {

    private val LOGGER = LoggerFactory.getLogger(FaccampConnector::class.java)!!

    private val USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36"

    private val HOME_URL = configService.getRequiredString("faccamp.url.home")
    private val LOGIN_URL = configService.getRequiredString("faccamp.url.login")

    fun executeLogin(ra: String, password: String): LoginData {
        val homepageResponse = connect(HOME_URL, GET, mapOf(), mapOf())
        val loginResponse = connect(LOGIN_URL, POST, mapOf("user" to ra, "senha" to password), homepageResponse.cookies())

        return LoginData(document = loginResponse.parse(), cookies = loginResponse.cookies())
    }

    private fun connect(url: String, method: Method, params: Map<String, String>, cookies: Map<String, String>): Response {
        try {
            LOGGER.info("Executing $method request on url -> '$url'")
            return Jsoup.connect(url)
                    .data(params)
                    .header("User-Agent", USER_AGENT)
                    .cookies(cookies)
                    .method(method)
                    .execute()
        } catch (e: IOException) {
            LOGGER.error("There was an error executing $method request on url -> '$url'\"", e)
            throw FaccampConnectionException("There was an error executing $method request on url -> '$url'\"", e)
        }
    }
}