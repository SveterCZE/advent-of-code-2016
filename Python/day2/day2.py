def get_instructions():
    f = open("input.txt", "r")
    instructions = []
    for line in f:
        instructions.append(list(line.strip()))
    return instructions

def part1(instructions):
    numpad = [["1", "2", "3"], ["4", "5" , "6"], ["7", "8", "9"]]
    coordinates = [1,1]
    recorded_code = ""
    for elem in instructions:
        recorded_code += make_movements(elem, numpad, coordinates, len(numpad) - 1)
    return recorded_code
    

def part2(instructions):
    numpad = [["x", "x", "1", "x", "x"], ["x", "2", "3", "4", "x"], ["5", "6", "7", "8", "9"], ["x", "A", "B", "C", "x"], ["x", "x", "D", "x", "x"]]
    coordinates = [3,0]
    recorded_code = ""
    for elem in instructions:
        recorded_code += make_movements(elem, numpad, coordinates, len(numpad) - 1)
    return recorded_code

def make_movements(list_of_instructions, numpad, coordinates, max_len):
    for movement in list_of_instructions:
        proposed_new_coordinate = change_coordinate(coordinates, movement)
        if is_valid_coordinate(proposed_new_coordinate, max_len, numpad):
            coordinates[0] = proposed_new_coordinate[0]
            coordinates[1] = proposed_new_coordinate[1]
        else:
            pass
    return numpad[coordinates[0]][coordinates[1]]

def change_coordinate(coordinates, movement):
    proposed_new_coordinate = [coordinates[0], coordinates[1]]
    if movement == "U":
        proposed_new_coordinate[0] -= 1
    elif movement == "D":
        proposed_new_coordinate[0] += 1
    elif movement == "R":
        proposed_new_coordinate[1] += 1
    elif movement == "L":
        proposed_new_coordinate[1] -= 1
    return proposed_new_coordinate

def is_valid_coordinate(proposed_new_coordinate, max_len, numpad):
    if proposed_new_coordinate[0] >= 0 and proposed_new_coordinate[0] <= max_len and proposed_new_coordinate[1] >= 0 and proposed_new_coordinate[1] <= max_len and numpad[proposed_new_coordinate[0]][proposed_new_coordinate[1]] != "x":
        return True
    else:
        return False

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    print(part2(instructions))