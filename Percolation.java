public class Percolation {
    private int N;
    private WeightedQuickUnionUF wqu;
    private int[][] grid;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        N = n;
        grid = new int[N][N];
        wqu = new WeightedQuickUnionUF((n*n)+2);
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++){
                grid[i][j] = 0;
            }

        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if(row > N || col > N || row < 1 || col < 1){
            throw new IllegalArgumentException("Argument outside index range");
        }
        int x = row - 1;
        int y = col - 1;
        grid[x][y] = 1;
        if(x-1 >=0 && isOpen(x-1, y)){
            wqu.union(to1D(x,y), to1D(x-1, y));
        }
        if(x+1 < N && isOpen(x+1, y)){
            wqu.union(to1D(x,y), to1D(x+1, y));
        }
        if(y-1 >= 0 && isOpen(x, y-1)){
            wqu.union(to1D(x, y), to1D(x, y-1));
        }
        if(y+1 < N && isOpen(x, y+1)){
            wqu.union(to1D(x,y), to1D(x, y+1));
        }
        if(x==0){
            wqu.union(0, to1D(x,y));
        }
        if(y==N-1){
            wqu.union(to1D(x,y), N*N+1);
        }
    }

    private int to1D(int row, int col)
    {
        if(row > N || col > N || row < 1 || col < 1){
            throw new IllegalArgumentException("Argument outside index range");
        }
        return (row-1) * N + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        return grid[row-1][col-1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        return grid[row-1][col-1] == 0;
    }

    // returns the number of open sites
    public int numberOfOpenSites()
    {
        int count = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j< N; j++){
                count += grid[i][j];
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates()
    {
        return wqu.connected(0, N*N+1);
    }

    public static void main(String[] args)
    {

    }
}
