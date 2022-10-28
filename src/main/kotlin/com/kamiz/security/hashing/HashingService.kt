package com.kamiz.security.hashing

interface HashingService {
    fun generateHash(value: String): String
    fun verify(value: String, hashedValue: String): Boolean
}