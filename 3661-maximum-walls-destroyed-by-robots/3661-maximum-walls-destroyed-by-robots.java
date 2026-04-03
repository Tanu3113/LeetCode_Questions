import java.util.*;

class Solution {
    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;

        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> robots[a] - robots[b]);

        int[] R = new int[n], D = new int[n];
        for (int i = 0; i < n; i++) { R[i] = robots[idx[i]]; D[i] = distance[idx[i]]; }

        int[] W = walls.clone();
        Arrays.sort(W);

        // Partition positions into territories:
        // territory[i] = [tlo[i], thi[i]] — wall positions exclusively reachable only by robot i
        // (midpoints between adjacent robots define boundaries)
        // tlo[0] = -inf, thi[n-1] = +inf
        // boundary between i and i+1 = R[i] (robot i owns its own position and left half,
        //                                     robot i+1 owns right half)
        // Specifically: tlo[i] = R[i-1]+1 (or 0), thi[i] = R[i+1]-1 (or INF)
        // But bullet ranges further restrict: robot can only hit [R[i]-D[i], R[i]+D[i]]
        // AND blocked by adjacent robots.
        //
        // The real insight: split the NUMBER LINE at each robot position.
        // Walls left of midpoint(R[i-1],R[i]) are "left territory" — only reachable by robots ≤ i.
        // Since bullets are blocked by the nearest robot, each wall is reachable by AT MOST 2
        // adjacent robots (the one to its left and the one to its right).
        //
        // So we model it as: for each wall w in W, which robots can reach it?
        // Only robots directly adjacent to w (one on each side).
        // -> This means we need to decide, for each "gap" between adjacent robots,
        //    which robot covers which walls in that gap.
        //
        // For gap between R[i] and R[i+1]:
        //   - Robot i  can cover walls in [R[i], min(R[i]+D[i], R[i+1]-1)] firing RIGHT
        //   - Robot i+1 can cover walls in [max(R[i+1]-D[i+1], R[i]+1), R[i+1]] firing LEFT
        //   - These ranges may overlap (walls coverable by BOTH)
        //   - But each robot fires ONE bullet total (left OR right for ALL walls it hits)
        //
        // This is the crux: robot i's single bullet covers ALL walls in its chosen direction.
        // So the choice is: fire left (cover a whole range) or fire right (cover a whole range).
        //
        // Model as interval DP where dp[i][dir] = max unique walls for robots 0..i
        // where robot i chose direction dir (0=left,1=right).
        //
        // Transition requires knowing how many NEW walls robot i adds given robot i-1's choice.
        // "New" = not already counted by robot i-1.
        //
        // Robot i fires LEFT: covers [lb_i, R[i]] where lb_i = max(R[i]-D[i], R[i-1]+1)
        //   If robot i-1 fired RIGHT: robot i-1 covered [R[i-1], rb_{i-1}]
        //                             where rb_{i-1} = min(R[i-1]+D[i-1], R[i]-1)
        //   Overlap = walls in [lb_i, rb_{i-1}]  (robot i-1's right ∩ robot i's left)
        //           = walls in [lb_i, min(rb_{i-1}, R[i])]
        //   If robot i-1 fired LEFT: no overlap with robot i's left (robot i-1 went left,
        //                            robot i also goes left but leftbound is R[i-1]+1,
        //                            so robot i's left starts AFTER robot i-1)
        //
        // Robot i fires RIGHT: covers [R[i], rb_i]
        //   If robot i-1 fired RIGHT: robot i-1 went [R[i-1], R[i]-1], robot i goes [R[i], rb_i]
        //                             No overlap (they don't share any position).
        //   If robot i-1 fired LEFT: no overlap.
        //
        // So overlap ONLY occurs: robot i-1 RIGHT + robot i LEFT.

        // Precompute ranges
        int[] lLo = new int[n], lHi = new int[n];
        int[] rLo = new int[n], rHi = new int[n];

        for (int i = 0; i < n; i++) {
            int lb = R[i] - D[i];
            if (i > 0) lb = Math.max(lb, R[i-1] + 1);
            lLo[i] = lowerBound(W, lb);
            lHi[i] = upperBound(W, R[i]);

            int rb = R[i] + D[i];
            if (i < n-1) rb = Math.min(rb, R[i+1] - 1);
            rLo[i] = lowerBound(W, R[i]);
            rHi[i] = upperBound(W, rb);
        }

        // Overlap[i] = walls in (robot i-1's right range) ∩ (robot i's left range)
        // robot i-1 right: [R[i-1], min(R[i-1]+D[i-1], R[i]-1)]
        // robot i   left:  [max(R[i]-D[i], R[i-1]+1), R[i]]
        // intersection: [max(R[i]-D[i], R[i-1]+1), min(R[i-1]+D[i-1], R[i]-1, R[i])]
        //             = [lLo[i] as position, min(rHi[i-1] upper bound position, R[i])]
        // In index space: [max(rLo[i-1], lLo[i]), min(rHi[i-1], lHi[i])]
        int[] ov = new int[n];
        for (int i = 1; i < n; i++) {
            int lo = Math.max(rLo[i-1], lLo[i]);
            int hi = Math.min(rHi[i-1], lHi[i]);
            ov[i] = Math.max(0, hi - lo);
        }

        int[] dp = new int[]{
            lHi[0] - lLo[0],
            rHi[0] - rLo[0]
        };

        for (int i = 1; i < n; i++) {
            int lC = lHi[i] - lLo[i];
            int rC = rHi[i] - rLo[i];
            int[] ndp = new int[2];

            // curr fires LEFT
            ndp[0] = Math.max(
                dp[0] + lC,           // prev=left,  no overlap
                dp[1] + lC - ov[i]    // prev=right, subtract overlap
            );

            // curr fires RIGHT (never overlaps with prev regardless of prev's choice)
            ndp[1] = Math.max(
                dp[0] + rC,
                dp[1] + rC
            );
            ndp[1] = dp[0] > dp[1] ? dp[0] + rC : dp[1] + rC; // same as max(dp)*+rC
            ndp[1] = Math.max(dp[0], dp[1]) + rC;

            dp = ndp;
        }

        return Math.max(dp[0], dp[1]);
    }

    private int lowerBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] < target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }

    private int upperBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (arr[mid] <= target) lo = mid + 1;
            else hi = mid;
        }
        return lo;
    }
}