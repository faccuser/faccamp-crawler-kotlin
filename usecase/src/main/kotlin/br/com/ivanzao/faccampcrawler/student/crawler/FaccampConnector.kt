package br.com.ivanzao.faccampcrawler.student.crawler

import br.com.ivanzao.faccampcrawler.student.config.ConfigService
import br.com.ivanzao.faccampcrawler.student.crawler.model.LoginData
import org.jsoup.Connection.Method.GET
import org.jsoup.Connection.Method.POST
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class FaccampConnector(configService: ConfigService) : AbstractConnector() {

    private val LOGGER = LoggerFactory.getLogger(FaccampConnector::class.java)!!

    private val HOME_URL = configService.getRequiredString("faccamp.url.home")
    private val LOGIN_VALIDATION_URL = configService.getRequiredString("faccamp.url.validation")

    private val DEFAULT_HEADERS = mapOf(
            "Host" to "www.psxportalacademico.com.br",
            "Connection" to "keep-alive",
            "Cache-Control" to "max-age=0",
            "Upgrade-Insecure-Requests" to "1",
            "User-Agent" to "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36",
            "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
            "Referer" to "https://www.psxportalacademico.com.br/faccamp/index.php",
            "Accept-Encoding" to "gzip, deflate, br",
            "Accept-Language" to "pt-BR,pt;q=0.8,en-US;q=0.6,en;q=0.4")

    fun executeLogin(ra: String, password: String): LoginData {
        val homepageResponse = connect(HOME_URL, GET)

        val cookies = homepageResponse.cookies()

        val loginResponse = connect(LOGIN_VALIDATION_URL, POST, mapOf("user" to ra, "senha" to password), cookies, DEFAULT_HEADERS)

        return LoginData(document = loginResponse.parse(), cookies = loginResponse.cookies())
    }

}