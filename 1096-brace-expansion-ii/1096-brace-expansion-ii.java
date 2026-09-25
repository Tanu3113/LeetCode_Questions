class Solution {
    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression);
        List<String> sortedList = new ArrayList<>(result);
        Collections.sort(sortedList);
        return sortedList;
    }

    private Set<String> parse(String expr) {
        int index = expr.indexOf('{');
        if (index == -1) {
            Set<String> set = new HashSet<>();
            set.add(expr);
            return set;
        }

        int start = index;
        int balance = 0;
        int end = -1;
        for (int i = start; i < expr.length(); i++) {
            if (expr.charAt(i) == '{') balance++;
            else if (expr.charAt(i) == '}') balance--;
            
            if (balance == 0) {
                end = i;
                break;
            }
        }

        String prefix = expr.substring(0, start);
        String middle = expr.substring(start + 1, end);
        String suffix = expr.substring(end + 1);

        List<Set<String>> groups = new ArrayList<>();
        int b = 0;
        int prev = 0;
        for (int i = 0; i < middle.length(); i++) {
            char c = middle.charAt(i);
            if (c == '{') b++;
            else if (c == '}') b--;
            else if (c == ',' && b == 0) {
                groups.add(parse(middle.substring(prev, i)));
                prev = i + 1;
            }
        }
        groups.add(parse(middle.substring(prev)));

        Set<String> middleSet = new HashSet<>();
        for (Set<String> group : groups) {
            middleSet.addAll(group);
        }

        Set<String> prefixSet = prefix.isEmpty() ? new HashSet<>(Collections.singleton("")) : parse(prefix);
        Set<String> suffixSet = suffix.isEmpty() ? new HashSet<>(Collections.singleton("")) : parse(suffix);

        Set<String> combined = new HashSet<>();
        for (String p : prefixSet) {
            for (String m : middleSet) {
                for (String s : suffixSet) {
                    combined.add(p + m + s);
                }
            }
        }

        return combined;
    }
}