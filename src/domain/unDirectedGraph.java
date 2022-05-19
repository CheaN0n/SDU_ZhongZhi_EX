package domain;

import java.text.NumberFormat;


/**
 * 无向无权图，用于实验1，2
 */
public class unDirectedGraph extends graph{
    private int n;//顶点数
    private int e;//边数
    private int [][] matrix;//邻接矩阵，为0代表两点之间不存在边

    public unDirectedGraph() {
    }

    public unDirectedGraph(int num) {
        this.n = num;
        this.e = 0;
        matrix = new int[n+1][n+1];
        for (int i = 1;i<=n;i++){
            for (int j = 1;j<=n;j++){
                matrix[i][j] = 0;
            }
        }
    }

    @Override
    public int getVerticesNumber() {
        return n;
    }

    @Override
    public int getEdgesNumber() {
        return e;
    }

    @Override
    public boolean isEdgeExist(int a, int b) {
        if (a>=1 && b>=1 && a<=n && b<=n && matrix[a][b] == 1 && matrix[b][a] == 1){
            return true;
        }
        return false;
    }

    @Override
    public void insertEdge(int a, int b) {
        if (a<1||b<1||a>n||b>n||a==b){
            return;
        }else {
            if (matrix[a][b] == 0){
                e++;
            }
            matrix [a][b] = 1;
        }

    }
    public int getFriendsIsFriendsNumber(int a) {
        int []friends = getNextVertix(a);
        int friendsIsFriends = 0;
        for (int i = 0; i < degree(a); i++) {
            for (int j = 0; j < degree(a) - 1 - i; j++) {
                int x = friends[i];
                int y = friends[i + 1 + j];
                if (isEdgeExist(x,y)) {
                    friendsIsFriends++;
                }
            }
        }
        return friendsIsFriends;
    }


    @Override
    public int degree(int a) {
        int degree = 0;
        for (int i = 1;i<=n;i++){
            if (matrix[i][a] == 1 && matrix[a][i] == 1){
                degree++;
            }
        }
        return degree;
    }


    @Override
    public int[] getNextVertix(int a) {
        int[] nextVertices = new int[n+1];
        int count = 0;
        for (int j = 1; j <= n; j++) {
            if (isEdgeExist(a,j) && a!=j) {
                nextVertices[count++] = j;
            }
        }
        return nextVertices;
    }

    @Override
    public void printMatrix() {
        for (int i = 1;i<=n;i++){
            for (int j = 1;j<=n;j++){
                System.out.print(matrix[i][j]+"\t");
                if (j==n){
                    System.out.println();
                }
            }
        }
    }

    public void printJuJiXiShu(){
        String[] JJXS = new String[n+1];
        for (int i = 1;i<=n;i++){
            int degree = degree(i);
            int denominator = degree*(degree-1)/2;
            if (denominator == 0){
                JJXS[i] = "0";
            }else {
                int molecule = getFriendsIsFriendsNumber(i);
                if (molecule == 0){
                    JJXS[i] = "0";
                }else {
                    if (molecule == denominator){
                        JJXS[i] = "1";
                    }else {
                        JJXS[i] = String.valueOf(molecule)+"/"+String.valueOf(denominator);
                    }
                }
            }
        }
        for (int i = 1;i<=n;i++){
            System.out.println("顶点"+i+"的聚集系数为:"+JJXS[i]);
        }
    }
    public void printLinLiChongDieDu() {
        String[] LLCDD = new String[e];
        int count = 0;
        for (int i = 1; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (isEdgeExist(i, j) ) {
                    //获得与A，B邻接的顶点
                    int[] getAEdges = getNextVertix(i);
                    int[] getBEdges = getNextVertix(j);

                    int legA = degree(i) ;
                    int legB = degree(j) ;

                    if (legA == 1 && legB == 1) {
                        LLCDD[count++] = "0";
                    } else {
                        int molecule = getMole(getAEdges, getBEdges,legA,legB);
                        int denominator = getDenosub1(getAEdges, getBEdges,legA,legB,i,j)+molecule;


                        if (denominator == molecule) {
                            LLCDD[count++] = "1";
                        } else if (molecule == 0) {
                            LLCDD[count++] = "0";
                        } else {
                            LLCDD[count++] = String.valueOf(molecule) + "/" + String.valueOf(denominator);
                        }
                    }
                    System.out.println("边(" + i + "," + j + ")之间的邻里重叠度为" + LLCDD[count - 1]);
                }
            }
        }
    }


    private int getMole(int[] getAEdges, int[] getBEdges,int legA,int legB) {
        int mole = 0;
        for (int i = 0;i<legA;i++){
            for (int j = 0;j<legB;j++){
                if (getAEdges[i] == getBEdges[j]){
                    mole++;
                    break;
                }
            }
        }
        return mole;
    }

    private int getDenosub1(int[] getAEdges, int[] getBEdges,int legA,int legB,int A,int B) {
        int deno = 0;
        for (int i = 0;i<legA;i++){
            for (int j = 0;j<legB;j++){
                if (getAEdges[i] != getBEdges[j] && getAEdges[i] != B && getBEdges[j] != A){
                    deno++;
                }
            }
        }
        return deno;

    }

    public double getAvgCountFriendofFriend(int a){
        double sum = 0;
        int []friends = getNextVertix(a);
        int len = degree(a);
        for (int i = 0;i < len;i++){
            sum+=degree(friends[i]);
        }
        return sum/len;
    }

    public void printFriendshipParadoxRate(){
        double count = 0;
        for (int i = 1;i<=n;i++){
            if (degree(i)<getAvgCountFriendofFriend(i)){
                count++;
            }
        }
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMaximumIntegerDigits(2); // 设置数的整数部分所允许的最大位数
        nf.setMaximumFractionDigits(5);// 设置数的小数部分所允许的最大位数
        System.out.println("符合友谊悖论的结点占比为:"+nf.format(count/(double) n));
    }
}