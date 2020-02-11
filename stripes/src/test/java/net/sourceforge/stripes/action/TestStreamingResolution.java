/* Copyright 2014 Ben Gunter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.stripes.action;

import net.sourceforge.stripes.mock.MockHttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.mail.internet.ContentDisposition;
import javax.mail.internet.ParseException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class TestStreamingResolution {

    @Test
    public void testContentDisposition() throws Exception {
        doTestContentDisposition(true, UUID.randomUUID().toString());
        doTestContentDisposition(false, UUID.randomUUID().toString());
        doTestContentDisposition(true, null);
        doTestContentDisposition(false, null);
    }

    private void doTestContentDisposition(boolean attachment, String filename) throws Exception {
        byte[] data = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
        ByteArrayInputStream is = new ByteArrayInputStream(data);

        StreamingResolution resolution = new StreamingResolution("application/octet-stream", is);
        resolution.setAttachment(attachment);
        if (filename != null) {
            resolution.setFilename(filename);
        }

        MockHttpServletResponse response = new MockHttpServletResponse();
        resolution.applyHeaders(response);
        resolution.stream(response);
        Assertions.assertArrayEquals(data,
                                     response.getOutputBytes());

        ContentDisposition disposition = getContentDisposition(response);
        if (attachment) {
            if (filename == null) {
                Assertions.assertNotNull(disposition);
                Assertions.assertEquals("attachment",
                                        disposition.getDisposition());
                Assertions.assertNull(disposition.getParameter("filename"));
            } else {
                Assertions.assertNotNull(disposition);
                Assertions.assertEquals("attachment",
                                        disposition.getDisposition());
                Assertions.assertNotNull(disposition.getParameter("filename"));
            }
        } else {
            if (filename == null) {
                Assertions.assertNull(disposition);
            } else {
                Assertions.assertNotNull(disposition);
                Assertions.assertEquals("attachment",
                                        disposition.getDisposition());
                Assertions.assertNotNull(disposition.getParameter("filename"));
            }
        }
    }

    private ContentDisposition getContentDisposition(MockHttpServletResponse response)
            throws ParseException {
        final List<Object> list = response.getHeaderMap().get("Content-Disposition");
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return new ContentDisposition(list.get(0).toString());
        }
    }
}
