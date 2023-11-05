def get_instructions():
    f = open("input.txt","r")
    for line in f:
        return int(line.strip())

def part1(instructions):
    elf_circle = CircularList()
    for i in range(instructions):
        elf_circle.add_new_node()
    elf_circle.close_circle()
    while elf_circle.get_size() > 1:
        elf_circle.remove_gifts()
    return elf_circle.get_current_turn().get_order()

class CircularList:
    def __init__(self):
        self.counter = 0
        self.first_inserted_node = None
        self.last_inserted_node = None
        self.taking_turn = None
    
    def add_new_node(self):
        new_node = CircularNode(self.counter + 1, self.last_inserted_node)
        if self.first_inserted_node == None:
            self.first_inserted_node = new_node
        if new_node.previous_node != None:
            new_node.previous_node.next_node = new_node
        self.last_inserted_node = new_node
        self.counter += 1
    
    def close_circle(self):
        self.last_inserted_node.next_node = self.first_inserted_node
        self.first_inserted_node.previous_node = self.last_inserted_node
        self.taking_turn = self.first_inserted_node

    def remove_gifts(self):
        self.taking_turn.next_node = self.taking_turn.next_node.next_node
        self.taking_turn.next_node.previous_node = self.taking_turn
        self.taking_turn = self.taking_turn.next_node
        self.counter -= 1

    def get_size(self):
        return self.counter
    
    def get_current_turn(self):
        return self.taking_turn
    
class CircularNode:
    def __init__(self, order, previous_node):
        self.order = order
        self.previous_node = previous_node
        self.next_node = None
    
    def get_order(self):
        return self.order


if __name__ == "__main__":
    instructions = get_instructions()
    print(part1(instructions))
    # print(part2(instructions))