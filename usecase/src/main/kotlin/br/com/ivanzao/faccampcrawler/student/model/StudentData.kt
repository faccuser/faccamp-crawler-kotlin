package br.com.ivanzao.faccampcrawler.student.model

import java.time.LocalDate

data class StudentData(
        val ra: String,
        val name: String,
        val program: Program,
        val courses: List<CourseData>
)

data class Program(
        val code: String,
        val name: String,
        val period: Int
)

data class CourseData(
        val code: String,
        val name: String,
        val partialAverage: Double?,
        val finalAverage: Double?,
        val absencesLimit: Int?,
        val absences: Int?,
        val grades: List<Grade>?,
        val edpGrades: List<Grade>?
)


data class Grade(
        val mark: Double,
        val date: LocalDate
)