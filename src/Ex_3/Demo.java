package Ex_3;
import domain.unDirectedGraphWithRight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
输入：任意极化关系下图的邻接矩阵（注意边有正负）
输出：是否含有负向边圈

所谓“极化关系”，指的是网络中的关系分为“友好”和“敌对”两种，这在人际关系和国际关系的一些特定时期都是显著的。在这样的模型中，结构的稳定性是关注的重点，即一个网络结构中的各个关系性质是趋向于不变，还是趋向于改变（从友好变为敌对，或者反过来）？落实到计算问题上，就是要检测图中是否存在包含奇数个敌对边（负向边）的圈

 */
public class Demo {

    public static unDirectedGraphWithRight readDataFromFile(){
        System.out.println("请输入存储邻接矩阵的文件名称(文件默认存储在input目录下)");
        Scanner scanner = new Scanner(System.in);
        String FileName = scanner.nextLine();
        unDirectedGraphWithRight graph = null;
        int n = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/EX_3/input/"+FileName));
            String str;
            str = in.readLine();
            String[] nums_str = str.split(" ");
            n = nums_str.length;
            int []nums = new int[n];
            graph = new unDirectedGraphWithRight(n);
            for (int i = 0;i < n;i++){
                nums[i] = Integer.parseInt(nums_str[i]);
//                System.out.print(nums[i]);
                if (nums[i] != 0){
                    graph.insertEdge(1,i+1,nums[i]);
                }
            }
            for (int i = 1;i<=n-1;i++){
                str = in.readLine();
                nums_str = str.split(" ");
                for (int j = 0;j < n;j++){
                    nums[j] = Integer.parseInt(nums_str[j]);
//                    System.out.print(nums[j]);
                    if (nums[j] != 0){
                        graph.insertEdge(i+1,j+1,nums[j]);
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
        unDirectedGraphWithRight graph = readDataFromFile();
        graph.printMatrix();
//        unDirectedGraphWithRight graph = new unDirectedGraphWithRight(5);
//        graph.insertEdge(1,2,2);
//        graph.insertEdge(2,1,2);
//        graph.insertEdge(2,3,-5);
//        graph.insertEdge(3,2,-5);
//        graph.insertEdge(2,5,-5);
//        graph.insertEdge(5,2,-5);
//        graph.insertEdge(3,4,-1);
//        graph.insertEdge(4,3,-1);
//        graph.insertEdge(4,5,-1);
//        graph.insertEdge(5,4,-1);
        graph.dfs();
        if (graph.hasCycle()){
            System.out.println("存在包含奇数个敌对边（负向边）的圈");
        }else{
            System.out.println("不存在包含奇数个敌对边（负向边）的圈");
        }

//        ==================
//        graph.insertEdge(1,2,2);
//        graph.insertEdge(2,1,2);
//        graph.insertEdge(2,3,-5);
//        graph.insertEdge(3,2,-5);
//        graph.insertEdge(2,4,-5);
//        graph.insertEdge(4,2,-5);
//        graph.insertEdge(3,4,1);
//        graph.insertEdge(4,3,1);


    }

}
