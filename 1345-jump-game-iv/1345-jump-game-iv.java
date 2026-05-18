class Solution {
    public int minJumps(int[] arr) {
        int n = arr.length;
        if (n == 1) return 0;

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++)
            graph.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);

        boolean[] visited = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        visited[0] = true;
        int steps = 0;

        while (!q.isEmpty()) {
            steps++;
            int size = q.size();
            while (size-- > 0) {
                int i = q.poll();
                List<Integer> neighbors = new ArrayList<>();
                if (i - 1 >= 0) neighbors.add(i - 1);
                if (i + 1 < n)  neighbors.add(i + 1);
                List<Integer> same = graph.remove(arr[i]);
                if (same != null) neighbors.addAll(same);
                for (int j : neighbors) {
                    if (j == n - 1) return steps;
                    if (!visited[j]) {
                        visited[j] = true;
                        q.offer(j);
                    }
                }
            }
        }
        return -1;
    }
}