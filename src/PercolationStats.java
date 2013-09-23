import java.util.Random;


public class PercolationStats {
//	public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
//	public double mean()                     // sample mean of percolation threshold
//	public double stddev()                   // sample standard deviation of percolation threshold
//	public double confidenceLo()             // returns lower bound of the 95% confidence interval
//	public double confidenceHi()             // returns upper bound of the 95% confidence interval
//	public static void main(String[] args)   // test client, described below
	private double[] thresholds;
	private final int N,T;
	public PercolationStats(int N, int T){
		if (N<=0) throw new IllegalArgumentException("N");
		if (T<=0) throw new IllegalArgumentException("T");
		this.N=N;
		this.T=T;
		thresholds=new double[T];
		for (int i = 0; i < T; i++) {
			thresholds[i]=getThreshold(N);
		}
		
	}
	private double getThreshold(int N){
		Percolation percolation=new Percolation(N);
		int count=0;
		Random random=new Random();
		while (!percolation.percolates()) {
			int i=random.nextInt(N)+1;
			int j=random.nextInt(N)+1;
			if (!percolation.isOpen(i, j)) {
				percolation.open(i, j);
				count++;
			}
		}
		return (double)count/(double)(N*N);
	}
	public double mean() {
		return StdStats.mean(thresholds);
	}
	public double stddev(){
		return StdStats.stddev(thresholds);
	}
	public double confidenceLo(){
		return mean()-1.96*stddev()/Math.sqrt(T);
	}
	public double confidenceHi() {
		return mean()+1.96*stddev()/Math.sqrt(T);
	}
	public static void main(String[] args) {
		In in=new In();
		int N=in.readInt();
		int T=in.readInt();
		PercolationStats stats=new PercolationStats(N,T);
		System.out.println("mean                    = "+stats.mean());
		System.out.println("stddev                  = "+stats.stddev());
		System.out.println("95% confidence interval = "+stats.confidenceLo()+" ,"+stats.confidenceHi());
	}
}
