package day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class day7 {
    public static void main (String[] args) throws IOException {
        List<IP_Address> player_instructions = get_input();
        System.out.println(part1(player_instructions));
        System.out.println(part2(player_instructions));
    }

    private static List<IP_Address> get_input() throws IOException {
        List<IP_Address> new_address_list = new ArrayList<>();
        List<String> lines = load_input();
        for (String line: lines){
            String[] split_line = line.split("[\\[\\]]");
            List<String> outer_elements = new ArrayList<>();
            List<String> inner_elements = new ArrayList<>();
            for (int i = 0; i < split_line.length; i += 1){
                if (i%2 == 0)
                    outer_elements.add(split_line[i]);
                else
                    inner_elements.add(split_line[i]);
            }
            IP_Address new_IP_Address = new IP_Address(outer_elements, inner_elements);
            new_address_list.add(new_IP_Address);
        }
        return new_address_list;
    }

    private static int part1(List<IP_Address> player_instructions){
        int valid_address_counter = 0;
        for (IP_Address chcked_address: player_instructions){
            if (chcked_address.is_valid())
                valid_address_counter += 1;
        }
        return valid_address_counter;
    }

    private static int part2(List<IP_Address> player_instructions){
        int secure_address_counter = 0;
        for (IP_Address chcked_address: player_instructions){
            if (chcked_address.is_secure())
                secure_address_counter += 1;
        }
        return secure_address_counter;
    }

    private static List<String> load_input() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day7/input.txt"));
        return lines;
    }

    private static class IP_Address {
        private List<String> outer_elements;
        private List<String> inner_elements;

        private IP_Address (List<String> outer_elements, List<String> inner_elements){
            this.inner_elements = inner_elements;
            this.outer_elements = outer_elements;
        }

        private boolean is_valid (){
            boolean outer_test_pass = false;
            boolean inner_test_pass = true;
            for (String checked_element: this.outer_elements){
                if (contains_ABBA(checked_element))
                    outer_test_pass = true;
            }
            for (String checked_element: this.inner_elements){
                if (contains_ABBA(checked_element))
                    inner_test_pass = false;
            }
            return outer_test_pass && inner_test_pass;
        }

        private boolean is_secure(){
            List<char[]> possible_pair_letters = new ArrayList<>();
            for (String checked_element: this.outer_elements){
                find_possible_triplets(checked_element, possible_pair_letters);
            }
            if (possible_pair_letters.size() == 0)
                return false;
            for (String checked_element: this.inner_elements){
                if(find_SSL_match(checked_element, possible_pair_letters))
                    return true;
            }
            return false;
        }

        private boolean contains_ABBA(String checked_element){
            for (int i = 0; i < checked_element.length() - 3; i+=1){
                if(checked_element.charAt(i)==checked_element.charAt(i+3) && checked_element.charAt(i+1)==checked_element.charAt(i+2) && checked_element.charAt(i)!=checked_element.charAt(i+1))
                    return true;
            }
            return false;
        }

        private void find_possible_triplets(String checked_element, List<char[]> possible_pair_letters){
            for (int i = 0; i < checked_element.length() - 2; i += 1){
                if (checked_element.charAt(i) == checked_element.charAt(i+2) && checked_element.charAt(i) != checked_element.charAt(i+1))
                {
                    possible_pair_letters.add(new char[]{checked_element.charAt(i), checked_element.charAt(i+1)});
                }
            }
        }

        private boolean find_SSL_match(String checked_element, List<char[]> possible_pair_letters){
            for (int i = 0; i < checked_element.length() - 2; i += 1){
                for (char[] checked_pair: possible_pair_letters){
                    if (checked_element.charAt(i+0) == checked_pair[1] && checked_element.charAt(i+2) == checked_pair[1] && checked_element.charAt(i+1) == checked_pair[0])
                        return true;
                }
            }
            return false;
        }
    }
}
