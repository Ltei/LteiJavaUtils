package com.ltei.ljubase

object LIf {

    inline fun <A> noNull(a: A?, block: (a: A) -> Unit) {
        if (a != null) block(a)
    }

    inline fun <A> noNull(a: A?, block: (a: A) -> Unit, fallback: () -> Unit) {
        if (a != null) block(a)
        else fallback()
    }

    inline fun <A, B> noNull(a: A?, b: B?, block: (a: A, b: B) -> Unit) {
        if (a != null && b != null) block(a, b)
    }

    inline fun <A, B> noNull(a: A?, b: B?, block: (a: A, b: B) -> Unit, fallback: () -> Unit) {
        if (a != null && b != null) block(a, b)
        else fallback()
    }

    inline fun <A, B, C> noNull(a: A?, b: B?, c: C?, block: (a: A, b: B, c: C) -> Unit) {
        if (a != null && b != null && c != null) block(a, b, c)
    }

    inline fun <A, B, C> noNull(a: A?, b: B?, c: C?, block: (a: A, b: B, c: C) -> Unit, fallback: () -> Unit) {
        if (a != null && b != null && c != null) block(a, b, c)
        else fallback()
    }

    inline fun <A, B, C, D> noNull(a: A?, b: B?, c: C?, d: D?, block: (a: A, b: B, c: C, d: D) -> Unit) {
        if (a != null && b != null && c != null && d != null) block(a, b, c, d)
    }

    inline fun <A, B, C, D> noNull(a: A?, b: B?, c: C?, d: D?, block: (a: A, b: B, c: C, d: D) -> Unit, fallback: () -> Unit) {
        if (a != null && b != null && c != null && d != null) block(a, b, c, d)
        else fallback()
    }

    inline fun <A, B, C, D, E> noNull(a: A?, b: B?, c: C?, d: D?, e: E?, block: (a: A, b: B, c: C, d: D, e: E) -> Unit) {
        if (a != null && b != null && c != null && d != null && e != null) block(a, b, c, d, e)
    }

    inline fun <A, B, C, D, E, F> noNull(a: A?, b: B?, c: C?, d: D?, e: E?, f: F?, block: (a: A, b: B, c: C, d: D, e: E, f: F) -> Unit) {
        if (a != null && b != null && c != null && d != null && e != null && f != null) block(a, b, c, d, e, f)
    }

    inline fun <A, B, C, D, E, F, G> noNull(a: A?, b: B?, c: C?, d: D?, e: E?, f: F?, g: G?, block: (a: A, b: B, c: C, d: D, e: E, f: F, g: G) -> Unit) {
        if (a != null && b != null && c != null && d != null && e != null && f != null && g != null) block(a, b, c, d, e, f, g)
    }

    inline fun <A, B, C, D, E, F, G, H> noNull(a: A?, b: B?, c: C?, d: D?, e: E?, f: F?, g: G?, h: H?, block: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H) -> Unit) {
        if (a != null && b != null && c != null && d != null && e != null && f != null && g != null && h != null) block(a, b, c, d, e, f, g, h)
    }

    inline fun <A, B, C, D, E, F, G, H, I> noNull(a: A?, b: B?, c: C?, d: D?, e: E?, f: F?, g: G?, h: H?, i: I?, block: (a: A, b: B, c: C, d: D, e: E, f: F, g: G, h: H, i: I) -> Unit) {
        if (a != null && b != null && c != null && d != null && e != null && f != null && g != null && h != null && i != null) block(a, b, c, d, e, f, g, h, i)
    }

}