def part1(fav_no, target_destination, max_limit = False):
    initial_destination = (1,1)
    visited_destinations = set()
    coordinates_queue = []
    coordinates_queue.append(initial_destination)
    return recursive_helper(fav_no, target_destination, visited_destinations, coordinates_queue, 0, max_limit)
    
def recursive_helper(fav_no, target_destination, visited_destinations, coordinates_queue, step_count, max_limit):
    # BASE CASE 1 --- Maximum number of steps have been achieved
    if max_limit!= False and max_limit == step_count:
        return len(visited_destinations)

    # BASE CASE 2 --- The target destination had been reached
    if target_destination in visited_destinations:
        return step_count
    # RECURSIVE CASE --- Make one more step
    next_step_destinations = []
    possible_next_steps = generate_new_steps(coordinates_queue, fav_no)
    for checked_possible_state in possible_next_steps:
        if checked_possible_state not in visited_destinations:
            next_step_destinations.append(checked_possible_state)
            visited_destinations.add(checked_possible_state)
    return recursive_helper(fav_no, target_destination, visited_destinations, next_step_destinations, step_count + 1, max_limit)

def generate_new_steps(coordinates_queue, fav_no):
    possible_next_steps = []
    for checked_item in coordinates_queue:
        neighbouring_coordinates = []
        neighbouring_coordinates.append((checked_item[0] + 1, checked_item[1]))
        neighbouring_coordinates.append((checked_item[0] - 1, checked_item[1]))
        neighbouring_coordinates.append((checked_item[0], checked_item[1] + 1))
        neighbouring_coordinates.append((checked_item[0], checked_item[1] - 1))
        for neighbouring_coordinate in neighbouring_coordinates:
            if is_not_negative(neighbouring_coordinate) and is_not_wall(neighbouring_coordinate, fav_no):
                possible_next_steps.append(neighbouring_coordinate)
    return possible_next_steps

def is_not_negative(checked_coordinate):
    if -1 in checked_coordinate:
        return False
    else:
        return True

def is_not_wall(checked_coordinate, fav_no):
    initial_value = (checked_coordinate[0] * checked_coordinate[0]) + (3 * checked_coordinate[0]) + (2 * checked_coordinate[0] * checked_coordinate[1]) + checked_coordinate[1] + (checked_coordinate[1] * checked_coordinate[1])
    initial_value += fav_no
    binary_value = format(initial_value, 'b')
    if count_ones_in_binary(binary_value) % 2 == 0:
        return True
    else:
        return False

def count_ones_in_binary(binary_value):
    counter = 0
    for i in range(len(binary_value)):
        if int(binary_value[i]) == 1:
            counter += 1
    return counter

if __name__ == "__main__":
    print(part1(1352, (31,39)))
    print(part1(1352, (31,39), 50))