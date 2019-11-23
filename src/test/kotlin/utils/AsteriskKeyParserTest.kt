package utils

import com.eny.i18n.plugin.utils.*
import org.junit.Test
import kotlin.test.assertEquals

//@Ignore
class AsteriskKeyParserTest : TestBase {

//${fileExpr}:ROOT.Key1.Key31               / *         *{11}:ROOT{4}.Key1{4}.Key31{5}
    @Test
    fun parseExpressionWithFilePartInTemplate() {
        val elements = listOf(
            KeyElement.unresolvedTemplate("\${fileExpr}"),
            KeyElement.literal(":ROOT.Key1.Key31")
        )
        val parsed = parse(elements)
        assertEquals("*{11}:ROOT{4}.Key1{4}.Key31{5}", toTestString(parsed))
    }

//prefix${fileExpr}:ROOT.Key4.Key5          / *         prefix*{17}:ROOT{4}.Key4{4}.Key5{4}
    @Test
    fun parsePrefixedExpressionWithFilePartInTemplate() {
        val elements = listOf(
            KeyElement.literal("prefix"),
            KeyElement.unresolvedTemplate("\${fileExpr}"),
            KeyElement.literal(":ROOT.Key4.Key5")
        )
        val parsed = parse(elements)
        assertEquals("prefix*{17}:ROOT{4}.Key4{4}.Key5{4}", toTestString(parsed))
    }

//${fileExpr}postfix:ROOT.Key4.Key5         / *         *postfix{18}:ROOT{4}.Key4{4}.Key5{4}
    @Test
    fun parsePostfixedExpressionWithFilePartInTemplate() {
        val elements = listOf(
            KeyElement.unresolvedTemplate("\${fileExpr}"),
            KeyElement.literal("postfix"),
            KeyElement.literal(":ROOT.Key4.Key5")
        )
        val parsed = parse(elements)
        assertEquals("*postfix{18}:ROOT{4}.Key4{4}.Key5{4}", toTestString(parsed))
    }

//prefix${fileExpr}postfix:ROOT.Key4.Key5   / *         prefix*postfix{24}:ROOT{4}.Key4{4}.Key5{4}
    @Test
    fun parseMixedExpressionWithFilePartInTemplate() {
        val elements = listOf(
            KeyElement.literal("prefix"),
            KeyElement.unresolvedTemplate("\${fileExpr}"),
            KeyElement.literal("postfix"),
            KeyElement.literal(":ROOT.Key4.Key5")
        )
        val parsed = parse(elements)
        assertEquals("prefix*postfix{24}:ROOT{4}.Key4{4}.Key5{4}", toTestString(parsed))
    }

//prefix${fileExpr}postfix.ROOT.Key4.Key5   / *         prefix*postfix{24}.ROOT{4}.Key4{4}.Key5{4} || prefix*:*postfix{24}.ROOT{4}.Key4{4}.Key5{4}
    @Test
    fun parseNsSeparatorInExpression() {
        val elements = listOf(
            KeyElement.literal("prefix"),
            KeyElement.unresolvedTemplate("\${fileExpr}"),
            KeyElement.literal("postfix"),
            KeyElement.literal(".ROOT.Key4.Key5")
        )
        val parsed = parse(elements)
        assertEquals("prefix*postfix{24}.ROOT{4}.Key4{4}.Key5{4}", toTestString(parsed))
    }

//filename:${key}                           / *         filename{8}:*{6}
    @Test
    fun parseExpressionWithKeyInTemplate() {
        val elements = listOf(
            KeyElement.literal("filename:"),
            KeyElement.unresolvedTemplate("\${key}")
        )
        val parsed = parse(elements)
        assertEquals("filename{8}:*{6}", toTestString(parsed))
    }

//filename:${key}item                       / *         filename{8}:*item{10}
    @Test
    fun parseExpressionWithKeyInTemplate2() {
        val elements = listOf(
            KeyElement.literal("filename:"),
            KeyElement.unresolvedTemplate("\${key}"),
            KeyElement.literal("item")
        )
        val parsed = parse(elements)
        assertEquals("filename{8}:*item{10}", toTestString(parsed))
    }

//filename:${key}.item                      / *         filename{8}:*{6}.item{4}
    @Test
    fun parseExpressionWithKeyInTemplate3() {
        val elements = listOf(
            KeyElement.literal("filename:"),
            KeyElement.unresolvedTemplate("\${key}"),
            KeyElement.literal(".item")
        )
        val parsed = parse(elements)
        assertEquals("filename{8}:*{6}.item{4}", toTestString(parsed))
    }

//filename:root.${key}                      / *         filename{8}:root{4}.*{6}
    @Test
    fun partOfKeyIsExpression() {
        val elements = listOf(
            KeyElement.literal("filename:root."),
            KeyElement.unresolvedTemplate("\${key}")
        )
        val parsed = parse(elements)
        assertEquals("filename{8}:root{4}.*{6}", toTestString(parsed))
    }

//filename:root${key}                       / *         filename{8}:root*{10}
    @Test
    fun partOfKeyIsExpression2() {
        val elements = listOf(
            KeyElement.literal("filename:root"),
            KeyElement.unresolvedTemplate("\${key}")
        )
        val parsed = parse(elements)
        assertEquals("filename{8}:root*{10}", toTestString(parsed))
    }
}