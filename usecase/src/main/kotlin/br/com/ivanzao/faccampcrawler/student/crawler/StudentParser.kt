package br.com.ivanzao.faccampcrawler.student.crawler

import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class StudentParser {

    fun validateLogin(html: Document): Boolean {
        val elements = html.select("em")
        return elements.first().ownText() != "user"
    }

}