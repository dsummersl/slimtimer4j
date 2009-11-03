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


import org.picocontainer.DefaultPicoContainer;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SlimTimerClient {
    public static void main(String[] args) {
        String apiKey = System.getProperty("apiKey");
        DefaultPicoContainer pico = new DefaultPicoContainer();
        pico.addComponent(new SlimTimerRemoteService());
        pico.addComponent(new LoginRequest(new User(System.getProperty("user"), System.getProperty("password")),
            apiKey));
        pico.addComponent(SlimTimer.class);

        try {
            SlimTimer slimTimer = pico.getComponent(SlimTimer.class);
            slimTimer.login();
            System.out.println("logged in? " + slimTimer.isLoggedIn());

            Task task = new Task();
            task.setName("Test Task");
            task = slimTimer.addTask(task);
            System.out.println("CREATE");
            System.out.println("Task: " + task.getName() + "(" + task.getId() + ")");
            System.out.println("tags: " + task.getTags());
            System.out.println(" hrs: " + task.getHours());

            task = slimTimer.getTask(task.getId());
            System.out.println("READ");
            System.out.println("Task: " + task.getName() + "(" + task.getId() + ")");
            System.out.println("tags: " + task.getTags());
            System.out.println(" hrs: " + task.getHours());

            task.setTags("one,two");
            task = slimTimer.updateTask(task);
            System.out.println("UPDATE");
            System.out.println("Task: " + task.getName() + "(" + task.getId() + ")");
            System.out.println("tags: " + task.getTags());
            System.out.println(" hrs: " + task.getHours());

            slimTimer.deleteTask(task.getId());
            System.out.println("DELETE");
            try {
                task = slimTimer.getTask(task.getId());
                System.out.println("... uh oh ... maybe it didn't delete...");
            }
            catch (IOException e) { }

            List<Task> tasks = slimTimer.getTasks(null, false);
            System.out.println("LIST");
            for (int i = 0; i < tasks.size(); i++) {
                task = tasks.get(i);
                System.out.println("Task: " + task.getName() + "(" + task.getId() + ")");
                System.out.println("tags: " + task.getTags());
                System.out.println(" hrs: " + task.getHours());
            }

            TimeEntry entry = new TimeEntry();
            entry.setStartTime(new Date());
            entry.setDurationInSeconds(1);
            entry.setInProgress(true);
            entry.setTask(slimTimer.getTask(task.getId()));
            entry = slimTimer.addTimeEntry(entry);
            System.out.println("CREATE");
            System.out
                .println("Entry: " + entry.getTask().getId() + " - " + entry.getComments() + " - " + entry.getId());
            System.out.println(" tags: " + entry.getTags());
            System.out.println(" stat: " + entry.isInProgress());

            System.out.println("\n*READ*\n");
            entry = slimTimer.getTimeEntry(entry.getId());
            System.out
                .println("Entry: " + entry.getTask().getId() + " - " + entry.getComments() + " - " + entry.getId());
            System.out.println(" tags: " + entry.getTags());
            System.out.println(" stat: " + entry.isInProgress());

            System.out.println("\n*UPDATE*\n");
            entry.setEndTime(new Date());
            entry = slimTimer.updateTimeEntry(entry);
            System.out
                .println("Entry: " + entry.getTask().getId() + " - " + entry.getComments() + " - " + entry.getId());
            System.out.println(" tags: " + entry.getTags());
            System.out.println(" stat: " + entry.isInProgress());

            System.out.println("DELETE");
            slimTimer.deleteTimeEntry(entry.getId());
            try {
                entry = slimTimer.getTimeEntry(entry.getId());
                System.out.println("... uh oh ... maybe it didn't delete...");
            }
            catch (IOException e) { }

            List<TimeEntry> entries = slimTimer.getTimeEntries(new Date(2009 - 1900, 2 - 1, 1),
                new Date(2009 - 1900, 2 - 1, 10));
            for (int i = 0; i < entries.size(); i++) {
                TimeEntry e = entries.get(i);
                System.out.println("Entry: " + e.getTask().getName() + " - " + e.getComments());
                System.out.println(" tags: " + e.getTags());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
