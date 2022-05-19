package domain;

abstract class graph{
    public abstract int getVerticesNumber();//获取顶点个数
    public abstract int getEdgesNumber();//获取边条数
    public abstract boolean isEdgeExist(int a,int b);//返回两个顶点之间是否存在边
    public abstract void insertEdge(int a,int b);//在两个顶点之间插入边
    public abstract int degree(int a);//无向图使用
    public abstract int[] getNextVertix(int a);
    public abstract void printMatrix();
}