from itertools import permutations
from copy import deepcopy

def get_instructions():
    f = open("input.txt","r")
    grid = []
    for line in f:
        grid.append(list(line.strip()))
    return grid

def find_common_variables(instructions):
    distances_db = {}
    valid_values = find_valid_values(instructions)
    for elem in valid_values:
        count_distances(instructions, distances_db, elem)
    return distances_db, valid_values

def part1(distances_db, valid_values):
    return count_shortest_route(distances_db, 0, deepcopy(valid_values), False)

def part2(distances_db, valid_values):
    return count_shortest_route(distances_db, 0, deepcopy(valid_values), True)

def count_shortest_route(distances_db, starting_point, valid_values, return_home):
    shortest = 999999999999
    valid_values.remove(starting_point)
    valid_values.sort()
    possible_journeys = permutations(valid_values)
    for checked_journey in possible_journeys:
        checked_journey_as_list = [starting_point]
        checked_journey_as_list += list(checked_journey)
        if return_home == True:
            checked_journey_as_list.append(starting_point)
        distance = calculate_journey_distance(checked_journey_as_list, distances_db)
        if distance < shortest:
            shortest = distance
    return shortest

def calculate_journey_distance(checked_journey, distances_db):
    journey_sum = 0
    for i in range(len(checked_journey) - 1):
        journey_sum += distances_db[(checked_journey[i], checked_journey[i+1])]
    return journey_sum

def count_distances(instructions, distances_db, starting_value):
    starting_coordinate = find_starting_coordinate(instructions, starting_value)
    build_distances_db(instructions, distances_db, starting_value, starting_coordinate)

def find_starting_coordinate(instructions, starting_value):
    for i in range(len(instructions)):
        for j in range (len(instructions[0])):
            if instructions[i][j] == str(starting_value):
                return (i,j)

def build_distances_db(instructions, distances_db, starting_value, starting_coordinate):
    visted_items = set()
    visted_items.add(starting_coordinate)
    step_counter = 1
    build_distances_recursive(instructions, distances_db, starting_value, [starting_coordinate], visted_items, step_counter)

def build_distances_recursive(instructions, distances_db, starting_value, checked_steps, visted_items, step_counter):
    next_steps = []
    for checked_step in checked_steps:
        possible_neighbouring_items = generate_neighbours(checked_step)
        valid_neighbourgs = find_valid_neighbours(instructions, possible_neighbouring_items, visted_items)
        for elem in valid_neighbourgs:
            next_steps.append(elem)
            visted_items.add(elem)
            if instructions[elem[0]][elem[1]].isnumeric() == True:
                distances_db[(starting_value, int(instructions[elem[0]][elem[1]]))] = step_counter
    if len(next_steps) > 0:
        build_distances_recursive(instructions, distances_db, starting_value, next_steps, visted_items, step_counter + 1)

def generate_neighbours(initial_coordinate):
    neighbours = []
    neighbours.append((initial_coordinate[0] + 1, initial_coordinate[1]))
    neighbours.append((initial_coordinate[0], initial_coordinate[1] + 1))
    neighbours.append((initial_coordinate[0] - 1, initial_coordinate[1]))
    neighbours.append((initial_coordinate[0], initial_coordinate[1] - 1))
    return neighbours

def find_valid_neighbours(instructions, possible_neighbouring_items, visted_items):
    valid_coordinates = []
    for elem in possible_neighbouring_items:
        if elem in visted_items:
            continue
        elif instructions[elem[0]][elem[1]] == "#":
            continue
        else:
            valid_coordinates.append(elem)
    return valid_coordinates

def find_valid_values(instructions):
    valid_values = []
    for i in range(len(instructions)):
        for j in range (len(instructions[0])):
            if instructions[i][j].isnumeric() == True:
                valid_values.append(int(instructions[i][j]))
    return valid_values

if __name__ == "__main__":
    instructions = get_instructions()
    distances_db, valid_values = find_common_variables(instructions)
    print(part1(distances_db, valid_values))
    print(part2(distances_db, valid_values))