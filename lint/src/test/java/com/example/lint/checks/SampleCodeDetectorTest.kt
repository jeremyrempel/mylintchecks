/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lint.checks

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Issue
import java.io.File

@Suppress("UnstableApiUsage")
class SampleCodeDetectorTest : LintDetectorTest() {
    fun testBasic() {

        lint()
            .allowMissingSdk()
            .files(
                java("""
                    package test.pkg;
                    public class TestClass1 {
                        // In a comment, mentioning "lint" has no effect
                        private static String s1 = "Ignore non-word usages: linting";
                        private static String s2 = "Let's say it: lint";
                    }
                    """
                ).indented())
                .run()
                .expect("""
                    src/test/pkg/TestClass1.java:5: Warning: This code mentions lint: Congratulations [ShortUniqueId]
                        private static String s2 = "Let's say it: lint";
                                                   ~~~~~~~~~~~~~~~~~~~~
                    0 errors, 1 warnings
                    """
                )
    }

    override fun getDetector(): Detector {
        return SampleCodeDetector()
    }

    override fun getIssues(): List<Issue> {
        return listOf(SampleCodeDetector.ISSUE)
    }
}