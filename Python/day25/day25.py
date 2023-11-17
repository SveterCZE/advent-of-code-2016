import copy

def get_instructions():
    f = open("input.txt","r")
    list_of_instructions = []
    for line in f:
        list_of_instructions.append(line.strip().split())
    return list_of_instructions

def part1_helper(instructions, starting_value):
    registers = {}
    registers["a"], registers["b"], registers["c"], registers["d"] = starting_value, 0, 0, 0
    pointer = 0
    output = []
    while True:
        checked_instruction = instructions[pointer]
        if checked_instruction[0] == "cpy" and is_valid_instruction(checked_instruction):
            try:
                if checked_instruction[1].strip('-').isnumeric() == True:
                    registers[checked_instruction[2]] = int(checked_instruction[1])
                else:
                    registers[checked_instruction[2]] = registers[checked_instruction[1]]
                pointer += 1
            except:
                pointer += 1
        elif checked_instruction[0] == "inc" and is_valid_instruction(checked_instruction):
            registers[checked_instruction[1]] += 1
            pointer += 1
        elif checked_instruction[0] == "dec" and is_valid_instruction(checked_instruction):
            registers[checked_instruction[1]] -= 1
            pointer += 1
        elif checked_instruction[0] == "jnz" and is_valid_instruction(checked_instruction):
            try:
                if checked_instruction[1].strip('-').isnumeric() == True:
                    if checked_instruction[1] != "0":
                        if checked_instruction[2].strip('-').isnumeric() == True:
                            pointer += int(checked_instruction[2])
                        else:
                            pointer += int(registers[checked_instruction[2]])
                    else:
                        pointer += 1
                else:
                    if registers[checked_instruction[1]] != 0:
                        if checked_instruction[2].strip('-').isnumeric() == True:
                            pointer += int(checked_instruction[2])
                        else:
                            pointer += int(registers[checked_instruction[2]])
                    else:
                        pointer += 1
            except:
                pointer += 1
        elif checked_instruction[0] == "tgl" and is_valid_instruction(checked_instruction):
            if checked_instruction[1].strip('-').isnumeric() == True:
                toggle_pointer = pointer + int(checked_instruction[1])
            else:
                toggle_pointer = pointer + registers[checked_instruction[1]]
            
            
            if toggle_pointer >= 0 and toggle_pointer < len(instructions):
                if len(instructions[toggle_pointer]) == 2:
                    if instructions[toggle_pointer][0] == "inc":
                        instructions[toggle_pointer][0] = "dec"
                    else:
                        instructions[toggle_pointer][0] = "inc"
                if len(instructions[toggle_pointer]) == 3:
                    if instructions[toggle_pointer][0] == "jnz":
                        instructions[toggle_pointer][0] = "cpy"
                    else:
                        instructions[toggle_pointer][0] = "jnz"
            pointer += 1
        
        elif checked_instruction[0] == "out" and is_valid_instruction(checked_instruction):
            if checked_instruction[1].strip('-').isnumeric() == True:
                output.append(int(checked_instruction[1])) 
            else:
                output.append(int(registers[checked_instruction[1]]))
            
            if len(output) % 2 == 0:
                if output[-1] != 1:
                    break
            else:
                if output[-1] != 0:
                    break
            if len(output) > 100:
                return True
            
            pointer += 1
        if pointer >= len(instructions):
            return False
    return False

def is_valid_instruction(checked_instruction):
    if checked_instruction[0] == "cpy":
        if len(checked_instruction) != 3:
            return False
        if checked_instruction[2].isalpha() == False:
            return False
        return True

    if checked_instruction[0] == "inc" or checked_instruction[0] == "dec":
        if len(checked_instruction) != 2:
            return False
        if checked_instruction[1].isalpha() == False:
            return False
        return True
    
    if checked_instruction[0] == "jnz":
        if len(checked_instruction) != 3:
            return False
        return True

    if checked_instruction[0] == "tgl" or checked_instruction[0] == "out":
        if len(checked_instruction) != 2:
            return False
        return True

def part1(insructions):
    counter = 0
    while True:
        if part1_helper(copy.deepcopy(insructions), counter) == True:
            return counter
        counter += 1

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))