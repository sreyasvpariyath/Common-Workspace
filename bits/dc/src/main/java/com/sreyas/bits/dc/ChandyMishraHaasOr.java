package com.sreyas.bits.dc;

import java.util.List;

import static com.sreyas.bits.dc.CMHUtils.*;

/*
@author Sreyas V Pariyath
Nov 17 2020 10:22 PM
 */
public class ChandyMishraHaasOr {


    public static void loadPrereq(int numberOfProcess,int[][] graph)
    {
        loadProcessList(numberOfProcess);
        initDepProcessMap(graph);

    }

    public static void main(String[] args) {


        int[][]graph=new int[6][6];
        int number_of_proc=6;
        final int query_initiator=0;
        //**************************************************SAMPLE FROM SLIDE******************************************************************
                     /*P1*/           /*P2*/          /*P3*/          /*P4*/             /*P5*/             /*P6*/
        /*P1 */     graph[0][0]=0;  graph[0][1]=1;  graph[0][2]=0;  graph[0][3]=0;     graph[0][4]=0;     graph[0][5]=0;
        /*P2 */     graph[1][0]=0;  graph[1][1]=0;  graph[1][2]=1;  graph[1][3]=0;     graph[1][4]=0;     graph[1][5]=1;
        /*P3 */     graph[2][0]=0;  graph[2][1]=0;  graph[2][2]=0;  graph[2][3]=1;     graph[2][4]=1;     graph[2][5]=0;
        /*P4 */     graph[3][0]=0;  graph[3][1]=0;  graph[3][2]=0;  graph[3][3]=0;     graph[3][4]=1;     graph[3][5]=0;
        /*P5 */     graph[4][0]=0;  graph[4][1]=0;  graph[4][2]=0;  graph[4][3]=0;     graph[4][4]=0;     graph[4][5]=1;
        /*P6 */     graph[5][0]=1;  graph[5][1]=0;  graph[5][2]=0;  graph[5][3]=0;     graph[5][4]=0;     graph[5][5]=0;

        printIt(graph);
        loadPrereq(number_of_proc,graph);
        Process pi=procList.get(query_initiator);
        setPi(pi);
        List<Process>depList=pi.getDepProcessList();
        pi.setWait(true);
        pi.setNum(depList.size());
        for (Process p : depList) {
            pi.queryInit(query_initiator, query_initiator, p.getNodeNo());
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
