@file:OptIn(ExperimentalUnsignedTypes::class)

package dev.matytyma.crypto.hash

private const val COMPRESSION_ROUNDS: Int = 12
private val rotationConstants: IntArray = intArrayOf(32, 24, 16, 63)

private val IV: ULongArray = ulongArrayOf(
    0x6A09E667F3BCC908UL, 0xBB67AE8584CAA73BUL, 0x3C6EF372FE94F82BUL, 0xA54FF53A5F1D36F1UL,
    0x510E527FADE682D1UL, 0x9B05688C2B3E6C1FUL, 0x1F83D9ABFB41BD6BUL, 0x5BE0CD19137E2179UL,
)

private val SIGMA: Array<IntArray> = arrayOf(
    intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15),
    intArrayOf(14, 10, 4, 8, 9, 15, 13, 6, 1, 12, 0, 2, 11, 7, 5, 3),
    intArrayOf(11, 8, 12, 0, 5, 2, 15, 13, 10, 14, 3, 6, 7, 1, 9, 4),
    intArrayOf(7, 9, 3, 1, 13, 12, 11, 14, 2, 6, 5, 10, 4, 0, 15, 8),
    intArrayOf(9, 0, 5, 7, 2, 4, 10, 15, 14, 1, 11, 12, 6, 8, 3, 13),
    intArrayOf(2, 12, 6, 10, 0, 11, 8, 3, 4, 13, 7, 5, 15, 14, 1, 9),
    intArrayOf(12, 5, 1, 15, 14, 13, 4, 10, 0, 7, 6, 3, 9, 2, 8, 11),
    intArrayOf(13, 11, 7, 14, 12, 1, 3, 9, 5, 0, 15, 4, 8, 6, 2, 10),
    intArrayOf(6, 15, 14, 9, 11, 3, 0, 8, 12, 2, 13, 7, 1, 4, 10, 5),
    intArrayOf(10, 2, 8, 4, 7, 6, 1, 5, 15, 11, 9, 14, 3, 12, 13, 0),
)

private fun ULongArray.mix(a: Int, b: Int, c: Int, d: Int, x: ULong, y: ULong): ULongArray {
    this[a] = this[a] + this[b] + x
    this[d] = (this[d] xor this[a]).rotateRight(rotationConstants[0])
    this[c] = this[c] + this[d]
    this[b] = (this[b] xor this[c]).rotateRight(rotationConstants[1])
    this[a] = this[a] + this[b] + y
    this[d] = (this[d] xor this[a]).rotateRight(rotationConstants[2])
    this[c] = this[c] + this[d]
    this[b] = (this[b] xor this[c]).rotateRight(rotationConstants[3])

    return this
}

private fun ULongArray.compress(
    block: ULongArray,
    offsetLow: ULong,
    offsetHigh: ULong,
    invert: Boolean,
): ULongArray {
    val work = ulongArrayOf(*this, *IV)
    work[12] = work[12] xor offsetLow
    work[13] = work[13] xor offsetHigh
    if (invert) work[14] = work[14].inv()

    for (i in 0..<COMPRESSION_ROUNDS) {
        val s = SIGMA[i % 10]
        for (j in 0..3) {
            work.mix(j, j + 4, j + 8, j + 12, block[s[j * 2]], block[s[j * 2 + 1]])
        }
        work.mix(0, 5, 10, 15, block[s[8]], block[s[9]])
        work.mix(1, 6, 11, 12, block[s[10]], block[s[11]])
        work.mix(2, 7, 8, 13, block[s[12]], block[s[13]])
        work.mix(3, 4, 9, 14, block[s[14]], block[s[15]])
    }

    for (i in 0..7) {
        this[i] = this[i] xor work[i] xor work[i + 8]
    }

    return this
}

fun blake2b(
    message: UByteArray,
    key: UByteArray = ubyteArrayOf(),
    hashLength: Int,
): UByteArray {
    require(key.size <= 64) { "Key must be at most 64 bytes" }
    require(hashLength in 1..64) { "Hash length must be in range 1..64" }

    val rawData = ubyteArrayOf(
        *key, *UByteArray((128 - key.size % 128) % 128),
        *message, *UByteArray((128 - message.size % 128) % 128),
    )

    val data = List(rawData.size / 128) { i ->
        ULongArray(16) { j ->
            (rawData[i * 128 + j * 8 + 7].toULong() shl 56) +
                    (rawData[i * 128 + j * 8 + 6].toULong() shl 48) +
                    (rawData[i * 128 + j * 8 + 5].toULong() shl 40) +
                    (rawData[i * 128 + j * 8 + 4].toULong() shl 32) +
                    (rawData[i * 128 + j * 8 + 3].toULong() shl 24) +
                    (rawData[i * 128 + j * 8 + 2].toULong() shl 16) +
                    (rawData[i * 128 + j * 8 + 1].toULong() shl 8) +
                    rawData[i * 128 + j * 8 + 0].toULong()
        }
    }

    val hash = ulongArrayOf(*IV)
    hash[0] = hash[0] xor 0x01010000UL xor (key.size shl 8).toULong() xor hashLength.toULong()

    for (i in 0..(data.size - 2)) {
        hash.compress(data[i], (i.toULong() + 1UL) shl 7, i.toULong() shr 57, false)
    }

    if (key.isEmpty()) {
        hash.compress(data.last(), message.size.toULong(), 0UL, true)
    } else {
        hash.compress(data.last(), message.size.toULong() + 128UL, 0UL, true)
    }
    val finalHash = UByteArray(hash.size * 8)
    for (i in 0..hash.lastIndex) {
        val block = hash[i]
        for (j in 0..7) {
            finalHash[i * 8 + j] = (block shr (j * 8)).toUByte()
        }
    }

    return finalHash
}
