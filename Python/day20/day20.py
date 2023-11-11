def get_instructions():
    f = open("input.txt","r")
    tuples = []
    for line in f:
        numeric_values = line.split("-")
        tuples.append((int(numeric_values[0]), int(numeric_values[1])))
    return tuples

def process_tuples(instructions):
    processed_tuples = []
    processed_tuples.append(instructions[0])
    for i in range(1, len(instructions)):
        tuples_to_be_removed = []
        proposed_tuple = instructions[i]
        proposed_tuple_valid = True
        for j in range(len(processed_tuples)):
            checked_tuple = processed_tuples[j]
            # ALTERNATIVE 1 --- PROPOSED TUPLE IS BIGGER. CHECKED TO BE REMOVED AND PROPOSED TO BE EXTENDED
            if proposed_tuple[0] <= checked_tuple[0] and proposed_tuple[1] >= checked_tuple[1]:
                tuples_to_be_removed.append(checked_tuple)
            # ALTERNATIVE 2 --- PROPOSED TUPLE IS SMALLER. WILL BE IGNORED. CAN BREAK
            elif proposed_tuple[0] >= checked_tuple[0] and proposed_tuple[1] <= checked_tuple[1]:
                proposed_tuple_valid = False
                continue
            # ALTERNATIVE 3 --- LEFT SIDE IS SMALLER
            elif proposed_tuple[0] <= checked_tuple[0] and proposed_tuple[1] <= checked_tuple[1] and proposed_tuple[1] >= checked_tuple[0]:
                tuples_to_be_removed.append(checked_tuple)
                proposed_tuple = (proposed_tuple[0], checked_tuple[1])
                continue
            # ALTERNATIVE 4 --- RIGHT SIDE IS BIGGER
            elif proposed_tuple[0] >= checked_tuple[0] and proposed_tuple[1] >= checked_tuple[1] and proposed_tuple[0] <= checked_tuple[1]:
                tuples_to_be_removed.append(checked_tuple)
                proposed_tuple = (checked_tuple[0], proposed_tuple[1])
                continue
            # ALTERNATIVE 5 --- No overlap
            else:
                pass
        if proposed_tuple_valid == True:
            processed_tuples.append(proposed_tuple)
        for elem in tuples_to_be_removed:
            processed_tuples.remove(elem)
    return processed_tuples

def part1(processed_tuples):
    return(find_gap_value(processed_tuples, find_lowest(processed_tuples)))

def part2(processed_tuples):
    return count_valid(processed_tuples)

def find_gap_value(processed_tuples, starting_value):
    for checked_tuple in processed_tuples:
        if (checked_tuple[0] == starting_value):
            return find_gap_value(processed_tuples, checked_tuple[1] + 1)
    return starting_value

def count_valid(processed_tuples):
    lowest = find_lowest(processed_tuples)
    highest = 4294967295
    potential_total = highest - lowest + 1
    for checked_elem in processed_tuples:
        potential_total = potential_total - ((checked_elem[1] - checked_elem[0]) + 1)
    return potential_total

def find_lowest(processed_tuples):
    lowest = processed_tuples[0][0]
    for i in range(1, len(processed_tuples)):
        if processed_tuples[i][0] < lowest:
            lowest = processed_tuples[i][0]
    return lowest

if __name__ == "__main__":
    processed_tuples = process_tuples(get_instructions())
    print(part1(processed_tuples))
    print(part2(processed_tuples))