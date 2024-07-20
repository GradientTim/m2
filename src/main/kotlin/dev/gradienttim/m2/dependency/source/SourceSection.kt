package dev.gradienttim.m2.dependency.source

import okio.Source

data class SourceSection<S : Any>(
    val source: S?,
    val asc: Source?,
    val md5: Source?,
    val sha1: Source?,
)
