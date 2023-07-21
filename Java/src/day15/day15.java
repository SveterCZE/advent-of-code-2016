package day15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day15 {
    public static void main (String[] args) throws IOException {
        List<List<Integer>> encrypted_message = get_input();
        System.out.println(part1(encrypted_message));
        encrypted_message.add(new ArrayList<>(){{add(7); add(11); add(0); add(0);}});
        System.out.println(part1(encrypted_message));
    }

    private static List<List<Integer>> get_input() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day15/input.txt"));
        List<List<Integer>> instructions = new ArrayList<>();
        for (String checked_line: lines){
            List<Integer> temp_list = new ArrayList<>();
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(checked_line);
            while(m.find()) {
                temp_list.add(Integer.parseInt(m.group()));
            }
            instructions.add(temp_list);
        }
        return instructions;
    }

    private static int part1(List<List<Integer>> instructions){
        int counter = 0;
        int[] positions = new int[instructions.size()];
        Map<Integer, Integer> disk_db = new HashMap<>();
        for (int i = 0; i < instructions.size(); i += 1){
            positions[i] = instructions.get(i).get(3);
            disk_db.put(instructions.get(i).get(0), instructions.get(i).get(1));
        }
        for (int i = 0; i < positions.length; i += 1){
            positions[i] += i+1;
            positions[i] = positions[i] % disk_db.get(i+1);
        }
        while (true){
            if (are_disks_aligned(positions))
                return counter;
            for (int i = 0; i < positions.length; i += 1){
                positions[i] += 1;
                positions[i] = positions[i] % disk_db.get(i+1);
            }
            counter += 1;
        }
    }

    private static boolean are_disks_aligned(int[] positions){
        for (int position : positions) {
            if (position != 0)
                return false;
        }
        return true;
    }
}
