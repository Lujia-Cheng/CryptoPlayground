import java.util.TreeMap;

public class FrequencyAnalysis {
    public static void main(String[] args) {
        String text = "aaab";
        frequencyAnalysis(text);
    }

    private static void frequencyAnalysis(String str) {
        TreeMap<Character, Integer> map = new TreeMap<>();
        for (char ch : str.toUpperCase().toCharArray()) {
            map.merge(ch, 1, (o, n) -> o + 1);
        }
        System.out.println("char, count");
        map.forEach((k, v) -> System.out.println(k + ", " + v));
    }
}
