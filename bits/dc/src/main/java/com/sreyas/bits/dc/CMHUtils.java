package com.sreyas.bits.dc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
@author Sreyas V Pariyath
Nov 17 2020 10:45 PM
 */
public class CMHUtils {

    public static Map<Integer, List<Process>> depMap;
    public static List<Process> procList;

    public static Map<Integer, List<Process>> initDepProcessMap(int[][] graph) {
        depMap = new HashMap<Integer, List<Process>>();
        for (int i = 0; i < graph[0].length; i++) {
            List<Process> depList = new ArrayList<Process>();
            for (int j = 0; j < graph[1].length; j++) {
                if ((graph[i][j] == 1) && i != j) {
                    depList.add(procList.get(j));
                }
            }
            depMap.put(i, depList);
        }
        for (Process process : procList) {
            process.setDepProcessList(depMap.get(process.getNodeNo()));
            process.setNum(process.getDepProcessList().size());
        }
        return depMap;
    }

    public static List<Process> loadProcessList(int numberOfProcess) {
        procList = new ArrayList<Process>();
        for (int i = 0; i < numberOfProcess; i++) {
            Process p = new Process(i);
            procList.add(p);
        }
        return procList;

    }

}
