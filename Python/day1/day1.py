import re
import copy

def get_instructions():
    f = open("input.txt", "r")
    instructions = []
    for line in f:
        x = re.split(", ", line.strip())
        instructions = [
            tuple(filter(None, re.split(r'(\d+)', item)))
            for item in x
        ]
    return instructions

def part1(instructions):
    player_coordinates = [0,0]
    direction = "N"
    for elem in instructions:
        direction, player_coordinates = make_move(player_coordinates, direction, elem)
    return count_manhattan_distance(player_coordinates)

def part2(instructions):
    player_coordinates = (0,0)
    direction = "N"
    visited_nodes = set()
    first_match = None
    for elem in instructions:
        direction, player_coordinates, first_match = make_move_by_one_step(player_coordinates, direction, elem, visited_nodes, first_match)
    return count_manhattan_distance(first_match)

def make_move(player_coordinates, direction, move_instruction):
    direction = turn_player(direction, move_instruction[0])
    new_coordinate = make_moves(player_coordinates, direction, int(move_instruction[1]))
    return direction, new_coordinate

def make_move_by_one_step(player_coordinates, direction, move_instruction, visited_nodes, first_match):
    direction = turn_player(direction, move_instruction[0])
    new_coordinate, first_match = make_incremental_moves(player_coordinates, direction, int(move_instruction[1]), visited_nodes, first_match)
    return direction, new_coordinate, first_match

def turn_player(direction, move_direction_instruction):
    if move_direction_instruction == "R":
        if direction == "N":
            direction = "E"
        elif direction == "E":
            direction = "S"
        elif direction == "S":
            direction = "W"
        else:
            direction = "N"
    elif move_direction_instruction == "L":
        if direction == "N":
            direction = "W"
        elif direction == "W":
            direction = "S"
        elif direction == "S":
            direction = "E"
        else:
            direction = "N"
    return direction

def make_moves(player_coordinates, direction, step_count):
    if direction == "N":
        player_coordinates[0] += step_count
    elif direction == "S":
        player_coordinates[0] -= step_count
    elif direction == "E":
        player_coordinates[1] += step_count
    elif direction == "W":
        player_coordinates[1] -= step_count
    return player_coordinates

def make_incremental_moves(player_coordinates, direction, step_count, visited_nodes, first_match):
    for i in range (step_count):
        if direction == "N":
            player_coordinates = (player_coordinates[0] + 1,  player_coordinates[1])
        elif direction == "S":
            player_coordinates = (player_coordinates[0] - 1,  player_coordinates[1])
        elif direction == "E":
            player_coordinates = (player_coordinates[0],  player_coordinates[1] + 1)
        elif direction == "W":
            player_coordinates = (player_coordinates[0],  player_coordinates[1] - 1)
        print(player_coordinates)
        if player_coordinates not in visited_nodes:
            visited_nodes.add(player_coordinates)
        else:
            if first_match == None:
                first_match = player_coordinates
    return player_coordinates, first_match

def count_manhattan_distance(player_coordinates):
    return abs(player_coordinates[0]) + abs(player_coordinates[1])

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    print(part2(instructions))