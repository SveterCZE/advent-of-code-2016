package day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class day2 {
    public static void main(String[] args) throws IOException {
        List<String[]> player_instructions = get_input();
        System.out.println(part1(player_instructions));
        System.out.println(part2(player_instructions));
    }

    private static List<String[]> get_input() throws IOException {
        List<String> my_input = ReadInput();
        List<String[]> converted_instructions = new ArrayList<>();
        for (int i = 0; i < my_input.size(); i += 1) {
            String[] temp_array = my_input.get(i).split("");
            converted_instructions.add(temp_array);
        }
        return converted_instructions;
    }

    private static List<String> ReadInput() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day2/input.txt"));
        return lines;
    }

    private static String part1(List<String[]> player_instructions) {
        Integer[] player_coordinates = {1,1};
        StringBuilder sb = new StringBuilder();
        char[][] numpad = {{'1', '2', '3'},
                {'4', '5', '6'},
                {'7', '8', '9'}};
        for (String[] instruction_array: player_instructions) {
            sb.append(make_movements(instruction_array, numpad, player_coordinates, numpad.length - 1));
        }
        return sb.toString();
    }

    private static String part2(List<String[]> player_instructions) {
        Integer[] player_coordinates = {3,0};
        StringBuilder sb = new StringBuilder();
        char[][] numpad = {{'x', 'x', '1', 'x', 'x'},
                {'x', '2', '3', '4', 'x'},
                {'5', '6', '7', '8', '9'},
                {'x', 'A', 'B', 'C', 'x'},
                {'x', 'x', 'D', 'x', 'x'}};
        for (String[] instruction_array: player_instructions) {
            sb.append(make_movements(instruction_array, numpad, player_coordinates, numpad.length - 1));
        }
        return sb.toString();
    }

    private static char make_movements(String[] instruction_array, char[][] numpad, Integer[] player_coordinates, int maxlen){
        for (String movement: instruction_array){
            Integer[] proposed_coordinates = propose_new_coordinates(player_coordinates, movement);
            if (is_valid_coordinate(proposed_coordinates, maxlen, numpad)){
                player_coordinates[0] = proposed_coordinates[0];
                player_coordinates[1] = proposed_coordinates[1];
            }
        }
        return numpad[player_coordinates[0]][player_coordinates[1]];
    }

    private static Integer[] propose_new_coordinates(Integer[] player_coordinates, String movement){
        Integer[] proposed_coordinates = {player_coordinates[0], player_coordinates[1]};
        if (movement.equals("U"))
            proposed_coordinates[0] -= 1;
        else if (movement.equals("D"))
            proposed_coordinates[0] += 1;
        else if (movement.equals("R"))
            proposed_coordinates[1] += 1;
        else if (movement.equals("L"))
            proposed_coordinates[1] -= 1;
        return proposed_coordinates;
    }

    private static boolean is_valid_coordinate(Integer[] proposed_coordinate, int maxlen, char[][] numpad){
        if ((proposed_coordinate[0] >= 0) && (proposed_coordinate[0] <= maxlen) && (proposed_coordinate[1] >= 0) && (proposed_coordinate[1] <= maxlen) && numpad[proposed_coordinate[0]][proposed_coordinate[1]] != 'x')
            return true;
        else
            return false;
    }
}