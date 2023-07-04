package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class day1 {
    public static void main (String[] args) throws IOException {
        List<Instruction> player_instructions = get_input();
        System.out.println(part1(player_instructions));
        System.out.println(part2(player_instructions));
    }

    private static int part1 (List<Instruction> player_instructions){
        Integer[] player_coordinates = new Integer[2];
        player_coordinates[0] = 0;
        player_coordinates[1] = 0;
        char player_direction = 'N';
        for (Instruction checked_instruction: player_instructions) {
            player_direction = make_turn(player_coordinates, player_direction, checked_instruction);
        }
        int manhattan_distance = count_manhattan_distance(player_coordinates);
        return manhattan_distance;
    }

    private static int part2 (List<Instruction> player_instructions){
        Set<Coordinate> visited_coordinates = new HashSet<>();
        Coordinate player_coordinate = new Coordinate(0, 0);
        visited_coordinates.add(player_coordinate);
        List<Coordinate> matches = new ArrayList<>();
        char player_direction = 'N';
        for (Instruction checked_instruction: player_instructions) {
            player_direction = make_turn_by_steps(player_coordinate, player_direction, checked_instruction, matches, visited_coordinates);
            if (matches.size() != 0){
                break;
            }
        }
        int manhattan_distance = matches.get(0).count_manhattan_distance();
        return manhattan_distance;
    }

    private static List<Instruction> get_input() throws IOException {
        List<String> my_input = ReadInput();
        List<Instruction> converted_instructions = new ArrayList<>();
        for (int i = 0; i < my_input.size(); i+=1){
            converted_instructions = parse_input(my_input.get(i), converted_instructions);
        }
        return converted_instructions;
    }

    private static char make_turn(Integer[] player_coordinates, char player_direction, Instruction checked_instruction){
        player_direction = turn_player(player_direction, checked_instruction);
        move_player(player_coordinates, player_direction, checked_instruction);
        return player_direction;
    }

    private static char make_turn_by_steps(Coordinate player_coordinates, char player_direction, Instruction checked_instruction, List<Coordinate> matches, Set<Coordinate> visited_coordinates){
        player_direction = turn_player(player_direction, checked_instruction);
        make_incremental_moves(player_coordinates, player_direction, checked_instruction, matches, visited_coordinates);
        return player_direction;
    }

    private static int count_manhattan_distance (Integer[] player_coordinates){
        return Math.abs(player_coordinates[0]) + Math.abs(player_coordinates[1]);
    }

    private static char turn_player (char direction, Instruction checked_instruction) {
        if (checked_instruction.get_direction() == 'R') {
            if (direction == 'N'){
                direction = 'E';
            }
            else if (direction == 'E'){
                direction = 'S';
            }
            else if (direction == 'S'){
                direction = 'W';
            }
            else direction = 'N';
        }

        else {
            if (direction == 'N'){
                direction = 'W';
            }
            else if (direction == 'W'){
                direction = 'S';
            }
            else if (direction == 'S'){
                direction = 'E';
            }
            else direction = 'N';
        }
        return direction;
    }

    private static void move_player(Integer[] player_coordinates, char player_direction, Instruction checked_instruction) {
        if (player_direction == 'N'){
            player_coordinates[0] += checked_instruction.get_step_count();
        }
        else if (player_direction == 'S'){
            player_coordinates[0] -= checked_instruction.get_step_count();
        }
        else if (player_direction == 'E'){
            player_coordinates[1] += checked_instruction.get_step_count();
        }
        else if (player_direction == 'W'){
            player_coordinates[1] -= checked_instruction.get_step_count();
        }
    }

    private static void make_incremental_moves(Coordinate player_coordinates, char player_direction, Instruction checked_instruction, List<Coordinate> matches, Set<Coordinate> visited_coordinates){
        for (int i = 0; i < checked_instruction.get_step_count(); i += 1) {
            if (player_direction == 'N'){
                player_coordinates.change_x_coord(1);
            }
            else if (player_direction == 'S'){
                player_coordinates.change_x_coord(-1);
            }
            else if (player_direction == 'E'){
                player_coordinates.change_y_coord(1);
            }
            else if (player_direction == 'W'){
                player_coordinates.change_y_coord(-1);
            }
            Coordinate placeholder_coord = new Coordinate(player_coordinates.get_x_coord(), player_coordinates.get_y_coord());
            if (visited_coordinates.contains(placeholder_coord)){
                matches.add(placeholder_coord);
            }
            else {
                visited_coordinates.add(placeholder_coord);
            }
        }
    }

    private static List<Instruction> parse_input(String input_list, List<Instruction> converted_instructions) {
        List<String> split_instruction_List = Arrays.asList(input_list.split(","));
        for (int i = 0; i < split_instruction_List.size(); i+=1) {
            int step_count;
            char direction = 'a';
            StringBuffer sb_steps = new StringBuffer();
            for(char c : split_instruction_List.get(i).toCharArray()) {
                if(Character.toString(c).matches("\\d")){
                    sb_steps.append(c);
                }
                if(Character.toString(c).matches("[a-zA-Z]")){
                    direction = c;
                }
            }
            step_count = Integer.parseInt(sb_steps.toString());
            converted_instructions.add(new Instruction(direction, step_count));
        }
        return converted_instructions;
    }

    private static List<String> ReadInput() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day1/input.txt"));
        return lines;
    }

    private static class Instruction {
        private char direction;
        private int step_count;

        private Instruction(char direction, int step_count){
            this.direction = direction;
            this.step_count = step_count;
        }

        private char get_direction(){
            return this.direction;
        }
        private int get_step_count(){
            return this.step_count;
        }
    }

    private static class Coordinate {
        private int x_coord;
        private int y_coord;

        private Coordinate(int x_coord, int y_coord){
            this.x_coord = x_coord;
            this.y_coord = y_coord;
        }

        private int get_x_coord(){
            return this.x_coord;
        }
        private int get_y_coord(){
            return this.y_coord;
        }

        private void change_x_coord(int change_value){
            this.x_coord += change_value;
        }

        private void change_y_coord(int change_value){
            this.y_coord += change_value;
        }

        private int count_manhattan_distance(){
            return Math.abs(this.get_x_coord()) + Math.abs(this.get_y_coord());
        }

        @Override
        public boolean equals(Object o){
            if (o == this) {
                return true;
            }

            if (!(o instanceof Coordinate)) {
                return false;
            }

            Coordinate c = (Coordinate) o;

            if ((this.get_x_coord() == c.get_x_coord()) && (this.get_y_coord() == c.get_y_coord())) return true;
            else return false;
        }

        @Override
        public int hashCode(){
            int hash = 713;
            hash = 713 * hash + this.get_x_coord();
            hash = 713 * hash + this.get_y_coord();
            return hash;
        }
    }
}
