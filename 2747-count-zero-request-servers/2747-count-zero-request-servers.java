class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {

        // Sort logs by request time
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        // Store queries with their original indices
        int k = queries.length;
        int[][] sortedQueries = new int[k][2];

        for (int i = 0; i < k; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }

        // Sort queries by time
        Arrays.sort(sortedQueries, (a, b) ->
            Integer.compare(a[0], b[0]));

        int[] ans = new int[k];

        // Server ID -> Request count in current window
        HashMap<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int right = 0;
        int m = logs.length;

        for (int i = 0; i < k; i++) {

            int q = sortedQueries[i][0];
            int start = q - x;

            // Add logs whose time <= query time
            while (right < m && logs[right][1] <= q) {

                int server = logs[right][0];

                map.put(server, map.getOrDefault(server, 0) + 1);

                right++;
            }

            // Remove logs whose time < window start
            while (left < right && logs[left][1] < start) {

                int server = logs[left][0];

                map.put(server, map.get(server) - 1);

                if (map.get(server) == 0) {
                    map.remove(server);
                }

                left++;
            }

            // Servers with zero requests
            ans[sortedQueries[i][1]] = n - map.size();
        }

        return ans;
    }
}