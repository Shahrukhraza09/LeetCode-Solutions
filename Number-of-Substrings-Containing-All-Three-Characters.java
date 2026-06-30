1class Solution {
2    public int numberOfSubstrings(String s) {
3        int n = s.length();
4        int[] count = new int[3]; // count[0]=a, count[1]=b, count[2]=c
5        int left = 0;
6        int ans = 0;
7
8        for (int right = 0; right < n; right++) {
9            count[s.charAt(right) - 'a']++;
10
11            while (count[0] > 0 && count[1] > 0 && count[2] > 0) {
12                ans += (n - right);
13
14                count[s.charAt(left) - 'a']--;
15                left++;
16            }
17        }
18
19        return ans;
20    }
21}