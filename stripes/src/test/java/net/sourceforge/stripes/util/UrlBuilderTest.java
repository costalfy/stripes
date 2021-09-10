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

import net.sourceforge.stripes.FilterEnabledTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Simple set of tests for the UrlBuilder class.
 *
 * @author Tim Fennell
 */
public class UrlBuilderTest extends FilterEnabledTestBase {

    @Test
    public void testBasicUrl() {
        String path = "/test/page.jsp";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);
        String result = builder.toString();
        Assertions.assertEquals(result,
                                path);
    }

    @Test
    public void testUrlWithParameters() {
        String path = "/test/page.jsp";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);
        builder.addParameter("one", 1);
        builder.addParameter("two", 2);
        builder.addParameter("three", 3);
        String result = builder.toString();
        Assertions.assertEquals(result,
                                "/test/page.jsp?one=1&two=2&three=3");
    }

    @Test
    public void testUrlWithParametersAndAnchor() {
        String path = "/test/page.jsp#someAnchor";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);
        builder.addParameter("one", 1);
        builder.addParameter("two", 2);
        builder.addParameter("three", 3);
        String result = builder.toString();
        Assertions.assertEquals(result,
                                "/test/page.jsp?one=1&two=2&three=3#someAnchor");
    }

    @Test
    public void testUrlWithParametersAndAnchor2() {
        String path = "/test/page.jsp#someAnchor";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);
        builder.addParameter("one", 1);
        builder.addParameter("two", 2);
        builder.addParameter("three", 3);
        builder.setAnchor("someOtherAnchor");
        String result = builder.toString();
        Assertions.assertEquals(result,
                                "/test/page.jsp?one=1&two=2&three=3#someOtherAnchor");
    }

    @Test
    public void testUrlWithParametersAndAnchor3() {
        String path = "/test/page.jsp#someAnchor";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);
        builder.addParameter("one", 1);
        builder.addParameter("two", 2);
        builder.addParameter("three", 3);
        builder.setAnchor(null);
        String result = builder.toString();
        Assertions.assertEquals(result,
                                "/test/page.jsp?one=1&two=2&three=3");
    }

    @Test
    public void testUrlWithParameterVarargs() {
        String path = "/test/page.jsp";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);
        builder.addParameter("one", 1, "one", "uno", "i");
        String result = builder.toString();
        Assertions.assertEquals(result,
                                "/test/page.jsp?one=1&one=one&one=uno&one=i");
    }

    @Test
    public void testUrlWithValuelessParameter() {
        String path = "/test/page.jsp";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);
        builder.addParameter("one");
        builder.addParameter("two", "");
        builder.addParameter("three");
        String result = builder.toString();
        Assertions.assertEquals(result,
                                "/test/page.jsp?one=&two=&three=");
    }

    @Test
    public void testUrlWithParameterArray() {
        String path = "/test/page.jsp";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);
        builder.addParameter("one",
                             Literal.array("1", "one", "uno", "i"));
        String result = builder.toString();
        Assertions.assertEquals(result,
                                "/test/page.jsp?one=1&one=one&one=uno&one=i");
    }

    @Test
    public void testUrlWithParameterCollection() {
        String path = "/test/page.jsp";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);

        builder.addParameter("one", Literal.list("1", "one", "uno", "i"));
        String result = builder.toString();
        Assertions.assertEquals(result,
                                "/test/page.jsp?one=1&one=one&one=uno&one=i");
    }

    @Test
    public void testUrlWithParameterMap() {
        String path = "/test/page.jsp";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(), path, false);
        Map<Object, Object> map = new LinkedHashMap<>();
        map.put("one", "one");
        map.put("two", Literal.list("2", "two"));
        map.put("three", Literal.array("3", "three"));

        builder.addParameters(map);
        String result = builder.toString();
        Assertions.assertEquals(result,
                                "/test/page.jsp?one=one&two=2&two=two&three=3&three=three");
    }

    @Test
    public void testUrlWithRepeatedParameters() {
        String path = "/test/page.jsp";
        UrlBuilder builder = new UrlBuilder(Locale.getDefault(),
                                            path,
                                            false);
        builder.addParameter("one",
                             "one");
        builder.addParameter("one",
                             "two",
                             "three");
        builder.addParameter("one",
                             "four");

        String result = builder.toString();
        Assertions.assertTrue(result.contains("one=one"));
        Assertions.assertTrue(result.contains("one=two"));
        Assertions.assertTrue(result.contains("one=three"));
        Assertions.assertTrue(result.contains("one=four"));
    }
}
