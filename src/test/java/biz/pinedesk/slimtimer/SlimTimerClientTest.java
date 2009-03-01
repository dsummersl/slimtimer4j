/**
 * Copyright 2009: Dane Summers<dsummersl@yahoo.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package biz.pinedesk.slimtimer;

import junit.framework.TestCase;
import static org.easymock.EasyMock.*;
import org.picocontainer.DefaultPicoContainer;

import java.io.IOException;

public class SlimTimerClientTest extends TestCase {
    public void testLogin() throws IOException {
        String apiKey = "abid";
        RemoteService remoteService = createMock(RemoteService.class);
        expect(remoteService.sendMessage(eq(Crud.create),
            eq("http://slimtimer.com/users/token"),
            eq(new LoginRequest("me", "pass", apiKey)))).andReturn(new LoginResponse(1, "a token"));
        replay(remoteService);

        DefaultPicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(remoteService);
        pico.addComponent(new LoginRequest("me", "pass", apiKey));
        pico.addComponent(SlimTimer.class);

        SlimTimer slimTimer = pico.getComponent(SlimTimer.class);
        slimTimer.login();
        assertTrue(slimTimer.isLoggedIn());
        verify(remoteService);

        reset(remoteService);
        expect(remoteService.sendMessage(eq(Crud.create), (String) anyObject(), anyObject())).andThrow(new IOException(
            "failed"));
        replay(remoteService);

        pico = new DefaultPicoContainer();
        pico.addComponent(remoteService);
        pico.addComponent(new LoginRequest("me", "pass", apiKey));
        pico.addComponent(SlimTimer.class);

        slimTimer = pico.getComponent(SlimTimer.class);
        try {
            slimTimer.login();
            fail();
        }
        catch (IOException e) { }
        assertFalse(slimTimer.isLoggedIn());
        verify(remoteService);
    }

    public void testTasks() {
        // TODO you can do CRUD for tasks
    }

    public void testTimeEntries() {
        // TODO you can do CRUD for time entries.
    }
}
