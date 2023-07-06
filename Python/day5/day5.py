import hashlib

def get_instructions():
    f = open("input.txt", "r")
    for line in f:
        return line.strip()

def part1(instructions):
    discovered_code = ""
    index = 0
    while len(discovered_code) < 8:
        generated_input = instructions + str(index)
        result = hashlib.md5(generated_input.encode()).hexdigest()
        if is_valid(result):
            discovered_code += result[5]
        index += 1
    return discovered_code

def part2(instructions):
    discovered_code = [None for x in range(8)]
    index = 0
    while True:
        generated_input = instructions + str(index)
        result = hashlib.md5(generated_input.encode()).hexdigest()
        if is_valid(result):
            try:
                if discovered_code[int(result[5])] == None:
                    discovered_code[int(result[5])] = result[6]
            except:
                pass
            if None not in discovered_code:
                break
        index += 1
    return "".join([x for x in discovered_code])

def is_valid(checked_hash):
    for elem in checked_hash[:5]:
        if elem != "0":
            return False
    return True

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    print(part2(instructions))