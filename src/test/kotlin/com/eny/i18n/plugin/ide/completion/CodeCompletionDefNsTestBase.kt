package com.eny.i18n.plugin.ide.completion

import com.eny.i18n.plugin.utils.generator.code.*
import com.eny.i18n.plugin.utils.generator.translation.JsonTranslationGenerator
import com.eny.i18n.plugin.utils.generator.translation.TranslationGenerator
import com.eny.i18n.plugin.utils.generator.translation.YamlTranslationGenerator

abstract class CodeCompletionDefNsTestBase(codeGenerator: CodeGenerator, translationGenerator: TranslationGenerator): CodeCompletionTestBase(codeGenerator, translationGenerator, DefaultNsKeyGenerator(), ::DefaultNsChecker) {

    fun testEmptyKeyCompletion() {
        myFixture.addFileToProject("assets/translation.${translationGenerator.ext()}", translationGenerator.generateContent("tstw", "fstt", "leu", "value"))
        myFixture.configureByText("empty.${codeGenerator.ext()}", codeGenerator.generate( "\"<caret>\""))
        val vars = myFixture.completeBasic()
        assertTrue(vars.find {it.lookupString == "tstw"} != null)
    }

    fun testRootKeyCompletion() {
        myFixture.addFileToProject("assets/translation.${translationGenerator.ext()}", translationGenerator.generateContent("tst1", "base", "single", "only one value"))
        myFixture.configureByText("empty.${codeGenerator.ext()}", codeGenerator.generate("\"tst<caret>\""))
        val vars = myFixture.completeBasic()
        assertTrue(vars.find {it.lookupString == "tst1"} != null)
    }
}

internal class CodeCompletionTsJsonDefNsTest: CodeCompletionDefNsTestBase(TsCodeGenerator(), JsonTranslationGenerator())
internal class CodeCompletionJsJsonDefNsTest: CodeCompletionDefNsTestBase(JsCodeGenerator(), JsonTranslationGenerator())
internal class CodeCompletionTsxJsonDefNsTest: CodeCompletionDefNsTestBase(TsxCodeGenerator(), JsonTranslationGenerator())
internal class CodeCompletionJsxJsonDefNsTest: CodeCompletionDefNsTestBase(JsxCodeGenerator(), JsonTranslationGenerator())
internal class CodeCompletionTsYamlDefNsTest: CodeCompletionDefNsTestBase(TsCodeGenerator(), YamlTranslationGenerator())
internal class CodeCompletionJsYamlDefNsTest: CodeCompletionDefNsTestBase(JsCodeGenerator(), YamlTranslationGenerator())
internal class CodeCompletionTsxYamlDefNsTest: CodeCompletionDefNsTestBase(TsxCodeGenerator(), YamlTranslationGenerator())
internal class CodeCompletionJsxYamlDefNsTest: CodeCompletionDefNsTestBase(JsxCodeGenerator(), YamlTranslationGenerator())