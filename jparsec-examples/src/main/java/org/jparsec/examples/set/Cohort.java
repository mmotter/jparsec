package org.jparsec.examples.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Cohort {
    private final String label;
    private static final ArrayList<String> cohortList = new ArrayList<String>(
            Arrays.asList("ABC", "DEF", "XYZ"));
    public Cohort(CharSequence label) {
        this.label = label.toString();
    }

    public static boolean in(String s) {
        return cohortList.contains(s);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(label);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        final Cohort other = (Cohort) obj;

        return Objects.equals(this.label, other.label);
    }

    @Override
    public String toString() {
        return "Cohort{" + "label='" + label + '\'' + '}';
    }
}
