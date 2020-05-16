package com.eny.i18n.plugin.factory

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement

/**
 * Defines translation text extraction
 */
interface TranslationExtractor {
    fun canExtract(element: PsiElement): Boolean
    fun isExtracted(element: PsiElement): Boolean
    fun text(element: PsiElement): String
    fun textRange(element: PsiElement): TextRange = element.parent.textRange
    fun template(element: PsiElement): (argument: String) -> String = {"i18n.t($it)"}
}

interface FoldingProvider {
    fun collectContainers(root: PsiElement): List<PsiElement>
    fun collectLiterals(container: PsiElement): Pair<List<PsiElement>, Int>
    fun getFoldingRange(container: PsiElement, offset: Int, psiElement: PsiElement): TextRange
}