class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null) {
            return result;
        }

        Set<String> visited = new HashSet<>();
        java.util.Queue<String> queue = new java.util.LinkedList<>();

        queue.offer(s);
        visited.add(s);
        boolean found = false;

        while (!queue.isEmpty()) {
            String curr = queue.poll();

            if (isValid(curr)) {
                result.add(curr);
                found = true;
            }

            if (found) {
                continue;
            }

            for (int i = 0; i < curr.length(); i++) {
                char c = curr.charAt(i);
                if (c != '(' && c != ')') {
                    continue;
                }

                String next = curr.substring(0, i) + curr.substring(i + 1);
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }

        return result;
    }

    private boolean isValid(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            }
        }
        return count == 0;
    }
}