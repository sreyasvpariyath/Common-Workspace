package com.sreyas.bits.dc;

import java.util.ArrayList;
import java.util.List;
import static com.sreyas.bits.dc.CMHUtils.*;
/*
@author Sreyas V Pariyath
Nov 17 2020 11:02 PM
 */
public class Process {

    int nodeNo;
    boolean isEngaging;
    boolean wait;
    int num;
    int whoEngaged;
    List<Process> depProcessList = new ArrayList<Process>();

    public Process(int nodeNo) {
        this.nodeNo = nodeNo;
        this.isEngaging = true;
        this.wait = true;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setNodeNo(int nodeNo) {
        this.nodeNo = nodeNo;
    }

    public void setEngaging(boolean engaging) {
        isEngaging = engaging;
    }

    public int getNum() {
        return num;
    }

    public boolean isWait() {
        return wait;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public int getNodeNo() {
        return nodeNo;
    }

    public boolean isEngaging() {
        return isEngaging;
    }

    public List<Process> getDepProcessList() {
        return depProcessList;
    }

    public void setDepProcessList(List<Process> depProcessList) {
        this.depProcessList = depProcessList;
    }

    void receiver1(int i, int j, int k) //receive query
    {
        if (isEngaging) {
            isEngaging = false;

        }
    }

    void queryInit(int i, int j, int k) {
        //a a b
        if (isEngaging) {
            whoEngaged = j;
            num = getNum();
            wait = true;
            isEngaging = false;
            for (Process process : depProcessList) {
                process.queryInit(i, nodeNo, process.getNodeNo());
            }
        } else {
            if (wait) {
                Process p = procList.get(j);
                p.replyListner(i, nodeNo, k);
            }
        }
    }
    
    void replyListner(int i, int j, int k) {
        if (wait) {
            num--;
            if (num == 0) {
                if (i == k) {
                    System.out.println("DEADLOCK DETECTED");
                } else {
                    Process p = procList.get(whoEngaged);
                    p.replyListner(i, nodeNo, j);
                }
            }
        }
    }
}
