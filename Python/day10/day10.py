def get_instructions():
    f = open("input.txt", "r")
    playground = Playground()
    for line in f:
        temp_line = line.strip().split()
        if temp_line[0] == "bot":
            playground.insert_new_bot(Bot(temp_line[1], temp_line[5], temp_line[6], temp_line[10], temp_line[11]))
    f = open("input.txt", "r")
    for line in f:
        temp_line = line.strip().split()
        if temp_line[0] == "value":
            playground.bots_db[temp_line[5]].insert_new_chip(int(temp_line[1]))
    return playground

def part1(instructions):
    instructions.run_distributions()
    return instructions.decisive_bot

def part2(instructions):
    return(instructions.bins_db["0"][0] * instructions.bins_db["1"][0] * instructions.bins_db["2"][0])

class Bot():
    def __init__(self, bot_number, low_targ_class, low_targ_no, high_targ_class, high_targ_no):
        self.bot_number = bot_number
        self.low_targ_class = low_targ_class
        self.low_targ_no = low_targ_no
        self.high_targ_class = high_targ_class
        self.high_targ_no = high_targ_no
        self.chips_held = []
    
    def can_bot_distribute(self):
        if len(self.chips_held) >= 2:
            return True
        else:
            return False
    
    def remove_value(self, high_low_sel):
        if high_low_sel == "H":
            returned_chip_value = self.chips_held.pop(1)
            return ((returned_chip_value, self.high_targ_class, self.high_targ_no))
        elif high_low_sel == "L":
            returned_chip_value = self.chips_held.pop(0)
            return ((returned_chip_value, self.low_targ_class, self.low_targ_no))
    
    def insert_new_chip(self, inserted_chip_value):
        self.chips_held.append(inserted_chip_value)
        self.chips_held.sort()

    def contains_destination_chips(self, chip1, chip2):
        return chip1 in self.chips_held and chip2 in self.chips_held


class Playground():
    def __init__(self):
        self.bots_db = {}
        self.bins_db = {}
        self.ready_bots = []
        self.decisive_bot = False

    def insert_new_bot(self, inserted_bot):
        self.bots_db[inserted_bot.bot_number] = inserted_bot

    def insert_into_bin(self, bin_no, passed_value):
        if bin_no in self.bins_db:
            self.bins_db[bin_no].appennd(passed_value)
        else:
            self.bins_db[bin_no] = []
            self.bins_db[bin_no].append(passed_value)
        return 0
    
    def insert_into_bot(self, bot_no, passed_value):
        self.bots_db[bot_no].insert_new_chip(passed_value)
        if self.bots_db[bot_no].can_bot_distribute() == True:
            self.ready_bots.append(self.bots_db[bot_no])
        if self.bots_db[bot_no].contains_destination_chips(61, 17):
            return bot_no
        return 0
    
    def run_distributions(self):
        for key, value in self.bots_db.items():
            if self.bots_db[key].can_bot_distribute() == True:
                self.ready_bots.append(value)
        
        while True:
            distributing_bot = self.ready_bots.pop()
            high_value = distributing_bot.remove_value("H")
            low_value = distributing_bot.remove_value("L")
            if self.execute_distrbution(high_value) != 0:
                self.decisive_bot = high_value[2]
            if self.execute_distrbution(low_value) != 0:
                self.decisive_bot = low_value[2]
            if len(self.ready_bots) == 0:
                break
    
    def execute_distrbution(self, distribution_instruction):
        if distribution_instruction[1] == "output":
            return self.insert_into_bin(distribution_instruction[2], distribution_instruction[0])
        elif distribution_instruction[1] == "bot":
            return self.insert_into_bot(distribution_instruction[2], distribution_instruction[0])


if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    print(part2(instructions))