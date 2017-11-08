package br.com.ivanzao.faccampcrawler.student.crawler.parser

import br.com.ivanzao.faccampcrawler.student.exception.InvalidLoginException
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LoginParser {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(LoginParser::class.java)!!
    }

    fun validateLogin(html: Document) {
        if (html.toString().contains("Falha no login")) {
            LOGGER.info("Incorrect password or invalid login")
            throw InvalidLoginException("Incorrect password or invalid login")
        }
    }

}