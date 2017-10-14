package br.com.ivanzao.faccampcrawler.student.repository

import br.com.ivanzao.faccampcrawler.student.document.StudentDataCacheDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface StudentDataCacheRepository : MongoRepository<StudentDataCacheDocument, String> {

    fun findTopByRaOrderByCreatedAtDesc(ra: String): StudentDataCacheDocument?

}