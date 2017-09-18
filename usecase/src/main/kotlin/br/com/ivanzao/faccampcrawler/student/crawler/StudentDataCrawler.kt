package br.com.ivanzao.faccampcrawler.student.crawler

import br.com.ivanzao.faccampcrawler.student.aynsc.AsyncExecutor
import br.com.ivanzao.faccampcrawler.student.crawler.model.LoginData
import br.com.ivanzao.faccampcrawler.student.crawler.parser.CoursesDataParser
import br.com.ivanzao.faccampcrawler.student.crawler.parser.GradesDataParser
import br.com.ivanzao.faccampcrawler.student.crawler.parser.LoginParser
import br.com.ivanzao.faccampcrawler.student.crawler.parser.StudentDataParser
import br.com.ivanzao.faccampcrawler.student.exception.InvalidLoginException
import br.com.ivanzao.faccampcrawler.student.model.CourseData
import br.com.ivanzao.faccampcrawler.student.model.Grade
import br.com.ivanzao.faccampcrawler.student.model.StudentData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StudentDataCrawler(private val loginParser: LoginParser,
                         private val studentParser: StudentDataParser,
                         private val coursesParser: CoursesDataParser,
                         private val gradesParser: GradesDataParser,
                         private val executor: AsyncExecutor,
                         private val connector: FaccampConnector) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(StudentDataCrawler::class.java)!!
    }

    fun login(ra: String, password: String): LoginData {
        LOGGER.info("Executing login for student with ra: $ra")
        val loginData = connector.executeLogin(ra, password)

        if (!loginParser.validateLogin(loginData.document)) {
            LOGGER.info("Incorrect password or invalid login for ra: $ra")
            throw InvalidLoginException("Incorrect password or invalid login for ra: $ra")
        }

        return loginData
    }

    fun retrieveData(loginData: LoginData): StudentData {
        LOGGER.info("Retrieving student data")
        val futureCoursesPage = executor.submit { connector.retrieveCoursesPage(loginData.cookies) }

        val futureGradesData = executor.submit {
            val gradesPage = connector.retrieveGradesPage(loginData.cookies)
            return@submit gradesParser.extractGradesData(gradesPage)
        }

        val futureEDPGradesData = executor.submit {
            val edpGradesPage = connector.retrieveEDPGradesPage(loginData.cookies)
            return@submit gradesParser.extractGradesData(edpGradesPage)
        }

        val coursesPage = futureCoursesPage.get()

        val studentData = studentParser.extractStudentData(coursesPage)
        val coursesData = coursesParser.extractCoursesData(coursesPage)

        mergeCoursesGrades(coursesData, futureGradesData.get(), futureEDPGradesData.get())

        return studentData.copy(courses = coursesData)
    }

    private fun mergeCoursesGrades(coursesData: List<CourseData>,
                                   grades: List<List<Grade>>,
                                   edpGrades: List<List<Grade>>) {
        coursesData.forEachIndexed { index, courseData ->
            courseData.grades.addAll(grades[index])
            courseData.edpGrades.addAll(edpGrades[index])
        }
    }

}