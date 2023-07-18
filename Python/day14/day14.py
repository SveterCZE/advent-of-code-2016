import hashlib

def get_instructions():
    f = open("input.txt","r")
    for line in f:
        return line.strip()

def part1(instructions, extended_hashing):
    counter = 0
    valid_hashes = set()
    tentative_hashes = []
    while True:
        str_counter = instructions + str(counter) 
        result = hashlib.md5(str_counter.encode("utf-8")).hexdigest()
        if extended_hashing == True:
            for i in range(2016):
                result = hashlib.md5(result.encode("utf-8")).hexdigest()
        triple_search_result = contains_triple(result)
        if triple_search_result != -1:
            five_search_result = contains_five(result)
            if five_search_result != -1:
               find_valid_matches(five_search_result, tentative_hashes, valid_hashes, counter)
            tentative_hashes.append((counter, triple_search_result, result))
        if len(tentative_hashes) > 0:
            if counter > int(tentative_hashes[0][0]) + 1000:
                tentative_hashes.pop(0)
        counter += 1
        if len(valid_hashes) >= 64:
            return highest_decisive_pointer(valid_hashes)

def contains_triple(result):
    for i in range(len(result) - 2):
        if (result[i] == result[i+1] == result[i+2]):
            return result[i]
    return -1

def contains_five(result):
    for i in range(len(result) - 4):
        if (result[i] == result[i+1] == result[i+2] == result[i+3] == result[i+4]):
            return result[i]
    return -1

def find_valid_matches(five_search_result, tentative_hashes, valid_hashes, counter):
    items_to_be_added = []
    for checked_possible_hash in tentative_hashes:
        if checked_possible_hash[1] == five_search_result:
            items_to_be_added.append(checked_possible_hash)
    for elem in items_to_be_added:
        valid_hashes.add((len(valid_hashes) + 1, elem))

def highest_decisive_pointer(valid_hashes):
    finals = []
    for elem in valid_hashes:
        finals.append(elem)
    finals.sort()
    for elem in finals:
        print(elem)
    return 0

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions, False))
    print(part1(instructions, True))