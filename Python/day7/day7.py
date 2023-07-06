import re

def get_instructions():
    f = open("input.txt", "r")
    instructions = []
    for line in f:
        database = {}
        database["outer"] = []
        database["inner"] = []
        temp_line = re.split('[\[,\]]', line.strip())
        for i in range(len(temp_line)):
            if i % 2 == 0:
                database["outer"].append(temp_line[i])
            else:
                database["inner"].append(temp_line[i])
        instructions.append(database)
    return instructions

def part1(instructions):
    valid_address_counter = 0
    for checked_address in instructions:
        if is_valid_address(checked_address):
            valid_address_counter += 1
    return valid_address_counter

def part2(instructions):
    valid_address_counter = 0
    for checked_address in instructions:
        if is_secure_address(checked_address):
            valid_address_counter += 1
    return valid_address_counter

def is_valid_address(checked_address):
    outer_test_pass = False
    inner_test_pass = True
    for elem in checked_address["outer"]:
        if contains_ABBA(elem):
            outer_test_pass = True
    for elem in checked_address["inner"]:
        if contains_ABBA(elem):
            outer_test_pass = False
    return outer_test_pass and inner_test_pass

def is_secure_address(checked_address):
    possible_pair_letters = []
    for elem in checked_address["outer"]:
        find_possible_triplets(elem, possible_pair_letters)
    if len(possible_pair_letters) == 0:
        return False
    for elem in checked_address["inner"]:
        if find_SSL_match(elem, possible_pair_letters):
            return True
    return False

def contains_ABBA(checked_sequence):
    for i in range(len(checked_sequence) - 3):
        if checked_sequence[i] == checked_sequence[i+3] and checked_sequence[i+1] == checked_sequence[i+2] and checked_sequence[i] != checked_sequence[i+1]:
            return True
    return False

def find_possible_triplets(checked_sequence, possible_pair_letters):
    for i in range(len(checked_sequence) - 2):
        if checked_sequence[i] == checked_sequence[i+2] and checked_sequence[i] != checked_sequence[i+1]:
            possible_pair_letters.append((checked_sequence[i], checked_sequence[i+1]))

def find_SSL_match(checked_sequence, possible_pair_letters):
    for i in range(len(checked_sequence) - 2):
        for checked_pair in possible_pair_letters:
            if checked_sequence[i+0] == checked_pair[1] and checked_sequence[i+2] == checked_pair[1] and checked_sequence[i+1] == checked_pair[0]:
                return True

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    print(part2(instructions))