package br.com.ivanzao.faccampcrawler.student.crawler.parser

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test

class StudentDataParserTest {

    private val parser = StudentDataParser()

    @Test
    fun testExtractStudentData() {
        val htmlDocument = Jsoup.parse(StudentDataParserTest::class.java.getResource("/valid-courses-page.html").readText())
        val studentData = parser.extractStudentData(htmlDocument)

        assertEquals("Tyler Durden", studentData.name)
        assertEquals("1984", studentData.ra)

        assertEquals("52", studentData.program.code)
        assertEquals("Ciência da Computação", studentData.program.name)
        assertEquals(8, studentData.program.period)
    }
}