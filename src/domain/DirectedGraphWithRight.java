package domain;

public class DirectedGraphWithRight extends graph{

    private int n;//顶点数
    private int e;//边数
    private int [][] matrix;//邻接矩阵，为0代表两点之间不存在边
    boolean deletedV[];

    public DirectedGraphWithRight() {
    }

    public DirectedGraphWithRight(int num) {
        this.n = num;
        this.e = 0;
        matrix = new int[n+1][n+1];
        deletedV = new boolean[n+1];
        for (int i = 1;i<=n;i++){
            deletedV[i] = false;
        }
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
        if (a>=1 && b>=1 && a<=n && b<=n && matrix[a][b] != 0 ){
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

    public int indegree(int a){
        int indegree = 0;
        for (int i = 1;i<=n;i++){
            if (matrix[i][a] != 0 ){
                indegree++;
            }
        }
        return indegree;
    }

    public int outdegree(int a){
        int outdegree = 0;
        for (int i = 1;i<=n;i++){
            if (matrix[a][i] != 0){
                outdegree++;
            }
        }
        return outdegree;
    }

    @Override
    public int degree(int a) {
        return indegree(a)+outdegree(a);
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
    public int getZeroIndegreeV(){
        for (int i = 1;i<=n;i++){
            if (indegree(i) == 0 && !deletedV[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     * 将i顶点相关的初度和入度均设置为0
     * @param i
     */
    public void deleteV(int i){
        deletedV[i] = true;
        for (int j = 1;j<=n;j++){
            matrix[i][j] = 0;
            matrix[j][i] = 0;
        }
    }

    public boolean isExistDirectedCycle(){
        while (true){
            int tobedelete = getZeroIndegreeV();
            if (tobedelete!=-1){
                deleteV(tobedelete);
            }else{
                break;
            }
        }
        for (int i = 1;i<=n;i++){
            if (!deletedV[i]){
                return false;
            }
        }
        return true;
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
}
