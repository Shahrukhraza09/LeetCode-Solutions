import java.util.*;

public class Solution {
    static class Edge {
        int to;
        long cost;
        Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    // Wrapper for driver
    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        return maxPathScore(n, edges, online, k);
    }

    private int maxPathScore(int n, int[][] edges, boolean[] online, long k) {
        // Build adjacency list
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
        long maxEdge = 0;
        int[] indegree = new int[n];
        for (int[] e : edges) {
            graph.get(e[0]).add(new Edge(e[1], e[2]));
            indegree[e[1]]++;
            maxEdge = Math.max(maxEdge, e[2]);
        }

        // Compute topological order once
        List<Integer> topo = new ArrayList<>();
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < n; i++) if (indegree[i] == 0) q.add(i);
        while (!q.isEmpty()) {
            int u = q.poll();
            topo.add(u);
            for (Edge e : graph.get(u)) {
                indegree[e.to]--;
                if (indegree[e.to] == 0) q.add(e.to);
            }
        }

        int ans = -1;
        long low = 0, high = maxEdge;

        while (low <= high) {
            long mid = (low + high) / 2;
            if (canReach(graph, topo, n, online, k, mid)) {
                ans = (int) mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    private boolean canReach(List<List<Edge>> graph, List<Integer> topo, int n,
                             boolean[] online, long k, long threshold) {
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;

        for (int u : topo) {
            if (!online[u]) continue; // skip offline nodes
            if (dist[u] == Long.MAX_VALUE) continue;
            for (Edge e : graph.get(u)) {
                if (e.cost < threshold) continue;
                if (!online[e.to]) continue;
                if (dist[u] + e.cost < dist[e.to]) {
                    dist[e.to] = dist[u] + e.cost;
                }
            }
        }
        return dist[n - 1] <= k;
    }
}
