package br.com.ivanzao.faccampcrawler.student.crawler.parser

import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CoursesDataParserTest {

    private val parser = CoursesDataParser()

    @Test
    fun testExtractCoursesData() {
        val htmlDocument = Jsoup.parse(StudentDataParserTest::class.java
                .getResource("/pages/valid-courses-page.html").readText())
        val coursesData = parser.extractCoursesData(htmlDocument)

        assertEquals(6, coursesData.size)

        assertEquals("T181", coursesData[0].code)
        assertEquals("Computação Gráfica", coursesData[0].name)
        assertEquals(0.0, coursesData[0].partialAverage)
        assertEquals(9.5, coursesData[0].finalAverage)
        assertEquals(10, coursesData[0].absencesLimit)
        assertEquals(8, coursesData[0].absences)

        assertEquals("S159", coursesData[1].code)
        assertEquals("Direito e Ética na Computação", coursesData[1].name)
        assertEquals(0.0, coursesData[1].partialAverage)
        assertEquals(7.8, coursesData[1].finalAverage)
        assertEquals(20, coursesData[1].absencesLimit)
        assertNull(coursesData[1].absences)

        assertEquals("0270", coursesData[2].code)
        assertEquals("Empreendedorismo", coursesData[2].name)
        assertEquals(0.0, coursesData[2].partialAverage)
        assertNull(coursesData[2].finalAverage)
        assertEquals(20, coursesData[2].absencesLimit)
        assertNull(coursesData[2].absences)

        assertEquals("S158", coursesData[3].code)
        assertEquals("Implementação de Linguagens de Programação", coursesData[3].name)
        assertEquals(0.0, coursesData[3].partialAverage)
        assertNull(coursesData[3].finalAverage)
        assertEquals(20, coursesData[3].absencesLimit)
        assertEquals(5, coursesData[3].absences)

        assertEquals("1068", coursesData[4].code)
        assertEquals("Sistemas para Internet II", coursesData[4].name)
        assertEquals(0.0, coursesData[4].partialAverage)
        assertEquals(6.0, coursesData[4].finalAverage)
        assertEquals(20, coursesData[4].absencesLimit)
        assertEquals(12, coursesData[4].absences)

        assertEquals("T180", coursesData[5].code)
        assertEquals("Trabalho de Diplomação II", coursesData[5].name)
        assertNull(coursesData[5].partialAverage)
        assertNull(coursesData[5].finalAverage)
        assertNull(coursesData[5].absencesLimit)
        assertNull(coursesData[5].absences)
    }

}