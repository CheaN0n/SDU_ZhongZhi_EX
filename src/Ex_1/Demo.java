package Ex_1;

import domain.unDirectedGraph;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
计算聚集系数和邻里重叠度
输入：任意的有向图
输出：
1）每个节点的聚集系数
2）每个节点对的邻里重叠度

相关定义：
聚集系数：节点A的聚集系数 = A的任意两个朋友之间也是朋友的概率（即邻居间朋友对的个数除以总对数）
邻里重叠度：与A、B均为邻居的节点数/ 与节点A、B中至少一个为邻居的节点数

 */
public class Demo {
    public static unDirectedGraph inputData(){
        Scanner scanner = new Scanner(System.in);
        int n;
        System.out.println("请输入n*n的邻接矩阵中n的值");
        n = scanner.nextInt();
        unDirectedGraph graph = new unDirectedGraph(n);
        System.out.println("请输入邻接矩阵");
        for (int i = 1;i<=n;i++){
            for (int j = 1;j<=n;j++){
                int flag = scanner.nextInt();
                if (flag == 1){
                    graph.insertEdge(i,j);
                }
            }
        }
        return graph;
    }
    public static unDirectedGraph readDataFromFile(){
        System.out.println("请输入存储邻接矩阵的文件名称(文件默认存储在input目录下)");
        Scanner scanner = new Scanner(System.in);
        String FileName = scanner.nextLine();
        unDirectedGraph graph = null;
        int n = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/EX_1/input/"+FileName));
            String str;
            str = in.readLine();
            String[] nums_str = str.split(" ");
            n = nums_str.length;
            int []nums = new int[n];
            graph = new unDirectedGraph(n);
            for (int i = 0;i < n;i++){
                nums[i] = Integer.parseInt(nums_str[i]);
//                System.out.print(nums[i]);
                if (nums[i] == 1){
                    graph.insertEdge(1,i+1);
                }
            }
            for (int i = 1;i<=n-1;i++){
                str = in.readLine();
                nums_str = str.split(" ");
                for (int j = 0;j < n;j++){
                    nums[j] = Integer.parseInt(nums_str[j]);
//                    System.out.print(nums[j]);
                    if (nums[j] == 1){
                        graph.insertEdge(i+1,j+1);
                    }
                }
            }
            System.out.println("输入的矩阵为:");
            graph.printMatrix();
            System.out.println("===================");
        }catch (IOException e){
            System.err.println("文件读取失败");
        }
        return graph;
    }
    public static void main(String[] args) {
        unDirectedGraph graph = readDataFromFile();
//        unDirectedGraph graph = inputData();
//        graph.printMatrix();
        graph.printJuJiXiShu();
        graph.printLinLiChongDieDu();
    }

}
