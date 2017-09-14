package br.com.ivanzao.faccampcrawler.student.crawler.parser

import br.com.ivanzao.faccampcrawler.student.model.Grade
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Year

@Component
class GradesDataParser {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(GradesDataParser::class.java)
    }

    fun extractGradesData(gradesPage: Document): List<List<Grade>> {
        LOGGER.info("Extracting grades data")
        val tableRows = gradesPage.select("table.GradeNotas tbody tr")
        val coursesRow = tableRows.filter { row -> isCourseRow(row) }

        val gradesData = ArrayList<List<Grade>>()

        coursesRow.forEach { row ->
            val grades = ArrayList<Grade>()
            val gradeTables = row.select("table tbody")

            gradeTables.forEach { table ->
                val tableElements = table.select("tr")
                if (tableElements.size == 2) {
                    grades.add(Grade(date = dateTextToLocalDate(tableElements[0].text().trim()),
                            mark = tableElements[1].text().trim().toDouble()))
                }
            }

            gradesData.add(grades)
        }

        return gradesData
    }

    private fun isCourseRow(row: Element) = row.children().size >= 3 && row.children()[0].text() != "DISCIPLINAS"

    private fun dateTextToLocalDate(dateText: String): LocalDate {
        val splitText = dateText.split("-")
        return LocalDate.of(Year.now().value, splitText[1].toInt(), splitText[0].toInt())
    }

}