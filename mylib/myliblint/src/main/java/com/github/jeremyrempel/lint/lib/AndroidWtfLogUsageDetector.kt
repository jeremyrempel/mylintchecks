package com.github.jeremyrempel.lint.lib

import com.android.tools.lint.detector.api.*
import com.intellij.psi.PsiMethod
import org.jetbrains.uast.UCallExpression

/**
 * Look for usages of android.util.Log.wtf and replace with f
 */
class AndroidWtfLogUsageDetector : Detector(), SourceCodeScanner {
    companion object {
        /** Issue describing the problem and pointing to the detector implementation */
        @JvmField
        val ISSUE: Issue = Issue.create(
            // ID: used in @SuppressLint warnings etc
            id = "LogWtfUsage",
            // Title -- shown in the IDE's preference dialog, as category headers in the
            // Analysis results window, etc
            briefDescription = "Using incorrect logging library",
            // Full explanation of the issue; you can use some markdown markup such as
            // `monospace`, *italic*, and **bold**.
            explanation = """
                    Log.wtf should no longer be used. All instances of Log.wtf should be replaced with Log.f
                    """, // no need to .trimIndent(), lint does that automatically
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(
                AndroidWtfLogUsageDetector::class.java, Scope.JAVA_FILE_SCOPE
            )
        )
    }

    override fun getApplicableMethodNames() = listOf("wtf")

    override fun visitMethodCall(context: JavaContext, node: UCallExpression, method: PsiMethod) {
        if (!context.evaluator.isMemberInClass(method, "android.util.Log")) {
            return
        }

        val quickFixData = LintFix.create()
            .name("Use Log.f()")
            .replace()
            .text(method.name)
            .with("f")
            .robot(true) // can be applied automatically
            .independent(true) // does not conflict w/ other auto-fixes
            .build()

        context.report(
            issue = ISSUE,
            scope = node,
            location = context.getCallLocation(
                node,
                includeReceiver = true,
                includeArguments = false
            ),
            message = "Use Log.f rather than Log.wtf",
            quickfixData = quickFixData
        )
    }
}