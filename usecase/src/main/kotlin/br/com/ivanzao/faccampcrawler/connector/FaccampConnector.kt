package br.com.ivanzao.faccampcrawler.connector

import br.com.ivanzao.faccampcrawler.config.ConfigService
import br.com.ivanzao.faccampcrawler.student.crawler.model.LoginData
import org.jsoup.Connection.Method.GET
import org.jsoup.Connection.Method.POST
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class FaccampConnector(configService: ConfigService) : AbstractConnector() {

    private val INDEX_URL = configService.getRequiredString("faccamp.url.index")
    private val LOGIN_VALIDATION_URL = configService.getRequiredString("faccamp.url.validation")
    private val PORTAL_URL = configService.getRequiredString("faccamp.url.portal")

    companion object {
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

        private val COURSES_PAGE_PARAMS = mapOf(
                "frame_notas" to "frame_alu_notas_resultados.php",
                "frame" to "frame_alu_notas.php",
                "slc" to "X")

        private val GRADES_PAGE_PARAMS = mapOf(
                "frame_notas" to "frame_alu_notas_grade.php",
                "frame" to "frame_alu_notas.php",
                "etapa" to "ML",
                "slc" to "X")

        private val EDP_GRADES_PAGE_PARAMS = mapOf(
                "frame_notas" to "frame_alu_notas_grade.php",
                "frame" to "frame_alu_notas.php",
                "etapa" to "EDP",
                "slc" to "X")
    }

    fun executeLogin(ra: String, password: String): LoginData {
        val homepageResponse = connect(INDEX_URL, GET)

        val cookies = homepageResponse.cookies()
        val params = mapOf("user" to ra, "senha" to password)

        val loginResponse = connect(LOGIN_VALIDATION_URL, POST, params, cookies, DEFAULT_HEADERS)

        return LoginData(ra = ra, document = loginResponse.parse(), cookies = cookies)
    }

    fun retrieveCoursesPage(cookies: Map<String, String>): Document {
        return connect(PORTAL_URL, GET, COURSES_PAGE_PARAMS, cookies, DEFAULT_HEADERS).parse()
    }

    fun retrieveGradesPage(cookies: Map<String, String>): Document {
        return connect(PORTAL_URL, GET, GRADES_PAGE_PARAMS, cookies, DEFAULT_HEADERS).parse()
    }

    fun retrieveEDPGradesPage(cookies: Map<String, String>): Document {
        return connect(PORTAL_URL, GET, EDP_GRADES_PAGE_PARAMS, cookies, DEFAULT_HEADERS).parse()
    }

}