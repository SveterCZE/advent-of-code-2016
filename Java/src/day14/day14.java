package day14;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class day14 {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String player_instructions = get_input();
        System.out.println(part1(player_instructions, false));
        System.out.println(part1(player_instructions, true));
    }

    private static int part1(String instructions, boolean extended_hashing) throws NoSuchAlgorithmException {
        int counter = 0;
        List<Integer> valid_hashes = new ArrayList<>();
        Map<Integer, char[]> pre_calculated_hashes = new HashMap<>();
        while (true){
            char[] hashtext = calculate_hash(instructions, counter, extended_hashing, pre_calculated_hashes);
            Tuple2<Boolean, Character> triple_search_result = contains_triple(hashtext);
            if (triple_search_result.getFirst()) {
                for (int i = 0; i < 1000; i += 1){
                    char[] successive_hashtext = calculate_hash(instructions, counter + i + 1, extended_hashing, pre_calculated_hashes);
                    boolean five_search_result = contains_five(successive_hashtext, triple_search_result.getSecond());
                    if (five_search_result){
                        valid_hashes.add(counter);
                        break;
                    }
                }
            }
            counter += 1;
            if (valid_hashes.size() >= 64)
                break;
        }
        return valid_hashes.get(63);
    }

    private static char[] calculate_hash(String instructions, int counter, boolean extended_hashing, Map<Integer, char[]> pre_calculated_hashes) throws  NoSuchAlgorithmException {
        if (pre_calculated_hashes.containsKey(counter))
            return pre_calculated_hashes.get(counter);
        else {
            MessageDigest md = MessageDigest.getInstance("MD5");
            StringBuilder sb = new StringBuilder();
            sb.append(instructions);
            sb.append(counter);
            String to_be_hashed = sb.toString();
            String hashtext = generate_hash(md, to_be_hashed);
            if (extended_hashing) {
                for (int i = 0; i < 2016; i += 1)
                    hashtext = generate_hash(md, hashtext);
            }
            char[] hash_as_array = hashtext.toCharArray();
            pre_calculated_hashes.put(counter, hash_as_array);
            return hash_as_array;
        }
    }

    private static Tuple2<Boolean, Character> contains_triple(char[] hashtext){
        for (int i = 0; i < hashtext.length - 2; i += 1) {
            if (hashtext[i] == hashtext[i+1] && hashtext[i] == hashtext[i+2])
                return new Tuple2(true, hashtext[i]);
        }
        return new Tuple2(false, '0');
    }

    private static boolean contains_five(char[] hashtext, char seeked_match){
        for (int i = 0; i < hashtext.length - 4; i += 1){
            if (hashtext[i] == hashtext[i+1] && hashtext[i] == hashtext[i+2]
                    && hashtext[i] == hashtext[i+3] && hashtext[i] == hashtext[i+4] && hashtext[i] == seeked_match)
                return true;
        }
        return false;
    }

    private static String get_input() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day14/input.txt"));
        return lines.get(0);
    }

    private static String generate_hash(MessageDigest md, String instructions){
        byte[] messageDigest = md.digest(instructions.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    public static class Tuple2<T,U>{
        private final T first;
        private final U second;

        public Tuple2(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() { return first; }
        public U getSecond() { return second; }
    }
}
