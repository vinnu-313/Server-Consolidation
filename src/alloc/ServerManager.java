/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alloc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Vinnu
 */
public class ServerManager {

    //
    private List<Server> serverList;

    //Constructors.....
    public ServerManager() {
        serverList = new ArrayList<Server>();
    }

    public ServerManager(List<Server> serverList) {
        this.serverList = serverList;
    }

    //Setter and Getter.....
    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }

    public Server getServerById(int id) {
        Iterator it = serverList.iterator();
        while (it.hasNext()) {
            Server s = (Server) it.next();
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    public Task getTaskById(int id) {
        Iterator it = serverList.iterator();
        while (it.hasNext()) {
            Server s = (Server) it.next();
            Iterator i = (Iterator) s.getTaskList().iterator();
            while (i.hasNext()) {
                Task t = (Task) i.next();
                if (t.getTaskId() == id) {
                    return t;
                }
            }
        }
        return null;
    }

    public Collection<Task> getAllTaskList() {
        Collection<Task> list = new ArrayList<Task>();
        Iterator it = serverList.iterator();
        while (it.hasNext()) {
            Server s = (Server) it.next();
            list.addAll(s.getTaskList());
        }
        return list;
    }

    public Collection<Task> getAllTaskList(Server targetServer) {
        Collection<Task> list = new ArrayList<Task>();
        Iterator it = serverList.iterator();
        while (it.hasNext()) {
            Server s = (Server) it.next();
            if (targetServer != s) {
                list.addAll(s.getTaskList());
            }
        }
        return list;
    }
    
    public Server getTargetServer() {
        Server s = new Server();
        Iterator it = serverList.iterator();
        while (it.hasNext()) {
            Server ts = (Server) it.next();
            if (s.getCurrentCpu() < ts.getCurrentCpu()) {
                s = ts;
            }
        }
        if (s.getCurrentCpu() == 0) {
            return null;
        }
        return s;
    }
    public Server getServerByTask(Task t){
        Iterator it=serverList.iterator();
        while (it.hasNext()) {
            Server ts = (Server) it.next();
            Iterator itr=ts.getTaskList().iterator();
            while (itr.hasNext()) {
                Task task = (Task) itr.next();
                if(task==t){
                    return ts;
                }
            }
        }
        return null;
    }
}
