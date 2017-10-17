package br.com.ivanzao.faccampcrawler.util

object TextUtils {

    fun isNumeric(text: String) = text.matches(Regex("-?\\\\d+(\\\\.\\\\d+)?"))
}