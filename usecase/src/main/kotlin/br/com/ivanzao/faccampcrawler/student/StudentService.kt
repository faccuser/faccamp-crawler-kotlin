package br.com.ivanzao.faccampcrawler.student

import br.com.ivanzao.faccampcrawler.student.model.Student
import org.springframework.stereotype.Service

@Service
class StudentService {

    fun retrieveData(ra: String, password: String): Student {
        return Student(ra = ra, password = password)
    }

}