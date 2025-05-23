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
