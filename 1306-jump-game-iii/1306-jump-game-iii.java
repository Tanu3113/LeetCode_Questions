class Solution {
    public boolean canReach(int[] arr, int start) {
        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[arr.length];
        q.offer(start);
        visited[start] = true;
        while (!q.isEmpty()) {
            int i = q.poll();
            if (arr[i] == 0) return true;
            int left = i - arr[i];
            int right = i + arr[i];
            if (left >= 0 && !visited[left]) { visited[left] = true; q.offer(left); }
            if (right < arr.length && !visited[right]) { visited[right] = true; q.offer(right); }
        }
        return false;
    }
}