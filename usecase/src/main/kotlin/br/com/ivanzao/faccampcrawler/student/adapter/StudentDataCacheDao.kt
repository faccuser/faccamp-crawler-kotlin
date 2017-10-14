package br.com.ivanzao.faccampcrawler.student.adapter

import br.com.ivanzao.faccampcrawler.student.model.StudentDataCache

interface StudentDataCacheDao {

    fun get(ra: String): StudentDataCache?

    fun save(cache: StudentDataCache)

}