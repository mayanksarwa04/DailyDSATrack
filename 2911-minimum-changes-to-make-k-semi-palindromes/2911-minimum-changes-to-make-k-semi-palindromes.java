
class Solution {

    public int minimumChanges(String s, int k) {

        int n = s.length();

        // Part 1: Precompute the minimum cost
        // to make every substring semi-palindromic.

        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {

            for (int i = 0; i + len <= n; i++) {

                int j = i + len - 1;

                cost[i][j] = Integer.MAX_VALUE;

                // Try every valid divisor d
                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    // Compare symmetric characters
                    // in each group.
                    for (int r = 0; r < d; r++) {

                        int left = i + r;
                        int right = i + len - d + r;

                        while (left < right) {

                            if (s.charAt(left) != s.charAt(right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    cost[i][j] = Math.min(cost[i][j], changes);
                }
            }
        }

        // Part 2: Dynamic Programming

        int INF = 1000000000;

        int[][] dp = new int[k + 1][n + 1];

        for (int p = 0; p <= k; p++) {
            for (int i = 0; i <= n; i++) {
                dp[p][i] = INF;
            }
        }

        // Base case
        dp[0][0] = 0;

        // p = number of partitions
        for (int p = 1; p <= k; p++) {

            // i = number of characters considered
            for (int i = 2 * p; i <= n; i++) {

                // j = starting index of last substring
                for (int j = 2 * (p - 1); j <= i - 2; j++) {

                    if (dp[p - 1][j] == INF) {
                        continue;
                    }

                    dp[p][i] = Math.min(
                        dp[p][i],
                        dp[p - 1][j] + cost[j][i - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}

