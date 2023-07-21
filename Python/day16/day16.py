import copy

def get_instructions():
    f = open("input.txt","r")
    for line in f:
        return [int(x) for x in list(line.strip())]

def part1(instructions, target_disk_size):
    if len(instructions) >= target_disk_size:
        return calculate_checksum(instructions, target_disk_size)
    else:
        b = copy.deepcopy(instructions)
        b.reverse()
        for i in range(len(b)):
            if b[i] == 0:
                b[i] = 1
            else:
                b[i] = 0
        instructions.append(0)
        instructions.extend(b)
        return part1(instructions, target_disk_size)

def calculate_checksum(instructions, target_disk_size):
    relevant_part = instructions[:target_disk_size]
    return checksum_recursive_helper(relevant_part)

def checksum_recursive_helper(relevant_part):
    if (len(relevant_part) % 2) != 0:
        return convert_to_string(relevant_part)
    else:
        checksum_list = []
        for i in range(0, len(relevant_part) - 1, 2):
            if relevant_part[i] == relevant_part[i+1]:
                checksum_list.append(1)
            else:
                checksum_list.append(0)
        return checksum_recursive_helper(checksum_list)

def convert_to_string(relevant_part):
    string_result = ""
    for elem in relevant_part:
        string_result += str(elem)
    return string_result

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions, 272))
    print(part1(instructions, 35651584))