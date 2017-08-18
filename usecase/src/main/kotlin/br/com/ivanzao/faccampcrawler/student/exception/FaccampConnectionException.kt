package br.com.ivanzao.faccampcrawler.student.exception

import java.io.IOException

class FaccampConnectionException : IOException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}