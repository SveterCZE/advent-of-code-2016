package day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class day6 {

    public static void main (String[] args) throws IOException {
        List<char[]> player_instructions = get_input();
        System.out.println(part1(player_instructions));
        System.out.println(part2(player_instructions));
    }

    private static String part1(List<char[]> player_instruction){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < player_instruction.get(i).length; i += 1){
            Map<Character, Integer> frequency_table = get_frequency_table(player_instruction, i);
            sb.append(find_most_frequent_letter(frequency_table));
        }
        return sb.toString();
    }

    private static String part2(List<char[]> player_instruction){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < player_instruction.get(i).length; i += 1){
            Map<Character, Integer> frequency_table = get_frequency_table(player_instruction, i);
            sb.append(find_least_frequent_letter(frequency_table));
        }
        return sb.toString();
    }

    private static Map<Character, Integer> get_frequency_table(List<char[]> player_instruction, int column){
        Map<Character, Integer> frequency_table = new HashMap<>();
        for (int i = 0; i < player_instruction.size(); i += 1){
            if(frequency_table.containsKey(player_instruction.get(i)[column])){
                frequency_table.replace(player_instruction.get(i)[column], frequency_table.get(player_instruction.get(i)[column]) + 1);
            }
            else{
                frequency_table.put(player_instruction.get(i)[column], 1);
            }
        }
        return frequency_table;
    }

    private static Character find_most_frequent_letter(Map<Character, Integer> frequency_table) {
        int highest_frequency = 0;
        Character most_frequent_letter = ' ';
        for (Map.Entry<Character, Integer> entry : frequency_table.entrySet()){
            if (entry.getValue() > highest_frequency){
                highest_frequency = entry.getValue();
                most_frequent_letter = entry.getKey();
            }
        }
        return most_frequent_letter;
    }

    private static Character find_least_frequent_letter(Map<Character, Integer> frequency_table) {
        int lowest_frequency = 999999;
        Character least_frequent_letter = ' ';
        for (Map.Entry<Character, Integer> entry : frequency_table.entrySet()){
            if (entry.getValue() < lowest_frequency){
                lowest_frequency = entry.getValue();
                least_frequent_letter = entry.getKey();
            }
        }
        return least_frequent_letter;
    }

    private static List<char[]> get_input() throws IOException {
        List<char[]> player_instruction = new ArrayList<>();
        List<String> lines = load_input();
        for (String line: lines){
            player_instruction.add(line.toCharArray());
        }
        return player_instruction;
    }

    private static List<String> load_input() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day6/input.txt"));
        return lines;
    }
}
