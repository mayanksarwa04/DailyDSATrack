
import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;

        // Find minimum price
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            minPrice = Math.min(minPrice, item[1]);
        }

        // Calculate benefit of activating each item
        int[] value = new int[n];

        for (int i = 0; i < n; i++) {
            value[i] = 1;

            for (int j = 0; j < n; j++) {
                if (i != j &&
                    items[j][0] % items[i][0] == 0) {
                    value[i]++;
                }
            }
        }

        // dp0: Maximum value without buying
        // any minimum-price item.

        // dp1: Maximum value after buying
        // at least one minimum-price item.

        int NEG = -1_000_000_000;

        int[] dp0 = new int[budget + 1];
        int[] dp1 = new int[budget + 1];

        Arrays.fill(dp0, NEG);
        Arrays.fill(dp1, NEG);

        dp0[0] = 0;

        // 0/1 Knapsack
        for (int i = 0; i < n; i++) {

            int price = items[i][1];
            int val = value[i];

            boolean isMin = (price == minPrice);

            for (int b = budget; b >= price; b--) {

                if (isMin) {

                    // Activate the first minimum-price item
                    dp1[b] = Math.max(
                        dp1[b],
                        dp0[b - price] + val
                    );

                    // Activate another minimum-price item
                    if (dp1[b - price] != NEG) {
                        dp1[b] = Math.max(
                            dp1[b],
                            dp1[b - price] + val
                        );
                    }

                } else {

                    // Buy a non-minimum-price item
                    if (dp0[b - price] != NEG) {
                        dp0[b] = Math.max(
                            dp0[b],
                            dp0[b - price] + val
                        );
                    }

                    if (dp1[b - price] != NEG) {
                        dp1[b] = Math.max(
                            dp1[b],
                            dp1[b - price] + val
                        );
                    }
                }
            }
        }

        int ans = 0;

        for (int b = 0; b <= budget; b++) {

            // At least one minimum-price item selected.
            // Remaining budget buys additional copies.
            if (dp1[b] != NEG) {
                int remaining = budget - b;

                ans = Math.max(
                    ans,
                    dp1[b] + remaining / minPrice
                );
            }

            // No minimum-price item selected.
            // Remaining budget must be less than minPrice.
            if (budget - b < minPrice && dp0[b] != NEG) {
                ans = Math.max(ans, dp0[b]);
            }
        }

        return ans;
    }
}

