package moe.kadosawa.ayami.utils

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default

object Args {
    val parser = ArgParser("Ayami")

    val configPath by parser.option(ArgType.String, fullName = "config", shortName = "c")
        .default("config.properties")

    val refreshSlash by parser.option(ArgType.Boolean, fullName = "refreshSlash")
        .default(false)

    val dbInit by parser.option(ArgType.Boolean, fullName = "dbInit")
        .default(false)
}