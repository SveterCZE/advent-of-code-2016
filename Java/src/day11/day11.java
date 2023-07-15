package day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class day11 {
    public static void main(String[] args) throws IOException{
        int[] initial_state = get_input();
        System.out.println(part1(initial_state));
        System.out.println(part1(add_part2_items(initial_state)));
    }

    private static int[] get_input() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day11/input.txt"));
        List<List<String>> split_lines = new ArrayList<>();
        List<String> elements = new ArrayList<>();
        for (String checked_string: lines){
            String[] split_line = checked_string.split(" ");
            split_lines.add(new ArrayList<String>(Arrays.asList(split_line)));
            for (int i = 0; i < split_line.length; i += 1){
                if (split_line[i].contains("generator")){
                    elements.add(split_line[i -1]);
                }
            }
        }
        Collections.sort(elements);
        int[] positions = new int[(elements.size() * 2) + 1];
        positions[0] = 1;

        for (int i = 0; i < split_lines.size(); i += 1){
            for (int j = 0; j < elements.size(); j += 1){
                if (split_lines.get(i).contains(elements.get(j)))
                    positions[(j * 2) + 2] = i + 1;
                StringBuilder sb = new StringBuilder();
                sb.append(elements.get(j));
                sb.append("-compatible");
                String chip_name = sb.toString();
                if (split_lines.get(i).contains(chip_name))
                    positions[(j * 2) + 1] = i + 1;
            }
        }
        return positions;
    }

    private static int part1(int[] initial_state){
        Set<String> positions_db = new HashSet();
        positions_db.add(Arrays.toString(initial_state));
        List<int[]> list_of_instructions = new ArrayList<>();
        list_of_instructions.add(initial_state);
        return make_individual_movements(list_of_instructions, positions_db, 0);
    }

    private static int make_individual_movements(List<int[]> list_of_instructions, Set<String> positions_db, int step_count){
        // BASE CASE --- Any solution is final
        for (int[] checked_solution: list_of_instructions){
            if (is_final_solution(checked_solution))
                return step_count;
        }
        // RECURSIVE CASE --- Check each element and generate possible new states
        List<int[]> valid_states = new ArrayList<>();
        for (int[] checked_possible_state: list_of_instructions){
            // Determine positions of suitable elements for movement
            List<Integer> available_elements = find_suitable_elements(checked_possible_state);
            List<List<Integer>> possible_couples = generate_possible_couples(available_elements);
            for (Integer possible_element: available_elements){
                List<Integer> individual_item_list = new ArrayList<>();
                individual_item_list.add(possible_element);
                possible_couples.add(individual_item_list);
            }
            // Determine possible destination floors
            List<Integer> destination_floors = find_destination_floors(checked_possible_state[0]);
            List<int[]> possible_new_states = new ArrayList<>();
            for (Integer destination_floor: destination_floors){
                for (List<Integer> moved_couple: possible_couples){
                    int[] temp_new_state = Arrays.copyOf(checked_possible_state, checked_possible_state.length);
                    temp_new_state[0] = destination_floor;
                    for (Integer elem: moved_couple){
                        temp_new_state[elem] = destination_floor;
                    }
                    possible_new_states.add(temp_new_state);
                }
            }
            generate_valid_states(possible_new_states, positions_db, valid_states);
        }
        return make_individual_movements(valid_states, positions_db, step_count + 1);
    }

    private static int[] add_part2_items(int[] initial_state){
        int[] positions = new int[initial_state.length + 4];
        for (int i = 0; i < initial_state.length; i += 1)
            positions[i] = initial_state[i];
        for (int i = initial_state.length; i < initial_state.length + 4; i += 1)
            positions[i] = 1;
        return positions;
    }

    private static void generate_valid_states(List<int[]> possible_new_states, Set<String> positions_db, List<int[]> valid_states){
        for (int[] checked_state: possible_new_states){
            String checked_state_hash = Arrays.toString(checked_state);
            if (!positions_db.contains(checked_state_hash)){
                if (is_valid_state(checked_state)){
                    valid_states.add(checked_state);
                    positions_db.add(checked_state_hash);
                }
            }
        }
    }

    private static boolean is_valid_state(int[] checked_state){
        for (int chid_id = 1; chid_id<checked_state.length; chid_id += 2){
            if (!(is_chip_safe(checked_state, chid_id)))
                return false;
        }
        return true;
    }

    private static boolean is_chip_safe(int[] checked_state, int chip_id){
        if (checked_state[chip_id] == checked_state[chip_id+1])
            return true;
        for (int i = 2; i < checked_state.length; i += 2){
            if (checked_state[chip_id] == checked_state[i])
                return false;
        }
        return true;
    }

    private static List<Integer> find_destination_floors(int current_floor){
        List<Integer> destination_floors = new ArrayList<>();
        if (current_floor == 1){
            destination_floors.add(2);
            return destination_floors;
        }
        else if (current_floor == 4){
            destination_floors.add(3);
            return destination_floors;
        }
        else{
            destination_floors.add(current_floor + 1);
            destination_floors.add(current_floor - 1);
            return destination_floors;
        }
    }

    private static List<List<Integer>> generate_possible_couples(List<Integer> available_elements){
        List<List<Integer>> possible_couples = new ArrayList<>();
        for (int i = 0; i < available_elements.size(); i += 1){
            for (int j = i +1; j < available_elements.size(); j += 1){
                List<Integer> generated_combination = new ArrayList<>();
                generated_combination.add(available_elements.get(i));
                generated_combination.add(available_elements.get(j));
                possible_couples.add(generated_combination);
            }
        }
        return possible_couples;
    }

    private static boolean is_final_solution(int[] checked_solution){
        for (int x: checked_solution){
            if (x != 4)
                return false;
        }
        return true;
    }

    private static List<Integer> find_suitable_elements(int[] checked_possible_state){
        List<Integer> available_elements = new ArrayList<>();
        for (int i = 1; i < checked_possible_state.length; i+=1){
            if (checked_possible_state[0] == checked_possible_state[i])
                available_elements.add(i);
        }
        return available_elements;
    }
}
