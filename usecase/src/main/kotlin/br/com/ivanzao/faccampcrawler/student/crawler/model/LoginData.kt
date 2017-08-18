package br.com.ivanzao.faccampcrawler.student.crawler.model

import org.jsoup.nodes.Document

data class LoginData(
        val document: Document,
        val cookies: Map<String, String>
)