import re

def get_instructions():
    f = open("input.txt", "r")
    instructions = []
    for line in f:
        temp_line = line.strip().split("-")
        room_num = re.findall(r'\d+', temp_line[-1])
        checksum = re.findall(r'[a-zA-Z]+', temp_line[-1])
        instructions.append([temp_line[:-1], int(room_num[0]), checksum[0]])
    return instructions

def part1(instructions):
    sector_ids_sum = 0
    for elem in instructions:
        discovered_code = ""
        letter_counts = count_letters(elem[0])
        while len(discovered_code) < 5:
            most_frequent = find_most_frequent_letters(letter_counts)
            most_frequent.sort()
            for letter in most_frequent:
                discovered_code += letter
        discovered_code = discovered_code[:5]
        if discovered_code == elem[2]:
            sector_ids_sum += elem[1]
    return sector_ids_sum

def part2(instructions):
    for elem in instructions:
        step_count = elem[1]
        for i in range(len(elem[0])):
            elem[0][i] = shift_word(elem[0][i], step_count)
    for elem in instructions:
        if "northpole" in elem[0]:
            return elem[1]


def count_letters(list_of_chars):
    counter = {}
    combined_string = ""
    for elem in list_of_chars:
        combined_string += elem
    combined_list = list(combined_string)
    for elem in combined_list:
        if elem in counter:
            counter[elem] += 1
        else:
            counter[elem] = 1
    return counter

def find_most_frequent_letters(letter_counts):
    matches = []
    best_score = 0
    for key, value in letter_counts.items():
        if value > best_score:
            best_score = value
    for key, value in letter_counts.items():
        if value == best_score:
            matches.append(key)
    for match in matches:
        letter_counts.pop(match)
    return matches

def shift_word(word, step_count):
    return ''.join(chr((ord(char) - 97 + step_count) % 26 + 97) for char in word)

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    print(part2(instructions))