public class LongestPalindromicSubstring {
    public static void main(String[] args) {
        String test1 = "babad";
        String test2 = "cbbd";
        String test3 = "a";
        String test4 = "aacabdkacaa";
        
        System.out.println("Input: \"" + test1 + "\" → Output: \"" + longestPalindrome(test1) + "\"");
        System.out.println("Input: \"" + test2 + "\" → Output: \"" + longestPalindrome(test2) + "\"");
        System.out.println("Input: \"" + test3 + "\" → Output: \"" + longestPalindrome(test3) + "\"");
        System.out.println("Input: \"" + test4 + "\" → Output: \"" + longestPalindrome(test4) + "\"");
    }
    
    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        
        int start = 0;
        int end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // Consider odd length palindromes (with center at i)
            int len1 = expandAroundCenter(s, i, i);
            
            // Consider even length palindromes (with center between i and i+1)
            int len2 = expandAroundCenter(s, i, i + 1);
            
            // Take the longer of the two expansions
            int len = Math.max(len1, len2);
            
            // Update start and end if we found a longer palindrome
            if (len > end - start) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }
        
        return s.substring(start, end + 1);
    }
    
    // Helper method to expand around center and find palindrome length
    private static int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        
        // Return the length of the palindrome
        // (right - left - 1) gives us the length
        return right - left - 1;
    }
}
