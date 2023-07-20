import hashlib

def get_instructions():
    f = open("input.txt","r")
    for line in f:
        return line.strip()

def part1(instructions, extended_hashing):
    counter = 0
    valid_hash_pointers = []
    pre_calculated_hashes = {}
    while True:
        result = calculate_hash(instructions, counter, extended_hashing, pre_calculated_hashes)
        triple_search_result = contains_triple(result)
        if triple_search_result != -1:
            for i in range(1000):
                successive_hash = calculate_hash(instructions, counter + i + 1, extended_hashing, pre_calculated_hashes)
                if contains_five(successive_hash, triple_search_result):
                    valid_hash_pointers.append(counter)
                    break
        counter += 1
        if len(valid_hash_pointers) >= 64:
            return valid_hash_pointers[-1]

def calculate_hash(instructions, counter, extended_hashing, pre_calculated_hashes):
    if counter in pre_calculated_hashes:
        return pre_calculated_hashes[counter]
    else:
        str_counter = instructions + str(counter) 
        result = hashlib.md5(str_counter.encode("utf-8")).hexdigest()
        if extended_hashing == True:
            for i in range(2016):
                result = hashlib.md5(result.encode("utf-8")).hexdigest()
        pre_calculated_hashes[counter] = result
        return result


def contains_triple(result):
    for i in range(len(result) - 2):
        if (result[i] == result[i+1] == result[i+2]):
            return result[i]
    return -1

def contains_five(result, triple_search_result):
    for i in range(len(result) - 4):
        if (triple_search_result == result[i] == result[i+1] == result[i+2] == result[i+3] == result[i+4]):
            return True
    return False

if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions, False))
    print(part1(instructions, True))