import re

def get_instructions():
    f = open("input.txt", "r")
    instructions = []
    for line in f:
        movement = re.findall('rect|column|row|\d+', line.strip())
        instructions.append((movement[0], int(movement[1]), int(movement[2])))
    return instructions

def part1(instructions):
    screen = generate_screen()
    for checked_instruction in instructions:
        if checked_instruction[0] == "rect":
            create_rectangle(screen, checked_instruction)
        else:
            make_movements(screen, checked_instruction)
    print(count_lit_pixels(screen))
    return screen 

def part2(screen):
    for i in range(len(screen)):
        for j in range(len(screen[0])):
            if screen[i][j] == 1:
                print("█", end='')
            else:
                print(" ", end='')
        print("")

def generate_screen():
    screen = []
    for i in range(6):
        screen_line = []
        for j in range(50):
            screen_line.append(0)
        screen.append(screen_line)
    return screen

def create_rectangle(screen, checked_instruction):
    for i in range(checked_instruction[2]):
        for j in range(checked_instruction[1]):
            screen[i][j] = 1

def make_movements(screen, checked_instruction):
    if checked_instruction[0] == "column":
        rotate_column(screen, checked_instruction)
    elif checked_instruction[0] == "row":
        rotate_row(screen, checked_instruction)

def rotate_column(screen, checked_instruction):
    temp_column = []
    for i in range(6):
        temp_column.append(0)
    for i in range(6):
        temp_column[(i+checked_instruction[2]) % 6] = screen[i][checked_instruction[1]]
    for i in range(6):
        screen[i][checked_instruction[1]] = temp_column[i]
    
def rotate_row(screen, checked_instruction):
    temp_row = []
    for i in range(50):
        temp_row.append(0)
    for i in range(50):
        temp_row[(i+checked_instruction[2]) % 50] = screen[checked_instruction[1]][i]
    for i in range(50):
        screen[checked_instruction[1]][i] = temp_row[i]

def count_lit_pixels(screen):
    pixels_lit = 0
    for i in range(len(screen)):
        for j in range(len(screen[0])):
            if screen[i][j] == 1:
                pixels_lit += 1
    return pixels_lit

if __name__ == "__main__":
    instructions = get_instructions()
    screen = part1(instructions)
    part2(screen)