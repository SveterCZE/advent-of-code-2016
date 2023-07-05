def get_instructions():
    f = open("input.txt", "r")
    instructions = []
    for line in f:
        temp_line = [int(x) for x in line.strip().split()]
        instructions.append(temp_line)
    return instructions

def part1(instructions):
    valid_triangle_count = 0
    for elem in instructions:
        elem.sort()
        if check_valid_triange(elem):
            valid_triangle_count += 1
    return valid_triangle_count

def part2(instructions):
    valid_triangle_count = 0
    for i in range(0, len(instructions), 3):
        for j in range(3):
            triangle_arms = [instructions[i][j], instructions[i+1][j], instructions[i+2][j]]
            triangle_arms.sort()
            if check_valid_triange(triangle_arms):
                valid_triangle_count += 1
    return valid_triangle_count

def check_valid_triange(triangle_arms):
    if triangle_arms[0] + triangle_arms[1] > triangle_arms[2]:
        return True
    else:
        return False

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    instructions = get_instructions()
    print(part2(instructions))