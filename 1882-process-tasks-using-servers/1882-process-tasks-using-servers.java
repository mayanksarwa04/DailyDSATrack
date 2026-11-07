class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Integer.compare(a[0], b[0]);
                return Integer.compare(a[1], b[1]);
            }
        );

        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Integer.compare(a[0], b[0]);
                if (a[1] != b[1])
                    return Integer.compare(a[1], b[1]);
                return Integer.compare(a[2], b[2]);
            }
        );

        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        int[] ans = new int[m];

        int time = 0;

        for (int j = 0; j < m; j++) {

            // Time cannot go backwards
            time = Math.max(time, j);

            // Release completed servers
            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                int[] s = busy.poll();
                free.offer(new int[]{s[1], s[2]});
            }

            // Wait if no server is available
            if (free.isEmpty()) {

                time = busy.peek()[0];

                // Release all servers finishing at this time
                while (!busy.isEmpty() && busy.peek()[0] <= time) {
                    int[] s = busy.poll();
                    free.offer(new int[]{s[1], s[2]});
                }
            }

            // Select the best available server
            int[] server = free.poll();

            int weight = server[0];
            int index = server[1];

            ans[j] = index;

            // Assign task and update finishing time
            busy.offer(new int[]{
                time + tasks[j],
                weight,
                index
            });
        }

        return ans;
    }
}
