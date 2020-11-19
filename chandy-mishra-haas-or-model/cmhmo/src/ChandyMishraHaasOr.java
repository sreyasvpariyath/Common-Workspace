

import java.util.List;
import java.util.Scanner;


/*
@author Sreyas V Pariyath
Nov 17 2020 10:22 PM
 */
public class ChandyMishraHaasOr {


    public static void loadPrereq(int numberOfProcess, int[][] graph) {
        CMHUtils.loadProcessList(numberOfProcess);
        CMHUtils.initDepProcessMap(graph);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        try {
            println("Enter the number of processes");
            final int number_of_proc = sc.nextInt();
            final int graph[][] = new int[number_of_proc][number_of_proc];
            println("Note: Enter 0 if there is no Dependency otherwise 1(Only 0s and 1s will be accepted)");
            println("Enter the wait for graph:");
            for (int i = 0; i < number_of_proc; i++) {
                for (int j = 0; j < number_of_proc; j++) {
                    print("P" + i + " to P" + j + ":");
                    int num = sc.nextInt();
                    num = num < 0 ? 0 : num;
                    num = num > 1 ? 1 : num;
                    graph[i][j] = num;
                }
            }
            println("Enter the Query initiating Process (Process no starts from 0)");
            final int query_initiator = sc.nextInt();
            if (query_initiator >= number_of_proc) {
                println("Provide a valid input");
                return;
            }
            println("Sending Query...");
            printIt(graph);
            loadPrereq(number_of_proc, graph);
            Process pi = CMHUtils.procList.get(query_initiator);
            List<Process> depList = pi.getDepProcessList();
            pi.setWait(true);
            pi.setNum(depList.size());
            for (Process p : depList) {
                pi.queryInit(query_initiator, query_initiator, p.getNodeNo());
            }
        } catch (Exception crap) {
            crap.printStackTrace();
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }

    public static void printIt(int[][] graph) {
        int n = graph[0].length;
        int m = graph.length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                print(graph[i][j] + " ");
            }
            println("");
        }
    }

    static void print(String data) {
        System.out.print(data);
    }

    static void println(String data) {
        System.out.println(data);
    }

}
