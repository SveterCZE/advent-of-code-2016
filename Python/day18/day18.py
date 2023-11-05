def get_instructions():
    f = open("input.txt","r")
    for line in f:
        temp_line = list(line.strip())
        temp_line.append(".")
        temp_line.insert(0, ".")
        return temp_line

def part1(instructions, line_count):
    line_db = []
    line_db.append(instructions)
    while True:
        new_temp_line = []
        new_temp_line.append(".")
        for i in range (1, len(line_db[0] )- 1):
            if is_tile_trap(i, line_db[-1]):
                new_temp_line.append("^")
            else:
                new_temp_line.append(".")
        new_temp_line.append(".")
        line_db.append(new_temp_line)
        if len(line_db) == line_count:
            break
    return count_safe_tiles(line_db)

def is_tile_trap(pointer, previous_line):
    if (previous_line[pointer - 1] == "^" and  previous_line[pointer] == "^" and previous_line[pointer + 1] == "."):
        return True
    elif (previous_line[pointer - 1] == "." and  previous_line[pointer] == "^" and previous_line[pointer + 1] == "^"):
        return True
    elif (previous_line[pointer - 1] == "^" and  previous_line[pointer] == "." and previous_line[pointer + 1] == "."):
        return True
    elif (previous_line[pointer - 1] == "." and  previous_line[pointer] == "." and previous_line[pointer + 1] == "^"):
        return True
    else:
        return False

def count_safe_tiles(line_db):
    counter = 0
    for checked_line in line_db:
        for i in range (1, len(checked_line) - 1):
            if checked_line[i] == ".":
                counter += 1
    return counter

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions, 40))
    print(part1(instructions, 400000))