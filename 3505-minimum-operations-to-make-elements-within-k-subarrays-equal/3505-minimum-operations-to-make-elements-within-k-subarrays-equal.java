class Solution {
   

    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    long sumLeft = 0;
    long sumRight = 0;

    int sizeLeft = 0;
    int sizeRight = 0;

    // Insert into a TreeMap
    void add(TreeMap<Integer, Integer> map, int num) {
        map.put(num, map.getOrDefault(num, 0) + 1);
    }

    // Remove one occurrence
    void remove(TreeMap<Integer, Integer> map, int num) {
        int count = map.get(num);

        if (count == 1) {
            map.remove(num);
        } else {
            map.put(num, count - 1);
        }
    }

    // Add a number to the current window
    void addNumber(int num) {

        if (left.isEmpty() || num <= left.lastKey()) {
            add(left, num);
            sumLeft += num;
            sizeLeft++;
        } else {
            add(right, num);
            sumRight += num;
            sizeRight++;
        }

        balance();
    }

    // Remove a number from the current window
    void removeNumber(int num) {

        if (left.containsKey(num)) {
            remove(left, num);
            sumLeft -= num;
            sizeLeft--;
        } else {
            remove(right, num);
            sumRight -= num;
            sizeRight--;
        }

        balance();
    }

    // Maintain equal halves (left can have one extra)
    void balance() {

        while (sizeLeft > sizeRight + 1) {

            int num = left.lastKey();

            remove(left, num);
            sumLeft -= num;
            sizeLeft--;

            add(right, num);
            sumRight += num;
            sizeRight++;
        }

        while (sizeLeft < sizeRight) {

            int num = right.firstKey();

            remove(right, num);
            sumRight -= num;
            sizeRight--;

            add(left, num);
            sumLeft += num;
            sizeLeft++;
        }
    }

    // Calculate minimum operations for current window
    long getCost() {

        long median = left.lastKey();

        long leftCost = median * sizeLeft - sumLeft;

        long rightCost = sumRight - median * sizeRight;

        return leftCost + rightCost;
    }

    void clearWindow() {

        left.clear();
        right.clear();

        sumLeft = 0;
        sumRight = 0;

        sizeLeft = 0;
        sizeRight = 0;
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Step 1: Calculate cost of every window

        long[] cost = new long[n - x + 1];

        clearWindow();

        // First window
        for (int i = 0; i < x; i++) {
            addNumber(nums[i]);
        }

        cost[0] = getCost();

        // Remaining windows using sliding window
        for (int i = x; i < n; i++) {

            removeNumber(nums[i - x]);

            addNumber(nums[i]);

            cost[i - x + 1] = getCost();
        }

        // Step 2: Dynamic Programming

        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[n + 1][k + 1];

        for (long[] row : dp) {
            Arrays.fill(row, INF);
        }

        // Zero windows require zero operations
        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }

        // dp[i][j] = minimum cost using first i elements
        // to select exactly j non-overlapping windows

        for (int i = 1; i <= n; i++) {

            for (int j = 1; j <= k; j++) {

                // Option 1: Skip current element
                dp[i][j] = dp[i - 1][j];

                // Option 2: Select a window ending at i-1
                if (i >= x) {

                    int start = i - x;

                    if (dp[start][j - 1] != INF) {

                        dp[i][j] = Math.min(
                            dp[i][j],
                            dp[start][j - 1] + cost[start]
                        );
                    }
                }
            }
        }

        return dp[n][k];
    }
}