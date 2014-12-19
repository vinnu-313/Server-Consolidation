/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alloc;

/**
 *
 * @author Vinnu
 */
public class Task {

    private int taskId;
    private int reqCpu;
    private int reqMem;

    //Constructors.....
    public Task(int taskId, int reqCpu, int reqMem) {
        this.taskId = taskId;
        this.reqCpu = reqCpu;
        this.reqMem = reqMem;
    }

    public Task(int taskId) {
        this.taskId = taskId;
    }

    public Task() {
    }

    //Getter and Setter
    public int getReqCpu() {
        return reqCpu;
    }

    public void setReqCpu(int reqCpu) {
        this.reqCpu = reqCpu;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getReqMem() {
        return reqMem;
    }

    public void setReqMem(int reqMem) {
        this.reqMem = reqMem;
    }

    @Override
    public String toString() {
        return "\nTask Id = " + taskId + ", Memory = " + reqMem + ", CPU = " + reqCpu;
    }

}
