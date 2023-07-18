package day13;

import java.util.*;

public class day13 {
    public static void main(String[] args) {
        System.out.println(part1(1352, new Coordinate(31,39), 0));
        System.out.println(part1(1352, new Coordinate(31,39), 50));
    }

    private static int part1(int fav_no, Coordinate target_destination, int max_limit){
        Coordinate initial_destination = new Coordinate(1,1);
        Set<Coordinate> visited_destinations = new HashSet<>();
        List<Coordinate> coordinates_queue = new ArrayList<>();
        coordinates_queue.add(initial_destination);
        return recursive_helper(fav_no, target_destination, visited_destinations, coordinates_queue, 0, max_limit);
    }

    private static int recursive_helper(int fav_no, Coordinate target_destination, Set<Coordinate> visited_destinations, List<Coordinate> coordinates_queue, int step_count, int max_limit){
        if (max_limit > 0 && max_limit == step_count)
            return visited_destinations.size();
        if (max_limit == 0 && visited_destinations.contains(target_destination))
            return step_count;
        List<Coordinate> next_step_destinations = new ArrayList<>();
        List<Coordinate> possible_new_steps = generate_new_steps(coordinates_queue, fav_no);
        for (Coordinate checked_possible_state: possible_new_steps){
            if (!visited_destinations.contains(checked_possible_state)){
                next_step_destinations.add(checked_possible_state);
                visited_destinations.add(checked_possible_state);
            }
        }
        return recursive_helper(fav_no, target_destination, visited_destinations, next_step_destinations, step_count + 1, max_limit);
    }

    private static List<Coordinate> generate_new_steps(List<Coordinate> coordinates_queue, int fav_no){
        List<Coordinate> possible_new_steps = new ArrayList<>();
        for (Coordinate checked_item: coordinates_queue){
            List<Coordinate> neighbouring_coordinates = new ArrayList<>();
            neighbouring_coordinates.add(new Coordinate(checked_item.get_x_coord() + 1, checked_item.get_y_coord()));
            neighbouring_coordinates.add(new Coordinate(checked_item.get_x_coord() - 1, checked_item.get_y_coord()));
            neighbouring_coordinates.add(new Coordinate(checked_item.get_x_coord(), checked_item.get_y_coord() + 1));
            neighbouring_coordinates.add(new Coordinate(checked_item.get_x_coord(), checked_item.get_y_coord() - 1));
            for (Coordinate neighbouring_coordinate: neighbouring_coordinates){
                if (is_not_negative(neighbouring_coordinate) && is_not_wall(neighbouring_coordinate, fav_no))
                    possible_new_steps.add(neighbouring_coordinate);
            }
        }
        return possible_new_steps;
    }

    private static boolean is_not_wall(Coordinate neighbouring_coordinate, int fav_no){
        int initial_value = (neighbouring_coordinate.get_x_coord() * neighbouring_coordinate.get_x_coord()) + (3 * neighbouring_coordinate.get_x_coord()) +
                (2 * neighbouring_coordinate.get_x_coord() * neighbouring_coordinate.get_y_coord()) + neighbouring_coordinate.get_y_coord() +
                (neighbouring_coordinate.get_y_coord() * neighbouring_coordinate.get_y_coord());
        initial_value += fav_no;
        char[] binary_value = Integer.toBinaryString(initial_value).toCharArray();
        if (count_ones_in_binary(binary_value) % 2 == 0)
            return true;
        return false;
    }

    private static int count_ones_in_binary(char[] binary_value){
        int counter = 0;
        for (char checked_char: binary_value){
            if (checked_char == '1')
                counter += 1;
        }
        return counter;
    }

    private static boolean is_not_negative(Coordinate checked_coordinate){
        if (checked_coordinate.get_x_coord() == -1 || checked_coordinate.get_y_coord() == -1)
            return false;
        return true;
    }

    private static class Coordinate {
        private int x_coord;
        private int y_coord;

        public Coordinate(int x_coord, int y_coord) {
            this.x_coord = x_coord;
            this.y_coord = y_coord;
        }

        private int get_x_coord() {
            return this.x_coord;
        }

        private int get_y_coord() {
            return this.y_coord;
        }

        private void change_x_coord(int change_value) {
            this.x_coord += change_value;
        }

        private void change_y_coord(int change_value) {
            this.y_coord += change_value;
        }

        private int count_manhattan_distance() {
            return Math.abs(this.get_x_coord()) + Math.abs(this.get_y_coord());
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }

            if (!(o instanceof Coordinate)) {
                return false;
            }

            Coordinate c = (Coordinate) o;

            if ((this.get_x_coord() == c.get_x_coord()) && (this.get_y_coord() == c.get_y_coord())) return true;
            else return false;
        }

        @Override
        public int hashCode() {
            int hash = 31;
            hash = 31 * hash + this.get_x_coord();
            hash = 31 * hash + this.get_y_coord();
            return hash;
        }
    }
}
