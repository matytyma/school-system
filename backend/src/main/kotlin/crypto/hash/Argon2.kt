package dev.matytyma.crypto.hash

import dev.matytyma.crypto.util.toBytesLE

class Argon2(
    private val parallelism: Int,
    private val hashLength: Int,
    private val memorySize: Int,
    private val iterations: Int,
    private val type: Type = Type.ARGON2ID,
) {
    companion object {
        private const val VERSION = 0x13.toByte()

        enum class Type {
            ARGON2D,
            ARGON2I,
            ARGON2ID,
        }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    fun hash(
        message: UByteArray,
        salt: UByteArray = ubyteArrayOf(),
        secret: UByteArray = ubyteArrayOf(),
        associatedData: UByteArray = ubyteArrayOf(),
    ): ByteArray {
        require(parallelism >= 1) { "Degree of parallelism must be at least 1" }
        require(hashLength >= 4) { "Hash length must be at least 4" }
        require(memorySize >= 8 * parallelism) { "Memory size must be at least 8 * parallelism" }
        require(iterations >= 1) { "Number of iterations must be at least 1" }

        val entropy = ubyteArrayOf(
            *parallelism.toBytesLE(),
            *hashLength.toBytesLE(),
            *memorySize.toBytesLE(),
            *iterations.toBytesLE(),
            *VERSION.toInt().toBytesLE(),
            *type.ordinal.toBytesLE(),
            *message.size.toBytesLE(),
            *message,
            *salt.size.toBytesLE(),
            *salt,
            *secret.size.toBytesLE(),
            *secret,
            *associatedData.size.toBytesLE(),
            *associatedData,
        )

        val initialHash = blake2b(entropy, hashLength = 64)

        return TODO()
    }
}
