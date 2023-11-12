from itertools import permutations

def get_instructions():
    f = open("input.txt","r")
    instructions = []
    for line in f:
        temp_line = line.strip().split()
        if temp_line[0] == "swap" and temp_line[1] == "position":
            instructions.append(("SP", (int(temp_line[2]), int(temp_line[5]))))
        elif temp_line[0] == "swap" and temp_line[1] == "letter":
            instructions.append(("SL", (temp_line[2], temp_line[5])))
        elif temp_line[0] == "rotate" and temp_line[1] == "left":
            instructions.append(("RL", int(temp_line[2])))
        elif temp_line[0] == "rotate" and temp_line[1] == "right":
            instructions.append(("RR", int(temp_line[2])))
        elif temp_line[0] == "rotate" and temp_line[1] == "based":
            instructions.append(("RB", temp_line[6]))
        elif temp_line[0] == "reverse":
            instructions.append(("REV", (int(temp_line[2]), int(temp_line[4]))))
        elif temp_line[0] == "move":
            instructions.append(("MOV", (int(temp_line[2]), int(temp_line[5]))))
    return instructions

def part1(instructions, initial_word):
    for checked_instruction in instructions:
        if checked_instruction[0] == "SP":
            swap_positions(initial_word, checked_instruction[1])
        elif checked_instruction[0] == "SL":
            swap_letters(initial_word, checked_instruction[1])
        elif checked_instruction[0] == "REV":
            reverse_positions(initial_word, checked_instruction[1])
        elif checked_instruction[0] == "RL":
            rotate_left(initial_word, checked_instruction[1])
        elif checked_instruction[0] == "RR":
            rotate_right(initial_word, checked_instruction[1])
        elif checked_instruction[0] == "MOV":
            move_items(initial_word, checked_instruction[1])
        elif checked_instruction[0] == "RB":
            rotate_based(initial_word, checked_instruction[1])
    return "".join(initial_word)

def swap_positions(initial_word, instruction):
    temp_value = initial_word[instruction[0]]
    initial_word[instruction[0]] = initial_word[instruction[1]]
    initial_word[instruction[1]] = temp_value

def swap_letters(initial_word, instruction):
    value1 = initial_word.index(instruction[0])
    value2 = initial_word.index(instruction[1])
    swap_positions(initial_word, (value1, value2))

def reverse_positions(initial_word, instruction):
    temp_list = initial_word[instruction[0]:instruction[1] + 1]
    temp_list.reverse()
    for i in range(len(temp_list)):
        initial_word[i + instruction[0]] = temp_list[i]

def rotate_left(initial_word, instruction):
    for i in range(instruction):
        popped = initial_word.pop(0)
        initial_word.append(popped)

def rotate_right(initial_word, instruction):
    for i in range(instruction):
        popped = initial_word.pop()
        initial_word.insert(0, popped)

def move_items(initial_word, instruction):
    popped = initial_word.pop(instruction[0])
    initial_word.insert(instruction[1], popped)

def rotate_based(initial_word, instruction):
    index_position = initial_word.index(instruction)
    movement_count = index_position + 1
    if index_position >= 4:
        movement_count += 1
    rotate_right(initial_word, movement_count)

def part2(instructions):
    target = "fbgdceah"
    target_password = list(target)
    target_password.sort()
    possible_initial_passwords = permutations(target_password)
    for elem in possible_initial_passwords:
        outcome = part1(instructions, list(elem))
        if outcome == target:
            return "".join(elem)

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions, list("abcdefgh")))
    print(part2(instructions))