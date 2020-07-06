package com.github.jeremyrempel.lint.lib

import com.android.tools.lint.checks.infrastructure.LintDetectorTest
import org.junit.Test
import java.io.File

class AndroidWtfLogUsageDetectorTest : LintDetectorTest() {
    @Test
    fun `test given kotlin log wtf usage one failure reported`() {
        lint()
            .sdkHome(File("/home/jeremy/Android/Sdk"))
            .files(
                java(
                    """
                    package com.github.jeremyrempel.mylinttest;
                    
                    import android.util.Log;
                    
                    public class TestClass1 {
                        public void doSomething() {
                            Log.wtf("wtf", "this is another err");
                        }
                    }
                    """
                ).indented()
            )
            .run()
            .expect(
                """
src/com/github/jeremyrempel/mylinttest/TestClass1.java:7: Warning: Use Log.f rather than Log.wtf [LogWtfUsage]
        Log.wtf("wtf", "this is another err");
        ~~~~~~~
0 errors, 1 warnings                    
                    """
            )
    }

    override fun getDetector() = AndroidWtfLogUsageDetector()

    override fun getIssues() = listOf(AndroidWtfLogUsageDetector.ISSUE)
}