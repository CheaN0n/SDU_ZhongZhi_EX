package domain;

import java.util.ArrayList;
import java.util.Comparator;

public class Votes {
    private int m;//投票者
    private int n;//候选项目
    private int [][] matrix;//投票者-候选项目矩阵

    public Votes() {
    }
    public Votes(int m, int n) {
        this.m = m;
        this.n = n;
        this.matrix = new int[m+1][n+1];
        //初始化
        for (int i = 1;i<=m;i++){
            for (int j = 1;j<=n;j++){
                matrix[i][j] = 0;
            }
        }
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public void inputMatrx(String [][]personalVotes){
        for (int i = 0;i<m;i++){
            for (int j = 0;j<n;j++){
                this.matrix[i+1][j+1] = Integer.parseInt(personalVotes[i][j]);
            }
        }
    }

    /**
     * 在投票者m中i的优先级，
     * @param i 候选项目
     * @param m 投票者
     * @return 返回i的下标,寻找失败则返回-1
     */
    public int search(int i,int m){
        for (int j = 1;j<=n;j++){
            if (matrix[m][j] == i){
                return j;
            }
        }
        return -1;
    }
    /**
     * 比较第一个候选项目对第二个候选项目的受支持度，大于0即为i比j更受欢迎
     * @param i 第一个候选项目
     * @param j 第二个候选项目
     * @return
     */
    public int compare(int i,int j){
        int advantage = 0;
        for (int k = 1;k <= m; k++){
            int pos1 = search(i,k);
            int pos2 = search(j,k);
            if (pos1<pos2){
                advantage++;
            }else {
                advantage--;
            }
        }
        return advantage;
    }

    public boolean checkIsExistCondorcetParadox(){
        System.out.println("孔多塞排序有向图（初始）：");
        DirectedGraphWithRight graph = condorcet_DirectedGraph_Generator();
        graph.printMatrix();
        if (graph.isExistDirectedCycle()){
            return true;
        }else {
            return false;
        }
    }

    public DirectedGraphWithRight condorcet_DirectedGraph_Generator(){
        DirectedGraphWithRight matrix = new DirectedGraphWithRight(n);
        for (int i = 1;i <= n;i++){
            for(int j = 1;j <= n;j++){
                int advantage = compare(i,j);
                if (advantage>0){
                    matrix.insertEdge(i,j,1);
                    matrix.insertEdge(j,i,-1);
                }else {
                    matrix.insertEdge(i,j,-1);
                    matrix.insertEdge(j,i,1);
                }
            }
        }
        return matrix;
    }

    /**
     * 根据孔多塞定理，获得支持度由大到小的序列
     * @return
     */
    public int [] condorcet(){
        int order[] = matrix[1];
        for (int i = 1;i < n;i++){
            for (int j = 1;j<n-1-i;j++){
                if (compare(order[j],order[j+1])<0){
                    int temp = order[j];
                    order[j] = order[j+1];
                    order[j+1] = temp;
                }
            }
        }
        return order;
    }

    /**
     * 根据中位项定理获得排序
     * @return
     */
    public int[] medianTheoremOrder(){
        int []order = new int[n+1];

        for (int i = 1;i<=n;i++){
            ArrayList first = new ArrayList<Integer>();
            for (int j = 1;j<=m;j++){
                if (matrix[j][i] >=0){
                    first.add(matrix[j][i]);
                }
            }
            first.sort(Comparator.naturalOrder());
            int size = first.size();
            order[i] = (int) first.get((1+size)/2);
            for (int x = 1;x<=m;x++){
                for (int y = 1;y<=n;y++){
                    if (matrix[x][y] == order[i]){
                        matrix[x][y] = -1;
                    }
                }
            }
        }
        return order;
    }

    /**
     * 判断一个投票者的投票情况是否符合单峰性质
     * @param votes
     * @return
     */
    public static boolean checkIsSinglePeak(int []votes){
        int k = votes[1];
        boolean isSinglePeak = true;
        for (int i = k+1;i<votes.length-1;i++){
            for (int j = i;j<votes.length-1;j++){
                if (getIndex(votes,i)>getIndex(votes,j)){
                    isSinglePeak = false;
                }
            }
        }
        for (int i = k-1;i>=1;i--){
            for (int j = i;j>=1;j--){
                if (getIndex(votes,i)>getIndex(votes,j)){
                    isSinglePeak = false;
                }
            }
        }
        return isSinglePeak;
    }
    public void deleteInWiseVotes(){
        for (int i = 1;i<=m;i++){
            if (!checkIsSinglePeak(matrix[i])){
                for (int j = 1;j<=n;j++){
                    matrix[i][j] = -1;//设为-1 == 删除
                }
            }
        }
        int count = 0;
        boolean []valid_voters = new boolean[m+1];
        //整合删除不理智投票者后的投票情况
        for (int i = 1;i<=m;i++){
            if (matrix[i][1] >=0){
                count++;
                valid_voters[i] = true;
            }
        }
        int [][]final_matrix = new int[count+1][n+1];
        int index = 1;
        for (int i = 1;i<=m;i++){
            if (valid_voters[i]){
                final_matrix[index] = matrix[i];
                index++;
            }
        }
        this.m = count;
        this.matrix = final_matrix;
    }
    public static int getIndex(int []arr,int target){
        int index = -1;
        for (int i = 1;i<arr.length;i++){
            if (arr[i] == target){
                index = i;
                break;
            }
        }
        return index+1;
    }
    public void printMatrix(){
        for (int i = 1;i<=m;i++){
            for (int j = 1;j<=n;j++){
                System.out.print(matrix[i][j]+"\t");
                if (j==n){
                    System.out.println();
                }
            }
        }
    }

    public void statistical(){
        System.out.println("投票情况如下：");
        this.printMatrix();
        if (!this.checkIsExistCondorcetParadox()){
            System.out.println("不存在孔多塞悖论，全排序:");
            int[] order = this.condorcet();
            for (int i =1;i<=n;i++) {
                System.out.print(order[i]+" ");
            }
        }else if (this.checkIsExistCondorcetParadox()){
            System.out.println("存在孔多塞悖论");
            System.out.println("剔除不满足单峰性质的投票者后，投票情况如下");
            this.deleteInWiseVotes();
            this.printMatrix();
            System.out.println("根据中位项定理，全排序:");
            int[]order = this.medianTheoremOrder();
            for (int i =1;i<=n;i++) {
                System.out.print(order[i]+" ");
            }
        }
    }
}
