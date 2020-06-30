package com.example.lint.checks

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

class AndroidLogUsageDetector : Detector(), SourceCodeScanner {
    companion object {
        /** Issue describing the problem and pointing to the detector implementation */
        @JvmField
        val ISSUE: Issue = Issue.create(
            // ID: used in @SuppressLint warnings etc
            id = "AndroidLogUsage",
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
                AndroidLogUsageDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }

    // ---- implements SourceCodeScanner ----
    override fun getApplicableMethodNames(): List<String> {
        return listOf("d", "w", "f", "e", "wtf")
    }

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, "android.util.Log")) {
            return
        }
        context.report(
            ISSUE,
            node,
            context.getCallLocation(node, includeReceiver = true, includeArguments = false),
            "Don't include `android.util.Log` here; use custom logger instead"
        )
    }
}