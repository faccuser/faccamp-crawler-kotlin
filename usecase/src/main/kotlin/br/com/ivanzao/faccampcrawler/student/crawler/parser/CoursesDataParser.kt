package br.com.ivanzao.faccampcrawler.student.crawler.parser

import br.com.ivanzao.faccampcrawler.student.model.CourseData
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CoursesDataParser {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CoursesDataParser::class.java)
    }

    fun extractCoursesData(coursesPage: Document): List<CourseData> {
        LOGGER.info("Extracting courses data")
        val courses = ArrayList<CourseData>()

        val coursesGrid = coursesPage.select(".GradeNotas tbody tr")
        (2 until coursesGrid.size - 1).mapTo(courses) { extractCourseDataFromRow(coursesGrid[it]) }

        return courses
    }

    private fun extractCourseDataFromRow(courseDataRow: Element): CourseData {
        val rowColumns = courseDataRow.select("td")

        val code = rowColumns[0].text().removeWhitespace()
        val name = rowColumns[1].text().removeWhitespace()

        if (courseDataRow.text().contains("o dispon")) {
            return CourseData(code = code, name = name, absencesLimit = null, partialAverage = null,
                    finalAverage = null, absences = null, grades = ArrayList(), edpGrades = ArrayList())
        }

        val partialAverage = rowColumns.select(".ColunaMP").text().removeWhitespace().toDouble()
        val finalAverage = extractFinalAverage(rowColumns)

        val absencesLimit = rowColumns[15].text().removeWhitespace().toInt()
        val absences = extractAbsences(rowColumns)

        return CourseData(code = code, name = name, partialAverage = partialAverage, finalAverage = finalAverage,
                absences = absences, absencesLimit = absencesLimit, grades = ArrayList(), edpGrades = ArrayList())
    }

    private fun extractAbsences(rowColumns: Elements): Int? {
        val absencesText = rowColumns[16].text().removeWhitespace()
        if (!absencesText.isEmpty()) {
            return absencesText.toInt()
        }

        return null
    }

    private fun extractFinalAverage(rowColumns: Elements): Double? {
        val finalAverageText = rowColumns.select(".ColunaMF").text().removeWhitespace()
        if (!finalAverageText.isEmpty()) {
            return finalAverageText.toDouble()
        }

        return null
    }

    private fun String.removeWhitespace(): String {
        return this.replace("&nbsp;", "").trim()
    }

}