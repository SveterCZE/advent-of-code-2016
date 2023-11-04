import hashlib
import copy

def get_instructions():
    f = open("input.txt","r")
    for line in f:
        return line.strip()

def part1(instructions):
    coordinate = (0,0)
    steps_taken=""
    positions_DB = [(coordinate, steps_taken)]
    return recursive_helper(instructions, positions_DB)

def part2(instructions):
    coordinate = (0,0)
    steps_taken=""
    positions_DB = [(coordinate, steps_taken)]
    valid_journeys = []
    return recursive_helper_p2(instructions, positions_DB, valid_journeys)

def recursive_helper_p2(instructions, positions_DB, valid_journeys):
    # BASE CASE --- No further steps possible
    if len(positions_DB) == 0:
        return len(valid_journeys[-1])
    else:
        new_moves_DB = []
        for checked_position in positions_DB:
            coordinate = checked_position[0]
            steps_taken = checked_position[1]
            possible_next_steps = generate_possible_next_steps(coordinate)
            hashcode = generate_hashcode(instructions, steps_taken)
            for i in range(len(possible_next_steps)):
                if is_valid_coordinate(possible_next_steps[i]):
                    if is_door_open(hashcode, i):
                        new_coordinate = possible_next_steps[i]
                        new_journey = copy.deepcopy(steps_taken)
                        if i == 0:
                            new_journey += "U"
                        elif i == 1:
                            new_journey += "D"
                        elif i == 2:
                            new_journey += "L"
                        else:
                            new_journey += "R"
                        if new_coordinate == (3,3):
                            valid_journeys.append(new_journey)
                        else:
                            new_moves_DB.append((new_coordinate, new_journey))
        return recursive_helper_p2(instructions, new_moves_DB, valid_journeys)  

def recursive_helper(instructions, positions_DB):
    # BASE CASE --- Target reached
    for checked_position in positions_DB:
        if checked_position[0] == (3,3):
            return checked_position[1]
    # RECURSIVE CASE --- Explore potential alternative ways
    else:
        new_moves_DB = []
        for checked_position in positions_DB:
            coordinate = checked_position[0]
            steps_taken = checked_position[1]
            possible_next_steps = generate_possible_next_steps(coordinate)
            hashcode = generate_hashcode(instructions, steps_taken)
            for i in range(len(possible_next_steps)):
                if is_valid_coordinate(possible_next_steps[i]):
                    if is_door_open(hashcode, i):
                        new_coordinate = possible_next_steps[i]
                        new_journey = copy.deepcopy(steps_taken)
                        if i == 0:
                            new_journey += "U"
                        elif i == 1:
                            new_journey += "D"
                        elif i == 2:
                            new_journey += "L"
                        else:
                            new_journey += "R"
                        new_moves_DB.append((new_coordinate, new_journey))
        return recursive_helper(instructions, new_moves_DB)

def generate_possible_next_steps(coordinate):
    up = (coordinate[0] - 1, coordinate[1])
    down = (coordinate[0] + 1, coordinate[1])
    left = (coordinate[0], coordinate[1] - 1)
    right = (coordinate[0], coordinate[1] + 1)
    return [up, down, left, right]

def is_valid_coordinate(checked_coordinate):
    for elem in checked_coordinate:
        if elem < 0 or elem > 3:
            return False
    return True

def generate_hashcode(instructions, steps_taken):
    combined_string = instructions + steps_taken
    hash = hashlib.md5(combined_string.encode("utf-8")).hexdigest()
    return hash

def is_door_open(hashcode, position):
    if hashcode[position] in "bcdef":
        return True
    else:
        return False

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    print(part2(instructions))