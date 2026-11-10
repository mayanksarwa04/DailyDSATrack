
import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        // Stores currently available servers
        TreeSet<Integer> available = new TreeSet<>();

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        // Min Heap: {finishTime, serverId}
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        // Count requests handled by each server
        int[] count = new int[k];

        for (int i = 0; i < arrival.length; i++) {

            // Release all servers whose requests are completed
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {

                long[] completed = busy.poll();

                int server = (int) completed[1];

                available.add(server);
            }

            // If all servers are busy, drop the request
            if (available.isEmpty()) {
                continue;
            }

            // Preferred server
            int preferred = i % k;

            // Find next available server
            Integer server = available.ceiling(preferred);

            // Wrap around if no server is available after preferred
            if (server == null) {
                server = available.first();
            }

            // Assign request
            available.remove(server);

            count[server]++;

            // Calculate completion time
            long finishTime = (long) arrival[i] + load[i];

            busy.offer(new long[]{finishTime, server});
        }

        // Find maximum requests handled
        int max = 0;

        for (int c : count) {
            max = Math.max(max, c);
        }

        // Collect busiest servers
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                result.add(i);
            }
        }

        return result;
    }
}
