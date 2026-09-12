class Solution {
    private record Result(long weight, List<Integer> indices) {}
    private record Interval(long left, long right, long weight, int originalIndex) {}

    public int[] maximumWeight(List<List<Integer>> input) {
        int n = input.size();
        Interval[] intervals = new Interval[n];
        for (int i = 0; i < n; ++i) {
            List<Integer> item = input.get(i);
            intervals[i] = new Interval(item.get(0), item.get(1), item.get(2), i);
        }

        Arrays.sort(intervals, Comparator.comparingLong(Interval::right));

        Result[][] dp = new Result[n][5];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= 4; j++) {
                dp[i][j] = new Result(0, new ArrayList<>());
            }
        }

        for (int i = 0; i < n; i++) {
            int low = 0, high = i - 1, idx = -1;
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (intervals[mid].right() < intervals[i].left()) {
                    idx = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            for (int j = 1; j <= 4; j++) {
                Result skip = (i > 0) ? dp[i - 1][j] : new Result(0, new ArrayList<>());

                Result take;
                if (j == 1) {
                    List<Integer> single = new ArrayList<>();
                    single.add(intervals[i].originalIndex());
                    take = new Result(intervals[i].weight(), single);
                } else {
                    if (idx != -1) {
                        Result prev = dp[idx][j - 1];
                        if (prev.weight() > 0 || j == 1) {
                            List<Integer> combined = new ArrayList<>(prev.indices());
                            combined.add(intervals[i].originalIndex());
                            Collections.sort(combined);
                            take = new Result(intervals[i].weight() + prev.weight(), combined);
                        } else {
                            take = new Result(-1, new ArrayList<>());
                        }
                    } else {
                        take = new Result(-1, new ArrayList<>());
                    }
                }

                dp[i][j] = chooseBest(skip, take);
            }
        }

        Result best = new Result(-1, new ArrayList<>());
        for (int j = 1; j <= 4; j++) {
            best = chooseBest(best, dp[n - 1][j]);
        }

        int[] ans = new int[best.indices().size()];
        for (int i = 0; i < best.indices().size(); i++) {
            ans[i] = best.indices().get(i);
        }
        return ans;
    }

    private Result chooseBest(Result a, Result b) {
        if (a.weight() > b.weight()) return a;
        if (b.weight() > a.weight()) return b;
        
        if (a.indices().isEmpty()) return b;
        if (b.indices().isEmpty()) return a;

        int minLen = Math.min(a.indices().size(), b.indices().size());
        for (int i = 0; i < minLen; i++) {
            int cmp = Integer.compare(a.indices().get(i), b.indices().get(i));
            if (cmp != 0) {
                return cmp < 0 ? a : b;
            }
        }
        return a.indices().size() < b.indices().size() ? a : b;
    }
}