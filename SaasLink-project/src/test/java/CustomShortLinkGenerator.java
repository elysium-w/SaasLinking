import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CustomShortLinkGenerator {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 6; // 短链接长度

    private static final Map<String, String> urlMap = new HashMap<>(); // 用于存储长链接和短链接的映射关系

    public static String generateShortLink(String longUrl) {
        // 检查长链接是否已经生成过短链接
        if (urlMap.containsKey(longUrl)) {
            return urlMap.get(longUrl);
        }

        // 生成短链接
        StringBuilder shortLink = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < LENGTH; i++) {
            shortLink.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        // 存储长链接和短链接的映射关系
        urlMap.put(longUrl, shortLink.toString());

        return shortLink.toString();
    }

    public static void main(String[] args) {
        String longUrl1 = "https://www.example.com/page1";
        String shortLink1 = generateShortLink(longUrl1);
        System.out.println("Short link for " + longUrl1 + ": " + shortLink1);

        String longUrl2 = "https://www.example.com/page2";
        String shortLink2 = generateShortLink(longUrl2);
        System.out.println("Short link for " + longUrl2 + ": " + shortLink2);

        // 如果再次生成相同的长链接的短链接，应该返回之前生成的短链接
        String shortLink3 = generateShortLink(longUrl1);
        System.out.println("Short link for " + longUrl1 + ": " + shortLink3 + " (Reusing existing short link)");
    }
}
