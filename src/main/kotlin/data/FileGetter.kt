package org.example.data

import org.example.error.FileDoesNotExistException
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