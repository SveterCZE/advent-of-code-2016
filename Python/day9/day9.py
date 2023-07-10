import re

def get_instructions():
    f = open("input.txt", "r")
    for line in f:
        return line.strip()

def part1(instructions):
    pointer = 0
    decrypted_message = ""
    while pointer < len(instructions):
        if instructions[pointer] == "(":
            start = pointer
            while instructions[pointer] != ")":
                pointer += 1
            end = pointer
            decryption_instructions = extract_decryption_instructions(start, end, instructions)
            for i in range(decryption_instructions[1]):
                for j in range(decryption_instructions[0]):
                    decrypted_message += instructions[pointer+j+1]
            pointer += decryption_instructions[0]
        
        else:
            decrypted_message += instructions[pointer]
        pointer += 1
    return(len(decrypted_message))

def part2(instructions):
    pointer = 0
    decrypted_message_len = 0
    while pointer < len(instructions):
        if instructions[pointer] == "(":
            start = pointer
            while instructions[pointer] != ")":
                pointer += 1
            end = pointer
            decryption_instructions = extract_decryption_instructions(start, end, instructions)
            decrypted_message_len += (part2(instructions[end+1:end+1+decryption_instructions[0]]) * decryption_instructions[1])
            pointer += decryption_instructions[0]
        else:
            decrypted_message_len += 1
        pointer += 1
    return decrypted_message_len

def extract_decryption_instructions(start, end, instructions):
    return [int(x) for x in re.findall('\d+', instructions[start:end])] 

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    print(part2(instructions))