package day14;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class day14 {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String player_instructions = get_input();
        System.out.println(part1(player_instructions, false));
        System.out.println(part1(player_instructions, true));
    }

    private static int part1(String instructions, boolean extended_hashing) throws NoSuchAlgorithmException {
        int counter = 0;
        Set<Tuple2<Integer, Potential_Hash>> valid_hashes = new HashSet();
        List<Potential_Hash> potential_hashes = new ArrayList<>();
        MessageDigest md = MessageDigest.getInstance("MD5");
        while (true){
            StringBuilder sb = new StringBuilder();
            sb.append(instructions);
            sb.append(counter);
            String to_be_hashed = sb.toString();
            String hashtext = generate_hash(md, to_be_hashed);
            if (extended_hashing){
                for (int i = 0; i < 2016; i += 1)
                    hashtext = generate_hash(md, hashtext);
            }
            Tuple2<Boolean, Character> triple_search_result = contains_triple(hashtext);
            if (triple_search_result.getFirst()) {
                Tuple2<Boolean, Character> five_search_result = contains_five(hashtext);
                if (five_search_result.getFirst()) {
                    find_valid_matches(five_search_result, potential_hashes, valid_hashes);
                }
                potential_hashes.add(new Potential_Hash(counter, triple_search_result.getSecond(), hashtext));
            }
            if (potential_hashes.size() > 0){
                if (counter > potential_hashes.get(0).get_round_found() + 1000)
                    potential_hashes.remove(0);
            }
            counter += 1;
            if (valid_hashes.size() >= 64)
                break;
        }
        return find_decisive_hash(valid_hashes);
    }

    private static int find_decisive_hash(Set<Tuple2<Integer, Potential_Hash>> valid_hashes){
        for (Tuple2<Integer, Potential_Hash> checked_hash_tuple: valid_hashes)
        {
            if (checked_hash_tuple.getFirst() == 64)
                return checked_hash_tuple.getSecond().get_round_found();
        }
        return -1;
    }

    private static Tuple2<Boolean, Character> contains_triple(String hashtext){
        char[] hash_as_array = hashtext.toCharArray();
        for (int i = 0; i < hash_as_array.length - 2; i += 1) {
            if (hash_as_array[i] == hash_as_array[i+1] && hash_as_array[i] == hash_as_array[i+2])
                return new Tuple2(true, hash_as_array[i]);
        }
        return new Tuple2(false, '0');
    }

    private static Tuple2<Boolean, Character> contains_five(String hashtext){
        char[] hash_as_array = hashtext.toCharArray();
        for (int i = 0; i < hash_as_array.length - 4; i += 1){
            if (hash_as_array[i] == hash_as_array[i+1] && hash_as_array[i] == hash_as_array[i+2]
                    && hash_as_array[i] == hash_as_array[i+3] && hash_as_array[i] == hash_as_array[i+4])
                return new Tuple2(true, hash_as_array[i]);
        }
        return new Tuple2(false, '0');
    }

    private static String get_input() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day14/input.txt"));
        return lines.get(0);
    }

    private static void find_valid_matches(Tuple2<Boolean, Character> five_search_result, List<Potential_Hash> tentative_hashes, Set<Tuple2<Integer, Potential_Hash>> valid_hashes){
        List<Potential_Hash> items_to_be_added = new ArrayList<>();
        for (Potential_Hash checked_possible_hash: tentative_hashes){
            if (checked_possible_hash.get_repeating_letter() == five_search_result.getSecond())
                items_to_be_added.add(checked_possible_hash);
        }
        for (Potential_Hash added_hash: items_to_be_added){
            valid_hashes.add(new Tuple2(valid_hashes.size() + 1, added_hash));
        }
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

    public static class Potential_Hash{
        private final int round_found;
        private final char repeating_letter;
        private final String hash_generated;
        public Potential_Hash(int round_found, char repeating_letter, String hash_generated){
            this.round_found = round_found;
            this.repeating_letter = repeating_letter;
            this.hash_generated = hash_generated;
        }

        public int get_round_found(){
            return this.round_found;
        }

        public int get_repeating_letter(){
            return this.repeating_letter;
        }

        public String get_hash_generated(){
            return this.hash_generated;
        }
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
