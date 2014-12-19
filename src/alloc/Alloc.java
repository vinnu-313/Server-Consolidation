/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package alloc;

import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vinnu
 */
public class Alloc {

    public static void main(String arg[]) {
        int mem, cpu, id;
        String name = null;
        Iterator it = null;
        ServerManager manager = new ServerManager();
        while (true) {
            try {
                System.out.println("1 - Add Server");
                System.out.println("2 - Add Task");
                System.out.println("3 - Remove Server");
                System.out.println("4 - Remove Task");
                System.out.println("5 - View Server Details");
                System.out.println("6 - View Task Details");
                System.out.println("7 - Exit");
                System.out.print("Enter your choice : ");
                DataInputStream dis = new DataInputStream(System.in);
                int choice = Integer.parseInt(dis.readLine());
                switch (choice) {
                    case 1:
                        System.out.println("Enter Server Details");
                        System.out.print("Name : ");
                        name = dis.readLine();
                        System.out.print("Server Id : ");
                        do {
                            id = Integer.parseInt(dis.readLine());
                            if (manager.getServerById(id) != null) {
                                System.out.println("Server with given id already exist..!");
                                System.out.print("Server Id : ");
                            } else if (id == 0) {
                                System.out.println("Server Id can't be 0(zero)");
                                System.out.print("Server Id : ");
                            }
                        } while (manager.getServerById(id) != null || id == 0);
                        System.out.print("Memory : ");
                        mem = Integer.parseInt(dis.readLine());
                        System.out.print("CPU : ");
                        cpu = Integer.parseInt(dis.readLine());
                        Server s = new Server(name, id, cpu, mem);
                        manager.getServerList().add(s);
                        System.out.println("Server Created.....!");
                        break;
                    case 2:
                        System.out.println("Enter Task Details");
                        System.out.print("Task Id : ");
                        do {
                            id = Integer.parseInt(dis.readLine());
                            if (manager.getTaskById(id) != null) {
                                System.out.println("Task with given id already exist..!");
                                System.out.print("Task Id : ");
                            }
                        } while (manager.getTaskById(id) != null);
                        System.out.print("Memory : ");
                        mem = Integer.parseInt(dis.readLine());
                        System.out.print("CPU : ");
                        cpu = Integer.parseInt(dis.readLine());
                        Task t = new Task(id, cpu, mem);
                        Iterator itr = manager.getServerList().iterator();
                        Server tempServer = null;
                        boolean allocated = false;
                        while (itr.hasNext()) {
                            tempServer = (Server) itr.next();
                            if (tempServer.addTask(t)) {
                                allocated = true;
                                break;
                            }
                        }
                        if (allocated) {
                            System.out.println("Task Allocated on " + tempServer.getName());
                        } else {
                            System.out.println("Unable to allocate task with simple allocation");
                            System.out.println("Trying with migration.....");
                            //Finding the server with highest free CPU.....
                            Server targetServer = manager.getTargetServer();
                            System.out.println("Target Server is ");
                            System.out.println(targetServer);
                            //Finding the maximum task on other servers.....
                            Task[] tarray = new Task[1];
                            tarray = targetServer.getTaskList().toArray(tarray);
                            //Sorting tasks from Target Server
                            for (int i = 0; i < tarray.length - 1; i++) {
                                for (int j = i + 1; j < tarray.length; j++) {
                                    if (tarray[i].getReqCpu() < tarray[j].getReqCpu()) {
                                        Task temp = tarray[j];
                                        tarray[j] = tarray[i];
                                        tarray[i] = temp;
                                    }
                                }
                            }
                            List<Server> serverList=new ArrayList<Server>();
                            for(int i=0;i<manager.getServerList().size();++i){
                                if(manager.getServerList().get(i)!=targetServer){
                                    serverList.add(manager.getServerList().get(i));
                                    System.out.println(manager.getServerList().get(i));
                                }
                            }
//                            for (int i = 0; i < serverArray.length; ++i) {
//                                if (serverArray[i] == targetServer) {
//                                    System.out.println("Target FOund");
//                                    for (int j = i + 1; j < serverArray.length; j++) {
//                                        serverArray[j - 1] = serverArray[j];
//                                    }
//                                    serverArray[serverArray.length-1]=null;
//                                    break;
//                                }
//                            }
                            Server[] serverArray=new Server[serverList.size()];
                            serverArray=serverList.toArray(serverArray);
                            System.out.println("After Removing the Target Server from ServerArray");
                            for(int i=0;i<serverArray.length;++i){
                                System.out.println(serverArray[i]);
                            }
                            //Sorting servers except target
                            for (int i = 0; i < serverArray.length - 1; ++i) {
                                for (int j = i + 1; j < serverArray.length; ++j) {
                                    if (serverArray[i].getCurrentCpu() > serverArray[j].getCurrentCpu()) {
                                        tempServer = serverArray[i];
                                        serverArray[i] = serverArray[j];
                                        serverArray[j] = tempServer;
                                    }
                                }
                            }

                            for (Task task : tarray) {
                                for (Server server : serverArray) {
                                    if (server.isAllocatable(task) && (targetServer.getCurrentCpu() + task.getReqCpu()) > t.getReqCpu()) {
                                        targetServer.removeTask(task);
                                        server.addTask(task);
                                        targetServer.addTask(t);
                                        allocated = true;
                                        break;
                                    }
                                }
                                if (allocated) {
                                    break;
                                }
                            }
                            if (allocated) {
                                System.out.println("Task Allocated on " + targetServer.getName());
                            } else {
                                System.out.println("Unable to Allocate task....!\n Consider adding new server.");
                            }
                        }
                        break;
                    case 3:
                        System.out.print("To remove server, enter it's Server Id : ");
                        id = Integer.parseInt(dis.readLine());
                        if (manager.getServerById(id).isRemovable()) {
                            manager.getServerList().remove(manager.getServerById(id));
                            System.out.println("Server Removed Successfully....!");
                        } else {
                            System.out.println("Unable to remove the server (might have tasks running on it)");
                        }
                        break;
                    case 4:
                        System.out.print("To remove task, enter it's Task Id : ");
                        id = Integer.parseInt(dis.readLine());
                        it = manager.getServerList().iterator();
                        boolean removed = false;
                        while (it.hasNext()) {
                            tempServer = (Server) it.next();
                            if (tempServer.removeTask(id)) {
                                System.out.println("Task Removed Successfully");
                                removed = true;
                                break;
                            }
                        }
                        if (!removed) {
                            System.out.println("Unable to find task on any of the servers..... :(");
                        }
                        break;
                    case 5:
                        System.out.println("Choose server to view it's details");
                        it = manager.getServerList().iterator();
                        while (it.hasNext()) {
                            tempServer = (Server) it.next();
                            System.out.println(tempServer.getId() + " - " + tempServer.getName());
                        }
                        System.out.println("0 - ALL");
                        id = Integer.parseInt(dis.readLine());
                        if (id != 0) {
                            tempServer = manager.getServerById(id);
                            System.out.println(tempServer);
                        } else {
                            it = manager.getServerList().iterator();
                            while (it.hasNext()) {
                                System.out.println(it.next());
                            }
                        }
                        break;
                    case 6:
                        it = manager.getAllTaskList().iterator();
                        while (it.hasNext()) {
                            System.out.println(it.next());
                        }
                        break;
                    case 7:
                        System.exit(0);
                }
            } catch (Exception ex) {
                Logger.getLogger(Alloc.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
