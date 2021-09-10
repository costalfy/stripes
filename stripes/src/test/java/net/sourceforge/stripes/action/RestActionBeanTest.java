/*
 * Copyright 2014 Rick Grashel.
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
package net.sourceforge.stripes.action;

import net.sourceforge.stripes.FilterEnabledTestBase;
import net.sourceforge.stripes.mock.MockRoundtrip;
import net.sourceforge.stripes.util.Log;
import net.sourceforge.stripes.validation.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.HttpURLConnection;
import java.util.*;

/**
 * This is a series of tests for Stripes REST action beans.
 */
@RestActionBean
@UrlBinding("/test/{person}")
public class RestActionBeanTest extends FilterEnabledTestBase implements ActionBean {

    private static final Log log = Log.getInstance(RestActionBeanTest.class);

    @Validate(on = "head", required = true)
    private String id;

    @ValidateNestedProperties({
        @Validate(field = "id", required = true, on = "boundPersonEvent")
    })
    @Validate(converter = PersonTypeConverter.class)
    private Person person;

    public Resolution customHttpVerb() {
        return new JsonResolution("Yay!  This is a custom HTTP verb!");
    }

    public Resolution get() {
        Map< String, Object> response = new HashMap<>();
        response.put("foo", "bar");
        response.put("hello", "world");
        response.put("person", new Person());

        Map< String, Number> nested = new HashMap<>();
        nested.put("one", 1);
        nested.put("two", 2);

        response.put("numbers", nested);

        return new JsonResolution(response);
    }

    @POST
    public Resolution boundPersonEvent() {
        return new JsonResolution(getPerson());
    }

    public Resolution jsonResolutionWithExclusion() {
        Person p = new Person();
        return new JsonResolution(p, "firstName");
    }

    @ValidationMethod(on = "head")
    public void validateHeadCall(ValidationErrors errors) {
        errors.addGlobalError(new SimpleError("The head request was not valid for whatever custom reason."));
    }

    public Resolution head() {
        return new JsonResolution("Successful head!");
    }

    public Resolution testUnhandledExceptionEvent() {
        throw new RuntimeException("Some Unhandled Exception Occurred!");
    }

    @POST
    public Resolution onlySupportsPost() {
        return new JsonResolution("Successful onlySupportsPost()!");
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }

    private ActionBeanContext context;

