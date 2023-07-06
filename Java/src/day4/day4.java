package day4;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day4 {
    public static void main (String[] args) throws IOException {
        List<Hotel_Room> player_instructions = get_input();
        System.out.println(part1(player_instructions));
        System.out.println(part2(player_instructions));
    }

    private static List<Hotel_Room> get_input() throws IOException {
        List<Hotel_Room> player_instructions = new ArrayList<>();
        List<String> raw_input = ReadInput();
        for (String checked_room: raw_input) {
            String[] temp_list = checked_room.split("-");
            String last_element = "";
            List<String> room_name = new ArrayList<>();
            for (int i = 0; i < temp_list.length; i += 1){
                if (i == temp_list.length - 1)
                    last_element = temp_list[i];
                else
                    room_name.add(temp_list[i]);
            }

            StringBuilder sb = new StringBuilder();
            Pattern p = Pattern.compile("[a-zA-Z]+");
            Matcher m = p.matcher(last_element);
            while(m.find()) {
                sb.append(m.group());
            }
            String checksum = sb.toString();

            sb = new StringBuilder();
            p = Pattern.compile("\\d+");
            m = p.matcher(last_element);
            while(m.find()) {
                sb.append(m.group());
            }
            Integer sector_id = Integer.parseInt(sb.toString());

            Hotel_Room new_room = new Hotel_Room(room_name, sector_id, checksum);
            player_instructions.add(new_room);
        }

        return player_instructions;
    }

    private static List<String> ReadInput() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day4/input.txt"));
        return lines;
    }

    private static int part1(List<Hotel_Room> player_instructions){
        int valid_rooms_sector_ids = 0;
        for (Hotel_Room checked_room: player_instructions){
            if (checked_room.is_valid())
                valid_rooms_sector_ids += checked_room.get_sector_id();
        }
        return valid_rooms_sector_ids;
    }

    private static int part2(List<Hotel_Room> player_instructions){
        for (Hotel_Room checked_room: player_instructions){
            List<String> decrypted_name = checked_room.decrypt_room();
            if (decrypted_name.contains("northpole"))
                return checked_room.get_sector_id();
        }
        return 0;
    }

    private static class Hotel_Room{
        private List<String> room_name;
        private int sector_id;
        private String checksum;

        private Hotel_Room(List<String> room_name, int sector_id, String checksum){
            this.room_name = room_name;
            this.sector_id = sector_id;
            this.checksum = checksum;
        }

        private boolean is_valid(){
            Map<Character, Integer> letter_count = get_letter_count();
            String generated_checksum = generate_checksum(letter_count);
            return this.checksum.equals(generated_checksum);
        }

        private Map<Character, Integer> get_letter_count(){
            Map<Character, Integer> letter_count = new HashMap<>();
            for (String checked_name_part: this.room_name){
                for (Character checked_letter: checked_name_part.toCharArray()){
                    if (letter_count.containsKey(checked_letter))
                        letter_count.replace(checked_letter, letter_count.get(checked_letter) + 1);
                    else
                        letter_count.put(checked_letter, 1);
                }
            }
            return letter_count;
        }

        private String generate_checksum(Map<Character, Integer> letter_count){
            List<Character> checksum_list = new ArrayList<>();
            while (checksum_list.size() < 5) {
                List<Character> temp_list = new ArrayList<>();
                Integer most_frequent = 0;
                for (Map.Entry<Character,Integer> entry : letter_count.entrySet()) {
                    if (entry.getValue() > most_frequent)
                        most_frequent = entry.getValue();
                }
                for (Map.Entry<Character,Integer> entry : letter_count.entrySet()) {
                    if (entry.getValue() == most_frequent)
                        temp_list.add(entry.getKey());
                }
                for (Character inserted_character: temp_list) {
                    letter_count.remove(inserted_character);
                }
                Collections.sort(temp_list);
                for (Character sorted_character: temp_list) {
                    checksum_list.add(sorted_character);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 5; i += 1) {
                sb.append(checksum_list.get(i));
            }
            return sb.toString();
        }

        private List<String> decrypt_room(){
            List<String> decrypted_name = new ArrayList<>();
            for (String encrypted_part: this.room_name) {
                char[] converted_array = encrypted_part.toCharArray();
                for (int i=0; i < converted_array.length; i+=1){
                    int converted_letter = ((((int) converted_array[i] - 97) + this.get_sector_id()) % 26) + 97;
                    converted_array[i] = (char) converted_letter;
                }
                decrypted_name.add(String.valueOf(converted_array));
            }
            return decrypted_name;
        }

        private int get_sector_id(){
            return this.sector_id;
        }
    }
}
