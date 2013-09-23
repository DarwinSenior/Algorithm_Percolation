
public class Percolation {
//	public Percolation(int N)              // create N-by-N grid, with all sites blocked
//	public void open(int i, int j)         // open site (row i, column j) if it is not already
//	public boolean isOpen(int i, int j)    // is site (row i, column j) open?
//	public boolean isFull(int i, int j)    // is site (row i, column j) full?
//	public boolean percolates()            // does the system percolate?
	private WeightedQuickUnionUF unionFindUpper,unionFindLower;
	private boolean[] status;
	private final int N;
	private final int start,end;
	private boolean percolate;
	public Percolation(int N) {
		this.N=N;
		unionFindUpper=new WeightedQuickUnionUF((N+2)*(N+2)+1);
		unionFindLower=new WeightedQuickUnionUF((N+2)*(N+2)+1);
		status=new boolean[(N+2)*(N+2)+1];
		start=0;
		end=(N+1)*(N+1);
		percolate=false;
//		for (int i = 1; i <= N; i++) {
//			unionFind.union(start, i);
//			status[i]=true;
//		}
	}
	private int getIndex(int i, int j) {
		if (i<=0 || i>N) throw new IndexOutOfBoundsException();
		if (j<=0 || j>N) throw new IndexOutOfBoundsException();

		return i*N+j;
	}
	public boolean isOpen(int i, int j){
		if (i<=0 || i>N) throw new IndexOutOfBoundsException();
		if (j<=0 || j>N) throw new IndexOutOfBoundsException(); 
		return status[getIndex(i, j)];
	}
	public void open(int i, int j){
		if (i<=0 || i>N) throw new IndexOutOfBoundsException();
		if (j<=0 || j>N) throw new IndexOutOfBoundsException(); 
		final int[][] direction={{-1,0},{1,0},{0,1},{0,-1}};
		int centre=getIndex(i, j);
		for (int k = 0; k < direction.length; k++) {
			if (i+direction[k][0]==0 || i+direction[k][0]==N+1 ||
					j+direction[k][1]==0 || j+direction[k][1]==N+1) {
				continue;
			}
			int around=getIndex(i+direction[k][0], j+direction[k][1]);
			if (status[around]) {
				unionFindUpper.union(centre, around);
				unionFindLower.union(centre, around);
			}
		}
		status[centre]=true;
		if (i==1) {
			unionFindUpper.union(start, centre);
		}
		if (i==N) {
			unionFindLower.union(end, centre);
		}
		if (unionFindLower.connected(end, centre)&&unionFindUpper.connected(start, centre)) {
			percolate=true;
		}
	}
	public boolean isFull(int i, int j){
		if (i<=0 || i>N) throw new IndexOutOfBoundsException();
		if (j<=0 || j>N) throw new IndexOutOfBoundsException(); 

		return unionFindUpper.connected(start, getIndex(i, j));
	}
	public boolean percolates(){
		return percolate;
	}
}
