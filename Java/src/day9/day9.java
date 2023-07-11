package day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day9 {
    public static void main (String[] args) throws IOException{
        char[] encrypted_message = get_input();
        System.out.println(part1(encrypted_message));
        System.out.println(part2(encrypted_message));
    }

    private static char[] get_input() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day9/input.txt"));
        return lines.get(0).toCharArray();
    }

    private static int part1(char[] encrypted_message){
        int pointer = 0;
        int start;
        int end;
        StringBuilder sb = new StringBuilder();
        while (pointer < encrypted_message.length){
            if (encrypted_message[pointer] == '('){
                start = pointer;
                while (encrypted_message[pointer] != ')')
                    pointer += 1;
                end = pointer;
                int[] decryption_instruction = extract_decrypted_instruction(start, end, encrypted_message);
                for (int i = 0; i < decryption_instruction[1]; i+= 1){
                    for (int j = 0; j < decryption_instruction[0]; j+= 1)
                        sb.append(encrypted_message[pointer+j+1]);
                }
                pointer += decryption_instruction[0];
            }
            else
                sb.append(encrypted_message[pointer]);
            pointer += 1;
        }
        return sb.toString().length();
    }

    private static long part2(char[] encrypted_message) {
        int pointer = 0;
        long decrypted_message_len = 0;
        int start;
        int end;
        while (pointer < encrypted_message.length) {
            if (encrypted_message[pointer] == '(') {
                start = pointer;
                while (encrypted_message[pointer] != ')')
                    pointer += 1;
                end = pointer;
                int[] decryption_instruction = extract_decrypted_instruction(start, end, encrypted_message);
                decrypted_message_len += part2(Arrays.copyOfRange(encrypted_message, end+1, end+1+decryption_instruction[0])) * decryption_instruction[1];
                pointer += decryption_instruction[0];
            } else {
                decrypted_message_len += 1;
            }
            pointer += 1;
        }
        return decrypted_message_len;
    }

    private static int[] extract_decrypted_instruction(int start, int end, char[] encrypted_message){
        int[] decryption_instruction = new int[2];
        Pattern p = Pattern.compile("\\d+");
        String string_instruction = String.valueOf(Arrays.copyOfRange(encrypted_message, start, end));
        Matcher m = p.matcher(string_instruction);
        int counter = 0;
        while(m.find()) {
            decryption_instruction[counter] = Integer.parseInt(m.group());
            counter += 1;
        }
        return decryption_instruction;
    }
}
