def get_instructions():
    f = open("input.txt", "r")
    instructions = []
    for line in f:
        instructions.append(list(line.strip()))
    return instructions

def part1(instructions):
    password = ""
    for i in range(len(instructions[0])):
        frequency_table = get_frequency_table(instructions, i)
        password += find_most_common_letter(frequency_table)
    return password

def part2(instructions):
    password = ""
    for i in range(len(instructions[0])):
        frequency_table = get_frequency_table(instructions, i)
        password += find_least_common_letter(frequency_table)
    return password

def get_frequency_table(instructions, column):
    letter_db = {}
    for i in range(len(instructions)):
        if instructions[i][column] not in letter_db:
            letter_db[instructions[i][column]] = 1
        else:
            letter_db[instructions[i][column]] += 1
    return letter_db

def find_most_common_letter(frequency_table):
    most_frequent_letter = ""
    highest_frequency = 0
    for key, value in frequency_table.items():
        if value > highest_frequency:
            highest_frequency = value
            most_frequent_letter = key
    return most_frequent_letter

def find_least_common_letter(frequency_table):
    least_frequent_letter = ""
    lowest_frequency = 9999999
    for key, value in frequency_table.items():
        if value < lowest_frequency:
            lowest_frequency = value
            least_frequent_letter = key
    return least_frequent_letter

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    print(part2(instructions))