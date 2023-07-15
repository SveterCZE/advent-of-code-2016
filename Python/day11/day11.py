from itertools import combinations
import copy

def get_instructions():
    f = open("input.txt", "r")
    split_lines = []
    elements = []
    for line in f:
        split_line = line.strip().split()
        split_lines.append(split_line)
        for i in range(len(split_line)):
            if "generator" in split_line[i]:
                elements.append(split_line[i-1])
    elements.sort()
    positions = []
    for i in range((len(elements) * 2) + 1):
        positions.append(0)
    positions[0] = 1
    for i in range(len(split_lines)):
        for j in range(len(elements)):
            if elements[j] in split_lines[i]:
                positions[(j * 2) + 2] = i + 1
            if elements[j] + "-compatible" in split_lines[i]:
                positions[(j * 2) + 1] = i + 1
    return positions

def part1(instructions):
    positions_db = set()
    positions_db.add("".join(str(e) for e in instructions))
    list_of_instructions = []
    list_of_instructions.append(instructions)
    final_step_count = make_individual_movements(list_of_instructions, positions_db, 0)
    return final_step_count

def make_individual_movements(list_of_instructions, positions_db, step_count):
    # BASE CASE --- Any solution is final
    for checked_solution in list_of_instructions:
        if is_final_solution(checked_solution):
            return step_count

    # RECURSIVE CASE --- Check each element and generate possible new states
    valid_states = []
    for checked_possible_state in list_of_instructions:
        # Determine positions of suitable elements for movemenet
        available_elemens = find_suitable_elements(checked_possible_state)
        couples = list(combinations(available_elemens, 2))
        for elem in available_elemens:
            couples.append([elem])
        # Determine possible destination floors
        destination_floors = find_destination_floors(checked_possible_state[0])
        
        # Generate possible new states
        # iterate over floors, then over possible element movements
        possible_new_states = []
        for destination_floor in destination_floors:
            for moved_couple in couples:
                temp_new_state = copy.deepcopy(checked_possible_state)
                temp_new_state[0] = destination_floor
                for elem in moved_couple:
                    temp_new_state[elem] = destination_floor
                possible_new_states.append(temp_new_state)
        # Detemine_valid_states
        valid_states = generate_valid_states(possible_new_states, positions_db, valid_states)
    return make_individual_movements(valid_states, positions_db, step_count + 1)

def part2(instructions):
    positions_db = set()
    positions_db.add("".join(str(e) for e in instructions))
    list_of_instructions = []
    list_of_instructions.append(instructions)
    final_step_count = make_individual_movements(list_of_instructions, positions_db, 0)
    return final_step_count


def find_suitable_elements(instructions):
    available_elements = []
    for i in range(1, len(instructions)):
        if instructions[0] == instructions[i]:
            available_elements.append(i)
    return available_elements

def find_destination_floors(elevator_position):
    destination_floors = []
    if elevator_position == 1:
        destination_floors.append(2)
        return destination_floors
    elif elevator_position == 4:
        destination_floors.append(3)
        return destination_floors
    else:
        destination_floors.append(elevator_position + 1)
        destination_floors.append(elevator_position - 1)
        return destination_floors

def generate_valid_states(possible_new_states, positions_db, valid_states):
    for checked_state in possible_new_states:
        # Check that the state had not been visited
        checked_state_hash = "".join(str(e) for e in checked_state)
        if checked_state_hash not in positions_db:
            if is_valid_state(checked_state):
                valid_states.append(checked_state)
                positions_db.add(checked_state_hash)
    return valid_states

def is_valid_state(checked_state):
    for chip_id in range(1,len(checked_state), 2):
        if is_chip_safe(checked_state, chip_id) == False:
            return False
    return True

def is_chip_safe(checked_state, chip_id):
    # If the chip is connected to its PSU, it is safe
    if checked_state[chip_id] == checked_state[chip_id+1]:
        return True
    # If not and its on the floor with other PSU, return false
    for i in range(2, len(checked_state), 2):
        if checked_state[chip_id] == checked_state[i]:
            return False
    # Return true if passed
    return True

def is_final_solution(checked_solution):
    for elem in checked_solution:
        if elem != 4:
            return False
    return True

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    instructions = get_instructions()
    instructions.append(1)
    instructions.append(1)
    instructions.append(1)
    instructions.append(1)
    print(part2(instructions))