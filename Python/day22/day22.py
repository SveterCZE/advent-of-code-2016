import re

def get_instructions():
    f = open("input.txt","r")
    list_of_nodes = []
    for line in f:
        parsed_data = re.findall(r'\d+', line)
        if len(parsed_data) == 6:
            list_of_nodes.append([(int(parsed_data[0]), int(parsed_data[1])), int(parsed_data[2]), int(parsed_data[3]), int(parsed_data[4]), int(parsed_data[5])])
    return list_of_nodes

def part1(instructions):
    valid_pairs = set()
    for i in range(len(instructions)):
        for j in range(len(instructions)):
            if i != j:
                if are_valid_couples(instructions[i], instructions[j]) == True:
                    valid_pairs.add((min(i,j), max(i,j)))
    return len(valid_pairs)

def are_valid_couples(sector1, sector2):
    if sector1[2] != 0 and sector1[2] <= sector2[3]:
        return True
    else:
        return False

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    # print(part2(instructions))