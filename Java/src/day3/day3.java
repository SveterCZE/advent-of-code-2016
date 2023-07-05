package day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day3 {
    public static void main (String[] args) throws IOException {
        List<List<Integer>> player_instructions = get_input();
        System.out.println(part1(player_instructions));
        player_instructions = get_input();
        System.out.println(part2(player_instructions));
    }

    private static int part1(List<List<Integer>> player_instructions){
        int valid_triangle_count = 0;
        for (List<Integer> indvidual_triangle: player_instructions) {
            Collections.sort(indvidual_triangle);
            if (is_valid_triangle(indvidual_triangle)) {
                valid_triangle_count += 1;
            }
        }
        return valid_triangle_count;
    }

    private static int part2(List<List<Integer>> player_instructions){
        int valid_triangle_count = 0;
        for (int i = 0; i <= player_instructions.size() - 1; i += 3){
            for (int j = 0; j < 3; j += 1 ) {
                List<Integer> indvidual_triangle = new ArrayList<>();
                indvidual_triangle.add(player_instructions.get(i).get(j));
                indvidual_triangle.add(player_instructions.get(i+1).get(j));
                indvidual_triangle.add(player_instructions.get(i+2).get(j));
                Collections.sort(indvidual_triangle);
                if (is_valid_triangle(indvidual_triangle)) {
                    valid_triangle_count += 1;
                }
            }
        }
        return valid_triangle_count;
    }
    
    private static boolean is_valid_triangle(List<Integer> indvidual_triangle) {
        if (indvidual_triangle.get(0) + indvidual_triangle.get(1) > indvidual_triangle.get(2))
            return true;
        else return false;
    }

    private static List<List<Integer>> get_input() throws IOException {
        List<String> my_input = ReadInput();
        List<List<Integer>> converted_instructions = new ArrayList<>();
        for (int i = 0; i < my_input.size(); i+=1){
            parse_input(my_input.get(i), converted_instructions);
        }
        return converted_instructions;
    }

    private static List<String> ReadInput() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day3/input.txt"));
        return lines;
    }

    private static void parse_input(String input_line, List<List<Integer>> converted_instructions) {
        List<Integer> parsed_line = new ArrayList<>();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(input_line);
        while(m.find()) {
            parsed_line.add(Integer.valueOf(m.group()));
        }
        converted_instructions.add(parsed_line);
    }

}
