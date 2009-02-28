package biz.pinedesk.slimtimer;

import biz.pinedesk.slimtimer.util.DateConverter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SlimTimer {
    private LoginRequest loginRequest;

    /**
     * the login credentials. Can't perform any tasks unless we have this.
     */
    private LoginResponse loginResponse;

    private RemoteService remoteService;

    private DateConverter dateConverter = new DateConverter("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public SlimTimer(LoginRequest loginRequest, RemoteService remoteService) {
        this.loginRequest = loginRequest;
        this.remoteService = remoteService;
    }

    public void login() throws IOException {
        if (loginResponse == null) {
            loginResponse = (LoginResponse) remoteService.sendMessage(Crud.create,
                "http://slimtimer.com/users/token",
                loginRequest);
        }
    }

    public boolean isLoggedIn() {return loginResponse != null;}

    public List<Task> getAllTasks() throws IOException {
        login();
        return getAllTasks(null);
    }

    public List<Task> getAllTasks(Role role) throws IOException {
        login();
        String r = role == null ? "" : "&filter[role]=" + role;
        return (List<Task>) remoteService.sendMessage(Crud.list, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/tasks" + getCreds() + r, null);
    }

    /**
     * get tasks, filtered.
     *
     * @param role
     * @param completed If yes, only show complete tasks, if no, only show incomplete tasks.
     * @return
     * @throws IOException
     */
    public List<Task> getTasks(Role role, boolean completed) throws IOException {
        login();
        // &filter[show_completed]=Yes/No/Only
        String sc = completed ? "only" : "no";
        String r = role == null ? "" : "&filter[role]=" + role;
        return (List<Task>) remoteService.sendMessage(Crud.read, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/tasks" + getCreds() + "&filter[show_completed]=" + sc + r, null);
    }

    public Task getTask(int id) throws IOException {
        login();
        return (Task) remoteService.sendMessage(Crud.read, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/tasks/" + id + getCreds(), null);
    }

    public Task updateTask(Task task) throws IOException {
        login();
        return (Task) remoteService.sendMessage(Crud.update, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/tasks/" + task.getId() + getCreds(), task);
    }

    public Task addTask(Task task) throws IOException {
        login();
        return (Task) remoteService.sendMessage(Crud.create, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/tasks" + getCreds(), task);
    }

    public void deleteTask(int id) throws IOException {
        login();
        remoteService.sendMessage(Crud.delete, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/tasks/" + id + getCreds(), null);
    }

    public List<TimeEntry> getAllTimeEntries() throws IOException {
        login();
        return (List<TimeEntry>) remoteService.sendMessage(Crud.list, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/time_entries" + getCreds(), null);
    }

    public List<TimeEntry> getTimeEntries(Date start, Date end) throws IOException {
        login();
        // &filter[range_start]=...&filter[range_end]=...
        String ss = start == null ? "" : "&filter[range_start]=" + dateConverter.dateToString(start);
        String es = end == null ? "" : "&filter[range_end]=" + dateConverter.dateToString(end);
        return (List<TimeEntry>) remoteService.sendMessage(Crud.list, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/time_entries" + getCreds() + ss + es, null);
    }

    public TimeEntry addTimeEntry(TimeEntry entry) throws IOException {
        login();
        entry.setTaskId(entry.getTask().getId()); // hack for a shortcoming in xstream...
        return (TimeEntry) remoteService.sendMessage(Crud.create, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/time_entries" + getCreds(), entry);
    }

    public TimeEntry getTimeEntry(int id) throws IOException {
        login();
        return (TimeEntry) remoteService.sendMessage(Crud.read, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/time_entries/" + id + getCreds(), null);
    }

    public TimeEntry updateTimeEntry(TimeEntry entry) throws IOException {
        login();
        entry.setTaskId(entry.getTask().getId()); // hack for a shortcoming in xstream...
        return (TimeEntry) remoteService.sendMessage(Crud.update, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/time_entries/" + entry.getId() + getCreds(), entry);
    }

    public void deleteTimeEntry(int id) throws IOException {
        login();
        remoteService.sendMessage(Crud.delete, "http://slimtimer.com/users/" +
            loginResponse.getUserId() + "/time_entries/" + id + getCreds(), null);
    }

    public String getCreds() {
        return "?api_key=" + loginRequest.getApiKey() + "&access_token=" + loginResponse.getAccessToken();
    }
}
