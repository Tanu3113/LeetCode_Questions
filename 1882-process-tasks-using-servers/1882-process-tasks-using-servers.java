class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        PriorityQueue<int[]> available = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0]) {
                    return Integer.compare(a[0], b[0]);
                }
                return Integer.compare(a[1], b[1]);
            }
        );

        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        int n = servers.length;
        int m = tasks.length;

        int[] ans = new int[m];

        for (int i = 0; i < n; i++) {
            available.offer(new int[]{servers[i], i});
        }

        long currentTime = 0;

        for (int j = 0; j < m; j++) {

            currentTime = Math.max(currentTime, j);

            while (!busy.isEmpty() && busy.peek()[0] <= currentTime) {

                long[] s = busy.poll();

                available.offer(new int[]{
                    (int) s[1],
                    (int) s[2]
                });
            }

            if (available.isEmpty()) {

                currentTime = busy.peek()[0];

                while (!busy.isEmpty() &&
                       busy.peek()[0] <= currentTime) {

                    long[] s = busy.poll();

                    available.offer(new int[]{
                        (int) s[1],
                        (int) s[2]
                    });
                }
            }

            int[] server = available.poll();

            int weight = server[0];
            int index = server[1];

            ans[j] = index;

            busy.offer(new long[]{
                currentTime + tasks[j],
                weight,
                index
            });
        }

        return ans;
    }
}