package br.com.ivanzao.faccampcrawler.student.crawler.parser

import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class LoginParser {

    fun validateLogin(html: Document): Boolean {
        val elements = html.select("em")
        return elements.first().ownText() != "user"
    }

}