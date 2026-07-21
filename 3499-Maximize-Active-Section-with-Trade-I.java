class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        String t = "1" + s + "1";

        int originalOnes = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') originalOnes++;
        }

        ArrayList<Character> ch = new ArrayList<>();
        ArrayList<Integer> len = new ArrayList<>();

        int i = 0;
        while (i < t.length()) {
            char c = t.charAt(i);
            int j = i;
            while (j < t.length() && t.charAt(j) == c) j++;
            ch.add(c);
            len.add(j - i);
            i = j;
        }

        int ans = originalOnes;

        for (int k = 1; k + 1 < ch.size(); k++) {
            if (ch.get(k) == '1' &&
                ch.get(k - 1) == '0' &&
                ch.get(k + 1) == '0') {

                ans = Math.max(ans,
                        originalOnes + len.get(k - 1) + len.get(k + 1));
            }
        }

        return ans;
    }
}