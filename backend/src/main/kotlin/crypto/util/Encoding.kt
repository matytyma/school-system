package dev.matytyma.crypto.util

@OptIn(ExperimentalUnsignedTypes::class)
fun UInt.toLittleEndian() = ubyteArrayOf(
    this.toUByte(),
    (this shr 8).toUByte(),
    (this shr 16).toUByte(),
    (this shr 24).toUByte(),
)

@OptIn(ExperimentalUnsignedTypes::class)
fun Int.toLittleEndian() = this.toUInt().toLittleEndian()

@OptIn(ExperimentalUnsignedTypes::class)
fun ULong.toLittleEndian() = ubyteArrayOf(
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
fun Long.toLittleEndian() = this.toULong().toLittleEndian()
