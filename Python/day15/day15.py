import re

def get_instructions():
    f = open("input.txt","r")
    initial_positions = []
    disk_db = {}
    for line in f:
        temp_line = re.findall('\d+', line.strip())
        initial_positions.append(int(temp_line[-1]))
        disk_db[int(temp_line[0])] = int(temp_line[1])
    return initial_positions, disk_db

def part1(positions, disk_db):
    for i in range(len(positions)):
        positions[i] += i+1
        positions[i] = positions[i] % disk_db[i+1]
    counter = 0
    while True:
        if are_disks_aligned(positions):
            return counter
        for i in range(len(positions)):
            positions[i] += 1
            positions[i] = positions[i] % disk_db[i+1]
        counter += 1

def are_disks_aligned(positions):
    for elem in positions:
        if elem != 0:
            return False
    return True

if __name__ == "__main__":
    initial_positions, disk_db = get_instructions()
    print(part1(initial_positions, disk_db))
    initial_positions, disk_db = get_instructions()
    initial_positions.append(0)
    disk_db[len(initial_positions)] = 11
    print(part1(initial_positions, disk_db))