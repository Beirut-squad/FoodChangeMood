package org.example.data

import java.io.File

class FileGetter {

    fun getFile(inputFile: File): String {
        if (inputFile.exists()) {
            return inputFile.readText()
        } else {
            throw FileDoesNotExistException()
        }
    }
}

class FileDoesNotExistException: Exception()