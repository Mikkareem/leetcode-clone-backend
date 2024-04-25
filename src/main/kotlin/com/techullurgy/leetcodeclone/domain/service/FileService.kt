package com.techullurgy.leetcodeclone.domain.service

import java.io.File
import java.io.FileWriter

class FileService {
    companion object {
        fun writeFile(filePath: String, value: String) {
            val file = File(filePath)
            file.createDirectoriesIfNotExists()
            file.createNewFile()
            FileWriter(file).use {
                it.write(value)
            }
        }

        fun deleteFile(filePath: String) {
            val file = File(filePath)
            file.delete()
        }

        private fun File.createDirectoriesIfNotExists() {
            val folder = path.substring(0, path.lastIndexOf('/'))
            val tempFile = File(folder)
            tempFile.mkdirs()
        }
    }
}