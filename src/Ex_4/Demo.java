package Ex_4;
import domain.unDirectedGraph;//使用不带权无向图即可

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Demo {
    static Scanner scanner = new Scanner(System.in);
    public static unDirectedGraph readDataFromFile(){
        System.out.println("请输入存储邻接矩阵的文件名称(文件默认存储在input目录下)");
        String FileName = scanner.nextLine();
        unDirectedGraph graph = null;
        int n = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/Ex_4/input/"+FileName));
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

    /**
     * 根据用户输入获得初始结点集S
     * @param n 顶点数量
     * @return
     */
    public static ArrayList getOriginalVertices(int n){
        System.out.println("请输入初始节点集(1-"+n+"之间的整数，用,分开):");
        String origins = scanner.nextLine();
        String[] originalVertices_char = origins.split(",");
        int size = originalVertices_char.length;
        ArrayList originalVertices = new ArrayList();
        for (int i = 0;i<size;i++){
            originalVertices.add(Integer.parseInt(originalVertices_char[i]));
        }
        return originalVertices;
    }

    /**
     * 根据用户输入获得门槛值q
     * @return
     */
    public static double getThresholdValue(){
        System.out.println("请输入门槛值q:");
        double q = scanner.nextDouble();
        return q;
    }

    /**
     * 模拟输出扩散过程
     * @param graph 无向图
     * @param activeVertices 已经激活的结点集
     * @param q 门槛值
     */
    public static void SpreadSimulation(unDirectedGraph graph,ArrayList activeVertices ,double q){
        int count = 1;//记录模拟轮数
        int n = graph.getVerticesNumber();
        System.out.println("初始结点集为:");
        Iterator iterator = activeVertices.iterator();
        while (iterator.hasNext()){
            System.out.print(iterator.next()+" ");
        }
        System.out.println();
        System.out.println("========================");
        while(true){
            boolean hasNew = false;//记录每一轮扩散是否有新添加的结点，如果没有说明扩散结束
            boolean isJoin[] = new boolean[n+1];//记录将要激活的结点
            for (int i = 1;i<n;i++){
                isJoin[i] = false;
            }
            for (int i = 1;i<=n;i++){
                if (!activeVertices.contains(i)){
                    double activeNum = 0;
                    int []nextVertices = graph.getNextVertix(i);
                    for (int j = 0;j<graph.degree(i);j++){
                        if (activeVertices.contains(nextVertices[j])){
                            activeNum++;
                        }
                    }
                    double degree = graph.degree(i);
                    if (activeNum/degree>=q){
                        isJoin[i] = true;
                        hasNew = true;
                    }
                }
            }
            for (int i = 1;i<=n;i++){
                if (isJoin[i]){
                    activeVertices.add(i);
                }
            }
            if (!hasNew){
                break;
            }
            System.out.println("第"+count+"轮扩散后已激活的结点集为:");
            Iterator it = activeVertices.iterator();
            while (it.hasNext()){
                System.out.print(it.next()+" ");
            }
            System.out.println();
            System.out.println("========================");

            count++;
        }

    }
    public static void main(String[] args) {
        unDirectedGraph graph = readDataFromFile();
        ArrayList originalVertices = getOriginalVertices(graph.getVerticesNumber());
        double q = getThresholdValue();
        SpreadSimulation(graph,originalVertices,q);

    }
}
