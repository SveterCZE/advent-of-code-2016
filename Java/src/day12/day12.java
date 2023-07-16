package day12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class day12 {
    public static void main(String[] args) throws IOException{
        List<String[]> instructions = get_input();
        System.out.println(part1(instructions, 0));
        System.out.println(part1(instructions, 1));
    }

    private static List<String[]> get_input() throws IOException {
        List<String[]> processed_instructions = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get("src/day12/input.txt"));
        for (String checked_string: lines)
            processed_instructions.add(checked_string.split(" "));
        return processed_instructions;
    }

    private static int part1(List<String[]> instructions, int c_value){
        Map<String, Integer> registers = new HashMap<>();
        registers.put("a", 0);
        registers.put("b", 0);
        registers.put("c", c_value);
        registers.put("d", 0);
        int pointer = 0;
        while (true){
            String[] checked_instruction = instructions.get(pointer);
            if (checked_instruction[0].equals("cpy")){
                if (Pattern.compile("-?\\d+(\\.\\d+)?").matcher(checked_instruction[1]).matches())
                    registers.put(checked_instruction[2], Integer.valueOf(checked_instruction[1]));
                else
                    registers.put(checked_instruction[2], registers.get(checked_instruction[1]));
                pointer += 1;
            }
            else if (checked_instruction[0].equals("inc")){
                registers.put(checked_instruction[1], registers.get(checked_instruction[1]) + 1);
                pointer += 1;
            }
            else if (checked_instruction[0].equals("dec")){
                registers.put(checked_instruction[1], registers.get(checked_instruction[1]) - 1);
                pointer += 1;
            }
            else if (checked_instruction[0].equals("jnz")){
                if (Pattern.compile("-?\\d+(\\.\\d+)?").matcher(checked_instruction[1]).matches()){
                    if (!checked_instruction[1].equals(0))
                        pointer += Integer.parseInt(checked_instruction[2]);
                    else
                        pointer += 1;
                }
                else {
                    if (!registers.get(checked_instruction[1]).equals(0))
                        pointer += Integer.parseInt(checked_instruction[2]);
                    else
                        pointer += 1;
                }
            }
            if (pointer >= instructions.size()){
                break;
            }
        }
        return registers.get("a");
    }
}
