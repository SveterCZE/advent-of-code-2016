def get_instructions():
    f = open("input.txt","r")
    list_of_instructions = []
    for line in f:
        list_of_instructions.append(line.strip().split())
    return list_of_instructions

def part1(instructions, c_value):
    registers = {}
    registers["a"], registers["b"], registers["c"], registers["d"] = 0, 0, c_value, 0
    pointer = 0
    while True:
        checked_instruction = instructions[pointer]
        if checked_instruction[0] == "cpy":
            if checked_instruction[1].isnumeric() == True:
                registers[checked_instruction[2]] = int(checked_instruction[1])
            else:
                registers[checked_instruction[2]] = registers[checked_instruction[1]]
            pointer += 1
        elif checked_instruction[0] == "inc":
            registers[checked_instruction[1]] += 1
            pointer += 1
        elif checked_instruction[0] == "dec":
            registers[checked_instruction[1]] -= 1
            pointer += 1
        elif checked_instruction[0] == "jnz":
            if checked_instruction[1].isnumeric() == True:
                if checked_instruction[1] != 0:
                    pointer += int(checked_instruction[2])
                else:
                    pointer += 1
            else:
                if registers[checked_instruction[1]] != 0:
                    pointer += int(checked_instruction[2])
                else:
                    pointer += 1
        if pointer >= len(instructions):
            break
    return registers["a"]

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions, 0))
    print(part1(instructions, 1))