package com.mygdx.game.functions

fun <A, B, C> compose (fnA: (A) -> B, fnB: (B) -> C): (A) -> C {
    return { a: A  -> fnB(fnA(a)) }
}

typealias Func<A, B> = (A) -> B


