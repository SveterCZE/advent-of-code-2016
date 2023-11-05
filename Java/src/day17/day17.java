package day17;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class day17 {
    public static void main (String args[]) throws IOException, NoSuchAlgorithmException{
        String code_input = get_input();
        System.out.println(part1(code_input));
        System.out.println(part2(code_input));
    }

    private static String part1(String code_input) throws NoSuchAlgorithmException{
        Coordinate initial_coordinate = new Coordinate(0,0);
        List<Character> steps_taken = new ArrayList<>();
        List<Coordinate_Steps_Tuple> positions_DB = new ArrayList<>();
        positions_DB.add(new Coordinate_Steps_Tuple(initial_coordinate, steps_taken));
        return recursive_helper(code_input, positions_DB);
    }

    private static Integer part2(String code_input) throws NoSuchAlgorithmException{
        Coordinate initial_coordinate = new Coordinate(0,0);
        List<Character> steps_taken = new ArrayList<>();
        List<Coordinate_Steps_Tuple> positions_DB = new ArrayList<>();
        positions_DB.add(new Coordinate_Steps_Tuple(initial_coordinate, steps_taken));
        List<List<Character>> valid_journeys = new ArrayList<>();
        return recursive_helper_p2(code_input, positions_DB, valid_journeys).length();
    }

    private static String recursive_helper(String code_input, List<Coordinate_Steps_Tuple> positions_DB) throws NoSuchAlgorithmException {
    // BASE CASE --- Target reached
        for (Coordinate_Steps_Tuple checked_tuple: positions_DB){
            if (checked_tuple.getCurrent_coordinate().is_final_coordinate()) {
                return bulid_string(checked_tuple.getSteps_taken());
            }
        }
    // RECURSIVE CASE --- Explore potential alternative ways
        List<Coordinate_Steps_Tuple> new_moves_DB = new ArrayList<>();
        for (Coordinate_Steps_Tuple checked_tuple: positions_DB){
            List<Coordinate> potential_next_steps = generate_possible_steps(checked_tuple.getCurrent_coordinate());
            String hashcode = generate_hashocode(code_input, checked_tuple.getSteps_taken());
            for (int i = 0; i < potential_next_steps.size(); i += 1){
                if (potential_next_steps.get(i).is_valid_coordinate()){
                    if (is_door_open(hashcode, i)){
                        Coordinate_Steps_Tuple new_coordinate_step_tuple = generate_new_coord_and_journey(potential_next_steps, checked_tuple.getSteps_taken(), i);
                        new_moves_DB.add(new_coordinate_step_tuple);
                    }
                }
            }
        }
        return recursive_helper(code_input, new_moves_DB);
    }

    private static String recursive_helper_p2(String code_input, List<Coordinate_Steps_Tuple> positions_DB, List<List<Character>> valid_journeys) throws NoSuchAlgorithmException {
    // BASE CASE --- Target reached
        if (positions_DB.size() == 0)
            return bulid_string(valid_journeys.get(valid_journeys.size() - 1));
    // RECURSIVE CASE --- Explore potential alternative ways
        List<Coordinate_Steps_Tuple> new_moves_DB = new ArrayList<>();
        for (Coordinate_Steps_Tuple checked_tuple: positions_DB) {
            List<Coordinate> potential_next_steps = generate_possible_steps(checked_tuple.getCurrent_coordinate());
            String hashcode = generate_hashocode(code_input, checked_tuple.getSteps_taken());
            for (int i = 0; i < potential_next_steps.size(); i += 1) {
                if (potential_next_steps.get(i).is_valid_coordinate()) {
                    if (is_door_open(hashcode, i)) {
                        Coordinate_Steps_Tuple new_coordinate_step_tuple = generate_new_coord_and_journey(potential_next_steps, checked_tuple.getSteps_taken(), i);
                        if (new_coordinate_step_tuple.getCurrent_coordinate().is_final_coordinate())
                            valid_journeys.add(new_coordinate_step_tuple.getSteps_taken());
                        else
                            new_moves_DB.add(new_coordinate_step_tuple);
                    }
                }
            }
        }
        return recursive_helper_p2(code_input, new_moves_DB, valid_journeys);
    }

    private static String bulid_string(List<Character> final_list){
        StringBuilder sb = new StringBuilder();
        for (Character final_char : final_list)
            sb.append(final_char);
        return sb.toString();
    }

    private static String get_input() throws IOException {
        List<String> raw_input = Files.readAllLines(Paths.get("src/day17/input.txt"));
        return raw_input.get(0);
    }

    private static List<Coordinate> generate_possible_steps (Coordinate current_coordinate){
        List<Coordinate> possible_steps = new ArrayList<>();
        possible_steps.add(new Coordinate(current_coordinate.getX_coord() - 1, current_coordinate.getY_coord()));
        possible_steps.add(new Coordinate(current_coordinate.getX_coord() + 1, current_coordinate.getY_coord()));
        possible_steps.add(new Coordinate(current_coordinate.getX_coord(), current_coordinate.getY_coord() - 1));
        possible_steps.add(new Coordinate(current_coordinate.getX_coord(), current_coordinate.getY_coord() + 1));
        return possible_steps;
    }

    private static String generate_hashocode(String code_input, List<Character> steps_taken) throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();
        sb.append(code_input);
        for (Character checked_char: steps_taken){
            sb.append(checked_char);
        }
        String combined_string = sb.toString();
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] messageDigest = md.digest(combined_string.getBytes());
        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    private static boolean is_door_open(String hashcode, int position){
        int char_num_value = Character.getNumericValue(hashcode.charAt(position));
        if ((char_num_value >= 11) && (char_num_value <= 15)){
            return true;
        }
        return false;
    }

    private static Coordinate_Steps_Tuple generate_new_coord_and_journey(List<Coordinate> potential_next_steps, List<Character> steps_taken, int i){
        Coordinate new_coordinate = potential_next_steps.get(i);
        List<Character> new_journey = new ArrayList<>();
        for (Character checked_char: steps_taken){
            new_journey.add(checked_char);
        }
        if (i == 0)
            new_journey.add("U".charAt(0));
        else if (i == 1)
            new_journey.add("D".charAt(0));
        else if (i == 2)
            new_journey.add("L".charAt(0));
        else
            new_journey.add("R".charAt(0));
        return new Coordinate_Steps_Tuple(new_coordinate, new_journey);
    }

    private static class Coordinate_Steps_Tuple{
        private Coordinate current_coordinate;
        private List<Character> steps_taken;

        private Coordinate_Steps_Tuple(Coordinate current_coordinate, List<Character> steps_taken) {
            this.steps_taken = steps_taken;
            this.current_coordinate = current_coordinate;
        }

        public Coordinate getCurrent_coordinate() {
            return this.current_coordinate;
        }

        public List<Character> getSteps_taken() {
            return steps_taken;
        }
    }

    public static class Coordinate{
        private int x_coord;
        private int y_coord;

        private Coordinate(int x, int y){
            this.x_coord = x;
            this.y_coord = y;
        }

        public int getX_coord() {
            return x_coord;
        }

        public int getY_coord() {
            return y_coord;
        }

        private boolean is_final_coordinate(){
            if ((this.x_coord == 3) && (this.y_coord == 3))
                return true;
            else return false;
        }

        private boolean is_valid_coordinate(){
            if ((x_coord < 0) || (x_coord > 3) || (y_coord < 0) || (y_coord > 3))
                return false;
            else return true;
        }
    }
}
