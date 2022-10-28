package com.kamiz.security.hashing

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@KtorExperimentalAPI
class SHA256HashingService : HashingService {
    val hashKey = hex(System.getenv("SECRET_KEY"))
    val hmacKey = SecretKeySpec(hashKey, "HmacSHA1")

    override fun generateHash(value: String): String { // 4
        val hmac = Mac.getInstance("HmacSHA1")
        hmac.init(hmacKey)
        return hex(hmac.doFinal(value.toByteArray(Charsets.UTF_8)))
    }

    override fun verify(value: String, hashedValue: String): Boolean {
        return generateHash(value) == hashedValue
    }
}