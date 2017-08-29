package br.com.ivanzao.faccampcrawler.student.crawler.parser

import br.com.ivanzao.faccampcrawler.student.model.CourseData
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class CoursesDataParser {

    fun extractCoursesData(coursesPage: Document): List<CourseData> {
        throw Exception()
    }

}