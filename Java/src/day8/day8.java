package day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class day8 {
    public static void main (String[] args) throws IOException {
        List<Screen_Instruction> player_instructions = get_input();
        Integer[][] resulting_display = part1(player_instructions);
        part2(resulting_display);
    }

    private static List<Screen_Instruction> get_input() throws IOException {
        List<Screen_Instruction> player_instructions = new ArrayList<>();
        List<String> raw_input = load_input();
        for (String checked_instruction: raw_input){
            player_instructions.add(parse_instruction(checked_instruction));
        }
        return player_instructions;
    }

    private static List<String> load_input() throws IOException {
        return Files.readAllLines(Paths.get("src/day8/input.txt"));
    }

    private static Integer[][] part1(List<Screen_Instruction> player_instructions){
        Integer[][] screen = generate_screen();
        for (Screen_Instruction checked_instruction: player_instructions){
            if (checked_instruction.get_instruction_type().equals("rect")){
                create_rectangle(screen, checked_instruction);
            }
            else{
                make_movements(screen, checked_instruction);
            }
        }
        System.out.println(count_lit_pixels(screen));
        return screen;
    }

    private static void part2(Integer[][] resulting_display){
        for (int i = 0; i < 6; i += 1){
            for (int j = 0; j < 50; j += 1){
                if (resulting_display[i][j] == 1)
                    System.out.print("█");
                else
                    System.out.print(" ");
            }
            System.out.println("");
        }
    }

    private static void create_rectangle(Integer[][] screen, Screen_Instruction checked_instruction){
        for (int i = 0; i < checked_instruction.get_offset(); i += 1){
            for (int j = 0; j < checked_instruction.get_row_column(); j += 1) {
                screen[i][j] = 1;
            }
        }
    }

    private static void make_movements(Integer[][] screen, Screen_Instruction checked_instruction){
        if (checked_instruction.get_instruction_type().equals("column"))
            rotate_column(screen, checked_instruction);
        else if (checked_instruction.get_instruction_type().equals("row"))
            rotate_row(screen, checked_instruction);
    }

    private static void rotate_column(Integer[][] screen, Screen_Instruction checked_instruction) {
        Integer[] temp_column = new Integer[6];
        for (int i = 0; i < 6; i += 1){
            temp_column[i] = 0;
        }
        for (int i = 0; i < 6; i += 1){
            temp_column[(i+ checked_instruction.get_offset()) % 6] = screen[i][checked_instruction.get_row_column()];
        }
        for (int i = 0; i < 6; i += 1){
            screen[i][checked_instruction.get_row_column()] = temp_column[i];
        }
    }

    private static void rotate_row(Integer[][] screen, Screen_Instruction checked_instruction) {
        Integer[] temp_row = new Integer[50];
        for (int i = 0; i < 50; i += 1){
            temp_row[i] = 0;
        }
        for (int i = 0; i < 50; i += 1){
            temp_row[(i+ checked_instruction.get_offset()) % 50] = screen[checked_instruction.get_row_column()][i];
        }
        for (int i = 0; i < 50; i += 1){
            screen[checked_instruction.get_row_column()][i] = temp_row[i];
        }
    }

    private static int count_lit_pixels(Integer[][]  screen){
        int counter = 0;
        for (int i = 0; i < 6; i += 1){
            for (int j = 0; j < 50; j += 1){
                if (screen[i][j] == 1)
                    counter += 1;
            }
        }
        return counter;
    }

    private static Integer[][] generate_screen(){
        Integer[][] screen = new Integer[6][50];
        for (int i = 0; i < 6; i += 1){
            for (int j = 0; j < 50; j += 1){
                screen[i][j] = 0;
            }
        }
        return screen;
    }

    private static Screen_Instruction parse_instruction(String checked_instruction){
        List<String> compiled_input = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        Pattern p = Pattern.compile("row|column|rect|\\d+");
        Matcher m = p.matcher(checked_instruction);
        while(m.find()) {
            sb.append(m.group());
            compiled_input.add(sb.toString());
            sb = new StringBuilder();
        }
        return new Screen_Instruction(compiled_input.get(0), Integer.parseInt(compiled_input.get(1)), Integer.parseInt(compiled_input.get(2)));
    }

    private static class Screen_Instruction {
        String instruction_type;
        int row_column;
        int offset;

        public Screen_Instruction(String instruction_type, int row_column, int offset){
            this.instruction_type = instruction_type;
            this.row_column = row_column;
            this.offset = offset;
        }

        private String get_instruction_type(){
            return this.instruction_type;
        }

        private int get_row_column(){
            return this.row_column;
        }

        private int get_offset(){
            return this.offset;
        }
    }
}
