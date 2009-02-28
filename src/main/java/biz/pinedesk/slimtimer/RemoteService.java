package biz.pinedesk.slimtimer;

import java.io.IOException;

/**
 * method by which one communicates with the remote service.
 */
public interface RemoteService {
    Object sendMessage(Crud action, String url, Object message) throws IOException;
}
