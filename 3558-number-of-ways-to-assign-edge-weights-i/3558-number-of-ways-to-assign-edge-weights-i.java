class Solution {
    private static final int MOD = 1_000_000_007;

    public int assignEdgeWeights(int[][] edges) {
        int n = edges.length + 1;
        
      
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }
        
      
        int maxDepth = findMaxDepth(1, 0, graph);
        
        
        return (int) power(2, maxDepth - 1);
    }

    private int findMaxDepth(int curr, int parent, List<Integer>[] graph) {
        int depth = 0;
        for (int neighbor : graph[curr]) {
            if (neighbor != parent) {
                depth = Math.max(depth, 1 + findMaxDepth(neighbor, curr, graph));
            }
        }
        return depth;
    }

    
    private long power(long base, int exp) {
        long result = 1;
        base = base % MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % MOD;
            }
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return result;
    }
}