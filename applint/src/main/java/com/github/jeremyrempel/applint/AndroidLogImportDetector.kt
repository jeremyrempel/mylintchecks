package com.github.jeremyrempel.applint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import org.jetbrains.uast.*

class AndroidLogImportDetector : Detector(), SourceCodeScanner {

    companion object {
        /** Issue describing the problem and pointing to the detector implementation */
        @JvmField
        val ISSUE: Issue = Issue.create(
            // ID: used in @SuppressLint warnings etc
            id = "AndroidLog",
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
                AndroidLogImportDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement>>? {
        return listOf(UImportStatement::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitImportStatement(node: UImportStatement) {
                val resolved: PsiElement? = node.resolve()
                if (resolved is PsiClass) {
                    val qualifiedName = resolved.qualifiedName

                    if ("android.util.Log" == qualifiedName) {
                        val location: Location = context.getLocation(node)
                        context.report(
                            ISSUE,
                            node,
                            location,
                            "Don't include `android.util.Log` here; use custom logger instead"
                        )
                    }
                }
            }
        }
    }
}