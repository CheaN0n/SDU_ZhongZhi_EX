package Ex_2;

import domain.unDirectedGraph;

import java.util.Random;
import java.util.Scanner;

/*
2	友谊悖论验证	输入：任意图的邻接矩阵（考察随机图和社会网络两种图，模拟生成）
输出：
1）符合友谊悖论的节点占比

相关定义：
友谊悖论：是一种社会现象, 指大多数人认为, 自己的朋友比自己拥有更多的朋友

 */
public class Demo {
    public static unDirectedGraph genRandomGraph(){
        System.out.println("请输入n*n的邻接矩阵中n的值，程序将根据n生成随机图");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        unDirectedGraph graph = new unDirectedGraph(n);
//        graph.printMatrix();
        long t = System.currentTimeMillis();
        Random r = new Random(t);
        for (int i = 1;i<=n;i++){
            for (int j = 1;j<=n;j++){
                if (j<=i){//保证生成无向图对应的对称矩阵
                    int flag = r.nextInt(2);
                    if (flag == 1){
                        graph.insertEdge(i,j);
                        graph.insertEdge(j,i);
                    }
                }
            }
        }
        return graph;
    }

    public static void main(String[] args) {
        unDirectedGraph graph = genRandomGraph();
//        graph.printMatrix();
        graph.printFriendshipParadoxRate();
    }
}
