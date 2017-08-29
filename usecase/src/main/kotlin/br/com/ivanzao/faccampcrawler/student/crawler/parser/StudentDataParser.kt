package br.com.ivanzao.faccampcrawler.student.crawler.parser

import br.com.ivanzao.faccampcrawler.student.model.Program
import br.com.ivanzao.faccampcrawler.student.model.StudentData
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StudentDataParser {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(StudentDataParser::class.java)
    }

    fun extractStudentData(coursesPage: Document): StudentData {
        LOGGER.info("Extracting student data")
        val resultsHeader = coursesPage.select("td.LinhaPar")

        val ra = resultsHeader[4].text().trim()
        val name = resultsHeader[5].text().trim()
        val program = extractProgram(resultsHeader)

        return StudentData(ra = ra, name = name, program = program, courses = ArrayList())
    }

    private fun extractProgram(resultsHeader: Elements): Program {
        val code = resultsHeader[6].text().trim()
        val name = extractProgramName(resultsHeader[7].text())
        val period = Integer.valueOf(resultsHeader[8].text().trim())

        return Program(code = code, name = name, period = period)
    }

    private fun extractProgramName(rawName: String): String {
        if (rawName.contains("-")) {
            return rawName.split("-").first().trim()
        }

        return rawName.trim()
    }

}