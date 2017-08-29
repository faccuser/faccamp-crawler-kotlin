package br.com.ivanzao.faccampcrawler.student.crawler.model

import org.jsoup.nodes.Document

data class LoginData(
        val ra: String,
        val document: Document,
        val cookies: Map<String, String>
)