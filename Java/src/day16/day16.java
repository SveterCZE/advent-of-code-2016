package day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

public class day16 {
    public static void main (String[] args) throws IOException {
        List<Integer> code_input = get_input();
        System.out.println(part1(code_input, 272));
        System.out.println(part1(code_input, 35651584));
    }

    private static String part1 (List<Integer> code_input, Integer target_disk_size){
        if (code_input.size() > target_disk_size)
            return calculate_checksum(code_input, target_disk_size);
        else {
            List <Integer> b = new ArrayList<>();
            for (Integer checked_integer: code_input){
                b.add(checked_integer);
            }
            Collections.reverse(b);
            for (int i = 0; i < b.size(); i += 1){
                if (b.get(i) == 0)
                    b.set(i, 1);
                else
                    b.set(i, 0);
            }
            code_input.add(0);
            code_input.addAll(b);
            return part1(code_input, target_disk_size);
        }
    }

    private static String calculate_checksum (List<Integer> code_input, Integer target_disk_size){
        List<Integer> relevant_part = code_input.subList(0,target_disk_size);
        return checksum_recursive_helper(relevant_part);
    }

    private static String checksum_recursive_helper (List<Integer> relevant_part){
        if ((relevant_part.size() % 2) != 0)
            return convert_to_string(relevant_part);
        else {
            List<Integer> checksum_list = new ArrayList<>();
            for (int i = 0; i < (relevant_part.size() - 1); i += 2){
                if (relevant_part.get(i) == relevant_part.get(i+1)){
                    checksum_list.add(1);
                }
                else
                    checksum_list.add(0);
            }
            return checksum_recursive_helper(checksum_list);
        }
    }

    private static String convert_to_string(List<Integer> relevant_part){
        StringBuilder sb = new StringBuilder();
        for (Integer checked_int: relevant_part)
            sb.append(checked_int);
        return sb.toString();
    }

    private static List<Integer> get_input() throws IOException {
        List<String> raw_input = Files.readAllLines(Paths.get("src/day16/input.txt"));
        List<Integer> code_input = new ArrayList<>();
        for (String checked_line : raw_input){
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(checked_line);
            while(m.find()) {
                char[] converted = m.group().toCharArray();
                for (int i = 0; i < converted.length; i += 1){
                    code_input.add(Character.getNumericValue(converted[i]));
                }
            }
        }
        return code_input;
    }
}
