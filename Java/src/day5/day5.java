package day5;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class day5 {

    public static void main (String[] args) throws IOException, NoSuchAlgorithmException {
        String player_instructions = get_input();
        System.out.println(part1(player_instructions));
        System.out.println(part2(player_instructions));
    }

    private static String part1(String player_instructions) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        StringBuilder sb = new StringBuilder(8);
        int index = 0;
        while (sb.length() < 8) {
            String hash = generate_hash(player_instructions, index, md);
            if (is_hash_valid(hash))
                sb.append(hash.toCharArray()[5]);
            index += 1;
        }
        return sb.toString();
    }

    private static String part2(String player_instructions) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        char[] results_char = {'0', '0', '0', '0', '0', '0', '0', '0'};
        boolean[] results_checker = new boolean[8];
        int index = 0;
        while (true) {
            String hash = generate_hash(player_instructions, index, md);
            if (is_hash_valid(hash)) {
                char[] hash_array = hash.toCharArray();
                int position_value = Character.getNumericValue(hash_array[5]);
                if (position_value < 8) {
                    if (!results_checker[position_value]) {
                        results_checker[position_value] = true;
                        results_char[position_value] = hash_array[6];
                    }
                    if (all_figures_instered(results_checker))
                        break;
                }
            }
            index += 1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(results_char);
        return sb.toString();
    }

    private static String get_input() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day5/input.txt"));
        return lines.get(0);
    }

    private static String generate_hash(String player_instructions, int index, MessageDigest md) {
        StringBuilder sb = new StringBuilder();
        sb.append(player_instructions);
        sb.append(index);
        String generated_input = sb.toString();
        byte[] messageDigest = md.digest(generated_input.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    private static boolean is_hash_valid (String hash){
        char[] char_array = hash.toCharArray();
        for (int i=0; i<5; i+=1) {
            if (char_array[i] != '0')
                return false;
        }
        return true;
    }

    private static boolean all_figures_instered (boolean[] checked_array) {
        for (boolean checked_bool: checked_array){
            if (!checked_bool)
                return false;
        }
        return true;
    }
}
