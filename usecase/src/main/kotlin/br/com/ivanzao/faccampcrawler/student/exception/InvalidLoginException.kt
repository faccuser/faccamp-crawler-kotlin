package br.com.ivanzao.faccampcrawler.student.exception

class InvalidLoginException : IllegalArgumentException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}