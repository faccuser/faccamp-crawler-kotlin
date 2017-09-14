package br.com.ivanzao.faccampcrawler.student.crawler.parser

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Year

class GradesDataParserTest {

    private val parser = GradesDataParser()

    @Test
    fun testExtractPartialStudentData() {
        val htmlDocument = Jsoup.parse(StudentDataParserTest::class.java
                .getResource("/pages/partial-edp-grades-page.html").readText())
        val gradesData = parser.extractGradesData(htmlDocument)

        assertEquals(6, gradesData.size)

        assertEquals("${Year.now().value}-08-23", gradesData[0][0].date.toString())
        assertEquals(10.0, gradesData[0][0].mark, 0.0)
        assertEquals("${Year.now().value}-08-30", gradesData[1][0].date.toString())
        assertEquals(9.0, gradesData[1][0].mark, 0.0)
    }

    @Test
    fun testExtractEmptyStudentData() {
        val htmlDocument = Jsoup.parse(StudentDataParserTest::class.java
                .getResource("/pages/empty-grades-page.html").readText())
        val gradesData = parser.extractGradesData(htmlDocument)

        assertEquals(6, gradesData.size)

        for (grades in gradesData) {
            assertEquals(0, grades.size)
        }
    }
}