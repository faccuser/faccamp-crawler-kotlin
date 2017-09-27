package br.com.ivanzao.faccampcrawler.student.model

import java.time.LocalDateTime

data class StudentDataCache(
        val ra: String,
        val createdAt: LocalDateTime,
        val data: StudentData
)