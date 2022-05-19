package domain;

/**
 * 无向有权图
 */
public class unDirectedGraphWithRight extends graph{
    private int n;//顶点数
    private int e;//边数
    private int [][] matrix;//邻接矩阵，为0代表两点之间不存在边
    private boolean [] isVisit;
    private boolean hasCycle = false;
    static int cycleECount = 0;

    public unDirectedGraphWithRight() {
    }

    //初始化邻接矩阵，和访问数组
    public unDirectedGraphWithRight(int num) {
        this.n = num;
        this.e = 0;
        matrix = new int[n+1][n+1];
        for (int i = 1;i<=n;i++){
            for (int j = 1;j<=n;j++){
                matrix[i][j] = 0;
            }
        }
        isVisit = new boolean[n+1];
        for (int i = 1;i<=n;i++){
            isVisit[i] = false;
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
        if (a>=1 && b>=1 && a<=n && b<=n && matrix[a][b] != 0 && matrix[b][a] != 0){
            return true;
        }
        return false;
    }


    public boolean hasCycle() {
        return hasCycle;
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

    //插入带权边
    public void insertEdge(int a, int b,int right) {
        if (a<1||b<1||a>n||b>n||a==b){
            return;
        }else {
            if (matrix[a][b] == 0){
                e++;
            }
            matrix [a][b] = right;
        }
    }

    @Override
    public int degree(int a) {
        int degree = 0;
        for (int i = 1;i<=n;i++){
            if (matrix[i][a] != 0 && matrix[a][i] != 0){
                degree++;
            }
        }
        return degree;
    }
    public int getRight(int a,int b){
        return matrix[a][b];
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

    public void dfs(){
        for (int v = 1;v<=n;v++ ){
            cycleECount = 0;
            if (!isVisit[v]){
                search(v,v);
            }
        }
    }
    public void search(int v,int prev){
        isVisit[v] = true;
        for(int w = v ;w<=n;w++){
            if(isEdgeExist(w,v) && !isVisit[w] && getRight(w,v) < 0){
                cycleECount++;
                search(w,v);
            }else if (isEdgeExist(w,v) && getRight(w,v) > 0){
                return;
            } else if (isEdgeExist(w,v) && w!=prev && isVisit[w]){
                cycleECount++;
//                System.out.println(cycleVCount);
                if (cycleECount % 2 == 1) {
                    hasCycle = true;
                }
            }
        }
    }


}
