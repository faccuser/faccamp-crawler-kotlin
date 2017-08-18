package br.com.ivanzao.faccampcrawler.student

import br.com.ivanzao.faccampcrawler.student.crawler.StudentCrawler
import br.com.ivanzao.faccampcrawler.student.model.StudentData
import org.springframework.stereotype.Service

@Service
class StudentService(private val crawler: StudentCrawler) {

    fun retrieveData(ra: String, password: String): StudentData {
        //TODO: login and get data cache if exists
        val loginData = crawler.login(ra, password)
        val studentData = crawler.retrieveData(loginData)
        //TODO: execute cache storing logic

        return studentData
    }

}