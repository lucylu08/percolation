public class PercolationStats {
    private double[] results;
    private int total_trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if(n <= 0){
            throw new IllegalArgumentException("Illegal Arugument");
        }
        results = new double[trials];
        total_trials = trials;
        for(int i = 0; i < total_trials; i++)
        {
            Percolation percolate = new Percolation(n);
            while (!percolate.percolates()) {
                int random_x = StdRandom.uniform(1, n + 1);
                int random_y = StdRandom.uniform(1, n + 1);
                percolate.open(random_x, random_y);
            }
            results[i] = (double)percolate.numberOfOpenSites()/(n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean()
    {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - (1.96*stddev())/ Math.sqrt(total_trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + (1.96*stddev())/ Math.sqrt(total_trials);
    }

    public static void main(String[] args)
    {
       PercolationStats perco_stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
       StdOut.printf("mean = %f", perco_stats.mean());
       StdOut.printf("stddev = %f", perco_stats.stddev());
       StdOut.printf("95% confidence interval = [%f, %f]", perco_stats.confidenceLo(), perco_stats.confidenceHi());
    }

}
