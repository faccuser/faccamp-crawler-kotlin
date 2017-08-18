package br.com.ivanzao.faccampcrawler.student.crawler

import br.com.ivanzao.faccampcrawler.student.crawler.model.LoginData
import br.com.ivanzao.faccampcrawler.student.exception.InvalidLoginException
import br.com.ivanzao.faccampcrawler.student.model.StudentData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StudentCrawler(private val parser: StudentParser,
                     private val faccampConnector: FaccampConnector) {

    private val LOGGER = LoggerFactory.getLogger(StudentCrawler::class.java)!!

    fun login(ra: String, password: String): LoginData {
        LOGGER.info("Executing login for student with ra: $ra")
        val loginData = faccampConnector.executeLogin(ra, password)

        if (parser.validateLogin(loginData.document)) {
            LOGGER.info("Successfully logged in student with ra: $ra")
            return loginData
        }

        LOGGER.info("Incorrect password or invalid login for ra: $ra")
        throw InvalidLoginException("Incorrect password or invalid login for ra: $ra")
    }

    fun retrieveData(loginData: LoginData): StudentData {
        LOGGER.info("Retrieving data for student")
        return StudentData("dummy", "dummy")
    }

}