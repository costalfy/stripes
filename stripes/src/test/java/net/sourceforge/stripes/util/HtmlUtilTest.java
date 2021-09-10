/* Copyright (C) 2005 Tim Fennell
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the license with this software. If not,
 * it can be found online at http://www.fsf.org/licensing/licenses/lgpl.html
 */
package net.sourceforge.stripes.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Unit tests for the HtmlUtil class..
 */
public class HtmlUtilTest {

    @Test
    public void testJoinAndSplit() {
        String[] input = {"foo", "bar", "foobar"};
        List<String> listInput = Arrays.asList(input);

        String combined = HtmlUtil.combineValues(listInput);
        Collection<String> output = HtmlUtil.splitValues(combined);

        Assertions.assertEquals(output.size(),
                                listInput.size(),
                                "Different number of params.");
        Assertions.assertTrue(output.contains("foo"));
        Assertions.assertTrue(output.contains("bar"));
        Assertions.assertTrue(output.contains("foobar"));
    }

    @Test
    public void testJoinWithNoStrings() {
        String combined = HtmlUtil.combineValues(null);
        Assertions.assertEquals(combined,
                                "");

        combined = HtmlUtil.combineValues(new HashSet<>());
        Assertions.assertEquals(combined,
                                "");
    }

    @Test
    public void testSplitWithNoValues() {
        Collection<String> values = HtmlUtil.splitValues(null);
        Assertions.assertNotNull(values);
        Assertions.assertEquals(values.size(),
                                0);

        values = HtmlUtil.splitValues("");
        Assertions.assertNotNull(values);
        Assertions.assertEquals(values.size(),
                                0);
    }
}
