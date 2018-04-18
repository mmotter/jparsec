package org.jparsec.examples.set;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SetParserTest {

    @Test
    public void testEvaluate() {
        assertResult(true, "TRUE");
        assertResult(false, "FALSE");
        assertResult(true, "(TRUE)");
        assertResult(false, "(FALSE)");

        // Test Union
        assertResult(true, "TRUE|TRUE");
        assertResult(true, "TRUE|FALSE");
        assertResult(false, "FALSE|FALSE");

        // Test Intersection
        assertResult(true, "TRUE&TRUE");
        assertResult(false, "TRUE&FALSE");
        assertResult(false, "FALSE&FALSE");

        // Test Complement
        assertResult(false, "!TRUE");
        assertResult(true, "!FALSE");

        // Test Difference
        assertResult(false, "TRUE&!TRUE");
        assertResult(true, "TRUE&!FALSE");

    }

    @Test
    public void testCohortEvaluate() {
        // Valid List of Cohorts exists in Cohort class
        // Includes: ABC, DEF, XYZ
        // All other Cohort ids might exist, but a patient
        // is not in those cohorts.
        assertCohortResult(true, "ABC");
        assertCohortResult(true, "DEF");
        assertCohortResult(true, "XYZ");
        assertCohortResult(false, "GHI");

        // Test Union
        assertCohortResult(true, "ABC|DEF");
        assertCohortResult(true, "ABC|DEF|GHI");
        assertCohortResult(false, "GHI|LMO");

        // Test Intersection
        assertCohortResult(true, "ABC&DEF");
        assertCohortResult(false, "ABC&GHI");
        assertCohortResult(false, "GHI&LMO");

        // Test Complement
        assertCohortResult(false, "!ABC");
        assertCohortResult(true, "!LMO");
    }

    private static void assertResult(Boolean expected, String source) {
        assertEquals(expected, SetParser.evaluate(source));
    }

    private static void assertCohortResult(Boolean expected, String source) {
        assertEquals(expected, CohortParser.evaluate(source));
    }
}
