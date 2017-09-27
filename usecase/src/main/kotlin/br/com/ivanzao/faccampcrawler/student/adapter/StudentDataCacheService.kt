package br.com.ivanzao.faccampcrawler.student.adapter

import br.com.ivanzao.faccampcrawler.student.model.StudentData
import br.com.ivanzao.faccampcrawler.student.model.StudentDataCache
import java.time.LocalDateTime

interface StudentDataCacheRepository {

    fun getCache(ra: String): StudentDataCache?

    fun save(ra: String, createdAt: LocalDateTime, studentData: StudentData)

}