package com.eny.i18n.plugin.language.jsx

import com.eny.i18n.plugin.factory.FoldingProvider
import com.eny.i18n.plugin.factory.LanguageFactory
import com.eny.i18n.plugin.factory.TranslationExtractor
import com.eny.i18n.plugin.utils.toBoolean
import com.intellij.lang.ecmascript6.JSXHarmonyFileType
import com.intellij.lang.javascript.JavascriptLanguage
import com.intellij.lang.javascript.TypeScriptJSXFileType
import com.intellij.lang.javascript.patterns.JSPatterns
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlTag

/**
 * Vue language components factory
 */
class JsxLanguageFactory: LanguageFactory {
    override fun translationExtractor(): TranslationExtractor = JsxTranslationExtractor()
    /**
     * No special implementation for folding builder is required
     */
    override fun foldingProvider(): FoldingProvider = object: FoldingProvider {
        override fun collectContainers(root: PsiElement): List<PsiElement> = emptyList()
        override fun collectLiterals(container: PsiElement): Pair<List<PsiElement>, Int> = Pair(emptyList(), 0)
        override fun getFoldingRange(container: PsiElement, offset: Int, psiElement: PsiElement): TextRange = TextRange.EMPTY_RANGE
    }
}

internal class JsxTranslationExtractor: TranslationExtractor {
    override fun canExtract(element: PsiElement): Boolean =
        listOf(JSXHarmonyFileType.INSTANCE, TypeScriptJSXFileType.INSTANCE).any { it == element.containingFile.fileType } &&
            !PsiTreeUtil.findChildOfType(PsiTreeUtil.getParentOfType(element, XmlTag::class.java), XmlTag::class.java).toBoolean()

    override fun isExtracted(element: PsiElement): Boolean =
        element.isJs() && JSPatterns.jsArgument("t", 0).accepts(element.parent)

    override fun text(element: PsiElement): String {
        val parentTag = PsiTreeUtil.getParentOfType(element, XmlTag::class.java)
        return if (parentTag != null) {
            parentTag.value.textElements.map {it.text}.joinToString(" ")
        } else {
            element.parent.text
        }
    }
    override fun textRange(element: PsiElement): TextRange =
        PsiTreeUtil.getParentOfType(element, XmlTag::class.java)
            ?.value
            ?.textElements
            ?.let {
                TextRange(
                    it.first().textRange.startOffset,
                    it.last().textRange.endOffset
                )
            } ?:
        TextRange(
            element.parent.textRange.startOffset,
            element.parent.textRange.endOffset
        )
    private fun PsiElement.isJs(): Boolean = this.language == JavascriptLanguage.INSTANCE
}