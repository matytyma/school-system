package dev.matytyma.crypto.util

@OptIn(ExperimentalUnsignedTypes::class)
fun UInt.toBytesLE() = ubyteArrayOf(
    this.toUByte(),
    (this shr 8).toUByte(),
    (this shr 16).toUByte(),
    (this shr 24).toUByte(),
)

@OptIn(ExperimentalUnsignedTypes::class)
fun Int.toBytesLE() = toUInt().toBytesLE()

@OptIn(ExperimentalUnsignedTypes::class)
fun ULong.toBytesLE() = ubyteArrayOf(
    this.toUByte(),
    (this shr 8).toUByte(),
    (this shr 16).toUByte(),
    (this shr 24).toUByte(),
    (this shr 32).toUByte(),
    (this shr 40).toUByte(),
    (this shr 48).toUByte(),
    (this shr 56).toUByte(),
)

@OptIn(ExperimentalUnsignedTypes::class)
fun Long.toBytesLE() = toULong().toBytesLE()

@OptIn(ExperimentalUnsignedTypes::class)
fun List<UByte>.fromBytesLE() = (this[7].toULong() shl 56) +
        (this[6].toULong() shl 48) +
        (this[5].toULong() shl 40) +
        (this[4].toULong() shl 32) +
        (this[3].toULong() shl 24) +
        (this[2].toULong() shl 16) +
        (this[1].toULong() shl 8) +
        this[0].toULong()
