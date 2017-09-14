package br.com.ivanzao.faccampcrawler.student

import br.com.ivanzao.faccampcrawler.student.crawler.StudentDataCrawler
import br.com.ivanzao.faccampcrawler.student.model.StudentData
import org.springframework.stereotype.Service

@Service
class StudentService(private val crawler: StudentDataCrawler) {

    fun retrieveData(ra: String, password: String): StudentData {
        val loginData = crawler.login(ra, password)
        //TODO: login and get data cache if exists

        val studentData = crawler.retrieveData(loginData)
        //TODO: execute cache storing logic

        return studentData
    }

}