    public ActionBeanContext getContext() {
        return this.context;
    }

    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Test
    public void testGetAttemptOnPostMethod() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.getRequest().setMethod("GET");
        trip.execute("onlySupportsPost");
        Assertions.assertEquals(trip.getResponse()
                                        .getStatus(),
                                HttpURLConnection.HTTP_BAD_METHOD);
        logTripResponse(trip);
    }

    @Test
    public void testPostAttemptOnPostMethod() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.getRequest().setMethod("POST");
        trip.execute("onlySupportsPost");
        Assertions.assertEquals(trip.getResponse()
                                        .getStatus(),
                                HttpURLConnection.HTTP_OK);
        logTripResponse(trip);
    }

    @Test
    public void testSuccessfulGet() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.getRequest().setMethod("GET");
        trip.execute();
        Assertions.assertEquals(trip.getResponse()
                                        .getStatus(),
                                HttpURLConnection.HTTP_OK);
        logTripResponse(trip);
    }

    @Test
    public void testFailedPost() throws Exception {
        // Since no event is specified, this should default to the post() method and attempt
        // to execute it.  Since one doesn't exist, it should throw a 404.
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.getRequest().setMethod("POST");
        trip.execute();
        Assertions.assertEquals(trip.getResponse()
                                        .getStatus(),
                                HttpURLConnection.HTTP_NOT_FOUND);
        logTripResponse(trip);
    }

    @Test
    public void testMissingRequiredParameterOnHead() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.getRequest().setMethod("HEAD");
        trip.execute();
        Assertions.assertTrue(trip.getValidationErrors()
                                      .hasFieldErrors() && trip.getValidationErrors()
                                                                   .size() == 1);
        logTripResponse(trip);
    }

    @Test
    public void testFailedCustomValidationOnHead() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.setParameter("id", "SOME_ID");
        trip.getRequest().setMethod("HEAD");
        trip.execute();
        Assertions.assertTrue(!trip.getValidationErrors()
                .hasFieldErrors() && trip.getValidationErrors()
                                             .size() == 1);
        logTripResponse(trip);
    }

    @Test
    public void testCustomHTTPVerb() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.getRequest().setMethod("customHttpVerb");
        trip.execute();
        Assertions.assertEquals(trip.getResponse()
                                        .getStatus(),
                                HttpURLConnection.HTTP_OK);
        logTripResponse(trip);
    }

    @Test
    public void testJsonResolutionWithPropertyExclusion() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), getClass());
        trip.getRequest().setMethod("jsonResolutionWithExclusion");
        trip.execute();
        Assertions.assertFalse(trip.getResponse()
                                       .getOutputString()
                                       .toUpperCase()
                                       .contains("FIRSTNAME"));
        logTripResponse(trip);
    }

    @Test
    public void testJsonBindingFromRequestBody() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(),
                                               "/test/1");
        trip.getRequest()
                .addHeader("Content-Type",
                           "application/json");
        trip.getRequest()
                .setMethod("POST");
        String json = "{ \"person\" : { \"firstName\":\"Jane\", \"lastName\":\"Johnson\", \"favoriteFoods\" : [\"Snickers\",\"Scotch\",\"Pizza\"], \"children\" : [{ \"firstName\": \"Jackie\"},{\"firstName\":\"Janie\"}]}}";
        trip.getRequest()
                .setRequestBody(json);
        trip.execute("boundPersonEvent");
        RestActionBeanTest bean = trip.getActionBean(getClass());
        Assertions.assertEquals(bean.getPerson()
                                        .getId(),
                                "1");
        Assertions.assertEquals(bean.getPerson()
                                        .getFirstName(),
                                "Jane");
        Assertions.assertEquals(bean.getPerson()
                                        .getLastName(),
                                "Johnson");
        Assertions.assertEquals(bean.getPerson()
                                        .getChildren()
                                        .size(),
                                2);
        List<String> favoriteFoods = new ArrayList<>();
        favoriteFoods.add("Snickers");
        favoriteFoods.add("Scotch");
        favoriteFoods.add("Pizza");
        Assertions.assertEquals(bean.getPerson()
                                        .getFavoriteFoods(),
                                favoriteFoods);

        logTripResponse(trip);
    }

    @Test
    public void testThereIsNoJsonBindingWithoutRequestBody() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(),
                                               "/test/1");
        trip.getRequest()
                .addHeader("Content-Type",
                           "application/json");
        trip.getRequest()
                .setMethod("POST");
        String emptyBody = "";
        trip.getRequest()
                .setRequestBody(emptyBody);
        trip.execute("boundPersonEvent");
        RestActionBeanTest bean = trip.getActionBean(getClass());
        Assertions.assertEquals(bean.getPerson()
                                        .getId(),
                                "1");
        Assertions.assertEquals(bean.getPerson()
                                        .getFirstName(),
                                "John");
        Assertions.assertEquals(bean.getPerson()
                                        .getLastName(),
                                "Doe");
        Assertions.assertEquals(bean.getPerson()
                                        .getChildren()
                                        .size(),
                                0);
        Assertions.assertEquals(bean.getPerson()
                                        .getFavoriteFoods()
                                        .size(),
                                0);

        logTripResponse(trip);
    }

    @Test
    /**
     * This tests to make sure that a JSON request that is bound to an event has
     * its validation handled properly. In this case, the person.id is a
     * required field and is not bound.
     */
    public void testJsonBindingFromRequestBodyWithValidationError() throws Exception {
        MockRoundtrip trip = new MockRoundtrip(getMockServletContext(), "/test");
        trip.getRequest().addHeader("Content-Type", "application/json");
        trip.getRequest().setMethod("POST");
        String json = "{ \"person\" : { \"firstName\":\"Jane\", \"lastName\":\"Johnson\", \"favoriteFoods\" : [\"Snickers\",\"Scotch\",\"Pizza\"], \"children\" : [{ \"firstName\": \"Jackie\"},{\"firstName\":\"Janie\"}]}}";
        trip.getRequest().setRequestBody(json);
        trip.execute("boundPersonEvent");
        Assertions.assertTrue(trip.getValidationErrors()
                                      .hasFieldErrors());

        logTripResponse(trip);
    }

    private void logTripResponse(MockRoundtrip trip) {
        log.debug("TRIP RESPONSE: [Event=" + trip.getActionBean(getClass()).getContext().getEventName() + "] [Status=" + trip.getResponse().getStatus()
                + "] [Message=" + trip.getResponse().getOutputString() + "] [Error Message="
                + trip.getResponse().getErrorMessage() + "]");
    }

    public static class Person {

        String id = null;
        String firstName = "John";
        String lastName = "Doe";
        List< String> favoriteFoods = new ArrayList<>();
        List< Person> children = new ArrayList<>();

        public void setChildren(List< Person> children) {
            this.children = children;
        }

        public List< Person> getChildren() {
            return this.children;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public void setFavoriteFoods(List< String> favoriteFoods) {
            this.favoriteFoods = favoriteFoods;
        }

        public List< String> getFavoriteFoods() {
            return this.favoriteFoods;
        }

        public String getFirstName() {
            return this.firstName;
        }

        public String getLastName() {
            return this.lastName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }

    public static class PersonTypeConverter implements TypeConverter<Person> {

        public void setLocale(Locale locale) {
        }

        public Locale getLocale() {
            return Locale.getDefault();
        }

        public Person convert(String input, Class<? extends Person> targetType, Collection<ValidationError> errors) {
            Person p = new Person();
            p.setId(input);
            return p;
        }
    }
}
