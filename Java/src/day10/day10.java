package day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class day10 {
    public static void main(String[] args) throws IOException {
        Playground instructions = get_input();
        System.out.println(part1(instructions));
        System.out.println(part2(instructions));
    }

    private static Playground get_input() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("src/day10/input.txt"));
        List<String[]> insertion_instructions = new ArrayList<>();
        Playground instructions = new Playground();
        for (String checked_line: lines){
            String[] split_line = checked_line.split(" ");
            if (split_line[0].equals("bot")){
                instructions.insert_new_bot(new Bot(split_line[1], split_line[5], split_line[6], split_line[10], split_line[11]));
            }
            else{
                insertion_instructions.add(split_line);
            }
        }
        for (String[] checked_line: insertion_instructions){
            instructions.bots_db.get(Integer.valueOf(checked_line[5])).insert_new_chip(checked_line[1]);
        }
        return instructions;
    }

    public static int part1(Playground instructions){
        instructions.run_distributions();
        return instructions.get_decisive_bot();
    }

    public static int part2(Playground instructions){
        return instructions.bins_db.get(0).get(0) * instructions.bins_db.get(1).get(0) * instructions.bins_db.get(2).get(0);
    }

    private static class Playground{
        private final Map<Integer, Bot> bots_db;
        private final Map<Integer, List<Integer>> bins_db;
        private final List<Bot> ready_bots;
        private Integer decisive_bot;

        private Playground(){
            this.bots_db = new HashMap<>();
            this.bins_db = new HashMap<>();
            this.ready_bots = new ArrayList<>();
            this.decisive_bot = -1;
        }

        private void insert_new_bot(Bot inserted_bot){
            bots_db.put(inserted_bot.get_bot_no(), inserted_bot);
        }

        private Integer get_decisive_bot(){
            return this.decisive_bot;
        }

        private void run_distributions(){
           for (Map.Entry<Integer,Bot> entry : this.bots_db.entrySet()){
               if (this.bots_db.get(entry.getKey()).can_bot_distribute()){
                   this.ready_bots.add(this.bots_db.get(entry.getKey()));
               }
           }
            do {
                Bot distributing_bot = this.ready_bots.remove(0);
                String[] high_value = distributing_bot.remove_value('H');
                String[] low_value = distributing_bot.remove_value('L');
                if (this.execute_distribution(high_value) != 0)
                    this.decisive_bot = Integer.valueOf(high_value[2]);
                if (this.execute_distribution(low_value) != 0)
                    this.decisive_bot = Integer.valueOf(low_value[2]);
            } while (this.ready_bots.size() != 0);
        }

        private Integer execute_distribution(String[] distribution_instruction){
            if (distribution_instruction[1].equals("output"))
                return this.insert_into_bin(Integer.valueOf(distribution_instruction[2]), Integer.valueOf(distribution_instruction[0]));
            else if (distribution_instruction[1].equals("bot"))
                return this.insert_into_bot(Integer.valueOf(distribution_instruction[2]), Integer.valueOf(distribution_instruction[0]));
            else
                return -1;
        }

        private Integer insert_into_bin(Integer bin_no, Integer passed_value){
            if (!this.bins_db.containsValue(bin_no)) {
                this.bins_db.put(bin_no, new ArrayList<>());
            }
            this.bins_db.get(bin_no).add(passed_value);
            return 0;
        }

        private Integer insert_into_bot(Integer bot_no, Integer passed_value){
            this.bots_db.get(bot_no).insert_new_chip(passed_value.toString());
            if (this.bots_db.get(bot_no).can_bot_distribute())
                this.ready_bots.add(this.bots_db.get(bot_no));
            if (this.bots_db.get(bot_no).contains_destination_chips(61, 17))
                return bot_no;
            return 0;
        }

    }

    private static class Bot{
        private final Integer bot_number;
        private final String low_targ_class;
        private final Integer low_targ_no;
        private final String high_targ_class;
        private final Integer high_targ_no;
        private final List<Integer> chips_held;

        private Bot(String bot_number, String low_targ_class, String low_targ_no, String high_targ_class, String high_targ_no){
            this.bot_number = Integer.valueOf(bot_number);
            this.low_targ_class = low_targ_class;
            this.low_targ_no = Integer.valueOf(low_targ_no);
            this.high_targ_class = high_targ_class;
            this.high_targ_no = Integer.valueOf(high_targ_no);
            this.chips_held = new ArrayList<>();
        }

        private Integer get_bot_no(){
            return this.bot_number;
        }

        private void insert_new_chip(String inserted_chip_value){
            this.chips_held.add(Integer.valueOf(inserted_chip_value));
            Collections.sort(chips_held);
        }

        private boolean can_bot_distribute(){
            return this.chips_held.size() >= 2;
        }

        private String[] remove_value(char high_low_sel){
            if (high_low_sel == 'H'){
                Integer returned_chip_value = this.chips_held.remove(1);
                return new String[]{returned_chip_value.toString(), this.high_targ_class, this.high_targ_no.toString()};
            }
            else if (high_low_sel == 'L'){
                Integer returned_chip_value = this.chips_held.remove(0);
                return new String[]{returned_chip_value.toString(), this.low_targ_class, this.low_targ_no.toString()};
            }
            else
                return new String[]{};
        }

        private boolean contains_destination_chips(Integer chip1, Integer chip2){
            return this.chips_held.contains(chip1) && this.chips_held.contains(chip2);
        }

    }

}
