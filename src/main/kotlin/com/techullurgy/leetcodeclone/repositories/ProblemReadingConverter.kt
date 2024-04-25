package com.techullurgy.leetcodeclone.repositories

import com.techullurgy.leetcodeclone.domain.db.CodeSnippet
import org.bson.Document
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.mongodb.core.convert.MongoCustomConversions

@ReadingConverter
class ProblemReadingConverter : Converter<Document, CodeSnippet> {
    override fun convert(source: Document): CodeSnippet {
        return when (source.getString("_class")) {
            "c" -> CodeSnippet.CSnippet(snippet = source.getString("snippet"))
            "cpp" -> CodeSnippet.CPPSnippet(snippet = source.getString("snippet"))
            "java" -> CodeSnippet.JavaSnippet(snippet = source.getString("snippet"))
            "python" -> CodeSnippet.PythonSnippet(snippet = source.getString("snippet"))
            "javascript" -> CodeSnippet.JavascriptSnippet(snippet = source.getString("snippet"))
            null -> throw IllegalStateException("Codesnippet cannot be null")
            else -> throw IllegalStateException("Codesnippet Error")
        }
    }
}

@Configuration
class MongoConfig {
    @Bean
    fun mongoCustomConversions(): MongoCustomConversions {
        val converterList: MutableList<Converter<*, *>> = ArrayList()
        converterList.add(ProblemReadingConverter())
        return MongoCustomConversions(converterList)
    }
}