package com.github.jeremyrempel.applint

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class DeprecatedClassUsageDetector : Detector(), SourceCodeScanner {

    companion object {
        /** Issue describing the problem and pointing to the detector implementation */
        @JvmField
        val ISSUE: Issue = Issue.create(
            // ID: used in @SuppressLint warnings etc
            id = "DeprecatedLib",
            // Title -- shown in the IDE's preference dialog, as category headers in the
            // Analysis results window, etc
            briefDescription = "Deprecated library",
            // Full explanation of the issue; you can use some markdown markup such as
            // `monospace`, *italic*, and **bold**.
            explanation = """
                    This check highlights string literals in code which mentions the word `lint`. \
                    Blah blah blah.

                    Another paragraph here.
                    """, // no need to .trimIndent(), lint does that automatically
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(
                DeprecatedClassUsageDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableConstructorTypes(): List<String> {
        return listOf("com.github.jeremyrempel.mylinttest.MyDeprecatedLibClass")
    }

    override fun visitConstructor(
        context: JavaContext,
        node: UCallExpression,
        constructor: PsiMethod
    ) {
        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getLocation(node),
            message = "`MyDeprecatedLibClass` should not be used."
        )
    }
}