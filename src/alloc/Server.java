/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alloc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Vinnu
 */
public class Server {

    private String name;
    private int id;
    private int totalCpu;
    private int totalMem;
    private int currentCpu;
    private int currentMem;
    Collection<Task> taskList;

    public Server(String name, int id, int totalCpu, int totalMem) {
        this.name = name;
        this.id = id;
        this.totalCpu = totalCpu;
        this.totalMem = totalMem;
        this.currentCpu = totalCpu;
        this.currentMem = totalMem;
        this.taskList = new ArrayList<Task>();
    }

    public Server() {
        this.totalCpu = 0;
        this.totalMem = 0;
        this.currentCpu = 0;
        this.currentMem = 0;
        this.id = 0;
        this.name = null;
        this.taskList = new ArrayList<Task>();
    }

    public Server(int totalCpu, int totalMem, Collection<Task> taskList) {
        this.totalCpu = totalCpu;
        this.totalMem = totalMem;
        this.currentCpu = totalCpu;
        this.currentMem = totalMem;
        this.taskList = taskList;
    }

    public Server(int totalCpu, int totalMem) {
        this.totalCpu = totalCpu;
        this.totalMem = totalMem;
        this.currentCpu = totalCpu;
        this.currentMem = totalMem;
        this.taskList = new ArrayList<Task>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(Collection<Task> taskList) {
        this.taskList = taskList;
    }

    public int getTotalCpu() {
        return totalCpu;
    }

    public void setTotalCpu(int totalCpu) {
        this.totalCpu = totalCpu;
    }

    public int getTotalMem() {
        return totalMem;
    }

    public void setTotalMem(int totalMem) {
        this.totalMem = totalMem;
    }

    public int getCurrentCpu() {
        return currentCpu;
    }

    public void setCurrentCpu(int currentCpu) {
        this.currentCpu = currentCpu;
    }

    public int getCurrentMem() {
        return currentMem;
    }

    public void setCurrentMem(int currentMem) {
        this.currentMem = currentMem;
    }

    public boolean isAllocatable(Task t) {
        if (this.currentCpu >= t.getReqCpu() && this.currentMem >= t.getReqMem()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean addTask(Task t) {
        if (isAllocatable(t)) {
            taskList.add(t);
            currentCpu -= t.getReqCpu();
            currentMem -= t.getReqMem();
            return true;
        } else {
            return false;
        }
    }

    public boolean isRemovable() {
        return taskList.isEmpty();
    }

    public boolean removeTask(Task t) {
        boolean b = taskList.remove(t);
        if (b) {
            currentCpu += t.getReqCpu();
            currentMem += t.getReqMem();
        }
        return b;
    }

    public boolean removeTask(int id) {
        Iterator it = taskList.iterator();
        while (it.hasNext()) {
            Task t = (Task) it.next();
            if (t.getTaskId() == id) {
                taskList.remove(t);
                currentCpu += t.getReqCpu();
                currentMem += t.getReqMem();
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nServer Id : ").append(this.getId());
        sb.append("\nServer Name : ").append(this.getName());
        sb.append("\nTotal Memory : ").append(this.getTotalMem());
        sb.append("\nTotal CPU : ").append(this.getTotalCpu());
        sb.append("\nCurrent Memory : ").append(this.getCurrentMem());
        sb.append("\nCurrent CPU : ").append(this.getCurrentCpu());
        if (this.getTaskList().size() > 0) {
            sb.append("\n******* TASK LIST *********");
            Iterator it = this.getTaskList().iterator();
            while (it.hasNext()) {
                sb.append(it.next());
            }
        } else {
            sb.append("\nNo Tasks in tasklist\n");
        }
        return new String(sb);
    }
}
